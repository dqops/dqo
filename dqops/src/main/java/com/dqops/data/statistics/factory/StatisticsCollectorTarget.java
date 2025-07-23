/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.statistics.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Statistics collector types (target types) enumeration. The values are stored in the "collector_type" column in the statistics results parquet file.
 */
public enum StatisticsCollectorTarget {
    @JsonProperty("table")
    table,

    @JsonProperty("column")
    column
}
