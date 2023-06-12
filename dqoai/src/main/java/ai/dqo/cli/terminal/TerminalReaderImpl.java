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
     * @return Character that was read.
     */
    @Override
    public Character tryReadChar(long timeoutMillis) {
        try {
            Terminal terminal = this.lineReader.getTerminal();

            int readResult = terminal.reader().peek(timeoutMillis);
            if (readResult <= 0) {
                return null;
            }
            return (char)terminal.reader().read();
        }
        catch (IOException ioe) {
            return null;
        }
    }
}
