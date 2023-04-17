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
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConnectionServiceImpl implements ConnectionService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoQueueJobFactory dqoQueueJobFactory;
    private final DqoJobQueue dqoJobQueue;

    @Autowired
    public ConnectionServiceImpl(UserHomeContextFactory userHomeContextFactory,
                                 DqoQueueJobFactory dqoQueueJobFactory,
                                 DqoJobQueue dqoJobQueue) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Finds a connection located in provided user home.
     * @param userHome       User home.
     * @param connectionName Connection name.
     * @return Connection wrapper with the requested connection.
     */
    @Override
    public ConnectionWrapper getConnection(UserHome userHome, String connectionName) {
        ConnectionList connections = userHome.getConnections();
        return connections.getByObjectName(connectionName, true);
    }

    /**
     * Deletes connection from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this connection.
     * @param connectionName Connection name.
     * @return Asynchronous job result object for deferred background operations.
     */
    @Override
    public PushJobResult<DeleteStoredDataQueueJobResult> deleteConnection(String connectionName) {
        List<String> connectionNameList = new LinkedList<>();
        connectionNameList.add(connectionName);

        List<PushJobResult<DeleteStoredDataQueueJobResult>> jobResultList = this.deleteConnections(connectionNameList);
        return jobResultList.isEmpty() ? null : jobResultList.get(0);
    }

    /**
     * Deletes connections from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these connections.
     * @param connectionNames Iterable of connection names.
     * @return List of asynchronous job result objects for deferred background operations.
     */
    @Override
    public List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteConnections(Iterable<String> connectionNames) {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<DeleteStoredDataQueueJobParameters> deleteStoredDataParameters = new ArrayList<>();

        for (String connectionName : connectionNames) {
            ConnectionWrapper connectionWrapper = this.getConnection(userHome, connectionName);
            if (connectionWrapper == null) {
                continue;
            }

            connectionWrapper.markForDeletion();

            DeleteStoredDataQueueJobParameters param = new DeleteStoredDataQueueJobParameters() {{
                setConnectionName(connectionName);
                setDeleteStatistics(true);
                setDeleteCheckResults(true);
                setDeleteSensorReadouts(true);
                setDeleteErrors(true);
            }};
            deleteStoredDataParameters.add(param);
        }

        List<PushJobResult<DeleteStoredDataQueueJobResult>> results = new ArrayList<>();
        for (DeleteStoredDataQueueJobParameters param : deleteStoredDataParameters) {
            DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
            deleteStoredDataJob.setDeletionParameters(param);
            PushJobResult<DeleteStoredDataQueueJobResult> jobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob);
            results.add(jobResult);
        }

        userHomeContext.flush();
        return results;
    }
}
