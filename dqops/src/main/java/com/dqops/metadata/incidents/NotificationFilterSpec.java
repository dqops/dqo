package com.dqops.metadata.incidents;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

// todo:
@Data
public class NotificationFilterSpec extends AbstractSpec implements Cloneable, InvalidYamlStatusHolder {
    private static final ChildHierarchyNodeFieldMapImpl<NotificationFilterSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    private String connection;
    private String schema;
    private String table;
    private Integer tablePriority;
    private String dataGroupName;
    private String qualityDimension;
    private String checkCategory;
    private String checkType;
    private String checkName;
    private Integer highestSeverity;

    @JsonIgnore
    private String yamlParsingError;

    // todo: add search patterns?, see CheckSearchFilters

    // todo: getters setters

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

    // todo getters and setters

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
    public IncidentNotificationSpec deepClone() {
        IncidentNotificationSpec cloned = (IncidentNotificationSpec) super.deepClone();
        // todo fields?
        return cloned;
    }

}
