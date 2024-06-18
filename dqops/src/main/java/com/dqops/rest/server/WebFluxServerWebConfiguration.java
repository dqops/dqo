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
package com.dqops.rest.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Configures remaining Spring WebFlux configuration parameters.
 */
@Configuration
@EnableWebFlux
public class WebFluxServerWebConfiguration implements WebFluxConfigurer {
    private Environment environment;

    public WebFluxServerWebConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * Overrides the configuration of a maximum payload size for rest api, for some versions of Spring Boot that do not use the default configuration parameter.
     * @param configurer Server codec configurer.
     */
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        String maxMemorySizeString = environment.getProperty("spring.codec.max-in-memory-size", "5MB");
        DataSize maxMemorySize = DataSize.parse(maxMemorySizeString, DataUnit.BYTES);
        configurer.defaultCodecs().maxInMemorySize((int)maxMemorySize.toBytes());
    }
}
