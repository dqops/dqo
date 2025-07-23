/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.sources;

/**
 * Object mother for {@link PartitionIncrementalTimeWindowSpec}
 */
public class PartitionIncrementalTimeWindowSpecObjectMother {
    /**
     * Creates a long term (20 years) time window for incremental checks.
     * @return Long term time window.
     */
    public static PartitionIncrementalTimeWindowSpec createLongTermTimeWindow() {
        PartitionIncrementalTimeWindowSpec partitionIncrementalTimeWindowSpec = new PartitionIncrementalTimeWindowSpec();
        partitionIncrementalTimeWindowSpec.setDailyPartitioningRecentDays(365 * 20);
        partitionIncrementalTimeWindowSpec.setMonthlyPartitioningRecentMonths(12 * 20);
        return partitionIncrementalTimeWindowSpec;
    }

    /**
     * Creates a not configured time window for incremental checks.
     * @return Not configured time window with nulls for time ranges.
     */
    public static PartitionIncrementalTimeWindowSpec createNonConfiguredTimeWindow() {
        PartitionIncrementalTimeWindowSpec partitionIncrementalTimeWindowSpec = new PartitionIncrementalTimeWindowSpec();
        partitionIncrementalTimeWindowSpec.setDailyPartitioningRecentDays(null);
        partitionIncrementalTimeWindowSpec.setMonthlyPartitioningRecentMonths(null);
        return partitionIncrementalTimeWindowSpec;
    }
}
