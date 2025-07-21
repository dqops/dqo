/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.checkresults.snapshot;

import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import com.dqops.data.checkresults.factory.CheckResultsTableFactoryImpl;
import com.dqops.data.storage.DummyParquetPartitionStorageService;

/**
 * Object mother for {@link CheckResultsSnapshotFactory}
 */
public class RuleResultsSnapshotFactoryObjectMother {
    /**
     * Creates a rule result storage service that returns a dummy storage service.
     * It will behave like there are no historic rule results that are saved will be discarded.
     * @return Rule result storage service.
     */
    public static CheckResultsSnapshotFactory createDummyRuleResultsStorageService() {
        return new CheckResultsSnapshotFactoryImpl(new DummyParquetPartitionStorageService(), new CheckResultsTableFactoryImpl(new SensorReadoutsTableFactoryImpl()));
    }
}
