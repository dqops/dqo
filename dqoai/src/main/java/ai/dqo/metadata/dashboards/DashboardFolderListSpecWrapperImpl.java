/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.dashboards;

import ai.dqo.metadata.basespecs.AbstractElementWrapper;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * List of custom dashboards.
 */
public class DashboardFolderListSpecWrapperImpl extends AbstractElementWrapper<String, DashboardsFolderListSpec> implements DashboardFolderListSpecWrapper {


    @JsonIgnore
    private final static String NAME = "dashboards";

    /**
     * Creates a default dashboard definition wrapper.
     */
    public DashboardFolderListSpecWrapperImpl() {
    }

    /**
     * Gets the data quality dashboard name.
     * @return Data quality dashboard name.
     */
    public String getName() {
        return NAME;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getName();
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
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

}
