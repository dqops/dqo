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

import com.dqops.data.checkresults.models.CheckResultsOverviewDataModel;
import com.dqops.data.statistics.models.StatisticsMetricModel;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.QualityCategoryModel;
import com.dqops.statistics.column.sampling.ColumnSamplingColumnSamplesStatisticsCollectorSpec;

import java.util.*;

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
     * A list of sample values for the data asset. Provided only for column data assets, and filled from the results of the column sampling sensor.
     */
    private List<Object> sampleValues = new ArrayList<>();

    /**
     * Verifies if any results for profiling checks are present.
     * @return True when there are any results from profiling checks, false when no results.
     */
    public boolean hasAnyProfilingChecksResults() {
        if (profilingCheckResults.isEmpty()) {
            return false;
        }

        for (ProfilingCheckResult profilingCheckResult : this.profilingCheckResults.values()) {
            if (profilingCheckResult.getActualValue() != null || profilingCheckResult.getSeverityLevel() != null) {
                return true;
            }
        }

        return false;
    }

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
    public ProfilingCheckResult getProfilingCheckByCheckName(String checkName, boolean addWhenMissing) {
        ProfilingCheckResult profilingCheckResult = this.profilingCheckResults.get(checkName);
        if (profilingCheckResult == null && addWhenMissing) {
            profilingCheckResult = new ProfilingCheckResult();
            this.profilingCheckResults.put(checkName, profilingCheckResult);
        }
        return profilingCheckResult;
    }

    /**
     * Imports check results from the check overview.
     * @param checkResultsOverviewTableLevel Check results from the check results overview to be imported and categorized by check names.
     */
    public void importProfilingChecksResults(CheckResultsOverviewDataModel[] checkResultsOverviewTableLevel) {
        for (CheckResultsOverviewDataModel checkResultsOverviewDataModel : checkResultsOverviewTableLevel) {
            String checkName = checkResultsOverviewDataModel.getCheckName();
            ProfilingCheckResult profilingCheckByCheckName = this.getProfilingCheckByCheckName(checkName, true);
            profilingCheckByCheckName.importCheckResultsOverview(checkResultsOverviewDataModel);
        }
    }

    /**
     * Imports check models from the check container.
     * @param checkContainerModel Check container model with all checks.
     */
    public void importChecksModels(CheckContainerModel checkContainerModel) {
        List<QualityCategoryModel> categoriesList = checkContainerModel.getCategories();

        for (QualityCategoryModel categoryModel : categoriesList) {
            List<CheckModel> checkModels = categoryModel.getChecks();

            for (CheckModel checkModel : checkModels) {
                if (!checkModel.isConfigured() && !checkModel.isDefaultCheck()) {
                    continue;
                }

                ProfilingCheckResult profilingCheckResult = getProfilingCheckByCheckName(checkModel.getCheckName(), true);
                profilingCheckResult.importCheckModel(checkModel);
            }
        }
    }

    /**
     * Import results from the statistics.
     * @param statistics Statistics models.
     */
    public void importStatistics(List<StatisticsMetricModel> statistics) {
        for (StatisticsMetricModel statisticsMetricModel : statistics) {
            String sensorName = statisticsMetricModel.getSensorName();
            List<StatisticsMetricModel> statisticsModelBySensor = this.basicStatistics.get(sensorName);
            if (statisticsModelBySensor == null) {
                statisticsModelBySensor = new ArrayList<>();
                this.basicStatistics.put(sensorName, statisticsModelBySensor);
            }
            statisticsModelBySensor.add(statisticsMetricModel);

            for (ProfilingCheckResult profilingCheckResult : this.profilingCheckResults.values()) {
                if (Objects.equals(sensorName, profilingCheckResult.getSensorName())) {
                    profilingCheckResult.importStatistics(statisticsMetricModel);
                }
            }

            if (Objects.equals(sensorName, ColumnSamplingColumnSamplesStatisticsCollectorSpec.SENSOR_NAME)) {
                // column sampling sensor
                Object sampleValue = statisticsMetricModel.getResult();
                this.sampleValues.add(sampleValue);
            }
        }
    }
}
