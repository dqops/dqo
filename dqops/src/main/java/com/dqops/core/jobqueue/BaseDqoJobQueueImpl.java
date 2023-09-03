/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.core.jobqueue;

import com.dqops.core.configuration.DqoQueueConfigurationProperties;
import com.dqops.core.jobqueue.concurrency.DqoJobConcurrencyLimiter;
import com.dqops.core.jobqueue.concurrency.ParallelJobLimitProvider;
import com.dqops.core.jobqueue.exceptions.DqoInvalidQueueConfigurationException;
import com.dqops.core.jobqueue.exceptions.DqoJobQueuePushFailedException;
import com.dqops.core.jobqueue.exceptions.DqoQueueJobCancelledException;
import com.dqops.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import com.dqops.core.principal.DqoUserPrincipal;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * DQO job queue - manages a pool of threads that are executing operations.
 * This is a base abstract class for two job queues, one is the main job queue that runs independent jobs and child jobs, but not parent jobs: {@link DqoJobQueueImpl}.
 * The other subclass is the {@link ParentDqoJobQueueImpl} that runs only {@link ParentDqoQueueJob} jobs that should not perform too much logic, they should just start child jobs on the {@link DqoJobQueueImpl} main queue and wait.
 */
@Slf4j
public abstract class BaseDqoJobQueueImpl implements DisposableBean {
    public static final int MAX_THREADS = 1024;
    public static final int MAX_WAIT_FOR_THREAD_STOP_MS = 5000;

    private final DqoQueueConfigurationProperties queueConfigurationProperties;
    protected ParallelJobLimitProvider parallelJobLimitProvider;
    private final DqoJobConcurrencyLimiter jobConcurrencyLimiter;
    private final DqoJobIdGenerator dqoJobIdGenerator;
    private final DqoJobQueueMonitoringService queueMonitoringService;
    private final AtomicInteger startedThreadsCount = new AtomicInteger();
    private final AtomicInteger runningJobsCount = new AtomicInteger();
    private LinkedBlockingQueue<DqoJobQueueEntry> jobsBlockingQueue;
    private ConcurrentHashMap<DqoQueueJobId, DqoJobQueueEntry> jobEntriesByJobId;
    private Set<DqoJobQueueEntry> runningJobs;
    private List<DqoJobQueueEntry> jobsQueuedBeforeStart = new ArrayList<>();
    private volatile boolean started;
    private volatile boolean stopInProgress;
    private ExecutorService executorService;
    private List<Future<?>> runnerThreadsFutures;
    private final Object runnerThreadsFuturesLock = new Object();


    /**
     * Creates a new dqo queue job.
     * @param queueConfigurationProperties dqo.cloud.* configuration parameters.
     * @param jobConcurrencyLimiter Job concurrency limiter.
     * @param parallelJobLimitProvider Dependency to a service that retrieves the parallel threads limit that is supported.
     * @param dqoJobIdGenerator Job ID generator.
     * @param queueMonitoringService Queue monitoring service.
     */
    @Autowired
    public BaseDqoJobQueueImpl(DqoQueueConfigurationProperties queueConfigurationProperties,
                               ParallelJobLimitProvider parallelJobLimitProvider,
                               DqoJobConcurrencyLimiter jobConcurrencyLimiter,
                               DqoJobIdGenerator dqoJobIdGenerator,
                               DqoJobQueueMonitoringService queueMonitoringService) {
        this.queueConfigurationProperties = queueConfigurationProperties;
        this.parallelJobLimitProvider = parallelJobLimitProvider;
        this.jobConcurrencyLimiter = jobConcurrencyLimiter;
        this.dqoJobIdGenerator = dqoJobIdGenerator;
        this.queueMonitoringService = queueMonitoringService;
    }

    /**
     * Starts the job queue, creates a thread pool.
     */
    public void start() {
        if (started) {
            return;
        }

        this.jobsBlockingQueue = new LinkedBlockingQueue<>(queueConfigurationProperties.getMaxNonBlockingCapacity() != null ?
                queueConfigurationProperties.getMaxNonBlockingCapacity() : Integer.MAX_VALUE);

        int threads = this.parallelJobLimitProvider.getMaxDegreeOfParallelism();
        if (threads < 1 || threads > MAX_THREADS) {
            throw new DqoInvalidQueueConfigurationException("Invalid queue thread count: " + threads);
        }
        this.executorService = Executors.newCachedThreadPool();
        this.runnerThreadsFutures = new ArrayList<>();
        this.runningJobs = ConcurrentHashMap.newKeySet();
        this.jobEntriesByJobId = new ConcurrentHashMap<DqoQueueJobId, DqoJobQueueEntry>();

        for (int i = 0; i < this.parallelJobLimitProvider.getMaxDegreeOfParallelism(); i++) {
            this.startedThreadsCount.incrementAndGet();
            Future<?> jobProcessingThreadFuture = this.executorService.submit(this::jobProcessingThreadLoop);
            this.runnerThreadsFutures.add(jobProcessingThreadFuture);
        }

        List<DqoJobQueueEntry> preStartQueuedJobs;

        synchronized (this) {
            this.started = true;

            if (this.jobsQueuedBeforeStart.size() > 0) {
                preStartQueuedJobs = new ArrayList<>(this.jobsQueuedBeforeStart);
                Lists.reverse(preStartQueuedJobs);
                this.jobsQueuedBeforeStart = new ArrayList<>();
            } else {
                preStartQueuedJobs = new ArrayList<>();
            }
        }

        for (DqoJobQueueEntry jobQueueEntry : preStartQueuedJobs) {
            this.pushJobCore(jobQueueEntry.getJob(), jobQueueEntry.getJobId().getParentJobId(), jobQueueEntry.getJob().getPrincipal());
        }
    }

    /**
     * Checks if the job queue is started and is running. The health check depends on a working job queue.
     * @return True when the job queue is started and is running, false when the job queue was not yet started or is stopping.
     */
    public boolean isStarted() {
        return this.started;
    }

    /**
     * Starts one more processing thread if all threads are processing jobs and the maximum number of threads is not exceeded.
     * @return True when a new thread was started, false when there was no need to start one more thread.
     */
    public boolean startNewThreadWhenRequired() {
        int startedThreadsCount = this.startedThreadsCount.get();
        if (startedThreadsCount == 0 ||
                (startedThreadsCount < this.parallelJobLimitProvider.getMaxDegreeOfParallelism() && startedThreadsCount == this.runningJobsCount.get())) {
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

                    if (dqoJobQueueEntry == null) {  // no parked jobs, waiting for the parallel limit
                        dqoJobQueueEntry = jobQueue.take(); // waiting on the main blocking queue
                        if (dqoJobQueueEntry.getJobConcurrencyConstraints() != null) {
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

                    if (job.getFinishedFuture().isCancelled()) {
                        this.queueMonitoringService.publishJobFullyCancelledEvent(dqoJobQueueEntry);
                        continue;
                    }

                    this.queueMonitoringService.publishJobRunningEvent(dqoJobQueueEntry);

                    this.runningJobsCount.incrementAndGet();
                    try {
                        DqoJobExecutionContext jobExecutionContext = new DqoJobExecutionContext(dqoJobQueueEntry.getJobId(), job.getCancellationToken());
                        this.runningJobs.add(dqoJobQueueEntry);
                        job.execute(jobExecutionContext);

                        this.queueMonitoringService.publishJobSucceededEvent(dqoJobQueueEntry);
                    }
                    catch (DqoQueueJobCancelledException cex) {
                        this.queueMonitoringService.publishJobFullyCancelledEvent(dqoJobQueueEntry);
                    }
                    catch (Exception ex) {
                        log.error("Failed to execute a job: " + ex.getMessage(), ex);
                        this.queueMonitoringService.publishJobFailedEvent(dqoJobQueueEntry, ex.getMessage());
                    }
                    finally {
                        this.jobEntriesByJobId.remove(dqoJobQueueEntry.getJobId());
                        this.runningJobs.remove(dqoJobQueueEntry);
                        this.runningJobsCount.decrementAndGet();
                        if (dqoJobQueueEntry.getJobConcurrencyConstraints() != null) {
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
    public void stop() {
        if (!this.started || this.stopInProgress) {
            return;
        }

        this.stopInProgress = true;

        int startedThreadsCount = this.startedThreadsCount.get();
        for (int i = 0; i < startedThreadsCount; i++) {
            try {
                DqoQueueJobId newJobId = this.dqoJobIdGenerator.createNewJobId();
                this.jobsBlockingQueue.put(new DqoJobQueueEntry(new PoisonDqoJobQueueJob(), newJobId));
            } catch (InterruptedException ex) {
                log.error("Job queue stop() operation failed to publish a poison message", ex);
            }
        }

        List<Future<?>> threadFinishFutures = null;
        synchronized (this.runnerThreadsFuturesLock) {
            threadFinishFutures = new ArrayList<>(this.runnerThreadsFutures);
        }

        for (int i = 0; i < threadFinishFutures.size(); i++) {
            Future<?> threadFinishFuture = threadFinishFutures.get(i);
            try {
                threadFinishFuture.get(MAX_WAIT_FOR_THREAD_STOP_MS, TimeUnit.MILLISECONDS);
            }
            catch (Exception ex) {
                log.error("Waiting for a job queue thread future failed: " + ex.getMessage(), ex);
            }
        }

        this.executorService.shutdown();

        cancelRemainingJobs();

        this.executorService = null;
        this.runnerThreadsFutures = null;
        this.jobsBlockingQueue = null;
        this.runningJobs = null;
        this.jobEntriesByJobId = null;
        this.started = false;
        this.stopInProgress = false;
    }

    /**
     * Cancels all remaining jobs.
     */
    protected void cancelRemainingJobs() {
        DqoJobQueueEntry[] remainingJobs = this.jobsBlockingQueue.toArray(DqoJobQueueEntry[]::new);
        for (DqoJobQueueEntry jobEntry : remainingJobs) {
            try {
                jobEntry.getJob().getCancellationToken().cancel();
            }
            catch (Exception ex) {
                log.error("Cancelling an unscheduled job failed: " + ex.getMessage(), ex);
            }
        }

        DqoJobQueueEntry[] remainingParkedJobs = this.jobConcurrencyLimiter.getParkedJobs();
        for (DqoJobQueueEntry jobEntry : remainingParkedJobs) {
            try {
                jobEntry.getJob().getCancellationToken().cancel();
            }
            catch (Exception ex) {
                log.error("Cancelling an unscheduled job failed: " + ex.getMessage(), ex);
            }
        }

        this.jobConcurrencyLimiter.clear();
    }

    /**
     * Pushes a job to the job queue without waiting.
     * @param job Job to be pushed.
     * @param parentJobId Parent job id.
     * @param principal Principal that will be used to run the job.
     * @return Started job summary and a future to await for finish.
     */
    protected <T> PushJobResult<T> pushJobCore(DqoQueueJob<T> job, DqoQueueJobId parentJobId, DqoUserPrincipal principal) {
        if (!this.started) {
            if (job.getJobId() == null) {
                DqoQueueJobId newJobId = this.dqoJobIdGenerator.createNewJobId();
                newJobId.setParentJobId(parentJobId);
                job.setJobId(newJobId);
            }
            DqoJobQueueEntry jobQueueEntry = new DqoJobQueueEntry(job, job.getJobId());
            job.setPrincipal(principal);
            synchronized (this) {
                this.jobsQueuedBeforeStart.add(jobQueueEntry);
            }
            return new PushJobResult<>(job.getFinishedFuture(), job.getJobId());
        }

        boolean newThreadStarted = this.startNewThreadWhenRequired();
        if (newThreadStarted) {
            log.debug("New processing thread started, total processing threads count: " + this.startedThreadsCount.get());
        }

        try {
            if (job.getJobId() == null) {
                DqoQueueJobId newJobId = this.dqoJobIdGenerator.createNewJobId();
                newJobId.setParentJobId(parentJobId);
                job.setJobId(newJobId);
            }
            DqoJobQueueEntry jobQueueEntry = new DqoJobQueueEntry(job, job.getJobId());
            job.setPrincipal(principal);
            this.jobEntriesByJobId.put(job.getJobId(), jobQueueEntry);

            this.queueMonitoringService.publishJobAddedEvent(jobQueueEntry);
            this.jobsBlockingQueue.put(jobQueueEntry);

            return new PushJobResult<>(job.getFinishedFuture(), job.getJobId());
        } catch (InterruptedException e) {
            throw new DqoJobQueuePushFailedException("Cannot push a job to the queue", e);
        }
    }

    /**
     * Cancels a job given a job id.
     * @param jobId Job id.
     */
    public void cancelJob(DqoQueueJobId jobId) {
        DqoJobQueueEntry dqoJobQueueEntry = this.jobEntriesByJobId.get(jobId);
        if (dqoJobQueueEntry != null) {
            this.queueMonitoringService.publishJobCancellationRequestedEvent(dqoJobQueueEntry);
            dqoJobQueueEntry.getJob().getCancellationToken().cancel();
        } else {
            if (!this.started) {
                synchronized (this) {
                    Optional<DqoJobQueueEntry> preStartQueuedJob = this.jobsQueuedBeforeStart.stream().filter(je -> je.getJobId().equals(jobId)).findFirst();
                    if (preStartQueuedJob.isPresent()) {
                        this.jobsQueuedBeforeStart.remove(preStartQueuedJob.get());
                    }
                }
            }
            return;
        }


        // TODO: cancel also a job before it even started execution (remove from the queue, notify the job queue monitoring service)
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
