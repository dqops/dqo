/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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

package ai.dqo.core.jobqueue;

import java.util.function.Consumer;

/**
 * Handle returned after registering a job cancellation listener on a job. The handle could be closed, which unregisters the listener.
 */
public class JobCancellationListenerHandle implements AutoCloseable {
    private final JobCancellationToken jobCancellationToken;
    private final Consumer<JobCancellationToken> onJobCancelledCallback;

    /**
     * Creates a job cancellation listener handle.
     * @param jobCancellationToken Job cancellation token where the handle will be registered.
     * @param onJobCancelledCallback Cancellation callback.
     */
    public JobCancellationListenerHandle(JobCancellationToken jobCancellationToken,
                                         Consumer<JobCancellationToken> onJobCancelledCallback) {
        assert jobCancellationToken != null;
        assert onJobCancelledCallback != null;

        this.jobCancellationToken = jobCancellationToken;
        this.onJobCancelledCallback = onJobCancelledCallback;
    }

    /**
     * Returns the job cancellation token on which this cancellation listener handle is registered.
     * @return Parent job cancellation token.
     */
    public JobCancellationToken getJobCancellationToken() {
        return jobCancellationToken;
    }

    /**
     * Returns the callback that should be called when the job is cancelled.
     * @return Job cancellation listener that should be called.
     */
    public Consumer<JobCancellationToken> getOnJobCancelledCallback() {
        return onJobCancelledCallback;
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     */
    @Override
    public void close() {
        this.jobCancellationToken.unregisterListener(this);
    }
}
