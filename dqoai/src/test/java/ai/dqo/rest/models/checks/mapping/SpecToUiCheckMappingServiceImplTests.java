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
import ai.dqo.metadata.groupings.DataStreamMappingSpecMap;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.rest.models.checks.UIAllChecksModel;
import ai.dqo.rest.models.checks.UICheckModel;
import ai.dqo.rest.models.checks.basic.UIAllChecksBasicModel;
import ai.dqo.rest.models.checks.basic.UICheckBasicModel;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
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
        this.sut = new SpecToUiCheckMappingServiceImpl(new ReflectionServiceImpl());
    }

    @Test
    void createUiModel_whenEmptyTableChecksModelGiven_thenCreatesUiModel() {
        TableAdHocCheckCategoriesSpec tableCheckCategoriesSpec = new TableAdHocCheckCategoriesSpec();
        UIAllChecksModel uiModel = this.sut.createUiModel(tableCheckCategoriesSpec, new CheckSearchFilters(), DataStreamMappingSpecMap.DEFAULT_MAPPING_NAME);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(2, uiModel.getCategories().size());
    }

    @Test
    void createUiModel_whenEmptyColumnChecksModelGiven_thenCreatesUiModel() {
        ColumnAdHocCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnAdHocCheckCategoriesSpec();
        UIAllChecksModel uiModel = this.sut.createUiModel(columnCheckCategoriesSpec, new CheckSearchFilters(), DataStreamMappingSpecMap.DEFAULT_MAPPING_NAME);

        Assertions.assertNotNull(uiModel);
        Assertions.assertEquals(5, uiModel.getCategories().size());
    }

    private Map.Entry<Iterable<Map.Entry<String, Iterable<String>>>, Iterable<Map.Entry<String, Iterable<String>>>>
    extractCheckNamesFromUIModels(UIAllChecksModel uiModel, UIAllChecksBasicModel uiBasicModel) {
        Iterable<Map.Entry<String, Iterable<String>>> categoryToChecksModel =
                uiModel.getCategories().stream().map(
                        uiQualityCategoryModel -> new AbstractMap.SimpleEntry<String, Iterable<String>>(
                                uiQualityCategoryModel.getCategory(),
                                uiQualityCategoryModel.getChecks()
                                        .stream()
                                        .map(UICheckModel::getCheckName)
                                        .collect(Collectors.toList())
                        )
                ).collect(Collectors.toList());

        Iterable<Map.Entry<String, Iterable<String>>> categoryToChecksBasicModel =
                uiBasicModel.getCategories().stream().map(
                        uiQualityCategoryModel -> new AbstractMap.SimpleEntry<String, Iterable<String>>(
                                uiQualityCategoryModel.getCategory(),
                                uiQualityCategoryModel.getChecks()
                                        .stream()
                                        .map(UICheckBasicModel::getCheckName)
                                        .collect(Collectors.toList())
                        )
                ).collect(Collectors.toList());

        return new AbstractMap.SimpleEntry<>(categoryToChecksModel, categoryToChecksBasicModel);
    }

    @Test
    void createUiBasicModel_whenEmptyTableChecksModelGiven_thenCreatesUiBasicModel() {
        TableAdHocCheckCategoriesSpec tableCheckCategoriesSpec = new TableAdHocCheckCategoriesSpec();
        UIAllChecksModel uiModel = this.sut.createUiModel(tableCheckCategoriesSpec, new CheckSearchFilters());
        UIAllChecksBasicModel uiBasicModel = this.sut.createUiBasicModel(tableCheckCategoriesSpec, new CheckSearchFilters());

        Assertions.assertNotNull(uiBasicModel);
        Assertions.assertEquals(4, uiBasicModel.getCategories().size());

        Map.Entry<Iterable<Map.Entry<String, Iterable<String>>>, Iterable<Map.Entry<String, Iterable<String>>>> names =
                extractCheckNamesFromUIModels(uiModel, uiBasicModel);

        Assertions.assertIterableEquals(names.getKey(), names.getValue());
    }

    @Test
    void createUiBasicModel_whenEmptyColumnChecksModelGiven_thenCreatesUiBasicModel() {
        ColumnAdHocCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnAdHocCheckCategoriesSpec();
        UIAllChecksModel uiModel = this.sut.createUiModel(columnCheckCategoriesSpec, new CheckSearchFilters());
        UIAllChecksBasicModel uiBasicModel = this.sut.createUiBasicModel(columnCheckCategoriesSpec, new CheckSearchFilters());

        Assertions.assertNotNull(uiBasicModel);
        Assertions.assertEquals(6, uiBasicModel.getCategories().size());

        Map.Entry<Iterable<Map.Entry<String, Iterable<String>>>, Iterable<Map.Entry<String, Iterable<String>>>> names =
                extractCheckNamesFromUIModels(uiModel, uiBasicModel);

        Assertions.assertIterableEquals(names.getKey(), names.getValue());
    }
}
