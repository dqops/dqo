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

package com.dqops.metadata.lineage;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Describes the list of source columns for a column in the current table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnLineageSourceSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<ColumnLineageSourceSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("source_columns", o -> o.sourceColumns);
        }
    };

    @JsonPropertyDescription("A list of source columns from the source table name from which this column receives data.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private SourceColumnsSetSpec sourceColumns = new SourceColumnsSetSpec();

    @JsonPropertyDescription("A dictionary of mapping properties stored as a key/value dictionary. Data lineage synchronization tools that are importing data lineage mappings from external data lineage sources can use it to store mapping information.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> properties;

    /**
     * Returns a list of source column names.
     * @return Source column names.
     */
    public SourceColumnsSetSpec getSourceColumns() {
        return sourceColumns;
    }

    /**
     * Sets a list of source column names.
     * @param sourceColumns Source column names.
     */
    public void setSourceColumns(SourceColumnsSetSpec sourceColumns) {
        setDirtyIf(!Objects.equals(this.sourceColumns, sourceColumns));
        this.sourceColumns = sourceColumns;
        propagateHierarchyIdToField(sourceColumns, "source_columns");
    }

    /**
     * Sets a map of additional properties for mapping data lineage to external tools.
     * @return Map of custom properties used by external data lineage synchronization tools.
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Sets a reference to dictionary with extra parameters used for mapping data lineage to other tools.
     * @param properties Properties as a dictionary.
     */
    public void setProperties(Map<String, String> properties) {
        setDirtyIf(!Objects.equals(this.properties, properties));
        this.properties = properties != null ? Collections.unmodifiableMap(properties) : null;
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
    public ColumnLineageSourceSpec deepClone() {
        ColumnLineageSourceSpec cloned = (ColumnLineageSourceSpec) super.deepClone();
        return cloned;
    }

    /**
     * Returns the name of the target column. It is the key name under which this object is stored in the parent dictionary.
     * @return Target column name.
     */
    @JsonIgnore
    public String getTargetColumnName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }

        return (String)hierarchyId.getLast();
    }
}
