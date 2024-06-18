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
package com.dqops.services.check;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.custom.CustomCategoryCheckSpecMap;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.ParentDqoJobQueue;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.jobs.RunChecksQueueJob;
import com.dqops.execution.checks.jobs.RunChecksParameters;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.errorsampling.ErrorSamplerExecutionSummary;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesQueueJob;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesParameters;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.services.check.mapping.AllChecksModelFactory;
import com.dqops.services.check.mapping.AllChecksPatchApplier;
import com.dqops.services.check.mapping.models.*;
import com.dqops.services.check.mapping.models.column.AllColumnChecksModel;
import com.dqops.services.check.mapping.models.table.AllTableChecksModel;
import com.dqops.services.check.mapping.utils.AllChecksModelUtility;
import com.dqops.services.check.models.AllChecksPatchParameters;
import com.dqops.services.check.models.BulkCheckDeactivateParameters;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service called to run checks or operate on checks.
 */
@Service
public class CheckServiceImpl implements CheckService {
    private AllChecksModelFactory allChecksModelFactory;
    private AllChecksPatchApplier allChecksPatchApplier;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private ParentDqoJobQueue parentDqoJobQueue;
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Default injection constructor.
     * @param allChecksModelFactory UI all checks patch factory for creating patches to be updated.
     * @param allChecksPatchApplier UI all checks patch applier for affecting the hierarchy tree with changes from the patch.
     * @param dqoQueueJobFactory Job factory used to create a new instance of a job.
     * @param parentDqoJobQueue DQOps job queue to execute the operation.
     * @param userHomeContextFactory User home context factory.
     */
    @Autowired
    public CheckServiceImpl(AllChecksModelFactory allChecksModelFactory,
                            AllChecksPatchApplier allChecksPatchApplier,
                            DqoQueueJobFactory dqoQueueJobFactory,
                            ParentDqoJobQueue parentDqoJobQueue,
                            UserHomeContextFactory userHomeContextFactory) {
        this.allChecksModelFactory = allChecksModelFactory;
        this.allChecksPatchApplier = allChecksPatchApplier;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.parentDqoJobQueue = parentDqoJobQueue;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Runs checks given the filters.
     * @param checkSearchFilters Check search filters.
     * @param timeWindowFilterParameters Optional user provided time window parameters, limits the time period that is analyzed.
     * @param checkExecutionProgressListener Progress listener that will report the progress.
     * @param dummyRun Run the sensors in a dummy mode (sensors are not executed).
     * @param principal Principal that will be used to run the job.
     * @return Check execution summary.
     */
    @Override
    public CheckExecutionSummary runChecks(CheckSearchFilters checkSearchFilters,
                                           TimeWindowFilterParameters timeWindowFilterParameters,
                                           CheckExecutionProgressListener checkExecutionProgressListener,
										   boolean dummyRun,
                                           DqoUserPrincipal principal) {
        RunChecksQueueJob runChecksJob = this.dqoQueueJobFactory.createRunChecksJob();
        RunChecksParameters parameters = new RunChecksParameters(checkSearchFilters, timeWindowFilterParameters,
                checkExecutionProgressListener, dummyRun);
        runChecksJob.setParameters(parameters);

        this.parentDqoJobQueue.pushJob(runChecksJob, principal);
        return runChecksJob.getResult();
    }

    /**
     * Runs error samplers for the data quality checks identified by a check search filter.
     *
     * @param checkSearchFilters             Check search filters.
     * @param timeWindowFilterParameters     Optional user provided time window parameters, limits the time period that is analyzed.
     * @param errorSamplesDataScope          Error sampling scope (whole table, or per data grouping).
     * @param errorSamplerProgressListener   Progress listener that will report the progress.
     * @param errorSamplesDataScope          Error sampling scope (whole table, or per data grouping).
     * @param dummyRun                       Run the sensors in a dummy mode (sensors are not executed).
     * @param principal                      Principal that will be used to run the job.
     * @return Error sampler execution summary.
     */
    @Override
    public ErrorSamplerExecutionSummary collectErrorSamples(CheckSearchFilters checkSearchFilters,
                                                            TimeWindowFilterParameters timeWindowFilterParameters,
                                                            ErrorSamplesDataScope  errorSamplesDataScope,
                                                            ErrorSamplerExecutionProgressListener errorSamplerProgressListener,
                                                            boolean dummyRun,
                                                            DqoUserPrincipal principal) {
        CollectErrorSamplesQueueJob runChecksJob = this.dqoQueueJobFactory.createCollectErrorSamplesJob();
        CollectErrorSamplesParameters parameters = new CollectErrorSamplesParameters(
                checkSearchFilters, timeWindowFilterParameters, errorSamplesDataScope, errorSamplerProgressListener, dummyRun);
        runChecksJob.setParameters(parameters);

        this.parentDqoJobQueue.pushJob(runChecksJob, principal);
        return runChecksJob.getResult();
    }

    /**
     * Disable existing checks matching the provided filters.
     *
     * @param parameters Bulk check disable parameters.
     * @param principal User principal.
     */
    @Override
    public void disableChecks(BulkCheckDeactivateParameters parameters, DqoUserPrincipal principal) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
        UserHome userHome = userHomeContext.getUserHome();
        HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        Collection<AbstractCheckSpec<?,?,?,?>> checks = hierarchyNodeTreeSearcher.findChecks(userHome, parameters.getCheckSearchFilters());

        if (parameters.getSelectedTablesToColumns() != null && !parameters.getSelectedTablesToColumns().isEmpty()) {
            Map<String, Set<String>> searchableTablesToColumns = getSearchableTableToColumnsMapping(parameters.getSelectedTablesToColumns());

            checks = checks.stream()
                    .filter(check -> {
                        String tableName = userHome.findTableFor(check.getHierarchyId()).getPhysicalTableName().getTableName();

                        // Column is null for table-level checks.
                        ColumnSpec columnNullable = userHome.findColumnFor(check.getHierarchyId());
                        String columnName = columnNullable != null
                                ? columnNullable.getColumnName()
                                : null;

                        if (!searchableTablesToColumns.containsKey(tableName)) {
                            return false;
                        }

                        Set<String> selectedColumns = searchableTablesToColumns.get(tableName);
                        if (columnName != null && !selectedColumns.contains(columnName)) {
                            return false;
                        }

                        return true;
                    }).collect(Collectors.toList());
        }

        for (AbstractCheckSpec<?,?,?,?> check : checks) {
            check.setDisabled(true);
        }

        userHomeContext.flush();
    }

    /**
     * Delete existing checks matching the provided filters.
     *
     * @param parameters Bulk check disable parameters.
     * @param principal User principal.
     */
    @Override
    public void deleteChecks(BulkCheckDeactivateParameters parameters, DqoUserPrincipal principal) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
        UserHome userHome = userHomeContext.getUserHome();
        HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        Collection<AbstractCheckSpec<?,?,?,?>> checks = hierarchyNodeTreeSearcher.findChecks(userHome, parameters.getCheckSearchFilters());

        if (parameters.getSelectedTablesToColumns() != null && !parameters.getSelectedTablesToColumns().isEmpty()) {
            Map<String, Set<String>> searchableTablesToColumns = getSearchableTableToColumnsMapping(parameters.getSelectedTablesToColumns());

            checks = checks.stream()
                    .filter(check -> {
                        String tableName = userHome.findTableFor(check.getHierarchyId()).getPhysicalTableName().getTableName();

                        // Column is null for table-level checks.
                        ColumnSpec columnNullable = userHome.findColumnFor(check.getHierarchyId());
                        String columnName = columnNullable != null
                                ? columnNullable.getColumnName()
                                : null;

                        if (!searchableTablesToColumns.containsKey(tableName)) {
                            return false;
                        }

                        Set<String> selectedColumns = searchableTablesToColumns.get(tableName);
                        if (columnName != null && !selectedColumns.contains(columnName)) {
                            return false;
                        }

                        return true;
                    }).collect(Collectors.toList());
        }

        for (AbstractCheckSpec<?,?,?,?> check : checks) {
            HierarchyId parentHierarchyId = check.getHierarchyId().getParentHierarchyId();
            HierarchyNode checkParentNode = userHome.findNode(parentHierarchyId);
            if (checkParentNode instanceof AbstractCheckCategorySpec) {
                AbstractCheckCategorySpec checkCategoryNode = (AbstractCheckCategorySpec)checkParentNode;
                checkCategoryNode.detachChildNode(check.getCheckName());
            }
            else if (checkParentNode instanceof CustomCategoryCheckSpecMap) {
                CustomCategoryCheckSpecMap customChecksMap = (CustomCategoryCheckSpecMap)checkParentNode;
                customChecksMap.remove(check.getCheckName());
            }
        }

        userHomeContext.flush();
    }

    protected Map<String, Set<String>> getSearchableTableToColumnsMapping(Map<String, List<String>> tableToColumnsMapping) {
        Map<String, Set<String>> searchableMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> tableToColumns: tableToColumnsMapping.entrySet()) {
            Set<String> columnsSet = tableToColumns.getValue() != null
                    ? new LinkedHashSet<>(tableToColumns.getValue())
                    : new LinkedHashSet<>();
            searchableMap.put(tableToColumns.getKey(), columnsSet);
        }
        return searchableMap;
    }

    /**
     * Update checks configuration based on provided parameters.
     * @param parameters Parameters for creating the patches and updating.
     * @param principal  User principal.
     * @return List of patches (by connections) of the updated configuration of all checks.
     */
    @Override
    public List<AllChecksModel> activateOrUpdateAllChecks(AllChecksPatchParameters parameters, DqoUserPrincipal principal) {
        if (parameters == null
                || parameters.getCheckSearchFilters() == null
                || Strings.isNullOrEmpty(parameters.getCheckSearchFilters().getConnection())
                || Strings.isNullOrEmpty(parameters.getCheckSearchFilters().getCheckName())
        ) {
            // Successfully updated nothing.
            return new ArrayList<>();
        }

        List<AllChecksModel> patches = this.allChecksModelFactory.findAllConfiguredAndPossibleChecks(parameters.getCheckSearchFilters(), principal);
        if (parameters.getSelectedTablesToColumns() != null && !parameters.getSelectedTablesToColumns().isEmpty()) {
            for (AllChecksModel patch: patches) {
                AllChecksModelUtility.pruneToConcreteTargets(parameters.getSelectedTablesToColumns(), patch);
            }

            // Discard empty models after pruning.
            patches = patches.stream()
                    .filter(patch -> patch.getTableChecksModel() != null || patch.getColumnChecksModel() != null)
                    .collect(Collectors.toList());
        }

        Stream<CheckContainerModel> collectedCheckContainers = Stream.empty();

        List<AllColumnChecksModel> allColumnChecksModels = patches.stream()
                .map(AllChecksModel::getColumnChecksModel)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!allColumnChecksModels.isEmpty()) {
            Stream<CheckContainerModel> columnCheckContainers = allColumnChecksModels.stream()
                    .flatMap(model -> model.getTableColumnChecksModels().stream())
                    .flatMap(model -> model.getColumnChecksModels().stream())
                    .flatMap(model -> model.getCheckContainers().values().stream());
            collectedCheckContainers = Stream.concat(collectedCheckContainers, columnCheckContainers);
        }

        List<AllTableChecksModel> allTableChecksModels = patches.stream()
                .map(AllChecksModel::getTableChecksModel)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!allTableChecksModels.isEmpty()) {
            Stream<CheckContainerModel> tableCheckContainers = allTableChecksModels.stream()
                    .flatMap(model -> model.getSchemaTableChecksModels().stream())
                    .flatMap(model -> model.getTableChecksModels().stream())
                    .flatMap(model -> model.getCheckContainers().values().stream());
            collectedCheckContainers = Stream.concat(collectedCheckContainers, tableCheckContainers);
        }

        Iterable<CheckModel> checks = collectedCheckContainers
                .flatMap(model -> model.getCategories().stream())
                .flatMap(model -> model.getChecks().stream())
                .collect(Collectors.toList());

        for (CheckModel check: checks) {
            patchCheckModel(check, parameters);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connectionList = userHome.getConnections();

        for (AllChecksModel patch: patches) {
            String connectionName = patch.getConnectionName();
            ConnectionWrapper connectionWrapper = connectionList.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                continue;
            }

            this.allChecksPatchApplier.applyPatchOnConnection(patch, connectionWrapper);
        }
        userHomeContext.flush();

        return patches;
    }

    protected void patchCheckModel(CheckModel model,
                                   AllChecksPatchParameters parameters) {
        RuleThresholdsModel ruleThresholdsModel = model.getRule();
        if (ruleThresholdsModel == null) {
            ruleThresholdsModel = new RuleThresholdsModel();
            model.setRule(ruleThresholdsModel);
        }

        patchRuleThresholdsModel(ruleThresholdsModel,
                parameters.getCheckModelPatch().getRule(),
                parameters.isOverrideConflicts());

        List<FieldModel> newSensorFields = getPatchedFields(model.getSensorParameters(),
                parameters.getCheckModelPatch().getSensorParameters(),
                parameters.isOverrideConflicts());
        model.setSensorParameters(newSensorFields);

        model.setConfigured((ruleThresholdsModel.getWarning() != null && ruleThresholdsModel.getWarning().isConfigured())
                || (ruleThresholdsModel.getError() != null && ruleThresholdsModel.getError().isConfigured())
                || (ruleThresholdsModel.getFatal() != null && ruleThresholdsModel.getFatal().isConfigured()));

        model.setDisabled((ruleThresholdsModel.getWarning() == null || ruleThresholdsModel.getWarning().isDisabled())
                && (ruleThresholdsModel.getError() == null || ruleThresholdsModel.getError().isDisabled())
                && (ruleThresholdsModel.getFatal() == null || ruleThresholdsModel.getFatal().isDisabled()));
    }

    protected void patchRuleThresholdsModel(RuleThresholdsModel ruleThresholdsModel,
                                            RuleThresholdsModel rulePatchModel,
                                            boolean isOverride) {
        if (ruleThresholdsModel.getWarning() == null || !ruleThresholdsModel.getWarning().isConfigured() || isOverride) {
            ruleThresholdsModel.setWarning(rulePatchModel.getWarning());
        }

        if (ruleThresholdsModel.getError() == null || !ruleThresholdsModel.getError().isConfigured() || isOverride) {
            ruleThresholdsModel.setError(rulePatchModel.getError());
        }

        if (ruleThresholdsModel.getFatal() == null || !ruleThresholdsModel.getFatal().isConfigured() || isOverride) {
            ruleThresholdsModel.setFatal(rulePatchModel.getFatal());
        }
    }

    protected List<FieldModel> getPatchedFields(List<FieldModel> sourceFields,
                                                List<FieldModel> patches,
                                                boolean overrideConflicts) {
        Map<String, FieldModel> paramsByName = new LinkedHashMap<>();
        for (FieldModel fieldModel: sourceFields) {
            paramsByName.put(fieldModel.getDefinition().getDisplayName(), fieldModel);
        }

        for (FieldModel patch: patches) {
            String paramName = patch.getDefinition().getDisplayName();
            if (!paramsByName.containsKey(paramName)) {
                throw new IllegalArgumentException(String.format("Check doesn't have field %s.", paramName));
            }
            if (paramsByName.get(paramName).getValue() == null || overrideConflicts) {
                paramsByName.put(paramName, patch);
            }
        }

        return new ArrayList<>(paramsByName.values());
    }
}
