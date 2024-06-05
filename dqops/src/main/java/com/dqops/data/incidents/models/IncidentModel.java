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

package com.dqops.data.incidents.models;

import com.dqops.checks.CheckType;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.StringPatternComparer;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import tech.tablesaw.api.Row;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Data quality incident model shown on an incident details screen.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentModel {
    /**
     * Incident id (primary key).
     */
    @JsonPropertyDescription("Incident ID - the primary key that identifies each data quality incident.")
    private String incidentId;

    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name affected by a data quality incident.")
    private String connection;

    /**
     * The year when the incident was first seen. This value is required to load an incident's monthly partition.
     */
    @JsonPropertyDescription("The year when the incident was first seen. This value is required to load an incident's monthly partition.")
    private int year;

    /**
     * The month when the incident was first seen. This value is required to load an incident's monthly partition.
     */
    @JsonPropertyDescription("The month when the incident was first seen. This value is required to load an incident's monthly partition.")
    private int month;

    /**
     * Schema name affected by a data quality incident.
     */
    @JsonPropertyDescription("Schema name affected by a data quality incident.")
    private String schema;

    /**
     * Table name affected by a data quality incident.
     */
    @JsonPropertyDescription("Table name affected by a data quality incident.")
    private String table;

    /**
     * Table priority of the table that was affected by a data quality incident.
     */
    @JsonPropertyDescription("Table priority of the table that was affected by a data quality incident.")
    private Integer tablePriority;

    /**
     * Data quality incident hash that identifies similar incidents on the same incident grouping level.
     */
    @JsonPropertyDescription("Data quality incident hash that identifies similar incidents on the same incident grouping level.")
    private Long incidentHash;

    /**
     * The UTC timestamp when the data quality incident was first seen.
     */
    @JsonPropertyDescription("The UTC timestamp when the data quality incident was first seen.")
    private Instant firstSeen;

    /**
     * The UTC timestamp when the data quality incident was last seen.
     */
    @JsonPropertyDescription("The UTC timestamp when the data quality incident was last seen.")
    private Instant lastSeen;

    /**
     * The UTC timestamp when the data quality incident is valid until. All new failed data quality check results until that date will be included in this incident, unless the incident status is changed to resolved, so a new incident must be created.
     */
    @JsonPropertyDescription("The UTC timestamp when the data quality incident is valid until. All new failed data quality check results until that date will be included in this incident, unless the incident status is changed to resolved, so a new incident must be created.")
    private Instant incidentUntil;

    /**
     * The data stream name that was affected by a data quality incident.
     */
    @JsonPropertyDescription("The data group that was affected by a data quality incident.")
    private String dataGroup;

    /**
     * The data quality dimension that was affected by a data quality incident.
     */
    @JsonPropertyDescription("The data quality dimension that was affected by a data quality incident.")
    private String qualityDimension;

    /**
     * The data quality check category that was affected by a data quality incident.
     */
    @JsonPropertyDescription("The data quality check category that was affected by a data quality incident.")
    private String checkCategory;

    /**
     * The data quality check type that was affected by a data quality incident.
     */
    @JsonPropertyDescription("The data quality check type that was affected by a data quality incident.")
    private String checkType;

    /**
     * The data quality check name that was affected by a data quality incident.
     */
    @JsonPropertyDescription("The data quality check name that was affected by a data quality incident.")
    private String checkName;

    /**
     * The highest failed check severity that was detected as part of this data quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.
     */
    @JsonPropertyDescription("The highest failed check severity that was detected as part of this data quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.")
    private int highestSeverity;

    /**
     * The minimum severity of the data quality incident, copied from the incident configuration at a connection or table at the time when the incident was first seen. Possible values are: 1 - warning, 2 - error, 3 - fatal.
     */
    @JsonPropertyDescription("The minimum severity of the data quality incident, copied from the incident configuration at a connection or table at the time when the incident was first seen. Possible values are: 1 - warning, 2 - error, 3 - fatal.")
    private int minimumSeverity;

    /**
     * The total number of failed data quality checks that were seen when the incident was raised for the first time.
     */
    @JsonPropertyDescription("The total number of failed data quality checks that were seen when the incident was raised for the first time.")
    private int failedChecksCount;

    /**
     * The link (url) to a ticket in an external system that is tracking this incident.
     */
    @JsonPropertyDescription("The link (url) to a ticket in an external system that is tracking this incident.")
    public String issueUrl;

    /**
     * Incident status.
     */
    @JsonPropertyDescription("Incident status.")
    private IncidentStatus status;

    /**
     * Creates a new incident notification message from a single row of the "incidents" table. The column names are defined in {@link IncidentsColumnNames} class.
     * @param incidentRow Incident row - a row from the Incident's Tablesaw row.
     * @param connectionName Connection name.
     * @return Incident list model instance.
     */
    public static IncidentModel fromIncidentRow(Row incidentRow, String connectionName) {
        IncidentModel model = new IncidentModel();
        model.setIncidentId(incidentRow.getString(IncidentsColumnNames.ID_COLUMN_NAME));
        model.setConnection(connectionName);
        model.setSchema(incidentRow.getString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME));
        model.setTable(incidentRow.getString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME));
        if (!incidentRow.isMissing(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME)) {
            model.setTablePriority(incidentRow.getInt(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME));
        }
        model.setIncidentHash(incidentRow.getLong(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME));
        Instant firstSeen = incidentRow.getInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME);
        model.setFirstSeen(firstSeen);
        ZonedDateTime zonedFirstSeen = firstSeen.atZone(ZoneOffset.UTC);
        model.setYear(zonedFirstSeen.get(ChronoField.YEAR));
        model.setMonth(zonedFirstSeen.get(ChronoField.MONTH_OF_YEAR));
        model.setLastSeen(incidentRow.getInstant(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME));
        model.setIncidentUntil(incidentRow.getInstant(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME));
        if (!incidentRow.isMissing(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME)) {
            model.setDataGroup(incidentRow.getString(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME)) {
            model.setQualityDimension(incidentRow.getString(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME)) {
            model.setCheckCategory(incidentRow.getString(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME)) {
            model.setCheckType(incidentRow.getString(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME)) {
            model.setCheckName(incidentRow.getString(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME)) {
            model.setIssueUrl(incidentRow.getString(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME));
        }
        model.setHighestSeverity(incidentRow.getInt(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME));
        if (!incidentRow.isMissing(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME)) {
            model.setMinimumSeverity(incidentRow.getInt(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME));
        }
        model.setFailedChecksCount(incidentRow.getInt(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME));
        model.setStatus(IncidentStatus.valueOf(incidentRow.getString(IncidentsColumnNames.STATUS_COLUMN_NAME)));

        return model;
    }

    /**
     * Checks if any filtered field name matches a pattern.
     * @param filter Filter pattern.
     * @return True when the incident matches a pattern.
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

        return StringPatternComparer.matchSearchPattern(this.incidentId, filter) ||
                StringPatternComparer.matchSearchPattern(this.schema, filter) ||
                StringPatternComparer.matchSearchPattern(this.table, filter) ||
                StringPatternComparer.matchSearchPattern(this.dataGroup, filter) ||
                StringPatternComparer.matchSearchPattern(this.qualityDimension, filter) ||
                StringPatternComparer.matchSearchPattern(this.checkCategory, filter) ||
                StringPatternComparer.matchSearchPattern(this.checkType, filter) ||
                StringPatternComparer.matchSearchPattern(this.checkName, filter) ||
                StringPatternComparer.matchSearchPattern(this.issueUrl, filter);
    }

    /**
     * Creates a comparator for a chosen field.
     * @param sortOrder Sort order.
     * @return Comparator instance.
     */
    public static Comparator<IncidentModel> makeSortComparator(IncidentSortOrder sortOrder) {
        switch (sortOrder) {
            case table:
                return Comparator.comparing(o -> o.table);
            case tablePriority:
                return Comparator.comparing(o -> o.tablePriority);
            case firstSeen:
                return Comparator.comparing(o -> o.firstSeen);
            case lastSeen:
                return Comparator.comparing(o -> o.lastSeen);
            case dataGroup:
                return Comparator.comparing(o -> o.dataGroup);
            case qualityDimension:
                return Comparator.comparing(o -> o.qualityDimension);
            case checkName:
                return Comparator.comparing(o -> o.checkName);
            case highestSeverity:
                return Comparator.comparing(o -> o.highestSeverity);
            case failedChecksCount:
                return Comparator.comparing(o -> o.failedChecksCount);
            default:
                throw new NoSuchElementException("Unsupported sort order on: " + sortOrder);
        }
    }

    /**
     * Creates a check search filter that will find all data quality checks that are covered by this incident.
     * @return Check search filter that matches all checks that are related to this incident.
     */
    public CheckSearchFilters toCheckSearchFilter() {
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        checkSearchFilters.setConnection(this.getConnection());
        checkSearchFilters.setPhysicalTableName(new PhysicalTableName(this.schema, this.table));
        checkSearchFilters.setQualityDimension(this.getQualityDimension());
        checkSearchFilters.setCheckCategory(this.getCheckCategory());
        checkSearchFilters.setCheckName(this.getCheckName());

        if (!Strings.isNullOrEmpty(this.checkType)) {
            checkSearchFilters.setCheckType(CheckType.valueOf(this.checkType));
        }

        return checkSearchFilters;
    }

    /**
     * Sample factory for an incident model.
     */
    public static class IncidentModelSampleFactory implements SampleValueFactory<IncidentModel> {
        @Override
        public IncidentModel createSample() {
            return new IncidentModel() {{
               setConnection("datalake");
               setYear(2024);
               setMonth(06);
               setStatus(IncidentStatus.open);
               setIncidentId("c05e6544-46e5-47ed-b8c2-b72927199976");
               setQualityDimension("Completeness");
               setHighestSeverity(2);
               setFailedChecksCount(5);
               setFirstSeen(LocalDateTime.of(2024, 06, 01, 11, 45, 22, 0).toInstant(ZoneOffset.UTC));
               setSchema("public");
               setTable("fact_sales");
            }};
        }
    }
}
