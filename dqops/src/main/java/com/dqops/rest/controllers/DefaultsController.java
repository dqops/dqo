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
@Api(value = "Defaults", description = "Default settings management for configuring the default data quality checks that are configured for all imported tables and columns.")
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
     * Returns the UI model for the default configuration of default profiling checks on a table level.
     * @return Check UI model with the configuration of the profiling checks that are applied to new tables.
     */
    @GetMapping(value = "/defaultchecks/profiling/table", produces = "application/json")
    @ApiOperation(value = "getDefaultProfilingTableChecks", notes = "Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported tables on a table level.",
            response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultProfilingTableChecks() {
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

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(defaultChecksContainerSpec.getTable(),
                null, null, null, executionContext, null);

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default profiling checks on a column level.
     * @return Check UI model with the configuration of the profiling checks that are applied to new tables.
     */
    @GetMapping(value = "/defaultchecks/profiling/column", produces = "application/json")
    @ApiOperation(value = "getDefaultProfilingColumnChecks", notes = "Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported column on a column level.",
            response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultProfilingColumnChecks() {
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

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(defaultChecksContainerSpec.getColumn(),
                null, null, null, executionContext, null);

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default daily recurring checks on a table level.
     * @return Check UI model with the configuration of the daily recurring checks that are applied to new tables.
     */
    @GetMapping(value = "/defaultchecks/dataobservability/recurring/daily/table", produces = "application/json")
    @ApiOperation(value = "getDefaultDataObservabilityDailyRecurringTableChecks", notes = "Returns UI model to show and edit the default configuration of the daily recurring (Data Observability and monitoring) checks that are configured for all imported tables on a table level.",
            response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultDataObservabilityDailyRecurringTableChecks() {
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

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(defaultChecksContainerSpec.getTable(),
                null, null, null, executionContext, null);

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default daily recurring checks on a column level.
     * @return Check UI model with the configuration of the daily recurring checks that are applied to new tables.
     */
    @GetMapping(value = "/defaultchecks/dataobservability/recurring/daily/column", produces = "application/json")
    @ApiOperation(value = "getDefaultDataObservabilityDailyRecurringColumnChecks", notes = "Returns UI model to show and edit the default configuration of the daily recurring (Data Observability and monitoring) checks that are configured for all imported columns on a column level.",
            response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultDataObservabilityDailyRecurringColumnChecks() {
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

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(defaultChecksContainerSpec.getColumn(),
                null, null, null, executionContext, null);

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default monthly recurring checks on a table level.
     * @return Check UI model with the configuration of the monthly recurring checks that are applied to new tables.
     */
    @GetMapping(value = "/defaultchecks/dataobservability/recurring/monthly/table", produces = "application/json")
    @ApiOperation(value = "getDefaultDataObservabilityMonthlyRecurringTableChecks", notes = "Returns UI model to show and edit the default configuration of the monthly recurring (Data Observability end of month scores) checks that are configured for all imported tables on a table level.",
            response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultDataObservabilityMonthlyRecurringTableChecks() {
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

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(defaultChecksContainerSpec.getTable(),
                null, null, null, executionContext, null);

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default monthly recurring checks on a column level.
     * @return Check UI model with the configuration of the monthly recurring checks that are applied to new tables.
     */
    @GetMapping(value = "/defaultchecks/dataobservability/recurring/monthly/column", produces = "application/json")
    @ApiOperation(value = "getDefaultDataObservabilityMonthlyRecurringColumnChecks", notes = "Returns UI model to show and edit the default configuration of the monthly recurring (Data Observability end of month scores) checks that are configured for all imported columns on a column level.",
            response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultDataObservabilityMonthlyRecurringColumnChecks() {
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

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(defaultChecksContainerSpec.getColumn(),
                null, null, null, executionContext, null);

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Updates the configuration of default profiling checks on a table level.
     * @param checkContainerModel New configuration of the default profiling checks.
     * @return Empty response.
     */
    @PutMapping(value = "/defaultchecks/profiling/table", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultProfilingTableChecks", notes = "New configuration of the default profiling checks on a table level. These checks will be applied to new tables.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of profiling checks successfully updated."),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDefaultProfilingTableChecks(
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
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checkContainerSpec.getTable());
            defaultDataObservabilityChecks.setProfiling(checkContainerSpec);
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of default profiling checks on a column level.
     * @param checkContainerModel New configuration of the default profiling checks.
     * @return Empty response.
     */
    @PutMapping(value = "/defaultchecks/profiling/column", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultProfilingColumnChecks", notes = "New configuration of the default profiling checks on a column level. These checks will be applied to new columns.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of profiling checks successfully updated."),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDefaultProfilingColumnChecks(
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
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checkContainerSpec.getColumn());
            defaultDataObservabilityChecks.setProfiling(checkContainerSpec);
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of default daily recurring (data observability) checks on a table level.
     * @param checkContainerModel New configuration of the default daily recurring checks.
     * @return Empty response.
     */
    @PutMapping(value = "/defaultchecks/dataobservability/recurring/daily/table", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultDataObservabilityDailyRecurringTableChecks", notes = "New configuration of the default daily recurring (data observability) checks on a table level. These checks will be applied on new tables.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of daily recurring (daily data observability) checks successfully updated."),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDefaultDataObservabilityDailyRecurringTableChecks(
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
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checkContainerSpec.getTable());
            defaultDataObservabilityChecks.setRecurringDaily(checkContainerSpec);
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of default daily recurring (data observability) checks on a column level.
     * @param checkContainerModel New configuration of the default daily recurring checks.
     * @return Empty response.
     */
    @PutMapping(value = "/defaultchecks/dataobservability/recurring/daily/column", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultDataObservabilityDailyRecurringColumnChecks", notes = "New configuration of the default daily recurring (data observability) checks on a column level. These checks will be applied on new columns.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of daily recurring (daily data observability) checks successfully updated."),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDefaultDataObservabilityDailyRecurringColumnChecks(
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
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checkContainerSpec.getColumn());
            defaultDataObservabilityChecks.setRecurringDaily(checkContainerSpec);
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of default monthly recurring (data observability) checks on a table level.
     * @param checkContainerModel New configuration of the default daily recurring checks.
     * @return Empty response.
     */
    @PutMapping(value = "/defaultchecks/dataobservability/recurring/monthly/table", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultDataObservabilityMonthlyRecurringTableChecks", notes = "New configuration of the default monthly recurring checkpoints on a table level. These checks will be applied on new tables.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of daily recurring (daily data observability) checks successfully updated."),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDefaultDataObservabilityMonthlyRecurringTableChecks(
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
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checkContainerSpec.getTable());
            defaultDataObservabilityChecks.setRecurringMonthly(checkContainerSpec);
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of default monthly recurring (data observability) checks on a column level.
     * @param checkContainerModel New configuration of the default daily recurring checks.
     * @return Empty response.
     */
    @PutMapping(value = "/defaultchecks/dataobservability/recurring/monthly/column", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultDataObservabilityMonthlyRecurringColumnChecks", notes = "New configuration of the default monthly recurring checkpoints on a column level. These checks will be applied on new columns.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of daily recurring (daily data observability) checks successfully updated."),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDefaultDataObservabilityMonthlyRecurringColumnChecks(
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
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checkContainerSpec.getColumn());
            defaultDataObservabilityChecks.setRecurringMonthly(checkContainerSpec);
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }
}
