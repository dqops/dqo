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

import ai.dqo.core.jobqueue.DqoQueueJobId;
import ai.dqo.metadata.definitions.checks.CheckDefinitionList;
import ai.dqo.metadata.definitions.checks.CheckDefinitionWrapper;
import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.metadata.CheckBasicFolderModel;
import ai.dqo.rest.models.metadata.CheckBasicModel;
import ai.dqo.rest.models.metadata.CheckModel;
import ai.dqo.rest.models.metadata.RuleBasicFolderModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import autovalue.shaded.com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Api(value = "Checks", description = "Check management")
public class CheckController {

    private DqoHomeContextFactory dqoHomeContextFactory;
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param dqoHomeContextFactory  Dqo home context factory.
     * @param userHomeContextFactory User home context factory.
     */
    @Autowired
    public CheckController(DqoHomeContextFactory dqoHomeContextFactory, UserHomeContextFactory userHomeContextFactory) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns the configuration of a check, first looking up if it is a custom check, then looking up if it is a built-in check.
     * @param fullCheckName Full check name.
     * @return Model of the check with specific check name.
     */
    @GetMapping("/checks/{fullCheckName}")
    @ApiOperation(value = "getCheck", notes = "Returns a check definition", response = CheckModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckModel.class),
            @ApiResponse(code = 404, message = "Check name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckModel>> getCheck(
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
        CheckModel checkModel = new CheckModel(effectiveCheckDefinition, isCustom, isBuiltIn);

        return new ResponseEntity<>(Mono.just(checkModel), HttpStatus.OK);
    }

    /**
     * Creates (adds) a new custom check given the check information (a sensor and a rule pair).
     * @param checkModel Check model to save.
     * @param fullCheckName Full check name.
     * @return Empty response.
     */
    @PostMapping("/checks/{fullCheckName}")
    @ApiOperation(value = "createCheck", notes = "Creates (adds) a new custom check that is a pair of a sensor name and a rule name.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New custom check successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Custom check with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createCheck(
            @ApiParam("Full check name") @PathVariable String fullCheckName,
            @ApiParam("Check model") @RequestBody CheckModel checkModel) {
        if (checkModel == null || Strings.isNullOrEmpty(fullCheckName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        CheckDefinitionList userCheckDefinitionList = userHome.getChecks();
        CheckDefinitionWrapper existingCheckDefinitionWrapper = userCheckDefinitionList.getByObjectName(fullCheckName, true);

        if (existingCheckDefinitionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        CheckDefinitionWrapper checkDefinitionWrapper = userCheckDefinitionList.createAndAddNew(checkModel.getRuleName());
        checkDefinitionWrapper.setSpec(checkModel.toCheckDefinitionSpec());
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates an existing check, creating possibly a custom check.
     * @param checkModel Check definition model.
     * @param fullCheckName Full check name.
     * @return Empty response.
     */
    @PutMapping("/checks/{fullCheckName}")
    @ApiOperation(value = "updateCheck", notes = "Updates an existing check, making a custom check definition if it is not present")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom check successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Check not found"),
            @ApiResponse(code = 409, message = "Cannot change a check definition of a built-in check"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateCheck(
            @ApiParam("List of check definitions") @RequestBody CheckModel checkModel,
            @ApiParam("Full check name") @PathVariable String fullCheckName) {

        if (Strings.isNullOrEmpty(fullCheckName) || checkModel == null) {
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

        if (checkModel.equalsBuiltInCheck(builtinCheckDefinitionWrapper)) {
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
            checkDefinitionWrapper.setSpec(checkModel.toCheckDefinitionSpec());
        }
        else {
            existingUserCheckDefinitionWrapper.setSpec(checkModel.toCheckDefinitionSpec());
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a custom check definition.
     * @param fullCheckName  Full check name.
     * @return Empty response.
     */
    @DeleteMapping("/checks/{fullCheckName}")
    @ApiOperation(value = "deleteCheck", notes = "Deletes a custom check definition")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Custom check definition successfully deleted", response = DqoQueueJobId.class),
            @ApiResponse(code = 404, message = "Custom check not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteCheck(
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
    @GetMapping("/definitions/checks")
    @ApiOperation(value = "getCheckFolderTree", notes = "Returns a tree of all checks available in DQO, both built-in checks and user defined or customized checks.",
            response = CheckBasicFolderModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckBasicFolderModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<CheckBasicFolderModel>> getCheckFolderTree() {
        CheckBasicFolderModel checkBasicFolderModel = createCheckTreeModel();

        return new ResponseEntity<>(Mono.just(checkBasicFolderModel), HttpStatus.OK);
    }

    /**
     * Builds a check tree.
     * @return Check tree.
     */
    @NotNull
    private CheckBasicFolderModel createCheckTreeModel() {
        CheckBasicFolderModel checkBasicFolderModel = new CheckBasicFolderModel();

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

        for (CheckDefinitionWrapper checkDefinitionWrapperUserHome : checkDefinitionWrapperListUserHome) {
            String checkNameUserHome = checkDefinitionWrapperUserHome.getCheckName();
            checkBasicFolderModel.addCheck(checkNameUserHome, true, builtInCheckNames.contains(checkNameUserHome));
        }

        for (CheckDefinitionWrapper checkDefinitionWrapperDqoHome : checkDefinitionWrapperListDqoHome) {
            String checkNameDqoHome = checkDefinitionWrapperDqoHome.getCheckName();
            checkBasicFolderModel.addCheck(checkNameDqoHome, customCheckNames.contains(checkNameDqoHome), true);
        }
        return checkBasicFolderModel;
    }

    /**
     * Returns a flat list of all checks
     * @return List of all checks
     */
    @GetMapping("/checks")
    @ApiOperation(value = "getAllChecks", notes = "Returns a flat list of all checks available in DQO, both built-in checks and user defined or customized checks.",
            response = CheckBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CheckBasicModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Flux<CheckBasicModel>> getAllChecks() {
        CheckBasicFolderModel checkBasicFolderModel = createCheckTreeModel();
        List<CheckBasicModel> allChecks = checkBasicFolderModel.getAllChecks();

        return new ResponseEntity<>(Flux.fromStream(allChecks.stream()), HttpStatus.OK);
    }
}