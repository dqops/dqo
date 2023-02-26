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

import ai.dqo.core.jobqueue.PushJobResult;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import ai.dqo.metadata.sources.TableWrapper;

import java.util.List;

/**
 * Service that performs table operations.
 */
public interface TableService {
    /**
     * Deletes table from metadata and flushes user context.
     * Cleans all stored data from .data folder related to this table.
     * @param tableWrapper Table wrapper.
     * @return Asynchronous job result object for deferred background operations.
     */
    PushJobResult<DeleteStoredDataQueueJobResult> deleteTable(TableWrapper tableWrapper);

    /**
     * Deletes tables from metadata and flushes user context.
     * Cleans all stored data from .data folder related to these tables.
     * @param tableWrappers Iterable of table wrappers.
     * @return Asynchronous job result object for deferred background operations.
     */
    List<PushJobResult<DeleteStoredDataQueueJobResult>> deleteTables(Iterable<TableWrapper> tableWrappers);
}
