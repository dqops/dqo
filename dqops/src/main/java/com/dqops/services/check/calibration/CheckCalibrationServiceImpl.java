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
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Data quality check calibration service that can disable data quality checks or reconfigure data quality check thresholds.
 */
@Component
public class CheckCalibrationServiceImpl implements CheckCalibrationService {
    private final HierarchyNodeTreeSearcher treeSearcher;
    private final DefaultObservabilityConfigurationService defaultObservabilityConfigurationService;

    @Autowired
    public CheckCalibrationServiceImpl(
            HierarchyNodeTreeSearcher treeSearcher,
            DefaultObservabilityConfigurationService defaultObservabilityConfigurationService) {
        this.treeSearcher = treeSearcher;
        this.defaultObservabilityConfigurationService = defaultObservabilityConfigurationService;
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
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableAndColumns(connectionWrapper.getSpec(), targetTableSpec, userHome);

            Collection<AbstractCheckSpec<?, ?, ?, ?>> targetChecksToDisable = this.treeSearcher.findChecks(targetTableSpec, checkSearchFilters);
            for (AbstractCheckSpec<?, ?, ?, ?> checkSpec : targetChecksToDisable) {
                if (!disableProfilingChecks) {
                    AbstractCheckCategorySpec abstractCheckCategorySpec = userHome.findNodeOnPathOfType(checkSpec, AbstractCheckCategorySpec.class);
                    if (abstractCheckCategorySpec.getCheckType() == CheckType.profiling) {
                        continue;
                    }
                }

                checkSpec.setDisabled(true);
                if (checkSpec.isDefaultCheck()) {
                    checkSpec.setDefaultCheck(false);
                }
            }

            Collection<AbstractCheckSpec<?, ?, ?, ?>> allChecksOnTable = this.treeSearcher.findChecks(targetTableSpec, new CheckSearchFilters());
            for (AbstractCheckSpec<?, ?, ?, ?> checkSpec : allChecksOnTable) {
                if (checkSpec.isDefaultCheck()) {
                    HierarchyNode[] nodesOnPath = checkSpec.getHierarchyId().getNodesOnPath(targetTableSpec);
                    HierarchyNode parentNode = nodesOnPath[nodesOnPath.length - 2];
                    parentNode.detachChildNode(checkSpec.getHierarchyId().getLast());
                }
            }
        }
    }
}
