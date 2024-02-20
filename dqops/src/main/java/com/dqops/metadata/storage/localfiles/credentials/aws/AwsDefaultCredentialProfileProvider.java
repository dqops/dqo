package com.dqops.metadata.storage.localfiles.credentials.aws;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileContent;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileNames;
import com.dqops.metadata.storage.localfiles.credentials.FileSharedCredentialWrapperImpl;
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
                secretValueLookupContext != null && secretValueLookupContext.getUserHome() != null &&
                secretValueLookupContext.getUserHome().getCredentials() != null ?
                (FileSharedCredentialWrapperImpl) secretValueLookupContext.getUserHome().getCredentials().getByObjectName(
                    DefaultCloudCredentialFileNames.AWS_DEFAULT_CREDENTIALS_NAME, true) : null;

        if (defaultCredentialsSharedSecret != null && defaultCredentialsSharedSecret.getObject() != null) {
            String keyContent = defaultCredentialsSharedSecret.getObject().getTextContent();

            if (Objects.equals(keyContent.replace("\r\n", "\n"), DefaultCloudCredentialFileContent.AWS_DEFAULT_CREDENTIALS_INITIAL_CONTENT)) {
                throw new DqoRuntimeException("The .credentials/" + DefaultCloudCredentialFileNames.AWS_DEFAULT_CREDENTIALS_NAME +
                        " file contains default (fake) credentials. Please update the file by setting valid AWS default credentials.");
            }

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
