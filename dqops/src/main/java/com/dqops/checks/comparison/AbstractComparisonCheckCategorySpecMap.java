/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.comparison;

import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

/**
 * Base class for comparison check container maps that contain comparison checks for each defined data comparison to a reference table.
 */
public abstract class AbstractComparisonCheckCategorySpecMap<V extends AbstractComparisonCheckCategorySpec> extends AbstractDirtyTrackingSpecMap<V> {
    /**
     * The name of the category for comparisons.
     */
    public static final String COMPARISONS_CATEGORY_NAME = "comparisons";

    /**
     * The name of the "timeliness" check category.
     */
    public static final String TIMELINESS_CATEGORY_NAME = "timeliness";


    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Retrieves or creates, adds and returns a check container for a given comparison.
     * @param comparisonName Table comparison name.
     * @return Check container for the given comparison. Never null.
     */
    public abstract V getOrAdd(String comparisonName);
}
