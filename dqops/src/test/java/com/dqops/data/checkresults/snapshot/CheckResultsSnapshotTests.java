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
package com.dqops.data.checkresults.snapshot;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.filesystem.cache.LocalFileSystemCache;
import com.dqops.core.filesystem.cache.LocalFileSystemCacheObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.core.synchronization.status.SynchronizationStatusTrackerStub;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.locks.UserHomeLockManagerObjectMother;
import com.dqops.data.local.LocalDqoUserHomePathProvider;
import com.dqops.data.local.LocalDqoUserHomePathProviderObjectMother;
import com.dqops.data.readouts.factory.SensorReadoutTableFactoryObjectMother;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.normalization.SensorNormalizedResultObjectMother;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.data.storage.*;
import com.dqops.data.storage.parquet.HadoopConfigurationProviderObjectMother;
import com.dqops.metadata.sources.PhysicalTableName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@SpringBootTest
public class CheckResultsSnapshotTests extends BaseTest {
    private CheckResultsSnapshot sut;
    private DqoConfigurationProperties dqoConfigurationProperties;
    private ParquetPartitionStorageService parquetStorageService;
    private PhysicalTableName tableName;
    private DqoUserConfigurationProperties dqoUserConfigurationProperties;
    private UserDomainIdentity userDomainIdentity;

    @BeforeEach
    void setUp() {
		dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        dqoUserConfigurationProperties = DqoUserConfigurationPropertiesObjectMother.createConfigurationWithTemporaryUserHome(true);
        userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        LocalDqoUserHomePathProvider localUserHomeProviderStub = LocalDqoUserHomePathProviderObjectMother.createLocalUserHomeProviderStub(dqoUserConfigurationProperties);
        UserHomeLockManager newLockManager = UserHomeLockManagerObjectMother.createNewLockManager();
        LocalFileSystemCache fileSystemCache = LocalFileSystemCacheObjectMother.createNewCache();
        // TODO: Add stub / virtual filesystem for localUserHomeFileStorageService

        ParquetPartitionMetadataService parquetPartitionMetadataService = new ParquetPartitionMetadataServiceImpl(
                newLockManager, null);
        this.parquetStorageService = new ParquetPartitionStorageServiceImpl(
                parquetPartitionMetadataService,
                localUserHomeProviderStub,
                newLockManager,
                HadoopConfigurationProviderObjectMother.getDefault(),
                null,
                new SynchronizationStatusTrackerStub(),
                fileSystemCache);
		tableName = new PhysicalTableName("sch2", "tab2");
        Table newRows = SensorReadoutTableFactoryObjectMother.createEmptyNormalizedTable("new_rows");
		this.sut = new CheckResultsSnapshot(this.userDomainIdentity, "conn", tableName, this.parquetStorageService, newRows);
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

        CheckResultsSnapshot tempSut = new CheckResultsSnapshot(this.userDomainIdentity, "conn", tableName, this.parquetStorageService, sourceTable);
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

        Assertions.assertEquals(20.5, firstMonth.getData().column(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 2, 10, 14, 20, 55),
                firstMonth.getData().column(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).get(0));

        LoadedMonthlyPartition secondMonth = this.sut.getMonthPartition(end, false);
        Assertions.assertNotNull(secondMonth);
        Assertions.assertNotNull(secondMonth.getData());

        Assertions.assertEquals(30.5, secondMonth.getData().column(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 3, 10, 14, 30, 55),
                secondMonth.getData().column(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).get(0));
    }

    @Test
    void ensureMonthsAreLoaded_whenCalledSecondTimeWithRangeAlreadyLoaded_thenNoMoreLoads() {
		saveThreeMonthsData();
        LocalDate start = LocalDate.of(2022, 2, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);
		this.sut.ensureMonthsAreLoaded(start, end);

        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = this.sut.getLoadedMonthlyPartitions();
        Assertions.assertNotNull(loadedMonthlyPartitions);
        Assertions.assertEquals(2, loadedMonthlyPartitions.size());

		this.sut.ensureMonthsAreLoaded(start, end);
        Assertions.assertEquals(2, loadedMonthlyPartitions.size());
    }

    @Test
    void ensureMonthsAreLoaded_whenCalledAgainToLoadPreviousMonth_thenLoadsThoseMonths() {
		saveThreeMonthsData();
        LocalDate start = LocalDate.of(2022, 2, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);

		this.sut.ensureMonthsAreLoaded(start, end);

        LocalDate dateMonth1 = LocalDate.of(2022, 1, 1);
        this.sut.ensureMonthsAreLoaded(dateMonth1, end);

        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = this.sut.getLoadedMonthlyPartitions();
        Assertions.assertNotNull(loadedMonthlyPartitions);
        Assertions.assertEquals(3, loadedMonthlyPartitions.size());

        LoadedMonthlyPartition month2 = this.sut.getMonthPartition(start, false);
        Assertions.assertEquals(20.5, month2.getData().column(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 2, 10, 14, 20, 55),
                month2.getData().column(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).get(0));

        LoadedMonthlyPartition month3 = this.sut.getMonthPartition(end, false);
        Assertions.assertEquals(30.5, month3.getData().column(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 3, 10, 14, 30, 55),
                month3.getData().column(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).get(0));

        LoadedMonthlyPartition month1 = this.sut.getMonthPartition(dateMonth1, false);
        Assertions.assertEquals(10.5, month1.getData().column(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 1, 10, 14, 10, 55),
                month1.getData().column(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).get(0));
    }

    @Test
    void ensureMonthsAreLoaded_whenCalledAgainToLoadFollowingMonth_thenLoadsThoseMonths() {
		saveThreeMonthsData();
        LocalDate start = LocalDate.of(2022, 1, 1);
        LocalDate end = LocalDate.of(2022, 2, 1);

		this.sut.ensureMonthsAreLoaded(start, end);

        LocalDate dateMonth3 = LocalDate.of(2022, 3, 1);
        this.sut.ensureMonthsAreLoaded(end, dateMonth3);

        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = this.sut.getLoadedMonthlyPartitions();
        Assertions.assertNotNull(loadedMonthlyPartitions);
        Assertions.assertEquals(3, loadedMonthlyPartitions.size());

        LoadedMonthlyPartition month1 = this.sut.getMonthPartition(start, false);
        Assertions.assertEquals(10.5, month1.getData().column(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 1, 10, 14, 10, 55),
                month1.getData().column(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).get(0));

        LoadedMonthlyPartition month2 = this.sut.getMonthPartition(end, false);
        Assertions.assertEquals(20.5, month2.getData().column(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 2, 10, 14, 20, 55),
                month2.getData().column(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).get(0));

        LoadedMonthlyPartition month3 = this.sut.getMonthPartition(dateMonth3, false);
        Assertions.assertEquals(30.5, month3.getData().column(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 3, 10, 14, 30, 55),
                month3.getData().column(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME).get(0));
    }
}
