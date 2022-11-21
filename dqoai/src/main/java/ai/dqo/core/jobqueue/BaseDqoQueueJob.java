package ai.dqo.core.jobqueue;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Base class for DQO jobs.
 */
public abstract class BaseDqoQueueJob<T> {
    private final UUID jobId = UUID.randomUUID();
    private CompletableFuture<T> future = new CompletableFuture<T>();

    /**
     * Executes the job inside the job queue.
     */
    public final void execute() {
        try {
            T result = this.onExecute();
            this.future.complete(result);
        }
        catch (Exception ex){
            this.future.completeExceptionally(ex);
        }
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @return Optional result value that could be returned by the job.
     */
    public abstract T onExecute();

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     * @return Job type.
     */
    public abstract DqoJobType getJobType();

    /**
     * Returns a unique job ID that was assigned to the job.
     * @return Unique job id.
     */
    public UUID getJobId() {
        return jobId;
    }

    /**
     * Returns the job completable future used to await for the job to finish.
     * @return Completable future.
     */
    public CompletableFuture<T> getFuture() {
        return future;
    }

    /**
     * Waits for the result of the job.
     * @return Result of the job.
     */
    public T getResult() {
        try {
            return this.future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new DqoQueueJobExecutionException(e);
        }
    }

    /**
     * Waits until the job finishes. Halts the execution of the current thread.
     */
    public void waitForFinish() {
        getResult();
    }
}
