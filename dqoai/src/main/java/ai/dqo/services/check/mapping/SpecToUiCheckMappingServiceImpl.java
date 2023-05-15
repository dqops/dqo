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
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.connectors.ProviderType;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.core.scheduler.quartz.SchedulesUtilityService;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindService;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionSpec;
import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.metadata.scheduling.CheckRunRecurringScheduleGroup;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.scheduling.RecurringSchedulesSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.services.check.mapping.basicmodels.UICheckBasicModel;
import ai.dqo.services.check.mapping.basicmodels.UICheckContainerBasicModel;
import ai.dqo.services.check.mapping.models.*;
import ai.dqo.utils.reflection.ClassInfo;
import ai.dqo.utils.reflection.FieldInfo;
import ai.dqo.utils.reflection.ReflectionService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service that creates the UI model from the data quality check specifications,
 * enabling transformation between the storage model (YAML compliant) with a UI friendly UI model.
 */
@Service
public class SpecToUiCheckMappingServiceImpl implements SpecToUiCheckMappingService {
    private final ReflectionService reflectionService;
    private final SensorDefinitionFindService sensorDefinitionFindService;
    private final SchedulesUtilityService schedulesUtilityService;

    /**
     * Creates a check mapping service that exchanges the data for data quality checks between UI models and the data quality check specifications.
     * @param reflectionService Reflection service used to read the list of checks.
     * @param sensorDefinitionFindService Service that finds the definition of sensors, to verify their capabilities.
     * @param schedulesUtilityService Schedule specs utility service to get detailed info about CRON expressions.
     */
    @Autowired
    public SpecToUiCheckMappingServiceImpl(ReflectionService reflectionService,
                                           SensorDefinitionFindService sensorDefinitionFindService,
                                           SchedulesUtilityService schedulesUtilityService) {
        this.reflectionService = reflectionService;
        this.sensorDefinitionFindService = sensorDefinitionFindService;
        this.schedulesUtilityService = schedulesUtilityService;
    }

    /**
     * Creates a check mapping service that exchanges the data for data quality checks between UI models and the data quality check specifications.
     * WARNING: It doesn't set the {@link SchedulesUtilityService}, therefore it's not able to resolve schedules (i.e. CRON expressions).
     * @param reflectionService Reflection service used to read the list of checks.
     * @param sensorDefinitionFindService Service that finds the definition of sensors, to verify their capabilities.
     */
    public static SpecToUiCheckMappingServiceImpl createInstanceUnsafe(ReflectionService reflectionService,
                                                                       SensorDefinitionFindService sensorDefinitionFindService) {
        return new SpecToUiCheckMappingServiceImpl(reflectionService, sensorDefinitionFindService, null);
    }

    /**
     * Creates a UI friendly model of the whole checks container on table level or column level data quality checks, divided into categories.
     *
     * @param checkCategoriesSpec Table or column level data quality checks container of type profiling, recurring or partitioned check (for a specific timescale).
     * @param runChecksTemplate Check search filter for the parent table or column that is used as a template to create more fine-grained "run checks" job configurations. Also determines which checks will be included in the ui model.
     * @param connectionSpec Connection specification for the connection to which the table belongs to.
     * @param tableSpec Table specification with the configuration of the parent table.
     * @param executionContext Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType Provider type from the parent connection.
     * @return UI friendly model of data quality checks' container.
     */
    @Override
    public UICheckContainerModel createUiModel(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                               CheckSearchFilters runChecksTemplate,
                                               ConnectionSpec connectionSpec,
                                               TableSpec tableSpec,
                                               ExecutionContext executionContext,
                                               ProviderType providerType) {
        UICheckContainerModel uiCheckContainerModel = new UICheckContainerModel();
        uiCheckContainerModel.setRunChecksJobTemplate(runChecksTemplate.clone());
        uiCheckContainerModel.setPartitionByColumn(tableSpec.getTimestampColumns() != null ?
                tableSpec.getTimestampColumns().getPartitionByColumn() : null);
        uiCheckContainerModel.setDataCleanJobTemplate(
                DeleteStoredDataQueueJobParameters.fromCheckSearchFilters(
                        uiCheckContainerModel.getRunChecksJobTemplate()));

        UIEffectiveScheduleModel effectiveScheduleModel = getEffectiveScheduleModel(
                tableSpec.getSchedulesOverride(),
                checkCategoriesSpec.getSchedulingGroup(),
                UIEffectiveScheduleLevel.table_override
        );
        UIScheduleEnabledStatus scheduleEnabledStatus = getScheduleEnabledStatus(
                tableSpec.getSchedulesOverride() != null ?
                    tableSpec.getSchedulesOverride()
                        .getScheduleForCheckSchedulingGroup(
                                checkCategoriesSpec.getSchedulingGroup()
                        )
                    : null
        );
        if (effectiveScheduleModel == null && connectionSpec != null) {
            effectiveScheduleModel = getEffectiveScheduleModel(
                    connectionSpec.getSchedules(),
                    checkCategoriesSpec.getSchedulingGroup(),
                    UIEffectiveScheduleLevel.connection
            );
            scheduleEnabledStatus = getScheduleEnabledStatus(
                    connectionSpec.getSchedules() != null ?
                        connectionSpec.getSchedules()
                            .getScheduleForCheckSchedulingGroup(
                                    checkCategoriesSpec.getSchedulingGroup()
                            )
                        : null
            );
        }
        uiCheckContainerModel.setEffectiveSchedule(effectiveScheduleModel);
        uiCheckContainerModel.setEffectiveScheduleEnabledStatus(scheduleEnabledStatus);

        ClassInfo checkCategoriesClassInfo = reflectionService.getClassInfoForClass(checkCategoriesSpec.getClass());
        Optional<String> categoryNameFilter = Optional.ofNullable(uiCheckContainerModel.getRunChecksJobTemplate().getCheckCategory());
        List<FieldInfo> categoryFields = this.getFilteredFieldInfo(checkCategoriesClassInfo, categoryNameFilter);
        CheckType checkType = checkCategoriesSpec.getCheckType();
        CheckTimeScale checkTimeScale = checkCategoriesSpec.getCheckTimeScale();

        for (FieldInfo categoryFieldInfo : categoryFields) {
            Object categoryFieldValue = categoryFieldInfo.getFieldValueOrNewObject(checkCategoriesSpec);
            UIQualityCategoryModel categoryModel = createCategoryModel(categoryFieldInfo,
                    categoryFieldValue,
                    checkCategoriesSpec.getSchedulingGroup(),
                    runChecksTemplate,
                    tableSpec,
                    executionContext,
                    providerType,
                    checkType,
                    checkTimeScale);
            if (categoryModel != null && categoryModel.getChecks().size() > 0) {
                uiCheckContainerModel.getCategories().add(categoryModel);
            }
        }

        // All checks override schedule especially in cases when CheckSearchFilters are very specific.
        List<UICheckModel> allChecksFlattened = uiCheckContainerModel.getCategories().stream()
                .flatMap(checkCategory -> checkCategory.getChecks().stream())
                .collect(Collectors.toList());

        if (allChecksFlattened.stream().findAny().isPresent()
                && allChecksFlattened.stream().allMatch(
                        check -> check.getEffectiveSchedule() != null
        )) {
             // Info about schedule for the whole model is irrelevant.
            uiCheckContainerModel.setEffectiveSchedule(null);
            uiCheckContainerModel.setEffectiveScheduleEnabledStatus(UIScheduleEnabledStatus.overridden_by_checks);
        }

        return uiCheckContainerModel;
    }

    /**
     * Creates a simplistic UI friendly model of every data quality check on table level or column level, divided into categories.
     *
     * @param checkCategoriesSpec Table or column level data quality checks container of type profiling, recurring or partitioned check (for a specific timescale).
     * @param executionContext Check execution context with access to the check information.
     * @param providerType Provider type.
     * @return Simplistic UI friendly model of data quality checks' container.
     */
    @Override
    public UICheckContainerBasicModel createUiBasicModel(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                                    ExecutionContext executionContext,
                                                    ProviderType providerType) {
        UICheckContainerBasicModel uiCheckContainerBasicModel = new UICheckContainerBasicModel();
        ClassInfo checkCategoriesClassInfo = reflectionService.getClassInfoForClass(checkCategoriesSpec.getClass());
        List<FieldInfo> categoryFields = this.getFilteredFieldInfo(checkCategoriesClassInfo, Optional.empty());
        CheckType checkType = checkCategoriesSpec.getCheckType();
        CheckTimeScale checkTimeScale = checkCategoriesSpec.getCheckTimeScale();

        for (FieldInfo categoryFieldInfo : categoryFields) {
            Object checkCategoryParentNode = categoryFieldInfo.getFieldValueOrNewObject(checkCategoriesSpec);

            ClassInfo checkListClassInfo = reflectionService.getClassInfoForClass(checkCategoryParentNode.getClass());
            List<FieldInfo> checksFields = this.getFilteredFieldInfo(checkListClassInfo, Optional.empty());

            for (FieldInfo checkFieldInfo : checksFields) {
                boolean checkIsConfigured = checkFieldInfo.getFieldValue(checkCategoryParentNode) != null;
                AbstractCheckSpec<?,?,?,?> checkSpecObject = (AbstractCheckSpec<?,?,?,?>)checkFieldInfo.getFieldValueOrNewObject(checkCategoryParentNode);

                UICheckBasicModel checkModel = createCheckBasicModel(checkFieldInfo, checkSpecObject, executionContext, providerType, checkType, checkTimeScale);
                checkModel.setConfigured(checkIsConfigured);
                checkModel.setCheckCategory(categoryFieldInfo.getYamlFieldName());
                uiCheckContainerBasicModel.getChecks().add(checkModel);
            }
        }

        uiCheckContainerBasicModel.getChecks().sort(UICheckBasicModel::compareTo);
        return uiCheckContainerBasicModel;
    }

    /**
     * Creates a UI model for all data quality checks for one category.
     * @param categoryFieldInfo       Field info for the category field.
     * @param checkCategoryParentNode The current category specification object instance (an object that has fields for all data quality checks in the category).
     * @param scheduleGroup Scheduling group relevant to this check.
     * @param runChecksTemplate       Run check job template, acting as a filtering template.
     * @param tableSpec               Table specification with the configuration of the parent table.
     * @param executionContext Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType Provider type from the parent connection.
     * @param checkType Check type (profiling, recurring, ...).
     * @param checkTimeScale Check time scale: null for profiling, daily/monthly for others that apply the date truncation.
     * @return UI model for a category with all quality checks, filtered by runChecksTemplate.
     */
    protected UIQualityCategoryModel createCategoryModel(FieldInfo categoryFieldInfo,
                                                         Object checkCategoryParentNode,
                                                         CheckRunRecurringScheduleGroup scheduleGroup,
                                                         CheckSearchFilters runChecksTemplate,
                                                         TableSpec tableSpec,
                                                         ExecutionContext executionContext,
                                                         ProviderType providerType,
                                                         CheckType checkType,
                                                         CheckTimeScale checkTimeScale) {
        UIQualityCategoryModel categoryModel = new UIQualityCategoryModel();
        categoryModel.setCategory(categoryFieldInfo.getYamlFieldName());
        categoryModel.setHelpText(categoryFieldInfo.getHelpText());
        CheckSearchFilters runChecksCategoryTemplate = runChecksTemplate.clone();
        runChecksCategoryTemplate.setCheckCategory(categoryFieldInfo.getYamlFieldName());
        categoryModel.setRunChecksJobTemplate(runChecksCategoryTemplate);
        categoryModel.setDataCleanJobTemplate(
                DeleteStoredDataQueueJobParameters.fromCheckSearchFilters(
                        runChecksCategoryTemplate
                )
        );

        ClassInfo checkListClassInfo = reflectionService.getClassInfoForClass(checkCategoryParentNode.getClass());
        Optional<String> checkNameFilter = Optional.ofNullable(categoryModel.getRunChecksJobTemplate().getCheckName());
        List<FieldInfo> checksFields = this.getFilteredFieldInfo(checkListClassInfo, checkNameFilter);

        for (FieldInfo checkFieldInfo : checksFields) {
            AbstractSpec checkSpecObjectNullable = (AbstractSpec)checkFieldInfo.getFieldValue(checkCategoryParentNode);
            AbstractSpec checkSpecObject = checkSpecObjectNullable != null ? checkSpecObjectNullable :
                    (AbstractSpec)checkFieldInfo.getFieldValueOrNewObject(checkCategoryParentNode);
            AbstractCheckSpec<?,?,?,?> checkFieldValue = (AbstractCheckSpec<?,?,?,?>) checkSpecObject;
            UICheckModel checkModel = createCheckModel(checkFieldInfo,
                    checkFieldValue,
                    scheduleGroup,
                    runChecksCategoryTemplate,
                    tableSpec,
                    executionContext,
                    providerType,
                    checkType,
                    checkTimeScale);
            if (checkModel == null) {
                continue;
            }

            checkModel.setConfigured(checkSpecObjectNullable != null);
            categoryModel.getChecks().add(checkModel);
        }

        return categoryModel;
    }

    /**
     * Creates a UI model for a single data quality check.
     * @param checkFieldInfo Reflection info of the field in the parent object that stores the check specification field value.
     * @param checkSpec Check specification instance retrieved from the object.
     * @param scheduleGroup Scheduling group relevant to this check.
     * @param runChecksCategoryTemplate "run check" job configuration for the parent category, used to create templates for each check.
     * @param tableSpec Table specification with the configuration of the parent table.
     * @param executionContext Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType Provider type from the parent connection.
     * @param checkType Check type (profiling, recurring, ...).
     * @param checkTimeScale Check time scale: null for profiling, daily/monthly for others that apply the date truncation.
     * @return Check model.
     */
    protected UICheckModel createCheckModel(FieldInfo checkFieldInfo,
                                            AbstractCheckSpec<?,?,?,?> checkSpec,
                                            CheckRunRecurringScheduleGroup scheduleGroup,
                                            CheckSearchFilters runChecksCategoryTemplate,
                                            TableSpec tableSpec,
                                            ExecutionContext executionContext,
                                            ProviderType providerType,
                                            CheckType checkType,
                                            CheckTimeScale checkTimeScale) {
        UICheckModel checkModel = new UICheckModel();

        ClassInfo checkClassInfo = reflectionService.getClassInfoForClass(checkSpec.getClass());
        FieldInfo parametersFieldInfo = checkClassInfo.getField("parameters");
        AbstractSensorParametersSpec parametersSpec = (AbstractSensorParametersSpec)parametersFieldInfo.getFieldValueOrNewObject(checkSpec);
        checkModel.setFilter(parametersSpec.getFilter());
        String sensorDefinitionName = parametersSpec.getSensorDefinitionName();
        checkModel.setSensorName(sensorDefinitionName);
        checkModel.setSensorParametersSpec(parametersSpec);

        if (executionContext != null && providerType != null) {
            SensorDefinitionFindResult providerSensorDefinition = this.sensorDefinitionFindService.findProviderSensorDefinition(
                    executionContext, sensorDefinitionName, providerType);

            ProviderSensorDefinitionSpec providerSensorDefinitionSpec = providerSensorDefinition.getProviderSensorDefinitionSpec();
            if (providerSensorDefinitionSpec == null) {
                return null; // skip this check
            }

            if (providerSensorDefinitionSpec.isSupportsPartitionedChecks() != null &&
                    !providerSensorDefinitionSpec.isSupportsPartitionedChecks() && checkType == CheckType.PARTITIONED) {
                return null; // skip this check
            }

            checkModel.setSupportsDataStreams(providerSensorDefinitionSpec.isSupportsGroupingByDataStream() == null || providerSensorDefinitionSpec.isSupportsGroupingByDataStream());

            SensorDefinitionSpec sensorDefinitionSpec = providerSensorDefinition.getSensorDefinitionSpec();
            if (sensorDefinitionSpec.isRequiresEventTimestamp() &&
                    Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getEventTimestampColumn())) {
                if (sensorDefinitionSpec.isRequiresIngestionTimestamp() &&
                        Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getIngestionTimestampColumn())) {
                    checkModel.pushError(CheckConfigurationRequirementsError.missing_event_and_ingestion_columns);
                } else {
                    checkModel.pushError(CheckConfigurationRequirementsError.missing_event_timestamp_column);
                }
            } else {
                if (sensorDefinitionSpec.isRequiresIngestionTimestamp() &&
                        Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getIngestionTimestampColumn())) {
                    checkModel.pushError(CheckConfigurationRequirementsError.missing_ingestion_timestamp_column);
                }
            }
        }

        checkModel.setCheckName(checkFieldInfo.getDisplayName());
        checkModel.setHelpText(checkFieldInfo.getHelpText());
        CheckSearchFilters runOneCheckTemplate = runChecksCategoryTemplate.clone();
        runOneCheckTemplate.setCheckName(checkFieldInfo.getYamlFieldName());
        checkModel.setRunChecksJobTemplate(runOneCheckTemplate);

        DeleteStoredDataQueueJobParameters dataCleanJobTemplate = DeleteStoredDataQueueJobParameters.fromCheckSearchFilters(runOneCheckTemplate);
        dataCleanJobTemplate.setDataStreamName(checkSpec.getDataStream());
        checkModel.setDataCleanJobTemplate(dataCleanJobTemplate);

        RecurringScheduleSpec scheduleOverride = checkSpec.getScheduleOverride();
        checkModel.setScheduleOverride(scheduleOverride);
        if (scheduleOverride != null && !scheduleOverride.isDisabled()) {
                checkModel.setEffectiveSchedule(
                        UIEffectiveScheduleModel.fromRecurringScheduleSpec(
                                scheduleOverride,
                                scheduleGroup,
                                UIEffectiveScheduleLevel.check_override,
                                this::safeGetTimeOfNextExecution
                        )
                );
        }
        checkModel.setScheduleEnabledStatus(getScheduleEnabledStatus(scheduleOverride));

        checkModel.setComments(checkSpec.getComments());
        checkModel.setDisabled(checkSpec.isDisabled());
        checkModel.setExcludeFromKpi(checkSpec.isExcludeFromKpi());
        checkModel.setIncludeInSla(checkSpec.isIncludeInSla());
        checkModel.setDataStream(checkSpec.getDataStream());
        checkModel.setCheckSpec(checkSpec);
        checkModel.setCheckTarget(UICheckTarget.target);

        List<UIFieldModel> fieldsForParameterSpec = createFieldsForSensorParameters(parametersSpec);
        checkModel.setSensorParameters(fieldsForParameterSpec);

        UIRuleThresholdsModel ruleModel = createRuleThresholdsModel(checkSpec);
        checkModel.setRule(ruleModel);

        return checkModel;
    }

    /**
     * Creates a simplistic UI model for a single data quality check.
     * @param checkFieldInfo Reflection info of the field in the parent object that stores the check specification field value.
     * @param checkSpec Check specification.
     * @param executionContext Check execution context.
     * @param providerType Provider type from the parent connection.
     * @param checkType Check type (profiling, recurring, ...).
     * @param checkTimeScale Check time scale: null for profiling, daily/monthly for others that apply the date truncation.
     * @return Check basic model.
     */
    protected UICheckBasicModel createCheckBasicModel(FieldInfo checkFieldInfo,
                                                      AbstractCheckSpec<?,?,?,?> checkSpec,
                                                      ExecutionContext executionContext,
                                                      ProviderType providerType,
                                                      CheckType checkType,
                                                      CheckTimeScale checkTimeScale) {
        UICheckBasicModel checkModel = new UICheckBasicModel();
        checkModel.setCheckName(checkFieldInfo.getDisplayName());
        checkModel.setHelpText(checkFieldInfo.getHelpText());

        ClassInfo checkClassInfo = reflectionService.getClassInfoForClass(checkSpec.getClass());
        FieldInfo parametersFieldInfo = checkClassInfo.getField("parameters");

        AbstractSensorParametersSpec parametersSpec = (AbstractSensorParametersSpec)parametersFieldInfo.getFieldValueOrNewObject(checkSpec);
        String sensorDefinitionName = parametersSpec.getSensorDefinitionName();

        if (executionContext != null && providerType != null) {
            SensorDefinitionFindResult providerSensorDefinition = this.sensorDefinitionFindService.findProviderSensorDefinition(
                    executionContext, sensorDefinitionName, providerType);

            if (providerSensorDefinition == null) {
                return null; // skip this check
            }

            ProviderSensorDefinitionSpec providerSensorDefinitionSpec = providerSensorDefinition.getProviderSensorDefinitionSpec();
            if (providerSensorDefinitionSpec == null) {
                return null; // skip this check
            }

            if (providerSensorDefinitionSpec.isSupportsPartitionedChecks() != null &&
                    !providerSensorDefinitionSpec.isSupportsPartitionedChecks() && checkType == CheckType.PARTITIONED) {
                return null; // skip this check
            }
        }

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
     * Creates a list of fields used to edit a single rule severity level.
     * @param severityFieldInfo Reflection info of the field - used to retrieve the help text.
     * @param abstractCheckSpec Rule thresholds specification object (parent object).
     * @return Rule parameters.
     */
    public UIRuleParametersModel createRuleParametersModel(FieldInfo severityFieldInfo, AbstractCheckSpec<?,?,?,?> abstractCheckSpec) {
        UIRuleParametersModel parametersModel = new UIRuleParametersModel();
        AbstractRuleParametersSpec parametersValueNullable = (AbstractRuleParametersSpec)severityFieldInfo.getFieldValue(abstractCheckSpec);
        AbstractRuleParametersSpec parametersSpecNotNull = parametersValueNullable != null ? parametersValueNullable :
                (AbstractRuleParametersSpec)severityFieldInfo.getFieldValueOrNewObject(abstractCheckSpec);

        parametersModel.setConfigured(parametersValueNullable != null);
        parametersModel.setRuleName(parametersSpecNotNull.getRuleDefinitionName());
        parametersModel.setRuleParametersSpec(parametersSpecNotNull);

        List<UIFieldModel> uiFieldModels = createFieldsForRuleParameters(parametersSpecNotNull);
        parametersModel.setRuleParameters(uiFieldModels);

        return parametersModel;
    }

    /**
     * Creates a list of fields to edit all values in the rule parameters specification.
     * @param ruleParametersSpec Rule parameters specification.
     * @return List of UI fields for all rule parameter fields.
     */
    public List<UIFieldModel> createFieldsForRuleParameters(AbstractRuleParametersSpec ruleParametersSpec) {
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
    public List<UIFieldModel> createFieldsForSensorParameters(AbstractSensorParametersSpec parametersSpec) {
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
    public UIFieldModel createFieldModel(FieldInfo fieldInfo, Object parentObject) {
        Object fieldValue = fieldInfo.getFieldValue(parentObject);
        UIFieldModel fieldModel = new UIFieldModel();
        ParameterDefinitionSpec parameterDefinitionSpec = new ParameterDefinitionSpec();
        // we are using reflection info, but we could also pull the information from the sensor specification
        assert fieldInfo.getDataType() != null && fieldInfo.getDataType() != ParameterDataType.object_type :
                fieldInfo.getYamlFieldName() + " on " + parentObject.getClass().getName() + " has an unsupported type " + fieldInfo.getDataType();
        parameterDefinitionSpec.setDataType(fieldInfo.getDataType());
        parameterDefinitionSpec.setFieldName(fieldInfo.getYamlFieldName());
        parameterDefinitionSpec.setDisplayName(fieldInfo.getDisplayName());
        parameterDefinitionSpec.setHelpText(fieldInfo.getHelpText());
        parameterDefinitionSpec.setDisplayHint(fieldInfo.getDisplayHint());
        parameterDefinitionSpec.setSampleValues(fieldInfo.getSampleValues() != null ? List.of(fieldInfo.getSampleValues()) : null);
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
                case integer_list_type:
                    fieldModel.setIntegerListValue((List<Long>) fieldValue);
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

    /**
     * Gets a list of field infos from class info, with an optional filter for field's YAML name.
     * @param classInfo     ClassInfo object.
     * @param requiredField Name of the requested field.
     * @return A list of fields in class, optionally filtered.
     */
    protected List<FieldInfo> getFilteredFieldInfo(ClassInfo classInfo, Optional<String> requiredField) {
        List<FieldInfo> fields = new ArrayList<>();

        if (requiredField.isPresent()) {
            FieldInfo fieldInfo = classInfo.getFieldByYamlName(requiredField.get());
            if (fieldInfo != null) {
                fields.add(fieldInfo);
            }
        } else {
            classInfo.getFields().stream()
                    // skipping custom checks for the moment
                    .filter(fieldInfo -> !fieldInfo.getClassFieldName().equals("custom"))
                    .forEachOrdered(fields::add);
        }

        return fields;
    }

    /**
     * Gets the time of the next execution of <code>scheduleSpec</code>, if the {@link SchedulesUtilityService} is initialized.
     * @param scheduleSpec Schedule configuration containing a CRON expression with execution timetable.
     * @return Time of next execution of <code>scheduleSpec</code> if it's not disabled and the service is able to get it. Else null.
     */
    protected ZonedDateTime safeGetTimeOfNextExecution(RecurringScheduleSpec scheduleSpec) {
        if (this.schedulesUtilityService == null || scheduleSpec == null || scheduleSpec.isDisabled()) {
            return null;
        }
        return this.schedulesUtilityService.getTimeOfNextExecution(scheduleSpec);
    }

    /**
     * Gets the {@link UIEffectiveScheduleModel} out of <code>schedulesSpec</code>, if the schedule is enabled, provided some other arguments.
     * @param schedulesSpec Schedule spec.
     * @param scheduleGroup Schedule group.
     * @param scheduleLevel Schedule level.
     * @return Effective model of the schedule configuration. If <code>scheduleSpec</code> is null or disabled, returns null.
     */
    protected UIEffectiveScheduleModel getEffectiveScheduleModel(RecurringSchedulesSpec schedulesSpec,
                                                                 CheckRunRecurringScheduleGroup scheduleGroup,
                                                                 UIEffectiveScheduleLevel scheduleLevel) {
        RecurringScheduleSpec scheduleSpec = schedulesSpec != null
                ? schedulesSpec.getScheduleForCheckSchedulingGroup(scheduleGroup)
                : null;

        if (scheduleSpec != null && !scheduleSpec.isDisabled()) {
            return UIEffectiveScheduleModel.fromRecurringScheduleSpec(
                    scheduleSpec,
                    scheduleGroup,
                    scheduleLevel,
                    this::safeGetTimeOfNextExecution
            );
        }
        return null;
    }

    /**
     * Gets the relevant {@link UIScheduleEnabledStatus} from <code>scheduleSpec</code>
     * @param scheduleSpec Schedule configuration for which to get activation status.
     * @return {@link UIScheduleEnabledStatus} indicating the activation status of <code>scheduleSpec</code>.
     */
    protected UIScheduleEnabledStatus getScheduleEnabledStatus(RecurringScheduleSpec scheduleSpec) {
        if (scheduleSpec != null) {
            if (scheduleSpec.isDisabled()) {
                return UIScheduleEnabledStatus.disabled;
            }
            else {
                return UIScheduleEnabledStatus.enabled;
            }
        }
        else {
            return UIScheduleEnabledStatus.not_configured;
        }
    }
}
