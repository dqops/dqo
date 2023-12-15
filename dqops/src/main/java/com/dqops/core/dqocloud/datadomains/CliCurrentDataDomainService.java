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

package com.dqops.core.dqocloud.datadomains;

/**
 * Service that tracks the current data domain that is used by the command-line commands.
 */
public interface CliCurrentDataDomainService {
    /**
     * Returns the name of the current data domain. It is the name of the data domain in DQOps cloud, despite the name of the default data domain that was mounted to the DQOps user home root folder.
     *
     * @return Current data domain.
     */
    String getCurrentDataDomain();

    /**
     * Changes the current data domain.
     *
     * @param currentDataDomain New data domain.
     */
    void setCurrentDataDomain(String currentDataDomain);
}
