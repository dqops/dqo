package com.dqops.metadata.storage.localfiles.credentials;

import com.dqops.core.secrets.SecretValueLookupContext;
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
 * AWS default profile provider that use the keys available in the credential folder of DQO.
 */
@Component
public class AwsDefaultCredentialProfileProvider implements AwsProfileProvider {

    /**
     * Provides the AWS profile with credentials.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return AWS default profile created from the credential files available in DQOps shared credentials.
     */
    public Optional<Profile> provideProfile(SecretValueLookupContext secretValueLookupContext){

        String sectionName = "profiles";
        String sectionTitle = "default";

        FileSharedCredentialWrapperImpl defaultCredentialsSharedSecret =
                (FileSharedCredentialWrapperImpl) secretValueLookupContext.getUserHome().getCredentials().getByObjectName(
                    DefaultCloudCredentialFileNames.AWS_DEFAULT_CREDENTIALS_NAME, true);

        if (defaultCredentialsSharedSecret != null && defaultCredentialsSharedSecret.getObject() != null &&
                !Objects.equals(defaultCredentialsSharedSecret.getObject().getTextContent(), DefaultCloudCredentialFileContent.AWS_DEFAULT_CREDENTIALS_INITIAL_CONTENT)) {

            String keyContent = defaultCredentialsSharedSecret.getObject().getTextContent();

            ProfileFile profileFile;
            try (InputStream keyReaderStream = new ByteArrayInputStream(keyContent.getBytes(StandardCharsets.UTF_8))) {
                profileFile = ProfileFile.builder().content(keyReaderStream).type(ProfileFile.Type.CREDENTIALS).build();
            } catch (IOException e) {
                throw new RuntimeException("Failed to read AWS default credentials.");
            }

            return profileFile.getSection(sectionName, sectionTitle);
        }
        return Optional.empty();
    }

}
