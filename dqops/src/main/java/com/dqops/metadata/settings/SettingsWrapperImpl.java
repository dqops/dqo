/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.settings;

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Settings spec wrapper.
 */
public class SettingsWrapperImpl extends AbstractElementWrapper<String, LocalSettingsSpec> implements SettingsWrapper {
	@JsonIgnore
	private final static String NAME = "settings";

	/**
	 * Creates a new settings wrapper.
	 */
	@Autowired
	public SettingsWrapperImpl() {
	}

	public SettingsWrapperImpl(boolean readOnly) {
		super(readOnly);
	}

	/**
	 * Gets the connection name.
	 * @return Connection name.
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
	 *
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
		return null;
	}
}
