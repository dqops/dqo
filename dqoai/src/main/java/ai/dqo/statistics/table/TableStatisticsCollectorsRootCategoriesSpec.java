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
package ai.dqo.statistics.table;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.statistics.AbstractRootStatisticsCollectorsContainerSpec;
import ai.dqo.statistics.StatisticsCollectorTarget;
import ai.dqo.statistics.table.volume.TableVolumeStatisticsCollectorsSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Root profiler container that contains groups of table level statistics collectors (basic profilers).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableStatisticsCollectorsRootCategoriesSpec extends AbstractRootStatisticsCollectorsContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableStatisticsCollectorsRootCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootStatisticsCollectorsContainerSpec.FIELDS) {
        {
            put("volume", o -> o.volume);
        }
    };

    @JsonPropertyDescription("Configuration of volume statistics collectors on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeStatisticsCollectorsSpec volume = new TableVolumeStatisticsCollectorsSpec();

    /**
     * Returns the configuration of volume profilers on a table level.
     * @return Configuration of volume profilers.
     */
    public TableVolumeStatisticsCollectorsSpec getVolume() {
        return volume;
    }

    /**
     * Sets a configuration of volume profilers on a table level.
     * @param volume Volume profilers.
     */
    public void setVolume(TableVolumeStatisticsCollectorsSpec volume) {
        this.setDirtyIf(!Objects.equals(this.volume, volume));
        this.volume = volume;
        this.propagateHierarchyIdToField(volume, "volume");
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Returns the type of the target (table or column).
     *
     * @return Target type.
     */
    @Override
    public StatisticsCollectorTarget getTarget() {
        return StatisticsCollectorTarget.table;
    }
}
