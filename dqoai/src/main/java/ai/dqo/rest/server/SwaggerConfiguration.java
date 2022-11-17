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
package ai.dqo.rest.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Spring boot swagger configuration. Configures how Swagger shows the UI.
 */
@Configuration
public class SwaggerConfiguration implements WebFluxConfigurer {
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "DQO.ai REST API",
                "DQO.ai REST API backend for accessing the application",
                "0.1.0",
                "https://dqo.ai/terms-of-service",
                new Contact("Documati sp. z o.o.", "https://dqo.ai/", "support@dqo.ai"),
                "Apache License, Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }

    /**
     * Exposes the swagger documentation bean.
     * @return Documentation bean.
     */
    @Bean
    public Docket api() {
        // this will expose the swagger UI on: http://localhost:8888/swagger-ui/ and the swagger.xml on http://localhost:8888/v2/api-docs

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Configures the url mappings for static resources - the Swagger UI web interface.
     * @param registry Resource handler registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = "";
        registry.
                addResourceHandler(baseUrl + "/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(true);
    }
}
