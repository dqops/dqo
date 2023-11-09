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
package com.dqops.metadata.search;

import com.dqops.metadata.search.pattern.SearchPattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Connection search filters used to provide search parameters to find target columns.
 */
public class ColumnSearchFilters {
    @JsonPropertyDescription("The connection (data source) name. Supports search patterns in the format: 'source\\*', '\\*_prod', 'prefix\\*suffix'.")
    private String connectionName;

    @JsonPropertyDescription("The schema and table name. It is provided as *&lt;schema_name&gt.&lt;table_name&gt;*, for example *public.fact_sales*. " +
            "The schema and table name accept patterns both in the schema name and table name parts. " +
            "Sample patterns are: 'schema_name.tab_prefix_\\*', 'schema_name.*', '*.*', 'schema_name.\\*_customer', 'schema_name.tab_\\*_suffix'.")
    private String schemaTableName;

    @JsonPropertyDescription("The column name. This field accepts search patterns in the format: 'fk_\\*', '\\*_id', 'prefix\\*suffix'.")
    private String columnName;

    @JsonPropertyDescription("A boolean flag to target enabled tables, columns or checks. When the value of this field is not set, " +
            "the default value of this field is *true*, targeting only tables, columns and checks that are not implicitly disabled.")
    private Boolean enabled = true;

    @JsonPropertyDescription("The column data type that was imported from the data source and is stored in the " +
            "[columns -> column_name -> type_snapshot -> column_type](../../reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.")
    private String columnDataType;

    @JsonPropertyDescription("Optional filter to find only nullable (when the value is *true*) or not nullable (when the value is *false*) columns, based on the value of the " +
            "[columns -> column_name -> type_snapshot -> nullable](../../reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.")
    private Boolean nullable;

    @JsonPropertyDescription("An array of tags assigned to the table. All tags must be present on a table to match. The tags can use patterns:  'prefix\\*', '\\*suffix', 'prefix\\*suffix'. " +
            "The tags are assigned to the table on the data grouping screen when any of the data grouping hierarchy level is assigned a static value, which is a tag.")
    private String[] tags;

    @JsonPropertyDescription("An array of labels assigned to the table or a column. All labels must be present on a table or a column to match. The labels can use patterns:  'prefix\\*', '\\*suffix', 'prefix\\*suffix'. " +
            "The labels are assigned on the labels screen and stored in the *labels* node in the *.dqotable.yaml* file.")
    private String[] labels;

    @JsonIgnore
    private SearchPattern connectionNameSearchPattern;
    @JsonIgnore
    private SearchPattern schemaTableNameSearchPattern;
    @JsonIgnore
    private SearchPattern columnNameSearchPattern;
    @JsonIgnore
    private SearchPattern[] tagsSearchPatterns;
    @JsonIgnore
    private SearchPattern[] labelsSearchPatterns;

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public ColumnSearchFiltersVisitor createSearchFilterVisitor() {
        return new ColumnSearchFiltersVisitor(this);
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
     * Gets a column name search pattern.
     * @return Column name search pattern.
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Sets a column name search pattern.
     * @param columnName Column name search pattern.
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Gets the enabled search criteria. null - search is ignored on the enabled/disabled flags,
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
     * Gets the searched column datatype.
     * @return Column datatype name.
     */
    public String getColumnDataType() {
        return columnDataType;
    }

    /**
     * Sets the desired column datatype.
     * @param columnDataType New datatype to be sought after.
     */
    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    /**
     * Gets the column nullable flag.
     * @return Column nullable flag.
     */
    public Boolean getNullable() {
        return nullable;
    }

    /**
     * Sets the column nullable flag.
     * @param nullable New column nullable flag.
     */
    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * Returns the dimension name search patterns.
     * @return Dimension search patterns.
     */
    public String[] getTags() {
        return tags;
    }

    /**
     * Sets the dimension search patterns.
     * @param tags dimension search patterns.
     */
    public void setTags(String[] tags) {
        this.tags = tags;
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

    /**
     * Returns the {@link SearchPattern} related to <code>connectionName</code>.
     * Lazy getter, parses <code>connectionName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>connectionName</code>.
     */
    @JsonIgnore
    public SearchPattern getConnectionNameSearchPattern() {
        if (connectionNameSearchPattern == null && connectionName != null) {
            connectionNameSearchPattern = SearchPattern.create(false, connectionName);
        }

        return connectionNameSearchPattern;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>schemaTableName</code>.
     * Lazy getter, parses <code>schemaTableName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>schemaTableName</code>.
     */
    @JsonIgnore
    public SearchPattern getSchemaTableNameSearchPattern() {
        if (schemaTableNameSearchPattern == null && schemaTableName != null) {
            schemaTableNameSearchPattern = SearchPattern.create(false, schemaTableName);
        }

        return schemaTableNameSearchPattern;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>columnName</code>.
     * Lazy getter, parses <code>columnName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>columnName</code>.
     */
    @JsonIgnore
    public SearchPattern getColumnNameSearchPattern() {
        if (columnNameSearchPattern == null && columnName != null) {
            columnNameSearchPattern = SearchPattern.create(false, columnName);
        }

        return columnNameSearchPattern;
    }

    /**
     * Returns the {@link SearchPattern} related to a specific tag in <code>tags</code>.
     * Lazy getter, parses each <code>tag</code> as a search pattern when requested and returns parsed object.
     * @param i Index of requested tag search pattern. Corresponds to <code>tags[i]</code>.
     * @return {@link SearchPattern} related to <code>i</code>'th <code>tag</code>.
     */
    public SearchPattern getTagSearchPatternAt(int i) {
        if (tagsSearchPatterns == null) {
            tagsSearchPatterns = new SearchPattern[tags.length];
        }
        if (tagsSearchPatterns[i] == null && tags[i] != null) {
            tagsSearchPatterns[i] = SearchPattern.create(false, tags[i]);
        }

        return tagsSearchPatterns[i];
    }

    /**
     * Returns the {@link SearchPattern} related to a specific label in <code>labels</code>.
     * Lazy getter, parses each <code>label</code> as a search pattern when requested and returns parsed object.
     * @param i Index of requested label search pattern. Corresponds to <code>labels[i]</code>.
     * @return {@link SearchPattern} related to <code>i</code>'th <code>label</code>.
     */
    public SearchPattern getLabelSearchPatternAt(int i) {
        if (labelsSearchPatterns == null) {
            labelsSearchPatterns = new SearchPattern[labels.length];
        }
        if (labelsSearchPatterns[i] == null && labels[i] != null) {
            labelsSearchPatterns[i] = SearchPattern.create(false, labels[i]);
        }

        return labelsSearchPatterns[i];
    }
}
