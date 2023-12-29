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
package com.dqops.services.check.mapping;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.ConnectionSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.services.check.mapping.models.*;
import com.dqops.services.check.mapping.models.column.AllColumnChecksModel;
import com.dqops.services.check.mapping.models.column.ColumnChecksModel;
import com.dqops.services.check.mapping.models.column.TableColumnChecksModel;
import com.dqops.services.check.mapping.models.table.AllTableChecksModel;
import com.dqops.services.check.mapping.models.table.SchemaTableChecksModel;
import com.dqops.services.check.mapping.models.table.TableChecksModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AllChecksModelFactoryImpl implements AllChecksModelFactory {
    private ExecutionContextFactory executionContextFactory;
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private SpecToModelCheckMappingService specToModelCheckMappingService;

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
     * @param principal User principal.
     * @return List of {@link AllChecksModel}s (by connections) fitting the filters.
     */
    @Override
    public List<AllChecksModel> findAllConfiguredAndPossibleChecks(CheckSearchFilters checkSearchFilters,
                                                                   DqoUserPrincipal principal) {
        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserHome userHome = userHomeContext.getUserHome();
        boolean canManageChecks = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        connectionSearchFilters.setConnectionName(checkSearchFilters.getConnection());

        Collection<ConnectionSpec> connectionSpecs = this.hierarchyNodeTreeSearcher.findConnections(userHome, connectionSearchFilters);
        if (connectionSpecs.isEmpty()) {
            // No connections matching the filter.
            return new ArrayList<>();
        }

        List<AllChecksModel> uiConnectionPatches = connectionSpecs.stream()
                .map(connectionSpec -> userHome.getConnections().getByObjectName(connectionSpec.getConnectionName(), true))
                .map(connectionWrapper -> this.getAllChecksForConnection(connectionWrapper, checkSearchFilters, executionContext, canManageChecks))
                .filter(allChecksModel ->
                        (allChecksModel.getColumnChecksModel() != null && !allChecksModel.getColumnChecksModel().getTableColumnChecksModels().isEmpty())
                                || (allChecksModel.getTableChecksModel() != null && !allChecksModel.getTableChecksModel().getSchemaTableChecksModels().isEmpty()))
                .collect(Collectors.toList());

        return uiConnectionPatches;
    }

    /**
     * Generate one fake table and one fake column, capture all available checks that are supported on the connection.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param checkSearchFilters Additional check search filter to limit the list of possible checks.
     * @param principal Calling user principal.
     * @return Model of all possible checks, including both table and column level checks.
     */
    public AllChecksModel createTemplatedCheckModelsAvailableOnConnection(
            String connectionName,
            String schemaName,
            CheckSearchFilters checkSearchFilters,
            DqoUserPrincipal principal) {
        UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserHome userHome = userHomeContext.getUserHome();
        boolean canManageChecks = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        ConnectionWrapper originalConnectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        if (originalConnectionWrapper == null) {
            // No connections matching the filter.
            return null;
        }

        ConnectionWrapperImpl templatedConnectionWrapper = new ConnectionWrapperImpl(connectionName);
        templatedConnectionWrapper.setHierarchyId(originalConnectionWrapper.getHierarchyId());
        templatedConnectionWrapper.setSpec(originalConnectionWrapper.getSpec().deepClone());
        TableWrapper templatedTable = templatedConnectionWrapper.getTables().createAndAddNew(new PhysicalTableName(schemaName, "sample_table"));
        TableSpec templatedTableSpec = new TableSpec();
        templatedTable.setSpec(templatedTableSpec);
        templatedTableSpec.getColumns().put("sample_column", new ColumnSpec());

        AllChecksModel allChecksForConnection = this.getAllChecksForConnection(templatedConnectionWrapper, checkSearchFilters, executionContext, canManageChecks);
        return allChecksForConnection;
    }

    protected AllChecksModel getAllChecksForConnection(ConnectionWrapper connectionWrapper,
                                                       CheckSearchFilters checkSearchFilters,
                                                       ExecutionContext executionContext,
                                                       boolean canManageChecks) {
        AllChecksModel allChecksModel = new AllChecksModel();
        allChecksModel.setConnectionName(connectionWrapper.getName());

        // TODO: Add templates.

        CheckTarget checkTarget = checkSearchFilters.getCheckTarget();

        if (checkTarget != CheckTarget.column) {
            AllTableChecksModel allTableChecksModel = this.getAllTableChecksForConnection(
                    connectionWrapper, checkSearchFilters, executionContext, canManageChecks);
            allChecksModel.setTableChecksModel(allTableChecksModel);
        }

        if (checkTarget != CheckTarget.table) {
            AllColumnChecksModel columnChecksModel = this.getAllColumnChecksForConnection(
                    connectionWrapper, checkSearchFilters, executionContext, canManageChecks);
            allChecksModel.setColumnChecksModel(columnChecksModel);
        }

        return allChecksModel;
    }

    protected AllTableChecksModel getAllTableChecksForConnection(ConnectionWrapper connectionWrapper,
                                                                 CheckSearchFilters checkSearchFilters,
                                                                 ExecutionContext executionContext,
                                                                 boolean canManageChecks) {
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
                        tables, checkSearchFilters, executionContext, canManageChecks))
                .filter(schemaTableChecksModel -> !schemaTableChecksModel.getTableChecksModels().isEmpty())
                .collect(Collectors.toList());

        allTableChecksModel.setSchemaTableChecksModels(schemasChecks);
        return allTableChecksModel;
    }

    protected SchemaTableChecksModel getSchemaTableCheckModelForTables(ConnectionSpec connectionSpec,
                                                                       List<TableWrapper> tableWrappers,
                                                                       CheckSearchFilters checkSearchFilters,
                                                                       ExecutionContext executionContext,
                                                                       boolean canManageChecks) {
        String schemaName = tableWrappers.stream().findAny().get().getPhysicalTableName().getSchemaName();
        SchemaTableChecksModel schemaTableChecks = new SchemaTableChecksModel();
        schemaTableChecks.setSchemaName(schemaName);

        // TODO: Add templates.

        List<TableChecksModel> tableChecksModels = tableWrappers.stream()
                .map(tableWrapper -> getTableChecksModelForTable(connectionSpec, tableWrapper, checkSearchFilters, executionContext, canManageChecks))
                .filter(tableChecksModel -> !tableChecksModel.getCheckContainers().isEmpty())
                .collect(Collectors.toList());
        schemaTableChecks.setTableChecksModels(tableChecksModels);
        return schemaTableChecks;
    }

    protected TableChecksModel getTableChecksModelForTable(ConnectionSpec connectionSpec,
                                                           TableWrapper tableWrapper,
                                                           CheckSearchFilters checkSearchFilters,
                                                           ExecutionContext executionContext,
                                                           boolean canManageChecks) {
        String tableName = tableWrapper.getPhysicalTableName().getTableName();
        TableChecksModel tableChecksModel = new TableChecksModel();
        tableChecksModel.setTableName(tableName);

        // TODO: Add templates

        TableSpec tableSpec = tableWrapper.getSpec();
        tableChecksModel.setTableLevelFilter(tableSpec.getFilter());

        List<CheckType> checkTypes = this.getPossibleCheckTypes(checkSearchFilters.getCheckType());
        List<CheckTimeScale> timeScales = this.getPossibleCheckTimeScales(checkSearchFilters.getTimeScale());
        boolean findAlsoNotConfiguredChecks = checkSearchFilters.getCheckConfigured() == null || !checkSearchFilters.getCheckConfigured();

        Map<CheckContainerTypeModel, AbstractRootChecksContainerSpec> checkContainers = new HashMap<>();
        for (CheckType checkType : checkTypes) {
            if (checkType == CheckType.profiling) {
                AbstractRootChecksContainerSpec checkContainer = tableSpec.getTableCheckRootContainer(checkType, null, false, findAlsoNotConfiguredChecks);
                if (checkContainer == null) {
                    continue;
                }
                checkContainers.put(new CheckContainerTypeModel(checkType, null), checkContainer);
            }
            else {
                for (CheckTimeScale timeScale : timeScales) {
                    AbstractRootChecksContainerSpec checkContainer = tableSpec.getTableCheckRootContainer(checkType, timeScale, false, findAlsoNotConfiguredChecks);
                    if (checkContainer == null) {
                        continue;
                    }
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
                                connectionSpec.getProviderType(),
                                canManageChecks)
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
                                                                   ExecutionContext executionContext,
                                                                   boolean canManageChecks) {
        AllColumnChecksModel allColumnChecksModel = new AllColumnChecksModel();
        Collection<TableWrapper> tableWrappers = this.hierarchyNodeTreeSearcher
                .findTables(connectionWrapper, checkSearchFilters);

        // TODO: Add templates

        List<TableColumnChecksModel> tableColumnChecksModels = tableWrappers.stream()
                .map(table -> this.getTableColumnCheckModelForTable(connectionWrapper.getSpec(),
                        table, checkSearchFilters, executionContext, canManageChecks))
                .filter(tableColumnChecksModel -> !tableColumnChecksModel.getColumnChecksModels().isEmpty())
                .collect(Collectors.toList());

        allColumnChecksModel.setTableColumnChecksModels(tableColumnChecksModels);
        return allColumnChecksModel;
    }

    protected TableColumnChecksModel getTableColumnCheckModelForTable(ConnectionSpec connectionSpec,
                                                                      TableWrapper tableWrapper,
                                                                      CheckSearchFilters checkSearchFilters,
                                                                      ExecutionContext executionContext,
                                                                      boolean canManageChecks) {
        PhysicalTableName schemaTableName = tableWrapper.getPhysicalTableName();
        TableColumnChecksModel tableColumnChecksModel = new TableColumnChecksModel();
        tableColumnChecksModel.setSchemaTableName(schemaTableName);

        TableSpec tableSpec = tableWrapper.getSpec();
        tableColumnChecksModel.setTableLevelFilter(tableSpec.getFilter());

        // TODO: Add templates.

        List<ColumnChecksModel> columnChecksModels = tableSpec.getColumns().entrySet().stream()
                .filter(colToSpec ->
                        (checkSearchFilters.getColumn() == null || Objects.equals(colToSpec.getKey(), checkSearchFilters.getColumn()))
                                && (checkSearchFilters.getColumnNullable() == null || colToSpec.getValue().getTypeSnapshot() != null && colToSpec.getValue().getTypeSnapshot().getNullable() == checkSearchFilters.getColumnNullable())
                                && (checkSearchFilters.getColumnDataType() == null || colToSpec.getValue().getTypeSnapshot() != null && Objects.equals(colToSpec.getValue().getTypeSnapshot().getColumnType(), checkSearchFilters.getColumnDataType()))
                ).map(columnNameToSpec -> getColumnChecksModelForColumn(
                        connectionSpec,
                        tableSpec,
                        columnNameToSpec.getKey(),
                        columnNameToSpec.getValue(),
                        checkSearchFilters,
                        executionContext,
                        canManageChecks))
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
                                                              ExecutionContext executionContext,
                                                              boolean canManageChecks) {
        ColumnChecksModel columnChecksModel = new ColumnChecksModel();
        columnChecksModel.setColumnName(columnName);

        // TODO: Add templates

        List<CheckType> checkTypes = this.getPossibleCheckTypes(checkSearchFilters.getCheckType());
        List<CheckTimeScale> timeScales = this.getPossibleCheckTimeScales(checkSearchFilters.getTimeScale());
        boolean findAlsoNotConfiguredChecks = checkSearchFilters.getCheckConfigured() == null || !checkSearchFilters.getCheckConfigured();


        Map<CheckContainerTypeModel, AbstractRootChecksContainerSpec> checkContainers = new HashMap<>();
        for (CheckType checkType : checkTypes) {
            if (checkType == CheckType.profiling) {
                AbstractRootChecksContainerSpec checkContainer = columnSpec.getColumnCheckRootContainer(checkType, null, false, findAlsoNotConfiguredChecks);
                if (checkContainer == null) {
                    continue;
                }
                checkContainers.put(new CheckContainerTypeModel(checkType, null), checkContainer);
            }
            else {
                for (CheckTimeScale timeScale : timeScales) {
                    AbstractRootChecksContainerSpec checkContainer = columnSpec.getColumnCheckRootContainer(checkType, timeScale, false, findAlsoNotConfiguredChecks);
                    if (checkContainer == null) {
                        continue;
                    }
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
                            connectionSpec.getProviderType(),
                            canManageChecks)
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
        result.setCanEdit(baseModel.isCanEdit());
        result.setCanRunChecks(baseModel.isCanRunChecks());
        result.setCanDeleteData(baseModel.isCanDeleteData());
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
