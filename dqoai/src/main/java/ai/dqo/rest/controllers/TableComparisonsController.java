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

import ai.dqo.checks.CheckType;
import ai.dqo.metadata.comparisons.ReferenceTableComparisonSpec;
import ai.dqo.metadata.comparisons.ReferenceTableComparisonSpecMap;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.comparison.TableComparisonBasicModel;
import ai.dqo.rest.models.comparison.TableComparisonModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
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
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "TableComparisons", description = "Manages the configuration of table comparisons between data sources")
public class TableComparisonsController {
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param userHomeContextFactory      User home context factory.
     */
    @Autowired
    public TableComparisonsController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of named table comparisons on the table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return List of basic models of data comparison configurations on the table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons", produces = "application/json")
    @ApiOperation(value = "getTableComparisonConfigurations", notes = "Returns the list of data comparisons on a compared table", response = TableComparisonBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonBasicModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<TableComparisonBasicModel>> getTableComparisonConfigurations(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ReferenceTableComparisonSpecMap comparisonSpecMap = this.readComparisonConfigurations(userHomeContext, connectionName, schemaName, tableName);
        if (comparisonSpecMap == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<TableComparisonBasicModel> result = new LinkedList<>();
        for (ReferenceTableComparisonSpec comparisonSpec : comparisonSpecMap.values()) {
            TableComparisonBasicModel comparisonModel = TableComparisonBasicModel.fromReferenceTableComparisonSpec(comparisonSpec);
            result.add(comparisonModel);
        }

        return new ResponseEntity<>(Flux.fromIterable(result), HttpStatus.OK); // 200
    }

    /**
     * Returns the basic configuration of a specific data comparison configuration.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonName Table comparison name.
     * @return Model of the data comparison configuration.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{tableComparisonName}", produces = "application/json")
    @ApiOperation(value = "getTableComparisonConfiguration", notes = "Returns a model of the data comparison configuration", response = TableComparisonBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonBasicModel.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableComparisonBasicModel>> getTableComparisonConfiguration(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ReferenceTableComparisonSpecMap comparisonSpecMap = this.readComparisonConfigurations(userHomeContext, connectionName, schemaName, tableName);
        if (comparisonSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableComparisonSpec tableComparisonSpec = comparisonSpecMap.get(tableComparisonName);
        if (tableComparisonSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonBasicModel tableComparisonBasicModel = TableComparisonBasicModel.fromReferenceTableComparisonSpec(tableComparisonSpec);
        return new ResponseEntity<>(Mono.just(tableComparisonBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Update a specific data comparison configuration using a new model.
     * Remark: PUT method is used, because renaming the data comparison configuration would break idempotence.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonName  Table comparison configuration name up until now.
     * @param tableComparisonConfigurationModel Table comparison configuration trimmed model.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{tableComparisonName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComparisonConfiguration", notes = "Updates a table comparison configuration according to the provided model")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table comparison configuration successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 409, message = "Table comparison configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableComparisonConfiguration(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonName,
            @ApiParam("Table comparison configuration model with the selection of the tables to compare")
                @RequestBody TableComparisonBasicModel tableComparisonConfigurationModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonName) ||
                tableComparisonConfigurationModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ReferenceTableComparisonSpecMap comparisonSpecMap = this.readComparisonConfigurations(userHomeContext, connectionName, schemaName, tableName);
        if (comparisonSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableComparisonSpec tableComparisonSpec = comparisonSpecMap.get(tableComparisonName);
        if (tableComparisonSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String newName = tableComparisonConfigurationModel.getComparisonName();
        if (Strings.isNullOrEmpty(newName)) {
            newName = tableComparisonName;
        }

        if (!Objects.equals(newName, tableComparisonName) && comparisonSpecMap.containsKey(newName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a data comparison configuration with this name already exists
        }

        if (!newName.equals(tableComparisonName)) {
            // If renaming actually happened.
            comparisonSpecMap.remove(tableComparisonName);
            tableComparisonSpec.setHierarchyId(null);
            comparisonSpecMap.put(newName, tableComparisonSpec);
        }

        tableComparisonConfigurationModel.copyToReferenceTableComparisonSpec(tableComparisonSpec);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Creates (adds) a new named table comparison configuration.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonConfigurationModel Table comparison configuration model.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableComparisonConfiguration", notes = "Creates a new table comparison configuration on a table level")
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
            @ApiParam("Table comparison configuration model") @RequestBody TableComparisonBasicModel tableComparisonConfigurationModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                tableComparisonConfigurationModel == null               ||
                Strings.isNullOrEmpty(tableComparisonConfigurationModel.getComparisonName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        if (tableSpec.getComparisons().containsKey(tableComparisonConfigurationModel.getComparisonName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a table comparison configuration with this name already exists
        }

        ReferenceTableComparisonSpec comparisonSpec = new ReferenceTableComparisonSpec();
        tableComparisonConfigurationModel.copyToReferenceTableComparisonSpec(comparisonSpec);
        tableSpec.getComparisons().put(tableComparisonConfigurationModel.getComparisonName(), comparisonSpec);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
    }

    /**
     * Deletes a specific table comparison configuration.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonName Table comparison configuration name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{tableComparisonName}", produces = "application/json")
    @ApiOperation(value = "deleteTableComparisonConfiguration", notes = "Deletes a table comparison configuration from a table")
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
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonName) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ReferenceTableComparisonSpecMap comparisonSpecMap = this.readComparisonConfigurations(userHomeContext, connectionName, schemaName, tableName);
        if (comparisonSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // If table comparison configuration is not found, return success (idempotence).
        comparisonSpecMap.remove(tableComparisonName);

        // TODO: We can also disable the configuration of all comparison checks in all check types that are using the deleted comparison, it should be defined in the TableSpec class

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Returns the table comparison in the profiling check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonName Table comparison name.
     * @return Model of the table comparison using profiling checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{tableComparisonName}/profiling", produces = "application/json")
    @ApiOperation(value = "getTableComparisonProfiling", notes = "Returns a model of the table comparison using advanced profiling checks (comparison at any time)", response = TableComparisonModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableComparisonModel.class),
            @ApiResponse(code = 404, message = "Connection, table or table comparison not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableComparisonModel>> getTableComparisonProfiling(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableComparisonSpec tableComparisonSpec = tableSpec.getComparisons().get(tableComparisonName);
        if (tableComparisonSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, tableComparisonName, CheckType.PROFILING, null);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
    }

    /**
     * Update the configuration of profiling checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableComparisonName  Table comparison configuration name up until now.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{tableComparisonName}/profiling", consumes = "application/json", produces = "application/json")
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
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(tableComparisonName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableComparisonSpecMap comparisonSpecMap = this.readComparisonConfigurations(userHomeContext, connectionName, schemaName, tableName);
        if (comparisonSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableComparisonSpec tableComparisonSpec = comparisonSpecMap.get(tableComparisonName);
        if (tableComparisonSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, tableComparisonName, CheckType.PROFILING, null);

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
    protected ReferenceTableComparisonSpecMap readComparisonConfigurations(UserHomeContext userHomeContext, String connectionName, String schemaName, String tableName) {
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return null;
        }
        return tableSpec.getComparisons();
    }
}
