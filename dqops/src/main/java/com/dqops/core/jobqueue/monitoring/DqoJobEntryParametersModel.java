/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
import com.dqops.metadata.scheduling.CronScheduleSpec;
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
     * The job parameters for the "run scheduled checks" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"run scheduled checks\" cron queue job.")
    private CronScheduleSpec runScheduledChecksParameters;

    /**
     * The job parameters for the "collect scheduled statistics" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"collect scheduled statistics\" cron queue job.")
    private CronScheduleSpec collectScheduledStatisticsParameters;

    /**
     * The job parameters for the "auto import tables" queue job.
     */
    @JsonPropertyDescription("The job parameters for the \"auto import tables\" cron queue job.")
    private CronScheduleSpec autoImportTablesParameters;

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
