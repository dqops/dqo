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
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.check.CheckTemplate;
import com.dqops.services.check.mapping.models.CheckContainerTypeModel;
import com.dqops.services.check.models.CheckConfigurationModel;

import java.util.List;
import java.util.Map;

/**
 * Service that performs table operations.
 */
public interface TableService {
    /**
     * Finds a table located in provided user home.
     * @param userHome       User home.
     * @param connectionName Connection name.
     * @param tableName      Table name.
     * @return Table wrapper with the requested table.
     */
    TableWrapper getTable(UserHome userHome,
                          String connectionName,
                          PhysicalTableName tableName);

    /**
     * Retrieves a list of column level check templates on the given table.
     * @param connectionName Connection name.
     * @param tableName      Table name.
     * @param checkType      (Optional) Check type.
     * @param checkTimeScale (Optional) Check time-scale.
     * @param checkCategory  (Optional) Check category.
     * @param checkName      (Optional) Check name.
     * @param principal      User principal.
     * @return List of column level check templates on the requested table, matching the optional filters. Null if table doesn't exist.
     */
    List<CheckTemplate> getCheckTemplates(String connectionName,
                                          PhysicalTableName tableName,
                                          CheckType checkType,
                                          CheckTimeScale checkTimeScale,
                                          String checkCategory,
                                          String checkName,
                                          DqoUserPrincipal principal);

    /**
     * Retrieves a UI friendly data quality profiling check configuration list on a requested table.
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param checkContainerTypeModel Check container type model.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkTarget       (Optional) Filter on check target.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @param principal         User principal.
     * @return UI friendly data quality profiling check configuration list on a requested table.
     */
    List<CheckConfigurationModel> getCheckConfigurationsOnTable(
            String connectionName,
            PhysicalTableName physicalTableName,
            CheckContainerTypeModel checkContainerTypeModel,
            String columnNamePattern,
            String columnDataType,
            CheckTarget checkTarget,
            String checkCategory,
            String checkName,
            Boolean checkEnabled,
            Boolean checkConfigured,
            DqoUserPrincipal principal);

    /**
     * Deletes table from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this table.
     * @param connectionName Connection name
     * @param tableName      Physical table name.
     * @param principal Principal that will be used to run the job.
     * @return Asynchronous job result object for deferred background operations.
     */
    PushJobResult<DeleteStoredDataQueueJobResult> deleteTable(
            String connectionName, PhysicalTableName tableName, DqoUserPrincipal principal);

    /**
     * Deletes tables from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these tables.
     * @param connectionToTables Connection name to tables on that connection mapping.
     * @param principal Principal that will be used to run the job.
     * @return Asynchronous job result objects for deferred background operations.
     */
    List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteTables(
            Map<String, Iterable<PhysicalTableName>> connectionToTables, DqoUserPrincipal principal);
}
