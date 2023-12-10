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
package com.dqops.core.scheduler.runcheck;

import com.dqops.cli.terminal.TerminalTableWritter;
import com.dqops.core.jobqueue.DqoJobExecutionContext;
import com.dqops.core.jobqueue.DqoJobType;
import com.dqops.core.jobqueue.ParentDqoQueueJob;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.checks.CheckExecutionService;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.checks.progress.CheckExecutionProgressListenerProvider;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * DQOps job that runs all scheduled checks for one CRON schedule within the job scheduler (quartz).
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RunScheduledChecksDqoJob extends ParentDqoQueueJob<CheckExecutionSummary> {
    private JobSchedulerService jobSchedulerService;
    private CheckExecutionService checkExecutionService;
    private ExecutionContextFactory executionContextFactory;
    private CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider;
    private TerminalTableWritter terminalTableWritter;
    private MonitoringScheduleSpec cronSchedule;

    /**
     * Creates a dqo job that runs checks scheduled for one cron expression.
     * @param jobSchedulerService Job scheduler service - used to ask for the reporting mode.
     * @param checkExecutionService Check execution service that runs the data quality checks.
     * @param executionContextFactory Check execution context that will create a context - opening the local user home.
     * @param checkExecutionProgressListenerProvider Check execution progress listener used to get the correct logger.
     * @param terminalTableWriter Terminal table writer - used to write the summary information about the progress to the console.
     */
    @Autowired
    public RunScheduledChecksDqoJob(JobSchedulerService jobSchedulerService,
                                    CheckExecutionService checkExecutionService,
                                    ExecutionContextFactory executionContextFactory,
                                    CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider,
                                    TerminalTableWritter terminalTableWriter) {
        this.jobSchedulerService = jobSchedulerService;
        this.checkExecutionService = checkExecutionService;
        this.executionContextFactory = executionContextFactory;
        this.checkExecutionProgressListenerProvider = checkExecutionProgressListenerProvider;
        this.terminalTableWritter = terminalTableWriter;
    }

    /**
     * Cron schedule (cron expression and time zone) that is executed.
     * @return Cron schedule.
     */
    public MonitoringScheduleSpec getCronSchedule() {
        return cronSchedule;
    }

    /**
     * Sets the cron schedule that is triggered and whose checks are executed.
     * @param cronSchedule Cron schedule.
     */
    public void setCronSchedule(MonitoringScheduleSpec cronSchedule) {
        this.cronSchedule = cronSchedule;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public CheckExecutionSummary onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        CheckRunReportingMode checkRunReportingMode = this.jobSchedulerService.getCheckRunReportingMode();

        UserDomainIdentity userDomainIdentity = this.getPrincipal().getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);

        CheckExecutionProgressListener progressListener = this.checkExecutionProgressListenerProvider.getProgressListener(
                checkRunReportingMode, true);
        CheckExecutionSummary checkExecutionSummary = this.checkExecutionService.executeChecksForSchedule(
                executionContext, this.cronSchedule, progressListener, jobExecutionContext.getJobId(),
                jobExecutionContext.getCancellationToken(),
                this.getPrincipal());

        return checkExecutionSummary;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.run_scheduled_checks_cron;
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
     * Creates a typed parameters model that could be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel() {{
           setRunScheduledChecksParameters(cronSchedule);
        }};
    }
}
