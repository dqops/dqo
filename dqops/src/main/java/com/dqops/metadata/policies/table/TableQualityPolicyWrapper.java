/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.policies.table;

import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.basespecs.ObjectName;

/**
 * Default table-level checks pattern spec wrapper.
 */
public interface TableQualityPolicyWrapper extends ElementWrapper<TableQualityPolicySpec>, ObjectName<String> {
    /**
     * Gets the policy name.
     * @return Data quality policy name
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
    TableQualityPolicyWrapper clone();
}
