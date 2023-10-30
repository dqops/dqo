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

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.docs.SampleStringsRegistry;
import com.dqops.utils.docs.SampleValueFactory;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
            put("grouping_columns", o -> o.groupingColumns);
        }
    };

    @JsonPropertyDescription("The name of the connection in DQOp where the reference table (the source of truth) is configured. " +
                             "When the connection name is not provided, DQOps will find the reference table on the connection of the parent table.")
    private String referenceTableConnectionName;

    @JsonPropertyDescription("The name of the schema where the reference table is imported into DQOps. The reference table's metadata must be imported into DQOps.")
    private String referenceTableSchemaName;

    @JsonPropertyDescription("The name of the reference table that is imported into DQOps. The reference table's metadata must be imported into DQOps.")
    private String referenceTableName;

    @JsonPropertyDescription("Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the compared table. " +
            "This expression must be a SQL expression that will be added to the WHERE clause when querying the compared table.")
    private String comparedTableFilter;

    @JsonPropertyDescription("Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the reference table (the source of truth). " +
            "This expression must be a SQL expression that will be added to the WHERE clause when querying the reference table.")
    private String referenceTableFilter;

    @JsonPropertyDescription("The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is 'profiling'.")
    private CheckType checkType = CheckType.profiling;

    @JsonPropertyDescription("The time scale that this check comparison configuration is applicable. Supported values are 'daily' and 'monthly' for monitoring and partitioned checks or an empty value for profiling checks.")
    private CheckTimeScale timeScale;

    @JsonPropertyDescription("List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  " +
            "for grouping both the compared table and the reference table (the source of truth). " +
            "The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableComparisonGroupingColumnsPairsListSpec groupingColumns = new TableComparisonGroupingColumnsPairsListSpec();


    /**
     * Returns the name of the connection (data source) in DQOps where the reference table is imported.
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
     * Returns the SQL expression that is added to the sensor query on the compared table.
     * @return SQL filter for the compared table.
     */
    public String getComparedTableFilter() {
        return comparedTableFilter;
    }

    /**
     * Sets the sql query that is applied on the compared table.
     * @param comparedTableFilter SQL filter applied on the compared table.
     */
    public void setComparedTableFilter(String comparedTableFilter) {
        this.setDirtyIf(!Objects.equals(this.comparedTableFilter, comparedTableFilter));
        this.comparedTableFilter = comparedTableFilter;
    }

    /**
     * Returns the SQL expression that is added to the sensor query on the reference table.
     * @return SQL filter for the reference table.
     */
    public String getReferenceTableFilter() {
        return referenceTableFilter;
    }

    /**
     * Sets the SQL query added to the query on the reference table.
     * @param referenceTableFilter SQL query for the reference table.
     */
    public void setReferenceTableFilter(String referenceTableFilter) {
        this.setDirtyIf(!Objects.equals(this.referenceTableFilter, referenceTableFilter));
        this.referenceTableFilter = referenceTableFilter;
    }

    /**
     * Returns the check type that this table comparison configuration is applicable to.
     * @return The type of checks where this comparison is used.
     */
    public CheckType getCheckType() {
        return checkType;
    }

    /**
     * Sets the type of checks where this comparison is used.
     * @param checkType Type of checks.
     */
    public void setCheckType(CheckType checkType) {
        this.setDirtyIf(!Objects.equals(this.checkType, checkType));
        this.checkType = checkType;
    }

    /**
     * Returns the time scale that this table comparison is applicable to.
     * @return Time scale.
     */
    public CheckTimeScale getTimeScale() {
        return timeScale;
    }

    /**
     * Sets the time scale of the checks (monitoring, partitioned) that are used.
     * @param timeScale Time scale.
     */
    public void setTimeScale(CheckTimeScale timeScale) {
        this.setDirtyIf(!Objects.equals(this.timeScale, timeScale));
        this.timeScale = timeScale;
    }

    /**
     * Returns the list of grouping column pairs used to group results before matching.
     * @return A list of column pairs used for grouping and matching.
     */
    public TableComparisonGroupingColumnsPairsListSpec getGroupingColumns() {
        return groupingColumns;
    }

    /**
     * Sets a list of columns used for grouping and matching.
     * @param groupingColumns A new list of column pairs.
     */
    public void setGroupingColumns(TableComparisonGroupingColumnsPairsListSpec groupingColumns) {
        setDirtyIf(!Objects.equals(this.groupingColumns, groupingColumns));
        this.groupingColumns = groupingColumns;
        propagateHierarchyIdToField(groupingColumns, "grouping_columns");
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

    public static class TableComparisonConfigurationSpecSampleFactory implements SampleValueFactory<TableComparisonConfigurationSpec> {
        @Override
        public TableComparisonConfigurationSpec createSample() {
            return new TableComparisonConfigurationSpec() {{
                setReferenceTableConnectionName(SampleStringsRegistry.getConnectionName());
                setReferenceTableSchemaName(SampleStringsRegistry.getSchemaName());
                setReferenceTableName(SampleStringsRegistry.getTableName());
            }};
        }
    }
}
