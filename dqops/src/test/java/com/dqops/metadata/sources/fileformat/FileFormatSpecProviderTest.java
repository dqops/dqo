package com.dqops.metadata.sources.fileformat;

import com.dqops.BaseTest;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbSourceFilesType;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.files.SampleDataFilesProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

@SpringBootTest
public class FileFormatSpecProviderTest extends BaseTest {

    @Test
    void resolveFileFormat_whenAvailableOnTableAndParameters_retunsFormTable() {

        TableSpec tableSpec = new TableSpec(new PhysicalTableName("schema_name_example", "table_name_example"));
        tableSpec.setFileFormat(FileFormatSpecObjectMother.createForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day));

        DuckdbParametersSpec duckdbParametersSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.csv).getDuckdb();
        duckdbParametersSpec.setCsv(new CsvFileFormatSpec(){{
                setAutoDetect(false);
        }});

        FileFormatSpec fileFormatSpec = FileFormatSpecProvider.resolveFileFormat(duckdbParametersSpec, tableSpec);

        Assertions.assertNotNull(fileFormatSpec);
        Assertions.assertEquals(1, fileFormatSpec.getFilePaths().size());
        Assertions.assertTrue(fileFormatSpec.getCsv().getAutoDetect());
    }

    @Test
    void resolveFileFormat_whenNotSetOnTable_guessesFormConnectionParameters() {
        String schemaName = "schema_name_example";
        String tableName = "a/file/path.csv";
        String pathPrefix = "prefix_example";
        TableSpec tableSpec = new TableSpec(new PhysicalTableName(schemaName, tableName));

        DuckdbParametersSpec duckdbParametersSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.csv).getDuckdb();
        duckdbParametersSpec.setCsv(new CsvFileFormatSpec(){{
                setAutoDetect(false);
        }});
        duckdbParametersSpec.getDirectories().put(schemaName, pathPrefix);

        FileFormatSpec fileFormatSpec = FileFormatSpecProvider.resolveFileFormat(duckdbParametersSpec, tableSpec);

        Assertions.assertNotNull(fileFormatSpec);
        Assertions.assertEquals(1, fileFormatSpec.getFilePaths().size());
        Assertions.assertEquals(fileFormatSpec.getFilePaths().get(0),
                Path.of(pathPrefix,tableName).toString());
        Assertions.assertFalse(fileFormatSpec.getCsv().getAutoDetect());
    }

    @Test
    void resolveFileFormat_whenOnlyPathsSetOnFileFormat_retunsFormParametersWithPathsFormTable() {
        String sampleFileName = SampleCsvFileNames.continuous_days_one_row_per_day;
        TableSpec tableSpec = new TableSpec(new PhysicalTableName("schema_name_example", "table_name_example"));
        tableSpec.setFileFormat(
            new FileFormatSpec() {{
                setFilePaths(new FilePathListSpec(){{
                    add(SampleDataFilesProvider.getFile(sampleFileName).toString());
                }});
        }});

        DuckdbParametersSpec duckdbParametersSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.csv).getDuckdb();
        duckdbParametersSpec.setCsv(new CsvFileFormatSpec(){{
                setAutoDetect(false);
        }});

        FileFormatSpec fileFormatSpec = FileFormatSpecProvider.resolveFileFormat(duckdbParametersSpec, tableSpec);

        Assertions.assertNotNull(fileFormatSpec);
        Assertions.assertEquals(1, fileFormatSpec.getFilePaths().size());
        Assertions.assertFalse(fileFormatSpec.getCsv().getAutoDetect());
    }

    @Test
    void resolveFileFormat_whenOnlyPathsSetOnTableAndFormatOnParamsNotSet_returnsNewFormatWithPaths() {
        String sampleFileName = SampleCsvFileNames.continuous_days_one_row_per_day;
        TableSpec tableSpec = new TableSpec(new PhysicalTableName("schema_name_example", "table_name_example"));
        tableSpec.setFileFormat(
                new FileFormatSpec() {{
                    setFilePaths(new FilePathListSpec(){{
                        add(SampleDataFilesProvider.getFile(sampleFileName).toString());
                    }});
                }});

        DuckdbParametersSpec duckdbParametersSpec = new DuckdbParametersSpec(){{setSourceFilesType(DuckdbSourceFilesType.csv);}};

        FileFormatSpec fileFormatSpec = FileFormatSpecProvider.resolveFileFormat(duckdbParametersSpec, tableSpec);

        Assertions.assertNotNull(fileFormatSpec);
        Assertions.assertEquals(1, fileFormatSpec.getFilePaths().size());
        Assertions.assertTrue(fileFormatSpec.getCsv().getAutoDetect());
    }

    @Test
    void resolveFileFormat_whenDuckdbSourceFilesTypeIsNotSet_returnsNull() {
        String sampleFileName = SampleCsvFileNames.continuous_days_one_row_per_day;
        TableSpec tableSpec = new TableSpec(new PhysicalTableName("schema_name_example", "table_name_example"));
        tableSpec.setFileFormat(
                new FileFormatSpec() {{
                    setFilePaths(new FilePathListSpec(){{
                        add(SampleDataFilesProvider.getFile(sampleFileName).toString());
                    }});
                }});

        DuckdbParametersSpec duckdbParametersSpec = new DuckdbParametersSpec();

        FileFormatSpec fileFormatSpec = FileFormatSpecProvider.resolveFileFormat(duckdbParametersSpec, tableSpec);

        Assertions.assertNull(fileFormatSpec);
    }

    @Test
    void guessFilePaths_whenTableNameEndsWithExtension_returnsIt() {
        String schemaName = "schema_name_example";
        String filePath = "prefix_example";
        String tableName = "table_name_example.csv";

        TableSpec tableSpec = new TableSpec(new PhysicalTableName(schemaName, tableName));

        DuckdbParametersSpec duckdbParametersSpec = new DuckdbParametersSpec();
        duckdbParametersSpec.setDirectories(Map.of(schemaName, filePath));
        duckdbParametersSpec.setSourceFilesType(DuckdbSourceFilesType.csv);

        FilePathListSpec fileFormatSpec = FileFormatSpecProvider.guessFilePaths(duckdbParametersSpec, tableSpec);

        String expectedTablePath = Path.of(filePath, tableName).toString();

        Assertions.assertNotNull(fileFormatSpec);
        Assertions.assertEquals(expectedTablePath, fileFormatSpec.get(0));
    }

    @Test
    void guessFilePaths_whenTableNameDoesNotEndsWithExtension_returnsIt() {
        String schemaName = "schema_name_example";
        String filesFolder = "clients";
        String tableName = "table_name_example";

        TableSpec tableSpec = new TableSpec(new PhysicalTableName(schemaName, tableName));

        DuckdbParametersSpec duckdbParametersSpec = new DuckdbParametersSpec();
        duckdbParametersSpec.setDirectories(Map.of(schemaName, filesFolder));
        duckdbParametersSpec.setSourceFilesType(DuckdbSourceFilesType.csv);

        FilePathListSpec fileFormatSpec = FileFormatSpecProvider.guessFilePaths(duckdbParametersSpec, tableSpec);

        String expectedTablePath = filesFolder + File.separator + tableName + File.separator + "**.csv";
        Assertions.assertNotNull(fileFormatSpec);
        Assertions.assertEquals(expectedTablePath, fileFormatSpec.get(0));
    }

    @Test
    void guessFilePaths_whenTableIsAnAbsolutePath_doNotUseSchemaInAPath() {
        String schemaName = "schema_name_example";
        String filesFolder = "/dev/all_clients";
        String tableName = "/dev/specific_clients";

        TableSpec tableSpec = new TableSpec(new PhysicalTableName(schemaName, tableName));

        DuckdbParametersSpec duckdbParametersSpec = new DuckdbParametersSpec();
        duckdbParametersSpec.setDirectories(Map.of(schemaName, filesFolder));
        duckdbParametersSpec.setSourceFilesType(DuckdbSourceFilesType.csv);

        FilePathListSpec fileFormatSpec = FileFormatSpecProvider.guessFilePaths(duckdbParametersSpec, tableSpec);

        String expectedTablePath = tableName + File.separator + "**.csv";
        Assertions.assertNotNull(fileFormatSpec);
        Assertions.assertEquals(expectedTablePath, fileFormatSpec.get(0));
    }

    @Test
    void guessFilePaths_whenTableIsAnAbsolutePathToFileWithExtension_useTableNameOnly() {
        String schemaName = "schema_name_example";
        String filesFolder = "/dev/all_clients";
        String tableName = "/dev/specific_clients.csv";

        TableSpec tableSpec = new TableSpec(new PhysicalTableName(schemaName, tableName));

        DuckdbParametersSpec duckdbParametersSpec = new DuckdbParametersSpec();
        duckdbParametersSpec.setDirectories(Map.of(schemaName, filesFolder));
        duckdbParametersSpec.setSourceFilesType(DuckdbSourceFilesType.csv);

        FilePathListSpec fileFormatSpec = FileFormatSpecProvider.guessFilePaths(duckdbParametersSpec, tableSpec);

        Assertions.assertNotNull(fileFormatSpec);
        Assertions.assertEquals(tableName, fileFormatSpec.get(0));
    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void isPathAbsoluteSystemsWide_forWindowsWithFlippedSlash_returnsTrue() {
        Assertions.assertTrue(FileFormatSpecProvider.isPathAbsoluteSystemsWide("c:/dev"));
    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void isPathAbsoluteSystemsWide_forWindows_returnsTrue() {
        Assertions.assertTrue(FileFormatSpecProvider.isPathAbsoluteSystemsWide("c:\\dev"));
    }

    @Test
    void isPathAbsoluteSystemsWide_startsWithSlash_returnsTrue() {
        Assertions.assertTrue(FileFormatSpecProvider.isPathAbsoluteSystemsWide("/dev/sth"));
    }

    @Test
    void isPathAbsoluteSystemsWide_startsWithSlashFlipped_returnsTrue() {
        Assertions.assertTrue(FileFormatSpecProvider.isPathAbsoluteSystemsWide("\\dev\\sth"));
    }

    @Test
    void isPathAbsoluteSystemsWide_startsWithDot_returnsFalse() {
        Assertions.assertFalse(FileFormatSpecProvider.isPathAbsoluteSystemsWide("./dev/sth"));
    }

    @Test
    void isPathAbsoluteSystemsWide_isAws_returnsTrue() {
        Assertions.assertTrue(FileFormatSpecProvider.isPathAbsoluteSystemsWide("s3://project_123qwe/clients"));
    }

}