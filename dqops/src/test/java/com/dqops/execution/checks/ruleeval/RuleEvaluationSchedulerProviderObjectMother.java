/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.checks.ruleeval;

import com.dqops.utils.BeanFactoryObjectMother;

/**
 * Object mother for {@link RuleEvaluationSchedulerProvider}
 */
public class RuleEvaluationSchedulerProviderObjectMother {
    /**
     * Returns the default singleton with the thread pool (reactor job scheduler) to run threads that evaluate DQ rules.
     * @return Scheduler provider.
     */
    public static RuleEvaluationSchedulerProvider getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(RuleEvaluationSchedulerProvider.class);
    }
}
