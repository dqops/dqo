package com.dqops.metadata.incidents;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
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

    @JsonPropertyDescription("Description.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

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
    public boolean getProcessAdditionalFilters() {
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
    public boolean getDisabled() {
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
     * Returns a description.
     * @return Description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a description.
     * @param description Description.
     */
    public void setDescription(String description) {
        setDirtyIf(!Objects.equals(this.description, description));
        this.description = description;
    }

    /**
     * Returns a doNotCreateIncidents flag.
     * @return doNotCreateIncidents flag.
     */
    public boolean getDoNotCreateIncidents() {
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

}