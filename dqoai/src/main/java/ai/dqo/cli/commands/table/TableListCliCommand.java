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
import ai.dqo.cli.commands.TabularOutputFormat;
import ai.dqo.cli.commands.table.impl.TableService;
import ai.dqo.cli.completion.completedcommands.IConnectionNameCommand;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.TableNameCompleter;
import ai.dqo.cli.terminal.FileWritter;
import ai.dqo.cli.terminal.TablesawDatasetTableModel;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
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
@CommandLine.Command(name = "list", description = "List tables which match filters")
public class TableListCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
    private final TableService tableImportService;
    private final TerminalWriter terminalWriter;
    private final TerminalTableWritter terminalTableWritter;
    private final FileWritter fileWritter;

    @Autowired
    public TableListCliCommand(TerminalWriter terminalWriter,
							   TableService tableImportService,
                               TerminalTableWritter terminalTableWritter,
                               FileWritter fileWritter) {
        this.tableImportService = tableImportService;
        this.terminalWriter = terminalWriter;
        this.terminalTableWritter = terminalTableWritter;
        this.fileWritter = fileWritter;
    }

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name",
            required = false, completionCandidates = ConnectionNameCompleter.class)
    private String connectionName;

    @CommandLine.Option(names = {"-t", "--table"}, description = "Table name filter",
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
                    String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);
                    CliOperationStatus cliOperationStatus2 = this.fileWritter.writeStringToFile(renderedTable);
                    this.terminalWriter.writeLine(cliOperationStatus2.getMessage());
                } else {
                    this.terminalTableWritter.writeTable(cliOperationStatus.getTable(), true);
                }
            } else {
                if (this.isWriteToFile()) {
                    CliOperationStatus cliOperationStatus2 = this.fileWritter.writeStringToFile(cliOperationStatus.getMessage());
                    this.terminalWriter.writeLine(cliOperationStatus2.getMessage());
                }
                else {
                    this.terminalWriter.write(cliOperationStatus.getMessage());
                }
            }
            return 0;
        } else {
            this.terminalWriter.writeLine(cliOperationStatus.getMessage());
            return -1;
        }
    }
}
