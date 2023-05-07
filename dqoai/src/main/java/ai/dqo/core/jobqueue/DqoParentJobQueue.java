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

/**
 * DQO job queue - manages a pool of threads that are executing operations.
 * This is a second job queue that has it's own limits and is meant to accept only parent jobs that will create child jobs on the main job queue.
 */
public interface DqoParentJobQueue {
    /**
     * Starts the job queue, creates a thread pool.
     */
    void start();

    /**
     * Stops the job queue.
     */
    void stop();

    /**
     * Pushes a job to the job queue without waiting.
     *
     * @param job Job to be pushed.
     * @return Started job summary and a future to await for finish.
     */
    <T> PushJobResult<T> pushJob(DqoQueueJob<T> job);
}
