/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
