package ai.dqo.core.jobqueue.jobs.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Parameters for the {@link ImportSchemaQueueJob} job that imports tables from a database.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImportSchemaQueueJobParameters {
    private String connectionName;
    private String schemaName;
    private String tableNamePattern;

    public ImportSchemaQueueJobParameters() {
    }

    /**
     * Creates a schema import job parameters.
     * @param connectionName Connection name to import.
     * @param schemaName Schema name in the source database to import.
     * @param tableNamePattern Optional table name search filter.
     */
    public ImportSchemaQueueJobParameters(String connectionName, String schemaName, String tableNamePattern) {
        this.connectionName = connectionName;
        this.schemaName = schemaName;
        this.tableNamePattern = tableNamePattern;
    }

    /**
     * Returns the connection name to be imported.
     * @return Connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Sets the connection name to be imported.
     * @param connectionName Connection name.
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Returns the source schema name.
     * @return Source schema name.
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets the source schema name.
     * @param schemaName Source schema name.
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    /**
     * Sets an optional table filter. It could be a table name "customers", or a name with a prefix/suffix/infix: "*customer", "customer*", "cus*r".
     * @return Table name search pattern.
     */
    public String getTableNamePattern() {
        return tableNamePattern;
    }

    /**
     * Sets the table name search filter.
     * @param tableNamePattern Table name search filter.
     */
    public void setTableNamePattern(String tableNamePattern) {
        this.tableNamePattern = tableNamePattern;
    }
}
