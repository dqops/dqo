package com.dqops.duckdb.fileformat;

import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.duckdb.BaseDuckdbIntegrationTest;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.sampledata.SampleCsvFilesFolderNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.table.volume.TableVolumeRowCountSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

@SpringBootTest
public class DuckdbTableVolumeRowCountSensorParametersSpecFileIntegrationTest extends BaseDuckdbIntegrationTest {

    private HomeLocationFindService homeLocationFindService;
    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;
    private TableRowCountCheckSpec checkSpec;
    private TableVolumeRowCountSensorParametersSpec sut;

    @BeforeEach
    void setUp() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.create();
        String csvFilesFolder = SampleCsvFilesFolderNames.continuous_days_one_row_per_day_divided;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForLocalMultipleCsvFiles(csvFilesFolder, connectionSpec);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        this.sut = new TableVolumeRowCountSensorParametersSpec();
        this.checkSpec = new TableRowCountCheckSpec();
        this.checkSpec.setParameters(this.sut);
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
