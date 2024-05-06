package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;

import java.util.List;

public interface TablesLister {
    List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName);
}
