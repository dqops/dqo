/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
