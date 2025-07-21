/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.jobs.data;

import com.dqops.core.jobqueue.*;
import com.dqops.core.jobqueue.concurrency.ConcurrentJobType;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyTarget;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.UserDomainIdentity;
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
import tech.tablesaw.api.StringColumn;

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
        UserDomainIdentity userIdentity = this.getPrincipal().getDataDomainIdentity();

        if (this.repairParameters.isRepairErrors()) {
            // Load and ignore results to force automatic repair of corrupted data.
            this.loadAndFixMonthlyPartitions(ErrorsSnapshot.createErrorsStorageSettings(), userIdentity);
        }
        if (this.repairParameters.isRepairStatistics()) {
            this.loadAndFixMonthlyPartitions(StatisticsSnapshot.createStatisticsStorageSettings(), userIdentity);
        }
        if (this.repairParameters.isRepairCheckResults()) {
            this.loadAndFixMonthlyPartitions(CheckResultsSnapshot.createCheckResultsStorageSettings(), userIdentity);
        }
        if (this.repairParameters.isRepairSensorReadouts()) {
            this.loadAndFixMonthlyPartitions(SensorReadoutsSnapshot.createSensorReadoutsStorageSettings(), userIdentity);
        }

        return result;
    }

    /**
     * Load all partitions fitting the <code>repairParameters</code> for given storage settings.
     * @param fileStorageSettings File storage settings.
     * @param userIdentity        User identity that specifies the data domain.
     * @return Map of loaded partitions for existing partition ids. All columns are loaded for the partitions.
     */
    protected void loadAndFixMonthlyPartitions(FileStorageSettings fileStorageSettings, UserDomainIdentity userIdentity) {
        SearchPattern connectionSearchPattern = SearchPattern.create(false, this.repairParameters.getConnectionName());
        if (connectionSearchPattern.isWildcardSearchPattern()) {
            List<String> connectionNamesList = this.parquetPartitionMetadataService.listConnections(fileStorageSettings, userIdentity);
            for (String connectionName : connectionNamesList) {
                if (connectionSearchPattern.match(connectionName)) {
                    loadAndFixMonthlyPartitions(fileStorageSettings, connectionName, userIdentity);
                }
            }
        } else {
            loadAndFixMonthlyPartitions(fileStorageSettings, this.repairParameters.getConnectionName(), userIdentity);
        }
    }

    /**
     * Load all partitions fitting the <code>repairParameters</code> for given storage settings.
     * @param fileStorageSettings File storage settings.
     * @param connectionName Connection name to fix.
     * @param userIdentity User identity that specifies the data domain.
     * @return Map of loaded partitions for existing partition ids. All columns are loaded for the partitions.
     */
    protected void loadAndFixMonthlyPartitions(FileStorageSettings fileStorageSettings, String connectionName, UserDomainIdentity userIdentity) {
        List<ParquetPartitionId> partitionIds;
        if (this.repairParameters.getSchemaTableName() == null) {
            partitionIds = this.parquetPartitionMetadataService.getStoredPartitionsIds(
                    connectionName, fileStorageSettings, userIdentity);
        } else {
            partitionIds = this.parquetPartitionMetadataService.getStoredPartitionsIds(
                    connectionName,
                    PhysicalTableName.fromSchemaTableFilter(this.repairParameters.getSchemaTableName()),
                    fileStorageSettings,
                    userIdentity);
        }

        if (partitionIds == null) {
            return;
        }

        for (ParquetPartitionId partitionId: partitionIds) {
            // columnNames is null in order to force parquetPartitionStorageService to load all columns.
            LoadedMonthlyPartition loadedMonthlyPartition = this.parquetPartitionStorageService.loadPartition(
                    partitionId, fileStorageSettings, null, userIdentity);

            if (loadedMonthlyPartition.getData() != null && loadedMonthlyPartition.getData().rowCount() != 0) {
                StringColumn idColumn = loadedMonthlyPartition.getData().stringColumn(fileStorageSettings.getIdStringColumnName());
                if (idColumn.countUnique() != idColumn.size()) {
                    // duplicates found, removing the partition
                    this.parquetPartitionStorageService.deletePartitionFile(loadedMonthlyPartition.getPartitionId(), fileStorageSettings, userIdentity);
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
        return DqoJobType.repair_stored_data;
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
