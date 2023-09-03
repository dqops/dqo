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
package com.dqops.services.metadata;

import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.userhome.UserHome;

import java.util.List;
import java.util.Map;

/**
 * Service that performs column operations.
 */
public interface ColumnService {
    /**
     * Finds a column located in provided user home.
     * @param userHome       User home.
     * @param connectionName Connection name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Column spec with the requested column.
     */
    ColumnSpec getColumn(UserHome userHome,
                         String connectionName,
                         PhysicalTableName tableName,
                         String columnName);

    /**
     * Deletes column from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this column.
     * @param connectionName Connection name
     * @param tableName      Physical table name.
     * @param columnName     Column name.
     * @param principal Principal that will be used to run the job.
     * @return Asynchronous job result object for deferred background operations.
     */
    PushJobResult<DeleteStoredDataQueueJobResult> deleteColumn(String connectionName,
                                                               PhysicalTableName tableName,
                                                               String columnName,
                                                               DqoUserPrincipal principal);

    /**
     * Deletes columns from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these columns.
     * @param connectionToTableToColumns Mapping for every connection to a mapping for every table for which columns need to be deleted.
     * @param principal Principal that will be used to run the job.
     * @return List of asynchronous job result objects for deferred background operations.
     */
    List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteColumns(
            Map<String, Map<PhysicalTableName, Iterable<String>>> connectionToTableToColumns, DqoUserPrincipal principal);
}
