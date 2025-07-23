/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.scheduling;

import com.dqops.utils.docs.generators.SampleValueFactory;

/**
 * The run check scheduling group (profiling, daily checks, monthly checks, etc), which identifies the configuration of a schedule (cron expression) used schedule these checks on the job scheduler.
 */
public enum CheckRunScheduleGroup {
    /**
     * Schedule for profiling checks.
     */
    profiling,

    /**
     * Schedule for monitoring checks that should be executed daily because they capture daily snapshot of a data quality metrics.
     */
    monitoring_daily,

    /**
     * Schedule for monitoring checks that should be executed monthly because they capture monthly snapshot of a data quality metrics.
     */
    monitoring_monthly,

    /**
     * Schedule for partition checks for daily partitioned data.
     */
    partitioned_daily,

    /**
     * Schedule for partition checks for monthly partitioned data.
     */
    partitioned_monthly;

    public static class CheckRunScheduleGroupSampleFactory implements SampleValueFactory<CheckRunScheduleGroup> {
        @Override
        public CheckRunScheduleGroup createSample() {
            return partitioned_daily;
        }
    }
}
