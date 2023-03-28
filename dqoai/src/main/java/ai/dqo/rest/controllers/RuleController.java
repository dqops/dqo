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

import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.metadata.RuleBasicModel;
import ai.dqo.rest.models.metadata.RuleBasicTreeModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.services.rule.RuleMappingService;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
    private RuleMappingService ruleMappingService;


    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param dqoHomeContextFactory  Dqo home context factory.
     * @param userHomeContextFactory User home context factory.
     * @param ruleMappingService Rule mapper service.
     */
    @Autowired
    public RuleController(DqoHomeContextFactory dqoHomeContextFactory, UserHomeContextFactory userHomeContextFactory,
                          RuleMappingService ruleMappingService) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.userHomeContextFactory = userHomeContextFactory;
        this.ruleMappingService = ruleMappingService;
    }

    /**
     * Returns a list of builtin rules.
     * @return List of rules model.
     */
    @GetMapping("/builtin")
    @ApiOperation(value = "getAllBuiltInRules", notes = "Returns a list of builtin rules", response = RuleBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleBasicModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<RuleBasicModel>> getAllBuiltInRules() {

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        RuleDefinitionList ruleDefinitionList = dqoHome.getRules();

        List<RuleDefinitionWrapper> ruleDefinitionWrapperList = ruleDefinitionList.toList();

        Stream<RuleBasicModel> ruleBasicModelStream = ruleDefinitionWrapperList.stream()
                .map(ruleMappingService::toRuleBasicModel);

        return new ResponseEntity<>(Flux.fromStream(ruleBasicModelStream), HttpStatus.OK);
    }

    /**
     * Returns the configuration of a builtin rule.
     * @param ruleName Rule name.
     * @return Model of the rule with specific rule name.
     */
    @GetMapping("/builtin/{ruleName}")
    @ApiOperation(value = "getBuiltInRule", notes = "Returns a builtin rule", response = RuleBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleBasicModel.class),
            @ApiResponse(code = 404, message = "Rule name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<RuleBasicModel>> getBuiltInRule(
            @ApiParam("Rule name") @PathVariable String ruleName
    ) {

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        RuleDefinitionList ruleDefinitionList = dqoHome.getRules();
        RuleDefinitionWrapper ruleDefinitionWrapper = ruleDefinitionList.getByObjectName(ruleName, true);

        if (ruleDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        RuleBasicModel ruleModel = ruleMappingService.toRuleBasicModel(ruleDefinitionWrapper);

        return new ResponseEntity<>(Mono.just(ruleModel), HttpStatus.OK);
    }

    /**
     * Returns a list of custom rules.
     * @return List of rules model.
     */
    @GetMapping("/custom")
    @ApiOperation(value = "getAllCustomInRules", notes = "Returns a list of custom rules", response = RuleBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleBasicModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<RuleBasicModel>> getAllCustomInRules() {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        RuleDefinitionList ruleDefinitionList = userHome.getRules();
        Stream<RuleBasicModel> ruleBasicModelStream = ruleDefinitionList.toList().stream()
                .map(ruleMappingService::toRuleBasicModel);

        return new ResponseEntity<>(Flux.fromStream(ruleBasicModelStream), HttpStatus.OK);
    }

    /**
     * Returns the configuration of a custom rule.
     * @param ruleName Rule name.
     * @return Model of the rule with specific rule name.
     */
    @GetMapping("/custom/{ruleName}")
    @ApiOperation(value = "getCustomRule", notes = "Returns a custom rule", response = RuleBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleBasicModel.class),
            @ApiResponse(code = 404, message = "Rule name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<RuleBasicModel>> getCustomRule(
            @ApiParam("Rule name") @PathVariable String ruleName
    ) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        RuleDefinitionList ruleDefinitionList = userHome.getRules();
        RuleDefinitionWrapper ruleDefinitionWrapper = ruleDefinitionList.getByObjectName(ruleName, true);

        if (ruleDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        RuleBasicModel ruleModel = ruleMappingService.toRuleBasicModel(ruleDefinitionWrapper);

        return new ResponseEntity<>(Mono.just(ruleModel), HttpStatus.OK);
    }


    /**
     * Creates (adds) a new custom rule given sensor information.
     * @param ruleName  Rule name.
     * @param ruleBasicModel List of rule definitions.
     * @return Empty response.
     */
    @PostMapping("/custom/{ruleName}")
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
            @ApiParam("Rule name") @PathVariable String ruleName,
            @ApiParam("Rule basic model") @RequestBody RuleBasicModel ruleBasicModel) {
        if (Strings.isNullOrEmpty(ruleName) || ruleBasicModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        RuleDefinitionList ruleDefinitionList = userHome.getRules();

        RuleDefinitionWrapper existingRuleDefinitionWrapper = ruleDefinitionList.getByObjectName(ruleName, true);
        if (existingRuleDefinitionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        RuleDefinitionWrapper ruleDefinitionWrapper = ruleDefinitionList.createAndAddNew(ruleBasicModel.getRuleName());
        ruleDefinitionWrapper.setSpec(ruleMappingService.withRuleDefinitionSpec(ruleBasicModel));
        ruleDefinitionWrapper.setRulePythonModuleContent(ruleMappingService.withRuleDefinitionPythonModuleContent(ruleBasicModel));
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates an existing custom rule.
     * @param ruleName  Rule name.
     * @param ruleBasicModel List of rule definitions.
     * @return Empty response.
     */
    @PutMapping("/custom/{ruleName}")
    @ApiOperation(value = "updateCustomRule", notes = "Updates an existing custom rule")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom rule successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Rule not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateCustomRule(
            @ApiParam("Rule name") @PathVariable String ruleName,
            @ApiParam("List of rule definitions") @RequestBody RuleBasicModel ruleBasicModel) {
        if (Strings.isNullOrEmpty(ruleName) || ruleBasicModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        RuleDefinitionList ruleDefinitionList = userHome.getRules();

        RuleDefinitionWrapper existingRuleDefinitionWrapper = ruleDefinitionList.getByObjectName(ruleName, true);
        if (existingRuleDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        existingRuleDefinitionWrapper.setSpec(ruleMappingService.withRuleDefinitionSpec(ruleBasicModel));
        existingRuleDefinitionWrapper.setRulePythonModuleContent(ruleMappingService.withRuleDefinitionPythonModuleContent(ruleBasicModel));

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Returns a list of combined rules. If the same rule is defined both as custom (in user home)
     * and as builtin (in dqo home), we return the custom definition.
     * @return List of combined rules model.
     */
    @GetMapping("/combined")
    @ApiOperation(value = "getAllCombinedRules", notes = "Returns a list of combined rules", response = RuleBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleBasicModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<RuleBasicModel>> getAllCombinedRules() {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        RuleDefinitionList customRuleDefinitionList = userHome.getRules();

        List<RuleDefinitionWrapper> customRuleDefinitionWrapperList = customRuleDefinitionList.toList();


        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        RuleDefinitionList ruleDefinitionList = dqoHome.getRules();

        List<RuleDefinitionWrapper> ruleDefinitionWrapperList = ruleDefinitionList.toList();


        Map<String, RuleDefinitionWrapper> ruleDefinitionMap = new HashMap<>();


        for (RuleDefinitionWrapper ruleDefinitionWrapper: customRuleDefinitionWrapperList) {
            ruleDefinitionMap.put(ruleDefinitionWrapper.getRuleName(), ruleDefinitionWrapper);
        }

        for (RuleDefinitionWrapper ruleDefinitionWrapper: ruleDefinitionWrapperList) {
            if(!ruleDefinitionMap.containsKey(ruleDefinitionWrapper.getRuleName())){
                ruleDefinitionMap.put(ruleDefinitionWrapper.getRuleName(), ruleDefinitionWrapper);
            }
        }

        Stream<RuleBasicModel> ruleModelStream = ruleDefinitionMap.values().stream().map(ruleMappingService::toRuleBasicModel);


        return new ResponseEntity<>(Flux.fromStream(ruleModelStream), HttpStatus.OK);
    }

    /**
     * Returns a combined rule. Check if the rule definition exists in User home.
     * If not exists, find rule definition in DQO Home.
     * @return Combined rule model.
     */
    @GetMapping("/combined/{ruleName}")
    @ApiOperation(value = "getCombinedRule", notes = "Returns a list of combined rules", response = RuleBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleBasicModel.class),
            @ApiResponse(code = 404, message = "Rule name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<RuleBasicModel>> getCombinedRule(
            @ApiParam("Rule name") @PathVariable String ruleName
    ) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        RuleDefinitionList customRuleDefinitionList = userHome.getRules();

        RuleDefinitionWrapper ruleDefinitionWrapper = customRuleDefinitionList.getByObjectName(ruleName, true);

        if(ruleDefinitionWrapper == null){
            DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
            DqoHome dqoHome = dqoHomeContext.getDqoHome();
            RuleDefinitionList ruleDefinitionList = dqoHome.getRules();
            ruleDefinitionWrapper =  ruleDefinitionList.getByObjectName(ruleName, true);
        }

        if(ruleDefinitionWrapper == null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        RuleBasicModel ruleModel = ruleMappingService.toRuleBasicModel(ruleDefinitionWrapper);

        return new ResponseEntity<>(Mono.just(ruleModel), HttpStatus.OK);
    }


    /**
     * Returns all combined rule basic tree model.
     * @return rule basic tree model.
     */
    @GetMapping("/combinedbasictree")
    @ApiOperation(value = "getAllRulesBasicTree", notes = "Returns a list of combined basic tree rules", response = RuleBasicTreeModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RuleBasicTreeModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<RuleBasicTreeModel>> getAllRulesBasicTree() {

        RuleBasicTreeModel ruleBasicTreeModel = new RuleBasicTreeModel();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        List<RuleDefinitionWrapper> ruleDefinitionWrapperListDqoHome = dqoHome.getRules().toList();

        for (RuleDefinitionWrapper ruleDefinitionWrapperDqoHome : ruleDefinitionWrapperListDqoHome) {
            String ruleNameDqoHome = ruleDefinitionWrapperDqoHome.getRuleName();
            ruleBasicTreeModel.addChild(ruleNameDqoHome, false);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<RuleDefinitionWrapper> ruleDefinitionWrapperListUserHome = userHome.getRules().toList();

        for (RuleDefinitionWrapper ruleDefinitionWrapperUserHome : ruleDefinitionWrapperListUserHome) {
            String ruleNameUserHome = ruleDefinitionWrapperUserHome.getRuleName();
            ruleBasicTreeModel.addChild(ruleNameUserHome, true);
        }

        return new ResponseEntity<>(Mono.just(ruleBasicTreeModel), HttpStatus.OK);
    }

}