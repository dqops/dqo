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
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternList;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.*;
import com.dqops.rest.models.platform.SpringErrorPayload;
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
 * REST api controller to manage the list of default table-level check patterns.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "DefaultTableCheckPatterns", description = "Operations for managing the configuration of the default table-level checks for tables matching a pattern.")
public class DefaultTableCheckPatternsController {
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param userHomeContextFactory User home context factory.
     */
    @Autowired
    public DefaultTableCheckPatternsController( UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a flat list of all default check templates.
     * @return List of all default check templates.
     */
    @GetMapping(value = "/default/checks/table", produces = "application/json")
    @ApiOperation(value = "getAllDefaultTableChecksPatterns", notes = "Returns a flat list of all table-level default check patterns configured for this instance. Default checks are applied on tables dynamically.",
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
    public ResponseEntity<Flux<DefaultTableChecksPatternListModel>> getAllDefaultTableChecksPatterns(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();
        TableDefaultChecksPatternList defaultChecksPatternsList = userHome.getTableDefaultChecksPatterns();
        List<TableDefaultChecksPatternWrapper> patternWrappersList = defaultChecksPatternsList.toList();
        boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

        List<DefaultTableChecksPatternListModel> models = patternWrappersList.stream()
                .map(pw -> DefaultTableChecksPatternListModel.fromPatternSpecification(pw.getSpec(), canEdit))
                .collect(Collectors.toList());
        models.sort(Comparator.comparing(model -> model.getPatternName()));

        return new ResponseEntity<>(Flux.fromStream(models.stream()), HttpStatus.OK);
    }

    /**
     * Returns the configuration of a default checks pattern.
     * @param patternName Pattern name.
     * @return Model of the default checks pattern.
     */
    @GetMapping(value = "/default/checks/table/{patternName}", produces = "application/json")
    @ApiOperation(value = "getDefaultTableChecksPattern", notes = "Returns a default checks pattern definition", response = DefaultTableChecksPatternModel.class,
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
    public ResponseEntity<Mono<DefaultTableChecksPatternModel>> getDefaultTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Table pattern name") @PathVariable String patternName) {

        if (Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
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
    }

    /**
     * Creates (adds) a new default table-level checks pattern configuration.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PostMapping(value = "/default/checks/table/{patternName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createDefaultTableChecksPattern", notes = "Creates (adds) a new default table-level checks pattern configuration.", response = Void.class,
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
    public ResponseEntity<Mono<Void>> createDefaultTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName,
            @ApiParam("Default checks pattern model") @RequestBody DefaultTableChecksPatternModel patternModel) {
        if (patternModel == null || Strings.isNullOrEmpty(patternName) || patternModel.getPatternSpec() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
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
    }

    /**
     * Updates an existing default table-level checks pattern, creating possibly a new checks pattern configuration.
     * @param patternModel Default checks pattern model.
     * @param patternName Pattern name.
     * @return Empty response.
     */
    @PutMapping(value = "/default/checks/table/{patternName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultTableChecksPattern", notes = "Updates an default table-level checks pattern", response = Void.class,
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
    public ResponseEntity<Mono<Void>> updateDefaultTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Default checks pattern model") @RequestBody DefaultTableChecksPatternModel patternModel,
            @ApiParam("Pattern name") @PathVariable String patternName) {

        if (Strings.isNullOrEmpty(patternName) || patternModel == null || patternModel.getPatternSpec() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
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
    public ResponseEntity<Mono<Void>> deleteDefaultTableChecksPattern(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Pattern name") @PathVariable String patternName) {

        if (Strings.isNullOrEmpty(patternName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();

        TableDefaultChecksPatternList defaultChecksPatternsList = userHome.getTableDefaultChecksPatterns();
        TableDefaultChecksPatternWrapper existingDefaultChecksPatternWrapper = defaultChecksPatternsList.getByObjectName(patternName, true);

        if (existingDefaultChecksPatternWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        existingDefaultChecksPatternWrapper.markForDeletion();
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }
}