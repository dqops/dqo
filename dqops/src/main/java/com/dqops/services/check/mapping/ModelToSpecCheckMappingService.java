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

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.services.check.mapping.models.CheckContainerModel;

/**
 * Service that updates the check specification from a model that was filled with updates.
 */
public interface ModelToSpecCheckMappingService {
    /**
     * Updates the <code>checkContainerSpec</code> with the updates contained in the <code>model</code>.
     *
     * @param model              Data quality check model with the updates.
     * @param checkContainerSpec The target check container spec object that will be updated.
     * @param parentTable        Parent table specification.
     */
    void updateCheckContainerSpec(CheckContainerModel model, AbstractRootChecksContainerSpec checkContainerSpec, TableSpec parentTable);
}
