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
package com.dqops.cli.commands.collect;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.check.CheckRunCommandFailThreshold;
import com.dqops.cli.commands.check.impl.CheckCliService;
import com.dqops.cli.completion.completedcommands.ITableNameCommand;
import com.dqops.cli.completion.completers.*;
import com.dqops.cli.output.OutputFormatService;
import com.dqops.cli.terminal.FileWriter;
import com.dqops.cli.terminal.TablesawDatasetTableModel;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalTableWritter;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.execution.checks.CheckExecutionErrorSummary;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.checks.progress.CheckExecutionProgressListenerProvider;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import com.dqops.execution.errorsampling.ErrorSamplerExecutionSummary;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListenerProvider;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionReportingMode;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "collect errorsamples" 2nd level CLI command that executes data quality checks in an error sampling mode to collect error samples.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "errorsamples", header = "Run data quality checks that match a given condition and collects error samples",
        description = "Run data quality checks on your dataset that match a given condition and capture their error samples. The command output is a table with the results that provides insight into the list of invalid values.")
public class CollectErrorSamplesCliCommand extends BaseCommand implements ICommand, ITableNameCommand {
    private TerminalFactory terminalFactory;
    private TerminalTableWritter terminalTableWritter;
    private CheckCliService checkService;
    private ErrorSamplerExecutionProgressListenerProvider errorSamplerExecutionProgressListenerProvider;
    private JsonSerializer jsonSerializer;
    private OutputFormatService outputFormatService;
    private FileWriter fileWriter;

    public CollectErrorSamplesCliCommand() {
    }

    /**
     * Dependency injection constructor.
     * @param checkService Check implementation service.
     * @param jsonSerializer  Json serializer.
     */
    @Autowired
    public CollectErrorSamplesCliCommand(TerminalFactory terminalFactory,
                                         TerminalTableWritter terminalTableWritter,
                                         CheckCliService checkService,
                                         ErrorSamplerExecutionProgressListenerProvider errorSamplerExecutionProgressListenerProvider,
                                         JsonSerializer jsonSerializer,
                                         OutputFormatService outputFormatService,
                                         FileWriter fileWriter) {
        this.terminalFactory = terminalFactory;
        this.terminalTableWritter = terminalTableWritter;
        this.checkService = checkService;
        this.errorSamplerExecutionProgressListenerProvider = errorSamplerExecutionProgressListenerProvider;
        this.jsonSerializer = jsonSerializer;
        this.outputFormatService = outputFormatService;
        this.fileWriter = fileWriter;
    }

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name, supports patterns like 'conn*'",
            completionCandidates = ConnectionNameCompleter.class)
    private String connection;

    @CommandLine.Option(names = {"-t", "--table", "--full-table-name"}, description = "Full table name (schema.table), supports wildcard patterns 'sch*.tab*'",
            completionCandidates = FullTableNameCompleter.class)
    private String table;

    @CommandLine.Option(names = {"-col", "--column"}, description = "Column name, supports patterns like '*_id'",
            completionCandidates = ColumnNameCompleter.class)
    private String column;

    @CommandLine.Option(names = {"-ch", "--check"}, description = "Data quality check name, supports patterns like '*_id'",
            completionCandidates = CheckNameCompleter.class)
    private String check;

    @CommandLine.Option(names = {"-s", "--sensor"}, description = "Data quality sensor name (sensor definition or sensor name), supports patterns like 'table/validity/*'",
            completionCandidates = SensorNameCompleter.class)
    private String sensor;

    @CommandLine.Option(names = {"-e", "--enabled"}, description = "Runs only enabled or only disabled sensors, by default only enabled sensors are executed", defaultValue = "true")
    private Boolean enabled = true;

    @CommandLine.Option(names = {"-sc", "--scope"}, description = "Error sampling scope that is used for tables with data grouping. Error samples can be collected for the whole table, or for each data grouping. By default, collects error samples for a whole table.", defaultValue = "table")
    private ErrorSamplesDataScope scope = ErrorSamplesDataScope.table;

    @CommandLine.Option(names = {"-ct", "--check-type"}, description = "Data quality check type (profiling, monitoring, partitioned)")
    private CheckType checkType;

    @CommandLine.Option(names = {"-ts", "--time-scale"}, description = "Time scale for monitoring and partitioned checks (daily, monthly, etc.)")
    private CheckTimeScale timeScale;

    @CommandLine.Option(names = {"-cat", "--category"}, description = "Check category name (volume, nulls, numeric, etc.)")
    private String checkCategory;

    @CommandLine.Option(names = {"-d", "--dummy"}, description = "Runs data quality check in a dummy mode, sensors are not executed on the target database, but the rest of the process is performed", defaultValue = "false")
    private boolean dummyRun;

    @CommandLine.Option(names = {"-fe", "--fail-on-execution-errors"}, description = "Returns a command status code 4 (when called from the command line) if any execution errors were raised during the execution, the default value is true.", defaultValue = "true")
    private boolean failOnExecutionErrors = true;

    @CommandLine.Option(names = {"-m", "--mode"}, description = "Reporting mode (silent)", defaultValue = "silent")
    private ErrorSamplerExecutionReportingMode mode = ErrorSamplerExecutionReportingMode.silent;

    @CommandLine.Option(names = {"-tag", "--data-grouping-level-tag"}, description = "Data grouping hierarchy level filter (tag)",
            required = false)
    private String[] tags;

    @CommandLine.Option(names = {"-l", "--label"}, description = "Label filter", required = false)
    private String[] labels;

    @CommandLine.Mixin
    private TimeWindowFilterParameters timeWindowFilterParameters;

    /**
     * Gets the connection name.
     * @return Connection name.
     */
    public String getConnection() {
        return connection;
    }

    /**
     * Sets the connection name.
     * @param connection Connection name.
     */
    public void setConnection(String connection) {
        this.connection = connection;
    }

    /**
     * Get the schema.table filter.
     * @return schema.table filter.
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets the schema.table filter.
     * @param table Full table name filter.
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * Get the column name filter.
     * @return Column name filter.
     */
    public String getColumn() {
        return column;
    }

    /**
     * Sets the column name filter.
     * @param column Column name filter.
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * Get the check filter.
     * @return Check filter.
     */
    public String getCheck() {
        return check;
    }

    /**
     * Sets the check name filter.
     * @param check Check name filter.
     */
    public void setCheck(String check) {
        this.check = check;
    }

    /**
     * Gets the sensor name or sensor definition filter.
     * @return Sensor name filter.
     */
    public String getSensor() {
        return sensor;
    }

    /**
     * Sets the sensor name filter.
     * @param sensor Sensor name.
     */
    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    /**
     * Gets the enabled only (or disabled when the value is false) filter.
     * @return Enabled filter.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled filter.
     * @param enabled Enabled filter.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns the check type (category).
     * @return Check type (category).
     */
    public CheckType getCheckType() {
        return checkType;
    }

    /**
     * Sets the check type to filter.
     * @param checkType Check type filter.
     */
    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    /**
     * Gets the time scale filter for monitoring and partitioned checks.
     * @return Time scale filter.
     */
    public CheckTimeScale getTimeScale() {
        return timeScale;
    }

    /**
     * Sets the time scale filter for monitoring and partitioned checks.
     * @param timeScale Time scale filter.
     */
    public void setTimeScale(CheckTimeScale timeScale) {
        this.timeScale = timeScale;
    }

    /**
     * Returns a check category filter.
     * @return Check category filter.
     */
    public String getCheckCategory() {
        return checkCategory;
    }

    /**
     * Sets the checks category filter.
     * @param checkCategory Check category filter.
     */
    public void setCheckCategory(String checkCategory) {
        this.checkCategory = checkCategory;
    }

    /**
     * Returns an array of data stream hierarchy tags that must be assigned.
     * @return Array of data stream hierarchy tags.
     */
    public String[] getTags() {
        return tags;
    }

    /**
     * Sets an array of required tags on the data stream hierarchy.
     * @param tags Tags on the hierarchy.
     */
    public void setTags(String[] tags) {
        this.tags = tags;
    }

    /**
     * Sets the array of required labels to be assigned to target objects or their parents.
     * @return Array of labels.
     */
    public String[] getLabels() {
        return labels;
    }

    /**
     * Sets a reference to an array with required labels.
     * @param labels Array with required labels.
     */
    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    /**
     * Is the dummy run enabled.
     * @return Dummy run is enabled.
     */
    public boolean isDummyRun() {
        return dummyRun;
    }

    /**
     * Sets the dummy run mode.
     * @param dummyRun Dummy mode.
     */
    public void setDummyRun(boolean dummyRun) {
        this.dummyRun = dummyRun;
    }

    /**
     * Gets the progress reporting mode.
     * @return Progress reporting mode.
     */
    public ErrorSamplerExecutionReportingMode getMode() {
        return mode;
    }

    /**
     * Sets the progress reporting mode.
     * @param mode Progress reporting mode.
     */
    public void setMode(ErrorSamplerExecutionReportingMode mode) {
        this.mode = mode;
    }

    /**
     * Returns the time window filter parameters.
     * @return Time window filter parameters.
     */
    public TimeWindowFilterParameters getTimeWindowFilterParameters() {
        return timeWindowFilterParameters;
    }

    /**
     * Sets the time window filter parameters.
     * @param timeWindowFilterParameters Time window filter parameters.
     */
    public void setTimeWindowFilterParameters(TimeWindowFilterParameters timeWindowFilterParameters) {
        this.timeWindowFilterParameters = timeWindowFilterParameters;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        CheckSearchFilters filters = new CheckSearchFilters();
        filters.setConnection(this.connection);
        filters.setFullTableName(this.table);
        filters.setColumn(this.column);
        filters.setCheckName(this.check);
        filters.setSensorName(this.sensor);
        filters.setCheckType(this.checkType);
        filters.setTimeScale(this.timeScale);
        filters.setCheckCategory(this.checkCategory);
        filters.setEnabled(this.enabled);
        filters.setTags(this.tags);
        filters.setLabels(this.labels);

        ErrorSamplerExecutionProgressListener progressListener = this.errorSamplerExecutionProgressListenerProvider.getProgressListener(this.mode, false);
        ErrorSamplerExecutionSummary errorSamplerExecutionSummary = this.checkService.collectErrorSamples(filters, this.timeWindowFilterParameters,
                this.scope, progressListener, this.dummyRun);

        if (errorSamplerExecutionSummary.getTotalCollectorsExecuted() == 0) {
            this.terminalFactory.getWriter().writeLine("No checks with these filters were found, or target checks did not have an error sampler.");
            return 0;
        }

        if (this.mode != ErrorSamplerExecutionReportingMode.silent) {
            TablesawDatasetTableModel tablesawDatasetTableModel = new TablesawDatasetTableModel(errorSamplerExecutionSummary.getSummaryTable());
			this.terminalFactory.getWriter().writeLine("Error sampler summary per table:");
            switch(this.getOutputFormat()) {
                case CSV: {
                    String csvContent = this.outputFormatService.tableToCsv(tablesawDatasetTableModel);
                    if (this.isWriteToFile()) {
                        CliOperationStatus cliOperationStatus = this.fileWriter.writeStringToFile(csvContent);
                        this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
                    }
                    else {
                        this.terminalFactory.getWriter().write(csvContent);
                    }
                    break;
                }
                case JSON: {
                    String jsonContent = this.outputFormatService.tableToJson(tablesawDatasetTableModel);
                    if (this.isWriteToFile()) {
                        CliOperationStatus cliOperationStatus = this.fileWriter.writeStringToFile(jsonContent);
                        this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
                    }
                    else {
                        this.terminalFactory.getWriter().write(jsonContent);
                    }
                    break;
                }
                default: {
                    if (this.isWriteToFile()) {
                        TableBuilder tableBuilder = new TableBuilder(tablesawDatasetTableModel);
                        tableBuilder.addInnerBorder(BorderStyle.oldschool);
                        tableBuilder.addHeaderBorder(BorderStyle.oldschool);
                        String renderedTable = tableBuilder.build().render(this.terminalFactory.getWriter().getTerminalWidth() - 1);
                        CliOperationStatus cliOperationStatus = this.fileWriter.writeStringToFile(renderedTable);
                        this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
                    }
                    else {
                        this.terminalTableWritter.writeTable(tablesawDatasetTableModel, true);
                    }
                    break;
                }
            }
        }

        int executionErrorsCount = (int)errorSamplerExecutionSummary.getErrorSamplersFailedColumn().sum();
        if (this.failOnExecutionErrors && executionErrorsCount > 0) {
            return 4;
        }

        return 0;
    }
}
