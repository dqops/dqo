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
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.StatisticsCollectorSearchFilters;
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.metadata.SchemaModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST api controller to manage the list of schemas inside a connection.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Schemas", description = "Schema management")
public class SchemasController {
    private UserHomeContextFactory userHomeContextFactory;

    @Autowired
    public SchemasController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of schemas inside a connection.
     * @param connectionName Connection name.
     * @return List of schemas inside a connection.
     */
    @GetMapping("/{connectionName}/schemas")
    @ApiOperation(value = "getSchemas", notes = "Returns a list of schemas inside a connection", response = SchemaModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchemaModel[].class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Flux<SchemaModel>> getSchemas(
            @ApiParam("Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        List<String> schemaNameList = connectionWrapper.getTables().toList()
                .stream()
                .map(tw -> tw.getPhysicalTableName().getSchemaName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        Stream<SchemaModel> modelStream = schemaNameList.stream().map(s -> new SchemaModel() {{
            setConnectionName(connectionName);
            setSchemaName(s);
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(s + ".*");
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(s + ".*");
                setCheckType(CheckType.ADHOC);
                setEnabled(true);
            }});
            setRunWholeTableChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(s + ".*");
                setCheckType(CheckType.CHECKPOINT);
                setEnabled(true);
            }});
            setRunTimePeriodChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(s + ".*");
                setCheckType(CheckType.PARTITIONED);
                setEnabled(true);
            }});
            setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(s + ".*");
                setEnabled(true);
            }});
            setImportTableJobParameters(new ImportTablesQueueJobParameters()
            {{
                setConnectionName(connectionName);
                setSchemaName(s);
            }});
        }});

        return new ResponseEntity<>(Flux.fromStream(modelStream), HttpStatus.OK);
    }
}
