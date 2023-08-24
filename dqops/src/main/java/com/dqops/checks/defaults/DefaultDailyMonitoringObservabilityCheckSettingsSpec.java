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

package com.dqops.checks.defaults;

import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 * for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for daily monitoring checks only.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DefaultDailyMonitoringObservabilityCheckSettingsSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<DefaultDailyMonitoringObservabilityCheckSettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("table", o -> o.table);
            put("column", o -> o.column);
        }
    };

    @JsonPropertyDescription("The default configuration of daily monitoring checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DefaultDailyMonitoringTableObservabilityCheckSettingsSpec table = new DefaultDailyMonitoringTableObservabilityCheckSettingsSpec();

    @JsonPropertyDescription("The default configuration of daily monitoring checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec column = new DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec();

    /**
     * Returns the container of default table level checks.
     * @return Table level checks.
     */
    public DefaultDailyMonitoringTableObservabilityCheckSettingsSpec getTable() {
        return table;
    }

    /**
     * Sets the reference to the container of table level default checks.
     * @param table Table level checks container.
     */
    public void setTable(DefaultDailyMonitoringTableObservabilityCheckSettingsSpec table) {
        this.setDirtyIf(!Objects.equals(this.table, table));
        this.table = table;
        this.propagateHierarchyIdToField(table, "table");
    }

    /**
     * Returns the container of default column level checks.
     * @return Column level checks.
     */
    public DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec getColumn() {
        return column;
    }

    /**
     * Sets the reference to the container of column level default checks.
     * @param column Column level checks container.
     */
    public void setColumn(DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec column) {
        this.setDirtyIf(!Objects.equals(this.column, column));
        this.column = column;
        this.propagateHierarchyIdToField(column, "column");
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

    /**
     * Applies the checks on a target table.
     * @param targetTable Target table.
     * @param dialectSettings Dialect settings, to decide if the checks are applicable.
     */
    public void applyOnTable(TableSpec targetTable, ProviderDialectSettings dialectSettings) {
        if (this.table != null && !this.table.isDefault()) {
            this.table.applyOnTable(targetTable, dialectSettings);
        }

        if (this.column != null && !this.column.isDefault()) {
            for (ColumnSpec columnSpec : targetTable.getColumns().values()) {
                this.column.applyOnColumn(columnSpec, dialectSettings);
            }
        }
    }
}
