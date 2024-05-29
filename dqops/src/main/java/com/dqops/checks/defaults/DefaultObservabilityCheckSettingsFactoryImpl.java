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

import com.dqops.checks.column.checkspecs.anomaly.*;
import com.dqops.checks.column.checkspecs.datatype.ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.*;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaColumnExistsCheckSpec;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaTypeChangedCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctCountAnomalyDifferencingCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctCountAnomalyStationaryPartitionCheckSpec;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.uniqueness.ColumnUniquenessDailyMonitoringChecksSpec;
import com.dqops.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.anomaly.ColumnAnomalyDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.datatype.ColumnDatatypeDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.nulls.ColumnNullsDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.uniqueness.ColumnUniquenessDailyPartitionedChecksSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.monitoring.anomaly.ColumnAnomalyDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.datatype.ColumnDatatypeDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.schema.ColumnSchemaDailyMonitoringChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.table.checkspecs.availability.TableAvailabilityCheckSpec;
import com.dqops.checks.table.checkspecs.schema.*;
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataStalenessCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountAnomalyDifferencingCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountAnomalyStationaryPartitionCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountChangeCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.timeliness.TableTimelinessDailyMonitoringChecksSpec;
import com.dqops.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TablePartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.volume.TableVolumeDailyPartitionedChecksSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableSchemaProfilingChecksSpec;
import com.dqops.checks.table.profiling.TableTimelinessProfilingChecksSpec;
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
     * Create an initial configuration of table-level checks.
     *
     * @return The configuration of the default table level checks.
     */
    @Override
    public TableDefaultChecksPatternSpec createDefaultTableChecks() {
        TableDefaultChecksPatternSpec defaultPattern = new TableDefaultChecksPatternSpec();
        defaultPattern.setPriority(DEFAULT_PATTERNS_PRIORITY);
        defaultPattern.setDescription("The default configuration of data quality checks that are always activated. This configuration enables the volume monitoring, anomaly monitoring, timeliness and schema change checks.");

        TableProfilingCheckCategoriesSpec profilingChecks = new TableProfilingCheckCategoriesSpec();
        defaultPattern.setProfilingChecks(profilingChecks);
        TableVolumeProfilingChecksSpec tableVolumeProfiling = new TableVolumeProfilingChecksSpec();
        profilingChecks.setVolume(tableVolumeProfiling);
        tableVolumeProfiling.setProfileRowCount(new TableRowCountCheckSpec() {{
            setWarning(new MinCountRule1ParametersSpec());
        }});

        TableSchemaProfilingChecksSpec tableSchema = new TableSchemaProfilingChecksSpec();
        tableSchema.setProfileColumnCount(new TableSchemaColumnCountCheckSpec());
        profilingChecks.setSchema(tableSchema);

        TableTimelinessProfilingChecksSpec timelinessProfiling = new TableTimelinessProfilingChecksSpec();
        timelinessProfiling.setProfileDataFreshness(new TableDataFreshnessCheckSpec());
        profilingChecks.setTimeliness(timelinessProfiling);


        TableMonitoringCheckCategoriesSpec monitoringChecks = new TableMonitoringCheckCategoriesSpec();
        defaultPattern.setMonitoringChecks(monitoringChecks);
        TableDailyMonitoringCheckCategoriesSpec dailyMonitoring = new TableDailyMonitoringCheckCategoriesSpec();
        monitoringChecks.setDaily(dailyMonitoring);

        TableAvailabilityDailyMonitoringChecksSpec tableAvailability = new TableAvailabilityDailyMonitoringChecksSpec();
        dailyMonitoring.setAvailability(tableAvailability);
        tableAvailability.setDailyTableAvailability(new TableAvailabilityCheckSpec() {{
            setWarning(new MaxFailuresRule0ParametersSpec());
        }});

        TableVolumeDailyMonitoringChecksSpec tableVolumeDailyMonitoring = new TableVolumeDailyMonitoringChecksSpec();
        dailyMonitoring.setVolume(tableVolumeDailyMonitoring);
        tableVolumeDailyMonitoring.setDailyRowCount(new TableRowCountCheckSpec() {{
            setWarning(new MinCountRule1ParametersSpec());
        }});
        tableVolumeDailyMonitoring.setDailyRowCountChange(new TableRowCountChangeCheckSpec() {{
            setWarning(new ChangePercentRule10ParametersSpec());
        }});
        tableVolumeDailyMonitoring.setDailyRowCountAnomaly(new TableRowCountAnomalyDifferencingCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});

        TableSchemaDailyMonitoringChecksSpec tableSchemaDailyMonitoring = new TableSchemaDailyMonitoringChecksSpec();
        dailyMonitoring.setSchema(tableSchemaDailyMonitoring);
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

        TableTimelinessDailyMonitoringChecksSpec timelinessDailyMonitoring = new TableTimelinessDailyMonitoringChecksSpec();
        dailyMonitoring.setTimeliness(timelinessDailyMonitoring);
        timelinessDailyMonitoring.setDailyDataFreshness(new TableDataFreshnessCheckSpec() {{
            setWarning(new MaxDaysRule1ParametersSpec(2.0));
        }});
        timelinessDailyMonitoring.setDailyDataStaleness(new TableDataStalenessCheckSpec() {{
            setWarning(new MaxDaysRule1ParametersSpec(2.0));
        }});


        TablePartitionedCheckCategoriesSpec partitionedChecks = new TablePartitionedCheckCategoriesSpec();
        defaultPattern.setPartitionedChecks(partitionedChecks);
        TableDailyPartitionedCheckCategoriesSpec dailyPartitioned = new TableDailyPartitionedCheckCategoriesSpec();
        partitionedChecks.setDaily(dailyPartitioned);

        TableVolumeDailyPartitionedChecksSpec volumeDailyPartitioned = new TableVolumeDailyPartitionedChecksSpec();
        dailyPartitioned.setVolume(volumeDailyPartitioned);
        volumeDailyPartitioned.setDailyPartitionRowCount(new TableRowCountCheckSpec());
        volumeDailyPartitioned.setDailyPartitionRowCountAnomaly(new TableRowCountAnomalyStationaryPartitionCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        volumeDailyPartitioned.setDailyPartitionRowCountChange(new TableRowCountChangeCheckSpec() {{
            setWarning(new ChangePercentRule10ParametersSpec());
        }});

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
        defaultPattern.setDescription("The default configuration of data quality checks that are always activated. This configuration enables the volume monitoring, anomaly monitoring, timeliness and schema change checks.");

        ColumnProfilingCheckCategoriesSpec defaultProfilingChecks = new ColumnProfilingCheckCategoriesSpec();
        defaultPattern.setProfilingChecks(defaultProfilingChecks);

        ColumnNullsProfilingChecksSpec columnNullsProfiling = new ColumnNullsProfilingChecksSpec();
        defaultProfilingChecks.setNulls(columnNullsProfiling);
        columnNullsProfiling.setProfileNullsCount(new ColumnNullsCountCheckSpec() {{
            setWarning(new MaxCountRule0WarningParametersSpec());
        }});
        columnNullsProfiling.setProfileNotNullsCount(new ColumnNotNullsCountCheckSpec() {{
            setWarning(new MinCountRule1ParametersSpec());
        }});
        columnNullsProfiling.setProfileNullsPercent(new ColumnNullsPercentCheckSpec());


        ColumnMonitoringCheckCategoriesSpec defaultMonitoringChecks = new ColumnMonitoringCheckCategoriesSpec();
        defaultPattern.setMonitoringChecks(defaultMonitoringChecks);
        ColumnDailyMonitoringCheckCategoriesSpec defaultDailyChecks = new ColumnDailyMonitoringCheckCategoriesSpec();
        defaultMonitoringChecks.setDaily(defaultDailyChecks);

        ColumnDatatypeDailyMonitoringChecksSpec columnDatatype = new ColumnDatatypeDailyMonitoringChecksSpec();
        defaultDailyChecks.setDatatype(columnDatatype);
        columnDatatype.setDailyDetectedDatatypeInTextChanged(new ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});

        ColumnAnomalyDailyMonitoringChecksSpec columnAnomalyDailyMonitoring = new ColumnAnomalyDailyMonitoringChecksSpec();
        defaultDailyChecks.setAnomaly(columnAnomalyDailyMonitoring);
        columnAnomalyDailyMonitoring.setDailySumAnomaly(new ColumnSumAnomalyDifferencingCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        columnAnomalyDailyMonitoring.setDailyMeanAnomaly(new ColumnMeanAnomalyStationaryCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});

        ColumnSchemaDailyMonitoringChecksSpec columnSchemaDailyMonitoring = new ColumnSchemaDailyMonitoringChecksSpec();
        defaultDailyChecks.setSchema(columnSchemaDailyMonitoring);
        columnSchemaDailyMonitoring.setDailyColumnExists(new ColumnSchemaColumnExistsCheckSpec() {{
            setWarning(new Equals1RuleParametersSpec());
        }});
        columnSchemaDailyMonitoring.setDailyColumnTypeChanged(new ColumnSchemaTypeChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});

        ColumnNullsDailyMonitoringChecksSpec columnNullsDailyMonitoring = new ColumnNullsDailyMonitoringChecksSpec();
        defaultDailyChecks.setNulls(columnNullsDailyMonitoring);
        columnNullsDailyMonitoring.setDailyNullsCount(new ColumnNullsCountCheckSpec());
        columnNullsDailyMonitoring.setDailyNullsPercent(new ColumnNullsPercentCheckSpec());
        columnNullsDailyMonitoring.setDailyNotNullsPercent(new ColumnNotNullsPercentCheckSpec());
        columnNullsDailyMonitoring.setDailyNullsPercentAnomaly(new ColumnNullPercentAnomalyStationaryCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        columnNullsDailyMonitoring.setDailyNullsPercentChange(new ColumnNullPercentChangeCheckSpec() {{
            setWarning(new ChangePercentRule10ParametersSpec());
        }});

        ColumnUniquenessDailyMonitoringChecksSpec columnUniquenessDailyMonitoring = new ColumnUniquenessDailyMonitoringChecksSpec();
        defaultDailyChecks.setUniqueness(columnUniquenessDailyMonitoring);
        columnUniquenessDailyMonitoring.setDailyDistinctCountAnomaly(new ColumnDistinctCountAnomalyDifferencingCheckSpec() {{
            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});

        ColumnPartitionedCheckCategoriesSpec partitionedChecks = new ColumnPartitionedCheckCategoriesSpec();
        defaultPattern.setPartitionedChecks(partitionedChecks);

        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitionedChecks = new ColumnDailyPartitionedCheckCategoriesSpec();
        partitionedChecks.setDaily(dailyPartitionedChecks);

        ColumnNullsDailyPartitionedChecksSpec dailyPartitionedNulls = new ColumnNullsDailyPartitionedChecksSpec();
        dailyPartitionedChecks.setNulls(dailyPartitionedNulls);

        dailyPartitionedNulls.setDailyPartitionNullsCount(new ColumnNullsCountCheckSpec());
        dailyPartitionedNulls.setDailyPartitionNullsPercent(new ColumnNullsPercentCheckSpec());
        dailyPartitionedNulls.setDailyPartitionNotNullsPercent(new ColumnNotNullsPercentCheckSpec());
        dailyPartitionedNulls.setDailyPartitionNullsPercentAnomaly(new ColumnNullPercentAnomalyStationaryCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});

        ColumnAnomalyDailyPartitionedChecksSpec anomalyDailyPartitioned = new ColumnAnomalyDailyPartitionedChecksSpec();
        dailyPartitionedChecks.setAnomaly(anomalyDailyPartitioned);
        anomalyDailyPartitioned.setDailyPartitionSumAnomaly(new ColumnSumAnomalyStationaryPartitionCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        anomalyDailyPartitioned.setDailyPartitionMeanAnomaly(new ColumnMeanAnomalyStationaryCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        anomalyDailyPartitioned.setDailyPartitionMinAnomaly(new ColumnMinAnomalyStationaryCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});
        anomalyDailyPartitioned.setDailyPartitionMaxAnomaly(new ColumnMaxAnomalyStationaryCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});

        ColumnUniquenessDailyPartitionedChecksSpec uniquenessDailyPartitioned = new ColumnUniquenessDailyPartitionedChecksSpec();
        dailyPartitionedChecks.setUniqueness(uniquenessDailyPartitioned);
        uniquenessDailyPartitioned.setDailyPartitionDistinctCountAnomaly(new ColumnDistinctCountAnomalyStationaryPartitionCheckSpec() {{
            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
        }});

        ColumnDatatypeDailyPartitionedChecksSpec dataTypeDailyPartitioned = new ColumnDatatypeDailyPartitionedChecksSpec();
        dailyPartitionedChecks.setDatatype(dataTypeDailyPartitioned);
        dataTypeDailyPartitioned.setDailyPartitionDetectedDatatypeInTextChanged(new ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec() {{
            setWarning(new ValueChangedRuleParametersSpec());
        }});

        return defaultPattern;
    }
}
