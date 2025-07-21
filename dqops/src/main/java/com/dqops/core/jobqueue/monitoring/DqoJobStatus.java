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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Job status of a job on the queue.
 */
public enum DqoJobStatus {
    /**
     * The job is queued.
     */
    @JsonProperty("queued")
    queued,

    /**
     * The job is now running.
     */
    @JsonProperty("running")
    running,

    /**
     * The job is parked until the concurrency constraints are met.
     */
    @JsonProperty("waiting")
    waiting,  // concurrency constraint is not letting to start the job, so it is waiting

    /**
     * The job has finished successfully.
     */
    @JsonProperty("finished")
    finished,

    /**
     * The job has failed with an execution error.
     */
    @JsonProperty("failed")
    failed,

    /**
     * A request to cancel a job was issued, but the job is not yet cancelled.
     */
    @JsonProperty("cancel_requested")
    cancel_requested,

    /**
     * The job was fully cancelled and removed from the job queue.
     */
    @JsonProperty("cancelled")
    cancelled
}
