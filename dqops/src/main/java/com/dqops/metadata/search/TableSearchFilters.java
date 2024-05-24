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
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;

/**
 * Target table search filters used to find tables in the DQOps metadata.
 */
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class TableSearchFilters {
    @JsonPropertyDescription("The connection (data source) name. Supports search patterns in the format: 'source\\*', '\\*_prod', 'prefix\\*suffix'.")
    private String connection;

    @JsonPropertyDescription("The schema and table name. It is provided as *<schema_name>.<table_name>*, for example *public.fact_sales*. " +
            "The schema and table name accept patterns both in the schema name and table name parts. " +
            "Sample patterns are: 'schema_name.tab_prefix_\\*', 'schema_name.*', '*.*', 'schema_name.\\*_customer', 'schema_name.tab_\\*_suffix'.")
    private String fullTableName;

    /**
     * Alternative identification of the full table name.
     */
    @JsonIgnore
    private PhysicalTableName physicalTableName;

    @JsonPropertyDescription("A boolean flag to target enabled tables, columns or checks. When the value of this field is not set, " +
            "the default value of this field is *true*, targeting only tables, columns and checks that are not implicitly disabled.")
    private Boolean enabled = true;

    @JsonPropertyDescription("An array of tags assigned to the table. All tags must be present on a table to match. The tags can use patterns:  'prefix\\*', '\\*suffix', 'prefix\\*suffix'. " +
            "The tags are assigned to the table on the data grouping screen when any of the data grouping hierarchy level is assigned a static value, which is a tag.")
    private String[] tags;

    @JsonPropertyDescription("An array of labels assigned to the table. All labels must be present on a table to match. The labels can use patterns:  'prefix\\*', '\\*suffix', 'prefix\\*suffix'. " +
            "The labels are assigned on the labels screen and stored in the *labels* node in the *.dqotable.yaml* file.")
    private String[] labels;

    @JsonPropertyDescription("Optional limit for the maximum number of results to return.")
    private Integer maxResults;

    @JsonIgnore
    private SearchPattern connectionNameSearchPattern;
    @JsonIgnore
    private SearchPattern schemaNameSearchPattern;
    @JsonIgnore
    private SearchPattern tableNameSearchPattern;
    @JsonIgnore
    private SearchPattern[] tagsSearchPatterns;
    @JsonIgnore
    private SearchPattern[] labelsSearchPatterns;

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
    public String getConnection() {
        return connection;
    }

    /**
     * Sets the connection name search pattern.
     * @param connection Connection name search pattern.
     */
    public void setConnection(String connection) {
        this.connection = connection;
    }

    /**
     * Returns a "schema.table" search pattern. The pattern may also contain a wildcard '*', for example: "schema.fact*"
     * @return Schema / table search pattern.
     */
    public String getFullTableName() {
        return fullTableName;
    }

    /**
     * Sets a schema name and table name search pattern.
     * @param fullTableName Schema and table search pattern.
     */
    public void setFullTableName(String fullTableName) {
        this.fullTableName = fullTableName;
    }

    /**
     * Returns a full physical table name, which is an alternative filter to find the tables.
     * When the value is null, it is created from the full table name search pattern.
     * @return Full table name as a physical table name.
     */
    public PhysicalTableName getPhysicalTableName() {
        if (this.physicalTableName == null && !Strings.isNullOrEmpty(this.fullTableName)) {
            this.physicalTableName = PhysicalTableName.fromSchemaTableFilter(this.fullTableName);
        }
        return this.physicalTableName;
    }

    /**
     * Sets a filter to a physical table name.
     * @param physicalTableName Physical table name.
     */
    public void setPhysicalTableName(PhysicalTableName physicalTableName) {
        this.physicalTableName = physicalTableName;
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
     * Returns the data stream hierarchy tag name search patterns.
     * @return data stream hierarchy tag search patterns.
     */
    public String[] getTags() {
        return tags;
    }

    /**
     * Sets the data stream hierarchy tag search patterns.
     * @param tags data stream hierarchy tag search patterns.
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
        if (this.connectionNameSearchPattern == null && !Strings.isNullOrEmpty(this.connection)) {
            this.connectionNameSearchPattern = SearchPattern.create(false, this.connection);
        }

        return connectionNameSearchPattern;
    }
    
    protected void recreateSchemaTableNameSearchPatterns() {
        PhysicalTableName parsedSchemaTableName = this.getPhysicalTableName();
        if (parsedSchemaTableName == null) {
            return;
        }
        this.schemaNameSearchPattern = SearchPattern.create(false, parsedSchemaTableName.getSchemaName());
        this.tableNameSearchPattern = SearchPattern.create(false, parsedSchemaTableName.getTableName());
    }

    /**
     * Returns the {@link SearchPattern} related to <code>schemaName</code>.
     * Lazy getter, parses <code>schemaName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>schemaName</code>.
     */
    @JsonIgnore
    public SearchPattern getSchemaNameSearchPattern() {
        if (this.schemaNameSearchPattern == null && this.getPhysicalTableName() != null) {
            recreateSchemaTableNameSearchPatterns();
        }

        return schemaNameSearchPattern;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>tableName</code>.
     * Lazy getter, parses <code>tableName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>tableName</code>.
     */
    @JsonIgnore
    public SearchPattern getTableNameSearchPattern() {
        if (tableNameSearchPattern == null && this.getPhysicalTableName() != null) {
            recreateSchemaTableNameSearchPatterns();
        }

        return tableNameSearchPattern;
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

    /**
     * Sets the limit for the maximum number of results to return.
     * @return Limit or null.
     */
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * Sets the limit for the maximum number of results to return.
     * @param maxResults New limit of rows or null to disable limiting the results.
     */
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public static class TableSearchFiltersSampleFactory implements SampleValueFactory<TableSearchFilters> {
        @Override
        public TableSearchFilters createSample() {
            return new TableSearchFilters() {{
                setConnection(SampleStringsRegistry.getConnectionName());
                setFullTableName(SampleStringsRegistry.getSchemaTableName());
            }};
        }
    }
}
