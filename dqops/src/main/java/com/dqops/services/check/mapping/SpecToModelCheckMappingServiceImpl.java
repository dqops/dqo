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

import com.dqops.checks.*;
import com.dqops.checks.comparison.AbstractColumnComparisonCheckCategorySpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.checks.custom.CustomCheckSpec;
import com.dqops.checks.custom.CustomCheckSpecMap;
import com.dqops.checks.custom.CustomParametersSpecObject;
import com.dqops.connectors.ProviderType;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.core.scheduler.quartz.SchedulesUtilityService;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.rules.finder.RuleDefinitionFindResult;
import com.dqops.execution.rules.finder.RuleDefinitionFindService;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sensors.finder.SensorDefinitionFindService;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionList;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.definitions.rules.RuleDefinitionSpec;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.metadata.fields.ParameterDefinitionsListSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.scheduling.MonitoringSchedulesSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.CustomRuleParametersSpec;
import com.dqops.rules.HistoricDataPointsGrouping;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.sensors.CustomSensorParametersSpec;
import com.dqops.services.check.mapping.basicmodels.CheckListModel;
import com.dqops.services.check.mapping.basicmodels.CheckContainerListModel;
import com.dqops.services.check.mapping.models.*;
import com.dqops.services.check.matching.SimilarCheckCache;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ReflectionService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that creates a model from the data quality check specifications,
 * enabling transformation from the storage model (YAML compliant) to a UI friendly model.
 */
@Service
@Slf4j
public class SpecToModelCheckMappingServiceImpl implements SpecToModelCheckMappingService {
    private final ReflectionService reflectionService;
    private final SensorDefinitionFindService sensorDefinitionFindService;
    private final RuleDefinitionFindService ruleDefinitionFindService;
    private final SchedulesUtilityService schedulesUtilityService;
    private final SimilarCheckCache similarCheckCache;

    /**
     * Creates a check mapping service that exchanges the data for data quality checks between models and the data quality check specifications.
     * @param reflectionService Reflection service used to read the list of checks.
     * @param sensorDefinitionFindService Service that finds the definition of sensors, to verify their capabilities.
     * @param ruleDefinitionFindService Service that finds the definition of rules, used to look up the fields for rules used in custom checks.
     * @param schedulesUtilityService Schedule specs utility service to get detailed info about CRON expressions.
     * @param similarCheckCache Similar checks cache.
     */
    @Autowired
    public SpecToModelCheckMappingServiceImpl(ReflectionService reflectionService,
                                              SensorDefinitionFindService sensorDefinitionFindService,
                                              RuleDefinitionFindService ruleDefinitionFindService,
                                              SchedulesUtilityService schedulesUtilityService,
                                              SimilarCheckCache similarCheckCache) {
        this.reflectionService = reflectionService;
        this.sensorDefinitionFindService = sensorDefinitionFindService;
        this.ruleDefinitionFindService = ruleDefinitionFindService;
        this.schedulesUtilityService = schedulesUtilityService;
        this.similarCheckCache = similarCheckCache;
    }

    /**
     * Creates a check mapping service that exchanges the data for data quality checks between models and the data quality check specifications.
     * WARNING: It doesn't set the {@link SchedulesUtilityService}, therefore it's not able to resolve schedules (i.e. CRON expressions).
     * It is used for generating the documentation.
     * @param reflectionService Reflection service used to read the list of checks.
     * @param sensorDefinitionFindService Service that finds the definition of sensors, to verify their capabilities.
     * @param ruleDefinitionFindService Rule definition find service.
     */
    public static SpecToModelCheckMappingServiceImpl createInstanceUnsafe(ReflectionService reflectionService,
                                                                          SensorDefinitionFindService sensorDefinitionFindService,
                                                                          RuleDefinitionFindService ruleDefinitionFindService) {
        return new SpecToModelCheckMappingServiceImpl(reflectionService, sensorDefinitionFindService, ruleDefinitionFindService, null, null);
    }

    /**
     * Creates a model of the whole checks container on table level or column level data quality checks, divided into categories.
     *
     * @param checkCategoriesSpec Table or column level data quality checks container of type profiling, monitoring or partitioned check (for a specific timescale).
     * @param runChecksTemplate Check search filter for the parent table or column that is used as a template to create more fine-grained "run checks" job configurations. Also determines which checks will be included in the model.
     * @param connectionSpec Connection specification for the connection to which the table belongs to.
     * @param tableSpec Table specification with the configuration of the parent table.
     * @param executionContext Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType Provider type from the parent connection.
     * @param canManageChecks The user is an operator and can rul any operation.
     * @return Model of data quality checks' container.
     */
    @Override
    public CheckContainerModel createModel(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                           CheckSearchFilters runChecksTemplate,
                                           ConnectionSpec connectionSpec,
                                           TableSpec tableSpec,
                                           ExecutionContext executionContext,
                                           ProviderType providerType,
                                           boolean canManageChecks) {
        CheckContainerModel checkContainerModel = new CheckContainerModel();
        checkContainerModel.setCanEdit(canManageChecks);
        checkContainerModel.setCanRunChecks(canManageChecks);
        checkContainerModel.setCanDeleteData(canManageChecks);

        if (runChecksTemplate != null) {
            checkContainerModel.setRunChecksJobTemplate(runChecksTemplate.clone());

            checkContainerModel.setDataCleanJobTemplate(
                    DeleteStoredDataQueueJobParameters.fromCheckSearchFilters(
                            checkContainerModel.getRunChecksJobTemplate()));
        }

        if (tableSpec != null) {
            identifyEffectiveSchedulesAndPartitioning(checkCategoriesSpec, connectionSpec, tableSpec, checkContainerModel);
        }

        ClassInfo checkCategoriesClassInfo = reflectionService.getClassInfoForClass(checkCategoriesSpec.getClass());
        Optional<String> categoryNameFilter = Optional.ofNullable(checkContainerModel.getRunChecksJobTemplate()).map(ct -> ct.getCheckCategory());
        List<FieldInfo> categoryFields = this.getFilteredFieldInfo(checkCategoriesClassInfo, categoryNameFilter);
        CheckType checkType = checkCategoriesSpec.getCheckType();
        CheckTimeScale checkTimeScale = checkCategoriesSpec.getCheckTimeScale();

        for (FieldInfo categoryFieldInfo : categoryFields) {
            if (categoryFieldInfo.getDataType() != ParameterDataType.object_type) {
                continue;
            }

            Object categoryFieldValue = categoryFieldInfo.getFieldValueOrNewObject(checkCategoriesSpec);
            if (categoryFieldValue instanceof AbstractComparisonCheckCategorySpecMap<?>) {
                AbstractComparisonCheckCategorySpecMap<?> comparisonCheckCategorySpecMap = (AbstractComparisonCheckCategorySpecMap<?>)categoryFieldValue;
                Type actualTypeArgument = ((ParameterizedType) categoryFieldInfo.getClazz().getGenericSuperclass()).getActualTypeArguments()[0];
                Class<?> comparisonContainerClassType = (Class<?>) actualTypeArgument;
                ClassInfo comparisonChecksCategoryClassInfo = reflectionService.getClassInfoForClass(comparisonContainerClassType);

                if (tableSpec != null && tableSpec.getTableComparisons() != null && tableSpec.getTableComparisons().size() > 0) {
                    for (String comparisonName : tableSpec.getTableComparisons().keySet()) {
                        TableComparisonConfigurationSpec tableComparisonConfigurationSpec = tableSpec.getTableComparisons().get(comparisonName);
                        if (tableComparisonConfigurationSpec.getCheckType() != checkType ||
                            tableComparisonConfigurationSpec.getTimeScale() != checkTimeScale) {
                            continue; // configuration for a different check type
                        }

                        AbstractComparisonCheckCategorySpec configuredComparisonChecksCategory = comparisonCheckCategorySpecMap.get(comparisonName);
                        if (configuredComparisonChecksCategory == null) {
                            configuredComparisonChecksCategory = (AbstractComparisonCheckCategorySpec) comparisonChecksCategoryClassInfo.createNewInstance();
                            configuredComparisonChecksCategory.setHierarchyId(
                                    new HierarchyId(new HierarchyId(comparisonCheckCategorySpecMap.getHierarchyId()),
                                            categoryFieldInfo.getYamlFieldName(), comparisonName));
                        }

                        QualityCategoryModel comparisonCategoryModel = createCategoryModel(categoryFieldInfo,
                                configuredComparisonChecksCategory,
                                checkCategoriesSpec.getSchedulingGroup(),
                                runChecksTemplate,
                                tableSpec,
                                executionContext,
                                providerType,
                                checkCategoriesSpec.getCheckTarget(),
                                checkType,
                                checkTimeScale,
                                canManageChecks);
                        if (comparisonCategoryModel != null && comparisonCategoryModel.getChecks().size() > 0) {
                            checkContainerModel.getCategories().add(comparisonCategoryModel);
                        }
                    }
                }

                for (AbstractComparisonCheckCategorySpec configuredComparisonChecks : comparisonCheckCategorySpecMap.values()) {
                    if (tableSpec == null || (tableSpec.getTableComparisons() != null && tableSpec.getTableComparisons().get(configuredComparisonChecks.getComparisonName()) != null)) {
                        continue; // already added, we are adding only orphaned check configuration for reference table configurations no longer configured
                        // TODO: assign some boolean flag to the model to identify misconfigured (orphaned) checks, because they will fail to run anyway
                    }

                    if (!configuredComparisonChecks.hasAnyConfiguredChecks()) {
                        continue;  // abandoned configuration, no need to show it
                    }

                    QualityCategoryModel comparisonCategoryModel = createCategoryModel(categoryFieldInfo,
                            configuredComparisonChecks,
                            checkCategoriesSpec.getSchedulingGroup(),
                            runChecksTemplate,
                            tableSpec,
                            executionContext,
                            providerType,
                            checkCategoriesSpec.getCheckTarget(),
                            checkType,
                            checkTimeScale,
                            canManageChecks);
                    if (comparisonCategoryModel != null && comparisonCategoryModel.getChecks().size() > 0) {
                        checkContainerModel.getCategories().add(comparisonCategoryModel);
                    }
                }

                continue; // it is a comparison container, skipping
            }

            QualityCategoryModel categoryModel = createCategoryModel(categoryFieldInfo,
                    categoryFieldValue,
                    checkCategoriesSpec.getSchedulingGroup(),
                    runChecksTemplate,
                    tableSpec,
                    executionContext,
                    providerType,
                    checkCategoriesSpec.getCheckTarget(),
                    checkType,
                    checkTimeScale,
                    canManageChecks);
            if (categoryModel != null && categoryModel.getChecks().size() > 0) {
                checkContainerModel.getCategories().add(categoryModel);
            }
        }

        // All checks override schedule especially in cases when CheckSearchFilters are very specific.
        List<CheckModel> allChecksFlattened = checkContainerModel.getCategories().stream()
                .flatMap(checkCategory -> checkCategory.getChecks().stream())
                .collect(Collectors.toList());

        if (allChecksFlattened.stream().findAny().isPresent()
                && allChecksFlattened.stream().allMatch(
                        check -> check.getEffectiveSchedule() != null
        )) {
             // Info about schedule for the whole model is irrelevant.
            checkContainerModel.setEffectiveSchedule(null);
            checkContainerModel.setEffectiveScheduleEnabledStatus(ScheduleEnabledStatusModel.overridden_by_checks);
        }

        return checkContainerModel;
    }

    /**
     * Extracts the effective schedule and partitioning from the connection and table configuration.
     * Stores the information in the check container model.
     * @param checkCategoriesSpec Source check categories configuration.
     * @param connectionSpec Connection specification.
     * @param tableSpec Table specification.
     * @param targetCheckContainerModel Target check container model to update.
     */
    private void identifyEffectiveSchedulesAndPartitioning(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                                           ConnectionSpec connectionSpec,
                                                           TableSpec tableSpec,
                                                           CheckContainerModel targetCheckContainerModel) {
        targetCheckContainerModel.setPartitionByColumn(tableSpec.getTimestampColumns() != null ?
                tableSpec.getTimestampColumns().getPartitionByColumn() : null);

        EffectiveScheduleModel effectiveScheduleModel = getEffectiveScheduleModel(
                tableSpec.getSchedulesOverride(),
                checkCategoriesSpec.getSchedulingGroup(),
                EffectiveScheduleLevelModel.table_override
        );
        ScheduleEnabledStatusModel scheduleEnabledStatus = getScheduleEnabledStatus(
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
                    EffectiveScheduleLevelModel.connection
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
        targetCheckContainerModel.setEffectiveSchedule(effectiveScheduleModel);
        targetCheckContainerModel.setEffectiveScheduleEnabledStatus(scheduleEnabledStatus);
    }

    /**
     * Creates a simplistic model of every data quality check on table level or column level, divided into categories.
     *
     * @param checkCategoriesSpec Table or column level data quality checks container of type profiling, monitoring or partitioned check (for a specific timescale).
     * @param executionContext Check execution context with access to the check information.
     * @param providerType Provider type.
     * @param canManageChecks The user is an operator and can rul any operation.
     * @return Simplistic model of data quality checks' container.
     */
    @Override
    public CheckContainerListModel createBasicModel(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                                    ExecutionContext executionContext,
                                                    ProviderType providerType,
                                                    boolean canManageChecks) {
        CheckContainerListModel checkContainerListModel = new CheckContainerListModel();
        checkContainerListModel.setCanEdit(canManageChecks);
        checkContainerListModel.setCanRunChecks(canManageChecks);
        checkContainerListModel.setCanDeleteData(canManageChecks);

        ClassInfo checkCategoriesClassInfo = reflectionService.getClassInfoForClass(checkCategoriesSpec.getClass());
        List<FieldInfo> categoryFields = this.getFilteredFieldInfo(checkCategoriesClassInfo, Optional.empty());
        CheckType checkType = checkCategoriesSpec.getCheckType();
        CheckTimeScale checkTimeScale = checkCategoriesSpec.getCheckTimeScale();

        for (FieldInfo categoryFieldInfo : categoryFields) {
            if (categoryFieldInfo.getDataType() != ParameterDataType.object_type) {
                continue;
            }

            Object checkCategoryParentNode = categoryFieldInfo.getFieldValueOrNewObject(checkCategoriesSpec);
            if (checkCategoryParentNode instanceof AbstractComparisonCheckCategorySpecMap<?>) {
                continue; // not supported
            }

            if (checkCategoryParentNode instanceof CustomCheckSpecMap) {
                continue; // custom checks not returned in the basic model yet
            }

            ClassInfo checkListClassInfo = reflectionService.getClassInfoForClass(checkCategoryParentNode.getClass());
            List<FieldInfo> checksFields = this.getFilteredFieldInfo(checkListClassInfo, Optional.empty());

            for (FieldInfo checkFieldInfo : checksFields) {
                boolean checkIsConfigured = checkFieldInfo.getFieldValue(checkCategoryParentNode) != null;
                AbstractCheckSpec<?,?,?,?> checkSpecObject = (AbstractCheckSpec<?,?,?,?>)checkFieldInfo.getFieldValueOrNewObject(checkCategoryParentNode);

                CheckListModel checkModel = createCheckBasicModel(checkFieldInfo, checkSpecObject, executionContext, providerType, checkType, checkTimeScale);
                checkModel.setConfigured(checkIsConfigured);
                checkModel.setCheckCategory(categoryFieldInfo.getYamlFieldName());
                checkContainerListModel.getChecks().add(checkModel);
            }
        }

        checkContainerListModel.getChecks().sort(CheckListModel::compareTo);
        return checkContainerListModel;
    }

    /**
     * Creates a model for all data quality checks for one category.
     * @param categoryFieldInfo       Field info for the category field.
     * @param checkCategoryParentNode The current category specification object instance (an object that has fields for all data quality checks in the category).
     * @param scheduleGroup           Scheduling group relevant to this check.
     * @param runChecksTemplate       Run check job template, acting as a filtering template.
     * @param tableSpec               Table specification with the configuration of the parent table.
     * @param executionContext        Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType            Provider type from the parent connection.
     * @param checkTarget             Check target.
     * @param checkType               Check type (profiling, monitoring, ...).
     * @param checkTimeScale          Check time scale: null for profiling, daily/monthly for others that apply the date truncation.
     * @return Model for a category with all quality checks, filtered by runChecksTemplate.
     */
    protected QualityCategoryModel createCategoryModel(FieldInfo categoryFieldInfo,
                                                       Object checkCategoryParentNode,
                                                       CheckRunScheduleGroup scheduleGroup,
                                                       CheckSearchFilters runChecksTemplate,
                                                       TableSpec tableSpec,
                                                       ExecutionContext executionContext,
                                                       ProviderType providerType,
                                                       CheckTarget checkTarget,
                                                       CheckType checkType,
                                                       CheckTimeScale checkTimeScale,
                                                       boolean isOperator) {
        QualityCategoryModel categoryModel = new QualityCategoryModel();
        CheckSearchFilters runChecksCategoryTemplate = runChecksTemplate != null ? runChecksTemplate.clone() : null;

        if (checkCategoryParentNode instanceof AbstractComparisonCheckCategorySpec) {
            AbstractComparisonCheckCategorySpec comparisonCheckCategorySpec = (AbstractComparisonCheckCategorySpec)checkCategoryParentNode;
            String comparisonName = comparisonCheckCategorySpec.getComparisonName();
            categoryModel.setCategory(categoryFieldInfo.getYamlFieldName() + "/" + comparisonName);
            categoryModel.setComparisonName(comparisonName);
            if (comparisonCheckCategorySpec instanceof AbstractColumnComparisonCheckCategorySpec) {
                AbstractColumnComparisonCheckCategorySpec columnComparisonCheckCategorySpec = (AbstractColumnComparisonCheckCategorySpec)comparisonCheckCategorySpec;
                categoryModel.setCompareToColumn(columnComparisonCheckCategorySpec.getReferenceColumn());
            }
            runChecksCategoryTemplate.setTableComparisonName(comparisonName);
        } else {
            categoryModel.setCategory(categoryFieldInfo.getYamlFieldName());
        }

        categoryModel.setHelpText(categoryFieldInfo.getHelpText());
        if (runChecksCategoryTemplate != null) {
            runChecksCategoryTemplate.setCheckCategory(categoryFieldInfo.getYamlFieldName());
        }
        categoryModel.setRunChecksJobTemplate(runChecksCategoryTemplate);
        categoryModel.setDataCleanJobTemplate(
                DeleteStoredDataQueueJobParameters.fromCheckSearchFilters(
                        runChecksCategoryTemplate
                )
        );

        if (checkCategoryParentNode instanceof AbstractCheckCategorySpec) {
            ClassInfo checkListClassInfo = reflectionService.getClassInfoForClass(checkCategoryParentNode.getClass());
            Optional<String> checkNameFilter = Optional.ofNullable(categoryModel.getRunChecksJobTemplate()).map(ct -> ct.getCheckName());
            List<FieldInfo> checksFields = this.getFilteredFieldInfo(checkListClassInfo, checkNameFilter);

            for (FieldInfo checkFieldInfo : checksFields) {
                if (checkFieldInfo.getDataType() != ParameterDataType.object_type) {
                    continue;
                }

                AbstractSpec checkSpecObjectNullable = (AbstractSpec) checkFieldInfo.getFieldValue(checkCategoryParentNode);
                AbstractSpec checkSpecObject = checkSpecObjectNullable != null ? checkSpecObjectNullable :
                        (AbstractSpec) checkFieldInfo.getFieldValueOrNewObject(checkCategoryParentNode);
                AbstractCheckSpec<?, ?, ?, ?> checkFieldValue = (AbstractCheckSpec<?, ?, ?, ?>) checkSpecObject;
                CheckModel checkModel = createCheckModel(checkFieldInfo,
                        null,
                        checkFieldValue,
                        scheduleGroup,
                        runChecksCategoryTemplate,
                        tableSpec,
                        executionContext,
                        providerType,
                        checkTarget,
                        checkType,
                        checkTimeScale,
                        isOperator);
                if (checkModel == null) {
                    continue;
                }

                checkModel.setConfigured(checkSpecObjectNullable != null);
                categoryModel.getChecks().add(checkModel);
            }
        }

        CustomCheckSpecMap customCheckSpecMap = null;
        if (checkCategoryParentNode instanceof CustomCheckSpecMap) {
            customCheckSpecMap = (CustomCheckSpecMap)checkCategoryParentNode;
        } else if (checkCategoryParentNode instanceof AbstractCheckCategorySpec) {
            AbstractCheckCategorySpec abstractCheckCategorySpec = (AbstractCheckCategorySpec)checkCategoryParentNode;
            customCheckSpecMap = abstractCheckCategorySpec.getCustomChecks();
            if (customCheckSpecMap == null) {
                customCheckSpecMap = new CustomCheckSpecMap();
            }
        }

        if (customCheckSpecMap != null) {
            addCustomChecksToCategoryModel(customCheckSpecMap,
                    categoryModel,
                    scheduleGroup,
                    tableSpec,
                    executionContext,
                    providerType,
                    checkTarget,
                    checkType,
                    checkTimeScale,
                    isOperator);
        }

        return categoryModel;
    }

    /**
     * Adds the models of custom checks to the category model, showing these checks together with other checks.
     * @param customCheckSpecMap Custom check specification map.
     * @param targetCategoryModel Target category model to add checks.
     * @param scheduleGroup Scheduling group.
     * @param tableSpec Table specification.
     * @param executionContext Execution context.
     * @param providerType Provider type.
     * @param checkTarget Check target.
     * @param checkType Check type.
     * @param checkTimeScale Check time scale.
     * @param isOperator The current user is the operator and can manage the check.
     */
    protected void addCustomChecksToCategoryModel(CustomCheckSpecMap customCheckSpecMap,
                                                  QualityCategoryModel targetCategoryModel,
                                                  CheckRunScheduleGroup scheduleGroup,
                                                  TableSpec tableSpec,
                                                  ExecutionContext executionContext,
                                                  ProviderType providerType,
                                                  CheckTarget checkTarget,
                                                  CheckType checkType,
                                                  CheckTimeScale checkTimeScale,
                                                  boolean isOperator) {
        String category = targetCategoryModel.getCategory();
        if (executionContext == null || executionContext.getUserHomeContext() == null) {
            return;
        }

        CheckDefinitionList checkDefinitionList = executionContext.getUserHomeContext().getUserHome().getChecks();
        Collection<CheckDefinitionSpec> customChecksInCategory = checkDefinitionList.getChecksAtLevel(checkTarget, checkType, checkTimeScale, category);
        if (customChecksInCategory.isEmpty()) {
            return;
        }

        for (CheckDefinitionSpec customCheckDefinitionSpec : customChecksInCategory) {
            String checkName = customCheckDefinitionSpec.getCheckName();
            CustomCheckSpec customCheckSpec = customCheckSpecMap.get(checkName);
            boolean checkIsConfigured = false;
            if (customCheckSpec == null) {
                customCheckSpec = new CustomCheckSpec();
            } else {
                checkIsConfigured = true;
            }

            CheckModel customCheckModel = createCheckModel(null,
                    customCheckDefinitionSpec,
                    customCheckSpec,
                    scheduleGroup,
                    targetCategoryModel.getRunChecksJobTemplate(),
                    tableSpec,
                    executionContext,
                    providerType,
                    checkTarget,
                    checkType,
                    checkTimeScale,
                    isOperator);

            if (customCheckModel == null) {
                continue;
            }

            customCheckModel.setConfigured(checkIsConfigured);
            targetCategoryModel.getChecks().add(customCheckModel);
        }
    }

    /**
     * Creates a model for a single data quality check.
     * @param checkFieldInfo            Reflection info of the field in the parent object that stores the check specification field value.
     * @param customCheckDefinitionSpec Check definition specification for custom checks. When it is given, the <code>checkFieldInfo</code> parameter is null and the check specification is used instead.
     * @param checkSpec                 Check specification instance retrieved from the object.
     * @param scheduleGroup             Scheduling group relevant to this check.
     * @param runChecksCategoryTemplate "run check" job configuration for the parent category, used to create templates for each check.
     * @param tableSpec                 Table specification with the configuration of the parent table.
     * @param executionContext          Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType              Provider type from the parent connection.
     * @param checkTarget               Check target.
     * @param checkType                 Check type (profiling, monitoring, ...).
     * @param checkTimeScale            Check time scale: null for profiling, daily/monthly for others that apply the date truncation.
     * @return Check model.
     */
    @Override
    public CheckModel createCheckModel(FieldInfo checkFieldInfo,
                                       CheckDefinitionSpec customCheckDefinitionSpec,
                                       AbstractCheckSpec<?,?,?,?> checkSpec,
                                       CheckRunScheduleGroup scheduleGroup,
                                       CheckSearchFilters runChecksCategoryTemplate,
                                       TableSpec tableSpec,
                                       ExecutionContext executionContext,
                                       ProviderType providerType,
                                       CheckTarget checkTarget,
                                       CheckType checkType,
                                       CheckTimeScale checkTimeScale,
                                       boolean canManageChecks) {
        CheckModel checkModel = new CheckModel();
        checkModel.setCanEdit(canManageChecks);
        checkModel.setCanRunChecks(canManageChecks);
        checkModel.setCanDeleteData(canManageChecks);

        ClassInfo checkClassInfo = reflectionService.getClassInfoForClass(checkSpec.getClass());
        FieldInfo parametersFieldInfo = checkClassInfo.getField("parameters");
        AbstractSensorParametersSpec parametersSpec = (AbstractSensorParametersSpec)parametersFieldInfo.getFieldValueOrNewObject(checkSpec);
        checkModel.setFilter(parametersSpec.getFilter());
        String sensorDefinitionName = customCheckDefinitionSpec == null ? parametersSpec.getSensorDefinitionName() : customCheckDefinitionSpec.getSensorName();
        checkModel.setSensorName(sensorDefinitionName);
        checkModel.setSensorParametersSpec(parametersSpec);
        SensorDefinitionSpec sensorDefinitionSpec = null;

        if (executionContext != null && providerType != null) {
            SensorDefinitionFindResult providerSensorDefinition = this.sensorDefinitionFindService.findProviderSensorDefinition(
                    executionContext, sensorDefinitionName, providerType);

            if (providerSensorDefinition == null) {
                return null; // skip this check
            }

            sensorDefinitionSpec = providerSensorDefinition.getSensorDefinitionSpec();

            ProviderSensorDefinitionSpec providerSensorDefinitionSpec = providerSensorDefinition.getProviderSensorDefinitionSpec();
            if (providerSensorDefinitionSpec == null) {
                return null; // skip this check
            }

            if (providerSensorDefinitionSpec.getSupportsPartitionedChecks() != null &&
                    !providerSensorDefinitionSpec.getSupportsPartitionedChecks() && checkType == CheckType.partitioned) {
                return null; // skip this check
            }

            if (checkType == CheckType.partitioned && Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getPartitionByColumn())) {
                checkModel.pushError(CheckConfigurationRequirementsError.MISSING_PARTITION_BY_COLUMN);
            }

            checkModel.setSupportsGrouping(providerSensorDefinitionSpec.getSupportsGrouping() == null || providerSensorDefinitionSpec.getSupportsGrouping());

            if (sensorDefinitionSpec.isRequiresEventTimestamp() &&
                    Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getEventTimestampColumn())) {
                if (sensorDefinitionSpec.isRequiresIngestionTimestamp() &&
                        Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getIngestionTimestampColumn())) {
                    checkModel.pushError(CheckConfigurationRequirementsError.MISSING_EVENT_AND_INGESTION_COLUMNS);
                } else {
                    checkModel.pushError(CheckConfigurationRequirementsError.MISSING_EVENT_TIMESTAMP_COLUMN);
                }
            } else {
                if (sensorDefinitionSpec.isRequiresIngestionTimestamp() &&
                        Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getIngestionTimestampColumn())) {
                    checkModel.pushError(CheckConfigurationRequirementsError.MISSING_INGESTION_TIMESTAMP_COLUMN);
                }
            }
        }
        String checkName = checkFieldInfo != null ? checkFieldInfo.getYamlFieldName() : customCheckDefinitionSpec.getCheckName();
        checkModel.setCheckName(checkName);
        checkModel.setHelpText(checkFieldInfo != null ? checkFieldInfo.getHelpText() : customCheckDefinitionSpec.getHelpText());

        if (runChecksCategoryTemplate != null) {
            CheckSearchFilters runOneCheckTemplate = runChecksCategoryTemplate.clone();
            runOneCheckTemplate.setCheckName(checkName);
            checkModel.setRunChecksJobTemplate(runOneCheckTemplate);

            DeleteStoredDataQueueJobParameters dataCleanJobTemplate = DeleteStoredDataQueueJobParameters.fromCheckSearchFilters(runOneCheckTemplate);
            dataCleanJobTemplate.setDataGroupTag(checkSpec.getDataGrouping());
            checkModel.setDataCleanJobTemplate(dataCleanJobTemplate);
        }

        if (checkTarget != null && this.similarCheckCache != null && customCheckDefinitionSpec == null) {
            checkModel.setSimilarChecks(this.similarCheckCache.findSimilarChecksTo(checkTarget, checkName));
        }

        MonitoringScheduleSpec scheduleOverride = checkSpec.getScheduleOverride();
        checkModel.setScheduleOverride(scheduleOverride);
        if (scheduleOverride != null && !scheduleOverride.isDefault()) {
                checkModel.setEffectiveSchedule(
                        EffectiveScheduleModel.fromMonitoringScheduleSpec(
                                scheduleOverride,
                                scheduleGroup,
                                EffectiveScheduleLevelModel.check_override,
                                this::safeGetTimeOfNextExecution
                        )
                );
        }

        checkModel.setScheduleEnabledStatus(getScheduleEnabledStatus(scheduleOverride));
        checkModel.setComments(checkSpec.getComments());
        checkModel.setDisabled(checkSpec.isDisabled());
        checkModel.setExcludeFromKpi(checkSpec.isExcludeFromKpi());
        checkModel.setQualityDimension(checkSpec.getEffectiveDataQualityDimension());
        checkModel.setIncludeInSla(checkSpec.isIncludeInSla());
        checkModel.setDataGroupingConfiguration(checkSpec.getDataGrouping());
        checkModel.setCheckSpec(checkSpec);
        checkModel.setCheckTarget(CheckTargetModel.fromCheckTarget(checkTarget));

        if (customCheckDefinitionSpec == null) {
            List<FieldModel> fieldsForParameterSpec = createFieldsForSensorParameters(parametersSpec);
            checkModel.setSensorParameters(fieldsForParameterSpec);

            RuleThresholdsModel ruleModel = createRuleThresholdsModel(checkSpec);
            checkModel.setRule(ruleModel);
        } else {
            if (sensorDefinitionSpec != null) {
                CustomSensorParametersSpec customSensorParametersSpec = (CustomSensorParametersSpec) parametersSpec;
                List<FieldModel> customFieldsForParameterSpec = createFieldsForSensorParametersCustomCheck(
                        customSensorParametersSpec, sensorDefinitionSpec.getFields());
                checkModel.setSensorParameters(customFieldsForParameterSpec);

                RuleThresholdsModel ruleModel = createRuleThresholdsModelCustomCheck(
                        (CustomCheckSpec) checkSpec, customCheckDefinitionSpec, executionContext);
                checkModel.setRule(ruleModel);
            }
        }

        if (executionContext != null && executionContext.getUserHomeContext() != null) {
            String ruleName = checkModel.getRule().getWarning() != null ? checkModel.getRule().getWarning().getRuleName() : null;

            if (ruleName != null) {
                RuleDefinitionFindResult ruleFindResult = this.ruleDefinitionFindService.findRule(executionContext, ruleName);
                if (ruleFindResult != null && ruleFindResult.getRuleDefinitionSpec() != null) {
                    RuleTimeWindowSettingsSpec ruleTimeWindow = ruleFindResult.getRuleDefinitionSpec().getTimeWindow();
                    if (ruleTimeWindow != null) {
                        if (checkType == CheckType.profiling) {
                            if (tableSpec.getProfilingChecks().getResultTruncation() == null ||
                                    tableSpec.getProfilingChecks().getResultTruncation() == ProfilingTimePeriod.one_per_week ||
                                    tableSpec.getProfilingChecks().getResultTruncation() == ProfilingTimePeriod.one_per_month) {
                                checkModel.pushError(CheckConfigurationRequirementsError.PROFILING_CHECKS_RESULT_TRUNCATION_TOO_COARSE);
                            }
                        } else if (ruleTimeWindow.getHistoricDataPointGrouping() != null && checkTimeScale != null) {
                            TimePeriodGradient ruleTimePeriodGradient =
                                    ruleTimeWindow.getHistoricDataPointGrouping() == HistoricDataPointsGrouping.last_n_readouts
                                            ? checkTimeScale.toTimeSeriesGradient() : ruleTimeWindow.getHistoricDataPointGrouping().toTimePeriodGradient();
                            TimePeriodGradient checkTypeGradient = checkTimeScale.toTimeSeriesGradient();

                            if (checkTypeGradient.getRank() < ruleTimePeriodGradient.getRank()) {
                                StringBuilder errorBuilder = new StringBuilder();
                                errorBuilder.append("This data quality check cannot be correctly validated by a rule because the rule requires a time series that has a scale at least one value per ");
                                errorBuilder.append(ruleTimePeriodGradient);
                                errorBuilder.append(", but this type of check collects the sensor readout data (data quality measures) ");
                                errorBuilder.append(checkTimeScale);
                                errorBuilder.append(". Please reconfigure the custom check in the configuration section using a different rule that has a matching time scale requirement for historic data from the time series.");
                                checkModel.pushError(errorBuilder.toString());
                            }
                        }
                    }
                }
            }
        }

        return checkModel;
    }

    /**
     * Creates a simplistic model for a single data quality check.
     * @param checkFieldInfo Reflection info of the field in the parent object that stores the check specification field value.
     * @param checkSpec Check specification.
     * @param executionContext Check execution context.
     * @param providerType Provider type from the parent connection.
     * @param checkType Check type (profiling, monitoring, ...).
     * @param checkTimeScale Check time scale: null for profiling, daily/monthly for others that apply the date truncation.
     * @return Check basic model.
     */
    protected CheckListModel createCheckBasicModel(FieldInfo checkFieldInfo,
                                                   AbstractCheckSpec<?,?,?,?> checkSpec,
                                                   ExecutionContext executionContext,
                                                   ProviderType providerType,
                                                   CheckType checkType,
                                                   CheckTimeScale checkTimeScale) {
        CheckListModel checkModel = new CheckListModel();
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

            if (providerSensorDefinitionSpec.getSupportsPartitionedChecks() != null &&
                    !providerSensorDefinitionSpec.getSupportsPartitionedChecks() && checkType == CheckType.partitioned) {
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
    protected RuleThresholdsModel createRuleThresholdsModel(AbstractCheckSpec<?,?,?,?> checkSpec) {
        RuleThresholdsModel thresholdsModel = new RuleThresholdsModel();

        ClassInfo abstractCheckClassInfo = reflectionService.getClassInfoForClass(checkSpec.getClass());
        FieldInfo warningFieldInfo = abstractCheckClassInfo.getField("warning");
        RuleParametersModel warningSeverityParametersModel = createRuleParametersModel(warningFieldInfo, checkSpec);
        thresholdsModel.setWarning(warningSeverityParametersModel);

        FieldInfo errorFieldInfo = abstractCheckClassInfo.getField("error");
        RuleParametersModel errorSeverityParametersModel = createRuleParametersModel(errorFieldInfo, checkSpec);
        thresholdsModel.setError(errorSeverityParametersModel);

        FieldInfo fatalFieldInfo = abstractCheckClassInfo.getField("fatal");
        RuleParametersModel fatalSeverityParametersModel = createRuleParametersModel(fatalFieldInfo, checkSpec);
        thresholdsModel.setFatal(fatalSeverityParametersModel);

        return thresholdsModel;
    }

    /**
     * Create the rules model for a custom check.
     * @param checkSpec Check specification object.
     * @param customCheckDefinitionSpec Custom check definition.
     * @param executionContext Execution context with access to the dqo user home and dqo system home.
     * @return Rule thresholds model for the custom check.
     */
    protected RuleThresholdsModel createRuleThresholdsModelCustomCheck(CustomCheckSpec checkSpec,
                                                                       CheckDefinitionSpec customCheckDefinitionSpec,
                                                                       ExecutionContext executionContext) {
        RuleThresholdsModel thresholdsModel = new RuleThresholdsModel();
        if (Strings.isNullOrEmpty(customCheckDefinitionSpec.getRuleName())) {
            return thresholdsModel;
        }

        RuleDefinitionFindResult ruleDefinitionFindResult = this.ruleDefinitionFindService.findRule(
                executionContext, customCheckDefinitionSpec.getRuleName());
        if (ruleDefinitionFindResult == null) {
            // rule not found
            log.warn("Rule " + customCheckDefinitionSpec.getRuleName() + " referenced by a custom check " + customCheckDefinitionSpec.getFullCheckName() +
                    " was not found.");
            return thresholdsModel;
        }

        CustomRuleParametersSpec warningRuleParametersSpec = checkSpec.getWarning();
        thresholdsModel.setWarning(createRuleParametersModelCustomCheck(warningRuleParametersSpec, ruleDefinitionFindResult.getRuleDefinitionSpec()));

        CustomRuleParametersSpec errorRuleParametersSpec = checkSpec.getError();
        thresholdsModel.setError(createRuleParametersModelCustomCheck(errorRuleParametersSpec, ruleDefinitionFindResult.getRuleDefinitionSpec()));

        CustomRuleParametersSpec fatalRuleParametersSpec = checkSpec.getFatal();
        thresholdsModel.setFatal(createRuleParametersModelCustomCheck(fatalRuleParametersSpec, ruleDefinitionFindResult.getRuleDefinitionSpec()));

        return thresholdsModel;
    }

    /**
     * Creates a rule parameters model for a custom check.
     * @param fatalRuleParametersSpec Rule parameters object or null.
     * @param ruleDefinitionSpec Rule definition specification with the list of fields.
     * @return Rule parameters model.
     */
    protected RuleParametersModel createRuleParametersModelCustomCheck(
            CustomRuleParametersSpec fatalRuleParametersSpec, RuleDefinitionSpec ruleDefinitionSpec) {
        RuleParametersModel parametersModel = new RuleParametersModel();

        parametersModel.setConfigured(fatalRuleParametersSpec != null);
        parametersModel.setRuleName(ruleDefinitionSpec.getRuleName());
        parametersModel.setRuleParametersSpec(fatalRuleParametersSpec);

        List<FieldModel> fieldModels = createFieldsForRuleParametersCustomCheck(fatalRuleParametersSpec, ruleDefinitionSpec.getFields());
        parametersModel.setRuleParameters(fieldModels);

        return parametersModel;
    }

    /**
     * Creates a list of fields used to edit a single rule severity level.
     * @param severityFieldInfo Reflection info of the field - used to retrieve the help text.
     * @param abstractCheckSpec Rule thresholds specification object (parent object).
     * @return Rule parameters.
     */
    public RuleParametersModel createRuleParametersModel(FieldInfo severityFieldInfo, AbstractCheckSpec<?,?,?,?> abstractCheckSpec) {
        RuleParametersModel parametersModel = new RuleParametersModel();
        AbstractRuleParametersSpec parametersValueNullable = (AbstractRuleParametersSpec)severityFieldInfo.getFieldValue(abstractCheckSpec);
        AbstractRuleParametersSpec parametersSpecNotNull = parametersValueNullable != null ? parametersValueNullable :
                (AbstractRuleParametersSpec)severityFieldInfo.getFieldValueOrNewObject(abstractCheckSpec);

        parametersModel.setConfigured(parametersValueNullable != null);
        parametersModel.setRuleName(parametersSpecNotNull.getRuleDefinitionName());
        parametersModel.setRuleParametersSpec(parametersSpecNotNull);

        List<FieldModel> fieldModels = createFieldsForRuleParameters(parametersSpecNotNull);
        parametersModel.setRuleParameters(fieldModels);

        return parametersModel;
    }

    /**
     * Creates a list of fields to edit all values in the rule parameters specification.
     * @param ruleParametersSpec Rule parameters specification.
     * @return List of fields for all rule parameter fields.
     */
    public List<FieldModel> createFieldsForRuleParameters(AbstractRuleParametersSpec ruleParametersSpec) {
        List<FieldModel> fieldModels = new ArrayList<>();
        ClassInfo parametersClassInfo = reflectionService.getClassInfoForClass(ruleParametersSpec.getClass());
        List<FieldInfo> fields = parametersClassInfo.getFields();
        for (FieldInfo parameterField : fields) {
            if (Objects.equals("disabled", parameterField.getClassFieldName())) {
                continue; // we could also filter out non-direct fields, but that would cancel inheritance
            }

            FieldModel fieldModel = createFieldModel(parameterField, ruleParametersSpec);
            fieldModels.add(fieldModel);
        }

        return fieldModels;
    }

    /**
     * Creates a list of fields to edit all values in the sensor parameters specification.
     * @param parametersSpec Sensor parameters specification.
     * @return List of fields for all sensor parameter fields.
     */
    public List<FieldModel> createFieldsForSensorParameters(AbstractSensorParametersSpec parametersSpec) {
        List<FieldModel> fieldModels = new ArrayList<>();
        ClassInfo parametersClassInfo = reflectionService.getClassInfoForClass(parametersSpec.getClass());
        List<FieldInfo> fields = parametersClassInfo.getFields();
        for (FieldInfo parameterField : fields) {
            if (Objects.equals("disabled", parameterField.getClassFieldName()) ||
                    Objects.equals("filter", parameterField.getClassFieldName())) {
                continue; // we could also filter out non-direct fields, but that would cancel inheritance
            }

            FieldModel fieldModel = createFieldModel(parameterField, parametersSpec);
            fieldModels.add(fieldModel);
        }

        return fieldModels;
    }

    /**
     * Creates a list of fields to edit all values in the sensor parameters specification.
     * @param parametersSpec Sensor parameters specification.
     * @param parameterDefinitionsListSpec List of custom parameters available on the check.
     * @return List of fields for all sensor parameter fields.
     */
    public List<FieldModel> createFieldsForSensorParametersCustomCheck(CustomSensorParametersSpec parametersSpec,
                                                                       ParameterDefinitionsListSpec parameterDefinitionsListSpec) {
        List<FieldModel> fieldModels = new ArrayList<>();
        if (parameterDefinitionsListSpec == null || parameterDefinitionsListSpec.isEmpty()) {
            return fieldModels;
        }

        for (ParameterDefinitionSpec parameterDefinitionSpec : parameterDefinitionsListSpec) {
            FieldModel fieldModel = createFieldModelCustomCheck(parameterDefinitionSpec, parametersSpec);
            fieldModels.add(fieldModel);
        }

        return fieldModels;
    }

    /**
     * Creates a list of fields to edit all values in the rule parameters specification.
     * @param parametersSpec Sensor parameters specification.
     * @param parameterDefinitionsListSpec List of custom parameters available on the rule.
     * @return List of fields for all rule parameter fields.
     */
    public List<FieldModel> createFieldsForRuleParametersCustomCheck(CustomRuleParametersSpec parametersSpec,
                                                                     ParameterDefinitionsListSpec parameterDefinitionsListSpec) {
        List<FieldModel> fieldModels = new ArrayList<>();
        if (parameterDefinitionsListSpec == null || parameterDefinitionsListSpec.isEmpty()) {
            return fieldModels;
        }

        for (ParameterDefinitionSpec parameterDefinitionSpec : parameterDefinitionsListSpec) {
            FieldModel fieldModel = createFieldModelCustomCheck(parameterDefinitionSpec, parametersSpec);
            fieldModels.add(fieldModel);
        }

        return fieldModels;
    }

    /**
     * Creates a field model for a single field in an object.
     * @param fieldInfo    Reflection field information about the field.
     * @param parentObject Parent object to retrieve the field value from.
     * @return A field model with properly typed field value.
     */
    public FieldModel createFieldModel(FieldInfo fieldInfo, Object parentObject) {
        Object fieldValue = fieldInfo.getFieldValue(parentObject);
        FieldModel fieldModel = new FieldModel();
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
                case datetime_type:
                    fieldModel.setDatetimeValue((LocalDateTime) fieldValue);
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
     * Creates a field model for a single field in an object for a custom check.
     * @param parameterDefinitionSpec    Reflection field information about the field.
     * @param parentObject Parent object to retrieve the field value from.
     * @return A field model with properly typed field value.
     */
    public FieldModel createFieldModelCustomCheck(ParameterDefinitionSpec parameterDefinitionSpec, CustomParametersSpecObject parentObject) {
        Object fieldValue = parentObject != null ? parentObject.getParameter(parameterDefinitionSpec.getFieldName()) : null;
        FieldModel fieldModel = new FieldModel();
        fieldModel.setDefinition(parameterDefinitionSpec);

        if (fieldValue != null) {
            try {
                fieldModel.setValue(fieldValue);
            }
            catch (Exception ex) {
                // invalid value, not matching the data type, clearing
                log.warn("Cannot set the value " + fieldValue + " as a custom check model for the parameter " + parameterDefinitionSpec.getFieldName());
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
                    .filter(fieldInfo -> !fieldInfo.getYamlFieldName().equals("custom_checks"))
                    .forEachOrdered(fields::add);
        }

        return fields;
    }

    /**
     * Gets the time of the next execution of <code>scheduleSpec</code>, if the {@link SchedulesUtilityService} is initialized.
     * @param scheduleSpec Schedule configuration containing a CRON expression with execution timetable.
     * @return Time of next execution of <code>scheduleSpec</code> if it's not disabled and the service is able to get it. Else null.
     */
    protected ZonedDateTime safeGetTimeOfNextExecution(MonitoringScheduleSpec scheduleSpec) {
        if (this.schedulesUtilityService == null || scheduleSpec == null || scheduleSpec.isDisabled()) {
            return null;
        }
        return this.schedulesUtilityService.getTimeOfNextExecution(scheduleSpec);
    }

    /**
     * Gets the {@link EffectiveScheduleModel} out of <code>schedulesSpec</code>, if the schedule is enabled, provided some other arguments.
     * @param schedulesSpec Schedule spec.
     * @param scheduleGroup Schedule group.
     * @param scheduleLevel Schedule level.
     * @return Effective model of the schedule configuration. If <code>scheduleSpec</code> is null or disabled, returns null.
     */
    protected EffectiveScheduleModel getEffectiveScheduleModel(MonitoringSchedulesSpec schedulesSpec,
                                                               CheckRunScheduleGroup scheduleGroup,
                                                               EffectiveScheduleLevelModel scheduleLevel) {
        MonitoringScheduleSpec scheduleSpec = schedulesSpec != null
                ? schedulesSpec.getScheduleForCheckSchedulingGroup(scheduleGroup)
                : null;

        if (scheduleSpec != null && !scheduleSpec.isDefault()) {
            return EffectiveScheduleModel.fromMonitoringScheduleSpec(
                    scheduleSpec,
                    scheduleGroup,
                    scheduleLevel,
                    this::safeGetTimeOfNextExecution
            );
        }
        return null;
    }

    /**
     * Gets the relevant {@link ScheduleEnabledStatusModel} from <code>scheduleSpec</code>
     * @param scheduleSpec Schedule configuration for which to get activation status.
     * @return {@link ScheduleEnabledStatusModel} indicating the activation status of <code>scheduleSpec</code>.
     */
    protected ScheduleEnabledStatusModel getScheduleEnabledStatus(MonitoringScheduleSpec scheduleSpec) {
        if (scheduleSpec != null) {
            if (scheduleSpec.isDisabled()) {
                return ScheduleEnabledStatusModel.disabled;
            }
            else {
                return ScheduleEnabledStatusModel.enabled;
            }
        }
        else {
            return ScheduleEnabledStatusModel.not_configured;
        }
    }
}
