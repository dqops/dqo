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
package com.dqops.core.synchronization.service;

import com.dqops.core.synchronization.fileexchange.TargetTableModifiedPartitions;

/**
 * Service that asks the DQOps Cloud to refresh the data quality data warehouse after uploading new parquet files.
 */
public interface DqoCloudWarehouseService {
    /**
     * Refreshes a target table, sending additional information with the list of modified connections, tables and months that should be refreshed.
     *
     * @param targetTableModifiedPartitions Target table modified partitions. Identifies the target table and lists all unique connections, tables, dates of the month to be refreshed.
     */
    void refreshNativeTable(TargetTableModifiedPartitions targetTableModifiedPartitions);
}
