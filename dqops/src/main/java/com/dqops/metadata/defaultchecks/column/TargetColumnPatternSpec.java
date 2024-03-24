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

package com.dqops.metadata.defaultchecks.column;

import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.defaultchecks.table.TargetTablePatternFilter;
import com.dqops.metadata.defaultchecks.table.TargetTablePatternSpec;
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
 * The configuration of a column pattern to match default column checks. Includes also the pattern for the target table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TargetColumnPatternSpec extends TargetTablePatternSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TargetColumnPatternSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(TargetTablePatternSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The target column name filter. Accepts wildcards in the format: *id, *, c_*.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String column;

    @JsonPropertyDescription("The target column data type filter. Filters by a physical (database specific) data type name imported from the data source. Accepts wildcards in the format: *int, *, big*.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String dataType;

    @JsonPropertyDescription("The filter for a target data type category.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private DataTypeCategory dataTypeCategory;

    /**
     * Returns a column name filter.
     * @return Column name filter.
     */
    public String getColumn() {
        return column;
    }

    /**
     * Sets a column name filter.
     * @param column Column name filter.
     */
    public void setColumn(String column) {
        this.setDirtyIf(!Objects.equals(this.column, column));
        this.column = column;
    }

    /**
     * Returns the physical data type filter.
     * @return Physical data type filter.
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets a physical data type filter.
     * @param dataType Physical data type filter.
     */
    public void setDataType(String dataType) {
        this.setDirtyIf(!Objects.equals(this.dataType, dataType));
        this.dataType = dataType;
    }

    /**
     * Returns a data type category filter.
     * @return Data type category filter.
     */
    public DataTypeCategory getDataTypeCategory() {
        return dataTypeCategory;
    }

    /**
     * Sets a data type category filter.
     * @param dataTypeCategory Data type category filter.
     */
    public void setDataTypeCategory(DataTypeCategory dataTypeCategory) {
        this.setDirtyIf(this.dataTypeCategory != dataTypeCategory);
        this.dataTypeCategory = dataTypeCategory;
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
    public TargetColumnPatternFilter toPatternFilter() {
        return new TargetColumnPatternFilter(this);
    }
}
