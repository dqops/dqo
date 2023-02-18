/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.sampledata;

import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.sampledata.files.SampleTableFromCsv;

/**
 * This object describes a desired target table for testing on a target database.
 */
public class SampleTableMetadataForeign {
    private final String connectionName;
    private final ConnectionSpec connectionSpec;
    private final TableSpec tableSpec;
    private final SampleTableFromCsv tableData;

    /**
     * Creates a sample table metadata object.
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @param tableSpec Table specification.
     * @param tableData Table data loaded from a CSV sample file.
     */
    public SampleTableMetadataForeign(String connectionName, ConnectionSpec connectionSpec, TableSpec tableSpec, SampleTableFromCsv tableData) {
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
    public SampleTableFromCsv getTableData() {
        return tableData;
    }
}
