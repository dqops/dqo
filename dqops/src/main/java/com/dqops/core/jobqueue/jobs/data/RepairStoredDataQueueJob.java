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
package com.dqops.core.jobqueue.jobs.data;

import com.dqops.core.jobqueue.*;
import com.dqops.core.jobqueue.concurrency.ConcurrentJobType;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyTarget;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshot;
import com.dqops.data.errors.snapshot.ErrorsSnapshot;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.data.statistics.snapshot.StatisticsSnapshot;
import com.dqops.data.storage.*;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.dqops.metadata.sources.PhysicalTableName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.TextColumn;

import java.util.List;

/**
 * Queue job that repairs data stored in user's ".data" directory.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class RepairStoredDataQueueJob extends DqoQueueJob<RepairStoredDataQueueJobResult> {
    private ParquetPartitionStorageService parquetPartitionStorageService;
    private ParquetPartitionMetadataService parquetPartitionMetadataService;
    private RepairStoredDataQueueJobParameters repairParameters;

    @Autowired
    public RepairStoredDataQueueJob(ParquetPartitionStorageService parquetPartitionStorageService,
                                    ParquetPartitionMetadataService parquetPartitionMetadataService) {
        this.parquetPartitionStorageService = parquetPartitionStorageService;
        this.parquetPartitionMetadataService = parquetPartitionMetadataService;
    }

    /**
     * Returns the repair parameters object.
     * @return Repair parameters object.
     */
    public RepairStoredDataQueueJobParameters getRepairParameters() {
        return repairParameters;
    }

    /**
     * Sets the parameters object for the job that specify the data that needs to be removed from ".data" directory.
     * @param repairParameters Repair parameters to store.
     */
    public void setRepairParameters(RepairStoredDataQueueJobParameters repairParameters) {
        this.repairParameters = repairParameters;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public RepairStoredDataQueueJobResult onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        RepairStoredDataQueueJobResult result = new RepairStoredDataQueueJobResult();

        if (this.repairParameters.isRepairErrors()) {
            // Load and ignore results to force automatic repair of corrupted data.
            this.loadAndFixMonthlyPartitions(ErrorsSnapshot.createErrorsStorageSettings());
        }
        if (this.repairParameters.isRepairStatistics()) {
            this.loadAndFixMonthlyPartitions(StatisticsSnapshot.createStatisticsStorageSettings());
        }
        if (this.repairParameters.isRepairCheckResults()) {
            this.loadAndFixMonthlyPartitions(CheckResultsSnapshot.createCheckResultsStorageSettings());
        }
        if (this.repairParameters.isRepairSensorReadouts()) {
            this.loadAndFixMonthlyPartitions(SensorReadoutsSnapshot.createSensorReadoutsStorageSettings());
        }

        return result;
    }

    /**
     * Load all partitions fitting the <code>repairParameters</code> for given storage settings.
     * @param fileStorageSettings File storage settings.
     * @return Map of loaded partitions for existing partition ids. All columns are loaded for the partitions.
     */
    protected void loadAndFixMonthlyPartitions(FileStorageSettings fileStorageSettings) {
        SearchPattern connectionSearchPattern = SearchPattern.create(false, this.repairParameters.getConnectionName());
        if (connectionSearchPattern.isWildcardSearchPattern()) {
            List<String> connectionNamesList = this.parquetPartitionMetadataService.listConnections(fileStorageSettings);
            for (String connectionName : connectionNamesList) {
                if (connectionSearchPattern.match(connectionName)) {
                    loadAndFixMonthlyPartitions(fileStorageSettings, connectionName);
                }
            }
        } else {
            loadAndFixMonthlyPartitions(fileStorageSettings, this.repairParameters.getConnectionName());
        }
    }

    /**
     * Load all partitions fitting the <code>repairParameters</code> for given storage settings.
     * @param fileStorageSettings File storage settings.
     * @return Map of loaded partitions for existing partition ids. All columns are loaded for the partitions.
     */
    protected void loadAndFixMonthlyPartitions(FileStorageSettings fileStorageSettings, String connectionName) {
        List<ParquetPartitionId> partitionIds;
        if (this.repairParameters.getSchemaTableName() == null) {
            partitionIds = this.parquetPartitionMetadataService.getStoredPartitionsIds(
                    connectionName, fileStorageSettings);
        } else {
            partitionIds = this.parquetPartitionMetadataService.getStoredPartitionsIds(
                    connectionName,
                    PhysicalTableName.fromSchemaTableFilter(this.repairParameters.getSchemaTableName()),
                    fileStorageSettings);
        }

        if (partitionIds == null) {
            return;
        }

        for (ParquetPartitionId partitionId: partitionIds) {
            // columnNames is null in order to force parquetPartitionStorageService to load all columns.
            LoadedMonthlyPartition loadedMonthlyPartition = this.parquetPartitionStorageService.loadPartition(
                    partitionId, fileStorageSettings, null);

            if (loadedMonthlyPartition.getData() != null && loadedMonthlyPartition.getData().rowCount() != 0) {
                TextColumn idColumn = loadedMonthlyPartition.getData().textColumn(fileStorageSettings.getIdStringColumnName());
                if (idColumn.countUnique() != idColumn.size()) {
                    // duplicates found, removing the partition
                    this.parquetPartitionStorageService.deletePartitionFile(loadedMonthlyPartition.getPartitionId(), fileStorageSettings);
                    log.warn("Parquet file with duplicate rows having the same ID was identified, the parquet file was removed: " + loadedMonthlyPartition.getPartitionId().toString());
                }
            }
        }
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.REPAIR_STORED_DATA;
    }

    /**
     * Creates a typed parameters model that could be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel()
        {{
            setRepairStoredDataParameters(repairParameters);
        }};
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        RepairStoredDataQueueJobConcurrencyTarget target = new RepairStoredDataQueueJobConcurrencyTarget(
                this.repairParameters.getConnectionName());
        JobConcurrencyTarget concurrencyTarget = new JobConcurrencyTarget(ConcurrentJobType.REPAIR_STORED_DATA, target);
        JobConcurrencyConstraint repairLimit = new JobConcurrencyConstraint(concurrencyTarget, 1);
        return new JobConcurrencyConstraint[] { repairLimit };
    }
}
