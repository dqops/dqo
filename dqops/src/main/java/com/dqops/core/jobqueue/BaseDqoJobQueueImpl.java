/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
import com.dqops.core.principal.UserDomainIdentity;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DQOps job queue - manages a pool of threads that are executing operations.
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
    private ConcurrentHashMap<String, DqoQueueJobId> jobBusinessKeyToJobIdMap;
    private Set<DqoJobQueueEntry> runningJobs;
    private List<DqoJobQueueEntry> jobsQueuedBeforeStart = new ArrayList<>();
    private volatile boolean started;
    private volatile boolean stopInProgress;
    private volatile boolean stopped;
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
        if (started || this.stopInProgress) {
            return;
        }

        this.stopped = false;
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
        this.jobBusinessKeyToJobIdMap = new ConcurrentHashMap<>();

        for (int i = 0; i < this.parallelJobLimitProvider.getMaxDegreeOfParallelism(); i++) {
            this.startedThreadsCount.incrementAndGet();
            Future<?> jobProcessingThreadFuture = this.executorService.submit(this::jobProcessingThreadLoop);
            synchronized (this.runnerThreadsFuturesLock) {
                this.runnerThreadsFutures.add(jobProcessingThreadFuture);
            }
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

        log.debug("Job queue " + this.getClass().getName() + " started");
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
                                if (log.isDebugEnabled()) {
                                    log.debug("Job parked due to concurrency constraints: " + dqoJobQueueEntry.getJob());
                                }
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
                    if (log.isDebugEnabled()) {
                        log.debug("Job execution starting: " + dqoJobQueueEntry.getJob());
                    }

                    this.runningJobsCount.incrementAndGet();
                    try {
                        DqoJobExecutionContext jobExecutionContext = new DqoJobExecutionContext(dqoJobQueueEntry.getJobId(), job.getCancellationToken());
                        this.runningJobs.add(dqoJobQueueEntry);
                        job.execute(jobExecutionContext);

                        this.queueMonitoringService.publishJobSucceededEvent(dqoJobQueueEntry);
                        if (log.isDebugEnabled()) {
                            log.debug("Job execution finished: " + dqoJobQueueEntry.getJob());
                        }
                    }
                    catch (DqoQueueJobCancelledException cex) {
                        this.queueMonitoringService.publishJobFullyCancelledEvent(dqoJobQueueEntry);
                    }
                    catch (Throwable ex) {
                        log.error("Failed to execute a job: " + job + ", message: " + ex.getMessage(), ex);
                        this.queueMonitoringService.publishJobFailedEvent(dqoJobQueueEntry, ex.getMessage());
                    }
                    finally {
                        DqoQueueJobId jobId = dqoJobQueueEntry.getJobId();
                        if (this.jobEntriesByJobId != null) {
                            this.jobEntriesByJobId.remove(jobId);
                        }
                        if (jobId.getJobBusinessKey() != null) {
                            if (this.jobBusinessKeyToJobIdMap != null) {
                                this.jobBusinessKeyToJobIdMap.remove(jobId.getJobBusinessKey());
                            }
                        }

                        if (this.runningJobs != null) {
                            this.runningJobs.remove(dqoJobQueueEntry);
                        }

                        this.runningJobsCount.decrementAndGet();
                        if (dqoJobQueueEntry.getJobConcurrencyConstraints() != null) {
                                // tell the limiter that the job has finished
                            this.jobConcurrencyLimiter.notifyJobFinished(dqoJobQueueEntry);
                        }
                    }
                } catch (InterruptedException ex) {
                    break;
                } catch (Throwable ex) {
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

        try {
            int startedThreadsCount = this.startedThreadsCount.get();
            for (int i = 0; i < startedThreadsCount; i++) {
                try {
                    DqoQueueJobId newJobId = this.dqoJobIdGenerator.createNewJobId();
                    this.jobsBlockingQueue.put(new DqoJobQueueEntry(new PoisonDqoJobQueueJob(), newJobId, UserDomainIdentity.ROOT_DATA_DOMAIN));
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
                if (threadFinishFuture == null)  {
                    continue;
                }

                try {
                    threadFinishFuture.get(MAX_WAIT_FOR_THREAD_STOP_MS, TimeUnit.MILLISECONDS);
                }
                catch (Exception ex) {
                    log.error("Waiting for a job queue thread future failed: " + ex.getMessage(), ex);
                }
            }

            for (DqoJobQueueEntry jobBeforeStart : this.jobsQueuedBeforeStart) {
                try {
                    jobBeforeStart.getJob().getCancellationToken().cancel();
                }
                catch (Exception ex) {
                    log.error("Failed to cancel a queued job: " + ex.getMessage(), ex);
                }
            }

            this.jobsQueuedBeforeStart.clear();
            this.executorService.shutdown();

            cancelRemainingJobs();
        }
        finally {
            this.executorService = null;
            this.runnerThreadsFutures = null;
            this.jobsBlockingQueue = null;
            this.runningJobs = null;
            this.jobEntriesByJobId = null;
            this.jobBusinessKeyToJobIdMap = null;
            this.started = false;
            this.stopInProgress = false;
            this.stopped = true;
        }
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
    protected <T> PushJobResult<T> pushJobCore(DqoQueueJob<T> job,
                                               DqoQueueJobId parentJobId,
                                               DqoUserPrincipal principal) {
        if (this.stopped || this.stopInProgress) {
            try {
                job.getCancellationToken().cancel();
            }
            catch (Exception ex) {
                // ignore, race condition was once seen in the Java runtime libraries during shutdown
            }
            return new PushJobResult<>(job.getFinishedFuture(), job.getJobId());
        }

        synchronized (this) {
            if (!this.started) {
                if (job.getJobId() == null) {
                    DqoQueueJobId newJobId = this.dqoJobIdGenerator.createNewJobId();
                    newJobId.setParentJobId(parentJobId);
                    newJobId.setJobBusinessKey(job.getJobBusinessKey());
                    job.setJobId(newJobId);
                }
                DqoJobQueueEntry jobQueueEntry = new DqoJobQueueEntry(job, job.getJobId(), principal.getDataDomainIdentity().getDataDomainCloud());
                job.setPrincipal(principal);
                this.jobsQueuedBeforeStart.add(jobQueueEntry);
                if (log.isDebugEnabled()) {
                    log.debug("Queuing a job to be started when the job queue starts: " + job);
                }

                return new PushJobResult<>(job.getFinishedFuture(), job.getJobId());
            }
        }

        boolean newThreadStarted = this.startNewThreadWhenRequired();
        if (newThreadStarted) {
            log.debug("New processing thread started, total processing threads count: " + this.startedThreadsCount.get());
        }

        try {
            if (job.getJobId() == null) {
                DqoQueueJobId newJobId = this.dqoJobIdGenerator.createNewJobId();
                newJobId.setParentJobId(parentJobId);
                newJobId.setJobBusinessKey(job.getJobBusinessKey());
                job.setJobId(newJobId);
            }
            DqoJobQueueEntry jobQueueEntry = new DqoJobQueueEntry(job, job.getJobId(), principal.getDataDomainIdentity().getDataDomainCloud());
            job.setPrincipal(principal);
            this.jobEntriesByJobId.put(job.getJobId(), jobQueueEntry);
            if (job.getJobBusinessKey() != null) {
                this.jobBusinessKeyToJobIdMap.put(job.getJobBusinessKey(), job.getJobId());
            }

            this.queueMonitoringService.publishJobAddedEvent(jobQueueEntry);

            if (log.isDebugEnabled()) {
                log.debug("Adding a new job to the queue: " + job);
            }
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
        synchronized (this) {
            if (!this.jobsQueuedBeforeStart.isEmpty()) {
                Optional<DqoJobQueueEntry> scheduledJobToStart = this.jobsQueuedBeforeStart.stream()
                        .filter(j -> Objects.equals(j.getJobId(), jobId)).findFirst();
                if (scheduledJobToStart.isPresent()) {
                    this.jobsQueuedBeforeStart.remove(scheduledJobToStart);
                }
            }
        }

        if (!this.started || this.stopInProgress) {
            return; // the job will be cancelled anyway or cannot be started
        }

        DqoJobQueueEntry dqoJobQueueEntry = this.jobEntriesByJobId.get(jobId);
        if (dqoJobQueueEntry != null) {
            this.queueMonitoringService.publishJobCancellationRequestedEvent(dqoJobQueueEntry);
            dqoJobQueueEntry.getJob().getCancellationToken().cancel();
        } else {
            if (!this.started) {
                synchronized (this) {
                    Optional<DqoJobQueueEntry> preStartQueuedJob = this.jobsQueuedBeforeStart.stream().filter(je -> je.getJobId().equals(jobId)).findFirst();
                    if (preStartQueuedJob.isPresent()) {
                        DqoJobQueueEntry preStartJob = preStartQueuedJob.get();
                        this.jobsQueuedBeforeStart.remove(preStartJob);
                        if (preStartJob.getJob() != null && preStartJob.getJob().getJobBusinessKey() != null) {
                            this.jobBusinessKeyToJobIdMap.remove(preStartJob.getJob().getJobBusinessKey());
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds the job id of a job given a job business key, a unique user assigned job id.
     * @param jobBusinessKey Job business key.
     * @return Job id object when the job was found or null.
     */
    public DqoQueueJobId lookupJobIdByBusinessKey(String jobBusinessKey) {
        return this.jobBusinessKeyToJobIdMap.get(jobBusinessKey);
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
