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

package com.dqops.data.checkresults.models.currentstatus;

import com.dqops.checks.CheckType;
import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.rules.RuleSeverityLevel;
import com.dqops.utils.docs.generators.SampleListUtility;
import com.dqops.utils.docs.generators.SampleMapUtility;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The table validity status. It is a summary of the results of the most recently executed data quality checks on the table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableCurrentDataQualityStatusModel", description = "The table's most recent data quality status. " +
        "It is a summary of the results of the most recently executed data quality checks on the table. " +
        "Verify the value of the highest_severity_level to see if there are any data quality issues on the table. " +
        "The values of severity levels are: 0 - all data quality checks passed, 1 - a warning was detected, 2 - an error was detected, " +
        "3 - a fatal data quality issue was detected.")
@Data
public class TableCurrentDataQualityStatusModel implements CurrentDataQualityStatusHolder, Cloneable {
    /**
     * The connection name in DQOps.
     */
    @JsonPropertyDescription("The connection name in DQOps.")
    private String connectionName;

    /**
     * The schema name.
     */
    @JsonPropertyDescription("The schema name.")
    private String schemaName;

    /**
     * The table name.
     */
    @JsonPropertyDescription("The table name.")
    private String tableName;

    /**
     * The most recent data quality issue severity for this table. When the table is monitored using data grouping, it is the highest issue severity of all recently analyzed data groups.
     * For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.
     */
    @JsonPropertyDescription("The most recent data quality issue severity for this table. When the table is monitored using data grouping, it is the highest issue severity of all recently analyzed data groups. " +
            "For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.")
    private RuleSeverityLevel currentSeverity;

    /**
     * The highest severity of previous executions of this data quality issue in the analyzed time range.
     * It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore.
     * For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.
     */
    @JsonPropertyDescription("The highest severity of previous executions of this data quality issue in the analyzed time range. " +
            "It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore. " +
            "For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.")
    private RuleSeverityLevel highestHistoricalSeverity;

    /**
     * The UTC timestamp when the most recent data quality check was executed on the table.
     */
    @JsonPropertyDescription("The UTC timestamp when the most recent data quality check was executed on the table.")
    private Instant lastCheckExecutedAt;

    /**
     * The total number of most recent checks that were executed on the table. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.
     */
    @JsonPropertyDescription("The total number of most recent checks that were executed on the table. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.")
    private int executedChecks;

    /**
     * The number of most recent valid data quality checks that passed without raising any issues.
     */
    @JsonPropertyDescription("The number of most recent valid data quality checks that passed without raising any issues.")
    private int validResults;

    /**
     * The number of most recent data quality checks that failed by raising a warning severity data quality issue.
     */
    @JsonPropertyDescription("The number of most recent data quality checks that failed by raising a warning severity data quality issue.")
    private int warnings;

    /**
     * The number of most recent data quality checks that failed by raising an error severity data quality issue.
     */
    @JsonPropertyDescription("The number of most recent data quality checks that failed by raising an error severity data quality issue.")
    private int errors;

    /**
     * The number of most recent data quality checks that failed by raising a fatal severity data quality issue.
     */
    @JsonPropertyDescription("The number of most recent data quality checks that failed by raising a fatal severity data quality issue.")
    private int fatals;

    /**
     * The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQOps,
     * invalid queries in data quality sensors or invalid python rules.
     * When an execution error is reported, the configuration of a data quality check on a table must be updated.
     */
    @JsonPropertyDescription("The number of data quality check execution errors that were reported due to access issues to the data source, " +
            "invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. " +
            "When an execution error is reported, the configuration of a data quality check on a table must be updated.")
    private int executionErrors;

    /**
     * Data quality KPI score for the table, measured as a percentage of passed data quality checks.
     * DQOps counts data quality issues at a warning severity level as passed checks. The data quality KPI score is a value in the range 0..100.
     */
    @JsonPropertyDescription("Data quality KPI score for the table, measured as a percentage of passed data quality checks. " +
            "DQOps counts data quality issues at a warning severity level as passed checks. The data quality KPI score is a value in the range 0..100.")
    private Double dataQualityKpi;

    /**
     * The dictionary of statuses for data quality checks. The keys are data quality check names, the values are the current data quality check statuses
     * that describe the most current status.
     */
    @JsonPropertyDescription("The dictionary of statuses for data quality checks. The keys are data quality check names, the values are the current data quality check statuses " +
            "that describe the most current status.")
    private Map<String, CheckCurrentDataQualityStatusModel> checks = new LinkedHashMap<>();

    /**
     * Dictionary of data statues for all columns that have any known data quality results. The keys in the dictionary are the column names.
     */
    @JsonPropertyDescription("Dictionary of data statues for all columns that have any known data quality results. The keys in the dictionary are the column names.")
    private Map<String, ColumnCurrentDataQualityStatusModel> columns = new LinkedHashMap<>();

    /**
     * The data quality status for each data quality dimension. The status includes the status of table-level checks and column-level checks for all columns that are reported for the same dimension, such as Completeness.
     */
    @JsonPropertyDescription("Dictionary of the current data quality statues for each data quality dimension.")
    private Map<String, DimensionCurrentDataQualityStatusModel> dimensions = new LinkedHashMap<>();

    /**
     * Analyzes all table level checks and column level checks to calculate the highest severity level at a table level.
     */
    public void calculateHighestCurrentAndHistoricSeverity() {
        for (ColumnCurrentDataQualityStatusModel columnModel : columns.values()) {
            columnModel.calculateHighestCurrentAndHistoricSeverity();

            if (this.currentSeverity == null) {
                this.currentSeverity = columnModel.getCurrentSeverity();
            } else if (columnModel.getCurrentSeverity() != null &&
                    this.currentSeverity.getSeverity() < columnModel.getCurrentSeverity().getSeverity() &&
                    columnModel.getCurrentSeverity().getSeverity() != 4) {
                this.currentSeverity = columnModel.getCurrentSeverity();
            }

            if (this.highestHistoricalSeverity == null) {
                this.highestHistoricalSeverity = columnModel.getHighestHistoricalSeverity();
            } else if (columnModel.getHighestHistoricalSeverity() != null &&
                    this.highestHistoricalSeverity.getSeverity() < columnModel.getHighestHistoricalSeverity().getSeverity() &&
                    columnModel.getHighestHistoricalSeverity().getSeverity() != 4) {
                this.highestHistoricalSeverity = columnModel.getHighestHistoricalSeverity();
            }
        }

        for (CheckCurrentDataQualityStatusModel checkStatusModel : checks.values()) {
            if (this.currentSeverity == null) {
                this.currentSeverity = RuleSeverityLevel.fromCheckSeverity(checkStatusModel.getCurrentSeverity());
            } else if (checkStatusModel.getCurrentSeverity() != null &&
                    this.currentSeverity.getSeverity() < checkStatusModel.getCurrentSeverity().getSeverity() &&
                    checkStatusModel.getCurrentSeverity().getSeverity() != 4) {
                this.currentSeverity = RuleSeverityLevel.fromCheckSeverity(checkStatusModel.getCurrentSeverity());
            }

            if (this.highestHistoricalSeverity == null) {
                this.highestHistoricalSeverity = checkStatusModel.getHighestHistoricalSeverity();
            } else if (checkStatusModel.getHighestHistoricalSeverity() != null &&
                    this.highestHistoricalSeverity.getSeverity() < checkStatusModel.getHighestHistoricalSeverity().getSeverity() &&
                    checkStatusModel.getHighestHistoricalSeverity().getSeverity() != 4) {
                this.highestHistoricalSeverity = checkStatusModel.getHighestHistoricalSeverity();
            }
        }
    }

    /**
     * Recalculates the number of valid results, warnings, errors, fatal errors and execution errors from the values in the check results.
     * This method should be called after the list of checks was filtered.
     */
    public void countIssuesFromCheckResults() {
        this.executedChecks = 0;
        this.validResults = 0;
        this.warnings = 0;
        this.errors = 0;
        this.fatals = 0;
        this.executionErrors = 0;
        this.lastCheckExecutedAt = null;

        for (CheckCurrentDataQualityStatusModel checkStatusModel : checks.values()) {
            if (checkStatusModel.getLastExecutedAt() != null &&
                    (this.lastCheckExecutedAt == null || checkStatusModel.getLastExecutedAt().isAfter(this.lastCheckExecutedAt))) {
                this.lastCheckExecutedAt = checkStatusModel.getLastExecutedAt();
            }

            this.executedChecks += checkStatusModel.getExecutedChecks();
            this.validResults += checkStatusModel.getValidResults();
            this.warnings += checkStatusModel.getWarnings();
            this.errors += checkStatusModel.getErrors();
            this.fatals += checkStatusModel.getFatals();
            this.executionErrors += checkStatusModel.getExecutionErrors();
        }

        for (ColumnCurrentDataQualityStatusModel columModel : this.columns.values()) {
            columModel.countIssuesFromCheckResults();

            this.executedChecks += columModel.getExecutedChecks();
            this.validResults += columModel.getValidResults();
            this.warnings += columModel.getWarnings();
            this.errors += columModel.getErrors();
            this.fatals += columModel.getFatals();
            this.executionErrors += columModel.getExecutionErrors();
        }
    }

    /**
     * Calculates a data quality KPI score for a table.
     */
    public void calculateDataQualityKpiScore() {
        int totalExecutedChecksWithNoExecutionErrors = this.getValidResults() + this.getWarnings() + this.getErrors() + this.getFatals();
        Double dataQualityKpi = totalExecutedChecksWithNoExecutionErrors > 0 ?
                (this.getValidResults() + this.getWarnings()) * 100.0 / totalExecutedChecksWithNoExecutionErrors : null;
        setDataQualityKpi(dataQualityKpi);

        if (this.columns != null) {
            for (ColumnCurrentDataQualityStatusModel columnStatusModel : this.columns.values()) {
                columnStatusModel.calculateDataQualityKpiScore();
            }
        }
    }

    /**
     * Calculates the status for each data quality dimension, aggregates statuses of data quality checks for each data quality dimension.
     */
    public void calculateStatusesForDataQualityDimensions() {
        for (ColumnCurrentDataQualityStatusModel columnModel : columns.values()) {
            columnModel.calculateStatusesForDimensions();

            for (DimensionCurrentDataQualityStatusModel columnDimensionModel : columnModel.getDimensions().values()) {
                String dimensionName = columnDimensionModel.getDimension();
                DimensionCurrentDataQualityStatusModel tableDimensionModel = this.dimensions.get(dimensionName);
                if (tableDimensionModel == null) {
                    tableDimensionModel = new DimensionCurrentDataQualityStatusModel();
                    tableDimensionModel.setDimension(dimensionName);
                    this.dimensions.put(dimensionName, tableDimensionModel);
                }

                tableDimensionModel.appendResults(columnDimensionModel);
            }
        }

        for (CheckCurrentDataQualityStatusModel checkStatusModel : checks.values()) {
            String qualityDimension = checkStatusModel.getQualityDimension();
            if (qualityDimension == null) {
                continue; // should not happen, but if somebody intentionally configures an empty dimension....
            }

            DimensionCurrentDataQualityStatusModel dimensionModel = this.dimensions.get(qualityDimension);
            if (dimensionModel == null) {
                dimensionModel = new DimensionCurrentDataQualityStatusModel();
                dimensionModel.setDimension(qualityDimension);
                this.dimensions.put(qualityDimension, dimensionModel);
            }

            dimensionModel.appendCheckResult(checkStatusModel);
        }

        for (DimensionCurrentDataQualityStatusModel dimensionModel : this.dimensions.values()) {
            dimensionModel.calculateDataQualityKpiScore();
        }
    }

    /**
     * Makes a shallow clone of the object.
     * @return Shallow clone of the object.
     */
    protected TableCurrentDataQualityStatusModel clone() {
        try {
            return (TableCurrentDataQualityStatusModel)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }

    /**
     * Creates a deep clone of the table status model, preserving only the checks for an expected check type.
     * All scores and the data quality KPI is recalculated for the checks that left.
     * @param checkFilter Check filter that filters the checks that should be preserved.
     * @param includeColumnsAndChecks Include columns and checks. When this parameter is false, the dictionary of columns is removed.
     * @return A deep clone of the object with results only for that check type.
     */
    public TableCurrentDataQualityStatusModel cloneFilteredByCheckType(
            Predicate<CheckCurrentDataQualityStatusModel> checkFilter, boolean includeColumnsAndChecks) {
        TableCurrentDataQualityStatusModel tableStatusClone = this.clone();
        tableStatusClone.currentSeverity = null;
        tableStatusClone.highestHistoricalSeverity = null;
        tableStatusClone.dataQualityKpi = null;
        tableStatusClone.dimensions = new LinkedHashMap<>();
        tableStatusClone.checks = new LinkedHashMap<>();

        for (Map.Entry<String, CheckCurrentDataQualityStatusModel> keyValue : this.checks.entrySet()) {
            if (checkFilter == null || checkFilter.test(keyValue.getValue())) {
                tableStatusClone.checks.put(keyValue.getKey(), keyValue.getValue());
            }
        }

        tableStatusClone.columns = new LinkedHashMap<>();
        for (Map.Entry<String, ColumnCurrentDataQualityStatusModel> columnKeyValue : this.columns.entrySet()) {
            ColumnCurrentDataQualityStatusModel columnModelFiltered = columnKeyValue.getValue().cloneFilteredByCheckType(checkFilter);
            if (!columnModelFiltered.getChecks().isEmpty()) {
                tableStatusClone.columns.put(columnKeyValue.getKey(), columnModelFiltered);
            }
        }

        tableStatusClone.countIssuesFromCheckResults();
        tableStatusClone.calculateHighestCurrentAndHistoricSeverity();
        tableStatusClone.calculateDataQualityKpiScore();
        tableStatusClone.calculateStatusesForDataQualityDimensions();

        if (!includeColumnsAndChecks) {
            tableStatusClone.checks = null; // detaching checks
            tableStatusClone.columns = null; // detaching columns
        }

        return tableStatusClone;
    }

    /**
     * Creates a shallow clone of the object, without the checks and columns.
     * @return Shallow clone of the object, without checks and columns.
     */
    public TableCurrentDataQualityStatusModel shallowCloneWithoutCheckResultsAndColumns() {
        TableCurrentDataQualityStatusModel tableStatusClone = this.clone();
        tableStatusClone.checks = null; // detaching checks
        tableStatusClone.columns = null; // detaching columns
        return tableStatusClone;
    }

    public static class TableCurrentDataQualityStatusModelSampleFactory implements SampleValueFactory<TableCurrentDataQualityStatusModel> {
        @Override
        public TableCurrentDataQualityStatusModel createSample() {
            List<String> sampleChecksKeys = SampleListUtility.generateStringList("table_" + SampleStringsRegistry.getCheckName(), 2);

            int currentSeverityChecksLimit = CheckResultStatus.fromSeverity(CheckResultStatus.error.getSeverity() + 1).getSeverity();
            List<CheckCurrentDataQualityStatusModel> sampleChecksValues = SampleListUtility.generateList(CheckCurrentDataQualityStatusModel.class, 2,
                    CheckCurrentDataQualityStatusModel::getLastExecutedAt,
                    lastExecutedAt -> lastExecutedAt.plus(
                            Math.abs(new Random(100).nextInt()) % 120,
                            ChronoUnit.MINUTES),
                    CheckCurrentDataQualityStatusModel::setLastExecutedAt,

                    CheckCurrentDataQualityStatusModel::getCurrentSeverity,
                    severity -> CheckResultStatus.fromSeverity(
                            Math.abs(new Random(Integer.toUnsignedLong(
                                    severity.getSeverity())).nextInt()) % currentSeverityChecksLimit),
                    CheckCurrentDataQualityStatusModel::setCurrentSeverity);

            Map<String, CheckCurrentDataQualityStatusModel> sampleChecks = SampleMapUtility.generateMap(sampleChecksKeys, sampleChecksValues);

            List<String> sampleColumnsKeys = SampleListUtility.generateStringList(SampleStringsRegistry.getColumnName(), 2);
            List<ColumnCurrentDataQualityStatusModel> sampleColumnsValues = SampleListUtility.generateList(ColumnCurrentDataQualityStatusModel.class, 2);
            Map<String, ColumnCurrentDataQualityStatusModel> sampleColumns = SampleMapUtility.generateMap(sampleColumnsKeys, sampleColumnsValues);

            List<CheckCurrentDataQualityStatusModel> allSampleChecks = Stream.concat(
                    sampleChecksValues.stream(),
                    sampleColumnsValues.stream().flatMap(c -> c.getChecks().values().stream())
            ).collect(Collectors.toList());

            Instant checkAggregatedLastExecutedAt = Collections.max(allSampleChecks,
                    Comparator.comparing(CheckCurrentDataQualityStatusModel::getLastExecutedAt)).getLastExecutedAt();
            int executedChecksAggregate = allSampleChecks.size();
            int validResultsAggregate = (int) allSampleChecks.stream().filter(c -> c.getCurrentSeverity() == CheckResultStatus.valid).count();
            int warningResults = (int) allSampleChecks.stream().filter(c -> c.getCurrentSeverity() == CheckResultStatus.warning).count();
            int errorResults = (int) allSampleChecks.stream().filter(c -> c.getCurrentSeverity() == CheckResultStatus.error).count();
            int fatalResults = (int) allSampleChecks.stream().filter(c -> c.getCurrentSeverity() == CheckResultStatus.fatal).count();
            RuleSeverityLevel highestSeverity =
                    fatalResults > 0 ? RuleSeverityLevel.fatal : errorResults > 0 ? RuleSeverityLevel.error : warningResults > 0 ? RuleSeverityLevel.warning : RuleSeverityLevel.valid;

            int totalExecutedChecksWithNoExecutionErrors = validResultsAggregate + warningResults + errorResults + fatalResults;
            Double dataQualityKpi = totalExecutedChecksWithNoExecutionErrors > 0 ?
                    (validResultsAggregate + warningResults) * 100.0 / totalExecutedChecksWithNoExecutionErrors : null;

            TableCurrentDataQualityStatusModel result = new TableCurrentDataQualityStatusModel() {{
                setConnectionName(SampleStringsRegistry.getConnectionName());
                setSchemaName(SampleStringsRegistry.getSchemaName());
                setTableName(SampleStringsRegistry.getTableName());
                setLastCheckExecutedAt(checkAggregatedLastExecutedAt);
                setExecutedChecks(executedChecksAggregate);
                setValidResults(validResultsAggregate);
                setWarnings(warningResults);
                setErrors(errorResults);
                setCurrentSeverity(highestSeverity);
                setHighestHistoricalSeverity(RuleSeverityLevel.fatal);
                setChecks(sampleChecks);
                setColumns(sampleColumns);
                setDataQualityKpi(dataQualityKpi);
                setFatals(0);
                setExecutionErrors(0);
            }};
            result.calculateHighestCurrentAndHistoricSeverity();
            return result;
        }
    }
}
