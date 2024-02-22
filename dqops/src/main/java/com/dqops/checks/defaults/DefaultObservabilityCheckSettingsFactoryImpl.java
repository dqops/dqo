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

package com.dqops.checks.defaults;

import com.dqops.checks.column.checkspecs.anomaly.ColumnSumAnomalyDifferencingCheckSpec;
import com.dqops.checks.column.checkspecs.anomaly.ColumnMeanAnomalyStationaryCheckSpec;
import com.dqops.checks.column.checkspecs.datatype.ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.*;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaColumnExistsCheckSpec;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaTypeChangedCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctCountAnomalyDifferencingCheckSpec;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.uniqueness.ColumnUniquenessDailyMonitoringChecksSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.monitoring.anomaly.ColumnAnomalyDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.datatype.ColumnDatatypeDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.schema.ColumnSchemaDailyMonitoringChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.table.checkspecs.availability.TableAvailabilityCheckSpec;
import com.dqops.checks.table.checkspecs.schema.*;
import com.dqops.checks.table.checkspecs.volume.TableRowCountAnomalyDifferencingCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountChangeCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableSchemaProfilingChecksSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.monitoring.availability.TableAvailabilityDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.schema.TableSchemaDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternSpec;
import com.dqops.rules.change.ChangePercent1DayRule10ParametersSpec;
import com.dqops.rules.change.ChangePercentRule10ParametersSpec;
import com.dqops.rules.comparison.*;
import com.dqops.rules.percentile.AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec;
import com.dqops.rules.percentile.AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec;
import org.springframework.stereotype.Component;

/**
 * Factory that creates the default configuration of checks, when DQOps is initialized and the initial configuration is loaded into the local settings.
 */
@Component
public class DefaultObservabilityCheckSettingsFactoryImpl implements DefaultObservabilityCheckSettingsFactory {
    /**
     * The priority level of the default checks.
     */
    public static final Integer DEFAULT_PATTERNS_PRIORITY = 1000;

    /**
     * Creates the default check settings to be stored in the local settings. This is an initial, default configuration.
     * @return Default observability settings.
     */
    @Override
    public DefaultObservabilityChecksSpec createDefaultCheckSettings() {
        DefaultObservabilityChecksSpec defaultSettings = new DefaultObservabilityChecksSpec();

        return defaultSettings;
    }

    /**
     * Create an initial configuration of table-level checks.
     *
     * @return The configuration of the default table level checks.
     */
    @Override
    public TableDefaultChecksPatternSpec createDefaultTableChecks() {
        TableDefaultChecksPatternSpec defaultPattern = new TableDefaultChecksPatternSpec();
        defaultPattern.setPriority(DEFAULT_PATTERNS_PRIORITY);

        TableProfilingCheckCategoriesSpec profilingChecks = new TableProfilingCheckCategoriesSpec();
        defaultPattern.setProfilingChecks(profilingChecks);
        TableVolumeProfilingChecksSpec tableVolumeProfiling = new TableVolumeProfilingChecksSpec();
        tableVolumeProfiling.setProfileRowCount(new TableRowCountCheckSpec() {{
            setWarning(new MinCountRule1ParametersSpec());
        }});
        profilingChecks.setVolume(tableVolumeProfiling);

        TableSchemaProfilingChecksSpec tableSchema = new TableSchemaProfilingChecksSpec();
        tableSchema.setProfileColumnCount(new TableSchemaColumnCountCheckSpec());
        profilingChecks.setSchema(tableSchema);



        TableMonitoringCheckCategoriesSpec monitoringChecks = new TableMonitoringCheckCategoriesSpec();
        defaultPattern.setMonitoringChecks(monitoringChecks);
        TableDailyMonitoringCheckCategoriesSpec dailyMonitoring = new TableDailyMonitoringCheckCategoriesSpec();
        monitoringChecks.setDaily(dailyMonitoring);

        TableAvailabilityDailyMonitoringChecksSpec tableAvailability = new TableAvailabilityDailyMonitoringChecksSpec();
        tableAvailability.setDailyTableAvailability(new TableAvailabilityCheckSpec() {{
            setWarning(new MaxFailuresRule0ParametersSpec());
        }});
        dailyMonitoring.setAvailability(tableAvailability);

        TableVolumeDailyMonitoringChecksSpec tableVolumeDailyMonitoring = new TableVolumeDailyMonitoringChecksSpec();
        tableVolumeDailyMonitoring.setDailyRowCount(new TableRowCountCheckSpec() {{
            setWarning(new MinCountRule1ParametersSpec());
        }});
        tableVolumeDailyMonitoring.setDailyRowCountChange(new TableRowCountChangeCheckSpec() {{
            setWarning(new ChangePercentRule10ParametersSpec());
        }});
        tableVolumeDailyMonitoring.setDailyRowCountAnomaly(new TableRowCountAnomalyDifferencingCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        dailyMonitoring.setVolume(tableVolumeDailyMonitoring);

        TableSchemaDailyMonitoringChecksSpec tableSchemaDailyMonitoring = new TableSchemaDailyMonitoringChecksSpec();
        tableSchemaDailyMonitoring.setDailyColumnCount(new TableSchemaColumnCountCheckSpec());
        tableSchemaDailyMonitoring.setDailyColumnCountChanged(new TableSchemaColumnCountChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        tableSchemaDailyMonitoring.setDailyColumnListChanged(new TableSchemaColumnListChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        tableSchemaDailyMonitoring.setDailyColumnListOrOrderChanged(new TableSchemaColumnListOrOrderChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        tableSchemaDailyMonitoring.setDailyColumnTypesChanged(new TableSchemaColumnTypesChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        dailyMonitoring.setSchema(tableSchemaDailyMonitoring);

        return defaultPattern;
    }

    /**
     * Create an initial configuration of column-level checks.
     *
     * @return The configuration of the default column level checks.
     */
    @Override
    public ColumnDefaultChecksPatternSpec createDefaultColumnChecks() {
        ColumnDefaultChecksPatternSpec defaultPattern = new ColumnDefaultChecksPatternSpec();
        defaultPattern.setPriority(DEFAULT_PATTERNS_PRIORITY);

        ColumnProfilingCheckCategoriesSpec defaultProfilingChecks = new ColumnProfilingCheckCategoriesSpec();
        defaultPattern.setProfilingChecks(defaultProfilingChecks);

        ColumnNullsProfilingChecksSpec columnNullsProfiling = new ColumnNullsProfilingChecksSpec();
        columnNullsProfiling.setProfileNullsCount(new ColumnNullsCountCheckSpec() {{
            setWarning(new MaxCountRule0WarningParametersSpec());
        }});
        columnNullsProfiling.setProfileNotNullsCount(new ColumnNotNullsCountCheckSpec() {{
            setWarning(new MinCountRule1ParametersSpec());
        }});
        columnNullsProfiling.setProfileNullsPercent(new ColumnNullsPercentCheckSpec());
        defaultProfilingChecks.setNulls(columnNullsProfiling);



        ColumnMonitoringCheckCategoriesSpec defaultMonitoringChecks = new ColumnMonitoringCheckCategoriesSpec();
        defaultPattern.setMonitoringChecks(defaultMonitoringChecks);
        ColumnDailyMonitoringCheckCategoriesSpec defaultDailyChecks = new ColumnDailyMonitoringCheckCategoriesSpec();
        defaultMonitoringChecks.setDaily(defaultDailyChecks);

        ColumnDatatypeDailyMonitoringChecksSpec columnDatatype = new ColumnDatatypeDailyMonitoringChecksSpec();
        columnDatatype.setDailyDetectedDatatypeInTextChanged(new ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        defaultDailyChecks.setDatatype(columnDatatype);

        ColumnAnomalyDailyMonitoringChecksSpec columnAnomalyDailyMonitoring = new ColumnAnomalyDailyMonitoringChecksSpec();
        columnAnomalyDailyMonitoring.setDailySumAnomaly(new ColumnSumAnomalyDifferencingCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        columnAnomalyDailyMonitoring.setDailyMeanAnomaly(new ColumnMeanAnomalyStationaryCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        defaultDailyChecks.setAnomaly(columnAnomalyDailyMonitoring);

        ColumnSchemaDailyMonitoringChecksSpec columnSchemaDailyMonitoring = new ColumnSchemaDailyMonitoringChecksSpec();
        columnSchemaDailyMonitoring.setDailyColumnExists(new ColumnSchemaColumnExistsCheckSpec() {{
            setWarning(new Equals1RuleParametersSpec());
        }});
        columnSchemaDailyMonitoring.setDailyColumnTypeChanged(new ColumnSchemaTypeChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});
        defaultDailyChecks.setSchema(columnSchemaDailyMonitoring);

        ColumnNullsDailyMonitoringChecksSpec columnNullsDailyMonitoring = new ColumnNullsDailyMonitoringChecksSpec();
        columnNullsDailyMonitoring.setDailyNullsCount(new ColumnNullsCountCheckSpec());
        columnNullsDailyMonitoring.setDailyNotNullsCount(new ColumnNotNullsCountCheckSpec() {{
            setWarning(new MinCountRule1ParametersSpec());
        }});
        columnNullsDailyMonitoring.setDailyNullsPercent(new ColumnNullsPercentCheckSpec());
        columnNullsDailyMonitoring.setDailyNotNullsPercent(new ColumnNotNullsPercentCheckSpec());
        columnNullsDailyMonitoring.setDailyNullsPercentAnomaly(new ColumnNullPercentAnomalyStationaryCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        columnNullsDailyMonitoring.setDailyNullsPercentChange1Day(new ColumnNullPercentChange1DayCheckSpec() {{
            setWarning(new ChangePercent1DayRule10ParametersSpec());
        }});
        defaultDailyChecks.setNulls(columnNullsDailyMonitoring);

        ColumnUniquenessDailyMonitoringChecksSpec columnUniquenessDailyMonitoring = new ColumnUniquenessDailyMonitoringChecksSpec();
        columnUniquenessDailyMonitoring.setDailyDistinctCountAnomaly(new ColumnDistinctCountAnomalyDifferencingCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        defaultDailyChecks.setUniqueness(columnUniquenessDailyMonitoring);

        return defaultPattern;
    }
}
