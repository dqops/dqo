/*
 * Copyright © 2021 DQOps (support@dqops.com)
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

package com.dqops.metadata.incidents.defaultnotifications;

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.incidents.IncidentWebhookNotificationsSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Default incident webhook notification spec wrapper.
 */
public class DefaultIncidentWebhookNotificationsWrapperImpl extends AbstractElementWrapper<String, IncidentWebhookNotificationsSpec>
        implements DefaultIncidentWebhookNotificationsWrapper {

    @JsonIgnore
    private final static String NAME = "default_notification_webhooks";

    public DefaultIncidentWebhookNotificationsWrapperImpl() {
    }

    public DefaultIncidentWebhookNotificationsWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

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
        return visitor.accept(this, parameter);
    }

}
