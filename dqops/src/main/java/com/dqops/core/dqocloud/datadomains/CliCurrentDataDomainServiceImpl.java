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

import com.dqops.core.configuration.DqoUserConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Service that tracks the current data domain that is used by the command-line commands.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Lazy(false)
public class CliCurrentDataDomainServiceImpl implements CliCurrentDataDomainService {
    private final DqoUserConfigurationProperties dqoUserConfigurationProperties;
    private String currentDataDomain;

    @Autowired
    public CliCurrentDataDomainServiceImpl(DqoUserConfigurationProperties dqoUserConfigurationProperties) {
        this.dqoUserConfigurationProperties = dqoUserConfigurationProperties;
        this.currentDataDomain = dqoUserConfigurationProperties.getDefaultDataDomain();
    }

    /**
     * Returns the name of the current data domain. It is the name of the data domain in DQOps cloud, despite the name of the default data domain that was mounted to the DQOps user home root folder.
     * @return Current data domain.
     */
    @Override
    public String getCurrentDataDomain() {
        return currentDataDomain;
    }

    /**
     * Changes the current data domain.
     * @param currentDataDomain New data domain.
     */
    @Override
    public void setCurrentDataDomain(String currentDataDomain) {
        this.currentDataDomain = currentDataDomain;
    }
}
