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

package com.dqops.metadata.defaultchecks.table;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * The configuration of a table pattern to match default table checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TargetTablePatternSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TargetTablePatternSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The data source connection name filter. Accepts wildcards in the format: *conn, *, conn*.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String connection;

    @JsonPropertyDescription("The schema name filter. Accepts wildcards in the format: *_prod, *, pub*.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String schema;

    @JsonPropertyDescription("The table name filter. Accepts wildcards in the format: *_customers, *, fact_*.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String table;

    @JsonPropertyDescription("The maximum table priority (inclusive) for tables that are covered by the default checks.")
    private Integer tablePriority;

    @JsonPropertyDescription("The label filter. Accepts wildcards in the format: *_customers, *, fact_*. The label must be present on the connection or table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String label;

    /**
     * Returns the connection name filter.
     * @return Connection name filter.
     */
    public String getConnection() {
        return connection;
    }

    /**
     * Sets the connection name filter.
     * @param connection Connection name filter.
     */
    public void setConnection(String connection) {
        this.setDirtyIf(!Objects.equals(this.connection, connection));
        this.connection = connection;
    }

    /**
     * Gets the schema name filter.
     * @return Schema name filter.
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the schema name filter.
     * @param schema Schema name filter.
     */
    public void setSchema(String schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
    }

    /**
     * Returns the table filter.
     * @return Table filter.
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets the table filter.
     * @param table Table filter.
     */
    public void setTable(String table) {
        this.setDirtyIf(!Objects.equals(this.table, table));
        this.table = table;
    }

    /**
     * Returns the highest priority (inclusive) of tables that are covered by the checks.
     * @return Maximum table priority or null.
     */
    public Integer getTablePriority() {
        return tablePriority;
    }

    /**
     * Sets the maximum table priority of tables that are covered by the checks.
     * @param tablePriority Maximum table priority.
     */
    public void setTablePriority(Integer tablePriority) {
        this.setDirtyIf(!Objects.equals(this.tablePriority, tablePriority));
        this.tablePriority = tablePriority;
    }

    /**
     * Returns the label filter.
     * @return Label filter.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label filter.
     * @param label Label filter.
     */
    public void setLabel(String label) {
        this.setDirtyIf(!Objects.equals(this.label, label));
        this.label = label;
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
     * Creates a pattern filter for this search pattern.
     * @return Pattern filter.
     */
    public TargetTablePatternFilter toPatternFilter() {
        return new TargetTablePatternFilter(this);
    }
}
