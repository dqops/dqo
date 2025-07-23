/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mining;

import com.dqops.utils.BeanFactoryObjectMother;

/**
 * Object mother for {@link RuleMiningRuleRegistry}
 */
public class RuleMiningRuleRegistryObjectMother {
    /**
     * Retrieves the default rule mining registry with configured rules that support rule mining.
     * @return Rule mining registry.
     */
    public static RuleMiningRuleRegistry getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(RuleMiningRuleRegistry.class);
    }
}
