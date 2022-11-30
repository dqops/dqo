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
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.rest.models.checks.*;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.AbstractRuleThresholdsSpec;
import ai.dqo.utils.reflection.ClassInfo;
import ai.dqo.utils.reflection.FieldInfo;
import ai.dqo.utils.reflection.ReflectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Service that updates the check specification from the UI model that was filled with updates.
 */
@Component
public class UiToSpecCheckMappingServiceImpl implements UiToSpecCheckMappingService {
    private ReflectionService reflectionService;

    /**
     * Creates a mapping service given the required dependencies.
     *
     * @param reflectionService Reflection service that will be used to reflect fields in the check specifications.
     */
    @Autowired
    public UiToSpecCheckMappingServiceImpl(ReflectionService reflectionService) {
        this.reflectionService = reflectionService;
    }

    /**
     * Updates the <code>checkCategoriesSpec</code> with the updates received from the UI in the <code>model</code>.
     *
     * @param model               Data quality check UI model with the updates.
     * @param checkCategoriesSpec The target check categories spec object that will be updated.
     */
    @Override
    public void updateAllChecksSpecs(UIAllChecksModel model, AbstractRootChecksContainerSpec checkCategoriesSpec) {
        ClassInfo checkCategoriesClassInfo = reflectionService.getClassInfoForClass(checkCategoriesSpec.getClass());
        List<UIQualityCategoryModel> categoryModelList = model.getCategories();
        if (categoryModelList == null) {
            return;
        }

        for (UIQualityCategoryModel categoryModel : categoryModelList) {
            String qualityDimension = categoryModel.getCategory();
            FieldInfo categoryFieldInfo = checkCategoriesClassInfo.getFieldByYamlName(qualityDimension);
            AbstractSpec categorySpec = (AbstractSpec) categoryFieldInfo.getFieldValueOrNewObject(checkCategoriesSpec);

            updateCategoryChecksSpec(categoryModel, categorySpec);

            if (categorySpec.isDefault()) {
                categoryFieldInfo.setFieldValue(null, checkCategoriesSpec);
            } else {
                categoryFieldInfo.setFieldValue(categorySpec, checkCategoriesSpec);
            }
        }
    }

    /**
     * Updates the category container specification (an object that contains data quality check specifications) with the changes
     * received from the UI model.
     *
     * @param categoryModel Source UI category model with the updates.
     * @param categorySpec  Target category specification to update.
     */
    protected void updateCategoryChecksSpec(UIQualityCategoryModel categoryModel, AbstractSpec categorySpec) {
        ClassInfo checkCategoryClassInfo = reflectionService.getClassInfoForClass(categorySpec.getClass());
        List<UICheckModel> checkModels = categoryModel.getChecks();
        if (checkModels == null) {
            return;
        }

        for (UICheckModel checkModel : checkModels) {
            String yamlCheckName = checkModel.getCheckName();
            FieldInfo checkFieldInfo = checkCategoryClassInfo.getFieldByYamlName(yamlCheckName);

            if (!checkModel.isConfigured()) {
                // the check was unconfigured (selected to be deleted from YAML)
                checkFieldInfo.setFieldValue(null, categorySpec);
                continue;
            }

            AbstractSpec checkNodeObject = (AbstractSpec)checkFieldInfo.getFieldValueOrNewObject(categorySpec);

            if (checkNodeObject instanceof AbstractCheckDeprecatedSpec) {
                AbstractCheckDeprecatedSpec checkSpec = (AbstractCheckDeprecatedSpec) checkNodeObject;
                updateLegacyCheckSpec(checkModel, checkSpec);
            }
            else {
                AbstractCheckSpec<?,?,?,?> checkSpec = (AbstractCheckSpec<?,?,?,?>)checkNodeObject;
                updateCheckSpec(checkModel, checkSpec);
            }

            if (checkNodeObject.isDefault()) {
                checkFieldInfo.setFieldValue(null, categorySpec);  // when UI has sent no configuration (all fields nulls or defaults)
            } else {
                checkFieldInfo.setFieldValue(checkNodeObject, categorySpec);
            }
        }
    }

    /**
     * Updates the check specification <code>checkSpec</code> from the changes in the <code>checkModel</code> UI model.
     *
     * @param checkModel Source UI model for the data quality check.
     * @param checkSpec  Target check specification to update.
     */
    protected void updateCheckSpec(UICheckModel checkModel, AbstractCheckSpec<?,?,?,?> checkSpec) {
        checkSpec.setScheduleOverride(checkModel.getScheduleOverride());
        checkSpec.setComments(checkModel.getComments());
        checkSpec.setDisabled(checkModel.isDisabled());
        checkSpec.setExcludeFromKpi(checkModel.isExcludeFromKpi());
        checkSpec.getParameters().setFilter(checkModel.getFilter());

        updateFieldValues(checkModel.getSensorParameters(), checkSpec.getParameters());

        UIRuleThresholdsModel ruleThresholdsModel = checkModel.getRule();
        updateRuleThresholdsSpec(ruleThresholdsModel, checkSpec);
    }

    /**
     * Updates the check specification <code>checkSpec</code> from the changes in the <code>checkModel</code> UI model.
     *
     * @param checkModel Source UI model for the data quality check.
     * @param checkSpec  Target check specification to update.
     */
    protected void updateLegacyCheckSpec(UICheckModel checkModel, AbstractCheckDeprecatedSpec checkSpec) {
        checkSpec.setDataStreamsOverride(checkModel.getDataStreamsOverride());
        checkSpec.setTimeSeriesOverride(checkModel.getTimeSeriesOverride());
        checkSpec.setScheduleOverride(checkModel.getScheduleOverride());
        checkSpec.setComments(checkModel.getComments());
        checkSpec.getSensorParameters().setDisabled(checkModel.isDisabled());
        checkSpec.getSensorParameters().setFilter(checkModel.getFilter());

        updateFieldValues(checkModel.getSensorParameters(), checkSpec.getSensorParameters());

        ClassInfo checkClassInfo = reflectionService.getClassInfoForClass(checkSpec.getClass());
        AbstractRuleSetSpec ruleSet = checkSpec.getRuleSet();
        ClassInfo ruleThresholdsClassInfo = reflectionService.getClassInfoForClass(ruleSet.getClass());
        List<UIRuleThresholdsModel> ruleThresholdsModels = new ArrayList<>() {{ checkModel.getRule(); }};

        if (ruleThresholdsModels == null) {
            return;
        }

        for (UIRuleThresholdsModel ruleThresholdsModel : ruleThresholdsModels) {
            String yamlRuleName = ruleThresholdsModel.getFieldName();
            FieldInfo ruleThresholdsFieldInfo = ruleThresholdsClassInfo.getFieldByYamlName(yamlRuleName);
            AbstractRuleThresholdsSpec ruleThresholdsSpec = (AbstractRuleThresholdsSpec) ruleThresholdsFieldInfo.getFieldValueOrNewObject(ruleSet);

            updateLegacyRuleThresholdsSpec(ruleThresholdsModel, ruleThresholdsSpec);

            if (ruleThresholdsSpec.isDefault()) {
                ruleThresholdsFieldInfo.setFieldValue(null, ruleSet);
            } else {
                ruleThresholdsFieldInfo.setFieldValue(ruleThresholdsSpec, ruleSet);
            }
        }
    }

    /**
     * Updates the rule thresholds from the changes in the UI model.
     *
     * @param ruleThresholdsModel Source rule thresholds model with changes to the low, medium and high severities.
     * @param checkSpec  Target rule thresholds specification to update.
     */
    protected void updateRuleThresholdsSpec(UIRuleThresholdsModel ruleThresholdsModel, AbstractCheckSpec<?,?,?,?> checkSpec) {
        ClassInfo ruleThresholdsClassInfo = reflectionService.getClassInfoForClass(checkSpec.getClass());
        FieldInfo errorFieldInfo = ruleThresholdsClassInfo.getField("error");
        updateSeveritySpec(ruleThresholdsModel.getError(), errorFieldInfo, checkSpec);

        FieldInfo warningFieldInfo = ruleThresholdsClassInfo.getField("warning");
        updateSeveritySpec(ruleThresholdsModel.getWarning(), warningFieldInfo, checkSpec);

        FieldInfo fatalFieldInfo = ruleThresholdsClassInfo.getField("fatal");
        updateSeveritySpec(ruleThresholdsModel.getFatal(), fatalFieldInfo, checkSpec);
    }

    /**
     * Updates the rule thresholds from the changes in the UI model.
     *
     * @param ruleThresholdsModel Source rule thresholds model with changes to the low, medium and high severities.
     * @param ruleThresholdsSpec  Target rule thresholds specification to update.
     */
    protected void updateLegacyRuleThresholdsSpec(UIRuleThresholdsModel ruleThresholdsModel, AbstractRuleThresholdsSpec ruleThresholdsSpec) {
//        ruleThresholdsSpec.setTimeWindow(ruleThresholdsModel.getTimeWindow());

        ClassInfo ruleThresholdsClassInfo = reflectionService.getClassInfoForClass(ruleThresholdsSpec.getClass());
        FieldInfo lowFieldInfo = ruleThresholdsClassInfo.getField("low");
        updateLegacySeveritySpec(ruleThresholdsModel.getWarning(), lowFieldInfo, ruleThresholdsSpec);

        FieldInfo mediumFieldInfo = ruleThresholdsClassInfo.getField("medium");
        updateLegacySeveritySpec(ruleThresholdsModel.getError(), mediumFieldInfo, ruleThresholdsSpec);

        FieldInfo highFieldInfo = ruleThresholdsClassInfo.getField("high");
        updateLegacySeveritySpec(ruleThresholdsModel.getFatal(), highFieldInfo, ruleThresholdsSpec);
    }

    /**
     * Updates the severity object (with the parameters) in the parent rule threshold object. First retrieves (or creates) the rule parameters object,
     * then updates all rule parameter fields and finally stores the resulting object in the parent rule thresholds specification.
     * @param ruleParametersModel Source rule parameters object.
     * @param severityFieldInfo Field info for the severity field (low, medium, high) that will be updated.
     * @param checkSpec Target parent rule thresholds object to update.
     */
    protected void updateSeveritySpec(UIRuleParametersModel ruleParametersModel,
                                            FieldInfo severityFieldInfo,
                                            AbstractCheckSpec<?,?,?,?> checkSpec) {
        if (ruleParametersModel.isConfigured()) {
            AbstractRuleParametersSpec ruleParametersSpec = (AbstractRuleParametersSpec)
                    severityFieldInfo.getFieldValueOrNewObject(checkSpec);
            List<UIFieldModel> ruleParameterModels = ruleParametersModel.getRuleParameters();
            updateFieldValues(ruleParameterModels, ruleParametersSpec);

            severityFieldInfo.setFieldValue(ruleParametersSpec, checkSpec);
        } else {
            severityFieldInfo.setFieldValue(null, checkSpec);
        }
    }

    /**
     * Updates the severity object (with the parameters) in the parent rule threshold object. First retrieves (or creates) the rule parameters object,
     * then updates all rule parameter fields and finally stores the resulting object in the parent rule thresholds specification.
     * @param ruleParametersModel Source rule parameters object.
     * @param severityFieldInfo Field info for the severity field (low, medium, high) that will be updated.
     * @param parentRuleThresholdsSpec Target parent rule thresholds object to update.
     */
    protected void updateLegacySeveritySpec(UIRuleParametersModel ruleParametersModel,
                                            FieldInfo severityFieldInfo,
                                            AbstractRuleThresholdsSpec parentRuleThresholdsSpec) {
        AbstractRuleParametersSpec ruleParametersSpec = (AbstractRuleParametersSpec)
                severityFieldInfo.getFieldValueOrNewObject(parentRuleThresholdsSpec);
//        ruleParametersSpec.setDisabled(ruleParametersModel.isDisabled());
        List<UIFieldModel> ruleParameterModels = ruleParametersModel.getRuleParameters();

        updateFieldValues(ruleParameterModels, ruleParametersSpec);

        if (ruleParametersSpec.isDefault()) {
            severityFieldInfo.setFieldValue(null, parentRuleThresholdsSpec);
        } else {
            severityFieldInfo.setFieldValue(ruleParametersSpec, parentRuleThresholdsSpec);
        }
    }

    /**
     * Updates field values in the target object <code>targetParametersSpec</code> with field values received in <code>fieldModels</code>.
     * @param fieldModels A list of field models with new values for parameters.
     * @param targetParametersSpec Target parameters (rule or sensor) specification object to update.
     */
    protected void updateFieldValues(List<UIFieldModel> fieldModels, AbstractSpec targetParametersSpec) {
        ClassInfo parametersClassInfo = reflectionService.getClassInfoForClass(targetParametersSpec.getClass());

        if (fieldModels == null) {
            return;
        }

        for (UIFieldModel fieldModel : fieldModels) {
            ParameterDefinitionSpec fieldModelDefinition = fieldModel.getDefinition();
            String fieldName = fieldModelDefinition.getFieldName();
            FieldInfo fieldInfo = parametersClassInfo.getField(fieldName);

            if (fieldInfo == null) {
                throw new RuntimeException("Field " + fieldName + " was not found on the target class " + targetParametersSpec.getClass().getName());
            }

            switch (fieldInfo.getDataType()) {
                case string_type:
                    fieldInfo.setFieldValue(fieldModel.getStringValue(), targetParametersSpec);
                    break;
                case boolean_type:
                    fieldInfo.setFieldValue(fieldModel.getBooleanValue(), targetParametersSpec);
                    break;
                case integer_type:
                    fieldInfo.setFieldValue(fieldModel.getIntegerValue(), targetParametersSpec);
                    break;
                case long_type:
                    fieldInfo.setFieldValue(fieldModel.getLongValue(), targetParametersSpec);
                    break;
                case double_type:
                    fieldInfo.setFieldValue(fieldModel.getDoubleValue(), targetParametersSpec);
                    break;
                case instant_type:
                    fieldInfo.setFieldValue(fieldModel.getDateTimeValue(), targetParametersSpec);
                    break;
                case column_name_type:
                    fieldInfo.setFieldValue(fieldModel.getColumnNameValue(), targetParametersSpec);
                    break;
                case enum_type:
                    fieldInfo.setFieldValue(fieldModel.getEnumValue(), targetParametersSpec);
                    break;
                case string_list_type:
                    fieldInfo.setFieldValue(fieldModel.getStringListValue(), targetParametersSpec);
                    break;
                case date_type:
                    fieldInfo.setFieldValue(fieldModel.getDateValue(), targetParametersSpec);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported type: " + fieldInfo.getDataType().toString());
            }
        }
    }
}
