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
package ai.dqo.data.readouts.filestorage;

import ai.dqo.BaseTest;
import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.configuration.DqoConfigurationPropertiesObjectMother;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.core.locks.UserHomeLockManagerObjectMother;
import ai.dqo.data.ChangeDeltaMode;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.local.LocalDqoUserHomePathProviderObjectMother;
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
public class SensorReadoutsFileStorageServiceImplTests extends BaseTest {
    private SensorReadoutsFileStorageServiceImpl sut;
    private DqoConfigurationProperties dqoConfigurationProperties;

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
        this.sut = new SensorReadoutsFileStorageServiceImpl(localUserHomeProviderStub, newLockManager);
    }

    @Test
    void getDeltaMode_whenCalled_thenReturnsReplaceAll() {
        ChangeDeltaMode deltaMode = this.sut.getDeltaMode();
        Assertions.assertEquals(ChangeDeltaMode.REPLACE_ALL, deltaMode);
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderWithoutEncoding_thenReturnsFolderName() {
        String partitionPath = this.sut.makeHivePartitionPath("connection",
                new PhysicalTableName("sch", "tab"),
                LocalDate.of(2022, 10, 1));

        Assertions.assertEquals("c=connection/t=sch.tab/m=2022-10-01/", partitionPath);
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderConnectionEncoded_thenReturnsFolderName() {
        String partitionPath = this.sut.makeHivePartitionPath("connection 1/%&\\",
                new PhysicalTableName("sch", "tab"),
                LocalDate.of(2022, 10, 1));

        Assertions.assertEquals("c=connection+1%2F%25%26%5C/t=sch.tab/m=2022-10-01/", partitionPath);
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderAndTableEncoded_thenReturnsFolderName() {
        String partitionPath = this.sut.makeHivePartitionPath("connection",
                new PhysicalTableName("sch", "tab 1/%&\\"),
                LocalDate.of(2022, 10, 1));

        Assertions.assertEquals("c=connection/t=sch.tab+1%2F%25%26%5C/m=2022-10-01/", partitionPath);
    }

    @Test
    void saveTableMonth_whenTableWritten_thenTableCouldBeLoaded() {
        SensorReadoutsNormalizedResult normalizedResults = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResults.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResults.getActualValueColumn().set(row1.getRowNumber(), 20.5);
        normalizedResults.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 3, 10, 14, 40, 55));

        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 3, 1);

		this.sut.saveTableMonth(sourceTable, "connection", tableName, month);

        Table loadedTable = this.sut.loadForTableAndMonth("connection", tableName, month);
        Assertions.assertEquals(sourceTable.rowCount(), loadedTable.rowCount());
        Assertions.assertEquals(sourceTable.columnCount(), loadedTable.columnCount());
        Assertions.assertEquals(20.5, loadedTable.column(normalizedResults.getActualValueColumn().name()).get(0));
    }

    @Test
    void loadForTableAndMonth_whenFileNotPresent_thenReturnsNull() {
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 3, 1);

        Table loadedTable = this.sut.loadForTableAndMonth("connection", tableName, month);

        Assertions.assertNull(loadedTable);
    }

    @Test
    void loadForTableAndTimeRange_whenNoDataPresentForGivenRange_thenReturnsNullTable() {
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate start = LocalDate.of(2022, 1, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);

        Table table = this.sut.loadForTableAndMonthsRange("connection", tableName, start, end);

        Assertions.assertNull(table);
    }

    @Test
    void saveTableInMonthsRange_whenTableHasDataForThreeMonths_thenSavesFilesThatCouldBeLoadedInRange() {
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
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

		this.sut.saveTableInMonthsRange(sourceTable, "connection", tableName, start, end);

        Table table = this.sut.loadForTableAndMonthsRange("connection", tableName, start, end);
        Assertions.assertNotNull(table);
        Assertions.assertEquals(3, table.rowCount());
        Assertions.assertEquals(10.5, table.column(normalizedResults.getActualValueColumn().name()).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 1, 10, 14, 10, 55),
                table.column(normalizedResults.getTimePeriodColumn().name()).get(0));
        Assertions.assertEquals(20.5, table.column(normalizedResults.getActualValueColumn().name()).get(1));
        Assertions.assertEquals(LocalDateTime.of(2022, 2, 10, 14, 20, 55),
                table.column(normalizedResults.getTimePeriodColumn().name()).get(1));
        Assertions.assertEquals(30.5, table.column(normalizedResults.getActualValueColumn().name()).get(2));
        Assertions.assertEquals(LocalDateTime.of(2022, 3, 10, 14, 30, 55),
                table.column(normalizedResults.getTimePeriodColumn().name()).get(2));
    }
}
