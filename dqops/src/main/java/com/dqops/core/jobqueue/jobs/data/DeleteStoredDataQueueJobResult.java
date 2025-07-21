/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.jobs.data;

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.monitoring.DqoJobStatus;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.utils.docs.generators.GeneratorUtility;
import com.dqops.utils.docs.generators.SampleLongsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
     * Job id that identifies a job that was started on the DQOps job queue.
     */
    @JsonPropertyDescription("Job id that identifies a job that was started on the DQOps job queue.")
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

    public static class DeleteStoredDataQueueJobResultSampleFactory implements SampleValueFactory<DeleteStoredDataQueueJobResult> {
        @Override
        public DeleteStoredDataQueueJobResult createSample() {
            DqoQueueJobId dqoQueueJobId = GeneratorUtility.generateSample(DqoQueueJobId.class);
            return new DeleteStoredDataQueueJobResult(dqoQueueJobId);
        }
    }
}
