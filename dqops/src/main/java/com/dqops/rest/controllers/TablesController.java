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

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.defaults.DefaultObservabilityConfigurationService;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.core.similarity.SimilarTableModel;
import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.data.checkresults.services.CheckResultsDataService;
import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;
import com.dqops.data.checkresults.statuscache.TableStatusCache;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.statistics.services.StatisticsDataService;
import com.dqops.data.statistics.models.StatisticsResultsForTableModel;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.incidents.TableIncidentGroupingSpec;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.CronSchedulesSpec;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.search.TableSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.check.CheckTemplate;
import com.dqops.rest.models.metadata.*;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingService;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.basicmodels.CheckContainerListModel;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckContainerTypeModel;
import com.dqops.services.check.models.CheckConfigurationModel;
import com.dqops.services.locking.RestApiLockService;
import com.dqops.services.metadata.TableService;
import com.dqops.statistics.StatisticsCollectorTarget;
import com.dqops.utils.threading.CompletableFutureRunner;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * REST api controller to manage the list of tables inside a schema.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Tables", description = "Operations related to manage the metadata of imported tables, and managing the configuration of table-level data quality checks.")
@Slf4j
public class TablesController {
    private final TableService tableService;
    private UserHomeContextFactory userHomeContextFactory;
    private DqoHomeContextFactory dqoHomeContextFactory;
    private SpecToModelCheckMappingService specToModelCheckMappingService;
    private ModelToSpecCheckMappingService modelToSpecCheckMappingService;
    private StatisticsDataService statisticsDataService;
    private CheckResultsDataService checkResultsDataService;
    private DefaultObservabilityConfigurationService defaultObservabilityConfigurationService;
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private TableStatusCache tableStatusCache;
    private RestApiLockService lockService;
    private JobSchedulerService jobSchedulerService;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param tableService                     Table logic service.
     * @param userHomeContextFactory           User home context factory.
     * @param dqoHomeContextFactory            DQOps home context factory, used to retrieve the definition of built-in sensors.
     * @param specToModelCheckMappingService   Check mapper to convert the check specification to a model.
     * @param modelToSpecCheckMappingService   Check mapper to convert the check model to a check specification.
     * @param statisticsDataService            Statistics data service, provides access to the statistics (basic profiling).
     * @param checkResultsDataService          Check results data service, to verify if there are any check results present.
     * @param defaultObservabilityConfigurationService The service that applies the configuration of the default checks to a table.
     * @param hierarchyNodeTreeSearcher        Node searcher, used to search for tables using filters.
     * @param tableStatusCache                 The cache of last known data quality status for a table.
     * @param lockService                      Object lock service.
     * @param jobSchedulerService              Job scheduler service.
     */
    @Autowired
    public TablesController(TableService tableService,
                            UserHomeContextFactory userHomeContextFactory,
                            DqoHomeContextFactory dqoHomeContextFactory,
                            SpecToModelCheckMappingService specToModelCheckMappingService,
                            ModelToSpecCheckMappingService modelToSpecCheckMappingService,
                            StatisticsDataService statisticsDataService,
                            CheckResultsDataService checkResultsDataService,
                            DefaultObservabilityConfigurationService defaultObservabilityConfigurationService,
                            HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                            TableStatusCache tableStatusCache,
                            RestApiLockService lockService,
                            JobSchedulerService jobSchedulerService) {
        this.tableService = tableService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.specToModelCheckMappingService = specToModelCheckMappingService;
        this.modelToSpecCheckMappingService = modelToSpecCheckMappingService;
        this.statisticsDataService = statisticsDataService;
        this.checkResultsDataService = checkResultsDataService;
        this.defaultObservabilityConfigurationService = defaultObservabilityConfigurationService;
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.tableStatusCache = tableStatusCache;
        this.lockService = lockService;
        this.jobSchedulerService = jobSchedulerService;
    }

    /**
     * Returns a list of tables inside a database/schema.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @return List of tables inside a connection's schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables", produces = "application/json")
    @ApiOperation(value = "getTables", notes = "Returns a list of tables inside a connection/schema", response = TableListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableListModel[].class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<TableListModel>>> getTables(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam(name = "label", value = "Optional labels to filter the tables", required = false)
            @RequestParam(required = false) Optional<List<String>> label,
            @ApiParam(name = "page", value = "Page number, the first page is 1", required = false)
            @RequestParam(required = false) Optional<Integer> page,
            @ApiParam(name = "limit", value = "Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided", required = false)
            @RequestParam(required = false) Optional<Integer> limit,
            @ApiParam(name = "filter", value = "Optional table name filter", required = false)
            @RequestParam(required = false) Optional<String> filter,
            @ApiParam(name = "checkType", value = "Optional parameter for the check type, when provided, returns the results for data quality dimensions for the data quality checks of that type", required = false)
            @RequestParam(required = false) Optional<CheckType> checkType) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSearchFilters tableSearchFilters = new TableSearchFilters();
            if (label.isPresent() && label.get().size() > 0) {
                tableSearchFilters.setLabels(label.get().toArray(String[]::new));
            }
            if (filter.isEmpty() || Strings.isNullOrEmpty(filter.get())) {
                tableSearchFilters.setFullTableName(schemaName + ".*");
            } else {
                tableSearchFilters.setFullTableName(schemaName + "." + filter.get());
            }

            Integer tableLimit = null;
            Integer skip = null;
            if (page.isPresent() || limit.isPresent()) {
                if (page.isPresent() && page.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }
                if (limit.isPresent() && limit.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }

                Integer pageValue = page.orElse(1);
                Integer limitValue = limit.orElse(100);
                tableLimit = pageValue * limitValue;
                skip = (pageValue - 1) * limitValue;
            }

            tableSearchFilters.setMaxResults(tableLimit);
            Collection<TableWrapper> matchingTableWrappers = this.hierarchyNodeTreeSearcher.findTables(
                    connectionWrapper.getTables(), tableSearchFilters);

            List<TableSpec> tableSpecs = matchingTableWrappers
                    .stream()
                    .skip(skip == null ? 0 : skip)
                    .map(TableWrapper::getSpec)
                    .collect(Collectors.toList());

            boolean isEditor = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            boolean isOperator = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);
            List<TableListModel> tableModelsList = tableSpecs.stream()
                    .map(ts -> TableListModel.fromTableSpecificationForListEntry(
                            connectionName, ts, isEditor, isOperator))
                    .collect(Collectors.toList());

            tableModelsList.forEach(listModel -> {
                DomainConnectionTableKey tableStatusKey = new DomainConnectionTableKey(principal.getDataDomainIdentity().getDataDomainCloud(),
                        listModel.getConnectionName(), listModel.getTarget());
                TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                listModel.setDataQualityStatus(currentTableStatus != null ? currentTableStatus.shallowCloneWithoutCheckResultsAndColumns() : null);
            });

            if (tableModelsList.stream().anyMatch(model -> model.getDataQualityStatus() == null)) {
                // the results not loaded yet, we need to wait until the queue is empty
                CompletableFuture<Boolean> waitForLoadTasksFuture = this.tableStatusCache.getQueueEmptyFuture(TableStatusCache.EMPTY_QUEUE_WAIT_TIMEOUT_MS);

                Flux<TableListModel> resultListFilledWithDelay = Mono.fromFuture(waitForLoadTasksFuture)
                        .thenMany(Flux.fromIterable(tableModelsList)
                                .map(tableListModel -> {
                                    if (tableListModel.getDataQualityStatus() == null) {
                                        DomainConnectionTableKey tableStatusKey = new DomainConnectionTableKey(principal.getDataDomainIdentity().getDataDomainCloud(),
                                                tableListModel.getConnectionName(), tableListModel.getTarget());
                                        TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                                        tableListModel.setDataQualityStatus(currentTableStatus != null ? currentTableStatus.shallowCloneWithoutCheckResultsAndColumns() : null);
                                    }
                                    return tableListModel;
                                }));

                return new ResponseEntity<>(resultListFilledWithDelay, HttpStatus.OK); // 200
            }

            return new ResponseEntity<>(Flux.fromStream(tableModelsList.stream()), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the list of tables that are similar to a given table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return List of most similar tables.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/similar", produces = "application/json")
    @ApiOperation(value = "findSimilarTables", notes = "Finds a list of tables that are most similar to a given table", response = SimilarTableModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of similar tables returned", response = SimilarTableModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<SimilarTableModel>>> findSimilarTables(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "limit", value = "The maximum number of similar tables to return. The default result is 50 similar tables.", required = false)
            @RequestParam(required = false) Optional<Integer> limit) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    physicalTableName, true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            final double MAX_DIFFERENCE_PERCENT = 40.0; // the maximum difference
            List<SimilarTableModel> tablesSimilarTo = userHome.findTablesSimilarTo(
                    connectionName, physicalTableName, limit.orElse(50), MAX_DIFFERENCE_PERCENT);

            return new ResponseEntity<>(Flux.fromIterable(tablesSimilarTo), HttpStatus.OK); // 200
        }));
    }


    /**
     * Retrieves the table details given a connection name and a table names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Table specification for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}", produces = "application/json")
    @ApiOperation(value = "getTable", notes = "Return the table specification", response = TableModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table full specification returned", response = TableModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableModel>>> getTable(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            TableModel tableModel = new TableModel() {{
                setConnectionName(connectionWrapper.getName());
                setTableHash(tableSpec.getHierarchyId() != null ? tableSpec.getHierarchyId().hashCode64() : null);
                setSpec(tableSpec);
                setCanEdit(principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
                setYamlParsingError(tableSpec.getYamlParsingError());
            }};

            return new ResponseEntity<>(Mono.just(tableModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the basic table details given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Table basic information for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/basic", produces = "application/json")
    @ApiOperation(value = "getTableBasic", notes = "Return the basic table information", response = TableListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table basic information returned", response = TableListModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableListModel>>> getTableBasic(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            TableListModel tableListModel = TableListModel.fromTableSpecification(
                    connectionWrapper.getName(), tableSpec,
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT),
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

            return new ResponseEntity<>(Mono.just(tableListModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Return the status of profiling the table, which provides hints to the user about which profiling steps were not yet performed
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Table profiling status information for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profilingstatus", produces = "application/json")
    @ApiOperation(value = "getTableProfilingStatus", notes = "Return the status of profiling the table, which provides hints to the user about which profiling steps were not yet performed", response = TableProfilingSetupStatusModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table profiling status information returned", response = TableProfilingSetupStatusModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableProfilingSetupStatusModel>>> getTableProfilingStatus(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            TableProfilingSetupStatusModel statusModel = new TableProfilingSetupStatusModel();
            statusModel.setConnectionName(connectionWrapper.getName());
            statusModel.setTableHash(tableSpec.getHierarchyId().hashCode64());
            statusModel.setTarget(tableSpec.getPhysicalTableName());

            statusModel.setProfilingChecksConfigured(tableSpec.hasAnyChecksConfigured(CheckType.profiling) ||
                    tableSpec.getColumns().values().stream().anyMatch(columnSpec -> columnSpec.hasAnyChecksConfigured(CheckType.profiling)));
            statusModel.setMonitoringChecksConfigured(tableSpec.hasAnyChecksConfigured(CheckType.monitoring) ||
                    tableSpec.getColumns().values().stream().anyMatch(columnSpec -> columnSpec.hasAnyChecksConfigured(CheckType.monitoring)));
            statusModel.setMonitoringChecksConfigured(tableSpec.getTimestampColumns() == null || Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getPartitionByColumn()) ||
                    tableSpec.hasAnyChecksConfigured(CheckType.partitioned) ||
                    tableSpec.getColumns().values().stream().anyMatch(columnSpec -> columnSpec.hasAnyChecksConfigured(CheckType.partitioned)));

            statusModel.setBasicStatisticsCollected(this.statisticsDataService.getMostRecentStatisticsPartitionMonth(
                    connectionWrapper.getName(), tableSpec.getPhysicalTableName(), principal.getDataDomainIdentity()) != null);
            statusModel.setCheckResultsPresent(this.checkResultsDataService.hasAnyRecentCheckResults(
                    connectionWrapper.getName(), tableSpec.getPhysicalTableName(), principal.getDataDomainIdentity()));

            return new ResponseEntity<>(Mono.just(statusModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the table partitioning details given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Table partitioning information for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioning", produces = "application/json")
    @ApiOperation(value = "getTablePartitioning", notes = "Return the table partitioning information", response = TablePartitioningModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table partitioning information returned", response = TablePartitioningModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TablePartitioningModel>>> getTablePartitioning(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            TablePartitioningModel tablePartitioningModel = TablePartitioningModel.fromTableSpecification(
                    connectionWrapper.getName(), tableSpec, principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));

            return new ResponseEntity<>(Mono.just(tablePartitioningModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the default data grouping configuration for a table given a connection name and a table names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Default data grouping configuration for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/defaultgroupingconfiguration", produces = "application/json")
    @ApiOperation(value = "getTableDefaultGroupingConfiguration", notes = "Return the default data grouping configuration for a table.",
            response = DataGroupingConfigurationSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Default data grouping configuration for a table returned", response = DataGroupingConfigurationSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<DataGroupingConfigurationSpec>>> getTableDefaultGroupingConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            DataGroupingConfigurationSpec dataGroupingConfiguration = tableSpec.getDefaultDataGroupingConfiguration();

            return new ResponseEntity<>(Mono.justOrEmpty(dataGroupingConfiguration), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the overridden scheduling group configuration for a table given a connection name, a table name and a scheduling group (schedule name).
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param schedulingGroup Scheduling group name.
     * @return Overridden schedule configuration for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/schedulesoverride/{schedulingGroup}", produces = "application/json")
    @ApiOperation(value = "getTableSchedulingGroupOverride", notes = "Return the schedule override configuration for a table",
            response = CronScheduleSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Overridden schedule configuration for a table returned", response = CronScheduleSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CronScheduleSpec>>> getTableSchedulingGroupOverride(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Check scheduling group (named schedule)") @PathVariable CheckRunScheduleGroup schedulingGroup) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            CronSchedulesSpec schedules = tableSpec.getSchedulesOverride();
            if (schedules == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
            }

            CronScheduleSpec schedule = schedules.getScheduleForCheckSchedulingGroup(schedulingGroup);

            return new ResponseEntity<>(Mono.justOrEmpty(schedule), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the incident grouping configuration given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Incident grouping configuration for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/incidentgrouping", produces = "application/json")
    @ApiOperation(value = "getTableIncidentGrouping", notes = "Return the configuration of incident grouping on a table",
            response = TableIncidentGroupingSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table's incident grouping configuration returned", response = TableIncidentGroupingSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableIncidentGroupingSpec>>> getTableIncidentGrouping(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            TableIncidentGroupingSpec tableIncidentGrouping = tableSpec.getIncidentGrouping();
            if (tableIncidentGrouping == null) {
                tableIncidentGrouping = new TableIncidentGroupingSpec();
            }

            return new ResponseEntity<>(Mono.just(tableIncidentGrouping), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the labels for a table given a connection name and a table names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Labels assigned to the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/labels", produces = "application/json")
    @ApiOperation(value = "getTableLabels", notes = "Return the list of labels assigned to a table", response = LabelSetSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of labels on a table returned", response = LabelSetSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<LabelSetSpec>>> getTableLabels(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            LabelSetSpec labels = tableSpec.getLabels();

            return new ResponseEntity<>(Mono.justOrEmpty(labels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the comments on a table given a connection name and a table names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Comments on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comments", produces = "application/json")
    @ApiOperation(value = "getTableComments", notes = "Return the list of comments added to a table", response = CommentsListSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of comments on a table returned", response = CommentsListSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CommentsListSpec>>> getTableComments(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            CommentsListSpec comments = tableSpec.getComments();

            return new ResponseEntity<>(Mono.justOrEmpty(comments), HttpStatus.OK); // 200
        }));
    }
    
    /**
     * Retrieves the configuration of data quality checks on a table given a connection name and a table names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Data quality checks on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecks", notes = "Return the configuration of table level data quality checks on a table",
            response = TableProfilingCheckCategoriesSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level data quality checks on a table returned", response = TableProfilingCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableProfilingCheckCategoriesSpec>>> getTableProfilingChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            TableProfilingCheckCategoriesSpec checks = tableSpec.getProfilingChecks();
            return new ResponseEntity<>(Mono.justOrEmpty(checks), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of daily data quality monitoring on a table given a connection name and table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Daily data quality monitoring on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/daily", produces = "application/json")
    @ApiOperation(value = "getTableMonitoringChecksDaily", notes = "Return the configuration of daily table level data quality monitoring on a table",
            response = TableDailyMonitoringCheckCategoriesSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of daily table level data quality monitoring on a table returned",
                    response = TableDailyMonitoringCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableDailyMonitoringCheckCategoriesSpec>>> getTableDailyMonitoringChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            TableDailyMonitoringCheckCategoriesSpec dailyMonitoring = tableSpec.getMonitoringChecks().getDaily();
            return new ResponseEntity<>(Mono.justOrEmpty(dailyMonitoring), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of monthly data quality monitoring on a table given a connection name and table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Monthly data quality monitoring on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/monthly", produces = "application/json")
    @ApiOperation(value = "getTableMonitoringChecksMonthly", notes = "Return the configuration of monthly table level data quality monitoring on a table",
            response = TableMonthlyMonitoringCheckCategoriesSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of monthly table level data quality monitoring on a table returned",
                    response = TableMonthlyMonitoringCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableMonthlyMonitoringCheckCategoriesSpec>>> getTableMonitoringChecksMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            TableMonthlyMonitoringCheckCategoriesSpec monthlyMonitoring = tableSpec.getMonitoringChecks().getMonthly();
            return new ResponseEntity<>(Mono.justOrEmpty(monthlyMonitoring), HttpStatus.OK); // 200
        }));
    }
    
    /**
     * Retrieves the configuration of daily data quality partitioned checks on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Daily data quality partitioned checks on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/daily", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksDaily", notes = "Return the configuration of daily table level data quality partitioned checks on a table",
            response = TableDailyPartitionedCheckCategoriesSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level data quality partitioned checks on a table returned",
                    response = TableDailyPartitionedCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableDailyPartitionedCheckCategoriesSpec>>> getTableDailyPartitionedChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            TableDailyPartitionedCheckCategoriesSpec dailyPartitioned = tableSpec.getPartitionedChecks().getDaily();
            return new ResponseEntity<>(Mono.justOrEmpty(dailyPartitioned), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of monthly data quality partitioned checks on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Monthly data quality partitioned checks on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/monthly", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksMonthly", notes = "Return the configuration of monthly table level data quality partitioned checks on a table",
            response = TableMonthlyPartitionedCheckCategoriesSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level data quality partitioned checks on a table returned",
                    response = TableMonthlyPartitionedCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableMonthlyPartitionedCheckCategoriesSpec>>> getTablePartitionedChecksMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            TableMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = tableSpec.getPartitionedChecks().getMonthly();
            return new ResponseEntity<>(Mono.justOrEmpty(monthlyPartitioned), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of data quality profiling checks as a UI friendly model on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return UI friendly data quality profiling check configuration list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecksModel", notes = "Return a UI friendly model of configurations for all table level data quality profiling checks on a table",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level data quality profiling checks on a table returned",
                    response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getTableProfilingChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableOnly(
                    connectionWrapper.getSpec(), clonedTableWithDefaultChecks, userHome);
            AbstractRootChecksContainerSpec checks = clonedTableWithDefaultChecks.getTableCheckRootContainer(CheckType.profiling, null, false);

            CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
                setConnection(connectionWrapper.getName());
                setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
                setCheckType(checks.getCheckType());
                setTimeScale(checks.getCheckTimeScale());
                setEnabled(true);
            }};

            CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                    checks,
                    checkSearchFilters,
                    connectionWrapper.getSpec(),
                    tableSpec,
                     new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                    connectionWrapper.getSpec().getProviderType(),
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of data quality monitoring as a UI friendly model on a table given a connection name, a table name, and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @return UI friendly data quality monitoring configuration list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getTableMonitoringChecksModel", notes = "Return a UI friendly model of configurations for table level data quality monitoring on a table for a given time scale",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level {timeScale} data quality monitoring on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getTableMonitoringChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableOnly(
                    connectionWrapper.getSpec(), clonedTableWithDefaultChecks, userHome);
            AbstractRootChecksContainerSpec checks = clonedTableWithDefaultChecks.getTableCheckRootContainer(CheckType.monitoring, timeScale, false);

            CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
                setConnection(connectionWrapper.getName());
                setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
                setCheckType(checks.getCheckType());
                setTimeScale(checks.getCheckTimeScale());
                setEnabled(true);
            }};

            CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                    checks,
                    checkSearchFilters,
                    connectionWrapper.getSpec(),
                    tableSpec,
                    new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                    connectionWrapper.getSpec().getProviderType(),
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of data quality partitioned checks as a UI friendly model on a table given a connection name, a table name, and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @return UI friendly data quality partitioned check configuration list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksModel", notes = "Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level {timeScale} data quality partitioned checks on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getTablePartitionedChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableOnly(
                    connectionWrapper.getSpec(), clonedTableWithDefaultChecks, userHome);
            AbstractRootChecksContainerSpec checks = clonedTableWithDefaultChecks.getTableCheckRootContainer(CheckType.partitioned, timeScale, false);

            CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
                setConnection(connectionWrapper.getName());
                setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
                setCheckType(checks.getCheckType());
                setTimeScale(checks.getCheckTimeScale());
                setEnabled(true);
            }};

            CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                    checks,
                    checkSearchFilters,
                    connectionWrapper.getSpec(),
                    tableSpec,
                    new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                    connectionWrapper.getSpec().getProviderType(),
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves a simplistic list of data quality profiling checks as a UI friendly model on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Simplistic UI friendly data quality profiling checks list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model/basic", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecksBasicModel", notes = "Return a simplistic UI friendly model of all table level data quality profiling checks on a table",
            response = CheckContainerListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of table level data quality profiling checks on a table returned", response = CheckContainerListModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckContainerListModel>>> getTableProfilingChecksBasicModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false);
            CheckContainerListModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                    checks,
                    new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                    connectionWrapper.getSpec().getProviderType(),
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

            return new ResponseEntity<>(Mono.just(checksBasicModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves a simplistic list of data quality monitoring as a UI friendly model on a table given a connection name, a table name, and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @return Simplistic UI friendly data quality monitoring list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model/basic", produces = "application/json")
    @ApiOperation(value = "getTableMonitoringChecksBasicModel", notes = "Return a simplistic UI friendly model of table level data quality monitoring on a table for a given time scale",
            response = CheckContainerListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of table level {timeScale} data quality monitoring on a table returned", response = CheckContainerListModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckContainerListModel>>> getTableMonitoringChecksBasicModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.monitoring, timeScale, false);
            CheckContainerListModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                    checks,
                    new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                    connectionWrapper.getSpec().getProviderType(),
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

            return new ResponseEntity<>(Mono.just(checksBasicModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of data quality partitioned checks as a UI friendly model on a table given a connection name, a table name, and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @return Simplistic UI friendly data quality partitioned checks list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model/basic", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksBasicModel", notes = "Return a simplistic UI friendly model of table level data quality partitioned checks on a table for a given time scale",
            response = CheckContainerListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of table level {timeScale} data quality partitioned checks on a table returned", response = CheckContainerListModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckContainerListModel>>> getTablePartitionedChecksBasicModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.partitioned, timeScale, false);
            CheckContainerListModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                    checks, new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                    connectionWrapper.getSpec().getProviderType(),
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

            return new ResponseEntity<>(Mono.just(checksBasicModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of data quality profiling checks as a UI friendly model on a table given a connection name and a table name, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param checkCategory  Check category.
     * @param checkName      Check name.
     * @return UI friendly data quality profiling check configuration list on a requested table, filtered by category and check name.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model/filter/{checkCategory}/{checkName}", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecksModelFilter", notes = "Return a UI friendly model of configurations for all table level data quality profiling checks on a table passing a filter",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level data quality profiling checks on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getTableProfilingChecksModelFilter(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable String checkName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableOnly(
                    connectionWrapper.getSpec(), clonedTableWithDefaultChecks, userHome);
            AbstractRootChecksContainerSpec checks = clonedTableWithDefaultChecks.getTableCheckRootContainer(CheckType.profiling, null, false);

            CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
                setConnection(connectionWrapper.getName());
                setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
                setCheckType(checks.getCheckType());
                setTimeScale(checks.getCheckTimeScale());
                setCheckCategory(checkCategory);
                setCheckName(checkName);
                setEnabled(true);
            }};

            CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                    checks,
                    checkSearchFilters,
                    connectionWrapper.getSpec(),
                    tableSpec,
                    new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                    connectionWrapper.getSpec().getProviderType(),
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

            return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of data quality monitoring as a UI friendly model on a table given a connection name, a table name, and a time scale, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @param checkCategory  Check category.
     * @param checkName      Check name.
     * @return UI friendly data quality monitoring configuration list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model/filter/{checkCategory}/{checkName}", produces = "application/json")
    @ApiOperation(value = "getTableMonitoringChecksModelFilter",
            notes = "Return a UI friendly model of configurations for table level data quality monitoring on a table for a given time scale, filtered by category and check name.",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level {timeScale} data quality monitoring on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getTableMonitoringChecksModelFilter(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable String checkName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableOnly(
                    connectionWrapper.getSpec(), clonedTableWithDefaultChecks, userHome);
            AbstractRootChecksContainerSpec checks = clonedTableWithDefaultChecks.getTableCheckRootContainer(CheckType.monitoring, timeScale, false);

            CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
                setConnection(connectionWrapper.getName());
                setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
                setCheckType(checks.getCheckType());
                setTimeScale(checks.getCheckTimeScale());
                setCheckCategory(checkCategory);
                setCheckName(checkName);
                setEnabled(true);
            }};

            CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                    checks,
                    checkSearchFilters,
                    connectionWrapper.getSpec(),
                    tableSpec,
                    new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                    connectionWrapper.getSpec().getProviderType(),
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

            return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of data quality partitioned checks as a UI friendly model on a table given a connection name, a table name, and a time scale, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @param checkCategory  Check category.
     * @param checkName      Check name.
     * @return UI friendly data quality partitioned check configuration list on a requested table, filtered by category and check name.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model/filter/{checkCategory}/{checkName}", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksModelFilter",
            notes = "Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale, filtered by category and check name.",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level {timeScale} data quality partitioned checks on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CheckContainerModel>>> getTablePartitionedChecksModelFilter(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable String checkName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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
            this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableOnly(
                    connectionWrapper.getSpec(), clonedTableWithDefaultChecks, userHome);
            AbstractRootChecksContainerSpec checks = clonedTableWithDefaultChecks.getTableCheckRootContainer(CheckType.partitioned, timeScale, false);

            CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
                setConnection(connectionWrapper.getName());
                setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
                setCheckType(checks.getCheckType());
                setTimeScale(checks.getCheckTimeScale());
                setCheckCategory(checkCategory);
                setCheckName(checkName);
                setEnabled(true);
            }};

            CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                    checks,
                    checkSearchFilters,
                    connectionWrapper.getSpec(),
                    tableSpec,
                    new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                    connectionWrapper.getSpec().getProviderType(),
                    principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns a list of table level statistics for a table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name
     * @return List of table level statistics.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/statistics", produces = "application/json")
    @ApiOperation(value = "getTableStatistics",
            notes = "Returns a list of the profiler (statistics) metrics on a chosen table captured during the most recent statistics collection.",
            response = TableStatisticsModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableStatisticsModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableStatisticsModel>>> getTableStatistics(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    physicalTableName, true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            StatisticsResultsForTableModel mostRecentStatisticsMetricsForTable =
                    this.statisticsDataService.getMostRecentStatisticsForTable(connectionName, physicalTableName,
                            CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME, true, principal.getDataDomainIdentity());

            TableStatisticsModel resultModel = new TableStatisticsModel();
            resultModel.setConnectionName(connectionName);
            resultModel.setTable(physicalTableName);
            resultModel.setStatistics(mostRecentStatisticsMetricsForTable.getMetrics());
            resultModel.setCanCollectStatistics(principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

            resultModel.setCollectTableStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
                setTarget(StatisticsCollectorTarget.table);
                setEnabled(true);
            }});

            resultModel.setCollectTableAndColumnStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
                setEnabled(true);
            }});

            return new ResponseEntity<>(Mono.just(resultModel), HttpStatus.OK);
        }));
    }

    /**
     * Retrieves a UI friendly data quality profiling check configuration list of column-level checks on a requested table.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param tableName         Table name.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @param limit             The limit of results.
     * @return UI friendly data quality profiling check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/profiling/model", produces = "application/json")
    @ApiOperation(value = "getTableColumnsProfilingChecksModel", notes = "Return a UI friendly model of configurations for column-level data quality profiling checks on a table",
            response = CheckConfigurationModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality profiling checks on a schema returned", response = CheckConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckConfigurationModel>>> getTableColumnsProfilingChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(value = "Column name pattern", required = false) @RequestParam(required = false)
            Optional<String> columnNamePattern,
            @ApiParam(value = "Column data-type", required = false) @RequestParam(required = false)
            Optional<String> columnDataType,
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

            PhysicalTableName schemaTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, schemaTableName);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<CheckConfigurationModel> checkConfigurationModels = this.tableService.getCheckConfigurationsOnTable(
                    connectionName, schemaTableName, new CheckContainerTypeModel(CheckType.profiling, null),
                    columnNamePattern.orElse(null),
                    columnDataType.orElse(null),
                    CheckTarget.column,
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
     * Retrieves a UI friendly data quality monitoring check configuration list of column-level checks on a requested table.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param tableName         Table name.
     * @param timeScale         Check time-scale.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @param limit             The limit of results.
     * @return UI friendly data quality monitoring check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/monitoring/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getTableColumnsMonitoringChecksModel", notes = "Return a UI friendly model of configurations for column-level data quality monitoring checks on a table",
            response = CheckConfigurationModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality monitoring checks on a schema returned", response = CheckConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckConfigurationModel>>> getTableColumnsMonitoringChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Check time-scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Column name pattern", required = false) @RequestParam(required = false)
            Optional<String> columnNamePattern,
            @ApiParam(value = "Column data-type", required = false) @RequestParam(required = false)
            Optional<String> columnDataType,
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

            PhysicalTableName schemaTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, schemaTableName);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<CheckConfigurationModel> checkConfigurationModels = this.tableService.getCheckConfigurationsOnTable(
                    connectionName, schemaTableName, new CheckContainerTypeModel(CheckType.monitoring, timeScale),
                    columnNamePattern.orElse(null),
                    columnDataType.orElse(null),
                    CheckTarget.column,
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
     * Retrieves a UI friendly data quality partitioned check configuration list of column-level checks on a requested table.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param tableName         Table name.
     * @param timeScale         Check time-scale.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @param limit             The limit of results.
     * @return UI friendly data quality partitioned check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/partitioned/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getTableColumnsPartitionedChecksModel", notes = "Return a UI friendly model of configurations for column-level data quality partitioned checks on a table",
            response = CheckConfigurationModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality partitioned checks on a schema returned", response = CheckConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckConfigurationModel>>> getTableColumnsPartitionedChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Check time-scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Column name pattern", required = false) @RequestParam(required = false)
            Optional<String> columnNamePattern,
            @ApiParam(value = "Column data-type", required = false) @RequestParam(required = false)
            Optional<String> columnDataType,
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

            PhysicalTableName schemaTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, schemaTableName);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<CheckConfigurationModel> checkConfigurationModels = this.tableService.getCheckConfigurationsOnTable(
                    connectionName, schemaTableName, new CheckContainerTypeModel(CheckType.partitioned, timeScale),
                    columnNamePattern.orElse(null),
                    columnDataType.orElse(null),
                    CheckTarget.column,
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
     * Retrieves the list of profiling checks templates on the given table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param checkCategory  (Optional) Filter on check category.
     * @param checkName      (Optional) Filter on check name.
     * @return Data quality checks templates on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/profiling", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecksTemplates", notes = "Return available data quality checks on a requested table.", response = CheckTemplate[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a table returned", response = CheckTemplate[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckTemplate>>> getTableProfilingChecksTemplates(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            PhysicalTableName fullTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, fullTableName);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<CheckTemplate> checkTemplates = this.tableService.getCheckTemplates(
                    connectionName, fullTableName, CheckType.profiling,
                    null, checkCategory.orElse(null), checkName.orElse(null), principal);

            return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the list of monitoring checks templates on the given table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Check time scale.
     * @param checkCategory  (Optional) Filter on check category.
     * @param checkName      (Optional) Filter on check name.
     * @return Data quality checks templates on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/monitoring/{timeScale}", produces = "application/json")
    @ApiOperation(value = "getTableMonitoringChecksTemplates", notes = "Return available data quality checks on a requested table.", response = CheckTemplate[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a table returned", response = CheckTemplate[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckTemplate>>> getTableMonitoringChecksTemplates(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            PhysicalTableName fullTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, fullTableName);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<CheckTemplate> checkTemplates = this.tableService.getCheckTemplates(
                    connectionName, fullTableName, CheckType.monitoring,
                    timeScale, checkCategory.orElse(null), checkName.orElse(null), principal);

            return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the list of partitioned checks templates on the given table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Check time scale.
     * @param checkCategory  (Optional) Filter on check category.
     * @param checkName      (Optional) Filter on check name.
     * @return Data quality checks templates on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/partitioned/{timeScale}", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksTemplates", notes = "Return available data quality checks on a requested table.", response = CheckTemplate[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a table returned", response = CheckTemplate[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckTemplate>>> getTablePartitionedChecksTemplates(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            PhysicalTableName fullTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, fullTableName);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<CheckTemplate> checkTemplates = this.tableService.getCheckTemplates(
                    connectionName, fullTableName, CheckType.partitioned,
                    timeScale, checkCategory.orElse(null), checkName.orElse(null), principal);

            return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
        }));
    }


    /**
     * Creates (adds) a new table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableSpec      Table specification.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTable", notes = "Creates a new table (adds a table metadata)", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New table successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Table with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> createTable(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table specification") @RequestBody TableSpec tableSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableList tables = connectionWrapper.getTables();
            TableWrapper existingTableWrapper = tables.getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (existingTableWrapper != null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a table with this name already exists
            }

            TableWrapper tableWrapper = tables.createAndAddNew(new PhysicalTableName(schemaName, tableName));
            tableWrapper.setSpec(tableSpec);
            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Updates an existing table, updating the whole specification.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableSpec      Table specification.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTable", notes = "Updates an existing table specification, changing all the fields", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateTable(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Full table specification") @RequestBody TableSpec tableSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        TableList tables = connectionWrapper.getTables();
                        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                        if (tableWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
                        }

                        // TODO: validate the tableSpec
                        tableWrapper.setSpec(tableSpec);
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the basic fields of an existing table.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableListModel Basic table model with the new values.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/basic", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableBasic", notes = "Updates the basic field of an existing table, changing only the most important fields.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateTableBasic(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table basic model with the updated settings") @RequestBody TableListModel tableListModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            if (tableListModel.getTarget() == null ||
                    !Objects.equals(schemaName, tableListModel.getTarget().getSchemaName()) ||
                    !Objects.equals(tableName, tableListModel.getTarget().getTableName())) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 400 - wrong values
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        TableList tables = connectionWrapper.getTables();
                        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                        if (tableWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
                        }

                        // TODO: validate the tableSpec
                        TableSpec tableSpec = tableWrapper.getSpec();
                        tableListModel.copyToTableSpecification(tableSpec);
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the partitioning fields of an existing table.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tablePartitioningModel Table partitioning model with the new values.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioning", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTablePartitioning", notes = "Updates the table partitioning configuration of an existing table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table partitioning successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateTablePartitioning(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table partitioning model with the updated settings") @RequestBody TablePartitioningModel tablePartitioningModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            if (tablePartitioningModel.getTarget() == null ||
                    !Objects.equals(schemaName, tablePartitioningModel.getTarget().getSchemaName()) ||
                    !Objects.equals(tableName, tablePartitioningModel.getTarget().getTableName())) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 400 - wrong values
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        TableList tables = connectionWrapper.getTables();
                        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                        if (tableWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
                        }

                        TableSpec tableSpec = tableWrapper.getSpec();
                        tablePartitioningModel.copyToTableSpecification(tableSpec);
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the default data grouping configuration of an existing table.
     * @param connectionName         Connection name.
     * @param schemaName             Schema name.
     * @param tableName              Table name.
     * @param dataGroupingConfigurationSpec New default data grouping configuration or null (an empty optional).
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/defaultgroupingconfiguration", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableDefaultGroupingConfiguration", notes = "Updates the default data grouping configuration at a table level.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's default data grouping configuration successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateTableDefaultGroupingConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Default data grouping configuration to store or an empty object to clear the data grouping configuration on a table level")
            @RequestBody DataGroupingConfigurationSpec dataGroupingConfigurationSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        TableList tables = connectionWrapper.getTables();
                        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                        if (tableWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
                        }

                        TableSpec tableSpec = tableWrapper.getSpec();
                        if (dataGroupingConfigurationSpec != null) {
                            if (!Strings.isNullOrEmpty(tableSpec.getDefaultGroupingName())) {
                                tableSpec.getGroupings().remove(tableSpec.getDefaultGroupingName());
                                tableSpec.getGroupings().put(tableSpec.getDefaultGroupingName(), dataGroupingConfigurationSpec);
                            } else {
                                tableSpec.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
                                tableSpec.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);
                            }
                        } else {
                            if (tableSpec.getDefaultGroupingName() != null) {
                                tableSpec.getGroupings().remove(tableSpec.getDefaultGroupingName());
                                tableSpec.setDefaultGroupingName(null);
                            }
                        }
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the overridden schedule configuration of an existing table for a named scheduling group.
     * @param connectionName              Connection name.
     * @param schemaName                  Schema name.
     * @param tableName                   Table name.
     * @param cronScheduleSpec       New monitoring schedule configuration or an emtpy optional to clear the schedule configuration.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/schedulesoverride/{schedulingGroup}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableSchedulingGroupOverride",
            notes = "Updates the overridden schedule configuration of an existing table for a named schedule group (named schedule for checks using the same time scale).", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's overridden schedule configuration successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateTableSchedulingGroupOverride(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Check scheduling group (named schedule)") @PathVariable CheckRunScheduleGroup schedulingGroup,
            @ApiParam("Table's overridden schedule configuration to store or an empty object to clear the schedule configuration on a table")
                @RequestBody CronScheduleSpec cronScheduleSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        TableList tables = connectionWrapper.getTables();
                        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                        if (tableWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
                        }

                        TableSpec tableSpec = tableWrapper.getSpec();
                        CronSchedulesSpec schedules = tableSpec.getSchedulesOverride();
                        if (schedules == null) {
                            schedules = new CronSchedulesSpec();
                            tableSpec.setSchedulesOverride(schedules);
                        }
                        schedules.setScheduleForCheckSchedulingGroup(cronScheduleSpec, schedulingGroup);

                        boolean scheduleUpdated = tableSpec.isDirty();
                        userHomeContext.flush();

                        if (scheduleUpdated && this.jobSchedulerService != null) {
                            this.jobSchedulerService.triggerMetadataSynchronization(principal.getDataDomainIdentity().getDataDomainCloud());
                        }

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of incident grouping on an existing table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param incidentGrouping   New configuration of the table's incident grouping
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/incidentgrouping", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableIncidentGrouping", notes = "Updates the configuration of incident grouping on a table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's incident grouping configuration successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateTableIncidentGrouping(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("New configuration of the table's incident grouping")
            @RequestBody TableIncidentGroupingSpec incidentGrouping) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        TableList tables = connectionWrapper.getTables();
                        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                        if (tableWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
                        }

                        TableSpec tableSpec = tableWrapper.getSpec();
                        tableSpec.setIncidentGrouping(incidentGrouping);
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the list of labels of an existing table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param labelSetSpec   New list of labels or an empty optional to clear the list of labels.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/labels", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableLabels", notes = "Updates the list of assigned labels of an existing table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's labels successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableLabels(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("List of labels to attach (replace) on a table or an empty object to clear the list of labels on a table")
            @RequestBody LabelSetSpec labelSetSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        TableList tables = connectionWrapper.getTables();
                        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                        if (tableWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
                        }

                        tableWrapper.getSpec().setLabels(labelSetSpec);
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the list of comments of an existing table.
     * @param connectionName   Connection name.
     * @param schemaName       Schema name.
     * @param tableName        Table name.
     * @param commentsListSpec New list of comments or an empty optional to clear the list of comments.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comments", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComments", notes = "Updates the list of comments on an existing table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's comments successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableComments(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("List of comments to attach (replace) on a table or an empty object to clear the list of comments on a table")
            @RequestBody CommentsListSpec commentsListSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        TableList tables = connectionWrapper.getTables();
                        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                        if (tableWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
                        }

                        tableWrapper.getSpec().setComments(commentsListSpec);
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    protected <T extends AbstractRootChecksContainerSpec> boolean updateTableGenericChecks(
            DqoUserPrincipal principal,
            Consumer<TableSpec> tableSpecUpdater,
            String connectionName,
            String schemaName,
            String tableName) {
        return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                    UserHome userHome = userHomeContext.getUserHome();

                    ConnectionList connections = userHome.getConnections();
                    ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                    if (connectionWrapper == null) {
                        return false;
                    }

                    TableList tables = connectionWrapper.getTables();
                    TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                    if (tableWrapper == null) {
                        return false;
                    }

                    TableSpec tableSpec = tableWrapper.getSpec();
                    tableSpecUpdater.accept(tableSpec);
                    userHomeContext.flush();

                    return true;
                });
    }

    /**
     * Updates the configuration of table level data quality profiling checks of an existing table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name.
     * @param tableProfilingCheckCategoriesSpec New configuration of the data quality profiling checks on the table level.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableProfilingChecks", notes = "Updates the list of table level data quality profiling checks on an existing table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table level data quality profiling checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableProfilingChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Configuration of table level data quality profiling checks to store or an empty object to remove all data quality profiling checks on the table level (column level profiling checks are preserved).")
            @RequestBody TableProfilingCheckCategoriesSpec tableProfilingCheckCategoriesSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecks(
                principal,
                spec -> {
                    if (tableProfilingCheckCategoriesSpec != null) {
                        spec.setTableCheckRootContainer(tableProfilingCheckCategoriesSpec);
                    } else {
                        spec.setProfilingChecks(new TableProfilingCheckCategoriesSpec()); // it is never empty...
                    }
                },
                connectionName,
                schemaName,
                tableName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        }));
    }

    /**
     * Updates the configuration of daily table level data quality monitoring of an existing table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name.
     * @param tableDailyMonitoringSpec     New configuration of the daily data quality monitoring on the table level.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableMonitoringChecksDaily", notes = "Updates the list of daily table level data quality monitoring on an existing table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Daily table level data quality monitoring successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableDailyMonitoringChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Configuration of daily table level data quality monitoring to store or an empty object to remove all data quality monitoring on the table level (column level monitoring are preserved).")
            @RequestBody TableDailyMonitoringCheckCategoriesSpec tableDailyMonitoringSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecks(
                principal,
                spec -> spec.getMonitoringChecks().setDaily(tableDailyMonitoringSpec),
                connectionName,
                schemaName,
                tableName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        }));
    }

    /**
     * Updates the configuration of monthly table level data quality monitoring of an existing table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name.
     * @param tableMonthlyMonitoringSpec     New configuration of the monthly data quality monitoring on the table level.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableMonitoringChecksMonthly", notes = "Updates the list of monthly table level data quality monitoring on an existing table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Monthly table level data quality monitoring successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableMonitoringChecksMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Configuration of monthly table level data quality monitoring to store or an empty object to remove all data quality monitoring on the table level (column level monitoring are preserved).")
            @RequestBody TableMonthlyMonitoringCheckCategoriesSpec tableMonthlyMonitoringSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecks(
                principal,
                spec -> spec.getMonitoringChecks().setMonthly(tableMonthlyMonitoringSpec),
                connectionName,
                schemaName,
                tableName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        }));
    }

    /**
     * Updates the configuration of daily table level data quality partitioned checks of an existing table.
     * @param connectionName                  Connection name.
     * @param schemaName                      Schema name.
     * @param tableName                       Table name.
     * @param tableDailyPartitionedSpec New configuration of the daily data quality partitioned checks on the table level.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTablePartitionedChecksDaily", notes = "Updates the list of daily table level data quality partitioned checks on an existing table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Daily table level data quality monitoring successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTablePartitionedChecksDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Configuration of daily table level data quality partitioned checks to store or an empty object to remove all data quality partitioned checks on the table level (column level partitioned checks are preserved).")
            @RequestBody TableDailyPartitionedCheckCategoriesSpec tableDailyPartitionedSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecks(
                principal,
                spec -> spec.getPartitionedChecks().setDaily(tableDailyPartitionedSpec),
                connectionName,
                schemaName,
                tableName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        }));
    }

    /**
     * Updates the configuration of monthly table level data quality partitioned checks of an existing table.
     * @param connectionName                  Connection name.
     * @param schemaName                      Schema name.
     * @param tableName                       Table name.
     * @param tableMonthlyPartitionedSpec New configuration of the monthly data quality partitioned checks on the table level.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTablePartitionedChecksMonthly", notes = "Updates the list of monthly table level data quality partitioned checks on an existing table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Monthly table level data quality partitioned checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTablePartitionedChecksMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Configuration of monthly table level data quality partitioned checks to store or an empty object to remove all data quality partitioned checks on the table level (column level partitioned checks are preserved).")
            @RequestBody TableMonthlyPartitionedCheckCategoriesSpec tableMonthlyPartitionedSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            boolean success = this.updateTableGenericChecks(
                    principal,
                    spec -> spec.getPartitionedChecks().setMonthly(tableMonthlyPartitionedSpec),
                    connectionName,
                    schemaName,
                    tableName
            );

            if (success) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
            } else {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }
        }));
    }

    protected <T extends AbstractRootChecksContainerSpec> boolean updateTableGenericChecksModel(
            DqoUserPrincipal principal,
            Function<TableSpec, T> tableSpecToRootCheck,
            String connectionName,
            String schemaName,
            String tableName,
            CheckContainerModel checkContainerModel) {
        return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                    UserHome userHome = userHomeContext.getUserHome();

                    ConnectionList connections = userHome.getConnections();
                    ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                    if (connectionWrapper == null) {
                        return false;
                    }

                    TableList tables = connectionWrapper.getTables();
                    TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                    if (tableWrapper == null) {
                        return false;
                    }

                    TableSpec tableSpec = tableWrapper.getSpec();
                    T checksToUpdate = tableSpecToRootCheck.apply(tableSpec);
                    if (checksToUpdate == null) {
                        return false;
                    }

                    if (checkContainerModel != null) {
                        this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, checksToUpdate, tableSpec);
                        tableSpec.setTableCheckRootContainer(checksToUpdate);
                    } else {
                        // we cannot just remove all checks because the model is a patch, no changes in the patch means no changes to the object
                    }

                    userHomeContext.flush();
                    return true;
                });
    }

    /**
     * Updates the data quality profiling check specification on an existing table from a check model with a patch of changes.
     * @param connectionName           Connection name.
     * @param schemaName               Schema name.
     * @param tableName                Table name.
     * @param checkContainerModel      New configuration of the data quality checks on the table level provided as a model. The model may contain only a subset of data quality dimensions or checks. Only those profiling checks that are present in the model are updated, the others are preserved without any changes.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableProfilingChecksModel", notes = "Updates the data quality profiling checks from a model that contains a patch with changes.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table level data quality profiling checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableProfilingChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Model with the changes to be applied to the data quality profiling checks configuration.")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            boolean success = this.updateTableGenericChecksModel(
                    principal,
                    TableSpec::getProfilingChecks,
                    connectionName,
                    schemaName,
                    tableName,
                    checkContainerModel
            );

            if (success) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
            }
            else {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }
        }));
    }

    /**
     * Updates the data quality monitoring specification on an existing table for a given time scale from a check model with a patch of changes.
     * @param connectionName           Connection name.
     * @param schemaName               Schema name.
     * @param tableName                Table name.
     * @param timeScale                Time scale.
     * @param checkContainerModel      New configuration of the data quality monitoring on the table level provided as a model. The model may contain only a subset of data quality dimensions or checks. Only those monitoring that are present in the model are updated, the others are preserved without any changes.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableMonitoringChecksModel", notes = "Updates the data quality monitoring from a model that contains a patch with changes.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table level data quality monitoring successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found or invalid time scale"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateTableMonitoringChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Model with the changes to be applied to the data quality monitoring configuration.")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            boolean success = this.updateTableGenericChecksModel(
                    principal,
                    spec -> spec.getTableCheckRootContainer(CheckType.monitoring, timeScale, true),
                    connectionName,
                    schemaName,
                    tableName,
                    checkContainerModel
            );

            if (success) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
            }
            else {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }
        }));
    }

    /**
     * Updates the data quality partitioned checks specification on an existing table for a given time scale from a check model with a patch of changes.
     * @param connectionName           Connection name.
     * @param schemaName               Schema name.
     * @param tableName                Table name.
     * @param timeScale                Time scale.
     * @param checkContainerModel      New configuration of the data quality partitioned checks on the table level provided as a model. The model may contain only a subset of data quality dimensions or checks. Only those partitioned checks that are present in the model are updated, the others are preserved without any changes.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTablePartitionedChecksModel", notes = "Updates the data quality partitioned checks from a model that contains a patch with changes.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table level data quality partitioned checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found or invalid time scale"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<?>>> updateTablePartitionedChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Model with the changes to be applied to the data quality partitioned checks configuration.")
            @RequestBody CheckContainerModel checkContainerModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            boolean success = this.updateTableGenericChecksModel(
                    principal,
                    spec -> spec.getTableCheckRootContainer(CheckType.partitioned, timeScale, true),
                    connectionName,
                    schemaName,
                    tableName,
                    checkContainerModel
            );

            if (success) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
            }
            else {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }
        }));
    }

    /**
     * Deletes a table metadata.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Deferred operations job id.
     */
    @DeleteMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}", produces = "application/json")
    @ApiOperation(value = "deleteTable", notes = "Deletes a table", response = DqoQueueJobId.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table successfully deleted", response = DqoQueueJobId.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<DqoQueueJobId>>> deleteTable(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName),
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        TableList tables = connectionWrapper.getTables();
                        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
                        if (tableWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
                        }

                        PushJobResult<DeleteStoredDataResult> backgroundJob = this.tableService.deleteTable(
                                connectionName, tableWrapper.getPhysicalTableName(), principal);

                        return new ResponseEntity<>(Mono.just(backgroundJob.getJobId()), HttpStatus.OK); // 200
                    });
        }));
    }
}
