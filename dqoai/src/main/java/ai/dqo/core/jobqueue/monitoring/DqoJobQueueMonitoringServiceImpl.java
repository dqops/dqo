package ai.dqo.core.jobqueue.monitoring;

import ai.dqo.core.configuration.DqoQueueConfigurationProperties;
import ai.dqo.core.jobqueue.DqoJobIdGenerator;
import ai.dqo.core.jobqueue.DqoJobQueueEntry;
import ai.dqo.core.jobqueue.DqoQueueJobId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Job queue monitoring service. Tracks a queue of messages about the job queue that should be pushed back to the client.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class DqoJobQueueMonitoringServiceImpl implements DqoJobQueueMonitoringService {
    private final Object lock = new Object();
    private final Duration publishBusyLoopingDuration = Duration.ofSeconds(30);
    private final TreeMap<DqoQueueJobId, DqoJobHistoryEntryModel> allJobs = new TreeMap<>();
    private final TreeMap<Long, DqoJobChangeModel> jobChanges = new TreeMap<>();
    private final DqoJobIdGenerator dqoJobIdGenerator;
    private DqoQueueConfigurationProperties queueConfigurationProperties;
    private Sinks.Many<DqoJobChange> jobUpdateSink;


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
        this.jobUpdateSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<DqoJobChange> dqoJobChangeModelFlux = this.jobUpdateSink.asFlux();
        dqoJobChangeModelFlux.publishOn(Schedulers.parallel())
                .subscribe(jobModel -> onJobChange(jobModel));
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

            this.jobUpdateSink.emitNext(dqoJobChange, Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration));
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
            DqoJobChange dqoJobChange = new DqoJobChange(DqoJobStatus.RUNNING, jobQueueEntry.getJobId());
            this.jobUpdateSink.emitNext(dqoJobChange, Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration));
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
            DqoJobChange dqoJobChange = new DqoJobChange(DqoJobStatus.WAITING, jobQueueEntry.getJobId());
            this.jobUpdateSink.emitNext(dqoJobChange, Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration));
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
            DqoJobChange dqoJobChange = new DqoJobChange(DqoJobStatus.SUCCEEDED, jobQueueEntry.getJobId());
            this.jobUpdateSink.emitNext(dqoJobChange, Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration));
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
            this.jobUpdateSink.emitNext(dqoJobChange, Sinks.EmitFailureHandler.busyLooping(this.publishBusyLoopingDuration));
        }
        catch (Exception ex) {
            log.error("publishJobFailedEvent failed for job:" + jobQueueEntry.getJobId() + ", error: " + ex.getMessage());
        }
    }

    /**
     * Creates an initial job list model that is retrieved by the UI when UI start up and the notification panel
     * must be filled with a list of jobs that are on the queue or jobs that have already finished.
     * @return Initial job queue snapshot.
     */
    @Override
    public DqoJobQueueSnapshotModel getInitialJobList() {
        long changeSequence;
        List<DqoJobHistoryEntryModel> jobs;

        synchronized (this.lock) {
            changeSequence = this.dqoJobIdGenerator.generateNextIncrementalId();
            jobs = this.allJobs.values()
                    .stream()
                    .collect(Collectors.toList());
        }

        return new DqoJobQueueSnapshotModel(jobs, changeSequence);
    }

    /**
     * Called for each new or updated job.
     * @param jobChange Job change notification.
     */
    public void onJobChange(DqoJobChange jobChange) {
        try {
            synchronized (this.lock) {
                long changeSequence = this.dqoJobIdGenerator.generateNextIncrementalId(); // serialized change number

                if (jobChange.getStatus() == DqoJobStatus.QUEUED) {
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
                        }
                        this.allJobs.put(jobChange.getJobId(), clonedJobEntryModel);
                    }

                    removeOldFinishedJobs();
                    removeOldJobChanges();
                }
            }
        }
        catch (Exception ex) {
            log.error("onJobChange failed for job:" + jobChange.getJobId() + ", error: " + ex.getMessage());
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
                .filter(e -> e.getValue().getStatus() == DqoJobStatus.SUCCEEDED || e.getValue().getStatus() == DqoJobStatus.FAILED)
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
