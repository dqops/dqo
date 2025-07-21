/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.errorsamples.factory;

import tech.tablesaw.api.Table;

public interface ErrorSamplesTableFactory {
    /**
     * Creates an empty normalized sensor execution error samples table that has the right schema.
     *
     * @param tableName Table name.
     * @return Empty error samples table.
     */
    Table createEmptyErrorSamplesTable(String tableName);
}
