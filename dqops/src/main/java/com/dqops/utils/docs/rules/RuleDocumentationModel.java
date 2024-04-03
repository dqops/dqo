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

import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.rules.AbstractRuleParametersSpec;
import lombok.Data;

/**
 * Rule description model. Describes the rule and contains all information about the rule that could be gathered
 * from the rule parameters, definition, etc.
 */
@Data
public class RuleDocumentationModel {
    private Class<? extends AbstractRuleParametersSpec> ruleParametersClazz;

    /**
     * Rule description extracted from the JavaDoc comment for the whole rule definition parameter.
     */
    private String ruleParametersJavaDoc;

    /**
     * Rule name.
     */
    private String fullRuleName;

    /**
     * Rule category.
     */
    private String category;

    /**
     * Rule name inside the category.
     */
    private String ruleName;

    /**
     * Rule example in yaml format.
     */
    private String ruleExample;

    /**
     * Rule definition wrapper.
     */
    private RuleDefinitionWrapper definition;

    /**
     * The source code of the rule (Python).
     */
    private String pythonSourceCode;

    /**
     * The name of the first rule parameter, if the rule has any parameters. Used to generate CLI examples of activating the rule.
     */
    private String firstRuleParameterName;
}
