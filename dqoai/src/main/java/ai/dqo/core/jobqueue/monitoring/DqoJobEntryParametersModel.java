/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.jobqueue.monitoring;

import ai.dqo.core.dqocloud.synchronization.SynchronizeRootFolderDqoQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.schema.ImportSchemaQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import ai.dqo.core.scheduler.schedules.RunChecksCronSchedule;
import ai.dqo.execution.checks.RunChecksQueueJobParameters;
import ai.dqo.execution.profiler.RunProfilersQueueJobParameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Model object returned to UI that has typed fields for each supported job parameter type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DqoJobEntryParametersModel {
    private SynchronizeRootFolderDqoQueueJobParameters synchronizeRootFolderParameters;
    private RunChecksCronSchedule runScheduledChecksParameters;
    private RunChecksQueueJobParameters runChecksParameters;
    private RunProfilersQueueJobParameters runProfilersParameters;
    private ImportSchemaQueueJobParameters importSchemaParameters;
    private ImportTablesQueueJobParameters importTableParameters;
    private DeleteStoredDataQueueJobParameters deleteStoredDataParameters;
}
