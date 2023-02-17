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
import ai.dqo.services.check.mapping.models.UICheckContainerModel;

/**
 * Service that updates the check specification from the UI model that was filled with updates.
 */
public interface UiToSpecCheckMappingService {
    /**
     * Updates the <code>checkCategoriesSpec</code> with the updates received from the UI in the <code>model</code>.
     *
     * @param model               Data quality check UI model with the updates.
     * @param checkCategoriesSpec The target check categories spec object that will be updated.
     */
    void updateAllChecksSpecs(UICheckContainerModel model, AbstractRootChecksContainerSpec checkCategoriesSpec);
}
