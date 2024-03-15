package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.BaseTest;
import com.dqops.connectors.duckdb.*;
import com.dqops.metadata.sources.ConnectionSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void testConnection_whenPathCantBeListedOnS3DueToNoCredentials_throwException() {

        this.sourceConnection.getConnectionSpec().getDuckdb().setStorageType(DuckdbStorageType.s3);

        Map<String, String> directories = sourceConnection.getConnectionSpec()
                .getDuckdb()
                .getDirectories();

        directories.put("files", "");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "Cant connect to AWS S3. Verify credentials.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testConnection_whenPathReturnsEmptyFolderOnS3_throwException() {

        // todo: add the credentials

        Map<String, String> directories = sourceConnection.getConnectionSpec()
                .getDuckdb()
                .getDirectories();

        directories.put("files", "s3://dqops-duckdb-test/empty_folder/");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "No files found in the path s3://dqops-duckdb-test/empty_folder/.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testConnection_whenPathReturnsEmptyFolderOnLocal_throwException() {

        Map<String, String> directories = sourceConnection.getConnectionSpec()
                .getDuckdb()
                .getDirectories();

        directories.put("files", "c:/real_not_existing_folder");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.testConnection(sourceConnection);
        });

        String expectedMessage = "No files found in the path c:/real_not_existing_folder.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}