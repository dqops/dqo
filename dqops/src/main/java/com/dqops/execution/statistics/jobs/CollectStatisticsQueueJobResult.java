/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.statistics.jobs;

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.monitoring.DqoJobStatus;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Object returned from the operation that queues a "collect statistics" job. The result contains the job id that was started
 * and optionally can also contain the result of collecting the statistics if the operation was started with wait=true parameter to wait for the "collect statistics" job to finish.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "CollectStatisticsQueueJobResult", description = "Object returned from the operation that queues a \"collect statistics\" job. " +
        "The result contains the job id that was started and optionally can also contain the result of collecting the statistics  " +
        "if the operation was started with wait=true parameter to wait for the \"collect statistics\" job to finish.")
@EqualsAndHashCode(callSuper = false)
@Data
public class CollectStatisticsQueueJobResult {
    /**
     * Job id that identifies a job that was started on the DQOps job queue.
     */
    @JsonPropertyDescription("Job id that identifies a job that was started on the DQOps job queue.")
    private DqoQueueJobId jobId;

    /**
     * Optional result object that is returned only when the wait parameter was true and the "run checks" job has finished. Contains the summary result of the data quality checks executed, including the severity of the most severe issue detected.
     * The calling code (the data pipeline) can decide if further processing should be continued.
     */
    @JsonPropertyDescription("Optional result object that is returned only when the wait parameter was true and the \"collect statistics\" job has finished. " +
            "Contains the summary result of collecting basic statistics, including the number of statistics collectors (queries) that managed to capture metrics about the table(s). ")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CollectStatisticsResult result;

    /**
     * Job status.
     */
    @JsonPropertyDescription("Job status")
    private DqoJobStatus status = DqoJobStatus.queued;


    public CollectStatisticsQueueJobResult() {
    }

    /**
     * Creates a new model given all parameters.
     * @param jobId Job id.
     * @param result Job result.
     * @param status Job status.
     */
    public CollectStatisticsQueueJobResult(DqoQueueJobId jobId, CollectStatisticsResult result, DqoJobStatus status) {
        this.jobId = jobId;
        this.result = result;
        this.status = status;
    }

    /**
     * Creates a new model given all parameters.
     * @param jobId Job id.
     */
    public CollectStatisticsQueueJobResult(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }

    public static class CollectStatisticsQueueJobResultSampleFactory implements SampleValueFactory<CollectStatisticsQueueJobResult> {
        @Override
        public CollectStatisticsQueueJobResult createSample() {
            return new CollectStatisticsQueueJobResult() {{
                setJobId(new DqoQueueJobId.DqoQueueJobIdSampleFactory().createSample());
                setStatus(DqoJobStatus.finished);
                setResult(new CollectStatisticsResult() {{
                    setExecutedStatisticsCollectors(3);
                    setColumnsAnalyzed(1);
                    setColumnsSuccessfullyAnalyzed(0);
                    setTotalCollectorsFailed(1);
                    setTotalCollectedResults(2);
                }});
            }};
        }
    }
}
