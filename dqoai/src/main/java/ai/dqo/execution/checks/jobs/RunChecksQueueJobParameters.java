/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.execution.checks.jobs;

import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.sensors.TimeWindowFilterParameters;
import ai.dqo.metadata.search.CheckSearchFilters;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

/**
 * Parameters object for the run checks job.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "RunChecksQueueJobParameters", description = "Run checks configuration, specifies the target checks that should be executed and an optional time window.")
@EqualsAndHashCode(callSuper = false)
public class RunChecksQueueJobParameters {
    /**
     * Target data quality checks filter.
     */
    @JsonPropertyDescription("Target data quality checks filter.")
    private CheckSearchFilters checkSearchFilters;

    /**
     * Optional time window filter, configures the time range that is analyzed or the number of recent days/months to analyze for day or month partitioned data.
     */
    @JsonPropertyDescription("Optional time window filter, configures the time range that is analyzed or the number of recent days/months to analyze for day or month partitioned data.")
    private TimeWindowFilterParameters timeWindowFilter;

    /**
     * Job progress listener that will receive events showing the progress of execution.
     */
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private CheckExecutionProgressListener progressListener;

    /**
     * Set the value to true when the data quality checks should be executed in a dummy mode (without running checks on the target systems and storing the results). Only the jinja2 sensors will be rendered.
     */
    @JsonPropertyDescription("Set the value to true when the data quality checks should be executed in a dummy mode (without running checks on the target systems and storing the results). Only the jinja2 sensors will be rendered.")
    private boolean dummyExecution;

    /**
     * The result of running the check, updated when the run checks job finishes. Contains the count of executed checks.
     */
    @JsonPropertyDescription("The result of running the check, updated when the run checks job finishes. Contains the count of executed checks.")
    private volatile RunChecksQueueJobResult result;

    /**
     * Default constructor.
     */
    public RunChecksQueueJobParameters() {
    }

    /**
     * Creates a check run parameters.
     * @param checkSearchFilters Check search filters.
     * @param timeWindowFilter Optional user provided time window filters, used to restrict the time range that is analyzed.
     * @param progressListener Progress listener to receive events during the check execution.
     * @param dummyExecution True when it is a dummy run, only for showing rendered sensor queries.
     */
    public RunChecksQueueJobParameters(CheckSearchFilters checkSearchFilters,
                                       TimeWindowFilterParameters timeWindowFilter,
                                       CheckExecutionProgressListener progressListener,
                                       boolean dummyExecution) {
        this.checkSearchFilters = checkSearchFilters;
        this.timeWindowFilter = timeWindowFilter;
        this.progressListener = progressListener;
        this.dummyExecution = dummyExecution;
    }

    /**
     * Returns the checks search filters.
     * @return Check search filters.
     */
    public CheckSearchFilters getCheckSearchFilters() {
        return checkSearchFilters;
    }

    /**
     * Sets the check search filters.
     * @param checkSearchFilters Check search filters.
     */
    public void setCheckSearchFilters(CheckSearchFilters checkSearchFilters) {
        this.checkSearchFilters = checkSearchFilters;
    }

    /**
     * Returns an optional filter that restricts the time period that is analyzed.
     * @return Optional time window filter.
     */
    public TimeWindowFilterParameters getTimeWindowFilter() {
        return timeWindowFilter;
    }

    /**
     * Sets the user configuration of a time window.
     * @param timeWindowFilter Time window filter.
     */
    public void setTimeWindowFilter(TimeWindowFilterParameters timeWindowFilter) {
        this.timeWindowFilter = timeWindowFilter;
    }

    /**
     * Progress listener that should be used to run the checks.
     * @return Check progress listener.
     */
    public CheckExecutionProgressListener getProgressListener() {
        return progressListener;
    }

    /**
     * Sets a reference to a progress listener that will be used during the check execution.
     * @param progressListener Progress listener.
     */
    public void setProgressListener(CheckExecutionProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    /**
     * Returns true if it should be just a dummy run without actually running any queries.
     * @return true when it is a dummy run.
     */
    public boolean isDummyExecution() {
        return dummyExecution;
    }

    /**
     * Sets the flag to run the checks in a dummy mode (just simulation).
     * @param dummyExecution Run the checks in a dummy mode.
     */
    public void setDummyExecution(boolean dummyExecution) {
        this.dummyExecution = dummyExecution;
    }

    /**
     * Returns the result of running the check, updated when the run checks job finishes. Contains the count of executed checks.
     * @return The job result object.
     */
    public RunChecksQueueJobResult getResult() {
        return result;
    }

    /**
     * Sets the result of running the check, updated when the run checks job finishes. Contains the count of executed checks.
     * @param result The new job result object.
     */
    public void setResult(RunChecksQueueJobResult result) {
        this.result = result;
    }
}
