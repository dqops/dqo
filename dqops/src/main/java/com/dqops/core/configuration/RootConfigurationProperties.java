/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import com.dqops.cli.CliApplication;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the mapping for configuration parameters at the root level.
 */
@Configuration
@EqualsAndHashCode(callSuper = false)
public class RootConfigurationProperties implements Cloneable {
    private boolean silent;

    @Autowired
    public RootConfigurationProperties() {
        // this constructor picks the value from the main method, because we want to accept a parameter "--silent" without a value, but for spring configuration, such parameter means to set null to the value
        if (CliApplication.isSilentEnabledByArgument()) {
            this.silent = true;
        }
    }

    /**
     * Returns true if application should run in a silent mode (not showing the banner and the help text).
     * @return Run application in a silent mode.
     */
    public boolean isSilent() {
        return silent;
    }

    /**
     * Sets a flag to run the application in a silent mode.
     * @param silent True - do not show any banners, startup message, etc.
     */
    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    /**
     * Clones the current object.
     * @return Deeply cloned instance.
     */
    @Override
    public RootConfigurationProperties clone() {
        try {
            RootConfigurationProperties cloned = (RootConfigurationProperties) super.clone();
            return cloned;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
