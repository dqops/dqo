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
import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcherImpl;
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.services.check.mapping.UIAllChecksPatchApplier;
import ai.dqo.services.check.mapping.UIAllChecksModelFactory;
import ai.dqo.services.check.mapping.models.*;
import ai.dqo.services.check.models.UIAllChecksPatchParameters;
import ai.dqo.utils.conversion.StringTypeCaster;
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

        Stream<UICheckContainerModel> columnCheckContainers = patches.stream()
                .map(UIAllChecksModel::getColumnChecksModel)
                .flatMap(model -> model.getUiTableColumnChecksModels().stream())
                .flatMap(model -> model.getUiColumnChecksModels().stream())
                .flatMap(model -> model.getCheckContainers().values().stream());
        Stream<UICheckContainerModel> tableCheckContainers = patches.stream()
                .map(UIAllChecksModel::getTableChecksModel)
                .flatMap(model -> model.getUiSchemaTableChecksModels().stream())
                .flatMap(model -> model.getUiTableChecksModels().stream())
                .flatMap(model -> model.getCheckContainers().values().stream());

        Iterable<UICheckModel> checks = Stream.concat(columnCheckContainers, tableCheckContainers)
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
        patchRuleThresholdsModel(ruleThresholdsModel, parameters);

        List<UIFieldModel> sensorParametersPatches = parameters.getSensorOptions() != null
                ? optionMapToFields(parameters.getSensorOptions())
                : new ArrayList<>();
        patchSensorParametersInModel(model, sensorParametersPatches, parameters.isOverrideConflicts());

        model.setConfigured((ruleThresholdsModel.getWarning() != null && ruleThresholdsModel.getWarning().isConfigured())
                || (ruleThresholdsModel.getError() != null && ruleThresholdsModel.getError().isConfigured())
                || (ruleThresholdsModel.getFatal() != null && ruleThresholdsModel.getFatal().isConfigured()));

        model.setDisabled((ruleThresholdsModel.getWarning() == null || ruleThresholdsModel.getWarning().isDisabled())
                && (ruleThresholdsModel.getError() == null || ruleThresholdsModel.getError().isDisabled())
                && (ruleThresholdsModel.getFatal() == null || ruleThresholdsModel.getFatal().isDisabled()));
    }

    protected void patchSensorParametersInModel(UICheckModel model,
                                                List<UIFieldModel> sensorPatches,
                                                boolean overrideConflicts) {
        Map<String, UIFieldModel> modelSensorParamsByName = new HashMap<>();
        for (UIFieldModel fieldModel: model.getSensorParameters()) {
            modelSensorParamsByName.put(fieldModel.getDefinition().getDisplayName(), fieldModel);
        }

        for (UIFieldModel patch: sensorPatches) {
            String paramName = patch.getDefinition().getDisplayName();
            if (!modelSensorParamsByName.containsKey(paramName)) {
                throw new IllegalArgumentException(String.format("Check %s doesn't have field %s.", model.getCheckName(), paramName));
            }
            if (modelSensorParamsByName.get(paramName).getValue() == null || overrideConflicts) {
                modelSensorParamsByName.put(paramName, patch);
            }
        }

        model.setSensorParameters(new ArrayList<>(modelSensorParamsByName.values()));
    }

    protected void patchRuleThresholdsModel(UIRuleThresholdsModel ruleThresholdsModel, UIAllChecksPatchParameters parameters) {
        boolean isOverride = parameters.isOverrideConflicts();

        if (parameters.getWarningLevelOptions() != null) {
            Map<String, String> options = parameters.getWarningLevelOptions();
            List<UIFieldModel> newParameterFields = this.optionMapToFields(options);

            UIRuleParametersModel ruleParametersModel = ruleThresholdsModel.getWarning();
            if (ruleParametersModel == null) {
                ruleParametersModel = new UIRuleParametersModel();
                ruleThresholdsModel.setWarning(ruleParametersModel);
            }

            this.patchRuleParameters(ruleParametersModel, newParameterFields, isOverride);
        }
        if (parameters.getErrorLevelOptions() != null) {
            Map<String, String> options = parameters.getErrorLevelOptions();
            List<UIFieldModel> newParameterFields = this.optionMapToFields(options);

            UIRuleParametersModel ruleParametersModel = ruleThresholdsModel.getError();
            if (ruleParametersModel == null) {
                ruleParametersModel = new UIRuleParametersModel();
                ruleThresholdsModel.setError(ruleParametersModel);
            }

            this.patchRuleParameters(ruleParametersModel, newParameterFields, isOverride);
        }
        if (parameters.getFatalLevelOptions() != null) {
            Map<String, String> options = parameters.getFatalLevelOptions();
            List<UIFieldModel> newParameterFields = this.optionMapToFields(options);

            UIRuleParametersModel ruleParametersModel = ruleThresholdsModel.getFatal();
            if (ruleParametersModel == null) {
                ruleParametersModel = new UIRuleParametersModel();
                ruleThresholdsModel.setFatal(ruleParametersModel);
            }

            this.patchRuleParameters(ruleParametersModel, newParameterFields, isOverride);
        }

        if (ruleThresholdsModel.getWarning() != null) {
            ruleThresholdsModel.getWarning().setDisabled(parameters.isDisableWarningLevel());
        }
        if (ruleThresholdsModel.getError() != null) {
            ruleThresholdsModel.getError().setDisabled(parameters.isDisableErrorLevel());
        }
        if (ruleThresholdsModel.getFatal() != null) {
            ruleThresholdsModel.getFatal().setDisabled(parameters.isDisableFatalLevel());
        }
    }

    protected void patchRuleParameters(UIRuleParametersModel ruleParametersModel,
                                       List<UIFieldModel> patches,
                                       boolean overrideConflicts) {
        List<UIFieldModel> ruleParameterFields = ruleParametersModel.getRuleParameters();
        if (ruleParameterFields == null) {
            ruleParameterFields = new ArrayList<>();
            ruleParametersModel.setRuleParameters(ruleParameterFields);
        }

        for (UIFieldModel patch: patches) {
            String patchName = patch.getDefinition().getDisplayName();
            Optional<UIFieldModel> shouldOverride = ruleParameterFields.stream()
                    .filter(field -> field.getDefinition().getDisplayName().equals(patchName))
                    .findAny();

            if (shouldOverride.isPresent()) {
                if (overrideConflicts) {
                    UIFieldModel substitute = shouldOverride.get();
                    substitute.setValue(patch.getValue());
                }
            } else {
                ruleParameterFields.add(patch);
            }
        }

        ruleParametersModel.setConfigured(!ruleParameterFields.isEmpty());
    }

    protected List<UIFieldModel> optionMapToFields(Map<String, String> options) {
        List<UIFieldModel> result = new ArrayList<>();

        for (Map.Entry<String, String> option: options.entrySet()) {
            UIFieldModel uiFieldModel = new UIFieldModel();
            ParameterDefinitionSpec parameterDefinition = new ParameterDefinitionSpec();
            parameterDefinition.setDisplayName(option.getKey());
            parameterDefinition.setFieldName(option.getKey());
            uiFieldModel.setDefinition(parameterDefinition);

            this.assignUiFieldModelValue(uiFieldModel, option.getValue());
            result.add(uiFieldModel);
        }
        return result;
    }

    protected void assignUiFieldModelValue(UIFieldModel uiFieldModel, String value) {
        Integer valInt = StringTypeCaster.tryParseInt(value);
        if (valInt != null) {
            uiFieldModel.setIntegerValue(valInt);
            uiFieldModel.getDefinition().setDataType(ParameterDataType.integer_type);
            return;
        }

        Long valLong = StringTypeCaster.tryParseLong(value);
        if (valLong != null) {
            uiFieldModel.setLongValue(valLong);
            uiFieldModel.getDefinition().setDataType(ParameterDataType.long_type);
            return;
        }

        Double valDouble = StringTypeCaster.tryParseDouble(value);
        if (valDouble != null) {
            uiFieldModel.setDoubleValue(valDouble);
            uiFieldModel.getDefinition().setDataType(ParameterDataType.double_type);
            return;
        }

        uiFieldModel.setStringValue(value);
        uiFieldModel.getDefinition().setDataType(ParameterDataType.string_type);
    }

}
