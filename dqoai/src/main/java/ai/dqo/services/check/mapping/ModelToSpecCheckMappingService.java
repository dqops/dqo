/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.services.check.mapping;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.services.check.mapping.models.CheckContainerModel;

/**
 * Service that updates the check specification from a model that was filled with updates.
 */
public interface ModelToSpecCheckMappingService {
    /**
     * Updates the <code>checkContainerSpec</code> with the updates contained in the <code>model</code>.
     *
     * @param model              Data quality check model with the updates.
     * @param checkContainerSpec The target check container spec object that will be updated.
     */
    void updateCheckContainerSpec(CheckContainerModel model, AbstractRootChecksContainerSpec checkContainerSpec);
}
