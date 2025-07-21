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

/**
 * Returns the limit of parallel jobs that is supported.
 */
public interface ParallelJobLimitProvider {
    /**
     * Finds the supported parallel job limits for the job queue and the job scheduler.
     *
     * @return Maximum number of parallel threads that are supported.
     */
    int getMaxDegreeOfParallelism();
}
