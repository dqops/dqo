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
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import static com.dqops.rest.server.LocalUrlAddresses.swaggerUiPath;

/**
 * Spring boot swagger configuration. Configures how Swagger shows the UI.
 */
@Configuration
public class SwaggerConfiguration implements WebFluxConfigurer {
    /**
     * Configures the url mappings for static resources - the Swagger UI web interface.
     * @param registry Resource handler registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = "";
        registry.
                addResourceHandler(baseUrl + swaggerUiPath + "/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui-dist/5.9.2/")
                .resourceChain(true);

        registry.
                addResourceHandler(baseUrl + "/swagger-ui-override/**")
                .addResourceLocations("classpath:/static/swagger-ui-override/")
                .resourceChain(true);
    }
}
