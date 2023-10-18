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
        CompletableFuture<Character> waitForInput = CompletableFuture.supplyAsync(() -> {
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
