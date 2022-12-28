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

import ai.dqo.metadata.dashboards.DashboardService;
import ai.dqo.metadata.dashboards.DashboardsFolderListSpec;
import ai.dqo.metadata.dashboards.DashboardsFolderSpec;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Controller that provides access to data quality dashboards.
 */
@RestController
@RequestMapping("/api/dashboards")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Dashboards", description = "Provides access to data quality dashboards")
public class DashboardsController {
    private DashboardService dashboardService;

    /**
     * Default dependency injection constructor.
     * @param dashboardService Dashboard service that returns a list of built-in dashboards.
     */
    @Autowired
    public DashboardsController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
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
        DashboardsFolderListSpec dashboardList = this.dashboardService.getDashboards();

        return new ResponseEntity<>(Flux.fromStream(dashboardList.stream()), HttpStatus.OK); // 200
    }
}
