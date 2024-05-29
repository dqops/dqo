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

import autovalue.shaded.com.google.common.base.Strings;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternList;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.DefaultColumnChecksPatternListModel;
import com.dqops.rest.models.metadata.DefaultColumnChecksPatternModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingService;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.locking.RestApiLockService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST api controller to manage the list of default column-level check patterns.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "DefaultColumnCheckPatterns", description = "Operations for managing the configuration of the default column-level checks for columns matching a pattern.")
public class DefaultColumnCheckPatternsController {
    private final UserHomeContextFactory userHomeContextFactory;
    private final ExecutionContextFactory executionContextFactory;
    private final SpecToModelCheckMappingService specToModelCheckMappingService;
    private final ModelToSpecCheckMappingService modelToSpecCheckMappingService;
    private final RestApiLockService lockService;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param userHomeContextFactory User home context factory.
     * @param executionContextFactory Execution context factory.
     * @param specToModelCheckMappingService Check specification to UI conversion service.
     * @param modelToSpecCheckMappingService Check model to UI conversion service.
     * @param lockService Object lock service.
     */
    @Autowired
    public DefaultColumnCheckPatternsController(UserHomeContextFactory userHomeContextFactory,
                                                ExecutionContextFactory executionContextFactory,
                                                SpecToModelCheckMappingService specToModelCheckMappingService,
                                                ModelToSpecCheckMappingService modelToSpecCheckMappingService,
                                                RestApiLockService lockService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.executionContextFactory = executionContextFactory;
        this.specToModelCheckMappingService = specToModelCheckMappingService;
        this.modelToSpecCheckMappingService = modelToSpecCheckMappingService;
        this.lockService = lockService;
    }

    /**
     * Returns a flat list of all default check templates.
     * @return List of all default check templates.
     */
    @GetMapping(value = "/default/checks/column", produces = "application/json")
    @ApiOperation(value = "getAllDefaultColumnChecksPatterns",
            notes = "Returns a flat list of all column-level default check patterns configured for this instance. Default checks are applied on columns dynamically.",
            response = DefaultColumnChecksPatternListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DefaultColumnChecksPatternListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<DefaultColumnChecksPatternListModel>> getAllDefaultColumnChecksPatterns(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();
        ColumnDefaultChecksPatternList defaultChecksPatternsList = userHome.getColumnDefaultChecksPatterns();
        List<ColumnDefaultChecksPatternWrapper> patternWrappersList = defaultChecksPatternsList.toList();
        boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

        List<DefaultColumnChecksPatternListModel> models = patternWrappersList.stream()
                .map(pw -> DefaultColumnChecksPatternListModel.fromPatternSpecification(pw.getSpec(), canEdit))
                .collect(Collectors.toList());
        models.sort(Comparator.comparing(model -> model.getPatternName()));

        return new ResponseEntity<>(Flux.fromStream(models.stream()), HttpStatus.OK);
    }

    /**
     * Returns the configuration of a default checks pattern.
     * @param patternName Pattern name.
     * @return Model of the default checks pattern.
     */
    @GetMapping(value = "/default/checks/column/{patternName}/target", produces = "application/json")
    @ApiOperation(value = "getDefaultColumnChecksPatternTarget", notes = "Returns a default checks pattern definition", response = DefaultColumnChecksPatternListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DefaultColumnChecksPatternListModel.class),
            @ApiResponse(code = 404, message = "Pattern name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<DefaultColumnChecksPatternListModel>> getDefaultColumnChecksPatternTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Column pattern name") @PathVariable String patternName) {

        if (Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();
        ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper =
                userHome.getColumnDefaultChecksPatterns().getByObjectName(patternName, true);

        if (defaultChecksPatternWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
        DefaultColumnChecksPatternListModel patternModel =
                DefaultColumnChecksPatternListModel.fromPatternSpecification(defaultChecksPatternWrapper.getSpec(), canEdit);

        return new ResponseEntity<>(Mono.just(patternModel), HttpStatus.OK);
    }

    /**
     * Returns the configuration of a default checks pattern as a full specification.
     * @param patternName Pattern name.
     * @return Model of the default checks pattern.
     */
    @GetMapping(value = "/default/checks/column/{patternName}", produces = "application/json")
    @ApiOperation(value = "getDefaultColumnChecksPattern", notes = "Returns a default checks pattern definition as a full specification object", response = DefaultColumnChecksPatternModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DefaultColumnChecksPatternModel.class),
            @ApiResponse(code = 404, message = "Pattern name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<DefaultColumnChecksPatternModel>> getDefaultColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Column pattern name") @PathVariable String patternName) {

        if (Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();
        ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper =
                userHome.getColumnDefaultChecksPatterns().getByObjectName(patternName, true);

        if (defaultChecksPatternWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
        DefaultColumnChecksPatternModel patternModel = new DefaultColumnChecksPatternModel() {{
            setPatternName(patternName);
            setPatternSpec(defaultChecksPatternWrapper.getSpec());
            setCanEdit(canEdit);
            setYamlParsingError(defaultChecksPatternWrapper.getSpec().getYamlParsingError());
        }};

        return new ResponseEntity<>(Mono.just(patternModel), HttpStatus.OK);
    }

    /**
     * Creates (adds) a new default column-level checks pattern configuration.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PostMapping(value = "/default/checks/column/{patternName}/target", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createDefaultColumnChecksPatternTarget", notes = "Creates (adds) a new default column-level checks pattern configuration.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New checks pattern successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Check pattern with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> createDefaultColumnChecksPatternTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Default checks pattern model with only the target filters") @RequestBody DefaultColumnChecksPatternListModel patternModel) {
        if (patternModel == null || Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                    UserHome userHome = userHomeContext.getUserHome();

                    ColumnDefaultChecksPatternList defaultChecksPatternsList = userHome.getColumnDefaultChecksPatterns();
                    ColumnDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                    if (existingDefaultChecksPatternWrapper != null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
                    }

                    ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                    ColumnDefaultChecksPatternSpec patternSpec = new ColumnDefaultChecksPatternSpec();
                    patternSpec.setTarget(patternModel.getTargetColumn());
                    patternSpec.setPriority(patternModel.getPriority());
                    patternSpec.setDescription(patternModel.getDescription());
                    patternSpec.setDisabled(patternModel.isDisabled());
                    defaultChecksPatternWrapper.setSpec(patternSpec);
                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
                });
    }

    /**
     * Creates (adds) a new default column-level checks pattern configuration as a full specification object.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PostMapping(value = "/default/checks/column/{patternName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createDefaultColumnChecksPattern", notes = "Creates (adds) a new default column-level checks pattern configuration by saving a full specification object.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New checks pattern successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Check pattern with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> createDefaultColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Default checks pattern model") @RequestBody DefaultColumnChecksPatternModel patternModel) {
        if (patternModel == null || Strings.isNullOrEmpty(patternName) || patternModel.getPatternSpec() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                    UserHome userHome = userHomeContext.getUserHome();

                    ColumnDefaultChecksPatternList defaultChecksPatternsList = userHome.getColumnDefaultChecksPatterns();
                    ColumnDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                    if (existingDefaultChecksPatternWrapper != null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
                    }

                    ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                    defaultChecksPatternWrapper.setSpec(patternModel.getPatternSpec());
                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
                });
    }

    /**
     * Updates an existing default column-level checks pattern, creating possibly a new checks pattern configuration.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/column/{patternName}/target", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultColumnChecksPatternTarget", notes = "Updates an default column-level checks pattern, changing only the target object", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Default column-level checks pattern successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateDefaultColumnChecksPatternTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Default checks pattern model") @RequestBody DefaultColumnChecksPatternListModel patternModel,
            @ApiParam("Pattern name") @PathVariable String patternName) {

        if (Strings.isNullOrEmpty(patternName) || patternModel == null || patternModel.getTargetColumn() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                    UserHome userHome = userHomeContext.getUserHome();

                    ColumnDefaultChecksPatternList defaultChecksPatternsList = userHome.getColumnDefaultChecksPatterns();
                    ColumnDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);
                    ColumnDefaultChecksPatternSpec targetPatternSpec;

                    if (existingDefaultChecksPatternWrapper == null) {
                        ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                        targetPatternSpec = new ColumnDefaultChecksPatternSpec() {{
                            setTarget(patternModel.getTargetColumn());
                            setPriority(patternModel.getPriority());
                        }};
                        defaultChecksPatternWrapper.setSpec(targetPatternSpec);
                    } else {
                        targetPatternSpec = existingDefaultChecksPatternWrapper.getSpec(); // just to load
                        targetPatternSpec.setTarget(patternModel.getTargetColumn());
                        targetPatternSpec.setPriority(patternModel.getPriority());
                    }
                    targetPatternSpec.setDisabled(patternModel.isDisabled());
                    targetPatternSpec.setDescription(patternModel.getDescription());
                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
                });
    }

    /**
     * Updates an existing default column-level checks pattern, creating possibly a new checks pattern configuration, by passing a full specification.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/column/{patternName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultColumnChecksPattern", notes = "Updates an default column-level checks pattern by saving a full specification object", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Default column-level checks pattern successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateDefaultColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Default checks pattern model") @RequestBody DefaultColumnChecksPatternModel patternModel,
            @ApiParam("Pattern name") @PathVariable String patternName) {

        if (Strings.isNullOrEmpty(patternName) || patternModel == null || patternModel.getPatternSpec() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                    UserHome userHome = userHomeContext.getUserHome();

                    ColumnDefaultChecksPatternList defaultChecksPatternsList = userHome.getColumnDefaultChecksPatterns();
                    ColumnDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                    if (existingDefaultChecksPatternWrapper == null) {
                        ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                        defaultChecksPatternWrapper.setSpec(patternModel.getPatternSpec());
                    } else {
                        ColumnDefaultChecksPatternSpec oldPatternSpec = existingDefaultChecksPatternWrapper.getSpec(); // just to load
                        existingDefaultChecksPatternWrapper.setSpec(patternModel.getPatternSpec());
                    }
                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
                });
    }

    /**
     * Deletes a default checks pattern
     * @param patternName  Pattern name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/default/checks/column/{patternName}", produces = "application/json")
    @ApiOperation(value = "deleteDefaultColumnChecksPattern", notes = "Deletes a default column-level checks pattern", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Default column-level checks pattern successfully deleted", response = Void.class),
            @ApiResponse(code = 404, message = "Default checks pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> deleteDefaultColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {

        if (Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                    UserHome userHome = userHomeContext.getUserHome();

                    ColumnDefaultChecksPatternList defaultChecksPatternsList = userHome.getColumnDefaultChecksPatterns();
                    ColumnDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                    if (existingDefaultChecksPatternWrapper == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                    }

                    existingDefaultChecksPatternWrapper.markForDeletion();
                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                });
    }

    /**
     * Returns the UI model for the default configuration of default profiling checks on a column level.
     * @return Check UI model with the configuration of the profiling checks that are applied to columns.
     */
    @GetMapping(value = "/default/checks/column/{patternName}/profiling", produces = "application/json")
    @ApiOperation(value = "getDefaultProfilingColumnChecksPattern",
            notes = "Returns UI model to show and edit the default configuration of the profiling checks that are configured for a check pattern on a column level.",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Default checks pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultProfilingColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        UserHome userHome = userHomeContext.getUserHome();
        ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getColumnDefaultChecksPatterns()
                .getByObjectName(patternName, true);

        if (defaultChecksPatternWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
        AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.profiling, null, false);

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                null, null, null, executionContext, null,
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default daily monitoring checks on a column level.
     * @return Check UI model with the configuration of the daily monitoring checks that are applied to columns.
     */
    @GetMapping(value = "/default/checks/column/{patternName}/monitoring/daily", produces = "application/json")
    @ApiOperation(value = "getDefaultMonitoringDailyColumnChecksPattern",
            notes = "Returns UI model to show and edit the default configuration of the daily monitoring checks that are configured for a check pattern on a column level.",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Default checks pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultMonitoringDailyColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        UserHome userHome = userHomeContext.getUserHome();
        ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getColumnDefaultChecksPatterns()
                .getByObjectName(patternName, true);

        if (defaultChecksPatternWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
        AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, false);

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                null, null, null, executionContext, null,
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default monthly monitoring checks on a column level.
     * @return Check UI model with the configuration of the monthly monitoring checks that are applied to columns.
     */
    @GetMapping(value = "/default/checks/column/{patternName}/monitoring/monthly", produces = "application/json")
    @ApiOperation(value = "getDefaultMonitoringMonthlyColumnChecksPattern",
            notes = "Returns UI model to show and edit the default configuration of the monthly monitoring checks that are configured for a check pattern on a column level.",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Default checks pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultMonitoringMonthlyColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        UserHome userHome = userHomeContext.getUserHome();
        ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getColumnDefaultChecksPatterns()
                .getByObjectName(patternName, true);

        if (defaultChecksPatternWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
        AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.monthly, false);

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                null, null, null, executionContext, null,
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default daily partitioned checks on a column level.
     * @return Check UI model with the configuration of the daily partitioned checks that are applied to columns.
     */
    @GetMapping(value = "/default/checks/column/{patternName}/partitioned/daily", produces = "application/json")
    @ApiOperation(value = "getDefaultPartitionedDailyColumnChecksPattern",
            notes = "Returns UI model to show and edit the default configuration of the daily partitioned checks that are configured for a check pattern on a column level.",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Default checks pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultPartitionedDailyColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        UserHome userHome = userHomeContext.getUserHome();
        ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getColumnDefaultChecksPatterns()
                .getByObjectName(patternName, true);

        if (defaultChecksPatternWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
        AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.partitioned, CheckTimeScale.daily, false);

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                null, null, null, executionContext, null,
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Returns the UI model for the default configuration of default monthly partitioned checks on a column level.
     * @return Check UI model with the configuration of the monthly partitioned checks that are applied to columns.
     */
    @GetMapping(value = "/default/checks/column/{patternName}/partitioned/monthly", produces = "application/json")
    @ApiOperation(value = "getDefaultPartitionedMonthlyColumnChecksPattern",
            notes = "Returns UI model to show and edit the default configuration of the monthly partitioned checks that are configured for a check pattern on a column level.",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Default checks pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getDefaultPartitionedMonthlyColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();

        UserHome userHome = userHomeContext.getUserHome();
        ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getColumnDefaultChecksPatterns()
                .getByObjectName(patternName, true);

        if (defaultChecksPatternWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
        AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.partitioned, CheckTimeScale.monthly, false);

        CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                null, null, null, executionContext, null,
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

        return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
    }

    /**
     * Updates the configuration of default profiling checks pattern on a column level.
     * @param checkContainerModel New configuration of the default profiling checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/column/{patternName}/profiling", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultProfilingColumnChecksPattern",
            notes = "New configuration of the default profiling checks on a column level. These checks will be applied to columns.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of profiling checks successfully updated.", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Default check pattern configuration not found", response = Void.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateDefaultProfilingColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality profiling checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                    ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                    UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                    UserHome userHome = userHomeContext.getUserHome();

                    ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getColumnDefaultChecksPatterns()
                            .getByObjectName(patternName, true);

                    if (defaultChecksPatternWrapper == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                    }

                    ColumnDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                    AbstractRootChecksContainerSpec columnCheckRootContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.profiling, null, true);
                    this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, columnCheckRootContainer, null);
                    defaultChecksPatternSpec.setColumnCheckRootContainer(columnCheckRootContainer);

                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                });
    }

    /**
     * Updates the configuration of default daily monitoring checks pattern on a column level.
     * @param checkContainerModel New configuration of the default daily monitoring checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/column/{patternName}/monitoring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultMonitoringDailyColumnChecksPattern",
            notes = "New configuration of the default daily monitoring checks on a column level. These checks will be applied to columns.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of daily monitoring checks successfully updated.", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Default check pattern configuration not found", response = Void.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateDefaultMonitoringDailyColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality daily monitoring checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                    ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                    UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                    UserHome userHome = userHomeContext.getUserHome();

                    ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getColumnDefaultChecksPatterns()
                            .getByObjectName(patternName, true);

                    if (defaultChecksPatternWrapper == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                    }

                    ColumnDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                    AbstractRootChecksContainerSpec columnCheckRootContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
                    this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, columnCheckRootContainer, null);
                    defaultChecksPatternSpec.setColumnCheckRootContainer(columnCheckRootContainer);

                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                });
    }

    /**
     * Updates the configuration of default monthly monitoring checks pattern on a column level.
     * @param checkContainerModel New configuration of the default monthly monitoring checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/column/{patternName}/monitoring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultMonitoringMonthlyColumnChecksPattern",
            notes = "New configuration of the default monthly monitoring checks on a column level. These checks will be applied to columns.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of monthly monitoring checks successfully updated.", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Default check pattern configuration not found", response = Void.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateDefaultMonitoringMonthlyColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality monthly monitoring checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                    ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                    UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                    UserHome userHome = userHomeContext.getUserHome();

                    ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getColumnDefaultChecksPatterns()
                            .getByObjectName(patternName, true);

                    if (defaultChecksPatternWrapper == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                    }

                    ColumnDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                    AbstractRootChecksContainerSpec columnCheckRootContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.monthly, true);
                    this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, columnCheckRootContainer, null);
                    defaultChecksPatternSpec.setColumnCheckRootContainer(columnCheckRootContainer);

                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                });
    }

    /**
     * Updates the configuration of default daily partitioned checks pattern on a column level.
     * @param checkContainerModel New configuration of the default daily partitioned checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/column/{patternName}/partitioned/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultPartitionedDailyColumnChecksPattern",
            notes = "New configuration of the default daily partitioned checks on a column level. These checks will be applied to columns.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of daily partitioned checks successfully updated.", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Default check pattern configuration not found", response = Void.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateDefaultPartitionedDailyColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality daily partitioned checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                    ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                    UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                    UserHome userHome = userHomeContext.getUserHome();

                    ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getColumnDefaultChecksPatterns()
                            .getByObjectName(patternName, true);

                    if (defaultChecksPatternWrapper == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                    }

                    ColumnDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                    AbstractRootChecksContainerSpec columnCheckRootContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.partitioned, CheckTimeScale.daily, true);
                    this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, columnCheckRootContainer, null);
                    defaultChecksPatternSpec.setColumnCheckRootContainer(columnCheckRootContainer);

                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                });
    }

    /**
     * Updates the configuration of default monthly partitioned checks pattern on a column level.
     * @param checkContainerModel New configuration of the default monthly partitioned checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/column/{patternName}/partitioned/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultPartitionedMonthlyColumnChecksPattern",
            notes = "New configuration of the default monthly partitioned checks on a column level. These checks will be applied to columns.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of monthly partitioned checks successfully updated.", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Default check pattern configuration not found", response = Void.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateDefaultPartitionedMonthlyColumnChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality monthly partitioned checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                    ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                    UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                    UserHome userHome = userHomeContext.getUserHome();

                    ColumnDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getColumnDefaultChecksPatterns()
                            .getByObjectName(patternName, true);

                    if (defaultChecksPatternWrapper == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                    }

                    ColumnDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                    AbstractRootChecksContainerSpec columnCheckRootContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.partitioned, CheckTimeScale.monthly, true);
                    this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, columnCheckRootContainer, null);
                    defaultChecksPatternSpec.setColumnCheckRootContainer(columnCheckRootContainer);

                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                });
    }
}
