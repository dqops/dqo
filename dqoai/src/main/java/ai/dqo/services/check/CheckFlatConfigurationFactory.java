/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.services.check;

import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.services.check.mapping.models.AllChecksModel;
import ai.dqo.services.check.models.CheckConfigurationModel;

import java.util.List;

/**
 * Service for creating flat collections of self-contained check configuration models.
 */
public interface CheckFlatConfigurationFactory {
    /**
     * Flattens information contained in <code>allChecksModel</code>.
     * @param allChecksModel Partial model of checks on a single connection.
     * @return List of self-contained check configuration models.
     */
    List<CheckConfigurationModel> fromAllChecksModel(AllChecksModel allChecksModel);

    /**
     * Gets a collection of check configuration models that fit the provided filters.
     * @param checkSearchFilters Check search filters.
     * @return List of self-contained check configuration models that fit the filters.
     */
    List<CheckConfigurationModel> fromCheckSearchFilters(CheckSearchFilters checkSearchFilters);
}
