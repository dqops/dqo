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

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.defaults.DefaultObservabilityConfigurationService;
import com.dqops.core.configuration.DqoCheckMiningConfigurationProperties;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingService;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mining.CheckMiningParametersModel;
import com.dqops.services.check.mining.CheckMiningProposalModel;
import com.dqops.services.check.mining.CheckMiningService;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * REST API controller that performs rule mining and proposes the configuration of data quality checks and their rule thresholds for tables.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "RuleMining", description = "Performs rule mining and proposes the configuration of data quality checks and their rule thresholds for tables.")
public class RuleMiningController {
    private final ExecutionContextFactory executionContextFactory;
    private final CheckMiningService checkMiningService;
    private final ModelToSpecCheckMappingService modelToSpecCheckMappingService;
    private final DefaultObservabilityConfigurationService defaultObservabilityConfigurationService;
    private final DqoCheckMiningConfigurationProperties checkMiningConfigurationProperties;

    /**
     * Dependency injection constructor.
     * @param executionContextFactory Execution context factory to get access to the DQOps home and the user home.
     * @param checkMiningService Check mining service.
     * @param modelToSpecCheckMappingService Check model to specification mapping service, used to save proposed configuration.'
     * @param defaultObservabilityConfigurationService Default data observability check configuration service.
     * @param checkMiningConfigurationProperties Check mining configuration parameters with the default values.
     */
    @Autowired
    public RuleMiningController(
            ExecutionContextFactory executionContextFactory,
            CheckMiningService checkMiningService,
            ModelToSpecCheckMappingService modelToSpecCheckMappingService,
            DefaultObservabilityConfigurationService defaultObservabilityConfigurationService,
            DqoCheckMiningConfigurationProperties checkMiningConfigurationProperties) {
        this.executionContextFactory = executionContextFactory;
        this.checkMiningService = checkMiningService;
        this.modelToSpecCheckMappingService = modelToSpecCheckMappingService;
        this.defaultObservabilityConfigurationService = defaultObservabilityConfigurationService;
        this.checkMiningConfigurationProperties = checkMiningConfigurationProperties;
    }

    /**
     * Generates a check configuration proposal of data quality profiling checks as a UI friendly model on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param checkMiningParameters Check mining parameters.
     * @return UI friendly data quality profiling checks proposal on a requested table.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/propose", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "proposeTableProfilingChecks", notes = "Proposes the configuration of profiling checks on a table by generating suggested configuration of checks and their rule thresholds.",
            response = CheckMiningProposalModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Proposition of table level data quality profiling checks on a table returned",
                    response = CheckMiningProposalModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckMiningProposalModel>>> proposeTableProfilingChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data quality check mining parameters which configure which rules are analyzed and proposed")
            @RequestBody CheckMiningParametersModel checkMiningParameters) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            ExecutionContext executionContext = this.executionContextFactory.create(principal.getDataDomainIdentity(), true);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec clonedTableWithDefaultChecks = tableSpec.deepClone();
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableAndColumns(
                    connectionWrapper.getSpec(), clonedTableWithDefaultChecks, userHome);

            if (checkMiningParameters.getFailChecksAtPercentErrorRows() == null) {
                checkMiningParameters.setFailChecksAtPercentErrorRows(this.checkMiningConfigurationProperties.getFailChecksAtPercentErrorRows());
            }

            CheckMiningProposalModel checkMiningProposalModel = this.checkMiningService.proposeChecks(
                    connectionWrapper.getSpec(),
                    clonedTableWithDefaultChecks,
                    executionContext,
                    CheckType.profiling,
                    null,
                    checkMiningParameters);

            return new ResponseEntity<>(Mono.just(checkMiningProposalModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Applies the proposed configuration of data quality profiling checks on a table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name.
     * @param checkMiningProposalModel      Proposed configuration of data quality checks to apply to a table.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/applyproposal", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "applyProposedProfilingChecks", notes = "Applies the proposed configuration of data quality profiling checks on a table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data quality profiling checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> applyProposedProfilingChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Proposed configuration of data quality checks to be applied on the table and its columns.")
            @RequestBody CheckMiningProposalModel checkMiningProposalModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            ExecutionContext executionContext = this.executionContextFactory.create(principal.getDataDomainIdentity(), false);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            if (checkMiningProposalModel.getTableChecks() != null) {
                AbstractRootChecksContainerSpec tableCheckRootContainer = tableSpec.getTableCheckRootContainer(
                        CheckType.profiling, null, true);
                this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkMiningProposalModel.getTableChecks(), tableCheckRootContainer, tableSpec);
            }

            if (checkMiningProposalModel.getColumnChecks() != null) {
                for (Map.Entry<String, CheckContainerModel> columnCheckProposalKeyValue : checkMiningProposalModel.getColumnChecks().entrySet()) {
                    String columnName = columnCheckProposalKeyValue.getKey();
                    CheckContainerModel columnChecksModel = columnCheckProposalKeyValue.getValue();

                    ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
                    if (columnSpec == null) {
                        continue; // silently skipping
                    }

                    AbstractRootChecksContainerSpec columnCheckRootContainer = columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, true);
                    this.modelToSpecCheckMappingService.updateCheckContainerSpec(columnChecksModel, columnCheckRootContainer, tableSpec);
                }
            }

            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Generates a check configuration proposal of data quality monitoring checks as a UI friendly model on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param checkMiningParameters Check mining parameters.
     * @return UI friendly data quality monitoring checks proposal on a requested table.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/propose", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "proposeTableMonitoringChecks", notes = "Proposes the configuration of monitoring checks on a table by generating suggested configuration of checks and their rule thresholds.",
            response = CheckMiningProposalModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Proposition of table level data quality monitoring checks on a table returned",
                    response = CheckMiningProposalModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckMiningProposalModel>>> proposeTableMonitoringChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data quality check mining parameters which configure which rules are analyzed and proposed")
            @RequestBody CheckMiningParametersModel checkMiningParameters) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            ExecutionContext executionContext = this.executionContextFactory.create(principal.getDataDomainIdentity(), true);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec clonedTableWithDefaultChecks = tableSpec.deepClone();
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableAndColumns(
                    connectionWrapper.getSpec(), clonedTableWithDefaultChecks, userHome);

            if (checkMiningParameters.getFailChecksAtPercentErrorRows() == null) {
                checkMiningParameters.setFailChecksAtPercentErrorRows(this.checkMiningConfigurationProperties.getFailChecksAtPercentErrorRows());
            }

            CheckMiningProposalModel checkMiningProposalModel = this.checkMiningService.proposeChecks(
                    connectionWrapper.getSpec(),
                    clonedTableWithDefaultChecks,
                    executionContext,
                    CheckType.monitoring,
                    timeScale,
                    checkMiningParameters);

            return new ResponseEntity<>(Mono.just(checkMiningProposalModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Applies the proposed configuration of data quality monitoring checks on a table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name.
     * @param checkMiningProposalModel      Proposed configuration of data quality checks to apply to a table.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/applyproposal", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "applyProposedMonitoringChecks", notes = "Applies the proposed configuration of data quality monitoring checks on a table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data quality monitoring checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> applyProposedMonitoringChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Proposed configuration of data quality checks to be applied on the table and its columns.")
            @RequestBody CheckMiningProposalModel checkMiningProposalModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            ExecutionContext executionContext = this.executionContextFactory.create(principal.getDataDomainIdentity(), false);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            if (checkMiningProposalModel.getTableChecks() != null) {
                AbstractRootChecksContainerSpec tableCheckRootContainer = tableSpec.getTableCheckRootContainer(
                        CheckType.monitoring, timeScale, true);
                this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkMiningProposalModel.getTableChecks(), tableCheckRootContainer, tableSpec);
            }

            if (checkMiningProposalModel.getColumnChecks() != null) {
                for (Map.Entry<String, CheckContainerModel> columnCheckProposalKeyValue : checkMiningProposalModel.getColumnChecks().entrySet()) {
                    String columnName = columnCheckProposalKeyValue.getKey();
                    CheckContainerModel columnChecksModel = columnCheckProposalKeyValue.getValue();

                    ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
                    if (columnSpec == null) {
                        continue; // silently skipping
                    }

                    AbstractRootChecksContainerSpec columnCheckRootContainer = columnSpec.getColumnCheckRootContainer(CheckType.monitoring, timeScale, true);
                    this.modelToSpecCheckMappingService.updateCheckContainerSpec(columnChecksModel, columnCheckRootContainer, tableSpec);
                }
            }

            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Generates a check configuration proposal of data quality partitioned checks as a UI friendly model on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param checkMiningParameters Check mining parameters.
     * @return UI friendly data quality partitioned checks proposal on a requested table.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/propose", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "proposeTablePartitionedChecks", notes = "Proposes the configuration of partitioned checks on a table by generating suggested configuration of checks and their rule thresholds.",
            response = CheckMiningProposalModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Proposition of table level data quality partitioned checks on a table returned",
                    response = CheckMiningProposalModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckMiningProposalModel>>> proposeTablePartitionedChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data quality check mining parameters which configure which rules are analyzed and proposed")
            @RequestBody CheckMiningParametersModel checkMiningParameters) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            ExecutionContext executionContext = this.executionContextFactory.create(principal.getDataDomainIdentity(), true);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec clonedTableWithDefaultChecks = tableSpec.deepClone();
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableAndColumns(
                    connectionWrapper.getSpec(), clonedTableWithDefaultChecks, userHome);

            if (checkMiningParameters.getFailChecksAtPercentErrorRows() == null) {
                checkMiningParameters.setFailChecksAtPercentErrorRows(this.checkMiningConfigurationProperties.getFailChecksAtPercentErrorRows());
            }

            CheckMiningProposalModel checkMiningProposalModel = this.checkMiningService.proposeChecks(
                    connectionWrapper.getSpec(),
                    clonedTableWithDefaultChecks,
                    executionContext,
                    CheckType.partitioned,
                    timeScale,
                    checkMiningParameters);

            return new ResponseEntity<>(Mono.just(checkMiningProposalModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Applies the proposed configuration of data quality partitioned checks on a table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name.
     * @param checkMiningProposalModel      Proposed configuration of data quality checks to apply to a table.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/applyproposal", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "applyProposedPartitionedChecks", notes = "Applies the proposed configuration of data quality partitioned checks on a table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data quality partitioned checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> applyProposedPartitionedChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Proposed configuration of data quality checks to be applied on the table and its columns.")
            @RequestBody CheckMiningProposalModel checkMiningProposalModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            ExecutionContext executionContext = this.executionContextFactory.create(principal.getDataDomainIdentity(), false);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            if (checkMiningProposalModel.getTableChecks() != null) {
                AbstractRootChecksContainerSpec tableCheckRootContainer = tableSpec.getTableCheckRootContainer(
                        CheckType.partitioned, timeScale, true);
                this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkMiningProposalModel.getTableChecks(), tableCheckRootContainer, tableSpec);
            }

            if (checkMiningProposalModel.getColumnChecks() != null) {
                for (Map.Entry<String, CheckContainerModel> columnCheckProposalKeyValue : checkMiningProposalModel.getColumnChecks().entrySet()) {
                    String columnName = columnCheckProposalKeyValue.getKey();
                    CheckContainerModel columnChecksModel = columnCheckProposalKeyValue.getValue();

                    ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
                    if (columnSpec == null) {
                        continue; // silently skipping
                    }

                    AbstractRootChecksContainerSpec columnCheckRootContainer = columnSpec.getColumnCheckRootContainer(CheckType.partitioned, timeScale, true);
                    this.modelToSpecCheckMappingService.updateCheckContainerSpec(columnChecksModel, columnCheckRootContainer, tableSpec);
                }
            }

            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }
}
