package com.dqops.metadata.incidents;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// todo: description
@Data // todo remove
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


    // todo
    private NotificationFilterSpec notificationFilter;

    // todo
    private IncidentNotificationSpec notificationTarget;

    // todo
    private Integer priority = 1000;

    // todo
    private Boolean processAdditionalFilters = false;

    // todo: getters, setters


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
        // todo
        return cloned;
    }

}
