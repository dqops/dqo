package com.dqops.metadata.sources.fileformat;

import com.dqops.BaseTest;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbSourceFilesType;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.TableSpec;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileFormatSpecTest extends BaseTest {

    private FileFormatSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new FileFormatSpec();
    }

    @Test
    void buildTableOptionsString_whenSourceFilesTypeIsNull_throwsException() {
        DuckdbParametersSpec duckdb = new DuckdbParametersSpec();

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()->{
                    sut.buildTableOptionsString(duckdb, null);
                });
    }

    @Test
    void buildTableOptionsString_whenSourceFilesTypeIsCsv_callsCsvDuckdbReadMethod() {
        this.sut.setCsv(new CsvFileFormatSpec());
        this.sut.getFilePaths().add("/a/file/path");
        DuckdbParametersSpec duckdb = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.csv).getDuckdb();
        TableSpec tableSpec = new TableSpec();
        tableSpec.getColumns().put("col1", new ColumnSpec(){{
            setTypeSnapshot(new ColumnTypeSnapshotSpec("STRING"));
        }});

        String output = this.sut.buildTableOptionsString(duckdb, tableSpec);

        assertTrue(output.contains("read_csv"));
    }

    @Test
    void buildTableOptionsString_whenSourceFilesTypeIsJson_callsJsonDuckdbReadMethod() {
        this.sut.setJson(new JsonFileFormatSpec());
        this.sut.getFilePaths().add("/a/file/path");
        DuckdbParametersSpec duckdb = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.json).getDuckdb();
        TableSpec tableSpec = new TableSpec();
        tableSpec.getColumns().put("col1", new ColumnSpec(){{
            setTypeSnapshot(new ColumnTypeSnapshotSpec("STRING"));
        }});

        String output = this.sut.buildTableOptionsString(duckdb, tableSpec);

        assertTrue(output.contains("read_json"));
    }

    @Test
    void buildTableOptionsString_whenSourceFilesTypeIsParquet_callsParquetDuckdbReadMethod() {
        this.sut.setParquet(new ParquetFileFormatSpec());
        this.sut.getFilePaths().add("/a/file/path");
        DuckdbParametersSpec duckdb = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.parquet).getDuckdb();
        TableSpec tableSpec = new TableSpec();
        tableSpec.getColumns().put("col1", new ColumnSpec(){{
            setTypeSnapshot(new ColumnTypeSnapshotSpec("STRING"));
        }});

        String output = this.sut.buildTableOptionsString(duckdb, tableSpec);

        assertTrue(output.contains("read_parquet"));
    }

}