/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.credentials;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.basespecs.AbstractPojoElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Shared credential file wrapper.
 */
public class SharedCredentialWrapperImpl extends AbstractPojoElementWrapper<String, FileContent> implements SharedCredentialWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<SharedCredentialWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractPojoElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private String credentialName;

    /**
     * Creates a default shared credential wrapper.
     */
    public SharedCredentialWrapperImpl() {
    }

    public SharedCredentialWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a shared credential wrapper given a credential name.
     * @param credentialName Credential name.
     * @param readOnly Make the wrapper read-only.
     */
    public SharedCredentialWrapperImpl(String credentialName, boolean readOnly) {
        this(readOnly);
        this.credentialName = credentialName;
    }

    /**
     * Gets the shared credential name.
     * @return Shared credential name.
     */
    public String getCredentialName() {
        return credentialName;
    }

    /**
     * Sets a shared credential name.
     * @param credentialName Shared credential name.
     */
    public void setCredentialName(String credentialName) {
        assert this.credentialName == null || Objects.equals(this.credentialName, credentialName); // cannot change the name
        this.credentialName = credentialName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getCredentialName();
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
    public SharedCredentialWrapper clone() {
        return (SharedCredentialWrapper) super.deepClone();
    }
}
