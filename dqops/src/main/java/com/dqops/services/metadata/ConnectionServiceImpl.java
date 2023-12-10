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

import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
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
     * @param principal Principal that will be used to run the job.
     * @return Asynchronous job result object for deferred background operations.
     */
    @Override
    public PushJobResult<DeleteStoredDataResult> deleteConnection(String connectionName, DqoUserPrincipal principal) {
        List<String> connectionNameList = new LinkedList<>();
        connectionNameList.add(connectionName);

        List<PushJobResult<DeleteStoredDataResult>> jobResultList = this.deleteConnections(connectionNameList, principal);
        return jobResultList.isEmpty() ? null : jobResultList.get(0);
    }

    /**
     * Deletes connections from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these connections.
     * @param connectionNames Iterable of connection names.
     * @param principal Principal that will be used to run the job.
     * @return List of asynchronous job result objects for deferred background operations.
     */
    @Override
    public List<PushJobResult<DeleteStoredDataResult>> deleteConnections(Iterable<String> connectionNames, DqoUserPrincipal principal) {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();

        List<DeleteStoredDataQueueJobParameters> deleteStoredDataParameters = new ArrayList<>();

        for (String connectionName : connectionNames) {
            ConnectionWrapper connectionWrapper = this.getConnection(userHome, connectionName);
            if (connectionWrapper == null) {
                continue;
            }

            connectionWrapper.markForDeletion();

            DeleteStoredDataQueueJobParameters param = new DeleteStoredDataQueueJobParameters() {{
                setConnection(connectionName);
                setDeleteStatistics(true);
                setDeleteCheckResults(true);
                setDeleteSensorReadouts(true);
                setDeleteErrors(true);
            }};
            deleteStoredDataParameters.add(param);
        }

        List<PushJobResult<DeleteStoredDataResult>> results = new ArrayList<>();
        for (DeleteStoredDataQueueJobParameters param : deleteStoredDataParameters) {
            DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
            deleteStoredDataJob.setDeletionParameters(param);
            PushJobResult<DeleteStoredDataResult> jobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob, principal);
            results.add(jobResult);
        }

        userHomeContext.flush();
        return results;
    }
}
