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

/**
 * Context object passed to a job when it is executed. Provides access to the job id.
 */
public class DqoJobExecutionContext {
    private DqoQueueJobId jobId;
    private JobCancellationToken cancellationToken;

    /**
     * Creates a job execution context.
     * @param jobId Job id.
     * @param cancellationToken Job cancellation token. The job implementation should frequently verify
     *                          if a job was cancelled by checking the cancellation status of this cancellation token.
     */
    public DqoJobExecutionContext(DqoQueueJobId jobId, JobCancellationToken cancellationToken) {
        this.jobId = jobId;
        this.cancellationToken = cancellationToken;
    }

    /**
     * Returns the job id. The job id also contains job ids of its parent jobs.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }

    /**
     * Returns a reference to a job cancellation token. The job cancellation token supports checking whether the job was cancelled by the user, so the
     * job's logic should stop. The job cancellation token also supports registering cancellation listeners when an asynchronous
     * or a blocking operation should be cancelled from a separate thread.
     * @return Job cancellation token.
     */
    public JobCancellationToken getCancellationToken() {
        return cancellationToken;
    }
}
