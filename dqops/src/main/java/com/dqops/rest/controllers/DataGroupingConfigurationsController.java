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
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.DataGroupingConfigurationBasicModel;
import com.dqops.rest.models.metadata.DataGroupingConfigurationModel;
import com.dqops.rest.models.metadata.DataGroupingConfigurationTrimmedModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.core.principal.DqoUserPrincipal;
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

import java.util.*;

/**
 * REST api controller to manage the data grouping configurations on a table.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "DataGroupingConfigurations", description = "Manages data grouping configurations on a table")
public class DataGroupingConfigurationsController {
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param userHomeContextFactory      User home context factory.
     */
    @Autowired
    public DataGroupingConfigurationsController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of named data grouping configurations on the table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return List of basic models of data grouping configurations on the table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings", produces = "application/json")
    @ApiOperation(value = "getTableGroupingConfigurations", notes = "Returns the list of data grouping configurations on a table", response = DataGroupingConfigurationBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DataGroupingConfigurationBasicModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<DataGroupingConfigurationBasicModel>> getTableGroupingConfigurations(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DataGroupingConfigurationSpecMap dataGroupingsMapping = tableSpec.getGroupings();
        if (dataGroupingsMapping == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        boolean canEditConfiguration = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
        List<DataGroupingConfigurationBasicModel> result = new LinkedList<>();
        List<String> dataGroupingNamesList = new ArrayList<>(dataGroupingsMapping.keySet());
        for (int i = 0; i < dataGroupingNamesList.size() ; i++) {
            String groupingConfigurationName = dataGroupingNamesList.get(i);
            boolean isDefaultDataGrouping = Objects.equals(tableSpec.getDefaultGroupingName(), groupingConfigurationName);
            result.add(new DataGroupingConfigurationBasicModel(){{
                setConnectionName(connectionName);
                setSchemaName(schemaName);
                setTableName(tableName);
                setDataGroupingConfigurationName(groupingConfigurationName);
                setDefaultDataGroupingConfiguration(isDefaultDataGrouping);
                setCanEdit(canEditConfiguration);
            }});
        }

        return new ResponseEntity<>(Flux.fromIterable(result), HttpStatus.OK); // 200
    }

    /**
     * Returns the configuration of a specific data grouping configuration.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param groupingConfigurationName Data grouping name.
     * @return Model of the data grouping configuration.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{groupingConfigurationName}", produces = "application/json")
    @ApiOperation(value = "getTableGroupingConfiguration", notes = "Returns a model of the data grouping configuration", response = DataGroupingConfigurationModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DataGroupingConfigurationModel.class),
            @ApiResponse(code = 404, message = "Connection, table or data grouping not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<DataGroupingConfigurationModel>> getTableGroupingConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data grouping configuration name") @PathVariable String groupingConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        DataGroupingConfigurationSpecMap dataGroupingMapping = this.readGroupingConfigurations(userHomeContext, connectionName, schemaName, tableName);
        if (dataGroupingMapping == null || !dataGroupingMapping.containsKey(groupingConfigurationName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DataGroupingConfigurationModel result = new DataGroupingConfigurationModel() {{
            setConnectionName(connectionName);
            setSchemaName(schemaName);
            setTableName(tableName);
            setDataGroupingConfigurationName(groupingConfigurationName);
            setSpec(dataGroupingMapping.get(groupingConfigurationName));
            setCanEdit(principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
        }};
        return new ResponseEntity<>(Mono.just(result), HttpStatus.OK); // 200
    }


    /**
     * Update a specific data grouping configuration using a new model.
     * Remark: PUT method is used, because renaming the data grouping configuration would break idempotence.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param dataGroupingConfigurationName  Data grouping configuration name up until now.
     * @param dataGroupingConfigurationModel Data grouping configuration trimmed model.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableGroupingConfiguration", notes = "Updates a data grouping configuration according to the provided model")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data grouping configuration successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or data grouping not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 409, message = "Data grouping configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> updateTableGroupingConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data grouping configuration name") @PathVariable String dataGroupingConfigurationName,
            @ApiParam("Data grouping configuration simplified model") @RequestBody DataGroupingConfigurationTrimmedModel dataGroupingConfigurationModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(dataGroupingConfigurationName) ||
                dataGroupingConfigurationModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DataGroupingConfigurationSpecMap dataGroupingMapping = tableSpec.getGroupings();
        if (dataGroupingMapping == null || !dataGroupingMapping.containsKey(dataGroupingConfigurationName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String newName = dataGroupingConfigurationModel.getDataGroupingConfigurationName();
        if (Strings.isNullOrEmpty(newName)) {
            newName = dataGroupingConfigurationName;
        }

        if (!Objects.equals(newName, dataGroupingConfigurationName) && dataGroupingMapping.containsKey(newName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a data grouping configuration with this name already exists
        }

        DataGroupingConfigurationSpec newSpec = dataGroupingConfigurationModel.getSpec();
        dataGroupingMapping.put(newName, newSpec);
        if (!newName.equals(dataGroupingConfigurationName)) {
            // If renaming actually happened.
            dataGroupingMapping.remove(dataGroupingConfigurationName);
            if (Objects.equals(tableSpec.getDefaultGroupingName(), dataGroupingConfigurationName)) {
                tableSpec.setDefaultGroupingName(newName);
            }
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Creates (adds) a new named data grouping configuration.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableGroupingConfiguration", notes = "Creates a new data grouping configuration on a table level")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New data grouping configuration successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Data grouping configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> createTableGroupingConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data grouping configuration simplified model") @RequestBody DataGroupingConfigurationTrimmedModel dataGroupingConfigurationModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                dataGroupingConfigurationModel == null               ||
                Strings.isNullOrEmpty(dataGroupingConfigurationModel.getDataGroupingConfigurationName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        if (tableSpec.getGroupings().containsKey(dataGroupingConfigurationModel.getDataGroupingConfigurationName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a data grouping configuration with this name already exists
        }

        tableSpec.getGroupings().put(dataGroupingConfigurationModel.getDataGroupingConfigurationName(), dataGroupingConfigurationModel.getSpec());

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
    }

    /**
     * Sets a specific data grouping configuration as a default for the table.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param dataGroupingConfigurationName  Data grouping configuration name.
     * @return Empty response.
     */
    @PatchMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/setdefault", produces = "application/json")
    @ApiOperation(value = "setTableDefaultGroupingConfiguration", notes = "Sets a table's grouping configuration as the default or disables data grouping")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data grouping configuration successfully set as the default for the table"),
            @ApiResponse(code = 404, message = "Connection, table or data grouping configuration not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> setTableDefaultGroupingConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "dataGroupingConfigurationName", value = "Data grouping configuration name or empty to disable data grouping", required = true)
            @RequestParam(required = true) String dataGroupingConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DataGroupingConfigurationSpecMap dataGroupingMapping = tableSpec.getGroupings();
        if (!Strings.isNullOrEmpty(dataGroupingConfigurationName) && !dataGroupingMapping.containsKey(dataGroupingConfigurationName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        if (Strings.isNullOrEmpty(dataGroupingConfigurationName)) {
            tableSpec.setDefaultGroupingName(null);
        } else {
            tableSpec.setDefaultGroupingName(dataGroupingConfigurationName);
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Deletes a specific data grouping configuration.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataGroupingConfigurationName Data grouping configuration name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}", produces = "application/json")
    @ApiOperation(value = "deleteTableGroupingConfiguration", notes = "Deletes a data grouping configuration from a table")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data grouping configuration removed"),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 406, message = "Invalid request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> deleteTableGroupingConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data grouping configuration name") @PathVariable String dataGroupingConfigurationName) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(dataGroupingConfigurationName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DataGroupingConfigurationSpecMap dataGroupingsMap = tableSpec.getGroupings();
        if (dataGroupingsMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // If data grouping configuration is not found, return success (idempotence).
        dataGroupingsMap.remove(dataGroupingConfigurationName);
        if (Objects.equals(dataGroupingConfigurationName, tableSpec.getDefaultGroupingName())) {
            tableSpec.setDefaultGroupingName(null);
        }

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
     * Reads the data grouping configuration on a certain table, given its access path.
     * @param userHomeContext User-home context.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @return Data grouping configuration on the requested table. Null if not found.
     */
    protected DataGroupingConfigurationSpecMap readGroupingConfigurations(UserHomeContext userHomeContext, String connectionName, String schemaName, String tableName) {
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return null;
        }
        return tableSpec.getGroupings();
    }
}
