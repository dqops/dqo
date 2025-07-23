/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.cli.commands.data.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.models.DataDeleteResultPartition;
import com.dqops.data.storage.ParquetPartitionId;
import com.dqops.utils.exceptions.DqoRuntimeException;
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
    private DqoUserPrincipalProvider principalProvider;

    @Autowired
    public DataCliServiceImpl(DqoJobQueue dqoJobQueue, DqoQueueJobFactory dqoQueueJobFactory,
                              DqoUserPrincipalProvider principalProvider) {
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
        DqoUserPrincipal principal = this.principalProvider.getLocalUserPrincipal();
        PushJobResult<DeleteStoredDataResult> pushJobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob, principal);

        DeleteStoredDataResult deleteStoredDataResult = null;

        try {
            deleteStoredDataResult= pushJobResult.getFinishedFuture().get();
            cliOperationStatus.setSuccessMessage(String.format("%d affected partitions.", deleteStoredDataResult.getPartitionResults().size()));
        } catch (ExecutionException e) {
            throw new DqoRuntimeException(e);
        } catch (InterruptedException e) {
            throw new DqoRuntimeException(e);
        }

        if (deleteStoredDataResult != null) {
            cliOperationStatus.setTable(dataDeleteResultAsTable(deleteStoredDataResult));
        }
        return cliOperationStatus;
    }

    /**
     * Convert {@link DeleteStoredDataResult} to a Tablesaw format.
     * @param deleteStoredDataResult Results of "data delete" operation.
     * @return Tabular representation of the results.
     */
    protected Table dataDeleteResultAsTable(DeleteStoredDataResult deleteStoredDataResult) {
        Map<ParquetPartitionId, DataDeleteResultPartition> partitionMap = deleteStoredDataResult.getPartitionResults();
        Iterable<ParquetPartitionId> partitionIds = partitionMap.keySet().stream().sorted().collect(Collectors.toList());

        Table resultTable = Table.create().addColumns(
                StringColumn.create("Data type"),
                StringColumn.create("Connection"),
                StringColumn.create("Table"),
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
