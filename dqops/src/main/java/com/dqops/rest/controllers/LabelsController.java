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

import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.labels.labelcontainers.GlobalLabelsContainer;
import com.dqops.metadata.labels.labelcontainers.LabelCountContainer;
import com.dqops.metadata.labels.labelcontainers.LabelCounter;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.dqops.rest.models.metadata.LabelModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * REST API controller that returns all labels that are assigned to data assets. Labels serve the purpose of a lazy business glossary.
 */
@RestController
@RequestMapping("/api/labels")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Labels", description = "Operations that returns all labels that are assigned to data assets. Labels serve the purpose of a lazy business glossary.")
public class LabelsController {
    /**
     * The limit of results when paging was not applied.
     */
    public static final Integer MAX_RESULTS = 1000;

    private final GlobalLabelsContainer globalLabelsContainer;

    /**
     * Default constructor that receives dependencies from IoC.
     * @param globalLabelsContainer Global container of all found labels for each data domain.
     */
    @Autowired
    public LabelsController(GlobalLabelsContainer globalLabelsContainer) {
        this.globalLabelsContainer = globalLabelsContainer;
    }

    /**
     * Returns a list of all labels applied to the connections to data sources, including the count of assignments to these data assets.
     * @return List of all labels assigned to connections.
     */
    @GetMapping(value = "/connections", produces = "application/json")
    @ApiOperation(value = "getAllLabelsForConnections", notes = "Returns a list of all labels applied to the connections to data sources, including the count of assignments to these data assets.", response = LabelModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LabelModel[].class),
            @ApiResponse(code = 404, message = "Label prefix not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<LabelModel>>> getAllLabelsForConnections(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam(name = "page", value = "Page number, the first page is 1", required = false)
            @RequestParam(required = false) Optional<Integer> page,
            @ApiParam(name = "limit", value = "Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided", required = false)
            @RequestParam(required = false) Optional<Integer> limit,
            @ApiParam(name = "prefix", value = "Optional filter for the prefix of labels. For example, when the prefix is \"address\", this operation will return labels \"address/city\" and \"address/country\", but not \"address\".", required = false)
            @RequestParam(required = false) Optional<String> prefix,
            @ApiParam(name = "filter", value = "Optional table name filter", required = false)
            @RequestParam(required = false) Optional<String> filter) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            Integer resultsLimit = MAX_RESULTS;
            Integer skip = null;
            if (page.isPresent() || limit.isPresent()) {
                if (page.isPresent() && page.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }
                if (limit.isPresent() && limit.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }

                Integer pageValue = page.orElse(1);
                Integer limitValue = limit.orElse(100);
                resultsLimit = pageValue * limitValue;
                skip = (pageValue - 1) * limitValue;
            }

            LabelCountContainer labelsContainer = this.globalLabelsContainer.getConnectionLabels(principal.getDataDomainIdentity().getDataDomainCloud());
            List<LabelCounter> labelCountersList = null;
            if (prefix.isPresent() && !Strings.isNullOrEmpty(prefix.get())) {
                LabelCounter labelCounter = labelsContainer.getLabelCounter(prefix.get());
                if (labelCounter == null) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
                } else {
                    labelCountersList = labelCounter.getNestedLabels();
                    if (labelCountersList == null) {
                        return new ResponseEntity<>(Flux.empty(), HttpStatus.OK); // OK, but empty
                    }
                }
            } else {
                labelCountersList = labelsContainer.getLabels();
            }

            SearchPattern labelSearchPattern = filter.isPresent() && !Strings.isNullOrEmpty(filter.get()) ?
                    SearchPattern.create(true, filter.get().contains("*") ? filter.get() : "*" + filter.get() + "*") : null;

            List<LabelModel> labelModels = labelCountersList
                    .stream()
                    .filter(labelCounter -> {
                        if (labelSearchPattern != null) {
                            return labelSearchPattern.match(labelCounter.getLabel());
                        }
                        return true;
                    })
                    .limit(resultsLimit)
                    .skip(skip == null ? 0 : skip)
                    .map(LabelModel::fromLabelCounter)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(Flux.fromStream(labelModels.stream()), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns a list of all labels applied to tables, including the count of assignments to these data assets.
     * @return List of all labels assigned to tables.
     */
    @GetMapping(value = "/tables", produces = "application/json")
    @ApiOperation(value = "getAllLabelsForTables", notes = "Returns a list of all labels applied to tables, including the count of assignments to these data assets.", response = LabelModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LabelModel[].class),
            @ApiResponse(code = 404, message = "Label prefix not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<LabelModel>>> getAllLabelsForTables(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam(name = "page", value = "Page number, the first page is 1", required = false)
            @RequestParam(required = false) Optional<Integer> page,
            @ApiParam(name = "limit", value = "Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided", required = false)
            @RequestParam(required = false) Optional<Integer> limit,
            @ApiParam(name = "prefix", value = "Optional filter for the prefix of labels. For example, when the prefix is \"address\", this operation will return labels \"address/city\" and \"address/country\", but not \"address\".", required = false)
            @RequestParam(required = false) Optional<String> prefix,
            @ApiParam(name = "filter", value = "Optional table name filter", required = false)
            @RequestParam(required = false) Optional<String> filter) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            Integer resultsLimit = MAX_RESULTS;
            Integer skip = null;
            if (page.isPresent() || limit.isPresent()) {
                if (page.isPresent() && page.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }
                if (limit.isPresent() && limit.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }

                Integer pageValue = page.orElse(1);
                Integer limitValue = limit.orElse(100);
                resultsLimit = pageValue * limitValue;
                skip = (pageValue - 1) * limitValue;
            }

            LabelCountContainer labelsContainer = this.globalLabelsContainer.getTableLabels(principal.getDataDomainIdentity().getDataDomainCloud());
            List<LabelCounter> labelCountersList = null;
            if (prefix.isPresent() && !Strings.isNullOrEmpty(prefix.get())) {
                LabelCounter labelCounter = labelsContainer.getLabelCounter(prefix.get());
                if (labelCounter == null) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
                } else {
                    labelCountersList = labelCounter.getNestedLabels();
                    if (labelCountersList == null) {
                        return new ResponseEntity<>(Flux.empty(), HttpStatus.OK); // OK, but empty
                    }
                }
            } else {
                labelCountersList = labelsContainer.getLabels();
            }

            SearchPattern labelSearchPattern = filter.isPresent() && !Strings.isNullOrEmpty(filter.get()) ?
                    SearchPattern.create(true, filter.get().contains("*") ? filter.get() : "*" + filter.get() + "*") : null;

            List<LabelModel> labelModels = labelCountersList
                    .stream()
                    .filter(labelCounter -> {
                        if (labelSearchPattern != null) {
                            return labelSearchPattern.match(labelCounter.getLabel());
                        }
                        return true;
                    })
                    .limit(resultsLimit)
                    .skip(skip == null ? 0 : skip)
                    .map(LabelModel::fromLabelCounter)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(Flux.fromStream(labelModels.stream()), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns a list of all labels applied to columns, including the count of assignments to these data assets.
     * @return List of all labels assigned to columns.
     */
    @GetMapping(value = "/columns", produces = "application/json")
    @ApiOperation(value = "getAllLabelsForColumns", notes = "Returns a list of all labels applied to columns, including the count of assignments to these data assets.", response = LabelModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LabelModel[].class),
            @ApiResponse(code = 404, message = "Label prefix not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<LabelModel>>> getAllLabelsForColumns(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam(name = "page", value = "Page number, the first page is 1", required = false)
            @RequestParam(required = false) Optional<Integer> page,
            @ApiParam(name = "limit", value = "Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided", required = false)
            @RequestParam(required = false) Optional<Integer> limit,
            @ApiParam(name = "prefix", value = "Optional filter for the prefix of labels. For example, when the prefix is \"address\", this operation will return labels \"address/city\" and \"address/country\", but not \"address\".", required = false)
            @RequestParam(required = false) Optional<String> prefix,
            @ApiParam(name = "filter", value = "Optional table name filter", required = false)
            @RequestParam(required = false) Optional<String> filter) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            Integer resultsLimit = MAX_RESULTS;
            Integer skip = null;
            if (page.isPresent() || limit.isPresent()) {
                if (page.isPresent() && page.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }
                if (limit.isPresent() && limit.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }

                Integer pageValue = page.orElse(1);
                Integer limitValue = limit.orElse(100);
                resultsLimit = pageValue * limitValue;
                skip = (pageValue - 1) * limitValue;
            }

            LabelCountContainer labelsContainer = this.globalLabelsContainer.getColumnLabels(principal.getDataDomainIdentity().getDataDomainCloud());
            List<LabelCounter> labelCountersList = null;
            if (prefix.isPresent() && !Strings.isNullOrEmpty(prefix.get())) {
                LabelCounter labelCounter = labelsContainer.getLabelCounter(prefix.get());
                if (labelCounter == null) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
                } else {
                    labelCountersList = labelCounter.getNestedLabels();
                    if (labelCountersList == null) {
                        return new ResponseEntity<>(Flux.empty(), HttpStatus.OK); // OK, but empty
                    }
                }
            } else {
                labelCountersList = labelsContainer.getLabels();
            }

            SearchPattern labelSearchPattern = filter.isPresent() && !Strings.isNullOrEmpty(filter.get()) ?
                    SearchPattern.create(true, filter.get().contains("*") ? filter.get() : "*" + filter.get() + "*") : null;

            List<LabelModel> labelModels = labelCountersList
                    .stream()
                    .filter(labelCounter -> {
                        if (labelSearchPattern != null) {
                            return labelSearchPattern.match(labelCounter.getLabel());
                        }
                        return true;
                    })
                    .limit(resultsLimit)
                    .skip(skip == null ? 0 : skip)
                    .map(LabelModel::fromLabelCounter)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(Flux.fromStream(labelModels.stream()), HttpStatus.OK); // 200
        }));
    }
}
