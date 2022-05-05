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

import org.springframework.shell.table.TableModel;
import tech.tablesaw.api.Table;

/**
 * Table model rendered for an ascii table that uses a tablesaw table as a data source.
 * The output model has an extra first column which is a 1-based row index in brackets, like [1], [2], [3].
 * This table model is used to show a list of rows to the user and ask the user to pick one value (like a CLI combo box).
 */
public class RowSelectionTableModel extends TableModel {
    private final Table table;

    /**
     * Creates a table model given a tablesaw dataset (table).
     * @param table Dataset.
     */
    public RowSelectionTableModel(Table table) {
        this.table = table;
    }

    /**
     * Returns a number of rendered columns. An extra header row is added.
     * @return Row count plus the header row.
     */
    @Override
    public int getRowCount() {
        return this.table.rowCount();
    }

    /**
     * Gets the number of columns.
     * @return Number of columns.
     */
    @Override
    public int getColumnCount() {
        return this.table.columnCount() + 1; // an extra selection column
    }

    /**
     * Retrieves a row/column value pair.
     * @param row the row that is being queried
     * @param column the column that is being queried
     * @return the data value to be displayed at a given row and column, which may be null.
     */
    @Override
    public Object getValue(int row, int column) {
        if (column == 0) {
            int digits = 1;
            if (this.getRowCount() > 99) {
                digits = 3;
            }
            else if (this.getRowCount() > 9 && this.getRowCount() <= 99) {
                digits = 2;
            }

            return "\u00A0 [" + String.format("%" + digits + "s", row + 1) + "] \u00A0";   // \\u00A0 is a non-breaking space to avoid trimming
        }

        return this.table.get(row, column - 1);
    }
}
