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

import com.dqops.core.jobqueue.DqoJobType;
import com.dqops.core.jobqueue.DqoQueueJobId;

/**
 * Notification message with an information about a new or updated dqo queue job that is received by the job monitoring service.
 */
public class DqoJobChange {
    private DqoJobStatus status;
    private DqoQueueJobId jobId;
    private DqoJobType jobType;
    private String errorMessage;
    private DqoJobHistoryEntryModel updatedModel;
    private String domainName;

    /**
     * Creates a job change entry.
     * @param status Job status.
     * @param updatedModel New job's model or an updated model.
     * @param domainName Data domain name.
     */
    public DqoJobChange(DqoJobStatus status, DqoJobHistoryEntryModel updatedModel, String domainName) {
        this.status = status;
        this.updatedModel = updatedModel;
        this.jobId = updatedModel.getJobId();
        this.jobType = updatedModel.getJobType();
        this.domainName = domainName;
    }

    /**
     * Creates a job change model when just the status of a job has changed.
     * @param status New job status.
     * @param jobId Job id.
     * @param jobType Job type.
     * @param domainName Data domain name.
     */
    public DqoJobChange(DqoJobStatus status, DqoQueueJobId jobId, DqoJobType jobType, String domainName) {
        this.status = status;
        this.jobId = jobId;
        this.jobType = jobType;
        this.domainName = domainName;
    }

    /**
     * Creates a job change model when the job has failed. The job status should be failed.
     * @param jobId Job id.
     * @param errorMessage Error message.
     * @param jobType Job type.
     * @param domainName Data domain name.
     */
    public DqoJobChange(DqoQueueJobId jobId, String errorMessage, DqoJobType jobType, String domainName) {
        this.status = DqoJobStatus.failed;
        this.jobId = jobId;
        this.errorMessage = errorMessage;
        this.jobType = jobType;
        this.domainName = domainName;
    }

    /**
     * Returns the new job status.
     * @return Job status.
     */
    public DqoJobStatus getStatus() {
        return status;
    }

    /**
     * Returns the optional updated job entry model. A job model is sent when a new job is started or it failed with an error, so the error message is present in an updated job model.
     * @return New or updated job model.
     */
    public DqoJobHistoryEntryModel getUpdatedModel() {
        return updatedModel;
    }

    /**
     * Returns the job id of the job that has been updated.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }

    /**
     * Returns the job type.
     * @return Job type.
     */
    public DqoJobType getJobType() {
        return jobType;
    }

    /**
     * Returns the error message when the job has failed.
     * @return Error message for a failed job.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Returns the data domain name.
     * @return Data domain name.
     */
    public String getDomainName() {
        return domainName;
    }
}
