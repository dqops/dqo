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

import ai.dqo.BaseTest;
import ai.dqo.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import ai.dqo.core.scheduler.quartz.*;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableSpecObjectMother;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import ai.dqo.services.check.mapping.models.UICheckContainerModel;
import ai.dqo.services.check.mapping.models.UICheckModel;
import ai.dqo.services.check.mapping.basicmodels.UICheckContainerBasicModel;
import ai.dqo.services.check.mapping.basicmodels.UICheckBasicModel;
import ai.dqo.services.check.mapping.utils.UICheckContainerBasicModelUtility;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import ai.dqo.services.timezone.DefaultTimeZoneProviderObjectMother;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import ai.dqo.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class SpecToUiCheckMappingServiceImplTests extends BaseTest {
    private SpecToUiCheckMappingServiceImpl sut;
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
        
        this.sut = new SpecToUiCheckMappingServiceImpl(
                new ReflectionServiceImpl(),
                new SensorDefinitionFindServiceImpl(),
                schedulesUtilityService);
        
        this.bigQueryConnectionSpec = BigQueryConnectionSpecObjectMother.create();
        this.tableSpec = TableSpecObjectMother.create("public", "tab1");
        this.tableSpec.getDataStreams().setFirstDataStreamMapping(new DataStreamMappingSpec());
        this.executionContext = new ExecutionContext(null, DqoHomeContextObjectMother.getRealDqoHomeContext());
    }

    @Test
    void createUiModel_whenEmptyTableChecksModelGiven_thenCreatesUiModel() {
        TableProfilingCheckCategoriesSpec tableCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
        UICheckContainerModel uiModel = this.sut.createUiModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(5, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenEmptyColumnChecksModelGiven_thenCreatesUiModel() {
        ColumnProfilingCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        UICheckContainerModel uiModel = this.sut.createUiModel(columnCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(11, uiModel.getCategories().size());
    }

    private Map.Entry<Iterable<String>, Iterable<String>> extractCheckNamesFromUIModels(
            UICheckContainerModel uiModel,
            UICheckContainerBasicModel uiBasicModel) {

        Iterable<String> checksModel =
                uiModel.getCategories().stream()
                        .flatMap(
                                uiQualityCategoryModel ->
                                        uiQualityCategoryModel.getChecks()
                                                .stream()
                                                .map(UICheckModel::getCheckName)
                        ).sorted().collect(Collectors.toList());

        Iterable<String> checksBasicModel =
                uiBasicModel.getChecks().stream()
                        .map(UICheckBasicModel::getCheckName)
                        .sorted().collect(Collectors.toList());

        return new AbstractMap.SimpleEntry<>(checksModel, checksBasicModel);
    }

    @Test
    void createUiBasicModel_whenEmptyTableChecksModelGiven_thenCreatesUiBasicModel() {
        TableProfilingCheckCategoriesSpec tableCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
        UICheckContainerModel uiModel = this.sut.createUiModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery);
        UICheckContainerBasicModel uiBasicModel = this.sut.createUiBasicModel(tableCheckCategoriesSpec, this.executionContext, ProviderType.bigquery);

        Assertions.assertNotNull(uiBasicModel);
        Assertions.assertEquals(5, UICheckContainerBasicModelUtility.getCheckCategoryNames(uiBasicModel).size());

        Map.Entry<Iterable<String>, Iterable<String>> names = extractCheckNamesFromUIModels(uiModel, uiBasicModel);

        Assertions.assertIterableEquals(names.getKey(), names.getValue());
    }

    @Test
    void createUiBasicModel_whenEmptyColumnChecksModelGiven_thenCreatesUiBasicModel() {
        ColumnProfilingCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        UICheckContainerModel uiModel = this.sut.createUiModel(columnCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, this.executionContext, ProviderType.bigquery);
        UICheckContainerBasicModel uiBasicModel = this.sut.createUiBasicModel(columnCheckCategoriesSpec, this.executionContext, ProviderType.bigquery);

        Assertions.assertNotNull(uiBasicModel);
        Assertions.assertEquals(11, UICheckContainerBasicModelUtility.getCheckCategoryNames(uiBasicModel).size());

        Map.Entry<Iterable<String>, Iterable<String>> names = extractCheckNamesFromUIModels(uiModel, uiBasicModel);

        Assertions.assertIterableEquals(names.getKey(), names.getValue());
    }
}
