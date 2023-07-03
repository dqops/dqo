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
