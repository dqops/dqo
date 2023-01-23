package ai.dqo.connectors;

/**
 * Interface implemented by specific connection properties classes that have a configurable database name.
 */
public interface ConnectionProviderSpecificParameters {
    /**
     * Returns a database name.
     * @return Database name.
     */
    String getDatabase();
}
