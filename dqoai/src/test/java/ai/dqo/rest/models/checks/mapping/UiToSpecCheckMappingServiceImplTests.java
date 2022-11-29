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
package ai.dqo.rest.models.checks.mapping;

import ai.dqo.BaseTest;
import ai.dqo.checks.column.adhoc.ColumnAdHocCheckCategoriesSpec;
import ai.dqo.checks.table.adhoc.TableAdHocCheckCategoriesSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.rest.models.checks.UIAllChecksModel;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UiToSpecCheckMappingServiceImplTests extends BaseTest {
    private UiToSpecCheckMappingServiceImpl sut;
    private SpecToUiCheckMappingServiceImpl specToUiMapper;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        this.specToUiMapper = new SpecToUiCheckMappingServiceImpl(reflectionService);
        this.sut = new UiToSpecCheckMappingServiceImpl(reflectionService);
    }

    @Test
    void updateAllChecksSpecs_whenEmptyTableChecksModelGivenJustCreated_thenExecutesWithoutErrors() {
        TableAdHocCheckCategoriesSpec tableCheckCategoriesSpec = new TableAdHocCheckCategoriesSpec();
        UIAllChecksModel uiModel = this.specToUiMapper.createUiModel(tableCheckCategoriesSpec, new CheckSearchFilters());

        this.sut.updateAllChecksSpecs(uiModel, tableCheckCategoriesSpec);
    }

    @Test
    void updateAllChecksSpecs_whenEmptyColumnChecksModelGivenJustCreated_thenExecutesWithoutErrors() {
        ColumnAdHocCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnAdHocCheckCategoriesSpec();
        UIAllChecksModel uiModel = this.specToUiMapper.createUiModel(columnCheckCategoriesSpec, new CheckSearchFilters());

        this.sut.updateAllChecksSpecs(uiModel, columnCheckCategoriesSpec);
    }
}
