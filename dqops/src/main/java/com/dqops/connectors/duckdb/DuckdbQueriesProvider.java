package com.dqops.connectors.duckdb;

import com.dqops.connectors.duckdb.config.DuckdbStorageType;
import com.dqops.connectors.duckdb.fileslisting.azure.AzureStoragePath;
import com.dqops.connectors.storage.aws.AwsAuthenticationMode;
import com.dqops.connectors.storage.azure.AzureAuthenticationMode;
import com.dqops.metadata.sources.ConnectionSpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.parquet.Strings;

import java.nio.charset.StandardCharsets;

/**
 * Provides a queries specific to DuckDB with the human-readable formatting.
 */
public class DuckdbQueriesProvider {

    /**
     * Provides a CREATE SECRET query for an especial secret type given in duckdb parameters spec.
     * @param connectionSpec Connection spec with DuckDB parameters with credentials and setup.
     * @return Ready to execute create secrets query string.
     */
    public static String provideCreateSecretQuery(ConnectionSpec connectionSpec, String scope){
        DuckdbParametersSpec duckdbParametersSpec = connectionSpec.getDuckdb();
        DuckdbStorageType storageType = duckdbParametersSpec.getStorageType();
        String indent = "    ";
        StringBuilder loadSecretsString = new StringBuilder();

        if(connectionSpec.getDuckdb().getStorageType().equals(DuckdbStorageType.azure)){
            AzureStoragePath azureStoragePath = AzureStoragePath.from(scope, connectionSpec.getDuckdb().resolveAccountName());
            if(!scope.contains(azureStoragePath.getDomain())){
                scope = azureStoragePath.getAzFullPathPrefix();
            }
        }

        String secretName = "s_" + calculateSecretHex(scope);

        loadSecretsString.append("CREATE SECRET ").append(secretName).append(" (\n");
        loadSecretsString.append(indent).append("TYPE ").append(storageType.toString().toUpperCase()).append(",\n");
        switch (storageType){
            case s3:
                if (duckdbParametersSpec.getAwsAuthenticationMode() == AwsAuthenticationMode.default_credentials &&
                        (Strings.isNullOrEmpty(duckdbParametersSpec.getAwsAccessKeyId()) || Strings.isNullOrEmpty(duckdbParametersSpec.getAwsSecretAccessKey()))) {
                    loadSecretsString.append(indent).append("PROVIDER credential_chain,\n");

                    String defaultAuthenticationChain = "env;config;sts;sso;instance;process";
                    if (!Strings.isNullOrEmpty(duckdbParametersSpec.getAwsDefaultAuthenticationChain())) {
                        defaultAuthenticationChain = duckdbParametersSpec.getAwsDefaultAuthenticationChain();
                    }
                    loadSecretsString.append(indent).append("CHAIN '").append(defaultAuthenticationChain).append("',\n");
                } else {
                    loadSecretsString.append(indent).append("KEY_ID '").append(duckdbParametersSpec.getUser()).append("',\n");
                    loadSecretsString.append(indent).append("SECRET '").append(duckdbParametersSpec.getPassword()).append("',\n");
                }
                if (!Strings.isNullOrEmpty(duckdbParametersSpec.getProfile())) {
                    loadSecretsString.append(indent).append("PROFILE '").append(duckdbParametersSpec.getProfile()).append("',\n");
                }
                if (!Strings.isNullOrEmpty(duckdbParametersSpec.getRegion())) {
                    loadSecretsString.append(indent).append("REGION '").append(duckdbParametersSpec.getRegion()).append("',\n");
                }
                break;
            case azure:
                if(duckdbParametersSpec.getAzureAuthenticationMode().equals(AzureAuthenticationMode.connection_string)){
                    loadSecretsString.append(indent).append(AzureAuthenticationMode.connection_string.toString().toUpperCase())
                            .append(" '").append(duckdbParametersSpec.getPassword()).append("',\n");
                }
                if(duckdbParametersSpec.getAzureAuthenticationMode().equals(AzureAuthenticationMode.credential_chain)) {
                    loadSecretsString.append(indent).append("PROVIDER ")
                            .append(AzureAuthenticationMode.credential_chain.toString().toUpperCase()).append(",\n");
                    loadSecretsString.append(indent).append("ACCOUNT_NAME '").append(duckdbParametersSpec.getAccountName()).append("',\n");
                }
                if(duckdbParametersSpec.getAzureAuthenticationMode().equals(AzureAuthenticationMode.service_principal)
                        || duckdbParametersSpec.getAzureAuthenticationMode().equals(AzureAuthenticationMode.default_credentials)) {
                    loadSecretsString.append(indent).append("PROVIDER ")
                            .append(AzureAuthenticationMode.service_principal.toString().toUpperCase()).append(",\n");
                    loadSecretsString.append(indent).append("TENANT_ID '").append(duckdbParametersSpec.getTenantId()).append("',\n");
                    loadSecretsString.append(indent).append("CLIENT_ID '").append(duckdbParametersSpec.getClientId()).append("',\n");
                    loadSecretsString.append(indent).append("CLIENT_SECRET '").append(duckdbParametersSpec.getClientSecret()).append("',\n");
                    loadSecretsString.append(indent).append("ACCOUNT_NAME '").append(duckdbParametersSpec.getAccountName()).append("',\n");
                }
                break;
            case gcs:
                loadSecretsString.append(indent).append("KEY_ID '").append(duckdbParametersSpec.getAwsAccessKeyId()).append("',\n");
                loadSecretsString.append(indent).append("SECRET '").append(duckdbParametersSpec.getAwsSecretAccessKey()).append("',\n");
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
     * @param homePath The dqo home path with local extensions for DuckDB
     * @return Ready to execute query string.
     */
    public static String provideSetExtensionsQuery(String homePath){
        StringBuilder setCustomRepository = new StringBuilder();
        setCustomRepository.append("SET extension_directory = ");
        setCustomRepository.append("'");
        setCustomRepository.append(homePath);
        setCustomRepository.append("/bin/.duckdb/extensions");  // use only "/bin" for DuckDB < 0.10
        setCustomRepository.append("'");
        return setCustomRepository.toString();
    }

    /**
     * Calculates a hex for secret using scope and a name of a thread.
     * @return Hex string.
     */
    public static String calculateSecretHex(String scope) {
        Thread thread = Thread.currentThread();
        String threadedScope = (thread != null ? thread.getName() : "") + scope;
        String hex = new String(Hex.encodeHex((threadedScope).getBytes(StandardCharsets.UTF_8)));
        return hex;
    }

}
