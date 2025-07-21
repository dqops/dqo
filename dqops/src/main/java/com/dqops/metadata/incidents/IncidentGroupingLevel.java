/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.incidents;

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
