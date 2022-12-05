/*
 * Copyright © 2022 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import ai.dqo.rest.models.metadata.DataStreamTableBasicModel;
import ai.dqo.rest.models.metadata.DataStreamTableModel;
import ai.dqo.rest.models.metadata.DataStreamTableTrimmedModel;
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
@Api(value = "DataStreamsTable", description = "Manages data streams on a table")
public class DataStreamsTableController {
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param userHomeContextFactory      User home context factory.
     */
    @Autowired
    public DataStreamsTableController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of named data streams on the table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return List of basic models of data streams on the table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams")
    @ApiOperation(value = "getDataStreams", notes = "Returns a list of data streams on the table", response = DataStreamTableBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DataStreamTableBasicModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<DataStreamTableBasicModel>> getDataStreams(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        DataStreamMappingSpecMap dataStreamMapping = this.obtainDataStreamMapping(connectionName, schemaName, tableName);
        if (dataStreamMapping == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<DataStreamTableBasicModel> result = new LinkedList<>();
        for (String dataStreamName : dataStreamMapping.keySet()) {
            if (!dataStreamName.equals(DataStreamMappingSpecMap.DEFAULT_MAPPING_NAME)) {
                continue;
            }
            result.add(new DataStreamTableBasicModel(){{
                setConnectionName(connectionName);
                setSchemaName(schemaName);
                setTableName(tableName);
                setDataStreamName(dataStreamName);
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
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams/{dataStreamName}")
    @ApiOperation(value = "getDataStream", notes = "Returns a model of the data stream", response = DataStreamTableModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DataStreamTableModel[].class),
            @ApiResponse(code = 404, message = "Connection, table or data stream not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DataStreamTableModel>> getDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data stream name") @PathVariable String dataStreamName) {
        DataStreamMappingSpecMap dataStreamMapping = this.obtainDataStreamMapping(connectionName, schemaName, tableName);
        if (dataStreamMapping == null || !dataStreamMapping.containsKey(dataStreamName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        DataStreamTableModel result = new DataStreamTableModel(){{
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
    @PostMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams/{dataStreamName}")
    @ApiOperation(value = "updateDataStream", notes = "Updates a data stream according to the provided model")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data stream successfully updated"),
            @ApiResponse(code = 404, message = "Connection, table or data stream not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data stream name") @PathVariable String dataStreamName,
            @ApiParam("Data stream trimmed model") @RequestBody DataStreamTableTrimmedModel dataStreamModel) {
        if (Strings.isNullOrEmpty(connectionName)                                    ||
                Strings.isNullOrEmpty(schemaName)                                    ||
                Strings.isNullOrEmpty(tableName)                                     ||
                Strings.isNullOrEmpty(dataStreamName)                                ||
                // TODO: How to handle these default mappings?
                //       What do we mean by "named data stream"?
                //       How does a check with a default name relate to a default data stream (first on the list)?
                dataStreamName.equals(DataStreamMappingSpecMap.DEFAULT_MAPPING_NAME) ||
                dataStreamModel == null                                              ||
                (dataStreamModel.getDataStreamName() != null &&
                        dataStreamModel.getDataStreamName().equals(DataStreamMappingSpecMap.DEFAULT_MAPPING_NAME)
                )) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        DataStreamMappingSpecMap dataStreamMapping = this.obtainDataStreamMapping(connectionName, schemaName, tableName);
        if (dataStreamMapping == null || !dataStreamMapping.containsKey(dataStreamName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        String newName = dataStreamModel.getDataStreamName();
        if (Strings.isNullOrEmpty(newName)) {
            newName = dataStreamName;
        }

        DataStreamMappingSpec newSpec = dataStreamModel.getSpec();
        dataStreamMapping.put(newName, newSpec);
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }


    /**
     * Sets a specific data stream as a default for the table.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param dataStreamName  Data stream name.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams/{dataStreamName}/setDefault")
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

        TableSpec tableSpec = this.obtainTableSpec(connectionName, schemaName, tableName);
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

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }


    /**
     * Delets a specific data stream.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataStreamName Data stream name.
     * @return Empty response.
     */
    @DeleteMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/datastreams/{dataStreamName}")
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
                Strings.isNullOrEmpty(dataStreamName) ||
                // TODO: How to handle these default mappings?
                //       What do we mean by "named data stream"?
                //       How does a check with a default name relate to a default data stream (first on the list)?
                dataStreamName.equals(DataStreamMappingSpecMap.DEFAULT_MAPPING_NAME)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        DataStreamMappingSpecMap dataStreamMapping = this.obtainDataStreamMapping(connectionName, schemaName, tableName);
        if (dataStreamMapping == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // If data stream is not found, return success (because idempotence).
        if (dataStreamMapping.containsKey(dataStreamName)) {
            dataStreamMapping.remove(dataStreamName);
        }
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    // TODO: Suggestion: util class for exploring the path.
    private TableSpec obtainTableSpec(String connectionName, String schemaName, String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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

    private DataStreamMappingSpecMap obtainDataStreamMapping(String connectionName, String schemaName, String tableName) {
        TableSpec tableSpec = this.obtainTableSpec(connectionName, schemaName, tableName);
        if (tableSpec == null) {
            return null;
        }
        return tableSpec.getDataStreams();
    }
}
