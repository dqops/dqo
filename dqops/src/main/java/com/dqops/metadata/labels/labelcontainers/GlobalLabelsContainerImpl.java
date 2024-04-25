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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A global container of all labels, collected from all levels (connection, table, column).
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GlobalLabelsContainerImpl implements GlobalLabelsContainer {
    private final Map<String, DataDomainLabelsContainer> dataDomains = new LinkedHashMap<>();
    private final Object lock = new Object();

    /**
     * Retrieves or creates and returns a label container for a data domain.
     * @param dataDomainName Data domain name.
     * @return Labels container.
     */
    protected DataDomainLabelsContainer getDataDomainContainer(String dataDomainName) {
        synchronized (this.lock) {
            DataDomainLabelsContainer dataDomainLabelsContainer = this.dataDomains.get(dataDomainName);
            if (dataDomainLabelsContainer == null) {
                dataDomainLabelsContainer = new DataDomainLabelsContainer(dataDomainName);
                this.dataDomains.put(dataDomainName, dataDomainLabelsContainer);
            }

            return dataDomainLabelsContainer;
        }
    }

    /**
     * Returns the global labels container for labels defined on a connection level (assigned to the connection object).
     * @param dataDomainName Data domain name.
     * @return Connection level labels.
     */
    @Override
    public LabelCountContainer getConnectionLabels(String dataDomainName) {
        DataDomainLabelsContainer dataDomainContainer = this.getDataDomainContainer(dataDomainName);
        return dataDomainContainer.getConnectionLabels();
    }

    /**
     * Returns the global labels container for labels defined on a table level (assigned to the table object).
     * @param dataDomainName Data domain name.
     * @return Table level labels.
     */
    @Override
    public LabelCountContainer getTableLabels(String dataDomainName) {
        DataDomainLabelsContainer dataDomainContainer = this.getDataDomainContainer(dataDomainName);
        return dataDomainContainer.getTableLabels();
    }

    /**
     * Returns the global labels container for labels defined on a column level (assigned to the column object).
     * @param dataDomainName Data domain name.
     * @return Column level labels.
     */
    @Override
    public LabelCountContainer getColumnLabels(String dataDomainName) {
        DataDomainLabelsContainer dataDomainContainer = this.getDataDomainContainer(dataDomainName);
        return dataDomainContainer.getColumnLabels();
    }

    /**
     * Imports new labels from a connection level.
     * @param dataDomainName Data domain name.
     * @param connectionName Connection name.
     * @param newLabels New labels or null if the connection was removed and the old labels should be unregistered.
     */
    @Override
    public void importConnectionLabels(String dataDomainName, String connectionName, LabelCountContainer newLabels) {
        DataDomainLabelsContainer dataDomainContainer = this.getDataDomainContainer(dataDomainName);
        dataDomainContainer.importConnectionLabels(connectionName, newLabels);
    }

    /**
     * Imports new labels from a table level.
     * @param dataDomainName Data domain name.
     * @param connectionName Connection name.
     * @param schemaTableName Schema and table name.
     * @param newLabels New labels or null if the table was removed and the old labels should be unregistered.
     */
    @Override
    public void importTableLabels(String dataDomainName, String connectionName, PhysicalTableName schemaTableName, LabelCountContainer newLabels) {
        DataDomainLabelsContainer dataDomainContainer = this.getDataDomainContainer(dataDomainName);
        dataDomainContainer.importTableLabels(connectionName, schemaTableName, newLabels);
    }

    /**
     * Imports new labels from a column level, but aggregated for a whole table.
     * @param dataDomainName Data domain name.
     * @param connectionName Connection name.
     * @param schemaTableName Schema and table name.
     * @param newLabels New labels or null if the table was removed and the old labels should be unregistered.
     */
    @Override
    public void importColumnLabels(String dataDomainName, String connectionName, PhysicalTableName schemaTableName, LabelCountContainer newLabels) {
        DataDomainLabelsContainer dataDomainContainer = this.getDataDomainContainer(dataDomainName);
        dataDomainContainer.importColumnLabels(connectionName, schemaTableName, newLabels);
    }
}
