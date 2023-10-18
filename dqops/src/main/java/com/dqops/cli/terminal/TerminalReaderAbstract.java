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

import com.dqops.utils.exceptions.DqoRuntimeException;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import tech.tablesaw.api.Table;

import java.time.Duration;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Terminal input reader used to ask the user to provide answers.
 * Abstract implementation of the basic operational logic.
 */
@Slf4j
public abstract class TerminalReaderAbstract implements TerminalReader {
    private final TerminalWriter writer;

    public TerminalReaderAbstract(TerminalWriter writer) {
        this.writer = writer;
    }

    /**
     * Gets the terminal writer associated with this reader.
     * @return The terminal writer to which this reader writes.
     */
    protected TerminalWriter getWriter() {
        return this.writer;
    }

    /**
     * Read an input line from the user.
     * @param promptText Text to prompt the user with.
     * @return The line read from the user.
     */
    protected abstract String readLine(String promptText);

    /**
     * Read an input line from the user, hiding their input while writing.
     * @param promptText Text to prompt the user with.
     * @return The line read from the user.
     */
    protected abstract String readLineMasked(String promptText);

    /**
     * Tries to read one character from the terminal.
     *
     * @param timeoutMillis Read timeout.
     * @param peekOnly True when the method should only try to detect if there is any input within the timeout, without reading.
     * @return Character that was read.
     */
    @Override
    public abstract Character tryReadChar(long timeoutMillis, boolean peekOnly);

    /**
     * Starts a background job that will wait for any input on the console.
     * @param waitDuration Wait duration.
     * @param peekOnly True when the method should only try to detect if there is any input within the timeout, without reading.
     * @return Mono that returns true when any input appeared on the console (the user clicked any key). False or cancelled when no input appeared.
     */
    @Override
    public CompletableFuture<Boolean> waitForConsoleInput(Duration waitDuration, boolean peekOnly) {
        CompletableFuture<Boolean> inputReceivedCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Character c = this.tryReadChar(waitDuration.toMillis(), peekOnly);
            return c != null;
        });

        return inputReceivedCompletableFuture;
    }

    /**
     * Hangs on waiting for the user to confirm that the application should exit.
     *
     * @param startMessage Message to show before waiting for the user to confirm the exit.
     */
    @Override
    public void waitForExit(String startMessage) {
        if (startMessage != null) {
            this.getWriter().writeLine("");
            this.getWriter().writeLine(startMessage);
        }

        while (true) {
            String line = this.readLine("Press ENTER to stop the application.");
            if (this.promptBoolean("Exit the application", false)) {
                return;
            }
        }
    }

    /**
     * Hangs on waiting for the user to confirm that the application should exit.
     * Waits for up to <code>waitDuration</code>.
     *
     * @param startMessage Message to show before waiting for the user to confirm the exit.
     * @param waitDuration Wait duration. The method will return false when the timeout elapsed.
     * @return True - the user intentionally clicked any button to exit the application, false - the timeout elapsed.
     */
    @Override
    public boolean waitForExitWithTimeLimit(String startMessage, Duration waitDuration) {
        if (startMessage != null) {
            this.getWriter().writeLine("");
            this.getWriter().writeLine(startMessage);
            this.getWriter().writeLine("Press any key to stop the application.");
        }

        long startTimestamp = System.currentTimeMillis();
        CompletableFuture<Boolean> booleanCompletableFuture = this.waitForConsoleInput(waitDuration.plusSeconds(10L), true);
        try {
            Boolean wasExitedByUser = booleanCompletableFuture.get(waitDuration.toMillis(), TimeUnit.MILLISECONDS);
            if (!wasExitedByUser) {
                if (System.currentTimeMillis() < startTimestamp + 100) {
                    // special situation, running in Docker, with no access to stdin, so the stream was closed
                    throw new DqoRuntimeException("Failed to read from the input stream, console read finished too early");
                }
            }
            return wasExitedByUser;
        }
        catch (ExecutionException ex) {
            log.debug("Waiting for the user input before exit has failed: " + ex.getMessage(), ex);
            booleanCompletableFuture.cancel(true);
            throw new DqoRuntimeException("Failed to read from the input stream, message: " + ex.getMessage(), ex);
        }
        catch (InterruptedException | TimeoutException e) {
            log.debug("Waiting for the user input before exit was cancelled: " + e.getMessage(), e);
            booleanCompletableFuture.cancel(true);
            return false;
        }
    }

    /**
     * Asks the user to answer a question.
     *
     * @param question     Question (prompt) that will be followed by ": "
     * @param defaultValue Default value that is returned when the answer is empty.
     * @param acceptNullDefault When true and the <code>defaultValue</code> is null then a null value is accepted.
     *                          When false and the <code>defaultValue</code> is null then asks the user again until a response is given.
     * @return User entered response.
     */
    @Override
    public String prompt(String question, String defaultValue, boolean acceptNullDefault) {
        while (true) {
            String promptText = defaultValue == null ? question + ": " : question + " [" + defaultValue +  "]: ";
            String line = this.readLine(promptText);
            if (Strings.isNullOrEmpty(line)) {
                if (defaultValue != null || acceptNullDefault) {
                    return defaultValue;
                }
                else {
                    continue;
                }
            }
            return line;
        }
    }

    /**
     * Ask the user to provide a password that is masked.
     *
     * @param question Question that is asked, usually just a "password"
     * @param acceptEmpty When true and the user clicked enter, an empty string is accepted.
     * @return Password entered by the user.
     */
    @Override
    public String promptPassword(String question, boolean acceptEmpty) {
        while (true) {
            String line = this.readLineMasked(question + ": ");
            if (Strings.isNullOrEmpty(line) && !acceptEmpty) {
                continue;
            }
            return line;
        }
    }

    /**
     * Asks the user to answer a Y/N question.
     *
     * @param question       Prompt (question)
     * @param defaultValue Default value that is returned when the answer is empty.
     * @return User entered response.
     */
    @Override
    public Boolean promptBoolean(String question, boolean defaultValue) {
        StringBuilder sb = new StringBuilder();
        sb.append(question);
        sb.append(' ');
        sb.append('[');
        sb.append(defaultValue ? 'Y' : 'y');
        sb.append(',');
        sb.append(!defaultValue ? 'N' : 'n');
        sb.append(']');
        sb.append(": ");
        String prompt = sb.toString();

        while (true) {
            String line = this.readLine(prompt);
            if (Strings.isNullOrEmpty(line)) {
                return defaultValue;
            }

            String lowerCaseLine = line.toLowerCase(Locale.ROOT);
            if(Objects.equals(lowerCaseLine, "y") || Objects.equals(lowerCaseLine, "yes") ||
                    Objects.equals(lowerCaseLine, "true") || Objects.equals(lowerCaseLine, "t")) {
                return true;
            }

            if(Objects.equals(lowerCaseLine, "n") || Objects.equals(lowerCaseLine, "no") ||
                    Objects.equals(lowerCaseLine, "false") || Objects.equals(lowerCaseLine, "f")) {
                return false;
            }
        }
    }

    /**
     * Asks the user to answer a multiple choice question.
     * @param question Prompt (question)
     * @param defaultValue Default value that is returned when the answer is empty.
     * @param acceptNullDefault When true and the <code>defaultValue</code> is null then a null value is accepted.
     *                          When false and the <code>defaultValue</code> is null then asks the user again until a response is given.
     * @return User entered response.
     */
    @Override
    public Character promptChar(String question, Character defaultValue, boolean acceptNullDefault) {
        while (true) {
            String line = this.readLine(question);
            if (Strings.isNullOrEmpty(line)) {
                if (defaultValue != null || acceptNullDefault) {
                    return defaultValue;
                }
                else {
                    continue;
                }
            }

            return line.charAt(0);
        }
    }

    /**
     * Asks the user to pick a value from the enum list.
     *
     * @param question     Question to ask
     * @param enumType     Enum type to pick a value from.
     * @param defaultValue The default value that is selected when an empty answer was given.
     * @param acceptNullDefault When true and the <code>defaultValue</code> is null then a null value is accepted.
     *                          When false and the <code>defaultValue</code> is null then asks the user again until a response is given.
     * @return Selected enum value or the default value.
     */
    @Override
    public <T extends Enum<T>> T promptEnum(String question, Class<T> enumType, T defaultValue, boolean acceptNullDefault) {
        StringBuilder sb = new StringBuilder();
        sb.append(question);
        if (defaultValue != null) {
            sb.append(" [");
            sb.append(defaultValue);
            sb.append("]");
        }
        sb.append(":\n");
        T[] enumConstants = enumType.getEnumConstants();
        for (int i = 0; i < enumConstants.length; i++) {
            sb.append(String.format(" [%2d] %s%s\n", i+1, enumConstants[i].toString(), Objects.equals(enumConstants[i], defaultValue) ? " (default)" : ""));
        }
        String beforePrompt = sb.toString();

        while (true) {
			this.writer.write(beforePrompt);
            String line = this.readLine("Please enter one of the [] values: ");
            if (Strings.isNullOrEmpty(line)) {
                if (defaultValue != null || acceptNullDefault) {
                    return defaultValue;
                }
                else {
                    continue;
                }
            }

            try {
                int pickedNumber = Integer.parseInt(line.trim());
                if (pickedNumber <= 0 || pickedNumber > enumConstants.length) {
					this.writer.write(String.format("Please enter a number between 1 .. %d", enumConstants.length));
                }
                else {
                    return enumConstants[pickedNumber - 1];
                }
            } catch (NumberFormatException nfe) {
				this.writer.write(String.format("Please enter a number between 1 .. %d", enumConstants.length));
            }
        }
    }

    /**
     * Shows a list of models (dtos) as a table and asks the user to pick one row.
     * @param question Question that is shown before the table.
     * @param tableData List of models.
     * @param defaultValue Default value (a row index).
     * @param acceptNullDefault When true and the <code>defaultValue</code> is null then a null value is accepted.
     *                          When false and the <code>defaultValue</code> is null then asks the user again until a response is given.
     * @return 0-based row index that was selected or the default value (may be null).
     */
    public Integer pickDto(String question, FormattedTableDto<?> tableData, Integer defaultValue, boolean acceptNullDefault) {
        while (true) {
			this.writer.writeLine(question);
			this.writer.writeTable(tableData, false);
            String line = this.readLine("Please enter one of the [] values: ");
            if (Strings.isNullOrEmpty(line)) {
                if (defaultValue != null || acceptNullDefault) {
                    return defaultValue;
                }
                else {
                    continue;
                }
            }

            int rowCount = tableData.getRows().size();
            try {
                int pickedNumber = Integer.parseInt(line.trim());
                if (pickedNumber <= 0 || pickedNumber > rowCount) {
					this.writer.write(String.format("Please enter a number between 1 .. %d", rowCount));
                }
                else {
                    return pickedNumber - 1;  // WATCH OUT: the user picks a 1-based row index, so the user picks [1] to get the very first row, but rows are 0-based indexed and we will return 0 as the selected row index
                }
            } catch (NumberFormatException nfe) {
				this.writer.write(String.format("Please enter a number between 1 .. %d", rowCount));
            }
        }
    }

    /**
     * Shows a tablesaw table and asks the user to pick one row.
     * @param question Question that is shown before the table.
     * @param table Table (tablesaw) data.
     * @param defaultValue Default value (a row index).
     * @param acceptNullDefault When true and the <code>defaultValue</code> is null then a null value is accepted.
     *                          When false and the <code>defaultValue</code> is null then asks the user again until a response is given.
     * @return 0-based row index that was selected or the default value (may be null).
     */
    public Integer pickTableRow(String question, Table table, Integer defaultValue, boolean acceptNullDefault) {
        RowSelectionTableModel tableModel = new RowSelectionTableModel(table);

        while (true) {
			this.writer.writeLine(question);
			this.writer.writeTable(tableModel, false);
            String line = this.readLine("Please enter one of the [] values: ");
            if (Strings.isNullOrEmpty(line)) {
                if (defaultValue != null || acceptNullDefault) {
                    return defaultValue;
                }
                else {
                    continue;
                }
            }

            int rowCount = table.rowCount();
            try {
                int pickedNumber = Integer.parseInt(line.trim());
                if (pickedNumber <= 0 || pickedNumber > rowCount) {
					this.writer.write(String.format("Please enter a number between 1 .. %d", rowCount));
                }
                else {
                    return pickedNumber - 1;  // WATCH OUT: the user picks a 1-based row index, so the user picks [1] to get the very first row, but rows are 0-based indexed and we will return 0 as the selected row index
                }
            } catch (NumberFormatException nfe) {
				this.writer.write(String.format("Please enter a number between 1 .. %d", rowCount));
            }
        }
    }
}
