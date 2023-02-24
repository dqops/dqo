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
import ai.dqo.core.configuration.DqoUserConfigurationProperties;
import ai.dqo.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import ai.dqo.core.filesystem.synchronization.status.SynchronizationStatusTrackerStub;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.core.locks.UserHomeLockManagerObjectMother;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.local.LocalDqoUserHomePathProviderObjectMother;
import ai.dqo.data.readouts.factory.SensorReadoutTableFactoryObjectMother;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.data.readouts.normalization.SensorNormalizedResultObjectMother;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.data.storage.LoadedMonthlyPartition;
import ai.dqo.data.storage.ParquetPartitionStorageServiceImpl;
import ai.dqo.data.storage.parquet.HadoopConfigurationProviderObjectMother;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

@SpringBootTest
public class SensorReadoutsSnapshotTests extends BaseTest {
    private SensorReadoutsSnapshot sut;
    private DqoConfigurationProperties dqoConfigurationProperties;
    private ParquetPartitionStorageServiceImpl parquetStorageService;
    private PhysicalTableName tableName;
    private DqoUserConfigurationProperties dqoUserConfigurationProperties;

    @BeforeEach
    void setUp() {
		dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        dqoUserConfigurationProperties = DqoUserConfigurationPropertiesObjectMother.createConfigurationWithTemporaryUserHome(true);
        LocalDqoUserHomePathProvider localUserHomeProviderStub = LocalDqoUserHomePathProviderObjectMother.createLocalUserHomeProviderStub(dqoUserConfigurationProperties);
        UserHomeLockManager newLockManager = UserHomeLockManagerObjectMother.createNewLockManager();
        // TODO: Add stub / virtual filesystem for localUserHomeFileStorageService
        parquetStorageService = new ParquetPartitionStorageServiceImpl(localUserHomeProviderStub, newLockManager,
                HadoopConfigurationProviderObjectMother.getDefault(), null, new SynchronizationStatusTrackerStub());
		tableName = new PhysicalTableName("sch2", "tab2");
        Table newRows = SensorReadoutTableFactoryObjectMother.createEmptyNormalizedTable("new_rows");
		this.sut = new SensorReadoutsSnapshot("conn", tableName, this.parquetStorageService, newRows);
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

        SensorReadoutsSnapshot tempSut = new SensorReadoutsSnapshot("conn", tableName, this.parquetStorageService, sourceTable);
        tempSut.save();
    }

    @Test
    void ensureMonthsAreLoaded_whenCalledOnceToLoadTwoMonths_thenLoadsThoseMonths() {
		saveThreeMonthsData();
        LocalDate start = LocalDate.of(2022, 2, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);

		this.sut.ensureMonthsAreLoaded(start, end);

        LoadedMonthlyPartition firstMonth = this.sut.getMonthPartition(start, false);
        Assertions.assertNotNull(firstMonth);
        Assertions.assertNotNull(firstMonth.getData());

        Table table = this.sut.getAllData();
        Assertions.assertNotNull(table);
        Assertions.assertEquals(2, table.rowCount());

        Assertions.assertIterableEquals(new ArrayList<Double>(){{add(20.5); add(30.5);}},
                table.doubleColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).sorted(Comparator.comparingDouble(a -> a)));
        Assertions.assertIterableEquals(
                new ArrayList<LocalDateTime>(){{
                    add(LocalDateTime.of(2022, 2, 10, 14, 20, 55));
                    add(LocalDateTime.of(2022, 3, 10, 14, 30, 55));
                }},
                table.dateTimeColumn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).sorted(Comparator.comparing(dt -> dt))
        );
    }

    @Test
    void ensureMonthsAreLoaded_whenCalledSecondTimeWithRangeAlreadyLoaded_thenNoMoreLoads() {
		saveThreeMonthsData();
        LocalDate start = LocalDate.of(2022, 2, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);
		this.sut.ensureMonthsAreLoaded(start, end);

        Table table = this.sut.getAllData();
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

        Table table = this.sut.getAllData();
        Assertions.assertNotNull(table);
        Assertions.assertEquals(3, table.rowCount());

        Assertions.assertIterableEquals(new ArrayList<Double>(){{add(10.5); add(20.5); add(30.5);}},
                table.doubleColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).sorted(Comparator.comparingDouble(a -> a)));
        Assertions.assertIterableEquals(
                new ArrayList<LocalDateTime>(){{
                    add(LocalDateTime.of(2022, 1, 10, 14, 10, 55));
                    add(LocalDateTime.of(2022, 2, 10, 14, 20, 55));
                    add(LocalDateTime.of(2022, 3, 10, 14, 30, 55));
                }},
                table.dateTimeColumn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).sorted(Comparator.comparing(dt -> dt))
        );
    }

    @Test
    void ensureMonthsAreLoaded_whenCalledAgainToLoadFollowingMonth_thenLoadsThoseMonths() {
		saveThreeMonthsData();
        LocalDate start = LocalDate.of(2022, 1, 1);
        LocalDate end = LocalDate.of(2022, 2, 1);

		this.sut.ensureMonthsAreLoaded(start, end);

		this.sut.ensureMonthsAreLoaded(end, LocalDate.of(2022, 3, 1));

        Table table = this.sut.getAllData();
        Assertions.assertNotNull(table);
        Assertions.assertEquals(3, table.rowCount());

        Assertions.assertIterableEquals(new ArrayList<Double>(){{add(10.5); add(20.5); add(30.5);}},
                table.doubleColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).sorted(Comparator.comparingDouble(a -> a)));
        Assertions.assertIterableEquals(
                new ArrayList<LocalDateTime>(){{
                    add(LocalDateTime.of(2022, 1, 10, 14, 10, 55));
                    add(LocalDateTime.of(2022, 2, 10, 14, 20, 55));
                    add(LocalDateTime.of(2022, 3, 10, 14, 30, 55));
                }},
                table.dateTimeColumn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).sorted(Comparator.comparing(dt -> dt))
        );
    }
}
