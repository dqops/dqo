package ai.dqo.core.jobqueue;

import java.util.concurrent.CompletableFuture;

/**
 * DQO job queue - manages a pool of threads that are executing operations.
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
     * @return Completable future.
     */
    <T> CompletableFuture<T> pushJob(BaseDqoQueueJob<T> job);
}
