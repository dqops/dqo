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
