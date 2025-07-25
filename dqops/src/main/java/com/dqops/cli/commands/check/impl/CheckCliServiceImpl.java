/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.check.impl;

import com.dqops.cli.commands.check.impl.models.AllChecksModelCliPatchParameters;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.RunChecksTarget;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.errorsampling.ErrorSamplerExecutionSummary;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.services.check.CheckService;
import com.dqops.services.check.mapping.AllChecksModelFactory;
import com.dqops.services.check.mapping.models.*;
import com.dqops.services.check.models.BulkCheckDeactivateParameters;
import com.dqops.services.check.models.AllChecksPatchParameters;
import com.dqops.utils.conversion.StringTypeCaster;
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
    private final DqoUserPrincipalProvider apiKeyPrincipalProvider;

    /**
     * Default injection constructor.
     * @param allChecksModelFactory UI all checks patch factory for creating patches to be updated.
     */
    @Autowired
    public CheckCliServiceImpl(CheckService checkService,
                               AllChecksModelFactory allChecksModelFactory,
                               DqoUserPrincipalProvider apiKeyPrincipalProvider) {
        this.checkService = checkService;
        this.allChecksModelFactory = allChecksModelFactory;
        this.apiKeyPrincipalProvider = apiKeyPrincipalProvider;
    }

    /**
     * Runs checks given the filters.
     * @param checkSearchFilters Check search filters.
     * @param timeWindowFilterParameters Optional user provided time window parameters, limits the time period that is analyzed.
     * @param collectErrorSamples Collect error samples for failed checks.
     * @param checkExecutionProgressListener Progress listener that will report the progress.
     * @param dummyRun Run the sensors in a dummy mode (sensors are not executed).
     * @param executionTarget Execution target (sensor, rule, both).
     * @return Check execution summary.
     */
    @Override
    public CheckExecutionSummary runChecks(CheckSearchFilters checkSearchFilters,
                                           TimeWindowFilterParameters timeWindowFilterParameters,
                                           boolean collectErrorSamples,
                                           CheckExecutionProgressListener checkExecutionProgressListener,
										   boolean dummyRun,
                                           RunChecksTarget executionTarget) {
        DqoUserPrincipal principal = this.apiKeyPrincipalProvider.getLocalUserPrincipal();
        return this.checkService.runChecks(checkSearchFilters, timeWindowFilterParameters, collectErrorSamples,
                checkExecutionProgressListener, dummyRun, executionTarget, principal);
    }

    /**
     * Collects error samples for data quality checks identified by filters.
     *
     * @param checkSearchFilters                    Check search filters.
     * @param timeWindowFilterParameters            Optional user provided time window parameters, limits the time period that is analyzed.
     * @param errorSamplesDataScope                 Error sampling data scope - a whole table, or each data grouping.
     * @param errorSamplerExecutionProgressListener Progress listener that will report the progress.
     * @param dummyRun                              Run the sensors in a dummy mode (sensors are not executed).
     * @return Error sampler execution summary.
     */
    @Override
    public ErrorSamplerExecutionSummary collectErrorSamples(CheckSearchFilters checkSearchFilters,
                                                            TimeWindowFilterParameters timeWindowFilterParameters,
                                                            ErrorSamplesDataScope errorSamplesDataScope,
                                                            ErrorSamplerExecutionProgressListener errorSamplerExecutionProgressListener,
                                                            boolean dummyRun) {
        DqoUserPrincipal principal = this.apiKeyPrincipalProvider.getLocalUserPrincipal();
        return this.checkService.collectErrorSamples(checkSearchFilters, timeWindowFilterParameters, errorSamplesDataScope,
                errorSamplerExecutionProgressListener, dummyRun, principal);
    }

    /**
     * Deactivates existing checks matching the provided filters.
     *
     * @param filters Check search filters to find checks to disable.
     */
    @Override
    public void deactivateChecks(CheckSearchFilters filters) {
        DqoUserPrincipal userPrincipal = this.apiKeyPrincipalProvider.getLocalUserPrincipal();
        BulkCheckDeactivateParameters parameters = new BulkCheckDeactivateParameters();
        parameters.setCheckSearchFilters(filters);
        this.checkService.deleteChecks(parameters, userPrincipal);
    }

    /**
     * Update checks configuration based on provided parameters.
     * @param parameters Parameters for creating the patches and updating.
     * @return List of patches (by connections) of the updated configuration of all checks.
     */
    @Override
    public List<AllChecksModel> updateAllChecksPatch(AllChecksModelCliPatchParameters parameters) {
        DqoUserPrincipal userPrincipal = this.apiKeyPrincipalProvider.getLocalUserPrincipal();
        CheckModel sampleModel = this.getSampleCheckModelForUpdates(parameters.getCheckSearchFilters(), userPrincipal);
        prepareSampleCheckModelForUpdates(sampleModel);
        patchCheckModel(sampleModel, parameters);

        AllChecksPatchParameters allChecksPatchParameters = new AllChecksPatchParameters(){{
            setCheckSearchFilters(parameters.getCheckSearchFilters());
            setOverrideConflicts(parameters.isOverrideConflicts());
            setSelectedTablesToColumns(null);
            setCheckModelPatch(sampleModel);
        }};

        return this.checkService.activateOrUpdateAllChecks(allChecksPatchParameters, userPrincipal);
    }

    protected CheckModel getSampleCheckModelForUpdates(CheckSearchFilters checkSearchFilters, DqoUserPrincipal principal) {
        List<AllChecksModel> patches = this.allChecksModelFactory.findAllConfiguredAndPossibleChecks(checkSearchFilters, principal);
        Optional<CheckModel> sampleCheckModelFromTables = patches.stream()
                .map(AllChecksModel::getTableChecksModel)
                .flatMap(allTableChecksModel -> allTableChecksModel.getSchemaTableChecksModels().stream())
                .flatMap(schemaTableChecksModel -> schemaTableChecksModel.getTableChecksModels().stream())
                .flatMap(tableChecksModel -> tableChecksModel.getCheckContainers().values().stream())
                .flatMap(checkContainerModel -> checkContainerModel.getCategories().stream())
                .flatMap(qualityCategoryModel -> qualityCategoryModel.getChecks().stream())
                .findAny();
        if (sampleCheckModelFromTables.isPresent()) {
            return sampleCheckModelFromTables.get();
        }

        Optional<CheckModel> sampleCheckModelFromColumns = patches.stream()
                .map(AllChecksModel::getColumnChecksModel)
                .flatMap(allColumnChecksModel -> allColumnChecksModel.getTableColumnChecksModels().stream())
                .flatMap(tableColumnChecksModel -> tableColumnChecksModel.getColumnChecksModels().stream())
                .flatMap(columnChecksModel -> columnChecksModel.getCheckContainers().values().stream())
                .flatMap(checkContainerModel -> checkContainerModel.getCategories().stream())
                .flatMap(qualityCategoryModel -> qualityCategoryModel.getChecks().stream())
                .findAny();

        return sampleCheckModelFromColumns.orElse(null);
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

        checkModel.setDataGroupingOverride(null);
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
        checkModel.setDataGroupingConfiguration(null);
        checkModel.setConfigurationRequirementsErrors(null);
    }

    protected void patchCheckModel(CheckModel model,
                                   AllChecksModelCliPatchParameters parameters) {
        RuleThresholdsModel ruleThresholdsModel = model.getRule();
        patchRuleThresholdsModel(ruleThresholdsModel, parameters);

        List<FieldModel> sensorParametersPatches = parameters.getSensorOptions() != null
                ? optionMapToFields(parameters.getSensorOptions())
                : new ArrayList<>();
        patchSensorParametersInModel(model, sensorParametersPatches);
    }

    protected void patchSensorParametersInModel(CheckModel model,
                                                List<FieldModel> sensorPatches) {
        Map<String, FieldModel> modelSensorParamsByName = new LinkedHashMap<>();
        for (FieldModel fieldModel: model.getSensorParameters()) {
            modelSensorParamsByName.put(fieldModel.getDefinition().getDisplayName(), fieldModel);
        }

        for (FieldModel patch: sensorPatches) {
            String paramName = patch.getDefinition().getDisplayName();
            modelSensorParamsByName.put(paramName, patch);
        }

        model.setSensorParameters(new ArrayList<>(modelSensorParamsByName.values()));
    }

    protected void patchRuleThresholdsModel(RuleThresholdsModel ruleThresholdsModel, AllChecksModelCliPatchParameters parameters) {
        if (parameters.isDisableWarningLevel()) {
            RuleParametersModel ruleParametersModel = ruleThresholdsModel.getWarning();
            ruleParametersModel.setConfigured(false);
        } else if (parameters.getWarningLevelOptions() != null) {
            Map<String, String> options = parameters.getWarningLevelOptions();
            List<FieldModel> newParameterFields = this.optionMapToFields(options);

            RuleParametersModel ruleParametersModel = ruleThresholdsModel.getWarning();
            this.patchRuleParameters(ruleParametersModel, newParameterFields);
        }

        if (parameters.isDisableErrorLevel()) {
            RuleParametersModel ruleParametersModel = ruleThresholdsModel.getError();
            ruleParametersModel.setConfigured(false);
        } else if (parameters.getErrorLevelOptions() != null) {
            Map<String, String> options = parameters.getErrorLevelOptions();
            List<FieldModel> newParameterFields = this.optionMapToFields(options);

            RuleParametersModel ruleParametersModel = ruleThresholdsModel.getError();
            this.patchRuleParameters(ruleParametersModel, newParameterFields);
        }

        if (parameters.isDisableFatalLevel()) {
            RuleParametersModel ruleParametersModel = ruleThresholdsModel.getFatal();
            ruleParametersModel.setConfigured(false);
        }
        else if (parameters.getFatalLevelOptions() != null) {
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

        ruleParametersModel.setConfigured(true);
    }

    protected List<FieldModel> optionMapToFields(Map<String, String> options) {
        List<FieldModel> result = new ArrayList<>();

        for (Map.Entry<String, String> option: options.entrySet()) {
            FieldModel fieldModel = new FieldModel();
            ParameterDefinitionSpec parameterDefinition = new ParameterDefinitionSpec();
            parameterDefinition.setDisplayName(option.getKey());
            parameterDefinition.setFieldName(option.getKey());
            fieldModel.setDefinition(parameterDefinition);

            this.assignFieldModelValue(fieldModel, option.getValue());
            result.add(fieldModel);
        }
        return result;
    }

    protected void assignFieldModelValue(FieldModel fieldModel, String value) {
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
