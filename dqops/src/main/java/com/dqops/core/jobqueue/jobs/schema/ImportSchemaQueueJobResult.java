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
