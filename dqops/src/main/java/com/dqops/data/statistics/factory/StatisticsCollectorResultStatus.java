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
