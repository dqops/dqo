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
import ai.dqo.core.scheduler.quartz.*;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.ExecutionContextFactory;
import ai.dqo.execution.ExecutionContextFactoryImpl;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcherImpl;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.services.check.mapping.models.AllChecksModel;
import ai.dqo.services.check.matching.SimilarCheckCacheImpl;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import ai.dqo.services.timezone.DefaultTimeZoneProviderObjectMother;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import ai.dqo.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AllChecksModelFactoryImplTests extends BaseTest {
    private AllChecksModelFactoryImpl sut;
    private ConnectionSpec connectionSpec;
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
        SpecToModelCheckMappingService specToModelCheckMappingService = new SpecToModelCheckMappingServiceImpl(
                reflectionService,
                sensorDefinitionFindService,
                schedulesUtilityService,
                new SimilarCheckCacheImpl(reflectionService, sensorDefinitionFindService));

        ExecutionContextFactory executionContextFactory = new ExecutionContextFactoryImpl(
                UserHomeContextFactoryObjectMother.createWithInMemoryContext(),
                DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory()
        );

        this.sut = new AllChecksModelFactoryImpl(
                executionContextFactory,
                new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl()),
                specToModelCheckMappingService);

        this.executionContext = executionContextFactory.create();

        UserHome userHome = this.executionContext.getUserHomeContext().getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        this.connectionSpec = connectionWrapper.getSpec();

        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("public", "tab1"));
        this.tableSpec = tableWrapper.getSpec();

        ColumnSpecMap columnSpecMap = this.tableSpec.getColumns();
        ColumnSpec columnSpec = new ColumnSpec();
        columnSpecMap.put("col1", columnSpec);
        this.tableSpec.getDataStreams().setFirstDataStreamMapping(new DataStreamMappingSpec());
    }

    @Test
    void fromCheckSearchFilters_whenConnectionNameGiven_thenCreatesFullConnectionUiModel() {
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        checkSearchFilters.setConnectionName(this.connectionSpec.getConnectionName());

        List<AllChecksModel> allChecksModels = this.sut.fromCheckSearchFilters(checkSearchFilters);
        Assertions.assertNotNull(allChecksModels);
        Assertions.assertEquals(1, allChecksModels.size());
    }
}
