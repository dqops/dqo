/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.monitoring;

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/**
 * Describes a change to the job status or the job queue (such as a new job was added).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoJobChangeModel implements Comparable<DqoJobChangeModel> {
    private DqoJobStatus status;
    private DqoQueueJobId jobId;
    private long changeSequence;
    private DqoJobHistoryEntryModel updatedModel;
    private Instant statusChangedAt;
    private String domainName;

    /**
     * Creates a job change model by assigning a change sequence number.
     * @param jobChange Job change entry that is copied to the new object.
     * @param changeSequence Change sequence number.
     */
    public DqoJobChangeModel(DqoJobChange jobChange, long changeSequence) {
        this.status = jobChange.getStatus();
        this.jobId = jobChange.getJobId();
        this.updatedModel = jobChange.getUpdatedModel();
        this.changeSequence = changeSequence;
        this.domainName = jobChange.getDomainName();
        if (jobChange.getUpdatedModel() != null) {
            this.statusChangedAt = jobChange.getUpdatedModel().getStatusChangedAt();
        } else {
            this.statusChangedAt = Instant.now();
        }
    }

    /**
     * Returns the most recent job status.
     * @return Job status.
     */
    public DqoJobStatus getStatus() {
        return status;
    }

    /**
     * Returns the job id.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }

    /**
     * Returns a change sequence that is used to sort job changes.
     * @return Change sequence.
     */
    public long getChangeSequence() {
        return changeSequence;
    }

    /**
     * Returns the data domain name.
     * @return Data domain name.
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * Returns a job model for a new job or an updated job.
     * @return Job model.
     */
    public DqoJobHistoryEntryModel getUpdatedModel() {
        return updatedModel;
    }

    /**
     * Updates the job entry model.
     * @param updatedModel New job entry model.
     */
    public void setUpdatedModel(DqoJobHistoryEntryModel updatedModel) {
        this.updatedModel = updatedModel;
    }

    /**
     * Returns the timestamp when the job was added or changed.
     * @return Job status change timestamp.
     */
    public Instant getStatusChangedAt() {
        return statusChangedAt;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(@NotNull DqoJobChangeModel o) {
        return Long.compare(this.changeSequence, o.changeSequence);
    }
}
