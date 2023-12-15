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
package com.dqops.data.incidents.snapshot;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.filesystem.cache.LocalFileSystemCache;
import com.dqops.core.filesystem.cache.LocalFileSystemCacheObjectMother;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.locks.UserHomeLockManagerObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.core.synchronization.status.SynchronizationStatusTrackerStub;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.incidents.factory.IncidentsTableFactoryImpl;
import com.dqops.data.incidents.factory.IncidentsTableFactoryObjectMother;
import com.dqops.data.local.LocalDqoUserHomePathProvider;
import com.dqops.data.local.LocalDqoUserHomePathProviderObjectMother;
import com.dqops.data.readouts.factory.SensorReadoutTableFactoryObjectMother;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.normalization.SensorNormalizedResultObjectMother;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.data.storage.LoadedMonthlyPartition;
import com.dqops.data.storage.ParquetPartitionMetadataService;
import com.dqops.data.storage.ParquetPartitionMetadataServiceImpl;
import com.dqops.data.storage.ParquetPartitionStorageServiceImpl;
import com.dqops.data.storage.parquet.HadoopConfigurationProviderObjectMother;
import com.dqops.metadata.sources.PhysicalTableName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;

@SpringBootTest
public class IncidentsSnapshotTests extends BaseTest {
    private IncidentsSnapshot sut;
    private DqoConfigurationProperties dqoConfigurationProperties;
    private ParquetPartitionStorageServiceImpl parquetStorageService;
    private DqoUserConfigurationProperties dqoUserConfigurationProperties;
    private IncidentsTableFactoryImpl incidentsTableFactory;
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
        Table newRows = IncidentsTableFactoryObjectMother.createEmptyNormalizedTable("new_rows");
		this.sut = new IncidentsSnapshot(userDomainIdentity, "conn", this.parquetStorageService, newRows);
        this.incidentsTableFactory = new IncidentsTableFactoryImpl();
    }

    void saveThreeMonthsData() {
        LocalDate start = LocalDate.of(2022, 1, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);

        Table sourceTable = this.incidentsTableFactory.createEmptyIncidentsTable("tab");
        Row row1 = sourceTable.appendRow();
        sourceTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row1.getRowNumber(), 10);
        sourceTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).
                set(row1.getRowNumber(), LocalDateTime.of(2022, 1, 10, 14, 10, 55).toInstant(ZoneOffset.UTC));

        Row row2 = sourceTable.appendRow();
        sourceTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row2.getRowNumber(), 20);
        sourceTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).
                set(row2.getRowNumber(), LocalDateTime.of(2022, 2, 10, 14, 20, 55).toInstant(ZoneOffset.UTC));

        Row row3 = sourceTable.appendRow();
        sourceTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row3.getRowNumber(), 30);
        sourceTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).
                set(row3.getRowNumber(), LocalDateTime.of(2022, 3, 10, 14, 30, 55).toInstant(ZoneOffset.UTC));

        IncidentsSnapshot tempSut = new IncidentsSnapshot(this.userDomainIdentity, "conn", this.parquetStorageService, sourceTable);
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

        Assertions.assertIterableEquals(new ArrayList<Integer>(){{add(20); add(30);}},
                table.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).sorted(Comparator.comparingDouble(a -> a)));
        Assertions.assertIterableEquals(
                new ArrayList<Instant>(){{
                    add(LocalDateTime.of(2022, 2, 10, 14, 20, 55).toInstant(ZoneOffset.UTC));
                    add(LocalDateTime.of(2022, 3, 10, 14, 30, 55).toInstant(ZoneOffset.UTC));
                }},
                table.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).sorted(Comparator.comparing(dt -> dt))
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

        Assertions.assertIterableEquals(new ArrayList<Integer>(){{add(10); add(20); add(30);}},
                table.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).sorted(Comparator.comparingDouble(a -> a)));
        Assertions.assertIterableEquals(
                new ArrayList<Instant>(){{
                    add(LocalDateTime.of(2022, 1, 10, 14, 10, 55).toInstant(ZoneOffset.UTC));
                    add(LocalDateTime.of(2022, 2, 10, 14, 20, 55).toInstant(ZoneOffset.UTC));
                    add(LocalDateTime.of(2022, 3, 10, 14, 30, 55).toInstant(ZoneOffset.UTC));
                }},
                table.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).sorted(Comparator.comparing(dt -> dt))
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

        Assertions.assertIterableEquals(new ArrayList<Integer>(){{add(10); add(20); add(30);}},
                table.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).sorted(Comparator.comparingDouble(a -> a)));
        Assertions.assertIterableEquals(
                new ArrayList<Instant>(){{
                    add(LocalDateTime.of(2022, 1, 10, 14, 10, 55).toInstant(ZoneOffset.UTC));
                    add(LocalDateTime.of(2022, 2, 10, 14, 20, 55).toInstant(ZoneOffset.UTC));
                    add(LocalDateTime.of(2022, 3, 10, 14, 30, 55).toInstant(ZoneOffset.UTC));
                }},
                table.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).sorted(Comparator.comparing(dt -> dt))
        );
    }
}
