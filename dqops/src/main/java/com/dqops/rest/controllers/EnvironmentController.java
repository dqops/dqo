/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.controllers;

import com.dqops.core.catalogsync.DataCatalogHealthSendService;
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
import com.dqops.utils.logging.DownloadLogsService;
import com.dqops.utils.threading.CompletableFutureRunner;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;
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
    private DownloadLogsService downloadLogsService;


    /**
     * Dependency injection constructor of the environment controller.
     * @param dqoCloudApiKeyProvider DQOps API key provider.
     * @param springEnvironment Spring Boot environment.
     * @param instanceCloudLoginService Local instance authentication token service, used to issue a local API key.
     * @param dataDomainRegistry Data domain registry - to detect if there are any data domains, so data domains are supported.
     * @param dataCatalogHealthSendService Data catalog notification send service - to see if data catalog synchronization is possible.
     * @param downloadLogsService Service that generates a ZIP file with all logs.
     */
    @Autowired
    public EnvironmentController(DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                 Environment springEnvironment,
                                 InstanceCloudLoginService instanceCloudLoginService,
                                 LocalDataDomainRegistry dataDomainRegistry,
                                 DataCatalogHealthSendService dataCatalogHealthSendService,
                                 DownloadLogsService downloadLogsService) {
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.springEnvironment = springEnvironment;
        this.instanceCloudLoginService = instanceCloudLoginService;
        this.dataDomainRegistry = dataDomainRegistry;
        this.dataCatalogHealthSendService = dataCatalogHealthSendService;
        this.downloadLogsService = downloadLogsService;
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

    /**
     * Downloads a ZIP file with the log files.
     * @return ZIP file with all the log files.
     */
    @GetMapping(value = "/logs/download", produces = "application/zip")
    @ApiOperation(value = "downloadLogs", notes = "Downloads logs as a zip file", response = byte[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = byte[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<InputStreamResource>>> downloadLogs(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            HttpHeaders headers = new HttpHeaders();
            String zipFileName ="dqops-logs-" + LocalDateTime.now().toString().replace(':', '-') + ".zip";
            headers.setContentDisposition(ContentDisposition.attachment().filename(zipFileName).build());


            InputStream zipLogsInputStream = this.downloadLogsService.zipLogsOnTheFly();
            InputStreamResource inputStreamResource = new InputStreamResource(zipLogsInputStream);

            return new ResponseEntity<>(Mono.just(inputStreamResource), headers, HttpStatus.OK);
        }));
    }
}
