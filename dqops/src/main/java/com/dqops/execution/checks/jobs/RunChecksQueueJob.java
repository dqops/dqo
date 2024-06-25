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
package com.dqops.execution.checks.jobs;

import com.dqops.core.jobqueue.DqoJobExecutionContext;
import com.dqops.core.jobqueue.DqoJobType;
import com.dqops.core.jobqueue.ParentDqoQueueJob;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.checks.CheckExecutionService;
import com.dqops.execution.checks.CheckExecutionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Run checks queue job.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RunChecksQueueJob extends ParentDqoQueueJob<CheckExecutionSummary> {
    private ExecutionContextFactory executionContextFactory;
    private CheckExecutionService checkExecutionService;
    private RunChecksParameters parameters;

    @Autowired
    public RunChecksQueueJob(
            ExecutionContextFactory executionContextFactory,
            CheckExecutionService checkExecutionService) {
        this.executionContextFactory = executionContextFactory;
        this.checkExecutionService = checkExecutionService;
    }

    /**
     * Returns the job parameters.
     * @return Job parameters.
     */
    public RunChecksParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the job parameters.
     * @param parameters Job parameters.
     */
    public void setParameters(RunChecksParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     *
     * @return Optional result value that can be returned by the job.
     */
    @Override
    public CheckExecutionSummary onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        UserDomainIdentity userDomainIdentity = this.getPrincipal().getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
        CheckExecutionSummary checkExecutionSummary = this.checkExecutionService.executeChecks(
                executionContext,
                this.parameters.getCheckSearchFilters(),
                this.parameters.getTimeWindowFilter(),
                this.parameters.isCollectErrorSamples(),
                this.parameters.getProgressListener(),
                this.parameters.isDummyExecution(),
                true,
                jobExecutionContext.getJobId(),
                jobExecutionContext.getCancellationToken(),
                this.getPrincipal());

        RunChecksResult jobResultSummary = RunChecksResult.fromCheckExecutionSummary(checkExecutionSummary);
        RunChecksParameters clonedParameters = this.getParameters().clone();
        clonedParameters.setRunChecksResult(jobResultSummary);
        this.setParameters(clonedParameters);

        return checkExecutionSummary;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.run_checks;
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        return null; // user can start any number of "run check" operations, the concurrency will be applied later on a table level
    }

    /**
     * Creates a typed parameters model that can be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel() {{
            setRunChecksParameters(parameters);
        }};
    }
}
