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
package com.dqops.data.errors.snapshot;

import com.dqops.data.errors.factory.ErrorsTableFactoryImpl;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import com.dqops.data.storage.DummyParquetPartitionStorageService;

/**
 * Object mother for {@link ErrorsSnapshotFactory}
 */
public class ErrorsSnapshotFactoryObjectMother {
    /**
     * Creates an errors storage service that returns a dummy storage service.
     * It will behave like there are no historic errors that are saved will be discarded.
     * @return Errors storage service.
     */
    public static ErrorsSnapshotFactory createDummyErrorsStorageService() {
        return new ErrorsSnapshotFactoryImpl(new DummyParquetPartitionStorageService(), new ErrorsTableFactoryImpl(new SensorReadoutsTableFactoryImpl()));
    }
}
