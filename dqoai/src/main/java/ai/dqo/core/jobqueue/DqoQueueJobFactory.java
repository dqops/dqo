package ai.dqo.core.jobqueue;

import ai.dqo.core.dqocloud.synchronization.SynchronizeRootFolderDqoQueueJob;
import ai.dqo.core.jobqueue.jobs.schema.ImportSchemaQueueJob;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJob;
import ai.dqo.core.scheduler.runcheck.RunScheduledChecksDqoJob;
import ai.dqo.core.scheduler.scan.RunPeriodicMetadataSynchronizationDqoJob;
import ai.dqo.execution.checks.RunChecksQueueJob;

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
     * Creates a DQO Cloud synchronization job that will synchronize one folder in the user home.
     * @return Cloud synchronization job for one folder.
     */
    SynchronizeRootFolderDqoQueueJob createSynchronizeRootFolderJob();

    /**
     * Creates a DQO job that runs a scheduled (every 10 minutes by default) metadata synchronization and detection of new cron schedules.
     * @return Periodic metadata synchronization job.
     */
    RunPeriodicMetadataSynchronizationDqoJob createRunPeriodicMetadataSynchronizationJob();

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
}
