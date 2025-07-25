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

import com.dqops.core.configuration.DqoInstanceConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.domains.DataDomainsService;
import com.dqops.core.domains.DqoDataDomainException;
import com.dqops.core.domains.LocalDataDomainModel;
import com.dqops.core.domains.LocalDataDomainRegistry;
import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.core.dqocloud.login.DqoUserTokenPayload;
import com.dqops.core.dqocloud.login.InstanceCloudLoginService;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.settings.domains.LocalDataDomainSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.rest.server.LocalUrlAddressesProvider;
import com.dqops.rest.server.authentication.AuthenticateWithDqoCloudWebFilter;
import com.dqops.utils.threading.CompletableFutureRunner;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * REST API controller for managing multiple data domains.
 */
@RestController
@RequestMapping("/api/domains")
@ResponseStatus(HttpStatus.OK)
@Api(value = "DataDomains", description = "Data domain management API to create different data domains.")
public class DataDomainsController {
    private final DataDomainsService dataDomainsService;
    private final UserHomeContextFactory userHomeContextFactory;
    private final LocalDataDomainRegistry localDataDomainRegistry;
    private final DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties;
    private final DqoUserConfigurationProperties dqoUserConfigurationProperties;
    private final InstanceCloudLoginService instanceCloudLoginService;


    /**
     * Dependency injection constructor.
     *
     * @param dataDomainsService Data domains service.
     * @param userHomeContextFactory User home context factory.
     * @param localDataDomainRegistry Local data domain registry.
     * @param dqoInstanceConfigurationProperties DQOps instance configuration - the cookie expiration time.
     * @param dqoUserConfigurationProperties DQO User Home configuration - to identify the default data domain.
     * @param instanceCloudLoginService Cloud login controller.
     */
    @Autowired
    public DataDomainsController(DataDomainsService dataDomainsService,
                                 UserHomeContextFactory userHomeContextFactory,
                                 LocalDataDomainRegistry localDataDomainRegistry,
                                 DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties,
                                 DqoUserConfigurationProperties dqoUserConfigurationProperties,
                                 InstanceCloudLoginService instanceCloudLoginService) {
        this.dataDomainsService = dataDomainsService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.localDataDomainRegistry = localDataDomainRegistry;
        this.dqoInstanceConfigurationProperties = dqoInstanceConfigurationProperties;
        this.dqoUserConfigurationProperties = dqoUserConfigurationProperties;
        this.instanceCloudLoginService = instanceCloudLoginService;
    }

    /**
     * Returns a list of all local data domains.
     * @return List of all local data domains.
     */
    @GetMapping(value = "/", produces = "application/json")
    @ApiOperation(value = "getLocalDataDomains", notes = "Returns a list of local data domains that this instance is maintaining. Data domains are supported only in an ENTERPRISE versions of DQOps.",
            response = LocalDataDomainModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LocalDataDomainModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<LocalDataDomainModel>>> getLocalDataDomains(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            List<LocalDataDomainModel> allDataDomains = this.dataDomainsService.getAllDataDomains();
            LinkedHashMap<String, DqoUserRole> domainRoles = principal.getDomainRoles();
            if (domainRoles == null) {
                ArrayList<LocalDataDomainModel> rootDomainList = new ArrayList<>();
                boolean isRootDomain = Objects.equals(dqoUserConfigurationProperties.getDefaultDataDomain(), UserDomainIdentity.ROOT_DATA_DOMAIN);
                rootDomainList.add(new LocalDataDomainModel() {{
                    setDomainName(isRootDomain ? UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME : dqoUserConfigurationProperties.getDefaultDataDomain());
                    setDisplayName(isRootDomain ? UserDomainIdentity.ROOT_DOMAIN_DISPLAY_NAME : dqoUserConfigurationProperties.getDefaultDataDomain());
                }});
                return new ResponseEntity<>(Flux.fromStream(rootDomainList.stream()), HttpStatus.OK);
            }

            if (principal.getAccountRole() == DqoUserRole.NONE) {
                allDataDomains.removeIf(model -> {
                    String domainName = model.getDomainName();
                    if (Objects.equals(domainName, UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME)) {
                        domainName = UserDomainIdentity.ROOT_DATA_DOMAIN;
                    }

                    return !domainRoles.containsKey(domainName) ||
                            domainRoles.get(domainName) == DqoUserRole.NONE;
                });
            }

            return new ResponseEntity<>(Flux.fromStream(allDataDomains.stream()), HttpStatus.OK);
        }));
    }

    /**
     * Creates (adds) a new data domain.
     * @param dataDomainDisplayName Data domain display name.
     * @return Empty response.
     */
    @PostMapping(value = "/{dataDomainDisplayName}", produces = "application/json")
    @ApiOperation(value = "createDataDomain", notes = "Creates a new data domain given a data domain display name.", response = LocalDataDomainModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "New data domain successfully created", response = LocalDataDomainModel.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 409, message = "Data domain with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.MANAGE_ACCOUNT})
    public Mono<ResponseEntity<Mono<LocalDataDomainModel>>> createDataDomain(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data domain display name") @PathVariable String dataDomainDisplayName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(dataDomainDisplayName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            LocalSettingsSpec localSettingsSpec = userHome.getSettings().getSpec();
            if (localSettingsSpec != null && localSettingsSpec.getDataDomains().values().stream()
                    .anyMatch(spec -> Objects.equals(spec.getDisplayName(), dataDomainDisplayName) || Objects.equals(spec.getDataDomainName(), dataDomainDisplayName))) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
            }

            try {
                LocalDataDomainModel localDataDomainModel = this.dataDomainsService.createDataDomain(dataDomainDisplayName);
                return new ResponseEntity<>(Mono.justOrEmpty(localDataDomainModel), HttpStatus.OK);
            }
            catch (DqoDataDomainException ddex) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST);
            }
        }));
    }

    /**
     * Deletes a data domain.
     * @param dataDomainName  Data domain name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/{dataDomainName}", produces = "application/json")
    @ApiOperation(value = "deleteDataDomain", notes = "Deletes a data domain. The domain is deleted in the DQOps SaaS cloud and locally.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data domain successfully deleted", response = Void.class),
            @ApiResponse(code = 400, message = "Data domain cannot be deleted"),
            @ApiResponse(code = 404, message = "Data domain not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.MANAGE_ACCOUNT})
    public Mono<ResponseEntity<Mono<Void>>> deleteDataDomain(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data domain name") @PathVariable String dataDomainName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(dataDomainName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            LocalSettingsSpec localSettingsSpec = userHome.getSettings().getSpec();
            if (localSettingsSpec == null || !localSettingsSpec.getDataDomains().containsKey(dataDomainName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            try {
                this.dataDomainsService.deleteDataDomain(dataDomainName);

                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
            }
            catch (DqoDataDomainException ddex) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST);
            }
        }));
    }

    /**
     * Synchronizes the domains in the SaaS cloud to this instance. All data domains will be created locally.
     * @return Empty response.
     */
    @PatchMapping(value = "/", produces = "application/json")
    @ApiOperation(value = "synchronizeDataDomains", notes = "Synchronizes the data domains in the SaaS DQOps Cloud to this instance. All data domains will be created locally.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data domain successfully synchronized", response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.MANAGE_ACCOUNT})
    public Mono<ResponseEntity<Mono<Void>>> synchronizeDataDomains(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            this.dataDomainsService.synchronizeDataDomainList(false);

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Switches the data domain.
     * @param principal User principal.
     * @param httpRequest Http server request - to get the base URL.
     * @param dataDomainName  Data domain name.
     * @return Empty response.
     */
    @GetMapping(value = "/{dataDomainName}/switch", produces = "application/json")
    @ApiOperation(value = "switchToDataDomain", notes = "Switches to a different data domain. This operation sends a special cookie and redirects the user to the home screen.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 303, message = "Redirect to a valid screen sent", response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<Void>>> switchToDataDomain(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            ServerHttpRequest httpRequest,
            @ApiParam("Data domain name") @PathVariable String dataDomainName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(dataDomainName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            String returnUrl = httpRequest.getURI().resolve("/").toString();
            URI instanceUri = URI.create(returnUrl);
            String urlHost = instanceUri.getHost();
            String realDataDomainName = Objects.equals(dataDomainName, UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME) ?
                    UserDomainIdentity.ROOT_DATA_DOMAIN : dataDomainName;

            if (!Objects.equals(realDataDomainName, UserDomainIdentity.ROOT_DATA_DOMAIN)) {
                LocalDataDomainSpec localDataDomainSpec = this.localDataDomainRegistry.getNestedDomain(dataDomainName);
                if (localDataDomainSpec == null) {
                    headers.add("Location", returnUrl);
                    return new ResponseEntity<>(Mono.empty(), headers, HttpStatus.SEE_OTHER);
                }
            }

            headers.add("Set-Cookie", AuthenticateWithDqoCloudWebFilter.DATA_DOMAIN_COOKIE + "=" + dataDomainName +"; Max-Age=" +
                    (this.dqoInstanceConfigurationProperties.getAuthenticationTokenExpirationMinutes() * 60L) + "; Path=/; Domain=" + urlHost);

            if (principal.getUserTokenPayload().getAccountRole() != DqoUserRole.NONE ||
                    (principal.getUserTokenPayload().getDomainRoles().containsKey(realDataDomainName) &&
                    principal.getUserTokenPayload().getDomainRoles().get(realDataDomainName) != DqoUserRole.NONE)) {
                // need to update the token and jump to the DQOps login

                String dqoCloudLoginUrl = this.instanceCloudLoginService.makeDqoLoginUrl(returnUrl);
                headers.add("Location", dqoCloudLoginUrl);
            } else {
                headers.add("Location", returnUrl); // ignore and reload
            }

            return new ResponseEntity<>(Mono.empty(), headers, HttpStatus.SEE_OTHER); // 303
        }));
    }
}
