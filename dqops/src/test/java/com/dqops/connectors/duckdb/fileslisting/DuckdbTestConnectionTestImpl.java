package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.BaseTest;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbStorageType;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderImpl;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DuckdbTestConnectionImplTest extends BaseTest {

    private DuckdbTestConnectionImpl sut;
    private DuckdbParametersSpec duckdbParametersSpec;

    @BeforeEach
    void setUp() {
        this.duckdbParametersSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv).getDuckdb();
        this.sut = new DuckdbTestConnectionImpl();
    }

    @Test
    void testConnection_whenListOfVirtualPathsIsEmpty_throwException() {

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(duckdbParametersSpec);
        });

        String expectedMessage = "Virtual schema name is not configured in the Import configuration.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConnection_whenPathIsNull_throwException() {

        Map<String, String> directories = duckdbParametersSpec.getDirectories();

        directories.put("files", null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(duckdbParametersSpec);
        });

        String expectedMessage = "A path is not filled in the schema: files";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConnection_whenPathIsEmptyString_throwException() {

        Map<String, String> directories = duckdbParametersSpec.getDirectories();

        directories.put("files", "");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(duckdbParametersSpec);
        });

        String expectedMessage = "A path is not filled in the schema: files";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConnection_whenPathCantBeListedOnS3DueToBlankAccessKey_throwException() {
        this.duckdbParametersSpec.setStorageType(DuckdbStorageType.s3);

        Map<String, String> directories = duckdbParametersSpec.getDirectories();

        directories.put("files", "s3://asdasd");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(duckdbParametersSpec);
        });

        String expectedMessage = "Access key ID cannot be blank.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConnection_whenPathCantBeListedOnS3DueToNoCredentials_throwException() {
        this.duckdbParametersSpec.setUser("asdasd");
        this.duckdbParametersSpec.setPassword("asdasd");
        this.duckdbParametersSpec.setRegion("us-east-2");
        this.duckdbParametersSpec.setStorageType(DuckdbStorageType.s3);

        Map<String, String> directories = duckdbParametersSpec.getDirectories();

        directories.put("files", "s3://dqops-duckdb-test");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(duckdbParametersSpec);
        });

        String expectedMessage = "No files found in the path s3://dqops-duckdb-test";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConnection_s3PathNotStartWithSchema_throwException() {
        this.duckdbParametersSpec.setStorageType(DuckdbStorageType.s3);

        Map<String, String> directories = duckdbParametersSpec.getDirectories();

        directories.put("files", "asdasd");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(duckdbParametersSpec);
        });

        String expectedMessage = "S3 path for the schema files must start with s3://";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConnection_whenPathReturnsEmptyFolderOnS3_throwException() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        SecretValueProviderImpl secretValueProvider = beanFactory.getBean(SecretValueProviderImpl.class);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);

        this.duckdbParametersSpec.setUser(secretValueProvider.expandValue("${DQOPS_TEST_AWS_ACCESS_KEY_ID}", secretValueLookupContext));
        this.duckdbParametersSpec.setPassword(secretValueProvider.expandValue("${DQOPS_TEST_AWS_SECRET_ACCESS_KEY}", secretValueLookupContext));
        this.duckdbParametersSpec.setRegion(secretValueProvider.expandValue("${DQOPS_TEST_AWS_REGION}", secretValueLookupContext));
        this.duckdbParametersSpec.setStorageType(DuckdbStorageType.s3);

        Map<String, String> directories = duckdbParametersSpec.getDirectories();

        directories.put("files", "s3://dqops-duckdb-test/empty_folder/");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(duckdbParametersSpec);
        });

        String expectedMessage = "No files found in the path s3://dqops-duckdb-test/empty_folder/";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConnection_whenPathReturnsEmptyFolderOnLocal_throwException() {

        Map<String, String> directories = duckdbParametersSpec.getDirectories();

        directories.put("files", "c:/not_existing_folder");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(duckdbParametersSpec);
        });

        String expectedMessage = "No files found in the path c:/not_existing_folder";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}