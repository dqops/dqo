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

import java.util.concurrent.CompletableFuture;

/**
 * Result object returned for a new job that is started. Returns a job ID and a completable future to wait for the job to finish.
 * @param <T> Job result type.
 */
public class PushJobResult<T> {
    private CompletableFuture<T> future;
    private DqoQueueJobId jobId;

    /**
     * Creates a result object for an operation that started a new job.
     * @param future Future to await for the result.
     * @param jobId Job id.
     */
    public PushJobResult(CompletableFuture<T> future, DqoQueueJobId jobId) {
        this.future = future;
        this.jobId = jobId;
    }

    /**
     * Future to await for the job to finish. Provides access to the result of a job if a job produces any result.
     * @return Future to await for the job to finish.
     */
    public CompletableFuture<T> getFuture() {
        return future;
    }

    /**
     * Returns the job id that was assigned to the job.
     * @return Job id of a new job.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }
}
