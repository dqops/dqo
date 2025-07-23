/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.metadata;

import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.models.DeleteStoredDataResult;
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
    PushJobResult<DeleteStoredDataResult> deleteColumn(String connectionName,
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
    List<PushJobResult<DeleteStoredDataResult>> deleteColumns(
            Map<String, Map<PhysicalTableName, Iterable<String>>> connectionToTableToColumns, DqoUserPrincipal principal);
}
