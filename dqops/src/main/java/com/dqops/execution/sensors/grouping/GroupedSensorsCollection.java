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

import com.dqops.execution.sensors.SensorPrepareResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Container of all grouped sensors that are grouped because they are similar and could be merged into bigger SQLs that
 * analyze multiple statistics or operate on different columns.
 */
public class GroupedSensorsCollection {
    private final LinkedHashMap<SensorGroupingKey, PreparedSensorsGroup> sensorGroups = new LinkedHashMap<>();
    private final List<PreparedSensorsGroup> fullGroups = new ArrayList<>();
    private final int maxQueriesInGroup;

    /**
     * Creates a group configuration, given the maximum number of queries to use in a group.
     * @param maxQueriesInGroup Max queries in a group.
     */
    public GroupedSensorsCollection(int maxQueriesInGroup) {
        this.maxQueriesInGroup = maxQueriesInGroup;
    }

    /**
     * Returns a collection of prepared sensor groups that were identified and could be executed as a single, merged query.
     * @return Collection of prepared sensor groups.
     */
    public Collection<PreparedSensorsGroup> getPreparedSensorGroups() {
        if (this.fullGroups.size() == 0) {
            return this.sensorGroups.values();
        }

        ArrayList<PreparedSensorsGroup> concatenatedGroups = new ArrayList<>(this.fullGroups);
        concatenatedGroups.addAll(this.sensorGroups.values());

        return concatenatedGroups;
    }

    /**
     * Adds a prepared sensor to the container. Finds a group of similar sensors that this prepared sensor could be merged with
     * and adds it to the group or creates a new group.
     * @param sensorPrepareResult Prepared sensor result to be added to a matching group.
     */
    public void addPreparedSensor(SensorPrepareResult sensorPrepareResult) {
        if (sensorPrepareResult.isDisableMergingQueries()) {
            PreparedSensorsGroup singleSensorGroup = new PreparedSensorsGroup();
            singleSensorGroup.add(sensorPrepareResult);
            this.fullGroups.add(singleSensorGroup);
        } else {
            SensorGroupingKey sensorGroupingKey = new SensorGroupingKey(
                    sensorPrepareResult.getFragmentedSqlQuery(), sensorPrepareResult.getSensorExecutor());

            PreparedSensorsGroup preparedSensorsGroup = this.sensorGroups.get(sensorGroupingKey);
            if (preparedSensorsGroup == null) {
                preparedSensorsGroup = new PreparedSensorsGroup();
                this.sensorGroups.put(sensorGroupingKey, preparedSensorsGroup);
            }

            preparedSensorsGroup.add(sensorPrepareResult);

            if (preparedSensorsGroup.size() >= this.maxQueriesInGroup) {
                this.fullGroups.add(preparedSensorsGroup);
                this.sensorGroups.remove(sensorGroupingKey);
            }
        }
    }

    /**
     * Adds all prepared sensors to the container, finding similar sensors that could be merged.
     * @param preparedSensors Collection of prepared sensor results.
     */
    public void addAllPreparedSensors(Collection<SensorPrepareResult> preparedSensors) {
        for (SensorPrepareResult sensorPrepareResult : preparedSensors) {
            this.addPreparedSensor(sensorPrepareResult);
        }

        for (PreparedSensorsGroup preparedSensorsGroup : this.getPreparedSensorGroups()) {
            preparedSensorsGroup.mergeQueries();
        }
    }
}
