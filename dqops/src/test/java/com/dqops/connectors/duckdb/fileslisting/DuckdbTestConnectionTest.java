package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.BaseTest;
import com.dqops.connectors.duckdb.*;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderImpl;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DuckdbTestConnectionTest extends BaseTest {

    private DuckdbTestConnection sut;
    private DuckdbSourceConnection sourceConnection;

    @BeforeEach
    void setUp() {
        this.sourceConnection = DuckdbSourceConnectionObjectMother.getDuckdbSourceConnection();
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sourceConnection.setConnectionSpec(spec);
        this.sut = new DuckdbTestConnection();
    }

    @Test
    void testConnection_whenListOfVirtualPathsIsEmpty_throwException() {

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "Virtual schema name is not configured in the Import configuration.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testConnection_whenPathIsNull_throwException() {

        Map<String, String> directories = sourceConnection.getConnectionSpec()
                .getDuckdb()
                .getDirectories();

        directories.put("files", null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "A path is not filled in the schema files.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testConnection_whenPathIsEmptyString_throwException() {

        Map<String, String> directories = sourceConnection.getConnectionSpec()
                .getDuckdb()
                .getDirectories();

        directories.put("files", "");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "A path is not filled in the schema files.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testConnection_whenPathCantBeListedOnS3DueToBlankAccessKey_throwException() {

        this.sourceConnection.getConnectionSpec().getDuckdb().setStorageType(DuckdbStorageType.s3);

        Map<String, String> directories = sourceConnection.getConnectionSpec()
                .getDuckdb()
                .getDirectories();

        directories.put("files", "asdasd");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "Access key ID cannot be blank.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testConnection_whenPathCantBeListedOnS3DueToNoCredentials_throwException() {

        DuckdbParametersSpec duckdbParametersSpec = sourceConnection.getConnectionSpec().getDuckdb();
        duckdbParametersSpec.setUser("asdasd");
        duckdbParametersSpec.setPassword("asdasd");
        duckdbParametersSpec.setRegion("us-east-2");
        duckdbParametersSpec.setStorageType(DuckdbStorageType.s3);

        Map<String, String> directories = duckdbParametersSpec.getDirectories();

        directories.put("files", "s3://dqops-duckdb-test");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "No files found in the path s3://dqops-duckdb-test";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testConnection_s3PathNotStartWithProtocol_throwException() {

        DuckdbParametersSpec duckdbParametersSpec = sourceConnection.getConnectionSpec().getDuckdb();
        duckdbParametersSpec.setUser("asdasd");
        duckdbParametersSpec.setPassword("asdasd");
        duckdbParametersSpec.setRegion("us-east-2");
        duckdbParametersSpec.setStorageType(DuckdbStorageType.s3);

        Map<String, String> directories = duckdbParametersSpec.getDirectories();

        directories.put("files", "asdasd");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "Invalid S3 URI: no hostname: asdasd";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testConnection_whenPathReturnsEmptyFolderOnS3_throwException() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        SecretValueProviderImpl secretValueProvider = beanFactory.getBean(SecretValueProviderImpl.class);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
        DuckdbParametersSpec duckdbParametersSpec = sourceConnection.getConnectionSpec().getDuckdb();
        duckdbParametersSpec.setUser(secretValueProvider.expandValue("${DQOPS_TEST_AWS_ACCESS_KEY_ID}", secretValueLookupContext));
        duckdbParametersSpec.setPassword(secretValueProvider.expandValue("${DQOPS_TEST_AWS_SECRET_ACCESS_KEY}", secretValueLookupContext));
        duckdbParametersSpec.setRegion(secretValueProvider.expandValue("${DQOPS_TEST_AWS_REGION}", secretValueLookupContext));
        duckdbParametersSpec.setStorageType(DuckdbStorageType.s3);


        Map<String, String> directories = sourceConnection.getConnectionSpec()
                .getDuckdb()
                .getDirectories();

        directories.put("files", "s3://dqops-duckdb-test/empty_folder/");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "No files found in the path s3://dqops-duckdb-test/empty_folder/";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testConnection_whenPathReturnsEmptyFolderOnLocal_throwException() {

        Map<String, String> directories = sourceConnection.getConnectionSpec()
                .getDuckdb()
                .getDirectories();

        directories.put("files", "c:/not_existing_folder");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "No files found in the path c:/not_existing_folder";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}