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

import com.dqops.core.configuration.DqoWebServerConfigurationProperties;
import com.google.api.gax.rpc.InvalidArgumentException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.ResourceHandlerRegistration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.time.Duration;
import java.util.Objects;

/**
 * Spring WebFlux configuration for serving the static content.
 */
@Configuration
public class StaticResourcesConfiguration implements WebFluxConfigurer {
    /**
     * Base url prefix used for registration.
     */
    public static final String BASE_URL = "";

    private final DqoWebServerConfigurationProperties webServerConfigurationProperties;

    /**
     * Dependency injection constructor.
     * @param webServerConfigurationProperties Web sever specific configuration.
     */
    public StaticResourcesConfiguration(DqoWebServerConfigurationProperties webServerConfigurationProperties) {
        this.webServerConfigurationProperties = webServerConfigurationProperties;
    }

    /**
     * Registers static resources with the production build of the DQO UI.
     * @param registry Resource handler registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration filesInRootFolderRegistration = registry.addResourceHandler(BASE_URL + "/*")
                .addResourceLocations("classpath:/static/")
                .setUseLastModified(true)
                .setOptimizeLocations(true);
        configureCacheControl(filesInRootFolderRegistration,
                this.webServerConfigurationProperties.getDynamicFilesCacheControlMaxAge(), true);

        configureStaticFilesFolder(registry, "images");
        configureStaticFilesFolder(registry, "static");

        ResourceHandlerRegistration allOtherDynamicBookmarkedFiles = registry.addResourceHandler(BASE_URL + "/**")
                .addResourceLocations("classpath:/static/")
                .setUseLastModified(true)
                .setOptimizeLocations(true);

        if (this.webServerConfigurationProperties.getDynamicFilesCacheControlMaxAge() != null) {
            Duration maxAge = Duration.ofSeconds(this.webServerConfigurationProperties.getDynamicFilesCacheControlMaxAge());
            allOtherDynamicBookmarkedFiles
                    .setCacheControl(CacheControl.maxAge(maxAge).cachePublic().mustRevalidate())
                    .resourceChain(false);
        } else {
            allOtherDynamicBookmarkedFiles.resourceChain(false);
        }
    }

    /**
     * Configures a resource handler for one folder with static files.
     * @param registry Resource handler registry where the entry is added.
     * @param folder Folder to use. The root folder is "". For any other folder, provide the name, without the preceding and without the trailing slash.
     */
    public void configureStaticFilesFolder(ResourceHandlerRegistry registry, String folder) {
        if (folder.startsWith("/") || folder.endsWith("/")) {
            throw new IllegalArgumentException("Invalid folder format for " + folder);
        }

        String rootBasedFolder = Objects.equals(folder, "") ? "" : folder + "/";

        ResourceHandlerRegistration resourceHandlerRegistration = registry.addResourceHandler(BASE_URL + "/" + rootBasedFolder + "**")
                .addResourceLocations("classpath:/static/" + rootBasedFolder)
                .setUseLastModified(true)
                .setOptimizeLocations(true);

        configureCacheControl(resourceHandlerRegistration,
                this.webServerConfigurationProperties.getStaticFilesCacheControlMaxAge(), false);
    }

    /**
     * Configures the cache control for a resource handle.
     * @param resourceHandlerRegistration Resource handle registration.
     * @param maxAge Max age in seconds or null when caching should be disabled.
     * @param mustRevalidate Turn on the must revalidate option.
     */
    private void configureCacheControl(ResourceHandlerRegistration resourceHandlerRegistration, Integer maxAge, boolean mustRevalidate) {
        if (maxAge != null) {
            Duration cacheControlDuration = Duration.ofSeconds(maxAge);
            CacheControl cacheControl = CacheControl.maxAge(cacheControlDuration).cachePublic();
            if (mustRevalidate) {
                cacheControl = cacheControl.mustRevalidate();
            }

            resourceHandlerRegistration
                    .setCacheControl(cacheControl)
                    .resourceChain(true);
        } else {
            resourceHandlerRegistration.resourceChain(true); // only caching static files in memory
        }
    }
}
