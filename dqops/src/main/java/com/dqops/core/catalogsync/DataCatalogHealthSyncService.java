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

package com.dqops.core.catalogsync;

import com.dqops.metadata.search.TableSearchFilters;
import com.dqops.metadata.userhome.UserHome;

/**
 * Service that initiates sending updated health statuses to the data catalog for requested tables.
 */
public interface DataCatalogHealthSyncService {
    /**
     * Retrieves and sends the current data quality health status to the data catalog for each table in the given user home
     * that matches the table filter.
     *
     * @param userHome           User home with tables to iterate over and synchronize.
     * @param tableSearchFilters Table search filter for matching tables.
     */
    void synchronizeDataCatalog(UserHome userHome, TableSearchFilters tableSearchFilters);
}
