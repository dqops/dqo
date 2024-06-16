/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.data.readouts.normalization;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.connectors.ProviderDialectSettingsObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.normalization.CommonTableNormalizationServiceImpl;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import com.dqops.execution.checks.EffectiveSensorRuleNames;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataStreamLevelSpecObjectMother;
import com.dqops.metadata.groupings.TimeSeriesConfigurationSpecObjectMother;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHomeImpl;
import com.dqops.metadata.userhome.UserHomeObjectMother;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

@SpringBootTest
public class SensorReadoutsNormalizationServiceImplTests extends BaseTest {
    private SensorReadoutsNormalizationServiceImpl sut;
    private Table table;
    private UserHomeImpl userHome;
    private TableSpec tableSpec;
    private TableRowCountCheckSpec checkSpec;
    private SensorExecutionRunParameters sensorExecutionRunParameters;
    private SensorExecutionResult sensorExecutionResult;
    private ZoneId utcZone;

    @BeforeEach
    void setUp() {
		this.sut = new SensorReadoutsNormalizationServiceImpl(
                new CommonTableNormalizationServiceImpl(),
                DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider());
        this.utcZone = ZoneId.of("UTC");
		this.table = Table.create("results");
		userHome = UserHomeObjectMother.createBareUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        connectionWrapper.getSpec().setProviderType(ProviderType.bigquery);
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "tab1"));
		tableSpec = tableWrapper.getSpec();
        tableSpec.setDefaultDataGroupingConfiguration(new DataGroupingConfigurationSpec());
		checkSpec = new TableRowCountCheckSpec();
        tableSpec.getProfilingChecks().setVolume(new TableVolumeProfilingChecksSpec());
		tableSpec.getProfilingChecks().getVolume().setProfileRowCount(checkSpec);
		sensorExecutionRunParameters = new SensorExecutionRunParameters(connectionWrapper.getSpec(), tableSpec, null,
				checkSpec,
                null,
                new EffectiveSensorRuleNames(checkSpec.getParameters().getSensorDefinitionName(), checkSpec.getRuleDefinitionName()),
                CheckType.profiling,
                null, // time series
                new TimeWindowFilterParameters(),
                tableSpec.getDefaultDataGroupingConfiguration(),
                null,
                null,
                checkSpec.getParameters(),
                ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.bigquery),
                null,
                1000,
                true,
                null);
		sensorExecutionResult = new SensorExecutionResult(this.sensorExecutionRunParameters, this.table);
    }

    @Test
    void analyzeAndPrepareResults_whenSchemaComparedWithTableCreatedBySensorReadoutFactory_thenSchemaMatches() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));

        SensorReadoutsNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);

        Table normalizedTable = results.getTable();
        SensorReadoutsTableFactoryImpl sensorReadoutTableFactory = new SensorReadoutsTableFactoryImpl();
        Table emptyTable = sensorReadoutTableFactory.createEmptySensorReadoutsTable("empty");

        Column<?> severityColumn = normalizedTable.column(CheckResultsColumnNames.SEVERITY_COLUMN_NAME);
        Assertions.assertNotNull(severityColumn);
        normalizedTable.removeColumns(severityColumn);

        Assertions.assertEquals(emptyTable.columnCount(), normalizedTable.columnCount());
        for(int i = 0; i < emptyTable.columnCount(); i++) {
            Assertions.assertEquals(emptyTable.column(i).name(), normalizedTable.column(i).name());
            Assertions.assertSame(emptyTable.column(i).type(), normalizedTable.column(i).type());
        }
    }

//    @Test
//    void analyzeAndPrepareResults_whenActualValueColumnMissing_thenThrowsException() {
//        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
//                CheckTimeScale.daily, "date"));
//        Assertions.assertThrows(SensorResultNormalizeException.class, () -> {
//			this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);
//        });
//    }

    @Test
    void analyzeAndPrepareResults_whenOnlyActualValueColumnPresentAndGradientDay_thenCreatesDatasetWithTimePeriodTodayAndDataStreamHash0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));

        SensorReadoutsNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);

        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getTimePeriodUtcColumn());
        Assertions.assertNotNull(results.getDataGroupHashColumn());
        LocalDateTime expectedTimePeriod = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        Assertions.assertEquals(expectedTimePeriod, results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(expectedTimePeriod.toInstant(TimeZone.getDefault().toZoneId().getRules().getOffset(expectedTimePeriod)), results.getTimePeriodUtcColumn().get(0));
        Assertions.assertEquals(0L, results.getDataGroupHashColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenOnlyActualValueColumnPresentAndGradientDayAndTimePeriodUtcPresent_thenCopiesTimePeriodUtc() {
        this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        LocalDateTime timePeriod = LocalDateTime.now(this.utcZone).minus(Period.ofDays(2)).truncatedTo(ChronoUnit.DAYS);
        this.table.addColumns(DateTimeColumn.create("time_period", timePeriod));
        Instant timePeriodUtc = timePeriod.toInstant(TimeZone.getDefault().toZoneId().getRules().getOffset(timePeriod));
        this.table.addColumns(InstantColumn.create("time_period_utc", timePeriodUtc));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));

        SensorReadoutsNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);

        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getTimePeriodUtcColumn());
        Assertions.assertNotNull(results.getDataGroupHashColumn());
        Assertions.assertEquals(timePeriod, results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(timePeriodUtc, results.getTimePeriodUtcColumn().get(0));
        Assertions.assertEquals(0L, results.getDataGroupHashColumn().get(0));
    }

//    @Test
//    void analyzeAndPrepareResults_whenOnlyActualValueColumnPresentAndGradientDay_thenCreatesDatasetWithTimePeriodTodayAndDataStreamHash0() {
//		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
//        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
//                CheckTimeScale.daily, "date"));
//
//        SensorReadoutsNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);
//
//        Assertions.assertNotNull(results.getTable());
//        Assertions.assertEquals(1, results.getTable().rowCount());
//        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
//        Assertions.assertNotNull(results.getActualValueColumn());
//        Assertions.assertNotNull(results.getTimePeriodColumn());
//        Assertions.assertNotNull(results.getTimePeriodUtcColumn());
//        Assertions.assertNotNull(results.getDataStreamHashColumn());
//        TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
//        LocalDateTime expectedTimePeriod = LocalDateTime.of(LocalDateTime.now(this.utcZone).toLocalDate().with(fieldUS, 1), LocalTime.MIDNIGHT);
//        Assertions.assertEquals(expectedTimePeriod, results.getTimePeriodColumn().get(0));
//        Assertions.assertEquals(expectedTimePeriod.toInstant(TimeZone.getDefault().toZoneId().getRules().getOffset(expectedTimePeriod)), results.getTimePeriodUtcColumn().get(0));
//        Assertions.assertEquals(0L, results.getDataStreamHashColumn().get(0));
//    }

    @Test
    void analyzeAndPrepareResults_whenOnlyActualValueColumnPresentAndGradientMonth_thenCreatesDatasetWithTimePeriodTodayAndDataStreamHash0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.monthly, "date"));

        SensorReadoutsNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);

        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getTimePeriodUtcColumn());
        Assertions.assertNotNull(results.getDataGroupHashColumn());
        LocalDateTime localTimeNow = LocalDateTime.now(this.utcZone);
        LocalDateTime expectedTimePeriod = LocalDateTime.of(LocalDate.of(localTimeNow.getYear(), localTimeNow.getMonth(), 1), LocalTime.MIDNIGHT);
        Assertions.assertEquals(expectedTimePeriod, results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(expectedTimePeriod.toInstant(TimeZone.getDefault().toZoneId().getRules().getOffset(expectedTimePeriod)), results.getTimePeriodUtcColumn().get(0));
        Assertions.assertEquals(0L, results.getDataGroupHashColumn().get(0));
        Assertions.assertEquals("no grouping", results.getDataGroupNameColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenActualValueAndTimePeriodGradientDay_thenCreatesDatasetWithTimePeriodTodayAndDataStreamHash0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
		this.table.addColumns(DateTimeColumn.create("time_period", LocalDateTime.now(this.utcZone).minus(Period.ofDays(2)).truncatedTo(ChronoUnit.DAYS)));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));

        SensorReadoutsNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);

        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getTimePeriodUtcColumn());
        Assertions.assertNotNull(results.getDataGroupHashColumn());
        LocalDateTime localTimeNow = LocalDateTime.now(this.utcZone).minus(Period.ofDays(2));
        Assertions.assertEquals(localTimeNow.truncatedTo(ChronoUnit.DAYS), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(0L, results.getDataGroupHashColumn().get(0));
        Assertions.assertEquals("no grouping", results.getDataGroupNameColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenActualValueAndTimePeriodPresentButGradientNone_thenCreatesDatasetWithTimePeriodTodayAndDataStreamHash0() {
        this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        LocalDateTime expectedTimePeriod = LocalDateTime.of(2022, 11, 21, 15, 16, 17);
        this.table.addColumns(DateTimeColumn.create("time_period", expectedTimePeriod));

        SensorReadoutsNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);
        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getTimePeriodUtcColumn());
        Assertions.assertNotNull(results.getDataGroupHashColumn());
        Assertions.assertEquals(expectedTimePeriod, results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(0L, results.getDataGroupHashColumn().get(0));
        Assertions.assertEquals("no grouping", results.getDataGroupNameColumn().get(0));
        Assertions.assertEquals("2ba1c831-8156-f37f-7741-4bd9eede5d4e", results.getTimeSeriesIdColumn().get(0));
        Assertions.assertEquals("15af7fba-7fdb-f10f-b6b0-03adefaca0c1", results.getIdColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenActualValueAndTimePeriodGradientMonthButResultIsAtDayScale_thenCreatesDatasetWithTimePeriodTodayAndDataStreamHash0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
		this.table.addColumns(DateTimeColumn.create("time_period", LocalDateTime.now(this.utcZone).truncatedTo(ChronoUnit.DAYS)));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.monthly, "date"));

        SensorReadoutsNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);

        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getTimePeriodUtcColumn());
        Assertions.assertNotNull(results.getDataGroupHashColumn());
        LocalDateTime localTimeNow = LocalDateTime.now(this.utcZone);
        Assertions.assertEquals(LocalDateTime.of(LocalDate.of(localTimeNow.getYear(), localTimeNow.getMonth(), 1), LocalTime.MIDNIGHT), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(0L, results.getDataGroupHashColumn().get(0));
        Assertions.assertEquals("no grouping", results.getDataGroupNameColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenActualValueAndTimePeriodGradientDayAndDimension1_thenCreatesDatasetWithTimePeriodTodayAndDataStreamHashNot0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
		this.table.addColumns(DateTimeColumn.create("time_period", LocalDateTime.now(this.utcZone).minus(Period.ofDays(2)).truncatedTo(ChronoUnit.DAYS)));
		this.table.addColumns(TextColumn.create("grouping_level_1", "US"));
        this.tableSpec.getDefaultDataGroupingConfiguration()
                .setLevel1(DataStreamLevelSpecObjectMother.createColumnMapping("length_string"));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));

        SensorReadoutsNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);

        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getTimePeriodUtcColumn());
        Assertions.assertNotNull(results.getDataGroupHashColumn());
        LocalDateTime localTimeNow = LocalDateTime.now(this.utcZone).minus(Period.ofDays(2));
        Assertions.assertEquals(localTimeNow.truncatedTo(ChronoUnit.DAYS), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(3135242802568536781L, results.getDataGroupHashColumn().get(0));
        Assertions.assertEquals("US", results.getDataGroupNameColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenActualValueAndTimePeriodGradientDayAndDimension2_thenCreatesDatasetWithTimePeriodTodayAndDataStreamHashNot0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
		this.table.addColumns(DateTimeColumn.create("time_period", LocalDateTime.now(this.utcZone).minus(Period.ofDays(2)).truncatedTo(ChronoUnit.DAYS)));
		this.table.addColumns(TextColumn.create("grouping_level_2", "US"));
        this.tableSpec.getDefaultDataGroupingConfiguration()
                .setLevel2(DataStreamLevelSpecObjectMother.createColumnMapping("length_string"));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));

        SensorReadoutsNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, this.sensorExecutionRunParameters);

        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getTimePeriodUtcColumn());
        Assertions.assertNotNull(results.getDataGroupHashColumn());
        LocalDateTime localTimeNow = LocalDateTime.now(this.utcZone).minus(Period.ofDays(2));
        Assertions.assertEquals(localTimeNow.truncatedTo(ChronoUnit.DAYS), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(3135242802568536780L, results.getDataGroupHashColumn().get(0));
        Assertions.assertEquals(" / US", results.getDataGroupNameColumn().get(0));
    }

    // TODO: write more tests, for data streams, different data types (cast required), time period granularity trimming, etc...
}
