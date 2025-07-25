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

import com.dqops.rest.server.authentication.DqoAuthenticationMethod;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the mapping for the web server configuration inside the dqo.webserver.*
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.webserver")
@EqualsAndHashCode(callSuper = false)
public class DqoWebServerConfigurationProperties implements Cloneable {
    private Integer staticFilesCacheControlMaxAge;
    private Integer dynamicFilesCacheControlMaxAge;
    private DqoAuthenticationMethod authenticationMethod = DqoAuthenticationMethod.none;

    /**
     * Returns the maximum age for public caching using a Cache-Control header for static files. The value is in seconds.
     * @return The maximum age for public caching using a Cache-Control header for static files.
     */
    public Integer getStaticFilesCacheControlMaxAge() {
        return staticFilesCacheControlMaxAge;
    }

    /**
     * Sets the maximum age for public caching using a Cache-Control header for static files.
     * @param staticFilesCacheControlMaxAge Max age in seconds or null when caching should be disabled.
     */
    public void setStaticFilesCacheControlMaxAge(Integer staticFilesCacheControlMaxAge) {
        this.staticFilesCacheControlMaxAge = staticFilesCacheControlMaxAge;
    }

    /**
     * Cache control for dynamic files that could change. These are the root url of the page and bookmarked direct links to screens. Dynamic files are always cached with a must-revalidate flag.
     * @return Max age for dynamic files.
     */
    public Integer getDynamicFilesCacheControlMaxAge() {
        return dynamicFilesCacheControlMaxAge;
    }

    /**
     * Sets the max cache age for dynamic files.
     * @param dynamicFilesCacheControlMaxAge Max cache age for dynamic files.
     */
    public void setDynamicFilesCacheControlMaxAge(Integer dynamicFilesCacheControlMaxAge) {
        this.dynamicFilesCacheControlMaxAge = dynamicFilesCacheControlMaxAge;
    }

    /**
     * Returns the authentication method used to authenticate local users.
     * @return Authentication method.
     */
    public DqoAuthenticationMethod getAuthenticationMethod() {
        return authenticationMethod;
    }

    /**
     * Sets an authentication method to authenticate local users.
     * @param authenticationMethod Authentication method.
     */
    public void setAuthenticationMethod(DqoAuthenticationMethod authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    /**
     * Clones the current object.
     * @return Deeply cloned instance.
     */
    @Override
    public DqoWebServerConfigurationProperties clone() {
        try {
            DqoWebServerConfigurationProperties cloned = (DqoWebServerConfigurationProperties) super.clone();
            return cloned;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
