/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler.quartz;

import org.quartz.JobKey;

/**
 * Predefined Quartz job keys for built-in jobs (synchronization, running checks).
 */
public final class JobKeys {
    /**
     * Predefined job that synchronizes the metadata.
     */
    public static final JobKey SYNCHRONIZE_METADATA = new JobKey(JobNames.SYNCHRONIZE_METADATA, null);

    /**
     * Predefined job that runs the data quality checks.
     */
    public static final JobKey RUN_CHECKS = new JobKey(JobNames.RUN_CHECKS, null);

    /**
     * Predefined job that runs basic profiling and collecting statistics.
     */
    public static final JobKey COLLECT_STATISTICS = new JobKey(JobNames.COLLECT_STATISTICS, null);

    /**
     * Predefined job that automatically imports tables.
     */
    public static final JobKey IMPORT_TABLES = new JobKey(JobNames.IMPORT_TABLES, null);

    /**
     * Predefined job that does nothing.
     */
    public static final JobKey DUMMY = new JobKey(JobNames.DUMMY, null);
}
