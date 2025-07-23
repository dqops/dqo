/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue;

/**
 * Job ID generation service, creates increasing job ids using the current time.
 */
public interface DqoJobIdGenerator {
    /**
     * Creates a new job id, assigning a new incremental id.
     * @return New job id.
     */
    DqoQueueJobId createNewJobId();

    /**
     * Creates a next incremental id.
     * @return Next incremental id.
     */
    long generateNextIncrementalId();
}
