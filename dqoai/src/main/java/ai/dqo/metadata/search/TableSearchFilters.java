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

import ai.dqo.metadata.search.pattern.SearchPattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Hierarchy node search filters.
 */
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class TableSearchFilters {
    private String connectionName;
    private String schemaTableName;
    private Boolean enabled = true;
    private String[] tags;
    private String[] labels;

    @JsonIgnore
    private SearchPattern connectionNameSearchPattern;
    @JsonIgnore
    private SearchPattern schemaTableNameSearchPattern;
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
    public SearchPattern getSchemaTableNameSearchPattern() {
        if (schemaTableNameSearchPattern == null && schemaTableName != null) {
            schemaTableNameSearchPattern = SearchPattern.create(false, schemaTableName);
        }

        return schemaTableNameSearchPattern;
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
