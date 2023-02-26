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

package ai.dqo.services.metadata;

import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.PushJobResult;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ColumnServiceImpl implements ColumnService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoQueueJobFactory dqoQueueJobFactory;
    private final DqoJobQueue dqoJobQueue;

    @Autowired
    public ColumnServiceImpl(UserHomeContextFactory userHomeContextFactory,
                             DqoQueueJobFactory dqoQueueJobFactory,
                             DqoJobQueue dqoJobQueue) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Deletes column from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this column.
     *
     * @param columnSpec Column spec.
     * @return Asynchronous job result object for deferred background operations.
     */
    @Override
    public PushJobResult<DeleteStoredDataQueueJobResult> deleteColumn(ColumnSpec columnSpec) {
        List<PushJobResult<DeleteStoredDataQueueJobResult>> jobResultList = this.deleteColumns(
                new LinkedList<>(){{add(columnSpec);}}
        );
        return jobResultList.get(0);
    }

    /**
     * Deletes columns from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these columns.
     *
     * @param columnSpecs Iterable of column specs.
     * @return List of asynchronous job result objects for deferred background operations.
     */
    @Override
    public List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteColumns(Iterable<ColumnSpec> columnSpecs) {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        for (ColumnSpec columnSpec: columnSpecs) {
            TableWrapper table = userHome.findTableFor(columnSpec.getHierarchyId());
            table.getSpec().getColumns().remove(columnSpec.getColumnName());
        }

        userHomeContext.flush();

        List<DeleteStoredDataQueueJobParameters> deleteStoredDataParameters = this.getDeleteStoredDataQueueJobParameters(userHome, columnSpecs);

        return deleteStoredDataParameters.stream()
                .map(param -> {
                    DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
                    deleteStoredDataJob.setDeletionParameters(param);
                    return this.dqoJobQueue.pushJob(deleteStoredDataJob);
                }).collect(Collectors.toList());
    }

    /**
     * Get parameters for delete stored data job, pointing to all results related to specific columns.
     * @param userHome User home.
     * @param columnSpecs Column specs.
     * @return List of delete stored data job parameters.
     */
    protected List<DeleteStoredDataQueueJobParameters> getDeleteStoredDataQueueJobParameters(
            UserHome userHome,
            Iterable<ColumnSpec> columnSpecs) {
        Map<String, Map<String, List<String>>> connectionToTableToColumnsMapping = new HashMap<>();
        for (ColumnSpec columnSpec: columnSpecs) {
            TableWrapper tableWrapper = userHome.findTableFor(columnSpec.getHierarchyId());

            String connection = userHome.findConnectionFor(tableWrapper.getHierarchyId()).getName();
            if (!connectionToTableToColumnsMapping.containsKey(connection)) {
                connectionToTableToColumnsMapping.put(connection, new HashMap<>());
            }

            Map<String, List<String>> tableToColumnsMapping = connectionToTableToColumnsMapping.get(connection);
            String table = tableWrapper.getPhysicalTableName().toTableSearchFilter();
            if (!tableToColumnsMapping.containsKey(table)) {
                tableToColumnsMapping.put(table, new ArrayList<>());
            }
            tableToColumnsMapping.get(table).add(columnSpec.getColumnName());
        }

        List<DeleteStoredDataQueueJobParameters> parameters = new ArrayList<>();
        connectionToTableToColumnsMapping.forEach(
                (connection, tableToColumnsMapping) -> tableToColumnsMapping.forEach(
                        (table, columns) -> {
                            DeleteStoredDataQueueJobParameters param = new DeleteStoredDataQueueJobParameters() {{
                                setConnectionName(connection);
                                setSchemaTableName(table);
                                setColumnNames(columns);
                                setDeleteStatistics(true);
                                setDeleteRuleResults(true);
                                setDeleteSensorReadouts(true);
                                setDeleteErrors(true);
                            }};
                            parameters.add(param);
                        }
                ));
        return parameters;
    }
}
