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
package com.dqops.cli.terminal;

import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import picocli.CommandLine;
import tech.tablesaw.api.Table;

/**
 * Console terminal wrapper. Provides access to the terminal services.
 * Abstract implementation of the basic operational logic.
 */
public abstract class TerminalWriterAbstract implements TerminalWriter {
    /**
     * Writes a text to the terminal without moving to the next line.
     * @param text Text to be written.
     */
    @Override
    public abstract void write(String text);

    /**
     * Writes a URL to the terminal, formatted as a hyperlink if possible.
     *
     * @param url URL to be linked.
     * @param text Text to be displayed if hyperlinked.
     */
    @Override
    public abstract void writeUrl(String url, String text);

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
     * Renders a table model.
     * @param tableData Table data.
     * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
     */
    @Override
    public void writeTable(FormattedTableDto<?> tableData, boolean addBorder) {
        TableModel tableModel = new BeanListTableModel<>(tableData.getRows(), tableData.getHeaders());
        this.writeTable(tableModel, addBorder);
    }

    /**
     * Writes a table dataset with a header that is extracted from the column names.
     * @param table Table (dataset).
     * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
     */
    @Override
    public void writeTable(Table table, boolean addBorder) {
        TablesawDatasetTableModel tableModel = new TablesawDatasetTableModel(table);
        this.writeTable(tableModel, addBorder);
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
        String renderedTable = tableBuilder.build().render(this.getTerminalWidth() - 1);
        this.write(renderedTable);
    }

    /**
     * Clears the screen.
     */
    @Override
    public abstract void clearScreen();

    /**
     * Gets terminal width.
     * @return Terminal width.
     */
    @Override
    public abstract Integer getTerminalWidth();

    /**
     * Gets terminal height.
     * @return Terminal height.
     */
    @Override
    public abstract Integer getTerminalHeight();
}
