package ai.dqo.core.jobqueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * DQO job queue concurrency limiter. Maintains a count of concurrent jobs running in parallel in order to enforce
 * concurrency limits.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class DqoJobConcurrencyLimiterImpl implements DqoJobConcurrencyLimiter {
    private final Object lock = new Object();
    private final Map<JobConcurrencyTarget, ConcurrentJobLimit> targetLimits = new LinkedHashMap<>();
    private final LinkedList<DqoJobQueueEntry> parkedJobs = new LinkedList<>();

    /**
     * Registers a new job that has a concurrency constraint. The concurrency limit for that job is checked to decide
     * if the job is allowed to run. If the concurrency limit is not reached, the running jobs count is increased by one
     * and the job is returned, so it is allowed to run.
     * If the concurrency limit is exceeded, the job is parked on a queue and the method returns null, so the job should not be started.
     * @param dqoJobQueueEntry
     * @return The job that was passed (<code>dqoJobQueueEntry</code>) when the job is allowed to run, null when the job was parked.
     */
    @Override
    public DqoJobQueueEntry parkOrRegisterStartedJob(DqoJobQueueEntry dqoJobQueueEntry) {
        JobConcurrencyConstraint jobConcurrencyConstraint = dqoJobQueueEntry.getJobConcurrencyConstraint();
        assert jobConcurrencyConstraint != null;

        JobConcurrencyTarget concurrencyTarget = jobConcurrencyConstraint.getConcurrencyTarget();
        assert concurrencyTarget != null;

        synchronized (this.lock) {
            ConcurrentJobLimit concurrentJobLimit = this.targetLimits.get(concurrencyTarget);
            if (concurrentJobLimit == null) {
                concurrentJobLimit = new ConcurrentJobLimit(jobConcurrencyConstraint.getConcurrentJobsLimit());
                this.targetLimits.put(concurrencyTarget, concurrentJobLimit);
            }
            else {
                concurrentJobLimit.setNewLimit(jobConcurrencyConstraint.getConcurrentJobsLimit()); // maybe the limit has changed
            }

            if (concurrentJobLimit.isConcurrencyLimitReached()) {
                parkedJobs.add(dqoJobQueueEntry);
                return null; // parking the job
            }

            int currentJobCount = concurrentJobLimit.incrementRunningJobsCount();
            assert currentJobCount > 0;
            return dqoJobQueueEntry;
        }
    }

    /**
     * Tries to take (and dequeue) an available job that is below the concurrency limit.
     * @return A dequeued job that should be started or null. The running jobs count is increased for the returned job, because it is assumed that it will be started instantly.
     */
    @Override
    public DqoJobQueueEntry takeFirstAvailable() {
        synchronized (this.lock) {
            if (this.parkedJobs.size() == 0) {
                return null; // the queue is empty, nothing to deque
            }

            Iterator<DqoJobQueueEntry> parkedJobsIterator = this.parkedJobs.iterator();
            while (parkedJobsIterator.hasNext()) {
                DqoJobQueueEntry parkedEntry = parkedJobsIterator.next();

                ConcurrentJobLimit concurrentJobLimit = this.targetLimits.get(parkedEntry.getJobConcurrencyConstraint().getConcurrencyTarget());
                assert concurrentJobLimit != null;

                if (!concurrentJobLimit.isConcurrencyLimitReached()) {
                    int currentJobCount = concurrentJobLimit.incrementRunningJobsCount();
                    assert currentJobCount > 0;
                    parkedJobsIterator.remove();
                    return parkedEntry;
                }
            }

            return null; // no available (not limited by concurrency) jobs found
        }
    }

    /**
     * Retrieves an array of parked jobs.
     * @return Array of parked jobs, that are awaiting because their concurrency limits are exceeded.
     */
    @Override
    public DqoJobQueueEntry[] getParkedJobs() {
        synchronized (this.lock) {
            return this.parkedJobs.toArray(new DqoJobQueueEntry[this.parkedJobs.size()]);
        }
    }

    /**
     * Notifies the concurrency limiter that a concurrency limited job has finished and the running job count for that type of jobs could be decreased.
     *
     * @param dqoJobQueueEntry Job entry that was just finished.
     */
    @Override
    public void notifyJobFinished(DqoJobQueueEntry dqoJobQueueEntry) {
        assert dqoJobQueueEntry.getJobConcurrencyConstraint() != null;

        synchronized (this.lock) {
            ConcurrentJobLimit concurrentJobLimit = this.targetLimits.get(dqoJobQueueEntry.getJobConcurrencyConstraint().getConcurrencyTarget());
            assert concurrentJobLimit != null;

            concurrentJobLimit.decrementRunningJobsCount();
        }
    }
}
