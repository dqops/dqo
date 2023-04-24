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
package ai.dqo.data.incidents.snapshot;

import ai.dqo.data.incidents.factory.IncidentsTableFactoryImpl;
import ai.dqo.data.storage.DummyParquetPartitionStorageService;

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
