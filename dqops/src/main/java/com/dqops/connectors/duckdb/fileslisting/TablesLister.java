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
     * @return The list of SourceTableModel.
     */
    List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName);
}
