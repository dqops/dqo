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

import java.util.Objects;

/**
 * Identifies a target object on which a limit of concurrent jobs is configured. This object is used as a key in a dictionary
 * that maintains the limits.
 */
public class JobConcurrencyTarget {
    private final ConcurrentJobType jobType;
    private final Object target;

    /**
     * Creates a job concurrency target object.
     * @param jobType Job type.
     * @param target Hierarchy id that identifies the object that has a concurrency limit.
     */
    public JobConcurrencyTarget(ConcurrentJobType jobType, Object target) {
        this.jobType = jobType;
        this.target = target;
    }

    /**
     * Returns the job type on which a concurrency limit is imposed.
     * @return Job type with a concurrency limit.
     */
    public ConcurrentJobType getJobType() {
        return jobType;
    }

    /**
     * Returns the hierarchy node ID of the node that has a concurrency limit.
     * @return Concurrency target hierarchy id.
     */
    public Object getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobConcurrencyTarget that = (JobConcurrencyTarget) o;

        if (jobType != that.jobType) return false;
        return Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        int result = jobType != null ? jobType.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }
}
