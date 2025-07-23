/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.table.profiling;

import com.dqops.checks.column.profiling.ColumnComparisonProfilingChecksSpec;
import com.dqops.checks.comparison.AbstractTableComparisonCheckCategorySpecMap;

/**
 * Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 * must match a name of a table comparison that is defined on the parent table.
 */
public class TableComparisonProfilingChecksSpecMap extends AbstractTableComparisonCheckCategorySpecMap<TableComparisonProfilingChecksSpec> {
    /**
     * Retrieves or creates, adds and returns a check container for a given comparison.
     *
     * @param comparisonName Table comparison name.
     * @return Check container for the given comparison. Never null.
     */
    @Override
    public TableComparisonProfilingChecksSpec getOrAdd(String comparisonName) {
        TableComparisonProfilingChecksSpec checksSpec = this.get(comparisonName);
        if (checksSpec == null) {
            checksSpec = new TableComparisonProfilingChecksSpec();
            this.put(comparisonName, checksSpec);
        }

        return checksSpec;
    }
}
