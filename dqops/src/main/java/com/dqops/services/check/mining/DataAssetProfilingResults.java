/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mining;

import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.data.checkresults.models.CheckResultsOverviewDataModel;
import com.dqops.data.statistics.models.StatisticsMetricModel;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.QualityCategoryModel;
import com.dqops.statistics.column.sampling.ColumnSamplingColumnSamplesStatisticsCollectorSpec;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Summary information with the recent basic statistics and profiling check results extracted for one target data asset.
 * The target data asset is a single column, or a whole table. The results for a whole table are limited to the table-level statistics and profiling checks.
 */
public abstract class DataAssetProfilingResults {
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
            String categoryName = checkResultsOverviewDataModel.getCheckCategory();
            if (Objects.equals(categoryName, AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME)) {
                continue;
            }

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
                ProfilingCheckResult profilingCheckResult = getProfilingCheckByCheckName(checkModel.getCheckName(), true);
                profilingCheckResult.importCheckModel(checkModel);
            }
        }
    }

    /**
     * Import results from the statistics.
     * @param statistics Statistics models.
     * @param timeZoneId Time zone id.
     */
    public void importStatistics(List<StatisticsMetricModel> statistics, ZoneId timeZoneId) {
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
        }
    }

    /**
     * Removes the results of all profiling checks that were applied by default check patterns (policies), because we don't want to reconfigure them.
     */
    public void removeChecksAppliedByPatterns() {
        LinkedHashMap<String, ProfilingCheckResult> copyOfProfilingChecks = new LinkedHashMap<>(this.profilingCheckResults);

        for (Map.Entry<String, ProfilingCheckResult> profilingCheckKeyValue : copyOfProfilingChecks.entrySet()) {
            ProfilingCheckResult profilingCheckResult = profilingCheckKeyValue.getValue();
            if (profilingCheckResult.getProfilingCheckModel() != null && profilingCheckResult.getProfilingCheckModel().isDefaultCheck()) {
                this.profilingCheckResults.remove(profilingCheckKeyValue.getKey());
            }
        }
    }
}
