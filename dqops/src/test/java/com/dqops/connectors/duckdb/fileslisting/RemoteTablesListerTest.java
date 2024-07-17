package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.BaseTest;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.config.DuckdbStorageType;
import com.dqops.metadata.sources.fileformat.CompressionType;
import com.dqops.metadata.sources.fileformat.csv.CsvFileFormatSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RemoteTablesListerTest extends BaseTest {

    private final String schema = "schema_name";
    private DuckdbParametersSpec duckdb;
    private RemoteTablesLister sut;

    @BeforeEach
    void setUp() {
        this.duckdb = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv).getDuckdb();
        this.sut = (RemoteTablesLister) TablesListerProviderObjectMother.getProvider().createTablesLister(DuckdbStorageType.s3);
    }

    @Test
    void filterAndTransform_fromFiles_createCollection() {
        List<String> filesList = List.of("file_1.csv", "file_2.csv", "file_3.csv");

        List<SourceTableModel> sourceTableModels = sut.filterAndTransform(duckdb, filesList, schema, null, 10);

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
    void filterAndTransform_fromFilesWhereTableContainsFilterApplied_createCollection() {
        List<String> filesList = List.of("file_1.csv", "file_2.csv", "file_3.csv");

        List<SourceTableModel> sourceTableModels = sut.filterAndTransform(duckdb, filesList, schema, "2", 10);

        assertThat(sourceTableModels)
                .hasSize(1)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().toString())
                .containsExactlyInAnyOrder(
                        "schema_name.file_2.csv"
                );
    }

    @Test
    void filterAndTransform_fromFilesWhereLimitApplied_createCollection() {
        List<String> filesList = List.of("file_1.csv", "file_2.csv", "file_3.csv");

        List<SourceTableModel> sourceTableModels = sut.filterAndTransform(duckdb, filesList, schema, null, 2);

        assertThat(sourceTableModels)
                .hasSize(2)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().toString())
                .containsExactlyInAnyOrder(
                        "schema_name.file_1.csv",
                        "schema_name.file_2.csv"
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