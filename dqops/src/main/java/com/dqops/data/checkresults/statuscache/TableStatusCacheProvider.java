/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.checkresults.statuscache;

/**
 * Intermediary service used to retrieve the instance of the table status cache, to avoid circular dependencies
 * during the IoC initialization.
 */
public interface TableStatusCacheProvider {
    /**
     * Returns the current instance of the table status cache.
     *
     * @return Table status cache instance.
     */
    TableStatusCache getTableStatusCache();
}
