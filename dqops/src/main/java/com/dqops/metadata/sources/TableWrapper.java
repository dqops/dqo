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

/**
 * Table spec wrapper.
 */
public interface TableWrapper extends ElementWrapper<TableSpec>, ObjectName<PhysicalTableName> {
    /**
     * Gets the physical table name that was decoded from the spec file name.
     * @return Physical table name decoded from the file name.
     */
    PhysicalTableName getPhysicalTableName();

    /**
     * Sets a physical table name that is used to generate a file name.
     * @param physicalTableName Physical table name used to generate a yaml file.
     */
    void setPhysicalTableName(PhysicalTableName physicalTableName);
}
