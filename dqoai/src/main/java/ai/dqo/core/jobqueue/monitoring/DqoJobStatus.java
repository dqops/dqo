/*
 * Copyright © 2022 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
