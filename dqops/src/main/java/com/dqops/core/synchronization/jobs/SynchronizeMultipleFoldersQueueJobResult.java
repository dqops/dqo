/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
     * Job id that identifies a job that was started on the DQOps job queue.
     */
    @JsonPropertyDescription("Job id that identifies a job that was started on the DQOps job queue.")
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
