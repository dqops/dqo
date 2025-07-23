/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
    private String domainName;

    /**
     * Creates a dqo job queue entry.
     * @param job Job.
     * @param jobId Job id.
     * @param jobConcurrencyConstraints Optional array of job concurrency constraints retrieved from the job. It is cached for the whole time when the job is awaiting on the queue.
     * @param domainName Data domain name.
     */
    public DqoJobQueueEntry(DqoQueueJob<?> job, DqoQueueJobId jobId, JobConcurrencyConstraint[] jobConcurrencyConstraints, String domainName) {
        this.job = job;
        this.jobId = jobId;
        this.jobConcurrencyConstraints = jobConcurrencyConstraints;
        this.domainName = domainName;
    }

    /**
     * Creates a dqo job queue entry for a job that has no concurrency constrains (parallel execution limits).
     * @param job Job.
     * @param jobId Job id.
     * @param domainName Domain name.
     */
    public DqoJobQueueEntry(DqoQueueJob<?> job, DqoQueueJobId jobId, String domainName) {
        this(job, jobId, job.getConcurrencyConstraints(), domainName);
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

    /**
     * Returns the data domain name.
     * @return Data domain name.
     */
    public String getDomainName() {
        return domainName;
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
