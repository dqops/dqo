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

import ai.dqo.core.configuration.DqoQueueConfigurationProperties;
import ai.dqo.core.synchronization.status.FileSynchronizationChangeDetectionService;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
    private final DqoJobConcurrencyLimiter jobConcurrencyLimiter;
    private DqoJobIdGenerator dqoJobIdGenerator;
    private final DqoJobQueueMonitoringService queueMonitoringService;
    private final FileSynchronizationChangeDetectionService fileSynchronizationChangeDetectionService;
    private final AtomicInteger startedThreadsCount = new AtomicInteger();
    private final AtomicInteger runningJobsCount = new AtomicInteger();
    private LinkedBlockingQueue<DqoJobQueueEntry> jobsBlockingQueue;
    private Set<DqoJobQueueEntry> runningJobs;
    private boolean started;
    private ExecutorService executorService;
    private List<Future<?>> runnerThreadsFutures;
    private final Object runnerThreadsFuturesLock = new Object();


    /**
     * Creates a new dqo queue job.
     * @param queueConfigurationProperties dqo.cloud.* configuration parameters.
     * @param jobConcurrencyLimiter Job concurrency limiter.
     * @param dqoJobIdGenerator Job ID generator.
     * @param queueMonitoringService Queue monitoring service.
     * @param fileSynchronizationChangeDetectionService File synchronization change detector, scans local files and detects if there are any unsynchronized changes.
     */
    @Autowired
    public DqoJobQueueImpl(DqoQueueConfigurationProperties queueConfigurationProperties,
                           DqoJobConcurrencyLimiter jobConcurrencyLimiter,
                           DqoJobIdGenerator dqoJobIdGenerator,
                           DqoJobQueueMonitoringService queueMonitoringService,
                           FileSynchronizationChangeDetectionService fileSynchronizationChangeDetectionService) {
        this.queueConfigurationProperties = queueConfigurationProperties;
        this.jobConcurrencyLimiter = jobConcurrencyLimiter;
        this.dqoJobIdGenerator = dqoJobIdGenerator;
        this.queueMonitoringService = queueMonitoringService;
        this.fileSynchronizationChangeDetectionService = fileSynchronizationChangeDetectionService;
    }

    /**
     * Starts the job queue, creates a thread pool.
     */
    @Override
    public void start() {
        if (started) {
            return;
        }

        this.queueMonitoringService.start();
        this.fileSynchronizationChangeDetectionService.detectUnsynchronizedChangesInBackground();
        this.jobsBlockingQueue = new LinkedBlockingQueue<>(queueConfigurationProperties.getMaxNonBlockingCapacity() != null ?
                queueConfigurationProperties.getMaxNonBlockingCapacity() : Integer.MAX_VALUE);

        int threads = this.queueConfigurationProperties.getThreads();
        if (threads < 1 || threads > MAX_THREADS) {
            throw new InvalidQueueConfigurationException("Invalid queue thread count: " + threads);
        }
        this.executorService = Executors.newCachedThreadPool();
        this.runnerThreadsFutures = new ArrayList<>();
        this.runningJobs = ConcurrentHashMap.newKeySet();

        startNewThreadWhenRequired(); // start the first processing thread

        this.started = true;
    }

    /**
     * Starts one more processing thread if all threads are processing jobs and the maximum number of threads is not exceeded.
     * @return True when a new thread was started, false when there was no need to start one more thread.
     */
    public boolean startNewThreadWhenRequired() {
        int startedThreadsCount = this.startedThreadsCount.get();
        if (startedThreadsCount == 0 ||
                (startedThreadsCount < this.queueConfigurationProperties.getThreads() && startedThreadsCount == this.runningJobsCount.get())) {
            if (this.startedThreadsCount.compareAndSet(startedThreadsCount, startedThreadsCount + 1)) {
                Future<?> jobProcessingThreadFuture = this.executorService.submit(this::jobProcessingThreadLoop);
                synchronized (this.runnerThreadsFuturesLock) {
                    this.runnerThreadsFutures.add(jobProcessingThreadFuture);
                }
            }
        }

        return false;
    }

    /**
     * Core method for each job processing thread.
     */
    protected void jobProcessingThreadLoop() {
        LinkedBlockingQueue<DqoJobQueueEntry> jobQueue = this.jobsBlockingQueue;

        try {
            while (true) {
                try {
                    DqoJobQueueEntry dqoJobQueueEntry = this.jobConcurrencyLimiter.takeFirstAvailable();

                    if (dqoJobQueueEntry == null) {  // no parked jobs, waiting cor the parallel limit
                        dqoJobQueueEntry = jobQueue.take(); // waiting on the main blocking queue
                        if (dqoJobQueueEntry.getJobConcurrencyConstraint() != null) {
                            DqoJobQueueEntry unparkedDqoJobToRun = this.jobConcurrencyLimiter.parkOrRegisterStartedJob(dqoJobQueueEntry);
                            if (unparkedDqoJobToRun == null) {
                                this.queueMonitoringService.publishJobParkedEvent(dqoJobQueueEntry);
                                continue; // the job was parked
                            }
                            else {
                                dqoJobQueueEntry = unparkedDqoJobToRun;
                            }
                        }
                    }

                    DqoQueueJob<?> job = dqoJobQueueEntry.getJob();

                    if (job instanceof PoisonDqoJobQueueJob) {
                        return;
                    }

                    this.queueMonitoringService.publishJobRunningEvent(dqoJobQueueEntry);

                    this.runningJobsCount.incrementAndGet();
                    try {
                        if (job.getFuture().isCancelled()) {
                            continue;
                        }
                        DqoJobExecutionContext jobExecutionContext = new DqoJobExecutionContext(dqoJobQueueEntry.getJobId());
                        this.runningJobs.add(dqoJobQueueEntry);
                        job.execute(jobExecutionContext);

                        this.queueMonitoringService.publishJobSucceededEvent(dqoJobQueueEntry);
                    } catch (Exception ex) {
                        log.error("Failed to execute a job: " + ex.getMessage(), ex);
                        this.queueMonitoringService.publishJobFailedEvent(dqoJobQueueEntry, ex.getMessage());
                    }
                    finally {
                        this.runningJobs.remove(dqoJobQueueEntry);
                        this.runningJobsCount.decrementAndGet();
                        if (dqoJobQueueEntry.getJobConcurrencyConstraint() != null) {
                                // tell the limiter that the job has finished
                            this.jobConcurrencyLimiter.notifyJobFinished(dqoJobQueueEntry);
                        }
                    }
                } catch (InterruptedException ex) {
                    break;
                } catch (Exception ex) {
                    log.error("Job failed to execute: " + ex.getMessage(), ex);
                }
            }
        }
        finally {
            this.startedThreadsCount.decrementAndGet();
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

        for (int i = 0; i < this.startedThreadsCount.get(); i++) {
            try {
                DqoQueueJobId newJobId = this.dqoJobIdGenerator.createNewJobId();
                this.jobsBlockingQueue.put(new DqoJobQueueEntry(new PoisonDqoJobQueueJob(), newJobId));
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
        this.queueMonitoringService.stop();

        this.executorService = null;
        this.runnerThreadsFutures = null;
        this.jobsBlockingQueue = null;
        this.runningJobs = null;
        this.started = false;
    }

    /**
     * Pushes a job to the job queue without waiting.
     * @param job Job to be pushed.
     * @return Started job summary and a future to await for finish.
     */
    @Override
    public <T> PushJobResult<T> pushJob(DqoQueueJob<T> job) {
        if (!this.started) {
            throw new IllegalStateException("Cannot publish a job because the job queue is not started yet.");
        }

        boolean newThreadStarted = this.startNewThreadWhenRequired();
        if (newThreadStarted) {
            log.debug("New processing thread started, total processing threads count: " + this.startedThreadsCount.get());
        }

        try {
            DqoQueueJobId newJobId = this.dqoJobIdGenerator.createNewJobId();
            DqoJobQueueEntry jobQueueEntry = new DqoJobQueueEntry(job, newJobId);
            this.queueMonitoringService.publishJobAddedEvent(jobQueueEntry);
            this.jobsBlockingQueue.put(jobQueueEntry);

            return new PushJobResult<>(job.getFuture(), newJobId);
        } catch (InterruptedException e) {
            throw new JobQueuePushFailedException("Cannot push a job to the queue", e);
        }
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
