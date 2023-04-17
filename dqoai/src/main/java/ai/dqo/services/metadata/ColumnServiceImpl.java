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
public class ColumnServiceImpl implements ColumnService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoQueueJobFactory dqoQueueJobFactory;
    private final DqoJobQueue dqoJobQueue;

    @Autowired
    public ColumnServiceImpl(UserHomeContextFactory userHomeContextFactory,
                             DqoQueueJobFactory dqoQueueJobFactory,
                             DqoJobQueue dqoJobQueue) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Finds a column located in provided user home.
     * @param userHome       User home.
     * @param connectionName Connection name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Column spec with the requested column.
     */
    @Override
    public ColumnSpec getColumn(UserHome userHome,
                                String connectionName,
                                PhysicalTableName tableName,
                                String columnName) {
        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        TableList tableList = connectionWrapper.getTables();
        TableWrapper tableWrapper = tableList.getByObjectName(tableName, true);
        if (tableWrapper == null) {
            return null;
        }

        return tableWrapper.getSpec().getColumns().get(columnName);
    }

    /**
     * Deletes column from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this column.
     * @param connectionName Connection name
     * @param tableName      Physical table name.
     * @param columnName     Column name.
     * @return Asynchronous job result object for deferred background operations.
     */
    @Override
    public PushJobResult<DeleteStoredDataQueueJobResult> deleteColumn(String connectionName,
                                                                      PhysicalTableName tableName,
                                                                      String columnName) {
        List<String> columnNameList = new LinkedList<>();
        columnNameList.add(columnName);

        Map<PhysicalTableName, Iterable<String>> tableToColumnMapping = new HashMap<>();
        tableToColumnMapping.put(tableName, columnNameList);

        Map<String, Map<PhysicalTableName, Iterable<String>>> connToTabToColMapping = new HashMap<>();
        connToTabToColMapping.put(connectionName, tableToColumnMapping);

        List<PushJobResult<DeleteStoredDataQueueJobResult>> jobResultList = this.deleteColumns(connToTabToColMapping);

        return jobResultList.isEmpty() ? null : jobResultList.get(0);
    }

    /**
     * Deletes columns from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these columns.
     * @param connectionToTableToColumns Mapping for every connection to a mapping for every table for which columns need to be deleted.
     * @return List of asynchronous job result objects for deferred background operations.
     */
    @Override
    public List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteColumns(
            Map<String, Map<PhysicalTableName, Iterable<String>>> connectionToTableToColumns) {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<DeleteStoredDataQueueJobParameters> deleteStoredDataParameters = new ArrayList<>();

        for (Map.Entry<String, Map<PhysicalTableName, Iterable<String>>> connToTabToColsEntry :
                connectionToTableToColumns.entrySet()) {
            String connectionName = connToTabToColsEntry.getKey();

            for (Map.Entry<PhysicalTableName, Iterable<String>> tableToColsEntry :
                    connToTabToColsEntry.getValue().entrySet()) {
                PhysicalTableName tableName = tableToColsEntry.getKey();
                List<String> columnNamesRemoved = new ArrayList<>();

                for (String columnName : tableToColsEntry.getValue()) {
                    ColumnSpec columnSpec = this.getColumn(userHome, connectionName, tableName, columnName);
                    if (columnSpec == null) {
                        continue;
                    }

                    TableWrapper table = userHome.findTableFor(columnSpec.getHierarchyId());
                    table.getSpec().getColumns().remove(columnName);
                    columnNamesRemoved.add(columnName);
                }

                DeleteStoredDataQueueJobParameters param = new DeleteStoredDataQueueJobParameters() {{
                    setConnectionName(connectionName);
                    setSchemaTableName(tableName.toTableSearchFilter());
                    setColumnNames(columnNamesRemoved);
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
