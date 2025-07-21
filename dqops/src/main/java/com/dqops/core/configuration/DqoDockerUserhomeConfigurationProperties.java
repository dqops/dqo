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

import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Dqo configuration when run in docker related to user-home.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.docker.user-home")
@EqualsAndHashCode(callSuper = false)
public class DqoDockerUserhomeConfigurationProperties implements Cloneable {

    private boolean allowUnmounted = false;

    /**
     * Get the flag stating whether dqo-user-home can be initialized inside docker container without mounting an external volume.
     * @return True if dqo-user-home can be initialized inside raw container's filesystem, without mounting an external volume. False by default.
     */
    public boolean isAllowUnmounted() {
        return allowUnmounted;
    }

    /**
     * Set the flag stating whether dqo-user-home can be initialized inside docker container without mounting an external volume.
     * @param allowUnmounted New value of <code>allowUnmounted</code> flag.
     */
    public void setAllowUnmounted(boolean allowUnmounted) {
        this.allowUnmounted = allowUnmounted;
    }

    /**
     * Clones the current object.
     * @return Cloned instance.
     */
    @Override
    public DqoDockerUserhomeConfigurationProperties clone() {
        try {
            return (DqoDockerUserhomeConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
