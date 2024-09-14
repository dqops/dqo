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

import java.util.List;

/**
 * Data domain management service that synchronizes data domain with the DQOps Cloud, creates, deletes and synchronizes the list of data domains.
 */
public interface DataDomainsService {
    /**
     * Downloads the list of data domains from DQOps cloud and configures the domains in the local DQOps instance.
     */
    void synchronizeDataDomainList();

    /**
     * Returns a list of all local data domains. It also returns the default data domain.
     *
     * @return Return all data domains.
     */
    List<LocalDataDomainModel> getAllDataDomains();

    /**
     * Creates a new data domain on the server and then create a local data domain.
     *
     * @param dataDomainDisplayName Data domain display name.
     * @return Data domain model.
     */
    LocalDataDomainModel createDataDomain(String dataDomainDisplayName);

    /**
     * Deletes a data domain from the server and locally.
     *
     * @param dataDomainName Data domain name.
     */
    void deleteDataDomain(String dataDomainName);
}
