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
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TableServiceImpl implements TableService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoQueueJobFactory dqoQueueJobFactory;
    private final DqoJobQueue dqoJobQueue;

    @Autowired
    public TableServiceImpl(UserHomeContextFactory userHomeContextFactory,
                            DqoQueueJobFactory dqoQueueJobFactory,
                            DqoJobQueue dqoJobQueue) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Finds a table located in provided user home.
     * @param userHome       User home.
     * @param connectionName Connection name.
     * @param tableName      Table name.
     * @return Table wrapper with the requested table.
     */
    @Override
    public TableWrapper getTable(UserHome userHome, String connectionName, PhysicalTableName tableName) {
        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        return connectionWrapper.getTables().getByObjectName(tableName, true);
    }

    /**
     * Deletes table from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this table.
     * @param connectionName Connection name
     * @param tableName      Physical table name.
     * @return Asynchronous job result object for deferred background operations.
     */
    @Override
    public PushJobResult<DeleteStoredDataQueueJobResult> deleteTable(String connectionName, PhysicalTableName tableName) {
        List<PhysicalTableName> tableNameList = new LinkedList<>();
        tableNameList.add(tableName);

        Map<String, Iterable<PhysicalTableName>> connectionToTableMapping = new HashMap<>();
        connectionToTableMapping.put(connectionName, tableNameList);
        List<PushJobResult<DeleteStoredDataQueueJobResult>> jobResultList = this.deleteTables(connectionToTableMapping);

        return jobResultList.isEmpty() ? null : jobResultList.get(0);
    }

    /**
     * Deletes tables from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these tables.
     * @param connectionToTables Connection name to tables on that connection mapping.
     * @return Asynchronous job result objects for deferred background operations.
     */
    @Override
    public List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteTables(Map<String, Iterable<PhysicalTableName>> connectionToTables) {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<DeleteStoredDataQueueJobParameters> deleteStoredDataParameters = new ArrayList<>();

        for (Map.Entry<String, Iterable<PhysicalTableName>> connToTables : connectionToTables.entrySet()) {
            String connectionName = connToTables.getKey();
            for (PhysicalTableName tableName : connToTables.getValue()) {
                TableWrapper tableWrapper = this.getTable(userHome, connectionName, tableName);
                if (tableWrapper == null) {
                    continue;
                }

                tableWrapper.markForDeletion();

                DeleteStoredDataQueueJobParameters param = new DeleteStoredDataQueueJobParameters() {{
                    setConnectionName(connectionName);
                    setSchemaTableName(tableName.toTableSearchFilter());
                    setDeleteStatistics(true);
                    setDeleteCheckResults(true);
                    setDeleteSensorReadouts(true);
                    setDeleteErrors(true);
                }};
                deleteStoredDataParameters.add(param);
            }
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
