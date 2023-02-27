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
package ai.dqo.execution.sensors;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.checks.CheckType;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.data.statistics.factory.StatisticsDataScope;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesMode;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.PartitionIncrementalTimeWindowSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.statistics.AbstractStatisticsCollectorSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for {@link SensorExecutionRunParameters} objects. Expands all parameters in the form ${ENV_VAR} or ${sm://secret-name}
 */
@Component
public class SensorExecutionRunParametersFactoryImpl implements SensorExecutionRunParametersFactory {
    private final SecretValueProvider secretValueProvider;

    /**
     * Default injection constructor.
     * @param secretValueProvider Secret value provider to expand environment variables and secrets.
     */
    @Autowired
    public SensorExecutionRunParametersFactoryImpl(SecretValueProvider secretValueProvider) {
        this.secretValueProvider = secretValueProvider;
    }

    /**
     * Creates a sensor parameters object. The sensor parameter object contains cloned, truncated and expanded (parameter expansion)
     * specifications for the target connection, table, column, check.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Optional column specification for column sensors.
     * @param check Check specification.
     * @param checkType Check type (adhoc, checkpoint, partitioned).
     * @param timeSeriesConfigurationSpec Time series configuration extracted from the group of checks (ad-hoc, checkpoints, partitioned).
     * @param userTimeWindowFilters Optional user provided time window filters to analyze a time range of data or recent months/days.
     *                             When not provided, the defaults are copied from the table's incremental time window configuration for a matching partition time scale.
     * @param dialectSettings Dialect settings.
     * @return Sensor execution run parameters.
     */
    @Override
    public SensorExecutionRunParameters createSensorParameters(ConnectionSpec connection,
                                                               TableSpec table,
                                                               ColumnSpec column,
                                                               AbstractCheckSpec<?,?,?,?> check,
                                                               CheckType checkType,
                                                               TimeSeriesConfigurationSpec timeSeriesConfigurationSpec,
                                                               TimeWindowFilterParameters userTimeWindowFilters,
                                                               ProviderDialectSettings dialectSettings) {
        ConnectionSpec expandedConnection = connection.expandAndTrim(this.secretValueProvider);
        TableSpec expandedTable = table.expandAndTrim(this.secretValueProvider);
        ColumnSpec expandedColumn = column != null ? column.expandAndTrim(this.secretValueProvider) : null;
        AbstractSensorParametersSpec sensorParameters = check.getParameters().expandAndTrim(this.secretValueProvider);

        TimeSeriesConfigurationSpec timeSeries = timeSeriesConfigurationSpec; // TODO: for very custom checks, we can extract the time series override from the check
        DataStreamMappingSpec dataStreams = check.getDataStream() != null ?
                expandedTable.getDataStreams().get(check.getDataStream()) : expandedTable.getDataStreams().getFirstDataStreamMapping();
        TimeWindowFilterParameters timeWindowFilterParameters =
                this.makeEffectiveIncrementalFilter(table, timeSeries, userTimeWindowFilters);

        return new SensorExecutionRunParameters(expandedConnection, expandedTable, expandedColumn,
                check, null, checkType, timeSeries, timeWindowFilterParameters,
                dataStreams, sensorParameters, dialectSettings);
    }

    /**
     * Creates a sensor parameters object for a statistics collector. The sensor parameter object contains cloned, truncated and expanded (parameter expansion)
     * specifications for the target connection, table, column, check.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Optional column specification for column sensors.
     * @param statisticsCollectorSpec Statistics collector specification.
     * @param userTimeWindowFilters Optional user provided time window filters to analyze a time range of data or recent months/days.
     * @param statisticsDataScope Data scope (whole table or per data stream) for collecting statistics.
     * @param dialectSettings Dialect settings.
     * @return Sensor execution run parameters.
     */
    @Override
    public SensorExecutionRunParameters createStatisticsSensorParameters(ConnectionSpec connection,
                                                                         TableSpec table,
                                                                         ColumnSpec column,
                                                                         AbstractStatisticsCollectorSpec<?> statisticsCollectorSpec,
                                                                         TimeWindowFilterParameters userTimeWindowFilters,
                                                                         StatisticsDataScope statisticsDataScope,
                                                                         ProviderDialectSettings dialectSettings) {
        ConnectionSpec expandedConnection = connection.expandAndTrim(this.secretValueProvider);
        TableSpec expandedTable = table.expandAndTrim(this.secretValueProvider);
        ColumnSpec expandedColumn = column != null ? column.expandAndTrim(this.secretValueProvider) : null;
        AbstractSensorParametersSpec sensorParameters = statisticsCollectorSpec.getParameters().expandAndTrim(this.secretValueProvider);

        TimeSeriesConfigurationSpec timeSeries = TimeSeriesConfigurationSpec.createCurrentTimeMilliseconds();
        DataStreamMappingSpec dataStreams = statisticsDataScope == StatisticsDataScope.table ? null :
                expandedTable.getDataStreams().getFirstDataStreamMapping();
        TimeWindowFilterParameters timeWindowFilterParameters =
                this.makeEffectiveIncrementalFilter(table, timeSeries, userTimeWindowFilters);

        return new SensorExecutionRunParameters(expandedConnection, expandedTable, expandedColumn,
                null, statisticsCollectorSpec, null, timeSeries, timeWindowFilterParameters,
                dataStreams, sensorParameters, dialectSettings);
    }

    /**
     * Creates an effective time range filter, picking the correct configuration from the table's incremental time window
     * for the time scale used by a partitioned check or just uses the user provided filters.
     * @param tableSpec Table specification with the incremental time window configuration.
     * @param timeSeriesConfigurationSpec Time series configuration, to check what time scale (daily, monthly) is used.
     * @param userTimeWindowFilters User provided time window filter that will override the default configuration.
     * @return Effective incremental time window filers, not null (even empty, but not null).
     */
    private TimeWindowFilterParameters makeEffectiveIncrementalFilter(
            TableSpec tableSpec,
            TimeSeriesConfigurationSpec timeSeriesConfigurationSpec,
            TimeWindowFilterParameters userTimeWindowFilters) {
        if (timeSeriesConfigurationSpec.getMode() == TimeSeriesMode.current_time) {
            return userTimeWindowFilters != null ? userTimeWindowFilters : new TimeWindowFilterParameters();
        }

        PartitionIncrementalTimeWindowSpec tableTimeWindowSpec = tableSpec.getIncrementalTimeWindow();
        TimeWindowFilterParameters resultFilter = new TimeWindowFilterParameters();

        switch (timeSeriesConfigurationSpec.getTimeGradient()) {
            case day:
                resultFilter.setDailyPartitioningRecentDays(tableTimeWindowSpec.getDailyPartitioningRecentDays());
                resultFilter.setDailyPartitioningIncludeToday(tableTimeWindowSpec.isDailyPartitioningIncludeToday());
                break;

            case month:
                resultFilter.setMonthlyPartitioningRecentMonths(tableTimeWindowSpec.getMonthlyPartitioningRecentMonths());
                resultFilter.setMonthlyPartitioningIncludeCurrentMonth(tableTimeWindowSpec.isMonthlyPartitioningIncludeCurrentMonth());
                break;
            default:
                break;
        }

        TimeWindowFilterParameters effectiveFilter = resultFilter.withUserFilters(userTimeWindowFilters); // override defaults with the user provided settings
        return effectiveFilter;
    }
}
