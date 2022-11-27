package ai.dqo.core.jobqueue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Base class for DQO jobs.
 */
public abstract class DqoQueueJob<T> {
    private CompletableFuture<T> future = new CompletableFuture<T>();

    /**
     * Executes the job inside the job queue.
     * @param jobExecutionContext Job execution context.
     */
    public final void execute(DqoJobExecutionContext jobExecutionContext) {
        try {
            T result = this.onExecute(jobExecutionContext);
            this.future.complete(result);
        }
        catch (Exception ex){
            this.future.completeExceptionally(ex);
        }
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    public abstract T onExecute(DqoJobExecutionContext jobExecutionContext);

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     * @return Job type.
     */
    public abstract DqoJobType getJobType();

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    public abstract JobConcurrencyConstraint getConcurrencyConstraint();

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
