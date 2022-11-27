package ai.dqo.core.jobqueue.monitoring;

import ai.dqo.core.jobqueue.DqoJobQueueEntry;

/**
 * Job queue monitoring service. Tracks a queue of messages about the job queue that should be pushed back to the client.
 */
public interface DqoJobQueueMonitoringService {
    /**
     * Starts the queue monitor.
     */
    void start();

    /**
     * Publishes a new job on the history.
     *
     * @param jobQueueEntry New job entry.
     */
    void publishJobAddedEvent(DqoJobQueueEntry jobQueueEntry);

    /**
     * Publishes a notification that a job was started.
     *
     * @param jobQueueEntry Job queue entry.
     */
    void publishJobRunningEvent(DqoJobQueueEntry jobQueueEntry);

    /**
     * Publishes a notification that a job was parked because of the concurrency limit.
     *
     * @param jobQueueEntry Job queue entry.
     */
    void publishJobParkedEvent(DqoJobQueueEntry jobQueueEntry);

    /**
     * Publishes a notification that a job has successfully finished.
     *
     * @param jobQueueEntry Job queue entry.
     */
    void publishJobSucceededEvent(DqoJobQueueEntry jobQueueEntry);

    /**
     * Publishes a notification that a job has failed with an error.
     *
     * @param jobQueueEntry Job queue entry.
     * @param errorMessage  Error message.
     */
    void publishJobFailedEvent(DqoJobQueueEntry jobQueueEntry, String errorMessage);

    /**
     * Creates an initial job list model that is retrieved by the UI when UI start up and the notification panel
     * must be filled with a list of jobs that are on the queue or jobs that have already finished.
     *
     * @return Initial job queue snapshot.
     */
    DqoJobQueueSnapshotModel getInitialJobList();
}
