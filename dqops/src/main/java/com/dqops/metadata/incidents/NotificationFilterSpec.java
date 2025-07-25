/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.incidents;

import com.dqops.checks.CheckType;
import com.dqops.core.incidents.message.IncidentNotificationMessage;
import com.dqops.data.incidents.models.IncidentModel;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.apache.parquet.Strings;

import java.util.Objects;

/**
 * Filter for filtered notifications.
 */
public class NotificationFilterSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<NotificationFilterSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Connection name. Supports search patterns in the format: 'source\\*', '\\*_prod', 'prefix\\*suffix'.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String connection;

    @JsonPropertyDescription("Schema name. This field accepts search patterns in the format: 'schema_name_\\*', '\\*_schema', 'prefix\\*suffix'.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String schema;

    @JsonPropertyDescription("Table name. This field accepts search patterns in the format: 'table_name_\\*', '\\*table', 'prefix\\*suffix'.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String table;

    @JsonPropertyDescription("Table priority.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer tablePriority;

    @JsonPropertyDescription("Data group name. This field accepts search patterns in the format: 'group_name_\\*', '\\*group', 'prefix\\*suffix'.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dataGroupName;

    @JsonPropertyDescription("Quality dimension.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String qualityDimension;

    @JsonPropertyDescription("The target check category, for example: *nulls*, *volume*, *anomaly*.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String checkCategory;

    @JsonPropertyDescription("The target type of checks to run. Supported values are *profiling*, *monitoring* and *partitioned*.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CheckType checkType;

    @JsonPropertyDescription("The target check name to run only this named check. Uses the short check name which is the name of the deepest folder in the *checks* folder. " +
                             "This field supports search patterns such as: 'profiling_\\*', '\\*_count', 'profiling_\\*_percent'.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String checkName;

    @JsonPropertyDescription("Highest severity.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer highestSeverity;

    @JsonIgnore
    private SearchPattern connectionNameSearchPattern;
    @JsonIgnore
    private SearchPattern schemaNameSearchPattern;
    @JsonIgnore
    private SearchPattern tableNameSearchPattern;
    @JsonIgnore
    private SearchPattern dataGroupNameSearchPattern;
    @JsonIgnore
    private SearchPattern checkNameSearchPattern;

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
    public CheckType getCheckType() {
        return checkType;
    }

    /**
     * Sets a check type
     * @param checkType Check type
     */
    public void setCheckType(CheckType checkType) {
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
     * Returns the {@link SearchPattern} related to <code>columnName</code>.
     * Lazy getter, parses <code>columnName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>connectionName</code>.
     */
    @JsonIgnore
    public SearchPattern getConnectionNameSearchPattern() {
        if (connectionNameSearchPattern == null && !Strings.isNullOrEmpty(connection)) {
            connectionNameSearchPattern = SearchPattern.create(false, connection);
        }

        return connectionNameSearchPattern;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>schema</code>.
     * Lazy getter, parses <code>columnName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>schema</code>.
     */
    @JsonIgnore
    public SearchPattern getSchemaNameSearchPattern() {
        if (schemaNameSearchPattern == null && !Strings.isNullOrEmpty(schema)) {
            schemaNameSearchPattern = SearchPattern.create(false, schema);
        }

        return schemaNameSearchPattern;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>table</code>.
     * Lazy getter, parses <code>columnName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>table</code>.
     */
    @JsonIgnore
    public SearchPattern getTableNameSearchPattern() {
        if (tableNameSearchPattern == null && !Strings.isNullOrEmpty(table)) {
            tableNameSearchPattern = SearchPattern.create(false, table);
        }

        return tableNameSearchPattern;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>dataGroupName</code>.
     * Lazy getter, parses <code>columnName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>dataGroupName</code>.
     */
    @JsonIgnore
    public SearchPattern getDataGroupNameSearchPattern() {
        if (dataGroupNameSearchPattern == null && !Strings.isNullOrEmpty(dataGroupName)) {
            dataGroupNameSearchPattern = SearchPattern.create(false, dataGroupName);
        }

        return dataGroupNameSearchPattern;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>checkName</code>.
     * Lazy getter, parses <code>columnName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>checkName</code>.
     */
    @JsonIgnore
    public SearchPattern getCheckNameSearchPattern() {
        if (checkNameSearchPattern == null && !Strings.isNullOrEmpty(checkName)) {
            checkNameSearchPattern = SearchPattern.create(false, checkName);
        }

        return checkNameSearchPattern;
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

    /**
     * Filters a message by the filter parameters and decide if the incident notification message matches the filters.
     * @param message Message to verify.
     * @return True if the message matches the filter parameters.
     */
    public boolean isMatch(IncidentNotificationMessage message) {
        boolean match = (Strings.isNullOrEmpty(this.getConnection()) || this.getConnectionNameSearchPattern().match(message.getConnection())) &&
                (Strings.isNullOrEmpty(this.getSchema()) || this.getSchemaNameSearchPattern().match(message.getSchema())) &&
                (Strings.isNullOrEmpty(this.getTable()) || this.getTableNameSearchPattern().match(message.getTable())) &&
                (this.getTablePriority() == null || this.getTablePriority().equals(message.getTablePriority())) &&
                (Strings.isNullOrEmpty(this.getDataGroupName()) || this.getDataGroupNameSearchPattern().match(message.getDataGroupName())) &&
                (Strings.isNullOrEmpty(this.getQualityDimension()) || this.getQualityDimension().equals(message.getQualityDimension())) &&
                (Strings.isNullOrEmpty(this.getCheckCategory()) || this.getCheckCategory().equals(message.getCheckCategory())) &&
                (this.getCheckType() == null || this.getCheckType().equals(message.getCheckType())) &&
                (Strings.isNullOrEmpty(this.getCheckName()) || this.getCheckNameSearchPattern().match(message.getCheckName())) &&
                (this.getHighestSeverity() == null || this.getHighestSeverity().equals(message.getHighestSeverity()));
        return match;
    }

    /**
     * Filters a message by the filter parameters and decide if the incident notification message matches the filters.
     * @param incidentModel Incident model to verify.
     * @return True if the message matches the filter parameters.
     */
    public boolean isMatch(IncidentModel incidentModel) {
        boolean match = (Strings.isNullOrEmpty(this.getConnection()) || this.getConnectionNameSearchPattern().match(incidentModel.getConnection())) &&
                (Strings.isNullOrEmpty(this.getSchema()) || this.getSchemaNameSearchPattern().match(incidentModel.getSchema())) &&
                (Strings.isNullOrEmpty(this.getTable()) || this.getTableNameSearchPattern().match(incidentModel.getTable())) &&
                (this.getTablePriority() == null || this.getTablePriority().equals(incidentModel.getTablePriority())) &&
                (Strings.isNullOrEmpty(this.getDataGroupName()) || this.getDataGroupNameSearchPattern().match(incidentModel.getDataGroup())) &&
                (Strings.isNullOrEmpty(this.getQualityDimension()) || this.getQualityDimension().equals(incidentModel.getQualityDimension())) &&
                (Strings.isNullOrEmpty(this.getCheckCategory()) || this.getCheckCategory().equals(incidentModel.getCheckCategory())) &&
                (this.getCheckType() == null || this.getCheckType().equals(incidentModel.getCheckType())) &&
                (Strings.isNullOrEmpty(this.getCheckName()) || this.getCheckNameSearchPattern().match(incidentModel.getCheckName())) &&
                (this.getHighestSeverity() == null || this.getHighestSeverity().equals(incidentModel.getHighestSeverity()));
        return match;
    }
}
