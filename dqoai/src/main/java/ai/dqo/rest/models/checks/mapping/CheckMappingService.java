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

import ai.dqo.checks.AbstractCheckCategoriesSpec;
import ai.dqo.rest.models.checks.UIAllChecksModel;

/**
 * Service that converts UI models to the data quality check specifications or creates the UI model from the data quality check specifications,
 * enabling transformation between the storage model (YAML compliant) with a UI friendly UI model.
 */
public interface CheckMappingService {
    /**
     * Creates a checks UI model for the whole container of table level or column level data quality checks, divided into DAMA dimensions.
     *
     * @param checkDimensionsSpec Table level data quality checks container or a column level data quality checks container.
     * @return Checks data quality container.
     */
    UIAllChecksModel createUiModel(AbstractCheckCategoriesSpec checkDimensionsSpec);
}
