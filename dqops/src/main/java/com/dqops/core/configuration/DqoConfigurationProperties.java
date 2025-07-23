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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * Configuration POJO with the configuration for DQOps. Properties are mapped to the root "dqo." prefix.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo")
@EqualsAndHashCode(callSuper = false)
public class DqoConfigurationProperties implements Cloneable {
    private String home;
    private String version = "";
    private String yamlSchemaServer = "https://cloud.dqops.com/dqo-yaml-schema/";
    private String defaultTimeZone = TimeZone.getDefault().getID();
    private String javaOpts;

    /**
     * Returns the location of the dqo.io home folder (installation folder). The installation folder contains
     * the default templates and libraries. The folder may be changed by changing the dqo.home configuration property
     * or setting a DQO_HOME environment variable.
     * @return DQOps home folder path.
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
     * Returns the application version.
     * @return Application version number.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the application version number.
     * @param version Version number.
     */
    public void setVersion(String version) {
        this.version = version;
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
     * The default value of this parameter is the time zone of the DQOps instance (host data zone).
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
     * Returns the value that was passed as the DQO_JAVA_OPTS environment variable.
     * @return The value of the DQO_JAVA_OPTS env value.
     */
    public String getJavaOpts() {
        return javaOpts;
    }

    /**
     * Sets the parameter captured from the DQO_JAVA_OPTS environment variable, just to allow previewing it in the settings popup as a DQOps configuration parameter.
     * @param javaOpts DQO_JAVA_OPTS value.
     */
    public void setJavaOpts(String javaOpts) {
        this.javaOpts = javaOpts;
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
