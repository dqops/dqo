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
