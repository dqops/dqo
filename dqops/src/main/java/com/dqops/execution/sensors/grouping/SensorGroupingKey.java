/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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

    /**
     * Creates a sensor grouping key.
     * @param fragmentedSqlQuery Parsed sql query that is divided into SQL fragments, static fragments can be compared as a part of this key.
     * @param sensorExecutor Sensor executor instance that will be used to execute this sensor.
     */
    public SensorGroupingKey(FragmentedSqlQuery fragmentedSqlQuery,
                             AbstractGroupedSensorExecutor sensorExecutor) {
        this.fragmentedSqlQuery = fragmentedSqlQuery;
        this.sensorExecutor = sensorExecutor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorGroupingKey that = (SensorGroupingKey) o;

        if (!Objects.equals(fragmentedSqlQuery, that.fragmentedSqlQuery))
            return false;
        return Objects.equals(sensorExecutor, that.sensorExecutor);
    }

    @Override
    public int hashCode() {
        int result = fragmentedSqlQuery != null ? fragmentedSqlQuery.hashCode() : 0;
        result = 31 * result + (sensorExecutor != null ? sensorExecutor.hashCode() : 0);
        return result;
    }
}
