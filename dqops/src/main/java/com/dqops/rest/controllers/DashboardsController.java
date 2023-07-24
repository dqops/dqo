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

import com.dqops.core.dqocloud.apikey.DqoCloudInvalidKeyException;
import com.dqops.core.dqocloud.dashboards.LookerStudioUrlService;
import com.dqops.metadata.dashboards.DashboardSpec;
import com.dqops.metadata.dashboards.DashboardsFolderListSpec;
import com.dqops.metadata.dashboards.DashboardsFolderSpec;
import com.dqops.rest.models.dashboards.AuthenticatedDashboardModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.metadata.DashboardsProvider;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

/**
 * Controller that provides access to data quality dashboards.
 */
@RestController
@RequestMapping("api/dashboards")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Dashboards", description = "Provides access to data quality dashboards")
@Slf4j
public class DashboardsController {
    private final LookerStudioUrlService lookerStudioUrlService;
    private DashboardsProvider dashboardsProvider;


    /**
     * Default dependency injection constructor.
     * @param lookerStudioUrlService Looker studio URL service, creates authenticated urls.
     * @param dashboardsProvider Dashboard tree provider.
     */
    @Autowired
    public DashboardsController(LookerStudioUrlService lookerStudioUrlService,
                                DashboardsProvider dashboardsProvider) {
        this.lookerStudioUrlService = lookerStudioUrlService;
        this.dashboardsProvider = dashboardsProvider;
    }

    /**
     * Returns a list of root folders with supported dashboards.
     * @return List of root folders with supported dashboards.
     */
    @GetMapping(produces = "application/json")
    @ApiOperation(value = "getAllDashboards", notes = "Returns a list of root folders with dashboards", response = DashboardsFolderSpec[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DashboardsFolderSpec[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<DashboardsFolderSpec>> getAllDashboards() {
        DashboardsFolderListSpec dashboardList = this.dashboardsProvider.getDashboardTree();

        return ResponseEntity.ok()
                .cacheControl(CacheControl
                        .maxAge(Duration.ofDays(1))
                        .cachePublic()
                        .mustRevalidate())
                .lastModified(dashboardList.getFileLastModified())
                .eTag(dashboardList.getFileLastModified().toString())
                .body(Flux.fromStream(dashboardList.stream())); // 200
    }

    /**
     * Retrieves a model of a single dashboard in one level folder, generating also an authenticated url.
     * @param folder Folder name.
     * @param dashboardName Dashboard name.
     * @param windowLocationOrigin The value of the window.location.origin
     * @return Dashboard model with the authenticated url.
     */
    @GetMapping(value = "/{folder}/{dashboardName}", produces = "application/json")
    @ApiOperation(value = "getDashboardLevel1", notes = "Returns a single dashboard in one folder with a temporary authenticated url", response = AuthenticatedDashboardModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 401, message = "Unauthorized, DQO Cloud API key is outdated, please run \"cloud login\" from the DQO shell to update the API key"),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel1(
            @ApiParam("Root folder name") @PathVariable String folder,
            @ApiParam("Dashboard name") @PathVariable String dashboardName,
            @ApiParam(name = "windowLocationOrigin", value = "Optional url of the DQO instance, it should be the value of window.location.origin.", required = false)
            @RequestParam(required = false) Optional<String> windowLocationOrigin) {
        DashboardsFolderListSpec rootFolders = this.dashboardsProvider.getDashboardTree();

        DashboardsFolderSpec folder1Spec = rootFolders.getFolderByName(folder);
        if (folder1Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardSpec dashboard = folder1Spec.getDashboards().getDashboardByName(dashboardName);
        if (dashboard == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String dqoUrlOrigin = windowLocationOrigin.orElse(null);

        try {
            String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard, dqoUrlOrigin);
            AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(folder, dashboard, authenticatedDashboardUrl);
            return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
        }
        catch (DqoCloudInvalidKeyException invalidKeyException) {
            log.warn("DQO Cloud API Key was invalid, please run \"cloud login\" again from the DQO shell", invalidKeyException);
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
    @ApiOperation(value = "getDashboardLevel2", notes = "Returns a single dashboard in two folders with a temporary authenticated url", response = AuthenticatedDashboardModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 401, message = "Unauthorized, DQO Cloud API key is outdated, please run \"cloud login\" from the DQO shell to update the API key"),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel2(
            @ApiParam("Root folder name") @PathVariable String folder1,
            @ApiParam("Second level folder name") @PathVariable String folder2,
            @ApiParam("Dashboard name") @PathVariable String dashboardName,
            @ApiParam(name = "windowLocationOrigin", value = "Optional url of the DQO instance, it should be the value of window.location.origin.", required = false)
            @RequestParam(required = false) Optional<String> windowLocationOrigin) {
        DashboardsFolderListSpec rootFolders = this.dashboardsProvider.getDashboardTree();

        DashboardsFolderSpec folder1Spec = rootFolders.getFolderByName(folder1);
        if (folder1Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardsFolderSpec folder2Spec = folder1Spec.getFolders().getFolderByName(folder2);
        if (folder2Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardSpec dashboard = folder2Spec.getDashboards().getDashboardByName(dashboardName);
        if (dashboard == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String dqoUrlOrigin = windowLocationOrigin.orElse(null);

        try {
            String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard, dqoUrlOrigin);
            AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(
                    folder1 + "/" + folder2, dashboard, authenticatedDashboardUrl);
            return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
        }
        catch (DqoCloudInvalidKeyException invalidKeyException) {
            log.warn("DQO Cloud API Key was invalid, please run \"cloud login\" again from the DQO shell", invalidKeyException);
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
    @ApiOperation(value = "getDashboardLevel3", notes = "Returns a single dashboard in three folders with a temporary authenticated url", response = AuthenticatedDashboardModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 401, message = "Unauthorized, DQO Cloud API key is outdated, please run \"cloud login\" from the DQO shell to update the API key"),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel3(
            @ApiParam("Root folder name") @PathVariable String folder1,
            @ApiParam("Second level folder name") @PathVariable String folder2,
            @ApiParam("Third level folder name") @PathVariable String folder3,
            @ApiParam("Dashboard name") @PathVariable String dashboardName,
            @ApiParam(name = "windowLocationOrigin", value = "Optional url of the DQO instance, it should be the value of window.location.origin.", required = false)
            @RequestParam(required = false) Optional<String> windowLocationOrigin) {
        DashboardsFolderListSpec rootFolders = this.dashboardsProvider.getDashboardTree();

        DashboardsFolderSpec folder1Spec = rootFolders.getFolderByName(folder1);
        if (folder1Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardsFolderSpec folder2Spec = folder1Spec.getFolders().getFolderByName(folder2);
        if (folder2Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardsFolderSpec folder3Spec = folder2Spec.getFolders().getFolderByName(folder3);
        if (folder3Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardSpec dashboard = folder3Spec.getDashboards().getDashboardByName(dashboardName);
        if (dashboard == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String dqoUrlOrigin = windowLocationOrigin.orElse(null);

        try {
            String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard, dqoUrlOrigin);
            AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(
                    folder1 + "/" + folder2 + "/" + folder3, dashboard, authenticatedDashboardUrl);
            return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
        }
        catch (DqoCloudInvalidKeyException invalidKeyException) {
            log.warn("DQO Cloud API Key was invalid, please run \"cloud login\" again from the DQO shell", invalidKeyException);
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
    @ApiOperation(value = "getDashboardLevel4", notes = "Returns a single dashboard in three folders with a temporary authenticated url", response = AuthenticatedDashboardModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 401, message = "Unauthorized, DQO Cloud API key is outdated, please run \"cloud login\" from the DQO shell to update the API key"),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel4(
            @ApiParam("Root folder name") @PathVariable String folder1,
            @ApiParam("Second level folder name") @PathVariable String folder2,
            @ApiParam("Third level folder name") @PathVariable String folder3,
            @ApiParam("Fourth level folder name") @PathVariable String folder4,
            @ApiParam("Dashboard name") @PathVariable String dashboardName,
            @ApiParam(name = "windowLocationOrigin", value = "Optional url of the DQO instance, it should be the value of window.location.origin.", required = false)
            @RequestParam(required = false) Optional<String> windowLocationOrigin) {
        DashboardsFolderListSpec rootFolders = this.dashboardsProvider.getDashboardTree();

        DashboardsFolderSpec folder1Spec = rootFolders.getFolderByName(folder1);
        if (folder1Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardsFolderSpec folder2Spec = folder1Spec.getFolders().getFolderByName(folder2);
        if (folder2Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardsFolderSpec folder3Spec = folder2Spec.getFolders().getFolderByName(folder3);
        if (folder3Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardsFolderSpec folder4Spec = folder3Spec.getFolders().getFolderByName(folder4);
        if (folder4Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardSpec dashboard = folder4Spec.getDashboards().getDashboardByName(dashboardName);
        if (dashboard == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String dqoUrlOrigin = windowLocationOrigin.orElse(null);

        try {
            String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard, dqoUrlOrigin);
            AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(
                    folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4, dashboard, authenticatedDashboardUrl);
            return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
        }
        catch (DqoCloudInvalidKeyException invalidKeyException) {
            log.warn("DQO Cloud API Key was invalid, please run \"cloud login\" again from the DQO shell", invalidKeyException);
            return new ResponseEntity<>(Mono.empty(), HttpStatus.UNAUTHORIZED); // 401
        }
    }
}
