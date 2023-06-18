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

package ai.dqo.execution.sensors.grouping;

import ai.dqo.execution.sensors.SensorPrepareResult;

import java.util.ArrayList;
import java.util.List;

/**
 * A group of similar sensors that are grouped together. Their SQLs will be merged together in order to execute ony one query that can analyze multiple metrics from a table,
 * or even from multiple columns.
 */
public class PreparedSensorsGroup {
    private List<SensorPrepareResult> preparedSensors = new ArrayList<>();
    private String mergedSql;

    /**
     * Returns a collection of prepared sensors that are part of this group.
     * @return Collection of prepared sensors.
     */
    public List<SensorPrepareResult> getPreparedSensors() {
        return preparedSensors;
    }

    /**
     * Adds a prepared sensor to the group.
     * @param sensorPrepareResult Prepared sensor result.
     */
    public void add(SensorPrepareResult sensorPrepareResult) {
        this.preparedSensors.add(sensorPrepareResult);
    }

    /**
     * Returns a merged SQL query that is combined from multiple sensors.
     * @return Merged sql.
     */
    public String getMergedSql() {
        if (this.mergedSql == null && this.preparedSensors.size() == 1) {
            return this.preparedSensors.get(0).getRenderedSensorSql(); // fallback for single sensor groups
        }
        return this.mergedSql;
    }
}
