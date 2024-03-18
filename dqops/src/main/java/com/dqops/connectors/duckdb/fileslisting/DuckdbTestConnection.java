package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.duckdb.DuckdbParametersSpec;

/**
 * Used for testing a connection for DuckDB.
 */
public interface DuckdbTestConnection {

    /**
     * Tests the connection by listing files. Throws exception on misconfiguration such as no schema name, lack or invalid paths, no files in a path, etc.
     * @param duckdbParametersSpec DuckdbParametersSpec that allow to test a connection.
     */
    void testConnection(DuckdbParametersSpec duckdbParametersSpec);
}
