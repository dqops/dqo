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

import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.RuleFolderModel;
import com.dqops.rest.models.metadata.RuleListModel;
import com.dqops.rest.models.metadata.RuleModel;
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
 * REST api controller to manage the list of rules.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Rules", description = "Rule management")
public class RulesController {
    private DqoHomeContextFactory dqoHomeContextFactory;
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param dqoHomeContextFactory  Dqo home context factory.
     * @param userHomeContextFactory User home context factory.
     */
    @Autowired
    public RulesController(DqoHomeContextFactory dqoHomeContextFactory, UserHomeContextFactory userHomeContextFactory) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a flat list of all rules.
     * @return List of all rules.
     */
    @GetMapping(value = "/rules", produces = "application/json")
    @ApiOperation(value = "getAllRules", notes = "Returns a flat list of all rules available in DQO, both built-in rules and user defined or customized rules.",
            response = RuleListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<RuleListModel>> getAllRules(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        RuleFolderModel ruleFolderModel = createRuleTreeModel(principal);
        List<RuleListModel> allRules = ruleFolderModel.getAllRules();
        allRules.sort(Comparator.comparing(model -> model.getFullRuleName()));

        return new ResponseEntity<>(Flux.fromStream(allRules.stream()), HttpStatus.OK);
    }

    /**
     * Returns the configuration of a rule, first checking if it is a custom rule, then checking if it is a built-in rule.
     * @param fullRuleName Full rule name.
     * @return Model of the rule with specific rule name.
     */
    @GetMapping(value = "/rules/{fullRuleName}", produces = "application/json")
    @ApiOperation(value = "getRule", notes = "Returns a rule definition", response = RuleModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleModel.class),
            @ApiResponse(code = 404, message = "Rule name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<RuleModel>> getRule(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Full rule name") @PathVariable String fullRuleName) {

        if (Strings.isNullOrEmpty(fullRuleName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        RuleDefinitionWrapper userRuleDefinitionWrapper = userHome.getRules().getByObjectName(fullRuleName, true);

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        RuleDefinitionWrapper builtinRuleDefinitionWrapper = dqoHome.getRules().getByObjectName(fullRuleName, true);

        if (userRuleDefinitionWrapper == null && builtinRuleDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        RuleDefinitionWrapper effectiveRuleDefinition = Optional.ofNullable(userRuleDefinitionWrapper).orElse(builtinRuleDefinitionWrapper);

        boolean isCustom = userRuleDefinitionWrapper != null;
        boolean isBuiltIn = builtinRuleDefinitionWrapper != null;
        boolean canEdit = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
        RuleModel ruleModel = new RuleModel(effectiveRuleDefinition, isCustom, isBuiltIn, canEdit);

        return new ResponseEntity<>(Mono.just(ruleModel), HttpStatus.OK);
    }

    /**
     * Creates (adds) a new custom rule given sensor information.
     * @param ruleModel Rule model.
     * @param fullRuleName Full rule name.
     * @return Empty response.
     */
    @PostMapping(value = "/rules/{fullRuleName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createRule", notes = "Creates (adds) a new custom rule given the rule definition.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New custom rule successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Custom rule with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> createRule(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Full rule name") @PathVariable String fullRuleName,
            @ApiParam("Rule model") @RequestBody RuleModel ruleModel) {
        if (ruleModel == null || Strings.isNullOrEmpty(fullRuleName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        RuleDefinitionList userRuleDefinitionList = userHome.getRules();
        RuleDefinitionWrapper existingRuleDefinitionWrapper = userRuleDefinitionList.getByObjectName(fullRuleName, true);

        if (existingRuleDefinitionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        RuleDefinitionWrapper ruleDefinitionWrapper = userRuleDefinitionList.createAndAddNew(fullRuleName);
        ruleDefinitionWrapper.setSpec(ruleModel.toRuleDefinitionSpec());
        ruleDefinitionWrapper.setRulePythonModuleContent(ruleModel.makePythonModuleFileContent());
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates an existing rule, creating possibly a custom rule.
     * @param ruleModel Rule definition model.
     * @param fullRuleName Full rule name.
     * @return Empty response.
     */
    @PutMapping(value = "/rules/{fullRuleName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateRule", notes = "Updates an existing rule, making a custom rule definition if it is not present", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom rule successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Rule not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateRule(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("List of rule definitions") @RequestBody RuleModel ruleModel,
            @ApiParam("Full rule name") @PathVariable String fullRuleName) {

        if (Strings.isNullOrEmpty(fullRuleName) || ruleModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        RuleDefinitionList userRuleDefinitionList = userHome.getRules();
        RuleDefinitionWrapper existingUserRuleDefinitionWrapper = userRuleDefinitionList.getByObjectName(fullRuleName, true);

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        RuleDefinitionWrapper builtinRuleDefinitionWrapper = dqoHome.getRules().getByObjectName(fullRuleName, true);

        if (existingUserRuleDefinitionWrapper == null && builtinRuleDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        if (ruleModel.equalsBuiltInRule(builtinRuleDefinitionWrapper)) {
            if (existingUserRuleDefinitionWrapper != null) {
                existingUserRuleDefinitionWrapper.markForDeletion(); // remove customization
            }
            else {
                // ignore saving
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
            }
        }

        if (existingUserRuleDefinitionWrapper == null) {
            RuleDefinitionWrapper ruleDefinitionWrapper = userRuleDefinitionList.createAndAddNew(fullRuleName);
            ruleDefinitionWrapper.setSpec(ruleModel.toRuleDefinitionSpec());
            ruleDefinitionWrapper.setRulePythonModuleContent(ruleModel.makePythonModuleFileContent());
        }
        else {
            existingUserRuleDefinitionWrapper.setSpec(ruleModel.toRuleDefinitionSpec());
            existingUserRuleDefinitionWrapper.setRulePythonModuleContent(ruleModel.makePythonModuleFileContent());
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a custom rule definition.
     * @param fullRuleName  Full rule name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/rules/{fullRuleName}", produces = "application/json")
    @ApiOperation(value = "deleteRule", notes = "Deletes a custom rule definition", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom rule definition successfully deleted", response = Void.class),
            @ApiResponse(code = 404, message = "Custom rule not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> deleteRule(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Full rule name") @PathVariable String fullRuleName) {

        if (Strings.isNullOrEmpty(fullRuleName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        RuleDefinitionList userRuleDefinitionList = userHome.getRules();
        RuleDefinitionWrapper existingUserRuleDefinitionWrapper = userRuleDefinitionList.getByObjectName(fullRuleName, true);

        if (existingUserRuleDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        existingUserRuleDefinitionWrapper.markForDeletion();
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Returns all combined rule folder model.
     * @return rule basic tree model.
     */
    @GetMapping(value = "/definitions/rules", produces = "application/json")
    @ApiOperation(value = "getRuleFolderTree", notes = "Returns a tree of all rules available in DQO, both built-in rules and user defined or customized rules.",
            response = RuleFolderModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleFolderModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<RuleFolderModel>> getRuleFolderTree(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        RuleFolderModel ruleFolderModel = createRuleTreeModel(principal);

        return new ResponseEntity<>(Mono.just(ruleFolderModel), HttpStatus.OK);
    }

    /**
     * Creates a tree with all rules that are defined.
     * @return A tree with all rules.
     */
    @NotNull
    private RuleFolderModel createRuleTreeModel(DqoUserPrincipal principal) {
        RuleFolderModel ruleFolderModel = new RuleFolderModel();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        List<RuleDefinitionWrapper> ruleDefinitionWrapperListDqoHome = new ArrayList<>(dqoHome.getRules().toList());
        ruleDefinitionWrapperListDqoHome.sort(Comparator.comparing(rw -> rw.getRuleName()));
        Set<String> builtInRuleNames = ruleDefinitionWrapperListDqoHome.stream().map(rw -> rw.getRuleName()).collect(Collectors.toSet());

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        List<RuleDefinitionWrapper> ruleDefinitionWrapperListUserHome = new ArrayList<>(userHome.getRules().toList());
        ruleDefinitionWrapperListUserHome.sort(Comparator.comparing(rw -> rw.getRuleName()));
        Set<String> customRuleNames = ruleDefinitionWrapperListUserHome.stream().map(rw -> rw.getRuleName()).collect(Collectors.toSet());
        boolean canEditRule = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

        for (RuleDefinitionWrapper ruleDefinitionWrapperUserHome : ruleDefinitionWrapperListUserHome) {
            String ruleNameUserHome = ruleDefinitionWrapperUserHome.getRuleName();
            ruleFolderModel.addRule(ruleNameUserHome, true, builtInRuleNames.contains(ruleNameUserHome), canEditRule);
        }

        for (RuleDefinitionWrapper ruleDefinitionWrapperDqoHome : ruleDefinitionWrapperListDqoHome) {
            String ruleNameDqoHome = ruleDefinitionWrapperDqoHome.getRuleName();
            ruleFolderModel.addRule(ruleNameDqoHome, customRuleNames.contains(ruleNameDqoHome), true, canEditRule);
        }
        return ruleFolderModel;
    }
}