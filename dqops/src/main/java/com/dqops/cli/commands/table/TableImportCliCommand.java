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
package com.dqops.cli.commands.table;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.table.impl.TableCliService;
import com.dqops.cli.commands.table.impl.TableImportFailedException;
import com.dqops.cli.completion.completedcommands.IConnectionNameCommand;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.completion.completers.SchemaNameCompleter;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalTableWritter;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.metadata.search.StringPatternComparer;
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
@CommandLine.Command(name = "import", header = "Import tables from a specified database", description = "Import the tables from the specified database into the application. It allows the user to import the tables from the database into the application for performing various database operations.")
public class TableImportCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
    private TerminalFactory terminalFactory;
    private TerminalTableWritter terminalTableWriter;
    private TableCliService tableImportService;

    public TableImportCliCommand() {
    }

    @Autowired
    public TableImportCliCommand(TerminalFactory terminalFactory,
                                 TerminalTableWritter terminalTableWriter,
								 TableCliService tableImportService) {
        this.terminalFactory = terminalFactory;
        this.terminalTableWriter = terminalTableWriter;
        this.tableImportService = tableImportService;
    }

    /**
     * Connection name parameter.
     */
    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name",
            required = false, completionCandidates = ConnectionNameCompleter.class)
    private String connection;

    /**
     * Schema name parameter.
     */
    @CommandLine.Option(names = {"-s", "--schema"}, description = "Schema name",
            required = false, completionCandidates = SchemaNameCompleter.class)
    private String schema;

    /**
     * Table name parameter.
     */
    @CommandLine.Option(names = {"-t", "--table"}, description = "Table name, without the schema name.",
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
			this.connection = this.terminalFactory.getReader().prompt("Connection name (--connection)", null, false);
        }

        if (Strings.isNullOrEmpty(this.schema) || StringPatternComparer.isSearchPattern(this.schema)) {
			throwRequiredParameterMissingIfHeadless("--schema");

            try {
                schemaTable = this.tableImportService.loadSchemaList(this.connection, this.schema);
            } catch (TableImportFailedException ex) {
                this.terminalFactory.getWriter().writeLine(String.format("Cannot read schemas from the connection %s, error: %s", this.connection, ex.getMessage()));
                return -1;
            }

            Integer schemaIndex = this.terminalTableWriter.pickTableRowWithPaging("Select the schema (database, etc.) from which tables will be imported:",
                    schemaTable);
            if (schemaIndex == null) {
                this.terminalFactory.getWriter().writeLine("No schema was selected.");
                return -1;
            }

			this.setSchema(schemaTable.getString(schemaIndex.intValue(), 0));
        }

        CliOperationStatus cliOperationStatus = this.tableImportService.importTables(this.getConnection(), this.getSchema(), this.getTable());
        if (cliOperationStatus.isSuccess()) {
            this.terminalFactory.getWriter().writeLine("\nThe following tables were imported:");
            this.terminalFactory.getWriter().writeTable(cliOperationStatus.getTable(), true);
            return 0;
        } else {
            this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
            return -1;
        }
    }
}
