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
package ai.dqo.rest.controllers;

import ai.dqo.core.dqocloud.dashboards.LookerStudioUrlService;
import ai.dqo.metadata.dashboards.DashboardSpec;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.rest.models.dashboards.AuthenticatedDashboardModel;
import ai.dqo.metadata.dashboards.DashboardsFolderListSpec;
import ai.dqo.metadata.dashboards.DashboardsFolderSpec;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller that provides access to data quality dashboards.
 */
@RestController
@RequestMapping("/api/dashboards")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Dashboards", description = "Provides access to data quality dashboards")
public class DashboardsController {
    private final LookerStudioUrlService lookerStudioUrlService;
    private DqoHomeContextFactory dqoHomeContextFactory;


    /**
     * Default dependency injection constructor.
     * @param lookerStudioUrlService Looker studio URL service, creates authenticated urls.
     * @param dqoHomeContextFactory Dqo home context factory.
     */
    @Autowired
    public DashboardsController(LookerStudioUrlService lookerStudioUrlService,
                                DqoHomeContextFactory dqoHomeContextFactory) {
        this.lookerStudioUrlService = lookerStudioUrlService;
        this.dqoHomeContextFactory = dqoHomeContextFactory;
    }

    /**
     * Returns a list of root folders with supported dashboards.
     * @return List of root folders with supported dashboards.
     */
    @GetMapping
    @ApiOperation(value = "getAllDashboards", notes = "Returns a list of root folders with dashboards", response = DashboardsFolderSpec[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DashboardsFolderSpec[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<DashboardsFolderSpec>> getAllDashboards() {
        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        DashboardsFolderListSpec dashboardList= dqoHome.getDashboards().getSpec();

        return new ResponseEntity<>(Flux.fromStream(dashboardList.stream()), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a model of a single dashboard in one level folder, generating also an authenticated url.
     * @param folder Folder name.
     * @param dashboardName Dashboard name.
     * @return Dashboard model with the authenticated url.
     */
    @GetMapping("/{folder}/{dashboardName}")
    @ApiOperation(value = "getDashboardLevel1", notes = "Returns a single dashboard in one folder with a temporary authenticated url", response = AuthenticatedDashboardModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel1(
            @ApiParam("Root folder name") @PathVariable String folder,
            @ApiParam("Dashboard name") @PathVariable String dashboardName) {

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        DashboardsFolderListSpec rootFolders= dqoHome.getDashboards().getSpec();

        DashboardsFolderSpec folder1Spec = rootFolders.getFolderByName(folder);
        if (folder1Spec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DashboardSpec dashboard = folder1Spec.getDashboards().getDashboardByName(dashboardName);
        if (dashboard == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard);
        AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(folder, dashboard, authenticatedDashboardUrl);
        return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a model of a single dashboard in two level folder, generating also an authenticated url.
     * @param folder1 Folder name.
     * @param folder2 Folder name.
     * @param dashboardName Dashboard name.
     * @return Dashboard model with the authenticated url.
     */
    @GetMapping("/{folder1}/{folder2}/{dashboardName}")
    @ApiOperation(value = "getDashboardLevel2", notes = "Returns a single dashboard in two folders with a temporary authenticated url", response = AuthenticatedDashboardModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel2(
            @ApiParam("Root folder name") @PathVariable String folder1,
            @ApiParam("Second level folder name") @PathVariable String folder2,
            @ApiParam("Dashboard name") @PathVariable String dashboardName) {
        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        DashboardsFolderListSpec rootFolders= dqoHome.getDashboards().getSpec();

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

        String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard);
        AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(
                folder1 + "/" + folder2, dashboard, authenticatedDashboardUrl);
        return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a model of a single dashboard in three level folder, generating also an authenticated url.
     * @param folder1 Folder name.
     * @param folder2 Folder name.
     * @param folder3 Folder name.
     * @param dashboardName Dashboard name.
     * @return Dashboard model with the authenticated url.
     */
    @GetMapping("/{folder1}/{folder2}/{folder3}/{dashboardName}")
    @ApiOperation(value = "getDashboardLevel3", notes = "Returns a single dashboard in three folders with a temporary authenticated url", response = AuthenticatedDashboardModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard returned", response = AuthenticatedDashboardModel.class),
            @ApiResponse(code = 404, message = "Dashboard not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<AuthenticatedDashboardModel>> getDashboardLevel3(
            @ApiParam("Root folder name") @PathVariable String folder1,
            @ApiParam("Second level folder name") @PathVariable String folder2,
            @ApiParam("Third level folder name") @PathVariable String folder3,
            @ApiParam("Dashboard name") @PathVariable String dashboardName) {

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        DashboardsFolderListSpec rootFolders= dqoHome.getDashboards().getSpec();

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

        String authenticatedDashboardUrl = this.lookerStudioUrlService.makeAuthenticatedDashboardUrl(dashboard);
        AuthenticatedDashboardModel authenticatedDashboardModel = new AuthenticatedDashboardModel(
                folder1 + "/" + folder2 + "/" + folder3, dashboard, authenticatedDashboardUrl);
        return new ResponseEntity<>(Mono.just(authenticatedDashboardModel), HttpStatus.OK); // 200
    }
}
