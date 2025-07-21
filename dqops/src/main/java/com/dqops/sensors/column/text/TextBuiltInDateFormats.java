/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.column.text;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Predefined date formats.
 */
public enum TextBuiltInDateFormats {
    @JsonProperty("YYYY-MM-DD")
    ISO8601,

    @JsonProperty("MM/DD/YYYY")
    MonthDayYear,

    @JsonProperty("DD/MM/YYYY")
    DayMonthYear,

    @JsonProperty("YYYY/MM/DD")
    YearMonthDay,

    @JsonProperty("Month D, YYYY")
    MonthNameDayYear
    // TODO add more formats
}
