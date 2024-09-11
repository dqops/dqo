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
import com.dqops.checks.DefaultRuleSeverityLevel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.policies.table.TableDefaultChecksPatternList;
import com.dqops.metadata.policies.table.TableDefaultChecksPatternSpec;
import com.dqops.metadata.policies.table.TableDefaultChecksPatternWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.TableQualityPolicyListModel;
import com.dqops.rest.models.metadata.TableQualityPolicyModel;
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
@Api(value = "TableQualityPolicies", description = "Operations for managing the configuration of data quality policies at a table level. Policies are the default configuration of data quality checks for tables matching a pattern.")
public class TableQualityPoliciesController {
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
    public TableQualityPoliciesController(UserHomeContextFactory userHomeContextFactory,
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
    @GetMapping(value = "/policies/checks/table", produces = "application/json")
    @ApiOperation(value = "getTableQualityPolicies",
            notes = "Returns a flat list of all table-level default check patterns (data quality policies) configured for this instance. Default checks are applied on tables dynamically.",
            response = TableQualityPolicyListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableQualityPolicyListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<TableQualityPolicyListModel>>> getTableQualityPolicies(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            TableDefaultChecksPatternList defaultChecksPatternsList = userHome.getTableDefaultChecksPatterns();
            List<TableDefaultChecksPatternWrapper> patternWrappersList = defaultChecksPatternsList.toList();
            boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

            List<TableQualityPolicyListModel> models = patternWrappersList.stream()
                    .map(pw -> TableQualityPolicyListModel.fromPatternSpecification(pw.getSpec(), canEdit))
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
    @GetMapping(value = "/policies/checks/table/{patternName}/target", produces = "application/json")
    @ApiOperation(value = "getTableQualityPolicyTarget", notes = "Returns a default checks pattern definition (a data quality policy)", response = TableQualityPolicyListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableQualityPolicyListModel.class),
            @ApiResponse(code = 404, message = "Pattern name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableQualityPolicyListModel>>> getTableQualityPolicyTarget(
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
            TableQualityPolicyListModel patternModel =
                    TableQualityPolicyListModel.fromPatternSpecification(defaultChecksPatternWrapper.getSpec(), canEdit);

            return new ResponseEntity<>(Mono.just(patternModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the configuration of a default checks pattern as a full specification.
     * @param patternName Pattern name.
     * @return Model of the default checks pattern.
     */
    @GetMapping(value = "/policies/checks/table/{patternName}", produces = "application/json")
    @ApiOperation(value = "getTableQualityPolicy", notes = "Returns a default table-level checks pattern (data quality policy) definition as a full specification object",
            response = TableQualityPolicyModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableQualityPolicyModel.class),
            @ApiResponse(code = 404, message = "Pattern name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableQualityPolicyModel>>> getTableQualityPolicy(
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
            TableQualityPolicyModel patternModel = new TableQualityPolicyModel() {{
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
    @PostMapping(value = "/policies/checks/table/{patternName}/target", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableQualityPolicyTarget", notes = "Creates (adds) a new default table-level checks pattern configuration (a table-level data quality policy).", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> createTableQualityPolicyTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Default checks pattern model with only the target filters") @RequestBody TableQualityPolicyListModel patternModel) {
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
    @PostMapping(value = "/policies/checks/table/{patternName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableQualityPolicyPattern", notes = "Creates (adds) a new default table-level checks pattern (data quality policy) configuration by saving a full specification object.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> createTableQualityPolicyPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Default checks pattern model") @RequestBody TableQualityPolicyModel patternModel) {
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
     * Creates (adds) a copy of an existing default table-level checks pattern configuration, under a new name.
     * @param targetPatternName New name of the copied checks pattern.
     * @param sourcePatternName Name of the existing checks pattern.
     * @return Empty response.
     */
    @PostMapping(value = "/policies/checks/table/{targetPatternName}/copyfrom/{sourcePatternName}", produces = "application/json")
    @ApiOperation(value = "copyFromTableQualityPolicy", notes = "Creates (adds) a copy of an existing default table-level checks pattern configuration (data quality policy) under a new name.",
            response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Checks pattern successfully copied", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Source check pattern not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Checks pattern with the target name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> copyFromTableQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Target pattern name") @PathVariable String targetPatternName,
            @ApiParam("Source pattern name") @PathVariable String sourcePatternName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(targetPatternName) || Strings.isNullOrEmpty(sourcePatternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnCheckPattern(targetPatternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        TableDefaultChecksPatternList defaultChecksPatternsList = userHome.getTableDefaultChecksPatterns();
                        TableDefaultChecksPatternWrapper sourceDefaultChecksPatternWrapper =
                                defaultChecksPatternsList.getByObjectName(sourcePatternName, true);

                        if (sourceDefaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        TableDefaultChecksPatternWrapper existingTargetDefaultChecksPatternWrapper =
                                defaultChecksPatternsList.getByObjectName(targetPatternName, true);

                        if (existingTargetDefaultChecksPatternWrapper != null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409
                        }

                        TableDefaultChecksPatternWrapper targetDefaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(targetPatternName);
                        TableDefaultChecksPatternSpec targetDefaultChecksPatternSpec = (TableDefaultChecksPatternSpec) sourceDefaultChecksPatternWrapper.getSpec().deepClone();
                        targetDefaultChecksPatternWrapper.setSpec(targetDefaultChecksPatternSpec);

                        userHomeContext.flush();
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
                    });
        }));
    }

    /**
     * Updates an existing default table-level checks pattern, creating possibly a new checks pattern configuration.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PutMapping(value = "/policies/checks/table/{patternName}/target", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableQualityPolicyTarget", notes = "Updates an default table-level checks pattern (data quality policy), changing only the target object", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> updateTableQualityPolicyTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Default checks pattern model") @RequestBody TableQualityPolicyListModel patternModel,
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
    @PutMapping(value = "/policies/checks/table/{patternName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableQualityPolicy", notes = "Updates an default table-level checks pattern (data quality policy) by saving a full specification object", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> updateTableQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Default checks pattern model") @RequestBody TableQualityPolicyModel patternModel,
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
    @DeleteMapping(value = "/policies/checks/table/{patternName}", produces = "application/json")
    @ApiOperation(value = "deleteTableQualityPolicy", notes = "Deletes a default table-level checks pattern (a data quality policy at a column level).", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> deleteTableQualityPolicy(
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
    @GetMapping(value = "/policies/checks/table/{patternName}/profiling", produces = "application/json")
    @ApiOperation(value = "getProfilingTableQualityPolicy",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getProfilingTableQualityPolicy(
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
            checkContainerModel.changeDefaultSeverityLevel(DefaultRuleSeverityLevel.none);

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the UI model for the default configuration of default daily monitoring checks on a table level.
     * @return Check UI model with the configuration of the daily monitoring checks that are applied to tables.
     */
    @GetMapping(value = "/policies/checks/table/{patternName}/monitoring/daily", produces = "application/json")
    @ApiOperation(value = "getMonitoringDailyTableQualityPolicy",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getMonitoringDailyTableQualityPolicy(
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
    @GetMapping(value = "/policies/checks/table/{patternName}/monitoring/monthly", produces = "application/json")
    @ApiOperation(value = "getMonitoringMonthlyTableQualityPolicy",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getMonitoringMonthlyTableQualityPolicy(
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
    @GetMapping(value = "/policies/checks/table/{patternName}/partitioned/daily", produces = "application/json")
    @ApiOperation(value = "getPartitionedDailyTableQualityPolicy",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getPartitionedDailyTableQualityPolicy(
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
    @GetMapping(value = "/policies/checks/table/{patternName}/partitioned/monthly", produces = "application/json")
    @ApiOperation(value = "getPartitionedMonthlyTableQualityPolicy",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getPartitionedMonthlyTableQualityPolicy(
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
    @PutMapping(value = "/policies/checks/table/{patternName}/profiling", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateProfilingTableQualityPolicy",
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
    public Mono<ResponseEntity<Mono<Void>>> updateProfilingTableQualityPolicy(
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
    @PutMapping(value = "/policies/checks/table/{patternName}/monitoring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateMonitoringDailyTableQualityPolicy",
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
    public Mono<ResponseEntity<Mono<Void>>> updateMonitoringDailyTableQualityPolicy(
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
    @PutMapping(value = "/policies/checks/table/{patternName}/monitoring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateMonitoringMonthlyTableQualityPolicy",
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
    public Mono<ResponseEntity<Mono<Void>>> updateMonitoringMonthlyTableQualityPolicy(
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
    @PutMapping(value = "/policies/checks/table/{patternName}/partitioned/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updatePartitionedDailyTableQualityPolicy",
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
    public Mono<ResponseEntity<Mono<Void>>> updatePartitionedDailyTableQualityPolicy(
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
    @PutMapping(value = "/policies/checks/table/{patternName}/partitioned/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updatePartitionedMonthlyTableQualityPolicy",
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
    public Mono<ResponseEntity<Mono<Void>>> updatePartitionedMonthlyTableQualityPolicy(
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
