/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.cli.commands.check.impl;

import ai.dqo.cli.commands.check.impl.models.UIAllChecksCliPatchParameters;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.sensors.TimeWindowFilterParameters;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.services.check.CheckService;
import ai.dqo.services.check.mapping.UIAllChecksModelFactory;
import ai.dqo.services.check.mapping.UIAllChecksPatchApplier;
import ai.dqo.services.check.mapping.models.*;
import ai.dqo.services.check.models.UIAllChecksPatchParameters;
import ai.dqo.utils.conversion.StringTypeCaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service called to run checks or operate on checks from CLI.
 */
@Service
public class CheckCliServiceImpl implements CheckCliService {
    private CheckService checkService;
    private UIAllChecksModelFactory uiAllChecksModelFactory;
    private UIAllChecksPatchApplier uiAllChecksPatchApplier;
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Default injection constructor.
     * @param uiAllChecksModelFactory UI all checks patch factory for creating patches to be updated.
     * @param uiAllChecksPatchApplier UI all checks patch applier for affecting the hierarchy tree with changes from the patch.
     * @param userHomeContextFactory User home context factory.
     */
    @Autowired
    public CheckCliServiceImpl(CheckService checkService,
            UIAllChecksModelFactory uiAllChecksModelFactory,
                               UIAllChecksPatchApplier uiAllChecksPatchApplier,
                               UserHomeContextFactory userHomeContextFactory) {
        this.checkService = checkService;
        this.uiAllChecksModelFactory = uiAllChecksModelFactory;
        this.uiAllChecksPatchApplier = uiAllChecksPatchApplier;
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
        return this.checkService.runChecks(checkSearchFilters, timeWindowFilterParameters, checkExecutionProgressListener, dummyRun);
    }

    /**
     * Disable existing checks matching the provided filters.
     *
     * @param filters Check search filters to find checks to disable.
     */
    @Override
    public void disableChecks(CheckSearchFilters filters) {
        this.checkService.disableChecks(filters);
    }

    /**
     * Update checks configuration based on provided parameters.
     * @param parameters Parameters for creating the patches and updating.
     * @return List of patches (by connections) of the updated configuration of all checks.
     */
    @Override
    public List<UIAllChecksModel> updateAllChecksPatch(UIAllChecksCliPatchParameters parameters) {
        UICheckModel sampleModel = this.getSampleCheckModelForUpdates(parameters.getCheckSearchFilters());
        prepareSampleCheckModelForUpdates(sampleModel);
        patchUICheckModel(sampleModel, parameters);

        UIAllChecksPatchParameters uiAllChecksPatchParameters = new UIAllChecksPatchParameters(){{
            setCheckSearchFilters(parameters.getCheckSearchFilters());
            setOverrideConflicts(parameters.isOverrideConflicts());
            setSelectedTablesToColumns(null);
            setUiCheckModelPatch(sampleModel);
        }};

        return this.checkService.updateAllChecksPatch(uiAllChecksPatchParameters);
    }

    protected UICheckModel getSampleCheckModelForUpdates(CheckSearchFilters checkSearchFilters) {
        List<UIAllChecksModel> patches = this.uiAllChecksModelFactory.fromCheckSearchFilters(checkSearchFilters);
        Optional<UICheckModel> sampleUiCheckModelFromTables = patches.stream()
                .map(UIAllChecksModel::getTableChecksModel)
                .flatMap(allTableChecksModel -> allTableChecksModel.getUiSchemaTableChecksModels().stream())
                .flatMap(uiSchemaTableChecksModel -> uiSchemaTableChecksModel.getUiTableChecksModels().stream())
                .flatMap(tableChecksModel -> tableChecksModel.getCheckContainers().values().stream())
                .flatMap(uiCheckContainerModel -> uiCheckContainerModel.getCategories().stream())
                .flatMap(uiQualityCategoryModel -> uiQualityCategoryModel.getChecks().stream())
                .findAny();
        if (sampleUiCheckModelFromTables.isPresent()) {
            return sampleUiCheckModelFromTables.get();
        }

        Optional<UICheckModel> sampleUiCheckModelFromColumns = patches.stream()
                .map(UIAllChecksModel::getColumnChecksModel)
                .flatMap(allColumnChecksModel -> allColumnChecksModel.getUiTableColumnChecksModels().stream())
                .flatMap(uiTableColumnChecksModel -> uiTableColumnChecksModel.getUiColumnChecksModels().stream())
                .flatMap(columnChecksModel -> columnChecksModel.getCheckContainers().values().stream())
                .flatMap(uiCheckContainerModel -> uiCheckContainerModel.getCategories().stream())
                .flatMap(uiQualityCategoryModel -> uiQualityCategoryModel.getChecks().stream())
                .findAny();

        return sampleUiCheckModelFromColumns.orElse(null);
    }

    protected void prepareSampleCheckModelForUpdates(UICheckModel uiCheckModel) {
        if (uiCheckModel == null) {
            return;
        }

        uiCheckModel.setSensorParametersSpec(null);
        uiCheckModel.setCheckSpec(null);

//        for (UIFieldModel sensorParameterField: uiCheckModel.getSensorParameters()) {
//            sensorParameterField.setValue(null);
//        }
//
//        UIRuleThresholdsModel ruleThresholdsModel = uiCheckModel.getRule();
//        if (ruleThresholdsModel != null) {
//            UIRuleParametersModel warningRule = ruleThresholdsModel.getWarning();
//            UIRuleParametersModel errorRule = ruleThresholdsModel.getError();
//            UIRuleParametersModel fatalRule = ruleThresholdsModel.getFatal();
//            List<UIRuleParametersModel> rules = Stream.of(warningRule, errorRule, fatalRule).collect(Collectors.toList());
//            for (UIRuleParametersModel rule: rules) {
//                assert rule != null;
//                for (UIFieldModel ruleParameterField: rule.getRuleParameters()) {
//                    ruleParameterField.setValue(null);
//                }
//                rule.setDisabled(false);
//                rule.setConfigured(false);
//                rule.setRuleParametersSpec(null);
//            }
//        }
        uiCheckModel.applySampleValues();

        uiCheckModel.setDataStreamsOverride(null);
        uiCheckModel.setScheduleOverride(null);
        uiCheckModel.setEffectiveSchedule(null);
        uiCheckModel.setScheduleEnabledStatus(UIScheduleEnabledStatus.not_configured);
        uiCheckModel.setComments(new CommentsListSpec());

        uiCheckModel.setDisabled(false);
        uiCheckModel.setExcludeFromKpi(false);
        uiCheckModel.setIncludeInSla(false);
        uiCheckModel.setConfigured(true);

        uiCheckModel.setFilter(null);
        uiCheckModel.setRunChecksJobTemplate(null);
        uiCheckModel.setDataCleanJobTemplate(null);
        uiCheckModel.setDataStream(null);
        uiCheckModel.setConfigurationRequirementsErrors(null);
    }

    protected void patchUICheckModel(UICheckModel model,
                                     UIAllChecksCliPatchParameters parameters) {
        UIRuleThresholdsModel ruleThresholdsModel = model.getRule();
        patchRuleThresholdsModel(ruleThresholdsModel, parameters);

        List<UIFieldModel> sensorParametersPatches = parameters.getSensorOptions() != null
                ? optionMapToFields(parameters.getSensorOptions())
                : new ArrayList<>();
        patchSensorParametersInModel(model, sensorParametersPatches);
    }

    protected void patchSensorParametersInModel(UICheckModel model,
                                                List<UIFieldModel> sensorPatches) {
        Map<String, UIFieldModel> modelSensorParamsByName = new HashMap<>();
        for (UIFieldModel fieldModel: model.getSensorParameters()) {
            modelSensorParamsByName.put(fieldModel.getDefinition().getDisplayName(), fieldModel);
        }

        for (UIFieldModel patch: sensorPatches) {
            String paramName = patch.getDefinition().getDisplayName();
            modelSensorParamsByName.put(paramName, patch);
        }

        model.setSensorParameters(new ArrayList<>(modelSensorParamsByName.values()));
    }

    protected void patchRuleThresholdsModel(UIRuleThresholdsModel ruleThresholdsModel, UIAllChecksCliPatchParameters parameters) {
        if (parameters.getWarningLevelOptions() != null) {
            Map<String, String> options = parameters.getWarningLevelOptions();
            List<UIFieldModel> newParameterFields = this.optionMapToFields(options);

            UIRuleParametersModel ruleParametersModel = ruleThresholdsModel.getWarning();
            this.patchRuleParameters(ruleParametersModel, newParameterFields);
        }
        if (parameters.getErrorLevelOptions() != null) {
            Map<String, String> options = parameters.getErrorLevelOptions();
            List<UIFieldModel> newParameterFields = this.optionMapToFields(options);

            UIRuleParametersModel ruleParametersModel = ruleThresholdsModel.getError();
            this.patchRuleParameters(ruleParametersModel, newParameterFields);
        }
        if (parameters.getFatalLevelOptions() != null) {
            Map<String, String> options = parameters.getFatalLevelOptions();
            List<UIFieldModel> newParameterFields = this.optionMapToFields(options);

            UIRuleParametersModel ruleParametersModel = ruleThresholdsModel.getFatal();
            this.patchRuleParameters(ruleParametersModel, newParameterFields);
        }
    }

    protected void patchRuleParameters(UIRuleParametersModel ruleParametersModel,
                                       List<UIFieldModel> patches) {
        List<UIFieldModel> ruleParameterFields = ruleParametersModel.getRuleParameters();

        for (UIFieldModel patch: patches) {
            String patchName = patch.getDefinition().getDisplayName();
            Optional<UIFieldModel> shouldOverride = ruleParameterFields.stream()
                    .filter(field -> field.getDefinition().getDisplayName().equals(patchName))
                    .findAny();

            if (shouldOverride.isPresent()) {
                UIFieldModel substitute = shouldOverride.get();
                substitute.setValue(patch.getValue());
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
