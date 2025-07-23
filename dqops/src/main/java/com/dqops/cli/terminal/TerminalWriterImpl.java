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

import com.dqops.cli.terminal.ansi.UrlFormatter;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;


/**
 * Console terminal wrapper. Provides access to the terminal services.
 */
public class TerminalWriterImpl extends TerminalWriterAbstract {
    private final Terminal terminal;
    private final UrlFormatter urlFormatter;

    public TerminalWriterImpl(Terminal terminal,
                              UrlFormatter urlFormatter) {
        this.terminal = terminal;
        this.urlFormatter = urlFormatter;
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
     * Writes a URL to the terminal, formatted as a hyperlink if possible.
     *
     * @param url URL to be linked.
     * @param text Text to be displayed if hyperlinked.
     */
    @Override
    public void writeUrl(String url, String text) {
        this.write(this.urlFormatter.getUrlAnsiString(url, text));
    }

    /**
     * Clears the screen.
     */
    @Override
    public void clearScreen() {
        this.terminal.puts(InfoCmp.Capability.clear_screen);
        this.terminal.flush();
    }

    /**
     * Gets terminal width.
     * @return Terminal width.
     */
    @Override
    public Integer getTerminalWidth() {
        Size size = this.terminal.getSize();
        if (size == null) {
            return 80;
        }
        return size.getColumns();
    }

    /**
     * Gets terminal height.
     * @return Terminal height.
     */
    @Override
    public Integer getTerminalHeight() {
        Size size = this.terminal.getSize();
        if (size == null) {
            return 50;
        }
        return size.getRows();
    }
}
