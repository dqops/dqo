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
