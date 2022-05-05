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

import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.util.ArrayList;
import java.util.List;
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
