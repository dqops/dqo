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

package com.dqops.data.incidents.normalization;

import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.utils.tables.TableColumnUtility;
import tech.tablesaw.api.*;

/**
 * A wrapper over a tablesaw table with incidents. Creates missing columns in the table and provides typed access to the columns.
 */
public class IncidentsNormalizedResults {
    private final Table table;
    private final StringColumn idColumn;
    private final LongColumn incidentHashColumn;
    private final StringColumn schemaNameColumn;
    private final StringColumn tableNameColumn;
    private final IntColumn tablePriorityColumn;
    private final StringColumn dataStreamNameColumn;
    private final StringColumn qualityDimensionColumn;
    private final StringColumn checkCategoryColumn;
    private final StringColumn checkTypeColumn;
    private final StringColumn checkNameColumn;
    private final IntColumn highestSeverityColumn;
    private final IntColumn minimumSeverityColumn;
    private final InstantColumn firstSeenColumn;
    private final InstantColumn lastSeenColumn;
    private final InstantColumn incidentUntilColumn;
    private final IntColumn failedChecksCountColumn;
    private final StringColumn issueUrlColumn;
    private final StringColumn resolvedByColumn;
    private final StringColumn statusColumn;
    private final InstantColumn createdAtColumn;
    private final InstantColumn updatedAtColumn;
    private final StringColumn createdByColumn;
    private final StringColumn updatedByColumn;

    /**
     * Creates a normalized results wrapper over a table. As a side result, creates also missing columns.
     * @param table Tablesaw table with incidents.
     */
    public IncidentsNormalizedResults(Table table) {
        this(table, true);
    }

    /**
     * Creates a normalized results wrapper over a table. As a side result, creates also missing columns.
     * @param table Tablesaw table with incidents.
     * @param addColumWhenMissing Adds columns if they are missing.
     */
    public IncidentsNormalizedResults(Table table, boolean addColumWhenMissing) {
        this.table = table;
        this.idColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.ID_COLUMN_NAME, addColumWhenMissing);
        this.incidentHashColumn = TableColumnUtility.getOrAddLongColumn(table, IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME, addColumWhenMissing);
        this.schemaNameColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME, addColumWhenMissing);
        this.tableNameColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.TABLE_NAME_COLUMN_NAME, addColumWhenMissing);
        this.tablePriorityColumn = TableColumnUtility.getOrAddIntColumn(table, IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME, addColumWhenMissing);
        this.dataStreamNameColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME, addColumWhenMissing);
        this.qualityDimensionColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, addColumWhenMissing);
        this.checkCategoryColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME, addColumWhenMissing);
        this.checkTypeColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME, addColumWhenMissing);
        this.checkNameColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.CHECK_NAME_COLUMN_NAME, addColumWhenMissing);
        this.highestSeverityColumn = TableColumnUtility.getOrAddIntColumn(table, IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME, addColumWhenMissing);
        this.minimumSeverityColumn = TableColumnUtility.getOrAddIntColumn(table, IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME, addColumWhenMissing);
        this.firstSeenColumn = TableColumnUtility.getOrAddInstantColumn(table, IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME, addColumWhenMissing);
        this.lastSeenColumn = TableColumnUtility.getOrAddInstantColumn(table, IncidentsColumnNames.LAST_SEEN_COLUMN_NAME, addColumWhenMissing);
        this.incidentUntilColumn = TableColumnUtility.getOrAddInstantColumn(table, IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME, addColumWhenMissing);
        this.failedChecksCountColumn = TableColumnUtility.getOrAddIntColumn(table, IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME, addColumWhenMissing);
        this.issueUrlColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.ISSUE_URL_COLUMN_NAME, addColumWhenMissing);
        this.resolvedByColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.RESOLVED_BY_COLUMN_NAME, addColumWhenMissing);
        this.statusColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.STATUS_COLUMN_NAME, addColumWhenMissing);
        this.createdAtColumn = TableColumnUtility.getOrAddInstantColumn(table, IncidentsColumnNames.CREATED_AT_COLUMN_NAME, addColumWhenMissing);
        this.updatedAtColumn = TableColumnUtility.getOrAddInstantColumn(table, IncidentsColumnNames.UPDATED_AT_COLUMN_NAME, addColumWhenMissing);
        this.createdByColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.CREATED_BY_COLUMN_NAME, addColumWhenMissing);
        this.updatedByColumn = TableColumnUtility.getOrAddStringColumn(table, IncidentsColumnNames.UPDATED_BY_COLUMN_NAME, addColumWhenMissing);
    }

    /**
     * Returns the "Incidents" table, with all columns present.
     * @return Tablesaw table with incidents.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Returns the ID column.
     * @return ID column.
     */
    public StringColumn getIdColumn() {
        return idColumn;
    }

    /**
     * Returns the incident hash column.
     * @return Incident hash column.
     */
    public LongColumn getIncidentHashColumn() {
        return incidentHashColumn;
    }

    /**
     * Returns the schema name column.
     * @return Schema name column.
     */
    public StringColumn getSchemaNameColumn() {
        return schemaNameColumn;
    }

    /**
     * Returns the table name column.
     * @return Table name column.
     */
    public StringColumn getTableNameColumn() {
        return tableNameColumn;
    }

    /**
     * Returns the table priority column.
     * @return Table priority column.
     */
    public IntColumn getTablePriorityColumn() {
        return tablePriorityColumn;
    }

    /**
     * Returns the full data stream name column.
     * @return Data stream name column.
     */
    public StringColumn getDataStreamNameColumn() {
        return dataStreamNameColumn;
    }

    /**
     * Returns the data quality dimension column.
     * @return Data quality dimension column.
     */
    public StringColumn getQualityDimensionColumn() {
        return qualityDimensionColumn;
    }

    /**
     * Returns the check category column.
     * @return Check category column.
     */
    public StringColumn getCheckCategoryColumn() {
        return checkCategoryColumn;
    }

    /**
     * Returns the check type column.
     * @return Check type column.
     */
    public StringColumn getCheckTypeColumn() {
        return checkTypeColumn;
    }

    /**
     * Returns the check name column.
     * @return Check name column.
     */
    public StringColumn getCheckNameColumn() {
        return checkNameColumn;
    }

    /**
     * Returns the highest noticed issue severity.
     * @return Highest issue severity.
     */
    public IntColumn getHighestSeverityColumn() {
        return highestSeverityColumn;
    }

    /**
     * Returns the minimum severity configured for this incident.
     * @return Minimum issue severity.
     */
    public IntColumn getMinimumSeverityColumn() {
        return minimumSeverityColumn;
    }

    /**
     * Returns the timestamp when incident was first reported.
     * @return First seen timestamp column.
     */
    public InstantColumn getFirstSeenColumn() {
        return firstSeenColumn;
    }

    /**
     * Returns the timestamp column when the incident was last seen.
     * @return Last seen column.
     */
    public InstantColumn getLastSeenColumn() {
        return lastSeenColumn;
    }

    /**
     * Returns the column that contains a timestamp of the last point in time when data quality issues are matched to this incident.
     * @return The timestamp until the incident matches new issues.
     */
    public InstantColumn getIncidentUntilColumn() {
        return incidentUntilColumn;
    }

    /**
     * Returns the column with the total number of failed data quality checks.
     * @return Total number of failed data quality checks.
     */
    public IntColumn getFailedChecksCountColumn() {
        return failedChecksCountColumn;
    }

    /**
     * Returns the column that stores the url to an external ticket.
     * @return Ticket url column.
     */
    public StringColumn getIssueUrlColumn() {
        return issueUrlColumn;
    }

    /**
     * Returns the column name that stores the login of the person who resolved the incident.
     * @return The login of the person who resolved the incident.
     */
    public StringColumn getResolvedByColumn() {
        return resolvedByColumn;
    }

    /**
     * Returns the column that stores the incident status.
     * @return Incident status column.
     */
    public StringColumn getStatusColumn() {
        return statusColumn;
    }

    /**
     * Returns the created at column.
     * @return Created at column.
     */
    public InstantColumn getCreatedAtColumn() {
        return createdAtColumn;
    }

    /**
     * Returns the updated at column.
     * @return Updated at column.
     */
    public InstantColumn getUpdatedAtColumn() {
        return updatedAtColumn;
    }

    /**
     * Returns the created by column.
     * @return Created by column.
     */
    public StringColumn getCreatedByColumn() {
        return createdByColumn;
    }

    /**
     * Returns the updated by column.
     * @return Updated by column.
     */
    public StringColumn getUpdatedByColumn() {
        return updatedByColumn;
    }
}
