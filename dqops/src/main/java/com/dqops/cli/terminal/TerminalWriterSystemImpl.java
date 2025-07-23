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

/**
 * Console terminal wrapper. Provides access to the terminal services.
 * Implementation relying on the standard output (StdOut).
 */
public class TerminalWriterSystemImpl extends TerminalWriterAbstract {

    private Integer terminalWidth;

    public TerminalWriterSystemImpl(Integer terminalWidth) {
        this.terminalWidth = terminalWidth;
    }

    /**
     * Writes a text to the terminal without moving to the next line.
     * @param text Text to be written.
     */
    @Override
    public void write(String text) {
        if (text != null) {
            System.out.print(text);
            System.out.flush();
        }
    }

    /**
     * Writes a URL to the terminal, formatted as a hyperlink if possible.
     *
     * @param url URL to be linked.
     * @param text Text to be displayed if hyperlinked.
     */
    @Override
    public void writeUrl(String url, String text) {
        write(text + " {" + url + "}");
    }

    /**
     * Clears the screen.
     */
    public void clearScreen() {
    }

    /**
     * Gets terminal width.
     * @return Terminal width.
     */
    @Override
    public Integer getTerminalWidth() {
        return this.terminalWidth;
    }

    /**
     * Gets terminal height.
     * @return Terminal height.
     */
    public Integer getTerminalHeight() {
        return Integer.MAX_VALUE;
    }
}
