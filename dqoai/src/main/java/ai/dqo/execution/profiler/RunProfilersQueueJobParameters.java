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
package ai.dqo.execution.profiler;

import ai.dqo.data.profilingresults.factory.ProfilerDataScope;
import ai.dqo.execution.profiler.progress.ProfilerExecutionProgressListener;
import ai.dqo.metadata.search.ProfilerSearchFilters;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Parameters object for the run the profiler job.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunProfilersQueueJobParameters {
    private ProfilerSearchFilters profilerSearchFilters;
    private ProfilerDataScope dataScope = ProfilerDataScope.table;
    @JsonIgnore
    private ProfilerExecutionProgressListener progressListener;
    private boolean dummySensorExecution;

    public RunProfilersQueueJobParameters() {
    }

    /**
     * Creates profiler run parameters.
     * @param profilerSearchFilters Profiler search filters.
     * @param progressListener Progress listener to receive events during the profiler execution.
     * @param dataScope Profiler data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution True when it is a dummy run, only for showing rendered sensor queries.
     */
    public RunProfilersQueueJobParameters(ProfilerSearchFilters profilerSearchFilters,
                                          ProfilerExecutionProgressListener progressListener,
                                          ProfilerDataScope dataScope,
                                          boolean dummySensorExecution) {
        this.profilerSearchFilters = profilerSearchFilters;
        this.progressListener = progressListener;
        this.dataScope = dataScope;
        this.dummySensorExecution = dummySensorExecution;
    }

    /**
     * Returns the profiler search filters.
     * @return Profilers search filters.
     */
    public ProfilerSearchFilters getProfilerSearchFilters() {
        return profilerSearchFilters;
    }

    /**
     * Progress listener that should be used to run the profilers.
     * @return Profiler progress listener.
     */
    public ProfilerExecutionProgressListener getProgressListener() {
        return progressListener;
    }

    /**
     * Returns the profiler data scope (whole table or each data stream separately).
     * @return Data scope configuration.
     */
    public ProfilerDataScope getDataScope() {
        return dataScope;
    }

    /**
     * Returns true if it should be just a dummy run without actually running any queries.
     * @return true when it is a dummy run.
     */
    public boolean isDummySensorExecution() {
        return dummySensorExecution;
    }
}
