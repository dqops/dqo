/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.monitoring;

import com.dqops.core.jobqueue.DqoJobQueueEntry;
import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.synchronization.status.CloudSynchronizationFoldersStatusModel;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * Job queue monitoring service. Tracks a queue of messages about the job queue that should be pushed back to the client.
 */
public interface DqoJobQueueMonitoringService {
    /**
     * Starts the queue monitor.
     */
    void start();

    /**
     * Stops the queue monitoring. All calls to get a list of jobs will fail after the queue monitor was stopped.
     */
    void stop();

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
     * Publishes a notification that a request to cancel a job was received.
     *
     * @param jobQueueEntry Job queue entry.
     */
    void publishJobCancellationRequestedEvent(DqoJobQueueEntry jobQueueEntry);

    /**
     * Publishes a notification that a job has been fully cancelled and removed from the queue.
     *
     * @param jobQueueEntry Job queue entry.
     */
    void publishJobFullyCancelledEvent(DqoJobQueueEntry jobQueueEntry);

    /**
     * Creates an initial job list model that is retrieved by the UI when UI start up and the notification panel
     * must be filled with a list of jobs that are on the queue or jobs that have already finished.
     * @param domainName Data domain name.
     *
     * @return Initial job queue snapshot.
     */
    Mono<DqoJobQueueInitialSnapshotModel> getInitialJobList(String domainName);

    /**
     * Waits for a next batch of changes after the <code>lastChangeId</code>. May return an empty list after the timeout.
     * @param lastChangeId Last change id to get changes after.
     * @param timeout Timeout to wait.
     * @param timeUnit Timeout unit.
     * @param domainName Data domain name.
     * @return Mono with a list of changes and the next sequence id.
     */
    Mono<DqoJobQueueIncrementalSnapshotModel> getIncrementalJobChanges(long lastChangeId, long timeout, TimeUnit timeUnit, String domainName);

    /**
     * Publishes the current folder synchronization status.
     * @param dataDomainName Data domain name.
     * @param synchronizationStatus Folder synchronization status.
     */
    void publishFolderSynchronizationStatus(String dataDomainName, CloudSynchronizationFoldersStatusModel synchronizationStatus);

    /**
     * Finds the job identified by a job id.
     * @param jobId Job id to find.
     * @param dataDomain Data domain.
     * @return Job history entry model (with the most recent job's status) or null, when the job is no longer tracked or is missing.
     */
    DqoJobHistoryEntryModel getJob(DqoQueueJobId jobId, String dataDomain);

    /**
     * Tries to find a job id of a job that was assigned also a business key (a user assigned job id).
     * If there is a known (tracked) job with that business key, this method will return the job id. Otherwise, when not found, returns null.
     * @param jobBusinessKey Job business key to look up.
     * @param dataDomain Data domain name.
     * @return Job id or null when not found.
     */
    DqoQueueJobId lookupJobIdByBusinessKey(String jobBusinessKey, String dataDomain);
}
