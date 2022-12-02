package ai.dqo.core.jobqueue;

/**
 * The type of concurrent job on which concurrency limits are applied.
 */
public enum ConcurrentJobType {
    /**
     * All sorts of operations that run checks, executed instantly or scheduled.
     */
    RUN_CHECKS,

    /**
     * Cloud sync operations on a folder level.
     */
    SYNCHRONIZE_FOLDER,

    /**
     * Synchronizes the metadata that the scheduler knows. Detects new or disabled schedules (cron expressions).
     */
    SYNCHRONIZE_SCHEDULER_METADATA,

    /**
     * Import tables from one schema on a source connection.
     */
    IMPORT_SCHEMA,

    /**
     * Import tables from a source connection, given a list of table names.
     */
    IMPORT_TABLES,

//    /**
//     * Import a table from a source connection.
//     */
//    IMPORT_TABLE,
}
