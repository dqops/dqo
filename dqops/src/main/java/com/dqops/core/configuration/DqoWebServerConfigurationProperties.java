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
