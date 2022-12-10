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

import ai.dqo.core.jobqueue.DqoJobExecutionContext;
import ai.dqo.core.jobqueue.DqoJobType;
import ai.dqo.core.jobqueue.DqoQueueJob;
import ai.dqo.core.jobqueue.JobConcurrencyConstraint;
import ai.dqo.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import ai.dqo.data.profilingresults.factory.ProfilerDataScope;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.ExecutionContextFactory;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.RunChecksQueueJobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Run profiler queue job.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RunProfilersQueueJob extends DqoQueueJob<ProfilerExecutionSummary> {
    private ExecutionContextFactory executionContextFactory;
    private ProfilerExecutionService profilerExecutionService;
    private RunProfilersQueueJobParameters parameters;

    @Autowired
    public RunProfilersQueueJob(
            ExecutionContextFactory executionContextFactory,
            ProfilerExecutionService profilerExecutionService) {
        this.executionContextFactory = executionContextFactory;
        this.profilerExecutionService = profilerExecutionService;
    }

    /**
     * Returns the job parameters.
     * @return Job parameters.
     */
    public RunProfilersQueueJobParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the job parameters.
     * @param parameters Job parameters.
     */
    public void setParameters(RunProfilersQueueJobParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public ProfilerExecutionSummary onExecute(DqoJobExecutionContext jobExecutionContext) {
        ExecutionContext executionContext = this.executionContextFactory.create();
        ProfilerExecutionSummary profilerExecutionSummary = this.profilerExecutionService.executeProfilers(
                executionContext,
                this.parameters.getProfilerSearchFilters(),
                this.parameters.getProgressListener(),
                this.parameters.getDataScope(),
                this.parameters.isDummySensorExecution());
        return profilerExecutionSummary;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.RUN_PROFILERS;
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint getConcurrencyConstraint() {
        return null; // user can start any number of "run check" operations, the concurrency will be applied later on a table level
    }

    /**
     * Creates a typed parameters model that could be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel() {{
            setRunProfilersParameters(parameters);
        }};
    }
}
