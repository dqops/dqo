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

package ai.dqo.execution.sqltemplates.grouping;

import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * SQL query component parser that analyzes queries and finds similar fragments.
 */
@Component
public class SqlQueryFragmentsParserImpl implements SqlQueryFragmentsParser {
    @Autowired
    public SqlQueryFragmentsParserImpl() {
    }

    /**
     * Parses a query into SQL query components.
     * @param sql SQL query to divide.
     * @param actualValueColumnName The name of the actual_value column name (if it is different than the default actual_value).
     * @param expectedValueColumnName The name of the expected_value column name (if it is different than the default expected_value).
     * @return Query parsed into query fragment components.
     */
    @Override
    public FragmentedSqlQuery parseQueryToComponents(String sql, String actualValueColumnName, String expectedValueColumnName) {
        if (!sql.contains(actualValueColumnName)) {
            actualValueColumnName = SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME;
        }

        if (!sql.contains(expectedValueColumnName)) {
            expectedValueColumnName = SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME;
        }

        int actualValueUsagesCount = StringUtils.countMatches(sql, actualValueColumnName);
        if (actualValueUsagesCount != 1) {
            return FragmentedSqlQuery.createNotGroupableQuery(sql);
        }

        int expectedValueUsagesCount = StringUtils.countMatches(sql, expectedValueColumnName);
        if (expectedValueUsagesCount > 1) {
            return FragmentedSqlQuery.createNotGroupableQuery(sql);
        }

        List<String> listOfLines = sql.lines().collect(Collectors.toList());
        int indexIfSelect = findIndexOfLineEqualsText(listOfLines, 0, "SELECT");
        if (indexIfSelect < 0) {
            return FragmentedSqlQuery.createNotGroupableQuery(sql);
        }

        int indexOfActualValueAlias = findIndexOfLineHavingText(listOfLines, indexIfSelect + 1, actualValueColumnName);
        int indexOfExpectedValueAlias = findIndexOfLineHavingText(listOfLines, indexIfSelect + 1, expectedValueColumnName);
        if (indexOfExpectedValueAlias > 0 && indexOfExpectedValueAlias < indexOfActualValueAlias) {
            return FragmentedSqlQuery.createNotGroupableQuery(sql);
        }

        FragmentedSqlQuery componentList = new FragmentedSqlQuery(sql);
        componentList.setActualValueAlias(actualValueColumnName);
        componentList.setExpectedValueAlias(expectedValueColumnName);

        componentList.add(new SqlQueryFragment(SqlQueryFragmentType.STATIC_FRAGMENT,
                combineLines(listOfLines, 0, indexIfSelect + 1, true)));
        componentList.add(new SqlQueryFragment(SqlQueryFragmentType.ACTUAL_VALUE,
                combineLines(listOfLines, indexIfSelect + 1, indexOfActualValueAlias + 1, true)));
        if (indexOfExpectedValueAlias > 0) {
            componentList.add(new SqlQueryFragment(SqlQueryFragmentType.EXPECTED_VALUE,
                    combineLines(listOfLines, indexOfActualValueAlias + 1, indexOfExpectedValueAlias + 1, true)));
            componentList.add(new SqlQueryFragment(SqlQueryFragmentType.STATIC_FRAGMENT,
                    combineLines(listOfLines, indexOfExpectedValueAlias + 1, listOfLines.size(), false)));
        }
        else {
            componentList.add(new SqlQueryFragment(SqlQueryFragmentType.STATIC_FRAGMENT,
                    combineLines(listOfLines, indexOfActualValueAlias + 1, listOfLines.size(), false)));
        }

        return componentList;
    }

    /**
     * Finds an index of a line that is equal to a given text (the whole line). The search is done from the start of the list to the end.
     * @param lines List of lines.
     * @param startAtLine The index (0-based) of the first line to compare.
     * @param expectedText Expected text to appear on the line.
     * @return Index of the line where the text was found or -1 when there are no occurrences.
     */
    protected int findIndexOfLineEqualsText(List<String> lines, int startAtLine, String expectedText) {
        for (int i = startAtLine; i < lines.size(); i++) {
            String line = lines.get(i);
            if (Objects.equals(line, expectedText) || Objects.equals(line.trim(), expectedText)) {
                return i;
            }
        }

        return -1; // not found
    }

    /**
     * Finds an index of a line that contains the given text. The search is done from the end of the list (the last line), inspecting the list from the back.
     * @param lines List of lines.
     * @param startAtLine The index (0-based) of the first line to compare.
     * @param expectedText Expected text to appear on the line.
     * @return Index of the line where the text was found or -1 when there are no occurrences.
     */
    protected int findIndexOfLineHavingText(List<String> lines, int startAtLine, String expectedText) {
        for (int i = lines.size() - 1; i >= startAtLine; i--) {
            String line = lines.get(i);
            if (line.contains(expectedText)) {
                return i;
            }
        }

        return -1; // not found
    }

    /**
     * Combines all lines in the list, starting from the line at the index <code>firstLineIndex</code> (inclusive),
     * ending at the line at the index <code>lastLineIndex</code> (exclusive).
     * @param lines List of lines to combine.
     * @param firstLineIndex The first index (inclusive).
     * @param lastLineIndex The last index (exclusive).
     * @param eolAfterLastLine True when an end-of-line sequence should be added after the last line.
     * @return Combined text with line endings between combined lines.
     */
    protected String combineLines(List<String> lines, int firstLineIndex, int lastLineIndex, boolean eolAfterLastLine) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = firstLineIndex; i < lastLineIndex; i++) {
            if (i != firstLineIndex) {
                stringBuilder.append('\n');
            }
            stringBuilder.append(lines.get(i));
        }
        if (eolAfterLastLine) {
            stringBuilder.append('\n');
        }
        String combinedText = stringBuilder.toString();
        return combinedText;
    }
}
