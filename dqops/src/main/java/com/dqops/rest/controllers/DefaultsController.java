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

import com.dqops.checks.defaults.DefaultDailyRecurringObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultMonthlyRecurringObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultProfilingObservabilityCheckSettingsSpec;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.settings.SettingsSpec;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingService;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * REST Api controller that manages the default settings.
 */
@RestController
@RequestMapping("/api/defaults")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Settings", description = "Default settings management for configuring the default data quality checks that are configured for all imported tables and columns.")
public class DefaultsController {
    private ExecutionContextFactory executionContextFactory;
    private SpecToModelCheckMappingService specToModelCheckMappingService;
    private ModelToSpecCheckMappingService modelToSpecCheckMappingService;

    @Autowired
    public DefaultsController(
            ExecutionContextFactory executionContextFactory,
            SpecToModelCheckMappingService specToModelCheckMappingService,
            ModelToSpecCheckMappingService modelToSpecCheckMappingService) {
        this.executionContextFactory = executionContextFactory;
        this.specToModelCheckMappingService = specToModelCheckMappingService;
        this.modelToSpecCheckMappingService = modelToSpecCheckMappingService;
    }

    /**
     * Returns the UI model for the default configuration of default profiling checks.
     * @return Check UI model with the configuration of the profiling checks that are applied to new tables.
     */
    @GetMapping(value = "/defaultchecks/profiling", produces = "application/json")
    @ApiOperation(value = "getDefaultProfilingChecks", notes = "Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported tables and columns.",
            response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultProfilingChecks() {
        ExecutionContext executionContext = this.executionContextFactory.create();
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        SettingsSpec settingsSpec = userHomeContext.getUserHome().getSettings().getSpec();
        DefaultProfilingObservabilityCheckSettingsSpec defaultChecksContainerSpec = null;

        if (settingsSpec == null || settingsSpec.getDefaultDataObservabilityChecks() == null ||
                settingsSpec.getDefaultDataObservabilityChecks().getProfiling() == null) {
            defaultChecksContainerSpec = new DefaultProfilingObservabilityCheckSettingsSpec();
        } else {
            defaultChecksContainerSpec = settingsSpec.getDefaultDataObservabilityChecks().getProfiling();
        }

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(defaultChecksContainerSpec,
                null, null, null, executionContext, null);

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default daily recurring checks.
     * @return Check UI model with the configuration of the daily recurring checks that are applied to new tables.
     */
    @GetMapping(value = "/defaultchecks/dataobservability/recurring/daily", produces = "application/json")
    @ApiOperation(value = "getDefaultDataObservabilityDailyRecurringChecks", notes = "Returns UI model to show and edit the default configuration of the daily recurring (Data Observability and monitoring) checks that are configured for all imported tables and columns.",
            response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultDataObservabilityDailyRecurringChecks() {
        ExecutionContext executionContext = this.executionContextFactory.create();
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        SettingsSpec settingsSpec = userHomeContext.getUserHome().getSettings().getSpec();
        DefaultDailyRecurringObservabilityCheckSettingsSpec defaultChecksContainerSpec = null;

        if (settingsSpec == null || settingsSpec.getDefaultDataObservabilityChecks() == null ||
                settingsSpec.getDefaultDataObservabilityChecks().getRecurringDaily() == null) {
            defaultChecksContainerSpec = new DefaultDailyRecurringObservabilityCheckSettingsSpec();
        } else {
            defaultChecksContainerSpec = settingsSpec.getDefaultDataObservabilityChecks().getRecurringDaily();
        }

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(defaultChecksContainerSpec,
                null, null, null, executionContext, null);

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default monthly recurring checks.
     * @return Check UI model with the configuration of the monthly recurring checks that are applied to new tables.
     */
    @GetMapping(value = "/defaultchecks/dataobservability/recurring/monthly", produces = "application/json")
    @ApiOperation(value = "getDefaultDataObservabilityMonthlyRecurringChecks", notes = "Returns UI model to show and edit the default configuration of the monthly recurring (Data Observability end of month scores) checks that are configured for all imported tables and columns.",
            response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultDataObservabilityMonthlyRecurringChecks() {
        ExecutionContext executionContext = this.executionContextFactory.create();
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        SettingsSpec settingsSpec = userHomeContext.getUserHome().getSettings().getSpec();
        DefaultMonthlyRecurringObservabilityCheckSettingsSpec defaultChecksContainerSpec = null;

        if (settingsSpec == null || settingsSpec.getDefaultDataObservabilityChecks() == null ||
                settingsSpec.getDefaultDataObservabilityChecks().getRecurringMonthly() == null) {
            defaultChecksContainerSpec = new DefaultMonthlyRecurringObservabilityCheckSettingsSpec();
        } else {
            defaultChecksContainerSpec = settingsSpec.getDefaultDataObservabilityChecks().getRecurringMonthly();
        }

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(defaultChecksContainerSpec,
                null, null, null, executionContext, null);

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Updates the configuration of default profiling checks.
     * @param checkContainerModel New configuration of the default profiling checks.
     * @return Empty response.
     */
    @PutMapping(value = "/defaultchecks/profiling", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultProfilingChecks", notes = "New configuration of the default profiling checks. These checks will be applied on new tables and columns.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of profiling checks successfully updated."),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDefaultProfilingChecks(
            @ApiParam("Model with the changes to be applied to the data quality profiling checks configuration")
            @RequestBody Optional<CheckContainerModel> checkContainerModel) {
        ExecutionContext executionContext = this.executionContextFactory.create();
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();

        SettingsSpec settingsSpec = settingsWrapper.getSpec();
        if (settingsSpec == null) {
            settingsSpec = new SettingsSpec();
            settingsWrapper.setSpec(settingsSpec);
        }

        DefaultObservabilityCheckSettingsSpec defaultDataObservabilityChecks = settingsSpec.getDefaultDataObservabilityChecks();
        if (defaultDataObservabilityChecks == null) {
            defaultDataObservabilityChecks = new DefaultObservabilityCheckSettingsSpec();
            settingsSpec.setDefaultDataObservabilityChecks(defaultDataObservabilityChecks);
        }

        DefaultProfilingObservabilityCheckSettingsSpec checkContainerSpec = defaultDataObservabilityChecks.getProfiling();
        if (checkContainerSpec == null) {
            checkContainerSpec = new DefaultProfilingObservabilityCheckSettingsSpec();
        }

        if (checkContainerModel.isPresent()) {
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checkContainerSpec);
            defaultDataObservabilityChecks.setProfiling(checkContainerSpec);
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of default daily recurring (data observability) checks.
     * @param checkContainerModel New configuration of the default daily recurring checks.
     * @return Empty response.
     */
    @PutMapping(value = "/defaultchecks/dataobservability/recurring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultDataObservabilityDailyRecurringChecks", notes = "New configuration of the default daily recurring (data observability) checks. These checks will be applied on new tables and columns.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of daily recurring (daily data observability) checks successfully updated."),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDefaultDataObservabilityDailyRecurringChecks(
            @ApiParam("Model with the changes to be applied to the default configuration of the data observability daily recurring checks configuration")
            @RequestBody Optional<CheckContainerModel> checkContainerModel) {
        ExecutionContext executionContext = this.executionContextFactory.create();
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();

        SettingsSpec settingsSpec = settingsWrapper.getSpec();
        if (settingsSpec == null) {
            settingsSpec = new SettingsSpec();
            settingsWrapper.setSpec(settingsSpec);
        }

        DefaultObservabilityCheckSettingsSpec defaultDataObservabilityChecks = settingsSpec.getDefaultDataObservabilityChecks();
        if (defaultDataObservabilityChecks == null) {
            defaultDataObservabilityChecks = new DefaultObservabilityCheckSettingsSpec();
            settingsSpec.setDefaultDataObservabilityChecks(defaultDataObservabilityChecks);
        }

        DefaultDailyRecurringObservabilityCheckSettingsSpec checkContainerSpec = defaultDataObservabilityChecks.getRecurringDaily();
        if (checkContainerSpec == null) {
            checkContainerSpec = new DefaultDailyRecurringObservabilityCheckSettingsSpec();
        }

        if (checkContainerModel.isPresent()) {
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checkContainerSpec);
            defaultDataObservabilityChecks.setRecurringDaily(checkContainerSpec);
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of default monthly recurring (data observability) checks.
     * @param checkContainerModel New configuration of the default daily recurring checks.
     * @return Empty response.
     */
    @PutMapping(value = "/defaultchecks/dataobservability/recurring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultDataObservabilityMonthlyRecurringChecks", notes = "New configuration of the default monthly recurring checkpoints. These checks will be applied on new tables and columns.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of daily recurring (daily data observability) checks successfully updated."),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDefaultDataObservabilityMonthlyRecurringChecks(
            @ApiParam("Model with the changes to be applied to the default configuration of the data observability monthly recurring checks configuration")
            @RequestBody Optional<CheckContainerModel> checkContainerModel) {
        ExecutionContext executionContext = this.executionContextFactory.create();
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();

        SettingsSpec settingsSpec = settingsWrapper.getSpec();
        if (settingsSpec == null) {
            settingsSpec = new SettingsSpec();
            settingsWrapper.setSpec(settingsSpec);
        }

        DefaultObservabilityCheckSettingsSpec defaultDataObservabilityChecks = settingsSpec.getDefaultDataObservabilityChecks();
        if (defaultDataObservabilityChecks == null) {
            defaultDataObservabilityChecks = new DefaultObservabilityCheckSettingsSpec();
            settingsSpec.setDefaultDataObservabilityChecks(defaultDataObservabilityChecks);
        }

        DefaultMonthlyRecurringObservabilityCheckSettingsSpec checkContainerSpec = defaultDataObservabilityChecks.getRecurringMonthly();
        if (checkContainerSpec == null) {
            checkContainerSpec = new DefaultMonthlyRecurringObservabilityCheckSettingsSpec();
        }

        if (checkContainerModel.isPresent()) {
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checkContainerSpec);
            defaultDataObservabilityChecks.setRecurringMonthly(checkContainerSpec);
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }
}
