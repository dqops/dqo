/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.mapping;

import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.services.check.mapping.models.AllChecksModel;

/**
 * Service used for applying check configuration patches over high-level specs.
 */
public interface AllChecksPatchApplier {
    /**
     * Apply a patch to the check configuration on connection level.
     * @param allChecksPatch    Patch with the changes to be applied. Incomplete, targets only that which is to be updated.
     * @param connectionWrapper Connection wrapper to the connection this patch refers to.
     */
    void applyPatchOnConnection(AllChecksModel allChecksPatch, ConnectionWrapper connectionWrapper);
}
