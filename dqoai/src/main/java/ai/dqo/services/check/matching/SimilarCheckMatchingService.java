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
package ai.dqo.services.check.matching;

import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.TableSpec;

/**
 * Service that finds similar checks on a table or on a column.
 */
public interface SimilarCheckMatchingService {
    /**
     * Find similar checks in all check types for a table.
     *
     * @param tableSpec Table to be analyzed.
     * @return List of similar checks.
     */
    SimilarChecksContainer findSimilarTableChecks(TableSpec tableSpec);

    /**
     * Find similar checks in all check types for a column.
     *
     * @param tableSpec  Parent table of the column
     * @param columnSpec Column specification.
     * @return List of similar checks.
     */
    SimilarChecksContainer findSimilarColumnChecks(TableSpec tableSpec, ColumnSpec columnSpec);
}
