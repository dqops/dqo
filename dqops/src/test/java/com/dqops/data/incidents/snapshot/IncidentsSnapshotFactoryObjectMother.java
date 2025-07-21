/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.incidents.snapshot;

import com.dqops.data.incidents.factory.IncidentsTableFactoryImpl;
import com.dqops.data.storage.DummyParquetPartitionStorageService;

/**
 * Object mother for {@link IncidentsSnapshotFactory}
 */
public class IncidentsSnapshotFactoryObjectMother {
    /**
     * Creates an incidents storage service that returns a dummy storage service.
     * It will behave like there are no historic incidents and incidents that are saved will be discarded.
     * @return Incidents storage service.
     */
    public static IncidentsSnapshotFactory createDummyIncidentsStorageService() {
        return new IncidentsSnapshotFactoryImpl(new DummyParquetPartitionStorageService(), new IncidentsTableFactoryImpl());
    }
}
