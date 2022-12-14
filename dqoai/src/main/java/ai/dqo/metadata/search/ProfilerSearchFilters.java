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
import ai.dqo.profiling.ProfilerTarget;
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
 * Hierarchy node search filters for finding enabled profilers to be started.
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class ProfilerSearchFilters extends TableSearchFilters implements Cloneable {
    private String columnName;
    private String profilerName;
    private String sensorName;
    private String profilerCategory;
    private ProfilerTarget target;

    @JsonIgnore // we can't serialize it because it is a mix of types, will not support deserialization correctly
    private Set<HierarchyId> profilerHierarchyIds;

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public ProfilerSearchFiltersVisitor createProfilerSearchFilterVisitor() {
        return new ProfilerSearchFiltersVisitor(this);
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
     * Gets a profiler name search pattern.
     * @return Data profiler search pattern.
     */
    public String getProfilerName() {
        return profilerName;
    }

    /**
     * Sets a data profiler search pattern.
     * @param profilerName Data profiler search pattern.
     */
    public void setProfilerName(String profilerName) {
        this.profilerName = profilerName;
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
     * Returns the profiler category name as used in YAML to group the profilers.
     * @return Profiler  category name (standard, etc).
     */
    public String getProfilerCategory() {
        return profilerCategory;
    }

    /**
     * Sets the profiler category name as used in YAML.
     * @param profilerCategory Profiler category name.
     */
    public void setProfilerCategory(String profilerCategory) {
        this.profilerCategory = profilerCategory;
    }

    /**
     * Returns the target type (table or column).
     * @return Profiler target.
     */
    public ProfilerTarget getTarget() {
        return target;
    }

    /**
     * Sets the profiler target (table or column).
     * @param target Profiler target.
     */
    public void setTarget(ProfilerTarget target) {
        this.target = target;
    }

    /**
     * Returns a set of hierarchy ids of profilers that must match. When not null, only profilers in the set are returned as a result.
     * @return A set of profilers' hierarchy ids that must match or null when the profiler hierarchy id is not used for matching.
     */
    public Set<HierarchyId> getProfilerHierarchyIds() {
        return profilerHierarchyIds;
    }

    /**
     * Sets a set of hierarchy ids of profilers that must match. When not null, only profilers in the set are returned as a result.
     * @param profilerHierarchyIds A set of profilers' hierarchy ids that must match or null when the profiler hierarchy id is not used for matching.
     */
    public void setProfilerHierarchyIds(Set<HierarchyId> profilerHierarchyIds) {
        this.profilerHierarchyIds = profilerHierarchyIds;
    }

    /**
     * Returns a list of profiler id models that could be returned to the UI.
     * @return List of hierarchy id models of selected checks.
     */
    public List<HierarchyIdModel> getProfilerHierarchyIdsModels() {
        if (this.profilerHierarchyIds == null) {
            return null;
        }

        return this.profilerHierarchyIds.stream()
                .map(hierarchyId -> hierarchyId.toHierarchyIdModel())
                .collect(Collectors.toList());
    }

    /**
     * Sets a list of hierarchy ids of selected profilers, deserialized from JSON.
     * @param hierarchyIdsModels List of hierarchy ids, deserialized.
     */
    public void setProfilerHierarchyIdsModels(List<HierarchyIdModel> hierarchyIdsModels) {
        if (hierarchyIdsModels == null) {
            this.profilerHierarchyIds = null;
            return;
        }

        Set<HierarchyId> hierarchyIds = hierarchyIdsModels.stream()
                .map(hierarchyIdModel -> hierarchyIdModel.toHierarchyId())
                .collect(Collectors.toSet());
        this.profilerHierarchyIds = hierarchyIds;
    }

    /**
     * Creates a deep clone of the search object.
     * @return Deep cloned object.
     */
    @Override
    public ProfilerSearchFilters clone() {
        try {
            ProfilerSearchFilters cloned = (ProfilerSearchFilters) super.clone();
            if (this.profilerHierarchyIds != null) {
                cloned.profilerHierarchyIds = new HashSet<>(this.profilerHierarchyIds);
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object", ex);
        }
    }
}
