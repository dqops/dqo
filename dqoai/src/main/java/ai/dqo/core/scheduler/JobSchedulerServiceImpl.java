package ai.dqo.core.scheduler;

import ai.dqo.core.scheduler.quartz.SpringIoCJobFactory;
import ai.dqo.core.scheduler.scan.UniqueSchedulesCollection;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Job scheduling root class that manages an instance of a Quartz scheduler.
 */
@Component
public class JobSchedulerServiceImpl {
    private Scheduler scheduler;
    private StdSchedulerFactory schedulerFactory;
    private SpringIoCJobFactory jobFactory;

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

    public UniqueSchedulesCollection getActiveSchedules(JobKey jobKey) {
        try {
            List<? extends Trigger> triggersOfJob = this.scheduler.getTriggersOfJob(jobKey);
            return null;
        }
        catch (Exception ex) {
            throw new JobSchedulerException(ex);
        }
    }
}
