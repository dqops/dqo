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

package com.dqops.core.jobqueue;

import com.dqops.core.jobqueue.monitoring.DqoJobStatus;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.google.api.gax.rpc.InvalidArgumentException;

/**
 * DQOps job completion status, which says how a job has finished: the job was finished, the job failed with an exception, the job was cancelled.
 */
public enum DqoJobCompletionStatus {
    /**
     * The job has finishes successfully.
     */
    FINISHED,

    /**
     * The job failed with an exception.
     */
    FAILED,

    /**
     * The job was cancelled.
     */
    CANCELLED;

    /**
     * Converts the job status calculated from the completable future to a compatible job status.
     * Not all statuses are covered.
     * @return Job status.
     */
    public DqoJobStatus toJobStatus() {
        switch (this) {
            case FINISHED:
                return DqoJobStatus.finished;
            case FAILED:
                return DqoJobStatus.failed;
            case CANCELLED:
                return DqoJobStatus.cancelled;
            default:
                throw new DqoRuntimeException("Unknown status " + this);
        }
    }
}
