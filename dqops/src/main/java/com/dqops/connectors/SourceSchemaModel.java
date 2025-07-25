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

import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Model object with a single schema that was found in the source system.
 */
@EqualsAndHashCode(callSuper = false)
public class SourceSchemaModel {
    private String schemaName;
    private Map<String, Object> properties = new LinkedHashMap<>();

    /**
     * Default constructor.
     */
    public SourceSchemaModel() {
    }

    /**
     * Creates a schema model with a schema name.
     * @param schemaName Schema name.
     */
    public SourceSchemaModel(String schemaName) {
        this.schemaName = schemaName;
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
