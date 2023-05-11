/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.jobqueue;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/**
 * Identifies a single job.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoQueueJobId implements Comparable<DqoQueueJobId> {
    private long jobId;
    private DqoQueueJobId parentJobId;
    private Instant createdAt;

    /**
     * Creates a new job id.
     */
    public DqoQueueJobId() {
        this.createdAt = Instant.now();
    }

    /**
     * Creates a new job, given a job id.
     * @param jobId Job id.
     */
    public DqoQueueJobId(long jobId) {
        this();
        this.jobId = jobId;
    }

    /**
     * Returns a unique job ID  that was assigned to the job.
     * @return Unique job id.
     */
    public long getJobId() {
        return jobId;
    }

    /**
     * Sets the job id. Should be used only for deserialization.
     * @param jobId Job id.
     */
    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    /**
     * Returns the timestamp when the job was created.
     * @return Job created timestamp.
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the created timestamp. Should be used only for deserialization.
     * @param createdAt Created at timestamp.
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns an optional ID of a parent job.
     * @return Parent job id or null, when a parent job is not present.
     */
    public DqoQueueJobId getParentJobId() {
        return parentJobId;
    }

    /**
     * Sets an job id of a parent job. Should be used only for deserialization.
     * @param parentJobId ID of a parent job.
     */
    public void setParentJobId(DqoQueueJobId parentJobId) {
        this.parentJobId = parentJobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DqoQueueJobId that = (DqoQueueJobId) o;

        return jobId == that.jobId;
    }

    @Override
    public int hashCode() {
        return (int) (jobId ^ (jobId >>> 32));
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(@NotNull DqoQueueJobId o) {
        return Long.compare(this.jobId, o.jobId);
    }

    @Override
    public String toString() {
        return "DqoQueueJobId{" +
                "jobId=" + jobId +
                ", createdAt=" + createdAt +
                '}';
    }
}
