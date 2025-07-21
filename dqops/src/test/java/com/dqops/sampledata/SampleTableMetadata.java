/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sampledata;

import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.sampledata.files.SampleTableFromTestDataFile;

/**
 * This object describes a desired target table for testing on a target database.
 */
public class SampleTableMetadata {
    private String connectionName;
    private final ConnectionSpec connectionSpec;
    private final TableSpec tableSpec;
    private final SampleTableFromTestDataFile tableData;

    /**
     * Creates a sample table metadata object.
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @param tableSpec Table specification.
     * @param tableData Table data loaded from a test data sample file.
     */
    public SampleTableMetadata(String connectionName, ConnectionSpec connectionSpec, TableSpec tableSpec, SampleTableFromTestDataFile tableData) {
        this.connectionName = connectionName;
        this.connectionSpec = connectionSpec;
        this.tableSpec = tableSpec;
        this.tableData = tableData;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Sets a new connection name.
     * @param connectionName Connection name.
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Returns a connection specification.
     * @return Connection specification.
     */
    public ConnectionSpec getConnectionSpec() {
        return connectionSpec;
    }

    /**
     * Returns a table specification with the provider specific data types in columns and a correct target (physical) table name.
     * @return Table specification.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Testable data that will be loaded into a testable table.
     * @return Sample data.
     */
    public SampleTableFromTestDataFile getTableData() {
        return tableData;
    }
}
