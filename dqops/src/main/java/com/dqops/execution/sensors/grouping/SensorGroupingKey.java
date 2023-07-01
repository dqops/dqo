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

package com.dqops.execution.sensors.grouping;

import com.dqops.execution.sqltemplates.grouping.FragmentedSqlQuery;

import java.util.Objects;

/**
 * Dictionary key used to match sensors that share the same runner and the same
 */
public class SensorGroupingKey {
    private FragmentedSqlQuery fragmentedSqlQuery;
    private AbstractGroupedSensorExecutor sensorExecutor;
    private Integer queryGroupingId;

    /**
     * Creates a sensor grouping key.
     * @param fragmentedSqlQuery Parsed sql query that is divided into SQL fragments, static fragments could be compared as a part of this key.
     * @param sensorExecutor Sensor executor instance that will be used to execute this sensor.
     * @param queryGroupingId Optional object used to group queries.
     */
    public SensorGroupingKey(FragmentedSqlQuery fragmentedSqlQuery,
                             AbstractGroupedSensorExecutor sensorExecutor,
                             Integer queryGroupingId) {
        this.fragmentedSqlQuery = fragmentedSqlQuery;
        this.sensorExecutor = sensorExecutor;
        this.queryGroupingId = queryGroupingId;
    }

    /**
     * SQL query fragments that are used for comparison.
     * @return SQL query fragments.
     */
    public FragmentedSqlQuery getFragmentedSqlQuery() {
        return fragmentedSqlQuery;
    }

    /**
     * Returns the sensor executor instance that will run this sensor.
     * @return Sensor executor instance.
     */
    public AbstractGroupedSensorExecutor getSensorExecutor() {
        return sensorExecutor;
    }

    /**
     * Returns the query grouping id. It is an optional value used for assigning queries to different merge groups.
     * When the grouping id is unique, the query will not be grouped with other queries.
     * @return Query grouping id.
     */
    public Integer getQueryGroupingId() {
        return queryGroupingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorGroupingKey that = (SensorGroupingKey) o;

        if (!Objects.equals(fragmentedSqlQuery, that.fragmentedSqlQuery))
            return false;
        if (!Objects.equals(sensorExecutor, that.sensorExecutor))
            return false;
        return Objects.equals(queryGroupingId, that.queryGroupingId);
    }

    @Override
    public int hashCode() {
        int result = fragmentedSqlQuery != null ? fragmentedSqlQuery.hashCode() : 0;
        result = 31 * result + (sensorExecutor != null ? sensorExecutor.hashCode() : 0);
        result = 31 * result + (queryGroupingId != null ? queryGroupingId.hashCode() : 0);
        return result;
    }
}
