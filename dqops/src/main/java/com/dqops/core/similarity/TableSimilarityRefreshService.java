/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
