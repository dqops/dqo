/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.lineage;

import com.dqops.metadata.basespecs.*;
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
 * Data lineage specification for a table to identify a source table of the current table where this object is stored.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableLineageSourceSpec extends AbstractSpec implements ObjectName<TableLineageSource>, Flushable, InstanceStatusTracking {
    private static final ChildHierarchyNodeFieldMapImpl<TableLineageSourceSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("columns", o -> o.columns);
        }
    };

    @JsonPropertyDescription("The name of a source connection that is defined in DQOps and contains a source table from which the current table receives data.")
    private String sourceConnection = "";

    @JsonPropertyDescription("The name of a source schema within the source connection that contains a source table from which the current table receives data.")
    private String sourceSchema = "";

    @JsonPropertyDescription("The name of a source table in the source schema from which the current table receives data.")
    private String sourceTable = "";

    @JsonPropertyDescription("The name of a source tool from which this data lineage information was copied. This field should be filled when the data lineage was imported from another data catalog or a data lineage tracking platform.")
    private String dataLineageSourceTool;

    @JsonPropertyDescription("A dictionary of mapping properties stored as a key/value dictionary. Data lineage synchronization tools that are importing data lineage mappings from external data lineage sources can use it to store mapping information.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> properties;

    @JsonPropertyDescription("Configuration of source columns for each column in the current table. The keys in this dictionary are column names in the current table. The object stored in the dictionary contain a list of source columns.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnLineageSourceSpecMap columns = new ColumnLineageSourceSpecMap();

    /**
     * Returns the name of a connection in DQOps that contains a source table.
     * @return Connection name.
     */
    public String getSourceConnection() {
        return sourceConnection;
    }

    /**
     * Sets the name of a source connection in DQOps that contains the source table.
     * @param sourceConnection Source connection name.
     */
    public void setSourceConnection(String sourceConnection) {
        this.setDirtyIf(!Objects.equals(this.sourceConnection, sourceConnection));
        this.sourceConnection = sourceConnection;
    }

    /**
     * Returns the name of a source schema in the source connection that contains the source table.
     * @return Schema name.
     */
    public String getSourceSchema() {
        return sourceSchema;
    }

    /**
     * Sets the name of a source schema that contains a source table.
     * @param sourceSchema Source schema name.
     */
    public void setSourceSchema(String sourceSchema) {
        this.setDirtyIf(!Objects.equals(this.sourceSchema, sourceSchema));
        this.sourceSchema = sourceSchema;
    }

    /**
     * Returns the name of a source table.
     * @return Source table name.
     */
    public String getSourceTable() {
        return sourceTable;
    }

    /**
     * Sets the name of a source table.
     * @param sourceTable Source table name.
     */
    public void setSourceTable(String sourceTable) {
        this.setDirtyIf(!Objects.equals(this.sourceTable, sourceTable));
        this.sourceTable = sourceTable;
    }

    /**
     * Returns a name of a source data lineage tool from which the data was loaded.
     * @return Data lineage tool name.
     */
    public String getDataLineageSourceTool() {
        return dataLineageSourceTool;
    }

    /**
     * Sets the name of a data lineage tool.
     * @param dataLineageSourceTool Data lineage tool name.
     */
    public void setDataLineageSourceTool(String dataLineageSourceTool) {
        this.setDirtyIf(!Objects.equals(this.dataLineageSourceTool, dataLineageSourceTool));
        this.dataLineageSourceTool = dataLineageSourceTool;
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
     * Returns a dictionary of source column mappings.
     * @return Dictionary of source column mappings.
     */
    public ColumnLineageSourceSpecMap getColumns() {
        return columns;
    }

    /**
     * Sets the dictionary of source columns.
     * @param columns Source columns.
     */
    public void setColumns(ColumnLineageSourceSpecMap columns) {
        setDirtyIf(!Objects.equals(this.columns, columns));
        this.columns = columns;
        propagateHierarchyIdToField(columns, "columns");
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
    public TableLineageSourceSpec deepClone() {
        TableLineageSourceSpec cloned = (TableLineageSourceSpec) super.deepClone();
        return cloned;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     * Build the object from the names of the source connection, schema, and table.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public TableLineageSource getObjectName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return new TableLineageSource(this.sourceConnection, this.sourceSchema, this.sourceTable);
        }

        return (TableLineageSource)hierarchyId.getLast();
    }

    /**
     * Sets the object name of the target connection, schema and table. Replaces the values of the source connection, source schema, and source table names.
     * @param objectName Object name with new values.
     */
    @JsonIgnore
    public void setObjectName(TableLineageSource objectName) {
        if (objectName == null) {
            return;
        }

        this.setSourceConnection(objectName.getConnection());
        this.setSourceSchema(objectName.getSchema());
        this.setSourceTable(objectName.getTable());
    }

    /**
     * Flushes an object to a persistent store.
     */
    @Override
    public void flush() {
        // does nothing, just required
    }

    /**
     * Returns the status of the node.
     *
     * @return Object status (added, modified, deleted, etc).
     */
    @Override
    @JsonIgnore
    public InstanceStatus getStatus() {
        return InstanceStatus.MODIFIED; // not important
    }

    /**
     * Changes the status of the model.
     *
     * @param status New status.
     */
    @Override
    @JsonIgnore
    public void setStatus(InstanceStatus status) {
        // not tracked
    }

    /**
     * Marks the object for deletion. The status changes to {@link InstanceStatus#TO_BE_DELETED}.
     */
    @Override
    public void markForDeletion() {
        // not tracked
    }
}
