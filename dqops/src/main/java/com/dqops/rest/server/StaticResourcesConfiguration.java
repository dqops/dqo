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
import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.google.api.gax.rpc.InvalidArgumentException;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.ResourceHandlerRegistration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.nio.file.Path;
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

    /**
     * The name of the logo file in the "settings" folder.
     */
    public static final String LOGO_ICON_FILE_NAME = "logo.png";

    private final DqoWebServerConfigurationProperties webServerConfigurationProperties;
    private final HomeLocationFindService homeLocationFindService;

    /**
     * Dependency injection constructor.
     * @param webServerConfigurationProperties Web sever specific configuration.
     * @param homeLocationFindService User home finder.
     */
    @Autowired
    public StaticResourcesConfiguration(DqoWebServerConfigurationProperties webServerConfigurationProperties,
                                        HomeLocationFindService homeLocationFindService) {
        this.webServerConfigurationProperties = webServerConfigurationProperties;
        this.homeLocationFindService = homeLocationFindService;
    }

    /**
     * Registers static resources with the production build of the DQOps UI.
     * @param registry Resource handler registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String userHomePathString = this.homeLocationFindService.getUserHomePath();
        if (!Strings.isNullOrEmpty(userHomePathString)) {
            Path pathToLogoFile = Path.of(userHomePathString).resolve(BuiltInFolderNames.SETTINGS).resolve(LOGO_ICON_FILE_NAME);
            if (pathToLogoFile.toFile().exists()) {
                String logoAbsolutePathString = pathToLogoFile.toAbsolutePath().toString();
                ResourceHandlerRegistration logoResourceLocation = registry.addResourceHandler(BASE_URL + "/" + LOGO_ICON_FILE_NAME)
                        .addResourceLocations("file:" + (logoAbsolutePathString.startsWith("/") ? logoAbsolutePathString : "/" + logoAbsolutePathString))
                        .setUseLastModified(true)
                        .setOptimizeLocations(true);
                configureCacheControl(logoResourceLocation,
                        this.webServerConfigurationProperties.getDynamicFilesCacheControlMaxAge(), false);
            }
        }

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

        if (this.webServerConfigurationProperties.getDynamicFilesCacheControlMaxAge() != null &&
            this.webServerConfigurationProperties.getDynamicFilesCacheControlMaxAge() > 0) {
            Duration maxAge = Duration.ofSeconds(this.webServerConfigurationProperties.getDynamicFilesCacheControlMaxAge());

            allOtherDynamicBookmarkedFiles
                    .setCacheControl(CacheControl.maxAge(maxAge).cachePrivate().mustRevalidate())
                    .resourceChain(false);
        } else {
            allOtherDynamicBookmarkedFiles
                    .setCacheControl(CacheControl.noCache())
                    .resourceChain(false);
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
