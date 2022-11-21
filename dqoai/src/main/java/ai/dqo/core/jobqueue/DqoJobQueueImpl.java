package ai.dqo.core.jobqueue;

import ai.dqo.core.configuration.DqoQueueConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * DQO job queue - manages a pool of threads that are executing operations.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class DqoJobQueueImpl implements DqoJobQueue, InitializingBean, DisposableBean {
    public static final int MAX_THREADS = 1024;
    public static final int MAX_WAIT_FOR_THREAD_STOP_MS = 5000;

    private final DqoQueueConfigurationProperties queueConfigurationProperties;
    private LinkedBlockingQueue<BaseDqoQueueJob<?>> jobsBlockingQueue;
    private boolean started;
    private ExecutorService executorService;
    private List<Future<?>> runnerThreadsFutures;

    /**
     * Creates a new dqo queue job.
     * @param queueConfigurationProperties dqo.cloud.* configuration parameters.
     */
    @Autowired
    public DqoJobQueueImpl(DqoQueueConfigurationProperties queueConfigurationProperties) {
        this.queueConfigurationProperties = queueConfigurationProperties;
    }

    /**
     * Starts the job queue, creates a thread pool.
     */
    @Override
    public void start() {
        if (started) {
            return;
        }

        this.jobsBlockingQueue = new LinkedBlockingQueue<>(queueConfigurationProperties.getMaxNonBlockingCapacity() != null ?
                queueConfigurationProperties.getMaxNonBlockingCapacity() : Integer.MAX_VALUE);

        int threads = this.queueConfigurationProperties.getThreads();
        if (threads < 1 || threads > MAX_THREADS) {
            throw new InvalidQueueConfigurationException("Invalid queue thread count: " + threads);
        }
        this.executorService = Executors.newFixedThreadPool(threads);
        this.runnerThreadsFutures = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            Future<?> jobProcessingThreadFuture = this.executorService.submit(this::jobProcessingThreadLoop);
            this.runnerThreadsFutures.add(jobProcessingThreadFuture);
        }

        this.started = true;
    }

    /**
     * Core method for each job processing thread.
     */
    protected void jobProcessingThreadLoop() {
        LinkedBlockingQueue<BaseDqoQueueJob<?>> jobQueue = this.jobsBlockingQueue;

        while(true) {
            try {
                BaseDqoQueueJob<?> job = jobQueue.take();
                if (job instanceof PoisonDqoJobQueueJob) {
                    return;
                }

                try {
                    if (job.getFuture().isCancelled()) {
                        continue;
                    }
                    job.execute();
                }
                catch (Exception ex) {
                    log.error("Failed to execute a job: " + ex.getMessage(), ex);
                }
            } catch (InterruptedException ex) {
                break;
            }
            catch (Exception ex) {
                log.error("Job failed to execute: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Stops the job queue.
     */
    @Override
    public void stop() {
        if (!this.started) {
            return;
        }

        for (int i = 0; i < this.runnerThreadsFutures.size(); i++) {
            try {
                this.jobsBlockingQueue.put(new PoisonDqoJobQueueJob());
            } catch (InterruptedException ex) {
                log.error("Job queue stop() operation failed to publish a poison message", ex);
            }
        }

        for (int i = 0; i < this.runnerThreadsFutures.size(); i++) {
            Future<?> threadFinishFuture = this.runnerThreadsFutures.get(i);
            try {
                threadFinishFuture.get(MAX_WAIT_FOR_THREAD_STOP_MS, TimeUnit.MILLISECONDS);
            }
            catch (Exception ex) {
                log.error("Waiting for a job queue thread future failed: " + ex.getMessage(), ex);
            }
        }

        this.executorService.shutdown();

        this.executorService = null;
        this.runnerThreadsFutures = null;
        this.jobsBlockingQueue = null;
        this.started = false;
    }

    /**
     * Pushes a job to the job queue without waiting.
     * @param job Job to be pushed.
     * @return Completable future.
     */
    @Override
    public <T> CompletableFuture<T> pushJob(BaseDqoQueueJob<T> job) {
        if (!this.started) {
            throw new IllegalStateException("Cannot publish a job because the job queue is not started yet.");
        }

        try {
            this.jobsBlockingQueue.put(job);
        } catch (InterruptedException e) {
            throw new JobQueuePushFailedException("Cannot push a job to the queue", e);
        }

        return job.getFuture();
    }

    /**
     * Called by Spring when the context is initializing. Starts the job queue.
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }

    /**
     * Called by Spring when the application is shutting down. Stops the job queue.
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        this.stop();
    }
}
