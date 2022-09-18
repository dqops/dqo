package ai.dqo.core.scheduler.runcheck;

import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.core.scheduler.JobSchedulerService;
import ai.dqo.core.scheduler.quartz.JobDataMapAdapter;
import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
import ai.dqo.core.scheduler.synchronization.SchedulerFileSynchronizationService;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.CheckExecutionContextFactory;
import ai.dqo.execution.checks.CheckExecutionService;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListenerProvider;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Quartz job implementation that executes data quality checks for a given schedule.
 */
@Component
@DisallowConcurrentExecution
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RunChecksSchedulerJob implements Job {
    private JobDataMapAdapter jobDataMapAdapter;
    private JobSchedulerService jobSchedulerService;
    private CheckExecutionService checkExecutionService;
    private CheckExecutionContextFactory checkExecutionContextFactory;
    private CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider;
    private SchedulerFileSynchronizationService schedulerFileSynchronizationService;
    private TerminalTableWritter terminalTableWritter;

    /**
     * Creates a data quality check run job that is executed by the job scheduler. Dependencies are injected.
     * @param jobDataMapAdapter Quartz job data adapter that extracts the original schedule definition from the job data.
     * @param jobSchedulerService Job scheduler service - used to ask for the reporting mode.
     * @param checkExecutionService Check execution service that runs the data quality checks.
     * @param checkExecutionContextFactory Check execution context that will create a context - opening the local user home.
     * @param checkExecutionProgressListenerProvider Check execution progress listener used to get the correct logger.
     * @param schedulerFileSynchronizationService Scheduler file synchronization service.
     * @param terminalTableWritter Terminal table writer - used to write the summary information about the progress to the console.
     */
    @Autowired
    public RunChecksSchedulerJob(JobDataMapAdapter jobDataMapAdapter,
                                 JobSchedulerService jobSchedulerService,
                                 CheckExecutionService checkExecutionService,
                                 CheckExecutionContextFactory checkExecutionContextFactory,
                                 CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider,
                                 SchedulerFileSynchronizationService schedulerFileSynchronizationService,
                                 TerminalTableWritter terminalTableWritter) {
        this.jobDataMapAdapter = jobDataMapAdapter;
        this.jobSchedulerService = jobSchedulerService;
        this.checkExecutionService = checkExecutionService;
        this.checkExecutionContextFactory = checkExecutionContextFactory;
        this.checkExecutionProgressListenerProvider = checkExecutionProgressListenerProvider;
        this.schedulerFileSynchronizationService = schedulerFileSynchronizationService;
        this.terminalTableWritter = terminalTableWritter;
    }

    /**
     * Executes a job that runs data quality checks for a given schedule.
     * @param jobExecutionContext Job execution context.
     * @throws JobExecutionException Exception with the error if the job execution fails.
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            FileSystemSynchronizationReportingMode synchronizationMode = this.jobSchedulerService.getSynchronizationMode();
            CheckRunReportingMode checkRunReportingMode = this.jobSchedulerService.getCheckRunReportingMode();

            this.schedulerFileSynchronizationService.synchronizeData(synchronizationMode); // synchronize the data before running the checks, just in case that the files were removed remotely

            RunChecksSchedule runChecksSchedule = this.jobDataMapAdapter.getSchedule(jobExecutionContext.getMergedJobDataMap());
            CheckExecutionContext checkExecutionContext = this.checkExecutionContextFactory.create();

            CheckExecutionProgressListener progressListener = this.checkExecutionProgressListenerProvider.getProgressListener(
                    checkRunReportingMode, true);
            CheckExecutionSummary checkExecutionSummary = this.checkExecutionService.executeChecksForSchedule(
                    checkExecutionContext, runChecksSchedule, progressListener);

            this.schedulerFileSynchronizationService.synchronizeData(synchronizationMode); // push the updated data files (parquet) back to the cloud
        }
        catch (Exception ex) {
            throw new JobExecutionException(ex);
        }
    }
}
