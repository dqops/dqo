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

import org.springframework.shell.table.TableModel;
import picocli.CommandLine;
import tech.tablesaw.api.Table;

/**
 * Console terminal wrapper. Provides access to the terminal services.
 */
public interface TerminalWriter {
    /**
     * Writes a text to the terminal without moving to the next line.
     * @param text Text to be written.
     */
    void write(String text);

    /**
     * Writes a URL to the terminal, formatted as a hyperlink if possible.
     *
     * @param url URL to be linked.
     * @param text Text to be displayed if hyperlinked.
     */
    void writeUrl(String url, String text);

    /**
     * Writes a line to the terminal followed by an end of line.
     * @param text Text to be written.
     */
    void writeLine(String text);

    /**
     * Writes a command usage message to the terminal.
     * @param command Command object.
     */
    void writeCommandHelp(Object command);

    /**
     * Writes a command usage message to the terminal.
     * @param command Command line object.
     */
    void writeCommandSynopsis(CommandLine command);

    /**
     * Renders a table model.
     * @param tableData Table data.
     * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
     */
    void writeTable(FormattedTableDto<?> tableData, boolean addBorder);

    /**
     * Writes a table dataset with a header that is extracted from the column names.
     * @param table Table (dataset).
     * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
     */
    void writeTable(Table table, boolean addBorder);

    /**
     * Renders a table using a given table model.
     * @param tableModel Table model for the spring shell table.
     * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
     */
    void writeTable(TableModel tableModel, boolean addBorder);

    /**
     * Clears the screen.
     */
    void clearScreen();

    /**
     * Gets terminal width.
     * @return Terminal width.
     */
    Integer getTerminalWidth();

    /**
     * Gets terminal height.
     * @return Terminal height.
     */
    Integer getTerminalHeight();
}
