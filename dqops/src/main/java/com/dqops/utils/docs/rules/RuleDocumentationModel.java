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
