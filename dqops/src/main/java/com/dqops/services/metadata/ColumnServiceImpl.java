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
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
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
     * @param principal Principal that will be used to run the job.
     * @return Asynchronous job result object for deferred background operations.
     */
    @Override
    public PushJobResult<DeleteStoredDataResult> deleteColumn(String connectionName,
                                                              PhysicalTableName tableName,
                                                              String columnName,
                                                              DqoUserPrincipal principal) {
        List<String> columnNameList = new LinkedList<>();
        columnNameList.add(columnName);

        Map<PhysicalTableName, Iterable<String>> tableToColumnMapping = new LinkedHashMap<>();
        tableToColumnMapping.put(tableName, columnNameList);

        Map<String, Map<PhysicalTableName, Iterable<String>>> connToTabToColMapping = new LinkedHashMap<>();
        connToTabToColMapping.put(connectionName, tableToColumnMapping);

        List<PushJobResult<DeleteStoredDataResult>> jobResultList = this.deleteColumns(connToTabToColMapping, principal);

        return jobResultList.isEmpty() ? null : jobResultList.get(0);
    }

    /**
     * Deletes columns from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these columns.
     * @param connectionToTableToColumns Mapping for every connection to a mapping for every table for which columns need to be deleted.
     * @param principal Principal that will be used to run the job.
     * @return List of asynchronous job result objects for deferred background operations.
     */
    @Override
    public List<PushJobResult<DeleteStoredDataResult>> deleteColumns(
            Map<String, Map<PhysicalTableName, Iterable<String>>> connectionToTableToColumns,
            DqoUserPrincipal principal) {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
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
                    setConnection(connectionName);
                    setFullTableName(tableName.toTableSearchFilter());
                    setColumnNames(columnNamesRemoved);
                    setDeleteStatistics(true);
                    setDeleteCheckResults(true);
                    setDeleteSensorReadouts(true);
                    setDeleteErrors(true);
                }};
                deleteStoredDataParameters.add(param);
            }
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
