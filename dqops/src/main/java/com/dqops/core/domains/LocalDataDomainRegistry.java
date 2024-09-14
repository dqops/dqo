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
import java.util.List;

/**
 * Data domain registry that maintains the list of active data domains. When the list of domains is changed, it asks the data domain manager to update the domains.
 */
public interface LocalDataDomainRegistry {
    /**
     * Starts the service and loads the data domain list.
     *
     * @param localDataDomainManager Back reference to the data domain manager that will be used to manage local domains. It is provided a back reference to avoid circular references.
     */
    void start(LocalDataDomainManager localDataDomainManager);

    /**
     * Return a collection of nested data domain names (excluding the default domain).
     *
     * @return List of nested domains.
     */
    Collection<LocalDataDomainSpec> getNestedDataDomains();

    /**
     * Replaces the current list of data domains with a new list of domains. Some domains are deleted, other created locally.
     * @param newDataDomainList New list of data domains.
     */
    void replaceDataDomainList(List<LocalDataDomainSpec> newDataDomainList);

    /**
     * Adds a local data domain. Saves the domain to the local settings and starts the data domain locally.
     * @param localDataDomainSpec Data domain specification.
     */
    void addDataDomain(LocalDataDomainSpec localDataDomainSpec);

    /**
     * Deletes a local data domain. Operations for the domain (job scheduling) is stopped, but the local data is preserved.
     * @param dataDomainName Data domain name.
     */
    boolean deleteDataDomain(String dataDomainName);

    /**
     * Returns a data domain given the technical domain name.
     * @param dataDomainName Data domain name.
     * @return Data domain or null, when this domain is not maintained locally.
     */
    LocalDataDomainSpec getDomain(String dataDomainName);
}
