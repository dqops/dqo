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

import ai.dqo.core.jobqueue.DqoQueueJobId;

/**
 * Notification message with an information about a new or updated dqo queue job that is received by the job monitoring service.
 */
public class DqoJobChange {
    private DqoJobStatus status;
    private DqoQueueJobId jobId;
    private String errorMessage;
    private DqoJobHistoryEntryModel updatedModel;

    /**
     * Creates a job change entry.
     * @param status Job status.
     * @param updatedModel New job's model or an updated model.
     */
    public DqoJobChange(DqoJobStatus status, DqoJobHistoryEntryModel updatedModel) {
        this.status = status;
        this.updatedModel = updatedModel;
        this.jobId = updatedModel.getJobId();
    }

    /**
     * Creates a job change model when just the status of a job has changed.
     * @param status New job status.
     * @param jobId Job id.
     */
    public DqoJobChange(DqoJobStatus status, DqoQueueJobId jobId) {
        this.status = status;
        this.jobId = jobId;
    }

    /**
     * Creates a job change model when the job has failed. The job status should be failed.
     * @param jobId Job id.
     * @param errorMessage Error message.
     */
    public DqoJobChange(DqoQueueJobId jobId, String errorMessage) {
        this.status = DqoJobStatus.failed;
        this.jobId = jobId;
        this.errorMessage = errorMessage;
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
     * Returns the error message when teh job has failed.
     * @return Error message for a failed job.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
