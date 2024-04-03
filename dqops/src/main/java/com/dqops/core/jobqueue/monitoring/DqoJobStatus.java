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
