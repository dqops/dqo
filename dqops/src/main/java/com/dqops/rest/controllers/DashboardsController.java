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
import com.dqops.core.dqocloud.apikey.DqoCloudInvalidKeyException;
import com.dqops.core.dqocloud.apikey.DqoCloudLicenseType;
import com.dqops.core.dqocloud.dashboards.LookerStudioUrlService;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.dashboards.DashboardSpec;
import com.dqops.metadata.dashboards.DashboardsFolderListSpec;
import com.dqops.metadata.dashboards.DashboardsFolderSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.dashboards.AuthenticatedDashboardModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.services.metadata.DashboardsProvider;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

/**
 * Controller that provides access to data quality dashboards.
 */
@RestController
@RequestMapping("/api/dashboards")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Dashboards", description = "Operations for retrieving the list of data quality dashboards supported by DQOps and issuing short-term access keys to open a dashboard.")
@Slf4j
public class DashboardsController {
    private final LookerStudioUrlService lookerStudioUrlService;
    private final DashboardsProvider dashboardsProvider;
    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoCloudApiKeyProvider cloudApiKeyProvider;

    /**
     * Default dependency injection constructor.
     * @param lookerStudioUrlService Looker studio URL service, creates authenticated urls.
     * @param dashboardsProvider Dashboard tree provider.
     * @param userHomeContextFactory User home factory.
     * @param dqoCloudApiKeyProvider Cloud DQOps key provider.
     */
    @Autowired
    public DashboardsController(LookerStudioUrlService lookerStudioUrlService,
                                DashboardsProvider dashboardsProvider,
                                UserHomeContextFactory userHomeContextFactory,
                                DqoCloudApiKeyProvider dqoCloudApiKeyProvider) {
        this.lookerStudioUrlService = lookerStudioUrlService;
        this.dashboardsProvider = dashboardsProvider;
        this.userHomeContextFactory = userHomeContextFactory;
        this.cloudApiKeyProvider = dqoCloudApiKeyProvider;
    }

    /**
     * Returns a list of root folders with supported dashboards.
     * @return List of root folders with supported dashboards.
     */
    @GetMapping(produces = "application/json")
    @ApiOperation(value = "getAllDashboards", notes = "Returns a list of root folders with dashboards", response = DashboardsFolderSpec[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DashboardsFolderSpec[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<DashboardsFolderSpec>> getAllDashboards(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        DashboardsFolderListSpec dashboardList = this.dashboardsProvider.getDashboardTree();
        DashboardsFolderListSpec combinedDefaultAndUserDashboards = dashboardList;

        UserDomainIdentity userIdentity = principal.getDataDomainIdentity();
        DqoCloudApiKey cloudApiKey = this.cloudApiKeyProvider.getApiKey(userIdentity);
        if (cloudApiKey != null && cloudApiKey.getApiKeyPayload().getLicenseType() != DqoCloudLicenseType.FREE) {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity, true);
            UserHome userHome = userHomeContext.getUserHome();
            DashboardsFolderListSpec userDashboardsList = userHome.getDashboards().getSpec();
            combinedDefaultAndUserDashboards = dashboardList.merge(userDashboardsList);
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl
                        .maxAge(Duration.ofDays(1))
                        .cachePublic()
                        .mustRevalidate())
                .lastModified(combinedDefaultAndUserDashboards.getFileLastModified())
                .eTag(userIdentity.getDataDomainFolder() + "/" + combinedDefaultAndUserDashboards.getFileLastModified().toString())
                .body(Flux.fromStream(combinedDefaultAndUserDashboards.stream())); // 200
    }

    /**
     * Retrieves a custom user dashboard from the definition of custom dashboards.
     * @param principal DQOps user principal.
     * @param dashboardName Dashboard name.
     * @param folders Folders structure to traverse.
     * @return Custom dashboard specification or null, when the dashboard was not found.
     */
    protected DashboardSpec findUserCustomDashboard(DqoUserPrincipal principal, String dashboardName, String... folders) {
        DqoCloudApiKey cloudApiKey = this.cloudApiKeyProvider.getApiKey(principal.getDataDomainIdentity());
        if (cloudApiKey == null || cloudApiKey.getApiKeyPayload().getLicenseType() == DqoCloudLicenseType.FREE) {
            return null;
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();
        DashboardsFolderListSpec userDashboardsList = userHome.getDashboards().getSpec();
        if (userDashboardsList == null || userDashboardsList.size() == 0) {
            return null;
        }

        return userDashboardsList.getDashboard(dashboardName, folders);
    }

    /**
     * Retrieves a model of a single dashboard in one level folder, generating also an authenticated url.
     * @param folder Folder name.
     * @param dashboardName Dashboard name.
     * @param windowLocationOrigin The value of the window.location.origin
     * @return Dashboard model with the authenticated url.
     */
    @GetMapping(value = "/{folder}/{dashboardName}", produces = "application/json")
    @ApiOperation(value = "getDashboardLevel1", notes = "Returns a single dashboard in the tree of folder with a temporary authenticated url",
            response = AuthenticatedDashboardModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 401, message = "Unauthorized, DQOps Cloud API key is outdated, please run \"cloud login\" from the DQOps shell to update the API key"),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel1(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Root folder name") @PathVariable String folder,
            @ApiParam("Dashboard name") @PathVariable String dashboardName,
            @ApiParam(name = "windowLocationOrigin", value = "Optional url of the DQOps instance, it should be the value of window.location.origin.", required = false)
            @RequestParam(required = false) Optional<String> windowLocationOrigin) {
        DashboardSpec dashboard = this.findUserCustomDashboard(principal, dashboardName, folder);
        if (dashboard == null) {
            DashboardsFolderListSpec rootFolders = this.dashboardsProvider.getDashboardTree();
            dashboard = rootFolders.getDashboard(dashboardName, folder);
        }

        if (dashboard == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String dqoUrlOrigin = windowLocationOrigin.orElse(null);

        try {
            String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard, dqoUrlOrigin, principal);
            AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(folder, dashboard, authenticatedDashboardUrl);
            return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
        }
        catch (DqoCloudInvalidKeyException invalidKeyException) {
            log.warn("DQOps Cloud API Key was invalid, please run \"cloud login\" again from the DQOps shell", invalidKeyException);
            return new ResponseEntity<>(Mono.empty(), HttpStatus.UNAUTHORIZED); // 401
        }
    }

    /**
     * Retrieves a model of a single dashboard in two level folder, generating also an authenticated url.
     * @param folder1 Folder name.
     * @param folder2 Folder name.
     * @param dashboardName Dashboard name.
     * @param windowLocationOrigin The value of the window.location.origin
     * @return Dashboard model with the authenticated url.
     */
    @GetMapping(value = "/{folder1}/{folder2}/{dashboardName}", produces = "application/json")
    @ApiOperation(value = "getDashboardLevel2", notes = "Returns a single dashboard in the tree of folders with a temporary authenticated url",
            response = AuthenticatedDashboardModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 401, message = "Unauthorized, DQOps Cloud API key is outdated, please run \"cloud login\" from the DQOps shell to update the API key"),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel2(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Root folder name") @PathVariable String folder1,
            @ApiParam("Second level folder name") @PathVariable String folder2,
            @ApiParam("Dashboard name") @PathVariable String dashboardName,
            @ApiParam(name = "windowLocationOrigin", value = "Optional url of the DQOps instance, it should be the value of window.location.origin.", required = false)
            @RequestParam(required = false) Optional<String> windowLocationOrigin) {
        DashboardSpec dashboard = this.findUserCustomDashboard(principal, dashboardName, folder1, folder2);
        if (dashboard == null) {
            DashboardsFolderListSpec rootFolders = this.dashboardsProvider.getDashboardTree();
            dashboard = rootFolders.getDashboard(dashboardName, folder1, folder2);
        }

        if (dashboard == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String dqoUrlOrigin = windowLocationOrigin.orElse(null);

        try {
            String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard, dqoUrlOrigin, principal);
            AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(
                    folder1 + "/" + folder2, dashboard, authenticatedDashboardUrl);
            return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
        }
        catch (DqoCloudInvalidKeyException invalidKeyException) {
            log.warn("DQOps Cloud API Key was invalid, please run \"cloud login\" again from the DQOps shell", invalidKeyException);
            return new ResponseEntity<>(Mono.empty(), HttpStatus.UNAUTHORIZED); // 401
        }
    }

    /**
     * Retrieves a model of a single dashboard in three level folder, generating also an authenticated url.
     * @param folder1 Folder name.
     * @param folder2 Folder name.
     * @param folder3 Folder name.
     * @param dashboardName Dashboard name.
     * @param windowLocationOrigin The value of the window.location.origin
     * @return Dashboard model with the authenticated url.
     */
    @GetMapping(value = "/{folder1}/{folder2}/{folder3}/{dashboardName}", produces = "application/json")
    @ApiOperation(value = "getDashboardLevel3", notes = "Returns a single dashboard in the tree of folders with a temporary authenticated url",
            response = AuthenticatedDashboardModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 401, message = "Unauthorized, DQOps Cloud API key is outdated, please run \"cloud login\" from the DQOps shell to update the API key"),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel3(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Root folder name") @PathVariable String folder1,
            @ApiParam("Second level folder name") @PathVariable String folder2,
            @ApiParam("Third level folder name") @PathVariable String folder3,
            @ApiParam("Dashboard name") @PathVariable String dashboardName,
            @ApiParam(name = "windowLocationOrigin", value = "Optional url of the DQOps instance, it should be the value of window.location.origin.", required = false)
            @RequestParam(required = false) Optional<String> windowLocationOrigin) {
        DashboardSpec dashboard = this.findUserCustomDashboard(principal, dashboardName, folder1, folder2, folder3);
        if (dashboard == null) {
            DashboardsFolderListSpec rootFolders = this.dashboardsProvider.getDashboardTree();
            dashboard = rootFolders.getDashboard(dashboardName, folder1, folder2, folder3);
        }

        if (dashboard == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String dqoUrlOrigin = windowLocationOrigin.orElse(null);

        try {
            String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard, dqoUrlOrigin, principal);
            AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(
                    folder1 + "/" + folder2 + "/" + folder3, dashboard, authenticatedDashboardUrl);
            return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
        }
        catch (DqoCloudInvalidKeyException invalidKeyException) {
            log.warn("DQOps Cloud API Key was invalid, please run \"cloud login\" again from the DQOps shell", invalidKeyException);
            return new ResponseEntity<>(Mono.empty(), HttpStatus.UNAUTHORIZED); // 401
        }
    }

    /**
     * Retrieves a model of a single dashboard in four level folder, generating also an authenticated url.
     * @param folder1 Folder name.
     * @param folder2 Folder name.
     * @param folder3 Folder name.
     * @param folder4 Folder name.
     * @param windowLocationOrigin The value of the window.location.origin
     * @param dashboardName Dashboard name.
     * @return Dashboard model with the authenticated url.
     */
    @GetMapping(value = "/{folder1}/{folder2}/{folder3}/{folder4}/{dashboardName}", produces = "application/json")
    @ApiOperation(value = "getDashboardLevel4", notes = "Returns a single dashboard in the tree of folders with a temporary authenticated url",
            response = AuthenticatedDashboardModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 401, message = "Unauthorized, DQOps Cloud API key is outdated, please run \"cloud login\" from the DQOps shell to update the API key"),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel4(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Root folder name") @PathVariable String folder1,
            @ApiParam("Second level folder name") @PathVariable String folder2,
            @ApiParam("Third level folder name") @PathVariable String folder3,
            @ApiParam("Fourth level folder name") @PathVariable String folder4,
            @ApiParam("Dashboard name") @PathVariable String dashboardName,
            @ApiParam(name = "windowLocationOrigin", value = "Optional url of the DQOps instance, it should be the value of window.location.origin.", required = false)
            @RequestParam(required = false) Optional<String> windowLocationOrigin) {
        DashboardSpec dashboard = this.findUserCustomDashboard(principal, dashboardName, folder1, folder2, folder3, folder4);
        if (dashboard == null) {
            DashboardsFolderListSpec rootFolders = this.dashboardsProvider.getDashboardTree();
            dashboard = rootFolders.getDashboard(dashboardName, folder1, folder2, folder3, folder4);
        }

        if (dashboard == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String dqoUrlOrigin = windowLocationOrigin.orElse(null);

        try {
            String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard, dqoUrlOrigin, principal);
            AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(
                    folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4, dashboard, authenticatedDashboardUrl);
            return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
        }
        catch (DqoCloudInvalidKeyException invalidKeyException) {
            log.warn("DQOps Cloud API Key was invalid, please run \"cloud login\" again from the DQOps shell", invalidKeyException);
            return new ResponseEntity<>(Mono.empty(), HttpStatus.UNAUTHORIZED); // 401
        }
    }

    /**
     * Retrieves a model of a single dashboard in fifth level folder, generating also an authenticated url.
     * @param folder1 Folder name.
     * @param folder2 Folder name.
     * @param folder3 Folder name.
     * @param folder4 Folder name.
     * @param folder5 Folder name.
     * @param windowLocationOrigin The value of the window.location.origin
     * @param dashboardName Dashboard name.
     * @return Dashboard model with the authenticated url.
     */
    @GetMapping(value = "/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{dashboardName}", produces = "application/json")
    @ApiOperation(value = "getDashboardLevel5", notes = "Returns a single dashboard in the tree of folders with a temporary authenticated url",
            response = AuthenticatedDashboardModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 401, message = "Unauthorized, DQOps Cloud API key is outdated, please run \"cloud login\" from the DQOps shell to update the API key"),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel5(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Root folder name") @PathVariable String folder1,
            @ApiParam("Second level folder name") @PathVariable String folder2,
            @ApiParam("Third level folder name") @PathVariable String folder3,
            @ApiParam("Fourth level folder name") @PathVariable String folder4,
            @ApiParam("Fifth level folder name") @PathVariable String folder5,
            @ApiParam("Dashboard name") @PathVariable String dashboardName,
            @ApiParam(name = "windowLocationOrigin", value = "Optional url of the DQOps instance, it should be the value of window.location.origin.", required = false)
            @RequestParam(required = false) Optional<String> windowLocationOrigin) {
        DashboardSpec dashboard = this.findUserCustomDashboard(principal, dashboardName, folder1, folder2, folder3, folder4, folder5);
        if (dashboard == null) {
            DashboardsFolderListSpec rootFolders = this.dashboardsProvider.getDashboardTree();
            dashboard = rootFolders.getDashboard(dashboardName, folder1, folder2, folder3, folder4, folder5);
        }

        if (dashboard == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String dqoUrlOrigin = windowLocationOrigin.orElse(null);

        try {
            String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard, dqoUrlOrigin, principal);
            AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(
                    folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4 + "/" + folder5, dashboard, authenticatedDashboardUrl);
            return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
        }
        catch (DqoCloudInvalidKeyException invalidKeyException) {
            log.warn("DQOps Cloud API Key was invalid, please run \"cloud login\" again from the DQOps shell", invalidKeyException);
            return new ResponseEntity<>(Mono.empty(), HttpStatus.UNAUTHORIZED); // 401
        }
    }
}
