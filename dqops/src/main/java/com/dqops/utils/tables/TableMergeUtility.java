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
package com.dqops.utils.tables;

import com.dqops.data.normalization.CommonColumnNames;
import com.dqops.utils.exceptions.DqoRuntimeException;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jetbrains.annotations.NotNull;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;
import tech.tablesaw.selection.Selection;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
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
    public static Table mergeNewResults(Table currentResults,
                                        Table newResults,
                                        String[] joinColumns,
                                        String userName,
                                        String[] columnNamesToCopy) {
        assert joinColumns.length > 0;

        if (!tableSchemasEqual(currentResults, newResults)) {
            currentResults = recreateCurrentTableWithDifferentColumns(currentResults, newResults.columns());
        }

        if (joinColumns.length == 1 && tableSchemasEqual(currentResults, newResults)) {
            return mergeResultsSimple(currentResults, newResults, joinColumns[0], userName, columnNamesToCopy);
        }
        else {
            return mergeNewResultsOnMultipleColumns(currentResults, newResults, joinColumns);
        }
    }

    /**
     * Creates a new version of the current results, but having all expected columns.
     * @param sourceTable The source table to recreate.
     * @param expectedColumns A list of expected columns, we use the column names and column types, we do not copy values from them.
     * @return A new instance of a table that has all expected columns from the current results.
     */
    protected static Table recreateCurrentTableWithDifferentColumns(Table sourceTable, List<Column<?>> expectedColumns) {
        Map<String, ? extends Column<?>> currentColumnsByColumnName = sourceTable.columns().stream()
                .collect(Collectors.toMap(
                        c -> c.name(),
                        c -> c,
                        (key, value) -> value,
                        LinkedHashMap::new));

        List<Column<?>> newColumns = new ArrayList<>();
        for (Column<?> expectedColumn : expectedColumns) {
            String columnName = expectedColumn.name();
            Column<?> columnFromSourceTable = currentColumnsByColumnName.get(columnName);
            if (columnFromSourceTable != null) {
                newColumns.add(columnFromSourceTable);
            } else {
                newColumns.add(expectedColumn.emptyCopy(sourceTable.rowCount()));
            }
        }

        return Table.create(sourceTable.name(), newColumns);
    }

    /**
     * Merges results between two tables. The result table contains all rows from the <code>currentResults</code>
     * that do not have a duplicate value (identified by the <code>joinColumn</code> column value) in the new results.
     * Also all results from the <code>newResults</code> are appended.
     * @param currentResults Current results (old rows).
     * @param newResults New rows that will be appended or will replace rows.
     * @param idColumName Column name used as an ID.
     * @param userName User name that will be saved in the created_by field for new rows, or in the updated_by column for updated rows.
     * @return Merged results.
     */
    protected static Table mergeResultsSimple(Table currentResults,
                                              Table newResults,
                                              String idColumName,
                                              String userName,
                                              String[] copiedColumnNames) {
        Column<?> newResultIdColumn = newResults.column(idColumName);
        Instant now = Instant.now();
//        InstantColumn currentResultsCreatedAtColumn = currentResults.instantColumn(CommonColumnNames.CREATED_AT_COLUMN_NAME);
//        TextColumn currentResultsCreatedByColumn = currentResults.textColumn(CommonColumnNames.CREATED_BY_COLUMN_NAME);
        InstantColumn newResultsCreatedAtColumn = newResults.instantColumn(CommonColumnNames.CREATED_AT_COLUMN_NAME);
        InstantColumn newResultsUpdatedAtColumn = newResults.instantColumn(CommonColumnNames.UPDATED_AT_COLUMN_NAME);
        TextColumn newResultsUpdatedByColumn = newResults.textColumn(CommonColumnNames.UPDATED_BY_COLUMN_NAME);

        Table resultTable = null;

        Column<?> currentIdColumn = currentResults.column(idColumName);
        Selection newResultsThatAreUpdates = null;
        Map<Object, Integer> currentKeysToRowIndexesInCurrentData = null;

        if (currentIdColumn instanceof TextColumn) {
            TextColumn currentIdColumnString = (TextColumn) currentIdColumn;
            currentKeysToRowIndexesInCurrentData = TableColumnUtility.mapValuesToRowIndexes(currentIdColumnString);
            Set<String> idsInCurrentResults = currentIdColumnString.asSet();
            TextColumn newResultIdTextColumn = (TextColumn) newResultIdColumn;
            List<String> idsInNewResultsStrings = newResultIdTextColumn.asList();
            Selection notInSelection = currentIdColumnString.isNotIn(idsInNewResultsStrings);

            if (notInSelection.size() < currentIdColumnString.size()) {
                resultTable = currentResults.where(notInSelection);
                newResultsThatAreUpdates = newResultIdTextColumn.isIn(idsInCurrentResults);
            }
            else {
                resultTable = currentResults.copy();
            }
        }
        else if (currentIdColumn instanceof LongColumn) {
            LongColumn currentIdColumnLong = (LongColumn) currentIdColumn;
            LongColumn newResultsIdColumnLong = (LongColumn) newResultIdColumn;
            currentKeysToRowIndexesInCurrentData = TableColumnUtility.mapValuesToRowIndexes(currentIdColumnLong);
            Set<Number> idsInCurrentResultsLong = (Set<Number>)(Set<?>)currentIdColumnLong.asSet();
            long[] idsInNewResultsLong = newResultsIdColumnLong.asLongArray();
            Selection notInSelection = currentIdColumnLong.isNotIn(idsInNewResultsLong);

            if (notInSelection.size() < currentIdColumnLong.size()) {
                resultTable = currentResults.where(notInSelection);
                newResultsThatAreUpdates = newResultsIdColumnLong.isIn(idsInCurrentResultsLong);
            }
            else {
                resultTable = currentResults.copy();
            }
        } else {
            throw new DqoRuntimeException("Unsupported column type " + newResultIdColumn.type());
        }

        if (newResultsThatAreUpdates != null) {
            newResultsUpdatedAtColumn.set(newResultsThatAreUpdates, now);
            newResultsUpdatedByColumn.set(newResultsThatAreUpdates, userName);

            for (String copiedColumnName : copiedColumnNames) {
                Column currentColumnSource = TableColumnUtility.findColumn(currentResults, copiedColumnName);
                if (currentColumnSource == null) {
                    continue; // column missing
                }

                Column newColumnTarget = newResults.column(copiedColumnName);

                for (Integer rowIndexNewData : newResultsThatAreUpdates) {
                    Object key = newResultIdColumn.get(rowIndexNewData);
                    Integer rowIndexOldData = currentKeysToRowIndexesInCurrentData.get(key);

                    if (!currentColumnSource.isMissing(rowIndexOldData) && newColumnTarget.isMissing(rowIndexNewData)) {
                        Object oldValueCopied = currentColumnSource.get(rowIndexOldData);
                        //noinspection unchecked
                        newColumnTarget.set(rowIndexNewData, oldValueCopied);
                    }
                }
            }
        }

        Selection newCreatedRows = newResultsUpdatedAtColumn.isMissing();
        newResultsCreatedAtColumn.set(newCreatedRows, now);
        newResultsUpdatedByColumn.set(newCreatedRows, userName);
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
    protected static Table mergeNewResultsOnMultipleColumns(Table currentResults,
                                                            Table newResults,
                                                            String[] joinColumns) {
        List<Column<?>> newResultJoinColumns = new ArrayList<>();
        for (String joinColumnName : joinColumns) {
            newResultJoinColumns.add(newResults.column(joinColumnName).copy());
        }
        Table newResultsJoinColumnsTab = Table.create(newResults.name(), newResultJoinColumns);

        Table currentRowsJoined = currentResults.joinOn(joinColumns).leftOuter(newResultsJoinColumnsTab, true, true, joinColumns);
        Column<?> firstJoinColumnRightTable = currentRowsJoined.column(currentResults.columnCount()); // we take the first join column from the target that was added

        // TODO: before dropping updated rows, we must copy values, we must also fill the created_* and updated_* columns
        Table finalRows = currentRowsJoined.dropWhere(firstJoinColumnRightTable.isNotMissing());// dropping rows that have new versions
        int[] columnIndexesToRetain = IntStream.range(0, currentResults.columnCount()).toArray();
        finalRows = finalRows.retainColumns(columnIndexesToRetain);

        finalRows.append(newResults);
        return finalRows;
    }
}
