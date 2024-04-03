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
package com.dqops.metadata.groupings;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.fields.ControlType;
import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.parquet.Strings;

import java.util.Objects;

/**
 * Single data grouping dimension configuration. A data grouping dimension may be configured as a hardcoded value or a mapping to a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class DataGroupingDimensionSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<DataGroupingDimensionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The source of the data grouping dimension value. The default source of the grouping dimension is a tag. The tag should be assigned when there are many similar tables that store the same data for different areas (countries, etc.). It can be the name of the country if the table or partition stores information for that country.")
    private DataGroupingDimensionSource source = DataGroupingDimensionSource.tag;

    @JsonPropertyDescription("The value assigned to the data quality grouping dimension when the source is 'tag'. Assign a hard-coded (static) value to the data grouping dimension (tag) when there are multiple similar tables storing the same data for different areas (countries, etc.). This can be the name of the country if the table or partition stores information for that country.")
    private String tag;

    @JsonPropertyDescription("Column name that contains a dynamic data grouping dimension value (for dynamic data-driven data groupings). Sensor queries will be extended with a GROUP BY {data group level colum name}, sensors (and alerts) will be calculated for each unique value of the specified column. Also a separate time series will be tracked for each value.")
    @ControlType(ParameterDataType.column_name_type)
    private String column;

    @JsonPropertyDescription("Data grouping dimension name.")
    private String name;

    /**
     * Creates a new data grouping dimensionobject for a tag (static value assigned to all data quality results).
     * @param tag Tag value.
     * @return Data grouping dimension specification for a tag.
     */
    public static DataGroupingDimensionSpec createForTag(String tag) {
        DataGroupingDimensionSpec dataGroupingDimensionSpec = new DataGroupingDimensionSpec();
        dataGroupingDimensionSpec.setSource(DataGroupingDimensionSource.tag);
        dataGroupingDimensionSpec.setTag(tag);
        return dataGroupingDimensionSpec;
    }

    /**
     * Creates a new data grouping dimension object for a column, the queries will apply a GROUP BY clause over the given column for data segmentation.
     * @param column Column name.
     * @return Data grouping dimension specification for a column.
     */
    public static DataGroupingDimensionSpec createForColumn(String column) {
        DataGroupingDimensionSpec dataGroupingDimensionSpec = new DataGroupingDimensionSpec();
        dataGroupingDimensionSpec.setSource(DataGroupingDimensionSource.column_value);
        dataGroupingDimensionSpec.setColumn(column);
        return dataGroupingDimensionSpec;
    }

    /**
     * Data grouping dimension value source.
     * @return Gets the data grouping dimension value source.
     */
    public DataGroupingDimensionSource getSource() {
        return source;
    }

    /**
     * Sets the source of the data grouping dimension values. A data grouping dimension can be a static value or a dynamic value, returned from the data.
     * @param source Data grouping dimension source type.
     */
    public void setSource(DataGroupingDimensionSource source) {
		setDirtyIf(!Objects.equals(this.source, source));
        this.source = source;
    }

    /**
     * Returns a static value assigned to a data grouping dimension.
     * @return Static data grouping dimension value.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets a new static value of the data grouping dimension.
     * @param tag Static data grouping dimension value.
     */
    public void setTag(String tag) {
		setDirtyIf(!Objects.equals(this.tag, tag));
        this.tag = tag;
    }

    /**
     * Gets the column name that is added to the GROUP BY clause. Sensor results retrieved for each grouping (each unique value of the column) is tagged with the value of the column.
     * @return Column name used to make a dynamic data grouping dimension.
     */
    public String getColumn() {
        return column;
    }

    /**
     * Sets a name of the column used to populate a dynamic data grouping dimension.
     * @param column Column name.
     */
    public void setColumn(String column) {
		setDirtyIf(!Objects.equals(this.column, column));
        this.column = column;
    }

    /**
     * Returns a descriptive name of the data grouping dimension.
     * @return Data grouping dimension name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new data grouping dimension name.
     * @param name New data grouping dimension name.
     */
    public void setName(String name) {
        setDirtyIf(!Objects.equals(this.name, name));
        this.name = name;
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
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public DataGroupingDimensionSpec deepClone() {
        DataGroupingDimensionSpec cloned = (DataGroupingDimensionSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a cloned and expanded version of the objects. All parameters are changed to the values expanded from variables like ${ENV_VAR}.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret value lookup context.
     * @return Cloned and expanded copy of the object.
     */
    public DataGroupingDimensionSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        DataGroupingDimensionSpec cloned = this.deepClone();
        cloned.tag = cloned.source == DataGroupingDimensionSource.tag ? secretValueProvider.expandValue(cloned.tag, lookupContext) : null;
        cloned.column = cloned.source == DataGroupingDimensionSource.column_value ? secretValueProvider.expandValue(cloned.column, lookupContext) : null;
        return cloned;
    }

    /**
     * Creates a clone of this data grouping dimension configuration to be used by the Jinja2 renderer, but only if the data grouping mapping references a column and the column is not empty.
     * @return A clone of this object if it is a valid data grouping dimension configuration that can be used in a Jinja2 template or a null value if the data grouping configuration
     * is a tag or the column name is not provided.
     */
    public DataGroupingDimensionSpec truncateForSqlRendering() {
        if (this.source != DataGroupingDimensionSource.column_value || Strings.isNullOrEmpty(this.column)) {
            return null;
        }

        return this.deepClone();
    }

    public static class DataGroupingDimensionSpecSampleFactory implements SampleValueFactory<DataGroupingDimensionSpec> {
        @Override
        public DataGroupingDimensionSpec createSample() {
            return createForColumn(SampleStringsRegistry.getColumnName());
        }
    }
}
