package com.dqops.connectors.duckdb;

import com.dqops.BaseTest;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpecObjectMother;
import com.dqops.metadata.storage.localfiles.sources.FileConnectionListImpl;
import com.dqops.metadata.storage.localfiles.sources.FileConnectionWrapperImpl;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.utils.serialization.YamlSerializer;
import com.dqops.utils.serialization.YamlSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class DuckdbSourceConnectionTest extends BaseTest {

    private DuckdbSourceConnection sut;
    private FileConnectionWrapperImpl fileConnectionWrapper;
    private SecretValueLookupContext secretValueLookupContext;

    @BeforeEach
    void setUp() {
        String connectionName = "test-connection";
        this.sut = DuckdbSourceConnectionObjectMother.getDuckdbSourceConnection();
        UserHomeContext userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
        UserHome userHome = userHomeContext.getUserHome();
        userHome.getConnections().createAndAddNew(connectionName);
        secretValueLookupContext = new SecretValueLookupContext(userHome);
        FileConnectionListImpl connections = (FileConnectionListImpl) userHome.getConnections();

        FolderTreeNode connectionFolder = connections.getSourcesFolder().getOrAddDirectFolder(connectionName);
        YamlSerializer yamlSerializer = YamlSerializerObjectMother.createNew();
        this.fileConnectionWrapper = new FileConnectionWrapperImpl(connectionFolder, yamlSerializer);
    }

    @Test
    void retrieveTableMetadata_fromTableSpecWithCsvFilePath_readColumnTypes() {
        ConnectionSpec spec = DuckdbConnectionSpecObjectMother.createForCsv();
        this.sut.setConnectionSpec(spec);
        this.fileConnectionWrapper.setSpec(spec);

        String tableSchemaName = "example_schema";
        String tableName = "example_table";
        TableWrapper tableWrapper = fileConnectionWrapper.getTables().createAndAddNew(new PhysicalTableName(tableSchemaName, tableName));

        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day);
        tableWrapper.getSpec().setFileFormat(fileFormatSpec);

        List<String> tableNames = fileConnectionWrapper
                .getTables().toList().stream()
                .map(tw -> tw.getPhysicalTableName().toString())
                .collect(Collectors.toList());


        this.sut.open(secretValueLookupContext);
        List<TableSpec> tableSpecs = sut.retrieveTableMetadata(tableSchemaName, tableNames, fileConnectionWrapper);


        ColumnSpecMap firstTableColumns = tableSpecs.get(0).getColumns();
        ColumnSpec idColumn = firstTableColumns.get("id:INTEGER");
        Assertions.assertEquals("BIGINT", idColumn.getTypeSnapshot().getColumnType());
        Assertions.assertTrue(idColumn.getTypeSnapshot().getNullable());

        ColumnSpec dateColumn = firstTableColumns.get("date:LOCAL_DATE");
        Assertions.assertEquals("DATE", dateColumn.getTypeSnapshot().getColumnType());

        ColumnSpec valueColumn = firstTableColumns.get("value:STRING");
        Assertions.assertEquals("VARCHAR", valueColumn.getTypeSnapshot().getColumnType());

    }
}