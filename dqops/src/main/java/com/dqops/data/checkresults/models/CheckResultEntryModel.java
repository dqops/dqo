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
package com.dqops.data.checkresults.models;

import com.dqops.checks.CheckType;
import com.dqops.connectors.ProviderType;
import com.dqops.metadata.search.StringPatternComparer;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.rules.RuleSeverityLevel;
import com.dqops.utils.docs.generators.SampleLongsRegistry;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
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
    @JsonPropertyDescription("Check result primary key")
    String id;

    @JsonPropertyDescription("Check hash, do not set a value when writing results to DQOps")
    private long checkHash;
    @JsonPropertyDescription("Check category name")
    private String checkCategory;
    @JsonPropertyDescription("Check name")
    private String checkName;
    @JsonPropertyDescription("Check display name")
    private String checkDisplayName;
    @JsonPropertyDescription("Check type")
    private CheckType checkType;

    @JsonPropertyDescription("Actual value")
    Double actualValue;
    @JsonPropertyDescription("Expected value")
    Double expectedValue;
    @JsonPropertyDescription("Warning lower bound")
    Double warningLowerBound;
    @JsonPropertyDescription("Warning upper bound")
    Double warningUpperBound;
    @JsonPropertyDescription("Error lower bound")
    Double errorLowerBound;
    @JsonPropertyDescription("Error upper bound")
    Double errorUpperBound;
    @JsonPropertyDescription("Fatal lower bound")
    Double fatalLowerBound;
    @JsonPropertyDescription("Fatal upper bound")
    Double fatalUpperBound;
    @JsonPropertyDescription("Issue severity, 0 - valid, 1 - warning, 2 - error, 3 - fatal")
    Integer severity;

    @JsonPropertyDescription("Column name")
    String columnName;
    @JsonPropertyDescription("Data group name")
    String dataGroup;

    @JsonPropertyDescription("Duration (ms)")
    Integer durationMs;
    @JsonPropertyDescription("Executed at timestamp")
    Instant executedAt;
    @JsonPropertyDescription("Time gradient")
    TimePeriodGradient timeGradient;
    @JsonPropertyDescription("Time period")
    LocalDateTime timePeriod;

    @JsonPropertyDescription("Include in KPI")
    Boolean includeInKpi;
    @JsonPropertyDescription("Include in SLA")
    Boolean includeInSla;

    @JsonPropertyDescription("Provider name")
    String provider;
    @JsonPropertyDescription("Data quality dimension")
    String qualityDimension;
    @JsonPropertyDescription("Sensor name")
    String sensorName;
    @JsonPropertyDescription("Table comparison name")
    String tableComparison;

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
                StringPatternComparer.matchSearchPattern(this.timeGradient != null ? this.timeGradient.toString() : null, filter) ||
                StringPatternComparer.matchSearchPattern(this.qualityDimension, filter) ||
                StringPatternComparer.matchSearchPattern(this.checkCategory, filter) ||
                StringPatternComparer.matchSearchPattern(this.checkType != null ? this.checkType.getDisplayName() : null, filter) ||
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
                return Comparator.comparing(o -> o.executedAt, Comparator.nullsFirst(Comparator.naturalOrder()));
            case checkHash:
                return Comparator.comparing(o -> o.checkHash, Comparator.nullsFirst(Comparator.naturalOrder()));
            case checkCategory:
                return Comparator.comparing(o -> o.checkCategory, Comparator.nullsFirst(Comparator.naturalOrder()));
            case checkName:
                return Comparator.comparing(o -> o.checkName, Comparator.nullsFirst(Comparator.naturalOrder()));
            case checkDisplayName:
                return Comparator.comparing(o -> o.checkDisplayName, Comparator.nullsFirst(Comparator.naturalOrder()));
            case checkType:
                return Comparator.comparing(o -> o.checkType, Comparator.nullsFirst(Comparator.naturalOrder()));
            case actualValue:
                return Comparator.comparing(o -> o.actualValue, Comparator.nullsFirst(Comparator.naturalOrder()));
            case expectedValue:
                return Comparator.comparing(o -> o.expectedValue, Comparator.nullsFirst(Comparator.naturalOrder()));
            case severity:
                return Comparator.comparing(o -> o.severity, Comparator.nullsFirst(Comparator.naturalOrder()));
            case columnName:
                return Comparator.comparing(o -> o.columnName, Comparator.nullsFirst(Comparator.naturalOrder()));
            case dataGroup:
                return Comparator.comparing(o -> o.dataGroup, Comparator.nullsFirst(Comparator.naturalOrder()));
            case timeGradient:
                return Comparator.comparing(o -> o.timeGradient, Comparator.nullsFirst(Comparator.naturalOrder()));
            case timePeriod:
                return Comparator.comparing(o -> o.timePeriod, Comparator.nullsFirst(Comparator.naturalOrder()));
            case qualityDimension:
                return Comparator.comparing(o -> o.qualityDimension, Comparator.nullsFirst(Comparator.naturalOrder()));
            case sensorName:
                return Comparator.comparing(o -> o.sensorName, Comparator.nullsFirst(Comparator.naturalOrder()));
            default:
                throw new NoSuchElementException("Unsupported sort order on: " + sortOrder);
        }
    }

    public static class CheckResultEntryModelSampleFactory implements SampleValueFactory<CheckResultEntryModel> {
        @Override
        public CheckResultEntryModel createSample() {
            return new CheckResultEntryModel() {{
                setId(Long.toString(SampleLongsRegistry.getSequenceNumber()));
                setCheckCategory(SampleStringsRegistry.getCategoryName());
                setCheckName(SampleStringsRegistry.getCheckName());
                setCheckDisplayName(SampleStringsRegistry.getFullCheckName());
                setCheckType(CheckType.profiling);

                setActualValue(100d);
                setExpectedValue(110d);
                setWarningLowerBound(105d);
                setWarningUpperBound(115d);
                setErrorLowerBound(95d);
                setErrorUpperBound(125d);
                setFatalLowerBound(85d);
                setFatalUpperBound(135d);
                setSeverity(RuleSeverityLevel.error.getSeverity());

                setColumnName(SampleStringsRegistry.getColumnName());
                setDataGroup(SampleStringsRegistry.getDataGrouping());

                setDurationMs(142);
                setExecutedAt(Instant.parse("2023-10-01T14:00:00.00Z"));
                setTimeGradient(TimePeriodGradient.hour);
                setTimePeriod(LocalDateTime.of(2023, 10, 1, 14, 0, 0));

                setIncludeInKpi(true);
                setIncludeInSla(true);

                setProvider(ProviderType.bigquery.getDisplayName());
                setQualityDimension(SampleStringsRegistry.getQualityDimension());
                setSensorName(SampleStringsRegistry.getFullSensorName());
            }};
        }
    }
}
