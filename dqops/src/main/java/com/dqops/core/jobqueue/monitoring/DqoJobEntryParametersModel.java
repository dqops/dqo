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
import com.dqops.execution.statistics.jobs.CollectStatisticsOnTableQueueJobParameters;
import com.dqops.execution.statistics.jobs.CollectStatisticsQueueJobParameters;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Model object returned to UI that has typed fields for each supported job parameter type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DqoJobEntryParametersModel {
    private SynchronizeRootFolderDqoQueueJobParameters synchronizeRootFolderParameters;
    private SynchronizeMultipleFoldersDqoQueueJobParameters synchronizeMultipleFoldersParameters;
    private MonitoringScheduleSpec runScheduledChecksParameters;
    private RunChecksParameters runChecksParameters;
    private RunChecksOnTableParameters runChecksOnTableParameters;
    private CollectStatisticsQueueJobParameters collectStatisticsParameters;
    private CollectStatisticsOnTableQueueJobParameters collectStatisticsOnTableParameters;
    private ImportSchemaQueueJobParameters importSchemaParameters;
    private ImportTablesQueueJobParameters importTableParameters;
    private DeleteStoredDataQueueJobParameters deleteStoredDataParameters;
    private RepairStoredDataQueueJobParameters repairStoredDataParameters;
}
