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
 * Profiler result status enumeration: success - when the statistics collector (a basic profiler) managed to obtain a requested metric, error - when an error was returned. The error is stored in another field.
 * The values are stored in the "status" column in the statistics results parquet file.
 */
public enum StatisticsCollectorResultStatus {
    @JsonProperty("success")
    success,

    @JsonProperty("error")
    error
}
