/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.duckdb.config.DuckdbStorageType;

/**
 * Factory interface for the table listers based on file system.
 */
public interface TablesListerProvider {

    /**
     * Created the table lister depending on the storage type.
     * @param storageType The storage type supported in duckdb.
     * @return TableLister
     */
    TablesLister createTablesLister(DuckdbStorageType storageType);
}
