package ai.dqo.core.jobqueue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Job type that identifies a job by type.
 */
public enum DqoJobType {
    @JsonProperty("run checks")
    RUN_CHECKS,

    @JsonProperty("queue thread shutdown")
    QUEUE_THREAD_SHUTDOWN,

    @JsonProperty("synchronize folder")
    SYNCHRONIZE_FOLDER,

    @JsonProperty("scheduled synchronization")
    SCHEDULED_SYNCHRONIZATION,

    @JsonProperty("run scheduled checks by cron")
    RUN_SCHEDULED_CHECKS_CRON,

    @JsonProperty("import schema")
    IMPORT_SCHEMA,

    @JsonProperty("import selected tables")
    IMPORT_TABLES,

//    @JsonProperty("import table")
//    IMPORT_TABLE,
}
