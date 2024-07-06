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
import com.dqops.connectors.ProviderType;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternList;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.DefaultTableChecksPatternListModel;
import com.dqops.rest.models.metadata.DefaultTableChecksPatternModel;
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
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * REST api controller to manage the list of default table-level check patterns.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "DefaultTableCheckPatterns", description = "Operations for managing the configuration of the default table-level checks for tables matching a pattern.")
public class DefaultTableCheckPatternsController {
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
    public DefaultTableCheckPatternsController(UserHomeContextFactory userHomeContextFactory,
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
    @GetMapping(value = "/default/checks/table", produces = "application/json")
    @ApiOperation(value = "getAllDefaultTableChecksPatterns",
            notes = "Returns a flat list of all table-level default check patterns configured for this instance. Default checks are applied on tables dynamically.",
            response = DefaultTableChecksPatternListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DefaultTableChecksPatternListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<DefaultTableChecksPatternListModel>>> getAllDefaultTableChecksPatterns(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            TableDefaultChecksPatternList defaultChecksPatternsList = userHome.getTableDefaultChecksPatterns();
            List<TableDefaultChecksPatternWrapper> patternWrappersList = defaultChecksPatternsList.toList();
            boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

            List<DefaultTableChecksPatternListModel> models = patternWrappersList.stream()
                    .map(pw -> DefaultTableChecksPatternListModel.fromPatternSpecification(pw.getSpec(), canEdit))
                    .collect(Collectors.toList());
            models.sort(Comparator.comparing(model -> model.getPatternName()));

            return new ResponseEntity<>(Flux.fromStream(models.stream()), HttpStatus.OK);
        }));
    }

    /**
     * Returns the configuration of a default checks pattern.
     * @param patternName Pattern name.
     * @return Model of the default checks pattern.
     */
    @GetMapping(value = "/default/checks/table/{patternName}/target", produces = "application/json")
    @ApiOperation(value = "getDefaultTableChecksPatternTarget", notes = "Returns a default checks pattern definition", response = DefaultTableChecksPatternListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DefaultTableChecksPatternListModel.class),
            @ApiResponse(code = 404, message = "Pattern name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<DefaultTableChecksPatternListModel>>> getDefaultTableChecksPatternTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Table pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            TableDefaultChecksPatternWrapper defaultChecksPatternWrapper =
                    userHome.getTableDefaultChecksPatterns().getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            DefaultTableChecksPatternListModel patternModel =
                    DefaultTableChecksPatternListModel.fromPatternSpecification(defaultChecksPatternWrapper.getSpec(), canEdit);

            return new ResponseEntity<>(Mono.just(patternModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the configuration of a default checks pattern as a full specification.
     * @param patternName Pattern name.
     * @return Model of the default checks pattern.
     */
    @GetMapping(value = "/default/checks/table/{patternName}", produces = "application/json")
    @ApiOperation(value = "getDefaultTableChecksPattern", notes = "Returns a default checks pattern definition as a full specification object", response = DefaultTableChecksPatternModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DefaultTableChecksPatternModel.class),
            @ApiResponse(code = 404, message = "Pattern name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<DefaultTableChecksPatternModel>>> getDefaultTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Table pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            TableDefaultChecksPatternWrapper defaultChecksPatternWrapper =
                    userHome.getTableDefaultChecksPatterns().getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            DefaultTableChecksPatternModel patternModel = new DefaultTableChecksPatternModel() {{
                setPatternName(patternName);
                setPatternSpec(defaultChecksPatternWrapper.getSpec());
                setCanEdit(canEdit);
                setYamlParsingError(defaultChecksPatternWrapper.getSpec().getYamlParsingError());
            }};

            return new ResponseEntity<>(Mono.just(patternModel), HttpStatus.OK);
        }));
    }

    /**
     * Creates (adds) a new default table-level checks pattern configuration.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PostMapping(value = "/default/checks/table/{patternName}/target", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createDefaultTableChecksPatternTarget", notes = "Creates (adds) a new default table-level checks pattern configuration.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> createDefaultTableChecksPatternTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Default checks pattern model with only the target filters") @RequestBody DefaultTableChecksPatternListModel patternModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
        if (patternModel == null || Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                    UserHome userHome = userHomeContext.getUserHome();

                    TableDefaultChecksPatternList defaultChecksPatternsList = userHome.getTableDefaultChecksPatterns();
                    TableDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                    if (existingDefaultChecksPatternWrapper != null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
                    }

                    TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                    TableDefaultChecksPatternSpec patternSpec = new TableDefaultChecksPatternSpec();
                    patternSpec.setTarget(patternModel.getTargetTable());
                    patternSpec.setPriority(patternModel.getPriority());
                    patternSpec.setDescription(patternModel.getDescription());
                    patternSpec.setDisabled(patternModel.isDisabled());

                    defaultChecksPatternWrapper.setSpec(patternSpec);
                    userHomeContext.flush();

                    return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
                });
        }));
    }

    /**
     * Creates (adds) a new default table-level checks pattern configuration as a full specification object.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PostMapping(value = "/default/checks/table/{patternName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createDefaultTableChecksPattern", notes = "Creates (adds) a new default table-level checks pattern configuration by saving a full specification object.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> createDefaultTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Default checks pattern model") @RequestBody DefaultTableChecksPatternModel patternModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (patternModel == null || Strings.isNullOrEmpty(patternName) || patternModel.getPatternSpec() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        TableDefaultChecksPatternList defaultChecksPatternsList = userHome.getTableDefaultChecksPatterns();
                        TableDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                        if (existingDefaultChecksPatternWrapper != null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
                        }

                        TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                        defaultChecksPatternWrapper.setSpec(patternModel.getPatternSpec());
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
                    });
        }));
    }

    /**
     * Updates an existing default table-level checks pattern, creating possibly a new checks pattern configuration.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/table/{patternName}/target", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultTableChecksPatternTarget", notes = "Updates an default table-level checks pattern, changing only the target object", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Default table-level checks pattern successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateDefaultTableChecksPatternTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Default checks pattern model") @RequestBody DefaultTableChecksPatternListModel patternModel,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(patternName) || patternModel == null || patternModel.getTargetTable() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        TableDefaultChecksPatternList defaultChecksPatternsList = userHome.getTableDefaultChecksPatterns();
                        TableDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);
                        TableDefaultChecksPatternSpec targetPatternSpec;

                        if (existingDefaultChecksPatternWrapper == null) {
                            TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                            targetPatternSpec = new TableDefaultChecksPatternSpec() {{
                                setTarget(patternModel.getTargetTable());
                                setPriority(patternModel.getPriority());
                            }};
                            defaultChecksPatternWrapper.setSpec(targetPatternSpec);
                        } else {
                            targetPatternSpec = existingDefaultChecksPatternWrapper.getSpec(); // just to load
                            targetPatternSpec.setTarget(patternModel.getTargetTable());
                            targetPatternSpec.setPriority(patternModel.getPriority());
                        }
                        targetPatternSpec.setDisabled(patternModel.isDisabled());
                        targetPatternSpec.setDescription(patternModel.getDescription());
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
                    });
        }));
    }

    /**
     * Updates an existing default table-level checks pattern, creating possibly a new checks pattern configuration, by passing a full specification.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/table/{patternName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultTableChecksPattern", notes = "Updates an default table-level checks pattern by saving a full specification object", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Default table-level checks pattern successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateDefaultTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Default checks pattern model") @RequestBody DefaultTableChecksPatternModel patternModel,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(patternName) || patternModel == null || patternModel.getPatternSpec() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        TableDefaultChecksPatternList defaultChecksPatternsList = userHome.getTableDefaultChecksPatterns();
                        TableDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                        if (existingDefaultChecksPatternWrapper == null) {
                            TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                            defaultChecksPatternWrapper.setSpec(patternModel.getPatternSpec());
                        } else {
                            TableDefaultChecksPatternSpec oldPatternSpec = existingDefaultChecksPatternWrapper.getSpec(); // just to load
                            existingDefaultChecksPatternWrapper.setSpec(patternModel.getPatternSpec());
                        }
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
                    });
        }));
    }

    /**
     * Deletes a default checks pattern
     * @param patternName  Pattern name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/default/checks/table/{patternName}", produces = "application/json")
    @ApiOperation(value = "deleteDefaultTableChecksPattern", notes = "Deletes a default table-level checks pattern", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Default table-level checks pattern successfully deleted", response = Void.class),
            @ApiResponse(code = 404, message = "Default checks pattern not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> deleteDefaultTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        TableDefaultChecksPatternList defaultChecksPatternsList = userHome.getTableDefaultChecksPatterns();
                        TableDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                        if (existingDefaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        existingDefaultChecksPatternWrapper.markForDeletion();
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Returns the UI model for the default configuration of default profiling checks on a table level.
     * @return Check UI model with the configuration of the profiling checks that are applied to tables.
     */
    @GetMapping(value = "/default/checks/table/{patternName}/profiling", produces = "application/json")
    @ApiOperation(value = "getDefaultProfilingTableChecksPattern",
            notes = "Returns UI model to show and edit the default configuration of the profiling checks that are configured for a check pattern on a table level.",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getDefaultProfilingTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getTableDefaultChecksPatterns()
                    .getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
            AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getTableCheckRootContainer(CheckType.profiling, null, false);

            CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                    null, null, null, executionContext, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the UI model for the default configuration of default daily monitoring checks on a table level.
     * @return Check UI model with the configuration of the daily monitoring checks that are applied to tables.
     */
    @GetMapping(value = "/default/checks/table/{patternName}/monitoring/daily", produces = "application/json")
    @ApiOperation(value = "getDefaultMonitoringDailyTableChecksPattern",
            notes = "Returns UI model to show and edit the default configuration of the daily monitoring checks that are configured for a check pattern on a table level.",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getDefaultMonitoringDailyTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getTableDefaultChecksPatterns()
                    .getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
            AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getTableCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, false);

            CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                    null, null, null, executionContext, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the UI model for the default configuration of default monthly monitoring checks on a table level.
     * @return Check UI model with the configuration of the monthly monitoring checks that are applied to tables.
     */
    @GetMapping(value = "/default/checks/table/{patternName}/monitoring/monthly", produces = "application/json")
    @ApiOperation(value = "getDefaultMonitoringMonthlyTableChecksPattern",
            notes = "Returns UI model to show and edit the default configuration of the monthly monitoring checks that are configured for a check pattern on a table level.",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getDefaultMonitoringMonthlyTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getTableDefaultChecksPatterns()
                    .getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
            AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getTableCheckRootContainer(CheckType.monitoring, CheckTimeScale.monthly, false);

            CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                    null, null, null, executionContext, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the UI model for the default configuration of default daily partitioned checks on a table level.
     * @return Check UI model with the configuration of the daily partitioned checks that are applied to tables.
     */
    @GetMapping(value = "/default/checks/table/{patternName}/partitioned/daily", produces = "application/json")
    @ApiOperation(value = "getDefaultPartitionedDailyTableChecksPattern",
            notes = "Returns UI model to show and edit the default configuration of the daily partitioned checks that are configured for a check pattern on a table level.",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getDefaultPartitionedDailyTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getTableDefaultChecksPatterns()
                    .getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
            AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getTableCheckRootContainer(CheckType.partitioned, CheckTimeScale.daily, false);

            CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                    null, null, null, executionContext, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the UI model for the default configuration of default monthly partitioned checks on a table level.
     * @return Check UI model with the configuration of the monthly partitioned checks that are applied to tables.
     */
    @GetMapping(value = "/default/checks/table/{patternName}/partitioned/monthly", produces = "application/json")
    @ApiOperation(value = "getDefaultPartitionedMonthlyTableChecksPattern",
            notes = "Returns UI model to show and edit the default configuration of the monthly partitioned checks that are configured for a check pattern on a table level.",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getDefaultPartitionedMonthlyTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getTableDefaultChecksPatterns()
                    .getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
            AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getTableCheckRootContainer(CheckType.partitioned, CheckTimeScale.monthly, false);

            CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                    null, null, null, executionContext, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Updates the configuration of default profiling checks pattern on a table level.
     * @param checkContainerModel New configuration of the default profiling checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/table/{patternName}/profiling", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultProfilingTableChecksPattern",
            notes = "New configuration of the default profiling checks on a table level. These checks will be applied to tables.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> updateDefaultProfilingTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality profiling checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                        UserHome userHome = userHomeContext.getUserHome();

                        TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getTableDefaultChecksPatterns()
                                .getByObjectName(patternName, true);

                        if (defaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        TableDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                        AbstractRootChecksContainerSpec tableCheckRootContainer = defaultChecksPatternSpec.getTableCheckRootContainer(CheckType.profiling, null, true);
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, tableCheckRootContainer, null);
                        defaultChecksPatternSpec.setTableCheckRootContainer(tableCheckRootContainer);

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of default daily monitoring checks pattern on a table level.
     * @param checkContainerModel New configuration of the default daily monitoring checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/table/{patternName}/monitoring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultMonitoringDailyTableChecksPattern",
            notes = "New configuration of the default daily monitoring checks on a table level. These checks will be applied to tables.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> updateDefaultMonitoringDailyTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality daily monitoring checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                        UserHome userHome = userHomeContext.getUserHome();

                        TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getTableDefaultChecksPatterns()
                                .getByObjectName(patternName, true);

                        if (defaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        TableDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                        AbstractRootChecksContainerSpec tableCheckRootContainer = defaultChecksPatternSpec.getTableCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, tableCheckRootContainer, null);
                        defaultChecksPatternSpec.setTableCheckRootContainer(tableCheckRootContainer);

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of default monthly monitoring checks pattern on a table level.
     * @param checkContainerModel New configuration of the default monthly monitoring checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/table/{patternName}/monitoring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultMonitoringMonthlyTableChecksPattern",
            notes = "New configuration of the default monthly monitoring checks on a table level. These checks will be applied to tables.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> updateDefaultMonitoringMonthlyTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality monthly monitoring checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                        UserHome userHome = userHomeContext.getUserHome();

                        TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getTableDefaultChecksPatterns()
                                .getByObjectName(patternName, true);

                        if (defaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        TableDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                        AbstractRootChecksContainerSpec tableCheckRootContainer = defaultChecksPatternSpec.getTableCheckRootContainer(CheckType.monitoring, CheckTimeScale.monthly, true);
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, tableCheckRootContainer, null);
                        defaultChecksPatternSpec.setTableCheckRootContainer(tableCheckRootContainer);

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of default daily partitioned checks pattern on a table level.
     * @param checkContainerModel New configuration of the default daily partitioned checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/table/{patternName}/partitioned/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultPartitionedDailyTableChecksPattern",
            notes = "New configuration of the default daily partitioned checks on a table level. These checks will be applied to tables.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> updateDefaultPartitionedDailyTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality daily partitioned checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                        UserHome userHome = userHomeContext.getUserHome();

                        TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getTableDefaultChecksPatterns()
                                .getByObjectName(patternName, true);

                        if (defaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        TableDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                        AbstractRootChecksContainerSpec tableCheckRootContainer = defaultChecksPatternSpec.getTableCheckRootContainer(CheckType.partitioned, CheckTimeScale.daily, true);
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, tableCheckRootContainer, null);
                        defaultChecksPatternSpec.setTableCheckRootContainer(tableCheckRootContainer);

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of default monthly partitioned checks pattern on a table level.
     * @param checkContainerModel New configuration of the default monthly partitioned checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/table/{patternName}/partitioned/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultPartitionedMonthlyTableChecksPattern",
            notes = "New configuration of the default monthly partitioned checks on a table level. These checks will be applied to tables.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> updateDefaultPartitionedMonthlyTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality monthly partitioned checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                        UserHome userHome = userHomeContext.getUserHome();

                        TableDefaultChecksPatternWrapper defaultChecksPatternWrapper = userHome.getTableDefaultChecksPatterns()
                                .getByObjectName(patternName, true);

                        if (defaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        TableDefaultChecksPatternSpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                        AbstractRootChecksContainerSpec tableCheckRootContainer = defaultChecksPatternSpec.getTableCheckRootContainer(CheckType.partitioned, CheckTimeScale.monthly, true);
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, tableCheckRootContainer, null);
                        defaultChecksPatternSpec.setTableCheckRootContainer(tableCheckRootContainer);

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }
}
