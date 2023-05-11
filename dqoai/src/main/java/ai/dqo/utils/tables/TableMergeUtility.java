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
package ai.dqo.utils.tables;

import ai.dqo.utils.exceptions.DqoRuntimeException;
import org.jetbrains.annotations.NotNull;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;
import tech.tablesaw.columns.Column;
import tech.tablesaw.selection.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Support methods that operate on in-memory tables and perform joins.
 */
public class TableMergeUtility {
    /**
     * Table merge utility. Creates a new table that contains all rows from the <code>currentResults</code> except for
     * matching rows that are in the <code>newResults</code>, but with new rows copied from <code>newResults</code>.
     * Duplication is detected by a join.
     * @param currentResults Current results.
     * @param newResults New results, must be the same schema as the current results table.
     * @param joinColumns Join column names to detect overwritten rows.
     * @return New table with all rows.
     */
    public static Table mergeNewResults(Table currentResults, Table newResults, String... joinColumns) {
        assert joinColumns.length > 0;

        if (joinColumns.length == 1 && tableSchemasEqual(currentResults, newResults)) {
            return mergeResultsSimple(currentResults, newResults, joinColumns[0]);
        }
        else {
            return mergeNewResultsOnMultipleColumns(currentResults, newResults, joinColumns);
        }
    }

    /**
     * Merges results between two tables. The result table contains all rows from the <code>currentResults</code>
     * that do not have a duplicate value (identified by the <code>joinColumn</code> column value) in the new results.
     * Also all results from the <code>newResults</code> are appended.
     * @param currentResults Current results (old rows).
     * @param newResults New rows that will be appended or will replace rows.
     * @param idColumName Column name used as an ID.
     * @return Merged results.
     */
    protected static Table mergeResultsSimple(Table currentResults, Table newResults, String idColumName) {
        Column<?> newResultIdColumn = newResults.column(idColumName);
        Table resultTable = null;

        Column<?> currentIdColumn = currentResults.column(idColumName);
        if (currentIdColumn instanceof TextColumn) {
            TextColumn currentIdColumnString = (TextColumn) currentIdColumn;
            List<String> idsInNewResultsStrings = ((TextColumn)newResultIdColumn).asList();
            Selection notInSelection = currentIdColumnString.isNotIn(idsInNewResultsStrings);

            if (notInSelection.size() < currentIdColumnString.size()) {
                resultTable = currentResults.where(notInSelection);
            }
            else {
                resultTable = currentResults.copy();
            }
        }
        else if (currentIdColumn instanceof LongColumn) {
            LongColumn currentIdColumnLong = (LongColumn) currentIdColumn;
            long[] idsInNewResultsLong = ((LongColumn)newResultIdColumn).asLongArray();
            Selection notInSelection = currentIdColumnLong.isNotIn(idsInNewResultsLong);

            if (notInSelection.size() < currentIdColumnLong.size()) {
                resultTable = currentResults.where(notInSelection);
            }
            else {
                resultTable = currentResults.copy();
            }
        } else if (currentIdColumn instanceof StringColumn) {
            StringColumn currentIdColumnString = (StringColumn) currentIdColumn;
            List<String> idsInNewResultsStrings = ((StringColumn)newResultIdColumn).asList();
            Selection notInSelection = currentIdColumnString.isNotIn(idsInNewResultsStrings);

            if (notInSelection.size() < currentIdColumnString.size()) {
                resultTable = currentResults.where(notInSelection);
            }
            else {
                resultTable = currentResults.copy();
            }
        } else {
            throw new DqoRuntimeException("Unsupported column type " + newResultIdColumn.type());
        }

        resultTable.append(newResults);

        return resultTable;
    }

    /**
     * Compares if both table schemas are equal: column names at the same index have the same name and type.
     * @param table1 First table for comparison.
     * @param table2 Second table for comparison.
     * @return True when table schemas match, false when there are differences.
     */
    protected static boolean tableSchemasEqual(Table table1, Table table2) {
        if (table1.columnCount() != table2.columnCount()) {
            return false;
        }

        for (int i = 0; i < table1.columnCount() ; i++) {
            Column<?> column1 = table1.column(i);
            Column<?> column2 = table2.column(i);
            if (!Objects.equals(column1.name(), column2.name())) {
                return false;
            }

            if (column1.type() != column2.type()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Merges new results on multiple columns.
     * @param currentResults Current results.
     * @param newResults New results.
     * @param joinColumns Join columns used for joining.
     * @return Joined result.
     */
    @NotNull
    protected static Table mergeNewResultsOnMultipleColumns(Table currentResults, Table newResults, String[] joinColumns) {
        List<Column<?>> newResultJoinColumns = new ArrayList<>();
        for (String joinColumnName : joinColumns) {
            newResultJoinColumns.add(newResults.column(joinColumnName).copy());
        }
        Table newResultsJoinColumnsTab = Table.create(newResults.name(), newResultJoinColumns);

        Table currentRowsJoined = currentResults.joinOn(joinColumns).leftOuter(newResultsJoinColumnsTab, true, true, joinColumns);
        Column<?> firstJoinColumnRightTable = currentRowsJoined.column(currentResults.columnCount()); // we take the first join column from the target that was added
        Table finalRows = currentRowsJoined.dropWhere(firstJoinColumnRightTable.isNotMissing());// dropping rows that have new versions
        int[] columnIndexesToRetain = IntStream.range(0, currentResults.columnCount()).toArray();
        finalRows = finalRows.retainColumns(columnIndexesToRetain);

        finalRows.append(newResults);
        return finalRows;
    }
}
