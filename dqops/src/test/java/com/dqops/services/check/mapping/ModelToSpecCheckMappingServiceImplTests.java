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
import com.dqops.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import com.dqops.core.scheduler.quartz.*;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableSpecObjectMother;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.matching.SimilarCheckCacheImpl;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModelToSpecCheckMappingServiceImplTests extends BaseTest {
    private ModelToSpecCheckMappingServiceImpl sut;
    private SpecToModelCheckMappingServiceImpl specToUiMapper;
    private TableSpec tableSpec;
    private ConnectionSpec bigQueryConnectionSpec;

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
        this.specToUiMapper = new SpecToModelCheckMappingServiceImpl(
                reflectionService, sensorDefinitionFindService, schedulesUtilityService,
                new SimilarCheckCacheImpl(reflectionService, sensorDefinitionFindService));

        this.bigQueryConnectionSpec = BigQueryConnectionSpecObjectMother.create();
        
        
        this.sut = new ModelToSpecCheckMappingServiceImpl(reflectionService);
        this.tableSpec = TableSpecObjectMother.create("public", "tab1");
        this.tableSpec.setDefaultDataGroupingConfiguration(new DataGroupingConfigurationSpec());
    }

    @Test
    void updateAllChecksSpecs_whenEmptyTableChecksModelGivenJustCreated_thenExecutesWithoutErrors() {
        TableProfilingCheckCategoriesSpec tableCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.specToUiMapper.createModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, null, null);

        this.sut.updateCheckContainerSpec(uiModel, tableCheckCategoriesSpec);
    }

    @Test
    void updateAllChecksSpecs_whenEmptyColumnChecksModelGivenJustCreated_thenExecutesWithoutErrors() {
        ColumnProfilingCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.specToUiMapper.createModel(columnCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, null, null);

        this.sut.updateCheckContainerSpec(uiModel, columnCheckCategoriesSpec);
    }
}
