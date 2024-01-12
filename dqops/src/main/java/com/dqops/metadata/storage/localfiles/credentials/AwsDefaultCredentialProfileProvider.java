package com.dqops.metadata.storage.localfiles.credentials;

import com.dqops.core.secrets.SecretValueLookupContext;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.profiles.Profile;
import software.amazon.awssdk.profiles.ProfileFile;

import java.nio.file.Path;
import java.util.Objects;

@Component
public class AwsDefaultCredentialProfileProvider implements AwsProfileProvider {

    public Profile provideProfile(SecretValueLookupContext secretValueLookupContext){

        String sectionName = "profiles";
        String sectionTitle = "default";

        FileSharedCredentialWrapperImpl defaultCredentialsSharedSecret =
                (FileSharedCredentialWrapperImpl) secretValueLookupContext.getUserHome().getCredentials().getByObjectName(
                    DefaultCloudCredentialFileNames.AWS_DEFAULT_CREDENTIALS_NAME, true);

        if (defaultCredentialsSharedSecret != null && defaultCredentialsSharedSecret.getObject() != null &&
                !Objects.equals(defaultCredentialsSharedSecret.getObject().getTextContent(), DefaultCloudCredentialFileContent.AWS_DEFAULT_CREDENTIALS_INITIAL_CONTENT)) {

            Path credentialFilePath = defaultCredentialsSharedSecret.toAbsoluteFilePath();
            ProfileFile profileFile = ProfileFile.builder().content(credentialFilePath).type(ProfileFile.Type.CREDENTIALS).build();
            Profile profile = profileFile.getSection(sectionName, sectionTitle).orElse(null);

            if(profile == null
                    || profile.property(AwsCredentialProfileSettingNames.AWS_ACCESS_KEY_ID).isEmpty()
                    || profile.property(AwsCredentialProfileSettingNames.AWS_SECRET_ACCESS_KEY).isEmpty()) {
                return null;
            }

            return profile;
        }
        return null;
    }

}
