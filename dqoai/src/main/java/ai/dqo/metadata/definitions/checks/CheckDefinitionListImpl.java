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

import ai.dqo.metadata.basespecs.AbstractIndexingList;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;

import java.util.List;

/**
 * Collection of custom check definitions. Tracks the status of the child elements (addition, removal).
 */
public class CheckDefinitionListImpl extends AbstractIndexingList<String, CheckDefinitionWrapper> implements CheckDefinitionList {
    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param objectName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected CheckDefinitionWrapper createNewElement(String objectName) {
        CheckDefinitionWrapper checkDefinitionWrapper = new CheckDefinitionWrapperImpl();
        checkDefinitionWrapper.setCheckName(objectName);
        checkDefinitionWrapper.setSpec(new CheckDefinitionSpec());
        return checkDefinitionWrapper;
    }

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     *
     * @param checkName Check name to remove.
     * @return True when the model will be removed, false otherwise.
     */
    @Override
    public boolean remove(String checkName) {
        CheckDefinitionWrapper checkDefinitionWrapper = this.getByObjectName(checkName, true);
        if (checkDefinitionWrapper == null) {
            return false;
        }
        return this.remove(checkDefinitionWrapper);
    }

    /**
     * Returns the collection as an immutable list.
     *
     * @return List of custom rule definitions (wrappers).
     */
    @Override
    public List<CheckDefinitionWrapper> toList() {
        return List.copyOf(this);
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
