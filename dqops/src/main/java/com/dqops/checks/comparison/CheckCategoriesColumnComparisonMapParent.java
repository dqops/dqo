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

/**
 * Interface implemented by column level check categories containers (column profiling, column daily recurring, etc.)
 * that expose a map of comparison checks.
 */
public interface CheckCategoriesColumnComparisonMapParent {
    /**
     * Returns a map of comparison checks (column level comparisons), indexed by the table comparison name.
     * @return Map of comparisons.
     */
    AbstractColumnComparisonCheckCategorySpecMap<? extends AbstractColumnComparisonCheckCategorySpec> getComparisons();
}
