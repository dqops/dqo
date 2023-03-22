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
package ai.dqo.data.storage;

import ai.dqo.BaseTest;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshot;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class HivePartitionPathUtilityImplTests extends BaseTest {
    private HivePartitionPathUtilityImpl sut;
    private FileStorageSettings sensorReadoutsStorageSettings;

    @BeforeEach
    void setUp() {
        this.sut = new HivePartitionPathUtilityImpl();
        this.sensorReadoutsStorageSettings = SensorReadoutsSnapshot.createSensorReadoutsStorageSettings();
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderWithoutEncoding_thenReturnsFolderName() {
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                new PhysicalTableName("sch", "tab"),
                LocalDate.of(2022, 10, 1));
        String partitionPath = this.sut.makeHivePartitionPath(partitionId);

        Assertions.assertEquals("c=connection/t=sch.tab/m=2022-10-01/", partitionPath);
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderConnectionEncoded_thenReturnsFolderName() {
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection 1/%&\\",
                new PhysicalTableName("sch", "tab"),
                LocalDate.of(2022, 10, 1));
        String partitionPath = this.sut.makeHivePartitionPath(partitionId);

        Assertions.assertEquals("c=connection+1%2F%25%26%5C/t=sch.tab/m=2022-10-01/", partitionPath);
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderAndTableEncoded_thenReturnsFolderName() {
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                new PhysicalTableName("sch", "tab 1/%&\\"),
                LocalDate.of(2022, 10, 1));
        String partitionPath = this.sut.makeHivePartitionPath(partitionId);
        Assertions.assertEquals("c=connection/t=sch.tab+1%2F%25%26%5C/m=2022-10-01/", partitionPath);
    }
}
