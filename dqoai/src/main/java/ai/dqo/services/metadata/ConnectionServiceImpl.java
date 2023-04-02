/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.services.metadata;

import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.PushJobResult;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConnectionServiceImpl implements ConnectionService {
    private final DqoQueueJobFactory dqoQueueJobFactory;
    private final DqoJobQueue dqoJobQueue;

    @Autowired
    public ConnectionServiceImpl(DqoQueueJobFactory dqoQueueJobFactory,
                                 DqoJobQueue dqoJobQueue) {
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Deletes connection from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this connection.
     *
     * @param connectionWrapper Connection wrapper.
     * @param userHomeContext   User home context in which the wrapper has been opened.
     * @return Asynchronous job result object for deferred background operations.
     */
    @Override
    public PushJobResult<DeleteStoredDataQueueJobResult> deleteConnection(ConnectionWrapper connectionWrapper,
                                                                          UserHomeContext userHomeContext) {
        List<PushJobResult<DeleteStoredDataQueueJobResult>> jobResultList = this.deleteConnections(
                new LinkedList<>(){{add(connectionWrapper);}},
                userHomeContext
        );
        return jobResultList.get(0);
    }

    /**
     * Deletes connections from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these connections.
     *
     * @param connectionWrappers Iterable of connection wrappers.
     * @param userHomeContext    User home context in which the wrappers have been opened.
     * @return List of asynchronous job result objects for deferred background operations.
     */
    @Override
    public List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteConnections(Iterable<ConnectionWrapper> connectionWrappers,
                                                                                 UserHomeContext userHomeContext) {
        for (ConnectionWrapper connectionWrapper: connectionWrappers) {
            connectionWrapper.markForDeletion();
        }
        userHomeContext.flush();

        List<PushJobResult<DeleteStoredDataQueueJobResult>> results = new ArrayList<>();
        for (ConnectionWrapper connectionWrapper: connectionWrappers) {
            String connection = connectionWrapper.getName();

            DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
            DeleteStoredDataQueueJobParameters param = new DeleteStoredDataQueueJobParameters() {{
                setConnectionName(connection);
                setDeleteStatistics(true);
                setDeleteCheckResults(true);
                setDeleteSensorReadouts(true);
                setDeleteErrors(true);
            }};
            deleteStoredDataJob.setDeletionParameters(param);
            PushJobResult<DeleteStoredDataQueueJobResult> jobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob);
            results.add(jobResult);
        }
        return results;
    }
}
