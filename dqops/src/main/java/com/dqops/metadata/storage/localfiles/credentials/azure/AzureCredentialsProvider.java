package com.dqops.metadata.storage.localfiles.credentials.azure;

import com.dqops.core.secrets.SecretValueLookupContext;

import java.util.Optional;

/**
 * Azure credentials provider.
 */
public interface AzureCredentialsProvider {

    /**
     * Provides the Azure credentials.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Azure Credential object.
     */
    Optional<AzureCredential> provideCredentials(SecretValueLookupContext secretValueLookupContext);
}
