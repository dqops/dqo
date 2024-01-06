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

import com.dqops.checks.column.checkspecs.anomaly.ColumnSumAnomalyDifferencingCheckSpec;
import com.dqops.checks.column.checkspecs.anomaly.ColumnMeanAnomalyStationaryCheckSpec;
import com.dqops.checks.column.checkspecs.datatype.ColumnDatatypeStringDatatypeChangedCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.*;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaColumnExistsCheckSpec;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaTypeChangedCheckSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.monitoring.anomaly.ColumnAnomalyDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.datatype.ColumnDatatypeDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.schema.ColumnSchemaDailyMonitoringChecksSpec;
import com.dqops.checks.defaults.DefaultDailyMonitoringObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultMonthlyMonitoringObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultObservabilityChecksSpec;
import com.dqops.checks.defaults.DefaultProfilingObservabilityCheckSettingsSpec;
import com.dqops.checks.table.checkspecs.availability.TableAvailabilityCheckSpec;
import com.dqops.checks.table.checkspecs.schema.*;
import com.dqops.checks.table.checkspecs.volume.TableRowCountAnomalyDifferencingCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountChangeCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.profiling.TableSchemaProfilingChecksSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.monitoring.availability.TableAvailabilityDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.schema.TableSchemaDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.rules.change.ChangePercent1DayRule10ParametersSpec;
import com.dqops.rules.change.ChangePercentRule10ParametersSpec;
import com.dqops.rules.comparison.*;
import com.dqops.rules.percentile.AnomalyDifferencingPercentileMovingAverageRule1ParametersSpec;
import com.dqops.rules.percentile.AnomalyStationaryPercentileMovingAverageRule1ParametersSpec;
import org.springframework.stereotype.Component;

/**
 * Factory that creates the default configuration of checks, when DQOps is initialized and the initial configuration is loaded into the local settings.
 */
@Component
public class DefaultObservabilityCheckSettingsFactoryImpl implements DefaultObservabilityCheckSettingsFactory {
    /**
     * Creates the default check settings to be stored in the local settings. This is an initial, default configuration.
     * @return Default observability settings.
     */
    @Override
    public DefaultObservabilityChecksSpec createDefaultCheckSettings() {
        DefaultObservabilityChecksSpec defaultSettings = new DefaultObservabilityChecksSpec();
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
        tableVolume.setDailyRowCountChange(new TableRowCountChangeCheckSpec() {{
            setWarning(new ChangePercentRule10ParametersSpec());
        }});
        tableVolume.setDailyRowCountAnomaly(new TableRowCountAnomalyDifferencingCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRule1ParametersSpec());
        }});
        defaultSettings.getTable().setVolume(tableVolume);

        ColumnDatatypeDailyMonitoringChecksSpec columnDatatype = new ColumnDatatypeDailyMonitoringChecksSpec();
        columnDatatype.setDailyDetectedDatatypeInTextChanged(new ColumnDatatypeStringDatatypeChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        defaultSettings.getColumn().setDatatype(columnDatatype);

        ColumnAnomalyDailyMonitoringChecksSpec columnAnomaly = new ColumnAnomalyDailyMonitoringChecksSpec();
        columnAnomaly.setDailySumAnomaly(new ColumnSumAnomalyDifferencingCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRule1ParametersSpec());
        }});
        columnAnomaly.setDailyMeanAnomaly(new ColumnMeanAnomalyStationaryCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRule1ParametersSpec());
        }});
        defaultSettings.getColumn().setAnomaly(columnAnomaly);

        TableSchemaDailyMonitoringChecksSpec tableSchema = new TableSchemaDailyMonitoringChecksSpec();
        tableSchema.setDailyColumnCountChanged(new TableSchemaColumnCountChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        tableSchema.setDailyColumnListChanged(new TableSchemaColumnListChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        tableSchema.setDailyColumnListOrOrderChanged(new TableSchemaColumnListOrOrderChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        tableSchema.setDailyColumnTypesChanged(new TableSchemaColumnTypesChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        defaultSettings.getTable().setSchema(tableSchema);

        ColumnSchemaDailyMonitoringChecksSpec columnSchema = new ColumnSchemaDailyMonitoringChecksSpec();
        columnSchema.setDailyColumnExists(new ColumnSchemaColumnExistsCheckSpec() {{
            setWarning(new Equals1RuleParametersSpec());
        }});
        columnSchema.setDailyColumnTypeChanged(new ColumnSchemaTypeChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        defaultSettings.getColumn().setSchema(columnSchema);

        ColumnNullsDailyMonitoringChecksSpec columnNulls = new ColumnNullsDailyMonitoringChecksSpec();
        columnNulls.setDailyNullsCount(new ColumnNullsCountCheckSpec());
        columnNulls.setDailyNotNullsCount(new ColumnNotNullsCountCheckSpec() {{
            setWarning(new MinCountRule1ParametersSpec());
        }});
        columnNulls.setDailyNullsPercent(new ColumnNullsPercentCheckSpec());
        columnNulls.setDailyNotNullsPercent(new ColumnNotNullsPercentCheckSpec());
        columnNulls.setDailyNullsPercentAnomaly(new ColumnNullPercentAnomalyStationaryCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRule1ParametersSpec());
        }});
        columnNulls.setDailyNullsPercentChange1Day(new ColumnNullPercentChange1DayCheckSpec() {{
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
        TableVolumeProfilingChecksSpec tableVolume = new TableVolumeProfilingChecksSpec();
        tableVolume.setProfileRowCount(new TableRowCountCheckSpec() {{
            setWarning(new MinCountRuleWarningParametersSpec());
        }});
        defaultSettings.getTable().setVolume(tableVolume);

        TableSchemaProfilingChecksSpec tableSchema = new TableSchemaProfilingChecksSpec();
        tableSchema.setProfileExpectedColumnCount(new TableSchemaColumnCountCheckSpec());
        defaultSettings.getTable().setSchema(tableSchema);

        ColumnNullsProfilingChecksSpec columnNulls = new ColumnNullsProfilingChecksSpec();
        columnNulls.setProfileNullsCount(new ColumnNullsCountCheckSpec() {{
            setWarning(new MaxCountRule1ParametersSpec());
        }});
        columnNulls.setProfileNotNullsCount(new ColumnNotNullsCountCheckSpec() {{
            setWarning(new MinCountRule1ParametersSpec());
        }});
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
        return defaultSettings;
    }
}
