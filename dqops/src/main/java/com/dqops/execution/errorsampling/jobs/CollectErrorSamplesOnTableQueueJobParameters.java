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
package com.dqops.execution.errorsampling.jobs;

import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.errorsampling.progress.SilentErrorSamplerExecutionProgressListener;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Parameters object for running the error sampler job as a separate child job that collects error samples for one table separately, in order to improve concurrency.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CollectErrorSamplesOnTableQueueJobParameters implements Cloneable {
    /**
     * The name of the target connection.
     */
    @JsonPropertyDescription("The name of the target connection.")
    private String connection;

    /**
     * The maximum number of concurrent 'run checks on table' jobs that can be run on this connection. Limits the number of concurrent jobs.
     * Null value means that there are no limits applied.
     */
    @JsonPropertyDescription("The maximum number of concurrent 'collect error samples on table' jobs that can be run on this connection. Limits the number of concurrent jobs.")
    private Integer maxJobsPerConnection;

    /**
     * The full name of a target table.
     */
    @JsonPropertyDescription("The full physical name (schema.table) of the target table.")
    private PhysicalTableName table;

    /**
     * Check search filters that identify data quality checks for which the error samples are collected.
     */
    @JsonPropertyDescription("Check search filters that identify data quality checks for which the error samples are collected.")
    private CheckSearchFilters checkSearchFilters;

    /**
     * Optional time window filter, configures the time range for partitioned tables that is analyzed to find error samples.
     */
    @JsonPropertyDescription("Optional time window filter, configures the time range for partitioned tables that is analyzed to find error samples.")
    private TimeWindowFilterParameters timeWindowFilter;

    /**
     * The target scope of collecting error samples. Error samples can be collected for the entire or for each data grouping separately.
     */
    @JsonPropertyDescription("The target scope of collecting error samples. Error samples can be collected for the entire or for each data grouping separately.")
    private ErrorSamplesDataScope dataScope = ErrorSamplesDataScope.table;

    /**
     * Progress listener that will receive events during the error samples collection.
     */
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private ErrorSamplerExecutionProgressListener progressListener = new SilentErrorSamplerExecutionProgressListener();

    /**
     * Boolean flag that enables a dummy error samples collection (sensors are executed, but the error samples results are not written to the parquet files).
     */
    @JsonPropertyDescription("Boolean flag that enables a dummy error samples collection (sensors are executed, but the error samples results are not written to the parquet files).")
    private boolean dummySensorExecution;

    /**
     * The summary of the error sampling collection job after if finished. Returns the number of error samplers that collected samples, columns analyzed, error samples (values) captured.
     */
    @JsonPropertyDescription("The summary of the error sampling collection job after if finished. Returns the number of error samplers that collected samples, columns analyzed, error samples (values) captured.")
    private ErrorSamplerResult errorSamplerResult;

    public CollectErrorSamplesOnTableQueueJobParameters() {
    }

    /**
     * Creates statistics collection run parameters.
     * @param connection The name of the target connection.
     * @param maxJobsPerConnection The maximum number of concurrent 'collect error samples on table' jobs that can be run on this connection. Limits the number of concurrent jobs.
     * @param table The full physical name (schema.table) of the target table.
     * @param checkSearchFilters Check search filters.
     * @param timeWindowFilter Optional time window parameter to limit error sampling for a time period.
     * @param progressListener Progress listener to receive events during the error samples collector execution.
     * @param dataScope Error samples data scope to analyze - the whole table or each data grouping separately.
     * @param dummySensorExecution True when it is a dummy run, only for showing rendered sensor queries.
     */
    public CollectErrorSamplesOnTableQueueJobParameters(String connection,
                                                        Integer maxJobsPerConnection,
                                                        PhysicalTableName table,
                                                        CheckSearchFilters checkSearchFilters,
                                                        TimeWindowFilterParameters timeWindowFilter,
                                                        ErrorSamplerExecutionProgressListener progressListener,
                                                        ErrorSamplesDataScope dataScope,
                                                        boolean dummySensorExecution) {
        this.connection = connection;
        this.maxJobsPerConnection = maxJobsPerConnection;
        this.table = table;
        this.checkSearchFilters = checkSearchFilters;
        this.timeWindowFilter = timeWindowFilter;
        this.progressListener = progressListener;
        this.dataScope = dataScope;
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
     * Returns the limit of concurrent jobs (collect error samples on table) that can be executed in parallel on this connection.
     * @return Maximum number of concurrent error sampler collection executions on this connection.
     */
    public Integer getMaxJobsPerConnection() {
        return maxJobsPerConnection;
    }

    /**
     * Sets the limit of concurrent jobs (collect error samples on table) that can be executed in parallel on this connection.
     * @param maxJobsPerConnection Maximum number of concurrent error samples collection executions on this connection.
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
     * Returns the check search filters to select for which checks we want to capture error samples.
     * @return Check search filters.
     */
    public CheckSearchFilters getCheckSearchFilters() {
        return checkSearchFilters;
    }

    /**
     * Sets the check search filters to select target collectors.
     * @param checkSearchFilters Error sample collectors (checks) search filters.
     */
    public void setCheckSearchFilters(CheckSearchFilters checkSearchFilters) {
        this.checkSearchFilters = checkSearchFilters;
    }

    /**
     * Returns an optional time window filter to restrict error sample collection to a time period.
     * @return Optional time window filter.
     */
    public TimeWindowFilterParameters getTimeWindowFilter() {
        return timeWindowFilter;
    }

    /**
     * Sets an optional time window filter to limit sample collection to a time window.
     * @param timeWindowFilter Time window filter.
     */
    public void setTimeWindowFilter(TimeWindowFilterParameters timeWindowFilter) {
        this.timeWindowFilter = timeWindowFilter;
    }

    /**
     * Progress listener that should be used to run the error samplers.
     * @return Error samplers progress listener.
     */
    public ErrorSamplerExecutionProgressListener getProgressListener() {
        return progressListener;
    }

    /**
     * Sets the progress listener that should be used to run the error samples collectors.
     * @param progressListener Error samples collectors progress listener.
     */
    public void setProgressListener(ErrorSamplerExecutionProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    /**
     * Returns the error samples collection data scope (whole table or each data stream separately).
     * @return Data scope configuration.
     */
    public ErrorSamplesDataScope getDataScope() {
        return dataScope;
    }

    /**
     * Sets the error samples collection data scope (whole table or each data stream separately).
     * @param dataScope Data scope configuration.
     */
    public void setDataScope(ErrorSamplesDataScope dataScope) {
        this.dataScope = dataScope;
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
     * Returns the results of a finished job with the count of error samples that were collected.
     * @return The results of a finished job with the count of error samples that were collected.
     */
    public ErrorSamplerResult getErrorSamplerResult() {
        return errorSamplerResult;
    }

    /**
     * Sets the summary result of the error samples collection job, this method is called by the job when it finished successfully.
     * @param errorSamplerResult The error samples collection result.
     */
    public void setErrorSamplerResult(ErrorSamplerResult errorSamplerResult) {
        this.errorSamplerResult = errorSamplerResult;
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public CollectErrorSamplesOnTableQueueJobParameters clone() {
        try {
            return (CollectErrorSamplesOnTableQueueJobParameters)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }
}
