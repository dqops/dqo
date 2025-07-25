/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.terminal;

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
