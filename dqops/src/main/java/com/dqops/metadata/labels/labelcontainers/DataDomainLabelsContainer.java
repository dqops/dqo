/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.labels.labelcontainers;

import com.dqops.metadata.sources.PhysicalTableName;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * A global container of all labels, collected from all levels (connection, table, column) for one data domain.
 */
public class DataDomainLabelsContainer {
    private final LabelCountContainer connectionLabels = new LabelCountContainer();
    private final LabelCountContainer tableLabels = new LabelCountContainer();
    private final LabelCountContainer columnLabels = new LabelCountContainer();

    private final HashMap<String, LabelCountContainer> importedConnectionLabels = new LinkedHashMap<>();
    private final HashMap<TableLabelsKey, LabelCountContainer> importedTableLabels = new LinkedHashMap<>();
    private final HashMap<TableLabelsKey, LabelCountContainer> importedColumnLabels = new LinkedHashMap<>();
    private final Object lock = new Object();
    private final String domainName;

    public DataDomainLabelsContainer(String domainName) {
        this.domainName = domainName;
    }

    /**
     * Returns the data domain name.
     * @return Data domain name.
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * Returns the global labels container for labels defined on a connection level (assigned to the connection object).
     * @return Connection level labels.
     */
    public LabelCountContainer getConnectionLabels() {
        return connectionLabels;
    }

    /**
     * Returns the global labels container for labels defined on a table level (assigned to the table object).
     * @return Table level labels.
     */
    public LabelCountContainer getTableLabels() {
        return tableLabels;
    }

    /**
     * Returns the global labels container for labels defined on a column level (assigned to the column object).
     * @return Column level labels.
     */
    public LabelCountContainer getColumnLabels() {
        return columnLabels;
    }

    /**
     * Imports new labels from a connection level.
     * @param connectionName Connection name.
     * @param newLabels New labels or null if the connection was removed and the old labels should be unregistered.
     */
    public void importConnectionLabels(String connectionName, LabelCountContainer newLabels) {
        synchronized (this.lock) {
            LabelCountContainer oldLabels = this.importedConnectionLabels.get(connectionName);
            if (Objects.equals(oldLabels, newLabels)) {
                // no change
                return;
            }

            if (oldLabels != null) {
                this.connectionLabels.subtractCountsFromContainer(oldLabels);
                this.importedConnectionLabels.remove(connectionName);
            }

            if (newLabels != null && !newLabels.isEmpty()) {
                this.connectionLabels.addCountsFromContainer(newLabels);
                this.importedConnectionLabels.put(connectionName, newLabels);
            }
        }
    }

    /**
     * Imports new labels from a table level.
     * @param connectionName Connection name.
     * @param schemaTableName Schema and table name.
     * @param newLabels New labels or null if the table was removed and the old labels should be unregistered.
     */
    public void importTableLabels(String connectionName, PhysicalTableName schemaTableName, LabelCountContainer newLabels) {
        TableLabelsKey tableLabelsKey = new TableLabelsKey(connectionName, schemaTableName);

        synchronized (this.lock) {
            LabelCountContainer oldLabels = this.importedTableLabels.get(tableLabelsKey);
            if (Objects.equals(oldLabels, newLabels)) {
                // no change
                return;
            }

            if (oldLabels != null) {
                this.tableLabels.subtractCountsFromContainer(oldLabels);
                this.importedTableLabels.remove(tableLabelsKey);
            }

            if (newLabels != null && !newLabels.isEmpty()) {
                this.tableLabels.addCountsFromContainer(newLabels);
                this.importedTableLabels.put(tableLabelsKey, newLabels);
            }
        }
    }

    /**
     * Imports new labels from a column level, but aggregated for a whole table.
     * @param connectionName Connection name.
     * @param schemaTableName Schema and table name.
     * @param newLabels New labels or null if the table was removed and the old labels should be unregistered.
     */
    public void importColumnLabels(String connectionName, PhysicalTableName schemaTableName, LabelCountContainer newLabels) {
        TableLabelsKey tableLabelsKey = new TableLabelsKey(connectionName, schemaTableName);

        synchronized (this.lock) {
            LabelCountContainer oldLabels = this.importedColumnLabels.get(tableLabelsKey);
            if (Objects.equals(oldLabels, newLabels)) {
                // no change
                return;
            }

            if (oldLabels != null) {
                this.columnLabels.subtractCountsFromContainer(oldLabels);
                this.importedColumnLabels.remove(tableLabelsKey);
            }

            if (newLabels != null && !newLabels.isEmpty()) {
                this.columnLabels.addCountsFromContainer(newLabels);
                this.importedColumnLabels.put(tableLabelsKey, newLabels);
            }
        }
    }
}
