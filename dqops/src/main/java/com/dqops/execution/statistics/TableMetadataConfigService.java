/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
