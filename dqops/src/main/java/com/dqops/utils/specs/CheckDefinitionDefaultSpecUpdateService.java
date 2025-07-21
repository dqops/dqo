/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.specs;

import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;

/**
 * Built-in check definition update service that updates the list of supported built-in checks as check specification files
 * stored in the DQOps Home folder.
 */
public interface CheckDefinitionDefaultSpecUpdateService {
    /**
     * Updates the definitions of built-in checks in the DQOps Home's checks folder.
     *
     * @param dqoHomeContext DQOps Home context.
     */
    void updateCheckSpecifications(DqoHomeContext dqoHomeContext);
}
