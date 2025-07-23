/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.rules;

import com.dqops.rules.AbstractRuleParametersSpec;

/**
 * Rule documentation model factory that creates a rule documentation.
 * It should be only used from post processor classes that are called by Maven during build.
 */
public interface RuleDocumentationModelFactory {
    /**
     * Create a rule documentation model for a given rule parameter class instance.
     *
     * @param ruleParametersSpec Rule parameter instance.
     * @return Rule documentation model.
     */
    RuleDocumentationModel createRuleDocumentation(AbstractRuleParametersSpec ruleParametersSpec);
}
