package com.dqops.duckdb.connectors;

import com.dqops.BaseTest;
import com.dqops.connectors.SourceSchemaModel;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.DuckdbSourceConnection;
import com.dqops.connectors.duckdb.DuckdbSourceConnectionObjectMother;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpecObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.sampledata.*;
import com.dqops.sampledata.files.SampleDataFilesProvider;
import com.zaxxer.hikari.HikariConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class DuckdbSourceConnectionIntegrationTest extends BaseTest {

    private DuckdbSourceConnection sut;
    private SecretValueLookupContext secretValueLookupContext;
    private final String schemaName = "example_schema";
    private final String connectionName = "test_connection";
    private final String tableName = "example_table";

    @BeforeEach
    void setUp() {
        this.sut = DuckdbSourceConnectionObjectMother.getDuckdbSourceConnection();
        UserHomeContext userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
        UserHome userHome = userHomeContext.getUserHome();
        userHome.getConnections().createAndAddNew(connectionName);
        secretValueLookupContext = new SecretValueLookupContext(userHome);
    }

    @Test
    void retrieveTableMetadata_fromTableSpecWithCsvFilePath_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);

        ConnectionWrapperImpl connectionWrapper = new ConnectionWrapperImpl();
        connectionWrapper.setSpec(spec);
        PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(physicalTableName);
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day);

        tableWrapper.getSpec().setFileFormat(fileFormatSpec);
        tableWrapper.getSpec().setHierarchyId(new HierarchyId("connections", connectionName, "tables", physicalTableName));

        List<String> tableNames = connectionWrapper
                .getTables().toList().stream()
                .map(tw -> tw.getPhysicalTableName().getTableName().toString())
                .collect(Collectors.toList());

        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, null, 300, tableNames, connectionWrapper, secretValueLookupContext);

        ColumnSpecMap firstTableColumns = tableSpecs.get(0).getColumns();
        ColumnSpec idColumn = firstTableColumns.get("id:INTEGER");
        Assertions.assertEquals("BIGINT", idColumn.getTypeSnapshot().getColumnType());
        Assertions.assertTrue(idColumn.getTypeSnapshot().getNullable());

        ColumnSpec dateColumn = firstTableColumns.get("date:LOCAL_DATE");
        Assertions.assertEquals("DATE", dateColumn.getTypeSnapshot().getColumnType());

        ColumnSpec valueColumn = firstTableColumns.get("value:STRING");
        Assertions.assertEquals("VARCHAR", valueColumn.getTypeSnapshot().getColumnType());
    }

    @Test
    void retrieveTableMetadata_fromConnectionSpecWithCsvFilePathPrefix_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForCsvFile("");
        spec.getDuckdb().getDirectories().put(schemaName, fileFormatSpec.getFilePaths().get(0));

        List<String> tableNames = List.of(SampleCsvFileNames.continuous_days_one_row_per_day);

        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, null, 300, tableNames, null, secretValueLookupContext);

        ColumnSpecMap firstTableColumns = tableSpecs.get(0).getColumns();
        ColumnSpec idColumn = firstTableColumns.get("id:INTEGER");
        Assertions.assertEquals("BIGINT", idColumn.getTypeSnapshot().getColumnType());
        Assertions.assertTrue(idColumn.getTypeSnapshot().getNullable());

        ColumnSpec dateColumn = firstTableColumns.get("date:LOCAL_DATE");
        Assertions.assertEquals("DATE", dateColumn.getTypeSnapshot().getColumnType());

        ColumnSpec valueColumn = firstTableColumns.get("value:STRING");
        Assertions.assertEquals("VARCHAR", valueColumn.getTypeSnapshot().getColumnType());
    }

    @Test
    void retrieveTableMetadata_schemaHasPrefixWithTrailingSlash_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForCsvFile("");
        String schemaPrefix = fileFormatSpec.getFilePaths().get(0) + "/";
        spec.getDuckdb().getDirectories().put(schemaName, schemaPrefix);

        List<String> tableNames = List.of(SampleCsvFileNames.continuous_days_one_row_per_day);

        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, null, 300, tableNames, null, secretValueLookupContext);

        ColumnSpecMap firstTableColumns = tableSpecs.get(0).getColumns();
        ColumnSpec idColumn = firstTableColumns.get("id:INTEGER");
        Assertions.assertEquals("BIGINT", idColumn.getTypeSnapshot().getColumnType());
        Assertions.assertTrue(idColumn.getTypeSnapshot().getNullable());

        ColumnSpec dateColumn = firstTableColumns.get("date:LOCAL_DATE");
        Assertions.assertEquals("DATE", dateColumn.getTypeSnapshot().getColumnType());

        ColumnSpec valueColumn = firstTableColumns.get("value:STRING");
        Assertions.assertEquals("VARCHAR", valueColumn.getTypeSnapshot().getColumnType());
    }

    @Test
    void retrieveTableMetadata_tableHasAbsolutePathToFile_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForCsvFile("");
        String schemaPrefix = fileFormatSpec.getFilePaths().get(0);
        spec.getDuckdb().getDirectories().put(schemaName, schemaPrefix);

        List<String> tableNames = List.of(SampleDataFilesProvider.getFile(SampleCsvFileNames.continuous_days_one_row_per_day).toString());

        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, null, 300, tableNames, null, secretValueLookupContext);

        ColumnSpecMap firstTableColumns = tableSpecs.get(0).getColumns();
        ColumnSpec idColumn = firstTableColumns.get("id:INTEGER");
        Assertions.assertEquals("BIGINT", idColumn.getTypeSnapshot().getColumnType());
        Assertions.assertTrue(idColumn.getTypeSnapshot().getNullable());

        ColumnSpec dateColumn = firstTableColumns.get("date:LOCAL_DATE");
        Assertions.assertEquals("DATE", dateColumn.getTypeSnapshot().getColumnType());

        ColumnSpec valueColumn = firstTableColumns.get("value:STRING");
        Assertions.assertEquals("VARCHAR", valueColumn.getTypeSnapshot().getColumnType());
    }

    @Test
    void retrieveTableMetadata_fromConnectionSpecWithJsonFilePathPrefix_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.json);
        this.sut.setConnectionSpec(spec);
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForJsonFile(SampleJsonFileNames.folder_path);
        spec.getDuckdb().getDirectories().put(schemaName, fileFormatSpec.getFilePaths().get(0));

        List<String> tableNames = List.of(
                SampleJsonFileNames.continuous_days_one_row_per_day.substring(SampleJsonFileNames.folder_path.length()));

        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, null, 300, tableNames, null, secretValueLookupContext);

        ColumnSpecMap firstTableColumns = tableSpecs.get(0).getColumns();
        ColumnSpec idColumn = firstTableColumns.get("id:INTEGER");
        Assertions.assertEquals("BIGINT", idColumn.getTypeSnapshot().getColumnType());
        Assertions.assertTrue(idColumn.getTypeSnapshot().getNullable());

        ColumnSpec dateColumn = firstTableColumns.get("date:LOCAL_DATE");
        Assertions.assertEquals("DATE", dateColumn.getTypeSnapshot().getColumnType());

        ColumnSpec valueColumn = firstTableColumns.get("value:STRING");
        Assertions.assertEquals("VARCHAR", valueColumn.getTypeSnapshot().getColumnType());
    }

    @Test
    void retrieveTableMetadata_fromConnectionSpecWithParquetFilePathPrefix_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.parquet);
        this.sut.setConnectionSpec(spec);
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForParquetFile(SampleParquetFileNames.folder_path);
        spec.getDuckdb().getDirectories().put(schemaName, fileFormatSpec.getFilePaths().get(0));

        List<String> tableNames = List.of(
                SampleParquetFileNames.continuous_days_one_row_per_day.substring(SampleParquetFileNames.folder_path.length()));

        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, null, 300, tableNames, null, secretValueLookupContext);

        ColumnSpecMap firstTableColumns = tableSpecs.get(0).getColumns();
        ColumnSpec idColumn = firstTableColumns.get("id:INTEGER");
        Assertions.assertEquals("BIGINT", idColumn.getTypeSnapshot().getColumnType());
        Assertions.assertTrue(idColumn.getTypeSnapshot().getNullable());

        ColumnSpec dateColumn = firstTableColumns.get("date:LOCAL_DATE");
        Assertions.assertEquals("DATE", dateColumn.getTypeSnapshot().getColumnType());

        ColumnSpec valueColumn = firstTableColumns.get("value:STRING");
        Assertions.assertEquals("VARCHAR", valueColumn.getTypeSnapshot().getColumnType());
    }

    @Test
    void retrieveTableMetadata_fromConnectionSpecWithIcebergFormat_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.iceberg);
        this.sut.setConnectionSpec(spec);
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForIcebergFormat(SampleIcebergDirectoryNames.folder_path);
        spec.getDuckdb().getDirectories().put(schemaName, fileFormatSpec.getFilePaths().get(0));

        List<String> tableNames = List.of(
                SampleIcebergDirectoryNames.lineitem_iceberg_dataset.substring(SampleIcebergDirectoryNames.folder_path.length()));

        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, null, 300,
                tableNames, null, secretValueLookupContext);

        ColumnSpecMap firstTableColumns = tableSpecs.get(0).getColumns();
        ColumnSpec idColumn = firstTableColumns.get("l_orderkey");
        Assertions.assertEquals("INTEGER", idColumn.getTypeSnapshot().getColumnType());
        Assertions.assertTrue(idColumn.getTypeSnapshot().getNullable());

        ColumnSpec dateColumn = firstTableColumns.get("l_linestatus");
        Assertions.assertEquals("VARCHAR", dateColumn.getTypeSnapshot().getColumnType());

        ColumnSpec valueColumn = firstTableColumns.get("l_shipdate");
        Assertions.assertEquals("DATE", valueColumn.getTypeSnapshot().getColumnType());
    }

    @Test
    void retrieveTableMetadata_fromConnectionSpecWithDeltaLakeFormat_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.delta_lake);
        this.sut.setConnectionSpec(spec);
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForIcebergFormat(SampleDeltaLakeDirectoryNames.folder_path);
        spec.getDuckdb().getDirectories().put(schemaName, fileFormatSpec.getFilePaths().get(0));

        List<String> tableNames = List.of(
                SampleDeltaLakeDirectoryNames.people_countries.substring(SampleDeltaLakeDirectoryNames.folder_path.length()));

        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, null, 300,
                tableNames, null, secretValueLookupContext);

        ColumnSpecMap firstTableColumns = tableSpecs.get(0).getColumns();
        ColumnSpec idColumn = firstTableColumns.get("first_name");
        Assertions.assertEquals("VARCHAR", idColumn.getTypeSnapshot().getColumnType());
        Assertions.assertTrue(idColumn.getTypeSnapshot().getNullable());

    }

    @Test
    void listSchemas_whenSchemaIsAvailable_returnsListWithTheSchema() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);
        spec.getDuckdb().getDirectories().put(schemaName, tableName);

        List<SourceSchemaModel> sourceSchemaModels = sut.listSchemas();

        Assertions.assertEquals(1, sourceSchemaModels.size());
        Assertions.assertEquals(schemaName, sourceSchemaModels.get(0).getSchemaName());
    }

    @Test
    void listTables_schemaWithNoTrailingSlash_returnsAllFourFiles() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);
        String pathPrefix = SampleDataFilesProvider.getFile(SampleCsvFilesFolderNames.continuous_days_one_row_per_day_divided).toString();
        spec.getDuckdb().getDirectories().put(schemaName, pathPrefix);

        List<SourceTableModel> sourceTableModels = sut.listTables(schemaName, null, 300, secretValueLookupContext);

        Assertions.assertEquals(4, sourceTableModels.size());
        Assertions.assertTrue(sourceTableModels.get(0).getTableName().getTableName().equals("continuous_days_one_row_per_day_1.csv"));
        Assertions.assertTrue(sourceTableModels.get(1).getTableName().getTableName().equals("continuous_days_one_row_per_day_2.csv"));
        Assertions.assertTrue(sourceTableModels.get(2).getTableName().getTableName().equals("continuous_days_one_row_per_day_3.csv"));
        Assertions.assertTrue(sourceTableModels.get(3).getTableName().getTableName().equals("header.csv"));
    }

    @Test
    void listTables_schemaPointsToPathWithFolders_returnsAllThreeFolders() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);   // not really use it that the test checks listing folders
        this.sut.setConnectionSpec(spec);
        String pathPrefix = SampleDataFilesProvider.getFile("files/").toString();
        spec.getDuckdb().getDirectories().put(schemaName, pathPrefix);

        List<SourceTableModel> sourceTableModels = sut.listTables(schemaName, null, 300, secretValueLookupContext);

        Assertions.assertEquals(3, sourceTableModels.size());
        Assertions.assertTrue(sourceTableModels.get(0).getTableName().getTableName().contains("csv"));
        Assertions.assertTrue(sourceTableModels.get(1).getTableName().getTableName().contains("json"));
        Assertions.assertTrue(sourceTableModels.get(2).getTableName().getTableName().contains("parquet"));
    }

    @Test
    void listTables_schemaPrefixHasTrailingSlash_returnsFiles() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);
        String pathPrefix = SampleDataFilesProvider.getFile(SampleCsvFilesFolderNames.continuous_days_one_row_per_day_divided) + "/";
        spec.getDuckdb().getDirectories().put(schemaName, pathPrefix);

        List<SourceTableModel> sourceTableModels = sut.listTables(schemaName, null, 300, secretValueLookupContext);

        Assertions.assertEquals(4, sourceTableModels.size());
        Assertions.assertTrue(sourceTableModels.get(0).getTableName().getTableName().equals("continuous_days_one_row_per_day_1.csv"));
        Assertions.assertTrue(sourceTableModels.get(1).getTableName().getTableName().equals("continuous_days_one_row_per_day_2.csv"));
        Assertions.assertTrue(sourceTableModels.get(2).getTableName().getTableName().equals("continuous_days_one_row_per_day_3.csv"));
        Assertions.assertTrue(sourceTableModels.get(3).getTableName().getTableName().equals("header.csv"));
    }

    @Test
    void listTables_schemaPrefixHasTrailingBackSlash_returnsFiles() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);
        String pathPrefix = SampleDataFilesProvider.getFile(SampleCsvFilesFolderNames.continuous_days_one_row_per_day_divided) + "\\";
        spec.getDuckdb().getDirectories().put(schemaName, pathPrefix);

        List<SourceTableModel> sourceTableModels = sut.listTables(schemaName, null, 300, secretValueLookupContext);

        Assertions.assertEquals(4, sourceTableModels.size());
        Assertions.assertTrue(sourceTableModels.get(0).getTableName().getTableName().equals("continuous_days_one_row_per_day_1.csv"));
        Assertions.assertTrue(sourceTableModels.get(1).getTableName().getTableName().equals("continuous_days_one_row_per_day_2.csv"));
        Assertions.assertTrue(sourceTableModels.get(2).getTableName().getTableName().equals("continuous_days_one_row_per_day_3.csv"));
        Assertions.assertTrue(sourceTableModels.get(3).getTableName().getTableName().equals("header.csv"));
    }

    @Test
    void createHikariConfig_onBlankKeyAnvValue_propertyIsNotAdded() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);
        spec.getDuckdb().setProperties(new HashMap<>(){{
            put("", "");
        }});

        HikariConfig hikariConfig = sut.createHikariConfig(null);

        Assertions.assertEquals(0, hikariConfig.getDataSourceProperties().size());
    }

    @Test
    void createHikariConfig_oneKeyWithValue_propertysContrainIt() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);
        spec.getDuckdb().setProperties(new HashMap<>(){{
            put("some_key", "some_value");
        }});

        HikariConfig hikariConfig = sut.createHikariConfig(null);

        Assertions.assertEquals(1, hikariConfig.getDataSourceProperties().size());
        Assertions.assertEquals("some_value", hikariConfig.getDataSourceProperties().get("some_key"));
    }

    @Test
    void listTables_whenUsedTableNameContainsFilter_thenReturnTableThatMatchFilter() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        this.sut.setConnectionSpec(spec);

        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForCsvFile("");
        String schemaPrefix = fileFormatSpec.getFilePaths().get(0) + "/";
        spec.getDuckdb().getDirectories().put(schemaName, schemaPrefix);

        String tableName = SampleCsvFileNames.nulls_and_uniqueness.replace(".csv", "");
        String tableFilter = tableName.substring(2, tableName.length() - 3);

        List<SourceTableModel> tables = this.sut.listTables(schemaName, tableFilter, 300, secretValueLookupContext);

        Assertions.assertEquals(1, tables.size());
    }

}