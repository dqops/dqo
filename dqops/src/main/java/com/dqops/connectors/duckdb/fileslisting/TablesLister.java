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

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;

import java.util.List;

/**
 * Used to retrieve the list of files from a file storage that are used as tables by duckdb's listTables.
 */
public interface TablesLister {

    /**
     * Returns list of SourceTableModel from a file storage.
     * @param duckdb DuckdbParametersSpec
     * @param schemaName The name of a virtual schema.
     * @param tableNameContains Optional filter for table names that must contain this name.
     * @param limit The maximum number of tables to return.
     * @return The list of SourceTableModel.
     */
    List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName, String tableNameContains, int limit);
}
