/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
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

package ai.dqo.services.check.mapping;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.table.adhoc.TableAdHocCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TablePartitionedChecksRootSpec;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.ExecutionContextFactory;
import ai.dqo.metadata.search.*;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalker;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;
import ai.dqo.services.check.mapping.models.UICheckContainerModel;
import ai.dqo.services.check.mapping.models.column.UIAllColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.UIColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.UITableColumnChecksModel;
import ai.dqo.services.check.mapping.models.table.UIAllTableChecksModel;
import ai.dqo.services.check.mapping.models.table.UISchemaTableChecksModel;
import ai.dqo.services.check.mapping.models.table.UITableChecksModel;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UIAllChecksPatchFactoryImpl implements UIAllChecksPatchFactory {
    ExecutionContextFactory executionContextFactory;
    HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    SpecToUiCheckMappingService specToUiCheckMappingService;

    @Autowired
    public UIAllChecksPatchFactoryImpl(ExecutionContextFactory executionContextFactory,
                                       HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                       SpecToUiCheckMappingService specToUiCheckMappingService) {
        this.executionContextFactory = executionContextFactory;
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.specToUiCheckMappingService = specToUiCheckMappingService;
    }

    /**
     * Creates a list of {@link UIAllChecksModel}s based on provided filters.
     *
     * @param checkSearchFilters Check search filters.
     * @return List of {@link UIAllChecksModel}s (by connections) fitting the filters.
     */
    @Override
    public List<UIAllChecksModel> fromCheckSearchFilters(CheckSearchFilters checkSearchFilters) {
        ExecutionContext executionContext = this.executionContextFactory.create();
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        connectionSearchFilters.setConnectionName(checkSearchFilters.getConnectionName());

        Collection<ConnectionSpec> connectionSpecs = this.hierarchyNodeTreeSearcher
                .findConnections(userHome, connectionSearchFilters);
        if (connectionSpecs.isEmpty()) {
            // No connections matching the filter.
            return new ArrayList<>();
        }

        List<UIAllChecksModel> uiConnectionPatches = connectionSpecs.stream()
                .map(connectionSpec -> userHome.getConnections().getByObjectName(connectionSpec.getConnectionName(), true))
                .map(connectionWrapper -> this.getAllChecksForConnection(connectionWrapper, checkSearchFilters, executionContext))
                .collect(Collectors.toList());

        return uiConnectionPatches;
    }

    protected UIAllChecksModel getAllChecksForConnection(ConnectionWrapper connectionWrapper,
                                                         CheckSearchFilters checkSearchFilters,
                                                         ExecutionContext executionContext) {
        UIAllChecksModel uiAllChecksModel = new UIAllChecksModel();
        uiAllChecksModel.setConnectionName(connectionWrapper.getName());
        uiAllChecksModel.setChecksType(checkSearchFilters.getCheckType());
        uiAllChecksModel.setChecksTimeScale(checkSearchFilters.getTimeScale());

        // TODO: Add templates.

        if (checkSearchFilters.getColumnName() == null
                && checkSearchFilters.getColumnNullable() == null
                && checkSearchFilters.getColumnDataType() == null) {
            // No info specifying columns, we include whole table checks.
            UIAllTableChecksModel uiAllTableChecksModel = this.getAllTableChecksForConnection(
                    connectionWrapper, checkSearchFilters, executionContext);
            uiAllChecksModel.setTableChecksModel(uiAllTableChecksModel);
        }
        UIAllColumnChecksModel columnChecksModel = this.getAllColumnChecksForConnection(
                connectionWrapper, checkSearchFilters, executionContext);
        uiAllChecksModel.setColumnChecksModel(columnChecksModel);

        return uiAllChecksModel;
    }

    protected UIAllTableChecksModel getAllTableChecksForConnection(ConnectionWrapper connectionWrapper,
                                                                   CheckSearchFilters checkSearchFilters,
                                                                   ExecutionContext executionContext) {
        UIAllTableChecksModel allTableChecksModel = new UIAllTableChecksModel();
        Collection<TableWrapper> tableWrappers = this.hierarchyNodeTreeSearcher
                .findTables(connectionWrapper, checkSearchFilters);

        // TODO: Add templates

        List<UISchemaTableChecksModel> schemasChecks = tableWrappers.stream()
                .map(tableWrapper -> tableWrapper.getPhysicalTableName().getSchemaName())
                .distinct()
                .map(schemaName -> tableWrappers.stream()
                        .filter(tableWrapper -> tableWrapper.getPhysicalTableName().getSchemaName().equals(schemaName))
                        .collect(Collectors.toList()))
                .map(tables -> this.getSchemaTableCheckModelForTables(connectionWrapper.getSpec(),
                        tables, checkSearchFilters, executionContext))
                .collect(Collectors.toList());

        allTableChecksModel.setUiSchemaTableChecksModels(schemasChecks);
        return allTableChecksModel;
    }

    protected UISchemaTableChecksModel getSchemaTableCheckModelForTables(ConnectionSpec connectionSpec,
                                                                         List<TableWrapper> tableWrappers,
                                                                         CheckSearchFilters checkSearchFilters,
                                                                         ExecutionContext executionContext) {
        String schemaName = tableWrappers.stream().findAny().get().getPhysicalTableName().getSchemaName();
        UISchemaTableChecksModel schemaTableChecks = new UISchemaTableChecksModel();
        schemaTableChecks.setSchemaName(schemaName);

        // TODO: Add templates.

        List<UITableChecksModel> uiTableChecksModels = tableWrappers.stream()
                .map(tableWrapper -> getTableChecksModelForTable(connectionSpec, tableWrapper, checkSearchFilters, executionContext))
                .collect(Collectors.toList());
        schemaTableChecks.setUiTableChecksModels(uiTableChecksModels);
        return schemaTableChecks;
    }

    protected UITableChecksModel getTableChecksModelForTable(ConnectionSpec connectionSpec,
                                                             TableWrapper tableWrapper,
                                                             CheckSearchFilters checkSearchFilters,
                                                             ExecutionContext executionContext) {
        String tableName = tableWrapper.getPhysicalTableName().getTableName();
        UITableChecksModel tableChecksModel = new UITableChecksModel();
        tableChecksModel.setTableName(tableName);

        // TODO: Add templates

        TableSpec tableSpec = tableWrapper.getSpec();

        List<CheckType> checkTypes = this.getPossibleCheckTypes(checkSearchFilters.getCheckType());
        List<CheckTimeScale> timeScales = this.getPossibleCheckTimeScales(checkSearchFilters.getTimeScale());

        List<AbstractRootChecksContainerSpec> checkContainers = new ArrayList<>();
        for (CheckType checkType : checkTypes) {
            if (checkType == CheckType.ADHOC) {
                AbstractRootChecksContainerSpec checkContainer = tableSpec.getTableCheckRootContainer(checkType, null);
                checkContainers.add(checkContainer);
            }
            else {
                for (CheckTimeScale timeScale : timeScales) {
                    AbstractRootChecksContainerSpec checkContainer = tableSpec.getTableCheckRootContainer(checkType, timeScale);
                    checkContainers.add(checkContainer);
                }
            }
        }

        List<UICheckContainerModel> checkContainerModelList = checkContainers.stream()
                .map(checkContainer -> this.specToUiCheckMappingService.createUiModel(
                        checkContainer,
                        checkSearchFilters,
                        connectionSpec,
                        tableSpec,
                        executionContext,
                        connectionSpec.getProviderType()
                )).collect(Collectors.toList());
        tableChecksModel.setCheckContainers(checkContainerModelList);
        return tableChecksModel;
    }

    protected UIAllColumnChecksModel getAllColumnChecksForConnection(ConnectionWrapper connectionWrapper,
                                                                     CheckSearchFilters checkSearchFilters,
                                                                     ExecutionContext executionContext) {
        UIAllColumnChecksModel allColumnChecksModel = new UIAllColumnChecksModel();
        Collection<TableWrapper> tableWrappers = this.hierarchyNodeTreeSearcher
                .findTables(connectionWrapper, checkSearchFilters);

        // TODO: Add templates

        List<UITableColumnChecksModel> tableColumnChecksModels = tableWrappers.stream()
                .map(table -> this.getTableColumnCheckModelForTable(connectionWrapper.getSpec(),
                        table, checkSearchFilters, executionContext))
                .collect(Collectors.toList());

        allColumnChecksModel.setUiTableColumnChecksModels(tableColumnChecksModels);
        return allColumnChecksModel;
    }

    protected UITableColumnChecksModel getTableColumnCheckModelForTable(ConnectionSpec connectionSpec,
                                                                        TableWrapper tableWrapper,
                                                                        CheckSearchFilters checkSearchFilters,
                                                                        ExecutionContext executionContext) {
        PhysicalTableName schemaTableName = tableWrapper.getPhysicalTableName();
        UITableColumnChecksModel tableColumnChecksModel = new UITableColumnChecksModel();
        tableColumnChecksModel.setSchemaTableName(schemaTableName);

        TableSpec tableSpec = tableWrapper.getSpec();

        // TODO: Add templates.

        List<UIColumnChecksModel> uiColumnChecksModels = tableSpec.getColumns().entrySet().stream()
                .map(columnNameToSpec -> getColumnChecksModelForColumn(
                        connectionSpec,
                        tableSpec,
                        columnNameToSpec.getKey(),
                        columnNameToSpec.getValue(),
                        checkSearchFilters,
                        executionContext))
                .collect(Collectors.toList());
        tableColumnChecksModel.setUiColumnChecksModels(uiColumnChecksModels);
        return tableColumnChecksModel;
    }

    protected UIColumnChecksModel getColumnChecksModelForColumn(ConnectionSpec connectionSpec,
                                                                TableSpec tableSpec,
                                                                String columnName,
                                                                ColumnSpec columnSpec,
                                                                CheckSearchFilters checkSearchFilters,
                                                                ExecutionContext executionContext) {
        UIColumnChecksModel uiColumnChecksModel = new UIColumnChecksModel();
        uiColumnChecksModel.setColumnName(columnName);

        // TODO: Add templates

        List<CheckType> checkTypes = this.getPossibleCheckTypes(checkSearchFilters.getCheckType());
        List<CheckTimeScale> timeScales = this.getPossibleCheckTimeScales(checkSearchFilters.getTimeScale());

        List<AbstractRootChecksContainerSpec> checkContainers = new ArrayList<>();
        for (CheckType checkType : checkTypes) {
            if (checkType == CheckType.ADHOC) {
                AbstractRootChecksContainerSpec checkContainer = columnSpec.getColumnCheckRootContainer(checkType, null);
                checkContainers.add(checkContainer);
            }
            else {
                for (CheckTimeScale timeScale : timeScales) {
                    AbstractRootChecksContainerSpec checkContainer = columnSpec.getColumnCheckRootContainer(checkType, timeScale);
                    checkContainers.add(checkContainer);
                }
            }
        }

        List<UICheckContainerModel> checkContainerModelList = checkContainers.stream()
                .map(checkContainer -> this.specToUiCheckMappingService.createUiModel(
                        checkContainer,
                        checkSearchFilters,
                        connectionSpec,
                        tableSpec,
                        executionContext,
                        connectionSpec.getProviderType()
                )).collect(Collectors.toList());
        uiColumnChecksModel.setCheckContainers(checkContainerModelList);
        return uiColumnChecksModel;
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
