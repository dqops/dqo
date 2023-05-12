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
package ai.dqo.core.jobqueue.monitoring;

import ai.dqo.core.configuration.DqoQueueConfigurationProperties;
import ai.dqo.core.jobqueue.DqoJobIdGenerator;
import ai.dqo.core.jobqueue.DqoJobQueueEntry;
import ai.dqo.core.jobqueue.exceptions.DqoQueueJobExecutionException;
import ai.dqo.core.jobqueue.DqoQueueJobId;
import ai.dqo.core.synchronization.status.CloudSynchronizationFoldersStatusModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Job queue monitoring service. Tracks a queue of messages about the job queue that should be pushed back to the client.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class DqoJobQueueMonitoringServiceImpl implements DqoJobQueueMonitoringService {
    private final Object lock = new Object();
    private boolean started;
    private final Duration publishBusyLoopingDuration = Duration.ofSeconds(30);
    private final TreeMap<DqoQueueJobId, DqoJobHistoryEntryModel> allJobs = new TreeMap<>();
    private final TreeMap<Long, DqoJobChangeModel> jobChanges = new TreeMap<>();
    private final DqoJobIdGenerator dqoJobIdGenerator;
    private DqoQueueConfigurationProperties queueConfigurationProperties;
    private Sinks.Many<DqoChangeNotificationEntry> jobUpdateSink;
    private Map<Long, CompletableFuture<Long>> waitingClients = new ConcurrentHashMap<>();
    private volatile CloudSynchronizationFoldersStatusModel currentSynchronizationStatus = new CloudSynchronizationFoldersStatusModel(); // defaults
    private long currentSynchronizationStatusChangeId = 0;


    /**
     * Creates a new job queue monitor.
     * @param dqoJobIdGenerator Job ID generator, used to generate change models in increasing order, ensuring that new jobs have lower ids than changes to the job status.
     * @param queueConfigurationProperties Queue configuration parameters.
     */
    @Autowired
    public DqoJobQueueMonitoringServiceImpl(DqoJobIdGenerator dqoJobIdGenerator,
                                            DqoQueueConfigurationProperties queueConfigurationProperties) {
        this.dqoJobIdGenerator = dqoJobIdGenerator;
        this.queueConfigurationProperties = queueConfigurationProperties;
    }

    /**
     * Starts the queue monitor.
     */
    @Override
    public void start() {
        if (this.started) {
            return;
        }
        this.jobUpdateSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<DqoChangeNotificationEntry> dqoNotificationModelFlux = this.jobUpdateSink.asFlux();
        dqoNotificationModelFlux.subscribeOn(Schedulers.parallel())
                .doOnComplete(() -> releaseAwaitingClients())
                .subscribe(changeNotificationEntry -> onJobChange(changeNotificationEntry));

        this.started = true;
    }

    /**
     * Releases any awaiting clients.
     */
    protected void releaseAwaitingClients() {
        List<CompletableFuture<Long>> awaitingClients;

        synchronized (this.lock) {
            awaitingClients = this.waitingClients.values().stream().collect(Collectors.toList());
            this.waitingClients.clear();
        }

        for (CompletableFuture<Long> awaitingClientFuture :  awaitingClients) {
            awaitingClientFuture.completeExceptionally(new DqoQueueJobExecutionException("DQO job queue was stopped"));
        }
    }

    /**
     * Stops the queue monitoring. All calls to get a list of jobs will fail after the queue monitor was stopped.
     */
    @Override
    public void stop() {
        if (!this.started) {
            return;
        }

        try {
            Sinks.Many<DqoChangeNotificationEntry> currentSink = this.jobUpdateSink;
            this.jobUpdateSink = null;
            currentSink.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
        }
        finally {
            this.started = false;
        }
    }

    /**
     * Publishes a new job on the history.
     * @param jobQueueEntry New job entry.
     */
    @Override
    public void publishJobAddedEvent(DqoJobQueueEntry jobQueueEntry) {
        try {
            DqoJobHistoryEntryModel dqoJobHistoryEntryModel = new DqoJobHistoryEntryModel(jobQueueEntry);
            DqoJobChange dqoJobChange = new DqoJobChange(dqoJobHistoryEntryModel.getStatus(), dqoJobHistoryEntryModel);

            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.FAIL_FAST; // needs reactor 3.5.0:  Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration);
            this.jobUpdateSink.emitNext(new DqoChangeNotificationEntry(dqoJobChange), emitFailureHandler);
        }
        catch (Exception ex) {
            log.error("publishJobAddedEvent failed for job:" + jobQueueEntry.getJobId() + ", error: " + ex.getMessage());
        }
    }

    /**
     * Publishes a notification that a job was started.
     * @param jobQueueEntry Job queue entry.
     */
    @Override
    public void publishJobRunningEvent(DqoJobQueueEntry jobQueueEntry) {
        try {
            DqoJobChange dqoJobChange = new DqoJobChange(DqoJobStatus.running, jobQueueEntry.getJobId());
            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.FAIL_FAST; // needs reactor 3.5.0:  Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration);
            this.jobUpdateSink.emitNext(new DqoChangeNotificationEntry(dqoJobChange), emitFailureHandler);
        }
        catch (Exception ex) {
            log.error("publishJobRunningEvent failed for job:" + jobQueueEntry.getJobId() + ", error: " + ex.getMessage());
        }
    }

    /**
     * Publishes a notification that a job was parked because of the concurrency limit.
     * @param jobQueueEntry Job queue entry.
     */
    @Override
    public void publishJobParkedEvent(DqoJobQueueEntry jobQueueEntry) {
        try {
            DqoJobChange dqoJobChange = new DqoJobChange(DqoJobStatus.waiting, jobQueueEntry.getJobId());
            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.FAIL_FAST; // needs reactor 3.5.0:  Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration);
            this.jobUpdateSink.emitNext(new DqoChangeNotificationEntry(dqoJobChange), emitFailureHandler);
        }
        catch (Exception ex) {
            log.error("publishJobParkedEvent failed for job:" + jobQueueEntry.getJobId() + ", error: " + ex.getMessage());
        }
    }

    /**
     * Publishes a notification that a job has successfully finished.
     * @param jobQueueEntry Job queue entry.
     */
    @Override
    public void publishJobSucceededEvent(DqoJobQueueEntry jobQueueEntry) {
        try {
            DqoJobChange dqoJobChange = new DqoJobChange(DqoJobStatus.succeeded, jobQueueEntry.getJobId());
            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.FAIL_FAST; // needs reactor 3.5.0:  Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration);
            this.jobUpdateSink.emitNext(new DqoChangeNotificationEntry(dqoJobChange), emitFailureHandler);
        }
        catch (Exception ex) {
            log.error("publishJobSucceededEvent failed for job:" + jobQueueEntry.getJobId() + ", error: " + ex.getMessage());
        }
    }

    /**
     * Publishes a notification that a job has failed with an error.
     * @param jobQueueEntry Job queue entry.
     * @param errorMessage Error message.
     */
    @Override
    public void publishJobFailedEvent(DqoJobQueueEntry jobQueueEntry, String errorMessage) {
        try {
            DqoJobChange dqoJobChange = new DqoJobChange(jobQueueEntry.getJobId(), errorMessage);
            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.FAIL_FAST; // needs reactor 3.5.0:  Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration);
            this.jobUpdateSink.emitNext(new DqoChangeNotificationEntry(dqoJobChange), emitFailureHandler);
        }
        catch (Exception ex) {
            log.error("publishJobFailedEvent failed for job:" + jobQueueEntry.getJobId() + ", error: " + ex.getMessage());
        }
    }

    /**
     * Publishes a notification that a request to cancel a job was received.
     *
     * @param jobQueueEntry Job queue entry.
     */
    @Override
    public void publishJobCancellationRequestedEvent(DqoJobQueueEntry jobQueueEntry) {
        try {
            DqoJobChange dqoJobChange = new DqoJobChange(DqoJobStatus.cancel_requested, jobQueueEntry.getJobId());
            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.FAIL_FAST; // needs reactor 3.5.0:  Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration);
            this.jobUpdateSink.emitNext(new DqoChangeNotificationEntry(dqoJobChange), emitFailureHandler);
        }
        catch (Exception ex) {
            log.error("publishJobCancellationRequestedEvent failed for job:" + jobQueueEntry.getJobId() + ", error: " + ex.getMessage());
        }
    }

    /**
     * Publishes a notification that a job has been fully cancelled and removed from the queue.
     *
     * @param jobQueueEntry Job queue entry.
     */
    @Override
    public void publishJobFullyCancelledEvent(DqoJobQueueEntry jobQueueEntry) {
        try {
            DqoJobChange dqoJobChange = new DqoJobChange(DqoJobStatus.cancelled, jobQueueEntry.getJobId());
            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.FAIL_FAST; // needs reactor 3.5.0:  Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration);
            this.jobUpdateSink.emitNext(new DqoChangeNotificationEntry(dqoJobChange), emitFailureHandler);
        }
        catch (Exception ex) {
            log.error("publishJobFullyCancelledEvent failed for job:" + jobQueueEntry.getJobId() + ", error: " + ex.getMessage());
        }
    }

    /**
     * Publishes the current folder synchronization status.
     *
     * @param synchronizationStatus Folder synchronization status.
     */
    @Override
    public void publishFolderSynchronizationStatus(CloudSynchronizationFoldersStatusModel synchronizationStatus) {
        synchronized (this.lock) {
            this.currentSynchronizationStatus = synchronizationStatus;
            this.currentSynchronizationStatusChangeId = this.dqoJobIdGenerator.generateNextIncrementalId();
        }

        try {
            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.FAIL_FAST; // needs reactor 3.5.0:  Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration);
            this.jobUpdateSink.emitNext(new DqoChangeNotificationEntry(synchronizationStatus), emitFailureHandler);
        }
        catch (Exception ex) {
            log.error("publishFolderSynchronizationStatus failed, error: " + ex.getMessage());
        }
    }

    /**
     * Creates an initial job list model that is retrieved by the UI when UI start up and the notification panel
     * must be filled with a list of jobs that are on the queue or jobs that have already finished.
     * @return Initial job queue snapshot.
     */
    @Override
    public Mono<DqoJobQueueInitialSnapshotModel> getInitialJobList() {
        Mono<DqoJobQueueInitialSnapshotModel> jobsMono = Mono.defer(() -> {
            long changeSequence;
            List<DqoJobHistoryEntryModel> jobs;

            synchronized (this.lock) {
                changeSequence = this.dqoJobIdGenerator.generateNextIncrementalId();
                jobs = new ArrayList<>(this.allJobs.values());
            }

            return Mono.just(new DqoJobQueueInitialSnapshotModel(jobs, this.currentSynchronizationStatus, changeSequence));
        });

        return jobsMono;
    }

    /**
     * Waits for a next batch of changes after the <code>lastChangeId</code>. May return an empty list after the timeout.
     * @param lastChangeId Last change id to get changes after.
     * @param timeout Timeout to wait.
     * @param timeUnit Timeout unit.
     * @return Mono with a list of changes and the next sequence id.
     */
    @Override
    public Mono<DqoJobQueueIncrementalSnapshotModel> getIncrementalJobChanges(long lastChangeId, long timeout, TimeUnit timeUnit) {
        List<DqoJobChangeModel> changesList;
        long changeSequence;
        CompletableFuture<DqoJobQueueIncrementalSnapshotModel> waitForChangeFuture = null;

        synchronized (this.lock) {
            if (!this.started) {
                throw new DqoQueueJobExecutionException("Queue is stopped");
            }

            changeSequence = this.dqoJobIdGenerator.generateNextIncrementalId();
            changesList = new ArrayList<>(this.jobChanges
                    .tailMap(lastChangeId, false)
                    .values());
            if (changesList.size() == 0 && lastChangeId >= this.currentSynchronizationStatusChangeId) {
                CompletableFuture<Long> completableFuture = new CompletableFuture<>();
                completableFuture.completeOnTimeout(null, timeout, timeUnit);
                this.waitingClients.put(changeSequence, completableFuture);
                waitForChangeFuture = completableFuture.handleAsync((result, ex) -> {
                    synchronized (this.lock) {
                        this.waitingClients.remove(changeSequence);
                    }
                    if (result == null) {
                        return new DqoJobQueueIncrementalSnapshotModel(null, this.currentSynchronizationStatus, changeSequence);
                    }
                    else {
                        synchronized (this.lock) {
                            long nextChangeId = this.dqoJobIdGenerator.generateNextIncrementalId();
                            List<DqoJobChangeModel> newChangesList = new ArrayList<>(this.jobChanges
                                    .tailMap(lastChangeId, false)
                                    .values());

                            return new DqoJobQueueIncrementalSnapshotModel(newChangesList, this.currentSynchronizationStatus, nextChangeId);
                        }
                    }
                });
            }
        }

        if (changesList.size() > 0) {
            return Mono.just(new DqoJobQueueIncrementalSnapshotModel(changesList, this.currentSynchronizationStatus, changeSequence));
        }

        return Mono.fromFuture(waitForChangeFuture);
    }

    /**
     * Called for each new or updated job.
     * @param changeNotificationEntry Notification entry that should be published.
     */
    public void onJobChange(DqoChangeNotificationEntry changeNotificationEntry) {
        long changeSequence;
        List<CompletableFuture<Long>> awaitersToNotify = null;

        try {
            synchronized (this.lock) {
                changeSequence = this.dqoJobIdGenerator.generateNextIncrementalId(); // serialized change number
                DqoJobChange jobChange = changeNotificationEntry.getJobChange();

                if (jobChange != null) { // the job change is null when we are just publishing a change to the file synchronization status
                    if (jobChange.getStatus() == DqoJobStatus.queued) {
                        // new job
                        this.allJobs.put(jobChange.getJobId(), jobChange.getUpdatedModel());
                        DqoJobChangeModel dqoNewJobChangeModel = new DqoJobChangeModel(jobChange, changeSequence);
                        this.jobChanges.put(changeSequence, dqoNewJobChangeModel);
                    } else {
                        // updated job
                        DqoJobChangeModel dqoUpdatedJobChangeModel = new DqoJobChangeModel(jobChange, changeSequence);
                        this.jobChanges.put(changeSequence, dqoUpdatedJobChangeModel);
                        if (jobChange.getUpdatedModel() != null) {
                            assert Objects.equals(jobChange.getJobId(), jobChange.getUpdatedModel().getJobId());
                            this.allJobs.put(jobChange.getJobId(), jobChange.getUpdatedModel()); // replaces the current model
                        } else {
                            // update in the job
                            DqoJobHistoryEntryModel currentJobEntryModel = this.allJobs.get(jobChange.getJobId());
                            DqoJobHistoryEntryModel clonedJobEntryModel = currentJobEntryModel.clone();
                            clonedJobEntryModel.setStatus(jobChange.getStatus());
                            clonedJobEntryModel.setStatusChangedAt(dqoUpdatedJobChangeModel.getStatusChangedAt());
                            if (jobChange.getErrorMessage() != null) {
                                clonedJobEntryModel.setErrorMessage(jobChange.getErrorMessage());
                                dqoUpdatedJobChangeModel.setUpdatedModel(clonedJobEntryModel);
                            }
                            this.allJobs.put(jobChange.getJobId(), clonedJobEntryModel);
                        }
                    }
                }

                removeOldFinishedJobs();
                removeOldJobChanges();

                if (this.waitingClients.size() > 0) {
                    awaitersToNotify = new ArrayList<>(this.waitingClients.values());
                    this.waitingClients.clear();
                }
            }

            if (awaitersToNotify != null) {
                for (CompletableFuture<Long> awaitingClientFuture : awaitersToNotify) {
                    awaitingClientFuture.complete(changeSequence);
                }
            }
        }
        catch (Exception ex) {
            log.error("onJobChange failed" + ((changeNotificationEntry.getJobChange() != null) ?
                    "for job:" + changeNotificationEntry.getJobChange().getJobId() : "") + ", error: " + ex.getMessage());
        }
    }

    /**
     * Removes old, finished jobs.
     */
    public void removeOldFinishedJobs() {
        Instant oldJobsHistoryThresholdTimestamp = Instant.now().minus(
                this.queueConfigurationProperties.getKeepFinishedJobsHistorySeconds(), ChronoUnit.SECONDS);

        // clean up old jobs
        List<DqoQueueJobId> oldJobIdsToDelete = this.allJobs.entrySet()
                .stream()
                .takeWhile(e -> e.getValue().getStatusChangedAt().compareTo(oldJobsHistoryThresholdTimestamp) < 1)
                .filter(e -> e.getValue().getStatus() == DqoJobStatus.succeeded || e.getValue().getStatus() == DqoJobStatus.failed)
                .map(e -> e.getKey())
                .collect(Collectors.toList());

        if (oldJobIdsToDelete.size() > 0) {
            for (DqoQueueJobId jobId : oldJobIdsToDelete) {
                this.allJobs.remove(jobId);
            }
        }
    }

    /**
     * Removes old job changes.
     */
    public void removeOldJobChanges() {
        Instant oldJobChangesThresholdTimestamp = Instant.now().minus(
                this.queueConfigurationProperties.getKeepJobsChangesHistorySeconds(), ChronoUnit.SECONDS);

        List<Long> oldChangeIdsToDelete = this.jobChanges.entrySet()
                .stream()
                .takeWhile(e -> e.getValue().getStatusChangedAt().compareTo(oldJobChangesThresholdTimestamp) < 1)
                .map(e -> e.getKey())
                .collect(Collectors.toList());

        if (oldChangeIdsToDelete.size() > 0) {
            for (Long changeIdToDelete : oldChangeIdsToDelete) {
                this.jobChanges.remove(changeIdToDelete);
            }
        }
    }
}
