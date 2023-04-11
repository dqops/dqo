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
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;

import java.util.List;

/**
 * Service that performs connection operations.
 */
public interface ConnectionService {
    /**
     * Deletes connection from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this connection.
     * @param connectionWrapper Connection wrapper.
     * @param userHomeContext   User home context in which the wrapper has been opened.
     * @return Asynchronous job result object for deferred background operations.
     */
    PushJobResult<DeleteStoredDataQueueJobResult> deleteConnection(ConnectionWrapper connectionWrapper,
                                                                   UserHomeContext userHomeContext);

    /**
     * Deletes connections from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these connections.
     * @param connectionWrappers Iterable of connection wrappers.
     * @param userHomeContext    User home context in which the wrappers have been opened.
     * @return List of asynchronous job result objects for deferred background operations.
     */
    List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteConnections(Iterable<ConnectionWrapper> connectionWrappers,
                                                                          UserHomeContext userHomeContext);
}
