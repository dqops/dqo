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

package com.dqops.data.checkresults.normalization;

import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.utils.tables.TableColumnUtility;
import tech.tablesaw.api.*;

/**
 * A wrapper over a check results tablesaw table that adds missing columns or ensures that the columns are of the correct type.
 */
public class CheckResultsNormalizedResult extends SensorReadoutsNormalizedResult {
    private final IntColumn severityColumn;
    private final LongColumn incidentHashColumn;
    private final TextColumn referenceConnectionColumn;
    private final TextColumn referenceSchemaColumn;
    private final TextColumn referenceTableColumn;
    private final TextColumn referenceColumnColumn;
    private final BooleanColumn includeInKpiColumn;
    private final BooleanColumn includeInSlaColumn;
    private final DoubleColumn fatalLowerBoundColumn;
    private final DoubleColumn fatalUpperBoundColumn;
    private final DoubleColumn errorLowerBoundColumn;
    private final DoubleColumn errorUpperBoundColumn;
    private final DoubleColumn warningLowerBoundColumn;
    private final DoubleColumn warningUpperBoundColumn;

    /**
     * Create a normalized wrapper over a table with the .data/check_results data, adding missing columns.
     * @param table Tablesaw data for the check results.
     */
    public CheckResultsNormalizedResult(Table table) {
        this(table, true);
    }

    /**
     * Create a normalized wrapper over a table with the .data/check_results data, adding missing columns.
     * @param table Tablesaw data for the check results.
     * @param addColumWhenMissing Adds columns if they are missing.
     */
    public CheckResultsNormalizedResult(Table table, boolean addColumWhenMissing) {
        super(table, addColumWhenMissing);
        this.severityColumn = TableColumnUtility.getOrAddIntColumn(table, CheckResultsColumnNames.SEVERITY_COLUMN_NAME, addColumWhenMissing);
        this.incidentHashColumn = TableColumnUtility.getOrAddLongColumn(table, CheckResultsColumnNames.INCIDENT_HASH_COLUMN_NAME, addColumWhenMissing);
        this.referenceConnectionColumn = TableColumnUtility.getOrAddTextColumn(table, CheckResultsColumnNames.REFERENCE_CONNECTION_COLUMN_NAME, addColumWhenMissing);
        this.referenceSchemaColumn = TableColumnUtility.getOrAddTextColumn(table, CheckResultsColumnNames.REFERENCE_SCHEMA_COLUMN_NAME, addColumWhenMissing);
        this.referenceTableColumn = TableColumnUtility.getOrAddTextColumn(table, CheckResultsColumnNames.REFERENCE_TABLE_COLUMN_NAME, addColumWhenMissing);
        this.referenceColumnColumn = TableColumnUtility.getOrAddTextColumn(table, CheckResultsColumnNames.REFERENCE_COLUMN_COLUMN_NAME, addColumWhenMissing);
        this.includeInKpiColumn = TableColumnUtility.getOrAddBooleanColumn(table, CheckResultsColumnNames.INCLUDE_IN_KPI_COLUMN_NAME, addColumWhenMissing);
        this.includeInSlaColumn = TableColumnUtility.getOrAddBooleanColumn(table, CheckResultsColumnNames.INCLUDE_IN_SLA_COLUMN_NAME, addColumWhenMissing);
        this.fatalLowerBoundColumn = TableColumnUtility.getOrAddDoubleColumn(table, CheckResultsColumnNames.FATAL_LOWER_BOUND_COLUMN_NAME, addColumWhenMissing);
        this.fatalUpperBoundColumn = TableColumnUtility.getOrAddDoubleColumn(table, CheckResultsColumnNames.FATAL_UPPER_BOUND_COLUMN_NAME, addColumWhenMissing);
        this.errorLowerBoundColumn = TableColumnUtility.getOrAddDoubleColumn(table, CheckResultsColumnNames.ERROR_LOWER_BOUND_COLUMN_NAME, addColumWhenMissing);
        this.errorUpperBoundColumn = TableColumnUtility.getOrAddDoubleColumn(table, CheckResultsColumnNames.ERROR_UPPER_BOUND_COLUMN_NAME, addColumWhenMissing);
        this.warningLowerBoundColumn = TableColumnUtility.getOrAddDoubleColumn(table, CheckResultsColumnNames.WARNING_LOWER_BOUND_COLUMN_NAME, addColumWhenMissing);
        this.warningUpperBoundColumn = TableColumnUtility.getOrAddDoubleColumn(table, CheckResultsColumnNames.WARNING_UPPER_BOUND_COLUMN_NAME, addColumWhenMissing);
    }

    /**
     * Returns the severity column.
     * @return Severity column.
     */
    public IntColumn getSeverityColumn() {
        return severityColumn;
    }

    /**
     * Returns the incident hash column.
     * @return Incident hash column.
     */
    public LongColumn getIncidentHashColumn() {
        return incidentHashColumn;
    }

    /**
     * Returns the column that contains the name of a connection (data source) that contains the reference data that are compared.
     * @return The name of a reference connection.
     */
    public TextColumn getReferenceConnectionColumn() {
        return referenceConnectionColumn;
    }

    /**
     * Returns the column that contains the name of a schema that contains the reference data that are compared.
     * @return The name of a reference schema.
     */
    public TextColumn getReferenceSchemaColumn() {
        return referenceSchemaColumn;
    }

    /**
     * Returns the column that contains the name of a table that contains the reference data that are compared.
     * @return The name of a reference table.
     */
    public TextColumn getReferenceTableColumn() {
        return referenceTableColumn;
    }

    /**
     * Returns the column that contains the name of a column that contains the reference data that are compared.
     * @return The name of a reference column.
     */
    public TextColumn getReferenceColumnColumn() {
        return referenceColumnColumn;
    }

    /**
     * Returns the include in KPI column.
     * @return Include in KPI column.
     */
    public BooleanColumn getIncludeInKpiColumn() {
        return includeInKpiColumn;
    }

    /**
     * Returns the include in SLA column.
     * @return Include in SLA column.
     */
    public BooleanColumn getIncludeInSlaColumn() {
        return includeInSlaColumn;
    }

    /**
     * Returns the fatal issue lower bound column.
     * @return Fatal severity lower bound.
     */
    public DoubleColumn getFatalLowerBoundColumn() {
        return fatalLowerBoundColumn;
    }

    /**
     * Returns the fatal issue upper bound column.
     * @return Fatal severity upper bound.
     */
    public DoubleColumn getFatalUpperBoundColumn() {
        return fatalUpperBoundColumn;
    }

    /**
     * Returns the error issue lower bound column.
     * @return Error severity lower bound.
     */
    public DoubleColumn getErrorLowerBoundColumn() {
        return errorLowerBoundColumn;
    }

    /**
     * Returns the error issue upper bound column.
     * @return Error severity upper bound.
     */
    public DoubleColumn getErrorUpperBoundColumn() {
        return errorUpperBoundColumn;
    }

    /**
     * Returns the warning issue lower bound column.
     * @return Warning severity lower bound.
     */
    public DoubleColumn getWarningLowerBoundColumn() {
        return warningLowerBoundColumn;
    }

    /**
     * Returns the warning issue upper bound column.
     * @return Warning severity upper bound.
     */
    public DoubleColumn getWarningUpperBoundColumn() {
        return warningUpperBoundColumn;
    }
}
