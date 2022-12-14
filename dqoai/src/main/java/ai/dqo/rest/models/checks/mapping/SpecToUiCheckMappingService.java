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

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.rest.models.checks.UIAllChecksModel;
import ai.dqo.rest.models.checks.basic.UIAllChecksBasicModel;

/**
 * Service that creates a UI friendly model from the data quality check specifications,
 * enabling transformation from the storage model (YAML compliant) to a UI friendly model.
 */
public interface SpecToUiCheckMappingService {
    /**
     * Creates a UI friendly model of the whole checks container of table level or column level data quality checks, divided into categories.
     * @param checkCategoriesSpec Table or column level data quality checks container of type ad-hoc, checkpoint or partitioned check (for a specific timescale).
     * @param runChecksTemplate Check search filter for the parent table or column that is used as a template to create more fine-grained "run checks" job configurations. Also determines which checks will be included in the ui model.
     * @param defaultDataStreamName Default data stream name to assign to new checks. This is the name of the first named data stream on a table level.
     * @return UI friendly model of data quality checks' container.
     */
    UIAllChecksModel createUiModel(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                   CheckSearchFilters runChecksTemplate,
                                   String defaultDataStreamName);

    /**
     * Creates a simplistic UI friendly model of every data quality check on table level or column level, divided into categories.
     *
     * @param checkCategoriesSpec Table or column level data quality checks container of type ad-hoc, checkpoint or partitioned check (for a specific timescale).
     * @return Simplistic UI friendly model of data quality checks' container.
     */
    UIAllChecksBasicModel createUiBasicModel(AbstractRootChecksContainerSpec checkCategoriesSpec);
}
