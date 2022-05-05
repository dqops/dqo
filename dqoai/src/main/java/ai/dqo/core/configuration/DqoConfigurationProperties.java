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

/**
 * Configuration POJO with the configuration for Dqo.ai. Properties are mapped to the root "dqo." prefix.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo")
@EqualsAndHashCode(callSuper = false)
public class DqoConfigurationProperties implements Cloneable {
    @Autowired
    private DqoUserConfigurationProperties user;

    @Autowired
    private DqoPythonConfigurationProperties python;

    @Autowired
    private DqoSecretsConfigurationProperties secrets;

    @Autowired
    private DqoStorageConfigurationProperties storage;

    @Autowired
    private DqoCloudConfigurationProperties cloud;

    private String home;
    private String yamlSchemaServer;


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
     * User level configuration properties.
     * @return User level configuration with the user's home folder.
     */
    public DqoUserConfigurationProperties getUser() {
        return user;
    }

    /**
     * Sets the user's configuration properties.
     * @param user Configuration properties.
     */
    public void setUser(DqoUserConfigurationProperties user) {
        this.user = user;
    }

    /**
     * Returns the python configuration parameters.
     * @return Python configuration parameters.
     */
    public DqoPythonConfigurationProperties getPython() {
        return python;
    }

    /**
     * Sets the python configuration parameters.
     * @param python Python configuration parameters.
     */
    public void setPython(DqoPythonConfigurationProperties python) {
        this.python = python;
    }

    /**
     * Secrets configuration.
     * @return Secrets configuration.
     */
    public DqoSecretsConfigurationProperties getSecrets() {
        return secrets;
    }

    /**
     * Sets the secrets configuration.
     * @param secrets Secrets configuration.
     */
    public void setSecrets(DqoSecretsConfigurationProperties secrets) {
        this.secrets = secrets;
    }

    /**
     * DQO storage configuration - where to store the results.
     * @return Storage configuration.
     */
    public DqoStorageConfigurationProperties getStorage() {
        return storage;
    }

    /**
     * Sets the storage configuration.
     * @param storage New storage configuration.
     */
    public void setStorage(DqoStorageConfigurationProperties storage) {
        this.storage = storage;
    }

    /**
     * Returns the DQO Cloud configuration.
     * @return DQO cloud configuration.
     */
    public DqoCloudConfigurationProperties getCloud() {
        return cloud;
    }

    /**
     * Sets the configuration for the DQO Cloud.
     * @param cloud DQO Cloud configuration.
     */
    public void setCloud(DqoCloudConfigurationProperties cloud) {
        this.cloud = cloud;
    }

    /**
     * Clones the current object.
     * @return Deeply cloned instance.
     */
    @Override
    public DqoConfigurationProperties clone() {
        try {
            DqoConfigurationProperties cloned = (DqoConfigurationProperties) super.clone();
            cloned.user = this.user != null ? this.user.clone() : null;
            cloned.python = this.python != null ? this.python.clone() : null;
            cloned.secrets = this.secrets != null ? this.secrets.clone() : null;
            cloned.storage = this.storage != null ? this.storage.clone() : null;
            cloned.cloud = this.cloud != null ? this.cloud.clone() : null;
            return cloned;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
