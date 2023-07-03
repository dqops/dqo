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
package com.dqops.metadata.fileindices;

import com.dqops.metadata.basespecs.AbstractIndexingList;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.List;

/**
 * Data source collection. Tracks the status of the child elements (addition, removal).
 */
public class FileIndexListImpl extends AbstractIndexingList<FileIndexName, FileIndexWrapper> implements FileIndexList {
    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param objectName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected FileIndexWrapper createNewElement(FileIndexName objectName) {
        FileIndexWrapperImpl fileIndexWrapper = new FileIndexWrapperImpl();
        fileIndexWrapper.setIndexName(objectName);
        fileIndexWrapper.setSpec(new FileIndexSpec());
        return fileIndexWrapper;
    }

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     *
     * @param sourceName Source model to remove.
     * @return True when the model will be removed, false otherwise.
     */
    @Override
    public boolean remove(FileIndexName sourceName) {
        FileIndexWrapper fileIndexWrapper = this.getByObjectName(sourceName, true);
        if (fileIndexWrapper == null) {
            return false;
        }
        return this.remove(fileIndexWrapper);
    }

    /**
     * Returns the collection as an immutable list.
     *
     * @return List of connections.
     */
    @Override
    public List<FileIndexWrapper> toList() {
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
