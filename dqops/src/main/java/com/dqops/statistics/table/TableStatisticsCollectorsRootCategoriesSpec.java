/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.statistics.table;

import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.statistics.AbstractRootStatisticsCollectorsContainerSpec;
import com.dqops.statistics.StatisticsCollectorTarget;
import com.dqops.statistics.table.volume.TableVolumeStatisticsCollectorsSpec;
import com.dqops.statistics.table.schema.TableSchemaStatisticsCollectorsSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
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
            put("schema", o -> o.schema);
        }
    };

    @JsonPropertyDescription("Configuration of volume statistics collectors on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeStatisticsCollectorsSpec volume = new TableVolumeStatisticsCollectorsSpec();
    private TableSchemaStatisticsCollectorsSpec schema = new TableSchemaStatisticsCollectorsSpec();

    /**
     * Returns the configuration of volume profilers on a table level.
     * @return Configuration of volume profilers.
     */
    public TableVolumeStatisticsCollectorsSpec getVolume() {
        return volume;
    }

    /**
     * Returns the configuration of schema profilers on a table level.
     * @return Configuration of schema profilers.
     */
    public TableSchemaStatisticsCollectorsSpec getSchema() {
        return schema;
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
     * Sets a configuration of schema profilers on a table level.
     * @param schema Volume profilers.
     */
    public void setChema(TableSchemaStatisticsCollectorsSpec schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
        this.propagateHierarchyIdToField(schema, "schema");
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
