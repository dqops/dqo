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

package com.dqops.metadata.comparisons;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.parquet.Strings;

import java.util.Objects;

/**
 * Configuration of a pair of columns on the compared table and the reference table (the source of truth) that are joined
 * and used for grouping to perform data comparison of aggregated results (sums of columns, row counts, etc.).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class TableComparisonGroupingColumnsPairSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<TableComparisonGroupingColumnsPairSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The name of the column on the compared table (the parent table) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the reference table.")
    private String comparedTableColumnName;

    @JsonPropertyDescription("The name of the column on the reference table (the source of truth) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the compared table.")
    private String referenceTableColumnName;

    /**
     * The default empty constructor.
     */
    public TableComparisonGroupingColumnsPairSpec() {
    }

    /**
     * Constructor that creates the column pair specification.
     * @param comparedTableColumnName The name of the column on the compared table.
     * @param referenceTableColumnName The name of the column on the reference table.
     */
    public TableComparisonGroupingColumnsPairSpec(String comparedTableColumnName, String referenceTableColumnName) {
        this.comparedTableColumnName = comparedTableColumnName;
        this.referenceTableColumnName = referenceTableColumnName;
    }

    /**
     * Returns the column name on the compared table that is used for grouping and joining.
     * @return Column name on the compared table.
     */
    public String getComparedTableColumnName() {
        return comparedTableColumnName;
    }

    /**
     * Sets the column name on the compared table that is used for grouping and joining.
     * @param comparedTableColumnName Column name on the compared table.
     */
    public void setComparedTableColumnName(String comparedTableColumnName) {
        this.setDirtyIf(!Objects.equals(this.comparedTableColumnName, comparedTableColumnName));
        this.comparedTableColumnName = comparedTableColumnName;
    }

    /**
     * Returns the column name on the reference table that is used for grouping and joining.
     * @return Column name on the reference table.
     */
    public String getReferenceTableColumnName() {
        return referenceTableColumnName;
    }

    /**
     * Sets the column name on the reference table that is used for grouping and joining.
     * @param referenceTableColumnName Column name on the reference table.
     */
    public void setReferenceTableColumnName(String referenceTableColumnName) {
        this.setDirtyIf(!Objects.equals(this.referenceTableColumnName, referenceTableColumnName));
        this.referenceTableColumnName = referenceTableColumnName;
    }

    /**
     * Creates a data grouping dimension level with a group by the column name on the compared table.
     * @return Data grouping dimension to group on the correct column on the compared table.
     */
    public DataGroupingDimensionSpec createGroupingDimensionForComparedTable() {
        if (Strings.isNullOrEmpty(this.comparedTableColumnName)) {
            return null;
        }
        return DataGroupingDimensionSpec.createForColumn(this.comparedTableColumnName);
    }

    /**
     * Creates a data grouping dimension level with a group by the column name on the reference table.
     * @return Data grouping dimension to group on the correct column on the reference table.
     */
    public DataGroupingDimensionSpec createGroupingDimensionForReferenceTable() {
        if (Strings.isNullOrEmpty(this.referenceTableColumnName)) {
            return null;
        }
        return DataGroupingDimensionSpec.createForColumn(this.referenceTableColumnName);
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
     * Creates and returns a copy of this object.
     */
    @Override
    public TableComparisonGroupingColumnsPairSpec deepClone() {
        TableComparisonGroupingColumnsPairSpec cloned = (TableComparisonGroupingColumnsPairSpec) super.deepClone();
        return cloned;
    }
}
