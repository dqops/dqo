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

package com.dqops.metadata.lineage.lineageservices;

import com.dqops.data.checkresults.statuscache.TableStatusCache;
import com.dqops.metadata.lineage.lineagecache.TableLineageCache;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Table data lineage service that returns a data lineage graph around a given table.
 */
@Component
public class TableLineageServiceImpl {
    private final TableLineageCache tableLineageCache;
    private final TableStatusCache tableStatusCache;

    /**
     * Default dependency injection constructor.
     * @param tableLineageCache Table data lineage cache.
     * @param tableStatusCache Table data quality status cache.
     */
    @Autowired
    public TableLineageServiceImpl(TableLineageCache tableLineageCache,
                                   TableStatusCache tableStatusCache) {
        this.tableLineageCache = tableLineageCache;
        this.tableStatusCache = tableStatusCache;
    }

    public TableLineageModel buildDataLineageModel(UserHome userHome,
                                                   String connectionName,
                                                   PhysicalTableName referenceTable) {
        TableLineageModel tableLineageModel = new TableLineageModel();

        return tableLineageModel;
    }
}
