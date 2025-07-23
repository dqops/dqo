/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import static com.dqops.rest.server.LocalUrlAddressesProviderImpl.SWAGGER_UI_PATH;

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
                addResourceHandler(baseUrl + SWAGGER_UI_PATH + "/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui-dist/5.9.2/")
                .resourceChain(true);

        registry.
                addResourceHandler(baseUrl + "/swagger-ui-override/**")
                .addResourceLocations("classpath:/static/swagger-ui-override/")
                .resourceChain(true);
    }
}
