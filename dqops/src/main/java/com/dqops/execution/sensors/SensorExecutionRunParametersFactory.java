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
package com.dqops.execution.sensors;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckType;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;

/**
 * Factory for {@link SensorExecutionRunParameters} objects. Expands all parameters in the form ${ENV_VAR} or ${sm://secret-name}
 */
public interface SensorExecutionRunParametersFactory {
    /**
     * Creates a sensor parameters object. The sensor parameter object contains cloned, truncated and expanded (parameter expansion)
     * specifications for the target connection, table, column, check.
     * @param userHome User home used to look up credentials.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Optional column specification for column sensors.
     * @param check Check specification.
     * @param customCheckDefinition Optional custom check definition, required when the check is a custom check.
     * @param checkType Check type (profiling, monitoring, partitioned).
     * @param dataGroupingConfigurationOverride Data grouping configuration override. Used when not null. We need to assign a custom data grouping configuration for table comparison checks.
     * @param tableComparisonConfigurationSpec Table comparison configuration - only for comparison checks.
     * @param timeSeriesConfigurationSpec Time series configuration extracted from the group of checks (profiling, monitoring, partitioned).
     * @param userTimeWindowFilters Optional user provided time window filters to analyze a time range of data or recent months/days.
     *                             When not provided, the defaults are copied from the table's incremental time window configuration for a matching partition time scale.
     * @param dialectSettings Dialect settings.
     * @return Sensor execution run parameters.
     */
    SensorExecutionRunParameters createSensorParameters(UserHome userHome,
                                                        ConnectionSpec connection,
                                                        TableSpec table,
                                                        ColumnSpec column,
                                                        AbstractCheckSpec<?,?,?,?> check,
                                                        CheckDefinitionSpec customCheckDefinition,
                                                        CheckType checkType,
                                                        DataGroupingConfigurationSpec dataGroupingConfigurationOverride,
                                                        TableComparisonConfigurationSpec tableComparisonConfigurationSpec,
                                                        TimeSeriesConfigurationSpec timeSeriesConfigurationSpec,
                                                        TimeWindowFilterParameters userTimeWindowFilters,
                                                        ProviderDialectSettings dialectSettings);

    /**
     * Creates a sensor parameters object for a statistics collector. The sensor parameter object contains cloned, truncated and expanded (parameter expansion)
     * specifications for the target connection, table, column, check.
     * @param dqoHome DQO home.
     * @param userHome User home used to look up credentials.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Optional column specification for column sensors.
     * @param statisticsCollectorSpec Statistics collector specification.
     * @param userTimeWindowFilters Optional user provided time window filters to analyze a time range of data or recent months/days.
     * @param statisticsDataScope Data scope (whole table or per data stream) for collecting statistics.
     * @param dialectSettings Dialect settings.
     * @return Sensor execution run parameters.
     */
    SensorExecutionRunParameters createStatisticsSensorParameters(DqoHome dqoHome,
                                                                  UserHome userHome,
                                                                  ConnectionSpec connection,
                                                                  TableSpec table,
                                                                  ColumnSpec column,
                                                                  AbstractStatisticsCollectorSpec<?> statisticsCollectorSpec,
                                                                  TimeWindowFilterParameters userTimeWindowFilters,
                                                                  StatisticsDataScope statisticsDataScope,
                                                                  ProviderDialectSettings dialectSettings);

    /**
     * Creates a sensor parameters object for an error sample. The sensor parameter object contains cloned, truncated and expanded (parameter expansion)
     * specifications for the target connection, table, column, check.
     * @param dqoHome DQO home.
     * @param userHome User home used to look up credentials.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Optional column specification for column sensors.
     * @param check Check specification.
     * @param customCheckDefinition Optional custom check definition, required when the check is a custom check.
     * @param userTimeWindowFilters Optional user provided time window filters to analyze a time range of data or recent months/days.
     * @param statisticsDataScope Data scope (whole table or per data stream) for collecting statistics.
     * @param dialectSettings Dialect settings.
     * @return Sensor execution run parameters.
     */
    SensorExecutionRunParameters createErrorSamplerSensorParameters(DqoHome dqoHome,
                                                                    UserHome userHome,
                                                                    ConnectionSpec connection,
                                                                    TableSpec table,
                                                                    ColumnSpec column,
                                                                    AbstractCheckSpec<?,?,?,?> check,
                                                                    CheckDefinitionSpec customCheckDefinition,
                                                                    TimeWindowFilterParameters userTimeWindowFilters,
                                                                    ErrorSamplesDataScope statisticsDataScope,
                                                                    ProviderDialectSettings dialectSettings);

    /**
     * Creates an effective time range filter, picking the correct configuration from the table's incremental time window
     * for the time scale used by a partitioned check or just uses the user provided filters.
     * @param tableSpec Table specification with the incremental time window configuration.
     * @param timeSeriesConfigurationSpec Time series configuration, to check what time scale (daily, monthly) is used.
     * @param userTimeWindowFilters User provided time window filter that will override the default configuration.
     * @return Effective incremental time window filers, not null (even empty, but not null).
     */
    TimeWindowFilterParameters makeEffectiveIncrementalFilter(TableSpec tableSpec,
                                                              TimeSeriesConfigurationSpec timeSeriesConfigurationSpec,
                                                              TimeWindowFilterParameters userTimeWindowFilters);
}
