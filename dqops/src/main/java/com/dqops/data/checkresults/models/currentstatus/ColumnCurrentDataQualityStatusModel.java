/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.checkresults.models.currentstatus;

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
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;

/**
 * The column validity status. It is a summary of the results of the most recently executed data quality checks on the column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ColumnCurrentDataQualityStatusModel", description = "The column's most recent data quality status. " +
        "It is a summary of the results of the most recently executed data quality checks on the column. " +
        "Verify the value of the current_severity to see if there are any data quality issues on the column.")
@Data
public class ColumnCurrentDataQualityStatusModel implements CurrentDataQualityStatusHolder, Cloneable {
    /**
     * The most recent data quality issue severity for this column. When the table is monitored using data grouping, it is the highest issue severity of all recently analyzed data groups.
     * For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.
     */
    @JsonPropertyDescription("The most recent data quality issue severity for this column. When the table is monitored using data grouping, it is the highest issue severity of all recently analyzed data groups. " +
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
     * The UTC timestamp when the most recent data quality check was executed on the column.
     */
    @JsonPropertyDescription("The UTC timestamp when the most recent data quality check was executed on the column.")
    private Instant lastCheckExecutedAt;

    /**
     * The total number of most recent checks that were executed on the column. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.
     */
    @JsonPropertyDescription("The total number of most recent checks that were executed on the column. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.")
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
            "When an execution error is reported, the configuration of a data quality check on a column must be updated.")
    private int executionErrors;

    /**
     * Data quality KPI score for the column, measured as a percentage of passed data quality checks.
     * DQOps counts data quality issues at a warning severity level as passed checks. The data quality KPI score is a value in the range 0..100.
     */
    @JsonPropertyDescription("Data quality KPI score for the column, measured as a percentage of passed data quality checks. " +
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
     * The data quality status for each data quality dimension. The status includes the status of column-level for the same dimension, such as Completeness.
     */
    @JsonPropertyDescription("Dictionary of the current data quality statues for each data quality dimension.")
    private Map<String, DimensionCurrentDataQualityStatusModel> dimensions = new LinkedHashMap<>();


    /**
     * Calculates the highest current severity status and historic severity status from all checks.
     */
    public void calculateHighestCurrentAndHistoricSeverity() {
        this.currentSeverity = null;
        this.highestHistoricalSeverity = null;

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
     * Processes the check results and computes results for each data quality dimension.
     */
    public void calculateStatusesForDimensions() {
        this.dimensions.clear();

        for (CheckCurrentDataQualityStatusModel checkStatusModel : this.checks.values()) {
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
     * Calculates a data quality KPI score for a column.
     */
    public void calculateDataQualityKpiScore() {
        int totalExecutedChecksWithNoExecutionErrors = this.getValidResults() + this.getWarnings() + this.getErrors() + this.getFatals();
        Double dataQualityKpi = totalExecutedChecksWithNoExecutionErrors > 0 ?
                (this.getValidResults() + this.getWarnings()) * 100.0 / totalExecutedChecksWithNoExecutionErrors : null;
        setDataQualityKpi(dataQualityKpi);
    }

    /**
     * Makes a shallow clone of the object.
     * @return Shallow clone of the object.
     */
    protected ColumnCurrentDataQualityStatusModel clone() {
        try {
            return (ColumnCurrentDataQualityStatusModel)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }

    /**
     * Makes a deep clone of the object.
     * @return Deep clone of the object.
     */
    public ColumnCurrentDataQualityStatusModel deepClone() {
        ColumnCurrentDataQualityStatusModel cloned = this.clone();

        cloned.checks = new LinkedHashMap<>();
        for (Map.Entry<String, CheckCurrentDataQualityStatusModel> checkEntry : this.checks.entrySet()) {
            cloned.checks.put(checkEntry.getKey(), checkEntry.getValue().clone());
        }

        cloned.dimensions = new LinkedHashMap<>();
        for (Map.Entry<String, DimensionCurrentDataQualityStatusModel> dimensionEntry : this.dimensions.entrySet()) {
            cloned.dimensions.put(dimensionEntry.getKey(), dimensionEntry.getValue().clone());
        }

        return cloned;
    }

    /**
     * Creates a shallow clone of the object, without the dictionary of check results.
     * @return Shallow clone, without the check results.
     */
    public ColumnCurrentDataQualityStatusModel shallowCloneWithoutChecks() {
        ColumnCurrentDataQualityStatusModel cloned = this.clone();
        cloned.checks = null;
        return cloned;
    }

    /**
     * Creates a deep clone of the table status model, preserving only the checks for an expected check type.
     * All scores and the data quality KPI is recalculated for the checks that left.
     * @param checkFilter Check filter that filters the checks that should be preserved.
     * @return A deep clone of the object with results only for that check type.
     */
    public ColumnCurrentDataQualityStatusModel cloneFilteredByCheckType(Predicate<CheckCurrentDataQualityStatusModel> checkFilter) {
        ColumnCurrentDataQualityStatusModel tableStatusClone = this.clone();
        tableStatusClone.currentSeverity = null;
        tableStatusClone.highestHistoricalSeverity = null;
        tableStatusClone.lastCheckExecutedAt = null;
        // we are preserving the executedChecks... for informative reasons
        tableStatusClone.validResults = 0;
        tableStatusClone.warnings = 0;
        tableStatusClone.errors = 0;
        tableStatusClone.fatals = 0;
        tableStatusClone.executionErrors = 0;
        tableStatusClone.dimensions = new LinkedHashMap<>();
        tableStatusClone.checks = new LinkedHashMap<>();

        for (Map.Entry<String, CheckCurrentDataQualityStatusModel> keyValue : this.checks.entrySet()) {
            if (checkFilter == null || checkFilter.test(keyValue.getValue())) {
                tableStatusClone.checks.put(keyValue.getKey(), keyValue.getValue());
            }
        }

        tableStatusClone.calculateHighestCurrentAndHistoricSeverity();

        return tableStatusClone;
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

        for (CheckCurrentDataQualityStatusModel checkStatusModel : this.checks.values()) {
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
    }

    public static class ColumnCurrentDataQualityStatusModelSampleFactory implements SampleValueFactory<ColumnCurrentDataQualityStatusModel> {
        @Override
        public ColumnCurrentDataQualityStatusModel createSample() {
            List<String> sampleChecksKeys = SampleListUtility.generateStringList(SampleStringsRegistry.getCheckName(), 3);

            int currentSeverityLimit = CheckResultStatus.fromSeverity(CheckResultStatus.error.getSeverity() + 1).getSeverity();
            List<CheckCurrentDataQualityStatusModel> sampleChecksValues = SampleListUtility.generateList(CheckCurrentDataQualityStatusModel.class, 3,
                CheckCurrentDataQualityStatusModel::getLastExecutedAt,
                lastExecutedAt -> lastExecutedAt.plus(
                        Math.abs(new Random(Integer.toUnsignedLong(
                                lastExecutedAt.atZone(ZoneId.systemDefault()).getMinute())).nextInt()) % 120,
                        ChronoUnit.MINUTES),
                CheckCurrentDataQualityStatusModel::setLastExecutedAt,

                CheckCurrentDataQualityStatusModel::getCurrentSeverity,
                severity -> CheckResultStatus.fromSeverity(
                        Math.abs(new Random(Integer.toUnsignedLong(
                                severity.getSeverity())).nextInt()) % currentSeverityLimit),
                CheckCurrentDataQualityStatusModel::setCurrentSeverity);

            Map<String, CheckCurrentDataQualityStatusModel> sampleChecks = SampleMapUtility.generateMap(sampleChecksKeys, sampleChecksValues);
            Instant checkAggregatedLastExecutedAt = Collections.max(sampleChecksValues,
                    Comparator.comparing(CheckCurrentDataQualityStatusModel::getLastExecutedAt)).getLastExecutedAt();
            int executedChecksAggregate = sampleChecksValues.size();
            int validResultsAggregate = (int) sampleChecksValues.stream().filter(c -> c.getCurrentSeverity() == CheckResultStatus.valid).count();
            int warningResults = (int) sampleChecksValues.stream().filter(c -> c.getCurrentSeverity() == CheckResultStatus.warning).count();
            int errorResults = (int) sampleChecksValues.stream().filter(c -> c.getCurrentSeverity() == CheckResultStatus.error).count();

            ColumnCurrentDataQualityStatusModel result = new ColumnCurrentDataQualityStatusModel() {{
                setLastCheckExecutedAt(checkAggregatedLastExecutedAt);
                setExecutedChecks(executedChecksAggregate);
                setValidResults(validResultsAggregate);
                setWarnings(warningResults);
                setErrors(errorResults);
                setFatals(0);
                setExecutionErrors(0);
                setChecks(sampleChecks);
            }};
            result.calculateHighestCurrentAndHistoricSeverity();
            return result;
        }
    }
}
