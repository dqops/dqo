/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.sources;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Identifies a table that is referenced in data quality tests.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTargetSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<TableTargetSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    /**
     * Creates an empty target table specification.
     */
    public TableTargetSpec() {
    }

    /**
     * Creates a table specification given the physical schema name and physical table name.
     * @param schemaName Physical schema name.
     * @param tableName Physical table name.
     */
    public TableTargetSpec(String schemaName, String tableName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    @JsonPropertyDescription("Physical schema name in the target database.")
    private String schemaName;

    @JsonPropertyDescription("Physical table name in the target database.")
    private String tableName;

    @JsonPropertyDescription("Dictionary of additional properties (key/value) that may be used to identify a target table or could be useful when running sensor queries.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, String> properties = new LinkedHashMap<>();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private LinkedHashMap<String, String> originalProperties = new LinkedHashMap<>(); // used to perform comparison in the isDirty check

    /**
     * Returns a schema name in the database.
     * @return Schema name.
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets a schema name where the table is located.
     * @param schemaName Schema name.
     */
    public void setSchemaName(String schemaName) {
		setDirtyIf(!Objects.equals(this.schemaName, schemaName));
        this.schemaName = schemaName;
    }

    /**
     * Returns a table name. This is an unquoted, physical table name used in the target database that is analysed.
     * @return Table name.
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets a table name.
     * @param tableName Table name.
     */
    public void setTableName(String tableName) {
		setDirtyIf(!Objects.equals(this.tableName, tableName));
        this.tableName = tableName;
    }

    /**
     * Returns a key/value map of additional provider specific properties for the table.
     * @return Key/value dictionary of additional properties.
     */
    public LinkedHashMap<String, String> getProperties() {
        return properties;
    }

    /**
     * Sets a dictionary of additional table parameters.
     * @param properties Key/value dictionary with extra parameters.
     */
    public void setProperties(LinkedHashMap<String, String> properties) {
		setDirtyIf(!Objects.equals(this.properties, properties));
        this.properties = properties;
		this.originalProperties = (LinkedHashMap<String, String>) properties.clone();
    }

    /**
     * Checks if the table target is the same as the physical table name given. Schema name and table name are compared.
     * @param physicalTableName Physical table name.
     * @return True when both the schema and table names are equal.
     */
    public boolean equals(PhysicalTableName physicalTableName) {
        if (physicalTableName == null) {
            return false;
        }

        return Objects.equals(this.schemaName, physicalTableName.getSchemaName()) &&
                Objects.equals(this.tableName, physicalTableName.getTableName());
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        return super.isDirty() || !Objects.equals(this.properties, this.originalProperties);
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
        super.clearDirty(propagateToChildren);
		this.originalProperties = (LinkedHashMap<String, String>) this.properties.clone();
    }

    /**
     * Copies a physical table name from the given object.
     * @param physicalTableName Physical table name.
     */
    public void copyFrom(PhysicalTableName physicalTableName) {
		this.setSchemaName(physicalTableName.getSchemaName());
		this.setTableName(physicalTableName.getTableName());
    }

    /**
     * Converts the table target spec to a physical table name (schema name and table name).
     * @return Physical table name.
     */
    public PhysicalTableName toPhysicalTableName() {
        return new PhysicalTableName(this.getSchemaName(), this.getTableName());
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
    public TableTargetSpec clone() {
        try {
            TableTargetSpec cloned = (TableTargetSpec) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }

    /**
     * Creates a cloned and expanded copy of the object. Replaces the schema name and table name if it contains expressions.
     * @param secretValueProvider Secret value provider.
     * @return Expanded and cloned copy of the object.
     */
    public TableTargetSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        TableTargetSpec cloned = this.clone();
        cloned.schemaName = secretValueProvider.expandValue(cloned.schemaName);
        cloned.tableName = secretValueProvider.expandValue(cloned.tableName);
        return cloned;
    }
}
