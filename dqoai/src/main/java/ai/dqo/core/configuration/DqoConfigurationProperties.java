/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * Configuration POJO with the configuration for Dqo.ai. Properties are mapped to the root "dqo." prefix.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo")
@EqualsAndHashCode(callSuper = false)
public class DqoConfigurationProperties implements Cloneable {
    private String home;
    private String yamlSchemaServer = "https://cloud.dqo.ai/dqo-yaml-schema/";
    private String defaultTimeZone = TimeZone.getDefault().getID();

    /**
     * Returns the location of the dqo.io home folder (installation folder). The installation folder contains
     * the default templates and libraries. The folder may be changed by changing the dqo.home configuration property
     * or setting a DQO_HOME environment variable.
     * @return Dqo home folder poth.
     */
    public String getHome() {
        return home;
    }

    /**
     * Sets a home path.
     * @param home Home path.
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * Base URL to the yaml schema server.
     * @return Yaml schema server base url.
     */
    public String getYamlSchemaServer() {
        return yamlSchemaServer;
    }

    /**
     * Sets the YAML schema server base url.
     * @param yamlSchemaServer Yaml schema server base url.
     */
    public void setYamlSchemaServer(String yamlSchemaServer) {
        this.yamlSchemaServer = yamlSchemaServer;
    }

    /**
     * Returns the default server time zone used when the time zone is not configured in the user settings.
     * The default value of this parameter is the time zone of the DQO instance (host data zone).
     * @return Default server time zone.
     */
    public String getDefaultTimeZone() {
        return defaultTimeZone;
    }

    /**
     * Sets the IANA default time zone for the server.
     * @param defaultTimeZone IANA name of the default time zone.
     */
    public void setDefaultTimeZone(String defaultTimeZone) {
        this.defaultTimeZone = defaultTimeZone;
    }

    /**
     * Clones the current object.
     * @return Deeply cloned instance.
     */
    @Override
    public DqoConfigurationProperties clone() {
        try {
            DqoConfigurationProperties cloned = (DqoConfigurationProperties) super.clone();
            return cloned;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
