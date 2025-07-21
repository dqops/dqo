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

/**
 * Background service that reconciles search indexes in the background.
 */
public interface TableSimilarityReconciliationService {
    /**
     * Enables the service.
     */
    void start();

    /**
     * Updates the search index. This operation is called by a CRON scheduler.
     */
    void reconcileSearchIndex();
}
