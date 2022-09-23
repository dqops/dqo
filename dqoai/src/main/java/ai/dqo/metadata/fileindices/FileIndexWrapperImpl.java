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
package ai.dqo.metadata.fileindices;

import ai.dqo.metadata.basespecs.AbstractElementWrapper;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.search.DimensionSearcherObject;
import ai.dqo.metadata.search.LabelsSearcherObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * File index wrapper.
 */
public class FileIndexWrapperImpl extends AbstractElementWrapper<FileIndexName, FileIndexSpec> implements FileIndexWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<FileIndexWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private FileIndexName indexName;

    /**
     * Creates a new file index wrapper.
     */
    public FileIndexWrapperImpl() {
    }

    /**
     * Creates a new file index wrapper given an index name.
     * @param indexName File index name.
     */
    public FileIndexWrapperImpl(FileIndexName indexName) {
        this();
        this.indexName = indexName;
    }

    /**
     * Gets the file index name.
     * @return File index name.
     */
    public FileIndexName getIndexName() {
        return indexName;
    }

    /**
     * Sets a file index name.
     * @param indexName File index name.
     */
    public void setIndexName(FileIndexName indexName) {
        assert this.indexName == null || Objects.equals(this.indexName, indexName); // cannot change the name
        this.indexName = indexName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public FileIndexName getObjectName() {
        return this.getIndexName();
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
}
