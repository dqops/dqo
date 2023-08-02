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
package com.dqops.services.metadata;

import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.check.CheckTemplate;
import com.dqops.services.check.CheckFlatConfigurationFactory;
import com.dqops.services.check.mapping.AllChecksModelFactory;
import com.dqops.services.check.mapping.models.AllChecksModel;
import com.dqops.services.check.mapping.models.CheckContainerTypeModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.models.CheckConfigurationModel;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TableServiceImpl implements TableService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoQueueJobFactory dqoQueueJobFactory;
    private final DqoJobQueue dqoJobQueue;
    private final AllChecksModelFactory allChecksModelFactory;
    private final CheckFlatConfigurationFactory checkFlatConfigurationFactory;

    @Autowired
    public TableServiceImpl(UserHomeContextFactory userHomeContextFactory,
                            DqoQueueJobFactory dqoQueueJobFactory,
                            DqoJobQueue dqoJobQueue,
                            AllChecksModelFactory allChecksModelFactory,
                            CheckFlatConfigurationFactory checkFlatConfigurationFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.allChecksModelFactory = allChecksModelFactory;
        this.checkFlatConfigurationFactory = checkFlatConfigurationFactory;
    }

    /**
     * Finds a table located in provided user home.
     * @param userHome       User home.
     * @param connectionName Connection name.
     * @param tableName      Table name.
     * @return Table wrapper with the requested table.
     */
    @Override
    public TableWrapper getTable(UserHome userHome, String connectionName, PhysicalTableName tableName) {
        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        return connectionWrapper.getTables().getByObjectName(tableName, true);
    }

    /**
     * Retrieves a list of column level check templates on the given table.
     * @param connectionName Connection name.
     * @param tableName      Table name.
     * @param checkType      (Optional) Check type.
     * @param checkTimeScale (Optional) Check time-scale.
     * @param checkCategory  (Optional) Check category.
     * @param checkName      (Optional) Check name.
     * @return List of column level check templates on the requested table, matching the optional filters. Null if table doesn't exist.
     */
    @Override
    public List<CheckTemplate> getCheckTemplates(String connectionName,
                                                 PhysicalTableName tableName,
                                                 CheckType checkType,
                                                 CheckTimeScale checkTimeScale,
                                                 String checkCategory,
                                                 String checkName) {
        if (Strings.isNullOrEmpty(connectionName)
                || tableName == null
                || checkType == null) {
            // Connection name, table name and check type have to be provided.
            return null;
        }
        if ((checkType == CheckType.partitioned || checkType == CheckType.recurring) && checkTimeScale == null) {
            // Time scale has to be provided for partitioned and recurring checks.
            return null;
        }

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        checkSearchFilters.setConnectionName(connectionName);
        checkSearchFilters.setSchemaTableName(tableName.toTableSearchFilter());
        checkSearchFilters.setCheckType(checkType);
        checkSearchFilters.setTimeScale(checkTimeScale);
        // Filtering by checkTarget has to be done apart from these filters.
        checkSearchFilters.setCheckCategory(checkCategory);
        checkSearchFilters.setCheckName(checkName);

        List<AllChecksModel> allChecksModels = this.allChecksModelFactory.fromCheckSearchFilters(checkSearchFilters);

        CheckContainerTypeModel checkContainerTypeModel = new CheckContainerTypeModel(checkType, checkTimeScale);

        return allChecksModels.stream()
                // Get only column-level checks
                .map(AllChecksModel::getColumnChecksModel)
                .flatMap(model -> model.getTableColumnChecksModels().stream())
                .flatMap(model -> model.getColumnChecksModels().stream())
                .flatMap(model -> model.getCheckContainers().values().stream())
                .flatMap(model -> model.getCategories().stream())
                // For each category get check templates
                .map(categoryModel -> {
                    Map<String, CheckModel> checkNameToExampleCheck = new HashMap<>();
                    for (CheckModel checkModel: categoryModel.getChecks()) {
                        if (!checkNameToExampleCheck.containsKey(checkModel.getCheckName())) {
                            checkNameToExampleCheck.put(checkModel.getCheckName(), checkModel);
                        }
                    }

                    return checkNameToExampleCheck.values().stream()
                            .map(uiCheckModel -> CheckTemplate.fromUiCheckModel(
                                    uiCheckModel, categoryModel.getCategory(), checkContainerTypeModel, CheckTarget.column)
                            );
                })
                .reduce(Stream.empty(), Stream::concat)
                .collect(Collectors.toList());
    }

    @Override
    public List<CheckConfigurationModel> getCheckConfigurationsOnTable(String connectionName,
                                                                       PhysicalTableName physicalTableName,
                                                                       CheckContainerTypeModel checkContainerTypeModel,
                                                                       String columnNamePattern,
                                                                       String columnDataType,
                                                                       CheckTarget checkTarget,
                                                                       String checkCategory,
                                                                       String checkName,
                                                                       Boolean checkEnabled,
                                                                       Boolean checkConfigured) {
        CheckSearchFilters filters = new CheckSearchFilters();
        filters.setCheckType(checkContainerTypeModel.getCheckType());
        filters.setTimeScale(checkContainerTypeModel.getCheckTimeScale());
        filters.setConnectionName(connectionName);
        filters.setSchemaTableName(physicalTableName.toTableSearchFilter());
        filters.setColumnName(columnNamePattern);
        filters.setColumnDataType(columnDataType);
        filters.setCheckTarget(checkTarget);
        filters.setCheckCategory(checkCategory);
        filters.setCheckName(checkName);
        filters.setEnabled(checkEnabled);
        filters.setCheckConfigured(checkConfigured);

        return this.checkFlatConfigurationFactory.fromCheckSearchFilters(filters);
    }

    /**
     * Deletes table from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this table.
     * @param connectionName Connection name
     * @param tableName      Physical table name.
     * @return Asynchronous job result object for deferred background operations.
     */
    @Override
    public PushJobResult<DeleteStoredDataQueueJobResult> deleteTable(String connectionName, PhysicalTableName tableName) {
        List<PhysicalTableName> tableNameList = new LinkedList<>();
        tableNameList.add(tableName);

        Map<String, Iterable<PhysicalTableName>> connectionToTableMapping = new HashMap<>();
        connectionToTableMapping.put(connectionName, tableNameList);
        List<PushJobResult<DeleteStoredDataQueueJobResult>> jobResultList = this.deleteTables(connectionToTableMapping);

        return jobResultList.isEmpty() ? null : jobResultList.get(0);
    }

    /**
     * Deletes tables from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these tables.
     * @param connectionToTables Connection name to tables on that connection mapping.
     * @return Asynchronous job result objects for deferred background operations.
     */
    @Override
    public List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteTables(Map<String, Iterable<PhysicalTableName>> connectionToTables) {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<DeleteStoredDataQueueJobParameters> deleteStoredDataParameters = new ArrayList<>();

        for (Map.Entry<String, Iterable<PhysicalTableName>> connToTables : connectionToTables.entrySet()) {
            String connectionName = connToTables.getKey();
            for (PhysicalTableName tableName : connToTables.getValue()) {
                TableWrapper tableWrapper = this.getTable(userHome, connectionName, tableName);
                if (tableWrapper == null) {
                    continue;
                }

                tableWrapper.markForDeletion();

                DeleteStoredDataQueueJobParameters param = new DeleteStoredDataQueueJobParameters() {{
                    setConnectionName(connectionName);
                    setSchemaTableName(tableName.toTableSearchFilter());
                    setDeleteStatistics(true);
                    setDeleteCheckResults(true);
                    setDeleteSensorReadouts(true);
                    setDeleteErrors(true);
                }};
                deleteStoredDataParameters.add(param);
            }
        }

        List<PushJobResult<DeleteStoredDataQueueJobResult>> results = new ArrayList<>();
        for (DeleteStoredDataQueueJobParameters param : deleteStoredDataParameters) {
            DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
            deleteStoredDataJob.setDeletionParameters(param);
            PushJobResult<DeleteStoredDataQueueJobResult> jobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob);
            results.add(jobResult);
        }

        userHomeContext.flush();
        return results;
    }
}
