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
