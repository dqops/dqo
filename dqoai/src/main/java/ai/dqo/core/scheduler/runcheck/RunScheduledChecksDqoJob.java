package ai.dqo.core.scheduler.runcheck;

import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.core.jobqueue.BaseDqoQueueJob;
import ai.dqo.core.jobqueue.DqoJobType;
import ai.dqo.core.scheduler.JobSchedulerService;
import ai.dqo.core.scheduler.schedules.RunChecksCronSchedule;
import ai.dqo.core.scheduler.synchronization.SchedulerFileSynchronizationService;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.CheckExecutionContextFactory;
import ai.dqo.execution.checks.CheckExecutionService;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListenerProvider;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * DQO job that runs all scheduled checks for one CRON schedule within the job scheduler (quartz).
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RunScheduledChecksDqoJob extends BaseDqoQueueJob<Void> {
    private JobSchedulerService jobSchedulerService;
    private CheckExecutionService checkExecutionService;
    private CheckExecutionContextFactory checkExecutionContextFactory;
    private CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider;
    private SchedulerFileSynchronizationService schedulerFileSynchronizationService;
    private TerminalTableWritter terminalTableWritter;
    private RunChecksCronSchedule cronSchedule;

    /**
     * Creates a dqo job that runs checks scheduled for one cron expression.
     * @param jobSchedulerService Job scheduler service - used to ask for the reporting mode.
     * @param checkExecutionService Check execution service that runs the data quality checks.
     * @param checkExecutionContextFactory Check execution context that will create a context - opening the local user home.
     * @param checkExecutionProgressListenerProvider Check execution progress listener used to get the correct logger.
     * @param schedulerFileSynchronizationService Scheduler file synchronization service.
     * @param terminalTableWriter Terminal table writer - used to write the summary information about the progress to the console.
     */
    @Autowired
    public RunScheduledChecksDqoJob(JobSchedulerService jobSchedulerService,
                                    CheckExecutionService checkExecutionService,
                                    CheckExecutionContextFactory checkExecutionContextFactory,
                                    CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider,
                                    SchedulerFileSynchronizationService schedulerFileSynchronizationService,
                                    TerminalTableWritter terminalTableWriter) {
        this.jobSchedulerService = jobSchedulerService;
        this.checkExecutionService = checkExecutionService;
        this.checkExecutionContextFactory = checkExecutionContextFactory;
        this.checkExecutionProgressListenerProvider = checkExecutionProgressListenerProvider;
        this.schedulerFileSynchronizationService = schedulerFileSynchronizationService;
        this.terminalTableWritter = terminalTableWriter;
    }

    /**
     * Cron schedule (cron expression and time zone) that is executed.
     * @return Cron schedule.
     */
    public RunChecksCronSchedule getCronSchedule() {
        return cronSchedule;
    }

    /**
     * Sets the cron schedule that is triggered and whose checks are executed.
     * @param cronSchedule Cron schedule.
     */
    public void setCronSchedule(RunChecksCronSchedule cronSchedule) {
        this.cronSchedule = cronSchedule;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public Void onExecute() {
        FileSystemSynchronizationReportingMode synchronizationMode = this.jobSchedulerService.getSynchronizationMode();
        CheckRunReportingMode checkRunReportingMode = this.jobSchedulerService.getCheckRunReportingMode();

        this.schedulerFileSynchronizationService.synchronizeData(synchronizationMode); // synchronize the data before running the checks, just in case that the files were removed remotely

        CheckExecutionContext checkExecutionContext = this.checkExecutionContextFactory.create();

        CheckExecutionProgressListener progressListener = this.checkExecutionProgressListenerProvider.getProgressListener(
                checkRunReportingMode, true);
        CheckExecutionSummary checkExecutionSummary = this.checkExecutionService.executeChecksForSchedule(
                checkExecutionContext, this.cronSchedule, progressListener);

        this.schedulerFileSynchronizationService.synchronizeData(synchronizationMode); // push the updated data files (parquet) back to the cloud

        return null;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.RUN_SCHEDULED_CHECKS_CRON;
    }
}
