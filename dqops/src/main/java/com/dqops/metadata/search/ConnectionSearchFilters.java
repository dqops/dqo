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
 * Connection search filters used to find a connection.
 */
public class ConnectionSearchFilters {
    @JsonPropertyDescription("The connection (data source) name. Supports search patterns in the format: 'source\\*', '\\*_prod', 'prefix\\*suffix'.")
    private String connectionName;

    @JsonPropertyDescription("A boolean flag to target enabled connections. When the value of this field is not set, " +
            "the default value of this field is *true*, targeting only enabled connections (data sources) that are not implicitly disabled.")
    private Boolean enabled = true;

    @JsonPropertyDescription("An array of tags assigned as the default grouping tags on a connection. All tags must be present on the connection to match. The tags can use patterns:  'prefix\\*', '\\*suffix', 'prefix\\*suffix'. " +
            "The tags are assigned to the connection on the default data grouping screen when any of the data grouping hierarchy level is assigned a static value, which is a tag.")
    private String[] tags;

    @JsonPropertyDescription("An array of labels assigned to the connection. All labels must be present on a connection to match. The labels can use patterns:  'prefix\\*', '\\*suffix', 'prefix\\*suffix'. " +
            "The labels are assigned on the labels screen and stored in the *labels* node in the *connection.dqoconnection.yaml* file.")
    private String[] labels;

    @JsonIgnore
    private SearchPattern connectionNameSearchPattern;
    @JsonIgnore
    private SearchPattern[] tagsSearchPatterns;
    @JsonIgnore
    private SearchPattern[] labelsSearchPatterns;
    
    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public ConnectionSearchFiltersVisitor createSearchFilterVisitor() {
        return new ConnectionSearchFiltersVisitor(this);
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
     * Returns the {@link SearchPattern} related to a specific dimension in <code>dimensions</code>.
     * Lazy getter, parses each <code>dimension</code> as a search pattern when requested and returns parsed object.
     * @param i Index of requested dimension search pattern. Corresponds to <code>dimensions[i]</code>.
     * @return {@link SearchPattern} related to <code>i</code>'th <code>dimension</code>.
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
