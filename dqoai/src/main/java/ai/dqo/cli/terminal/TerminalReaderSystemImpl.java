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

import java.io.Console;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TerminalReaderSystemImpl extends TerminalReaderAbstract {

    private Scanner scanner;

    public TerminalReaderSystemImpl(TerminalWriter writer) {
        super(writer);
        this.scanner = new Scanner(System.in);
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
        return this.scanner.nextLine();
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
     * Always returns null.
     *
     * @param timeoutMillis Read timeout.
     * @return Returns null, as system terminal cannot read characters in this manner.
     */
    @Override
    public Character tryReadChar(long timeoutMillis) {
        return null;
    }

    /**
     * Starts a background job that will wait the whole duration and always return false.
     *
     * @param waitDuration Wait duration.
     * @return Mono that always returns false (no input on console).
     */
    @Override
    public CompletableFuture<Boolean> waitForConsoleInput(Duration waitDuration) {
        CompletableFuture<Boolean> waitForSomeTime = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(waitDuration.toMillis());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;
        });
        return waitForSomeTime;
    }

    /**
     * Hangs on waiting for the user to confirm that the application should exit.
     * Does nothing.
     *
     * @param startMessage Ignored
     */
    @Override
    public void waitForExit(String startMessage) {
        // Do nothing
    }

    /**
     * Hangs on waiting for the user to confirm that the application should exit.
     * Waits for up to <code>waitDuration</code>.
     * Always false.
     *
     * @param startMessage Ignored.
     * @param waitDuration Ignored.
     * @return False - the timeout elapsed, as no input could be possibly detected on console.
     */
    @Override
    public boolean waitForExitWithTimeLimit(String startMessage, Duration waitDuration) {
        this.getWriter().writeLine(startMessage);
        this.getWriter().writeLine("Press any key to stop the application.");

        CompletableFuture<Boolean> booleanCompletableFuture = this.waitForConsoleInput(waitDuration.plusSeconds(10L));
        try {
            Boolean wasExitedByUser = booleanCompletableFuture.get(waitDuration.toMillis(), TimeUnit.MILLISECONDS);
            return wasExitedByUser;
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            return false;
        }
    }
}
