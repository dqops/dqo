/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.dictionaries;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.AbstractPojoElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

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

    public DictionaryWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a data dictionary wrapper given a credential name.
     * @param dictionaryName Dictionary name.
     * @param readOnly Make the wrapper read-only.
     */
    public DictionaryWrapperImpl(String dictionaryName, boolean readOnly) {
        this(readOnly);
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

    /**
     * Parses the data dictionary and returns a flat list of entries.
     *
     * @return A list of dictionary entries.
     */
    @Override
    public Set<String> getDictionaryEntries() {
        Set<String> entries = new LinkedHashSet<>();
        FileContent fileContent = this.getObject();
        if (fileContent == null || fileContent.getTextContent() == null) {
            return entries;
        }

        Stream<String> lines = fileContent.getTextContent().lines();
        lines.forEach(line -> {
            if (line.isEmpty()) {
                return;
            }

            String[] splitByComma = StringUtils.split(line, ',');
            for (String entryByComma : splitByComma) {
                if (entryByComma.isEmpty()) {
                    continue;
                }

                entries.add(entryByComma);
            }
        });

        return entries;
    }
}
