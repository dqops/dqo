package com.dqops.metadata.storage.localfiles.credentials.aws;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileContent;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileNames;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.profiles.Profile;
import software.amazon.awssdk.profiles.ProfileFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * Provider for the AWS default config profile file available in the credential folder of DQO.
 */
@Component
public class AwsDefaultConfigProfileProvider implements AwsProfileProvider {

    /**
     * Provides the AWS config profile file.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return AWS default config profile created from the credential files available in DQOps shared credentials.
     */
    public Optional<Profile> provideProfile(SecretValueLookupContext secretValueLookupContext){

        String sectionName = "profiles";
        String sectionTitle = "default";

        FileContent fileContent = CredentialsFileProvider.getCredentialFileContent(
                DefaultCloudCredentialFileNames.AWS_DEFAULT_CONFIG_NAME,
                secretValueLookupContext
        );

        if (fileContent != null) {
            String keyContent = fileContent.getTextContent();

            if (Objects.equals(keyContent.replace("\r\n", "\n"), DefaultCloudCredentialFileContent.AWS_DEFAULT_CONFIG_INITIAL_CONTENT)) {
                throw new DqoRuntimeException("The .credentials/" + DefaultCloudCredentialFileNames.AWS_DEFAULT_CONFIG_NAME +
                        " file contains default (fake) credentials. Please update the file by setting valid AWS default credentials.");
            }

            ProfileFile profileFile;
            try (InputStream keyReaderStream = new ByteArrayInputStream(keyContent.getBytes(StandardCharsets.UTF_8))) {
                profileFile = ProfileFile.builder().content(keyReaderStream).type(ProfileFile.Type.CONFIGURATION).build();
            } catch (IOException e) {
                throw new RuntimeException("Failed to read AWS default credentials.");
            }

            return profileFile.getSection(sectionName, sectionTitle);
        }
        return Optional.empty();
    }

}
