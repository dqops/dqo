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
package com.dqops.connectors;

import com.dqops.metadata.sources.PhysicalTableName;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Source table model with the details of a table returned by a source connection during a table listing operation.
 */
@EqualsAndHashCode(callSuper = false)
public class SourceTableModel {
    private String schemaName;
    private PhysicalTableName tableName;
    private Map<String, Object> properties = new LinkedHashMap<>();

    /**
     * Default (empty) constructor.
     */
    public SourceTableModel() {
    }

    /**
     * Creates a table model given the schema name and table name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     */
    public SourceTableModel(String schemaName, PhysicalTableName tableName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    /**
     * Returns a schema name in the source database. It can also be a dataset in BigQuery or a database in oracle/mysql/...
     * @return Physical schema name.
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets a schema name.
     * @param schemaName Schema name.
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    /**
     * Gets the physical table name.
     * @return Physical table name.
     */
    public PhysicalTableName getTableName() {
        return tableName;
    }

    /**
     * Sets a physical table name.
     * @param tableName Physical table name.
     */
    public void setTableName(PhysicalTableName tableName) {
        this.tableName = tableName;
    }

    /**
     * Returns a dictionary with additional provider specific parameters.
     * @return Dictionary with additional properties.
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Sets a new property dictionary.
     * @param properties Properties
     */
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
