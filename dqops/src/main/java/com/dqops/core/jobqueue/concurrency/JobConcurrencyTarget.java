/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.concurrency;

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
