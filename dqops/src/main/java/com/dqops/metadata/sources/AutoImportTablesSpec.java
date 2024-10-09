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

package com.dqops.metadata.sources;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Specification object configured on a connection that configures how DQOps performs automatic schema import by a CRON scheduler.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class AutoImportTablesSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<AutoImportTablesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("schedule", o -> o.schedule);
        }
    };

    @JsonPropertyDescription("Source schema name filter. Accepts filters in the form of *s, s* and *s* to restrict import to selected schemas.")
    private String schemaFilter;

    @JsonPropertyDescription("Source table name filter. It is a table name or a text that must be present inside the table name.")
    private String tableNameContains;

    @JsonPropertyDescription("Schedule for importing source tables using a CRON scheduler.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CronScheduleSpec schedule;

    /**
     * Returns a schema name filter to filter which schemas are automatically imported.
     * @return Schema name filter.
     */
    public String getSchemaFilter() {
        return schemaFilter;
    }

    /**
     * Sets a schema name filter to restrict import to selected schemas.
     * @param schemaFilter Schema name filter.
     */
    public void setSchemaFilter(String schemaFilter) {
        this.setDirtyIf(!Objects.equals(this.schemaFilter, schemaFilter));
        this.schemaFilter = schemaFilter;
    }

    /**
     * Sets a table name filter to restrict import to selected tables.
     * @return Table name filter.
     */
    public String getTableNameContains() {
        return tableNameContains;
    }

    /**
     * Sets a table name filter to restrict import to selected tables.
     * @param tableNameContains Table name filter.
     */
    public void setTableNameContains(String tableNameContains) {
        this.setDirtyIf(!Objects.equals(this.tableNameContains, tableNameContains));
        this.tableNameContains = tableNameContains;
    }

    /**
     * Returns a CRON schedule used to automate importing tables into a parent connection.
     * @return Cron schedule configuration.
     */
    public CronScheduleSpec getSchedule() {
        return schedule;
    }

    /**
     * Sets a cron schedule configuration used to automatically import source tables into a connection.
     * @param schedule Cron schedule.
     */
    public void setSchedule(CronScheduleSpec schedule) {
        this.setDirtyIf(!Objects.equals(this.schedule, schedule));
        this.schedule = schedule;
        propagateHierarchyIdToField(schedule, "schedule");
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
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }
}
