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
package com.dqops.core.synchronization.jobs;

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.monitoring.DqoJobStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Object returned from the operation that queues a "synchronize multiple folders" job. The result contains the job id that was started
 * and optionally can also contain the job finish status if the operation was started with wait=true parameter to wait for the "synchronize multiple folders" job to finish.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "SynchronizeMultipleFoldersQueueJobResult", description = "Object returned from the operation that queues a \"synchronize multiple folders\" job. " +
        "The result contains the job id that was started and optionally can also contain the job finish status if the operation was started with wait=true parameter to wait for the \"synchronize multiple folders\" job to finish.")
@EqualsAndHashCode(callSuper = false)
@Data
public class SynchronizeMultipleFoldersQueueJobResult {
    /**
     * Job id that identifies a job that was started on the DQO job queue.
     */
    @JsonPropertyDescription("Job id that identifies a job that was started on the DQO job queue.")
    private DqoQueueJobId jobId;

    /**
     * Job status.
     */
    @JsonPropertyDescription("Job status")
    private DqoJobStatus status = DqoJobStatus.queued;


    public SynchronizeMultipleFoldersQueueJobResult() {
    }

    /**
     * Creates a new model given all parameters.
     * @param jobId Job id.
     * @param status Job status.
     */
    public SynchronizeMultipleFoldersQueueJobResult(DqoQueueJobId jobId, DqoJobStatus status) {
        this.jobId = jobId;
        this.status = status;
    }

    /**
     * Creates a new model given all parameters.
     * @param jobId Job id.
     */
    public SynchronizeMultipleFoldersQueueJobResult(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }
}
