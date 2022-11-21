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

import ai.dqo.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import ai.dqo.data.readouts.filestorage.DummySensorReadoutsFileStorageService;

/**
 * Object mother for {@link SensorReadoutsSnapshotFactory}
 */
public class SensorReadoutsSnapshotFactoryObjectMother {
    /**
     * Creates a sensor readouts storage service that returns a dummy storage service.
     * It will behave like there are no historic readouts and readouts that are saved will be discarded.
     * @return Sensor readouts storage service.
     */
    public static SensorReadoutsSnapshotFactory createDummySensorReadoutStorageService() {
        return new SensorReadoutsSnapshotFactoryImpl(new DummySensorReadoutsFileStorageService(), new SensorReadoutsTableFactoryImpl());
    }
}
