package ai.dqo.core.jobqueue;

import ai.dqo.core.dqocloud.synchronization.SynchronizeRootFolderDqoQueueJob;
import ai.dqo.core.scheduler.runcheck.RunScheduledChecksDqoJob;
import ai.dqo.core.scheduler.scan.RunPeriodicMetadataSynchronizationDqoJob;
import ai.dqo.execution.checks.RunChecksQueueJob;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DQO job instance factory. Creates new instances of jobs that could be configured before they are submitted to the queue.
 */
@Component
public class DqoQueueJobFactoryImpl implements DqoQueueJobFactory {
    private BeanFactory beanFactory;

    /**
     * Creates a new instance of a job factory using a spring bean factory. Job classes should be configured
     * as prototype beans.
     * @param beanFactory Bean factory.
     */
    @Autowired
    public DqoQueueJobFactoryImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Creates a run checks job.
     * @return New run checks job.
     */
    @Override
    public RunChecksQueueJob createRunChecksJob() {
        return this.beanFactory.getBean(RunChecksQueueJob.class);
    }

    /**
     * Creates a DQO Cloud synchronization job that will synchronize one folder in the user home.
     *
     * @return Cloud synchronization job for one folder.
     */
    @Override
    public SynchronizeRootFolderDqoQueueJob createSynchronizeRootFolderJob() {
        return this.beanFactory.getBean(SynchronizeRootFolderDqoQueueJob.class);
    }

    /**
     * Creates a DQO job that runs a scheduled (every 10 minutes by default) metadata synchronization and detection of new cron schedules.
     *
     * @return Periodic metadata synchronization job.
     */
    @Override
    public RunPeriodicMetadataSynchronizationDqoJob createRunPeriodicMetadataSynchronizationJob() {
        return this.beanFactory.getBean(RunPeriodicMetadataSynchronizationDqoJob.class);
    }

    /**
     * Creates a job that executes all checks scheduled for one cron expression.
     *
     * @return Run scheduled checks job.
     */
    @Override
    public RunScheduledChecksDqoJob createRunScheduledChecksJob() {
        return this.beanFactory.getBean(RunScheduledChecksDqoJob.class);
    }
}
