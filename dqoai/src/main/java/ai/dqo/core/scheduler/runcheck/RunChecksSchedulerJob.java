package ai.dqo.core.scheduler.runcheck;

import ai.dqo.core.scheduler.quartz.JobDataMapAdapter;
import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
import ai.dqo.core.scheduler.synchronization.SchedulerFileSynchronizationService;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.CheckExecutionContextFactory;
import ai.dqo.execution.checks.CheckExecutionService;
import ai.dqo.execution.checks.CheckExecutionSummary;
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
    private CheckExecutionService checkExecutionService;
    private CheckExecutionContextFactory checkExecutionContextFactory;
    private SchedulerCheckExecutionProgressListener checkExecutionProgressListener;
    private SchedulerFileSynchronizationService schedulerFileSynchronizationService;

    /**
     * Creates a data quality check run job that is executed by the job scheduler. Dependencies are injected.
     * @param jobDataMapAdapter Quartz job data adapter that extracts the original schedule definition from the job data.
     * @param checkExecutionService Check execution service that runs the data quality checks.
     * @param checkExecutionContextFactory Check execution context that will create a context - opening the local user home.
     * @param checkExecutionProgressListener A silent check execution progress listener.
     * @param schedulerFileSynchronizationService Scheduler file synchronization service.
     */
    @Autowired
    public RunChecksSchedulerJob(JobDataMapAdapter jobDataMapAdapter,
                                 CheckExecutionService checkExecutionService,
                                 CheckExecutionContextFactory checkExecutionContextFactory,
                                 SchedulerCheckExecutionProgressListener checkExecutionProgressListener,
                                 SchedulerFileSynchronizationService schedulerFileSynchronizationService) {
        this.jobDataMapAdapter = jobDataMapAdapter;
        this.checkExecutionService = checkExecutionService;
        this.checkExecutionContextFactory = checkExecutionContextFactory;
        this.checkExecutionProgressListener = checkExecutionProgressListener;
        this.schedulerFileSynchronizationService = schedulerFileSynchronizationService;
    }

    /**
     * Executes a job that runs data quality checks for a given schedule.
     * @param jobExecutionContext Job execution context.
     * @throws JobExecutionException Exception with the error if the job execution fails.
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            this.schedulerFileSynchronizationService.synchronizeData(); // synchronize the data before running the checks, just in case that the files were removed remotely

            RunChecksSchedule runChecksSchedule = this.jobDataMapAdapter.getSchedule(jobExecutionContext.getMergedJobDataMap());
            CheckExecutionContext checkExecutionContext = this.checkExecutionContextFactory.create();

            CheckExecutionSummary checkExecutionSummary = this.checkExecutionService.executeChecksForSchedule(
                    checkExecutionContext, runChecksSchedule, this.checkExecutionProgressListener);

            this.schedulerFileSynchronizationService.synchronizeData(); // push the updated data files (parquet) back to the cloud
        }
        catch (Exception ex) {
            throw new JobExecutionException(ex);
        }
    }
}
