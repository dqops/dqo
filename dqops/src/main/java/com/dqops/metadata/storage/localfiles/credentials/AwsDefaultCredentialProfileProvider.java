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

// todo javadoc
@Component
public class AwsDefaultCredentialProfileProvider implements AwsProfileProvider {

    // todo javadoc
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

            Optional<Profile> profile = profileFile.getSection(sectionName, sectionTitle);
            if(profile.isEmpty()
                    || profile.get().property(AwsCredentialProfileSettingNames.AWS_ACCESS_KEY_ID).isEmpty()
                    || profile.get().property(AwsCredentialProfileSettingNames.AWS_SECRET_ACCESS_KEY).isEmpty()) {
                return Optional.empty();
            }
            return profile;
        }
        return Optional.empty();
    }

}
