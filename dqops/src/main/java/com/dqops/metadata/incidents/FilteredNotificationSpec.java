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

import com.dqops.data.incidents.models.IncidentFilteredNotificationLocation;
import com.dqops.metadata.basespecs.AbstractSpec;
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
 * Notification with filters that is sent only if values in notification message match the filters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class FilteredNotificationSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<FilteredNotificationSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("filter", o -> o.filter);
            put("target", o -> o.target);
        }
    };

    @JsonPropertyDescription("Notification filter specification for filtering the incident by the values of its fields.")
    private NotificationFilterSpec filter;

    @JsonPropertyDescription("Notification target addresses for each of the status.")
    private IncidentNotificationTargetSpec target;

    @JsonPropertyDescription("The priority of the notification. Notifications are sent to the first notification targets that matches the filters when processAdditionalFilters is not set.")
    private int priority = 1000;

    @JsonPropertyDescription("Flag to break sending next notifications. Setting to true allows to send next notification from the list in priority order that matches the filter.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean processAdditionalFilters = false;

    @JsonPropertyDescription("Flag to turn off the notification filter.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled = false;

    @JsonPropertyDescription("Message with the details of the filtered notification such as purpose explanation, SLA note, etc.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonPropertyDescription("Flag to remove incident that match the filters.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean doNotCreateIncidents = false;

    /**
     * Returns a notification filter.
     * @return Notification filter.
     */
    public NotificationFilterSpec getFilter() {
        return filter;
    }

    /**
     * Sets a notification filter.
     * @param filter Notification filter.
     */
    public void setFilter(NotificationFilterSpec filter) {
        setDirtyIf(!Objects.equals(this.filter, filter));
        this.filter = filter;
        propagateHierarchyIdToField(filter, "filter");
    }

    /**
     * Returns an incident notification target.
     * @return Incident notification target.
     */
    public IncidentNotificationTargetSpec getTarget() {
        return target;
    }

    /**
     * Sets an incident notification target.
     * @param target Incident notification target.
     */
    public void setTarget(IncidentNotificationTargetSpec target) {
        setDirtyIf(!Objects.equals(this.target, target));
        this.target = target;
        propagateHierarchyIdToField(target, "target");
    }

    /**
     * Returns a notification priority.
     * @return Notification priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets a notification priority.
     * @param priority Notification priority.
     */
    public void setPriority(int priority) {
        setDirtyIf(!Objects.equals(this.priority, priority));
        this.priority = priority;
    }

    /**
     * Returns a process additional filters flag.
     * @return Process additional filters flag.
     */
    public boolean isProcessAdditionalFilters() {
        return processAdditionalFilters;
    }

    /**
     * Sets a process additional filters flag.
     * @param processAdditionalFilters Process additional filters flag.
     */
    public void setProcessAdditionalFilters(boolean processAdditionalFilters) {
        setDirtyIf(!Objects.equals(this.processAdditionalFilters, processAdditionalFilters));
        this.processAdditionalFilters = processAdditionalFilters;
    }

    /**
     * Returns a disabled flag.
     * @return Disabled flag.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets a disabled flag.
     * @param disabled Disabled flag.
     */
    public void setDisabled(boolean disabled) {
        setDirtyIf(!Objects.equals(this.disabled, disabled));
        this.disabled = disabled;
    }

    /**
     * Returns a message.
     * @return Message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets a message.
     * @param message Message.
     */
    public void setMessage(String message) {
        setDirtyIf(!Objects.equals(this.message, message));
        this.message = message;
    }

    /**
     * Returns a doNotCreateIncidents flag.
     * @return doNotCreateIncidents flag.
     */
    public boolean isDoNotCreateIncidents() {
        return doNotCreateIncidents;
    }

    /**
     * Sets a doNotCreateIncidents flag.
     * @param doNotCreateIncidents doNotCreateIncidents flag.
     */
    public void setDoNotCreateIncidents(boolean doNotCreateIncidents) {
        setDirtyIf(!Objects.equals(this.doNotCreateIncidents, doNotCreateIncidents));
        this.doNotCreateIncidents = doNotCreateIncidents;
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
    public FilteredNotificationSpec deepClone() {
        FilteredNotificationSpec cloned = (FilteredNotificationSpec) super.deepClone();
        return cloned;
    }

    /**
     * Retrieves the notification name from the hierarchy.
     * @return Notification name or null for a standalone notification spec object.
     */
    @JsonIgnore
    public String getNotificationName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        return hierarchyId.get(hierarchyId.size() - 1).toString();
    }

    /**
     * Detects where the notification is configured. Is it on the global level, or a connection level.
     * @return Identification of the location where the notification was found, a global level or a connection level.
     */
    @JsonIgnore
    public IncidentFilteredNotificationLocation getNotificationLocation() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }

        String connectionName = hierarchyId.getConnectionName();
        if (connectionName != null) {
            return IncidentFilteredNotificationLocation.connection;
        }

        return IncidentFilteredNotificationLocation.global;
    }
}
