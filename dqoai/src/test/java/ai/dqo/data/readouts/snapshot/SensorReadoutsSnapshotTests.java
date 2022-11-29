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
package ai.dqo.data.readouts.snapshot;

import ai.dqo.BaseTest;
import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.configuration.DqoConfigurationPropertiesObjectMother;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.core.locks.UserHomeLockManagerObjectMother;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.local.LocalDqoUserHomePathProviderObjectMother;
import ai.dqo.data.readouts.factory.SensorReadoutTableFactoryObjectMother;
import ai.dqo.data.readouts.filestorage.SensorReadoutsFileStorageServiceImpl;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.data.readouts.normalization.SensorNormalizedResultObjectMother;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class SensorReadoutsSnapshotTests extends BaseTest {
    private SensorReadoutsSnapshot sut;
    private DqoConfigurationProperties dqoConfigurationProperties;
    private SensorReadoutsFileStorageServiceImpl sensorReadoutsFileStorageService;
    private PhysicalTableName tableName;

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
		dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.createConfigurationWithTemporaryUserHome(true);
        LocalDqoUserHomePathProvider localUserHomeProviderStub = LocalDqoUserHomePathProviderObjectMother.createLocalUserHomeProviderStub(dqoConfigurationProperties);
        UserHomeLockManager newLockManager = UserHomeLockManagerObjectMother.createNewLockManager();
        sensorReadoutsFileStorageService = new SensorReadoutsFileStorageServiceImpl(localUserHomeProviderStub, newLockManager);
		tableName = new PhysicalTableName("sch2", "tab2");
        Table newRows = SensorReadoutTableFactoryObjectMother.createEmptyNormalizedTable("new_rows");
		this.sut = new SensorReadoutsSnapshot("conn", tableName, this.sensorReadoutsFileStorageService, newRows);
    }

    void saveThreeMonthsData() {
        LocalDate start = LocalDate.of(2022, 1, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);

        SensorReadoutsNormalizedResult normalizedResults = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResults.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResults.getActualValueColumn().set(row1.getRowNumber(), 10.5);
        normalizedResults.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 1, 10, 14, 10, 55));

        Row row2 = sourceTable.appendRow();
        normalizedResults.getActualValueColumn().set(row2.getRowNumber(), 20.5);
        normalizedResults.getTimePeriodColumn().set(row2.getRowNumber(), LocalDateTime.of(2022, 2, 10, 14, 20, 55));

        Row row3 = sourceTable.appendRow();
        normalizedResults.getActualValueColumn().set(row3.getRowNumber(), 30.5);
        normalizedResults.getTimePeriodColumn().set(row3.getRowNumber(), LocalDateTime.of(2022, 3, 10, 14, 30, 55));

		this.sensorReadoutsFileStorageService.saveTableInMonthsRange(sourceTable, this.sut.getConnection(), tableName, start, end);
    }

    @Test
    void ensureMonthsAreLoaded_whenCalledOnceToLoadTwoMonths_thenLoadsThoseMonths() {
		saveThreeMonthsData();
        LocalDate start = LocalDate.of(2022, 2, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);

		this.sut.ensureMonthsAreLoaded(start, end);

        Table table = this.sut.getHistoricResults();
        Assertions.assertNotNull(table);
        Assertions.assertEquals(2, table.rowCount());

        Assertions.assertEquals(20.5, table.column(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 2, 10, 14, 20, 55),
                table.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME).get(0));
        Assertions.assertEquals(30.5, table.column(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME).get(1));
        Assertions.assertEquals(LocalDateTime.of(2022, 3, 10, 14, 30, 55),
                table.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME).get(1));
    }

    @Test
    void ensureMonthsAreLoaded_whenCalledSecondTimeWithRangeAlreadyLoaded_thenNoMoreLoads() {
		saveThreeMonthsData();
        LocalDate start = LocalDate.of(2022, 2, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);
		this.sut.ensureMonthsAreLoaded(start, end);

        Table table = this.sut.getHistoricResults();
        Assertions.assertNotNull(table);
        Assertions.assertEquals(2, table.rowCount());

		this.sut.ensureMonthsAreLoaded(start, end);
        Assertions.assertEquals(2, table.rowCount());
    }

    @Test
    void ensureMonthsAreLoaded_whenCalledAgainToLoadPreviousMonth_thenLoadsThoseMonths() {
		saveThreeMonthsData();
        LocalDate start = LocalDate.of(2022, 2, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);

		this.sut.ensureMonthsAreLoaded(start, end);

		this.sut.ensureMonthsAreLoaded(LocalDate.of(2022, 1, 1), end);

        Table table = this.sut.getHistoricResults();
        Assertions.assertNotNull(table);
        Assertions.assertEquals(3, table.rowCount());

        Assertions.assertEquals(20.5, table.column(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 2, 10, 14, 20, 55),
                table.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME).get(0));
        Assertions.assertEquals(30.5, table.column(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME).get(1));
        Assertions.assertEquals(LocalDateTime.of(2022, 3, 10, 14, 30, 55),
                table.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME).get(1));
        Assertions.assertEquals(10.5, table.column(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME).get(2));
        Assertions.assertEquals(LocalDateTime.of(2022, 1, 10, 14, 10, 55),
                table.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME).get(2));
    }

    @Test
    void ensureMonthsAreLoaded_whenCalledAgainToLoadFollowingMonth_thenLoadsThoseMonths() {
		saveThreeMonthsData();
        LocalDate start = LocalDate.of(2022, 1, 1);
        LocalDate end = LocalDate.of(2022, 2, 1);

		this.sut.ensureMonthsAreLoaded(start, end);

		this.sut.ensureMonthsAreLoaded(end, LocalDate.of(2022, 3, 1));

        Table table = this.sut.getHistoricResults();
        Assertions.assertNotNull(table);
        Assertions.assertEquals(3, table.rowCount());

        Assertions.assertEquals(10.5, table.column(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 1, 10, 14, 10, 55),
                table.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME).get(0));
        Assertions.assertEquals(20.5, table.column(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME).get(1));
        Assertions.assertEquals(LocalDateTime.of(2022, 2, 10, 14, 20, 55),
                table.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME).get(1));
        Assertions.assertEquals(30.5, table.column(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME).get(2));
        Assertions.assertEquals(LocalDateTime.of(2022, 3, 10, 14, 30, 55),
                table.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME).get(2));
    }

    @Test
    void hasNewReadouts_whenNoNewRows_thenReturnsFalse() {
        Assertions.assertFalse(this.sut.hasNewReadouts());
    }

    @Test
    void hasNewReadouts_whenNewRows_thenReturnsTrue() {
		this.sut.getNewResults().appendRow();
        Assertions.assertTrue(this.sut.hasNewReadouts());
    }
}
