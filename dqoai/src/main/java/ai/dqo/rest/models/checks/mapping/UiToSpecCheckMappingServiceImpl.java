package ai.dqo.rest.models.checks.mapping;

import ai.dqo.checks.AbstractCheckCategoriesSpec;
import ai.dqo.checks.AbstractCheckSpec;
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
    public void updateAllChecksSpecs(UIAllChecksModel model, AbstractCheckCategoriesSpec checkCategoriesSpec) {
        ClassInfo checkCategoriesClassInfo = reflectionService.getClassInfoForClass(checkCategoriesSpec.getClass());
        List<UIQualityDimensionModel> dimensionModelList = model.getQualityDimensions();
        if (dimensionModelList == null) {
            return;
        }

        for (UIQualityDimensionModel dimensionModel : dimensionModelList) {
            String qualityDimension = dimensionModel.getQualityDimension();
            FieldInfo dimensionFieldInfo = checkCategoriesClassInfo.getFieldByYamlName(qualityDimension);
            AbstractSpec dimensionSpec = (AbstractSpec) dimensionFieldInfo.getFieldValueOrNewObject(checkCategoriesSpec);

            updateDimensionChecksSpec(dimensionModel, dimensionSpec);

            if (dimensionSpec.isDefault()) {
                dimensionFieldInfo.setFieldValue(null, checkCategoriesSpec);
            } else {
                dimensionFieldInfo.setFieldValue(dimensionSpec, checkCategoriesSpec);
            }
        }
    }

    /**
     * Updates the dimension container specification (an object that contains data quality check specifications) with the changes
     * received from the UI model.
     *
     * @param dimensionModel Source UI dimension model with the updates.
     * @param dimensionSpec  Target dimension specification to update.
     */
    protected void updateDimensionChecksSpec(UIQualityDimensionModel dimensionModel, AbstractSpec dimensionSpec) {
        ClassInfo checkCategoryClassInfo = reflectionService.getClassInfoForClass(dimensionSpec.getClass());
        List<UICheckModel> checkModels = dimensionModel.getChecks();
        if (checkModels == null) {
            return;
        }

        for (UICheckModel checkModel : checkModels) {
            String yamlCheckName = checkModel.getCheckName();
            FieldInfo checkFieldInfo = checkCategoryClassInfo.getFieldByYamlName(yamlCheckName);
            AbstractCheckSpec checkSpec = (AbstractCheckSpec) checkFieldInfo.getFieldValueOrNewObject(dimensionSpec);

            updateCheckSpec(checkModel, checkSpec);

            if (checkSpec.isDefault()) {
                checkFieldInfo.setFieldValue(null, dimensionSpec);
            } else {
                checkFieldInfo.setFieldValue(checkSpec, dimensionSpec);
            }
        }
    }

    /**
     * Updates the check specification <code>checkSpec</code> from the changes in the <code>checkModel</code> UI model.
     *
     * @param checkModel Source UI model for the data quality check.
     * @param checkSpec  Target check specification to update.
     */
    protected void updateCheckSpec(UICheckModel checkModel, AbstractCheckSpec checkSpec) {
        checkSpec.setDimensionsOverride(checkModel.getDimensionsOverride());
        checkSpec.setTimeSeriesOverride(checkModel.getTimeSeriesOverride());
        checkSpec.setScheduleOverride(checkModel.getScheduleOverride());
        checkSpec.setComments(checkModel.getComments());
        checkSpec.getSensorParameters().setDisabled(checkModel.isDisabled());
        checkSpec.getSensorParameters().setFilter(checkModel.getFilter());

        updateFieldValues(checkModel.getSensorParameters(), checkSpec.getSensorParameters());

        ClassInfo checkClassInfo = reflectionService.getClassInfoForClass(checkSpec.getClass());
        List<UIRuleThresholdsModel> ruleThresholdsModels = checkModel.getRules();
        if (ruleThresholdsModels == null) {
            return;
        }

        for (UIRuleThresholdsModel ruleThresholdsModel : ruleThresholdsModels) {
            String yamlRuleName = ruleThresholdsModel.getFieldName();
            FieldInfo ruleThresholdsFieldInfo = checkClassInfo.getFieldByYamlName(yamlRuleName);
            AbstractRuleThresholdsSpec ruleThresholdsSpec = (AbstractRuleThresholdsSpec) ruleThresholdsFieldInfo.getFieldValueOrNewObject(checkSpec);

            updateRuleThresholdsSpec(ruleThresholdsModel, ruleThresholdsSpec);

            if (ruleThresholdsSpec.isDefault()) {
                ruleThresholdsFieldInfo.setFieldValue(null, checkSpec);
            } else {
                ruleThresholdsFieldInfo.setFieldValue(ruleThresholdsSpec, checkSpec);
            }
        }
    }

    /**
     * Updates the rule thresholds from the changes in the UI model.
     *
     * @param ruleThresholdsModel Source rule thresholds model with changes to the low, medium and high severities.
     * @param ruleThresholdsSpec  Target rule thresholds specification to update.
     */
    protected void updateRuleThresholdsSpec(UIRuleThresholdsModel ruleThresholdsModel, AbstractRuleThresholdsSpec ruleThresholdsSpec) {
        ruleThresholdsSpec.setTimeWindow(ruleThresholdsModel.getTimeWindow());

        ClassInfo ruleThresholdsClassInfo = reflectionService.getClassInfoForClass(ruleThresholdsSpec.getClass());
        FieldInfo lowFieldInfo = ruleThresholdsClassInfo.getField("low");
        updateSeveritySpec(ruleThresholdsModel.getLow(), lowFieldInfo, ruleThresholdsSpec);

        FieldInfo mediumFieldInfo = ruleThresholdsClassInfo.getField("medium");
        updateSeveritySpec(ruleThresholdsModel.getMedium(), mediumFieldInfo, ruleThresholdsSpec);

        FieldInfo highFieldInfo = ruleThresholdsClassInfo.getField("high");
        updateSeveritySpec(ruleThresholdsModel.getHigh(), highFieldInfo, ruleThresholdsSpec);
    }

    /**
     * Updates the severity object (with the parameters) in the parent rule threshold object. First retrieves (or creates) the rule parameters object,
     * then updates all rule parameter fields and finally stores the resulting object in the parent rule thresholds specification.
     * @param ruleParametersModel Source rule parameters object.
     * @param severityFieldInfo Field info for the severity field (low, medium, high) that will be updated.
     * @param parentRuleThresholdsSpec Target parent rule thresholds object to update.
     */
    protected void updateSeveritySpec(UIRuleParametersModel ruleParametersModel,
                                      FieldInfo severityFieldInfo,
                                      AbstractRuleThresholdsSpec parentRuleThresholdsSpec) {
        AbstractRuleParametersSpec ruleParametersSpec = (AbstractRuleParametersSpec)
                severityFieldInfo.getFieldValueOrNewObject(parentRuleThresholdsSpec);
        ruleParametersSpec.setDisabled(ruleParametersModel.isDisabled());
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
            String yamlFieldName = fieldModelDefinition.getFieldName();
            FieldInfo fieldInfo = parametersClassInfo.getFieldByYamlName(yamlFieldName);

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
                default:
                    throw new UnsupportedOperationException("Unsupported type: " + fieldInfo.getDataType().toString());
            }
        }
    }
}
