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
package com.dqops.core.jobqueue.monitoring;

import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.core.jobqueue.jobs.data.RepairStoredDataQueueJobParameters;
import com.dqops.core.jobqueue.jobs.schema.ImportSchemaQueueJobParameters;
import com.dqops.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import com.dqops.core.synchronization.jobs.SynchronizeMultipleFoldersDqoQueueJobParameters;
import com.dqops.core.synchronization.jobs.SynchronizeRootFolderDqoQueueJobParameters;
import com.dqops.execution.checks.jobs.RunChecksOnTableParameters;
import com.dqops.execution.checks.jobs.RunChecksParameters;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesOnTableParameters;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesParameters;
import com.dqops.execution.statistics.jobs.CollectStatisticsOnTableQueueJobParameters;
import com.dqops.execution.statistics.jobs.CollectStatisticsQueueJobParameters;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

/**
 * Model object returned to UI that has typed fields for each supported job parameter type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DqoJobEntryParametersModel {
    /**
     * The job parameters for the "synchronize folder" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"synchronize folder\" queue job.")
    private SynchronizeRootFolderDqoQueueJobParameters synchronizeRootFolderParameters;

    /**
     * The job parameters for the "synchronize multiple folders" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"synchronize multiple folders\" queue job.")
    private SynchronizeMultipleFoldersDqoQueueJobParameters synchronizeMultipleFoldersParameters;

    /**
     * The job parameters for the "run scheduled checks cron" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"run scheduled checks cron\" queue job.")
    private MonitoringScheduleSpec runScheduledChecksParameters;

    /**
     * The job parameters for the "run checks" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"run checks\" queue job.")
    private RunChecksParameters runChecksParameters;

    /**
     * The job parameters for the "run checks on table" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"run checks on table\" queue job.")
    private RunChecksOnTableParameters runChecksOnTableParameters;

    /**
     * The job parameters for the "collect statistics" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"collect statistics\" queue job.")
    private CollectStatisticsQueueJobParameters collectStatisticsParameters;

    /**
     * The job parameters for the "collect statistics on table" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"collect statistics on table\" queue job.")
    private CollectStatisticsOnTableQueueJobParameters collectStatisticsOnTableParameters;

    /**
     * The job parameters for the "collect error samples" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"collect error samples\" queue job.")
    private CollectErrorSamplesParameters collectErrorSamplesParameters;

    /**
     * The job parameters for the "collect error samples on table" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"collect error samples on table\" queue job.")
    private CollectErrorSamplesOnTableParameters collectErrorSamplesOnTableParameters;

    /**
     * The job parameters for the "collect schema" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"collect schema\" queue job.")
    private ImportSchemaQueueJobParameters importSchemaParameters;

    /**
     * The job parameters for the "collect tables" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"collect tables\" queue job.")
    private ImportTablesQueueJobParameters importTableParameters;

    /**
     * The job parameters for the "delete stored data" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"delete stored data\" queue job.")
    private DeleteStoredDataQueueJobParameters deleteStoredDataParameters;

    /**
     * The job parameters for the "repair stored data" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"repair stored data\" queue job.")
    private RepairStoredDataQueueJobParameters repairStoredDataParameters;
}
