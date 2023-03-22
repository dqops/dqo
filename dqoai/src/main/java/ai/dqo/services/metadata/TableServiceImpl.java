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
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TableServiceImpl implements TableService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoQueueJobFactory dqoQueueJobFactory;
    private final DqoJobQueue dqoJobQueue;

    @Autowired
    public TableServiceImpl(UserHomeContextFactory userHomeContextFactory,
                            DqoQueueJobFactory dqoQueueJobFactory,
                            DqoJobQueue dqoJobQueue) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Deletes table from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this table.
     *
     * @param tableWrapper Table wrapper.
     * @return Asynchronous job result object for deferred background operations.
     */
    @Override
    public PushJobResult<DeleteStoredDataQueueJobResult> deleteTable(TableWrapper tableWrapper) {
        List<PushJobResult<DeleteStoredDataQueueJobResult>> jobResultList = this.deleteTables(
                new LinkedList<>(){{add(tableWrapper);}}
        );
        return jobResultList.get(0);
    }

    /**
     * Deletes tables from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these tables.
     *
     * @param tableWrappers Iterable of table wrappers.
     * @return Asynchronous job result object for deferred background operations.
     */
    @Override
    public List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteTables(Iterable<TableWrapper> tableWrappers) {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        for (TableWrapper tableWrapper: tableWrappers) {
            tableWrapper.markForDeletion();
        }
        userHomeContext.flush();

        List<PushJobResult<DeleteStoredDataQueueJobResult>> results = new ArrayList<>();
        for (TableWrapper tableWrapper: tableWrappers) {
            String connection = userHome.findConnectionFor(tableWrapper.getHierarchyId()).getName();
            String table = tableWrapper.getPhysicalTableName().toTableSearchFilter();

            DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
            DeleteStoredDataQueueJobParameters param = new DeleteStoredDataQueueJobParameters() {{
                setConnectionName(connection);
                setSchemaTableName(table);
                setDeleteStatistics(true);
                setDeleteCheckResults(true);
                setDeleteSensorReadouts(true);
                setDeleteErrors(true);
            }};
            deleteStoredDataJob.setDeletionParameters(param);
            PushJobResult<DeleteStoredDataQueueJobResult> jobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob);
            results.add(jobResult);
        }
        return results;
    }
}
