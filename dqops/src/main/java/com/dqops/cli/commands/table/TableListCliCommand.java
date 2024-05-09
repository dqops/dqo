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
import com.dqops.cli.commands.TabularOutputFormat;
import com.dqops.cli.commands.table.impl.TableCliService;
import com.dqops.cli.completion.completedcommands.IConnectionNameCommand;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.completion.completers.TableNameCompleter;
import com.dqops.cli.terminal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Component;
import picocli.CommandLine;


/**
 * Cli command to list tables.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "list", header = "List tables filtered by the given conditions", description = "List all the tables that match a given condition. It allows the user to use various filters, such as table name or schema names to list filtered tables.")
public class TableListCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
    private TerminalFactory terminalFactory;
    private TableCliService tableImportService;
    private TerminalTableWritter terminalTableWritter;
    private FileWriter fileWriter;

    public TableListCliCommand() {
    }

    @Autowired
    public TableListCliCommand(TerminalFactory terminalFactory,
                               TableCliService tableImportService,
                               TerminalTableWritter terminalTableWritter,
                               FileWriter fileWriter) {
        this.terminalFactory = terminalFactory;
        this.tableImportService = tableImportService;
        this.terminalTableWritter = terminalTableWritter;
        this.fileWriter = fileWriter;
    }

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name",
            required = false, completionCandidates = ConnectionNameCompleter.class)
    private String connectionName;

    @CommandLine.Option(names = {"-t", "--table", "--full-table-name"}, description = "Full table name filter in the form \"schema.table\", but also supporting patterns: public.*, *.customers, landing*.customer*.",
            required = false, completionCandidates = TableNameCompleter.class)
    private String tableName;

    @CommandLine.Option(names = {"-d", "--dimension"}, description = "Dimension filter",
            required = false)
    private String[] dimensions;

    @CommandLine.Option(names = {"-l", "--label"}, description = "Label filter", required = false)
    private String[] labels;

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnection() {
        return connectionName;
    }

    /**
     * Sets the connection name.
     * @param connectionName Connection name.
     */
    public void setConnection(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Returns the table name filter.
     * @return Table name filter.
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the table name filter.
     * @param tableName Table name filter.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Returns the dimensions filter.
     * @return Dimensions filter.
     */
    public String[] getDimensions() {
        return dimensions;
    }

    /**
     * Sets the dimensions filter.
     * @param dimensions Dimensions filter.
     */
    public void setDimensions(String[] dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {

        TabularOutputFormat tabularOutputFormat = this.getOutputFormat();
        CliOperationStatus cliOperationStatus = tableImportService.listTables(this.connectionName, this.tableName, tabularOutputFormat, dimensions, labels);
        if (cliOperationStatus.isSuccess()) {
            if (this.getOutputFormat() == TabularOutputFormat.TABLE) {
                if (this.isWriteToFile()) {
                    TableBuilder tableBuilder = new TableBuilder(new TablesawDatasetTableModel(cliOperationStatus.getTable()));
                    tableBuilder.addInnerBorder(BorderStyle.oldschool);
                    tableBuilder.addHeaderBorder(BorderStyle.oldschool);
                    String renderedTable = tableBuilder.build().render(this.terminalFactory.getWriter().getTerminalWidth() - 1);
                    CliOperationStatus cliOperationStatus2 = this.fileWriter.writeStringToFile(renderedTable);
                    this.terminalFactory.getWriter().writeLine(cliOperationStatus2.getMessage());
                } else {
                    this.terminalTableWritter.writeTable(cliOperationStatus.getTable(), true);
                }
            } else {
                if (this.isWriteToFile()) {
                    CliOperationStatus cliOperationStatus2 = this.fileWriter.writeStringToFile(cliOperationStatus.getMessage());
                    this.terminalFactory.getWriter().writeLine(cliOperationStatus2.getMessage());
                }
                else {
                    this.terminalFactory.getWriter().write(cliOperationStatus.getMessage());
                }
            }
            return 0;
        } else {
            this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
            return -1;
        }
    }
}
