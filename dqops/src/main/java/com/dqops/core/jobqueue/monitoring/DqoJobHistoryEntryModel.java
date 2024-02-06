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

import com.dqops.core.jobqueue.DqoJobQueueEntry;
import com.dqops.core.jobqueue.DqoJobType;
import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.utils.docs.generators.SampleLongsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * Model of a single job that was scheduled or has finished. It is stored in the job monitoring service on the history list.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoJobHistoryEntryModel implements Comparable<DqoJobHistoryEntryModel>, Cloneable {
    private DqoQueueJobId jobId;
    private DqoJobType jobType;
    private DqoJobEntryParametersModel parameters;
    private DqoJobStatus status;
    private String errorMessage;
    private Instant statusChangedAt;
    @JsonIgnore
    private DqoJobQueueEntry jobQueueEntry;


    public DqoJobHistoryEntryModel() {
    }

    /**
     * Creates a job entry model given all values.
     * @param jobId Job id.
     * @param jobType Job type.
     * @param parameters Job parameters.
     * @param jobQueueEntry Job queue entry.
     */
    public DqoJobHistoryEntryModel(DqoQueueJobId jobId,
                                   DqoJobType jobType,
                                   DqoJobEntryParametersModel parameters,
                                   DqoJobQueueEntry jobQueueEntry) {
        this.jobId = jobId;
        this.jobType = jobType;
        this.parameters = parameters;
        this.status = DqoJobStatus.queued;
        this.statusChangedAt = Instant.now();
        this.jobQueueEntry = jobQueueEntry;
    }

    /**
     * Creates a job entry model from a job entry in the queue. The status of the job is {@link DqoJobStatus#queued}.
     * @param jobQueueEntry Job entry from the queue.
     */
    public DqoJobHistoryEntryModel(DqoJobQueueEntry jobQueueEntry) {
        this.jobId = jobQueueEntry.getJobId();
        this.jobType = jobQueueEntry.getJob().getJobType();
        this.parameters = jobQueueEntry.getJob().createParametersModel();
        this.status = DqoJobStatus.queued;
        this.statusChangedAt = jobQueueEntry.getJobId().getCreatedAt();
        this.jobQueueEntry = jobQueueEntry;
    }

    /**
     * Returns the job id.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }

    /**
     * Returns a job type.
     * @return Job type.
     */
    public DqoJobType getJobType() {
        return jobType;
    }

    /**
     * Returns a job parameters model.
     * @return Parameter model with properties for each type of a job.
     */
    public DqoJobEntryParametersModel getParameters() {
        return parameters;
    }

    /**
     * Changes the job parameters.
     * @param parameters New job parameters.
     */
    public void setParameters(DqoJobEntryParametersModel parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns the current job status.
     * @return Job status.
     */
    public DqoJobStatus getStatus() {
        return status;
    }

    /**
     * Sets a new job status.
     * @param status New job status.
     */
    public void setStatus(DqoJobStatus status) {
        this.status = status;
    }

    /**
     * Returns an error message for a failed job.
     * @return Error message or null when the job has not finished or has finished.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets an error message for a failed job.
     * @param errorMessage Error message.
     */
    public void setErrorMessage(String errorMessage) {
        if (errorMessage != null) {
            this.status = DqoJobStatus.failed;
        }
        this.errorMessage = errorMessage;
    }

    /**
     * The timestamp when the status has changed for the last time.
     * @return Last status change timestamp.
     */
    public Instant getStatusChangedAt() {
        return statusChangedAt;
    }

    /**
     * Sets the last status change timestamp.
     * @param statusChangedAt Last status change timestamp.
     */
    public void setStatusChangedAt(Instant statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }

    /**
     * Returns the DQOps queue job entry, that could be used to wait for the job.
     * @return Job queue entry.
     */
    public DqoJobQueueEntry getJobQueueEntry() {
        return jobQueueEntry;
    }

    /**
     * Sets a reference to the job queue entry.
     * @param jobQueueEntry Job queue entry.
     */
    public void setJobQueueEntry(DqoJobQueueEntry jobQueueEntry) {
        this.jobQueueEntry = jobQueueEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DqoJobHistoryEntryModel that = (DqoJobHistoryEntryModel) o;

        return Objects.equals(jobId, that.jobId);
    }

    @Override
    public int hashCode() {
        return jobId != null ? jobId.hashCode() : 0;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(@NotNull DqoJobHistoryEntryModel o) {
        return this.jobId.compareTo(o.jobId);
    }

    /**
     * Creates a shallow clone of the object.
     */
    @Override
    public DqoJobHistoryEntryModel clone() {
        try {
            DqoJobHistoryEntryModel cloned = (DqoJobHistoryEntryModel) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Failed to clone an object");
        }
    }

    public static class DqoJobHistoryEntryModelSampleFactory implements SampleValueFactory<DqoJobHistoryEntryModel> {
        @Override
        public DqoJobHistoryEntryModel createSample() {
            return new DqoJobHistoryEntryModel() {{
                setStatus(DqoJobStatus.finished);
                setStatusChangedAt(LocalDateTime.of((int)SampleLongsRegistry.getYear(),
                        (int)SampleLongsRegistry.getMonth(),
                        11,
                        13,
                        42,
                        0
                ).toInstant(ZoneOffset.UTC));
            }};
        }
    }
}
