package ai.dqo.core.jobqueue;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Special purpose job that is pushed to the queue. Every worker thread that receives this job will
 * instantly shut down and push the job back to the queue - to kill (poison) other threads and effectively
 * stop the job queue without any delay caused by a delay.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class PoisonDqoJobQueueJob extends BaseDqoQueueJob<Void> {
    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public Void onExecute() {
        // do nothing
        return null;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.QUEUE_THREAD_SHUTDOWN;
    }
}
