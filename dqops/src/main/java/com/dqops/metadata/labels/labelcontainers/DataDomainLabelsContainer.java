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

package com.dqops.metadata.labels.labelcontainers;

import com.dqops.metadata.sources.PhysicalTableName;

import java.util.HashMap;
import java.util.Objects;

/**
 * A global container of all labels, collected from all levels (connection, table, column) for one data domain.
 */
public class DataDomainLabelsContainer {
    private final LabelCountContainer connectionLabels = new LabelCountContainer();
    private final LabelCountContainer tableLabels = new LabelCountContainer();
    private final LabelCountContainer columnLabels = new LabelCountContainer();

    private final HashMap<String, LabelCountContainer> importedConnectionLabels = new HashMap<>();
    private final HashMap<TableLabelsKey, LabelCountContainer> importedTableLabels = new HashMap<>();
    private final HashMap<TableLabelsKey, LabelCountContainer> importedColumnLabels = new HashMap<>();
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
                this.connectionLabels.addCountsFromContainer(oldLabels);
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
                this.tableLabels.addCountsFromContainer(oldLabels);
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
                this.columnLabels.addCountsFromContainer(oldLabels);
                this.importedColumnLabels.put(tableLabelsKey, newLabels);
            }
        }
    }
}
