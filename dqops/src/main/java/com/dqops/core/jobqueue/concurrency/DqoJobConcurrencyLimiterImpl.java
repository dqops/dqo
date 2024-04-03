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
package com.dqops.core.jobqueue.concurrency;

import com.dqops.core.jobqueue.DqoJobQueueEntry;
import com.dqops.core.jobqueue.exceptions.DqoQueueJobExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * DQOps job queue concurrency limiter. Maintains a count of concurrent jobs running in parallel in order to enforce
 * concurrency limits.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
     * @param dqoJobQueueEntry Job queue entry to be parked (returns null) or the job to be started.
     * @return The job that was passed (<code>dqoJobQueueEntry</code>) when the job is allowed to run, null when the job was parked.
     */
    @Override
    public DqoJobQueueEntry parkOrRegisterStartedJob(DqoJobQueueEntry dqoJobQueueEntry) {
        JobConcurrencyConstraint[] jobConcurrencyConstraints = dqoJobQueueEntry.getJobConcurrencyConstraints();
        assert jobConcurrencyConstraints != null;

        if (jobConcurrencyConstraints.length == 0) {
            throw new DqoQueueJobExecutionException("Cannot start a job that has an empty concurrency constraints list, use a 'null' array reference instead.");
        }

        synchronized (this.lock) {
            for (JobConcurrencyConstraint jobConcurrencyConstraint : jobConcurrencyConstraints) {
                JobConcurrencyTarget concurrencyTarget = jobConcurrencyConstraint.getConcurrencyTarget();
                assert concurrencyTarget != null;

                ConcurrentJobLimit concurrentJobLimit = this.targetLimits.get(concurrencyTarget);
                if (concurrentJobLimit != null) {
                    if (concurrentJobLimit.getRunningJobsCount() >= jobConcurrencyConstraint.getConcurrentJobsLimit()) {
                        // this limit will not be met, parking...
                        parkedJobs.add(dqoJobQueueEntry);
                        return null; // parking the job
                    }
                }
            }

            for (JobConcurrencyConstraint jobConcurrencyConstraint : jobConcurrencyConstraints) {
                JobConcurrencyTarget concurrencyTarget = jobConcurrencyConstraint.getConcurrencyTarget();
                assert concurrencyTarget != null;

                ConcurrentJobLimit concurrentJobLimit = this.targetLimits.get(concurrencyTarget);
                if (concurrentJobLimit == null) {
                    concurrentJobLimit = new ConcurrentJobLimit(jobConcurrencyConstraint.getConcurrentJobsLimit());
                    this.targetLimits.put(concurrencyTarget, concurrentJobLimit);
                }
                else {
                    concurrentJobLimit.setNewLimit(jobConcurrencyConstraint.getConcurrentJobsLimit()); // maybe the limit has changed
                }

                int currentJobCount = concurrentJobLimit.incrementRunningJobsCount();
                assert currentJobCount > 0;
            }

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

                JobConcurrencyConstraint[] jobConcurrencyConstraints = parkedEntry.getJobConcurrencyConstraints();

                boolean jobCanBeExecuted = true;
                for (JobConcurrencyConstraint jobConcurrencyConstraint : jobConcurrencyConstraints) {
                    JobConcurrencyTarget concurrencyTarget = jobConcurrencyConstraint.getConcurrencyTarget();
                    assert concurrencyTarget != null;

                    ConcurrentJobLimit concurrentJobLimit = this.targetLimits.get(concurrencyTarget);
                    assert concurrentJobLimit != null;

                    if (concurrentJobLimit.isConcurrencyLimitReached()) {
                        jobCanBeExecuted = false;
                        break;
                    }
                }

                if (!jobCanBeExecuted) {
                    continue; // check the next parked job
                }

                for (JobConcurrencyConstraint jobConcurrencyConstraint : jobConcurrencyConstraints) {
                    JobConcurrencyTarget concurrencyTarget = jobConcurrencyConstraint.getConcurrencyTarget();
                    assert concurrencyTarget != null;

                    ConcurrentJobLimit concurrentJobLimit = this.targetLimits.get(concurrencyTarget);
                    assert concurrentJobLimit != null;

                    int currentJobCount = concurrentJobLimit.incrementRunningJobsCount();
                    assert currentJobCount > 0;
                }

                parkedJobsIterator.remove();
                return parkedEntry;
            }

            return null; // no available (not limited by concurrency constraints) jobs found
        }
    }

    /**
     * Retrieves an array of parked jobs.
     * @return Array of parked jobs, that are awaiting because their concurrency limits are exceeded.
     */
    @Override
    public DqoJobQueueEntry[] getParkedJobs() {
        synchronized (this.lock) {
            return this.parkedJobs.toArray(DqoJobQueueEntry[]::new);
        }
    }

    /**
     * Notifies the concurrency limiter that a concurrency limited job has finished and the running job count for that type of jobs could be decreased.
     *
     * @param dqoJobQueueEntry Job entry that was just finished.
     */
    @Override
    public void notifyJobFinished(DqoJobQueueEntry dqoJobQueueEntry) {
        JobConcurrencyConstraint[] jobConcurrencyConstraints = dqoJobQueueEntry.getJobConcurrencyConstraints();
        assert jobConcurrencyConstraints != null;

        synchronized (this.lock) {
            for (JobConcurrencyConstraint jobConcurrencyConstraint : jobConcurrencyConstraints) {
                JobConcurrencyTarget concurrencyTarget = jobConcurrencyConstraint.getConcurrencyTarget();
                ConcurrentJobLimit concurrentJobLimit = this.targetLimits.get(concurrencyTarget);
                assert concurrentJobLimit != null;

                concurrentJobLimit.decrementRunningJobsCount();
            }
        }
    }

    /**
     * Clears (removes) all jobs and limits.
     */
    public void clear() {
        this.parkedJobs.clear();
        this.targetLimits.clear();
    }
}
