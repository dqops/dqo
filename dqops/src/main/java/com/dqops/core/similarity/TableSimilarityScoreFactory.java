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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.similarity.TableSimilarityContainer;
import com.dqops.metadata.sources.PhysicalTableName;

/**
 * Table similarity score calculation service.
 */
public interface TableSimilarityScoreFactory {
    /**
     * Calculates a table similarity score from statistics.
     *
     * @param connectionName     Connection name.
     * @param physicalTableName  Physical table name.
     * @param userDomainIdentity User identity and the data domain.
     * @return Table similarity score or null when the table has no statistics.
     */
    TableSimilarityContainer calculateSimilarityScore(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userDomainIdentity);
}
