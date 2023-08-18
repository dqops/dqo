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
import com.dqops.checks.column.checkspecs.nulls.ColumnAnomalyStationaryNullPercent30DaysCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnChangeNullPercentSinceYesterdayCheckSpec;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaColumnExistsCheckSpec;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaTypeChangedCheckSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnSchemaProfilingChecksSpec;
import com.dqops.checks.column.recurring.anomaly.ColumnAnomalyDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.datatype.ColumnDatatypeDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.nulls.ColumnNullsDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.schema.ColumnSchemaDailyRecurringChecksSpec;
import com.dqops.checks.defaults.DefaultDailyRecurringObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultMonthlyRecurringObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultProfilingObservabilityCheckSettingsSpec;
import com.dqops.checks.table.checkspecs.availability.TableAvailabilityCheckSpec;
import com.dqops.checks.table.checkspecs.schema.*;
import com.dqops.checks.table.checkspecs.volume.TableAnomalyDifferencingRowCount30DaysCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableChangeRowCountCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.profiling.TableAvailabilityProfilingChecksSpec;
import com.dqops.checks.table.profiling.TableSchemaProfilingChecksSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.recurring.availability.TableAvailabilityDailyRecurringChecksSpec;
import com.dqops.checks.table.recurring.schema.TableSchemaDailyRecurringChecksSpec;
import com.dqops.checks.table.recurring.volume.TableVolumeDailyRecurringChecksSpec;
import com.dqops.rules.change.ChangePercent1DayRule10ParametersSpec;
import com.dqops.rules.change.ChangePercentRule10ParametersSpec;
import com.dqops.rules.comparison.EqualsInteger1RuleParametersSpec;
import com.dqops.rules.comparison.MaxFailuresRule0ParametersSpec;
import com.dqops.rules.comparison.ValueChangedParametersSpec;
import com.dqops.rules.percentile.AnomalyDifferencingPercentileMovingAverage30DaysRule1ParametersSpec;
import com.dqops.rules.percentile.AnomalyDifferencingPercentileMovingAverageRule1ParametersSpec;
import com.dqops.rules.percentile.AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec;
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
        defaultSettings.setRecurringDaily(createDefaultDailyRecurringChecks());
        defaultSettings.setRecurringMonthly(createDefaultMonthlyRecurringChecks());

        return defaultSettings;
    }

    /**
     * Creates the default configuration of daily recurring checks.
     * @return The default configuration of daily recurring checks.
     */
    protected DefaultDailyRecurringObservabilityCheckSettingsSpec createDefaultDailyRecurringChecks() {
        DefaultDailyRecurringObservabilityCheckSettingsSpec defaultSettings = new DefaultDailyRecurringObservabilityCheckSettingsSpec();

        TableAvailabilityDailyRecurringChecksSpec tableAvailability = new TableAvailabilityDailyRecurringChecksSpec();
        tableAvailability.setDailyTableAvailability(new TableAvailabilityCheckSpec() {{
            setWarning(new MaxFailuresRule0ParametersSpec());
        }});
        defaultSettings.getTable().setAvailability(tableAvailability);

        TableVolumeDailyRecurringChecksSpec tableVolume = new TableVolumeDailyRecurringChecksSpec();
        tableVolume.setDailyRowCount(new TableRowCountCheckSpec()); // no rules, just monitoring
        tableVolume.setDailyRowCountChange(new TableChangeRowCountCheckSpec() {{
            setWarning(new ChangePercentRule10ParametersSpec());
        }});
        tableVolume.setDailyRowCountAnomalyDifferencing30Days(new TableAnomalyDifferencingRowCount30DaysCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverage30DaysRule1ParametersSpec());
        }});
        defaultSettings.getTable().setVolume(tableVolume);

        ColumnDatatypeDailyRecurringChecksSpec columnDatatype = new ColumnDatatypeDailyRecurringChecksSpec();
        columnDatatype.setDailyStringDatatypeChanged(new ColumnDatatypeStringDatatypeChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        defaultSettings.getColumn().setDatatype(columnDatatype);

        ColumnAnomalyDailyRecurringChecksSpec columnAnomaly = new ColumnAnomalyDailyRecurringChecksSpec();
        columnAnomaly.setDailySumAnomalyDifferencing(new ColumnAnomalyDifferencingSumCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRule1ParametersSpec());
        }});
        columnAnomaly.setDailyMeanAnomalyStationary(new ColumnAnomalyStationaryMeanCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRule1ParametersSpec());
        }});
        defaultSettings.getColumn().setAnomaly(columnAnomaly);

        TableSchemaDailyRecurringChecksSpec tableSchema = new TableSchemaDailyRecurringChecksSpec();
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

        ColumnSchemaDailyRecurringChecksSpec columnSchema = new ColumnSchemaDailyRecurringChecksSpec();
        columnSchema.setDailyColumnExists(new ColumnSchemaColumnExistsCheckSpec() {{
            setWarning(new EqualsInteger1RuleParametersSpec());
        }});
        columnSchema.setDailyColumnTypeChanged(new ColumnSchemaTypeChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        defaultSettings.getColumn().setSchema(columnSchema);

        ColumnNullsDailyRecurringChecksSpec columnNulls = new ColumnNullsDailyRecurringChecksSpec();
        columnNulls.setDailyNullsPercentAnomalyStationary30Days(new ColumnAnomalyStationaryNullPercent30DaysCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec());
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
        tableVolume.setProfileRowCount(new TableRowCountCheckSpec()); // no rules, just monitoring
        defaultSettings.getTable().setVolume(tableVolume);

        TableSchemaProfilingChecksSpec tableSchema = new TableSchemaProfilingChecksSpec();
        tableSchema.setProfileColumnCountChanged(new TableSchemaColumnCountChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        tableSchema.setProfileColumnListChanged(new TableSchemaColumnListChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        tableSchema.setProfileColumnListOrOrderChanged(new TableSchemaColumnListOrOrderChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
        tableSchema.setProfileColumnTypesChanged(new TableSchemaColumnTypesChangedCheckSpec() {{
            setWarning(new ValueChangedParametersSpec());
        }});
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
        columnNulls.setProfileNullsPercentAnomalyStationary30Days(new ColumnAnomalyStationaryNullPercent30DaysCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec());
        }});
        columnNulls.setProfileNullsPercentChangeYesterday(new ColumnChangeNullPercentSinceYesterdayCheckSpec() {{
            setWarning(new ChangePercent1DayRule10ParametersSpec());
        }});
        defaultSettings.getColumn().setNulls(columnNulls);

        return defaultSettings;
    }

    /**
     * Creates the default configuration of daily recurring checks.
     * @return The default configuration of daily recurring checks.
     */
    protected DefaultMonthlyRecurringObservabilityCheckSettingsSpec createDefaultMonthlyRecurringChecks() {
        DefaultMonthlyRecurringObservabilityCheckSettingsSpec defaultSettings = new DefaultMonthlyRecurringObservabilityCheckSettingsSpec();
        return defaultSettings;
    }
}
