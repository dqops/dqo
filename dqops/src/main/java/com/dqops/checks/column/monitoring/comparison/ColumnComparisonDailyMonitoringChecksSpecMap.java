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

package com.dqops.checks.column.monitoring.comparison;

import com.dqops.checks.comparison.AbstractColumnComparisonCheckCategorySpecMap;

/**
 * Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 * must match a name of a table comparison that is defined on the parent table.
 * Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.
 */
public class ColumnComparisonDailyMonitoringChecksSpecMap extends AbstractColumnComparisonCheckCategorySpecMap<ColumnComparisonDailyMonitoringChecksSpec> {
    /**
     * Retrieves or creates, adds and returns a check container for a given comparison.
     *
     * @param comparisonName Table comparison name.
     * @return Check container for the given comparison. Never null.
     */
    @Override
    public ColumnComparisonDailyMonitoringChecksSpec getOrAdd(String comparisonName) {
        ColumnComparisonDailyMonitoringChecksSpec checksSpec = this.get(comparisonName);
        if (checksSpec == null) {
            checksSpec = new ColumnComparisonDailyMonitoringChecksSpec();
            this.put(comparisonName, checksSpec);
        }

        return checksSpec;
    }
}
