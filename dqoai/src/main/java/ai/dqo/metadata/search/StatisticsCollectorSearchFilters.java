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
import ai.dqo.metadata.id.HierarchyIdModel;
import ai.dqo.profiling.StatisticsCollectorTarget;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Hierarchy node search filters for finding enabled statistics collectors (basic profilers) to be started.
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class StatisticsCollectorSearchFilters extends TableSearchFilters implements Cloneable {
    private String columnName;
    private String collectorName;
    private String sensorName;
    private String collectorCategory;
    private StatisticsCollectorTarget target;

    @JsonIgnore // we can't serialize it because it is a mix of types, will not support deserialization correctly
    private Set<HierarchyId> collectorsHierarchyIds;

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public StatisticsCollectorSearchFiltersVisitor createProfilerSearchFilterVisitor() {
        return new StatisticsCollectorSearchFiltersVisitor(this);
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
     * Gets a statistics collector name search pattern.
     * @return Data statistics collector search pattern.
     */
    public String getCollectorName() {
        return collectorName;
    }

    /**
     * Sets a data statistics collector search pattern.
     * @param collectorName Data statistics collector search pattern.
     */
    public void setCollectorName(String collectorName) {
        this.collectorName = collectorName;
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
     * Returns the statistics collector category name as used in YAML to group the collectors.
     * @return Statistics collector category name (standard, etc).
     */
    public String getCollectorCategory() {
        return collectorCategory;
    }

    /**
     * Sets the statistics collector category name as used in YAML.
     * @param collectorCategory Statistics collector category name.
     */
    public void setCollectorCategory(String collectorCategory) {
        this.collectorCategory = collectorCategory;
    }

    /**
     * Returns the target type (table or column).
     * @return Statistic collector target.
     */
    public StatisticsCollectorTarget getTarget() {
        return target;
    }

    /**
     * Sets the statistics collector target (table or column).
     * @param target Statistics collector target.
     */
    public void setTarget(StatisticsCollectorTarget target) {
        this.target = target;
    }

    /**
     * Returns a set of hierarchy ids of statistics collectors that must match. When not null, only statistics collectors in the set are returned as a result.
     * @return A set of collectors' hierarchy ids that must match or null when the statistics collector hierarchy id is not used for matching.
     */
    public Set<HierarchyId> getCollectorsHierarchyIds() {
        return collectorsHierarchyIds;
    }

    /**
     * Sets a set of hierarchy ids of statistics collectors that must match. When not null, only statistics collector in the set are returned as a result.
     * @param collectorsHierarchyIds A set of statistics collectors' hierarchy ids that must match or null when the collector hierarchy id is not used for matching.
     */
    public void setCollectorsHierarchyIds(Set<HierarchyId> collectorsHierarchyIds) {
        this.collectorsHierarchyIds = collectorsHierarchyIds;
    }

    /**
     * Returns a list of collector id models that could be returned to the UI.
     * @return List of hierarchy id models of selected checks.
     */
    public List<HierarchyIdModel> getCollectorsHierarchyIdsModels() {
        if (this.collectorsHierarchyIds == null) {
            return null;
        }

        return this.collectorsHierarchyIds.stream()
                .map(hierarchyId -> hierarchyId.toHierarchyIdModel())
                .collect(Collectors.toList());
    }

    /**
     * Sets a list of hierarchy ids of selected statistics collectors, deserialized from JSON.
     * @param hierarchyIdsModels List of hierarchy ids, deserialized.
     */
    public void setProfilerHierarchyIdsModels(List<HierarchyIdModel> hierarchyIdsModels) {
        if (hierarchyIdsModels == null) {
            this.collectorsHierarchyIds = null;
            return;
        }

        Set<HierarchyId> hierarchyIds = hierarchyIdsModels.stream()
                .map(hierarchyIdModel -> hierarchyIdModel.toHierarchyId())
                .collect(Collectors.toSet());
        this.collectorsHierarchyIds = hierarchyIds;
    }

    /**
     * Creates a deep clone of the search object.
     * @return Deep cloned object.
     */
    @Override
    public StatisticsCollectorSearchFilters clone() {
        try {
            StatisticsCollectorSearchFilters cloned = (StatisticsCollectorSearchFilters) super.clone();
            if (this.collectorsHierarchyIds != null) {
                cloned.collectorsHierarchyIds = new HashSet<>(this.collectorsHierarchyIds);
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object", ex);
        }
    }
}
