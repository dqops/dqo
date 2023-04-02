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
package ai.dqo.metadata.definitions.checks;

import ai.dqo.metadata.basespecs.AbstractElementWrapper;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Custom check definition spec wrapper.
 */
public class CheckDefinitionWrapperImpl extends AbstractElementWrapper<String, CheckDefinitionSpec> implements CheckDefinitionWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<CheckDefinitionWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private String checkName;

    /**
     * Creates a default check definition wrapper.
     */
    public CheckDefinitionWrapperImpl() {
    }

    /**
     * Creates a check definition wrapper given a check name.
     * @param checkName Check name.
     */
    public CheckDefinitionWrapperImpl(String checkName) {
        this();
        this.checkName = checkName;
    }

    /**
     * Gets the custom check name.
     * @return Custom check name.
     */
    public String getCheckName() {
        return checkName;
    }

    /**
     * Sets a custom check name.
     * @param checkName Custom check name.
     */
    public void setCheckName(String checkName) {
        assert this.checkName == null || Objects.equals(this.checkName, checkName); // cannot change the name
        this.checkName = checkName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getCheckName();
    }

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
     * Creates a deep clone of the object.
     *
     * @return Deeply cloned object.
     */
    @Override
    public CheckDefinitionWrapper clone() {
        return (CheckDefinitionWrapper) super.deepClone();
    }
}
