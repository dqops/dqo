/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.controllers;

import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.check.CheckTemplate;
import com.dqops.rest.models.metadata.SchemaModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.mapping.models.CheckContainerTypeModel;
import com.dqops.services.check.models.CheckConfigurationModel;
import com.dqops.services.metadata.SchemaService;
import com.dqops.utils.threading.CompletableFutureRunner;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.stream.Stream;

/**
 * REST api controller to manage the list of schemas inside a connection.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Schemas", description = "Operations for listing imported schemas from monitored data sources. Also provides operations for activating and deactivating multiple checks at once.")
public class SchemasController {
    private static final Logger LOG = LoggerFactory.getLogger(SchemasController.class);
    private final SchemaService schemaService;
    private final UserHomeContextFactory userHomeContextFactory;

    @Autowired
    public SchemasController(SchemaService schemaService,
                             UserHomeContextFactory userHomeContextFactory) {
        this.schemaService = schemaService;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of schemas inside a connection.
     * @param connectionName Connection name.
     * @return List of schemas inside a connection.
     */
    @GetMapping(value = "/{connectionName}/schemas", produces = "application/json")
    @ApiOperation(value = "getSchemas", notes = "Returns a list of schemas inside a connection", response = SchemaModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchemaModel[].class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<SchemaModel>>> getSchemas(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            ConnectionSpec connectionSpec = connectionWrapper.getSpec();
            DuckdbParametersSpec duckdbParametersSpec = connectionSpec.getDuckdb();

            if(connectionSpec.getProviderType() != null
                    && connectionSpec.getProviderType().equals(ProviderType.duckdb)
                    && duckdbParametersSpec != null) {
                schemaNameList.addAll(connectionWrapper.getSpec().getDuckdb().getDirectories().keySet().stream()
                        .filter(s -> !schemaNameList.contains(s)).collect(Collectors.toList()));
            }

            boolean isEditor = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            boolean isOperator = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);
            Stream<SchemaModel> modelStream = schemaNameList.stream()
                    .map(schemaName -> {
                        String directoryPrefix = null;
                        if(connectionSpec.getProviderType() != null
                                && connectionSpec.getProviderType().equals(ProviderType.duckdb)
                                && duckdbParametersSpec != null){
                            directoryPrefix = duckdbParametersSpec.getDirectories().get(schemaName);
                        }

                        SchemaModel schemaModel = SchemaModel.fromSchemaNameStrings(connectionName, schemaName, directoryPrefix, isEditor, isOperator);

                        if(connectionSpec.getProviderType() != null
                                && connectionSpec.getProviderType().equals(ProviderType.duckdb)
                                && directoryPrefix == null){
                            schemaModel.setErrorMessage("The schema mapping to the directory with data is missing.");
                        }

                        return schemaModel;
                    });

            return new ResponseEntity<>(Flux.fromStream(modelStream), HttpStatus.OK);
        }));
    }

    /**
     * Retrieves a list of profiling check configurations on a requested schema.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param tableNamePattern  (Optional) Table search pattern filter.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkTarget       (Optional) Filter on check target.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @param limit             The limit of results, the default value is 1000.
     * @return List of profiling check configurations on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/profiling/model", produces = "application/json")
    @ApiOperation(value = "getSchemaProfilingChecksModel", notes = "Return a flat list of configurations for profiling checks on a schema",
            response = CheckConfigurationModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of profiling checks configurations on a schema returned", response = CheckConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckConfigurationModel>>> getSchemaProfilingChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
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
            Optional<Boolean> checkEnabled,
            @ApiParam(value = "Check configured", required = false) @RequestParam(required = false)
            Optional<Boolean> checkConfigured,
            @ApiParam(value = "Limit of results, the default value is 1000", required = false) @RequestParam(required = false)
            Optional<Integer> limit) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            List<TableWrapper> tableWrappers = this.schemaService.getSchemaTables(userHome, connectionName, schemaName);
            if (tableWrappers == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<CheckConfigurationModel> checkConfigurationModels = this.schemaService.getCheckConfigurationsOnSchema(
                    connectionName, schemaName, new CheckContainerTypeModel(CheckType.profiling, null),
                    tableNamePattern.orElse(null),
                    columnNamePattern.orElse(null),
                    columnDataType.orElse(null),
                    checkTarget.orElse(null),
                    checkCategory.orElse(null),
                    checkName.orElse(null),
                    checkEnabled.orElse(null),
                    checkConfigured.orElse(null),
                    limit.orElse(1000),
                    principal
            );

            return new ResponseEntity<>(Flux.fromIterable(checkConfigurationModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves a UI friendly data quality monitoring check configuration list on a requested schema.
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
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @param limit             The limit of results, the default value is 1000.
     * @return UI friendly data quality monitoring check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/monitoring/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getSchemaMonitoringChecksModel", notes = "Return a UI friendly model of configurations for data quality monitoring checks on a schema",
            response = CheckConfigurationModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality monitoring checks on a schema returned", response = CheckConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckConfigurationModel>>> getSchemaMonitoringChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
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
            Optional<Boolean> checkEnabled,
            @ApiParam(value = "Check configured", required = false) @RequestParam(required = false)
            Optional<Boolean> checkConfigured,
            @ApiParam(value = "Limit of results, the default value is 1000", required = false) @RequestParam(required = false)
            Optional<Integer> limit) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            List<TableWrapper> tableWrappers = this.schemaService.getSchemaTables(userHome, connectionName, schemaName);
            if (tableWrappers == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<CheckConfigurationModel> checkConfigurationModels = this.schemaService.getCheckConfigurationsOnSchema(
                    connectionName, schemaName, new CheckContainerTypeModel(CheckType.monitoring, timeScale),
                    tableNamePattern.orElse(null),
                    columnNamePattern.orElse(null),
                    columnDataType.orElse(null),
                    checkTarget.orElse(null),
                    checkCategory.orElse(null),
                    checkName.orElse(null),
                    checkEnabled.orElse(null),
                    checkConfigured.orElse(null),
                    limit.orElse(1000),
                    principal
            );

            return new ResponseEntity<>(Flux.fromIterable(checkConfigurationModels), HttpStatus.OK); // 200
        }));
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
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @param limit             The limit of results, the default value is 1000.
     * @return UI friendly data quality partitioned check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/partitioned/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getSchemaPartitionedChecksModel", notes = "Return a UI friendly model of configurations for data quality partitioned checks on a schema",
            response = CheckConfigurationModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality partitioned checks on a schema returned", response = CheckConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckConfigurationModel>>> getSchemaPartitionedChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
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
            Optional<Boolean> checkEnabled,
            @ApiParam(value = "Check configured", required = false) @RequestParam(required = false)
            Optional<Boolean> checkConfigured,
            @ApiParam(value = "Limit of results, the default value is 1000", required = false) @RequestParam(required = false)
            Optional<Integer> limit) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            List<TableWrapper> tableWrappers = this.schemaService.getSchemaTables(userHome, connectionName, schemaName);
            if (tableWrappers == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<CheckConfigurationModel> checkConfigurationModels = this.schemaService.getCheckConfigurationsOnSchema(
                    connectionName, schemaName, new CheckContainerTypeModel(CheckType.partitioned, timeScale),
                    tableNamePattern.orElse(null),
                    columnNamePattern.orElse(null),
                    columnDataType.orElse(null),
                    checkTarget.orElse(null),
                    checkCategory.orElse(null),
                    checkName.orElse(null),
                    checkEnabled.orElse(null),
                    checkConfigured.orElse(null),
                    limit.orElse(1000),
                    principal
            );

            return new ResponseEntity<>(Flux.fromIterable(checkConfigurationModels), HttpStatus.OK); // 200
        }));
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
    @ApiOperation(value = "getSchemaProfilingChecksTemplates", notes = "Return available data quality checks on a requested schema.",
            response = CheckTemplate[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a schema returned", response = CheckTemplate[].class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckTemplate>>> getSchemaProfilingChecksTemplates(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam(value = "Check target", required = false) @RequestParam(required = false) Optional<CheckTarget> checkTarget,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            List<CheckTemplate> checkTemplates = this.schemaService.getCheckTemplates(
                    connectionName, schemaName, CheckType.profiling,
                    null, checkTarget.orElse(null), checkCategory.orElse(null), checkName.orElse(null), principal);

            return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the list of monitoring checks templates on the given schema.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param timeScale      Check time scale.
     * @param checkTarget    (Optional) Filter on check target.
     * @param checkCategory  (Optional) Filter on check category.
     * @param checkName      (Optional) Filter on check name.
     * @return Data quality checks templates on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/bulkenable/monitoring/{timeScale}", produces = "application/json")
    @ApiOperation(value = "getSchemaMonitoringChecksTemplates", notes = "Return available data quality checks on a requested schema.",
            response = CheckTemplate[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a schema returned", response = CheckTemplate[].class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckTemplate>>> getSchemaMonitoringChecksTemplates(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Check target", required = false) @RequestParam(required = false) Optional<CheckTarget> checkTarget,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            List<CheckTemplate> checkTemplates = this.schemaService.getCheckTemplates(
                    connectionName, schemaName, CheckType.monitoring,
                    timeScale, checkTarget.orElse(null), checkCategory.orElse(null), checkName.orElse(null), principal);

            return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
        }));
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
    @ApiOperation(value = "getSchemaPartitionedChecksTemplates", notes = "Return available data quality checks on a requested schema.",
            response = CheckTemplate[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a schema returned", response = CheckTemplate[].class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckTemplate>>> getSchemaPartitionedChecksTemplates(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Check target", required = false) @RequestParam(required = false) Optional<CheckTarget> checkTarget,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            List<CheckTemplate> checkTemplates = this.schemaService.getCheckTemplates(
                    connectionName, schemaName, CheckType.partitioned,
                    timeScale, checkTarget.orElse(null), checkCategory.orElse(null), checkName.orElse(null), principal);

            return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
        }));
    }
}
