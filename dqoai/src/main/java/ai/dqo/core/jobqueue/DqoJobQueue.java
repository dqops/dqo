package ai.dqo.core.jobqueue;

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
     * @return Started job summary and a future to await for finish.
     */
    <T> PushJobResult<T> pushJob(DqoQueueJob<T> job);
}
