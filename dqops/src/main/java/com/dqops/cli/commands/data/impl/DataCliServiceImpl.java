/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.cli.commands.data.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import com.dqops.core.principal.DqoCloudApiKeyPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.models.DataDeleteResult;
import com.dqops.data.models.DataDeleteResultPartition;
import com.dqops.data.storage.ParquetPartitionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Service handling operations related to stored data from CLI.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DataCliServiceImpl implements DataCliService {

    private DqoJobQueue dqoJobQueue;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoCloudApiKeyPrincipalProvider principalProvider;

    @Autowired
    public DataCliServiceImpl(DqoJobQueue dqoJobQueue, DqoQueueJobFactory dqoQueueJobFactory,
                              DqoCloudApiKeyPrincipalProvider principalProvider) {
        this.dqoJobQueue = dqoJobQueue;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.principalProvider = principalProvider;
    }

    /**
     * Delete data stored in .data folder (check results, sensor readouts, etc.).
     *
     * @param deleteStoredDataQueueJobParameters Parameters including filters that narrow the scope of operation. Connection name is required.
     * @return CliOperationStatus to display in CLI.
     */
    @Override
    public CliOperationStatus deleteStoredData(DeleteStoredDataQueueJobParameters deleteStoredDataQueueJobParameters) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
        deleteStoredDataJob.setDeletionParameters(deleteStoredDataQueueJobParameters);
        DqoUserPrincipal principal = this.principalProvider.createUserPrincipal();
        PushJobResult<DeleteStoredDataQueueJobResult> pushJobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob, principal);

        DataDeleteResult dataDeleteResult = null;

        try {
            DeleteStoredDataQueueJobResult jobResult = pushJobResult.getFinishedFuture().get();
            dataDeleteResult = jobResult.getOperationResult();
            cliOperationStatus.setSuccessMessage(String.format("%d affected partitions.", dataDeleteResult.getPartitionResults().size()));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (dataDeleteResult != null) {
            cliOperationStatus.setTable(dataDeleteResultAsTable(dataDeleteResult));
        }
        return cliOperationStatus;
    }

    /**
     * Convert {@link DataDeleteResult} to a Tablesaw format.
     * @param dataDeleteResult Results of "data delete" operation.
     * @return Tabular representation of the results.
     */
    protected Table dataDeleteResultAsTable(DataDeleteResult dataDeleteResult) {
        Map<ParquetPartitionId, DataDeleteResultPartition> partitionMap = dataDeleteResult.getPartitionResults();
        Iterable<ParquetPartitionId> partitionIds = partitionMap.keySet().stream().sorted().collect(Collectors.toList());

        Table resultTable = Table.create().addColumns(
                TextColumn.create("Data type"),
                TextColumn.create("Connection"),
                TextColumn.create("Table"),
                DateColumn.create("Month"),
                IntColumn.create("Affected rows"),
                BooleanColumn.create("Partition deleted"));

        for (ParquetPartitionId id: partitionIds) {
            DataDeleteResultPartition dataDeleteResultPartition = partitionMap.get(id);

            Row row = resultTable.appendRow();
            row.setString(0, id.getTableType().name());
            row.setString(1, id.getConnectionName());
            row.setString(2, id.getTableName().toTableSearchFilter());
            row.setDate(3, id.getMonth());
            row.setInt(4, dataDeleteResultPartition.getRowsAffectedCount());
            row.setBoolean(5, dataDeleteResultPartition.isPartitionDeleted());
        }

        return resultTable;
    }
}
