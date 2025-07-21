/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
