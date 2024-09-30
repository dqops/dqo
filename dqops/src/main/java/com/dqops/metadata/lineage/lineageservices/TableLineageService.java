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

import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;
import com.dqops.metadata.userhome.UserHome;

/**
 * Table data lineage service that returns a data lineage graph around a given table.
 */
public interface TableLineageService {
    /**
     * Returns a data lineage in a form of a flattened graph, returning all data flows (from table A to table B),
     * and the data quality status.
     *
     * @param userHome User home to find if tables are present.
     * @param referenceTable    The start table.
     * @param upstreamLineage   True when the data lineage should be calculated for upstream tables.
     * @param downstreamLineage True when the data lineage (the impact radius) should be calculated for downstream tables.
     * @return Table lineage model.
     */
    TableLineageModel buildDataLineageModel(UserHome userHome,
                                            DomainConnectionTableKey referenceTable,
                                            boolean upstreamLineage,
                                            boolean downstreamLineage);
}
