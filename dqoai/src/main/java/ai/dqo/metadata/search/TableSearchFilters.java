/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.search;

/**
 * Hierarchy node search filters.
 */
public class TableSearchFilters {
    private String connectionName;
    private String schemaTableName;
    private Boolean enabled = true;
    private String[] dimensions;
    private String[] labels;

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public TableSearchFiltersVisitor createTableSearchFilterVisitor() {
        return new TableSearchFiltersVisitor(this);
    }

    /**
     * Returns the connection name search pattern.
     * @return Connection name search pattern.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Sets the connection name search pattern.
     * @param connectionName Connection name search pattern.
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Returns a "schema.table" search pattern. The pattern may also contain a wildcard '*', for example: "schema.fact*"
     * @return Schema / table search pattern.
     */
    public String getSchemaTableName() {
        return schemaTableName;
    }

    /**
     * Sets a schema name and table name search pattern.
     * @param schemaTableName Schema and table search pattern.
     */
    public void setSchemaTableName(String schemaTableName) {
        this.schemaTableName = schemaTableName;
    }

    /**
     * Sets the enabled search criteria. null - search is ignored on the enabled/disabled flags,
     * true - only enabled (not explicitly disabled) nodes are returned (disabled connection or table stops search for nested elements),
     * false - only nodes that are disabled are returned.
     * @return Enabled search flag.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets an enabled (disabled) search flag.
     * @param enabled Enabled search flag.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns the dimension name search patterns.
     * @return Dimension search patterns.
     */
    public String[] getDimensions() {
        return dimensions;
    }

    /**
     * Sets the dimension search patterns.
     * @param dimensions dimension search patterns.
     */
    public void setDimensions(String[] dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * Returns the label search patterns.
     * @return Label search patterns.
     */
    public String[] getLabels() {
        return labels;
    }

    /**
     * Sets the label search patterns.
     * @param labels label search patterns.
     */
    public void setLabels(String[] labels) {
        this.labels = labels;
    }

}
