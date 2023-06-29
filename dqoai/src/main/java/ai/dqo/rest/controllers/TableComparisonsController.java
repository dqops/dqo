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

import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.metadata.comparisons.ReferenceTableSpec;
import ai.dqo.metadata.comparisons.ReferenceTableSpecMap;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.comparison.ReferenceTableModel;
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
     * Returns a list of reference tables (the source of truth) on a table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return List of reference tables.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/referencetables", produces = "application/json")
    @ApiOperation(value = "getReferenceTables", notes = "Returns the list of reference tables on a compared table", response = ReferenceTableModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ReferenceTableModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ReferenceTableModel>> getReferenceTables(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ReferenceTableSpecMap referenceTableSpecMap = this.readReferenceTablesMap(userHomeContext, connectionName, schemaName, tableName);
        if (referenceTableSpecMap == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<ReferenceTableModel> result = new LinkedList<>();
        for (ReferenceTableSpec referenceTableSpec : referenceTableSpecMap.values()) {
            ReferenceTableModel referenceTableModel = ReferenceTableModel.fromReferenceTableComparisonSpec(referenceTableSpec);
            result.add(referenceTableModel);
        }

        return new ResponseEntity<>(Flux.fromIterable(result), HttpStatus.OK); // 200
    }

    /**
     * Returns the configuration of a reference table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param referenceTableConfigurationName Reference table.
     * @return Reference table model.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/referencetables/{referenceTableConfigurationName}", produces = "application/json")
    @ApiOperation(value = "getReferenceTable", notes = "Returns a model of the reference table", response = ReferenceTableModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ReferenceTableModel.class),
            @ApiResponse(code = 404, message = "Connection, table or reference table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ReferenceTableModel>> getTableComparisonConfiguration(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ReferenceTableSpecMap referenceTableSpecMap = this.readReferenceTablesMap(userHomeContext, connectionName, schemaName, tableName);
        if (referenceTableSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableSpec = referenceTableSpecMap.get(referenceTableConfigurationName);
        if (referenceTableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableModel referenceTableModel = ReferenceTableModel.fromReferenceTableComparisonSpec(referenceTableSpec);
        return new ResponseEntity<>(Mono.just(referenceTableModel), HttpStatus.OK); // 200
    }

    /**
     * Update a specific reference table configuration using a new model.
     * Remark: PUT method is used, because renaming the reference table configuration would break idempotence.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param referenceTableConfigurationName  Reference table configuration name up until now.
     * @param referenceTableModel Reference table model.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/referencetables/{referenceTableConfigurationName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateReferenceTable", notes = "Updates a reference table configuration")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Reference table configuration successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or reference table not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 409, message = "Reference table configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateReferenceTable(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName,
            @ApiParam("Reference table model with the selection of the tables to compare")
                @RequestBody ReferenceTableModel referenceTableModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(referenceTableConfigurationName) ||
                referenceTableModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ReferenceTableSpecMap referenceTableSpecMap = this.readReferenceTablesMap(userHomeContext, connectionName, schemaName, tableName);
        if (referenceTableSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableSpec = referenceTableSpecMap.get(referenceTableConfigurationName);
        if (referenceTableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String newName = referenceTableModel.getReferenceTableConfigurationName();
        if (Strings.isNullOrEmpty(newName)) {
            newName = referenceTableConfigurationName;
        }

        if (!Objects.equals(newName, referenceTableConfigurationName) && referenceTableSpecMap.containsKey(newName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a reference table configuration with this name already exists
        }

        if (!newName.equals(referenceTableConfigurationName)) {
            // If renaming actually happened.
            referenceTableSpecMap.remove(referenceTableConfigurationName);
            referenceTableSpec.setHierarchyId(null);
            referenceTableSpecMap.put(newName, referenceTableSpec);
        }

        referenceTableModel.copyToReferenceTableComparisonSpec(referenceTableSpec);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Creates (adds) a new named reference table configuration.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param referenceTableModel Table comparison configuration model.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/referencetables", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createReferenceTable", notes = "Creates a new reference table configuration added to the compared table")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New reference table configuration successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Reference table configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createReferenceTable(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Reference table configuration model") @RequestBody ReferenceTableModel referenceTableModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                referenceTableModel == null               ||
                Strings.isNullOrEmpty(referenceTableModel.getReferenceTableConfigurationName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        if (tableSpec.getReferenceTables().containsKey(referenceTableModel.getReferenceTableConfigurationName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a table comparison configuration with this name already exists
        }

        ReferenceTableSpec referenceTableSpec = new ReferenceTableSpec();
        referenceTableModel.copyToReferenceTableComparisonSpec(referenceTableSpec);
        tableSpec.getReferenceTables().put(referenceTableModel.getReferenceTableConfigurationName(), referenceTableSpec);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
    }

    /**
     * Deletes a specific reference table configuration.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param referenceTableConfigurationName Reference table  configuration name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/referencetables/{referenceTableConfigurationName}", produces = "application/json")
    @ApiOperation(value = "deleteReferenceTable", notes = "Deletes a reference table configuration from a compared table")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Reference table configuration removed"),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 406, message = "Invalid request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteReferenceTable(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(referenceTableConfigurationName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ReferenceTableSpecMap referenceTableSpecMap = this.readReferenceTablesMap(userHomeContext, connectionName, schemaName, tableName);
        if (referenceTableSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // If reference table configuration is not found, return success (idempotence).
        referenceTableSpecMap.remove(referenceTableConfigurationName);

        // TODO: We can also disable the configuration of all comparison checks in all check types that are using the deleted comparison, it should be defined in the TableSpec class

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Returns the table comparison in the profiling check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param referenceTableConfigurationName Reference table configuration name.
     * @return Model of the table comparison using profiling checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{referenceTableConfigurationName}/profiling", produces = "application/json")
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
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableConfigurationSpec = tableSpec.getReferenceTables().get(referenceTableConfigurationName);
        if (referenceTableConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec referencedTableSpec = this.readTableSpec(userHomeContext,
                referenceTableConfigurationSpec.getReferenceTableConnectionName(),
                referenceTableConfigurationSpec.getReferenceTableSchemaName(),
                referenceTableConfigurationSpec.getReferenceTableName());

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec, referenceTableConfigurationName, CheckType.PROFILING, null);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
    }

    /**
     * Returns the table comparison in the daily recurring check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param referenceTableConfigurationName Reference table configuration name.
     * @return Model of the table comparison using daily recurring checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{referenceTableConfigurationName}/recurring/daily", produces = "application/json")
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
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableConfigurationSpec = tableSpec.getReferenceTables().get(referenceTableConfigurationName);
        if (referenceTableConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec referencedTableSpec = this.readTableSpec(userHomeContext,
                referenceTableConfigurationSpec.getReferenceTableConnectionName(),
                referenceTableConfigurationSpec.getReferenceTableSchemaName(),
                referenceTableConfigurationSpec.getReferenceTableName());

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec, referenceTableConfigurationName, CheckType.RECURRING, CheckTimeScale.daily);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
    }

    /**
     * Returns the table comparison in the monthly recurring check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param referenceTableConfigurationName Reference table configuration name.
     * @return Model of the table comparison using monthly recurring checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{referenceTableConfigurationName}/recurring/monthly", produces = "application/json")
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
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableConfigurationSpec = tableSpec.getReferenceTables().get(referenceTableConfigurationName);
        if (referenceTableConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec referencedTableSpec = this.readTableSpec(userHomeContext,
                referenceTableConfigurationSpec.getReferenceTableConnectionName(),
                referenceTableConfigurationSpec.getReferenceTableSchemaName(),
                referenceTableConfigurationSpec.getReferenceTableName());

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec, referenceTableConfigurationName, CheckType.RECURRING, CheckTimeScale.monthly);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
    }

    /**
     * Returns the table comparison in the daily partitioned check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param referenceTableConfigurationName Reference table configuration name.
     * @return Model of the table comparison using daily partitioned checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{referenceTableConfigurationName}/partitioned/daily", produces = "application/json")
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
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableConfigurationSpec = tableSpec.getReferenceTables().get(referenceTableConfigurationName);
        if (referenceTableConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec referencedTableSpec = this.readTableSpec(userHomeContext,
                referenceTableConfigurationSpec.getReferenceTableConnectionName(),
                referenceTableConfigurationSpec.getReferenceTableSchemaName(),
                referenceTableConfigurationSpec.getReferenceTableName());

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec, referenceTableConfigurationName, CheckType.PARTITIONED, CheckTimeScale.daily);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
    }

    /**
     * Returns the table comparison in the monthly partitioned check type.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param referenceTableConfigurationName Reference table configuration name.
     * @return Model of the table comparison using monthly partitioned checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comparisons/{referenceTableConfigurationName}/partitioned/monthly", produces = "application/json")
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
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableConfigurationSpec = tableSpec.getReferenceTables().get(referenceTableConfigurationName);
        if (referenceTableConfigurationSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec referencedTableSpec = this.readTableSpec(userHomeContext,
                referenceTableConfigurationSpec.getReferenceTableConnectionName(),
                referenceTableConfigurationSpec.getReferenceTableSchemaName(),
                referenceTableConfigurationSpec.getReferenceTableName());

        TableComparisonModel tableComparisonModel = TableComparisonModel.fromTableSpec(tableSpec, referencedTableSpec, referenceTableConfigurationName, CheckType.PARTITIONED, CheckTimeScale.monthly);
        return new ResponseEntity<>(Mono.just(tableComparisonModel), HttpStatus.OK); // 200
    }

    /**
     * Update the configuration of profiling checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param referenceTableConfigurationName  Reference table configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{referenceTableConfigurationName}/profiling", consumes = "application/json", produces = "application/json")
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
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(referenceTableConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpecMap referenceTableSpecMap = this.readReferenceTablesMap(userHomeContext, connectionName, schemaName, tableName);
        if (referenceTableSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableSpec = referenceTableSpecMap.get(referenceTableConfigurationName);
        if (referenceTableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, referenceTableConfigurationName, CheckType.PROFILING, null);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Update the configuration of daily recurring checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param referenceTableConfigurationName  Reference table configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{referenceTableConfigurationName}/recurring/daily", consumes = "application/json", produces = "application/json")
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
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(referenceTableConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpecMap referenceTableSpecMap = this.readReferenceTablesMap(userHomeContext, connectionName, schemaName, tableName);
        if (referenceTableSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableSpec = referenceTableSpecMap.get(referenceTableConfigurationName);
        if (referenceTableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, referenceTableConfigurationName, CheckType.RECURRING, CheckTimeScale.daily);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Update the configuration of monthly recurring checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param referenceTableConfigurationName  Reference table configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{referenceTableConfigurationName}/recurring/monthly", consumes = "application/json", produces = "application/json")
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
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(referenceTableConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpecMap referenceTableSpecMap = this.readReferenceTablesMap(userHomeContext, connectionName, schemaName, tableName);
        if (referenceTableSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableSpec = referenceTableSpecMap.get(referenceTableConfigurationName);
        if (referenceTableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, referenceTableConfigurationName, CheckType.RECURRING, CheckTimeScale.monthly);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Update the configuration of daily partitioned checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param referenceTableConfigurationName  Reference table configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{referenceTableConfigurationName}/partitioned/daily", consumes = "application/json", produces = "application/json")
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
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(referenceTableConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpecMap referenceTableSpecMap = this.readReferenceTablesMap(userHomeContext, connectionName, schemaName, tableName);
        if (referenceTableSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableSpec = referenceTableSpecMap.get(referenceTableConfigurationName);
        if (referenceTableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, referenceTableConfigurationName, CheckType.PARTITIONED, CheckTimeScale.daily);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Update the configuration of monthly partitioned checks for performing the table comparison.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param referenceTableConfigurationName  Reference table configuration name.
     * @param tableComparisonModel Table comparison model with all checks to enable or disable.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{referenceTableConfigurationName}/partitioned/monthly", consumes = "application/json", produces = "application/json")
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
            @ApiParam("Reference table configuration name") @PathVariable String referenceTableConfigurationName,
            @ApiParam("Table comparison configuration model with the selected checks to use for comparison")
            @RequestBody TableComparisonModel tableComparisonModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(referenceTableConfigurationName) ||
                tableComparisonModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpecMap referenceTableSpecMap = this.readReferenceTablesMap(userHomeContext, connectionName, schemaName, tableName);
        if (referenceTableSpecMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ReferenceTableSpec referenceTableSpec = referenceTableSpecMap.get(referenceTableConfigurationName);
        if (referenceTableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        tableComparisonModel.copyToTableSpec(tableSpec, referenceTableConfigurationName, CheckType.PARTITIONED, CheckTimeScale.monthly);

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
    protected ReferenceTableSpecMap readReferenceTablesMap(UserHomeContext userHomeContext, String connectionName, String schemaName, String tableName) {
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return null;
        }
        return tableSpec.getReferenceTables();
    }
}
