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
package ai.dqo.cli.terminal;

import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import tech.tablesaw.api.Table;

import java.util.Scanner;

import static org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.LinkType.start;

/**
 * Console terminal wrapper. Provides access to the terminal services.
 */
@Component
public class TerminalWriterImpl implements TerminalWriter {
    private final Terminal terminal;

    @Autowired
    public TerminalWriterImpl(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     * Writes a text to the terminal without moving to the next line.
     * @param text Text to be written.
     */
    @Override
    public void write(String text) {
		this.terminal.writer().write(text);
		this.terminal.flush();
    }

    /**
     * Writes a line to the terminal followed by an end of line.
     * @param text Text to be written.
     */
    @Override
    public void writeLine(String text) {
		write(text + "\n");
    }

    /**
     * Writes a command usage message to the terminal.
     * @param command Command object.
     */
    @Override
    public void writeCommandHelp(Object command) {
        String usageMessage = new CommandLine(command).getUsageMessage();
		writeLine(usageMessage);
    }

    /**
     * Writes a command usage message to the terminal.
     *
     * @param command Command line object.
     */
    @Override
    public void writeCommandSynopsis(CommandLine command) {
        CommandLine.Help help = command.getHelp();
        String helpText = help.fullSynopsis();
        writeLine(helpText);
    }

    /**
     * Clears the screen.
     */
    public void clearScreen() {
        this.terminal.puts(InfoCmp.Capability.clear_screen);
        this.terminal.flush();
    }

    /**
     * Renders a table model.
     * @param tableData Table data.
     * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
     */
    @Override
    public void writeTable(FormattedTableDto<?> tableData, boolean addBorder) {
        int height = this.terminal.getHeight() / 3;
        int rowsLeft = tableData.getRows().size();
        int index = 0;

        while (rowsLeft > 0) {
            int start = index * height;
            int maxEnd = tableData.getRows().size();
            if (start >= maxEnd) {
                break;
            }
            int end = Math.min(start + height, maxEnd);
            TableModel model = new BeanListTableModel<>(tableData.getRows().subList(start, end), tableData.getHeaders());

            TableBuilder tableBuilder = new TableBuilder(model);
            if (addBorder) {
                tableBuilder.addInnerBorder(BorderStyle.oldschool);
                tableBuilder.addHeaderBorder(BorderStyle.oldschool);
            }
            String renderedTable = tableBuilder.build().render(this.terminal.getWidth() - 1);

            this.write(renderedTable);

            if (rowsLeft >= height) {
                this.writeLine("Do you want to go to next page?");
                try {
                    int response = this.terminal.reader().read();
                    if (response != 'Y' && response != 'y') {
                        break;
                    }
                } catch (Exception e) {
                    return;
                }
            }

            rowsLeft -= height;
            index++;
        }
    }

    /**
     * Writes a table dataset with a header that is extracted from the column names.
     * @param table Table (dataset).
     * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
     */
    @Override
    public void writeTable(Table table, boolean addBorder) {
        int height = addBorder ? this.terminal.getHeight() / 3 : this.terminal.getHeight() - 2;
        int rowsLeft = table.rowCount();
        int index = 0;

        while (rowsLeft > 0) {
            int start = index * height;
            int maxEnd = table.rowCount();
            if (start >= maxEnd) {
                break;
            }
            int end = Math.min(start + height, maxEnd);
            TablesawDatasetTableModel tableModel = new TablesawDatasetTableModel(table.inRange(start, end));

            TableBuilder tableBuilder = new TableBuilder(tableModel);
            if (addBorder) {
                tableBuilder.addInnerBorder(BorderStyle.oldschool);
                tableBuilder.addHeaderBorder(BorderStyle.oldschool);
            }
            String renderedTable = tableBuilder.build().render(this.terminal.getWidth() - 1);

            this.write(renderedTable);

            if (rowsLeft >= height) {
                this.writeLine("Do you want to go to next page? [y/N]");
                Scanner scan = new Scanner(System.in);
                try {
                    int response = this.terminal.reader().read();
                    if (response != 'Y' && response != 'y') {
                        break;
                    }
                } catch (Exception e) {
                    return;
                }
            }

            rowsLeft -= height;
            index++;
        }
    }

    /**
     * Renders a table using a given table model.
     * @param tableModel Table model for the spring shell table.
     * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
     */
    @Override
    public void writeTable(TableModel tableModel, boolean addBorder) {
        TableBuilder tableBuilder = new TableBuilder(tableModel);
        if (addBorder) {
            tableBuilder.addInnerBorder(BorderStyle.oldschool);
            tableBuilder.addHeaderBorder(BorderStyle.oldschool);
        }
        String renderedTable = tableBuilder.build().render(this.terminal.getWidth() - 1);
        // TODO: support interactive paging for long lists
        this.write(renderedTable);
    }
}
