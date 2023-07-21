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

import com.dqops.core.jobqueue.exceptions.DqoQueueJobCancelledException;

/**
 * Dummy job cancellation token that does nothing and is associated to a job.
 */
public class DummyJobCancellationToken extends JobCancellationToken {
    private boolean isCancelled;

    /**
     * Crates a job cancellation token, given a job that could be cancelled.
     *
     * @param job Job instance.
     */
    public DummyJobCancellationToken(DqoQueueJob<?> job) {
        super(job);
    }

    /**
     * Crates a dummy job cancellation token without a job
     */
    public DummyJobCancellationToken() {
        super(null);
    }

    /**
     * Cancels the job. Also notifies all cancellation listeners that the job was cancelled.
     */
    @Override
    public void cancel() {
        this.isCancelled = true;
    }

    /**
     * Checks if the job was cancelled. This method should be used for a silent (no exception) method of detecting if the job should continue execution or should finish prematurely, because it was cancelled.
     *
     * @return True when the job was cancelled and should stop executing, false when it is not yet cancelled.
     */
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    /**
     * Checks if the job was cancelled.
     * When the job is not cancelled, the method returns silently.
     * When the job was cancelled, it will throw a runtime exception {@link DqoQueueJobCancelledException}.
     */
    @Override
    public void throwIfCancelled() {
        if (this.isCancelled) {
            throw new DqoQueueJobCancelledException();
        }
    }
}
