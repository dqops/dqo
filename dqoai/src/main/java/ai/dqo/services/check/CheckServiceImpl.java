/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.services.check;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.ParentDqoJobQueue;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.jobs.RunChecksQueueJob;
import ai.dqo.execution.checks.jobs.RunChecksQueueJobParameters;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.sensors.TimeWindowFilterParameters;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcherImpl;
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.services.check.mapping.UIAllChecksModelFactory;
import ai.dqo.services.check.mapping.UIAllChecksPatchApplier;
import ai.dqo.services.check.mapping.models.*;
import ai.dqo.services.check.mapping.models.column.UIAllColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.UIColumnChecksModel;
import ai.dqo.services.check.mapping.models.table.UIAllTableChecksModel;
import ai.dqo.services.check.mapping.utils.UIAllChecksModelUtility;
import ai.dqo.services.check.models.UIAllChecksPatchParameters;
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
    private UIAllChecksModelFactory uiAllChecksModelFactory;
    private UIAllChecksPatchApplier uiAllChecksPatchApplier;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private ParentDqoJobQueue parentDqoJobQueue;
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Default injection constructor.
     * @param uiAllChecksModelFactory UI all checks patch factory for creating patches to be updated.
     * @param uiAllChecksPatchApplier UI all checks patch applier for affecting the hierarchy tree with changes from the patch.
     * @param dqoQueueJobFactory Job factory used to create a new instance of a job.
     * @param parentDqoJobQueue DQO job queue to execute the operation.
     * @param userHomeContextFactory User home context factory.
     */
    @Autowired
    public CheckServiceImpl(UIAllChecksModelFactory uiAllChecksModelFactory,
                            UIAllChecksPatchApplier uiAllChecksPatchApplier,
                            DqoQueueJobFactory dqoQueueJobFactory,
                            ParentDqoJobQueue parentDqoJobQueue,
                            UserHomeContextFactory userHomeContextFactory) {
        this.uiAllChecksModelFactory = uiAllChecksModelFactory;
        this.uiAllChecksPatchApplier = uiAllChecksPatchApplier;
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
     * @return Check execution summary.
     */
    @Override
    public CheckExecutionSummary runChecks(CheckSearchFilters checkSearchFilters,
                                           TimeWindowFilterParameters timeWindowFilterParameters,
                                           CheckExecutionProgressListener checkExecutionProgressListener,
										   boolean dummyRun) {
        RunChecksQueueJob runChecksJob = this.dqoQueueJobFactory.createRunChecksJob();
        RunChecksQueueJobParameters parameters = new RunChecksQueueJobParameters(checkSearchFilters, timeWindowFilterParameters,
                checkExecutionProgressListener, dummyRun);
        runChecksJob.setParameters(parameters);

        this.parentDqoJobQueue.pushJob(runChecksJob);
        return runChecksJob.getResult();
    }

    /**
     * Disable existing checks matching the provided filters.
     *
     * @param filters Check search filters to find checks to disable.
     */
    @Override
    public void disableChecks(CheckSearchFilters filters) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        Collection<AbstractCheckSpec<?,?,?,?>> checks = hierarchyNodeTreeSearcher.findChecks(userHome, filters);

        for (AbstractCheckSpec<?,?,?,?> check: checks) {
            check.setDisabled(true);
        }
        userHomeContext.flush();
    }

    /**
     * Update checks configuration based on provided parameters.
     * @param parameters Parameters for creating the patches and updating.
     * @return List of patches (by connections) of the updated configuration of all checks.
     */
    @Override
    public List<UIAllChecksModel> updateAllChecksPatch(UIAllChecksPatchParameters parameters) {
        if (parameters == null
                || parameters.getCheckSearchFilters() == null
                || Strings.isNullOrEmpty(parameters.getCheckSearchFilters().getConnectionName())
                || Strings.isNullOrEmpty(parameters.getCheckSearchFilters().getCheckName())
        ) {
            // Successfully updated nothing.
            return new ArrayList<>();
        }

        List<UIAllChecksModel> patches = this.uiAllChecksModelFactory.fromCheckSearchFilters(parameters.getCheckSearchFilters());
        if (parameters.getSelectedTablesToColumns() != null) {
            for (UIAllChecksModel patch: patches) {
                UIAllChecksModelUtility.pruneToConcreteTargets(parameters.getSelectedTablesToColumns(), patch);
            }

            // Discard empty models after pruning.
            patches = patches.stream()
                    .filter(patch -> patch.getTableChecksModel() != null || patch.getColumnChecksModel() != null)
                    .collect(Collectors.toList());
        }

        Stream<UICheckContainerModel> collectedCheckContainers = Stream.empty();

        List<UIAllColumnChecksModel> allColumnChecksModels = patches.stream()
                .map(UIAllChecksModel::getColumnChecksModel)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!allColumnChecksModels.isEmpty()) {
            Stream<UICheckContainerModel> columnCheckContainers = allColumnChecksModels.stream()
                    .flatMap(model -> model.getUiTableColumnChecksModels().stream())
                    .flatMap(model -> model.getUiColumnChecksModels().stream())
                    .flatMap(model -> model.getCheckContainers().values().stream());
            collectedCheckContainers = Stream.concat(collectedCheckContainers, columnCheckContainers);
        }

        List<UIAllTableChecksModel> allTableChecksModels = patches.stream()
                .map(UIAllChecksModel::getTableChecksModel)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!allTableChecksModels.isEmpty()) {
            Stream<UICheckContainerModel> tableCheckContainers = allTableChecksModels.stream()
                    .flatMap(model -> model.getUiSchemaTableChecksModels().stream())
                    .flatMap(model -> model.getUiTableChecksModels().stream())
                    .flatMap(model -> model.getCheckContainers().values().stream());
            collectedCheckContainers = Stream.concat(collectedCheckContainers, tableCheckContainers);
        }

        Iterable<UICheckModel> checks = collectedCheckContainers
                .flatMap(model -> model.getCategories().stream())
                .flatMap(model -> model.getChecks().stream())
                .collect(Collectors.toList());

        for (UICheckModel check: checks) {
            patchUICheckModel(check, parameters);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connectionList = userHome.getConnections();

        for (UIAllChecksModel patch: patches) {
            String connectionName = patch.getConnectionName();
            ConnectionWrapper connectionWrapper = connectionList.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                continue;
            }

            this.uiAllChecksPatchApplier.applyPatchOnConnection(patch, connectionWrapper);
        }
        userHomeContext.flush();

        return patches;
    }

    protected void patchUICheckModel(UICheckModel model,
                                     UIAllChecksPatchParameters parameters) {
        UIRuleThresholdsModel ruleThresholdsModel = model.getRule();
        if (ruleThresholdsModel == null) {
            ruleThresholdsModel = new UIRuleThresholdsModel();
            model.setRule(ruleThresholdsModel);
        }

        patchRuleThresholdsModel(ruleThresholdsModel,
                parameters.getUiCheckModelPatch().getRule(),
                parameters.isOverrideConflicts());

        List<UIFieldModel> newSensorFields = getPatchedFields(model.getSensorParameters(),
                parameters.getUiCheckModelPatch().getSensorParameters(),
                parameters.isOverrideConflicts());
        model.setSensorParameters(newSensorFields);

        model.setConfigured((ruleThresholdsModel.getWarning() != null && ruleThresholdsModel.getWarning().isConfigured())
                || (ruleThresholdsModel.getError() != null && ruleThresholdsModel.getError().isConfigured())
                || (ruleThresholdsModel.getFatal() != null && ruleThresholdsModel.getFatal().isConfigured()));

        model.setDisabled((ruleThresholdsModel.getWarning() == null || ruleThresholdsModel.getWarning().isDisabled())
                && (ruleThresholdsModel.getError() == null || ruleThresholdsModel.getError().isDisabled())
                && (ruleThresholdsModel.getFatal() == null || ruleThresholdsModel.getFatal().isDisabled()));
    }

    protected void patchRuleThresholdsModel(UIRuleThresholdsModel ruleThresholdsModel,
                                            UIRuleThresholdsModel rulePatchModel,
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

    protected List<UIFieldModel> getPatchedFields(List<UIFieldModel> sourceFields,
                                                  List<UIFieldModel> patches,
                                                  boolean overrideConflicts) {
        Map<String, UIFieldModel> paramsByName = new HashMap<>();
        for (UIFieldModel fieldModel: sourceFields) {
            paramsByName.put(fieldModel.getDefinition().getDisplayName(), fieldModel);
        }

        for (UIFieldModel patch: patches) {
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
