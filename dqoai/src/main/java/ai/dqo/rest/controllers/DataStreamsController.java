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

import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpecMap;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.metadata.DataStreamBasicModel;
import ai.dqo.rest.models.metadata.DataStreamModel;
import ai.dqo.rest.models.metadata.DataStreamTrimmedModel;
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
 * REST api controller to manage the data streams on a table.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "DataStreams", description = "Manages data streams on a table")
public class DataStreamsController {
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param userHomeContextFactory      User home context factory.
     */
    @Autowired
    public DataStreamsController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of named data streams on the table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return List of basic models of data streams on the table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams", produces = "application/json")
    @ApiOperation(value = "getDataStreams", notes = "Returns a list of data streams on the table", response = DataStreamBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DataStreamBasicModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<DataStreamBasicModel>> getDataStreams(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        DataStreamMappingSpecMap dataStreamMapping = this.readDataStreamMapping(userHomeContext, connectionName, schemaName, tableName);
        if (dataStreamMapping == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<DataStreamBasicModel> result = new LinkedList<>();
        List<String> dataStreamNamesList = new ArrayList<>(dataStreamMapping.keySet());
        for (int i = 0; i < dataStreamNamesList.size() ; i++) {
            String dataStreamName = dataStreamNamesList.get(i);
            boolean isDefaultDataStream = (i == 0);
            result.add(new DataStreamBasicModel(){{
                setConnectionName(connectionName);
                setSchemaName(schemaName);
                setTableName(tableName);
                setDataStreamName(dataStreamName);
                setDefaultDataStream(isDefaultDataStream);
            }});
        }

        return new ResponseEntity<>(Flux.fromIterable(result), HttpStatus.OK); // 200
    }

    /**
     * Returns the configuration of a specific data stream.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataStreamName Data stream name.
     * @return Model of the data stream containing all configurations.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams/{dataStreamName}", produces = "application/json")
    @ApiOperation(value = "getDataStream", notes = "Returns a model of the data stream", response = DataStreamModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DataStreamModel[].class),
            @ApiResponse(code = 404, message = "Connection, table or data stream not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DataStreamModel>> getDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data stream name") @PathVariable String dataStreamName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        DataStreamMappingSpecMap dataStreamMapping = this.readDataStreamMapping(userHomeContext, connectionName, schemaName, tableName);
        if (dataStreamMapping == null || !dataStreamMapping.containsKey(dataStreamName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DataStreamModel result = new DataStreamModel(){{
            setConnectionName(connectionName);
            setSchemaName(schemaName);
            setTableName(tableName);
            setDataStreamName(dataStreamName);
            setSpec(dataStreamMapping.get(dataStreamName));
        }};
        return new ResponseEntity<>(Mono.just(result), HttpStatus.OK); // 200
    }


    /**
     * Update a specific data stream using a new model.
     * Remark: POST method is used, because renaming the data stream would break idempotence.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param dataStreamName  Data stream name up until now.
     * @param dataStreamModel Data stream trimmed model.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams/{dataStreamName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDataStream", notes = "Updates a data stream according to the provided model")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data stream successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or data stream not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 409, message = "Data stream name with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data stream name") @PathVariable String dataStreamName,
            @ApiParam("Data stream trimmed model") @RequestBody DataStreamTrimmedModel dataStreamModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(dataStreamName) ||
                dataStreamModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        DataStreamMappingSpecMap dataStreamMapping = this.readDataStreamMapping(userHomeContext, connectionName, schemaName, tableName);
        if (dataStreamMapping == null || !dataStreamMapping.containsKey(dataStreamName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String newName = dataStreamModel.getDataStreamName();
        if (Strings.isNullOrEmpty(newName)) {
            newName = dataStreamName;
        }

        if (newName != null && !Objects.equals(newName, dataStreamName) && dataStreamMapping.containsKey(newName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a data stream configuration with this name already exists
        }

        DataStreamMappingSpec newSpec = dataStreamModel.getSpec();
        dataStreamMapping.put(newName, newSpec);
        if (!newName.equals(dataStreamName)) {
            // If renaming actually happened.
            dataStreamMapping.remove(dataStreamName);
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Creates (adds) a new named data stream configuration.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createDataStream", notes = "Creates a new data stream configuration")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New data stream configuration successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Data stream name with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data stream trimmed model") @RequestBody DataStreamTrimmedModel dataStreamModel) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                dataStreamModel == null               ||
                Strings.isNullOrEmpty(dataStreamModel.getDataStreamName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        if (tableSpec.getDataStreams().containsKey(dataStreamModel.getDataStreamName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a data stream configuration with this name already exists
        }

        tableSpec.getDataStreams().put(dataStreamModel.getDataStreamName(), dataStreamModel.getSpec());

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
    }

    /**
     * Sets a specific data stream as a default for the table.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param dataStreamName  Data stream name.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams/{dataStreamName}/setDefault", produces = "application/json")
    @ApiOperation(value = "setDefaultDataStream", notes = "Sets a data stream as default")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data stream successfully set as default for the table"),
            @ApiResponse(code = 404, message = "Connection, table or data stream not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> setDefaultDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data stream name") @PathVariable String dataStreamName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DataStreamMappingSpecMap dataStreamMapping = tableSpec.getDataStreams();
        if (!dataStreamMapping.containsKey(dataStreamName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        if (!dataStreamName.equals(dataStreamMapping.getFirstDataStreamMappingName())) {
            // TODO: Think about implementing this inside DataStreamMappingSpecMap.
            DataStreamMappingSpecMap newMapping = new DataStreamMappingSpecMap();
            newMapping.put(dataStreamName, dataStreamMapping.get(dataStreamName));
            for (Map.Entry<String, DataStreamMappingSpec> dataStreamEntry : dataStreamMapping.entrySet()) {
                if (dataStreamEntry.getKey().equals(dataStreamName)) {
                    continue;
                }
                newMapping.put(dataStreamEntry.getKey(), dataStreamEntry.getValue());
            }
            tableSpec.setDataStreams(newMapping);
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Deletes a specific data stream.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataStreamName Data stream name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams/{dataStreamName}", produces = "application/json")
    @ApiOperation(value = "deleteDataStream", notes = "Deletes a data stream")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data stream removed"),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 406, message = "Invalid request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data stream name") @PathVariable String dataStreamName) {
        if (Strings.isNullOrEmpty(connectionName)     ||
                Strings.isNullOrEmpty(schemaName)     ||
                Strings.isNullOrEmpty(tableName)      ||
                Strings.isNullOrEmpty(dataStreamName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        DataStreamMappingSpecMap dataStreamMapping = this.readDataStreamMapping(userHomeContext, connectionName, schemaName, tableName);
        if (dataStreamMapping == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // If data stream is not found, return success (idempotence).
        dataStreamMapping.remove(dataStreamName);

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
     * Reads the data stream mappings on a certain table, given its access path.
     * @param userHomeContext User-home context.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @return Data stream mappings on the requested table. Null if not found.
     */
    protected DataStreamMappingSpecMap readDataStreamMapping(UserHomeContext userHomeContext, String connectionName, String schemaName, String tableName) {
        TableSpec tableSpec = this.readTableSpec(userHomeContext, connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return null;
        }
        return tableSpec.getDataStreams();
    }
}
