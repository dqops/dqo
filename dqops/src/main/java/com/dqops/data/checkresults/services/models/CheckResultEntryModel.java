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
package com.dqops.data.checkresults.services.models;

import com.dqops.metadata.search.StringPatternComparer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.parquet.Strings;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Detailed results for a single check. Represent one row in the check results table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class CheckResultEntryModel {
    @JsonPropertyDescription("Check result ID.")
    String id;

    @JsonPropertyDescription("Check hash.")
    private long checkHash;
    @JsonPropertyDescription("Check category name.")
    private String checkCategory;
    @JsonPropertyDescription("Check name.")
    private String checkName;
    @JsonPropertyDescription("Check display name.")
    private String checkDisplayName;
    @JsonPropertyDescription("Check type.")
    private String checkType;

    @JsonPropertyDescription("Actual value.")
    Double actualValue;
    @JsonPropertyDescription("Expected value.")
    Double expectedValue;
    @JsonPropertyDescription("Warning lower bound.")
    Double warningLowerBound;
    @JsonPropertyDescription("Warning upper bound.")
    Double warningUpperBound;
    @JsonPropertyDescription("Error lower bound.")
    Double errorLowerBound;
    @JsonPropertyDescription("Error upper bound.")
    Double errorUpperBound;
    @JsonPropertyDescription("Fatal lower bound.")
    Double fatalLowerBound;
    @JsonPropertyDescription("Fatal upper bound.")
    Double fatalUpperBound;
    @JsonPropertyDescription("Severity.")
    Integer severity;

    @JsonPropertyDescription("Column name.")
    String columnName;
    @JsonPropertyDescription("Data group.")
    String dataGroup;

    @JsonPropertyDescription("Duration (ms).")
    Integer durationMs;
    @JsonPropertyDescription("Executed at.")
    Instant executedAt;
    @JsonPropertyDescription("Time gradient.")
    String timeGradient;
    @JsonPropertyDescription("Time period.")
    LocalDateTime timePeriod;

    @JsonPropertyDescription("Include in KPI.")
    Boolean includeInKpi;
    @JsonPropertyDescription("Include in SLA.")
    Boolean includeInSla;
    @JsonPropertyDescription("Provider.")
    String provider;
    @JsonPropertyDescription("Quality dimension.")
    String qualityDimension;
    @JsonPropertyDescription("Sensor name.")
    String sensorName;

    /**
     * Checks if any filtered field name matches a pattern.
     * @param filter Filter pattern.
     * @return True when the check result matches a pattern.
     */
    public boolean matchesFilter(String filter) {
        if (filter.indexOf(' ') >= 0) {
            String[] strings = StringUtils.split(filter, ' ');
            for (int i = 0; i < strings.length; i++) {
                String filterElement = strings[i];
                if (Strings.isNullOrEmpty(filterElement)) {
                    continue;
                }

                if (!matchesFilter(filterElement)) {
                    return false;
                }
            }

            return true;
        }

        return StringPatternComparer.matchSearchPattern(this.checkDisplayName, filter) ||
                StringPatternComparer.matchSearchPattern(this.columnName, filter) ||
                StringPatternComparer.matchSearchPattern(this.dataGroup, filter) ||
                StringPatternComparer.matchSearchPattern(this.timeGradient, filter) ||
                StringPatternComparer.matchSearchPattern(this.qualityDimension, filter) ||
                StringPatternComparer.matchSearchPattern(this.checkCategory, filter) ||
                StringPatternComparer.matchSearchPattern(this.checkType, filter) ||
                StringPatternComparer.matchSearchPattern(this.checkName, filter) ||
                StringPatternComparer.matchSearchPattern(this.sensorName, filter);
    }

    /**
     * Creates a comparator for a chosen field.
     * @param sortOrder Sort order.
     * @return Comparator instance.
     */
    public static Comparator<CheckResultEntryModel> makeSortComparator(CheckResultSortOrder sortOrder) {
        switch (sortOrder) {
            case executedAt:
                return Comparator.comparing(o -> o.executedAt);
            case checkHash:
                return Comparator.comparing(o -> o.checkHash);
            case checkCategory:
                return Comparator.comparing(o -> o.checkCategory);
            case checkName:
                return Comparator.comparing(o -> o.checkName);
            case checkDisplayName:
                return Comparator.comparing(o -> o.checkDisplayName);
            case checkType:
                return Comparator.comparing(o -> o.checkType);
            case actualValue:
                return Comparator.comparing(o -> o.actualValue);
            case expectedValue:
                return Comparator.comparing(o -> o.expectedValue);
            case severity:
                return Comparator.comparing(o -> o.severity);
            case columnName:
                return Comparator.comparing(o -> o.columnName);
            case dataGroup:
                return Comparator.comparing(o -> o.dataGroup);
            case timeGradient:
                return Comparator.comparing(o -> o.timeGradient);
            case timePeriod:
                return Comparator.comparing(o -> o.timePeriod);
            case qualityDimension:
                return Comparator.comparing(o -> o.qualityDimension);
            case sensorName:
                return Comparator.comparing(o -> o.sensorName);
            default:
                throw new NoSuchElementException("Unsupported sort order on: " + sortOrder);
        }
    }
}
