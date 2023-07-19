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
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Identifies a data comparison configuration between a parent table (the compared table) and the target table from another data source (a reference table).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class TableComparisonConfigurationSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<TableComparisonConfigurationSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The name of the data grouping configuration on the parent table (the compared table) that will be used for comparison. " +
            "When not provided, uses the 'default' grouping name. When the parent table has no data grouping configurations, compares the whole table without grouping.")
    private String comparedTableGroupingName = DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME;

    @JsonPropertyDescription("The name of the data grouping configuration on the referenced name that will be used for comparison. " +
            "When not provided, uses the 'default' grouping name. When the reference table has no data grouping configurations, compares the whole table without grouping. " +
            "The data grouping configurations on the parent table and the reference table must have the same grouping dimension levels configured, but the configuration (the names of the columns) could be different.")
    private String referenceTableGroupingName = DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME;

    @JsonPropertyDescription("The name of the connection in DQO where the reference table (the source of truth) is configured. " +
                             "When the connection name is not provided, DQO will find the reference table on the connection of the parent table.")
    private String referenceTableConnectionName;

    @JsonPropertyDescription("The name of the schema where the reference table is imported into DQO. The reference table's metadata must be imported into DQO.")
    private String referenceTableSchemaName;

    @JsonPropertyDescription("The name of the reference table that is imported into DQO. The reference table's metadata must be imported into DQO.")
    private String referenceTableName;

    /**
     * Returns the data grouping configuration name on the parent table.
     * @return Grouping configuration name.
     */
    public String getComparedTableGroupingName() {
        return comparedTableGroupingName;
    }

    /**
     * Sets the name of the data grouping configuration name for the parent (compared) table.
     * @param comparedTableGroupingName Data grouping configuration name.
     */
    public void setComparedTableGroupingName(String comparedTableGroupingName) {
        this.setDirtyIf(!Objects.equals(this.comparedTableGroupingName, comparedTableGroupingName));
        this.comparedTableGroupingName = comparedTableGroupingName;
    }

    /**
     * Returns the name of the data grouping configuration on the reference table (the source of truth).
     * @return The data grouping configuration name.
     */
    public String getReferenceTableGroupingName() {
        return referenceTableGroupingName;
    }

    /**
     * Sets the name of the data grouping configuration on the reference table.
     * @param referenceTableGroupingName Data grouping configuration name.
     */
    public void setReferenceTableGroupingName(String referenceTableGroupingName) {
        this.setDirtyIf(!Objects.equals(this.referenceTableGroupingName, referenceTableGroupingName));
        this.referenceTableGroupingName = referenceTableGroupingName;
    }

    /**
     * Returns the name of the connection (data source) in DQO where the reference table is imported.
     * @return The name of the connection of the reference table.
     */
    public String getReferenceTableConnectionName() {
        return referenceTableConnectionName;
    }

    /**
     * Sets the name of the connection where the reference table is imported.
     * @param referenceTableConnectionName The name of the connection where the reference table is imported.
     */
    public void setReferenceTableConnectionName(String referenceTableConnectionName) {
        this.setDirtyIf(!Objects.equals(this.referenceTableConnectionName, referenceTableConnectionName));
        this.referenceTableConnectionName = referenceTableConnectionName;
    }

    /**
     * Returns the name of the schema where the reference table was imported.
     * @return The name of the schema with the reference table.
     */
    public String getReferenceTableSchemaName() {
        return referenceTableSchemaName;
    }

    /**
     * Sets the name of the schema with the reference table.
     * @param referenceTableSchemaName The name of the schema.
     */
    public void setReferenceTableSchemaName(String referenceTableSchemaName) {
        this.setDirtyIf(!Objects.equals(this.referenceTableSchemaName, referenceTableSchemaName));
        this.referenceTableSchemaName = referenceTableSchemaName;
    }

    /**
     * Returns the reference table name inside the schema.
     * @return The name of the reference table.
     */
    public String getReferenceTableName() {
        return referenceTableName;
    }

    /**
     * Sets the name of the reference table inside the schema.
     * @param referenceTableName Reference table name.
     */
    public void setReferenceTableName(String referenceTableName) {
        this.setDirtyIf(!Objects.equals(this.referenceTableName, referenceTableName));
        this.referenceTableName = referenceTableName;
    }

    /**
     * Retrieves the comparison name from the parent hierarchy. Works only when the comparison specification was added to the comparison's map under a comparison name.
     * @return Comparison name.
     */
    @JsonIgnore
    public String getComparisonName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }

        return hierarchyId.getLast().toString();
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
    public TableComparisonConfigurationSpec deepClone() {
        TableComparisonConfigurationSpec cloned = (TableComparisonConfigurationSpec) super.deepClone();
        return cloned;
    }
}
