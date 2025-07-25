/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for DQOps. Properties are mapped to the "dqo.queue." prefix that are responsible for the configuration of the job queue.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.queue")
@EqualsAndHashCode(callSuper = false)
public class DqoQueueConfigurationProperties implements Cloneable {
    private Integer maxNonBlockingCapacity;  // the default is null, which is unlimited
    private int keepFinishedJobsHistorySeconds = 3600;
    private int keepJobsChangesHistorySeconds = 300;
    private int getJobChangesSinceWaitSeconds = 20;
    private int publishBusyLoopingDurationSeconds = 30;
    private Integer maxConcurrentJobs;

    /**
     * Maximum queue capacity before pushing new jobs will become a blocking operation.
     * @return Max non-blocking queue capacity.
     */
    public Integer getMaxNonBlockingCapacity() {
        return maxNonBlockingCapacity;
    }

    /**
     * Sets the maximum queue capacity before blocking on pushing jobs will begin.
     * @param maxNonBlockingCapacity New maximum non-blocking capacity or null when the queue capacity should be unbounded.
     */
    public void setMaxNonBlockingCapacity(Integer maxNonBlockingCapacity) {
        this.maxNonBlockingCapacity = maxNonBlockingCapacity;
    }

    /**
     * Number of seconds to preserve the history of finished jobs on the queue monitor.
     * When user opens UI, jobs finished but to the given number of seconds ago will be still shown.
     * @return Number of seconds to preserve the history of finished jobs.
     */
    public int getKeepFinishedJobsHistorySeconds() {
        return keepFinishedJobsHistorySeconds;
    }

    /**
     * Sets the number of seconds to keep the history of finished jobs.
     * @param keepFinishedJobsHistorySeconds Finished jobs time to keep on the history list.
     */
    public void setKeepFinishedJobsHistorySeconds(int keepFinishedJobsHistorySeconds) {
        this.keepFinishedJobsHistorySeconds = keepFinishedJobsHistorySeconds;
    }

    /**
     * The number of seconds to preserve the history of job changes.
     * @return Number of seconds to keep the history of job changes.
     */
    public int getKeepJobsChangesHistorySeconds() {
        return keepJobsChangesHistorySeconds;
    }

    /**
     * Sets the number of seconds to keep the history of job changes.
     * @param keepJobsChangesHistorySeconds Number of seconds.
     */
    public void setKeepJobsChangesHistorySeconds(int keepJobsChangesHistorySeconds) {
        this.keepJobsChangesHistorySeconds = keepJobsChangesHistorySeconds;
    }

    /**
     * Number of seconds that a parked rest api {@link com.dqops.rest.controllers.JobsController#getJobChangesSince(long)} is waiting for any change to the job queue.
     * The rest api will return an empty change list after that timeout.
     * @return Number of seconds to wait for any change on the queue before returning an empty list of changes.
     */
    public int getGetJobChangesSinceWaitSeconds() {
        return getJobChangesSinceWaitSeconds;
    }

    /**
     * Sets a timeout for "parking" a rest api that is reading an incremental list of job changes on the queue.
     * @param getJobChangesSinceWaitSeconds Wait time in seconds.
     */
    public void setGetJobChangesSinceWaitSeconds(int getJobChangesSinceWaitSeconds) {
        this.getJobChangesSinceWaitSeconds = getJobChangesSinceWaitSeconds;
    }

    /**
     * Returns the duration of busy looping to wait until the sink can accept a notification.
     * @return Return the duration of busy looping to wait until the sink can accept a notification.
     */
    public int getPublishBusyLoopingDurationSeconds() {
        return publishBusyLoopingDurationSeconds;
    }

    /**
     * Sets the duration of busy looping to wait until the sink can accept a notification.
     * @param publishBusyLoopingDurationSeconds The duration of busy looping to wait until the sink can accept a notification.
     */
    public void setPublishBusyLoopingDurationSeconds(int publishBusyLoopingDurationSeconds) {
        this.publishBusyLoopingDurationSeconds = publishBusyLoopingDurationSeconds;
    }

    /**
     * Returns an additional, user provided limit for the number of concurrent threads that are running checks.
     * @return The number of threads or null when the limit is controlled only by the DQOps Cloud license.
     */
    public Integer getMaxConcurrentJobs() {
        return maxConcurrentJobs;
    }

    /**
     * Sets a user provided limit for the number of concurrent threads (jobs) that are executed by DQOps.
     * @param maxConcurrentJobs New number of concurrent jobs.
     */
    public void setMaxConcurrentJobs(Integer maxConcurrentJobs) {
        this.maxConcurrentJobs = maxConcurrentJobs;
    }

    /**
     * Clones the current object.
     * @return
     */
    @Override
    public DqoQueueConfigurationProperties clone() {
        try {
            return (DqoQueueConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
