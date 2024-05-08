package com.dqops.connectors.duckdb;

import com.dqops.connectors.storage.azure.AzureAuthenticationMode;
import com.dqops.metadata.sources.ConnectionSpec;

/**
 * Provides a queries specific to DuckDB with the human-readable formatting.
 */
public class DuckdbQueriesProvider {

    /**
     * Provides a CREATE SECRET query for an especial secret type given in duckdb parameters spec.
     * @param connectionSpec Connection spec with DuckDB parameters with credentials and setup.
     * @return Ready to execute create secrets query string.
     */
    public static String provideCreateSecretQuery(ConnectionSpec connectionSpec, String secretName, String scope){
        DuckdbParametersSpec duckdbParametersSpec = connectionSpec.getDuckdb();
        DuckdbStorageType storageType = duckdbParametersSpec.getStorageType();
        String indent = "    ";
        StringBuilder loadSecretsString = new StringBuilder();
        loadSecretsString.append("CREATE SECRET ").append(secretName).append(" (\n");
        loadSecretsString.append(indent).append("TYPE ").append(storageType.toString().toUpperCase()).append(",\n");
        switch (storageType){
            case s3:
                loadSecretsString.append(indent).append("KEY_ID '").append(duckdbParametersSpec.getUser()).append("',\n");
                loadSecretsString.append(indent).append("SECRET '").append(duckdbParametersSpec.getPassword()).append("',\n");
                loadSecretsString.append(indent).append("REGION '").append(duckdbParametersSpec.getRegion()).append("',\n");
                break;
            case azure:
                if(duckdbParametersSpec.getAzureAuthenticationMode().equals(AzureAuthenticationMode.connection_string)){
                    loadSecretsString.append(indent).append(AzureAuthenticationMode.connection_string.toString().toUpperCase())
                            .append(" '").append(duckdbParametersSpec.getPassword()).append("',\n");
                }
                if(duckdbParametersSpec.getAzureAuthenticationMode().equals(AzureAuthenticationMode.credential_chain)) {
                    loadSecretsString.append(indent).append("PROVIDER ")
                            .append(AzureAuthenticationMode.credential_chain.toString().toUpperCase()).append("',\n");
                    loadSecretsString.append(indent).append("ACCOUNT_NAME '").append(duckdbParametersSpec.getAccountName()).append("',\n");
                }
                if(duckdbParametersSpec.getAzureAuthenticationMode().equals(AzureAuthenticationMode.service_principal)) {
                    loadSecretsString.append(indent).append("PROVIDER ")
                            .append(AzureAuthenticationMode.service_principal.toString().toUpperCase()).append("',\n");
                    loadSecretsString.append(indent).append("TENANT_ID '").append(duckdbParametersSpec.getTenantId()).append("',\n");
                    loadSecretsString.append(indent).append("CLIENT_ID '").append(duckdbParametersSpec.getClientId()).append("',\n");
                    loadSecretsString.append(indent).append("CLIENT_SECRET '").append(duckdbParametersSpec.getClientSecret()).append("',\n");
                    loadSecretsString.append(indent).append("ACCOUNT_NAME '").append(duckdbParametersSpec.getAccountName()).append("',\n");
                }
                break;
            default:
                throw new RuntimeException("This type of DuckdbSecretsType is not supported: " + storageType);
        }
        loadSecretsString.append(indent).append("SCOPE '").append(scope).append("'\n");
        loadSecretsString.append(");");
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
        setCustomRepository.append("/bin/.duckdb/extensions");  // use only "/bin" for DuckDB < 0.10
        setCustomRepository.append("'");
        return setCustomRepository.toString();
    }

}
