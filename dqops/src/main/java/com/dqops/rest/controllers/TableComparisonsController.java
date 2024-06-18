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

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpecMap;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.comparison.TableComparisonConfigurationModel;
import com.dqops.rest.models.comparison.TableComparisonModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.locking.RestApiLockService;
import com.google.common.base.Strings;
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
import java.util.concurrent.CompletableFuture;

/**
 * REST api controller to manage the configuration of table comparisons between data sources.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "TableComparisons", description = "Operations for managing the configurations of table comparisons between tables on the same or different data sources")
public class TableComparisonsController {
    private final UserHomeContextFactory userHomeContextFactory;
    private final HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private final RestApiLockService lockService;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param userHomeContextFactory      User home context factory.
     * @param hierarchyNodeTreeSearcher   Node searcher.
     * @param lockService                 Object lock service.
     */
    @Autowired
    public TableComparisonsController(UserHomeContextFactory userHomeContextFactory,
                                      HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                      RestApiLockService lockService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.lockService = lockService;
    }

    /**
     * Returns a list of table comparison configurations on a table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return List of reference tables.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations", produces = "application/json")
    @ApiOperation(value = "getTableComparisonConfigurations", notes = "Returns the list of table comparison configurations on a compared table",
            response = TableComparisonConfigurationModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<TableComparisonConfigurationModel>>> getTableComparisonConfigurations(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "checkType", value = "Optional check type filter (profiling, monitoring, partitioned).", required = false)
            @RequestParam(required = false) Optional<CheckType> checkType,
            @ApiParam(name = "checkTimeScale", value = "Optional time scale filter for table comparisons specific to the monitoring and partitioned checks (values: daily or monthly).", required = false)
            @RequestParam(required = false) Optional<CheckTimeScale> checkTimeScale) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
            if (tableComparisonConfigurationSpecMap == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<TableComparisonConfigurationModel> result = new LinkedList<>();
            boolean canCompareTables = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

            for (TableComparisonConfigurationSpec tableComparisonConfigurationSpec : tableComparisonConfigurationSpecMap.values()) {
                if (checkType.isPresent() && checkType.get() != tableComparisonConfigurationSpec.getCheckType()) {
                    continue;
                }

                if (checkTimeScale.isPresent() && checkTimeScale.get() != tableComparisonConfigurationSpec.getTimeScale()) {
                    continue;
                }

                TableComparisonConfigurationModel tableComparisonConfigurationModel =
                        TableComparisonConfigurationModel.fromTableComparisonSpec(tableComparisonConfigurationSpec, canCompareTables);
                result.add(tableComparisonConfigurationModel);
            }

            return new ResponseEntity<>(Flux.fromIterable(result), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns the configuration of a table comparison.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Table comparison model.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}", produces = "application/json")
    @ApiOperation(value = "getTableComparisonConfiguration", notes = "Returns a model of the table comparison configuration",
            response = TableComparisonConfigurationModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonConfigurationModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableComparisonConfigurationModel>>> getTableComparisonConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Reference table configuration name") @PathVariable String tableComparisonConfigurationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
            if (tableComparisonConfigurationSpecMap == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableComparisonConfigurationSpec tableComparisonConfigurationSpec = tableComparisonConfigurationSpecMap.get(tableComparisonConfigurationName);
            if (tableComparisonConfigurationSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableComparisonConfigurationModel tableComparisonConfigurationModel = TableComparisonConfigurationModel.fromTableComparisonSpec(
                    tableComparisonConfigurationSpec, principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            return new ResponseEntity<>(Mono.just(tableComparisonConfigurationModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Update a specific table comparison configuration using a new model.
     * Remark: PUT method is used, because renaming the table comparison configuration would break idempotence.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationName  Table comparison configuration name up until now.
     * @param tableComparisonConfigurationModel Table comparison configuration model.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}",
            consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonConfiguration", notes = "Updates a table configuration configuration", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison configuration successfully updated", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison on the table not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 409, message = "Table comparison configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableComparisonConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison model with the configuration of the tables to compare")
                @RequestBody TableComparisonConfigurationModel tableComparisonConfigurationModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName)     ||
                    Strings.isNullOrEmpty(schemaName)     ||
                    Strings.isNullOrEmpty(tableName)      ||
                    Strings.isNullOrEmpty(tableComparisonConfigurationName) ||
                    tableComparisonConfigurationModel == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
                        if (tableComparisonConfigurationSpecMap == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        TableComparisonConfigurationSpec tableComparisonConfigurationSpec = tableComparisonConfigurationSpecMap.get(tableComparisonConfigurationName);
                        if (tableComparisonConfigurationSpec == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        String newName = tableComparisonConfigurationModel.getTableComparisonConfigurationName();
                        if (Strings.isNullOrEmpty(newName)) {
                            newName = tableComparisonConfigurationName;
                        }

                        if (!Objects.equals(newName, tableComparisonConfigurationName) && tableComparisonConfigurationSpecMap.containsKey(newName)) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a reference table configuration with this name already exists
                        }

                        if (!newName.equals(tableComparisonConfigurationName)) {
                            // If renaming actually happened.
                            tableComparisonConfigurationSpecMap.remove(tableComparisonConfigurationName);
                            tableComparisonConfigurationSpec.setHierarchyId(null);
                            tableComparisonConfigurationSpecMap.put(newName, tableComparisonConfigurationSpec);
                        }

                        tableComparisonConfigurationModel.copyToTableComparisonSpec(tableComparisonConfigurationSpec);

                        userHomeContext.flush();
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Creates (adds) a new named reference table comparison configuration.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationModel Table comparison configuration model.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableComparisonConfiguration", notes = "Creates a new table comparison configuration added to the compared table", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New table comparison configuration successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Table comparison configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> createTableComparisonConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration model") @RequestBody TableComparisonConfigurationModel tableComparisonConfigurationModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName)     ||
                    Strings.isNullOrEmpty(schemaName)     ||
                    Strings.isNullOrEmpty(tableName)      ||
                    tableComparisonConfigurationModel == null               ||
                    Strings.isNullOrEmpty(tableComparisonConfigurationModel.getTableComparisonConfigurationName())) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
                        if (tableSpec == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        if (tableSpec.getTableComparisons().containsKey(tableComparisonConfigurationModel.getTableComparisonConfigurationName())) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a table comparison configuration with this name already exists
                        }

                        TableComparisonConfigurationSpec tableComparisonConfigurationSpec = new TableComparisonConfigurationSpec();
                        tableComparisonConfigurationModel.copyToTableComparisonSpec(tableComparisonConfigurationSpec);
                        tableSpec.getTableComparisons().put(tableComparisonConfigurationModel.getTableComparisonConfigurationName(), tableComparisonConfigurationSpec);

                        userHomeContext.flush();
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
                    });
        }));
    }

    /**
     * Deletes a specific table comparison configuration. Deactivates also all configured comparison checks.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}", produces = "application/json")
    @ApiOperation(value = "deleteTableComparisonConfiguration", notes = "Deletes a table comparison configuration from a compared table", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison configuration removed", response = Void.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 406, message = "Invalid request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> deleteTableComparisonConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Reference table configuration name") @PathVariable String tableComparisonConfigurationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName)     ||
                    Strings.isNullOrEmpty(schemaName)     ||
                    Strings.isNullOrEmpty(tableName)      ||
                    Strings.isNullOrEmpty(tableComparisonConfigurationName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
                        if (tableSpec == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = tableSpec.getTableComparisons();
                        if (tableComparisonConfigurationSpecMap == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        // If reference table configuration is not found, return success (idempotence).
                        tableComparisonConfigurationSpecMap.remove(tableComparisonConfigurationName);

                        Collection<AbstractComparisonCheckCategorySpecMap<?>> comparisonCheckCategoryMaps = this.hierarchyNodeTreeSearcher.findComparisonCheckCategoryMaps(tableSpec);
                        for (AbstractComparisonCheckCategorySpecMap<?> comparisonCheckCategorySpecMap : comparisonCheckCategoryMaps) {
                            comparisonCheckCategorySpecMap.remove(tableComparisonConfigurationName);
                        }

                        userHomeContext.flush();
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Returns the table comparison in the profiling check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Model of the table comparison using profiling checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/profiling", produces = "application/json")
    @ApiOperation(value = "getTableComparisonProfiling", notes = "Returns a model of the table comparison using profiling checks (comparison at any time)",
            response = TableComparisonModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableComparisonModel>>> getTableComparisonProfiling(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableComparisonConfigurationSpec referenceTableConfigurationSpec = tableSpec.getTableComparisons().get(tableComparisonConfigurationName);
            if (referenceTableConfigurationSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec referencedTableSpec = this.readTableSpec(userHomeContext,
                    referenceTableConfigurationSpec.getReferenceTableConnectionName(),
                    referenceTableConfigurationSpec.getReferenceTableSchemaName(),
                    referenceTableConfigurationSpec.getReferenceTableName());

            TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec,
                    tableComparisonConfigurationName, CheckType.profiling, null,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns the table comparison in the daily monitoring check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Model of the table comparison using daily monitoring checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/monitoring/daily", produces = "application/json")
    @ApiOperation(value = "getTableComparisonMonitoringDaily", notes = "Returns a model of the table comparison using daily monitoring checks (comparison once a day)",
            response = TableComparisonModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableComparisonModel>>> getTableComparisonMonitoringDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableComparisonConfigurationSpec referenceTableConfigurationSpec = tableSpec.getTableComparisons().get(tableComparisonConfigurationName);
            if (referenceTableConfigurationSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec referencedTableSpec = this.readTableSpec(userHomeContext,
                    referenceTableConfigurationSpec.getReferenceTableConnectionName(),
                    referenceTableConfigurationSpec.getReferenceTableSchemaName(),
                    referenceTableConfigurationSpec.getReferenceTableName());

            TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec,
                    tableComparisonConfigurationName, CheckType.monitoring, CheckTimeScale.daily,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns the table comparison in the monthly monitoring check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Model of the table comparison using monthly monitoring checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/monitoring/monthly", produces = "application/json")
    @ApiOperation(value = "getTableComparisonMonitoringMonthly", notes = "Returns a model of the table comparison using monthly monitoring checks (comparison once a month)",
            response = TableComparisonModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableComparisonModel>>> getTableComparisonMonitoringMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableComparisonConfigurationSpec referenceTableConfigurationSpec = tableSpec.getTableComparisons().get(tableComparisonConfigurationName);
            if (referenceTableConfigurationSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec referencedTableSpec = this.readTableSpec(userHomeContext,
                    referenceTableConfigurationSpec.getReferenceTableConnectionName(),
                    referenceTableConfigurationSpec.getReferenceTableSchemaName(),
                    referenceTableConfigurationSpec.getReferenceTableName());

            TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec,
                    tableComparisonConfigurationName, CheckType.monitoring, CheckTimeScale.monthly,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns the table comparison in the daily partitioned check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Reference table configuration name.
     * @return Model of the table comparison using daily partitioned checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/partitioned/daily", produces = "application/json")
    @ApiOperation(value = "getTableComparisonPartitionedDaily", notes = "Returns a model of the table comparison using daily partition checks (comparing day to day)",
            response = TableComparisonModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableComparisonModel>>> getTableComparisonPartitionedDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableComparisonConfigurationSpec referenceTableConfigurationSpec = tableSpec.getTableComparisons().get(tableComparisonConfigurationName);
            if (referenceTableConfigurationSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec referencedTableSpec = this.readTableSpec(userHomeContext,
                    referenceTableConfigurationSpec.getReferenceTableConnectionName(),
                    referenceTableConfigurationSpec.getReferenceTableSchemaName(),
                    referenceTableConfigurationSpec.getReferenceTableName());

            TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec,
                    tableComparisonConfigurationName, CheckType.partitioned, CheckTimeScale.daily,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns the table comparison in the monthly partitioned check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Model of the table comparison using monthly partitioned checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/partitioned/monthly", produces = "application/json")
    @ApiOperation(value = "getTableComparisonPartitionedMonthly", notes = "Returns a model of the table comparison using monthly partition checks (comparing month to month)",
            response = TableComparisonModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableComparisonModel>>> getTableComparisonPartitionedMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableComparisonConfigurationSpec referenceTableConfigurationSpec = tableSpec.getTableComparisons().get(tableComparisonConfigurationName);
            if (referenceTableConfigurationSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec referencedTableSpec = this.readTableSpec(userHomeContext,
                    referenceTableConfigurationSpec.getReferenceTableConnectionName(),
                    referenceTableConfigurationSpec.getReferenceTableSchemaName(),
                    referenceTableConfigurationSpec.getReferenceTableName());

            TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec,
                    tableComparisonConfigurationName, CheckType.partitioned, CheckTimeScale.monthly,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Creates a new table comparison configuration for a given check type and activates selected comparison checks.
     * @param principal User principal.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @param tableComparisonModel Table comparison model to save.
     * @param checkType Check type.
     * @param checkTimeScale Check time scale.
     * @return Rest method result.
     */
    @NotNull
    private ResponseEntity<Mono<Void>> createTableComparisonConfigurationWithChecks(DqoUserPrincipal principal,
                                                                                    String connectionName,
                                                                                    String schemaName,
                                                                                    String tableName,
                                                                                    TableComparisonModel tableComparisonModel,
                                                                                    CheckType checkType,
                                                                                    CheckTimeScale checkTimeScale) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                tableComparisonModel == null          ||
                Strings.isNullOrEmpty(tableComparisonModel.getTableComparisonConfigurationName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                    TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
                    if (tableSpec == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
                    }

                    TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
                    if (tableComparisonConfigurationSpecMap == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
                    }

                    if (tableSpec.getTableComparisons().containsKey(tableComparisonModel.getTableComparisonConfigurationName())) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
                    }

                    TableComparisonConfigurationSpec tableComparisonConfigurationSpec = new TableComparisonConfigurationSpec();
                    tableSpec.getTableComparisons().put(tableComparisonModel.getTableComparisonConfigurationName(), tableComparisonConfigurationSpec);
                    tableComparisonModel.copyToTableSpec(tableSpec, tableComparisonModel.getTableComparisonConfigurationName(), checkType, checkTimeScale);

                    userHomeContext.flush();
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
                });
    }

    /**
     * Creates a configuration of profiling checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/profiling", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableComparisonProfiling", notes = "Creates a table comparison configuration using profiling checks", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New table comparison configuration for profiling checks successfully created", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> createTableComparisonProfiling(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return createTableComparisonConfigurationWithChecks(principal, connectionName, schemaName, tableName, tableComparisonModel, CheckType.profiling, null);
        }));
    }

    /**
     * Creates a configuration of daily monitoring checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableComparisonMonitoringDaily", notes = "Creates a table comparison configuration using daily monitoring checks", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New table comparison configuration for daily monitoring checks successfully created", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> createTableComparisonMonitoringDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return createTableComparisonConfigurationWithChecks(principal, connectionName, schemaName, tableName, tableComparisonModel,
                    CheckType.monitoring, CheckTimeScale.daily);
        }));
    }

    /**
     * Creates a configuration of monthly monitoring checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableComparisonMonitoringMonthly", notes = "Creates a table comparison configuration using monthly monitoring checks", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New table comparison configuration for monthly monitoring checks successfully created", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> createTableComparisonMonitoringMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return createTableComparisonConfigurationWithChecks(principal, connectionName, schemaName, tableName, tableComparisonModel,
                    CheckType.monitoring, CheckTimeScale.monthly);
        }));
    }

    /**
     * Creates a configuration of daily partitioned checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableComparisonPartitionedDaily", notes = "Creates a table comparison configuration using daily partitioned checks", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New table comparison configuration for daily partitioned checks successfully created", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> createTableComparisonPartitionedDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return createTableComparisonConfigurationWithChecks(principal, connectionName, schemaName, tableName, tableComparisonModel,
                    CheckType.partitioned, CheckTimeScale.daily);
        }));
    }

    /**
     * Creates a configuration of monthly partitioned checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableComparisonPartitionedMonthly", notes = "Creates a table comparison configuration using monthly partitioned checks", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New table comparison configuration for monthly partitioned checks successfully created", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> createTableComparisonPartitionedMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return createTableComparisonConfigurationWithChecks(principal, connectionName, schemaName, tableName, tableComparisonModel,
                    CheckType.partitioned, CheckTimeScale.monthly);
        }));
    }

    /**
     * Updates a table comparison configuration and configures checks.
     * @poram principal DQOps User principal.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name (the old name).
     * @param tableComparisonModel Table comparison model with checks.
     * @param checkType Check type.
     * @param checkTimeScale Check time scale.
     * @return The response to return.
     */
    @NotNull
    private ResponseEntity<Mono<Void>> updateTableComparisonWithChecks(DqoUserPrincipal principal,
                                                                       String connectionName,
                                                                       String schemaName,
                                                                       String tableName,
                                                                       String tableComparisonConfigurationName,
                                                                       TableComparisonModel tableComparisonModel,
                                                                       CheckType checkType,
                                                                       CheckTimeScale checkTimeScale) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                    TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
                    if (tableSpec == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
                    }

                    TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
                    if (tableComparisonConfigurationSpecMap == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
                    }

                    TableComparisonConfigurationSpec tableComparisonConfigurationSpec = tableComparisonConfigurationSpecMap.get(tableComparisonConfigurationName);
                    if (tableComparisonConfigurationSpec == null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
                    }

                    tableComparisonModel.copyToTableSpec(tableSpec, tableComparisonConfigurationName, checkType, checkTimeScale);

                    userHomeContext.flush();
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
                });
    }

    /**
     * Update the configuration of profiling checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationName  Table comparison configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/profiling/{tableComparisonConfigurationName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonProfiling", notes = "Updates a table comparison profiling checks", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison profiling checks successfully updated", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableComparisonProfiling(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return updateTableComparisonWithChecks(principal, connectionName, schemaName, tableName, tableComparisonConfigurationName,
                    tableComparisonModel, CheckType.profiling, null);
        }));
    }

    /**
     * Update the configuration of daily monitoring checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationName  Table comparison configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/daily/{tableComparisonConfigurationName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonMonitoringDaily", notes = "Updates a table comparison checks monitoring daily", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison daily monitoring checks successfully updated", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableComparisonMonitoringDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return updateTableComparisonWithChecks(principal, connectionName, schemaName, tableName, tableComparisonConfigurationName,
                    tableComparisonModel, CheckType.monitoring, CheckTimeScale.daily);
        }));
    }

    /**
     * Update the configuration of monthly monitoring checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationName  Table comparison configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/monthly/{tableComparisonConfigurationName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonMonitoringMonthly", notes = "Updates a table comparison checks monitoring monthly", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison daily monitoring checks successfully updated", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableComparisonMonitoringMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return updateTableComparisonWithChecks(principal, connectionName, schemaName, tableName, tableComparisonConfigurationName,
                    tableComparisonModel, CheckType.monitoring, CheckTimeScale.monthly);
        }));
    }

    /**
     * Update the configuration of daily partitioned checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationName  Table comparison configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/daily/{tableComparisonConfigurationName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonPartitionedDaily", notes = "Updates a table comparison checks partitioned daily (comparing day to day)", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison daily partitioned checks successfully updated", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableComparisonPartitionedDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return updateTableComparisonWithChecks(principal, connectionName, schemaName, tableName, tableComparisonConfigurationName,
                    tableComparisonModel, CheckType.partitioned, CheckTimeScale.daily);
        }));
    }

    /**
     * Update the configuration of monthly partitioned checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationName  Table comparison configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/monthly/{tableComparisonConfigurationName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonPartitionedMonthly", notes = "Updates a table comparison checks partitioned monthly (comparing month to month)", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison monthly partitioned checks successfully updated", response = Void.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableComparisonPartitionedMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return updateTableComparisonWithChecks(principal, connectionName, schemaName, tableName, tableComparisonConfigurationName,
                    tableComparisonModel, CheckType.partitioned, CheckTimeScale.monthly);
        }));
    }

    /**
     * Reads the specification of a certain table, given its access path.
     * @param userHomeContext User-home context.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @return TableSpec of the requested table. Null if not found.
     */
    protected TableSpec readTableSpec(UserHomeContext userHomeContext, String connectionName, String schemaName, String tableName) {
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return null;
        }

        return tableWrapper.getSpec();
    }

    /**
     * Reads the data comparisons configuration on a certain table, given its access path.
     * @param userHomeContext User-home context.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @return Data comparisons configuration on the requested table. Null if not found.
     */
    protected TableComparisonConfigurationSpecMap readTableComparisonConfigurationMap(UserHomeContext userHomeContext,
                                                                                      String connectionName, String schemaName, String tableName) {
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return null;
        }
        return tableSpec.getTableComparisons();
    }
}
