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

import ai.dqo.checks.CheckTarget;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.check.CheckTemplate;
import ai.dqo.rest.models.metadata.SchemaModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.services.check.mapping.UIAllChecksModelFactory;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;
import ai.dqo.services.metadata.SchemaService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
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
    private static final Logger LOG = LoggerFactory.getLogger(SchemasController.class);
    private final SchemaService schemaService;
    private final UserHomeContextFactory userHomeContextFactory;
    private final UIAllChecksModelFactory uiAllChecksModelFactory;

    @Autowired
    public SchemasController(SchemaService schemaService,
                             UserHomeContextFactory userHomeContextFactory,
                             UIAllChecksModelFactory uiAllChecksModelFactory) {
        this.schemaService = schemaService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.uiAllChecksModelFactory = uiAllChecksModelFactory;
    }

    /**
     * Returns a list of schemas inside a connection.
     * @param connectionName Connection name.
     * @return List of schemas inside a connection.
     */
    @GetMapping(value = "/{connectionName}/schemas", produces = "application/json")
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

        Stream<SchemaModel> modelStream = schemaNameList.stream()
                .map(s -> SchemaModel.fromSchemaNameStrings(connectionName, s));

        return new ResponseEntity<>(Flux.fromStream(modelStream), HttpStatus.OK);
    }

    /**
     * Retrieves a UI friendly data quality profiling check configuration list on a requested schema.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param tableNamePattern  (Optional) Table search pattern filter.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkTarget       (Optional) Filter on check target.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @return UI friendly data quality profiling check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/profiling/ui", produces = "application/json")
    @ApiOperation(value = "getSchemaProfilingChecksUI", notes = "Return a UI friendly model of configurations for data quality profiling checks on a schema", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality profiling checks on a schema returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getSchemaProfilingChecksUI(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam(value = "Table name pattern", required = false) @RequestParam(required = false)
            Optional<String> tableNamePattern,
            @ApiParam(value = "Column name pattern", required = false) @RequestParam(required = false)
            Optional<String> columnNamePattern,
            @ApiParam(value = "Column data-type", required = false) @RequestParam(required = false)
            Optional<String> columnDataType,
            @ApiParam(value = "Check target", required = false) @RequestParam(required = false)
            Optional<CheckTarget> checkTarget,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false)
            Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false)
            Optional<String> checkName,
            @ApiParam(value = "Check enabled", required = false) @RequestParam(required = false)
            Optional<Boolean> checkEnabled) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<TableWrapper> tableWrappers = this.schemaService.getSchemaTables(userHome, connectionName, schemaName);
        if (tableWrappers == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // TODO: Move this functionality to a dedicated service.
        String tableSearchPattern = PhysicalTableName.fromSchemaTableFilter(
                new PhysicalTableName(schemaName, tableNamePattern.orElse("")).toTableSearchFilter()
        ).toTableSearchFilter();

        CheckSearchFilters filters = new CheckSearchFilters();
        filters.setCheckType(CheckType.PROFILING);
        filters.setConnectionName(connectionName);
        filters.setSchemaTableName(tableSearchPattern);
        filters.setColumnName(columnNamePattern.orElse(null));
        filters.setColumnDataType(columnDataType.orElse(null));
        filters.setCheckTarget(checkTarget.orElse(null));
        filters.setCheckCategory(checkCategory.orElse(null));
        filters.setCheckName(checkName.orElse(null));
        filters.setEnabled(checkEnabled.orElse(null));

        List<UIAllChecksModel> uiAllChecksModel = this.uiAllChecksModelFactory.fromCheckSearchFilters(filters);
        if (uiAllChecksModel.size() != 1) {
            LOG.warn("Unexpected result size in getSchemaProfilingChecksUI: " + uiAllChecksModel.size());
        }

        return new ResponseEntity<>(Mono.just(uiAllChecksModel.get(0)), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly data quality recurring check configuration list on a requested schema.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param timeScale         Check time-scale.
     * @param tableNamePattern  (Optional) Table search pattern filter.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkTarget       (Optional) Filter on check target.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @return UI friendly data quality recurring check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/recurring/{timeScale}/ui", produces = "application/json")
    @ApiOperation(value = "getSchemaRecurringChecksUI", notes = "Return a UI friendly model of configurations for data quality recurring checks on a schema", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality recurring checks on a schema returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getSchemaRecurringChecksUI(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Check time-scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Table name pattern", required = false) @RequestParam(required = false)
            Optional<String> tableNamePattern,
            @ApiParam(value = "Column name pattern", required = false) @RequestParam(required = false)
            Optional<String> columnNamePattern,
            @ApiParam(value = "Column data-type", required = false) @RequestParam(required = false)
            Optional<String> columnDataType,
            @ApiParam(value = "Check target", required = false) @RequestParam(required = false)
            Optional<CheckTarget> checkTarget,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false)
            Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false)
            Optional<String> checkName,
            @ApiParam(value = "Check enabled", required = false) @RequestParam(required = false)
            Optional<Boolean> checkEnabled) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<TableWrapper> tableWrappers = this.schemaService.getSchemaTables(userHome, connectionName, schemaName);
        if (tableWrappers == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // TODO: Move this functionality to a dedicated service.
        String tableSearchPattern = PhysicalTableName.fromSchemaTableFilter(
                new PhysicalTableName(schemaName, tableNamePattern.orElse("")).toTableSearchFilter()
        ).toTableSearchFilter();

        CheckSearchFilters filters = new CheckSearchFilters();
        filters.setCheckType(CheckType.RECURRING);
        filters.setTimeScale(timeScale);
        filters.setConnectionName(connectionName);
        filters.setSchemaTableName(tableSearchPattern);
        filters.setColumnName(columnNamePattern.orElse(null));
        filters.setColumnDataType(columnDataType.orElse(null));
        filters.setCheckTarget(checkTarget.orElse(null));
        filters.setCheckCategory(checkCategory.orElse(null));
        filters.setCheckName(checkName.orElse(null));
        filters.setEnabled(checkEnabled.orElse(null));

        List<UIAllChecksModel> uiAllChecksModel = this.uiAllChecksModelFactory.fromCheckSearchFilters(filters);
        if (uiAllChecksModel.size() != 1) {
            LOG.warn("Unexpected result size in getSchemaRecurringChecksUI: " + uiAllChecksModel.size());
        }

        return new ResponseEntity<>(Mono.just(uiAllChecksModel.get(0)), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly data quality partitioned check configuration list on a requested schema.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param timeScale         Check time-scale.
     * @param tableNamePattern  (Optional) Table search pattern filter.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkTarget       (Optional) Filter on check target.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @return UI friendly data quality partitioned check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/partitioned/{timeScale}/ui", produces = "application/json")
    @ApiOperation(value = "getSchemaPartitionedChecksUI", notes = "Return a UI friendly model of configurations for data quality partitioned checks on a schema", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality partitioned checks on a schema returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getSchemaPartitionedChecksUI(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Check time-scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Table name pattern", required = false) @RequestParam(required = false)
            Optional<String> tableNamePattern,
            @ApiParam(value = "Column name pattern", required = false) @RequestParam(required = false)
            Optional<String> columnNamePattern,
            @ApiParam(value = "Column data-type", required = false) @RequestParam(required = false)
            Optional<String> columnDataType,
            @ApiParam(value = "Check target", required = false) @RequestParam(required = false)
            Optional<CheckTarget> checkTarget,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false)
            Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false)
            Optional<String> checkName,
            @ApiParam(value = "Check enabled", required = false) @RequestParam(required = false)
            Optional<Boolean> checkEnabled) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<TableWrapper> tableWrappers = this.schemaService.getSchemaTables(userHome, connectionName, schemaName);
        if (tableWrappers == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // TODO: Move this functionality to a dedicated service.
        String tableSearchPattern = PhysicalTableName.fromSchemaTableFilter(
                new PhysicalTableName(schemaName, tableNamePattern.orElse("")).toTableSearchFilter()
        ).toTableSearchFilter();

        CheckSearchFilters filters = new CheckSearchFilters();
        filters.setCheckType(CheckType.PARTITIONED);
        filters.setTimeScale(timeScale);
        filters.setConnectionName(connectionName);
        filters.setSchemaTableName(tableSearchPattern);
        filters.setColumnName(columnNamePattern.orElse(null));
        filters.setColumnDataType(columnDataType.orElse(null));
        filters.setCheckTarget(checkTarget.orElse(null));
        filters.setCheckCategory(checkCategory.orElse(null));
        filters.setCheckName(checkName.orElse(null));
        filters.setEnabled(checkEnabled.orElse(null));

        List<UIAllChecksModel> uiAllChecksModel = this.uiAllChecksModelFactory.fromCheckSearchFilters(filters);
        if (uiAllChecksModel.size() != 1) {
            LOG.warn("Unexpected result size in getSchemaPartitionedChecksUI: " + uiAllChecksModel.size());
        }

        return new ResponseEntity<>(Mono.just(uiAllChecksModel.get(0)), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the list of profiling checks templates on the given schema.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param checkTarget    (Optional) Filter on check target.
     * @param checkCategory  (Optional) Filter on check category.
     * @param checkName      (Optional) Filter on check name.
     * @return Data quality checks templates on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/bulkenable/profiling", produces = "application/json")
    @ApiOperation(value = "getSchemaProfilingChecksTemplates", notes = "Return available data quality checks on a requested schema.", response = CheckTemplate.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a schema returned", response = CheckTemplate.class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckTemplate>> getSchemaProfilingChecksTemplates(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam(value = "Check target", required = false) @RequestParam(required = false) Optional<CheckTarget> checkTarget,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<TableWrapper> tableWrappers = this.schemaService.getSchemaTables(userHome, connectionName, schemaName);
        if (tableWrappers == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<CheckTemplate> checkTemplates = this.schemaService.getCheckTemplates(
                connectionName, schemaName, CheckType.PROFILING,
                null, checkTarget.orElse(null), checkCategory.orElse(null), checkName.orElse(null));

        return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the list of recurring checks templates on the given schema.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param timeScale      Check time scale.
     * @param checkTarget    (Optional) Filter on check target.
     * @param checkCategory  (Optional) Filter on check category.
     * @param checkName      (Optional) Filter on check name.
     * @return Data quality checks templates on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/bulkenable/recurring/{timeScale}", produces = "application/json")
    @ApiOperation(value = "getSchemaRecurringChecksTemplates", notes = "Return available data quality checks on a requested schema.", response = CheckTemplate.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a schema returned", response = CheckTemplate.class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckTemplate>> getSchemaRecurringChecksTemplates(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Check target", required = false) @RequestParam(required = false) Optional<CheckTarget> checkTarget,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<TableWrapper> tableWrappers = this.schemaService.getSchemaTables(userHome, connectionName, schemaName);
        if (tableWrappers == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<CheckTemplate> checkTemplates = this.schemaService.getCheckTemplates(
                connectionName, schemaName, CheckType.RECURRING,
                timeScale, checkTarget.orElse(null), checkCategory.orElse(null), checkName.orElse(null));

        return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the list of partitioned checks templates on the given schema.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param timeScale      Check time scale.
     * @param checkTarget    (Optional) Filter on check target.
     * @param checkCategory  (Optional) Filter on check category.
     * @param checkName      (Optional) Filter on check name.
     * @return Data quality checks templates on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/bulkenable/partitioned/{timeScale}", produces = "application/json")
    @ApiOperation(value = "getSchemaPartitionedChecksTemplates", notes = "Return available data quality checks on a requested schema.", response = CheckTemplate.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a schema returned", response = CheckTemplate.class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckTemplate>> getSchemaPartitionedChecksTemplates(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Check target", required = false) @RequestParam(required = false) Optional<CheckTarget> checkTarget,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<TableWrapper> tableWrappers = this.schemaService.getSchemaTables(userHome, connectionName, schemaName);
        if (tableWrappers == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<CheckTemplate> checkTemplates = this.schemaService.getCheckTemplates(
                connectionName, schemaName, CheckType.PARTITIONED,
                timeScale, checkTarget.orElse(null), checkCategory.orElse(null), checkName.orElse(null));

        return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
    }
}
