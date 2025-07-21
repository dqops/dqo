/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.service;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.fileexchange.TargetTableModifiedPartitions;

/**
 * Service that asks the DQOps Cloud to refresh the data quality data warehouse after uploading new parquet files.
 */
public interface DqoCloudWarehouseService {
    /**
     * Refreshes a target table, sending additional information with the list of modified connections, tables and months that should be refreshed.
     *
     * @param userIdentity User identity that identifies the target data domain.
     * @param targetTableModifiedPartitions Target table modified partitions. Identifies the target table and lists all unique connections, tables, dates of the month to be refreshed.
     */
    void refreshNativeTable(TargetTableModifiedPartitions targetTableModifiedPartitions, UserDomainIdentity userIdentity);
}
