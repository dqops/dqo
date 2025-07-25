/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.definitions.rules;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Custom rule definition spec wrapper.
 */
public class RuleDefinitionWrapperImpl extends AbstractElementWrapper<String, RuleDefinitionSpec> implements RuleDefinitionWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<RuleDefinitionWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private String ruleName;
    @JsonIgnore
    private FileContent rulePythonModuleContent;

    /**
     * Creates a default rule definition wrapper.
     */
    public RuleDefinitionWrapperImpl() {
    }

    /**
     * Create the rule definition wrapper in an optional read-only mode.
     * @param readOnly Turn on read-only mode.
     */
    public RuleDefinitionWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a rule definition wrapper given a rule name.
     * @param ruleName Rule name.
     * @param readOnly Make the wrapper read-only.
     */
    public RuleDefinitionWrapperImpl(String ruleName, boolean readOnly) {
        this(readOnly);
        this.ruleName = ruleName;
    }

    /**
     * Gets the custom rule definition name.
     * @return Custom rule definition name.
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * Sets a custom rule definition name.
     * @param ruleName Custom rule definition name.
     */
    public void setRuleName(String ruleName) {
        assert this.ruleName == null || Objects.equals(this.ruleName, ruleName); // cannot change the name
        this.ruleName = ruleName;
    }

    /**
     * Get the content of a rule python module file.
     * @return Content of the python module file (.py file with the rule code).
     */
    public FileContent getRulePythonModuleContent() {
        return rulePythonModuleContent;
    }

    /**
     * Stores the content of the python module file (.py file).
     * @param rulePythonModuleContent Python rule implementation file content.
     */
    public void setRulePythonModuleContent(FileContent rulePythonModuleContent) {
		this.setModifiedIf(!Objects.equals(this.rulePythonModuleContent, rulePythonModuleContent));
        this.rulePythonModuleContent = rulePythonModuleContent == null ? new FileContent() : rulePythonModuleContent; // we are always storing something
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getRuleName();
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
    public RuleDefinitionWrapper clone() {
        return (RuleDefinitionWrapper) super.deepClone();
    }
}
