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
import com.dqops.checks.custom.CustomCheckSpec;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.core.configuration.DqoSensorLimitsConfigurationProperties;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.execution.checks.EffectiveSensorRuleNames;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.PartitionIncrementalTimeWindowSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for {@link SensorExecutionRunParameters} objects. Expands all parameters in the form ${ENV_VAR} or ${sm://secret-name}
 */
@Component
public class SensorExecutionRunParametersFactoryImpl implements SensorExecutionRunParametersFactory {
    private final SecretValueProvider secretValueProvider;
    private DqoSensorLimitsConfigurationProperties sensorLimitsConfigurationProperties;

    /**
     * Default injection constructor.
     * @param secretValueProvider Secret value provider to expand environment variables and secrets.
     * @param sensorLimitsConfigurationProperties Sensor limit configuration properties.
     */
    @Autowired
    public SensorExecutionRunParametersFactoryImpl(SecretValueProvider secretValueProvider,
                                                   DqoSensorLimitsConfigurationProperties sensorLimitsConfigurationProperties) {
        this.secretValueProvider = secretValueProvider;
        this.sensorLimitsConfigurationProperties = sensorLimitsConfigurationProperties;
    }

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
    @Override
    public SensorExecutionRunParameters createSensorParameters(UserHome userHome,
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
                                                               ProviderDialectSettings dialectSettings) {
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);

        ConnectionSpec expandedConnection = connection.expandAndTrim(this.secretValueProvider, secretValueLookupContext);
        TableSpec expandedTable = table.expandAndTrim(this.secretValueProvider, secretValueLookupContext);
        ColumnSpec expandedColumn = column != null ? column.expandAndTrim(this.secretValueProvider, secretValueLookupContext) : null;
        AbstractSensorParametersSpec sensorParameters = check.getParameters().expandAndTrim(this.secretValueProvider, secretValueLookupContext);

        TimeSeriesConfigurationSpec timeSeries = timeSeriesConfigurationSpec; // TODO: for very custom checks, we can extract the time series override from the check
        DataGroupingConfigurationSpec dataGroupingConfiguration =
                dataGroupingConfigurationOverride != null ? dataGroupingConfigurationOverride :
                        (check.getDataGrouping() != null ?
                         expandedTable.getGroupings().get(check.getDataGrouping()) : expandedTable.getDefaultDataGroupingConfiguration());
        TimeWindowFilterParameters timeWindowFilterParameters =
                this.makeEffectiveIncrementalFilter(table, timeSeries, userTimeWindowFilters);
        EffectiveSensorRuleNames effectiveSensorRuleNames = new EffectiveSensorRuleNames();
        if (customCheckDefinition != null) {
            effectiveSensorRuleNames.setSensorName(customCheckDefinition.getSensorName());
            effectiveSensorRuleNames.setRuleName(customCheckDefinition.getRuleName());
        } else {
            effectiveSensorRuleNames.setSensorName(check.getParameters().getSensorDefinitionName());
            effectiveSensorRuleNames.setRuleName(check.getRuleDefinitionName());
        }

        if (check instanceof CustomCheckSpec) {
            CustomCheckSpec customCheckSpec = (CustomCheckSpec) check;
            if (!Strings.isNullOrEmpty(customCheckSpec.getSensorName())) {
                effectiveSensorRuleNames.setSensorName(customCheckSpec.getSensorName());
            }
            if (!Strings.isNullOrEmpty(customCheckSpec.getRuleName())) {
                effectiveSensorRuleNames.setRuleName(customCheckSpec.getRuleName());
            }
        }

        CheckSearchFilters exactCheckSearchFilters = new CheckSearchFilters();
        exactCheckSearchFilters.setConnection(connection.getConnectionName());
        exactCheckSearchFilters.setFullTableName(table.getPhysicalTableName().toTableSearchFilter());
        exactCheckSearchFilters.setColumn(column == null ? null : column.getColumnName());
        exactCheckSearchFilters.setCheckCategory(check.getCategoryName());
        exactCheckSearchFilters.setCheckName(check.getCheckName());
        exactCheckSearchFilters.setSensorName(effectiveSensorRuleNames.getSensorName());

        int rowCountLimit = checkType == CheckType.partitioned ? this.sensorLimitsConfigurationProperties.getSensorReadoutLimitPartitioned() :
                this.sensorLimitsConfigurationProperties.getSensorReadoutLimit();
        return new SensorExecutionRunParameters(expandedConnection, expandedTable, expandedColumn,
                check, null, effectiveSensorRuleNames, checkType, timeSeries, timeWindowFilterParameters,
                dataGroupingConfiguration, tableComparisonConfigurationSpec, null, sensorParameters, dialectSettings, exactCheckSearchFilters,
                rowCountLimit, this.sensorLimitsConfigurationProperties.isFailOnSensorReadoutLimitExceeded());
    }

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
    @Override
    public SensorExecutionRunParameters createStatisticsSensorParameters(DqoHome dqoHome,
                                                                         UserHome userHome,
                                                                         ConnectionSpec connection,
                                                                         TableSpec table,
                                                                         ColumnSpec column,
                                                                         AbstractStatisticsCollectorSpec<?> statisticsCollectorSpec,
                                                                         TimeWindowFilterParameters userTimeWindowFilters,
                                                                         StatisticsDataScope statisticsDataScope,
                                                                         ProviderDialectSettings dialectSettings) {
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);
        ConnectionSpec expandedConnection = connection.expandAndTrim(this.secretValueProvider, secretValueLookupContext);
        TableSpec expandedTable = table.expandAndTrim(this.secretValueProvider, secretValueLookupContext);
        ColumnSpec expandedColumn = column != null ? column.expandAndTrim(this.secretValueProvider, secretValueLookupContext) : null;
        AbstractSensorParametersSpec sensorParameters = statisticsCollectorSpec.getParameters().expandAndTrim(this.secretValueProvider, secretValueLookupContext);

        TimeSeriesConfigurationSpec timeSeries = TimeSeriesConfigurationSpec.createCurrentTimeMilliseconds();
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = statisticsDataScope == StatisticsDataScope.table ? null :
                expandedTable.getDefaultDataGroupingConfiguration();
        TimeWindowFilterParameters timeWindowFilterParameters =
                this.makeEffectiveIncrementalFilter(table, timeSeries, userTimeWindowFilters);
        String sensorDefinitionName = statisticsCollectorSpec.getParameters().getSensorDefinitionName();
        EffectiveSensorRuleNames effectiveSensorRuleNames = new EffectiveSensorRuleNames(
                sensorDefinitionName, null);
        SensorDefinitionWrapper sensorDefinitionWrapper = dqoHome.getSensors().getByObjectName(sensorDefinitionName, true);
        if (sensorDefinitionWrapper == null) {
            return null;
        }

        ProviderSensorDefinitionWrapper providerSpecificSensor = sensorDefinitionWrapper.getProviderSensors().getByObjectName(connection.getProviderType(), true);
        if (providerSpecificSensor == null) {
            return null; // not supported on this data source (percentile on mysql for example)
        }

        return new SensorExecutionRunParameters(expandedConnection, expandedTable, expandedColumn,
                null, statisticsCollectorSpec, effectiveSensorRuleNames, null, timeSeries, timeWindowFilterParameters,
                dataGroupingConfigurationSpec, null, null, sensorParameters, dialectSettings, null,
                this.sensorLimitsConfigurationProperties.getSensorReadoutLimit(),
                false); // statistics is opportunistic, we do not fail, we just collect something for data groups
    }

    /**
     * Creates an effective time range filter, picking the correct configuration from the table's incremental time window
     * for the time scale used by a partitioned check or just uses the user provided filters.
     * @param tableSpec Table specification with the incremental time window configuration.
     * @param timeSeriesConfigurationSpec Time series configuration, to check what time scale (daily, monthly) is used.
     * @param userTimeWindowFilters User provided time window filter that will override the default configuration.
     * @return Effective incremental time window filers, not null (even empty, but not null).
     */
    @Override
    public TimeWindowFilterParameters makeEffectiveIncrementalFilter(
            TableSpec tableSpec,
            TimeSeriesConfigurationSpec timeSeriesConfigurationSpec,
            TimeWindowFilterParameters userTimeWindowFilters) {
        if (userTimeWindowFilters != null && userTimeWindowFilters.hasAnyParametersApplied()) {
            return userTimeWindowFilters;
        }

        if (timeSeriesConfigurationSpec.getMode() == TimeSeriesMode.current_time) {
            return new TimeWindowFilterParameters();
        }

        PartitionIncrementalTimeWindowSpec tableTimeWindowSpec = tableSpec.getIncrementalTimeWindow();
        TimeWindowFilterParameters resultFilter = new TimeWindowFilterParameters();


        TimePeriodGradient partitioningTimeGradient = timeSeriesConfigurationSpec.getMode() == TimeSeriesMode.timestamp_column ?
                timeSeriesConfigurationSpec.getTimeGradient() : null;
        switch (partitioningTimeGradient) {
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

        TimeWindowFilterParameters effectiveFilter = resultFilter.withUserFilters(userTimeWindowFilters,
                partitioningTimeGradient); // override defaults with the user provided settings
        return effectiveFilter;
    }
}
