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
package ai.dqo.services.check.mapping;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.services.check.mapping.models.*;
import ai.dqo.utils.exceptions.DqoRuntimeException;
import ai.dqo.utils.reflection.ClassInfo;
import ai.dqo.utils.reflection.FieldInfo;
import ai.dqo.utils.reflection.ReflectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
     * Updates the <code>checkContainerSpec</code> with the updates received from the UI in the <code>model</code>.
     *
     * @param model               Data quality check UI model with the updates.
     * @param checkContainerSpec The target check container spec object that will be updated.
     */
    @Override
    public void updateCheckContainerSpec(UICheckContainerModel model, AbstractRootChecksContainerSpec checkContainerSpec) {
        ClassInfo checkCategoriesClassInfo = reflectionService.getClassInfoForClass(checkContainerSpec.getClass());
        List<UIQualityCategoryModel> categoryModelList = model.getCategories();
        if (categoryModelList == null) {
            return;
        }

        for (UIQualityCategoryModel categoryModel : categoryModelList) {
            String categoryDisplayName = categoryModel.getCategory();
            FieldInfo categoryFieldInfo = checkCategoriesClassInfo.getFieldByYamlName(categoryDisplayName);
            if (categoryFieldInfo == null) {
                throw new DqoRuntimeException("Category " + categoryDisplayName + " not found on " + checkContainerSpec.getClass().getCanonicalName());
            }

            AbstractSpec categorySpec = (AbstractSpec) categoryFieldInfo.getFieldValueOrNewObject(checkContainerSpec);

            updateCategoryChecksSpec(categoryModel, categorySpec);

            if (categorySpec.isDefault()) {
                categoryFieldInfo.setFieldValue(null, checkContainerSpec);
            } else {
                categoryFieldInfo.setFieldValue(categorySpec, checkContainerSpec);
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

            if (checkFieldInfo == null) {
                throw new DqoRuntimeException("Check " + yamlCheckName + " not found on " + categorySpec.getClass().getCanonicalName());
            }

            if (!checkModel.isConfigured()) {
                // the check was unconfigured (selected to be deleted from YAML)
                checkFieldInfo.setFieldValue(null, categorySpec);
                continue;
            }

            AbstractSpec checkNodeObject = (AbstractSpec)checkFieldInfo.getFieldValueOrNewObject(categorySpec);

            AbstractCheckSpec<?,?,?,?> checkSpec = (AbstractCheckSpec<?,?,?,?>)checkNodeObject;
            updateCheckSpec(checkModel, checkSpec);

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
        checkSpec.setIncludeInSla(checkModel.isIncludeInSla());
        checkSpec.getParameters().setFilter(checkModel.getFilter());
        checkSpec.setDataStream(checkModel.getDataStream());

        updateFieldValues(checkModel.getSensorParameters(), checkSpec.getParameters());

        UIRuleThresholdsModel ruleThresholdsModel = checkModel.getRule();
        updateRuleThresholdsSpec(ruleThresholdsModel, checkSpec);
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
            String yamlFieldName = fieldModelDefinition.getFieldName();
            FieldInfo fieldInfo = parametersClassInfo.getFieldByYamlName(yamlFieldName);

            if (fieldInfo == null) {
                throw new RuntimeException("Field " + yamlFieldName + " was not found on the target class " + targetParametersSpec.getClass().getName());
            }
            
            UIFieldModel fieldModelForUpdate = fieldModel.cloneForUpdate();
            if (!fieldInfo.getDataType().equals(fieldModelForUpdate.getDefinition().getDataType())) {
                Object fieldModelValue = fieldModelForUpdate.getValue();
                fieldModelForUpdate.getDefinition().setDataType(fieldInfo.getDataType());
                fieldModelForUpdate.setValue(fieldModelValue);
            }

            switch (fieldInfo.getDataType()) {
                case string_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getStringValue(), targetParametersSpec);
                    break;
                case boolean_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getBooleanValue(), targetParametersSpec);
                    break;
                case integer_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getIntegerValue(), targetParametersSpec);
                    break;
                case long_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getLongValue(), targetParametersSpec);
                    break;
                case double_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getDoubleValue(), targetParametersSpec);
                    break;
                case instant_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getDateTimeValue(), targetParametersSpec);
                    break;
                case column_name_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getColumnNameValue(), targetParametersSpec);
                    break;
                case enum_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getEnumValue(), targetParametersSpec);
                    break;
                case string_list_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getStringListValue(), targetParametersSpec);
                    break;
                case integer_list_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getIntegerListValue(), targetParametersSpec);
                    break;
                case date_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getDateValue(), targetParametersSpec);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported type: " + fieldInfo.getDataType().toString());
            }
        }
    }
}
