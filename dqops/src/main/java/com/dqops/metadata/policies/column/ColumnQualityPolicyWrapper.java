/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.policies.column;

import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.basespecs.ObjectName;

/**
 * Default column-level checks pattern (quality policy) spec wrapper.
 */
public interface ColumnQualityPolicyWrapper extends ElementWrapper<ColumnQualityPolicySpec>, ObjectName<String> {
    /**
     * Gets the data quality policy name.
     * @return Quality policy name.
     */
    String getPolicyName();

    /**
     * Sets a data quality policy name.
     * @param policyName Quality policy name.
     */
    void setPolicyName(String policyName);

    /**
     * Creates a deep clone of the object.
     * @return Deeply cloned object.
     */
    ColumnQualityPolicyWrapper clone();
}
