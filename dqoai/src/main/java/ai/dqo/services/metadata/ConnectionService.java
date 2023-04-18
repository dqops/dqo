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
package ai.dqo.services.metadata;

import ai.dqo.core.jobqueue.PushJobResult;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.userhome.UserHome;

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
     * @return Asynchronous job result object for deferred background operations.
     */
    PushJobResult<DeleteStoredDataQueueJobResult> deleteConnection(String connectionName);

    /**
     * Deletes connections from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these connections.
     * @param connectionNames Iterable of connection names.
     * @return List of asynchronous job result objects for deferred background operations.
     */
    List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteConnections(Iterable<String> connectionNames);
}
