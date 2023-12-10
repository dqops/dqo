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
package com.dqops.core.configuration;

import com.dqops.core.principal.UserDomainIdentity;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Configuration POJO with the configuration for DQOps User Home. Properties are mapped to the "dqo.user." prefix.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.user")
@EqualsAndHashCode(callSuper = false)
@Lazy(false)
public class DqoUserConfigurationProperties implements Cloneable {
    private String home;
    private String defaultDataDomain = UserDomainIdentity.DEFAULT_DATA_DOMAIN;
    private boolean hasLocalHome;
    private boolean initializeUserHome;
    private static DqoUserConfigurationProperties INSTANCE;

    /**
     * Returns the default data model that was mounted as the root data domain.
     * @return Data domain from DQOps Cloud that was mounted as the default data domain at the root folder.
     */
    public static String getDataDomainMountedAtRoot() {
        return INSTANCE.defaultDataDomain;
    }

    public DqoUserConfigurationProperties() {
        if (INSTANCE == null) {
            INSTANCE = this;
        }
    }

    /**
     * Returns the location of the dqo.io user home folder. The user home folder is the location of connections and the data model.
     * The folder may be changed by changing the dqo.user.home configuration property
     * or setting a DQO_USER_HOME environment variable.
     * @return Dqo user home folder poth.
     */
    public String getHome() {
        return home;
    }

    /**
     * Sets the dqo user's home folder.
     * @param home Path to the user's home folder.
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * Returns the name of the data domain that is mounted at the root DQOps user home folder.
     * @return Default data domain.
     */
    public String getDefaultDataDomain() {
        return defaultDataDomain;
    }

    /**
     * Sets the name of the default data domain that is mounted at the root folder.
     * @param defaultDataDomain Default data domain.
     */
    public void setDefaultDataDomain(String defaultDataDomain) {
        if (defaultDataDomain == null){
            this.defaultDataDomain = "";
            return;
        }
        this.defaultDataDomain = defaultDataDomain;
    }

    /**
     * Is local home enabled. The local home directory is initialized (subfolders created) only when it was enabled.
     * @return Local home is enabled and should be initialized on startup.
     */
    public boolean isHasLocalHome() {
        return hasLocalHome;
    }

    /**
     * Enables or disables the local home.
     * @param hasLocalHome Local home is enabled and should be initialized on next startup.
     */
    public void setHasLocalHome(boolean hasLocalHome) {
        this.hasLocalHome = hasLocalHome;
    }

    /**
     * Returns true when the user home should be initialized without asking the user.
     * @return True when an empty user home should be initialized without asking the user for confirmation, false when the user must confirm.
     */
    public boolean isInitializeUserHome() {
        return initializeUserHome;
    }

    /**
     * Sets the flag if an empty user home is initialized without asking for confirmation, false when the user must confirm.
     * @param initializeUserHome True - initialize user home without asking, false - ask the user.
     */
    public void setInitializeUserHome(boolean initializeUserHome) {
        this.initializeUserHome = initializeUserHome;
    }

    @Override
    public DqoUserConfigurationProperties clone() {
        try {
            return (DqoUserConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
