package com.dqops.duckdb;

import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.table.volume.TableVolumeRowCountSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

@SpringBootTest
public class DuckdbReadCsvIntegrationTest extends BaseDuckdbIntegrationTest {

    private HomeLocationFindService homeLocationFindService;
    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;
    private TableRowCountCheckSpec checkSpec;
    private TableVolumeRowCountSensorParametersSpec tableVolumeRowCountSensorParametersSpec;

    @BeforeEach
    void setUp() {

        ConnectionSpec connectionSpec = new ConnectionSpec();
        connectionSpec.setProviderType(ProviderType.duckdb);
        DuckdbParametersSpec duckdbParametersSpec = new DuckdbParametersSpec();
        connectionSpec.setDuckdb(duckdbParametersSpec);

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForLocalCsvFile(
                SampleCsvFileNames.continuous_days_one_row_per_day, connectionSpec);
//        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        this.tableVolumeRowCountSensorParametersSpec = new TableVolumeRowCountSensorParametersSpec();
        this.checkSpec = new TableRowCountCheckSpec();
        this.checkSpec.setParameters(this.tableVolumeRowCountSensorParametersSpec);
        TableVolumeProfilingChecksSpec category = new TableVolumeProfilingChecksSpec();
        this.sampleTableMetadata.getTableSpec().getProfilingChecks().setVolume(category);
        category.setProfileRowCount(this.checkSpec);
    }

    @Test
    void runSensor_withUseOfLocalCsvFile_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForProfilingCheck(
                sampleTableMetadata, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(24L, resultTable.column(0).get(0));
    }

}
