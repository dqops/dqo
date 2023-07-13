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

import com.dqops.BaseTest;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import com.dqops.core.scheduler.quartz.*;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.comparisons.ReferenceTableSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableSpecObjectMother;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.basicmodels.CheckContainerBasicModel;
import com.dqops.services.check.mapping.basicmodels.CheckBasicModel;
import com.dqops.services.check.mapping.utils.CheckContainerBasicModelUtility;
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
                new JobDataMapAdapterImpl(JsonSerializerObjectMother.getDefault()),
                defaultTimeZoneProvider);
        
        SchedulesUtilityService schedulesUtilityService = new SchedulesUtilityServiceImpl(
                triggerFactory,
                defaultTimeZoneProvider);

        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SensorDefinitionFindServiceImpl sensorDefinitionFindService = new SensorDefinitionFindServiceImpl();
        this.sut = new SpecToModelCheckMappingServiceImpl(
                reflectionService,
                sensorDefinitionFindService,
                schedulesUtilityService,
                new SimilarCheckCacheImpl(reflectionService, sensorDefinitionFindService));
        
        this.bigQueryConnectionSpec = BigQueryConnectionSpecObjectMother.create();
        this.tableSpec = TableSpecObjectMother.create("public", "tab1");
        this.tableSpec.getGroupings().setFirstDataGroupingConfiguration(new DataGroupingConfigurationSpec());
        this.executionContext = new ExecutionContext(null, DqoHomeContextObjectMother.getRealDqoHomeContext());
    }

    @Test
    void createUiModel_whenEmptyTableChecksModelGiven_thenCreatesUiModel() {
        TableProfilingCheckCategoriesSpec tableCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(6, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenTableChecksProfilingModelGivenAndOneReferenceTableWithNoChecks_thenCreatesUiModel() {
        TableProfilingCheckCategoriesSpec tableCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
        this.tableSpec.getReferenceTables().put("comparison1", new ReferenceTableSpec());
        CheckContainerModel uiModel = this.sut.createModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(7, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenEmptyColumnChecksModelGiven_thenCreatesUiModel() {
        ColumnProfilingCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(columnCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(13, uiModel.getCategories().size());
    }

    private Map.Entry<Iterable<String>, Iterable<String>> extractCheckNamesFromUIModels(
            CheckContainerModel uiModel,
            CheckContainerBasicModel uiBasicModel) {

        Iterable<String> checksModel =
                uiModel.getCategories().stream()
                        .flatMap(
                                uiQualityCategoryModel ->
                                        uiQualityCategoryModel.getChecks()
                                                .stream()
                                                .map(CheckModel::getCheckName)
                        ).sorted().collect(Collectors.toList());

        Iterable<String> checksBasicModel =
                uiBasicModel.getChecks().stream()
                        .map(CheckBasicModel::getCheckName)
                        .sorted().collect(Collectors.toList());

        return new AbstractMap.SimpleEntry<>(checksModel, checksBasicModel);
    }

    @Test
    void createUiBasicModel_whenEmptyTableChecksModelGiven_thenCreatesUiBasicModel() {
        TableProfilingCheckCategoriesSpec tableCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery);
        CheckContainerBasicModel uiBasicModel = this.sut.createBasicModel(tableCheckCategoriesSpec, this.executionContext, ProviderType.bigquery);

        Assertions.assertNotNull(uiBasicModel);
        Assertions.assertEquals(6, CheckContainerBasicModelUtility.getCheckCategoryNames(uiBasicModel).size());

        Map.Entry<Iterable<String>, Iterable<String>> names = extractCheckNamesFromUIModels(uiModel, uiBasicModel);

        Assertions.assertIterableEquals(names.getKey(), names.getValue());
    }

    @Test
    void createUiBasicModel_whenEmptyColumnChecksModelGiven_thenCreatesUiBasicModel() {
        ColumnProfilingCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.sut.createModel(columnCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery);
        CheckContainerBasicModel uiBasicModel = this.sut.createBasicModel(columnCheckCategoriesSpec, this.executionContext, ProviderType.bigquery);

        Assertions.assertNotNull(uiBasicModel);
        Assertions.assertEquals(13, CheckContainerBasicModelUtility.getCheckCategoryNames(uiBasicModel).size());

        Map.Entry<Iterable<String>, Iterable<String>> names = extractCheckNamesFromUIModels(uiModel, uiBasicModel);

        Assertions.assertIterableEquals(names.getKey(), names.getValue());
    }
}
