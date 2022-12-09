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
package ai.dqo.core.jobqueue;

/**
 * Context object passed to a job when it is executed. Provides access to the job id.
 */
public class DqoJobExecutionContext {
    private DqoQueueJobId jobId;

    /**
     * Creates a job execution context.
     * @param jobId Job id.
     */
    public DqoJobExecutionContext(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }

    /**
     * Returns the job id. The job id also contains job ids of its parent jobs.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }
}
