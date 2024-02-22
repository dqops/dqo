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
public class AwsDefaultConfigProfileProviderTest extends BaseTest {

    private Path userHomePath;
    private SecretValueLookupContext secretValueLookupContext;
    private AwsDefaultConfigProfileProvider sut;

    @BeforeEach
    void setUp() {
        LocalFolderTreeNode localHomeFolder = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(true);
        Path testUserHome = localHomeFolder.getPhysicalAbsolutePath();
        LocalUserHomeCreatorObjectMother.initializeDqoUserHomeAt(testUserHome.toString());

        UserHomeContextFactory userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithEmptyTemporaryContext();
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY);
        userHomePath = userHomeContext.getHomeRoot().getPhysicalAbsolutePath();

        secretValueLookupContext = new SecretValueLookupContext(userHomeContext.getUserHome());
        sut = new AwsDefaultConfigProfileProvider();
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
    void provideProfile_customConfigSet_ReturnsProfile() throws IOException {

        Path defaultAwsCredentialsPath = userHomePath.resolve(BuiltInFolderNames.CREDENTIALS)
                .resolve(DefaultCloudCredentialFileNames.AWS_DEFAULT_CONFIG_NAME);

        Files.writeString(defaultAwsCredentialsPath, DefaultCloudCredentialFileContent.AWS_DEFAULT_CONFIG_INITIAL_CONTENT);

        String credentialFileContent = DefaultCloudCredentialFileContent.AWS_DEFAULT_CONFIG_INITIAL_CONTENT
                .replace("us-east-1", "overridden-region");

        Files.writeString(defaultAwsCredentialsPath, credentialFileContent);

        Optional<Profile> profile = this.sut.provideProfile(secretValueLookupContext);

        Assertions.assertTrue(profile.isPresent());

        Assertions.assertEquals("overridden-region",
                profile.get().property(AwsConfigSettingNames.REGION).orElse(null));
    }

    @Test
    void provideProfile_defaultInitializationConfig_thenThrowsException() throws IOException {

        Path defaultAwsCredentialsPath = userHomePath.resolve(BuiltInFolderNames.CREDENTIALS)
                .resolve(DefaultCloudCredentialFileNames.AWS_DEFAULT_CONFIG_NAME);
        Files.writeString(defaultAwsCredentialsPath, DefaultCloudCredentialFileContent.AWS_DEFAULT_CONFIG_INITIAL_CONTENT);

        Assertions.assertThrows(DqoRuntimeException.class, () -> {
                    Optional<Profile> profile = this.sut.provideProfile(secretValueLookupContext);
        });
    }
}
