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

import org.springframework.shell.table.TableModel;
import tech.tablesaw.api.Table;

/**
 * Spring shell table printer model that exposes a dataset as a table model for generating ascii tables.
 */
public class TablesawDatasetTableModel extends TableModel {
    private final Table table;

    /**
     * Creates a table model given a tablesaw dataset (table).
     * @param table Dataset.
     */
    public TablesawDatasetTableModel(Table table) {
        this.table = table;
    }

    /**
     * Returns a number of rendered columns. An extra header row is added.
     * @return Row count plus the header row.
     */
    @Override
    public int getRowCount() {
        return this.table.rowCount() + 1; // plus one for the virtual header row
    }

    /**
     * Gets the number of columns.
     * @return Number of columns.
     */
    @Override
    public int getColumnCount() {
        return this.table.columnCount();
    }

    /**
     * Retrieves a row/column value pair.
     * @param row the row that is being queried
     * @param column the column that is being queried
     * @return the data value to be displayed at a given row and column, which may be null.
     */
    @Override
    public Object getValue(int row, int column) {
        if (row == 0) {
            return this.table.column(column).name();
        }

        return this.table.get(row - 1, column);
    }
}
