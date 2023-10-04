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
package com.dqops.core.jobqueue.jobs.table;

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.monitoring.DqoJobStatus;
import com.dqops.execution.statistics.jobs.CollectStatisticsResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Object returned from the operation that queues a "import tables" job. The result contains the job id that was started
 * and optionally can also contain the result of importing tables if the operation was started with wait=true parameter to wait for the "import tables" job to finish.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ImportTablesQueueJobResult", description = "Object returned from the operation that queues a \"import tables\" job. " +
        "The result contains the job id that was started and optionally can also contain the result of importing the tables " +
        "if the operation was started with wait=true parameter to wait for the \"import tables\" job to finish.")
@EqualsAndHashCode(callSuper = false)
@Data
public class ImportTablesQueueJobResult {
    /**
     * Job id that identifies a job that was started on the DQO job queue.
     */
    @JsonPropertyDescription("Job id that identifies a job that was started on the DQO job queue.")
    private DqoQueueJobId jobId;

    /**
     * Optional result object that is returned only when the wait parameter was true and the "import tables" job has finished.
     * Contains the list of imported tables.
     * The calling code (the data pipeline) can decide if further processing should be continued.
     */
    @JsonPropertyDescription("Optional result object that is returned only when the wait parameter was true and the \"import tables\" job has finished. " +
            "Contains the summary result of importing tables, including table and column schemas of imported tables. ")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ImportTablesResult result;

    /**
     * Job status.
     */
    @JsonPropertyDescription("Job status")
    private DqoJobStatus status = DqoJobStatus.queued;


    public ImportTablesQueueJobResult() {
    }

    /**
     * Creates a new model given all parameters.
     * @param jobId Job id.
     * @param result Job result.
     * @param status Job status.
     */
    public ImportTablesQueueJobResult(DqoQueueJobId jobId, ImportTablesResult result, DqoJobStatus status) {
        this.jobId = jobId;
        this.result = result;
        this.status = status;
    }

    /**
     * Creates a new model given all parameters.
     * @param jobId Job id.
     */
    public ImportTablesQueueJobResult(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }
}
