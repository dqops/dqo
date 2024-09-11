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
import com.dqops.checks.column.checkspecs.pii.ColumnPiiContainsEmailPercentCheckSpec;
import com.dqops.checks.column.checkspecs.pii.ColumnPiiContainsUsaPhonePercentCheckSpec;
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
import com.dqops.checks.column.profiling.ColumnPiiProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.table.checkspecs.availability.TableAvailabilityCheckSpec;
import com.dqops.checks.table.checkspecs.schema.*;
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessAnomalyCheckSpec;
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
import com.dqops.metadata.policies.column.ColumnQualityPolicySpec;
import com.dqops.metadata.policies.table.TableQualityPolicySpec;
import com.dqops.rules.change.ChangePercentRule10ParametersSpec;
import com.dqops.rules.comparison.*;
import com.dqops.rules.percentile.AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec;
import com.dqops.rules.percentile.AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory that creates the default configuration of checks, when DQOps is initialized and the initial configuration is loaded into the local settings.
 */
@Component
public class DefaultObservabilityCheckSettingsFactoryImpl implements DefaultObservabilityCheckSettingsFactory {
    /**
     * Create an initial configuration of table-level checks.
     *
     * @return The configuration of the default table level checks.
     */
    @Override
    public List<TableQualityPolicySpec> createDefaultTableQualityPolicies() {
        List<TableQualityPolicySpec> policies = new ArrayList<>();

        policies.add(new TableQualityPolicySpec() {{
            setPolicyName("Detect empty tables daily");
            setDescription("Detects empty tables using daily monitoring checks.");
            setMonitoringChecks(new TableMonitoringCheckCategoriesSpec() {{
                setDaily(new TableDailyMonitoringCheckCategoriesSpec() {{
                    setVolume(new TableVolumeDailyMonitoringChecksSpec() {{
                        setDailyRowCount(new TableRowCountCheckSpec() {{
                            setWarning(new MinCountRule1ParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new TableQualityPolicySpec() {{
            setPolicyName("Detect data freshness anomalies daily");
            setDescription("Monitors data freshness anomalies daily");
            setMonitoringChecks(new TableMonitoringCheckCategoriesSpec() {{
                setDaily(new TableDailyMonitoringCheckCategoriesSpec() {{
                    setTimeliness(new TableTimelinessDailyMonitoringChecksSpec() {{
                        setDailyDataFreshnessAnomaly(new TableDataFreshnessAnomalyCheckSpec() {{
                            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new TableQualityPolicySpec() {{
            setPolicyName("Detect table availability issues daily");
            setDescription("Monitors table availability issues daily");
            setMonitoringChecks(new TableMonitoringCheckCategoriesSpec() {{
                setDaily(new TableDailyMonitoringCheckCategoriesSpec() {{
                    setAvailability(new TableAvailabilityDailyMonitoringChecksSpec() {{
                        setDailyTableAvailability(new TableAvailabilityCheckSpec() {{
                            setWarning(new MaxFailuresRule0ParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new TableQualityPolicySpec() {{
            setPolicyName("Detect data volume anomalies");
            setDescription("Monitors data volume of the whole table (using daily monitoring checks) and for each daily partition, using daily partition checks.");
            setMonitoringChecks(new TableMonitoringCheckCategoriesSpec() {{
                setDaily(new TableDailyMonitoringCheckCategoriesSpec() {{
                    setVolume(new TableVolumeDailyMonitoringChecksSpec() {{
                        setDailyRowCountAnomaly(new TableRowCountAnomalyDifferencingCheckSpec() {{
                            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
            setPartitionedChecks(new TablePartitionedCheckCategoriesSpec() {{
                setDaily(new TableDailyPartitionedCheckCategoriesSpec() {{
                    setVolume(new TableVolumeDailyPartitionedChecksSpec() {{
                        setDailyPartitionRowCountAnomaly(new TableRowCountAnomalyStationaryPartitionCheckSpec() {{
                            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new TableQualityPolicySpec() {{
            setPolicyName("Detect big day-to-day data volume changes");
            setDescription("Monitors data volume of the whole table daily and raises an issue when the volume has increased of decreased significantly.");
            setMonitoringChecks(new TableMonitoringCheckCategoriesSpec() {{
                setDaily(new TableDailyMonitoringCheckCategoriesSpec() {{
                    setVolume(new TableVolumeDailyMonitoringChecksSpec() {{
                        setDailyRowCountChange(new TableRowCountChangeCheckSpec() {{
                            setWarning(new ChangePercentRule10ParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new TableQualityPolicySpec() {{
            setPolicyName("Detect table schema changes");
            setDescription("Monitors the table schema and raises issues when the schema of the table was changed.");
            setMonitoringChecks(new TableMonitoringCheckCategoriesSpec() {{
                setDaily(new TableDailyMonitoringCheckCategoriesSpec() {{
                    setSchema(new TableSchemaDailyMonitoringChecksSpec() {{
                        setDailyColumnCountChanged(new TableSchemaColumnCountChangedCheckSpec() {{
                            setWarning(new ValueChangedRuleParametersSpec());
                        }});
                        setDailyColumnListChanged(new TableSchemaColumnListChangedCheckSpec() {{
                            setWarning(new ValueChangedRuleParametersSpec());
                        }});
                        setDailyColumnListOrOrderChanged(new TableSchemaColumnListOrOrderChangedCheckSpec() {{
                            setWarning(new ValueChangedRuleParametersSpec());
                        }});
                        setDailyColumnTypesChanged(new TableSchemaColumnTypesChangedCheckSpec() {{
                            setWarning(new ValueChangedRuleParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new TableQualityPolicySpec() {{
            setPolicyName("Track volume of daily partitions");
            setDescription("Monitors volume (row count) of daily partitions.");
            setPartitionedChecks(new TablePartitionedCheckCategoriesSpec() {{
                setDaily(new TableDailyPartitionedCheckCategoriesSpec() {{
                    setVolume(new TableVolumeDailyPartitionedChecksSpec() {{
                        setDailyPartitionRowCount(new TableRowCountCheckSpec()); // no rules
                    }});
                }});
            }});
        }});


        return policies;
    }

    /**
     * Create an initial configuration of column-level checks.
     *
     * @return The configuration of the default column level checks.
     */
    @Override
    public List<ColumnQualityPolicySpec> createDefaultColumnQualityPolicies() {
        List<ColumnQualityPolicySpec> policies = new ArrayList<>();

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect empty columns");
            setDescription("Detects empty columns using both monitoring checks an daily partitioned checks");
            setDisabled(true);
            setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                    setNulls(new ColumnNullsDailyMonitoringChecksSpec() {{
                        setDailyEmptyColumnFound(new ColumnEmptyColumnFoundCheckSpec() {{
                            setWarning(new MinCountRuleConstant1ParametersSpec());
                        }});
                    }});
                }});
            }});

            setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec() {{
                setDaily(new ColumnDailyPartitionedCheckCategoriesSpec() {{
                    setNulls(new ColumnNullsDailyPartitionedChecksSpec() {{
                        setDailyPartitionEmptyColumnFound(new ColumnEmptyColumnFoundCheckSpec() {{
                            setWarning(new MinCountRuleConstant1ParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect columns with any null values");
            setDescription("Detects columns containing any null values using both monitoring checks an daily partitioned checks");
            setDisabled(true);
            setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                    setNulls(new ColumnNullsDailyMonitoringChecksSpec() {{
                        setDailyNullsCount(new ColumnNullsCountCheckSpec() {{
                            setWarning(new MaxCountRule0WarningParametersSpec());
                        }});
                    }});
                }});
            }});

            setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec() {{
                setDaily(new ColumnDailyPartitionedCheckCategoriesSpec() {{
                    setNulls(new ColumnNullsDailyPartitionedChecksSpec() {{
                        setDailyPartitionNullsCount(new ColumnNullsCountCheckSpec() {{
                            setWarning(new MaxCountRule0WarningParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect change of the data type of values in text columns");
            setDescription("Detects when the values stored in a text column change their type. This policy should be activated on raw tables in the landing zones for table that store all values (also numeric an dates) in text columns.");
            setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                    setDatatype(new ColumnDatatypeDailyMonitoringChecksSpec() {{
                        setDailyDetectedDatatypeInTextChanged(new ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec() {{
                            setWarning(new ValueChangedRuleParametersSpec());
                        }});
                    }});
                }});
            }});
            setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec() {{
                setDaily(new ColumnDailyPartitionedCheckCategoriesSpec() {{
                    setDatatype(new ColumnDatatypeDailyPartitionedChecksSpec() {{
                        setDailyPartitionDetectedDatatypeInTextChanged(new ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec() {{
                            setWarning(new ValueChangedRuleParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Profile text columns to detect PII values (sensitive data)");
            setDescription("Activates data profiling checks on all text columns to detect if they contain sensitive data (emails, phone numbers). This policy should be enabled to allow the data quality rule miner to configure PII checks when a few sensitive values were detected.");
            setDisabled(false);
            setProfilingChecks(new ColumnProfilingCheckCategoriesSpec() {{
                setPii(new ColumnPiiProfilingChecksSpec() {{
                    setProfileContainsEmailPercent(new ColumnPiiContainsEmailPercentCheckSpec());
                    setProfileContainsUsaPhonePercent(new ColumnPiiContainsUsaPhonePercentCheckSpec());
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect anomalies in the sum and average of numeric values");
            setDescription("Monitors the sum and average (mean) aggregated values of numeric values and raises a data quality issue when the value changes too much day-to-day.");
            setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                    setAnomaly(new ColumnAnomalyDailyMonitoringChecksSpec() {{
                        setDailyMeanAnomaly(new ColumnMeanAnomalyStationaryCheckSpec() {{
                            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                        setDailySumAnomaly(new ColumnSumAnomalyDifferencingCheckSpec() {{
                            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect anomalies in the sum and average of numeric values at a partition level");
            setDescription("Monitors the sum and average (mean) aggregated values of numeric values and raises a data quality issue when the value changes too much between daily partitions.");
            setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec() {{
                setDaily(new ColumnDailyPartitionedCheckCategoriesSpec() {{
                    setAnomaly(new ColumnAnomalyDailyPartitionedChecksSpec() {{
                        setDailyPartitionMeanAnomaly(new ColumnMeanAnomalyStationaryCheckSpec() {{
                            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                        setDailyPartitionSumAnomaly(new ColumnSumAnomalyStationaryPartitionCheckSpec() {{
                            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect column schema changes");
            setDescription("Monitors the schema of columns registered in DQOps. Raises a data quality issue when the column is missing, or its data has changed.");
            setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                    setSchema(new ColumnSchemaDailyMonitoringChecksSpec() {{
                        setDailyColumnExists(new ColumnSchemaColumnExistsCheckSpec() {{
                            setWarning(new Equals1RuleParametersSpec());
                        }});
                        setDailyColumnTypeChanged(new ColumnSchemaTypeChangedCheckSpec() {{
                            setWarning(new ValueChangedRuleParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect anomalies in the percentage of null values");
            setDescription("Monitors the scale of null values in columns and raises an issue when the day-to-day change is significant.");
            setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                    setNulls(new ColumnNullsDailyMonitoringChecksSpec() {{
                        setDailyNullsPercentAnomaly(new ColumnNullPercentAnomalyStationaryCheckSpec() {{
                            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
            setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec() {{
                setDaily(new ColumnDailyPartitionedCheckCategoriesSpec() {{
                    setNulls(new ColumnNullsDailyPartitionedChecksSpec() {{
                        setDailyPartitionNullsPercentAnomaly(new ColumnNullPercentAnomalyStationaryCheckSpec() {{
                            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect significant changes in the percentage of null values");
            setDescription("Monitors the percentage of null values in columns and raises an issue when the day-to-day change above a threshold.");
            setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                    setNulls(new ColumnNullsDailyMonitoringChecksSpec() {{
                        setDailyNullsPercentChange(new ColumnNullPercentChangeCheckSpec() {{
                            setWarning(new ChangePercentRule10ParametersSpec());
                        }});
                    }});
                }});
            }});
            setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec() {{
                setDaily(new ColumnDailyPartitionedCheckCategoriesSpec() {{
                    setNulls(new ColumnNullsDailyPartitionedChecksSpec() {{
                        setDailyPartitionNullsPercentChange(new ColumnNullPercentChangeCheckSpec() {{
                            setWarning(new ChangePercentRule10ParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect anomalies in the number of distinct values");
            setDescription("Monitors the number of distinct values in a column and raises an issue when an anomaly is detected.");
            setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                    setUniqueness(new ColumnUniquenessDailyMonitoringChecksSpec() {{
                        setDailyDistinctCountAnomaly(new ColumnDistinctCountAnomalyDifferencingCheckSpec() {{
                            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
            setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec() {{
                setDaily(new ColumnDailyPartitionedCheckCategoriesSpec() {{
                    setUniqueness(new ColumnUniquenessDailyPartitionedChecksSpec() {{
                        setDailyPartitionDistinctCountAnomaly(new ColumnDistinctCountAnomalyStationaryPartitionCheckSpec() {{
                            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect outliers in numeric values");
            setDescription("Monitors numeric columns to detect new smallest (min) or biggest (max) value, which must be an anomaly.");
            setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                    setAnomaly(new ColumnAnomalyDailyMonitoringChecksSpec() {{
                        setDailyMinAnomaly(new ColumnMinAnomalyDifferencingCheckSpec() {{
                            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                        setDailyMaxAnomaly(new ColumnMaxAnomalyDifferencingCheckSpec() {{
                            setWarning(new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Detect outliers in numeric values across daily partitions");
            setDescription("Monitors numeric columns to detect new smallest (min) or biggest (max) value for each daily partition. Raises a data quality issue when the partition contains a big or small value that exceeds regular ranges.");
            setDisabled(true);
            setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec() {{
                setDaily(new ColumnDailyPartitionedCheckCategoriesSpec() {{
                    setAnomaly(new ColumnAnomalyDailyPartitionedChecksSpec() {{
                        setDailyPartitionMinAnomaly(new ColumnMinAnomalyStationaryCheckSpec() {{
                            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                        setDailyPartitionMaxAnomaly(new ColumnMaxAnomalyStationaryCheckSpec() {{
                            setWarning(new AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec());
                        }});
                    }});
                }});
            }});
        }});

        policies.add(new ColumnQualityPolicySpec() {{
            setPolicyName("Track the number and percentage of null values");
            setDescription("Monitors the number and the percentage of null values without raising data quality issues.");
            setPriority(ColumnQualityPolicySpec.DEFAULT_PATTERNS_PRIORITY * 2);
            setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                    setNulls(new ColumnNullsDailyMonitoringChecksSpec() {{
                        setDailyNullsCount(new ColumnNullsCountCheckSpec());
                        setDailyNullsPercent(new ColumnNullsPercentCheckSpec());
                    }});
                }});
            }});
            setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec() {{
                setDaily(new ColumnDailyPartitionedCheckCategoriesSpec() {{
                    setNulls(new ColumnNullsDailyPartitionedChecksSpec() {{
                        setDailyPartitionNullsCount(new ColumnNullsCountCheckSpec());
                        setDailyPartitionNullsPercent(new ColumnNullsPercentCheckSpec());
                    }});
                }});
            }});
        }});

        return policies;
    }
}
