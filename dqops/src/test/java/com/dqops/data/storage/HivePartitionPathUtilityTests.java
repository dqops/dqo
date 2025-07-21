/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.storage;

import com.dqops.BaseTest;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.metadata.sources.PhysicalTableName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class HivePartitionPathUtilityTests extends BaseTest {
    private FileStorageSettings sensorReadoutsStorageSettings;

    @BeforeEach
    void setUp() {
        this.sensorReadoutsStorageSettings = SensorReadoutsSnapshot.createSensorReadoutsStorageSettings();
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderWithoutEncoding_thenReturnsFolderName() {
        ParquetPartitionId partitionId = new ParquetPartitionId(
                UserDomainIdentity.ROOT_DATA_DOMAIN,
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                new PhysicalTableName("sch", "tab"),
                LocalDate.of(2022, 10, 1));
        String partitionPath = HivePartitionPathUtility.makeHivePartitionPath(partitionId);

        Assertions.assertEquals("c=connection/t=sch.tab/m=2022-10-01/", partitionPath);
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderConnectionEncoded_thenReturnsFolderName() {
        ParquetPartitionId partitionId = new ParquetPartitionId(
                UserDomainIdentity.ROOT_DATA_DOMAIN,
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection 1/%&\\",
                new PhysicalTableName("sch", "tab"),
                LocalDate.of(2022, 10, 1));
        String partitionPath = HivePartitionPathUtility.makeHivePartitionPath(partitionId);

        Assertions.assertEquals("c=connection+1%2F%25%26%5C/t=sch.tab/m=2022-10-01/", partitionPath);
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderAndTableEncoded_thenReturnsFolderName() {
        ParquetPartitionId partitionId = new ParquetPartitionId(
                UserDomainIdentity.ROOT_DATA_DOMAIN,
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                new PhysicalTableName("sch", "tab 1/%&\\"),
                LocalDate.of(2022, 10, 1));
        String partitionPath = HivePartitionPathUtility.makeHivePartitionPath(partitionId);
        Assertions.assertEquals("c=connection/t=sch.tab+1%2F%25%26%5C/m=2022-10-01/", partitionPath);
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderWithoutEncodingAndNestedDataDomain_thenReturnsFolderNameWithoutDomain() {
        ParquetPartitionId partitionId = new ParquetPartitionId(
                "sales",
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                new PhysicalTableName("sch", "tab"),
                LocalDate.of(2022, 10, 1));
        String partitionPath = HivePartitionPathUtility.makeHivePartitionPath(partitionId);

        Assertions.assertEquals("c=connection/t=sch.tab/m=2022-10-01/", partitionPath);
    }
}
