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
import ai.dqo.services.check.CheckService;
import ai.dqo.services.check.mapping.AllChecksModelFactory;
import ai.dqo.services.check.mapping.models.*;
import ai.dqo.services.check.models.BulkCheckDisableParameters;
import ai.dqo.services.check.models.AllChecksPatchParameters;
import ai.dqo.utils.conversion.StringTypeCaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service called to run checks or operate on checks from CLI.
 */
@Service
public class CheckCliServiceImpl implements CheckCliService {
    private final CheckService checkService;
    private final AllChecksModelFactory allChecksModelFactory;

    /**
     * Default injection constructor.
     * @param allChecksModelFactory UI all checks patch factory for creating patches to be updated.
     */
    @Autowired
    public CheckCliServiceImpl(CheckService checkService,
                               AllChecksModelFactory allChecksModelFactory) {
        this.checkService = checkService;
        this.allChecksModelFactory = allChecksModelFactory;
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
        BulkCheckDisableParameters parameters = new BulkCheckDisableParameters();
        parameters.setCheckSearchFilters(filters);
        this.checkService.disableChecks(parameters);
    }

    /**
     * Update checks configuration based on provided parameters.
     * @param parameters Parameters for creating the patches and updating.
     * @return List of patches (by connections) of the updated configuration of all checks.
     */
    @Override
    public List<AllChecksModel> updateAllChecksPatch(UIAllChecksCliPatchParameters parameters) {
        CheckModel sampleModel = this.getSampleCheckModelForUpdates(parameters.getCheckSearchFilters());
        prepareSampleCheckModelForUpdates(sampleModel);
        patchUICheckModel(sampleModel, parameters);

        AllChecksPatchParameters allChecksPatchParameters = new AllChecksPatchParameters(){{
            setCheckSearchFilters(parameters.getCheckSearchFilters());
            setOverrideConflicts(parameters.isOverrideConflicts());
            setSelectedTablesToColumns(null);
            setCheckModelPatch(sampleModel);
        }};

        return this.checkService.updateAllChecksPatch(allChecksPatchParameters);
    }

    protected CheckModel getSampleCheckModelForUpdates(CheckSearchFilters checkSearchFilters) {
        List<AllChecksModel> patches = this.allChecksModelFactory.fromCheckSearchFilters(checkSearchFilters);
        Optional<CheckModel> sampleUiCheckModelFromTables = patches.stream()
                .map(AllChecksModel::getTableChecksModel)
                .flatMap(allTableChecksModel -> allTableChecksModel.getSchemaTableChecksModels().stream())
                .flatMap(uiSchemaTableChecksModel -> uiSchemaTableChecksModel.getTableChecksModels().stream())
                .flatMap(tableChecksModel -> tableChecksModel.getCheckContainers().values().stream())
                .flatMap(uiCheckContainerModel -> uiCheckContainerModel.getCategories().stream())
                .flatMap(uiQualityCategoryModel -> uiQualityCategoryModel.getChecks().stream())
                .findAny();
        if (sampleUiCheckModelFromTables.isPresent()) {
            return sampleUiCheckModelFromTables.get();
        }

        Optional<CheckModel> sampleUiCheckModelFromColumns = patches.stream()
                .map(AllChecksModel::getColumnChecksModel)
                .flatMap(allColumnChecksModel -> allColumnChecksModel.getTableColumnChecksModels().stream())
                .flatMap(uiTableColumnChecksModel -> uiTableColumnChecksModel.getColumnChecksModels().stream())
                .flatMap(columnChecksModel -> columnChecksModel.getCheckContainers().values().stream())
                .flatMap(uiCheckContainerModel -> uiCheckContainerModel.getCategories().stream())
                .flatMap(uiQualityCategoryModel -> uiQualityCategoryModel.getChecks().stream())
                .findAny();

        return sampleUiCheckModelFromColumns.orElse(null);
    }

    protected void prepareSampleCheckModelForUpdates(CheckModel checkModel) {
        if (checkModel == null) {
            return;
        }

        checkModel.setSensorParametersSpec(null);
        checkModel.setCheckSpec(null);

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
        checkModel.applySampleValues();

        checkModel.setDataStreamsOverride(null);
        checkModel.setScheduleOverride(null);
        checkModel.setEffectiveSchedule(null);
        checkModel.setScheduleEnabledStatus(ScheduleEnabledStatusModel.not_configured);
        checkModel.setComments(new CommentsListSpec());

        checkModel.setDisabled(false);
        checkModel.setExcludeFromKpi(false);
        checkModel.setIncludeInSla(false);
        checkModel.setConfigured(true);

        checkModel.setFilter(null);
        checkModel.setRunChecksJobTemplate(null);
        checkModel.setDataCleanJobTemplate(null);
        checkModel.setDataStream(null);
        checkModel.setConfigurationRequirementsErrors(null);
    }

    protected void patchUICheckModel(CheckModel model,
                                     UIAllChecksCliPatchParameters parameters) {
        RuleThresholdsModel ruleThresholdsModel = model.getRule();
        patchRuleThresholdsModel(ruleThresholdsModel, parameters);

        List<FieldModel> sensorParametersPatches = parameters.getSensorOptions() != null
                ? optionMapToFields(parameters.getSensorOptions())
                : new ArrayList<>();
        patchSensorParametersInModel(model, sensorParametersPatches);
    }

    protected void patchSensorParametersInModel(CheckModel model,
                                                List<FieldModel> sensorPatches) {
        Map<String, FieldModel> modelSensorParamsByName = new HashMap<>();
        for (FieldModel fieldModel: model.getSensorParameters()) {
            modelSensorParamsByName.put(fieldModel.getDefinition().getDisplayName(), fieldModel);
        }

        for (FieldModel patch: sensorPatches) {
            String paramName = patch.getDefinition().getDisplayName();
            modelSensorParamsByName.put(paramName, patch);
        }

        model.setSensorParameters(new ArrayList<>(modelSensorParamsByName.values()));
    }

    protected void patchRuleThresholdsModel(RuleThresholdsModel ruleThresholdsModel, UIAllChecksCliPatchParameters parameters) {
        if (parameters.getWarningLevelOptions() != null) {
            Map<String, String> options = parameters.getWarningLevelOptions();
            List<FieldModel> newParameterFields = this.optionMapToFields(options);

            RuleParametersModel ruleParametersModel = ruleThresholdsModel.getWarning();
            this.patchRuleParameters(ruleParametersModel, newParameterFields);
        }
        if (parameters.getErrorLevelOptions() != null) {
            Map<String, String> options = parameters.getErrorLevelOptions();
            List<FieldModel> newParameterFields = this.optionMapToFields(options);

            RuleParametersModel ruleParametersModel = ruleThresholdsModel.getError();
            this.patchRuleParameters(ruleParametersModel, newParameterFields);
        }
        if (parameters.getFatalLevelOptions() != null) {
            Map<String, String> options = parameters.getFatalLevelOptions();
            List<FieldModel> newParameterFields = this.optionMapToFields(options);

            RuleParametersModel ruleParametersModel = ruleThresholdsModel.getFatal();
            this.patchRuleParameters(ruleParametersModel, newParameterFields);
        }
    }

    protected void patchRuleParameters(RuleParametersModel ruleParametersModel,
                                       List<FieldModel> patches) {
        List<FieldModel> ruleParameterFields = ruleParametersModel.getRuleParameters();

        for (FieldModel patch: patches) {
            String patchName = patch.getDefinition().getDisplayName();
            Optional<FieldModel> shouldOverride = ruleParameterFields.stream()
                    .filter(field -> field.getDefinition().getDisplayName().equals(patchName))
                    .findAny();

            if (shouldOverride.isPresent()) {
                FieldModel substitute = shouldOverride.get();
                substitute.setValue(patch.getValue());
            } else {
                ruleParameterFields.add(patch);
            }
        }

        ruleParametersModel.setConfigured(!ruleParameterFields.isEmpty());
    }

    protected List<FieldModel> optionMapToFields(Map<String, String> options) {
        List<FieldModel> result = new ArrayList<>();

        for (Map.Entry<String, String> option: options.entrySet()) {
            FieldModel fieldModel = new FieldModel();
            ParameterDefinitionSpec parameterDefinition = new ParameterDefinitionSpec();
            parameterDefinition.setDisplayName(option.getKey());
            parameterDefinition.setFieldName(option.getKey());
            fieldModel.setDefinition(parameterDefinition);

            this.assignUiFieldModelValue(fieldModel, option.getValue());
            result.add(fieldModel);
        }
        return result;
    }

    protected void assignUiFieldModelValue(FieldModel fieldModel, String value) {
        Integer valInt = StringTypeCaster.tryParseInt(value);
        if (valInt != null) {
            fieldModel.setIntegerValue(valInt);
            fieldModel.getDefinition().setDataType(ParameterDataType.integer_type);
            return;
        }

        Long valLong = StringTypeCaster.tryParseLong(value);
        if (valLong != null) {
            fieldModel.setLongValue(valLong);
            fieldModel.getDefinition().setDataType(ParameterDataType.long_type);
            return;
        }

        Double valDouble = StringTypeCaster.tryParseDouble(value);
        if (valDouble != null) {
            fieldModel.setDoubleValue(valDouble);
            fieldModel.getDefinition().setDataType(ParameterDataType.double_type);
            return;
        }

        fieldModel.setStringValue(value);
        fieldModel.getDefinition().setDataType(ParameterDataType.string_type);
    }
}
