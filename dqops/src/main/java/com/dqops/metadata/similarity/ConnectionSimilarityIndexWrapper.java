/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.similarity;

import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.basespecs.ObjectName;

/**
 * A wrapper for a table similarity score index at a connection level.
 */
public interface ConnectionSimilarityIndexWrapper extends ElementWrapper<ConnectionSimilarityIndexSpec>, ObjectName<String> {
    /**
     * Gets the connection name.
     * @return Connection name.
     */
    String getConnectionName();

    /**
     * Sets a connection name.
     * @param connectionName Connection name.
     */
    void setConnectionName(String connectionName);
}
