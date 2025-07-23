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

import com.dqops.utils.threading.CompletableFutureRunner;

import java.io.Console;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TerminalReaderSystemImpl extends TerminalReaderAbstract {

    public TerminalReaderSystemImpl(TerminalWriter writer) {
        super(writer);
    }

    /**
     * Read an input line from the user.
     *
     * @param promptText Text to prompt the user with.
     * @return The line read from the user.
     */
    @Override
    protected String readLine(String promptText) {
        this.getWriter().write(promptText);
        try {
            Scanner s = new Scanner(System.in);
            return s.nextLine();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Read an input line from the user, hiding their input while writing.
     *
     * @param promptText Text to prompt the user with.
     * @return The line read from the user.
     */
    @Override
    protected String readLineMasked(String promptText) {
        Console console = System.console();
        if (console == null) {
            // Console will be null if run from IDE. In terminal, it should be masked fine. (native istty() method)
            return this.readLine(promptText);
        }

        // Blank user input instead of '*'.
        return new String(console.readPassword(promptText));
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
        CompletableFuture<Character> waitForInput = CompletableFutureRunner.supplyAsync(() -> {
            try {
                PushbackInputStream pushbackInputStream = (PushbackInputStream) System.in;
                int readResult = pushbackInputStream.read();
                if (readResult == -1) {
                    return null;
                }

                if (peekOnly || Thread.currentThread().isInterrupted()) {
                    pushbackInputStream.unread(readResult);
                }
                return (char)readResult;
            } catch (IOException e) {
                throw new RuntimeException("Failed to access the standard input stream: " + e.getMessage(), e);
            }
        });

        try {
            Character inputCharacter = waitForInput.get(timeoutMillis, TimeUnit.MILLISECONDS);
            return inputCharacter;
        } catch (TimeoutException e) {
            waitForInput.cancel(true);
            return null;
        } catch (InterruptedException | ExecutionException e) {
            waitForInput.cancel(true);
            throw new RuntimeException(e);
        }
    }
}
