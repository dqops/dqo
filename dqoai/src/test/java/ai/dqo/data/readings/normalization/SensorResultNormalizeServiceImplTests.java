/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.data.readings.normalization;

import ai.dqo.BaseTest;
import ai.dqo.checks.table.consistency.TableConsistencyRowCountCheckSpec;
import ai.dqo.connectors.ProviderDialectSettingsObjectMother;
import ai.dqo.connectors.ProviderType;
import ai.dqo.data.readings.factory.SensorReadingsTableFactoryImpl;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.userhome.UserHomeImpl;
import ai.dqo.metadata.userhome.UserHomeObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

@SpringBootTest
public class SensorResultNormalizeServiceImplTests extends BaseTest {
    private SensorResultNormalizeServiceImpl sut;
    private ZoneId utcZone;
    private Table table;
    private UserHomeImpl userHome;
    private TableSpec tableSpec;
    private TableConsistencyRowCountCheckSpec checkSpec;
    private SensorExecutionRunParameters sensorExecutionRunParameters;
    private SensorExecutionResult sensorExecutionResult;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
		this.sut = new SensorResultNormalizeServiceImpl();
		this.table = Table.create("results");
		userHome = UserHomeObjectMother.createBareUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        connectionWrapper.getSpec().setProviderType(ProviderType.bigquery);
		this.utcZone = connectionWrapper.getSpec().getJavaTimeZoneId();
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "tab1"));
		tableSpec = tableWrapper.getSpec();
		checkSpec = new TableConsistencyRowCountCheckSpec();
		tableSpec.getChecks().getConsistency().setRowCount(checkSpec);
		sensorExecutionRunParameters = new SensorExecutionRunParameters(connectionWrapper.getSpec(), tableSpec, null,
				checkSpec.getHierarchyId(),
                checkSpec.getTimeSeriesOverride(),
                checkSpec.getDimensionsOverride(),
                checkSpec.getSensorParameters(),
                ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.bigquery));
		sensorExecutionResult = new SensorExecutionResult(this.sensorExecutionRunParameters, this.table);
    }

    @Test
    void analyzeAndPrepareResults_whenSchemaComparedWithTableCreatedBySensorReadingFactory_thenSchemaMatches() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        SensorNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);

        Table normalizedTable = results.getTable();
        SensorReadingsTableFactoryImpl sensorReadingTableFactory = new SensorReadingsTableFactoryImpl();
        Table emptyTable = sensorReadingTableFactory.createEmptySensorReadingsTable("empty");

        Assertions.assertEquals(emptyTable.columnCount(), normalizedTable.columnCount());
        for( int i = 0; i < emptyTable.columnCount(); i++) {
            Assertions.assertEquals(emptyTable.column(i).name(), normalizedTable.column(i).name());
            Assertions.assertSame(emptyTable.column(i).type(), normalizedTable.column(i).type());

        }
    }

    @Test
    void analyzeAndPrepareResults_whenActualValueColumnMissing_thenThrowsException() {
        Assertions.assertThrows(SensorResultNormalizeException.class, () -> {
			this.sut.normalizeResults(this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
        });
    }

    @Test
    void analyzeAndPrepareResults_whenOnlyActualValueColumnPresentAndGradientDay_thenCreatesDatasetWithTimePeriodTodayAndDimensionId0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        SensorNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getDimensionIdColumn());
        Assertions.assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(0L, results.getDimensionIdColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenOnlyActualValueColumnPresentAndGradientWeek_thenCreatesDatasetWithTimePeriodTodayAndDimensionId0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        SensorNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, TimeSeriesGradient.WEEK, this.sensorExecutionRunParameters);
        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getDimensionIdColumn());
        TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
        Assertions.assertEquals(LocalDateTime.of(LocalDateTime.now(this.utcZone).toLocalDate().with(fieldUS, 1), LocalTime.MIDNIGHT), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(0L, results.getDimensionIdColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenOnlyActualValueColumnPresentAndGradientMonth_thenCreatesDatasetWithTimePeriodTodayAndDimensionId0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        SensorNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, TimeSeriesGradient.MONTH, this.sensorExecutionRunParameters);
        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getDimensionIdColumn());
        LocalDateTime localTimeNow = LocalDateTime.now(this.utcZone);
        Assertions.assertEquals(LocalDateTime.of(LocalDate.of(localTimeNow.getYear(), localTimeNow.getMonth(), 1), LocalTime.MIDNIGHT), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(0L, results.getDimensionIdColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenActualValueAndTimePeriodGradientDay_thenCreatesDatasetWithTimePeriodTodayAndDimensionId0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
		this.table.addColumns(DateTimeColumn.create("time_period", LocalDateTime.now(this.utcZone).minus(Period.ofDays(2)).truncatedTo(ChronoUnit.DAYS)));
        SensorNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getDimensionIdColumn());
        LocalDateTime localTimeNow = LocalDateTime.now(this.utcZone).minus(Period.ofDays(2));
        Assertions.assertEquals(localTimeNow.truncatedTo(ChronoUnit.DAYS), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(0L, results.getDimensionIdColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenActualValueAndTimePeriodGradientMonthButResultIsAtDayScale_thenCreatesDatasetWithTimePeriodTodayAndDimensionId0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
		this.table.addColumns(DateTimeColumn.create("time_period", LocalDateTime.now(this.utcZone).truncatedTo(ChronoUnit.DAYS)));
        SensorNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, TimeSeriesGradient.MONTH, this.sensorExecutionRunParameters);
        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getDimensionIdColumn());
        LocalDateTime localTimeNow = LocalDateTime.now(this.utcZone);
        Assertions.assertEquals(LocalDateTime.of(LocalDate.of(localTimeNow.getYear(), localTimeNow.getMonth(), 1), LocalTime.MIDNIGHT), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(0L, results.getDimensionIdColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenActualValueAndTimePeriodGradientDayAndDimension1_thenCreatesDatasetWithTimePeriodTodayAndDimensionIdNot0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
		this.table.addColumns(DateTimeColumn.create("time_period", LocalDateTime.now(this.utcZone).minus(Period.ofDays(2)).truncatedTo(ChronoUnit.DAYS)));
		this.table.addColumns(StringColumn.create("dimension_1", "US"));
        SensorNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getDimensionIdColumn());
        LocalDateTime localTimeNow = LocalDateTime.now(this.utcZone).minus(Period.ofDays(2));
        Assertions.assertEquals(localTimeNow.truncatedTo(ChronoUnit.DAYS), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(6115115649832173011L, results.getDimensionIdColumn().get(0));
    }

    @Test
    void analyzeAndPrepareResults_whenActualValueAndTimePeriodGradientDayAndDimension2_thenCreatesDatasetWithTimePeriodTodayAndDimensionIdNot0() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
		this.table.addColumns(DateTimeColumn.create("time_period", LocalDateTime.now(this.utcZone).minus(Period.ofDays(2)).truncatedTo(ChronoUnit.DAYS)));
		this.table.addColumns(StringColumn.create("dimension_2", "US"));
        SensorNormalizedResult results = this.sut.normalizeResults(this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
        Assertions.assertNotNull(results.getTable());
        Assertions.assertEquals(1, results.getTable().rowCount());
        Assertions.assertEquals(12.5, results.getActualValueColumn().get(0));
        Assertions.assertNotNull(results.getActualValueColumn());
        Assertions.assertNotNull(results.getTimePeriodColumn());
        Assertions.assertNotNull(results.getDimensionIdColumn());
        LocalDateTime localTimeNow = LocalDateTime.now(this.utcZone).minus(Period.ofDays(2));
        Assertions.assertEquals(localTimeNow.truncatedTo(ChronoUnit.DAYS), results.getTimePeriodColumn().get(0));
        Assertions.assertEquals(4298143061576664681L, results.getDimensionIdColumn().get(0));
    }

    // TODO: write more tests, for dimensions, different data types (cast required), time period granularity trimming, etc...
}
