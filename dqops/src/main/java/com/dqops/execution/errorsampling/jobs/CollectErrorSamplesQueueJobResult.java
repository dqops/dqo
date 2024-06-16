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
@ApiModel(value = "CollectErrorSamplesQueueJobResult", description = "Object returned from the operation that queues a \"collect error samples\" job. " +
        "The result contains the job id that was started and optionally can also contain the result of collecting the error samples  " +
        "if the operation was started with wait=true parameter to wait for the \"collect error samples\" job to finish.")
@EqualsAndHashCode(callSuper = false)
@Data
public class CollectErrorSamplesQueueJobResult {
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


    public CollectErrorSamplesQueueJobResult() {
    }

    /**
     * Creates a new model given all parameters.
     * @param jobId Job id.
     * @param result Job result.
     * @param status Job status.
     */
    public CollectErrorSamplesQueueJobResult(DqoQueueJobId jobId, ErrorSamplerResult result, DqoJobStatus status) {
        this.jobId = jobId;
        this.result = result;
        this.status = status;
    }

    /**
     * Creates a new model given all parameters.
     * @param jobId Job id.
     */
    public CollectErrorSamplesQueueJobResult(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }

    public static class CollectErrorSamplesQueueJobResultSampleFactory implements SampleValueFactory<CollectErrorSamplesQueueJobResult> {
        @Override
        public CollectErrorSamplesQueueJobResult createSample() {
            return new CollectErrorSamplesQueueJobResult() {{
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
