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

import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.services.check.mapping.models.AllChecksModel;

import java.util.List;

/**
 * Factory for {@link AllChecksModel}.
 */
public interface AllChecksModelFactory {
    /**
     * Creates a list of {@link AllChecksModel}s based on provided filters.
     * @param checkSearchFilters Check search filters.
     * @return List of {@link AllChecksModel}s (by connections) fitting the filters.
     */
    List<AllChecksModel> fromCheckSearchFilters(CheckSearchFilters checkSearchFilters);
}
