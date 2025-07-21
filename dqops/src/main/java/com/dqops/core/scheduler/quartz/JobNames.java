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

/**
 * Constants with the default Quartz job names.
 */
public final class JobNames {
    /**
     * Job name to synchronize the metadata.
     */
    public static final String SYNCHRONIZE_METADATA = "SYNCHRONIZE_METADATA";

    /**
     * Job name to periodically runs data quality checks.
     */
    public static final String RUN_CHECKS = "RUN_CHECKS";

    /**
     * Job name to periodically collect statistics.
     */
    public static final String COLLECT_STATISTICS = "COLLECT_STATISTICS";

    /**
     * Job name to periodically import tables automatically.
     */
    public static final String IMPORT_TABLES = "IMPORT_TABLES";

    /**
     * Job name that does nothing.
     */
    public static final String DUMMY = "DUMMY";
}
