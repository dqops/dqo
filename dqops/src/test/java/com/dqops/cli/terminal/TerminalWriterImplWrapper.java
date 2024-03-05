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

import org.springframework.shell.table.TableModel;
import picocli.CommandLine;
import tech.tablesaw.api.Table;


/**
 * TerminalWriter wrapper. Provides a written text to the writer.
 */
public class TerminalWriterImplWrapper implements TerminalWriter {

    private final TerminalWriter terminalWriter;
    private String writtenText;

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
        int width = terminalWriter.getTerminalWidth();
        if (width <= 0) {
            return 80;
        }
        return width;
    }

    /**
     * Gets terminal height.
     * @return Terminal height.
     */
    @Override
    public Integer getTerminalHeight() {
        int height = terminalWriter.getTerminalHeight();
        if (height <= 0) {
            return 50;
        }
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
