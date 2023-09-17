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
package com.dqops.cli.terminal.logging;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the mapping for configuration parameters at the root level.
 */
@Configuration
@ConfigurationProperties
@EqualsAndHashCode(callSuper = false)
public class RootConfigurationProperties implements Cloneable {
    private Boolean silent;

    /**
     * Returns true if application should run in a silent mode (not showing the banner and the help text).
     * @return Run application in a silent mode.
     */
    public Boolean getSilent() {
        return silent;
    }

    /**
     * Sets a flag to run the application in a silent mode.
     * @param silent True - do not show any banners, startup message, etc.
     */
    public void setSilent(Boolean silent) {
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
