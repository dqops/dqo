package com.dqops.metadata.storage.localfiles.webhooks;

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.incidents.IncidentWebhookNotificationsSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Default incident webhook notification spec wrapper.
 */
public class DefaultIncidentWebhookNotificationsWrapperImpl extends AbstractElementWrapper<String, IncidentWebhookNotificationsSpec> implements DefaultIncidentWebhookNotificationsWrapper {

    @JsonIgnore
    private final static String NAME = "default_notification_webhooks";

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return NAME;
    }

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    @Override
    public void flush() {
        super.flush();
    }

    /**
     * Returns the child map on the spec class with all fields.
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
        return null;
    }

}
