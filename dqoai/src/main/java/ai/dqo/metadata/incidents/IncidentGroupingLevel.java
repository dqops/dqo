/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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

package ai.dqo.metadata.incidents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Incident grouping levels for aggregating similar failed data quality checks into incidents.
 */
public enum IncidentGroupingLevel {
    /**
     * Failed checks are grouped into incidents on a table level.
     */
    @JsonProperty("table")
    table,

    /**
     * Failed checks are grouped into incidents on a table and a data quality dimension levels.
     */
    @JsonProperty("table_dimension")
    table_dimension,

    /**
     * Failed checks are grouped into incidents on a table, a data quality dimension and a data quality category levels.
     */
    @JsonProperty("table_dimension_category")
    table_dimension_category,

    /**
     * Failed checks are grouped into incidents on a table, a data quality dimension, a data quality category and a data quality check type levels.
     */
    @JsonProperty("table_dimension_category_type")
    table_dimension_category_check_type,

    /**
     * Failed checks are grouped into incidents on a table, a data quality dimension, a data quality category and a data quality check name levels.
     */
    @JsonProperty("table_dimension_category_name")
    table_dimension_category_check_name;

    /**
     * Groups incidents by dimension.
     * @return True when grouping by dimension.
     */
    public boolean groupByDimension() {
        return this != table;
    }

    /**
     * Group incidents also by a check category.
     * @return Group by a check category.
     */
    public boolean groupByCheckCategory() {
        return this == table_dimension_category ||
               this == table_dimension_category_check_type ||
               this == table_dimension_category_check_name;
    }

    /**
     * Group incidents also by a check type.
     * @return Group by a check type.
     */
    public boolean groupByCheckType() {
        return this == table_dimension_category_check_type ||
               this == table_dimension_category_check_name;
    }

    /**
     * Group incidents by the check name (the lowest check tree identifier).
     * @return Group by a check name.
     */
    public boolean groupByCheckName() {
        return this == table_dimension_category_check_name;
    }
}
