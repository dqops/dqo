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

import lombok.Data;

import java.util.List;

/**
 * Rule groups model. Contains the rule category and list contain all information about the rule.
 */
@Data
public class RuleGroupedDocumentationModel {
    /**
     * Rule category
     */
    private String category;

    /**
     * List contain all information about the rule.
     */
    private List<RuleDocumentationModel> ruleDocumentationModels;
}
