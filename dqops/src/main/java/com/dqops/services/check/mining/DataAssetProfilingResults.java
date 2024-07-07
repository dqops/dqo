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

package com.dqops.services.check.mining;

import com.dqops.data.statistics.models.StatisticsMetricModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Summary information with the recent basic statistics and profiling check results extracted for one target data asset.
 * The target data asset is a single column, or a whole table. The results for a whole table are limited to the table-level statistics and profiling checks.
 */
public class DataAssetProfilingResults {
    /**
     * Basic statistic values. The key of the dictionary is a sensor name. The list contains collected values.
     * For example, when a MIN sensor finds a result, it is just one value in the list. But a data samplings sensor returns multiple values.
     */
    private Map<String, List<StatisticsMetricModel>> basicStatistics = new LinkedHashMap<>();

    /**
     * Dictionary of check results, indexed by a check name.
     */
    private Map<String, ProfilingCheckResult> profilingCheckResults = new LinkedHashMap<>();

    /**
     * Returns a list of statistic values for a given sensor name, if a statistics collector has results for that sensor.
     * @param sensorName Sensor name.
     * @param createWhenMissing When the value of this parameter is true and the statistics are not present, creates and stores a new list that the caller can use to append results.
     * @return List of results obtained by statistics or null.
     */
    public List<StatisticsMetricModel> getBasicStatisticsForSensor(String sensorName, boolean createWhenMissing) {
        List<StatisticsMetricModel> objects = this.basicStatistics.get(sensorName);
        if (objects == null && createWhenMissing) {
            objects = new ArrayList<>();
            this.basicStatistics.put(sensorName, objects);
        }

        return objects;
    }

    /**
     * Retrieves the results of a profiling check, given its name.
     * @param checkName Check name.
     * @return Profiling check results object with the severity.
     */
    public ProfilingCheckResult getProfilingCheckByCheckName(String checkName) {
        ProfilingCheckResult profilingCheckResult = this.profilingCheckResults.get(checkName);
        return profilingCheckResult;
    }

    /**
     * Adds the results of a profiling check, given its name.
     * @param checkName Check name.
     * @param profilingCheckResult Profiling check results object with the severity.
     */
    public void addProfilingCheckByCheckName(String checkName, ProfilingCheckResult profilingCheckResult) {
        this.profilingCheckResults.put(checkName, profilingCheckResult);
    }
}
