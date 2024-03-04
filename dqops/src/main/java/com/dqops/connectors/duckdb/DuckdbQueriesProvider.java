package com.dqops.connectors.duckdb;

import com.dqops.metadata.sources.ConnectionSpec;
import com.google.common.hash.HashCode;

/**
 * Provides a queries specific to DuckDB with the human-readable formatting.
 */
public class DuckdbQueriesProvider {

    /**
     * Provides a CREATE SECRET query for an especial secret type given in duckdb parameters spec.
     * @param connectionSpec Connection spec with DuckDB parameters with credentials and setup.
     * @return Ready to execute create secrets query string.
     */
    public static String provideCreateSecretQuery(ConnectionSpec connectionSpec, HashCode secretHash){
        DuckdbParametersSpec duckdbParametersSpec = connectionSpec.getDuckdb();
        DuckdbStorageType storageType = duckdbParametersSpec.getStorageType();
        String indent = "    ";
        StringBuilder loadSecretsString = new StringBuilder();
        String secretName = "secret_" + secretHash;
        loadSecretsString.append("CREATE SECRET ").append(secretName).append(" (\n");
        switch (storageType){
            case s3:
                loadSecretsString.append(indent).append("TYPE ").append(storageType.toString().toUpperCase()).append(",\n");
                loadSecretsString.append(indent).append("KEY_ID '").append(duckdbParametersSpec.getUser()).append("',\n");
                loadSecretsString.append(indent).append("SECRET '").append(duckdbParametersSpec.getPassword()).append("',\n");
                loadSecretsString.append(indent).append("REGION '").append(duckdbParametersSpec.getRegion()).append("'");
                break;
            default:
                throw new RuntimeException("This type of DuckdbSecretsType is not supported: " + storageType);
        }
        loadSecretsString.append("\n);");
        return loadSecretsString.toString();
    }


    /**
     * Provides query to set extension directory.
     * @param dqoHomePath The dqo home path with local extensions for DuckDB
     * @return Ready to execute query string.
     */
    public static String provideSetExtensionsQuery(String dqoHomePath){
        StringBuilder setCustomRepository = new StringBuilder();
        setCustomRepository.append("SET extension_directory = ");
        setCustomRepository.append("'");
        setCustomRepository.append(dqoHomePath);
        setCustomRepository.append("/bin/duckdb");
        setCustomRepository.append("'");
        return setCustomRepository.toString();
    }

}
