/*
 * Copyright © 2022 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.core.jobqueue.monitoring;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Returns the current snapshot of running jobs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoJobQueueInitialSnapshotModel {
    private List<DqoJobHistoryEntryModel> jobs;
    private long lastSequenceNumber;

    /**
     * Creates a new job snapshot.
     * @param jobs List of jobs.
     * @param lastSequenceNumber Last change id (sequence number).
     */
    public DqoJobQueueInitialSnapshotModel(List<DqoJobHistoryEntryModel> jobs, long lastSequenceNumber) {
        this.jobs = jobs;
        this.lastSequenceNumber = lastSequenceNumber;
    }

    /**
     * Returns a list of jobs on the queue. Finished jobs are also returned.
     * @return List of jobs.
     */
    public List<DqoJobHistoryEntryModel> getJobs() {
        return jobs;
    }

    /**
     * Returns the last change id. This value must be used in a follow-up parked rest api call to get changes after this change id.
     * @return Last change id (sequence number).
     */
    public long getLastSequenceNumber() {
        return lastSequenceNumber;
    }
}
