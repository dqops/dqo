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
