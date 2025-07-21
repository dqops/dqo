/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
