/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
