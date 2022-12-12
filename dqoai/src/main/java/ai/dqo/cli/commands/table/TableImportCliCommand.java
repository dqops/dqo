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
package ai.dqo.cli.commands.table;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.table.impl.TableImportFailedException;
import ai.dqo.cli.commands.table.impl.TableService;
import ai.dqo.cli.completion.completedcommands.IConnectionNameCommand;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.SchemaNameCompleter;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.metadata.search.StringPatternComparer;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import tech.tablesaw.api.Table;

/**
 * CLI command "table import" that retrieves a list of tables from the source metadata and imports those tables.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "import", description = "Import tables from a specified database")
public class TableImportCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
    private final TerminalReader terminalReader;
    private final TerminalWriter terminalWriter;
    private final TerminalTableWritter terminalTableWriter;
    private final TableService tableImportService;

    @Autowired
    public TableImportCliCommand(TerminalReader terminalReader,
								 TerminalWriter terminalWriter,
                                 TerminalTableWritter terminalTableWriter,
								 TableService tableImportService) {
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.terminalTableWriter = terminalTableWriter;
        this.tableImportService = tableImportService;
    }

    /**
     * Connection name parameter.
     */
    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection Name",
            required = false, completionCandidates = ConnectionNameCompleter.class)
    private String connection;

    /**
     * Schema name parameter.
     */
    @CommandLine.Option(names = {"-s", "--schema"}, description = "Schema Name",
            required = false, completionCandidates = SchemaNameCompleter.class)
    private String schema;

    /**
     * Table name parameter.
     */
    @CommandLine.Option(names = {"-t", "--table"}, description = "Table Name",
            required = false)
    private String table;

    /**
     * Returns the connection name.
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
     * Schema name that should be imported.
     * @return Schema name.
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the schema name to be imported.
     * @param schema Schema name.
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * Table name that should be imported.
     * @return Table name.
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets the table name to be imported.
     * @param table Table name.
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        Table schemaTable = null;

        if (Strings.isNullOrEmpty(this.connection)) {
			throwRequiredParameterMissingIfHeadless("--connection");
			this.connection = this.terminalReader.prompt("Connection name (--connection)", null, false);
        }

        if (Strings.isNullOrEmpty(this.schema) || StringPatternComparer.isSearchPattern(this.schema)) {
			throwRequiredParameterMissingIfHeadless("--schema");

            try {
                schemaTable = this.tableImportService.loadSchemaList(this.connection, this.schema);
            } catch (TableImportFailedException ex) {
				this.terminalWriter.writeLine(String.format("Cannot read schemas from the connection %s, error: %s", this.connection, ex.getMessage()));
                return -1;
            }

            Integer schemaIndex = this.terminalTableWriter.pickTableRowWithPaging("Select the schema (database, etc.) from which tables will be imported:",
                    schemaTable);
            if (schemaIndex == null) {
				this.terminalWriter.writeLine("No schema was selected.");
                return -1;
            }

			this.setSchema(schemaTable.getString(schemaIndex.intValue(), 0));
        }

        CliOperationStatus cliOperationStatus = this.tableImportService.importTables(this.getConnection(), this.getSchema(), this.getTable());
        if (cliOperationStatus.isSuccess()) {
            this.terminalWriter.writeLine("\nThe following tables were imported:");
            this.terminalWriter.writeTable(cliOperationStatus.getTable(), true);
            return 0;
        } else {
            this.terminalWriter.writeLine(cliOperationStatus.getMessage());
            return -1;
        }
    }
}
