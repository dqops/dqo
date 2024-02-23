package com.dqops.connectors.duckdb;

import com.dqops.BaseTest;
import com.dqops.connectors.SourceSchemaModel;
import com.dqops.connectors.SourceTableModel;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpecObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleJsonFileNames;
import com.dqops.sampledata.SampleParquetFileNames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class DuckdbSourceConnectionTest extends BaseTest {

    private DuckdbSourceConnection sut;
    private ConnectionWrapperImpl connectionWrapper;
    private SecretValueLookupContext secretValueLookupContext;
    private String connectionName = "test-connection";
    private String schemaName = "example_schema";
    private String tableName = "example_table";

    @BeforeEach
    void setUp() {
        this.sut = DuckdbSourceConnectionObjectMother.getDuckdbSourceConnection();
        UserHomeContext userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
        UserHome userHome = userHomeContext.getUserHome();
        userHome.getConnections().createAndAddNew(connectionName);
        secretValueLookupContext = new SecretValueLookupContext(userHome);
        this.connectionWrapper = new ConnectionWrapperImpl();
    }

    @Test
    void retrieveTableMetadata_fromTableSpecWithCsvFilePath_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.csv);
        this.sut.setConnectionSpec(spec);
        this.connectionWrapper.setSpec(spec);
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName(schemaName, tableName));
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day);
        tableWrapper.getSpec().setFileFormat(fileFormatSpec);
        List<String> tableNames = connectionWrapper
                .getTables().toList().stream()
                .map(tw -> tw.getPhysicalTableName().toString())
                .collect(Collectors.toList());


        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, tableNames, connectionWrapper, secretValueLookupContext);


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
    void retrieveTableMetadata_fromTableSpecWithJsonFilePath_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.json);
        this.sut.setConnectionSpec(spec);
        this.connectionWrapper.setSpec(spec);
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName(schemaName, tableName));
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForJsonFile(SampleJsonFileNames.continuous_days_one_row_per_day);
        tableWrapper.getSpec().setFileFormat(fileFormatSpec);
        List<String> tableNames = connectionWrapper
                .getTables().toList().stream()
                .map(tw -> tw.getPhysicalTableName().toString())
                .collect(Collectors.toList());


        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, tableNames, connectionWrapper, secretValueLookupContext);


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
    void retrieveTableMetadata_fromTableSpecWithParquetFilePath_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.parquet);
        this.sut.setConnectionSpec(spec);
        this.connectionWrapper.setSpec(spec);
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName(schemaName, tableName));
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForParquetFile(SampleParquetFileNames.continuous_days_one_row_per_day);
        tableWrapper.getSpec().setFileFormat(fileFormatSpec);
        List<String> tableNames = connectionWrapper
                .getTables().toList().stream()
                .map(tw -> tw.getPhysicalTableName().toString())
                .collect(Collectors.toList());


        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(schemaName, tableNames, connectionWrapper, secretValueLookupContext);


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
    void listSchemas_whenSchemaIsAvailable_returnsListWithTheSchema() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.csv);
        this.sut.setConnectionSpec(spec);
        this.connectionWrapper.setSpec(spec);
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName(schemaName, tableName));
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day);
        tableWrapper.getSpec().setFileFormat(fileFormatSpec);

        List<SourceSchemaModel> sourceSchemaModels = sut.listSchemas();

        Assertions.assertEquals(1, sourceSchemaModels.size());
        Assertions.assertEquals(schemaName, sourceSchemaModels.get(0).getSchemaName());
    }

    @Test
    void listTables_whenOneTableInOneSchemaIsAvailable_returnsListWithTheTable() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.csv);
        this.sut.setConnectionSpec(spec);
        this.connectionWrapper.setSpec(spec);
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName(schemaName, tableName));
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day);
        tableWrapper.getSpec().setFileFormat(fileFormatSpec);

        List<SourceTableModel> sourceTableModels = sut.listTables(schemaName, connectionWrapper);

        Assertions.assertEquals(1, sourceTableModels.size());
        Assertions.assertEquals(tableName, sourceTableModels.get(0).getTableName().getTableName());
    }

    

}