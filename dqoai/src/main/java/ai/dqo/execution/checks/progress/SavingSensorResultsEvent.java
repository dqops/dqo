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
package ai.dqo.execution.checks.progress;

import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshot;
import ai.dqo.metadata.sources.TableSpec;

/**
 * Progress event raised before the sensor results are saved for later use (they may be used later for time series calculation).
 */
public class SavingSensorResultsEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final SensorReadoutsSnapshot sensorReadoutsSnapshot;

    /**
     * Creates a progress event.
     *
     * @param tableSpec                       Target table.
     * @param sensorReadoutsSnapshot          Sensor results for the given table.
     */
    public SavingSensorResultsEvent(TableSpec tableSpec, SensorReadoutsSnapshot sensorReadoutsSnapshot) {
        this.tableSpec = tableSpec;
        this.sensorReadoutsSnapshot = sensorReadoutsSnapshot;
    }

    /**
     * Target table specification.
     *
     * @return Target table.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Sensor results for the given table.
     *
     * @return Sensor results for the given table.
     */
    public SensorReadoutsSnapshot getSensorReadouts() {
        return sensorReadoutsSnapshot;
    }
}
