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
import com.dqops.statistics.column.sampling.ColumnSamplingColumnSamplesStatisticsCollectorSpec;
import com.dqops.utils.conversion.DateTypesConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Summary information with the recent basic statistics and profiling check results extracted for one target column (a type of a data asset).
 * The results for a whole table are limited to the table-level statistics and profiling checks.
 */
public class ColumnDataAssetProfilingResults extends DataAssetProfilingResults {
    /**
     * A list of sample values for the data asset. Provided only for column data assets, and filled from the results of the column sampling sensor.
     */
    private List<ProfilingSampleValue> sampleValues = new ArrayList<>();

    /**
     * Returns a list of sample values collected by the value sampling statistics collector. That is the top 100 most common values.
     * @return List of sample values from the basic statistics.
     */
    public List<ProfilingSampleValue> getSampleValues() {
        return sampleValues;
    }

    /**
     * Import results from the statistics.
     * @param statistics Statistics models.
     * @param timeZoneId Time zone id.
     */
    @Override
    public void importStatistics(List<StatisticsMetricModel> statistics, ZoneId timeZoneId) {
        super.importStatistics(statistics, timeZoneId);

        for (StatisticsMetricModel statisticsMetricModel : statistics) {
            String sensorName = statisticsMetricModel.getSensorName();
            if (Objects.equals(sensorName, ColumnSamplingColumnSamplesStatisticsCollectorSpec.SENSOR_NAME)) {
                // column sampling sensor
                Object sampleValue = statisticsMetricModel.getResult();
                long sampleCount = statisticsMetricModel.getSampleCount() != null ? statisticsMetricModel.getSampleCount().longValue() : 0L;
                Instant instantValue = DateTypesConverter.toInstant(sampleValue, timeZoneId);
                this.sampleValues.add(new ProfilingSampleValue(sampleValue, sampleCount, instantValue));
            }
        }

        this.sampleValues.sort(Comparator.comparing((ProfilingSampleValue sampleValues) -> sampleValues.getCount()).reversed());
    }

    /**
     * Retrieves the count of not-null values in a column from either the profiling check, or statistics. Because statistics are stored in the profiling checks, it takes the statistics.
     * This call works only on the column data assets.
     * @return Returns the count of not null values.
     */
    public Long getNotNullsCount() {
        ProfilingCheckResult notNullCountResult = this.getProfilingCheckByCheckName("profile_not_nulls_count", true);

        if (notNullCountResult.getActualValue() != null) {
            return notNullCountResult.getActualValue().longValue();
        }

        return null;
    }

    /**
     * Sets a not null count value as it was coming from a different source. This method is meant to be called from unit tests.
     * @param notNullCount New not null count.
     */
    public void setNotNullsCount(Long notNullCount) {
        ProfilingCheckResult notNullCountResult = this.getProfilingCheckByCheckName("profile_not_nulls_count", true);

        if (notNullCountResult.getActualValue() == null) {
            notNullCountResult.setActualValue(notNullCount == null ? null : notNullCount.doubleValue());
            notNullCountResult.setExecutedAt(Instant.now());
        }
    }

    /**
     * Retrieves the count of null values in a column from either the profiling check, or statistics. Because statistics are stored in the profiling checks, it takes the statistics.
     * This call works only on the column data assets.
     * @return Returns the count of null values.
     */
    public Long getNullsCount() {
        ProfilingCheckResult nullsCountResult = this.getProfilingCheckByCheckName("profile_nulls_count", true);

        if (nullsCountResult.getActualValue() != null) {
            return nullsCountResult.getActualValue().longValue();
        }

        return null;
    }

    /**
     * Sets a null count value as it was coming from a different source. This method is meant to be called from unit tests.
     * @param nullCount New null count.
     */
    public void setNullsCount(Long nullCount) {
        ProfilingCheckResult nullsCountResult = this.getProfilingCheckByCheckName("profile_nulls_count", true);

        if (nullsCountResult.getActualValue() == null) {
            nullsCountResult.setActualValue(nullCount == null ? null : nullCount.doubleValue());
            nullsCountResult.setExecutedAt(Instant.now());
        }
    }

    /**
     * Retrieves the count of distinct values in a column from either the profiling check, or statistics. Because statistics are stored in the profiling checks, it takes the statistics.
     * This call works only on the column data assets.
     * @return Returns the count of distinct values.
     */
    public Long getDistinctCount() {
        ProfilingCheckResult distinctCountResult = this.getProfilingCheckByCheckName("profile_distinct_count", true);

        if (distinctCountResult.getActualValue() != null) {
            return distinctCountResult.getActualValue().longValue();
        }

        return null;
    }

    /**
     * Sets a distinct count value as it was coming from a different source. This method is meant to be called from unit tests.
     * @param distinctCount New distinct count.
     */
    public void setDistinctCount(Long distinctCount) {
        ProfilingCheckResult distinctCountResult = this.getProfilingCheckByCheckName("profile_distinct_count", true);

        if (distinctCountResult.getActualValue() == null) {
            distinctCountResult.setActualValue(distinctCount == null ? null : distinctCount.doubleValue());
            distinctCountResult.setExecutedAt(Instant.now());
        }
    }

    /**
     * Calculates the percentage (0.0 .. 100.0) of samples that pass a predicate.
     * @param matchPredicate Predicate to validate for each value.
     * @return The percentage of samples that match the predicate or null when no samples are present.
     */
    public Double matchPercentageOfSamples(Predicate<Object> matchPredicate) {
        if (this.sampleValues.isEmpty()) {
            return null;
        }

        long totalCount = 0L;
        long totalMatch = 0L;

        for (ProfilingSampleValue profilingSampleValue : this.sampleValues) {
            totalCount += profilingSampleValue.getCount();
            try {
                if (matchPredicate.test(profilingSampleValue.getValue())) {
                    totalMatch += profilingSampleValue.getCount();
                }
            }
            catch (Exception ex) {
                // ignore to allow brutal testing
            }
        }

        if (totalCount == 0L) {
            return null;
        }

        return (double)totalMatch * 100.0 / (double)totalCount;
    }

    /**
     * Measures how many sample values are strings that can be parsed by a given simple date format to a date.
     * @param dateFormat Date format.
     * @return Percent of matching samples or null when not enough samples or wrong values.
     */
    public Double measurePercentOfSamplesMatchingDateFormat(SimpleDateFormat dateFormat) {
        dateFormat.setLenient(false);
        Double percentMatch = this.matchPercentageOfSamples(value -> {
            if (!(value instanceof String)) {
                return false;
            }

            try {
                dateFormat.parse(value.toString());
                return true;
            }
            catch (ParseException ex) {
                return false;
            }
        });

        return percentMatch;
    }
}
