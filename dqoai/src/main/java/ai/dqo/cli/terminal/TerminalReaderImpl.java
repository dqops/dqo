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

import com.google.common.base.Strings;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tech.tablesaw.api.Table;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Terminal input reader used to ask the user to provide answers.
 */
@Component
public class TerminalReaderImpl implements TerminalReader {
    private final TerminalWriter writer;
    private final LineReader lineReader;

    @Autowired
    public TerminalReaderImpl(TerminalWriter writer, @Qualifier("cliLineReader") LineReader lineReader) {
        this.writer = writer;
        this.lineReader = lineReader;
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
            String line = lineReader.readLine(promptText);
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
     * Ask te user to provide a password that is masked.
     *
     * @param question Question that is asked, usually just a "password"
     * @param acceptEmpty When true and the user clicked enter, an empty string is accepted.
     * @return Password entered by the user.
     */
    @Override
    public String promptPassword(String question, boolean acceptEmpty) {
        while (true) {
            String line = lineReader.readLine(question + ": ", '*');
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
            String line = lineReader.readLine(prompt);
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
            String line = lineReader.readLine(question);
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
            String line = lineReader.readLine("Please enter one of the [] values: ");
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
            String line = lineReader.readLine("Please enter one of the [] values: ");
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
            String line = lineReader.readLine("Please enter one of the [] values: ");
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

    /**
     * Starts a background job that will wait for any input on the console.
     * @param waitDuration Wait duration.
     * @return Mono that returns true when any input appeared on the console (the user clicked any key). False or cancelled when no input appeared.
     */
    @Override
    public CompletableFuture<Boolean> waitForConsoleInput(Duration waitDuration) {
        CompletableFuture<Boolean> waitForAnyInputFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Terminal terminal = this.lineReader.getTerminal();

                long waitDurationMillis = waitDuration.toMillis();
                int readResult = terminal.reader().peek(waitDurationMillis);
                if (readResult <= 0) {
                    return false;
                }
                return true;
            } catch (IOException ioe) {
                return false;
            }
        });

        return waitForAnyInputFuture;
    }

    /**
     * Hangs on waiting for the user to confirm that the application should exit.
     *
     * @param startMessage Message to show before waiting for the user to confirm the exit.
     */
    @Override
    public void waitForExit(String startMessage) {
        this.writer.writeLine(startMessage);
        this.writer.writeLine("Press any key key to stop the application.");

        while (true) {
            Character character = this.tryReadChar(1000);
            if (character != null) {
                if (this.promptBoolean("Exit the application", false)) {
                    return;
                }
            }
        }
    }
}
