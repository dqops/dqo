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
            put("notification_filter", o -> o.notificationFilter);
            put("notification_target", o -> o.notificationTarget);
        }
    };

    @JsonPropertyDescription("Notification filter specification for filtering the incident by the values of its fields.")
    private NotificationFilterSpec notificationFilter;

    @JsonPropertyDescription("Notification target addresses for each of the status.")
    private IncidentNotificationSpec notificationTarget;

    @JsonPropertyDescription("The priority of the notification. Notifications are sent to the first notification targets that matches the filters when processAdditionalFilters is not set.")
    private Integer priority = 1000;

    @JsonPropertyDescription("Flag to break sending next notifications. Setting to true allows to send next notification from the list in priority order that matches the filter.")
    private Boolean processAdditionalFilters = false;

    /**
     * Returns a notification filter.
     * @return Notification filter.
     */
    public NotificationFilterSpec getNotificationFilter() {
        return notificationFilter;
    }

    /**
     * Sets a notification filter.
     * @param notificationFilter Notification filter.
     */
    public void setNotificationFilter(NotificationFilterSpec notificationFilter) {
        setDirtyIf(!Objects.equals(this.notificationFilter, notificationFilter));
        this.notificationFilter = notificationFilter;
    }

    /**
     * Returns an incident notification target.
     * @return Incident notification target.
     */
    public IncidentNotificationSpec getNotificationTarget() {
        return notificationTarget;
    }

    /**
     * Sets an incident notification target.
     * @param notificationTarget Incident notification target.
     */
    public void setNotificationTarget(IncidentNotificationSpec notificationTarget) {
        setDirtyIf(!Objects.equals(this.notificationTarget, notificationTarget));
        this.notificationTarget = notificationTarget;
    }

    /**
     * Returns a notification priority.
     * @return Notification priority.
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Sets a notification priority.
     * @param priority Notification priority.
     */
    public void setPriority(Integer priority) {
        setDirtyIf(!Objects.equals(this.priority, priority));
        this.priority = priority;
    }

    /**
     * Returns a process additional filters flag.
     * @return process additional filters flag.
     */
    public Boolean getProcessAdditionalFilters() {
        return processAdditionalFilters;
    }

    /**
     * Sets a process additional filters flag.
     * @param processAdditionalFilters Process additional filters flag.
     */
    public void setProcessAdditionalFilters(Boolean processAdditionalFilters) {
        setDirtyIf(!Objects.equals(this.processAdditionalFilters, processAdditionalFilters));
        this.processAdditionalFilters = processAdditionalFilters;
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
        cloned.notificationFilter = cloned.notificationFilter.deepClone();
        cloned.notificationTarget = cloned.notificationTarget.deepClone();
        return cloned;
    }

}
