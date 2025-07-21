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
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.userhome.UserHome;

import java.util.List;

/**
 * Service that performs connection operations.
 */
public interface ConnectionService {
    /**
     * Finds a connection located in provided user home.
     * @param userHome       User home.
     * @param connectionName Connection name.
     * @return Connection wrapper with the requested connection.
     */
    ConnectionWrapper getConnection(UserHome userHome, String connectionName);

    /**
     * Deletes connection from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this connection.
     * @param connectionName Connection name.
     * @param principal Principal that will be used to run the job.
     * @return Asynchronous job result object for deferred background operations.
     */
    PushJobResult<DeleteStoredDataResult> deleteConnection(String connectionName, DqoUserPrincipal principal);

    /**
     * Deletes connections from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these connections.
     * @param connectionNames Iterable of connection names.
     * @param principal Principal that will be used to run the job.
     * @return List of asynchronous job result objects for deferred background operations.
     */
    List<PushJobResult<DeleteStoredDataResult>> deleteConnections(Iterable<String> connectionNames, DqoUserPrincipal principal);
}
