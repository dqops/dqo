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
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.definitions.checks.CheckDefinitionList;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionWrapper;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.CheckDefinitionFolderModel;
import com.dqops.rest.models.metadata.CheckDefinitionListModel;
import com.dqops.rest.models.metadata.CheckDefinitionModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.utils.threading.CompletableFutureRunner;
import io.swagger.annotations.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * REST api controller to manage the list of checks.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Checks", description = "Data quality check definition management operations for adding/removing/changing custom data quality checks.")
public class ChecksController {
    private DqoHomeContextFactory dqoHomeContextFactory;
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param dqoHomeContextFactory  Dqo home context factory.
     * @param userHomeContextFactory User home context factory.
     */
    @Autowired
    public ChecksController(DqoHomeContextFactory dqoHomeContextFactory, UserHomeContextFactory userHomeContextFactory) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a flat list of all checks
     * @return List of all checks
     */
    @GetMapping(value = "/checks", produces = "application/json")
    @ApiOperation(value = "getAllChecks", notes = "Returns a flat list of all checks available in DQOps, both built-in checks and user defined or customized checks.",
            response = CheckDefinitionListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckDefinitionListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckDefinitionListModel>>> getAllChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            CheckDefinitionFolderModel checkDefinitionFolderModel = createCheckTreeModel(principal);
            List<CheckDefinitionListModel> allChecks = checkDefinitionFolderModel.getAllChecks();
            allChecks.sort(Comparator.comparing(model -> model.getFullCheckName()));

            return new ResponseEntity<>(Flux.fromStream(allChecks.stream()), HttpStatus.OK);
        }));
    }

    /**
     * Returns the configuration of a check, first looking up if it is a custom check, then looking up if it is a built-in check.
     * @param fullCheckName Full check name.
     * @return Model of the check with specific check name.
     */
    @GetMapping(value = "/checks/{fullCheckName}", produces = "application/json")
    @ApiOperation(value = "getCheck", notes = "Returns a check definition", response = CheckDefinitionModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckDefinitionModel.class),
            @ApiResponse(code = 404, message = "Check name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckDefinitionModel>>> getCheck(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Full check name") @PathVariable String fullCheckName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(fullCheckName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            CheckDefinitionWrapper userCheckDefinitionWrapper = userHome.getChecks().getByObjectName(fullCheckName, true);

            DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
            DqoHome dqoHome = dqoHomeContext.getDqoHome();
            CheckDefinitionWrapper builtinCheckDefinitionWrapper = dqoHome.getChecks().getByObjectName(fullCheckName, true);

            if (userCheckDefinitionWrapper == null && builtinCheckDefinitionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            CheckDefinitionWrapper effectiveCheckDefinition = Optional.ofNullable(userCheckDefinitionWrapper).orElse(builtinCheckDefinitionWrapper);

            boolean isCustom = userCheckDefinitionWrapper != null;
            boolean isBuiltIn = builtinCheckDefinitionWrapper != null;
            boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            CheckDefinitionModel checkDefinitionModel = new CheckDefinitionModel(effectiveCheckDefinition, isCustom, isBuiltIn, canEdit);

            return new ResponseEntity<>(Mono.just(checkDefinitionModel), HttpStatus.OK);
        }));
    }

    /**
     * Creates (adds) a new custom check given the check information (a sensor and a rule pair).
     * @param checkDefinitionModel Check model to save.
     * @param fullCheckName Full check name.
     * @return Empty response.
     */
    @PostMapping(value = "/checks/{fullCheckName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createCheck", notes = "Creates (adds) a new custom check that is a pair of a sensor name and a rule name.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New custom check successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Custom check with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> createCheck(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Full check name") @PathVariable String fullCheckName,
            @ApiParam("Check model") @RequestBody CheckDefinitionModel checkDefinitionModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (checkDefinitionModel == null || Strings.isNullOrEmpty(fullCheckName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            String[] fullCheckNameSplit = fullCheckName.split("/");
            if (!fullCheckNameSplit[fullCheckNameSplit.length - 1].equals(checkDefinitionModel.getCheckName())) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            UserHome userHome = userHomeContext.getUserHome();

            CheckDefinitionList userCheckDefinitionList = userHome.getChecks();
            CheckDefinitionWrapper existingCheckDefinitionWrapper = userCheckDefinitionList.getByObjectName(fullCheckName, true);

            if (existingCheckDefinitionWrapper != null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
            }

            CheckDefinitionWrapper checkDefinitionWrapper = userCheckDefinitionList.createAndAddNew(fullCheckName);
            checkDefinitionWrapper.setSpec(checkDefinitionModel.toCheckDefinitionSpec());
            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
        }));
    }

    /**
     * Updates an existing check, creating possibly a custom check.
     * @param checkDefinitionModel Check definition model.
     * @param fullCheckName Full check name.
     * @return Empty response.
     */
    @PutMapping(value = "/checks/{fullCheckName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateCheck", notes = "Updates an existing check, making a custom check definition if it is not present", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom check successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Check not found"),
            @ApiResponse(code = 409, message = "Cannot change a check definition of a built-in check"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateCheck(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("List of check definitions") @RequestBody CheckDefinitionModel checkDefinitionModel,
            @ApiParam("Full check name") @PathVariable String fullCheckName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(fullCheckName) || checkDefinitionModel == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            UserHome userHome = userHomeContext.getUserHome();
            CheckDefinitionList userCheckDefinitionList = userHome.getChecks();
            CheckDefinitionWrapper existingUserCheckDefinitionWrapper = userCheckDefinitionList.getByObjectName(fullCheckName, true);

            DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
            DqoHome dqoHome = dqoHomeContext.getDqoHome();
            CheckDefinitionWrapper builtinCheckDefinitionWrapper = dqoHome.getChecks().getByObjectName(fullCheckName, true);

            if (existingUserCheckDefinitionWrapper == null && builtinCheckDefinitionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            if (builtinCheckDefinitionWrapper != null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
            }

            if (checkDefinitionModel.equalsBuiltInCheck(builtinCheckDefinitionWrapper)) {
                if (existingUserCheckDefinitionWrapper != null) {
                    existingUserCheckDefinitionWrapper.markForDeletion(); // remove customization
                }
                else {
                    // ignore saving
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
                }
            }

            CheckDefinitionSpec newCheckDefinitionSpec = checkDefinitionModel.toCheckDefinitionSpec();
            if (existingUserCheckDefinitionWrapper == null) {
                CheckDefinitionWrapper checkDefinitionWrapper = userCheckDefinitionList.createAndAddNew(fullCheckName);
                checkDefinitionWrapper.setSpec(newCheckDefinitionSpec);
            }
            else {
                CheckDefinitionSpec oldCheckDefinitionSpec = existingUserCheckDefinitionWrapper.getSpec();  // loading
                existingUserCheckDefinitionWrapper.setSpec(newCheckDefinitionSpec);
            }

            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
        }));
    }

    /**
     * Deletes a custom check definition.
     * @param fullCheckName  Full check name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/checks/{fullCheckName}", produces = "application/json")
    @ApiOperation(value = "deleteCheck", notes = "Deletes a custom check definition", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Custom check definition successfully deleted", response = Void.class),
            @ApiResponse(code = 404, message = "Custom check not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> deleteCheck(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Full check name") @PathVariable String fullCheckName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(fullCheckName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            UserHome userHome = userHomeContext.getUserHome();

            CheckDefinitionList userCheckDefinitionList = userHome.getChecks();
            CheckDefinitionWrapper existingUserCheckDefinitionWrapper = userCheckDefinitionList.getByObjectName(fullCheckName, true);

            if (existingUserCheckDefinitionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            existingUserCheckDefinitionWrapper.markForDeletion();
            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Returns all combined check folder model.
     * @return check basic tree model.
     */
    @GetMapping(value = "/definitions/checks", produces = "application/json")
    @ApiOperation(value = "getCheckFolderTree", notes = "Returns a tree of all checks available in DQOps, both built-in checks and user defined or customized checks.",
            response = CheckDefinitionFolderModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckDefinitionFolderModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckDefinitionFolderModel>>> getCheckFolderTree(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            CheckDefinitionFolderModel checkDefinitionFolderModel = createCheckTreeModel(principal);

            return new ResponseEntity<>(Mono.just(checkDefinitionFolderModel), HttpStatus.OK);
        }));
    }

    /**
     * Builds a check tree.
     * @return Check tree.
     */
    @NotNull
    private CheckDefinitionFolderModel createCheckTreeModel(DqoUserPrincipal principal) {
        CheckDefinitionFolderModel checkDefinitionFolderModel = new CheckDefinitionFolderModel();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        List<CheckDefinitionWrapper> checkDefinitionWrapperListDqoHome = new ArrayList<>(dqoHome.getChecks().toList());
        checkDefinitionWrapperListDqoHome.sort(Comparator.comparing((CheckDefinitionWrapper rw) -> !rw.getSpec().isStandard())
                        .thenComparing(rw -> rw.getCheckName()));
        List<String> builtInCheckNames = checkDefinitionWrapperListDqoHome.stream().map(rw -> rw.getCheckName()).collect(Collectors.toList());

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();
        List<CheckDefinitionWrapper> checkDefinitionWrapperListUserHome = new ArrayList<>(userHome.getChecks().toList());
        checkDefinitionWrapperListUserHome.sort(Comparator.comparing((CheckDefinitionWrapper rw) -> !rw.getSpec().isStandard())
                .thenComparing(rw -> rw.getCheckName()));
        List<String> customCheckNames = checkDefinitionWrapperListUserHome.stream().map(rw -> rw.getCheckName()).collect(Collectors.toList());
        boolean canEditDefinitions = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

        for (CheckDefinitionWrapper checkDefinitionWrapperDqoHome : checkDefinitionWrapperListDqoHome) {
            String checkNameDqoHome = checkDefinitionWrapperDqoHome.getCheckName();
            checkDefinitionFolderModel.addCheck(checkNameDqoHome, customCheckNames.contains(checkNameDqoHome), true, canEditDefinitions, checkDefinitionWrapperDqoHome.getSpec().getYamlParsingError());
        }

        for (CheckDefinitionWrapper checkDefinitionWrapperUserHome : checkDefinitionWrapperListUserHome) {
            String checkNameUserHome = checkDefinitionWrapperUserHome.getCheckName();
            checkDefinitionFolderModel.addCheck(checkNameUserHome, true, builtInCheckNames.contains(checkNameUserHome), canEditDefinitions, checkDefinitionWrapperUserHome.getSpec().getYamlParsingError());
        }

        checkDefinitionFolderModel.addFolderIfMissing("table/profiling/custom");
        checkDefinitionFolderModel.addFolderIfMissing("column/profiling/custom");
        checkDefinitionFolderModel.addFolderIfMissing("table/monitoring/daily/custom");
        checkDefinitionFolderModel.addFolderIfMissing("column/monitoring/daily/custom");
        checkDefinitionFolderModel.addFolderIfMissing("table/monitoring/monthly/custom");
        checkDefinitionFolderModel.addFolderIfMissing("column/monitoring/monthly/custom");
        checkDefinitionFolderModel.addFolderIfMissing("table/partitioned/daily/custom");
        checkDefinitionFolderModel.addFolderIfMissing("column/partitioned/daily/custom");
        checkDefinitionFolderModel.addFolderIfMissing("table/partitioned/monthly/custom");
        checkDefinitionFolderModel.addFolderIfMissing("column/partitioned/monthly/custom");

        return checkDefinitionFolderModel;
    }
}