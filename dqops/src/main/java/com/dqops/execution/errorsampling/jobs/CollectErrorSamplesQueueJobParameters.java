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
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Parameters object for the run the error samples collection job.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CollectErrorSamplesQueueJobParameters implements Cloneable {
    /**
     * Check search filters that identify the checks for which the error samples should be collected.
     */
    @JsonPropertyDescription("Check search filters that identify the checks for which the error samples should be collected.")
    private CheckSearchFilters checkSearchFilters;

    /**
     * Optional time window filter, configures the time range for partitioned tables that is analyzed to find error samples.
     */
    @JsonPropertyDescription("Optional time window filter, configures the time range for partitioned tables that is analyzed to find error samples.")
    private TimeWindowFilterParameters timeWindowFilter;

    /**
     * The target scope of collecting error samples. Error samples can be collected for the entire table or for each data grouping separately.
     */
    @JsonPropertyDescription("The target scope of collecting error samples. Error samples can be collected for the entire table or for each data grouping separately.")
    private ErrorSamplesDataScope dataScope = ErrorSamplesDataScope.table;

    /**
     * Progress listener that will receive events during the statistics collection.
     */
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private ErrorSamplerExecutionProgressListener progressListener = new SilentErrorSamplerExecutionProgressListener();

    /**
     * Boolean flag that enables a dummy error sample collection (sensors are executed, but the error samples results are not written to the parquet files).
     */
    @JsonPropertyDescription("Boolean flag that enables a dummy error sample collection (sensors are executed, but the error samples results are not written to the parquet files).")
    private boolean dummySensorExecution;

    /**
     * The summary of the error sampling collection job after if finished. Returns the number of error samplers executed, columns analyzed, error samples (values) captured.
     */
    @JsonPropertyDescription("The summary of the error sampling collection job after if finished. Returns the number of error samplers executed, columns analyzed, error samples (values) captured.")
    private ErrorSamplerResult errorSamplerResult;

    public CollectErrorSamplesQueueJobParameters() {
    }

    /**
     * Creates error sampler run parameters.
     * @param checkSearchFilters Check search filters to identify target checks whose error samples are collected.
     * @param timeWindowFilter Optional time window parameter to limit error sampling for a time period.
     * @param progressListener Progress listener to receive events during the error samples collector execution.
     * @param dataScope Error samples data scope to analyze - the whole table or each data grouping separately.
     * @param dummySensorExecution True when it is a dummy run, only for showing rendered sensor queries.
     */
    public CollectErrorSamplesQueueJobParameters(CheckSearchFilters checkSearchFilters,
                                                 TimeWindowFilterParameters timeWindowFilter,
                                                 ErrorSamplerExecutionProgressListener progressListener,
                                                 ErrorSamplesDataScope dataScope,
                                                 boolean dummySensorExecution) {
        this.checkSearchFilters = checkSearchFilters;
        this.timeWindowFilter = timeWindowFilter;
        this.progressListener = progressListener;
        this.dataScope = dataScope;
        this.dummySensorExecution = dummySensorExecution;
    }

    /**
     * Returns the check search filters that identify checks for which we collect error samples.
     * @return Check search filters.
     */
    public CheckSearchFilters getCheckSearchFilters() {
        return checkSearchFilters;
    }

    /**
     * Returns an optional time window filter to restrict error sample collection to a time period.
     * @return Optional time window filter.
     */
    public TimeWindowFilterParameters getTimeWindowFilter() {
        return timeWindowFilter;
    }

    /**
     * Progress listener that should be used to run the error samplers.
     * @return Error sampler progress listener.
     */
    public ErrorSamplerExecutionProgressListener getProgressListener() {
        return progressListener;
    }

    /**
     * Returns the error sampling data scope (whole table or each data grouping separately).
     * @return Data scope configuration.
     */
    public ErrorSamplesDataScope getDataScope() {
        return dataScope;
    }

    /**
     * Returns true if it should be just a dummy run without actually running any queries.
     * @return true when it is a dummy run.
     */
    public boolean isDummySensorExecution() {
        return dummySensorExecution;
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
    public CollectErrorSamplesQueueJobParameters clone() {
        try {
            return (CollectErrorSamplesQueueJobParameters)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }
}
