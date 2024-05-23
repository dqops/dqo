/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.services.check.calibration;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckType;
import com.dqops.checks.defaults.DefaultObservabilityConfigurationService;
import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshot;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshotFactory;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;

import java.util.Collection;

/**
 * Data quality check calibration service that can disable data quality checks or reconfigure data quality check thresholds.
 */
@Component
@Slf4j
public class CheckCalibrationServiceImpl implements CheckCalibrationService {
    private final HierarchyNodeTreeSearcher treeSearcher;
    private final DefaultObservabilityConfigurationService defaultObservabilityConfigurationService;
    private final CheckResultsSnapshotFactory checkResultsSnapshotFactory;

    @Autowired
    public CheckCalibrationServiceImpl(
            HierarchyNodeTreeSearcher treeSearcher,
            DefaultObservabilityConfigurationService defaultObservabilityConfigurationService,
            CheckResultsSnapshotFactory checkResultsSnapshotFactory) {
        this.treeSearcher = treeSearcher;
        this.defaultObservabilityConfigurationService = defaultObservabilityConfigurationService;
        this.checkResultsSnapshotFactory = checkResultsSnapshotFactory;
    }

    /**
     * Disable checks matching the filters. The checks are marked as disabled and not removed from the table specification.
     * It also disables checks that were applied by policies (default check patterns).
     * @param checkSearchFilters Check search filter to apply. Must specify the connection name and table name.
     * @param userHome User home used to find the default checks and the target tables.
     * @param disableProfilingChecks Boolean flag that decides to also disable profiling checks if they match the check search filter.
     */
    @Override
    public void disableChecks(CheckSearchFilters checkSearchFilters, UserHome userHome, boolean disableProfilingChecks) {
        Collection<TableWrapper> targetTableWrappers = this.treeSearcher.findTables(userHome.getConnections(), checkSearchFilters);

        for (TableWrapper tableWrapper : targetTableWrappers) {
            TableSpec targetTableSpec = tableWrapper.getSpec();
            ConnectionWrapper connectionWrapper = userHome.findConnectionFor(tableWrapper.getHierarchyId());
            ConnectionSpec connectionSpec = connectionWrapper.getSpec();
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableAndColumns(connectionSpec, targetTableSpec, userHome);

            CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(
                    connectionSpec.getConnectionName(), targetTableSpec.getPhysicalTableName(),
                    CheckResultsColumnNames.CHECK_RESULTS_COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userHome.getUserIdentity());
            checkResultsSnapshot.ensureNRecentMonthsAreLoaded(null, null, 3);
            Table allResults = checkResultsSnapshot.getAllData();
            if (allResults == null) {
                allResults = checkResultsSnapshot.getTableDataChanges().getNewOrChangedRows(); // just an empty table
            }

            CheckResultsNormalizedResult checkResultsAllChecks = new CheckResultsNormalizedResult(allResults, false);


            Collection<AbstractCheckSpec<?, ?, ?, ?>> targetChecksToDisable = this.treeSearcher.findChecks(targetTableSpec, checkSearchFilters);
            for (AbstractCheckSpec<?, ?, ?, ?> checkSpec : targetChecksToDisable) {
                if (!disableProfilingChecks) {
                    AbstractCheckCategorySpec abstractCheckCategorySpec = userHome.findNodeOnPathOfType(checkSpec, AbstractCheckCategorySpec.class);
                    if (abstractCheckCategorySpec.getCheckType() == CheckType.profiling) {
                        continue;
                    }
                }

                if (!checkSpec.hasAnyRulesEnabled()) {
                    continue;
                }

                // disable checks that had issues
                try {
                    CheckSearchFilters checkSearchFiltersForSingleCheck = CheckSearchFilters.fromCheckSpecInstance(connectionSpec, checkSpec);
                    Selection checkResultsSelection = checkResultsAllChecks.findResults(checkSearchFiltersForSingleCheck);

                    Table checkResultsForOneCheck = allResults.where(checkResultsSelection);
                    if (checkResultsForOneCheck.isEmpty()) {
                        continue; // no results for this check
                    }

                    CheckResultsNormalizedResult checkResultsSingleCheck = new CheckResultsNormalizedResult(checkResultsForOneCheck, false);

                    Selection notExecutionErrorResults = checkResultsSingleCheck.getSeverityColumn().isNotEqualTo(4.0)
                            .andNot(checkResultsSingleCheck.getSeverityColumn().isMissing());
                    if (notExecutionErrorResults.isEmpty()) {
                        continue; // only execution errors
                    }

                    Selection resultsWithFailure = checkResultsSingleCheck.getSeverityColumn().isGreaterThan(0.0).andNot(notExecutionErrorResults);
                    if (resultsWithFailure.isEmpty()) {
                        continue; // no errors
                    }

                    checkSpec.setDisabled(true);
                }
                catch (Exception ex) {
                    log.error("Failed to disable the check " + checkSpec.getHierarchyId().toString() + ", error: " + ex.getMessage(), ex);
                }


                if (checkSpec.isDefaultCheck()) {
                    checkSpec.setDefaultCheck(false);
                }
            }

            detachDefaultChecks(targetTableSpec);
        }
    }

    /**
     * Detaches default checks (applied by check patterns) and removes references to them. This operation is required
     * before saving a table specification to a YAML file, because default checks would be saved.
     * @param tableSpec Table specification to clean.
     */
    protected void detachDefaultChecks(TableSpec tableSpec) {
        Collection<AbstractCheckSpec<?, ?, ?, ?>> allChecksOnTable = this.treeSearcher.findChecks(tableSpec, new CheckSearchFilters());
        for (AbstractCheckSpec<?, ?, ?, ?> checkSpec : allChecksOnTable) {
            if (checkSpec.isDefaultCheck()) {
                HierarchyNode[] nodesOnPath = checkSpec.getHierarchyId().getNodesOnPath(tableSpec);
                HierarchyNode parentNode = nodesOnPath[nodesOnPath.length - 2];
                parentNode.detachChildNode(checkSpec.getHierarchyId().getLast());
            }
        }

        tableSpec.detachEmptyCheckNodes();
    }

    /**
     * Decreases the sensitivity of the check by changing the rule severity for all matching checks by changing the rule parameters.
     * @param checkSearchFilters Check search filter to identify target tables to change.
     * @param userHome User home to find default checks and all required objects (connection, etc).
     * @param reconfigureProfilingChecks When true, also reconfigures profiling checks.
     */
    @Override
    public void decreaseCheckSensitivity(CheckSearchFilters checkSearchFilters, UserHome userHome, boolean reconfigureProfilingChecks) {
        Collection<TableWrapper> targetTableWrappers = this.treeSearcher.findTables(userHome.getConnections(), checkSearchFilters);

        for (TableWrapper tableWrapper : targetTableWrappers) {
            TableSpec targetTableSpec = tableWrapper.getSpec();
            ConnectionWrapper connectionWrapper = userHome.findConnectionFor(tableWrapper.getHierarchyId());
            ConnectionSpec connectionSpec = connectionWrapper.getSpec();
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableAndColumns(connectionSpec, targetTableSpec, userHome);

            CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(
                    connectionSpec.getConnectionName(), targetTableSpec.getPhysicalTableName(),
                    CheckResultsColumnNames.CHECK_RESULTS_COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userHome.getUserIdentity());
            checkResultsSnapshot.ensureNRecentMonthsAreLoaded(null, null, 3);
            Table allResults = checkResultsSnapshot.getAllData();
            if (allResults == null) {
                allResults = checkResultsSnapshot.getTableDataChanges().getNewOrChangedRows(); // just an empty table
            }

            CheckResultsNormalizedResult checkResultsAllChecks = new CheckResultsNormalizedResult(allResults, false);

            Collection<AbstractCheckSpec<?, ?, ?, ?>> targetChecksToDisable = this.treeSearcher.findChecks(targetTableSpec, checkSearchFilters);
            for (AbstractCheckSpec<?, ?, ?, ?> checkSpec : targetChecksToDisable) {
                if (!reconfigureProfilingChecks) {
                    AbstractCheckCategorySpec abstractCheckCategorySpec = userHome.findNodeOnPathOfType(checkSpec, AbstractCheckCategorySpec.class);
                    if (abstractCheckCategorySpec.getCheckType() == CheckType.profiling) {
                        continue;
                    }
                }

                if (!checkSpec.hasAnyRulesEnabled()) {
                    continue;
                }

                // apply changes
                try {
                    CheckSearchFilters checkSearchFiltersForSingleCheck = CheckSearchFilters.fromCheckSpecInstance(connectionSpec, checkSpec);
                    Selection checkResultsSelection = checkResultsAllChecks.findResults(checkSearchFiltersForSingleCheck);

                    Table checkResultsForOneCheck = allResults.where(checkResultsSelection);
                    CheckResultsNormalizedResult checkResultsSingleCheck = new CheckResultsNormalizedResult(checkResultsForOneCheck, false);

                    checkSpec.decreaseCheckSensitivity(checkResultsSingleCheck);
                }
                catch (Exception ex) {
                    log.error("Failed to recalibrate the check " + checkSpec.getHierarchyId().toString() + ", error: " + ex.getMessage(), ex);
                }

                if (checkSpec.isDefaultCheck()) {
                    checkSpec.setDefaultCheck(false);
                }
            }

            detachDefaultChecks(targetTableSpec);
        }
    }
}
