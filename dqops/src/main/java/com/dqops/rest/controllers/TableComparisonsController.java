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
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * REST api controller to manage the configuration of table comparisons between data sources.
 */
@RestController
@RequestMapping("api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "TableComparisons", description = "Manages the configuration of table comparisons between tables on the same or different data sources")
public class TableComparisonsController {
    private final UserHomeContextFactory userHomeContextFactory;
    private final HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param userHomeContextFactory      User home context factory.
     * @param hierarchyNodeTreeSearcher   Node searcher.
     */
    @Autowired
    public TableComparisonsController(UserHomeContextFactory userHomeContextFactory,
                                      HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
    }

    /**
     * Returns a list of table comparison configurations on a table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return List of reference tables.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons", produces = "application/json")
    @ApiOperation(value = "getTableComparisonConfigurations", notes = "Returns the list of table comparison configurations on a compared table", response = TableComparisonConfigurationModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<TableComparisonConfigurationModel>> getTableComparisonConfigurations(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
        if (tableComparisonConfigurationSpecMap == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<TableComparisonConfigurationModel> result = new LinkedList<>();
        for (TableComparisonConfigurationSpec tableComparisonConfigurationSpec : tableComparisonConfigurationSpecMap.values()) {
            TableComparisonConfigurationModel tableComparisonConfigurationModel = TableComparisonConfigurationModel.fromTableComparisonSpec(tableComparisonConfigurationSpec);
            result.add(tableComparisonConfigurationModel);
        }

        return new ResponseEntity<>(Flux.fromIterable(result), HttpStatus.OK); // 200
    }

    /**
     * Returns the configuration of a table comparison.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Table comparison model.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}", produces = "application/json")
    @ApiOperation(value = "getTableComparisonConfiguration", notes = "Returns a model of the table comparison configuration", response = TableComparisonConfigurationModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonConfigurationModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableComparisonConfigurationModel>> getTableComparisonConfiguration(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Reference table configuration name") @PathVariable String tableComparisonConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
        if (tableComparisonConfigurationSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpec tableComparisonConfigurationSpec = tableComparisonConfigurationSpecMap.get(tableComparisonConfigurationName);
        if (tableComparisonConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationModel tableComparisonConfigurationModel = TableComparisonConfigurationModel.fromTableComparisonSpec(tableComparisonConfigurationSpec);
        return new ResponseEntity<>(Mono.just(tableComparisonConfigurationModel), HttpStatus.OK); // 200
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
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}",
            consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonConfiguration", notes = "Updates a table configuration configuration")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison configuration successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or table comparison on the table not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 409, message = "Table comparison configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableComparisonConfiguration(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison model with the configuration of the tables to compare")
                @RequestBody TableComparisonConfigurationModel tableComparisonConfigurationModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonConfigurationName) ||
                tableComparisonConfigurationModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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
    }

    /**
     * Creates (adds) a new named reference table comparison configuration.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationModel Table comparison configuration model.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableComparisonConfiguration", notes = "Creates a new table comparison configuration added to the compared table")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New table comparison configuration successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Table comparison configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createTableComparisonConfiguration(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration model") @RequestBody TableComparisonConfigurationModel tableComparisonConfigurationModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                tableComparisonConfigurationModel == null               ||
                Strings.isNullOrEmpty(tableComparisonConfigurationModel.getTableComparisonConfigurationName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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
    }

    /**
     * Deletes a specific table comparison configuration. Deactivates also all configured comparison checks.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}", produces = "application/json")
    @ApiOperation(value = "deleteTableComparisonConfiguration", notes = "Deletes a table comparison configuration from a compared table")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison configuration removed"),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 406, message = "Invalid request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteTableComparisonConfiguration(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Reference table configuration name") @PathVariable String tableComparisonConfigurationName) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonConfigurationName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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
    }

    /**
     * Returns the table comparison in the profiling check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Model of the table comparison using profiling checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{tableComparisonConfigurationName}/profiling", produces = "application/json")
    @ApiOperation(value = "getTableComparisonProfiling", notes = "Returns a model of the table comparison using advanced profiling checks (comparison at any time)", response = TableComparisonModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableComparisonModel>> getTableComparisonProfiling(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec, tableComparisonConfigurationName, CheckType.PROFILING, null);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
    }

    /**
     * Returns the table comparison in the daily recurring check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Model of the table comparison using daily recurring checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{tableComparisonConfigurationName}/recurring/daily", produces = "application/json")
    @ApiOperation(value = "getTableComparisonRecurringDaily", notes = "Returns a model of the table comparison using daily recurring checks (comparison once a day)", response = TableComparisonModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableComparisonModel>> getTableComparisonRecurringDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec, tableComparisonConfigurationName, CheckType.RECURRING, CheckTimeScale.daily);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
    }

    /**
     * Returns the table comparison in the monthly recurring check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Model of the table comparison using monthly recurring checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{tableComparisonConfigurationName}/recurring/monthly", produces = "application/json")
    @ApiOperation(value = "getTableComparisonRecurringMonthly", notes = "Returns a model of the table comparison using monthly recurring checks (comparison once a month)", response = TableComparisonModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableComparisonModel>> getTableComparisonRecurringMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec, tableComparisonConfigurationName, CheckType.RECURRING, CheckTimeScale.monthly);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
    }

    /**
     * Returns the table comparison in the daily partitioned check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Reference table configuration name.
     * @return Model of the table comparison using daily partitioned checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{tableComparisonConfigurationName}/partitioned/daily", produces = "application/json")
    @ApiOperation(value = "getTableComparisonPartitionedDaily", notes = "Returns a model of the table comparison using daily partition checks (comparing day to day)", response = TableComparisonModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableComparisonModel>> getTableComparisonPartitionedDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec, tableComparisonConfigurationName, CheckType.PARTITIONED, CheckTimeScale.daily);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
    }

    /**
     * Returns the table comparison in the monthly partitioned check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Model of the table comparison using monthly partitioned checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{tableComparisonConfigurationName}/partitioned/monthly", produces = "application/json")
    @ApiOperation(value = "getTableComparisonPartitionedMonthly", notes = "Returns a model of the table comparison using monthly partition checks (comparing month to month)", response = TableComparisonModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableComparisonModel>> getTableComparisonPartitionedMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec, tableComparisonConfigurationName, CheckType.PARTITIONED, CheckTimeScale.monthly);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
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
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{tableComparisonConfigurationName}/profiling", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonProfiling", notes = "Updates a table comparison profiling checks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison profiling checks successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableComparisonProfiling(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
        if (tableComparisonConfigurationSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpec tableComparisonConfigurationSpec = tableComparisonConfigurationSpecMap.get(tableComparisonConfigurationName);
        if (tableComparisonConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, tableComparisonConfigurationName, CheckType.PROFILING, null);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Update the configuration of daily recurring checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationName  Table comparison configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{tableComparisonConfigurationName}/recurring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonRecurringDaily", notes = "Updates a table comparison checks recurring daily")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison daily recurring checks successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableComparisonRecurringDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
        if (tableComparisonConfigurationSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpec tableComparisonConfigurationSpec = tableComparisonConfigurationSpecMap.get(tableComparisonConfigurationName);
        if (tableComparisonConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, tableComparisonConfigurationName, CheckType.RECURRING, CheckTimeScale.daily);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Update the configuration of monthly recurring checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationName  Table comparison configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{tableComparisonConfigurationName}/recurring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonRecurringMonthly", notes = "Updates a table comparison checks recurring monthly")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison daily recurring checks successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableComparisonRecurringMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
        if (tableComparisonConfigurationSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpec tableComparisonConfigurationSpec = tableComparisonConfigurationSpecMap.get(tableComparisonConfigurationName);
        if (tableComparisonConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, tableComparisonConfigurationName, CheckType.RECURRING, CheckTimeScale.monthly);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
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
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{tableComparisonConfigurationName}/partitioned/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonPartitionedDaily", notes = "Updates a table comparison checks partitioned daily (comparing day to day)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison daily partitioned checks successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableComparisonPartitionedDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
        if (tableComparisonConfigurationSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpec tableComparisonConfigurationSpec = tableComparisonConfigurationSpecMap.get(tableComparisonConfigurationName);
        if (tableComparisonConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, tableComparisonConfigurationName, CheckType.PARTITIONED, CheckTimeScale.daily);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
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
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{tableComparisonConfigurationName}/partitioned/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonPartitionedMonthly", notes = "Updates a table comparison checks partitioned monthly (comparing month to month)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison monthly partitioned checks successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableComparisonPartitionedMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap = this.readTableComparisonConfigurationMap(userHomeContext, connectionName, schemaName, tableName);
        if (tableComparisonConfigurationSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonConfigurationSpec tableComparisonConfigurationSpec = tableComparisonConfigurationSpecMap.get(tableComparisonConfigurationName);
        if (tableComparisonConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, tableComparisonConfigurationName, CheckType.PARTITIONED, CheckTimeScale.monthly);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
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
