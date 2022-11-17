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

import ai.dqo.metadata.id.HierarchyId;

import java.util.Set;

/**
 * Hierarchy node search filters.
 */
public class CheckSearchFilters extends TableSearchFilters {
    private String columnName;
    private String checkName;
    private String sensorName;
    private Set<HierarchyId> checkHierarchyIds;

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public LegacyCheckSearchFiltersVisitor createLegacyCheckSearchFilterVisitor() {
        return new LegacyCheckSearchFiltersVisitor(this);
    }

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public CheckSearchFiltersVisitor createCheckSearchFilterVisitor() {
        return new CheckSearchFiltersVisitor(this);
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
     * Gets a data quality check search pattern.
     * @return Data quality check search pattern.
     */
    public String getCheckName() {
        return checkName;
    }

    /**
     * Sets a data quality check search pattern.
     * @param checkName Data quality check search pattern.
     */
    public void setCheckName(String checkName) {
        this.checkName = checkName;
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
     * Returns a set of hierarchy ids of checks that must match. When not null, only checks in the set are returned as a result.
     * @return A set of checks' hierarchy ids that must match or null when the check hierarchy id is not used for matching.
     */
    public Set<HierarchyId> getCheckHierarchyIds() {
        return checkHierarchyIds;
    }

    /**
     * Sets a set of hierarchy ids of checks that must match. When not null, only checks in the set are returned as a result.
     * @param checkHierarchyIds A set of checks' hierarchy ids that must match or null when the check hierarchy id is not used for matching.
     */
    public void setCheckHierarchyIds(Set<HierarchyId> checkHierarchyIds) {
        this.checkHierarchyIds = checkHierarchyIds;
    }
}
