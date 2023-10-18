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
