package com.dqops.connectors.duckdb;

import com.dqops.metadata.sources.ConnectionSpec;
import com.google.common.hash.HashCode;

/**
 * Provides a queries specific to DuckDB with the human-readable formatting.
 */
public class DuckdbQueriesProvider {

    /**
     * Provides a CREATE SECRET query for especial secret type given in duckdb parameters spec.
     * @param connectionSpec Connection spec with DuckDB parameters with credentials and setup.
     * @return Ready to execute create secrets query string.
     */
    public static String provideCreateSecretQuery(ConnectionSpec connectionSpec, HashCode secretHash){
        DuckdbParametersSpec duckdbParametersSpec = connectionSpec.getDuckdb();
        DuckdbSecretsType secretsType = duckdbParametersSpec.getSecretsType();
        String indent = "    ";
        StringBuilder loadSecretsString = new StringBuilder();
        String secretName = "secret_" + secretHash;
        loadSecretsString.append("CREATE SECRET ").append(secretName).append(" (\n");
        switch (secretsType){
            case s3:
                loadSecretsString.append(indent).append("TYPE ").append(secretsType.toString().toUpperCase()).append(",\n");
                // todo: use the default credentials when below are not set
                // todo: use secretValueProvider for the below
                loadSecretsString.append(indent).append("KEY_ID '").append(duckdbParametersSpec.getUser()).append("',\n");
                loadSecretsString.append(indent).append("SECRET '").append(duckdbParametersSpec.getPassword()).append("',\n");
                loadSecretsString.append(indent).append("REGION '").append(duckdbParametersSpec.getRegion()).append("'");
                break;
            default:
                throw new RuntimeException("This type of DuckdbSecretsType is not supported: " + secretsType);
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
