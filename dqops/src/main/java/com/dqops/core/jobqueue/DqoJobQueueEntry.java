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
package com.dqops.core.jobqueue;

import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import org.jetbrains.annotations.NotNull;

/**
 * Holder of a single DQOps job on the queue.
 */
public class DqoJobQueueEntry implements Comparable<DqoJobQueueEntry> {
    private DqoQueueJob<?> job;
    private DqoQueueJobId jobId;
    private JobConcurrencyConstraint[] jobConcurrencyConstraints;

    /**
     * Creates a dqo job queue entry.
     * @param job Job.
     * @param jobId Job id.
     * @param jobConcurrencyConstraints Optional array of job concurrency constraints retrieved from the job. It is cached for the whole time when the job is awaiting on the queue.
     */
    public DqoJobQueueEntry(DqoQueueJob<?> job, DqoQueueJobId jobId, JobConcurrencyConstraint[] jobConcurrencyConstraints) {
        this.job = job;
        this.jobId = jobId;
        this.jobConcurrencyConstraints = jobConcurrencyConstraints;
    }

    /**
     * Creates a dqo job queue entry for a job that has no concurrency constrains (parallel execution limits).
     * @param job Job.
     * @param jobId Job id.
     */
    public DqoJobQueueEntry(DqoQueueJob<?> job, DqoQueueJobId jobId) {
        this(job, jobId, job.getConcurrencyConstraints());
    }

    /**
     * Returns the dqo job object instance.
     * @return DQOps Job instance.
     */
    public DqoQueueJob<?> getJob() {
        return job;
    }

    /**
     * Returns the dqo job id.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }

    /**
     * Returns an optional array of job concurrency constraints that identifies the concurrency target and the DOP.
     * @return Array of job concurrency constraints or null, when the job has no concurrency limits.
     */
    public JobConcurrencyConstraint[] getJobConcurrencyConstraints() {
        return jobConcurrencyConstraints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DqoJobQueueEntry that = (DqoJobQueueEntry) o;

        return jobId.equals(that.jobId);
    }

    @Override
    public int hashCode() {
        return jobId.hashCode();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(@NotNull DqoJobQueueEntry o) {
        return this.jobId.compareTo(o.getJobId());
    }
}
