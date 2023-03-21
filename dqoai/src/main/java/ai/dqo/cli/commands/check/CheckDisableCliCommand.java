/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.completion.completedcommands.ITableNameCommand;
import ai.dqo.cli.completion.completers.ColumnNameCompleter;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.FullTableNameCompleter;
import ai.dqo.cli.output.OutputFormatService;
import ai.dqo.cli.terminal.FileWritter;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.services.check.CheckService;
import ai.dqo.services.check.models.UIAllChecksPatchParameters;
import ai.dqo.utils.serialization.JsonSerializer;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.HashMap;

/**
 * "check disable" 2nd level CLI command that disables data quality checks.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "disable", description = "Disable data quality checks matching specified filters")
public class CheckDisableCliCommand extends BaseCommand implements ICommand, ITableNameCommand {
    private TerminalReader terminalReader;
    private CheckService checkService;

    public CheckDisableCliCommand() {
    }

    @Autowired
    public CheckDisableCliCommand(TerminalReader terminalReader,
                                  CheckService checkService) {
        this.terminalReader = terminalReader;
        this.checkService = checkService;
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

    @CommandLine.Option(names = {"-ct", "--check-type"}, description = "Data quality check type (profiling, checkpoint, partitioned)")
    private CheckType checkType;

    @CommandLine.Option(names = {"-ts", "--time-scale"}, description = "Time scale for checkpoint and partitioned checks (daily, monthly, etc.)")
    private CheckTimeScale timeScale;

    @CommandLine.Option(names = {"-cat", "--category"}, description = "Check category name (standard, nulls, numeric, etc.)")
    private String checkCategory;

    @CommandLine.Option(names = {"-dt", "--data-type"}, description = "Datatype of columns on which to disable checks.")
    private String datatypeFilter;

    @CommandLine.Option(names = {"-n", "--nullable"}, description = "Disable check only on nullable columns (false for explicitly non-nullable columns).")
    private Boolean columnNullable = null;

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
     * Gets the column datatype filter.
     * @return Map of datatype filter.
     */
    public String getDatatypeFilter() {
        return datatypeFilter;
    }

    public void setDatatypeFilter(String datatypeFilter) {
        this.datatypeFilter = datatypeFilter;
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
        filters.setConnectionName(this.connection);
        filters.setSchemaTableName(this.table);
        filters.setColumnName(this.column);
        filters.setCheckName(this.check);
        filters.setSensorName(this.sensor);
        filters.setCheckType(this.checkType);
        filters.setTimeScale(this.timeScale);
        filters.setCheckCategory(this.checkCategory);
        filters.setColumnDataType(this.datatypeFilter);
        filters.setColumnNullable(this.columnNullable);

        this.checkService.disableChecks(filters);

        return 0;
    }
}
