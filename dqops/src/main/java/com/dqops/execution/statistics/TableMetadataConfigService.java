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

package com.dqops.execution.statistics;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.sources.PhysicalTableName;
import tech.tablesaw.api.Table;

/**
 * Special service that is used during statistics collection to use statistics to configure the table.
 * This service configures the timestamp columns and the ID columns.
 */
public interface TableMetadataConfigService {
    /**
     * Configures the table metadata by analyzing the table schema.
     *
     * @param userDomainIdentity           User domain identity to load the correct user home for a correct user domain.
     * @param connectionName               Connection name.
     * @param physicalTableName            Target table.
     * @param allNormalizedStatisticsTable The results recently captured from statistics.
     */
    void configureTableMetadata(UserDomainIdentity userDomainIdentity,
                                String connectionName,
                                PhysicalTableName physicalTableName,
                                Table allNormalizedStatisticsTable);
}
