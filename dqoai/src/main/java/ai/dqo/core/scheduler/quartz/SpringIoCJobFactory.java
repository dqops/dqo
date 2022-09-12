package ai.dqo.core.scheduler.quartz;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Custom job factory for quartz that will instantiate job class instances using IoC (Spring).
 */
@Component
public class SpringIoCJobFactory implements JobFactory {
    private static final Logger LOG = LoggerFactory.getLogger(SpringIoCJobFactory.class);

    private BeanFactory beanFactory;

    /**
     * Constructor for the job factory.
     * @param beanFactory The default instance of the Spring bean factory.
     */
    @Autowired
    public SpringIoCJobFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Creates a job instance using IoC container.
     * @param triggerFiredBundle Trigger and job details.
     * @param scheduler Scheduler.
     * @return Job instance created from the job factory.
     * @throws SchedulerException
     */
    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        Class<? extends Job> jobClass = triggerFiredBundle.getJobDetail().getJobClass();
        try {
            Job jobInstance = this.beanFactory.getBean(jobClass);
            return jobInstance;
        }
        catch (Exception ex) {
            LOG.debug("Cannot create an instance of a job " + jobClass.getCanonicalName(), ex);
            throw new SchedulerException("Cannot create an instance of a job " + jobClass.getCanonicalName(), ex);
        }
    }
}
