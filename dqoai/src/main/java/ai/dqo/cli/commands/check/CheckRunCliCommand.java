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
package ai.dqo.cli.commands.check;

import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.check.impl.CheckService;
import ai.dqo.cli.completion.completedcommands.ITableNameCommand;
import ai.dqo.cli.completion.completers.ColumnNameCompleter;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.FullTableNameCompleter;
import ai.dqo.cli.output.OutputFormatService;
import ai.dqo.cli.terminal.FileWritter;
import ai.dqo.cli.terminal.TablesawDatasetTableModel;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListenerProvider;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "check run" 2nd level CLI command that executes data quality checks.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "run", description = "Run checks matching specified filters")
public class CheckRunCliCommand  extends BaseCommand implements ICommand, ITableNameCommand {
    private final TerminalWriter terminalWriter;
    private final TerminalTableWritter terminalTableWritter;
    private final CheckService checkService;
    private CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider;
    private JsonSerializer jsonSerializer;
    private final OutputFormatService outputFormatService;
    private final FileWritter fileWritter;

    /**
     * Dependency injection constructor.
     * @param terminalWriter Terminal writer.
     * @param checkService Check implementation service.
     * @param jsonSerializer  Json serializer.
     */
    @Autowired
    public CheckRunCliCommand(TerminalWriter terminalWriter,
                              TerminalTableWritter terminalTableWritter,
                              CheckService checkService,
                              CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider,
                              JsonSerializer jsonSerializer,
                              OutputFormatService outputFormatService,
                              FileWritter fileWritter) {
        this.terminalWriter = terminalWriter;
        this.terminalTableWritter = terminalTableWritter;
        this.checkService = checkService;
        this.checkExecutionProgressListenerProvider = checkExecutionProgressListenerProvider;
        this.jsonSerializer = jsonSerializer;
        this.outputFormatService = outputFormatService;
        this.fileWritter = fileWritter;
    }

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name, supports patterns like 'conn*'",
            completionCandidates = ConnectionNameCompleter.class)
    private String connection;

    @CommandLine.Option(names = {"-t", "--table"}, description = "Full table name (schema.table), supports patterns like 'sch*.tab*'",
            completionCandidates = FullTableNameCompleter.class)
    private String table;

    @CommandLine.Option(names = {"-col", "--column"}, description = "Column name, supports patterns like '*_id'",
            completionCandidates = ColumnNameCompleter.class)
    private String column;

    @CommandLine.Option(names = {"-ch", "--check"}, description = "Data quality check name, supports patterns like '*_id'")
    private String check;

    @CommandLine.Option(names = {"-s", "--sensor"}, description = "Data quality sensor name (sensor definition or sensor name), supports patterns like 'table/validity/*'")
    private String sensor;

    @CommandLine.Option(names = {"-e", "--enabled"}, description = "Runs only enabled or only disabled sensors, by default only enabled sensors are executed", defaultValue = "true")
    private Boolean enabled = true;

    @CommandLine.Option(names = {"-ct", "--check-type"}, description = "Data quality check type (adhoc, checkpoint, partitioned)")
    private CheckType checkType;

    @CommandLine.Option(names = {"-s", "--time-scale"}, description = "Time scale for checkpoint and partitioned checks (daily, monthly, etc.)")
    private CheckTimeScale timeScale;

    @CommandLine.Option(names = {"-cat", "--category"}, description = "Check category name (standard, nulls, numeric, etc.)")
    private String checkCategory;

    @CommandLine.Option(names = {"-d", "--dummy"}, description = "Runs data quality check in a dummy mode, sensors are not executed on the target database, but the rest of the process is performed", defaultValue = "false")
    private boolean dummyRun;

    @CommandLine.Option(names = {"-m", "--mode"}, description = "Reporting mode (silent, summary, info, debug)", defaultValue = "summary")
    private CheckRunReportingMode mode = CheckRunReportingMode.summary;

    @CommandLine.Option(names = {"-ds", "--data-stream-level"}, description = "Data stream level filter",
            required = false)
    private String[] dimensions;

    @CommandLine.Option(names = {"-l", "--label"}, description = "Label filter", required = false)
    private String[] labels;

    @CommandLine.Option(names = {"-f", "--fail-at"}, description = "Lowest data quality issue severity level (warning, error, fatal) that will cause the command to return with an error code. Use 'none' to return always a success error code.", defaultValue = "error")
    private CheckRunCommandFailThreshold failAt;

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
     * Gets the time scale filter for checkpoint and partitioned checks.
     * @return Time scale filter.
     */
    public CheckTimeScale getTimeScale() {
        return timeScale;
    }

    /**
     * Sets the time scale filter for checkpoint and partitioned checks.
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
    public CheckRunReportingMode getMode() {
        return mode;
    }

    /**
     * Sets the progress reporting mode.
     * @param mode Progress reporting mode.
     */
    public void setMode(CheckRunReportingMode mode) {
        this.mode = mode;
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
        filters.setConnectionName(this.connection);
        filters.setSchemaTableName(this.table);
        filters.setColumnName(this.column);
        filters.setCheckName(this.check);
        filters.setSensorName(this.sensor);
        filters.setCheckType(this.checkType);
        filters.setTimeScale(this.timeScale);
        filters.setCheckCategory(this.checkCategory);
        filters.setEnabled(this.enabled);
        filters.setDimensions(this.dimensions);
        filters.setLabels(this.labels);

        CheckExecutionProgressListener progressListener = this.checkExecutionProgressListenerProvider.getProgressListener(this.mode, false);
        CheckExecutionSummary checkExecutionSummary = this.checkService.runChecks(filters, progressListener, this.dummyRun);

        if (this.mode != CheckRunReportingMode.silent) {
            TablesawDatasetTableModel tablesawDatasetTableModel = new TablesawDatasetTableModel(checkExecutionSummary.getSummaryTable());
			this.terminalWriter.writeLine("Check evaluation summary per table:");
            switch(this.getOutputFormat()) {
                case CSV: {
                    String csvContent = this.outputFormatService.tableToCsv(tablesawDatasetTableModel);
                    if (this.isWriteToFile()) {
                        CliOperationStatus cliOperationStatus = this.fileWritter.writeStringToFile(csvContent);
                        this.terminalWriter.writeLine(cliOperationStatus.getMessage());
                    }
                    else {
                        this.terminalWriter.write(csvContent);
                    }
                    break;
                }
                case JSON: {
                    String jsonContent = this.outputFormatService.tableToJson(tablesawDatasetTableModel);
                    if (this.isWriteToFile()) {
                        CliOperationStatus cliOperationStatus = this.fileWritter.writeStringToFile(jsonContent);
                        this.terminalWriter.writeLine(cliOperationStatus.getMessage());
                    }
                    else {
                        this.terminalWriter.write(jsonContent);
                    }
                    break;
                }
                default: {
                    if (this.isWriteToFile()) {
                        TableBuilder tableBuilder = new TableBuilder(tablesawDatasetTableModel);
                        tableBuilder.addInnerBorder(BorderStyle.oldschool);
                        tableBuilder.addHeaderBorder(BorderStyle.oldschool);
                        String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);
                        CliOperationStatus cliOperationStatus = this.fileWritter.writeStringToFile(renderedTable);
                        this.terminalWriter.writeLine(cliOperationStatus.getMessage());
                    }
                    else {
                        this.terminalTableWritter.writeTable(tablesawDatasetTableModel, true);
                    }
                    break;
                }
            }
        }

        int fatalIssuesCount = checkExecutionSummary.getFatalSeverityIssuesCount();
        int errorIssuesCount = checkExecutionSummary.getErrorSeverityIssuesCount();
        int warningIssuesCount = checkExecutionSummary.getWarningSeverityIssuesCount();

        CheckRunCommandFailThreshold checkRunCommandFailThreshold = this.failAt != null ? this.failAt : CheckRunCommandFailThreshold.error;
        switch (checkRunCommandFailThreshold) {
            case warning:
                if (warningIssuesCount > 0 && errorIssuesCount == 0 && fatalIssuesCount == 0) {
                    return 1;
                }
                // move to the next level...

            case error:
                if (errorIssuesCount > 0 && fatalIssuesCount == 0) {
                    return 2;
                }
                // move to the next level...

            case fatal:
                if (fatalIssuesCount > 0) {
                    return 3;
                }
                // move to the next level...

            default:
                return 0;
        }
    }
}
