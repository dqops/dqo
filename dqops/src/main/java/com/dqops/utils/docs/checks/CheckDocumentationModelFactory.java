/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.checks;

import java.util.List;

/**
 * Check documentation model factory. Creates documentation objects for each check.
 */
public interface CheckDocumentationModelFactory {
    /**
     * Create a list of check documentation models for table level checks. Each category contains a list of similar checks to be documented on the same page.
     *
     * @return Documentation for each check category on a table level.
     */
    List<CheckCategoryDocumentationModel> makeDocumentationForTableChecks();

    /**
     * Create a list of check documentation models for column level checks. Each category contains a list of similar checks to be documented on the same page.
     *
     * @return Documentation for each check category on a column level.
     */
    List<CheckCategoryDocumentationModel> makeDocumentationForColumnChecks();
}
