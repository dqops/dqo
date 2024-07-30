package com.dqops.metadata.incidents;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.Objects;

/**
 * Filter for filtered notifications.
 */
public class NotificationFilterSpec extends AbstractSpec implements Cloneable, InvalidYamlStatusHolder {
    private static final ChildHierarchyNodeFieldMapImpl<NotificationFilterSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Connection name.")
    private String connection;

    @JsonPropertyDescription("Schema name.")
    private String schema;

    @JsonPropertyDescription("Table name.")
    private String table;

    @JsonPropertyDescription("Table priority.")
    private Integer tablePriority;

    @JsonPropertyDescription("Data group name.")
    private String dataGroupName;

    @JsonPropertyDescription("Quality dimension.")
    private String qualityDimension;

    @JsonPropertyDescription("Check category.")
    private String checkCategory;

    @JsonPropertyDescription("Check type.")
    private String checkType;

    @JsonPropertyDescription("Check name.")
    private String checkName;

    @JsonPropertyDescription("Highest severity.")
    private Integer highestSeverity;

    @JsonIgnore
    private String yamlParsingError;

    // todo: add search patterns?, see CheckSearchFilters

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Returns the YAML parsing error that was captured.
     *
     * @return YAML parsing error.
     */
    @Override
    public String getYamlParsingError() {
        return this.yamlParsingError;
    }

    /**
     * Returns a connection name
     * @return Schema connection
     */
    public String getConnection() {
        return connection;
    }

    /**
     * Sets a connection name
     * @param connection Connection name
     */
    public void setConnection(String connection) {
        this.setDirtyIf(!Objects.equals(this.connection, connection));
        this.connection = connection;
    }

    /**
     * Returns a schema name
     * @return Schema name
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets a schema name
     * @param schema Schema name
     */
    public void setSchema(String schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
    }

    /**
     * Returns a table name
     * @return Table name
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets a table name
     * @param table Table name
     */
    public void setTable(String table) {
        this.setDirtyIf(!Objects.equals(this.table, table));
        this.table = table;
    }

    /**
     * Returns a table priority
     * @return Table priority
     */
    public Integer getTablePriority() {
        return tablePriority;
    }

    /**
     * Sets a table priority
     * @param tablePriority Table priority
     */
    public void setTablePriority(Integer tablePriority) {
        this.setDirtyIf(!Objects.equals(this.tablePriority, tablePriority));
        this.tablePriority = tablePriority;
    }

    /**
     * Returns a data group name
     * @return Data group name
     */
    public String getDataGroupName() {
        return dataGroupName;
    }

    /**
     * Sets a data group name
     * @param dataGroupName Data group name
     */
    public void setDataGroupName(String dataGroupName) {
        this.setDirtyIf(!Objects.equals(this.dataGroupName, dataGroupName));
        this.dataGroupName = dataGroupName;
    }

    /**
     * Returns a quality dimension
     * @return Quality dimension
     */
    public String getQualityDimension() {
        return qualityDimension;
    }

    /**
     * Sets a quality dimension
     * @param qualityDimension Quality dimension
     */
    public void setQualityDimension(String qualityDimension) {
        this.setDirtyIf(!Objects.equals(this.qualityDimension, qualityDimension));
        this.qualityDimension = qualityDimension;
    }

    /**
     * Returns a check category
     * @return Check category
     */
    public String getCheckCategory() {
        return checkCategory;
    }

    /**
     * Sets a check category
     * @param checkCategory Check category
     */
    public void setCheckCategory(String checkCategory) {
        this.setDirtyIf(!Objects.equals(this.checkCategory, checkCategory));
        this.checkCategory = checkCategory;
    }

    /**
     * Returns a check type
     * @return Check type
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * Sets a check type
     * @param checkType Check type
     */
    public void setCheckType(String checkType) {
        this.setDirtyIf(!Objects.equals(this.checkType, checkType));
        this.checkType = checkType;
    }

    /**
     * Returns a check name
     * @return Check name
     */
    public String getCheckName() {
        return checkName;
    }

    /**
     * Sets a check name
     * @param checkName Check name
     */
    public void setCheckName(String checkName) {
        this.setDirtyIf(!Objects.equals(this.checkName, checkName));
        this.checkName = checkName;
    }

    /**
     * Returns the highest severity
     * @return Highest severity
     */
    public Integer getHighestSeverity() {
        return highestSeverity;
    }

    /**
     * Sets the highest severity
     * @param highestSeverity Highest severity
     */
    public void setHighestSeverity(Integer highestSeverity) {
        this.setDirtyIf(!Objects.equals(this.highestSeverity, highestSeverity));
        this.highestSeverity = highestSeverity;
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
    public NotificationFilterSpec deepClone() {
        NotificationFilterSpec cloned = (NotificationFilterSpec) super.deepClone();
        return cloned;
    }

}
