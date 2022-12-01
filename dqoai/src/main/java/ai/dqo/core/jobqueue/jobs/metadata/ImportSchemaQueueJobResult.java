package ai.dqo.core.jobqueue.jobs.metadata;

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
