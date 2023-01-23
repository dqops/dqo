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
package ai.dqo.cli.commands.connection;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.connection.impl.ConnectionService;
import ai.dqo.cli.commands.connection.impl.models.ConnectionListModel;
import ai.dqo.cli.output.OutputFormatService;
import ai.dqo.cli.terminal.FileWritter;
import ai.dqo.cli.terminal.FormattedTableDto;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import com.google.api.client.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "connection list" 2nd level cli command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "list", description = "List connections which match filters")
public class ConnectionListCliCommand extends BaseCommand implements ICommand {
    private ConnectionService connectionService;
    private TerminalWriter terminalWriter;
    private TerminalTableWritter terminalTableWritter;
    private OutputFormatService outputFormatService;
    private FileWritter fileWritter;

    public ConnectionListCliCommand() {
    }

    @Autowired
    public ConnectionListCliCommand(ConnectionService connectionService,
									TerminalWriter terminalWriter,
                                    TerminalTableWritter terminalTableWritter,
                                    OutputFormatService outputFormatService,
                                    FileWritter fileWritter) {
        this.connectionService = connectionService;
        this.terminalWriter = terminalWriter;
        this.terminalTableWritter = terminalTableWritter;
        this.outputFormatService = outputFormatService;
        this.fileWritter = fileWritter;
    }

    @CommandLine.Option(names = {"-n", "--name"}, description = "Connection name filter", required = false)
    private String name;

    @CommandLine.Option(names = {"-d", "--dimension"}, description = "Dimension filter", required = false)
    private String[] dimensions;

    @CommandLine.Option(names = {"-l", "--label"}, description = "Label filter", required = false)
    private String[] labels;

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
        if (Strings.isNullOrEmpty(name)) {
            name = "*";
        }
        FormattedTableDto<ConnectionListModel> formattedTable = this.connectionService.loadConnectionTable(name, dimensions, labels);
        switch(this.getOutputFormat()) {
            case CSV: {
                String csvContent = this.outputFormatService.tableToCsv(formattedTable);
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
                String jsonContent = this.outputFormatService.tableToJson(formattedTable);
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
                    TableModel model = new BeanListTableModel<>(formattedTable.getRows(), formattedTable.getHeaders());
                    TableBuilder tableBuilder = new TableBuilder(model);
                    tableBuilder.addInnerBorder(BorderStyle.oldschool);
                    tableBuilder.addHeaderBorder(BorderStyle.oldschool);
                    String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);
                    CliOperationStatus cliOperationStatus = this.fileWritter.writeStringToFile(renderedTable);
                    this.terminalWriter.writeLine(cliOperationStatus.getMessage());
                }
                else {
                    this.terminalTableWritter.writeTable(formattedTable, true);
                }
                break;
            }
        }

        return 0;
    }
}
