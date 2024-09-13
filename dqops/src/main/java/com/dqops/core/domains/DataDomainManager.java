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

package com.dqops.core.domains;

import com.dqops.metadata.settings.domains.LocalDataDomainSpec;

/**
 * Data domain manager that maintains the configuration and activation of local data domains.
 */
public interface DataDomainManager {
    /**
     * Activates the data domains.
     */
    void start();

    /**
     * Activates a local data domain.
     *
     * @param dataDomainSpec Data domain specification.
     */
    void initializeLocalDataDomain(LocalDataDomainSpec dataDomainSpec);

    /**
     * Updates the configuration of a data domain.
     * @param existingDataDomainSpec Old data domain configuration.
     * @param updatedDataDomainSpec New data domain configuration or null, when the domain should be stopped.
     */

    void updateLocalDataDomain(LocalDataDomainSpec existingDataDomainSpec, LocalDataDomainSpec updatedDataDomainSpec);
}
