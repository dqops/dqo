/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.storage.localfiles.credentials.aws;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileContent;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileNames;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.apache.parquet.Strings;
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
public class AwsDefaultConfigProfileProvider {

    /**
     * Provides the AWS config profile file.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @param profile AWS profile name.
     * @return AWS default config profile created from the credential files available in DQOps shared credentials.
     */
    public static Optional<Profile> provideProfile(SecretValueLookupContext secretValueLookupContext, String profile){

        String sectionName = "profiles";
        String sectionTitle = Strings.isNullOrEmpty(profile) ? "default" : null;

        FileContent fileContent = CredentialsFileProvider.getCredentialFileContent(
                DefaultCloudCredentialFileNames.AWS_DEFAULT_CONFIG_NAME,
                secretValueLookupContext
        );

        if (fileContent != null) {
            String keyContent = fileContent.getTextContent();

            if (Objects.equals(keyContent.replace("\r\n", "\n"), DefaultCloudCredentialFileContent.AWS_DEFAULT_CONFIG_INITIAL_CONTENT)) {
                if (!Objects.equals(sectionTitle, "default")) {
                    ProfileFile profileFile;
                    try (InputStream keyReaderStream = new ByteArrayInputStream(keyContent.getBytes(StandardCharsets.UTF_8))) {
                        profileFile = ProfileFile.builder().content(keyReaderStream).type(ProfileFile.Type.CONFIGURATION).build();
                        return profileFile.getSection(sectionName, sectionTitle);
                    } catch (IOException e) {
                        throw new DqoRuntimeException("The .credentials/" + DefaultCloudCredentialFileNames.AWS_DEFAULT_CONFIG_NAME +
                                " file contains default (fake) credentials. Please update the file by setting valid AWS default credentials.");
                    }
                }
            } else {
                ProfileFile profileFile;
                try (InputStream keyReaderStream = new ByteArrayInputStream(keyContent.getBytes(StandardCharsets.UTF_8))) {
                    profileFile = ProfileFile.builder().content(keyReaderStream).type(ProfileFile.Type.CONFIGURATION).build();
                } catch (IOException e) {
                    throw new DqoRuntimeException("Failed to read AWS default credentials.");
                }

                return profileFile.getSection(sectionName, sectionTitle);
            }
        }
        return Optional.empty();
    }

}
