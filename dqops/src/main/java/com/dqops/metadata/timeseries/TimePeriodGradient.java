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
package com.dqops.metadata.timeseries;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Time series gradient type (daily, monthly, quarterly, monthly, weekly, hourly).
 */
public enum TimePeriodGradient {
    @JsonProperty("year")
    year,

    @JsonProperty("quarter")
    quarter,

    @JsonProperty("month")
    month,

    @JsonProperty("week")
    week,

    @JsonProperty("day")
    day,

    @JsonProperty("hour")
    hour,

    @JsonProperty("millisecond")
    millisecond
}
