package ai.dqo.core.scheduler;

import ai.dqo.core.scheduler.quartz.JobKeys;
import ai.dqo.core.scheduler.quartz.JobNames;
import ai.dqo.core.scheduler.quartz.SpringIoCJobFactory;
import ai.dqo.core.scheduler.runcheck.RunChecksSchedulerJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;

/**
 * Job scheduling root class that manages an instance of a Quartz scheduler.
 */
@Component
public class JobSchedulerServiceImpl {
    private Scheduler scheduler;
    private StdSchedulerFactory schedulerFactory;
    private SpringIoCJobFactory jobFactory;
    private JobDetail runChecksJob;

    /**
     * Job scheduler service constructor.
     * @param schedulerFactory Quartz scheduler factory (new).
     * @param jobFactory Custom job factory that uses Spring IoC for instantiating job instances.
     */
    @Autowired
    public JobSchedulerServiceImpl(StdSchedulerFactory schedulerFactory,
                                   SpringIoCJobFactory jobFactory) {
        this.schedulerFactory = schedulerFactory;
        this.jobFactory = jobFactory;
    }

    /**
     * Initializes and starts the scheduler.
     */
    public void start() {
        createAndStartScheduler();
        defineDefaultJobs();
    }

    /**
     * Creates and starts a scheduler, but without any jobs.
     */
    public void createAndStartScheduler() {
        assert scheduler == null;
        try {
            this.scheduler = schedulerFactory.getScheduler();
            this.scheduler.setJobFactory(this.jobFactory);
            this.scheduler.start();
        }
        catch (Exception ex) {
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Defines the default jobs like running checks or synchronizing the metadata.
     */
    public void defineDefaultJobs() {
        try {
            this.runChecksJob = newJob(RunChecksSchedulerJob.class)
                .withIdentity(JobKeys.RUN_CHECKS)
                .build();
            this.scheduler.addJob(this.runChecksJob, true);

            
        } catch (SchedulerException ex) {
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Stops the scheduler.
     */
    public void shutdown() {
        try {
            if (this.scheduler != null) {
                this.scheduler.shutdown();
            }
            this.scheduler = null;
        }
        catch (Exception ex) {
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Returns the default job scheduler. The scheduler must be started first.
     * @return Quartz scheduler instance.
     */
    public Scheduler getScheduler() {
        assert this.scheduler != null : "The scheduler must be first started";
        return this.scheduler;
    }
}
