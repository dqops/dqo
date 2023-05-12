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
import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.metadata.RuleModel;
import ai.dqo.rest.models.metadata.RuleBasicFolderModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import autovalue.shaded.com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * REST api controller to manage the list of rules.
 */
@RestController
@RequestMapping("/api/rules")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Rules", description = "Rule management")
public class RuleController {

    private DqoHomeContextFactory dqoHomeContextFactory;
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param dqoHomeContextFactory  Dqo home context factory.
     * @param userHomeContextFactory User home context factory.
     */
    @Autowired
    public RuleController(DqoHomeContextFactory dqoHomeContextFactory, UserHomeContextFactory userHomeContextFactory) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns the configuration of a rule, first checking if it is a custom rule, then checking if it is a built-in rule.
     * @param fullRuleName Full rule name.
     * @return Model of the rule with specific rule name.
     */
    @GetMapping("/{fullRuleName}")
    @ApiOperation(value = "getRule", notes = "Returns a rule definition", response = RuleModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleModel.class),
            @ApiResponse(code = 404, message = "Rule name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<RuleModel>> getRule(
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
        RuleModel ruleModel = new RuleModel(effectiveRuleDefinition, isCustom, isBuiltIn);

        return new ResponseEntity<>(Mono.just(ruleModel), HttpStatus.OK);
    }

    /**
     * Creates (adds) a new custom rule given sensor information.
     * @param ruleModel List of rule definitions.
     * @param fullRuleName Full rule name.
     * @return Empty response.
     */
    @PostMapping("/{fullRuleName}")
    @ApiOperation(value = "createRule", notes = "Creates (adds) a new custom rule given sensor information.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New custom rule successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Custom rule with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createRule(
            @ApiParam("Full rule name") @PathVariable String fullRuleName,
            @ApiParam("Rule basic model") @RequestBody RuleModel ruleModel) {
        if (ruleModel == null || Strings.isNullOrEmpty(fullRuleName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        RuleDefinitionList ruleDefinitionList = userHome.getRules();
        RuleDefinitionWrapper existingRuleDefinitionWrapper = ruleDefinitionList.getByObjectName(fullRuleName, true);

        if (existingRuleDefinitionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        RuleDefinitionWrapper ruleDefinitionWrapper = ruleDefinitionList.createAndAddNew(ruleModel.getRuleName());
        ruleDefinitionWrapper.setSpec(ruleModel.withRuleDefinitionSpec());
        ruleDefinitionWrapper.setRulePythonModuleContent(ruleModel.withRuleDefinitionPythonModuleContent());
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates an existing rule, creating possibly a custom rule.
     * @param ruleModel Rule definition model.
     * @param fullRuleName Full rule name.
     * @return Empty response.
     */
    @PutMapping("/{fullRuleName}")
    @ApiOperation(value = "updateRule", notes = "Updates an existing rule, making a custom rule definition if it is not present")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom rule successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Rule not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateRule(
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

        if (existingUserRuleDefinitionWrapper == null) {
            RuleDefinitionWrapper ruleDefinitionWrapper = userRuleDefinitionList.createAndAddNew(fullRuleName);
            ruleDefinitionWrapper.setSpec(ruleModel.withRuleDefinitionSpec());
            ruleDefinitionWrapper.setRulePythonModuleContent(ruleModel.withRuleDefinitionPythonModuleContent());
        }
        else {
            existingUserRuleDefinitionWrapper.setSpec(ruleModel.withRuleDefinitionSpec());
            existingUserRuleDefinitionWrapper.setRulePythonModuleContent(ruleModel.withRuleDefinitionPythonModuleContent());
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a custom rule definition.
     * @param fullRuleName  Full rule name.
     * @return Empty response.
     */
    @DeleteMapping("/{fullRuleName}")
    @ApiOperation(value = "deleteRule", notes = "Deletes a custom rule definition")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Custom rule definition successfully deleted", response = DqoQueueJobId.class),
            @ApiResponse(code = 404, message = "Custom rule not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteRule(
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
    @GetMapping()
    @ApiOperation(value = "getRuleFolderTree", notes = "Returns a tree of all rules available in DQO, both built-in rules and user defined or customized rules.",
            response = RuleBasicFolderModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleBasicFolderModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<RuleBasicFolderModel>> getRuleFolderTree() {
        RuleBasicFolderModel ruleBasicFolderModel = new RuleBasicFolderModel();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        List<RuleDefinitionWrapper> ruleDefinitionWrapperListDqoHome = dqoHome.getRules().toList();

        for (RuleDefinitionWrapper ruleDefinitionWrapperDqoHome : ruleDefinitionWrapperListDqoHome) {
            String ruleNameDqoHome = ruleDefinitionWrapperDqoHome.getRuleName();
            ruleBasicFolderModel.addRule(ruleNameDqoHome, false);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<RuleDefinitionWrapper> ruleDefinitionWrapperListUserHome = userHome.getRules().toList();

        for (RuleDefinitionWrapper ruleDefinitionWrapperUserHome : ruleDefinitionWrapperListUserHome) {
            String ruleNameUserHome = ruleDefinitionWrapperUserHome.getRuleName();
            ruleBasicFolderModel.addRule(ruleNameUserHome, true);
        }

        return new ResponseEntity<>(Mono.just(ruleBasicFolderModel), HttpStatus.OK);
    }
}