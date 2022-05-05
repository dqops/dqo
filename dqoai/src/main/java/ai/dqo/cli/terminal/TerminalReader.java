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

import tech.tablesaw.api.Table;

/**
 * Terminal input reader that prompts the user to enter information from the terminal and the command line.
 */
public interface TerminalReader {
    /**
     * Asks the user to answer a question.
     * @param question Question (prompt) that will be followed by ": "
     * @param defaultValue Default value that is returned when the answer is empty.
     * @param acceptNullDefault When true and the <code>defaultValue</code> is null then a null value is accepted.
     *                          When false and the <code>defaultValue</code> is null then asks the user again until a response is given.
     * @return User entered response.
     */
    String prompt(String question, String defaultValue, boolean acceptNullDefault);

    /**
     * Ask te user to provide a password that is masked.
     * @param question Question that is asked, usually just a "password"
     * @param acceptEmpty When true and the user clicked enter, an empty string is accepted.
     * @return Password entered by the user.
     */
    String promptPassword(String question, boolean acceptEmpty);

    /**
     * Asks the user to answer a Y/N question.
     * @param question Prompt (question)
     * @param defaultValue Default value that is returned when the answer is empty.
     * @param acceptNullDefault When true and the <code>defaultValue</code> is null then a null value is accepted.
     *                          When false and the <code>defaultValue</code> is null then asks the user again until a response is given.
     * @return User entered response.
     */
    Boolean promptBoolean(String question, boolean defaultValue, boolean acceptNullDefault);

    /**
     * Asks the user to pick a value from the enum list.
     * @param question Question to ask
     * @param enumType Enum type to pick a value from.
     * @param defaultValue The default value that is selected when an empty answer was given.
     * @param acceptNullDefault When true and the <code>defaultValue</code> is null then a null value is accepted.
     *                          When false and the <code>defaultValue</code> is null then asks the user again until a response is given.
     * @param <T> Enum type.
     * @return Selected enum value or the default value.
     */
    <T extends Enum<T>> T promptEnum(String question, Class<T> enumType, T defaultValue, boolean acceptNullDefault);

    /**
     * Shows a list of models (dtos) as a table and asks the user to pick one row.
     * @param question Question that is shown before the table.
     * @param tableData List of models.
     * @param defaultValue Default value (a row index).
     * @param acceptNullDefault When true and the <code>defaultValue</code> is null then a null value is accepted.
     *                          When false and the <code>defaultValue</code> is null then asks the user again until a response is given.
     * @return 0-based row index that was selected or the default value (may be null).
     */
    Integer pickDto(String question, FormattedTableDto<?> tableData, Integer defaultValue, boolean acceptNullDefault);

    /**
     * Shows a tablesaw table and asks the user to pick one row.
     * @param question Question that is shown before the table.
     * @param table Table (tablesaw) data.
     * @param defaultValue Default value (a row index).
     * @param acceptNullDefault When true and the <code>defaultValue</code> is null then a null value is accepted.
     *                          When false and the <code>defaultValue</code> is null then asks the user again until a response is given.
     * @return 0-based row index that was selected or the default value (may be null).
     */
    Integer pickTableRow(String question, Table table, Integer defaultValue, boolean acceptNullDefault);

    /**
     * Tries to read one character from the terminal.
     * @param timeoutMillis Read timeout.
     * @return Character that was read.
     */
    Character tryReadChar(long timeoutMillis);
}
