/*
 * Copyright © 2022 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.core.jobqueue.jobs.table;

import tech.tablesaw.api.Table;

/**
 * Result object from the {@link ImportTablesQueueJob} table import job that returns list of tables that have been imported.
 */
public class ImportTablesQueueJobResult {
    private Table importedTables;

    /**
     * Creates a job result object.
     * @param importedTable List of imported tables in a tabular format.
     */
    public ImportTablesQueueJobResult(Table importedTable) {
        this.importedTables = importedTable;
    }

    /**
     * Returns a list of imported tables in a tabular object.
     * @return Imported tables.
     */
    public Table getImportedTables() {
        return importedTables;
    }

    /**
     * Stores a tablesaw table with the list of imported tables.
     * @param importedTables Imported table.
     */
    public void setImportedTables(Table importedTables) {
        this.importedTables = importedTables;
    }
}
