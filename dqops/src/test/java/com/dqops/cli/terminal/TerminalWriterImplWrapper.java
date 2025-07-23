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

import lombok.Setter;
import org.springframework.shell.table.TableModel;
import picocli.CommandLine;
import tech.tablesaw.api.Table;


/**
 * TerminalWriter wrapper. Provides a written text to the writer.
 */
public class TerminalWriterImplWrapper implements TerminalWriter {

    private final TerminalWriter terminalWriter;
    private String writtenText;

    @Setter
    private Integer terminalHeight;

    @Setter
    private Integer terminalWidth;

    public TerminalWriterImplWrapper(TerminalWriter terminalWriter) {
        this.terminalWriter = terminalWriter;
        writtenText = "";
    }

    public String getWrittenText(){
        return writtenText;
    }

    /**
     * Writes a text to the terminal without moving to the next line.
     * @param text Text to be written.
     */
    @Override
    public void write(String text) {
        writtenText += text;
		terminalWriter.write(text);
    }

    /**
     * Writes a URL to the terminal, formatted as a hyperlink if possible.
     *
     * @param url URL to be linked.
     * @param text Text to be displayed if hyperlinked.
     */
    @Override
    public void writeUrl(String url, String text) {
        terminalWriter.writeUrl(url, text);
    }



    /**
     * Clears the screen.
     */
    @Override
    public void clearScreen() {
        terminalWriter.clearScreen();
    }

    /**
     * Gets terminal width.
     * @return Terminal width.
     */
    @Override
    public Integer getTerminalWidth() {
        if(terminalWidth != null){
            return terminalWidth;
        }
        int width = terminalWriter.getTerminalWidth();
        return width;
    }

    /**
     * Gets terminal height.
     * @return Terminal height.
     */
    @Override
    public Integer getTerminalHeight() {
        if(terminalHeight != null){
            return terminalHeight;
        }
        int height = terminalWriter.getTerminalHeight();
        return height;
    }

    @Override
    public void writeLine(String text) {
        terminalWriter.writeLine(text);
    }

    @Override
    public void writeCommandHelp(Object command) {
        terminalWriter.writeCommandHelp(command);
    }

    @Override
    public void writeCommandSynopsis(CommandLine command) {
        terminalWriter.writeCommandSynopsis(command);
    }

    @Override
    public void writeTable(FormattedTableDto<?> tableData, boolean addBorder) {
        terminalWriter.writeTable(tableData, addBorder);
    }

    @Override
    public void writeTable(Table table, boolean addBorder) {
        terminalWriter.writeTable(table, addBorder);
    }

    @Override
    public void writeTable(TableModel tableModel, boolean addBorder) {
        terminalWriter.writeTable(tableModel, addBorder);
    }

}
