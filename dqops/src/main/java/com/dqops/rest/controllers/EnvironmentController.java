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

import com.dqops.core.catalogsync.DataCatalogHealthSendService;
import com.dqops.core.configuration.DqoIntegrationsConfigurationProperties;
import com.dqops.core.domains.LocalDataDomainRegistry;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.login.DqoUserTokenPayload;
import com.dqops.core.dqocloud.login.InstanceCloudLoginService;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.secrets.signature.SignedObject;
import com.dqops.rest.models.platform.DqoSettingsModel;
import com.dqops.rest.models.platform.DqoUserProfileModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.utils.threading.CompletableFutureRunner;
import io.swagger.annotations.*;
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
import java.util.concurrent.CompletableFuture;
import java.util.stream.StreamSupport;

/**
 * REST API controller that returns information about the configuration of DQOps and the profile of the current user.
 */
@RestController
@RequestMapping("/api/environment")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Environment", description = "DQOps environment and configuration controller, provides access to the DQOps configuration, " +
        "current user's information and issue local API Keys for the calling user.")
@Slf4j
public class EnvironmentController {
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private Environment springEnvironment;
    private InstanceCloudLoginService instanceCloudLoginService;
    private LocalDataDomainRegistry dataDomainRegistry;
    private DataCatalogHealthSendService dataCatalogHealthSendService;


    /**
     * Dependency injection constructor of the environment controller.
     * @param dqoCloudApiKeyProvider DQOps API key provider.
     * @param springEnvironment Spring Boot environment.
     * @param instanceCloudLoginService Local instance authentication token service, used to issue a local API key.
     * @param dataDomainRegistry Data domain registry - to detect if there are any data domains, so data domains are supported.
     * @param dataCatalogHealthSendService Data catalog notification send service - to see if data catalog synchronization is possible.
     */
    @Autowired
    public EnvironmentController(DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                 Environment springEnvironment,
                                 InstanceCloudLoginService instanceCloudLoginService,
                                 LocalDataDomainRegistry dataDomainRegistry,
                                 DataCatalogHealthSendService dataCatalogHealthSendService) {
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.springEnvironment = springEnvironment;
        this.instanceCloudLoginService = instanceCloudLoginService;
        this.dataDomainRegistry = dataDomainRegistry;

        this.dataCatalogHealthSendService = dataCatalogHealthSendService;
    }

    /**
     * Returns all effective DQOps configuration settings.
     * @return Model with a summary of all effective configuration settings.
     */
    @GetMapping(value = "/settings", produces = "application/json")
    @ApiOperation(value = "getDqoSettings", notes = "Returns all effective DQOps configuration settings.",
            response = DqoSettingsModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DqoSettingsModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<DqoSettingsModel>>> getDqoSettings(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            final DqoSettingsModel dqoSettingsModel = new DqoSettingsModel();
            final MutablePropertySources sources = ((AbstractEnvironment) this.springEnvironment).getPropertySources();

            StreamSupport.stream(sources.spliterator(), false)
                    .filter(ps -> ps instanceof EnumerablePropertySource)
                    .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                    .flatMap(Arrays::stream)
                    .distinct()
                    .filter(propertyName -> !(propertyName.toLowerCase(Locale.ROOT).contains("credentials") ||
                            propertyName.toLowerCase(Locale.ROOT).contains("username") ||
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
        }));
    }

    /**
     * Returns the profile of the current user.
     * @return The profile of the current user.
     */
    @GetMapping(value = "/profile", produces = "application/json")
    @ApiOperation(value = "getUserProfile", notes = "Returns the profile of the current user.",
            response = DqoUserProfileModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DqoUserProfileModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<DqoUserProfileModel>>> getUserProfile(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
            return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
                DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey(principal.getDataDomainIdentity());
                if (apiKey == null) {
                    DqoUserProfileModel dqoUserProfileModel = DqoUserProfileModel.createFreeUserModel();
                    return new ResponseEntity<>(Mono.just(dqoUserProfileModel), HttpStatus.OK);
                }

                DqoUserProfileModel dqoUserProfileModel = DqoUserProfileModel.fromApiKeyAndPrincipal(
                        apiKey, principal, this.dataCatalogHealthSendService.isSynchronizationSupported());
                if (this.dataDomainRegistry.getNestedDataDomains() == null) {
                    dqoUserProfileModel.setCanUseDataDomains(false);
                }

                return new ResponseEntity<>(Mono.just(dqoUserProfileModel), HttpStatus.OK);
        }));
    }

    /**
     * Issues a local API Key for the calling user.
     * @return The local API key issued for the calling user.
     */
    @GetMapping(value = "/issueapikey", produces = "application/json")
    @ApiOperation(value = "issueApiKey", notes = "Issues a local API Key for the calling user. This API Key can be used to authenticate using the DQOps REST API client. " +
            "This API Key should be passed in the \"Authorization\" HTTP header in the format \"Authorization: Bearer <api_key>\".",
            response = String.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<String>>> issueApiKey(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
                return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
                    SignedObject<DqoUserTokenPayload> signedLocalApiKey = this.instanceCloudLoginService.issueApiKey(principal);

                    return new ResponseEntity<>(Mono.just(signedLocalApiKey.getSignedHex()), HttpStatus.OK);
                }));
    }
}
