/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.groupings;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data grouping dimension value source. Specifies how a single level of a data grouping dimension's value is populated.
 */
public enum DataGroupingDimensionSource {
    /**
     * The data grouping dimension value is hardcoded as a static value (tag). All data quality results are assigned the same, hardcoded data grouping level value.
     */
    @JsonProperty("tag")
    tag,

    /**
     * The data grouping dimensionvalue is obtained from the data. An extra GROUP BY [column name] is added to the sensor query.
     */
    @JsonProperty("column_value")
    column_value
}
