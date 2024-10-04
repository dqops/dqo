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

package com.dqops.core.similarity;

import com.dqops.metadata.sources.PhysicalTableName;

/**
 * Service that receives notifications about updated table statistics to refresh the search indexes.
 */
public interface TableSimilarityRefreshService {
    /**
     * Notifies the table similarity refresh service that a table was updated and its search index should be refreshed.
     *
     * @param dataDomain Data domain name.
     * @param connection Connection name.
     * @param table      Physical table name.
     */
    void refreshTable(String dataDomain, String connection, PhysicalTableName table);

    /**
     * Starts the service.
     */
    void start();

    /**
     * Stops the service.
     */
    void stop();
}
