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

package com.dqops.core.similarity;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.similarity.TableSimilarityStore;
import com.dqops.metadata.sources.PhysicalTableName;

/**
 * Table similarity score calculation service.
 */
public interface TableSimilarityScoreFactory {
    /**
     * Calculates a table similarity score from statistics.
     *
     * @param connectionName     Connection name.
     * @param physicalTableName  Physical table name.
     * @param userDomainIdentity User identity and the data domain.
     * @return Table similarity score or null when the table has no statistics.
     */
    TableSimilarityStore calculateSimilarityScore(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userDomainIdentity);
}
