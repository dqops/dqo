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

import com.dqops.utils.exceptions.DqoRuntimeException;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Terminal input reader used to ask the user to provide answers.
 */
public class TerminalReaderImpl extends TerminalReaderAbstract {
    private final LineReader lineReader;

    public TerminalReaderImpl(TerminalWriter writer, LineReader lineReader) {
        super(writer);
        this.lineReader = lineReader;
    }

    /**
     * Read an input line from the user.
     *
     * @param promptText Text to prompt the user with.
     * @return The line read from the user.
     */
    @Override
    protected String readLine(String promptText) {
        return this.lineReader.readLine(promptText);
    }

    /**
     * Read an input line from the user, hiding their input while writing.
     *
     * @param promptText Text to prompt the user with.
     * @return The line read from the user.
     */
    @Override
    protected String readLineMasked(String promptText) {
        return this.lineReader.readLine(promptText, '*');
    }

    /**
     * Tries to read one character from the terminal.
     *
     * @param timeoutMillis Read timeout.
     * @param peekOnly True when the method should only try to detect if there is any input within the timeout, without reading.
     * @return Character that was read.
     */
    @Override
    public Character tryReadChar(long timeoutMillis, boolean peekOnly) {
        long startMillis = System.currentTimeMillis();

        while (startMillis + timeoutMillis > System.currentTimeMillis()) {
            try {
                Terminal terminal = this.lineReader.getTerminal();

                int readResult = terminal.reader().peek(0);
                if (readResult <= 0) {
                    try {
                        Thread.sleep(Math.min(100, timeoutMillis));
                    }
                    catch (InterruptedException ex) {
                        throw new DqoRuntimeException("Waiting for input interrupted, message: " + ex.getMessage(), ex);
                    }
                    continue;
                }

                if (peekOnly || Thread.currentThread().isInterrupted()) {
                    return null;
                }

                return (char) terminal.reader().read();
            } catch (IOException ioe) {
                return null;
            }
        }

        return null;
    }
}
