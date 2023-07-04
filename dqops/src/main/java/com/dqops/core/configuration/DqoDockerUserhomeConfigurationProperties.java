/*
 * Copyright © 2023 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
