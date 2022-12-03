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
package ai.dqo.rest.models.checks.mapping;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.AbstractCheckDeprecatedSpec;
import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.rest.models.checks.*;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.AbstractRuleThresholdsSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.utils.reflection.ClassInfo;
import ai.dqo.utils.reflection.FieldInfo;
import ai.dqo.utils.reflection.ReflectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service that creates the UI model from the data quality check specifications,
 * enabling transformation between the storage model (YAML compliant) with a UI friendly UI model.
 */
@Component
public class SpecToUiCheckMappingServiceImpl implements SpecToUiCheckMappingService {
    private ReflectionService reflectionService;

    /**
     * Creates a check mapping service that exchanges the data for data quality checks between UI models and the data quality check specifications.
     * @param reflectionService Reflection service used to read the list of checks.
     */
    @Autowired
    public SpecToUiCheckMappingServiceImpl(ReflectionService reflectionService) {
        this.reflectionService = reflectionService;
    }

    /**
     * Creates a checks UI model for the whole container of table level or column level data quality checks, divided into categories.
     * @param checkCategoriesSpec Table level data quality checks container or a column level data quality checks container.
     * @param runChecksTemplate Check search filter for the parent table or column that is used as a template to create more fine grained "run checks" job configurations.
     * @param defaultDataStreamName Default data stream name to assign to new checks. This is the name of the first named data stream on a table level.
     * @return Checks data quality container.
     */
    @Override
    public UIAllChecksModel createUiModel(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                          CheckSearchFilters runChecksTemplate,
                                          String defaultDataStreamName) {
        UIAllChecksModel uiAllChecksModel = new UIAllChecksModel();
        uiAllChecksModel.setRunChecksJobTemplate(runChecksTemplate.clone());
        ClassInfo checkCategoriesClassInfo = reflectionService.getClassInfoForClass(checkCategoriesSpec.getClass());
        List<FieldInfo> dimensionsFields = checkCategoriesClassInfo.getFields();
        for (FieldInfo dimensionFieldInfo : dimensionsFields) {
            if (Objects.equals(dimensionFieldInfo.getClassFieldName(), "custom")) {
                // skipping custom categories
                continue;
            }

            Object categoryFieldValue = dimensionFieldInfo.getFieldValueOrNewObject(checkCategoriesSpec);
            UIQualityCategoryModel categoryModel = createCategoryModel(dimensionFieldInfo,
                    categoryFieldValue, runChecksTemplate, defaultDataStreamName);
            uiAllChecksModel.getCategories().add(categoryModel);
        }

        return uiAllChecksModel;
    }

    /**
     * Creates a UI model for all data quality checks for one category.
     * @param dimensionFieldInfo Field info for the category field.
     * @param checkCategoryParentNode The current category specification object instance (an object that has fields for all data quality checks in the category).
     * @param runChecksTemplate Run check job template.
     * @param defaultDataStreamName Default data stream name to assign to new checks. This is the name of the first named data stream on a table level.
     * @return UI model for a category with all quality checks.
     */
    protected UIQualityCategoryModel createCategoryModel(FieldInfo dimensionFieldInfo,
                                                         Object checkCategoryParentNode,
                                                         CheckSearchFilters runChecksTemplate,
                                                         String defaultDataStreamName) {
        UIQualityCategoryModel categoryModel = new UIQualityCategoryModel();
        categoryModel.setCategory(dimensionFieldInfo.getDisplayName());
        categoryModel.setHelpText(dimensionFieldInfo.getHelpText());
        CheckSearchFilters runChecksCategoryTemplate = runChecksTemplate.clone();
        runChecksCategoryTemplate.setCheckCategory(dimensionFieldInfo.getYamlFieldName());
        categoryModel.setRunChecksJobTemplate(runChecksCategoryTemplate);

        ClassInfo checkListClassInfo = reflectionService.getClassInfoForClass(checkCategoryParentNode.getClass());
        List<FieldInfo> checksFields = checkListClassInfo.getFields();
        for (FieldInfo checkFieldInfo : checksFields) {
            if (Objects.equals(checkFieldInfo.getClassFieldName(), "custom")) {
                // skipping custom checks for the moment
                continue;
            }

            AbstractSpec checkSpecObjectNullable = (AbstractSpec)checkFieldInfo.getFieldValue(checkCategoryParentNode);
            AbstractSpec checkSpecObject = checkSpecObjectNullable != null ? checkSpecObjectNullable :
                    (AbstractSpec)checkFieldInfo.getFieldValueOrNewObject(checkCategoryParentNode);
            if (checkSpecObject instanceof AbstractCheckDeprecatedSpec) {
                AbstractCheckDeprecatedSpec checkFieldValue = (AbstractCheckDeprecatedSpec) checkSpecObject;
                UICheckModel checkModel = createLegacyCheckModel(checkFieldInfo, checkFieldValue);
                checkModel.setConfigured(checkSpecObjectNullable != null);
                categoryModel.getChecks().add(checkModel);
            }
            else {
                AbstractCheckSpec<?,?,?,?> checkFieldValue = (AbstractCheckSpec<?,?,?,?>) checkSpecObject;
                UICheckModel checkModel = createCheckModel(checkFieldInfo, checkFieldValue, runChecksCategoryTemplate);
                checkModel.setConfigured(checkSpecObjectNullable != null);
                categoryModel.getChecks().add(checkModel);

                if (checkSpecObjectNullable == null) { // this check is not configured, so we will propose the default data stream name
                    checkModel.setDataStream(defaultDataStreamName);
                }
            }
        }

        return categoryModel;
    }

    /**
     * Creates a UI model for a single data quality check.
     * @param checkFieldInfo Reflection info of the field in the parent object that stores the check specification field value.
     * @param checkSpec Check specification instance retrieved from the object.
     * @param runChecksCategoryTemplate "run check" job configuration for the parent category, used to create templates for each check.
     * @return Check model.
     */
    protected UICheckModel createCheckModel(FieldInfo checkFieldInfo, AbstractCheckSpec<?,?,?,?> checkSpec, CheckSearchFilters runChecksCategoryTemplate) {
        UICheckModel checkModel = new UICheckModel();
        checkModel.setCheckName(checkFieldInfo.getDisplayName());
        checkModel.setHelpText(checkFieldInfo.getHelpText());
        CheckSearchFilters runOneCheckTemplate = runChecksCategoryTemplate.clone();
        runOneCheckTemplate.setCheckName(checkFieldInfo.getYamlFieldName());
        checkModel.setRunChecksJobTemplate(runOneCheckTemplate);

        checkModel.setScheduleOverride(checkSpec.getScheduleOverride());
        checkModel.setComments(checkSpec.getComments());
        checkModel.setDisabled(checkSpec.isDisabled());
        checkModel.setExcludeFromKpi(checkSpec.isExcludeFromKpi());
        checkModel.setSupportsTimeSeries(false);
        checkModel.setSupportsDataStreams(false);
        checkModel.setDataStream(checkSpec.getDataStream());

        ClassInfo checkClassInfo = reflectionService.getClassInfoForClass(checkSpec.getClass());
        FieldInfo parametersFieldInfo = checkClassInfo.getField("parameters");
        AbstractSensorParametersSpec parametersSpec = (AbstractSensorParametersSpec)parametersFieldInfo.getFieldValueOrNewObject(checkSpec);
        checkModel.setFilter(parametersSpec.getFilter());

        List<UIFieldModel> fieldsForParameterSpec = createFieldsForSensorParameters(parametersSpec);
        checkModel.setSensorParameters(fieldsForParameterSpec);

        UIRuleThresholdsModel ruleModel = createRuleThresholdsModel(checkSpec);
        checkModel.setRule(ruleModel);

        return checkModel;
    }

    /**
     * Creates a UI model for a single data quality check.
     * @param checkFieldInfo Reflection info of the field in the parent object that stores the check specification field value.
     * @param checkFieldValue Check specification instance retrieved from the object.
     * @return Check model.
     */
    protected UICheckModel createLegacyCheckModel(FieldInfo checkFieldInfo, AbstractCheckDeprecatedSpec checkFieldValue) {
        UICheckModel checkModel = new UICheckModel();
        checkModel.setCheckName(checkFieldInfo.getDisplayName());
        checkModel.setHelpText(checkFieldInfo.getHelpText());

        checkModel.setDataStreamsOverride(checkFieldValue.getDataStreamsOverride());
        checkModel.setTimeSeriesOverride(checkFieldValue.getTimeSeriesOverride());
        checkModel.setScheduleOverride(checkFieldValue.getScheduleOverride());
        checkModel.setComments(checkFieldValue.getComments());
        checkModel.setDisabled(checkFieldValue.getSensorParameters().isDisabled());
        checkModel.setFilter(checkFieldValue.getSensorParameters().getFilter());
        checkModel.setSupportsTimeSeries(true);
        checkModel.setSupportsDataStreams(true);

        ClassInfo checkClassInfo = reflectionService.getClassInfoForClass(checkFieldValue.getClass());
        FieldInfo parametersFieldInfo = checkClassInfo.getField("parameters");
        AbstractSensorParametersSpec parametersSpec = (AbstractSensorParametersSpec)parametersFieldInfo.getFieldValueOrNewObject(checkFieldValue);
        List<UIFieldModel> fieldsForParameterSpec = createFieldsForSensorParameters(parametersSpec);
        checkModel.setSensorParameters(fieldsForParameterSpec);

        FieldInfo rulesFieldInfo = checkClassInfo.getField("rules");
        AbstractRuleSetSpec rulesSpec = (AbstractRuleSetSpec)rulesFieldInfo.getFieldValueOrNewObject(checkFieldValue);

        ClassInfo rulesSpecClassInfo = reflectionService.getClassInfoForClass(rulesSpec.getClass());
        List<FieldInfo> rulesFields = rulesSpecClassInfo.getFields();
        Optional<FieldInfo> firstRuleField = rulesFields.stream().filter(r -> !Objects.equals(r.getClassFieldName(), "custom"))
                .findFirst();

        AbstractRuleThresholdsSpec ruleFieldValue = (AbstractRuleThresholdsSpec)firstRuleField.get().getFieldValueOrNewObject(rulesSpec);
        UIRuleThresholdsModel ruleModel = createLegacyRuleThresholdsModel(firstRuleField.get(), ruleFieldValue);
        checkModel.setRule(ruleModel);

        return checkModel;
    }

    /**
     * Create a rule threshold model that describes the rule thresholds (alert, warning, fatal) and their parameters.
     * @param checkSpec Rule threshold object with the alert/warning/fatal thresholds.
     * @return Rule threshold model.
     */
    protected UIRuleThresholdsModel createRuleThresholdsModel(AbstractCheckSpec<?,?,?,?> checkSpec) {
        UIRuleThresholdsModel thresholdsModel = new UIRuleThresholdsModel();

        ClassInfo abstractCheckClassInfo = reflectionService.getClassInfoForClass(checkSpec.getClass());
        FieldInfo warningFieldInfo = abstractCheckClassInfo.getField("warning");
        UIRuleParametersModel warningSeverityParametersModel = createRuleParametersModel(warningFieldInfo, checkSpec);
        thresholdsModel.setWarning(warningSeverityParametersModel);

        FieldInfo errorFieldInfo = abstractCheckClassInfo.getField("error");
        UIRuleParametersModel errorSeverityParametersModel = createRuleParametersModel(errorFieldInfo, checkSpec);
        thresholdsModel.setError(errorSeverityParametersModel);

        FieldInfo fatalFieldInfo = abstractCheckClassInfo.getField("fatal");
        UIRuleParametersModel fatalSeverityParametersModel = createRuleParametersModel(fatalFieldInfo, checkSpec);
        thresholdsModel.setFatal(fatalSeverityParametersModel);

        return thresholdsModel;
    }

    /**
     * Create a rule threshold model that describes the rule thresholds (low, medium, high) and their parameters.
     * @param ruleFieldInfo Rule field reflection info - used to get the rule name.
     * @param ruleFieldValue Rule threshold object with the low/medium/high values.
     * @return Rule threshold model.
     */
    protected UIRuleThresholdsModel createLegacyRuleThresholdsModel(FieldInfo ruleFieldInfo, AbstractRuleThresholdsSpec ruleFieldValue) {
        UIRuleThresholdsModel thresholdsModel = new UIRuleThresholdsModel();
        thresholdsModel.setFieldName(ruleFieldInfo.getDisplayName());
//        thresholdsModel.setDisabled(ruleFieldValue.isDisabled());
        thresholdsModel.setHelpHext(ruleFieldInfo.getHelpText());
//        thresholdsModel.setTimeWindow(ruleFieldValue.getTimeWindow());

        ClassInfo ruleThresholdsClassInfo = reflectionService.getClassInfoForClass(ruleFieldValue.getClass());
        FieldInfo lowFieldInfo = ruleThresholdsClassInfo.getField("low");
        UIRuleParametersModel lowSeverityParametersModel = createLegacyRuleParametersModel(lowFieldInfo, ruleFieldValue);
        thresholdsModel.setWarning(lowSeverityParametersModel);

        FieldInfo mediumFieldInfo = ruleThresholdsClassInfo.getField("medium");
        UIRuleParametersModel mediumSeverityParametersModel = createLegacyRuleParametersModel(mediumFieldInfo, ruleFieldValue);
        thresholdsModel.setError(mediumSeverityParametersModel);

        FieldInfo highFieldInfo = ruleThresholdsClassInfo.getField("high");
        UIRuleParametersModel highSeverityParametersModel = createLegacyRuleParametersModel(highFieldInfo, ruleFieldValue);
        thresholdsModel.setFatal(highSeverityParametersModel);

        return thresholdsModel;
    }

    /**
     * Creates a list of fields used to edit a single rule severity level.
     * @param severityFieldInfo Reflection info of the field - used to retrieve the help text.
     * @param abstractCheckSpec Rule thresholds specification object (parent object).
     * @return Rule parameters.
     */
    protected UIRuleParametersModel createRuleParametersModel(FieldInfo severityFieldInfo, AbstractCheckSpec<?,?,?,?> abstractCheckSpec) {
        UIRuleParametersModel parametersModel = new UIRuleParametersModel();
        AbstractRuleParametersSpec parametersValueNullable = (AbstractRuleParametersSpec)severityFieldInfo.getFieldValue(abstractCheckSpec);
        AbstractRuleParametersSpec parametersSpecNotNull = parametersValueNullable != null ? parametersValueNullable :
                (AbstractRuleParametersSpec)severityFieldInfo.getFieldValueOrNewObject(abstractCheckSpec);

        parametersModel.setConfigured(parametersValueNullable != null);

        List<UIFieldModel> uiFieldModels = createFieldsForRuleParameters(parametersSpecNotNull);
        parametersModel.setRuleParameters(uiFieldModels);

        return parametersModel;
    }

    /**
     * Creates a list of fields used to edit a single rule severity level.
     * @param severityFieldInfo Reflection info of the field - used to retrieve the help text.
     * @param ruleThresholdsSpec Rule thresholds specification object (parent object).
     * @return Rule parameters.
     */
    protected UIRuleParametersModel createLegacyRuleParametersModel(FieldInfo severityFieldInfo, AbstractRuleThresholdsSpec ruleThresholdsSpec) {
        UIRuleParametersModel parametersModel = new UIRuleParametersModel();
        AbstractRuleParametersSpec parametersValueNullable = (AbstractRuleParametersSpec)severityFieldInfo.getFieldValue(ruleThresholdsSpec);
        AbstractRuleParametersSpec parametersSpecNotNull = parametersValueNullable != null ? parametersValueNullable :
                (AbstractRuleParametersSpec)severityFieldInfo.getFieldValueOrNewObject(ruleThresholdsSpec);

        parametersModel.setConfigured(parametersValueNullable != null);
//        parametersModel.setDisabled(parametersSpecNotNull.isDisabled());

        List<UIFieldModel> uiFieldModels = createFieldsForRuleParameters(parametersSpecNotNull);
        parametersModel.setRuleParameters(uiFieldModels);

        return parametersModel;
    }

    /**
     * Creates a list of fields to edit all values in the rule parameters specification.
     * @param ruleParametersSpec Rule parameters specification.
     * @return List of UI fields for all rule parameter fields.
     */
    protected List<UIFieldModel> createFieldsForRuleParameters(AbstractRuleParametersSpec ruleParametersSpec) {
        List<UIFieldModel> fieldModels = new ArrayList<>();
        ClassInfo parametersClassInfo = reflectionService.getClassInfoForClass(ruleParametersSpec.getClass());
        List<FieldInfo> fields = parametersClassInfo.getFields();
        for (FieldInfo parameterField : fields) {
            if (Objects.equals("disabled", parameterField.getClassFieldName())) {
                continue; // we could also filter out non-direct fields, but that would cancel inheritance
            }

            UIFieldModel fieldModel = createFieldModel(parameterField, ruleParametersSpec);
            fieldModels.add(fieldModel);
        }

        return fieldModels;
    }

    /**
     * Creates a list of fields to edit all values in the sensor parameters specification.
     * @param parametersSpec Sensor parameters specification.
     * @return List of UI fields for all sensor parameter fields.
     */
    protected List<UIFieldModel> createFieldsForSensorParameters(AbstractSensorParametersSpec parametersSpec) {
        List<UIFieldModel> fieldModels = new ArrayList<>();
        ClassInfo parametersClassInfo = reflectionService.getClassInfoForClass(parametersSpec.getClass());
        List<FieldInfo> fields = parametersClassInfo.getFields();
        for (FieldInfo parameterField : fields) {
            if (Objects.equals("disabled", parameterField.getClassFieldName()) ||
                    Objects.equals("filter", parameterField.getClassFieldName())) {
                continue; // we could also filter out non-direct fields, but that would cancel inheritance
            }

            UIFieldModel fieldModel = createFieldModel(parameterField, parametersSpec);
            fieldModels.add(fieldModel);
        }

        return fieldModels;
    }

    /**
     * Creates a field model for a single field in an object.
     * @param fieldInfo Reflection field information about the field.
     * @param parentObject Parent object to retrieve the field value from.
     * @return UI field model with properly typed field value.
     */
    protected UIFieldModel createFieldModel(FieldInfo fieldInfo, Object parentObject) {
        Object fieldValue = fieldInfo.getFieldValue(parentObject);
        UIFieldModel fieldModel = new UIFieldModel();
        ParameterDefinitionSpec parameterDefinitionSpec = new ParameterDefinitionSpec();
        // we are using reflection info, but we could also pull the information from the sensor specification
        assert fieldInfo.getDataType() != null && fieldInfo.getDataType() != ParameterDataType.object_type :
                fieldInfo.getYamlFieldName() + " on " + parentObject.getClass().getName() + " has an unsupported type " + fieldInfo.getDataType();
        parameterDefinitionSpec.setDataType(fieldInfo.getDataType());
        parameterDefinitionSpec.setFieldName(fieldInfo.getClassFieldName());
        parameterDefinitionSpec.setDisplayName(fieldInfo.getDisplayName());
        parameterDefinitionSpec.setHelpHext(fieldInfo.getHelpText());
        if (fieldInfo.getDataType() == ParameterDataType.enum_type) {
            List<String> supportedEnumValues = fieldInfo.getEnumValuesByName().values()
                    .stream().map(e -> e.getYamlName())
                    .collect(Collectors.toList());
            parameterDefinitionSpec.setAllowedValues(supportedEnumValues);
        }

        // TODO: support the parameterDefinitionSpec.required using javax.validation annotations like @NotNull

        fieldModel.setDefinition(parameterDefinitionSpec);

        if (fieldValue != null) {
            switch (fieldInfo.getDataType()) {
                case string_type:
                    fieldModel.setStringValue((String) fieldValue);
                    break;
                case boolean_type:
                    fieldModel.setBooleanValue((Boolean) fieldValue);
                    break;
                case integer_type:
                    fieldModel.setIntegerValue((Integer) fieldValue);
                    break;
                case long_type:
                    fieldModel.setLongValue((Long) fieldValue);
                    break;
                case double_type:
                    fieldModel.setDoubleValue((Double) fieldValue);
                    break;
                case instant_type:
                    fieldModel.setDateTimeValue((Instant) fieldValue);
                    break;
                case column_name_type:
                    fieldModel.setColumnNameValue((String) fieldValue);
                    break;
                case enum_type:
                    fieldModel.setEnumValue((String) fieldValue);
                    break;
                case string_list_type:
                    fieldModel.setStringListValue((List<String>) fieldValue);
                    break;
                case date_type:
                    fieldModel.setDateValue((LocalDate) fieldValue);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported type: " + fieldInfo.getDataType().toString());
            }
        }

        return fieldModel;
    }
}
