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

import java.util.Collection;

/**
 * DQO job queue - manages a pool of threads that are executing operations.
 * This is the entry point to manage the main DQO job queue that runs independent and child jobs, but not parent jobs.
 */
public interface DqoJobQueue {
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

    /**
     * Pushes a job to the job queue without waiting, storing also a reference to the parent job.
     *
     * @param job Job to be pushed.
     * @param parentJobId Parent job id.
     * @return Started job summary and a future to await for finish.
     */
    <T> PushJobResult<T> pushJob(DqoQueueJob<T> job, DqoQueueJobId parentJobId);

    /**
     * Pushes a collection of child jobs.
     * @param childJobs Collection of child jobs.
     * @param parentJobId Parent job id.
     * @return Child jobs container that will track the completion of all child jobs.
     * @param <T> Child job result type.
     */
    <T> ChildDqoQueueJobsContainer<T> pushChildJobs(Collection<DqoQueueJob<T>> childJobs, DqoQueueJobId parentJobId);

    /**
     * Cancels a job given a job id.
     * @param jobId Job id.
     */
    void cancelJob(DqoQueueJobId jobId);
}
