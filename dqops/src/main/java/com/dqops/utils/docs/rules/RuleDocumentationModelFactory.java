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
