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

package com.dqops.core.incidents;

import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import tech.tablesaw.api.Table;

/**
 * A data transfer object that wraps new check results on a single table that should be loaded into the incident table.
 */
public class TableIncidentImportBatch {
    private Table newCheckResults;
    private ConnectionSpec connection;
    private TableSpec table;

    /**
     * Creates a new incident import batch with results for a single table.
     * @param newCheckResults New check results
     * @param connection
     * @param table
     */
    public TableIncidentImportBatch(Table newCheckResults, ConnectionSpec connection, TableSpec table) {
        this.newCheckResults = newCheckResults;
        this.connection = connection;
        this.table = table;
    }

    /**
     * Tablesaw table with new check results. The column names are defined in {@link com.dqops.data.checkresults.factory.CheckResultsColumnNames}.
     * @return Table with new check results.
     */
    public Table getNewCheckResults() {
        return newCheckResults;
    }

    /**
     * Returns the connection specification.
     * @return Connection specification.
     */
    public ConnectionSpec getConnection() {
        return connection;
    }

    /**
     * Returns the table specification.
     * @return Table specification.
     */
    public TableSpec getTable() {
        return table;
    }
}
