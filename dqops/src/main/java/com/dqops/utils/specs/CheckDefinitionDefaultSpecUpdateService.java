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
