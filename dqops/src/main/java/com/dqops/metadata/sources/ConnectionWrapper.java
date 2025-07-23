/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.sources;

import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.basespecs.ObjectName;
import com.dqops.metadata.scheduling.SchedulingRootNode;

/**
 * Connection spec wrapper.
 */
public interface ConnectionWrapper extends ElementWrapper<ConnectionSpec>, ObjectName<String>, SchedulingRootNode {
    /**
     * Gets the connection name.
     * @return Connection name.
     */
    String getName();

    /**
     * Sets a connection name.
     * @param name Connection name.
     */
    void setName(String name);

    /**
     * Returns a list of tables.
     * @return List of tables.
     */
    TableList getTables();
}
