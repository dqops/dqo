/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.mapping;

import com.dqops.BaseTest;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.scheduler.quartz.*;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableSpecObjectMother;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.basicmodels.CheckContainerListModel;
import com.dqops.services.check.mapping.basicmodels.CheckListModel;
import com.dqops.services.check.mapping.models.QualityCategoryModel;
import com.dqops.services.check.mapping.utils.CheckContainerListModelUtility;
import com.dqops.services.check.matching.SimilarCheckCacheImpl;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class SpecToModelCheckMappingServiceImplTests extends BaseTest {
    private SpecToModelCheckMappingServiceImpl sut;
    private ConnectionSpec bigQueryConnectionSpec;
    private TableSpec tableSpec;
    private ExecutionContext executionContext;

    @BeforeEach
    void setUp() {
        DefaultTimeZoneProvider defaultTimeZoneProvider = DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider();
        TriggerFactory triggerFactory = new TriggerFactoryImpl(
                new JobDataMapAdapterImpl(JsonSerializerObjectMother.getDefault(), DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration()),
                defaultTimeZoneProvider);
        
        SchedulesUtilityService schedulesUtilityService = new SchedulesUtilityServiceImpl(
                triggerFactory,
                defaultTimeZoneProvider);

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SensorDefinitionFindServiceImpl sensorDefinitionFindService = new SensorDefinitionFindServiceImpl();
        RuleDefinitionFindServiceImpl ruleDefinitionFindService = new RuleDefinitionFindServiceImpl();
        SimilarCheckCacheImpl similarCheckCache = new SimilarCheckCacheImpl(reflectionService,
                sensorDefinitionFindService, ruleDefinitionFindService, dqoHomeContextFactory);
        this.sut = new SpecToModelCheckMappingServiceImpl(
                reflectionService,
                sensorDefinitionFindService,
                ruleDefinitionFindService,
                schedulesUtilityService,
                similarCheckCache);
        
        this.bigQueryConnectionSpec = BigQueryConnectionSpecObjectMother.create();
        this.tableSpec = TableSpecObjectMother.create("public", "tab1");
        this.tableSpec.setDefaultDataGroupingConfiguration(new DataGroupingConfigurationSpec());
        this.executionContext = new ExecutionContext(null, DqoHomeContextObjectMother.getRealDqoHomeContext());
    }

    @Test
    void createUiModel_whenEmptyTableChecksModelGiven_thenCreatesUiModel() {
        TableProfilingCheckCategoriesSpec tableCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery, true);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(7, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenTableChecksProfilingModelGivenAndOneReferenceTableWithNoChecks_thenCreatesUiModel() {
        TableProfilingCheckCategoriesSpec tableCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
        this.tableSpec.getTableComparisons().put("comparison1", new TableComparisonConfigurationSpec());
        CheckContainerModel uiModel = this.sut.createModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery, true);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(8, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenEmptyColumnChecksModelGiven_thenCreatesUiModel() {
        ColumnProfilingCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(columnCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery, true);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(17, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenEmptyColumnChecksModelGivenAndGeneratingForOnlyOneCheck_thenCreatesUiModel() {
        ColumnProfilingCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        checkSearchFilters.setCheckCategory("text");
        checkSearchFilters.setCheckName("profile_text_max_length");
        CheckContainerModel uiModel = this.sut.createModel(columnCheckCategoriesSpec, checkSearchFilters,
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery, true);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(1, uiModel.getCategories().size());
        QualityCategoryModel categoryModel = uiModel.getCategories().get(0);
        Assertions.assertEquals("text", categoryModel.getCategory());
        Assertions.assertEquals(1, categoryModel.getChecks().size());
        CheckModel checkModel = categoryModel.getChecks().get(0);
        Assertions.assertEquals("profile_text_max_length", checkModel.getCheckName());
        Assertions.assertNotNull(checkModel.getRule().getWarning());
        Assertions.assertNotNull(checkModel.getRule().getError());
        Assertions.assertNotNull(checkModel.getRule().getFatal());
        Assertions.assertEquals(2, checkModel.getRule().getError().getRuleParameters().size());
    }

    private Map.Entry<List<String>, List<String>> extractCheckNamesFromUIModels(
            CheckContainerModel uiModel,
            CheckContainerListModel uiBasicModel) {

        List<String> checksModel =
                uiModel.getCategories().stream()
                        .flatMap(
                                uiQualityCategoryModel ->
                                        uiQualityCategoryModel.getChecks()
                                                .stream()
                                                .map(CheckModel::getCheckName)
                        ).sorted().collect(Collectors.toList());

        List<String> checksBasicModel =
                uiBasicModel.getChecks().stream()
                        .map(CheckListModel::getCheckName)
                        .sorted().collect(Collectors.toList());

        return new AbstractMap.SimpleEntry<>(checksModel, checksBasicModel);
    }

    @Test
    void createUiBasicModel_whenEmptyTableChecksModelGiven_thenCreatesUiBasicModel() {
        TableProfilingCheckCategoriesSpec tableCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery, true);
        CheckContainerListModel uiBasicModel = this.sut.createBasicModel(tableCheckCategoriesSpec, this.executionContext, ProviderType.bigquery, true);

        Assertions.assertNotNull(uiBasicModel);
        Assertions.assertEquals(6, CheckContainerListModelUtility.getCheckCategoryNames(uiBasicModel).size());

        Map.Entry<List<String>, List<String>> names = extractCheckNamesFromUIModels(uiModel, uiBasicModel);
        HashSet<String> allCheckNames = new HashSet<>(names.getKey());

        names.getValue().forEach(basicCheckName -> {
            Assertions.assertTrue(allCheckNames.contains(basicCheckName));
        });
    }

    @Test
    void createUiBasicModel_whenEmptyColumnChecksModelGiven_thenCreatesUiBasicModel() {
        ColumnProfilingCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(columnCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery, true);
        CheckContainerListModel uiBasicModel = this.sut.createBasicModel(columnCheckCategoriesSpec, this.executionContext, ProviderType.bigquery, true);

        Assertions.assertNotNull(uiBasicModel);
        Assertions.assertEquals(17, CheckContainerListModelUtility.getCheckCategoryNames(uiBasicModel).size());

        Map.Entry<List<String>, List<String>> names = extractCheckNamesFromUIModels(uiModel, uiBasicModel);
        HashSet<String> allCheckNames = new HashSet<>(names.getKey());

        names.getValue().forEach(basicCheckName -> {
            Assertions.assertTrue(allCheckNames.contains(basicCheckName));
        });
    }

    @Test
    void createUiModel_whenDataObservabilityProfilingTableConfigurationGiven_thenCreatesUiModel() {
        TableProfilingCheckCategoriesSpec defaultProfilingChecks = new TableProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(defaultProfilingChecks, null,
                null, null, this.executionContext, null, true);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(7, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenDataObservabilityProfilingColumnConfigurationGiven_thenCreatesUiModel() {
        ColumnProfilingCheckCategoriesSpec defaultProfilingChecks = new ColumnProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(defaultProfilingChecks, null,
                null, null, this.executionContext, null, true);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(17, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenDataObservabilityDailyMonitoringTableConfigurationGiven_thenCreatesUiModel() {
        TableDailyMonitoringCheckCategoriesSpec defaultMonitoringChecks = new TableDailyMonitoringCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(defaultMonitoringChecks, null,
                null, null, this.executionContext, null, true);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(7, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenDataObservabilityDailyMonitoringColumnConfigurationGiven_thenCreatesUiModel() {
        ColumnDailyMonitoringCheckCategoriesSpec defaultMonitoringChecks = new ColumnDailyMonitoringCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(defaultMonitoringChecks, null,
                null, null, this.executionContext, null, true);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(17, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenDataObservabilityMonthlyMonitoringTableConfigurationGiven_thenCreatesUiModel() {
        TableMonthlyMonitoringCheckCategoriesSpec defaultMonitoringChecks = new TableMonthlyMonitoringCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(defaultMonitoringChecks, null,
                null, null, this.executionContext, null, true);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(7, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenDataObservabilityMonthlyMonitoringColumnConfigurationGiven_thenCreatesUiModel() {
        ColumnMonthlyMonitoringCheckCategoriesSpec defaultMonitoringChecks = new ColumnMonthlyMonitoringCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(defaultMonitoringChecks, null,
                null, null, this.executionContext, null, true);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(16, uiModel.getCategories().size());
    }
}
