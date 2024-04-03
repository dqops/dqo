package com.dqops.metadata.storage.localfiles.credentials.azure;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileContent;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileNames;
import com.dqops.metadata.storage.localfiles.credentials.FileSharedCredentialWrapperImpl;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Azure credentials provider implementation.
 */
@Component
public class AzureCredentialsProviderImpl implements AzureCredentialsProvider {

    /**
     * Provides the Azure credentials from the DQOps' default credentials.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Optional Azure Credential object.
     */
    public Optional<AzureCredential> provideCredentials(SecretValueLookupContext secretValueLookupContext){

        FileSharedCredentialWrapperImpl defaultCredentialsSharedSecret =
                secretValueLookupContext != null && secretValueLookupContext.getUserHome() != null &&
                secretValueLookupContext.getUserHome().getCredentials() != null ?
                (FileSharedCredentialWrapperImpl) secretValueLookupContext.getUserHome().getCredentials().getByObjectName(
                        DefaultCloudCredentialFileNames.AZURE_DEFAULT_CREDENTIALS_NAME, true) : null;

        if (defaultCredentialsSharedSecret != null && defaultCredentialsSharedSecret.getObject() != null) {
            String credentialFileContent = defaultCredentialsSharedSecret.getObject().getTextContent();

            if (Objects.equals(credentialFileContent.replace("\r\n", "\n"), DefaultCloudCredentialFileContent.AZURE_DEFAULT_CREDENTIALS_INITIAL_CONTENT)) {
                throw new DqoRuntimeException("The .credentials/" + DefaultCloudCredentialFileNames.AZURE_DEFAULT_CREDENTIALS_NAME +
                        " file contains default (fake) credentials. Please update the file by setting valid Azure credentials.");
            }

            try (InputStream keyReaderStream = new ByteArrayInputStream(credentialFileContent.getBytes(StandardCharsets.UTF_8))) {
                String content = new String(keyReaderStream.readAllBytes(), StandardCharsets.UTF_8);

                Map<String, String> credentialsMap = Arrays.stream(content.split("\n"))
                        .collect(Collectors.toMap(
                                s -> List.of(s.split("=")).get(0),
                                s -> List.of(s.split("=")).get(1)
                        ));

                AzureCredential azureCredential = AzureCredential.builder()
                        .user(credentialsMap.get(AzureCredentialSettingNames.AZURE_USER))
                        .password(credentialsMap.get(AzureCredentialSettingNames.AZURE_PASSWORD))
                        .authentication(credentialsMap.get(AzureCredentialSettingNames.AZURE_AUTHENTICATION_METHOD))
                        .build();

                return Optional.of(azureCredential);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read Azure default credentials.");
            }
        }
        return Optional.empty();
    }

}
