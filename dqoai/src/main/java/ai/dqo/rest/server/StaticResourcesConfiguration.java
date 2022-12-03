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

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.time.Duration;

/**
 * Spring WebFlux configuration for serving the static content.
 */
@Configuration
public class StaticResourcesConfiguration implements WebFluxConfigurer {
    /**
     * Registers static resources with the production build of the DQO UI.
     * @param registry Resource handler registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = "";
        registry.addResourceHandler(baseUrl + "/**")
                .addResourceLocations("classpath:/static/")
                .setUseLastModified(true)
                .setOptimizeLocations(true)
                .setCacheControl(CacheControl.maxAge(Duration.ofMinutes(60)).cachePublic().mustRevalidate())
                .resourceChain(true);
    }
}
