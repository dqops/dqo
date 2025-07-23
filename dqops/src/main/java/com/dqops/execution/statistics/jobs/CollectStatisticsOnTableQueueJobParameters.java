/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.statistics.jobs;

import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.execution.statistics.progress.SilentStatisticsCollectorExecutionProgressListener;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Parameters object for the run the statistics collection job for a separate child job that collects statistics for each table separately, in order to improve concurrency.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CollectStatisticsOnTableQueueJobParameters implements Cloneable {
    /**
     * The name of the target connection.
     */
    @JsonPropertyDescription("The name of the target connection.")
    private String connection;

    /**
     * The maximum number of concurrent 'run checks on table' jobs that can be run on this connection. Limits the number of concurrent jobs.
     * Null value means that there are no limits applied.
     */
    @JsonPropertyDescription("The maximum number of concurrent 'run checks on table' jobs that can be run on this connection. Limits the number of concurrent jobs.")
    private Integer maxJobsPerConnection;

    /**
     * The full name of a target table.
     */
    @JsonPropertyDescription("The full physical name (schema.table) of the target table.")
    private PhysicalTableName table;

    /**
     * Statistics collectors search filters that identify the type of statistics collector to run.
     */
    @JsonPropertyDescription("Statistics collectors search filters that identify the type of statistics collector to run.")
    private StatisticsCollectorSearchFilters statisticsCollectorSearchFilters;

    /**
     * The target scope of collecting statistics. Statistics can be collected for the entire or for each data stream separately.
     */
    @JsonPropertyDescription("The target scope of collecting statistics. Statistics can be collected for the entire table or for each data grouping separately.")
    private StatisticsDataScope dataScope = StatisticsDataScope.table;

    /**
     * The default limit of column samples that are collected.
     */
    @JsonPropertyDescription("The default limit of column samples that are collected.")
    private Integer samplesLimit;

    /**
     * Turns on a special mode of collecting statistics that will configure the timestamp and ID columns. It should be used only during the first statistics collection.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonPropertyDescription("Turns on a special mode of collecting statistics that will configure the timestamp and ID columns. It should be used only during the first statistics collection.")
    private boolean configureTable;

    /**
     * Progress listener that will receive events during the statistics collection.
     */
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private StatisticsCollectorExecutionProgressListener progressListener = new SilentStatisticsCollectorExecutionProgressListener();

    /**
     * Boolean flag that enables a dummy statistics collection (sensors are executed, but the statistics results are not written to the parquet files).
     */
    @JsonPropertyDescription("Boolean flag that enables a dummy statistics collection (sensors are executed, but the statistics results are not written to the parquet files).")
    private boolean dummySensorExecution;

    /**
     * The summary of the statistics collection job after if finished. Returns the number of collectors analyzed, columns analyzed, statistics results captured.
     */
    @JsonPropertyDescription("The summary of the statistics collection job after if finished. Returns the number of collectors analyzed, columns analyzed, statistics results captured.")
    private CollectStatisticsResult collectStatisticsResult;

    public CollectStatisticsOnTableQueueJobParameters() {
    }

    /**
     * Creates statistics collection run parameters.
     * @param connection The name of the target connection.
     * @param maxJobsPerConnection The maximum number of concurrent 'run checks on table' jobs that can be run on this connection. Limits the number of concurrent jobs.
     * @param table The full physical name (schema.table) of the target table.
     * @param statisticsCollectorSearchFilters Statistics collector search filters.
     * @param progressListener Progress listener to receive events during the statistics collector execution.
     * @param dataScope Statistics data scope to analyze - the whole table or each data stream separately.
     * @param samplesLimit The limit of the number of samples to collect.
     * @param configureTable The columns on the table should be configured.
     * @param dummySensorExecution True when it is a dummy run, only for showing rendered sensor queries.
     */
    public CollectStatisticsOnTableQueueJobParameters(String connection,
                                                      Integer maxJobsPerConnection,
                                                      PhysicalTableName table,
                                                      StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
                                                      StatisticsCollectorExecutionProgressListener progressListener,
                                                      StatisticsDataScope dataScope,
                                                      Integer samplesLimit,
                                                      boolean configureTable,
                                                      boolean dummySensorExecution) {
        this.connection = connection;
        this.maxJobsPerConnection = maxJobsPerConnection;
        this.table = table;
        this.statisticsCollectorSearchFilters = statisticsCollectorSearchFilters;
        this.progressListener = progressListener;
        this.dataScope = dataScope;
        this.samplesLimit = samplesLimit;
        this.configureTable = configureTable;
        this.dummySensorExecution = dummySensorExecution;
    }

    /**
     * Returns the name of the target connection.
     * @return Target connection name.
     */
    public String getConnection() {
        return connection;
    }

    /**
     * Sets the name of the target connection.
     * @param connection Target connection name.
     */
    public void setConnection(String connection) {
        this.connection = connection;
    }

    /**
     * Returns the limit of concurrent jobs (collect statistics on table) that can be executed in parallel on this connection.
     * @return Maximum number of concurrent statistics collection executions on this connection.
     */
    public Integer getMaxJobsPerConnection() {
        return maxJobsPerConnection;
    }

    /**
     * Sets the limit of concurrent jobs (collect statistics on table) that can be executed in parallel on this connection.
     * @param maxJobsPerConnection Maximum number of concurrent statistics collection executions on this connection.
     */
    public void setMaxJobsPerConnection(Integer maxJobsPerConnection) {
        this.maxJobsPerConnection = maxJobsPerConnection;
    }

    /**
     * Gets the full physical name (schema.table) of the target table.
     * @return Physical name of the target table.
     */
    public PhysicalTableName getTable() {
        return table;
    }

    /**
     * Sets the full physical name (schema.table) of the target table.
     * @param table Physical name of the target table.
     */
    public void setTable(PhysicalTableName table) {
        this.table = table;
    }

    /**
     * Returns the statistics collectors search filters.
     * @return Statistics collectors search filters.
     */
    public StatisticsCollectorSearchFilters getStatisticsCollectorSearchFilters() {
        return statisticsCollectorSearchFilters;
    }

    /**
     * Sets the statistics collectors search filters.
     * @param statisticsCollectorSearchFilters Statistics collectors search filters.
     */
    public void setStatisticsCollectorSearchFilters(StatisticsCollectorSearchFilters statisticsCollectorSearchFilters) {
        this.statisticsCollectorSearchFilters = statisticsCollectorSearchFilters;
    }

    /**
     * Progress listener that should be used to run the statistics collectors.
     * @return Statistics collectors progress listener.
     */
    public StatisticsCollectorExecutionProgressListener getProgressListener() {
        return progressListener;
    }

    /**
     * Sets the progress listener that should be used to run the statistics collectors.
     * @param progressListener Statistics collectors progress listener.
     */
    public void setProgressListener(StatisticsCollectorExecutionProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    /**
     * Returns the statistics data scope (whole table or each data stream separately).
     * @return Data scope configuration.
     */
    public StatisticsDataScope getDataScope() {
        return dataScope;
    }

    /**
     * Sets the statistics data scope (whole table or each data stream separately).
     * @param dataScope Data scope configuration.
     */
    public void setDataScope(StatisticsDataScope dataScope) {
        this.dataScope = dataScope;
    }

    /**
     * Returns the limit of the column samples to capture.
     * @return The limit of columns samples.
     */
    public Integer getSamplesLimit() {
        return samplesLimit;
    }

    /**
     * Sets the limit of column samples to capture.
     * @param samplesLimit Column samples limit.
     */
    public void setSamplesLimit(Integer samplesLimit) {
        this.samplesLimit = samplesLimit;
    }

    /**
     * Returns true when the tables should be configured.
     * @return True when the tables should be configured.
     */
    public boolean isConfigureTable() {
        return configureTable;
    }

    /**
     * Sets the flag to configure the table.
     * @param configureTable True when the table should be configured.
     */
    public void setConfigureTable(boolean configureTable) {
        this.configureTable = configureTable;
    }

    /**
     * Returns true if it should be just a dummy run without actually running any queries.
     * @return true when it is a dummy run.
     */
    public boolean isDummySensorExecution() {
        return dummySensorExecution;
    }

    /**
     * Sets the flag if it should be just a dummy run without actually running any queries.
     * @param dummySensorExecution true when it is a dummy run.
     */
    public void setDummySensorExecution(boolean dummySensorExecution) {
        this.dummySensorExecution = dummySensorExecution;
    }

    /**
     * Returns the results of a finished job with the count of statistics that were collected.
     * @return The results of a finished job with the count of statistics that were collected.
     */
    public CollectStatisticsResult getCollectStatisticsResult() {
        return collectStatisticsResult;
    }

    /**
     * Sets the summary result of the statistics collection job, this method is called by the job when it finished successfully.
     * @param collectStatisticsResult The statistics collection result.
     */
    public void setCollectStatisticsResult(CollectStatisticsResult collectStatisticsResult) {
        this.collectStatisticsResult = collectStatisticsResult;
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public CollectStatisticsOnTableQueueJobParameters clone() {
        try {
            return (CollectStatisticsOnTableQueueJobParameters)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }
}
