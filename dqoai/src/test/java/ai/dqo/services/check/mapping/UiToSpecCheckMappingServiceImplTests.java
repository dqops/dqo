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
import ai.dqo.checks.column.adhoc.ColumnAdHocCheckCategoriesSpec;
import ai.dqo.checks.table.adhoc.TableAdHocCheckCategoriesSpec;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpecMap;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableSpecObjectMother;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.services.check.mapping.UiToSpecCheckMappingServiceImpl;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UiToSpecCheckMappingServiceImplTests extends BaseTest {
    private UiToSpecCheckMappingServiceImpl sut;
    private SpecToUiCheckMappingServiceImpl specToUiMapper;
    private TableSpec tableSpec;

    @BeforeEach
    void setUp() {
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SensorDefinitionFindServiceImpl sensorDefinitionFindService = new SensorDefinitionFindServiceImpl();
        this.specToUiMapper = new SpecToUiCheckMappingServiceImpl(reflectionService, sensorDefinitionFindService);
        this.sut = new UiToSpecCheckMappingServiceImpl(reflectionService);
        this.tableSpec = TableSpecObjectMother.create("public", "tab1");
        this.tableSpec.getDataStreams().setFirstDataStreamMapping(new DataStreamMappingSpec());
    }

    @Test
    void updateAllChecksSpecs_whenEmptyTableChecksModelGivenJustCreated_thenExecutesWithoutErrors() {
        TableAdHocCheckCategoriesSpec tableCheckCategoriesSpec = new TableAdHocCheckCategoriesSpec();
        UIAllChecksModel uiModel = this.specToUiMapper.createUiModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.tableSpec, null, null);

        this.sut.updateAllChecksSpecs(uiModel, tableCheckCategoriesSpec);
    }

    @Test
    void updateAllChecksSpecs_whenEmptyColumnChecksModelGivenJustCreated_thenExecutesWithoutErrors() {
        ColumnAdHocCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnAdHocCheckCategoriesSpec();
        UIAllChecksModel uiModel = this.specToUiMapper.createUiModel(columnCheckCategoriesSpec, new CheckSearchFilters(),
                this.tableSpec, null, null);

        this.sut.updateAllChecksSpecs(uiModel, columnCheckCategoriesSpec);
    }
}
