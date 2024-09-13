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

import java.util.Collection;

/**
 * Data domain registry that maintains the list of active data domains. When the list of domains is changed, it asks the data domain manager to update the domains.
 */
public interface DataDomainRegistry {
    /**
     * Starts the service and loads the data domain list.
     *
     * @param dataDomainManager Back reference to the data domain manager that will be used to manage local domains. It is provided a back reference to avoid circular references.
     */
    void start(DataDomainManager dataDomainManager);

    /**
     * Return a collection of nested data domain names (excluding the default domain).
     *
     * @return List of nested domains.
     */
    Collection<LocalDataDomainSpec> getNestedDataDomainNames();
}
