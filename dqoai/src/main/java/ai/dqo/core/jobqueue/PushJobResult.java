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
