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
package com.dqops.core.jobqueue.jobs.data;

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.monitoring.DqoJobStatus;
import com.dqops.data.models.DeleteStoredDataResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Object returned from the operation that queues a "delete stored data" job. The result contains the job id that was started
 * and optionally can also contain a dictionary of partitions that were cleared or deleted if the operation was started with wait=true parameter to wait for the "delete stored data" job to finish.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "DeleteStoredDataQueueJobResult", description = "Object returned from the operation that queues a \"delete stored data\" job. " +
        "The result contains the job id that was started and optionally can also contain a dictionary of partitions that were cleared or deleted " +
        "if the operation was started with wait=true parameter to wait for the \"delete stored data\" job to finish.")
@EqualsAndHashCode(callSuper = false)
@Data
public class DeleteStoredDataQueueJobResult {
    /**
     * Job id that identifies a job that was started on the DQO job queue.
     */
    @JsonPropertyDescription("Job id that identifies a job that was started on the DQO job queue.")
    private DqoQueueJobId jobId;

    /**
     * Optional result object that is returned only when the wait parameter was true and the "delete stored data" job has finished.
     * Contains a list of partitions that were deleted or updated.
     * The calling code (the data pipeline) can decide if further processing should be continued.
     */
    @JsonPropertyDescription("Optional result object that is returned only when the wait parameter was true and the \"delete stored data\" job has finished. " +
            "Contains a list of partitions that were deleted or updated.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DeleteStoredDataResult result;

    /**
     * Job status.
     */
    @JsonPropertyDescription("Job status")
    private DqoJobStatus status = DqoJobStatus.queued;

    public DeleteStoredDataQueueJobResult(DqoQueueJobId jobId, DeleteStoredDataResult result, DqoJobStatus status) {
        this.jobId = jobId;
        this.result = result;
        this.status = status;
    }

    public DeleteStoredDataQueueJobResult(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }
}
