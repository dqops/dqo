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

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.metadata.definitions.checks.CheckDefinitionList;
import com.dqops.metadata.definitions.checks.CheckDefinitionWrapper;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.CheckSpecFolderBasicModel;
import com.dqops.rest.models.metadata.CheckSpecBasicModel;
import com.dqops.rest.models.metadata.CheckSpecModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import autovalue.shaded.com.google.common.base.Strings;
import com.dqops.core.principal.DqoUserPrincipal;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * REST api controller to manage the list of checks.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Checks", description = "Data quality check definition management")
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
    @ApiOperation(value = "getAllChecks", notes = "Returns a flat list of all checks available in DQO, both built-in checks and user defined or customized checks.",
            response = CheckSpecBasicModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckSpecBasicModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<CheckSpecBasicModel>> getAllChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        CheckSpecFolderBasicModel checkSpecFolderBasicModel = createCheckTreeModel(principal);
        List<CheckSpecBasicModel> allChecks = checkSpecFolderBasicModel.getAllChecks();
        allChecks.sort(Comparator.comparing(model -> model.getFullCheckName()));

        return new ResponseEntity<>(Flux.fromStream(allChecks.stream()), HttpStatus.OK);
    }

    /**
     * Returns the configuration of a check, first looking up if it is a custom check, then looking up if it is a built-in check.
     * @param fullCheckName Full check name.
     * @return Model of the check with specific check name.
     */
    @GetMapping(value = "/checks/{fullCheckName}", produces = "application/json")
    @ApiOperation(value = "getCheck", notes = "Returns a check definition", response = CheckSpecModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckSpecModel.class),
            @ApiResponse(code = 404, message = "Check name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckSpecModel>> getCheck(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Full check name") @PathVariable String fullCheckName) {

        if (Strings.isNullOrEmpty(fullCheckName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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
        CheckSpecModel checkSpecModel = new CheckSpecModel(effectiveCheckDefinition, isCustom, isBuiltIn, canEdit);

        return new ResponseEntity<>(Mono.just(checkSpecModel), HttpStatus.OK);
    }

    /**
     * Creates (adds) a new custom check given the check information (a sensor and a rule pair).
     * @param checkSpecModel Check model to save.
     * @param fullCheckName Full check name.
     * @return Empty response.
     */
    @PostMapping(value = "/checks/{fullCheckName}", consumes = "application/json")
    @ApiOperation(value = "createCheck", notes = "Creates (adds) a new custom check that is a pair of a sensor name and a rule name.",
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New custom check successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Custom check with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> createCheck(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Full check name") @PathVariable String fullCheckName,
            @ApiParam("Check model") @RequestBody CheckSpecModel checkSpecModel) {
        if (checkSpecModel == null || Strings.isNullOrEmpty(fullCheckName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        String[] fullCheckNameSplit = fullCheckName.split("/");
        if (!fullCheckNameSplit[fullCheckNameSplit.length - 1].equals(checkSpecModel.getCheckName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        CheckDefinitionList userCheckDefinitionList = userHome.getChecks();
        CheckDefinitionWrapper existingCheckDefinitionWrapper = userCheckDefinitionList.getByObjectName(fullCheckName, true);

        if (existingCheckDefinitionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        CheckDefinitionWrapper checkDefinitionWrapper = userCheckDefinitionList.createAndAddNew(fullCheckName);
        checkDefinitionWrapper.setSpec(checkSpecModel.toCheckDefinitionSpec());
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates an existing check, creating possibly a custom check.
     * @param checkSpecModel Check definition model.
     * @param fullCheckName Full check name.
     * @return Empty response.
     */
    @PutMapping(value = "/checks/{fullCheckName}", consumes = "application/json")
    @ApiOperation(value = "updateCheck", notes = "Updates an existing check, making a custom check definition if it is not present",
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom check successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Check not found"),
            @ApiResponse(code = 409, message = "Cannot change a check definition of a built-in check"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> updateCheck(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("List of check definitions") @RequestBody CheckSpecModel checkSpecModel,
            @ApiParam("Full check name") @PathVariable String fullCheckName) {

        if (Strings.isNullOrEmpty(fullCheckName) || checkSpecModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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

        if (checkSpecModel.equalsBuiltInCheck(builtinCheckDefinitionWrapper)) {
            if (existingUserCheckDefinitionWrapper != null) {
                existingUserCheckDefinitionWrapper.markForDeletion(); // remove customization
            }
            else {
                // ignore saving
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
            }
        }

        if (existingUserCheckDefinitionWrapper == null) {
            CheckDefinitionWrapper checkDefinitionWrapper = userCheckDefinitionList.createAndAddNew(fullCheckName);
            checkDefinitionWrapper.setSpec(checkSpecModel.toCheckDefinitionSpec());
        }
        else {
            existingUserCheckDefinitionWrapper.setSpec(checkSpecModel.toCheckDefinitionSpec());
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a custom check definition.
     * @param fullCheckName  Full check name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/checks/{fullCheckName}", produces = "application/json")
    @ApiOperation(value = "deleteCheck", notes = "Deletes a custom check definition",
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Custom check definition successfully deleted"),
            @ApiResponse(code = 404, message = "Custom check not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> deleteCheck(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Full check name") @PathVariable String fullCheckName) {

        if (Strings.isNullOrEmpty(fullCheckName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        CheckDefinitionList userCheckDefinitionList = userHome.getChecks();
        CheckDefinitionWrapper existingUserCheckDefinitionWrapper = userCheckDefinitionList.getByObjectName(fullCheckName, true);

        if (existingUserCheckDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        existingUserCheckDefinitionWrapper.markForDeletion();
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Returns all combined check folder model.
     * @return check basic tree model.
     */
    @GetMapping(value = "/definitions/checks", produces = "application/json")
    @ApiOperation(value = "getCheckFolderTree", notes = "Returns a tree of all checks available in DQO, both built-in checks and user defined or customized checks.",
            response = CheckSpecFolderBasicModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckSpecFolderBasicModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckSpecFolderBasicModel>> getCheckFolderTree(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        CheckSpecFolderBasicModel checkSpecFolderBasicModel = createCheckTreeModel(principal);

        return new ResponseEntity<>(Mono.just(checkSpecFolderBasicModel), HttpStatus.OK);
    }

    /**
     * Builds a check tree.
     * @return Check tree.
     */
    @NotNull
    private CheckSpecFolderBasicModel createCheckTreeModel(DqoUserPrincipal principal) {
        CheckSpecFolderBasicModel checkSpecFolderBasicModel = new CheckSpecFolderBasicModel();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        List<CheckDefinitionWrapper> checkDefinitionWrapperListDqoHome = new ArrayList<>(dqoHome.getChecks().toList());
        checkDefinitionWrapperListDqoHome.sort(Comparator.comparing(rw -> rw.getCheckName()));
        Set<String> builtInCheckNames = checkDefinitionWrapperListDqoHome.stream().map(rw -> rw.getCheckName()).collect(Collectors.toSet());

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        List<CheckDefinitionWrapper> checkDefinitionWrapperListUserHome = new ArrayList<>(userHome.getChecks().toList());
        checkDefinitionWrapperListUserHome.sort(Comparator.comparing(rw -> rw.getCheckName()));
        Set<String> customCheckNames = checkDefinitionWrapperListUserHome.stream().map(rw -> rw.getCheckName()).collect(Collectors.toSet());
        boolean canEditDefinitions = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

        for (CheckDefinitionWrapper checkDefinitionWrapperUserHome : checkDefinitionWrapperListUserHome) {
            String checkNameUserHome = checkDefinitionWrapperUserHome.getCheckName();
            checkSpecFolderBasicModel.addCheck(checkNameUserHome, true, builtInCheckNames.contains(checkNameUserHome), canEditDefinitions);
        }

        for (CheckDefinitionWrapper checkDefinitionWrapperDqoHome : checkDefinitionWrapperListDqoHome) {
            String checkNameDqoHome = checkDefinitionWrapperDqoHome.getCheckName();
            checkSpecFolderBasicModel.addCheck(checkNameDqoHome, customCheckNames.contains(checkNameDqoHome), true, canEditDefinitions);
        }

        checkSpecFolderBasicModel.addFolderIfMissing("table/profiling/custom");
        checkSpecFolderBasicModel.addFolderIfMissing("column/profiling/custom");
        checkSpecFolderBasicModel.addFolderIfMissing("table/monitoring/daily/custom");
        checkSpecFolderBasicModel.addFolderIfMissing("column/monitoring/daily/custom");
        checkSpecFolderBasicModel.addFolderIfMissing("table/monitoring/monthly/custom");
        checkSpecFolderBasicModel.addFolderIfMissing("column/monitoring/monthly/custom");
        checkSpecFolderBasicModel.addFolderIfMissing("table/partitioned/daily/custom");
        checkSpecFolderBasicModel.addFolderIfMissing("column/partitioned/daily/custom");
        checkSpecFolderBasicModel.addFolderIfMissing("table/partitioned/monthly/custom");
        checkSpecFolderBasicModel.addFolderIfMissing("column/partitioned/monthly/custom");

        return checkSpecFolderBasicModel;
    }
}