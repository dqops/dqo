/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.search;

import com.dqops.metadata.search.pattern.SearchPattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.apache.parquet.Strings;

/**
 * Hierarchy node search filters.
 */
public class SensorDefinitionSearchFilters {
    @JsonPropertyDescription("The target sensor name. This filter supports search patterns such as 'prefix_\\*', '\\*_suffix', 'prefix\\*suffix'.")
    private String sensorName;

    @JsonPropertyDescription("Boolean flag to search only for enabled sensors or only disabled sensors. The default value is *true*, which prevents searching for all rules despite their enabled status.")
    private Boolean enabled = true;

    @JsonIgnore
    private SearchPattern sensorNameSearchPattern;

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public SensorDefinitionSearchFiltersVisitor createSearchFilterVisitor() {
        return new SensorDefinitionSearchFiltersVisitor(this);
    }

    /**
     * Gets a sensor name search pattern.
     * @return Sensor name search pattern.
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * Sets a sensor name search pattern.
     * @param sensorName Sensor name search pattern.
     */
    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
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
     * Returns the {@link SearchPattern} related to <code>sensorName</code>.
     * Lazy getter, parses <code>sensorName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>sensorName</code>.
     */
    @JsonIgnore
    public SearchPattern getSensorNameSearchPattern() {
        if (sensorNameSearchPattern == null && !Strings.isNullOrEmpty(sensorName)) {
            sensorNameSearchPattern = SearchPattern.create(false, sensorName);
        }

        return sensorNameSearchPattern;
    }
}
