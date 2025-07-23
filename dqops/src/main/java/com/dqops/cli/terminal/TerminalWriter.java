/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.terminal;

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
