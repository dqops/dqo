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
package ai.dqo.core.jobqueue;

import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.data.RepairStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.schema.ImportSchemaQueueJob;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJob;
import ai.dqo.core.scheduler.runcheck.RunScheduledChecksDqoJob;
import ai.dqo.core.synchronization.jobs.SynchronizeMultipleFoldersDqoQueueJob;
import ai.dqo.core.synchronization.jobs.SynchronizeRootFolderDqoQueueJob;
import ai.dqo.execution.checks.jobs.RunChecksOnTableQueueJob;
import ai.dqo.execution.checks.jobs.RunChecksQueueJob;
import ai.dqo.execution.statistics.CollectStatisticsCollectionQueueJob;

/**
 * DQO job instance factory. Creates new instances of jobs that could be configured before they are submitted to the queue.
 */
public interface DqoQueueJobFactory {
    /**
     * Creates a run checks job.
     *
     * @return New run checks job.
     */
    RunChecksQueueJob createRunChecksJob();

    /**
     * Creates a run checks on table job (that runs checks on a single table, within the parallel operations per table limits).
     * @return New run checks on table job.
     */
    RunChecksOnTableQueueJob createRunChecksOnTableJob();

    /**
     * Creates a job that runs profilers.
     * @return New run profilers job.
     */
    CollectStatisticsCollectionQueueJob createRunProfilersJob();

    /**
     * Creates a DQO Cloud synchronization job that will synchronize one folder in the user home.
     * @return Cloud synchronization job for one folder.
     */
    SynchronizeRootFolderDqoQueueJob createSynchronizeRootFolderJob();

    /**
     * Creates a DQO Cloud synchronization parent job that will start multiple child jobs to synchronize multiple DQO User home folders in parallel.
     *
     * @return Cloud synchronization job for multiple folders.
     */
    SynchronizeMultipleFoldersDqoQueueJob createSynchronizeMultipleFoldersJob();

    /**
     * Creates a job that executes all checks scheduled for one cron expression.
     * @return Run scheduled checks job.
     */
    RunScheduledChecksDqoJob createRunScheduledChecksJob();

    /**
     * Creates a job that imports tables from a schema in a source database.
     * @return Schema import job.
     */
    ImportSchemaQueueJob createImportSchemaJob();

    /**
     * Creates a job that imports tables from a schema in a source database.
     * @return Schema import job.
     */
    ImportTablesQueueJob createImportTablesJob();

    /**
     * Creates a job that deletes data from user's ".data" directory.
     * @return Delete stored data job.
     */
    DeleteStoredDataQueueJob createDeleteStoredDataJob();

    /**
     * Creates a job that repairs data in user's ".data" directory.
     * @return Repair stored data job.
     */
    RepairStoredDataQueueJob createRepairStoredDataJob();
}
