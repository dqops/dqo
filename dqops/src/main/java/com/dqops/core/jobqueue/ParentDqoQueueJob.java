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
 * Base class for DQOps queue jobs that are parent jobs and will be executed on the {@link ParentDqoJobQueue} job queue for parent jobs.
 * @param <T> Job result type.
 */
public abstract class ParentDqoQueueJob<T> extends DqoQueueJob<T> {
}
