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
package com.dqops.rest.controllers;

import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.rest.models.platform.DqoSettingsModel;
import com.dqops.rest.models.platform.DqoUserProfileModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.core.principal.DqoUserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.StreamSupport;

/**
 * REST API controller that returns information about the configuration of DQO and the profile of the current user.
 */
@RestController
@RequestMapping("/api/environment")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Environment", description = "DQO environment and configuration controller, provides access to the DQO configuration.")
@Slf4j
public class EnvironmentController {
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private Environment springEnvironment;

    /**
     * Dependency injection constructor of the environment controller.
     * @param dqoCloudApiKeyProvider DQO API key provider.
     * @param springEnvironment Spring Boot environment.
     */
    @Autowired
    public EnvironmentController(DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                 Environment springEnvironment) {
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.springEnvironment = springEnvironment;
    }

    /**
     * Returns all effective DQO configuration settings.
     * @return Model with a summary of all effective configuration settings.
     */
    @GetMapping(value = "/settings", produces = "application/json")
    @ApiOperation(value = "getDqoSettings", notes = "Returns all effective DQO configuration settings.",
            response = DqoSettingsModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DqoSettingsModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<DqoSettingsModel>> getDqoSettings(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        final DqoSettingsModel dqoSettingsModel = new DqoSettingsModel();
        final MutablePropertySources sources = ((AbstractEnvironment) this.springEnvironment).getPropertySources();

        StreamSupport.stream(sources.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                .flatMap(Arrays::stream)
                .distinct()
                .filter(propertyName -> !(propertyName.toLowerCase(Locale.ROOT).contains("credentials") ||
                        propertyName.toLowerCase(Locale.ROOT).contains("password") ||
                        propertyName.toLowerCase(Locale.ROOT).contains("key") ||
                        propertyName.toLowerCase(Locale.ROOT).contains("token")))
                .filter(propertyName -> propertyName.startsWith("dqo.") ||
                        propertyName.startsWith("logging.") ||
                        propertyName.startsWith("server.") ||
                        propertyName.startsWith("spring."))
                .sorted()
                .forEach(propertyName -> {
                    try {
                        dqoSettingsModel.getProperties().put(propertyName, this.springEnvironment.getProperty(propertyName));
                    }
                    catch (Exception ex) {
                        // ignore, probably unresolved environment variables
                    }
                });

        return new ResponseEntity<>(Mono.just(dqoSettingsModel), HttpStatus.OK);
    }

    /**
     * Returns the profile of the current user.
     * @return The profile of the current user.
     */
    @GetMapping(value = "/profile", produces = "application/json")
    @ApiOperation(value = "getUserProfile", notes = "Returns the profile of the current user.",
            response = DqoUserProfileModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DqoUserProfileModel.class),
            @ApiResponse(code = 404, message = "User not logged in", response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<DqoUserProfileModel>> getUserProfile(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey();
        if (apiKey == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DqoUserProfileModel dqoUserProfileModel = DqoUserProfileModel.fromApiKey(apiKey);
        return new ResponseEntity<>(Mono.just(dqoUserProfileModel), HttpStatus.OK);
    }
}
