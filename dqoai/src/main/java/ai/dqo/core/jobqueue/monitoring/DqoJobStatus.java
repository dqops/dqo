package ai.dqo.core.jobqueue.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Job status of a job on the queue.
 */
public enum DqoJobStatus {
    @JsonProperty("queued")
    QUEUED,

    @JsonProperty("running")
    RUNNING,

    @JsonProperty("waiting")
    WAITING,  // concurrency constraint is not letting to start the job, so it is waiting

    @JsonProperty("succeeded")
    SUCCEEDED,

    @JsonProperty("failed")
    FAILED
}
