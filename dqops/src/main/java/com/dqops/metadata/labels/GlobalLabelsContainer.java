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

package com.dqops.metadata.labels;

import com.dqops.metadata.sources.PhysicalTableName;

/**
 * A global container of all labels, collected from all levels (connection, table, column).
 */
public interface GlobalLabelsContainer {
    /**
     * Returns the global labels container for labels defined on a connection level (assigned to the connection object).
     *
     * @param dataDomainName Data domain name.
     *
     * @return Connection level labels.
     */
    LabelCountContainer getConnectionLabels(String dataDomainName);

    /**
     * Returns the global labels container for labels defined on a table level (assigned to the table object).
     *
     * @param dataDomainName Data domain name.
     * @return Table level labels.
     */
    LabelCountContainer getTableLabels(String dataDomainName);

    /**
     * Returns the global labels container for labels defined on a column level (assigned to the column object).
     *
     * @param dataDomainName Data domain name.
     * @return Column level labels.
     */
    LabelCountContainer getColumnLabels(String dataDomainName);

    /**
     * Imports new labels from a connection level.
     *
     * @param dataDomainName Data domain name.
     * @param connectionName Connection name.
     * @param newLabels      New labels or null if the connection was removed and the old labels should be unregistered.
     */
    void importConnectionLabels(String dataDomainName, String connectionName, LabelCountContainer newLabels);

    /**
     * Imports new labels from a table level.
     *
     * @param dataDomainName Data domain name.
     * @param connectionName  Connection name.
     * @param schemaTableName Schema and table name.
     * @param newLabels       New labels or null if the table was removed and the old labels should be unregistered.
     */
    void importTableLabels(String dataDomainName, String connectionName, PhysicalTableName schemaTableName, LabelCountContainer newLabels);

    /**
     * Imports new labels from a column level, but aggregated for a whole table.
     *
     * @param dataDomainName Data domain name.
     * @param connectionName  Connection name.
     * @param schemaTableName Schema and table name.
     * @param newLabels       New labels or null if the table was removed and the old labels should be unregistered.
     */
    void importColumnLabels(String dataDomainName, String connectionName, PhysicalTableName schemaTableName, LabelCountContainer newLabels);
}
