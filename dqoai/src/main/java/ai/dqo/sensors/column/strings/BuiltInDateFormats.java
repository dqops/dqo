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
package ai.dqo.sensors.column.strings;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Predefined date formats.
 */
public enum BuiltInDateFormats {
    @JsonProperty("YYYY-MM-DD")
    ISO8601,

    @JsonProperty("MM/DD/YYYY")
    MonthSlashDaySlashYear,

    @JsonProperty("MM.DD.YYYY")
    MonthDotDayDotYear,

    @JsonProperty("MM-DD-YYYY")
    MonthDashDayDashYear,

    @JsonProperty("DD/MM/YYYY")
    DaySlashMonthSlashYear,

    @JsonProperty("DD.MM.YYYY")
    DayDotMonthDotYear,

    @JsonProperty("DD-MM-YYYY")
    DayDashMonthDashYear,

    @JsonProperty("YYYY/MM/DD")
    YearSlashMonthSlashDay,

    @JsonProperty("YYYY.MM.DD")
    YearDotMonthDotDay,

    @JsonProperty("YYYY-MM-DD")
    YearDashMonthDashDay,

    @JsonProperty("MonthName DD YYYY")
    MonthNameSpaceDaySpaceYear,

    @JsonProperty("DD MonthName/YYYY")
    DaySpaceMonthNameSlashYear,

    @JsonProperty("DD MonthName.YYYY")
    DaySpaceMonthNameDotYear,

    @JsonProperty("DD MonthName-YYYY")
    DaySpaceMonthNameDashYear,

    @JsonProperty("DD MonthName YYYY")
    DaySpaceMonthNameSpaceYear,

    @JsonProperty("YYYY MonthName/DD")
    YearSpaceMonthNameSlashDay,

    @JsonProperty("YYYY MonthName.DD")
    YearSpaceMonthNameDotDay,

    @JsonProperty("YYYY MonthName-DD")
    YearSpaceMonthNameDashDay,

    @JsonProperty("YYYY MonthName DD")
    YearSpaceMonthNameSpaceDay,

    // TODO add more formats
}
