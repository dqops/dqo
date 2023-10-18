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

/**
 * DQOps job queue concurrency limiter. Maintains a count of concurrent jobs running in parallel in order to enforce
 * concurrency limits.
 */
public interface DqoJobConcurrencyLimiter {
    /**
     * Registers a new job that has a concurrency constraint. The concurrency limit for that job is checked to decide
     * if the job is allowed to run. If the concurrency limit is not reached, the running jobs count is increased by one
     * and the job is returned, so it is allowed to run.
     * If the concurrency limit is exceeded, the job is parked on a queue and the method returns null, so the job should not be started.
     *
     * @param dqoJobQueueEntry
     * @return The job that was passed (<code>dqoJobQueueEntry</code>) when the job is allowed to run, null when the job was parked.
     */
    DqoJobQueueEntry parkOrRegisterStartedJob(DqoJobQueueEntry dqoJobQueueEntry);

    /**
     * Tries to take (and dequeue) an available job that is below the concurrency limit.
     *
     * @return A dequeued job that should be started or null. The running jobs count is increased for the returned job, because it is assumed that it will be started instantly.
     */
    DqoJobQueueEntry takeFirstAvailable();

    /**
     * Retrieves an array of parked jobs.
     *
     * @return Array of parked jobs, that are awaiting because their concurrency limits are exceeded.
     */
    DqoJobQueueEntry[] getParkedJobs();

    /**
     * Notifies the concurrency limiter that a concurrency limited job has finished and the running job count for that type of jobs could be decreased.
     * @param dqoJobQueueEntry Job entry that was just finished.
     */
    void notifyJobFinished(DqoJobQueueEntry dqoJobQueueEntry);

    /**
     * Clears (removes) all jobs and limits.
     */
    void clear();
}
