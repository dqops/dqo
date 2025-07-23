/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.column.monitoring.comparison;

import com.dqops.checks.comparison.AbstractColumnComparisonCheckCategorySpecMap;

/**
 * Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 * must match a name of a table comparison that is defined on the parent table.
 * Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.
 */
public class ColumnComparisonMonthlyMonitoringChecksSpecMap extends AbstractColumnComparisonCheckCategorySpecMap<ColumnComparisonMonthlyMonitoringChecksSpec> {
    /**
     * Retrieves or creates, adds and returns a check container for a given comparison.
     *
     * @param comparisonName Table comparison name.
     * @return Check container for the given comparison. Never null.
     */
    @Override
    public ColumnComparisonMonthlyMonitoringChecksSpec getOrAdd(String comparisonName) {
        ColumnComparisonMonthlyMonitoringChecksSpec checksSpec = this.get(comparisonName);
        if (checksSpec == null) {
            checksSpec = new ColumnComparisonMonthlyMonitoringChecksSpec();
            this.put(comparisonName, checksSpec);
        }

        return checksSpec;
    }
}
