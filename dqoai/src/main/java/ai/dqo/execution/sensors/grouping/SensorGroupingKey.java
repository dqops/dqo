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

import ai.dqo.execution.sensors.runners.AbstractSensorRunner;
import ai.dqo.execution.sqltemplates.grouping.FragmentedSqlQuery;

import java.util.Objects;

/**
 * Dictionary key used to match sensors that share the same runner and the same
 */
public class SensorGroupingKey {
    private FragmentedSqlQuery fragmentedSqlQuery;
    private AbstractSensorRunner sensorRunner;

    /**
     * Creates a sensor grouping key.
     * @param fragmentedSqlQuery Parsed sql query that is divided into SQL fragments, static fragments could be compared as a part of this key.
     * @param sensorRunner Sensor runner instance that will be used to execute this sensor.
     */
    public SensorGroupingKey(FragmentedSqlQuery fragmentedSqlQuery, AbstractSensorRunner sensorRunner) {
        this.fragmentedSqlQuery = fragmentedSqlQuery;
        this.sensorRunner = sensorRunner;
    }

    /**
     * SQL query fragments that are used for comparison.
     * @return SQL query fragments.
     */
    public FragmentedSqlQuery getFragmentedSqlQuery() {
        return fragmentedSqlQuery;
    }

    /**
     * Returns the sensor runner instance that will run this sensor.
     * @return Sensor runner instance.
     */
    public AbstractSensorRunner getSensorRunner() {
        return sensorRunner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorGroupingKey that = (SensorGroupingKey) o;

        if (!Objects.equals(fragmentedSqlQuery, that.fragmentedSqlQuery))
            return false;
        return Objects.equals(sensorRunner, that.sensorRunner);
    }

    @Override
    public int hashCode() {
        int result = fragmentedSqlQuery != null ? fragmentedSqlQuery.hashCode() : 0;
        result = 31 * result + (sensorRunner != null ? sensorRunner.hashCode() : 0);
        return result;
    }
}
