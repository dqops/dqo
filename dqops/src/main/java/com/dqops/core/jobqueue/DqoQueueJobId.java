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

import com.dqops.utils.docs.generators.SampleLongsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Identifies a single job.
 */
@ApiModel(value = "DqoQueueJobId", description = "Identifies a single job that was pushed to the job queue.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoQueueJobId implements Comparable<DqoQueueJobId> {
    @JsonPropertyDescription("Job id.")
    private long jobId;

    @JsonPropertyDescription("Optional job business key that was assigned to the job. A business key is an alternative user assigned unique job identifier used to find the status of a job finding it by the business key.")
    private String jobBusinessKey;

    @JsonPropertyDescription("Parent job id. Filled only for nested jobs, for example a sub-job that runs data quality checks on a single table.")
    private DqoQueueJobId parentJobId;

    @JsonPropertyDescription("The timestamp when the job was created.")
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
     * Returns an optional business key that is an alternative, user assigned unique job identifier.
     * @return Job business key.
     */
    public String getJobBusinessKey() {
        return jobBusinessKey;
    }

    /**
     * Sets a job business key.
     * @param jobBusinessKey Job business key.
     */
    public void setJobBusinessKey(String jobBusinessKey) {
        this.jobBusinessKey = jobBusinessKey;
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
                ", jobBusinessKey=" + jobBusinessKey +
                ", createdAt=" + createdAt +
                '}';
    }

    public static class DqoQueueJobIdSampleFactory implements SampleValueFactory<DqoQueueJobId> {
        @Override
        public DqoQueueJobId createSample() {
            return new DqoQueueJobId() {{
                setJobId(SampleLongsRegistry.getJobId());
                setCreatedAt(LocalDateTime.of((int)SampleLongsRegistry.getYear(),
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
