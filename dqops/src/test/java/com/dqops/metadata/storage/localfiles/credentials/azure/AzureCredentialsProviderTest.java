package com.dqops.metadata.storage.localfiles.credentials.azure;

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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@SpringBootTest
class AzureCredentialsProviderTest extends BaseTest {

    private Path userHomePath;
    private SecretValueLookupContext secretValueLookupContext;
    private AzureCredentialsProviderImpl sut;

    @BeforeEach
    void setUp() {
        LocalFolderTreeNode localHomeFolder = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(true);
        Path testUserHome = localHomeFolder.getPhysicalAbsolutePath();
        LocalUserHomeCreatorObjectMother.initializeDqoUserHomeAt(testUserHome.toString());

        UserHomeContextFactory userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithEmptyTemporaryContext();
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY, false);
        userHomePath = userHomeContext.getHomeRoot().getPhysicalAbsolutePath();

        secretValueLookupContext = new SecretValueLookupContext(userHomeContext.getUserHome());
        sut = new AzureCredentialsProviderImpl();
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
    void provideCredential_customCredentialsSet_returnsCredentials() throws IOException {

        Path defaultAwsCredentialsPath = userHomePath.resolve(BuiltInFolderNames.CREDENTIALS)
                .resolve(DefaultCloudCredentialFileNames.AZURE_DEFAULT_CREDENTIALS_NAME);

        Files.writeString(defaultAwsCredentialsPath, DefaultCloudCredentialFileContent.AZURE_DEFAULT_CREDENTIALS_INITIAL_CONTENT);

        String credentialFileContent = DefaultCloudCredentialFileContent.AZURE_DEFAULT_CREDENTIALS_INITIAL_CONTENT
                .replace("PLEASE_REPLACE_WITH_YOUR_AZURE_TENANT_ID", "my_tenant_id")
                .replace("PLEASE_REPLACE_WITH_YOUR_AZURE_CLIENT_ID", "my_client_id")
                .replace("PLEASE_REPLACE_WITH_YOUR_AZURE_CLIENT_SECRET", "my_client_secret")
                .replace("PLEASE_REPLACE_WITH_YOUR_AZURE_STORAGE_ACCOUNT_NAME", "my_account_name");

        Files.writeString(defaultAwsCredentialsPath, credentialFileContent);

        Optional<AzureCredential> azureCredential = this.sut.provideCredentials(secretValueLookupContext);

        Assertions.assertTrue(azureCredential.isPresent());
        Assertions.assertEquals("my_tenant_id", azureCredential.get().getTenantId());
        Assertions.assertEquals("my_client_id", azureCredential.get().getClientId());
        Assertions.assertEquals("my_client_secret", azureCredential.get().getClientSecret());
        Assertions.assertEquals("my_account_name", azureCredential.get().getAccountName());
    }

    @Test
    void provideCredential_defaultInitializationCredentials_thenThrowsException() throws IOException {
        Path defaultAzureCredentialsPath = userHomePath.resolve(BuiltInFolderNames.CREDENTIALS)
                .resolve(DefaultCloudCredentialFileNames.AZURE_DEFAULT_CREDENTIALS_NAME);

        Files.writeString(defaultAzureCredentialsPath, DefaultCloudCredentialFileContent.AZURE_DEFAULT_CREDENTIALS_INITIAL_CONTENT);

        Assertions.assertThrows(DqoRuntimeException.class, () -> {
                    Optional<AzureCredential> azureCredential = this.sut.provideCredentials(secretValueLookupContext);
        });
    }
}