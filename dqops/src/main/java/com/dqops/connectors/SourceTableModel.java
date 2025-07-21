/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
