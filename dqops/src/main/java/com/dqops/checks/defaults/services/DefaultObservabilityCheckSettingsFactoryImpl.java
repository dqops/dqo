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

package com.dqops.checks.defaults.services;

import com.dqops.checks.column.checkspecs.anomaly.ColumnAnomalyDifferencingSumCheckSpec;
import com.dqops.checks.column.checkspecs.anomaly.ColumnAnomalyStationaryMeanCheckSpec;
import com.dqops.checks.column.checkspecs.datatype.ColumnDatatypeStringDatatypeChangedCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.*;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaColumnExistsCheckSpec;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaTypeChangedCheckSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.schema.ColumnSchemaMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnSchemaProfilingChecksSpec;
import com.dqops.checks.column.monitoring.anomaly.ColumnAnomalyDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.datatype.ColumnDatatypeDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.schema.ColumnSchemaDailyMonitoringChecksSpec;
import com.dqops.checks.defaults.DefaultDailyMonitoringObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultMonthlyMonitoringObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultProfilingObservabilityCheckSettingsSpec;
import com.dqops.checks.table.checkspecs.availability.TableAvailabilityCheckSpec;
import com.dqops.checks.table.checkspecs.schema.*;
import com.dqops.checks.table.checkspecs.volume.TableAnomalyDifferencingRowCountCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableChangeRowCountCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.monitoring.availability.TableAvailabilityMonthlyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.schema.TableSchemaMonthlyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeMonthlyMonitoringChecksSpec;
import com.dqops.checks.table.profiling.TableAvailabilityProfilingChecksSpec;
import com.dqops.checks.table.profiling.TableSchemaProfilingChecksSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.monitoring.availability.TableAvailabilityDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.schema.TableSchemaDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.rules.change.ChangePercent1DayRule10ParametersSpec;
import com.dqops.rules.change.ChangePercentRule10ParametersSpec;
import com.dqops.rules.comparison.EqualsInteger1RuleParametersSpec;
import com.dqops.rules.comparison.MaxFailuresRule0ParametersSpec;
import com.dqops.rules.comparison.MinCountRuleWarningParametersSpec;
import com.dqops.rules.comparison.ValueChangedParametersSpec;
import com.dqops.rules.percentile.AnomalyDifferencingPercentileMovingAverageRule1ParametersSpec;
import com.dqops.rules.percentile.AnomalyStationaryPercentileMovingAverageRule1ParametersSpec;
import org.springframework.stereotype.Component;

/**
 * Factory that creates the default configuration of checks, when DQO is initialized and the initial configuration is loaded into the local settings.
 */
@Component
public class DefaultObservabilityCheckSettingsFactoryImpl implements DefaultObservabilityCheckSettingsFactory {
    /**
     * Creates the default check settings to be stored in the local settings. This is an initial, default configuration.
     * @return Default observability settings.
     */
    @Override
    public DefaultObservabilityCheckSettingsSpec createDefaultCheckSettings() {
        DefaultObservabilityCheckSettingsSpec defaultSettings = new DefaultObservabilityCheckSettingsSpec();
        defaultSettings.setProfiling(createDefaultProfilingChecks());
        defaultSettings.setMonitoringDaily(createDefaultDailyMonitoringChecks());
        defaultSettings.setMonitoringMonthly(createDefaultMonthlyMonitoringChecks());

        return defaultSettings;
    }

    /**
     * Creates the default configuration of daily monitoring checks.
     * @return The default configuration of daily monitoring checks.
     */
    protected DefaultDailyMonitoringObservabilityCheckSettingsSpec createDefaultDailyMonitoringChecks() {
        DefaultDailyMonitoringObservabilityCheckSettingsSpec defaultSettings = new DefaultDailyMonitoringObservabilityCheckSettingsSpec();

        TableAvailabilityDailyMonitoringChecksSpec tableAvailability = new TableAvailabilityDailyMonitoringChecksSpec();
        tableAvailability.setDailyTableAvailability(new TableAvailabilityCheckSpec() {{
            setWarning(new MaxFailuresRule0ParametersSpec());
        }});
        defaultSettings.getTable().setAvailability(tableAvailability);

        TableVolumeDailyMonitoringChecksSpec tableVolume = new TableVolumeDailyMonitoringChecksSpec();
        tableVolume.setDailyRowCount(new TableRowCountCheckSpec() {{
            setWarning(new MinCountRuleWarningParametersSpec());
        }});
        tableVolume.setDailyRowCountChange(new TableChangeRowCountCheckSpec() {{
            setWarning(new ChangePercentRule10ParametersSpec());
        }});
        tableVolume.setDailyRowCountAnomalyDifferencing(new TableAnomalyDifferencingRowCountCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRule1ParametersSpec());
        }});
        defaultSettings.getTable().setVolume(tableVolume);

        ColumnDatatypeDailyMonitoringChecksSpec columnDatatype = new ColumnDatatypeDailyMonitoringChecksSpec();
        columnDatatype.setDailyStringDatatypeChanged(new ColumnDatatypeStringDatatypeChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        defaultSettings.getColumn().setDatatype(columnDatatype);

        ColumnAnomalyDailyMonitoringChecksSpec columnAnomaly = new ColumnAnomalyDailyMonitoringChecksSpec();
        columnAnomaly.setDailySumAnomalyDifferencing(new ColumnAnomalyDifferencingSumCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRule1ParametersSpec());
        }});
        columnAnomaly.setDailyMeanAnomalyStationary(new ColumnAnomalyStationaryMeanCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRule1ParametersSpec());
        }});
        defaultSettings.getColumn().setAnomaly(columnAnomaly);

        TableSchemaDailyMonitoringChecksSpec tableSchema = new TableSchemaDailyMonitoringChecksSpec();
        tableSchema.setDailyColumnCountChanged(new TableSchemaColumnCountChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        tableSchema.setDailyColumnListChanged(new TableSchemaColumnListChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        tableSchema.setDailyColumnListOrOrderChanged(new TableSchemaColumnListOrOrderChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        tableSchema.setDailyColumnTypesChanged(new TableSchemaColumnTypesChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        defaultSettings.getTable().setSchema(tableSchema);

        ColumnSchemaDailyMonitoringChecksSpec columnSchema = new ColumnSchemaDailyMonitoringChecksSpec();
        columnSchema.setDailyColumnExists(new ColumnSchemaColumnExistsCheckSpec() {{
            setWarning(new EqualsInteger1RuleParametersSpec());
        }});
        columnSchema.setDailyColumnTypeChanged(new ColumnSchemaTypeChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        defaultSettings.getColumn().setSchema(columnSchema);

        ColumnNullsDailyMonitoringChecksSpec columnNulls = new ColumnNullsDailyMonitoringChecksSpec();
        columnNulls.setDailyNullsCount(new ColumnNullsCountCheckSpec());
        columnNulls.setDailyNullsPercent(new ColumnNullsPercentCheckSpec());
        columnNulls.setDailyNotNullsPercent(new ColumnNotNullsPercentCheckSpec());
        columnNulls.setDailyNullsPercentAnomalyStationary(new ColumnAnomalyStationaryNullPercentCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRule1ParametersSpec());
        }});
        columnNulls.setDailyNullsPercentChangeYesterday(new ColumnChangeNullPercentSinceYesterdayCheckSpec() {{
            setWarning(new ChangePercent1DayRule10ParametersSpec());
        }});
        defaultSettings.getColumn().setNulls(columnNulls);

        return defaultSettings;
    }

    /**
     * Creates the default configuration of profiling checks.
     * @return The default configuration of profiling checks.
     */
    protected DefaultProfilingObservabilityCheckSettingsSpec createDefaultProfilingChecks() {
        DefaultProfilingObservabilityCheckSettingsSpec defaultSettings = new DefaultProfilingObservabilityCheckSettingsSpec();
        TableAvailabilityProfilingChecksSpec tableAvailability = new TableAvailabilityProfilingChecksSpec();
        tableAvailability.setProfileTableAvailability(new TableAvailabilityCheckSpec() {{
            setWarning(new MaxFailuresRule0ParametersSpec());
        }});
        defaultSettings.getTable().setAvailability(tableAvailability);

        TableVolumeProfilingChecksSpec tableVolume = new TableVolumeProfilingChecksSpec();
        tableVolume.setProfileRowCount(new TableRowCountCheckSpec() {{
            setWarning(new MinCountRuleWarningParametersSpec());
        }});
        defaultSettings.getTable().setVolume(tableVolume);

        TableSchemaProfilingChecksSpec tableSchema = new TableSchemaProfilingChecksSpec();
        tableSchema.setProfileColumnCount(new TableSchemaColumnCountCheckSpec());
        defaultSettings.getTable().setSchema(tableSchema);

        ColumnSchemaProfilingChecksSpec columnSchema = new ColumnSchemaProfilingChecksSpec();
        columnSchema.setProfileColumnExists(new ColumnSchemaColumnExistsCheckSpec() {{
            setWarning(new EqualsInteger1RuleParametersSpec());
        }});
        columnSchema.setProfileColumnTypeChanged(new ColumnSchemaTypeChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        defaultSettings.getColumn().setSchema(columnSchema);

        ColumnNullsProfilingChecksSpec columnNulls = new ColumnNullsProfilingChecksSpec();
        columnNulls.setProfileNullsCount(new ColumnNullsCountCheckSpec());
        columnNulls.setProfileNullsPercent(new ColumnNullsPercentCheckSpec());
        defaultSettings.getColumn().setNulls(columnNulls);

        return defaultSettings;
    }

    /**
     * Creates the default configuration of daily monitoring checks.
     * @return The default configuration of daily monitoring checks.
     */
    protected DefaultMonthlyMonitoringObservabilityCheckSettingsSpec createDefaultMonthlyMonitoringChecks() {
        DefaultMonthlyMonitoringObservabilityCheckSettingsSpec defaultSettings = new DefaultMonthlyMonitoringObservabilityCheckSettingsSpec();
        TableAvailabilityMonthlyMonitoringChecksSpec availability = new TableAvailabilityMonthlyMonitoringChecksSpec();
        availability.setMonthlyTableAvailability(new TableAvailabilityCheckSpec() {{
            setWarning(new MaxFailuresRule0ParametersSpec());
        }});
        defaultSettings.getTable().setAvailability(availability);

        TableVolumeMonthlyMonitoringChecksSpec volume = new TableVolumeMonthlyMonitoringChecksSpec();
        volume.setMonthlyRowCount(new TableRowCountCheckSpec() {{
            setWarning(new MinCountRuleWarningParametersSpec());
        }});
        defaultSettings.getTable().setVolume(volume);

        TableSchemaMonthlyMonitoringChecksSpec tableSchema = new TableSchemaMonthlyMonitoringChecksSpec();
        tableSchema.setMonthlyColumnCount(new TableSchemaColumnCountCheckSpec()); // just snapshotting
        tableSchema.setMonthlyColumnCountChanged(new TableSchemaColumnCountChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        tableSchema.setMonthlyColumnListChanged(new TableSchemaColumnListChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        tableSchema.setMonthlyColumnTypesChanged(new TableSchemaColumnTypesChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        tableSchema.setMonthlyColumnListOrOrderChanged(new TableSchemaColumnListOrOrderChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        defaultSettings.getTable().setSchema(tableSchema);

        ColumnSchemaMonthlyMonitoringChecksSpec columnSchema = new ColumnSchemaMonthlyMonitoringChecksSpec();
        columnSchema.setMonthlyColumnExists(new ColumnSchemaColumnExistsCheckSpec() {{
            setWarning(new EqualsInteger1RuleParametersSpec());
        }});
        columnSchema.setMonthlyColumnTypeChanged(new ColumnSchemaTypeChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        defaultSettings.getColumn().setSchema(columnSchema);

        ColumnNullsMonthlyMonitoringChecksSpec columnNulls = new ColumnNullsMonthlyMonitoringChecksSpec();
        columnNulls.setMonthlyNullsCount(new ColumnNullsCountCheckSpec());
        columnNulls.setMonthlyNullsPercent(new ColumnNullsPercentCheckSpec());
        defaultSettings.getColumn().setNulls(columnNulls);

        return defaultSettings;
    }
}
