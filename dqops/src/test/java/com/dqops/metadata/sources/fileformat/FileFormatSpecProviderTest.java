package com.dqops.metadata.sources.fileformat;

import com.dqops.BaseTest;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbSourceFilesType;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.files.SampleDataFilesProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileFormatSpecProviderTest extends BaseTest {

    @Test
    void resolveFileFormat_whenAvailableOnTableAndParameters_retunsFormTable() {

        TableSpec tableSpec = new TableSpec();
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
    void resolveFileFormat_whenNotSetOnTable_retunsFormParameters() {

        TableSpec tableSpec = new TableSpec();

        DuckdbParametersSpec duckdbParametersSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.csv).getDuckdb();
        duckdbParametersSpec.setCsv(new CsvFileFormatSpec(){{
                setAutoDetect(false);
        }});

        FileFormatSpec fileFormatSpec = FileFormatSpecProvider.resolveFileFormat(duckdbParametersSpec, tableSpec);

        Assertions.assertNotNull(fileFormatSpec);
        Assertions.assertEquals(0, fileFormatSpec.getFilePaths().size());
        Assertions.assertFalse(fileFormatSpec.getCsv().getAutoDetect());
    }

    @Test
    void resolveFileFormat_whenOnlyPathsSetOnFileFormat_retunsFormParametersWithPathsFormTable() {
        String sampleFileName = SampleCsvFileNames.continuous_days_one_row_per_day;
        TableSpec tableSpec = new TableSpec();
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
        TableSpec tableSpec = new TableSpec();
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
        TableSpec tableSpec = new TableSpec();
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



}