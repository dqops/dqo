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

import com.dqops.BaseTest;
import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNode;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNodeObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileContent;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileNames;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeCreatorObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.profiles.Profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@SpringBootTest
public class AwsDefaultCredentialProfileProviderTest extends BaseTest {

    private Path userHomePath;
    private SecretValueLookupContext secretValueLookupContext;
    private AwsDefaultCredentialProfileProvider sut;

    @BeforeEach
    void setUp() {
        LocalFolderTreeNode localHomeFolder = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(true);
        Path testUserHome = localHomeFolder.getPhysicalAbsolutePath();
        LocalUserHomeCreatorObjectMother.initializeDqoUserHomeAt(testUserHome.toString());

        UserHomeContextFactory userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithEmptyTemporaryContext();
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY, false);
        userHomePath = userHomeContext.getHomeRoot().getPhysicalAbsolutePath();

        secretValueLookupContext = new SecretValueLookupContext(userHomeContext.getUserHome());
        sut = new AwsDefaultCredentialProfileProvider();
    }

    @AfterEach
    void tearDown() {
        try {
            FileUtils.deleteDirectory(userHomePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void provideProfile_customCredentialsSet_ReturnsProfile() throws IOException {

        Path defaultAwsCredentialsPath = userHomePath.resolve(BuiltInFolderNames.CREDENTIALS)
                .resolve(DefaultCloudCredentialFileNames.AWS_DEFAULT_CREDENTIALS_NAME);

        Files.writeString(defaultAwsCredentialsPath, DefaultCloudCredentialFileContent.AWS_DEFAULT_CREDENTIALS_INITIAL_CONTENT);

        String credentialFileContent = DefaultCloudCredentialFileContent.AWS_DEFAULT_CREDENTIALS_INITIAL_CONTENT
                .replace("PLEASE_REPLACE_WITH_YOUR_AWS_ACCESS_KEY_ID", "my_custom_access_key_id")
                .replace("PLEASE_REPLACE_WITH_YOUR_AWS_SECRET_ACCESS_KEY", "my_custom_secret_access_key");

        Files.writeString(defaultAwsCredentialsPath, credentialFileContent);

        Optional<Profile> profile = this.sut.provideProfile(secretValueLookupContext, null);

        Assertions.assertTrue(profile.isPresent());

        Assertions.assertEquals("my_custom_access_key_id",
                profile.get().property(AwsCredentialProfileSettingNames.AWS_ACCESS_KEY_ID).orElse(null));

        Assertions.assertEquals("my_custom_secret_access_key",
                profile.get().property(AwsCredentialProfileSettingNames.AWS_SECRET_ACCESS_KEY).orElse(null));
    }

    @Test
    void provideProfile_defaultInitializationCredentials_thenThrowsException() throws IOException {

        Path defaultAwsCredentialsPath = userHomePath.resolve(BuiltInFolderNames.CREDENTIALS)
                .resolve(DefaultCloudCredentialFileNames.AWS_DEFAULT_CREDENTIALS_NAME);
        Files.writeString(defaultAwsCredentialsPath, DefaultCloudCredentialFileContent.AWS_DEFAULT_CREDENTIALS_INITIAL_CONTENT);

        Assertions.assertThrows(DqoRuntimeException.class, () -> {
                    Optional<Profile> profile = this.sut.provideProfile(secretValueLookupContext, null);
        });
    }
}
