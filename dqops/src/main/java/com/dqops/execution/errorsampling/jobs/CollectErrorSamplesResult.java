/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.errorsampling.jobs;

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.monitoring.DqoJobStatus;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Object returned from the operation that queues a "collect error samples" job. The result contains the job id that was started
 * and optionally can also contain the result of collecting the error samples if the operation was started with wait=true parameter to wait for the "collect error samples" job to finish.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "CollectErrorSamplesResult", description = "Object returned from the operation that queues a \"collect error samples\" job. " +
        "The result contains the job id that was started and optionally can also contain the result of collecting the error samples  " +
        "if the operation was started with wait=true parameter to wait for the \"collect error samples\" job to finish.")
@EqualsAndHashCode(callSuper = false)
@Data
public class CollectErrorSamplesResult {
    /**
     * Job id that identifies a job that was started on the DQOps job queue.
     */
    @JsonPropertyDescription("Job id that identifies a job that was started on the DQOps job queue.")
    private DqoQueueJobId jobId;

    /**
     * Optional result object that is returned only when the wait parameter was true and the "collect error samples" job has finished.
     * Contains the summary result of the error samplers executed, including the number of samples collected.
     * The calling code (the data pipeline) can decide if further processing should be continued.
     */
    @JsonPropertyDescription("Optional result object that is returned only when the wait parameter was true and the \"collect error samples\" job has finished. " +
            "Contains the summary result of collecting error samples, including the number of error samplers that were executed, and the number of error samples collected. ")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorSamplerResult result;

    /**
     * Job status.
     */
    @JsonPropertyDescription("Job status")
    private DqoJobStatus status = DqoJobStatus.queued;


    public CollectErrorSamplesResult() {
    }

    /**
     * Creates a new model given all parameters.
     * @param jobId Job id.
     * @param result Job result.
     * @param status Job status.
     */
    public CollectErrorSamplesResult(DqoQueueJobId jobId, ErrorSamplerResult result, DqoJobStatus status) {
        this.jobId = jobId;
        this.result = result;
        this.status = status;
    }

    /**
     * Creates a new model given all parameters.
     * @param jobId Job id.
     */
    public CollectErrorSamplesResult(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }

    public static class CollectErrorSamplesQueueJobResultSampleFactory implements SampleValueFactory<CollectErrorSamplesResult> {
        @Override
        public CollectErrorSamplesResult createSample() {
            return new CollectErrorSamplesResult() {{
                setJobId(new DqoQueueJobId.DqoQueueJobIdSampleFactory().createSample());
                setStatus(DqoJobStatus.finished);
                setResult(new ErrorSamplerResult() {{
                    setExecutedErrorSamplers(3);
                    setColumnsAnalyzed(1);
                    setColumnsSuccessfullyAnalyzed(0);
                    setTotalErrorSamplersFailed(1);
                    setTotalErrorSamplesCollected(2);
                }});
            }};
        }
    }
}
