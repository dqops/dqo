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

package com.dqops.core.jobqueue.exceptions;

import com.dqops.core.jobqueue.DqoQueueJob;
import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.utils.exceptions.DqoRuntimeException;

/**
 * Exception thrown when a job was cancelled. It is a runtime exception.
 */
public class DqoQueueJobCancelledException extends DqoRuntimeException {
    private DqoQueueJob<?> job;
    private DqoQueueJobId jobId;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public DqoQueueJobCancelledException() {
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.
     */
    public DqoQueueJobCancelledException(DqoQueueJob<?> job) {
        this.job = job;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.
     */
    public DqoQueueJobCancelledException(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }

    /**
     * Returns the job that was cancelled.
     * @return Cancelled job.
     */
    public DqoQueueJob<?> getJob() {
        return job;
    }

    /**
     * Returns the cancelled job id.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }
}
