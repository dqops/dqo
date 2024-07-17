package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.BaseTest;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.config.DuckdbStorageType;
import com.dqops.connectors.duckdb.fileslisting.azure.AzureTablesLister;
import com.dqops.metadata.sources.fileformat.CompressionType;
import com.dqops.metadata.sources.fileformat.csv.CsvFileFormatSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AzureTablesListerTest extends BaseTest {

    private final String schema = "schema_name";
    private DuckdbParametersSpec duckdb;
    private AzureTablesLister sut;

    @BeforeEach
    void setUp() {
        this.duckdb = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv).getDuckdb();
        this.sut = (AzureTablesLister) TablesListerProviderObjectMother.getProvider().createTablesLister(DuckdbStorageType.azure);
    }

    @Test
    void filterAndTransform_fromFiles_createCollection() {
        List<String> filesList = List.of("file_1.csv", "file_2.csv", "file_3.csv");
        DuckdbParametersSpec duckdb = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv).getDuckdb();

        List<SourceTableModel> sourceTableModels = sut.filterAndTransform(duckdb, filesList, schema, null, 300);

        assertThat(sourceTableModels)
                .hasSize(3)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().toString())
                .containsExactlyInAnyOrder(
                        "schema_name.file_1.csv",
                        "schema_name.file_2.csv",
                        "schema_name.file_3.csv"
                );
    }

    @Test
    void filterAndTransform_whenFileHasNoExtension_doesNotUseIt() {
        List<String> filesList = List.of("file_1");

        List<SourceTableModel> sourceTableModels = sut.filterAndTransform(duckdb, filesList, schema, null, 300);

        assertThat(sourceTableModels)
                .hasSize(0);
    }

    @Test
    void filterAndTransform_whenFileHasTrailingSlash_usesItWithoutSlash() {
        List<String> filesList = List.of("file_1/");

        List<SourceTableModel> sourceTableModels = sut.filterAndTransform(duckdb, filesList, schema, null, 300);

        assertThat(sourceTableModels)
                .hasSize(1)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().toString())
                .containsExactlyInAnyOrder(
                        "schema_name.file_1"
                );
    }

    @Test
    void filterAndTransform_fileIsGz_usesIt() {
        List<String> filesList = List.of("file_1.csv.gz");

        duckdb.setCsv(new CsvFileFormatSpec());
        duckdb.getCsv().setCompression(CompressionType.gzip);
        List<SourceTableModel> sourceTableModels = sut.filterAndTransform(duckdb, filesList, schema, null, 300);

        assertThat(sourceTableModels)
                .hasSize(1)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().toString())
                .containsExactlyInAnyOrder(
                        "schema_name.file_1.csv.gz"
                );
    }

}