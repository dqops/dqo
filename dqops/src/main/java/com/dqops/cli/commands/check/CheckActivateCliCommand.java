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
package com.dqops.cli.commands.check;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.check.impl.CheckCliService;
import com.dqops.cli.commands.check.impl.models.AllChecksModelCliPatchParameters;
import com.dqops.cli.completion.completedcommands.ITableNameCommand;
import com.dqops.cli.completion.completers.*;
import com.dqops.cli.output.OutputFormatService;
import com.dqops.cli.terminal.FileWriter;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalTableWritter;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.utils.serialization.JsonSerializer;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * "check activate" 2nd level CLI command that activates data quality checks.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "activate", description = "Activates data quality checks matching specified filters")
public class CheckActivateCliCommand extends BaseCommand implements ICommand, ITableNameCommand {
    private TerminalReader terminalReader;
    private TerminalWriter terminalWriter;
    private TerminalTableWritter terminalTableWritter;
    private CheckCliService checkService;
    private JsonSerializer jsonSerializer;
    private OutputFormatService outputFormatService;
    private FileWriter fileWriter;

    public CheckActivateCliCommand() {
    }

    @Autowired
    public CheckActivateCliCommand(TerminalReader terminalReader,
                                   TerminalWriter terminalWriter,
                                   TerminalTableWritter terminalTableWritter,
                                   CheckCliService checkService,
                                   JsonSerializer jsonSerializer,
                                   OutputFormatService outputFormatService,
                                   FileWriter fileWriter) {
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.terminalTableWritter = terminalTableWritter;
        this.checkService = checkService;
        this.jsonSerializer = jsonSerializer;
        this.outputFormatService = outputFormatService;
        this.fileWriter = fileWriter;
    }

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name, supports patterns like 'conn*'",
            completionCandidates = ConnectionNameCompleter.class)
    private String connection;

    @CommandLine.Option(names = {"-t", "--table", "--full-table-name"}, description = "Full table name (schema.table), supports patterns like 'sch*.tab*'",
            completionCandidates = FullTableNameCompleter.class)
    private String table;

    @CommandLine.Option(names = {"-col", "--column"}, description = "Column name, supports patterns like '*_id'",
            completionCandidates = ColumnNameCompleter.class)
    private String column;

    @CommandLine.Option(names = {"-ch", "--check"}, description = "Data quality check name, supports patterns like '*_id'",
            completionCandidates = CheckNameCompleter.class)
    private String check;

    @CommandLine.Option(names = {"-sn", "--sensor"}, description = "Data quality sensor name (sensor definition or sensor name), supports patterns like 'table/validity/*'",
            completionCandidates = SensorNameCompleter.class)
    private String sensor;

    @CommandLine.Option(names = {"-ct", "--check-type"}, description = "Data quality check type (profiling, monitoring, partitioned)")
    private CheckType checkType;

    @CommandLine.Option(names = {"-ts", "--time-scale"}, description = "Time scale for monitoring and partitioned checks (daily, monthly, etc.)")
    private CheckTimeScale timeScale;

    @CommandLine.Option(names = {"-cat", "--category"}, description = "Check category name (standard, nulls, numeric, etc.)")
    private String checkCategory;

    @CommandLine.Option(names = {"-dt", "--data-type"}, description = "Datatype of columns on which to enable checks.")
    private String datatypeFilter;

    @CommandLine.Option(names = {"-n", "--nullable"}, description = "Enable check only on nullable columns (false for explicitly non-nullable columns).")
    private Boolean columnNullable = null;

    @CommandLine.Option(names = {"-o", "--override"}, description = "Override existing configuration of selected checks.")
    private Boolean override = false;

    @CommandLine.Option(names = {"-S"}, description = "Configuration parameters for the sensor. Usage: -S<param_name>=<param_value>, --sensor-param=<param_name>=<param_value>")
    private Map<String, String> sensorParams;

    @CommandLine.Option(names = {"-W"}, description = "Warning level rule options. Usage: -W<rule_name>=<rule_value>, --warning-rule=<rule_name>=<rule_value>")
    private Map<String, String> warningLevelOptions;

    @CommandLine.Option(names = {"-E"}, description = "Error level rule options. Usage: -E<rule_name>=<rule_value>, --error-rule=<rule_name>=<rule_value>")
    private Map<String, String> errorLevelOptions;

    @CommandLine.Option(names = {"-F"}, description = "Fatal level rule options. Usage: -F<rule_name>=<rule_value>, --fatal-rule=<rule_name>=<rule_value>")
    private Map<String, String> fatalLevelOptions;

    @CommandLine.Option(names = {"-w", "--enable-warning"}, description = "Enables a warning severity rule. A warning severity rule is also enabled when any warning rule parameters are passed using the -Wparam_nam=value parameters")
    private boolean enableWarning;

    @CommandLine.Option(names = {"-e", "--enable-error"}, description = "Enables an error severity rule. An error severity rule is also enabled when any warning rule parameters are passed using the -Eparam_nam=value parameters")
    private boolean enableError;

    @CommandLine.Option(names = {"-f", "--enable-fatal"}, description = "Enables a fatal severity rule. A fatal severity rule is also enabled when any fatal rule parameters are passed using the -Fparam_nam=value parameters")
    private boolean enableFatal;

    @CommandLine.Option(names = {"-dw", "--disable-warning"}, description = "Disables a warning severity rule. Use this option to update already activated data quality checks, together with the --override option.")
    private boolean disableWarning;

    @CommandLine.Option(names = {"-de", "--disable-error"}, description = "Disables an error severity rule. Use this option to update already activated data quality checks, together with the --override option.")
    private boolean disableError;

    @CommandLine.Option(names = {"-df", "--disable-fatal"}, description = "Disables a fatal severity rule. Use this option to update already activated data quality checks, together with the --override option.")
    private boolean disableFatal;

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
     * Gets the column datatype filter.
     * @return Map of datatype filter.
     */
    public String getDatatypeFilter() {
        return datatypeFilter;
    }

    /**
     * Gets the information whether the supplied parameters for the checks should substitute their current configuration.
     * @return The "override" flag.
     */
    public Boolean getOverride() {
        return override;
    }

    /**
     * Set whether the supplied parameters for the checks should substitute their current configuration.
     * @param override New value of the "override" flag.
     */
    public void setOverride(Boolean override) {
        this.override = override;
    }

    /**
     * Gets the sensor parameters map.
     * @return Sensor parameters map.
     */
    public Map<String, String> getSensorParams() {
        return sensorParams;
    }

    /**
     * Gets the warning level options map.
     * @return Warning level rule options map.
     */
    public Map<String, String> getWarningLevelOptions() {
        return warningLevelOptions;
    }

    /**
     * Gets the error level options map.
     * @return Error level rule options map.
     */
    public Map<String, String> getErrorLevelOptions() {
        return errorLevelOptions;
    }

    /**
     * Gets the fatal level options map.
     * @return Fatal level rule options map.
     */
    public Map<String, String> getFatalLevelOptions() {
        return fatalLevelOptions;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        if (Strings.isNullOrEmpty(this.connection)) {
            throwRequiredParameterMissingIfHeadless("--connection");
            this.connection = this.terminalReader.prompt("Connection name (--connection)", null, false);
        }
        if (Strings.isNullOrEmpty(this.check)) {
            throwRequiredParameterMissingIfHeadless("--check");
            this.check = this.terminalReader.prompt("Data quality check name (--check)", null, false);
        }

        CheckSearchFilters filters = new CheckSearchFilters();
        filters.setConnection(this.connection);
        filters.setFullTableName(this.table);
        filters.setColumn(this.column);
        filters.setCheckName(this.check);
        filters.setSensorName(this.sensor);
        filters.setCheckType(this.checkType);
        filters.setTimeScale(this.timeScale);
        filters.setCheckCategory(this.checkCategory);
        filters.setColumnDataType(this.datatypeFilter);
        filters.setColumnNullable(this.columnNullable);

        if (this.warningLevelOptions == null || this.warningLevelOptions.isEmpty()) {
            this.warningLevelOptions = null;
        }

        if (this.errorLevelOptions == null || this.errorLevelOptions.isEmpty()) {
            this.errorLevelOptions = null;
        }

        if (this.fatalLevelOptions == null || this.fatalLevelOptions.isEmpty()) {
            this.fatalLevelOptions = null;
        }

        // now enable them with default options if the "enable" is on
        if (this.warningLevelOptions == null && this.enableWarning) {
            this.warningLevelOptions = new LinkedHashMap<>();
        }

        if (this.errorLevelOptions == null && this.enableError) {
            this.errorLevelOptions = new LinkedHashMap<>();
        }

        if (this.fatalLevelOptions == null && this.enableFatal) {
            this.fatalLevelOptions = new LinkedHashMap<>();
        }

        AllChecksModelCliPatchParameters patchParameters = new AllChecksModelCliPatchParameters() {{
            setCheckSearchFilters(filters);
            setSensorOptions(sensorParams);
            setOverrideConflicts(override);

            setWarningLevelOptions(warningLevelOptions);
            setErrorLevelOptions(errorLevelOptions);
            setFatalLevelOptions(fatalLevelOptions);

            setDisableWarningLevel(disableWarning);
            setDisableErrorLevel(disableError);
            setDisableFatalLevel(disableFatal);
        }};

        this.checkService.updateAllChecksPatch(patchParameters);

        return 0;
    }
}
