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
package ai.dqo.services.check.mapping;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTarget;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.ExecutionContextFactory;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.ConnectionSearchFilters;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.services.check.mapping.models.*;
import ai.dqo.services.check.mapping.models.column.AllColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.ColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.TableColumnChecksModel;
import ai.dqo.services.check.mapping.models.table.AllTableChecksModel;
import ai.dqo.services.check.mapping.models.table.SchemaTableChecksModel;
import ai.dqo.services.check.mapping.models.table.TableChecksModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AllChecksModelFactoryImpl implements AllChecksModelFactory {
    ExecutionContextFactory executionContextFactory;
    HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    SpecToModelCheckMappingService specToModelCheckMappingService;

    @Autowired
    public AllChecksModelFactoryImpl(ExecutionContextFactory executionContextFactory,
                                     HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                     SpecToModelCheckMappingService specToModelCheckMappingService) {
        this.executionContextFactory = executionContextFactory;
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.specToModelCheckMappingService = specToModelCheckMappingService;
    }

    /**
     * Creates a list of {@link AllChecksModel}s based on provided filters.
     *
     * @param checkSearchFilters Check search filters.
     * @return List of {@link AllChecksModel}s (by connections) fitting the filters.
     */
    @Override
    public List<AllChecksModel> fromCheckSearchFilters(CheckSearchFilters checkSearchFilters) {
        ExecutionContext executionContext = this.executionContextFactory.create();
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        connectionSearchFilters.setConnectionName(checkSearchFilters.getConnectionName());

        Collection<ConnectionSpec> connectionSpecs = this.hierarchyNodeTreeSearcher.findConnections(userHome, connectionSearchFilters);
        if (connectionSpecs.isEmpty()) {
            // No connections matching the filter.
            return new ArrayList<>();
        }

        List<AllChecksModel> uiConnectionPatches = connectionSpecs.stream()
                .map(connectionSpec -> userHome.getConnections().getByObjectName(connectionSpec.getConnectionName(), true))
                .map(connectionWrapper -> this.getAllChecksForConnection(connectionWrapper, checkSearchFilters, executionContext))
                .filter(allChecksModel ->
                        (allChecksModel.getColumnChecksModel() != null && !allChecksModel.getColumnChecksModel().getTableColumnChecksModels().isEmpty())
                                || (allChecksModel.getTableChecksModel() != null && !allChecksModel.getTableChecksModel().getSchemaTableChecksModels().isEmpty()))
                .collect(Collectors.toList());

        return uiConnectionPatches;
    }

    protected AllChecksModel getAllChecksForConnection(ConnectionWrapper connectionWrapper,
                                                       CheckSearchFilters checkSearchFilters,
                                                       ExecutionContext executionContext) {
        AllChecksModel allChecksModel = new AllChecksModel();
        allChecksModel.setConnectionName(connectionWrapper.getName());

        // TODO: Add templates.

        CheckTarget checkTarget = checkSearchFilters.getCheckTarget();

        if (checkTarget != CheckTarget.column) {
            AllTableChecksModel allTableChecksModel = this.getAllTableChecksForConnection(
                    connectionWrapper, checkSearchFilters, executionContext);
            allChecksModel.setTableChecksModel(allTableChecksModel);
        }

        if (checkTarget != CheckTarget.table) {
            AllColumnChecksModel columnChecksModel = this.getAllColumnChecksForConnection(
                    connectionWrapper, checkSearchFilters, executionContext);
            allChecksModel.setColumnChecksModel(columnChecksModel);
        }

        return allChecksModel;
    }

    protected AllTableChecksModel getAllTableChecksForConnection(ConnectionWrapper connectionWrapper,
                                                                 CheckSearchFilters checkSearchFilters,
                                                                 ExecutionContext executionContext) {
        AllTableChecksModel allTableChecksModel = new AllTableChecksModel();
        Collection<TableWrapper> tableWrappers = this.hierarchyNodeTreeSearcher
                .findTables(connectionWrapper, checkSearchFilters);

        // TODO: Add templates

        List<SchemaTableChecksModel> schemasChecks = tableWrappers.stream()
                .map(tableWrapper -> tableWrapper.getPhysicalTableName().getSchemaName())
                .distinct()
                .map(schemaName -> tableWrappers.stream()
                        .filter(tableWrapper -> tableWrapper.getPhysicalTableName().getSchemaName().equals(schemaName))
                        .collect(Collectors.toList()))
                .map(tables -> this.getSchemaTableCheckModelForTables(connectionWrapper.getSpec(),
                        tables, checkSearchFilters, executionContext))
                .filter(schemaTableChecksModel -> !schemaTableChecksModel.getTableChecksModels().isEmpty())
                .collect(Collectors.toList());

        allTableChecksModel.setSchemaTableChecksModels(schemasChecks);
        return allTableChecksModel;
    }

    protected SchemaTableChecksModel getSchemaTableCheckModelForTables(ConnectionSpec connectionSpec,
                                                                       List<TableWrapper> tableWrappers,
                                                                       CheckSearchFilters checkSearchFilters,
                                                                       ExecutionContext executionContext) {
        String schemaName = tableWrappers.stream().findAny().get().getPhysicalTableName().getSchemaName();
        SchemaTableChecksModel schemaTableChecks = new SchemaTableChecksModel();
        schemaTableChecks.setSchemaName(schemaName);

        // TODO: Add templates.

        List<TableChecksModel> tableChecksModels = tableWrappers.stream()
                .map(tableWrapper -> getTableChecksModelForTable(connectionSpec, tableWrapper, checkSearchFilters, executionContext))
                .filter(tableChecksModel -> !tableChecksModel.getCheckContainers().isEmpty())
                .collect(Collectors.toList());
        schemaTableChecks.setTableChecksModels(tableChecksModels);
        return schemaTableChecks;
    }

    protected TableChecksModel getTableChecksModelForTable(ConnectionSpec connectionSpec,
                                                           TableWrapper tableWrapper,
                                                           CheckSearchFilters checkSearchFilters,
                                                           ExecutionContext executionContext) {
        String tableName = tableWrapper.getPhysicalTableName().getTableName();
        TableChecksModel tableChecksModel = new TableChecksModel();
        tableChecksModel.setTableName(tableName);

        // TODO: Add templates

        TableSpec tableSpec = tableWrapper.getSpec();
        tableChecksModel.setTableLevelFilter(tableSpec.getFilter());

        List<CheckType> checkTypes = this.getPossibleCheckTypes(checkSearchFilters.getCheckType());
        List<CheckTimeScale> timeScales = this.getPossibleCheckTimeScales(checkSearchFilters.getTimeScale());

        Map<CheckContainerTypeModel, AbstractRootChecksContainerSpec> checkContainers = new HashMap<>();
        for (CheckType checkType : checkTypes) {
            if (checkType == CheckType.PROFILING) {
                AbstractRootChecksContainerSpec checkContainer = tableSpec.getTableCheckRootContainer(checkType, null, false);
                checkContainers.put(new CheckContainerTypeModel(checkType, null), checkContainer);
            }
            else {
                for (CheckTimeScale timeScale : timeScales) {
                    AbstractRootChecksContainerSpec checkContainer = tableSpec.getTableCheckRootContainer(checkType, timeScale, false);
                    checkContainers.put(new CheckContainerTypeModel(checkType, timeScale), checkContainer);
                }
            }
        }

        Map<CheckContainerTypeModel, CheckContainerModel> checkContainerModels = checkContainers.entrySet().stream()
                .map(checkContainerPair -> new AbstractMap.SimpleEntry<>(
                        checkContainerPair.getKey(),
                        this.specToModelCheckMappingService.createModel(
                                checkContainerPair.getValue(),
                                checkSearchFilters,
                                connectionSpec,
                                tableSpec,
                                executionContext,
                                connectionSpec.getProviderType())
                )).map(uiCheckContainerModelPair -> new AbstractMap.SimpleEntry<>(
                        uiCheckContainerModelPair.getKey(),
                        pruneCheckContainerModel(
                                uiCheckContainerModelPair.getValue(),
                                checkSearchFilters)
                )).filter(prunedPair -> prunedPair.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        tableChecksModel.setCheckContainers(checkContainerModels);
        return tableChecksModel;
    }

    protected AllColumnChecksModel getAllColumnChecksForConnection(ConnectionWrapper connectionWrapper,
                                                                   CheckSearchFilters checkSearchFilters,
                                                                   ExecutionContext executionContext) {
        AllColumnChecksModel allColumnChecksModel = new AllColumnChecksModel();
        Collection<TableWrapper> tableWrappers = this.hierarchyNodeTreeSearcher
                .findTables(connectionWrapper, checkSearchFilters);

        // TODO: Add templates

        List<TableColumnChecksModel> tableColumnChecksModels = tableWrappers.stream()
                .map(table -> this.getTableColumnCheckModelForTable(connectionWrapper.getSpec(),
                        table, checkSearchFilters, executionContext))
                .filter(tableColumnChecksModel -> !tableColumnChecksModel.getColumnChecksModels().isEmpty())
                .collect(Collectors.toList());

        allColumnChecksModel.setTableColumnChecksModels(tableColumnChecksModels);
        return allColumnChecksModel;
    }

    protected TableColumnChecksModel getTableColumnCheckModelForTable(ConnectionSpec connectionSpec,
                                                                      TableWrapper tableWrapper,
                                                                      CheckSearchFilters checkSearchFilters,
                                                                      ExecutionContext executionContext) {
        PhysicalTableName schemaTableName = tableWrapper.getPhysicalTableName();
        TableColumnChecksModel tableColumnChecksModel = new TableColumnChecksModel();
        tableColumnChecksModel.setSchemaTableName(schemaTableName);

        TableSpec tableSpec = tableWrapper.getSpec();
        tableColumnChecksModel.setTableLevelFilter(tableSpec.getFilter());

        // TODO: Add templates.

        List<ColumnChecksModel> columnChecksModels = tableSpec.getColumns().entrySet().stream()
                .filter(colToSpec ->
                        (checkSearchFilters.getColumnName() == null || colToSpec.getKey().equals(checkSearchFilters.getColumnName()))
                                && (checkSearchFilters.getColumnNullable() == null || colToSpec.getValue().getTypeSnapshot().getNullable() == checkSearchFilters.getColumnNullable())
                                && (checkSearchFilters.getColumnDataType() == null || colToSpec.getValue().getTypeSnapshot().getColumnType().equals(checkSearchFilters.getColumnDataType()))
                ).map(columnNameToSpec -> getColumnChecksModelForColumn(
                        connectionSpec,
                        tableSpec,
                        columnNameToSpec.getKey(),
                        columnNameToSpec.getValue(),
                        checkSearchFilters,
                        executionContext))
                .filter(columnChecksModel -> !columnChecksModel.getCheckContainers().isEmpty())
                .collect(Collectors.toList());
        tableColumnChecksModel.setColumnChecksModels(columnChecksModels);
        return tableColumnChecksModel;
    }

    protected ColumnChecksModel getColumnChecksModelForColumn(ConnectionSpec connectionSpec,
                                                              TableSpec tableSpec,
                                                              String columnName,
                                                              ColumnSpec columnSpec,
                                                              CheckSearchFilters checkSearchFilters,
                                                              ExecutionContext executionContext) {
        ColumnChecksModel columnChecksModel = new ColumnChecksModel();
        columnChecksModel.setColumnName(columnName);

        // TODO: Add templates

        List<CheckType> checkTypes = this.getPossibleCheckTypes(checkSearchFilters.getCheckType());
        List<CheckTimeScale> timeScales = this.getPossibleCheckTimeScales(checkSearchFilters.getTimeScale());

        Map<CheckContainerTypeModel, AbstractRootChecksContainerSpec> checkContainers = new HashMap<>();
        for (CheckType checkType : checkTypes) {
            if (checkType == CheckType.PROFILING) {
                AbstractRootChecksContainerSpec checkContainer = columnSpec.getColumnCheckRootContainer(checkType, null, false);
                checkContainers.put(new CheckContainerTypeModel(checkType, null), checkContainer);
            }
            else {
                for (CheckTimeScale timeScale : timeScales) {
                    AbstractRootChecksContainerSpec checkContainer = columnSpec.getColumnCheckRootContainer(checkType, timeScale, false);
                    checkContainers.put(new CheckContainerTypeModel(checkType, timeScale), checkContainer);
                }
            }
        }

        Map<CheckContainerTypeModel, CheckContainerModel> checkContainerModels = checkContainers.entrySet().stream()
                .map(checkContainerPair -> new AbstractMap.SimpleEntry<>(
                        checkContainerPair.getKey(),
                        this.specToModelCheckMappingService.createModel(
                            checkContainerPair.getValue(),
                            checkSearchFilters,
                            connectionSpec,
                            tableSpec,
                            executionContext,
                            connectionSpec.getProviderType())
                )).map(uiCheckContainerModelPair -> new AbstractMap.SimpleEntry<>(
                        uiCheckContainerModelPair.getKey(),
                        pruneCheckContainerModel(
                            uiCheckContainerModelPair.getValue(),
                            checkSearchFilters)
                )).filter(prunedPair -> prunedPair.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        columnChecksModel.setCheckContainers(checkContainerModels);
        return columnChecksModel;
    }

    /**
     * Gets the {@link CheckContainerModel} containing only the information requested in the {@link CheckSearchFilters}.
     * TODO: Reconsider the dual model and possibly relocate this snippet to the time of model construction.
     * @param baseModel          Check container model staged for pruning.
     * @param checkSearchFilters Filters by which the model tree should be pruned.
     * @return Check container model comprised of information requested in the filters.
     */
    protected CheckContainerModel pruneCheckContainerModel(
            CheckContainerModel baseModel,
            CheckSearchFilters checkSearchFilters) {
        CheckContainerModel result = new CheckContainerModel();
        for (QualityCategoryModel categoryModel: baseModel.getCategories()) {
            if (checkSearchFilters.getCheckCategory() == null
                    || checkSearchFilters.getCheckCategory().equals(categoryModel.getCategory())) {
                QualityCategoryModel partialResult = pruneQualityCategoryModel(categoryModel, checkSearchFilters);
                result.getCategories().add(partialResult);
            }
        }
        if (result.getCategories().isEmpty()) {
            return null;
        }
        return result;
    }

    protected QualityCategoryModel pruneQualityCategoryModel(
            QualityCategoryModel baseModel,
            CheckSearchFilters checkSearchFilters) {
        QualityCategoryModel result = new QualityCategoryModel();
        result.setCategory(baseModel.getCategory());
        result.setHelpText(baseModel.getHelpText());
        for (CheckModel checkModel: baseModel.getChecks()) {
            boolean checkNamePredicate = checkSearchFilters.getCheckName() == null
                    || checkSearchFilters.getCheckName().equals(checkModel.getCheckName());
            boolean checkConfiguredPredicate = checkSearchFilters.getCheckConfigured() == null
                    || checkSearchFilters.getCheckConfigured().equals(checkModel.isConfigured());
            if (checkNamePredicate && checkConfiguredPredicate) {
                result.getChecks().add(checkModel);
            }
        }
        if (result.getChecks().isEmpty()) {
            return null;
        }
        return result;
    }

    protected List<CheckType> getPossibleCheckTypes(CheckType filterValue) {
        if (filterValue != null) {
            return List.of(filterValue);
        }
        return List.of(CheckType.values());
    }

    protected List<CheckTimeScale> getPossibleCheckTimeScales(CheckTimeScale filterValue) {
        if (filterValue != null) {
            return List.of(filterValue);
        }
        return List.of(CheckTimeScale.values());
    }
}
