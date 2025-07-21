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

import java.nio.file.Path;

/**
 * Service used during the build process (called from a maven profile) that updates the yaml definitions in the DQOps Home rule folder,
 * updating correct list of fields that were detected using reflection.
 */
public interface RuleDefinitionDefaultSpecUpdateService {
    /**
     * Checks if all rule definition yaml files in the DQOps Home rule folder have a correct list of parameters, matching the fields used in the Java spec classes.
     *
     * @param projectRootPath Path to the dqops module folder (code folder).
     * @param dqoHomeContext  DQOps Home context.
     */
    void updateRuleSpecifications(Path projectRootPath, DqoHomeContext dqoHomeContext);
}
