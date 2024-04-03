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
package com.dqops.services.check.mapping;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.checks.custom.CustomCategoryCheckSpecMap;
import com.dqops.checks.custom.CustomCheckSpec;
import com.dqops.checks.custom.CustomCheckSpecMap;
import com.dqops.checks.custom.CustomParametersSpecObject;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.services.check.mapping.models.*;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ReflectionService;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Service that updates the check specification from a model that was filled with updates.
 */
@Component
public class ModelToSpecCheckMappingServiceImpl implements ModelToSpecCheckMappingService {
    private ReflectionService reflectionService;

    /**
     * Creates a mapping service given the required dependencies.
     *
     * @param reflectionService Reflection service that will be used to reflect fields in the check specifications.
     */
    @Autowired
    public ModelToSpecCheckMappingServiceImpl(ReflectionService reflectionService) {
        this.reflectionService = reflectionService;
    }

    /**
     * Updates the <code>checkContainerSpec</code> with the updates contained in the <code>model</code>.
     *
     * @param model              Data quality check model with the updates.
     * @param checkContainerSpec The target check container spec object that will be updated.
     * @param parentTableSpec    Parent table specification.
     */
    @Override
    public void updateCheckContainerSpec(CheckContainerModel model,
                                         AbstractRootChecksContainerSpec checkContainerSpec,
                                         TableSpec parentTableSpec) {
        ClassInfo checkCategoriesClassInfo = reflectionService.getClassInfoForClass(checkContainerSpec.getClass());
        List<QualityCategoryModel> categoryModelList = model.getCategories();
        if (categoryModelList == null) {
            return;
        }

        for (QualityCategoryModel categoryModel : categoryModelList) {
            String categoryDisplayName = categoryModel.getCategory();
            if (categoryDisplayName.startsWith("comparisons/")) {
                AbstractComparisonCheckCategorySpecMap<?> comparisons = checkContainerSpec.getComparisons();
                String comparisonName = categoryModel.getComparisonName();

                if (parentTableSpec.getTableComparisons() == null || !parentTableSpec.getTableComparisons().containsKey(comparisonName)) {
                    continue; // skipping the comparisons, because we cannot save checks for a comparison that is not defined
                }

                AbstractComparisonCheckCategorySpec comparisonCheckCategorySpec = comparisons.get(comparisonName);
                if (comparisonCheckCategorySpec == null) {
                    Type actualTypeArgument = ((ParameterizedType) comparisons.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                    Class<?> comparisonContainerClassType = (Class<?>) actualTypeArgument;
                    ClassInfo comparisonChecksCategoryClassInfo = reflectionService.getClassInfoForClass(comparisonContainerClassType);

                    comparisonCheckCategorySpec = (AbstractComparisonCheckCategorySpec) comparisonChecksCategoryClassInfo.createNewInstance();
                }

                updateCategoryChecksSpec(categoryModel, comparisonCheckCategorySpec);

                if (comparisonCheckCategorySpec.isDefault()) {
                    comparisons.remove(comparisonName);
                } else {
                    ((Map)comparisons).put(comparisonName, comparisonCheckCategorySpec);
                }

                continue;
            }

            FieldInfo categoryFieldInfo = checkCategoriesClassInfo.getFieldByYamlName(categoryDisplayName);
            if (categoryFieldInfo == null) {
                throw new DqoRuntimeException("Category " + categoryDisplayName + " not found on " + checkContainerSpec.getClass().getCanonicalName());
            }

            Object categoryContainerSpec = categoryFieldInfo.getFieldValueOrNewObject(checkContainerSpec);
            if (categoryContainerSpec instanceof AbstractCheckCategorySpec) {
                AbstractCheckCategorySpec categorySpec = (AbstractCheckCategorySpec) categoryContainerSpec;
                updateCategoryChecksSpec(categoryModel, categorySpec);

                if (categorySpec.isDefault()) {
                    categoryFieldInfo.setFieldValue(null, checkContainerSpec);
                } else {
                    categoryFieldInfo.setFieldValue(categorySpec, checkContainerSpec);
                }
            }
            else if (categoryContainerSpec instanceof CustomCheckSpecMap) {
                CustomCheckSpecMap customChecksMap = (CustomCheckSpecMap)categoryContainerSpec;
                updateCustomChecksMap(categoryModel, customChecksMap);

                if (customChecksMap.isDefault()) {
                    categoryFieldInfo.setFieldValue(null, checkContainerSpec);
                } else {
                    categoryFieldInfo.setFieldValue(customChecksMap, checkContainerSpec);
                }
            }
        }
    }

    /**
     * Updates the category container specification (an object that contains data quality check specifications) with the changes
     * received from the model.
     *
     * @param categoryModel Source category model with the updates.
     * @param categorySpec  Target category specification to update.
     */
    protected void updateCategoryChecksSpec(QualityCategoryModel categoryModel, AbstractCheckCategorySpec categorySpec) {
        List<CheckModel> checkModels = categoryModel.getChecks();
        if (checkModels == null) {
            return;
        }

        ClassInfo checkCategoryClassInfo = reflectionService.getClassInfoForClass(categorySpec.getClass());
        for (CheckModel checkModel : checkModels) {
            String yamlCheckName = checkModel.getCheckName();
            FieldInfo checkFieldInfo = checkCategoryClassInfo.getFieldByYamlName(yamlCheckName);

            if (checkFieldInfo != null) {
                if (!checkModel.isConfigured()) {
                    // the check was unconfigured (selected to be deleted from YAML)
                    checkFieldInfo.setFieldValue(null, categorySpec);
                    continue;
                }

                AbstractSpec checkNodeObject = (AbstractSpec) checkFieldInfo.getFieldValueOrNewObject(categorySpec);

                AbstractCheckSpec<?, ?, ?, ?> checkSpec = (AbstractCheckSpec<?, ?, ?, ?>) checkNodeObject;
                updateCheckSpec(checkModel, checkSpec);

                if (checkNodeObject.isDefault()) {
                    checkFieldInfo.setFieldValue(null, categorySpec);  // when api has sent no configuration (all fields nulls or defaults)
                } else {
                    checkFieldInfo.setFieldValue(checkNodeObject, categorySpec);
                }
            } else {
                // custom check
                CustomCategoryCheckSpecMap customChecksMap = categorySpec.getCustomChecks();
                if (!checkModel.isConfigured()) {
                    if (customChecksMap != null) {
                        customChecksMap.remove(yamlCheckName);
                    }
                    continue;
                }

                if (customChecksMap == null) {
                    customChecksMap = new CustomCategoryCheckSpecMap();
                    categorySpec.setCustomChecks(customChecksMap);
                }

                CustomCheckSpec customCheckSpec = customChecksMap.get(yamlCheckName);
                if (customCheckSpec == null) {
                    customCheckSpec = new CustomCheckSpec();
                }

                updateCustomCheckSpec(checkModel, customCheckSpec);

                if (customCheckSpec.isDefault()) {
                    customChecksMap.remove(yamlCheckName);
                } else {
                    customChecksMap.put(yamlCheckName, customCheckSpec);
                }
            }
        }
    }

    /**
     * Updates a map of custom checks with the checks from the model.
     * @param categoryModel Source category model with models of custom checks to update.
     * @param customChecksMap Target custom checks map to update.
     */
    protected void updateCustomChecksMap(QualityCategoryModel categoryModel, CustomCheckSpecMap customChecksMap) {
        List<CheckModel> checkModels = categoryModel.getChecks();
        if (checkModels == null) {
            return;
        }

        for (CheckModel checkModel : checkModels) {
            String yamlCheckName = checkModel.getCheckName();

            if (checkModel.isConfigured()) {
                CustomCheckSpec customCheckSpec = customChecksMap.get(yamlCheckName);
                if (customCheckSpec == null) {
                    customCheckSpec = new CustomCheckSpec();
                }

                updateCustomCheckSpec(checkModel, customCheckSpec);
                customChecksMap.put(yamlCheckName, customCheckSpec);
            } else {
                customChecksMap.remove(yamlCheckName);
            }
        }
    }

    /**
     * Updates the check specification <code>checkSpec</code> from the changes in the <code>checkModel</code>.
     *
     * @param checkModel Source model for the data quality check.
     * @param checkSpec  Target check specification to update.
     */
    protected void updateCheckSpec(CheckModel checkModel, AbstractCheckSpec<?,?,?,?> checkSpec) {
        checkSpec.setScheduleOverride(checkModel.getScheduleOverride());
        checkSpec.setComments(checkModel.getComments());
        checkSpec.setDisabled(checkModel.isDisabled());
        checkSpec.setExcludeFromKpi(checkModel.isExcludeFromKpi());
        if (checkSpec.getDefaultDataQualityDimension() != null &&
                Objects.equals(checkModel.getQualityDimension(), checkSpec.getDefaultDataQualityDimension().name())) {
            checkSpec.setQualityDimension(null);
        }
        else {
            checkSpec.setQualityDimension(checkModel.getQualityDimension());
        }
        checkSpec.setIncludeInSla(checkModel.isIncludeInSla());
        checkSpec.getParameters().setFilter(checkModel.getFilter());
        checkSpec.setDataGrouping(checkModel.getDataGroupingConfiguration());

        updateFieldValues(checkModel.getSensorParameters(), checkSpec.getParameters());

        RuleThresholdsModel ruleThresholdsModel = checkModel.getRule();
        updateRuleThresholdsSpec(ruleThresholdsModel, checkSpec);
    }

    /**
     * Updates the custom check specification <code>checkSpec</code> from the changes in the <code>checkModel</code>.
     *
     * @param checkModel Source model for the data quality check.
     * @param checkSpec  Target custom check specification to update.
     */
    protected void updateCustomCheckSpec(CheckModel checkModel, CustomCheckSpec checkSpec) {
        checkSpec.setScheduleOverride(checkModel.getScheduleOverride());
        checkSpec.setComments(checkModel.getComments());
        checkSpec.setDisabled(checkModel.isDisabled());
        checkSpec.setExcludeFromKpi(checkModel.isExcludeFromKpi());
        if (checkSpec.getDefaultDataQualityDimension() != null &&
                Objects.equals(checkModel.getQualityDimension(), checkSpec.getDefaultDataQualityDimension().name())) {
            checkSpec.setQualityDimension(null);
        }
        else {
            checkSpec.setQualityDimension(checkModel.getQualityDimension());
        }
        checkSpec.setIncludeInSla(checkModel.isIncludeInSla());
        checkSpec.getParameters().setFilter(checkModel.getFilter());
        checkSpec.setDataGrouping(checkModel.getDataGroupingConfiguration());

        updateFieldValues(checkModel.getSensorParameters(), checkSpec.getParameters());

        RuleThresholdsModel ruleThresholdsModel = checkModel.getRule();
        updateRuleThresholdsSpec(ruleThresholdsModel, checkSpec);
    }

    /**
     * Updates the rule thresholds from the changes in the model.
     *
     * @param ruleThresholdsModel Source rule thresholds model with changes to the low, medium and high severities.
     * @param checkSpec           Target rule thresholds specification to update.
     */
    protected void updateRuleThresholdsSpec(RuleThresholdsModel ruleThresholdsModel, AbstractCheckSpec<?,?,?,?> checkSpec) {
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
    protected void updateSeveritySpec(RuleParametersModel ruleParametersModel,
                                      FieldInfo severityFieldInfo,
                                      AbstractCheckSpec<?,?,?,?> checkSpec) {
        if (ruleParametersModel != null && ruleParametersModel.isConfigured()) {
            AbstractRuleParametersSpec ruleParametersSpec = (AbstractRuleParametersSpec)
                    severityFieldInfo.getFieldValueOrNewObject(checkSpec);
            List<FieldModel> ruleParameterModels = ruleParametersModel.getRuleParameters();
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
    protected void updateFieldValues(List<FieldModel> fieldModels, AbstractSpec targetParametersSpec) {
        if (targetParametersSpec instanceof CustomParametersSpecObject) {
            CustomParametersSpecObject customParametersSpecObject = (CustomParametersSpecObject)targetParametersSpec;
            for (FieldModel fieldModel : fieldModels) {
                Object fieldValue = fieldModel.getValue();
                customParametersSpecObject.setParameter(fieldModel.getDefinition().getFieldName(), fieldValue);
            }

            return;
        }

        ClassInfo parametersClassInfo = reflectionService.getClassInfoForClass(targetParametersSpec.getClass());

        if (fieldModels == null) {
            return;
        }

        for (FieldModel fieldModel : fieldModels) {
            ParameterDefinitionSpec fieldModelDefinition = fieldModel.getDefinition();
            String yamlFieldName = fieldModelDefinition.getFieldName();
            FieldInfo fieldInfo = parametersClassInfo.getFieldByYamlName(yamlFieldName);

            if (fieldInfo == null) {
                throw new RuntimeException("Field " + yamlFieldName + " was not found on the target class " + targetParametersSpec.getClass().getName());
            }
            
            FieldModel fieldModelForUpdate = fieldModel.cloneForUpdate();
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
                case datetime_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getDatetimeValue(), targetParametersSpec);
                    break;
                case column_name_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getColumnNameValue(), targetParametersSpec);
                    break;
                case enum_type:
                    fieldInfo.setFieldValue(fieldModelForUpdate.getEnumValue(), targetParametersSpec);
                    break;
                case string_list_type:
                    List<String> stringListValue = fieldModelForUpdate.getStringListValue();
                    if (stringListValue != null) {
                        while (stringListValue.contains("")) {
                            stringListValue.remove("");
                        }
                        while (stringListValue.contains(null)) {
                            stringListValue.remove(null);
                        }
                    }
                    fieldInfo.setFieldValue(stringListValue, targetParametersSpec);
                    break;
                case integer_list_type:
                    List<Long> integerListValue = fieldModelForUpdate.getIntegerListValue();
                    if (integerListValue != null) {
                        while (integerListValue.contains(null)) {
                            integerListValue.remove(null);
                        }
                    }
                    fieldInfo.setFieldValue(integerListValue, targetParametersSpec);
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
