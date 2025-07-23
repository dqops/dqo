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
import com.dqops.metadata.policies.column.ColumnQualityPolicyList;
import com.dqops.metadata.policies.column.ColumnQualityPolicySpec;
import com.dqops.metadata.policies.column.ColumnQualityPolicyWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.ColumnQualityPolicyListModel;
import com.dqops.rest.models.metadata.ColumnQualityPolicyModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingService;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.locking.RestApiLockService;
import com.dqops.utils.threading.CompletableFutureRunner;
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
 * REST api controller to manage the list of default column-level check patterns.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "ColumnQualityPolicies", description = "Operations for managing the configuration of data quality policies at a column level. Policies are the default configuration of data quality checks for columns matching a pattern.")
public class ColumnQualityPoliciesController {
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
    public ColumnQualityPoliciesController(UserHomeContextFactory userHomeContextFactory,
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
    @GetMapping(value = "/policies/checks/column", produces = "application/json")
    @ApiOperation(value = "getColumnQualityPolicies",
            notes = "Returns a flat list of all column-level default check patterns configured for this instance. Default checks are applied on columns dynamically.",
            response = ColumnQualityPolicyListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ColumnQualityPolicyListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<ColumnQualityPolicyListModel>>> getColumnQualityPolicies(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            ColumnQualityPolicyList defaultChecksPatternsList = userHome.getColumnQualityPolicies();
            List<ColumnQualityPolicyWrapper> patternWrappersList = defaultChecksPatternsList.toList();
            boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

            List<ColumnQualityPolicyListModel> models = patternWrappersList.stream()
                    .map(pw -> ColumnQualityPolicyListModel.fromPatternSpecification(pw.getSpec(), canEdit))
                    .collect(Collectors.toList());
            models.sort(Comparator.comparing(model -> model.getPolicyName()));

            return new ResponseEntity<>(Flux.fromStream(models.stream()), HttpStatus.OK);
        }));
    }

    /**
     * Returns the configuration of a default checks pattern.
     * @param patternName Pattern name.
     * @return Model of the default checks pattern.
     */
    @GetMapping(value = "/policies/checks/column/{patternName}/target", produces = "application/json")
    @ApiOperation(value = "getColumnQualityPolicyTarget", notes = "Returns a default checks pattern definition", response = ColumnQualityPolicyListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ColumnQualityPolicyListModel.class),
            @ApiResponse(code = 404, message = "Pattern name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<ColumnQualityPolicyListModel>>> getColumnQualityPolicyTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Column pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            ColumnQualityPolicyWrapper defaultChecksPatternWrapper =
                    userHome.getColumnQualityPolicies().getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            ColumnQualityPolicyListModel patternModel =
                    ColumnQualityPolicyListModel.fromPatternSpecification(defaultChecksPatternWrapper.getSpec(), canEdit);

            return new ResponseEntity<>(Mono.just(patternModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the configuration of a default checks pattern as a full specification.
     * @param patternName Pattern name.
     * @return Model of the default checks pattern.
     */
    @GetMapping(value = "/policies/checks/column/{patternName}", produces = "application/json")
    @ApiOperation(value = "getColumnQualityPolicy", notes = "Returns a default checks pattern definition as a full specification object", response = ColumnQualityPolicyModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ColumnQualityPolicyModel.class),
            @ApiResponse(code = 404, message = "Pattern name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<ColumnQualityPolicyModel>>> getColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Column pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            ColumnQualityPolicyWrapper defaultChecksPatternWrapper =
                    userHome.getColumnQualityPolicies().getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            ColumnQualityPolicyModel patternModel = new ColumnQualityPolicyModel() {{
                setPolicyName(patternName);
                setPolicySpec(defaultChecksPatternWrapper.getSpec());
                setCanEdit(canEdit);
                setYamlParsingError(defaultChecksPatternWrapper.getSpec().getYamlParsingError());
            }};

            return new ResponseEntity<>(Mono.just(patternModel), HttpStatus.OK);
        }));
    }

    /**
     * Creates (adds) a new default column-level checks pattern configuration.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PostMapping(value = "/policies/checks/column/{patternName}/target", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createColumnQualityPolicyTarget", notes = "Creates (adds) a new default column-level checks pattern configuration.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> createColumnQualityPolicyTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Default checks pattern model with only the target filters") @RequestBody ColumnQualityPolicyListModel patternModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (patternModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyList defaultChecksPatternsList = userHome.getColumnQualityPolicies();
                        ColumnQualityPolicyWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                        if (existingDefaultChecksPatternWrapper != null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
                        }

                        ColumnQualityPolicyWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                        ColumnQualityPolicySpec patternSpec = new ColumnQualityPolicySpec();
                        patternSpec.setTarget(patternModel.getTargetColumn());
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
     * Creates (adds) a new default column-level checks pattern configuration as a full specification object.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PostMapping(value = "/policies/checks/column/{patternName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createColumnQualityPolicy", notes = "Creates (adds) a new default column-level checks pattern configuration by saving a full specification object.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> createColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Default checks pattern model") @RequestBody ColumnQualityPolicyModel patternModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (patternModel == null || Strings.isNullOrEmpty(patternName) || patternModel.getPolicySpec() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyList defaultChecksPatternsList = userHome.getColumnQualityPolicies();
                        ColumnQualityPolicyWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                        if (existingDefaultChecksPatternWrapper != null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
                        }

                        ColumnQualityPolicyWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                        defaultChecksPatternWrapper.setSpec(patternModel.getPolicySpec());
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
                    });
        }));
    }

    /**
     * Creates (adds) a copy of an existing default column-level checks pattern configuration, under a new name.
     * @param targetPatternName New name of the copied checks pattern.
     * @param sourcePatternName Name of the existing checks pattern.
     * @return Empty response.
     */
    @PostMapping(value = "/policies/checks/column/{targetPatternName}/copyfrom/{sourcePatternName}", produces = "application/json")
    @ApiOperation(value = "copyFromColumnQualityPolicy", notes = "Creates (adds) a copy of an existing default column-level checks pattern configuration, under a new name.", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> copyFromColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Target pattern name") @PathVariable String targetPatternName,
            @ApiParam("Source pattern name") @PathVariable String sourcePatternName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(targetPatternName) || Strings.isNullOrEmpty(sourcePatternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnCheckPattern(targetPatternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyList defaultChecksPatternsList = userHome.getColumnQualityPolicies();
                        ColumnQualityPolicyWrapper sourceDefaultChecksPatternWrapper =
                                defaultChecksPatternsList.getByObjectName(sourcePatternName, true);

                        if (sourceDefaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        ColumnQualityPolicyWrapper existingTargetDefaultChecksPatternWrapper =
                                defaultChecksPatternsList.getByObjectName(targetPatternName, true);

                        if (existingTargetDefaultChecksPatternWrapper != null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409
                        }

                        ColumnQualityPolicyWrapper targetDefaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(targetPatternName);
                        ColumnQualityPolicySpec targetDefaultChecksPatternSpec = (ColumnQualityPolicySpec) sourceDefaultChecksPatternWrapper.getSpec().deepClone();
                        targetDefaultChecksPatternWrapper.setSpec(targetDefaultChecksPatternSpec);

                        userHomeContext.flush();
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
                    });
        }));
    }

    /**
     * Updates an existing default column-level checks pattern, creating possibly a new checks pattern configuration.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PutMapping(value = "/policies/checks/column/{patternName}/target", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnQualityPolicyTarget", notes = "Updates an default column-level checks pattern, changing only the target object", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> updateColumnQualityPolicyTarget(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Default checks pattern model") @RequestBody ColumnQualityPolicyListModel patternModel,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(patternName) || patternModel == null || patternModel.getTargetColumn() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyList defaultChecksPatternsList = userHome.getColumnQualityPolicies();
                        ColumnQualityPolicyWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);
                        ColumnQualityPolicySpec targetPatternSpec;

                        if (existingDefaultChecksPatternWrapper == null) {
                            ColumnQualityPolicyWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                            targetPatternSpec = new ColumnQualityPolicySpec() {{
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
        }));
    }

    /**
     * Updates an existing default column-level checks pattern, creating possibly a new checks pattern configuration, by passing a full specification.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PutMapping(value = "/policies/checks/column/{patternName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnQualityPolicy", notes = "Updates an default column-level checks pattern by saving a full specification object", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> updateColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Default checks pattern model") @RequestBody ColumnQualityPolicyModel patternModel,
            @ApiParam("Pattern name") @PathVariable String patternName) {

        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(patternName) || patternModel == null || patternModel.getPolicySpec() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyList defaultChecksPatternsList = userHome.getColumnQualityPolicies();
                        ColumnQualityPolicyWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

                        if (existingDefaultChecksPatternWrapper == null) {
                            ColumnQualityPolicyWrapper defaultChecksPatternWrapper = defaultChecksPatternsList.createAndAddNew(patternName);
                            defaultChecksPatternWrapper.setSpec(patternModel.getPolicySpec());
                        } else {
                            ColumnQualityPolicySpec oldPatternSpec = existingDefaultChecksPatternWrapper.getSpec(); // just to load
                            existingDefaultChecksPatternWrapper.setSpec(patternModel.getPolicySpec());
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
    @DeleteMapping(value = "/policies/checks/column/{patternName}", produces = "application/json")
    @ApiOperation(value = "deleteColumnQualityPolicy", notes = "Deletes a default column-level checks pattern", response = Void.class,
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
    public Mono<ResponseEntity<Mono<Void>>> deleteColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyList defaultChecksPatternsList = userHome.getColumnQualityPolicies();
                        ColumnQualityPolicyWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

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
     * Returns the UI model for the default configuration of default profiling checks on a column level.
     * @return Check UI model with the configuration of the profiling checks that are applied to columns.
     */
    @GetMapping(value = "/policies/checks/column/{patternName}/profiling", produces = "application/json")
    @ApiOperation(value = "getProfilingColumnQualityPolicy",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getProfilingColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            ColumnQualityPolicyWrapper defaultChecksPatternWrapper = userHome.getColumnQualityPolicies()
                    .getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            ColumnQualityPolicySpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
            AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.profiling, null, false);

            CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                    null, null, null, executionContext, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
            checkContainerModel.changeDefaultSeverityLevel(DefaultRuleSeverityLevel.none);

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the UI model for the default configuration of default daily monitoring checks on a column level.
     * @return Check UI model with the configuration of the daily monitoring checks that are applied to columns.
     */
    @GetMapping(value = "/policies/checks/column/{patternName}/monitoring/daily", produces = "application/json")
    @ApiOperation(value = "getMonitoringDailyColumnQualityPolicy",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getMonitoringDailyColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            ColumnQualityPolicyWrapper defaultChecksPatternWrapper = userHome.getColumnQualityPolicies()
                    .getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            ColumnQualityPolicySpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
            AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, false);

            CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                    null, null, null, executionContext, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the UI model for the default configuration of default monthly monitoring checks on a column level.
     * @return Check UI model with the configuration of the monthly monitoring checks that are applied to columns.
     */
    @GetMapping(value = "/policies/checks/column/{patternName}/monitoring/monthly", produces = "application/json")
    @ApiOperation(value = "getMonitoringMonthlyColumnQualityPolicy",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getMonitoringMonthlyColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            ColumnQualityPolicyWrapper defaultChecksPatternWrapper = userHome.getColumnQualityPolicies()
                    .getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            ColumnQualityPolicySpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
            AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.monthly, false);

            CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                    null, null, null, executionContext, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the UI model for the default configuration of default daily partitioned checks on a column level.
     * @return Check UI model with the configuration of the daily partitioned checks that are applied to columns.
     */
    @GetMapping(value = "/policies/checks/column/{patternName}/partitioned/daily", produces = "application/json")
    @ApiOperation(value = "getPartitionedDailyColumnQualityPolicy",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getPartitionedDailyColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            ColumnQualityPolicyWrapper defaultChecksPatternWrapper = userHome.getColumnQualityPolicies()
                    .getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            ColumnQualityPolicySpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
            AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.partitioned, CheckTimeScale.daily, false);

            CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                    null, null, null, executionContext, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Returns the UI model for the default configuration of default monthly partitioned checks on a column level.
     * @return Check UI model with the configuration of the monthly partitioned checks that are applied to columns.
     */
    @GetMapping(value = "/policies/checks/column/{patternName}/partitioned/monthly", produces = "application/json")
    @ApiOperation(value = "getPartitionedMonthlyColumnQualityPolicy",
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
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getPartitionedMonthlyColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            ColumnQualityPolicyWrapper defaultChecksPatternWrapper = userHome.getColumnQualityPolicies()
                    .getByObjectName(patternName, true);

            if (defaultChecksPatternWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            ColumnQualityPolicySpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
            AbstractRootChecksContainerSpec checksContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.partitioned, CheckTimeScale.monthly, false);

            CheckContainerModel checkContainerModel = this.specToModelCheckMappingService.createModel(checksContainer,
                    null, null, null, executionContext, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

            return new ResponseEntity<>(Mono.just(checkContainerModel), HttpStatus.OK);
        }));
    }

    /**
     * Updates the configuration of default profiling checks pattern on a column level.
     * @param checkContainerModel New configuration of the default profiling checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/policies/checks/column/{patternName}/profiling", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateProfilingColumnQualityPolicy",
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
    public Mono<ResponseEntity<Mono<Void>>> updateProfilingColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality profiling checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyWrapper defaultChecksPatternWrapper = userHome.getColumnQualityPolicies()
                                .getByObjectName(patternName, true);

                        if (defaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        ColumnQualityPolicySpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                        AbstractRootChecksContainerSpec columnCheckRootContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.profiling, null, true);
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, columnCheckRootContainer, null);
                        defaultChecksPatternSpec.setColumnCheckRootContainer(columnCheckRootContainer);

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of default daily monitoring checks pattern on a column level.
     * @param checkContainerModel New configuration of the default daily monitoring checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/policies/checks/column/{patternName}/monitoring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateMonitoringDailyColumnQualityPolicy",
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
    public Mono<ResponseEntity<Mono<Void>>> updateMonitoringDailyColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality daily monitoring checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyWrapper defaultChecksPatternWrapper = userHome.getColumnQualityPolicies()
                                .getByObjectName(patternName, true);

                        if (defaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        ColumnQualityPolicySpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                        AbstractRootChecksContainerSpec columnCheckRootContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, columnCheckRootContainer, null);
                        defaultChecksPatternSpec.setColumnCheckRootContainer(columnCheckRootContainer);

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of default monthly monitoring checks pattern on a column level.
     * @param checkContainerModel New configuration of the default monthly monitoring checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/policies/checks/column/{patternName}/monitoring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateMonitoringMonthlyColumnQualityPolicy",
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
    public Mono<ResponseEntity<Mono<Void>>> updateMonitoringMonthlyColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality monthly monitoring checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyWrapper defaultChecksPatternWrapper = userHome.getColumnQualityPolicies()
                                .getByObjectName(patternName, true);

                        if (defaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        ColumnQualityPolicySpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                        AbstractRootChecksContainerSpec columnCheckRootContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.monthly, true);
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, columnCheckRootContainer, null);
                        defaultChecksPatternSpec.setColumnCheckRootContainer(columnCheckRootContainer);

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of default daily partitioned checks pattern on a column level.
     * @param checkContainerModel New configuration of the default daily partitioned checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/policies/checks/column/{patternName}/partitioned/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updatePartitionedDailyColumnQualityPolicy",
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
    public Mono<ResponseEntity<Mono<Void>>> updatePartitionedDailyColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality daily partitioned checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyWrapper defaultChecksPatternWrapper = userHome.getColumnQualityPolicies()
                                .getByObjectName(patternName, true);

                        if (defaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        ColumnQualityPolicySpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                        AbstractRootChecksContainerSpec columnCheckRootContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.partitioned, CheckTimeScale.daily, true);
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, columnCheckRootContainer, null);
                        defaultChecksPatternSpec.setColumnCheckRootContainer(columnCheckRootContainer);

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of default monthly partitioned checks pattern on a column level.
     * @param checkContainerModel New configuration of the default monthly partitioned checks pattern.
     * @return Empty response.
     */
    @PutMapping(value = "/policies/checks/column/{patternName}/partitioned/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updatePartitionedMonthlyColumnQualityPolicy",
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
    public Mono<ResponseEntity<Mono<Void>>> updatePartitionedMonthlyColumnQualityPolicy(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Model with the changes to be applied to the data quality monthly partitioned checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (checkContainerModel == null || Strings.isNullOrEmpty(patternName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            return this.lockService.callSynchronouslyOnCheckPattern(patternName,
                    () -> {
                        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
                        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
                        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
                        UserHome userHome = userHomeContext.getUserHome();

                        ColumnQualityPolicyWrapper defaultChecksPatternWrapper = userHome.getColumnQualityPolicies()
                                .getByObjectName(patternName, true);

                        if (defaultChecksPatternWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        ColumnQualityPolicySpec defaultChecksPatternSpec = defaultChecksPatternWrapper.getSpec();
                        AbstractRootChecksContainerSpec columnCheckRootContainer = defaultChecksPatternSpec.getColumnCheckRootContainer(CheckType.partitioned, CheckTimeScale.monthly, true);
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, columnCheckRootContainer, null);
                        defaultChecksPatternSpec.setColumnCheckRootContainer(columnCheckRootContainer);

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }
}
