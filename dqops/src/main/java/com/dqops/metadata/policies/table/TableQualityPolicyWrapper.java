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
