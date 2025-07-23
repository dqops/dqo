/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.jobs.schema;

import tech.tablesaw.api.Table;

/**
 * Result object from the {@link ImportSchemaQueueJob} table import job that returns a list of imported tables.
 */
public class ImportSchemaQueueJobResult {
    private Table importedTables;

    /**
     * Creates a job result object.
     * @param importedTables List of imported tables in a tabular format.
     */
    public ImportSchemaQueueJobResult(Table importedTables) {
        this.importedTables = importedTables;
    }

    /**
     * Returns a list of imported tables in a tabular object.
     * @return Imported tables.
     */
    public Table getImportedTables() {
        return importedTables;
    }

    /**
     * Stores a tablesaw table with imported tables.
     * @param importedTables Imported tables.
     */
    public void setImportedTables(Table importedTables) {
        this.importedTables = importedTables;
    }
}
