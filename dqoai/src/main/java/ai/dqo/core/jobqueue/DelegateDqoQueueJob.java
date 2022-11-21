package ai.dqo.core.jobqueue;

import java.util.function.Supplier;

/**
 * DQO Job that executes a given job queue.
 * @param <T> Result type.
 */
public class DelegateDqoQueueJob<T> extends BaseDqoQueueJob<T> {
    private Supplier<T> callee;

    /**
     * Creates a delegated job that will execute a given delegate provided as a lambda function.
     * @param callee Function to call inside the job.
     */
    public DelegateDqoQueueJob(Supplier<T> callee) {
        this.callee = callee;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public T onExecute() {
        return this.callee.get();
    }
}
