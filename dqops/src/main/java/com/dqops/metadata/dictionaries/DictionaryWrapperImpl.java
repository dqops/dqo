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
package com.dqops.metadata.dictionaries;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.AbstractPojoElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Data dictionary file wrapper.
 */
public class DictionaryWrapperImpl extends AbstractPojoElementWrapper<String, FileContent> implements DictionaryWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<DictionaryWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractPojoElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private String dictionaryName;

    /**
     * Creates a default shared credential wrapper.
     */
    public DictionaryWrapperImpl() {
    }

    /**
     * Creates a data dictionary wrapper given a credential name.
     * @param dictionaryName Dictionary name.
     */
    public DictionaryWrapperImpl(String dictionaryName) {
        this();
        this.dictionaryName = dictionaryName;
    }

    /**
     * Gets the data dictionary name.
     * @return Dictionary name.
     */
    public String getDictionaryName() {
        return dictionaryName;
    }

    /**
     * Sets a data dictionary name.
     * @param dictionaryName Dictionary name.
     */
    public void setDictionaryName(String dictionaryName) {
        assert this.dictionaryName == null || Objects.equals(this.dictionaryName, dictionaryName); // cannot change the name
        this.dictionaryName = dictionaryName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getDictionaryName();
    }

    /**
     * Extracts an absolute file path to the credential file. This method returns null if the credentials are not stored on the disk, but using an in-memory user home instance.
     *
     * @return Absolut path to the file or null when it is not possible to find the file.
     */
    @Override
    public Path toAbsoluteFilePath() {
        return null;
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
    public DictionaryWrapper clone() {
        return (DictionaryWrapper) super.deepClone();
    }
}
