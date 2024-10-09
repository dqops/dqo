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

package com.dqops.core.similarity;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.statistics.models.StatisticsMetricModel;
import com.dqops.data.statistics.models.StatisticsResultsForColumnModel;
import com.dqops.data.statistics.models.StatisticsResultsForTableModel;
import com.dqops.data.statistics.services.StatisticsDataService;
import com.dqops.metadata.similarity.TableSimilarityContainer;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.statistics.column.sampling.ColumnSamplingColumnSamplesStatisticsCollectorSpec;
import com.dqops.utils.conversion.DateTypesConverter;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

/**
 * Table similarity score calculation service.
 */
@Component
public class TableSimilarityScoreFactoryImpl implements TableSimilarityScoreFactory {
    private final StatisticsDataService statisticsDataService;
    private final DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * Dependency injection constructor.
     * @param statisticsDataService Statistics data service.
     * @param defaultTimeZoneProvider Default timezone provider.
     */
    @Autowired
    public TableSimilarityScoreFactoryImpl(
            StatisticsDataService statisticsDataService,
            DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.statisticsDataService = statisticsDataService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Calculates a table similarity score from statistics.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity and the data domain.
     * @return Table similarity score or null when the table has no statistics.
     */
    @Override
    public TableSimilarityContainer calculateSimilarityScore(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userDomainIdentity) {
        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
        HashFunction hashFunction = Hashing.farmHashFingerprint64();
        DataSimilarityFormula tableSimilarityCalculator = new DataSimilarityFormula();

        TableSimilarityContainer tableSimilarityContainer = new TableSimilarityContainer();
        StatisticsResultsForTableModel mostRecentStatisticsForTable = this.statisticsDataService.getMostRecentStatisticsForTable(
                connectionName, physicalTableName,
                CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME, true, userDomainIdentity);

        if (mostRecentStatisticsForTable == null) {
            return null;
        }

        for (StatisticsResultsForColumnModel columnStatistics : mostRecentStatisticsForTable.getColumns().values()) {
            DataSimilarityFormula columnSimilarityCalculator = new DataSimilarityFormula();

            for (StatisticsMetricModel statisticsMetricModel : columnStatistics.getMetrics()) {
                String sensorName = statisticsMetricModel.getSensorName();
                if (Objects.equals(sensorName, ColumnSamplingColumnSamplesStatisticsCollectorSpec.SENSOR_NAME)) {
                    // column sampling sensor
                    Object sampleValue = statisticsMetricModel.getResult();
                    long sampleCount = statisticsMetricModel.getSampleCount() != null ? statisticsMetricModel.getSampleCount() : 1L;
                    Instant instantValue = DateTypesConverter.toInstant(sampleValue, defaultTimeZoneId);
                    String sampleValueString = sampleValue != null ? sampleValue.toString() : "";
                    if (instantValue != null) {
                        sampleValueString = instantValue.atZone(defaultTimeZoneId)
                                .toLocalDate().toString();
                    }

                    HashCode hashCode = hashFunction.hashString(sampleValueString, StandardCharsets.UTF_8);
                    columnSimilarityCalculator.append(hashCode.asLong(), sampleCount);
                    tableSimilarityCalculator.append(hashCode.asLong(), sampleCount);
                }
            }

            tableSimilarityContainer.getCs().put(columnStatistics.getColumnName(), columnSimilarityCalculator.getScore());
        }

        tableSimilarityContainer.setTs(tableSimilarityCalculator.getScore());
        tableSimilarityContainer.setLm(this.statisticsDataService.getStatisticsLastModified(connectionName, physicalTableName, userDomainIdentity));
        return tableSimilarityContainer;
    }
}
