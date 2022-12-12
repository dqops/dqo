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
 * Configuration of the concurrency limit (degree of parallelism) for each job, configured at a level that has a concurrency limit.
 */
public class JobConcurrencyConstraint {
    private final JobConcurrencyTarget concurrencyTarget;
    private Integer concurrentJobsLimit;

    /**
     * Creates a concurrency constraint.
     * @param concurrencyTarget Concurrency target that identifies the job type and the node on which the limit is appliede.
     * @param concurrentJobsLimit Number of concurrent jobs that could be executed on that node.
     */
    public JobConcurrencyConstraint(JobConcurrencyTarget concurrencyTarget, Integer concurrentJobsLimit) {
        this.concurrencyTarget = concurrencyTarget;
        this.concurrentJobsLimit = concurrentJobsLimit;
    }

    /**
     * Returns the concurrency target.
     * @return Concurrency target.
     */
    public JobConcurrencyTarget getConcurrencyTarget() {
        return concurrencyTarget;
    }

    /**
     * Returns the number of concurrent jobs that can execute on the node.
     * @return Concurrency limit.
     */
    public Integer getConcurrentJobsLimit() {
        return concurrentJobsLimit;
    }
}
