""" Contains all the data models used in inputs/outputs """

from .authenticated_dashboard_model import AuthenticatedDashboardModel
from .between_floats_rule_parameters_spec import BetweenFloatsRuleParametersSpec
from .big_query_parameters_spec import BigQueryParametersSpec
from .big_query_parameters_spec_authentication_mode import (
    BigQueryParametersSpecAuthenticationMode,
)
from .bulk_check_disable_parameters import BulkCheckDisableParameters
from .bulk_check_disable_parameters_selected_tables_to_columns import (
    BulkCheckDisableParametersSelectedTablesToColumns,
)
from .change_percentile_moving_within_7_days_rule_parameters_spec import (
    ChangePercentileMovingWithin7DaysRuleParametersSpec,
)
from .change_percentile_moving_within_30_days_rule_parameters_spec import (
    ChangePercentileMovingWithin30DaysRuleParametersSpec,
)
from .change_percentile_moving_within_60_days_rule_parameters_spec import (
    ChangePercentileMovingWithin60DaysRuleParametersSpec,
)
from .check_basic_folder_model import CheckBasicFolderModel
from .check_basic_folder_model_folders import CheckBasicFolderModelFolders
from .check_basic_model import CheckBasicModel
from .check_model import CheckModel
from .check_result_detailed_single_model import CheckResultDetailedSingleModel
from .check_results_detailed_data_model import CheckResultsDetailedDataModel
from .check_results_overview_data_model import CheckResultsOverviewDataModel
from .check_results_overview_data_model_statuses_item import (
    CheckResultsOverviewDataModelStatusesItem,
)
from .check_search_filters import CheckSearchFilters
from .check_search_filters_check_target import CheckSearchFiltersCheckTarget
from .check_search_filters_check_type import CheckSearchFiltersCheckType
from .check_search_filters_time_scale import CheckSearchFiltersTimeScale
from .check_template import CheckTemplate
from .check_template_check_target import CheckTemplateCheckTarget
from .cloud_synchronization_folders_status_model import (
    CloudSynchronizationFoldersStatusModel,
)
from .cloud_synchronization_folders_status_model_checks import (
    CloudSynchronizationFoldersStatusModelChecks,
)
from .cloud_synchronization_folders_status_model_data_check_results import (
    CloudSynchronizationFoldersStatusModelDataCheckResults,
)
from .cloud_synchronization_folders_status_model_data_errors import (
    CloudSynchronizationFoldersStatusModelDataErrors,
)
from .cloud_synchronization_folders_status_model_data_incidents import (
    CloudSynchronizationFoldersStatusModelDataIncidents,
)
from .cloud_synchronization_folders_status_model_data_sensor_readouts import (
    CloudSynchronizationFoldersStatusModelDataSensorReadouts,
)
from .cloud_synchronization_folders_status_model_data_statistics import (
    CloudSynchronizationFoldersStatusModelDataStatistics,
)
from .cloud_synchronization_folders_status_model_rules import (
    CloudSynchronizationFoldersStatusModelRules,
)
from .cloud_synchronization_folders_status_model_sensors import (
    CloudSynchronizationFoldersStatusModelSensors,
)
from .cloud_synchronization_folders_status_model_sources import (
    CloudSynchronizationFoldersStatusModelSources,
)
from .collect_statistics_on_table_queue_job_parameters import (
    CollectStatisticsOnTableQueueJobParameters,
)
from .collect_statistics_on_table_queue_job_parameters_data_scope import (
    CollectStatisticsOnTableQueueJobParametersDataScope,
)
from .collect_statistics_queue_job_parameters import CollectStatisticsQueueJobParameters
from .collect_statistics_queue_job_parameters_data_scope import (
    CollectStatisticsQueueJobParametersDataScope,
)
from .collect_statistics_queue_job_result import CollectStatisticsQueueJobResult
from .column_accuracy_average_match_percent_check_spec import (
    ColumnAccuracyAverageMatchPercentCheckSpec,
)
from .column_accuracy_average_match_percent_sensor_parameters_spec import (
    ColumnAccuracyAverageMatchPercentSensorParametersSpec,
)
from .column_accuracy_daily_partitioned_checks_spec import (
    ColumnAccuracyDailyPartitionedChecksSpec,
)
from .column_accuracy_daily_recurring_checks_spec import (
    ColumnAccuracyDailyRecurringChecksSpec,
)
from .column_accuracy_max_match_percent_check_spec import (
    ColumnAccuracyMaxMatchPercentCheckSpec,
)
from .column_accuracy_max_match_percent_sensor_parameters_spec import (
    ColumnAccuracyMaxMatchPercentSensorParametersSpec,
)
from .column_accuracy_min_match_percent_check_spec import (
    ColumnAccuracyMinMatchPercentCheckSpec,
)
from .column_accuracy_min_match_percent_sensor_parameters_spec import (
    ColumnAccuracyMinMatchPercentSensorParametersSpec,
)
from .column_accuracy_monthly_partitioned_checks_spec import (
    ColumnAccuracyMonthlyPartitionedChecksSpec,
)
from .column_accuracy_monthly_recurring_checks_spec import (
    ColumnAccuracyMonthlyRecurringChecksSpec,
)
from .column_accuracy_not_null_count_match_percent_check_spec import (
    ColumnAccuracyNotNullCountMatchPercentCheckSpec,
)
from .column_accuracy_not_null_count_match_percent_sensor_parameters_spec import (
    ColumnAccuracyNotNullCountMatchPercentSensorParametersSpec,
)
from .column_accuracy_profiling_checks_spec import ColumnAccuracyProfilingChecksSpec
from .column_accuracy_total_sum_match_percent_check_spec import (
    ColumnAccuracyTotalSumMatchPercentCheckSpec,
)
from .column_accuracy_total_sum_match_percent_sensor_parameters_spec import (
    ColumnAccuracyTotalSumMatchPercentSensorParametersSpec,
)
from .column_anomaly_daily_partitioned_checks_spec import (
    ColumnAnomalyDailyPartitionedChecksSpec,
)
from .column_anomaly_daily_recurring_checks_spec import (
    ColumnAnomalyDailyRecurringChecksSpec,
)
from .column_anomaly_mean_7_days_check_spec import ColumnAnomalyMean7DaysCheckSpec
from .column_anomaly_mean_30_days_check_spec import ColumnAnomalyMean30DaysCheckSpec
from .column_anomaly_mean_60_days_check_spec import ColumnAnomalyMean60DaysCheckSpec
from .column_anomaly_mean_change_7_days_check_spec import (
    ColumnAnomalyMeanChange7DaysCheckSpec,
)
from .column_anomaly_mean_change_30_days_check_spec import (
    ColumnAnomalyMeanChange30DaysCheckSpec,
)
from .column_anomaly_mean_change_60_days_check_spec import (
    ColumnAnomalyMeanChange60DaysCheckSpec,
)
from .column_anomaly_median_7_days_check_spec import ColumnAnomalyMedian7DaysCheckSpec
from .column_anomaly_median_30_days_check_spec import ColumnAnomalyMedian30DaysCheckSpec
from .column_anomaly_median_60_days_check_spec import ColumnAnomalyMedian60DaysCheckSpec
from .column_anomaly_median_change_7_days_check_spec import (
    ColumnAnomalyMedianChange7DaysCheckSpec,
)
from .column_anomaly_median_change_30_days_check_spec import (
    ColumnAnomalyMedianChange30DaysCheckSpec,
)
from .column_anomaly_median_change_60_days_check_spec import (
    ColumnAnomalyMedianChange60DaysCheckSpec,
)
from .column_anomaly_monthly_partitioned_checks_spec import (
    ColumnAnomalyMonthlyPartitionedChecksSpec,
)
from .column_anomaly_monthly_recurring_checks_spec import (
    ColumnAnomalyMonthlyRecurringChecksSpec,
)
from .column_anomaly_profiling_checks_spec import ColumnAnomalyProfilingChecksSpec
from .column_anomaly_sum_7_days_check_spec import ColumnAnomalySum7DaysCheckSpec
from .column_anomaly_sum_30_days_check_spec import ColumnAnomalySum30DaysCheckSpec
from .column_anomaly_sum_60_days_check_spec import ColumnAnomalySum60DaysCheckSpec
from .column_anomaly_sum_change_7_days_check_spec import (
    ColumnAnomalySumChange7DaysCheckSpec,
)
from .column_anomaly_sum_change_30_days_check_spec import (
    ColumnAnomalySumChange30DaysCheckSpec,
)
from .column_anomaly_sum_change_60_days_check_spec import (
    ColumnAnomalySumChange60DaysCheckSpec,
)
from .column_basic_model import ColumnBasicModel
from .column_bool_daily_partitioned_checks_spec import (
    ColumnBoolDailyPartitionedChecksSpec,
)
from .column_bool_daily_recurring_checks_spec import ColumnBoolDailyRecurringChecksSpec
from .column_bool_false_percent_sensor_parameters_spec import (
    ColumnBoolFalsePercentSensorParametersSpec,
)
from .column_bool_monthly_partitioned_checks_spec import (
    ColumnBoolMonthlyPartitionedChecksSpec,
)
from .column_bool_monthly_recurring_checks_spec import (
    ColumnBoolMonthlyRecurringChecksSpec,
)
from .column_bool_profiling_checks_spec import ColumnBoolProfilingChecksSpec
from .column_bool_true_percent_sensor_parameters_spec import (
    ColumnBoolTruePercentSensorParametersSpec,
)
from .column_change_mean_check_spec import ColumnChangeMeanCheckSpec
from .column_change_mean_since_7_days_check_spec import (
    ColumnChangeMeanSince7DaysCheckSpec,
)
from .column_change_mean_since_30_days_check_spec import (
    ColumnChangeMeanSince30DaysCheckSpec,
)
from .column_change_mean_since_yesterday_check_spec import (
    ColumnChangeMeanSinceYesterdayCheckSpec,
)
from .column_change_median_check_spec import ColumnChangeMedianCheckSpec
from .column_change_median_since_7_days_check_spec import (
    ColumnChangeMedianSince7DaysCheckSpec,
)
from .column_change_median_since_30_days_check_spec import (
    ColumnChangeMedianSince30DaysCheckSpec,
)
from .column_change_median_since_yesterday_check_spec import (
    ColumnChangeMedianSinceYesterdayCheckSpec,
)
from .column_change_sum_check_spec import ColumnChangeSumCheckSpec
from .column_change_sum_since_7_days_check_spec import (
    ColumnChangeSumSince7DaysCheckSpec,
)
from .column_change_sum_since_30_days_check_spec import (
    ColumnChangeSumSince30DaysCheckSpec,
)
from .column_change_sum_since_yesterday_check_spec import (
    ColumnChangeSumSinceYesterdayCheckSpec,
)
from .column_column_exists_sensor_parameters_spec import (
    ColumnColumnExistsSensorParametersSpec,
)
from .column_column_type_hash_sensor_parameters_spec import (
    ColumnColumnTypeHashSensorParametersSpec,
)
from .column_consistency_daily_partitioned_checks_spec import (
    ColumnConsistencyDailyPartitionedChecksSpec,
)
from .column_consistency_daily_recurring_checks_spec import (
    ColumnConsistencyDailyRecurringChecksSpec,
)
from .column_consistency_date_match_format_percent_check_spec import (
    ColumnConsistencyDateMatchFormatPercentCheckSpec,
)
from .column_consistency_date_match_format_percent_sensor_parameters_spec import (
    ColumnConsistencyDateMatchFormatPercentSensorParametersSpec,
)
from .column_consistency_date_match_format_percent_sensor_parameters_spec_date_formats import (
    ColumnConsistencyDateMatchFormatPercentSensorParametersSpecDateFormats,
)
from .column_consistency_monthly_partitioned_checks_spec import (
    ColumnConsistencyMonthlyPartitionedChecksSpec,
)
from .column_consistency_monthly_recurring_checks_spec import (
    ColumnConsistencyMonthlyRecurringChecksSpec,
)
from .column_consistency_profiling_checks_spec import (
    ColumnConsistencyProfilingChecksSpec,
)
from .column_daily_partitioned_check_categories_spec import (
    ColumnDailyPartitionedCheckCategoriesSpec,
)
from .column_daily_partitioned_check_categories_spec_custom import (
    ColumnDailyPartitionedCheckCategoriesSpecCustom,
)
from .column_daily_recurring_check_categories_spec import (
    ColumnDailyRecurringCheckCategoriesSpec,
)
from .column_daily_recurring_check_categories_spec_custom import (
    ColumnDailyRecurringCheckCategoriesSpecCustom,
)
from .column_date_values_in_future_percent_check_spec import (
    ColumnDateValuesInFuturePercentCheckSpec,
)
from .column_datetime_daily_partitioned_checks_spec import (
    ColumnDatetimeDailyPartitionedChecksSpec,
)
from .column_datetime_daily_recurring_checks_spec import (
    ColumnDatetimeDailyRecurringChecksSpec,
)
from .column_datetime_date_values_in_future_percent_sensor_parameters_spec import (
    ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec,
)
from .column_datetime_monthly_partitioned_checks_spec import (
    ColumnDatetimeMonthlyPartitionedChecksSpec,
)
from .column_datetime_monthly_recurring_checks_spec import (
    ColumnDatetimeMonthlyRecurringChecksSpec,
)
from .column_datetime_profiling_checks_spec import ColumnDatetimeProfilingChecksSpec
from .column_datetime_value_in_range_date_percent_check_spec import (
    ColumnDatetimeValueInRangeDatePercentCheckSpec,
)
from .column_datetime_value_in_range_date_percent_sensor_parameters_spec import (
    ColumnDatetimeValueInRangeDatePercentSensorParametersSpec,
)
from .column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
from .column_duplicate_percent_check_spec import ColumnDuplicatePercentCheckSpec
from .column_expected_numbers_in_use_count_check_spec import (
    ColumnExpectedNumbersInUseCountCheckSpec,
)
from .column_expected_strings_in_top_values_count_check_spec import (
    ColumnExpectedStringsInTopValuesCountCheckSpec,
)
from .column_expected_strings_in_use_count_check_spec import (
    ColumnExpectedStringsInUseCountCheckSpec,
)
from .column_false_percent_check_spec import ColumnFalsePercentCheckSpec
from .column_integrity_daily_partitioned_checks_spec import (
    ColumnIntegrityDailyPartitionedChecksSpec,
)
from .column_integrity_daily_recurring_checks_spec import (
    ColumnIntegrityDailyRecurringChecksSpec,
)
from .column_integrity_foreign_key_match_percent_check_spec import (
    ColumnIntegrityForeignKeyMatchPercentCheckSpec,
)
from .column_integrity_foreign_key_match_percent_sensor_parameters_spec import (
    ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec,
)
from .column_integrity_foreign_key_not_match_count_check_spec import (
    ColumnIntegrityForeignKeyNotMatchCountCheckSpec,
)
from .column_integrity_foreign_key_not_match_count_sensor_parameters_spec import (
    ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec,
)
from .column_integrity_monthly_partitioned_checks_spec import (
    ColumnIntegrityMonthlyPartitionedChecksSpec,
)
from .column_integrity_monthly_recurring_checks_spec import (
    ColumnIntegrityMonthlyRecurringChecksSpec,
)
from .column_integrity_profiling_checks_spec import ColumnIntegrityProfilingChecksSpec
from .column_invalid_latitude_count_check_spec import (
    ColumnInvalidLatitudeCountCheckSpec,
)
from .column_invalid_longitude_count_check_spec import (
    ColumnInvalidLongitudeCountCheckSpec,
)
from .column_max_in_range_check_spec import ColumnMaxInRangeCheckSpec
from .column_mean_in_range_check_spec import ColumnMeanInRangeCheckSpec
from .column_median_in_range_check_spec import ColumnMedianInRangeCheckSpec
from .column_min_in_range_check_spec import ColumnMinInRangeCheckSpec
from .column_model import ColumnModel
from .column_monthly_partitioned_check_categories_spec import (
    ColumnMonthlyPartitionedCheckCategoriesSpec,
)
from .column_monthly_partitioned_check_categories_spec_custom import (
    ColumnMonthlyPartitionedCheckCategoriesSpecCustom,
)
from .column_monthly_recurring_check_categories_spec import (
    ColumnMonthlyRecurringCheckCategoriesSpec,
)
from .column_monthly_recurring_check_categories_spec_custom import (
    ColumnMonthlyRecurringCheckCategoriesSpecCustom,
)
from .column_negative_count_check_spec import ColumnNegativeCountCheckSpec
from .column_negative_percent_check_spec import ColumnNegativePercentCheckSpec
from .column_non_negative_count_check_spec import ColumnNonNegativeCountCheckSpec
from .column_non_negative_percent_check_spec import ColumnNonNegativePercentCheckSpec
from .column_not_nulls_count_check_spec import ColumnNotNullsCountCheckSpec
from .column_not_nulls_percent_check_spec import ColumnNotNullsPercentCheckSpec
from .column_nulls_count_check_spec import ColumnNullsCountCheckSpec
from .column_nulls_daily_partitioned_checks_spec import (
    ColumnNullsDailyPartitionedChecksSpec,
)
from .column_nulls_daily_recurring_checks_spec import (
    ColumnNullsDailyRecurringChecksSpec,
)
from .column_nulls_monthly_partitioned_checks_spec import (
    ColumnNullsMonthlyPartitionedChecksSpec,
)
from .column_nulls_monthly_recurring_checks_spec import (
    ColumnNullsMonthlyRecurringChecksSpec,
)
from .column_nulls_not_nulls_count_sensor_parameters_spec import (
    ColumnNullsNotNullsCountSensorParametersSpec,
)
from .column_nulls_not_nulls_count_statistics_collector_spec import (
    ColumnNullsNotNullsCountStatisticsCollectorSpec,
)
from .column_nulls_not_nulls_percent_sensor_parameters_spec import (
    ColumnNullsNotNullsPercentSensorParametersSpec,
)
from .column_nulls_not_nulls_percent_statistics_collector_spec import (
    ColumnNullsNotNullsPercentStatisticsCollectorSpec,
)
from .column_nulls_nulls_count_sensor_parameters_spec import (
    ColumnNullsNullsCountSensorParametersSpec,
)
from .column_nulls_nulls_count_statistics_collector_spec import (
    ColumnNullsNullsCountStatisticsCollectorSpec,
)
from .column_nulls_nulls_percent_sensor_parameters_spec import (
    ColumnNullsNullsPercentSensorParametersSpec,
)
from .column_nulls_nulls_percent_statistics_collector_spec import (
    ColumnNullsNullsPercentStatisticsCollectorSpec,
)
from .column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec
from .column_nulls_profiling_checks_spec import ColumnNullsProfilingChecksSpec
from .column_nulls_statistics_collectors_spec import ColumnNullsStatisticsCollectorsSpec
from .column_number_value_in_set_percent_check_spec import (
    ColumnNumberValueInSetPercentCheckSpec,
)
from .column_numeric_daily_partitioned_checks_spec import (
    ColumnNumericDailyPartitionedChecksSpec,
)
from .column_numeric_daily_recurring_checks_spec import (
    ColumnNumericDailyRecurringChecksSpec,
)
from .column_numeric_expected_numbers_in_use_count_sensor_parameters_spec import (
    ColumnNumericExpectedNumbersInUseCountSensorParametersSpec,
)
from .column_numeric_invalid_latitude_count_sensor_parameters_spec import (
    ColumnNumericInvalidLatitudeCountSensorParametersSpec,
)
from .column_numeric_invalid_longitude_count_sensor_parameters_spec import (
    ColumnNumericInvalidLongitudeCountSensorParametersSpec,
)
from .column_numeric_max_sensor_parameters_spec import (
    ColumnNumericMaxSensorParametersSpec,
)
from .column_numeric_mean_sensor_parameters_spec import (
    ColumnNumericMeanSensorParametersSpec,
)
from .column_numeric_median_sensor_parameters_spec import (
    ColumnNumericMedianSensorParametersSpec,
)
from .column_numeric_min_sensor_parameters_spec import (
    ColumnNumericMinSensorParametersSpec,
)
from .column_numeric_monthly_partitioned_checks_spec import (
    ColumnNumericMonthlyPartitionedChecksSpec,
)
from .column_numeric_monthly_recurring_checks_spec import (
    ColumnNumericMonthlyRecurringChecksSpec,
)
from .column_numeric_negative_count_sensor_parameters_spec import (
    ColumnNumericNegativeCountSensorParametersSpec,
)
from .column_numeric_negative_percent_sensor_parameters_spec import (
    ColumnNumericNegativePercentSensorParametersSpec,
)
from .column_numeric_non_negative_count_sensor_parameters_spec import (
    ColumnNumericNonNegativeCountSensorParametersSpec,
)
from .column_numeric_non_negative_percent_sensor_parameters_spec import (
    ColumnNumericNonNegativePercentSensorParametersSpec,
)
from .column_numeric_number_value_in_set_percent_sensor_parameters_spec import (
    ColumnNumericNumberValueInSetPercentSensorParametersSpec,
)
from .column_numeric_percentile_10_sensor_parameters_spec import (
    ColumnNumericPercentile10SensorParametersSpec,
)
from .column_numeric_percentile_25_sensor_parameters_spec import (
    ColumnNumericPercentile25SensorParametersSpec,
)
from .column_numeric_percentile_75_sensor_parameters_spec import (
    ColumnNumericPercentile75SensorParametersSpec,
)
from .column_numeric_percentile_90_sensor_parameters_spec import (
    ColumnNumericPercentile90SensorParametersSpec,
)
from .column_numeric_percentile_sensor_parameters_spec import (
    ColumnNumericPercentileSensorParametersSpec,
)
from .column_numeric_population_stddev_sensor_parameters_spec import (
    ColumnNumericPopulationStddevSensorParametersSpec,
)
from .column_numeric_population_variance_sensor_parameters_spec import (
    ColumnNumericPopulationVarianceSensorParametersSpec,
)
from .column_numeric_profiling_checks_spec import ColumnNumericProfilingChecksSpec
from .column_numeric_sample_stddev_sensor_parameters_spec import (
    ColumnNumericSampleStddevSensorParametersSpec,
)
from .column_numeric_sample_variance_sensor_parameters_spec import (
    ColumnNumericSampleVarianceSensorParametersSpec,
)
from .column_numeric_sum_sensor_parameters_spec import (
    ColumnNumericSumSensorParametersSpec,
)
from .column_numeric_valid_latitude_percent_sensor_parameters_spec import (
    ColumnNumericValidLatitudePercentSensorParametersSpec,
)
from .column_numeric_valid_longitude_percent_sensor_parameters_spec import (
    ColumnNumericValidLongitudePercentSensorParametersSpec,
)
from .column_numeric_value_above_max_value_count_sensor_parameters_spec import (
    ColumnNumericValueAboveMaxValueCountSensorParametersSpec,
)
from .column_numeric_value_above_max_value_percent_sensor_parameters_spec import (
    ColumnNumericValueAboveMaxValuePercentSensorParametersSpec,
)
from .column_numeric_value_below_min_value_count_sensor_parameters_spec import (
    ColumnNumericValueBelowMinValueCountSensorParametersSpec,
)
from .column_numeric_value_below_min_value_percent_sensor_parameters_spec import (
    ColumnNumericValueBelowMinValuePercentSensorParametersSpec,
)
from .column_numeric_values_in_range_integers_percent_sensor_parameters_spec import (
    ColumnNumericValuesInRangeIntegersPercentSensorParametersSpec,
)
from .column_numeric_values_in_range_numeric_percent_sensor_parameters_spec import (
    ColumnNumericValuesInRangeNumericPercentSensorParametersSpec,
)
from .column_partitioned_checks_root_spec import ColumnPartitionedChecksRootSpec
from .column_percentile_10_in_range_check_spec import ColumnPercentile10InRangeCheckSpec
from .column_percentile_25_in_range_check_spec import ColumnPercentile25InRangeCheckSpec
from .column_percentile_75_in_range_check_spec import ColumnPercentile75InRangeCheckSpec
from .column_percentile_90_in_range_check_spec import ColumnPercentile90InRangeCheckSpec
from .column_percentile_in_range_check_spec import ColumnPercentileInRangeCheckSpec
from .column_pii_contains_email_percent_check_spec import (
    ColumnPiiContainsEmailPercentCheckSpec,
)
from .column_pii_contains_email_percent_sensor_parameters_spec import (
    ColumnPiiContainsEmailPercentSensorParametersSpec,
)
from .column_pii_contains_ip_4_percent_check_spec import (
    ColumnPiiContainsIp4PercentCheckSpec,
)
from .column_pii_contains_ip_4_percent_sensor_parameters_spec import (
    ColumnPiiContainsIp4PercentSensorParametersSpec,
)
from .column_pii_contains_ip_6_percent_check_spec import (
    ColumnPiiContainsIp6PercentCheckSpec,
)
from .column_pii_contains_ip_6_percent_sensor_parameters_spec import (
    ColumnPiiContainsIp6PercentSensorParametersSpec,
)
from .column_pii_contains_usa_phone_percent_check_spec import (
    ColumnPiiContainsUsaPhonePercentCheckSpec,
)
from .column_pii_contains_usa_phone_percent_sensor_parameters_spec import (
    ColumnPiiContainsUsaPhonePercentSensorParametersSpec,
)
from .column_pii_contains_usa_zipcode_percent_check_spec import (
    ColumnPiiContainsUsaZipcodePercentCheckSpec,
)
from .column_pii_contains_usa_zipcode_percent_sensor_parameters_spec import (
    ColumnPiiContainsUsaZipcodePercentSensorParametersSpec,
)
from .column_pii_daily_partitioned_checks_spec import (
    ColumnPiiDailyPartitionedChecksSpec,
)
from .column_pii_daily_recurring_checks_spec import ColumnPiiDailyRecurringChecksSpec
from .column_pii_monthly_partitioned_checks_spec import (
    ColumnPiiMonthlyPartitionedChecksSpec,
)
from .column_pii_monthly_recurring_checks_spec import (
    ColumnPiiMonthlyRecurringChecksSpec,
)
from .column_pii_profiling_checks_spec import ColumnPiiProfilingChecksSpec
from .column_pii_valid_email_percent_check_spec import (
    ColumnPiiValidEmailPercentCheckSpec,
)
from .column_pii_valid_email_percent_sensor_parameters_spec import (
    ColumnPiiValidEmailPercentSensorParametersSpec,
)
from .column_pii_valid_ip_4_address_percent_check_spec import (
    ColumnPiiValidIp4AddressPercentCheckSpec,
)
from .column_pii_valid_ip_4_address_percent_sensor_parameters_spec import (
    ColumnPiiValidIp4AddressPercentSensorParametersSpec,
)
from .column_pii_valid_ip_6_address_percent_check_spec import (
    ColumnPiiValidIp6AddressPercentCheckSpec,
)
from .column_pii_valid_ip_6_address_percent_sensor_parameters_spec import (
    ColumnPiiValidIp6AddressPercentSensorParametersSpec,
)
from .column_pii_valid_usa_phone_percent_check_spec import (
    ColumnPiiValidUsaPhonePercentCheckSpec,
)
from .column_pii_valid_usa_phone_percent_sensor_parameters_spec import (
    ColumnPiiValidUsaPhonePercentSensorParametersSpec,
)
from .column_pii_valid_usa_zipcode_percent_check_spec import (
    ColumnPiiValidUsaZipcodePercentCheckSpec,
)
from .column_pii_valid_usa_zipcode_percent_sensor_parameters_spec import (
    ColumnPiiValidUsaZipcodePercentSensorParametersSpec,
)
from .column_population_stddev_in_range_check_spec import (
    ColumnPopulationStddevInRangeCheckSpec,
)
from .column_population_variance_in_range_check_spec import (
    ColumnPopulationVarianceInRangeCheckSpec,
)
from .column_profiling_check_categories_spec import ColumnProfilingCheckCategoriesSpec
from .column_profiling_check_categories_spec_custom import (
    ColumnProfilingCheckCategoriesSpecCustom,
)
from .column_range_max_value_sensor_parameters_spec import (
    ColumnRangeMaxValueSensorParametersSpec,
)
from .column_range_max_value_statistics_collector_spec import (
    ColumnRangeMaxValueStatisticsCollectorSpec,
)
from .column_range_median_value_statistics_collector_spec import (
    ColumnRangeMedianValueStatisticsCollectorSpec,
)
from .column_range_min_value_sensor_parameters_spec import (
    ColumnRangeMinValueSensorParametersSpec,
)
from .column_range_min_value_statistics_collector_spec import (
    ColumnRangeMinValueStatisticsCollectorSpec,
)
from .column_range_statistics_collectors_spec import ColumnRangeStatisticsCollectorsSpec
from .column_range_sum_value_statistics_collector_spec import (
    ColumnRangeSumValueStatisticsCollectorSpec,
)
from .column_recurring_checks_root_spec import ColumnRecurringChecksRootSpec
from .column_sample_stddev_in_range_check_spec import ColumnSampleStddevInRangeCheckSpec
from .column_sample_variance_in_range_check_spec import (
    ColumnSampleVarianceInRangeCheckSpec,
)
from .column_sampling_column_samples_sensor_parameters_spec import (
    ColumnSamplingColumnSamplesSensorParametersSpec,
)
from .column_sampling_column_samples_statistics_collector_spec import (
    ColumnSamplingColumnSamplesStatisticsCollectorSpec,
)
from .column_sampling_statistics_collectors_spec import (
    ColumnSamplingStatisticsCollectorsSpec,
)
from .column_schema_column_exists_check_spec import ColumnSchemaColumnExistsCheckSpec
from .column_schema_daily_recurring_checks_spec import (
    ColumnSchemaDailyRecurringChecksSpec,
)
from .column_schema_monthly_recurring_checks_spec import (
    ColumnSchemaMonthlyRecurringChecksSpec,
)
from .column_schema_profiling_checks_spec import ColumnSchemaProfilingChecksSpec
from .column_schema_type_changed_check_spec import ColumnSchemaTypeChangedCheckSpec
from .column_spec import ColumnSpec
from .column_sql_aggregate_expr_check_spec import ColumnSqlAggregateExprCheckSpec
from .column_sql_aggregated_expression_sensor_parameters_spec import (
    ColumnSqlAggregatedExpressionSensorParametersSpec,
)
from .column_sql_condition_failed_count_check_spec import (
    ColumnSqlConditionFailedCountCheckSpec,
)
from .column_sql_condition_failed_count_sensor_parameters_spec import (
    ColumnSqlConditionFailedCountSensorParametersSpec,
)
from .column_sql_condition_passed_percent_check_spec import (
    ColumnSqlConditionPassedPercentCheckSpec,
)
from .column_sql_condition_passed_percent_sensor_parameters_spec import (
    ColumnSqlConditionPassedPercentSensorParametersSpec,
)
from .column_sql_daily_partitioned_checks_spec import (
    ColumnSqlDailyPartitionedChecksSpec,
)
from .column_sql_daily_recurring_checks_spec import ColumnSqlDailyRecurringChecksSpec
from .column_sql_monthly_partitioned_checks_spec import (
    ColumnSqlMonthlyPartitionedChecksSpec,
)
from .column_sql_monthly_recurring_checks_spec import (
    ColumnSqlMonthlyRecurringChecksSpec,
)
from .column_sql_profiling_checks_spec import ColumnSqlProfilingChecksSpec
from .column_statistics_collectors_root_categories_spec import (
    ColumnStatisticsCollectorsRootCategoriesSpec,
)
from .column_statistics_model import ColumnStatisticsModel
from .column_string_boolean_placeholder_percent_check_spec import (
    ColumnStringBooleanPlaceholderPercentCheckSpec,
)
from .column_string_datatype_changed_check_spec import (
    ColumnStringDatatypeChangedCheckSpec,
)
from .column_string_datatype_detected_check_spec import (
    ColumnStringDatatypeDetectedCheckSpec,
)
from .column_string_empty_count_check_spec import ColumnStringEmptyCountCheckSpec
from .column_string_empty_percent_check_spec import ColumnStringEmptyPercentCheckSpec
from .column_string_invalid_email_count_check_spec import (
    ColumnStringInvalidEmailCountCheckSpec,
)
from .column_string_invalid_ip_4_address_count_check_spec import (
    ColumnStringInvalidIp4AddressCountCheckSpec,
)
from .column_string_invalid_ip_6_address_count_check_spec import (
    ColumnStringInvalidIp6AddressCountCheckSpec,
)
from .column_string_invalid_uuid_count_check_spec import (
    ColumnStringInvalidUuidCountCheckSpec,
)
from .column_string_length_above_max_length_count_check_spec import (
    ColumnStringLengthAboveMaxLengthCountCheckSpec,
)
from .column_string_length_above_max_length_percent_check_spec import (
    ColumnStringLengthAboveMaxLengthPercentCheckSpec,
)
from .column_string_length_below_min_length_count_check_spec import (
    ColumnStringLengthBelowMinLengthCountCheckSpec,
)
from .column_string_length_below_min_length_percent_check_spec import (
    ColumnStringLengthBelowMinLengthPercentCheckSpec,
)
from .column_string_length_in_range_percent_check_spec import (
    ColumnStringLengthInRangePercentCheckSpec,
)
from .column_string_match_date_regex_percent_check_spec import (
    ColumnStringMatchDateRegexPercentCheckSpec,
)
from .column_string_match_name_regex_percent_check_spec import (
    ColumnStringMatchNameRegexPercentCheckSpec,
)
from .column_string_match_regex_percent_check_spec import (
    ColumnStringMatchRegexPercentCheckSpec,
)
from .column_string_max_length_check_spec import ColumnStringMaxLengthCheckSpec
from .column_string_mean_length_check_spec import ColumnStringMeanLengthCheckSpec
from .column_string_min_length_check_spec import ColumnStringMinLengthCheckSpec
from .column_string_not_match_date_regex_count_check_spec import (
    ColumnStringNotMatchDateRegexCountCheckSpec,
)
from .column_string_not_match_regex_count_check_spec import (
    ColumnStringNotMatchRegexCountCheckSpec,
)
from .column_string_null_placeholder_count_check_spec import (
    ColumnStringNullPlaceholderCountCheckSpec,
)
from .column_string_null_placeholder_percent_check_spec import (
    ColumnStringNullPlaceholderPercentCheckSpec,
)
from .column_string_parsable_to_float_percent_check_spec import (
    ColumnStringParsableToFloatPercentCheckSpec,
)
from .column_string_parsable_to_integer_percent_check_spec import (
    ColumnStringParsableToIntegerPercentCheckSpec,
)
from .column_string_surrounded_by_whitespace_count_check_spec import (
    ColumnStringSurroundedByWhitespaceCountCheckSpec,
)
from .column_string_surrounded_by_whitespace_percent_check_spec import (
    ColumnStringSurroundedByWhitespacePercentCheckSpec,
)
from .column_string_valid_country_code_percent_check_spec import (
    ColumnStringValidCountryCodePercentCheckSpec,
)
from .column_string_valid_currency_code_percent_check_spec import (
    ColumnStringValidCurrencyCodePercentCheckSpec,
)
from .column_string_valid_dates_percent_check_spec import (
    ColumnStringValidDatesPercentCheckSpec,
)
from .column_string_valid_uuid_percent_check_spec import (
    ColumnStringValidUuidPercentCheckSpec,
)
from .column_string_value_in_set_percent_check_spec import (
    ColumnStringValueInSetPercentCheckSpec,
)
from .column_string_whitespace_count_check_spec import (
    ColumnStringWhitespaceCountCheckSpec,
)
from .column_string_whitespace_percent_check_spec import (
    ColumnStringWhitespacePercentCheckSpec,
)
from .column_strings_daily_partitioned_checks_spec import (
    ColumnStringsDailyPartitionedChecksSpec,
)
from .column_strings_daily_recurring_checks_spec import (
    ColumnStringsDailyRecurringChecksSpec,
)
from .column_strings_expected_strings_in_top_values_count_sensor_parameters_spec import (
    ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec,
)
from .column_strings_expected_strings_in_use_count_sensor_parameters_spec import (
    ColumnStringsExpectedStringsInUseCountSensorParametersSpec,
)
from .column_strings_monthly_partitioned_checks_spec import (
    ColumnStringsMonthlyPartitionedChecksSpec,
)
from .column_strings_monthly_recurring_checks_spec import (
    ColumnStringsMonthlyRecurringChecksSpec,
)
from .column_strings_profiling_checks_spec import ColumnStringsProfilingChecksSpec
from .column_strings_statistics_collectors_spec import (
    ColumnStringsStatisticsCollectorsSpec,
)
from .column_strings_string_boolean_placeholder_percent_sensor_parameters_spec import (
    ColumnStringsStringBooleanPlaceholderPercentSensorParametersSpec,
)
from .column_strings_string_datatype_detect_sensor_parameters_spec import (
    ColumnStringsStringDatatypeDetectSensorParametersSpec,
)
from .column_strings_string_datatype_detect_statistics_collector_spec import (
    ColumnStringsStringDatatypeDetectStatisticsCollectorSpec,
)
from .column_strings_string_empty_count_sensor_parameters_spec import (
    ColumnStringsStringEmptyCountSensorParametersSpec,
)
from .column_strings_string_empty_percent_sensor_parameters_spec import (
    ColumnStringsStringEmptyPercentSensorParametersSpec,
)
from .column_strings_string_invalid_email_count_sensor_parameters_spec import (
    ColumnStringsStringInvalidEmailCountSensorParametersSpec,
)
from .column_strings_string_invalid_ip_4_address_count_sensor_parameters_spec import (
    ColumnStringsStringInvalidIp4AddressCountSensorParametersSpec,
)
from .column_strings_string_invalid_ip_6_address_count_sensor_parameters_spec import (
    ColumnStringsStringInvalidIp6AddressCountSensorParametersSpec,
)
from .column_strings_string_invalid_uuid_count_sensor_parameters_spec import (
    ColumnStringsStringInvalidUuidCountSensorParametersSpec,
)
from .column_strings_string_length_above_max_length_count_sensor_parameters_spec import (
    ColumnStringsStringLengthAboveMaxLengthCountSensorParametersSpec,
)
from .column_strings_string_length_above_max_length_percent_sensor_parameters_spec import (
    ColumnStringsStringLengthAboveMaxLengthPercentSensorParametersSpec,
)
from .column_strings_string_length_below_min_length_count_sensor_parameters_spec import (
    ColumnStringsStringLengthBelowMinLengthCountSensorParametersSpec,
)
from .column_strings_string_length_below_min_length_percent_sensor_parameters_spec import (
    ColumnStringsStringLengthBelowMinLengthPercentSensorParametersSpec,
)
from .column_strings_string_length_in_range_percent_sensor_parameters_spec import (
    ColumnStringsStringLengthInRangePercentSensorParametersSpec,
)
from .column_strings_string_match_date_regex_percent_sensor_parameters_spec import (
    ColumnStringsStringMatchDateRegexPercentSensorParametersSpec,
)
from .column_strings_string_match_date_regex_percent_sensor_parameters_spec_date_formats import (
    ColumnStringsStringMatchDateRegexPercentSensorParametersSpecDateFormats,
)
from .column_strings_string_match_name_regex_percent_sensor_parameters_spec import (
    ColumnStringsStringMatchNameRegexPercentSensorParametersSpec,
)
from .column_strings_string_match_regex_percent_sensor_parameters_spec import (
    ColumnStringsStringMatchRegexPercentSensorParametersSpec,
)
from .column_strings_string_max_length_sensor_parameters_spec import (
    ColumnStringsStringMaxLengthSensorParametersSpec,
)
from .column_strings_string_max_length_statistics_collector_spec import (
    ColumnStringsStringMaxLengthStatisticsCollectorSpec,
)
from .column_strings_string_mean_length_sensor_parameters_spec import (
    ColumnStringsStringMeanLengthSensorParametersSpec,
)
from .column_strings_string_mean_length_statistics_collector_spec import (
    ColumnStringsStringMeanLengthStatisticsCollectorSpec,
)
from .column_strings_string_min_length_sensor_parameters_spec import (
    ColumnStringsStringMinLengthSensorParametersSpec,
)
from .column_strings_string_min_length_statistics_collector_spec import (
    ColumnStringsStringMinLengthStatisticsCollectorSpec,
)
from .column_strings_string_not_match_date_regex_count_sensor_parameters_spec import (
    ColumnStringsStringNotMatchDateRegexCountSensorParametersSpec,
)
from .column_strings_string_not_match_date_regex_count_sensor_parameters_spec_date_formats import (
    ColumnStringsStringNotMatchDateRegexCountSensorParametersSpecDateFormats,
)
from .column_strings_string_not_match_regex_count_sensor_parameters_spec import (
    ColumnStringsStringNotMatchRegexCountSensorParametersSpec,
)
from .column_strings_string_null_placeholder_count_sensor_parameters_spec import (
    ColumnStringsStringNullPlaceholderCountSensorParametersSpec,
)
from .column_strings_string_null_placeholder_percent_sensor_parameters_spec import (
    ColumnStringsStringNullPlaceholderPercentSensorParametersSpec,
)
from .column_strings_string_parsable_to_float_percent_sensor_parameters_spec import (
    ColumnStringsStringParsableToFloatPercentSensorParametersSpec,
)
from .column_strings_string_parsable_to_integer_percent_sensor_parameters_spec import (
    ColumnStringsStringParsableToIntegerPercentSensorParametersSpec,
)
from .column_strings_string_surrounded_by_whitespace_count_sensor_parameters_spec import (
    ColumnStringsStringSurroundedByWhitespaceCountSensorParametersSpec,
)
from .column_strings_string_surrounded_by_whitespace_percent_sensor_parameters_spec import (
    ColumnStringsStringSurroundedByWhitespacePercentSensorParametersSpec,
)
from .column_strings_string_valid_country_code_percent_sensor_parameters_spec import (
    ColumnStringsStringValidCountryCodePercentSensorParametersSpec,
)
from .column_strings_string_valid_currency_code_percent_sensor_parameters_spec import (
    ColumnStringsStringValidCurrencyCodePercentSensorParametersSpec,
)
from .column_strings_string_valid_date_percent_sensor_parameters_spec import (
    ColumnStringsStringValidDatePercentSensorParametersSpec,
)
from .column_strings_string_valid_uuid_percent_sensor_parameters_spec import (
    ColumnStringsStringValidUuidPercentSensorParametersSpec,
)
from .column_strings_string_value_in_set_percent_sensor_parameters_spec import (
    ColumnStringsStringValueInSetPercentSensorParametersSpec,
)
from .column_strings_string_whitespace_count_sensor_parameters_spec import (
    ColumnStringsStringWhitespaceCountSensorParametersSpec,
)
from .column_strings_string_whitespace_percent_sensor_parameters_spec import (
    ColumnStringsStringWhitespacePercentSensorParametersSpec,
)
from .column_sum_in_range_check_spec import ColumnSumInRangeCheckSpec
from .column_true_percent_check_spec import ColumnTruePercentCheckSpec
from .column_type_snapshot_spec import ColumnTypeSnapshotSpec
from .column_unique_count_check_spec import ColumnUniqueCountCheckSpec
from .column_unique_percent_check_spec import ColumnUniquePercentCheckSpec
from .column_uniqueness_daily_partitioned_checks_spec import (
    ColumnUniquenessDailyPartitionedChecksSpec,
)
from .column_uniqueness_daily_recurring_checks_spec import (
    ColumnUniquenessDailyRecurringChecksSpec,
)
from .column_uniqueness_duplicate_count_sensor_parameters_spec import (
    ColumnUniquenessDuplicateCountSensorParametersSpec,
)
from .column_uniqueness_duplicate_count_statistics_collector_spec import (
    ColumnUniquenessDuplicateCountStatisticsCollectorSpec,
)
from .column_uniqueness_duplicate_percent_sensor_parameters_spec import (
    ColumnUniquenessDuplicatePercentSensorParametersSpec,
)
from .column_uniqueness_duplicate_percent_statistics_collector_spec import (
    ColumnUniquenessDuplicatePercentStatisticsCollectorSpec,
)
from .column_uniqueness_monthly_partitioned_checks_spec import (
    ColumnUniquenessMonthlyPartitionedChecksSpec,
)
from .column_uniqueness_monthly_recurring_checks_spec import (
    ColumnUniquenessMonthlyRecurringChecksSpec,
)
from .column_uniqueness_profiling_checks_spec import ColumnUniquenessProfilingChecksSpec
from .column_uniqueness_statistics_collectors_spec import (
    ColumnUniquenessStatisticsCollectorsSpec,
)
from .column_uniqueness_unique_count_sensor_parameters_spec import (
    ColumnUniquenessUniqueCountSensorParametersSpec,
)
from .column_uniqueness_unique_count_statistics_collector_spec import (
    ColumnUniquenessUniqueCountStatisticsCollectorSpec,
)
from .column_uniqueness_unique_percent_sensor_parameters_spec import (
    ColumnUniquenessUniquePercentSensorParametersSpec,
)
from .column_uniqueness_unique_percent_statistics_collector_spec import (
    ColumnUniquenessUniquePercentStatisticsCollectorSpec,
)
from .column_valid_latitude_percent_check_spec import (
    ColumnValidLatitudePercentCheckSpec,
)
from .column_valid_longitude_percent_check_spec import (
    ColumnValidLongitudePercentCheckSpec,
)
from .column_value_above_max_value_count_check_spec import (
    ColumnValueAboveMaxValueCountCheckSpec,
)
from .column_value_above_max_value_percent_check_spec import (
    ColumnValueAboveMaxValuePercentCheckSpec,
)
from .column_value_below_min_value_count_check_spec import (
    ColumnValueBelowMinValueCountCheckSpec,
)
from .column_value_below_min_value_percent_check_spec import (
    ColumnValueBelowMinValuePercentCheckSpec,
)
from .column_values_in_range_integers_percent_check_spec import (
    ColumnValuesInRangeIntegersPercentCheckSpec,
)
from .column_values_in_range_numeric_percent_check_spec import (
    ColumnValuesInRangeNumericPercentCheckSpec,
)
from .comment_spec import CommentSpec
from .common_column_model import CommonColumnModel
from .connection_basic_model import ConnectionBasicModel
from .connection_basic_model_provider_type import ConnectionBasicModelProviderType
from .connection_incident_grouping_spec import ConnectionIncidentGroupingSpec
from .connection_incident_grouping_spec_grouping_level import (
    ConnectionIncidentGroupingSpecGroupingLevel,
)
from .connection_incident_grouping_spec_minimum_severity import (
    ConnectionIncidentGroupingSpecMinimumSeverity,
)
from .connection_model import ConnectionModel
from .connection_remote_model import ConnectionRemoteModel
from .connection_remote_model_connection_status import (
    ConnectionRemoteModelConnectionStatus,
)
from .connection_spec import ConnectionSpec
from .connection_spec_provider_type import ConnectionSpecProviderType
from .custom_check_spec import CustomCheckSpec
from .custom_rule_parameters_spec import CustomRuleParametersSpec
from .custom_sensor_parameters_spec import CustomSensorParametersSpec
from .dashboard_spec import DashboardSpec
from .dashboard_spec_parameters import DashboardSpecParameters
from .dashboards_folder_spec import DashboardsFolderSpec
from .data_stream_basic_model import DataStreamBasicModel
from .data_stream_level_spec import DataStreamLevelSpec
from .data_stream_level_spec_source import DataStreamLevelSpecSource
from .data_stream_mapping_spec import DataStreamMappingSpec
from .data_stream_model import DataStreamModel
from .data_stream_trimmed_model import DataStreamTrimmedModel
from .datatype_equals_rule_parameters_spec import DatatypeEqualsRuleParametersSpec
from .delete_stored_data_queue_job_parameters import DeleteStoredDataQueueJobParameters
from .dqo_job_change_model import DqoJobChangeModel
from .dqo_job_change_model_status import DqoJobChangeModelStatus
from .dqo_job_entry_parameters_model import DqoJobEntryParametersModel
from .dqo_job_history_entry_model import DqoJobHistoryEntryModel
from .dqo_job_history_entry_model_job_type import DqoJobHistoryEntryModelJobType
from .dqo_job_history_entry_model_status import DqoJobHistoryEntryModelStatus
from .dqo_job_queue_incremental_snapshot_model import (
    DqoJobQueueIncrementalSnapshotModel,
)
from .dqo_job_queue_initial_snapshot_model import DqoJobQueueInitialSnapshotModel
from .dqo_queue_job_id import DqoQueueJobId
from .dqo_settings_model import DqoSettingsModel
from .dqo_settings_model_properties import DqoSettingsModelProperties
from .dqo_settings_model_properties_additional_property import (
    DqoSettingsModelPropertiesAdditionalProperty,
)
from .dqo_user_profile_model import DqoUserProfileModel
from .duration import Duration
from .equals_integer_1_rule_parameters_spec import EqualsInteger1RuleParametersSpec
from .equals_integer_rule_parameters_spec import EqualsIntegerRuleParametersSpec
from .error_detailed_single_model import ErrorDetailedSingleModel
from .errors_detailed_data_model import ErrorsDetailedDataModel
from .external_log_entry import ExternalLogEntry
from .find_recent_incidents_on_connection_direction import (
    FindRecentIncidentsOnConnectionDirection,
)
from .find_recent_incidents_on_connection_order import (
    FindRecentIncidentsOnConnectionOrder,
)
from .get_column_partitioned_checks_overview_time_scale import (
    GetColumnPartitionedChecksOverviewTimeScale,
)
from .get_column_partitioned_checks_results_time_scale import (
    GetColumnPartitionedChecksResultsTimeScale,
)
from .get_column_partitioned_checks_ui_basic_time_scale import (
    GetColumnPartitionedChecksUIBasicTimeScale,
)
from .get_column_partitioned_checks_ui_filter_time_scale import (
    GetColumnPartitionedChecksUIFilterTimeScale,
)
from .get_column_partitioned_checks_ui_time_scale import (
    GetColumnPartitionedChecksUITimeScale,
)
from .get_column_partitioned_errors_time_scale import (
    GetColumnPartitionedErrorsTimeScale,
)
from .get_column_partitioned_sensor_readouts_time_scale import (
    GetColumnPartitionedSensorReadoutsTimeScale,
)
from .get_column_recurring_checks_overview_time_scale import (
    GetColumnRecurringChecksOverviewTimeScale,
)
from .get_column_recurring_checks_results_time_scale import (
    GetColumnRecurringChecksResultsTimeScale,
)
from .get_column_recurring_checks_ui_basic_time_scale import (
    GetColumnRecurringChecksUIBasicTimeScale,
)
from .get_column_recurring_checks_ui_filter_time_scale import (
    GetColumnRecurringChecksUIFilterTimeScale,
)
from .get_column_recurring_checks_ui_time_scale import (
    GetColumnRecurringChecksUITimeScale,
)
from .get_column_recurring_errors_time_scale import GetColumnRecurringErrorsTimeScale
from .get_column_recurring_sensor_readouts_time_scale import (
    GetColumnRecurringSensorReadoutsTimeScale,
)
from .get_connection_scheduling_group_scheduling_group import (
    GetConnectionSchedulingGroupSchedulingGroup,
)
from .get_incident_issues_direction import GetIncidentIssuesDirection
from .get_incident_issues_order import GetIncidentIssuesOrder
from .get_schema_partitioned_checks_templates_check_target import (
    GetSchemaPartitionedChecksTemplatesCheckTarget,
)
from .get_schema_partitioned_checks_templates_time_scale import (
    GetSchemaPartitionedChecksTemplatesTimeScale,
)
from .get_schema_partitioned_checks_ui_check_target import (
    GetSchemaPartitionedChecksUICheckTarget,
)
from .get_schema_partitioned_checks_ui_time_scale import (
    GetSchemaPartitionedChecksUITimeScale,
)
from .get_schema_profiling_checks_templates_check_target import (
    GetSchemaProfilingChecksTemplatesCheckTarget,
)
from .get_schema_profiling_checks_ui_check_target import (
    GetSchemaProfilingChecksUICheckTarget,
)
from .get_schema_recurring_checks_templates_check_target import (
    GetSchemaRecurringChecksTemplatesCheckTarget,
)
from .get_schema_recurring_checks_templates_time_scale import (
    GetSchemaRecurringChecksTemplatesTimeScale,
)
from .get_schema_recurring_checks_ui_check_target import (
    GetSchemaRecurringChecksUICheckTarget,
)
from .get_schema_recurring_checks_ui_time_scale import (
    GetSchemaRecurringChecksUITimeScale,
)
from .get_table_columns_partitioned_checks_ui_time_scale import (
    GetTableColumnsPartitionedChecksUITimeScale,
)
from .get_table_columns_recurring_checks_ui_time_scale import (
    GetTableColumnsRecurringChecksUITimeScale,
)
from .get_table_partitioned_checks_overview_time_scale import (
    GetTablePartitionedChecksOverviewTimeScale,
)
from .get_table_partitioned_checks_results_time_scale import (
    GetTablePartitionedChecksResultsTimeScale,
)
from .get_table_partitioned_checks_templates_time_scale import (
    GetTablePartitionedChecksTemplatesTimeScale,
)
from .get_table_partitioned_checks_ui_basic_time_scale import (
    GetTablePartitionedChecksUIBasicTimeScale,
)
from .get_table_partitioned_checks_ui_filter_time_scale import (
    GetTablePartitionedChecksUIFilterTimeScale,
)
from .get_table_partitioned_checks_ui_time_scale import (
    GetTablePartitionedChecksUITimeScale,
)
from .get_table_partitioned_errors_time_scale import GetTablePartitionedErrorsTimeScale
from .get_table_partitioned_sensor_readouts_time_scale import (
    GetTablePartitionedSensorReadoutsTimeScale,
)
from .get_table_recurring_checks_overview_time_scale import (
    GetTableRecurringChecksOverviewTimeScale,
)
from .get_table_recurring_checks_results_time_scale import (
    GetTableRecurringChecksResultsTimeScale,
)
from .get_table_recurring_checks_templates_time_scale import (
    GetTableRecurringChecksTemplatesTimeScale,
)
from .get_table_recurring_checks_ui_basic_time_scale import (
    GetTableRecurringChecksUIBasicTimeScale,
)
from .get_table_recurring_checks_ui_filter_time_scale import (
    GetTableRecurringChecksUIFilterTimeScale,
)
from .get_table_recurring_checks_ui_time_scale import GetTableRecurringChecksUITimeScale
from .get_table_recurring_errors_time_scale import GetTableRecurringErrorsTimeScale
from .get_table_recurring_sensor_readouts_time_scale import (
    GetTableRecurringSensorReadoutsTimeScale,
)
from .get_table_scheduling_group_override_scheduling_group import (
    GetTableSchedulingGroupOverrideSchedulingGroup,
)
from .hierarchy_id_model import HierarchyIdModel
from .hierarchy_id_model_path_item import HierarchyIdModelPathItem
from .import_schema_queue_job_parameters import ImportSchemaQueueJobParameters
from .import_tables_queue_job_parameters import ImportTablesQueueJobParameters
from .incident_daily_issues_count import IncidentDailyIssuesCount
from .incident_issue_histogram_model import IncidentIssueHistogramModel
from .incident_issue_histogram_model_checks import IncidentIssueHistogramModelChecks
from .incident_issue_histogram_model_columns import IncidentIssueHistogramModelColumns
from .incident_issue_histogram_model_days import IncidentIssueHistogramModelDays
from .incident_model import IncidentModel
from .incident_model_status import IncidentModelStatus
from .incident_webhook_notifications_spec import IncidentWebhookNotificationsSpec
from .incidents_per_connection_model import IncidentsPerConnectionModel
from .max_count_rule_0_parameters_spec import MaxCountRule0ParametersSpec
from .max_count_rule_10_parameters_spec import MaxCountRule10ParametersSpec
from .max_count_rule_15_parameters_spec import MaxCountRule15ParametersSpec
from .max_days_rule_1_parameters_spec import MaxDaysRule1ParametersSpec
from .max_days_rule_2_parameters_spec import MaxDaysRule2ParametersSpec
from .max_days_rule_7_parameters_spec import MaxDaysRule7ParametersSpec
from .max_diff_percent_rule_1_parameters_spec import MaxDiffPercentRule1ParametersSpec
from .max_diff_percent_rule_2_parameters_spec import MaxDiffPercentRule2ParametersSpec
from .max_diff_percent_rule_5_parameters_spec import MaxDiffPercentRule5ParametersSpec
from .max_failures_rule_0_parameters_spec import MaxFailuresRule0ParametersSpec
from .max_failures_rule_5_parameters_spec import MaxFailuresRule5ParametersSpec
from .max_failures_rule_10_parameters_spec import MaxFailuresRule10ParametersSpec
from .max_missing_rule_0_parameters_spec import MaxMissingRule0ParametersSpec
from .max_missing_rule_1_parameters_spec import MaxMissingRule1ParametersSpec
from .max_missing_rule_2_parameters_spec import MaxMissingRule2ParametersSpec
from .max_percent_rule_0_parameters_spec import MaxPercentRule0ParametersSpec
from .max_percent_rule_1_parameters_spec import MaxPercentRule1ParametersSpec
from .max_percent_rule_2_parameters_spec import MaxPercentRule2ParametersSpec
from .max_percent_rule_5_parameters_spec import MaxPercentRule5ParametersSpec
from .max_percent_rule_95_parameters_spec import MaxPercentRule95ParametersSpec
from .max_percent_rule_99_parameters_spec import MaxPercentRule99ParametersSpec
from .max_percent_rule_100_parameters_spec import MaxPercentRule100ParametersSpec
from .max_value_rule_parameters_spec import MaxValueRuleParametersSpec
from .min_count_rule_0_parameters_spec import MinCountRule0ParametersSpec
from .min_count_rule_fatal_parameters_spec import MinCountRuleFatalParametersSpec
from .min_count_rule_warning_parameters_spec import MinCountRuleWarningParametersSpec
from .min_percent_rule_0_parameters_spec import MinPercentRule0ParametersSpec
from .min_percent_rule_2_parameters_spec import MinPercentRule2ParametersSpec
from .min_percent_rule_5_parameters_spec import MinPercentRule5ParametersSpec
from .min_percent_rule_95_parameters_spec import MinPercentRule95ParametersSpec
from .min_percent_rule_99_parameters_spec import MinPercentRule99ParametersSpec
from .min_percent_rule_100_parameters_spec import MinPercentRule100ParametersSpec
from .min_value_rule_parameters_spec import MinValueRuleParametersSpec
from .mono import Mono
from .mono_dqo_queue_job_id import MonoDqoQueueJobId
from .mono_object import MonoObject
from .mysql_parameters_spec import MysqlParametersSpec
from .mysql_parameters_spec_properties import MysqlParametersSpecProperties
from .optional import Optional
from .optional_column_daily_partitioned_check_categories_spec import (
    OptionalColumnDailyPartitionedCheckCategoriesSpec,
)
from .optional_column_daily_recurring_check_categories_spec import (
    OptionalColumnDailyRecurringCheckCategoriesSpec,
)
from .optional_column_monthly_partitioned_check_categories_spec import (
    OptionalColumnMonthlyPartitionedCheckCategoriesSpec,
)
from .optional_column_monthly_recurring_check_categories_spec import (
    OptionalColumnMonthlyRecurringCheckCategoriesSpec,
)
from .optional_column_profiling_check_categories_spec import (
    OptionalColumnProfilingCheckCategoriesSpec,
)
from .optional_comments_list_spec import OptionalCommentsListSpec
from .optional_connection_incident_grouping_spec import (
    OptionalConnectionIncidentGroupingSpec,
)
from .optional_data_stream_mapping_spec import OptionalDataStreamMappingSpec
from .optional_label_set_spec import OptionalLabelSetSpec
from .optional_recurring_schedule_spec import OptionalRecurringScheduleSpec
from .optional_table_daily_partitioned_check_categories_spec import (
    OptionalTableDailyPartitionedCheckCategoriesSpec,
)
from .optional_table_daily_recurring_categories_spec import (
    OptionalTableDailyRecurringCategoriesSpec,
)
from .optional_table_incident_grouping_spec import OptionalTableIncidentGroupingSpec
from .optional_table_monthly_partitioned_check_categories_spec import (
    OptionalTableMonthlyPartitionedCheckCategoriesSpec,
)
from .optional_table_monthly_recurring_check_categories_spec import (
    OptionalTableMonthlyRecurringCheckCategoriesSpec,
)
from .optional_table_profiling_check_categories_spec import (
    OptionalTableProfilingCheckCategoriesSpec,
)
from .optional_ui_check_container_model import OptionalUICheckContainerModel
from .oracle_parameters_spec import OracleParametersSpec
from .oracle_parameters_spec_properties import OracleParametersSpecProperties
from .parameter_definition_spec import ParameterDefinitionSpec
from .parameter_definition_spec_data_type import ParameterDefinitionSpecDataType
from .parameter_definition_spec_display_hint import ParameterDefinitionSpecDisplayHint
from .partition_incremental_time_window_spec import PartitionIncrementalTimeWindowSpec
from .percentile_moving_within_7_days_rule_parameters_spec import (
    PercentileMovingWithin7DaysRuleParametersSpec,
)
from .percentile_moving_within_30_days_rule_parameters_spec import (
    PercentileMovingWithin30DaysRuleParametersSpec,
)
from .percentile_moving_within_60_days_rule_parameters_spec import (
    PercentileMovingWithin60DaysRuleParametersSpec,
)
from .physical_table_name import PhysicalTableName
from .postgresql_parameters_spec import PostgresqlParametersSpec
from .postgresql_parameters_spec_properties import PostgresqlParametersSpecProperties
from .provider_sensor_basic_model import ProviderSensorBasicModel
from .provider_sensor_basic_model_provider_type import (
    ProviderSensorBasicModelProviderType,
)
from .provider_sensor_definition_spec import ProviderSensorDefinitionSpec
from .provider_sensor_definition_spec_parameters import (
    ProviderSensorDefinitionSpecParameters,
)
from .provider_sensor_definition_spec_type import ProviderSensorDefinitionSpecType
from .provider_sensor_model import ProviderSensorModel
from .provider_sensor_model_provider_type import ProviderSensorModelProviderType
from .recurring_schedule_spec import RecurringScheduleSpec
from .recurring_schedules_spec import RecurringSchedulesSpec
from .redshift_parameters_spec import RedshiftParametersSpec
from .redshift_parameters_spec_properties import RedshiftParametersSpecProperties
from .repair_stored_data_queue_job_parameters import RepairStoredDataQueueJobParameters
from .rule_basic_folder_model import RuleBasicFolderModel
from .rule_basic_folder_model_folders import RuleBasicFolderModelFolders
from .rule_basic_model import RuleBasicModel
from .rule_model import RuleModel
from .rule_model_mode import RuleModelMode
from .rule_model_parameters import RuleModelParameters
from .rule_model_type import RuleModelType
from .rule_time_window_settings_spec import RuleTimeWindowSettingsSpec
from .rule_time_window_settings_spec_historic_data_point_grouping import (
    RuleTimeWindowSettingsSpecHistoricDataPointGrouping,
)
from .run_checks_on_table_queue_job_parameters import RunChecksOnTableQueueJobParameters
from .run_checks_queue_job_parameters import RunChecksQueueJobParameters
from .run_checks_queue_job_result import RunChecksQueueJobResult
from .run_checks_queue_job_result_highest_severity import (
    RunChecksQueueJobResultHighestSeverity,
)
from .schema_model import SchemaModel
from .schema_remote_model import SchemaRemoteModel
from .sensor_basic_folder_model import SensorBasicFolderModel
from .sensor_basic_folder_model_folders import SensorBasicFolderModelFolders
from .sensor_basic_model import SensorBasicModel
from .sensor_definition_spec import SensorDefinitionSpec
from .sensor_definition_spec_parameters import SensorDefinitionSpecParameters
from .sensor_model import SensorModel
from .sensor_readout_detailed_single_model import SensorReadoutDetailedSingleModel
from .sensor_readouts_detailed_data_model import SensorReadoutsDetailedDataModel
from .set_incident_status_status import SetIncidentStatusStatus
from .similar_check_model import SimilarCheckModel
from .similar_check_model_check_target import SimilarCheckModelCheckTarget
from .similar_check_model_check_type import SimilarCheckModelCheckType
from .similar_check_model_time_scale import SimilarCheckModelTimeScale
from .snowflake_parameters_spec import SnowflakeParametersSpec
from .snowflake_parameters_spec_properties import SnowflakeParametersSpecProperties
from .spring_error_payload import SpringErrorPayload
from .sql_server_parameters_spec import SqlServerParametersSpec
from .sql_server_parameters_spec_properties import SqlServerParametersSpecProperties
from .statistics_collector_search_filters import StatisticsCollectorSearchFilters
from .statistics_collector_search_filters_target import (
    StatisticsCollectorSearchFiltersTarget,
)
from .statistics_metric_model import StatisticsMetricModel
from .statistics_metric_model_result import StatisticsMetricModelResult
from .statistics_metric_model_result_data_type import (
    StatisticsMetricModelResultDataType,
)
from .synchronize_multiple_folders_dqo_queue_job_parameters import (
    SynchronizeMultipleFoldersDqoQueueJobParameters,
)
from .synchronize_multiple_folders_dqo_queue_job_parameters_direction import (
    SynchronizeMultipleFoldersDqoQueueJobParametersDirection,
)
from .synchronize_root_folder_dqo_queue_job_parameters import (
    SynchronizeRootFolderDqoQueueJobParameters,
)
from .synchronize_root_folder_parameters import SynchronizeRootFolderParameters
from .synchronize_root_folder_parameters_direction import (
    SynchronizeRootFolderParametersDirection,
)
from .synchronize_root_folder_parameters_folder import (
    SynchronizeRootFolderParametersFolder,
)
from .table_accuracy_daily_recurring_checks_spec import (
    TableAccuracyDailyRecurringChecksSpec,
)
from .table_accuracy_monthly_recurring_checks_spec import (
    TableAccuracyMonthlyRecurringChecksSpec,
)
from .table_accuracy_profiling_checks_spec import TableAccuracyProfilingChecksSpec
from .table_accuracy_row_count_match_percent_check_spec import (
    TableAccuracyRowCountMatchPercentCheckSpec,
)
from .table_accuracy_row_count_match_percent_sensor_parameters_spec import (
    TableAccuracyRowCountMatchPercentSensorParametersSpec,
)
from .table_anomaly_row_count_7_days_check_spec import (
    TableAnomalyRowCount7DaysCheckSpec,
)
from .table_anomaly_row_count_30_days_check_spec import (
    TableAnomalyRowCount30DaysCheckSpec,
)
from .table_anomaly_row_count_60_days_check_spec import (
    TableAnomalyRowCount60DaysCheckSpec,
)
from .table_anomaly_row_count_change_7_days_check_spec import (
    TableAnomalyRowCountChange7DaysCheckSpec,
)
from .table_anomaly_row_count_change_30_days_check_spec import (
    TableAnomalyRowCountChange30DaysCheckSpec,
)
from .table_anomaly_row_count_change_60_days_check_spec import (
    TableAnomalyRowCountChange60DaysCheckSpec,
)
from .table_availability_check_spec import TableAvailabilityCheckSpec
from .table_availability_daily_recurring_checks_spec import (
    TableAvailabilityDailyRecurringChecksSpec,
)
from .table_availability_monthly_recurring_checks_spec import (
    TableAvailabilityMonthlyRecurringChecksSpec,
)
from .table_availability_profiling_checks_spec import (
    TableAvailabilityProfilingChecksSpec,
)
from .table_availability_sensor_parameters_spec import (
    TableAvailabilitySensorParametersSpec,
)
from .table_basic_model import TableBasicModel
from .table_change_row_count_check_spec import TableChangeRowCountCheckSpec
from .table_change_row_count_since_7_days_check_spec import (
    TableChangeRowCountSince7DaysCheckSpec,
)
from .table_change_row_count_since_30_days_check_spec import (
    TableChangeRowCountSince30DaysCheckSpec,
)
from .table_change_row_count_since_yesterday_check_spec import (
    TableChangeRowCountSinceYesterdayCheckSpec,
)
from .table_column_count_sensor_parameters_spec import (
    TableColumnCountSensorParametersSpec,
)
from .table_column_list_ordered_hash_sensor_parameters_spec import (
    TableColumnListOrderedHashSensorParametersSpec,
)
from .table_column_list_unordered_hash_sensor_parameters_spec import (
    TableColumnListUnorderedHashSensorParametersSpec,
)
from .table_column_types_hash_sensor_parameters_spec import (
    TableColumnTypesHashSensorParametersSpec,
)
from .table_columns_statistics_model import TableColumnsStatisticsModel
from .table_daily_partitioned_check_categories_spec import (
    TableDailyPartitionedCheckCategoriesSpec,
)
from .table_daily_partitioned_check_categories_spec_custom import (
    TableDailyPartitionedCheckCategoriesSpecCustom,
)
from .table_daily_recurring_categories_spec import TableDailyRecurringCategoriesSpec
from .table_daily_recurring_categories_spec_custom import (
    TableDailyRecurringCategoriesSpecCustom,
)
from .table_data_ingestion_delay_check_spec import TableDataIngestionDelayCheckSpec
from .table_days_since_most_recent_event_check_spec import (
    TableDaysSinceMostRecentEventCheckSpec,
)
from .table_days_since_most_recent_ingestion_check_spec import (
    TableDaysSinceMostRecentIngestionCheckSpec,
)
from .table_incident_grouping_spec import TableIncidentGroupingSpec
from .table_incident_grouping_spec_grouping_level import (
    TableIncidentGroupingSpecGroupingLevel,
)
from .table_incident_grouping_spec_minimum_severity import (
    TableIncidentGroupingSpecMinimumSeverity,
)
from .table_model import TableModel
from .table_monthly_partitioned_check_categories_spec import (
    TableMonthlyPartitionedCheckCategoriesSpec,
)
from .table_monthly_partitioned_check_categories_spec_custom import (
    TableMonthlyPartitionedCheckCategoriesSpecCustom,
)
from .table_monthly_recurring_check_categories_spec import (
    TableMonthlyRecurringCheckCategoriesSpec,
)
from .table_monthly_recurring_check_categories_spec_custom import (
    TableMonthlyRecurringCheckCategoriesSpecCustom,
)
from .table_owner_spec import TableOwnerSpec
from .table_partition_reload_lag_check_spec import TablePartitionReloadLagCheckSpec
from .table_partitioned_checks_root_spec import TablePartitionedChecksRootSpec
from .table_partitioning_model import TablePartitioningModel
from .table_profiling_check_categories_spec import TableProfilingCheckCategoriesSpec
from .table_profiling_check_categories_spec_custom import (
    TableProfilingCheckCategoriesSpecCustom,
)
from .table_recurring_checks_spec import TableRecurringChecksSpec
from .table_remote_basic_model import TableRemoteBasicModel
from .table_row_count_check_spec import TableRowCountCheckSpec
from .table_schema_column_count_changed_check_spec import (
    TableSchemaColumnCountChangedCheckSpec,
)
from .table_schema_column_count_check_spec import TableSchemaColumnCountCheckSpec
from .table_schema_column_list_changed_check_spec import (
    TableSchemaColumnListChangedCheckSpec,
)
from .table_schema_column_list_or_order_changed_check_spec import (
    TableSchemaColumnListOrOrderChangedCheckSpec,
)
from .table_schema_column_types_changed_check_spec import (
    TableSchemaColumnTypesChangedCheckSpec,
)
from .table_schema_daily_recurring_checks_spec import (
    TableSchemaDailyRecurringChecksSpec,
)
from .table_schema_monthly_recurring_checks_spec import (
    TableSchemaMonthlyRecurringChecksSpec,
)
from .table_schema_profiling_checks_spec import TableSchemaProfilingChecksSpec
from .table_spec import TableSpec
from .table_spec_columns import TableSpecColumns
from .table_spec_data_streams import TableSpecDataStreams
from .table_sql_aggregate_expr_check_spec import TableSqlAggregateExprCheckSpec
from .table_sql_aggregated_expression_sensor_parameters_spec import (
    TableSqlAggregatedExpressionSensorParametersSpec,
)
from .table_sql_condition_failed_count_check_spec import (
    TableSqlConditionFailedCountCheckSpec,
)
from .table_sql_condition_failed_count_sensor_parameters_spec import (
    TableSqlConditionFailedCountSensorParametersSpec,
)
from .table_sql_condition_passed_percent_check_spec import (
    TableSqlConditionPassedPercentCheckSpec,
)
from .table_sql_condition_passed_percent_sensor_parameters_spec import (
    TableSqlConditionPassedPercentSensorParametersSpec,
)
from .table_sql_daily_partitioned_checks_spec import TableSqlDailyPartitionedChecksSpec
from .table_sql_daily_recurring_checks_spec import TableSqlDailyRecurringChecksSpec
from .table_sql_monthly_partitioned_checks_spec import (
    TableSqlMonthlyPartitionedChecksSpec,
)
from .table_sql_monthly_recurring_checks_spec import TableSqlMonthlyRecurringChecksSpec
from .table_sql_profiling_checks_spec import TableSqlProfilingChecksSpec
from .table_statistics_collectors_root_categories_spec import (
    TableStatisticsCollectorsRootCategoriesSpec,
)
from .table_statistics_model import TableStatisticsModel
from .table_timeliness_daily_partitioned_checks_spec import (
    TableTimelinessDailyPartitionedChecksSpec,
)
from .table_timeliness_daily_recurring_checks_spec import (
    TableTimelinessDailyRecurringChecksSpec,
)
from .table_timeliness_data_ingestion_delay_sensor_parameters_spec import (
    TableTimelinessDataIngestionDelaySensorParametersSpec,
)
from .table_timeliness_days_since_most_recent_event_sensor_parameters_spec import (
    TableTimelinessDaysSinceMostRecentEventSensorParametersSpec,
)
from .table_timeliness_days_since_most_recent_ingestion_sensor_parameters_spec import (
    TableTimelinessDaysSinceMostRecentIngestionSensorParametersSpec,
)
from .table_timeliness_monthly_partitioned_checks_spec import (
    TableTimelinessMonthlyPartitionedChecksSpec,
)
from .table_timeliness_monthly_recurring_checks_spec import (
    TableTimelinessMonthlyRecurringChecksSpec,
)
from .table_timeliness_partition_reload_lag_sensor_parameters_spec import (
    TableTimelinessPartitionReloadLagSensorParametersSpec,
)
from .table_timeliness_profiling_checks_spec import TableTimelinessProfilingChecksSpec
from .table_volume_daily_partitioned_checks_spec import (
    TableVolumeDailyPartitionedChecksSpec,
)
from .table_volume_daily_recurring_checks_spec import (
    TableVolumeDailyRecurringChecksSpec,
)
from .table_volume_monthly_partitioned_checks_spec import (
    TableVolumeMonthlyPartitionedChecksSpec,
)
from .table_volume_monthly_recurring_checks_spec import (
    TableVolumeMonthlyRecurringChecksSpec,
)
from .table_volume_profiling_checks_spec import TableVolumeProfilingChecksSpec
from .table_volume_row_count_sensor_parameters_spec import (
    TableVolumeRowCountSensorParametersSpec,
)
from .table_volume_row_count_statistics_collector_spec import (
    TableVolumeRowCountStatisticsCollectorSpec,
)
from .table_volume_statistics_collectors_spec import TableVolumeStatisticsCollectorsSpec
from .temporal_unit import TemporalUnit
from .time_window_filter_parameters import TimeWindowFilterParameters
from .timestamp_columns_spec import TimestampColumnsSpec
from .ui_all_checks_model import UIAllChecksModel
from .ui_all_checks_patch_parameters import UIAllChecksPatchParameters
from .ui_all_checks_patch_parameters_selected_tables_to_columns import (
    UIAllChecksPatchParametersSelectedTablesToColumns,
)
from .ui_all_column_checks_model import UIAllColumnChecksModel
from .ui_all_column_checks_model_check_target import UIAllColumnChecksModelCheckTarget
from .ui_all_table_checks_model import UIAllTableChecksModel
from .ui_all_table_checks_model_check_target import UIAllTableChecksModelCheckTarget
from .ui_check_basic_model import UICheckBasicModel
from .ui_check_container_basic_model import UICheckContainerBasicModel
from .ui_check_container_model import UICheckContainerModel
from .ui_check_container_model_effective_schedule_enabled_status import (
    UICheckContainerModelEffectiveScheduleEnabledStatus,
)
from .ui_check_container_type_model import UICheckContainerTypeModel
from .ui_check_container_type_model_check_time_scale import (
    UICheckContainerTypeModelCheckTimeScale,
)
from .ui_check_container_type_model_check_type import UICheckContainerTypeModelCheckType
from .ui_check_model import UICheckModel
from .ui_check_model_check_target import UICheckModelCheckTarget
from .ui_check_model_configuration_requirements_errors_item import (
    UICheckModelConfigurationRequirementsErrorsItem,
)
from .ui_check_model_schedule_enabled_status import UICheckModelScheduleEnabledStatus
from .ui_column_checks_model import UIColumnChecksModel
from .ui_column_checks_model_check_containers import UIColumnChecksModelCheckContainers
from .ui_effective_schedule_model import UIEffectiveScheduleModel
from .ui_effective_schedule_model_schedule_group import (
    UIEffectiveScheduleModelScheduleGroup,
)
from .ui_effective_schedule_model_schedule_level import (
    UIEffectiveScheduleModelScheduleLevel,
)
from .ui_field_model import UIFieldModel
from .ui_quality_category_model import UIQualityCategoryModel
from .ui_rule_parameters_model import UIRuleParametersModel
from .ui_rule_thresholds_model import UIRuleThresholdsModel
from .ui_schema_table_checks_model import UISchemaTableChecksModel
from .ui_table_checks_model import UITableChecksModel
from .ui_table_checks_model_check_containers import UITableChecksModelCheckContainers
from .ui_table_column_checks_model import UITableColumnChecksModel
from .update_column_partitioned_checks_ui_time_scale import (
    UpdateColumnPartitionedChecksUITimeScale,
)
from .update_column_recurring_checks_ui_time_scale import (
    UpdateColumnRecurringChecksUITimeScale,
)
from .update_connection_scheduling_group_scheduling_group import (
    UpdateConnectionSchedulingGroupSchedulingGroup,
)
from .update_table_partitioned_checks_ui_time_scale import (
    UpdateTablePartitionedChecksUITimeScale,
)
from .update_table_recurring_checks_ui_time_scale import (
    UpdateTableRecurringChecksUITimeScale,
)
from .update_table_scheduling_group_override_scheduling_group import (
    UpdateTableSchedulingGroupOverrideSchedulingGroup,
)
from .value_changed_parameters_spec import ValueChangedParametersSpec
from .within_change_1_day_rule_parameters_spec import WithinChange1DayRuleParametersSpec
from .within_change_7_days_rule_parameters_spec import (
    WithinChange7DaysRuleParametersSpec,
)
from .within_change_30_days_rule_parameters_spec import (
    WithinChange30DaysRuleParametersSpec,
)
from .within_change_rule_parameters_spec import WithinChangeRuleParametersSpec

__all__ = (
    "AuthenticatedDashboardModel",
    "BetweenFloatsRuleParametersSpec",
    "BigQueryParametersSpec",
    "BigQueryParametersSpecAuthenticationMode",
    "BulkCheckDisableParameters",
    "BulkCheckDisableParametersSelectedTablesToColumns",
    "ChangePercentileMovingWithin30DaysRuleParametersSpec",
    "ChangePercentileMovingWithin60DaysRuleParametersSpec",
    "ChangePercentileMovingWithin7DaysRuleParametersSpec",
    "CheckBasicFolderModel",
    "CheckBasicFolderModelFolders",
    "CheckBasicModel",
    "CheckModel",
    "CheckResultDetailedSingleModel",
    "CheckResultsDetailedDataModel",
    "CheckResultsOverviewDataModel",
    "CheckResultsOverviewDataModelStatusesItem",
    "CheckSearchFilters",
    "CheckSearchFiltersCheckTarget",
    "CheckSearchFiltersCheckType",
    "CheckSearchFiltersTimeScale",
    "CheckTemplate",
    "CheckTemplateCheckTarget",
    "CloudSynchronizationFoldersStatusModel",
    "CloudSynchronizationFoldersStatusModelChecks",
    "CloudSynchronizationFoldersStatusModelDataCheckResults",
    "CloudSynchronizationFoldersStatusModelDataErrors",
    "CloudSynchronizationFoldersStatusModelDataIncidents",
    "CloudSynchronizationFoldersStatusModelDataSensorReadouts",
    "CloudSynchronizationFoldersStatusModelDataStatistics",
    "CloudSynchronizationFoldersStatusModelRules",
    "CloudSynchronizationFoldersStatusModelSensors",
    "CloudSynchronizationFoldersStatusModelSources",
    "CollectStatisticsOnTableQueueJobParameters",
    "CollectStatisticsOnTableQueueJobParametersDataScope",
    "CollectStatisticsQueueJobParameters",
    "CollectStatisticsQueueJobParametersDataScope",
    "CollectStatisticsQueueJobResult",
    "ColumnAccuracyAverageMatchPercentCheckSpec",
    "ColumnAccuracyAverageMatchPercentSensorParametersSpec",
    "ColumnAccuracyDailyPartitionedChecksSpec",
    "ColumnAccuracyDailyRecurringChecksSpec",
    "ColumnAccuracyMaxMatchPercentCheckSpec",
    "ColumnAccuracyMaxMatchPercentSensorParametersSpec",
    "ColumnAccuracyMinMatchPercentCheckSpec",
    "ColumnAccuracyMinMatchPercentSensorParametersSpec",
    "ColumnAccuracyMonthlyPartitionedChecksSpec",
    "ColumnAccuracyMonthlyRecurringChecksSpec",
    "ColumnAccuracyNotNullCountMatchPercentCheckSpec",
    "ColumnAccuracyNotNullCountMatchPercentSensorParametersSpec",
    "ColumnAccuracyProfilingChecksSpec",
    "ColumnAccuracyTotalSumMatchPercentCheckSpec",
    "ColumnAccuracyTotalSumMatchPercentSensorParametersSpec",
    "ColumnAnomalyDailyPartitionedChecksSpec",
    "ColumnAnomalyDailyRecurringChecksSpec",
    "ColumnAnomalyMean30DaysCheckSpec",
    "ColumnAnomalyMean60DaysCheckSpec",
    "ColumnAnomalyMean7DaysCheckSpec",
    "ColumnAnomalyMeanChange30DaysCheckSpec",
    "ColumnAnomalyMeanChange60DaysCheckSpec",
    "ColumnAnomalyMeanChange7DaysCheckSpec",
    "ColumnAnomalyMedian30DaysCheckSpec",
    "ColumnAnomalyMedian60DaysCheckSpec",
    "ColumnAnomalyMedian7DaysCheckSpec",
    "ColumnAnomalyMedianChange30DaysCheckSpec",
    "ColumnAnomalyMedianChange60DaysCheckSpec",
    "ColumnAnomalyMedianChange7DaysCheckSpec",
    "ColumnAnomalyMonthlyPartitionedChecksSpec",
    "ColumnAnomalyMonthlyRecurringChecksSpec",
    "ColumnAnomalyProfilingChecksSpec",
    "ColumnAnomalySum30DaysCheckSpec",
    "ColumnAnomalySum60DaysCheckSpec",
    "ColumnAnomalySum7DaysCheckSpec",
    "ColumnAnomalySumChange30DaysCheckSpec",
    "ColumnAnomalySumChange60DaysCheckSpec",
    "ColumnAnomalySumChange7DaysCheckSpec",
    "ColumnBasicModel",
    "ColumnBoolDailyPartitionedChecksSpec",
    "ColumnBoolDailyRecurringChecksSpec",
    "ColumnBoolFalsePercentSensorParametersSpec",
    "ColumnBoolMonthlyPartitionedChecksSpec",
    "ColumnBoolMonthlyRecurringChecksSpec",
    "ColumnBoolProfilingChecksSpec",
    "ColumnBoolTruePercentSensorParametersSpec",
    "ColumnChangeMeanCheckSpec",
    "ColumnChangeMeanSince30DaysCheckSpec",
    "ColumnChangeMeanSince7DaysCheckSpec",
    "ColumnChangeMeanSinceYesterdayCheckSpec",
    "ColumnChangeMedianCheckSpec",
    "ColumnChangeMedianSince30DaysCheckSpec",
    "ColumnChangeMedianSince7DaysCheckSpec",
    "ColumnChangeMedianSinceYesterdayCheckSpec",
    "ColumnChangeSumCheckSpec",
    "ColumnChangeSumSince30DaysCheckSpec",
    "ColumnChangeSumSince7DaysCheckSpec",
    "ColumnChangeSumSinceYesterdayCheckSpec",
    "ColumnColumnExistsSensorParametersSpec",
    "ColumnColumnTypeHashSensorParametersSpec",
    "ColumnConsistencyDailyPartitionedChecksSpec",
    "ColumnConsistencyDailyRecurringChecksSpec",
    "ColumnConsistencyDateMatchFormatPercentCheckSpec",
    "ColumnConsistencyDateMatchFormatPercentSensorParametersSpec",
    "ColumnConsistencyDateMatchFormatPercentSensorParametersSpecDateFormats",
    "ColumnConsistencyMonthlyPartitionedChecksSpec",
    "ColumnConsistencyMonthlyRecurringChecksSpec",
    "ColumnConsistencyProfilingChecksSpec",
    "ColumnDailyPartitionedCheckCategoriesSpec",
    "ColumnDailyPartitionedCheckCategoriesSpecCustom",
    "ColumnDailyRecurringCheckCategoriesSpec",
    "ColumnDailyRecurringCheckCategoriesSpecCustom",
    "ColumnDatetimeDailyPartitionedChecksSpec",
    "ColumnDatetimeDailyRecurringChecksSpec",
    "ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec",
    "ColumnDatetimeMonthlyPartitionedChecksSpec",
    "ColumnDatetimeMonthlyRecurringChecksSpec",
    "ColumnDatetimeProfilingChecksSpec",
    "ColumnDatetimeValueInRangeDatePercentCheckSpec",
    "ColumnDatetimeValueInRangeDatePercentSensorParametersSpec",
    "ColumnDateValuesInFuturePercentCheckSpec",
    "ColumnDuplicateCountCheckSpec",
    "ColumnDuplicatePercentCheckSpec",
    "ColumnExpectedNumbersInUseCountCheckSpec",
    "ColumnExpectedStringsInTopValuesCountCheckSpec",
    "ColumnExpectedStringsInUseCountCheckSpec",
    "ColumnFalsePercentCheckSpec",
    "ColumnIntegrityDailyPartitionedChecksSpec",
    "ColumnIntegrityDailyRecurringChecksSpec",
    "ColumnIntegrityForeignKeyMatchPercentCheckSpec",
    "ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec",
    "ColumnIntegrityForeignKeyNotMatchCountCheckSpec",
    "ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec",
    "ColumnIntegrityMonthlyPartitionedChecksSpec",
    "ColumnIntegrityMonthlyRecurringChecksSpec",
    "ColumnIntegrityProfilingChecksSpec",
    "ColumnInvalidLatitudeCountCheckSpec",
    "ColumnInvalidLongitudeCountCheckSpec",
    "ColumnMaxInRangeCheckSpec",
    "ColumnMeanInRangeCheckSpec",
    "ColumnMedianInRangeCheckSpec",
    "ColumnMinInRangeCheckSpec",
    "ColumnModel",
    "ColumnMonthlyPartitionedCheckCategoriesSpec",
    "ColumnMonthlyPartitionedCheckCategoriesSpecCustom",
    "ColumnMonthlyRecurringCheckCategoriesSpec",
    "ColumnMonthlyRecurringCheckCategoriesSpecCustom",
    "ColumnNegativeCountCheckSpec",
    "ColumnNegativePercentCheckSpec",
    "ColumnNonNegativeCountCheckSpec",
    "ColumnNonNegativePercentCheckSpec",
    "ColumnNotNullsCountCheckSpec",
    "ColumnNotNullsPercentCheckSpec",
    "ColumnNullsCountCheckSpec",
    "ColumnNullsDailyPartitionedChecksSpec",
    "ColumnNullsDailyRecurringChecksSpec",
    "ColumnNullsMonthlyPartitionedChecksSpec",
    "ColumnNullsMonthlyRecurringChecksSpec",
    "ColumnNullsNotNullsCountSensorParametersSpec",
    "ColumnNullsNotNullsCountStatisticsCollectorSpec",
    "ColumnNullsNotNullsPercentSensorParametersSpec",
    "ColumnNullsNotNullsPercentStatisticsCollectorSpec",
    "ColumnNullsNullsCountSensorParametersSpec",
    "ColumnNullsNullsCountStatisticsCollectorSpec",
    "ColumnNullsNullsPercentSensorParametersSpec",
    "ColumnNullsNullsPercentStatisticsCollectorSpec",
    "ColumnNullsPercentCheckSpec",
    "ColumnNullsProfilingChecksSpec",
    "ColumnNullsStatisticsCollectorsSpec",
    "ColumnNumberValueInSetPercentCheckSpec",
    "ColumnNumericDailyPartitionedChecksSpec",
    "ColumnNumericDailyRecurringChecksSpec",
    "ColumnNumericExpectedNumbersInUseCountSensorParametersSpec",
    "ColumnNumericInvalidLatitudeCountSensorParametersSpec",
    "ColumnNumericInvalidLongitudeCountSensorParametersSpec",
    "ColumnNumericMaxSensorParametersSpec",
    "ColumnNumericMeanSensorParametersSpec",
    "ColumnNumericMedianSensorParametersSpec",
    "ColumnNumericMinSensorParametersSpec",
    "ColumnNumericMonthlyPartitionedChecksSpec",
    "ColumnNumericMonthlyRecurringChecksSpec",
    "ColumnNumericNegativeCountSensorParametersSpec",
    "ColumnNumericNegativePercentSensorParametersSpec",
    "ColumnNumericNonNegativeCountSensorParametersSpec",
    "ColumnNumericNonNegativePercentSensorParametersSpec",
    "ColumnNumericNumberValueInSetPercentSensorParametersSpec",
    "ColumnNumericPercentile10SensorParametersSpec",
    "ColumnNumericPercentile25SensorParametersSpec",
    "ColumnNumericPercentile75SensorParametersSpec",
    "ColumnNumericPercentile90SensorParametersSpec",
    "ColumnNumericPercentileSensorParametersSpec",
    "ColumnNumericPopulationStddevSensorParametersSpec",
    "ColumnNumericPopulationVarianceSensorParametersSpec",
    "ColumnNumericProfilingChecksSpec",
    "ColumnNumericSampleStddevSensorParametersSpec",
    "ColumnNumericSampleVarianceSensorParametersSpec",
    "ColumnNumericSumSensorParametersSpec",
    "ColumnNumericValidLatitudePercentSensorParametersSpec",
    "ColumnNumericValidLongitudePercentSensorParametersSpec",
    "ColumnNumericValueAboveMaxValueCountSensorParametersSpec",
    "ColumnNumericValueAboveMaxValuePercentSensorParametersSpec",
    "ColumnNumericValueBelowMinValueCountSensorParametersSpec",
    "ColumnNumericValueBelowMinValuePercentSensorParametersSpec",
    "ColumnNumericValuesInRangeIntegersPercentSensorParametersSpec",
    "ColumnNumericValuesInRangeNumericPercentSensorParametersSpec",
    "ColumnPartitionedChecksRootSpec",
    "ColumnPercentile10InRangeCheckSpec",
    "ColumnPercentile25InRangeCheckSpec",
    "ColumnPercentile75InRangeCheckSpec",
    "ColumnPercentile90InRangeCheckSpec",
    "ColumnPercentileInRangeCheckSpec",
    "ColumnPiiContainsEmailPercentCheckSpec",
    "ColumnPiiContainsEmailPercentSensorParametersSpec",
    "ColumnPiiContainsIp4PercentCheckSpec",
    "ColumnPiiContainsIp4PercentSensorParametersSpec",
    "ColumnPiiContainsIp6PercentCheckSpec",
    "ColumnPiiContainsIp6PercentSensorParametersSpec",
    "ColumnPiiContainsUsaPhonePercentCheckSpec",
    "ColumnPiiContainsUsaPhonePercentSensorParametersSpec",
    "ColumnPiiContainsUsaZipcodePercentCheckSpec",
    "ColumnPiiContainsUsaZipcodePercentSensorParametersSpec",
    "ColumnPiiDailyPartitionedChecksSpec",
    "ColumnPiiDailyRecurringChecksSpec",
    "ColumnPiiMonthlyPartitionedChecksSpec",
    "ColumnPiiMonthlyRecurringChecksSpec",
    "ColumnPiiProfilingChecksSpec",
    "ColumnPiiValidEmailPercentCheckSpec",
    "ColumnPiiValidEmailPercentSensorParametersSpec",
    "ColumnPiiValidIp4AddressPercentCheckSpec",
    "ColumnPiiValidIp4AddressPercentSensorParametersSpec",
    "ColumnPiiValidIp6AddressPercentCheckSpec",
    "ColumnPiiValidIp6AddressPercentSensorParametersSpec",
    "ColumnPiiValidUsaPhonePercentCheckSpec",
    "ColumnPiiValidUsaPhonePercentSensorParametersSpec",
    "ColumnPiiValidUsaZipcodePercentCheckSpec",
    "ColumnPiiValidUsaZipcodePercentSensorParametersSpec",
    "ColumnPopulationStddevInRangeCheckSpec",
    "ColumnPopulationVarianceInRangeCheckSpec",
    "ColumnProfilingCheckCategoriesSpec",
    "ColumnProfilingCheckCategoriesSpecCustom",
    "ColumnRangeMaxValueSensorParametersSpec",
    "ColumnRangeMaxValueStatisticsCollectorSpec",
    "ColumnRangeMedianValueStatisticsCollectorSpec",
    "ColumnRangeMinValueSensorParametersSpec",
    "ColumnRangeMinValueStatisticsCollectorSpec",
    "ColumnRangeStatisticsCollectorsSpec",
    "ColumnRangeSumValueStatisticsCollectorSpec",
    "ColumnRecurringChecksRootSpec",
    "ColumnSampleStddevInRangeCheckSpec",
    "ColumnSampleVarianceInRangeCheckSpec",
    "ColumnSamplingColumnSamplesSensorParametersSpec",
    "ColumnSamplingColumnSamplesStatisticsCollectorSpec",
    "ColumnSamplingStatisticsCollectorsSpec",
    "ColumnSchemaColumnExistsCheckSpec",
    "ColumnSchemaDailyRecurringChecksSpec",
    "ColumnSchemaMonthlyRecurringChecksSpec",
    "ColumnSchemaProfilingChecksSpec",
    "ColumnSchemaTypeChangedCheckSpec",
    "ColumnSpec",
    "ColumnSqlAggregatedExpressionSensorParametersSpec",
    "ColumnSqlAggregateExprCheckSpec",
    "ColumnSqlConditionFailedCountCheckSpec",
    "ColumnSqlConditionFailedCountSensorParametersSpec",
    "ColumnSqlConditionPassedPercentCheckSpec",
    "ColumnSqlConditionPassedPercentSensorParametersSpec",
    "ColumnSqlDailyPartitionedChecksSpec",
    "ColumnSqlDailyRecurringChecksSpec",
    "ColumnSqlMonthlyPartitionedChecksSpec",
    "ColumnSqlMonthlyRecurringChecksSpec",
    "ColumnSqlProfilingChecksSpec",
    "ColumnStatisticsCollectorsRootCategoriesSpec",
    "ColumnStatisticsModel",
    "ColumnStringBooleanPlaceholderPercentCheckSpec",
    "ColumnStringDatatypeChangedCheckSpec",
    "ColumnStringDatatypeDetectedCheckSpec",
    "ColumnStringEmptyCountCheckSpec",
    "ColumnStringEmptyPercentCheckSpec",
    "ColumnStringInvalidEmailCountCheckSpec",
    "ColumnStringInvalidIp4AddressCountCheckSpec",
    "ColumnStringInvalidIp6AddressCountCheckSpec",
    "ColumnStringInvalidUuidCountCheckSpec",
    "ColumnStringLengthAboveMaxLengthCountCheckSpec",
    "ColumnStringLengthAboveMaxLengthPercentCheckSpec",
    "ColumnStringLengthBelowMinLengthCountCheckSpec",
    "ColumnStringLengthBelowMinLengthPercentCheckSpec",
    "ColumnStringLengthInRangePercentCheckSpec",
    "ColumnStringMatchDateRegexPercentCheckSpec",
    "ColumnStringMatchNameRegexPercentCheckSpec",
    "ColumnStringMatchRegexPercentCheckSpec",
    "ColumnStringMaxLengthCheckSpec",
    "ColumnStringMeanLengthCheckSpec",
    "ColumnStringMinLengthCheckSpec",
    "ColumnStringNotMatchDateRegexCountCheckSpec",
    "ColumnStringNotMatchRegexCountCheckSpec",
    "ColumnStringNullPlaceholderCountCheckSpec",
    "ColumnStringNullPlaceholderPercentCheckSpec",
    "ColumnStringParsableToFloatPercentCheckSpec",
    "ColumnStringParsableToIntegerPercentCheckSpec",
    "ColumnStringsDailyPartitionedChecksSpec",
    "ColumnStringsDailyRecurringChecksSpec",
    "ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec",
    "ColumnStringsExpectedStringsInUseCountSensorParametersSpec",
    "ColumnStringsMonthlyPartitionedChecksSpec",
    "ColumnStringsMonthlyRecurringChecksSpec",
    "ColumnStringsProfilingChecksSpec",
    "ColumnStringsStatisticsCollectorsSpec",
    "ColumnStringsStringBooleanPlaceholderPercentSensorParametersSpec",
    "ColumnStringsStringDatatypeDetectSensorParametersSpec",
    "ColumnStringsStringDatatypeDetectStatisticsCollectorSpec",
    "ColumnStringsStringEmptyCountSensorParametersSpec",
    "ColumnStringsStringEmptyPercentSensorParametersSpec",
    "ColumnStringsStringInvalidEmailCountSensorParametersSpec",
    "ColumnStringsStringInvalidIp4AddressCountSensorParametersSpec",
    "ColumnStringsStringInvalidIp6AddressCountSensorParametersSpec",
    "ColumnStringsStringInvalidUuidCountSensorParametersSpec",
    "ColumnStringsStringLengthAboveMaxLengthCountSensorParametersSpec",
    "ColumnStringsStringLengthAboveMaxLengthPercentSensorParametersSpec",
    "ColumnStringsStringLengthBelowMinLengthCountSensorParametersSpec",
    "ColumnStringsStringLengthBelowMinLengthPercentSensorParametersSpec",
    "ColumnStringsStringLengthInRangePercentSensorParametersSpec",
    "ColumnStringsStringMatchDateRegexPercentSensorParametersSpec",
    "ColumnStringsStringMatchDateRegexPercentSensorParametersSpecDateFormats",
    "ColumnStringsStringMatchNameRegexPercentSensorParametersSpec",
    "ColumnStringsStringMatchRegexPercentSensorParametersSpec",
    "ColumnStringsStringMaxLengthSensorParametersSpec",
    "ColumnStringsStringMaxLengthStatisticsCollectorSpec",
    "ColumnStringsStringMeanLengthSensorParametersSpec",
    "ColumnStringsStringMeanLengthStatisticsCollectorSpec",
    "ColumnStringsStringMinLengthSensorParametersSpec",
    "ColumnStringsStringMinLengthStatisticsCollectorSpec",
    "ColumnStringsStringNotMatchDateRegexCountSensorParametersSpec",
    "ColumnStringsStringNotMatchDateRegexCountSensorParametersSpecDateFormats",
    "ColumnStringsStringNotMatchRegexCountSensorParametersSpec",
    "ColumnStringsStringNullPlaceholderCountSensorParametersSpec",
    "ColumnStringsStringNullPlaceholderPercentSensorParametersSpec",
    "ColumnStringsStringParsableToFloatPercentSensorParametersSpec",
    "ColumnStringsStringParsableToIntegerPercentSensorParametersSpec",
    "ColumnStringsStringSurroundedByWhitespaceCountSensorParametersSpec",
    "ColumnStringsStringSurroundedByWhitespacePercentSensorParametersSpec",
    "ColumnStringsStringValidCountryCodePercentSensorParametersSpec",
    "ColumnStringsStringValidCurrencyCodePercentSensorParametersSpec",
    "ColumnStringsStringValidDatePercentSensorParametersSpec",
    "ColumnStringsStringValidUuidPercentSensorParametersSpec",
    "ColumnStringsStringValueInSetPercentSensorParametersSpec",
    "ColumnStringsStringWhitespaceCountSensorParametersSpec",
    "ColumnStringsStringWhitespacePercentSensorParametersSpec",
    "ColumnStringSurroundedByWhitespaceCountCheckSpec",
    "ColumnStringSurroundedByWhitespacePercentCheckSpec",
    "ColumnStringValidCountryCodePercentCheckSpec",
    "ColumnStringValidCurrencyCodePercentCheckSpec",
    "ColumnStringValidDatesPercentCheckSpec",
    "ColumnStringValidUuidPercentCheckSpec",
    "ColumnStringValueInSetPercentCheckSpec",
    "ColumnStringWhitespaceCountCheckSpec",
    "ColumnStringWhitespacePercentCheckSpec",
    "ColumnSumInRangeCheckSpec",
    "ColumnTruePercentCheckSpec",
    "ColumnTypeSnapshotSpec",
    "ColumnUniqueCountCheckSpec",
    "ColumnUniquenessDailyPartitionedChecksSpec",
    "ColumnUniquenessDailyRecurringChecksSpec",
    "ColumnUniquenessDuplicateCountSensorParametersSpec",
    "ColumnUniquenessDuplicateCountStatisticsCollectorSpec",
    "ColumnUniquenessDuplicatePercentSensorParametersSpec",
    "ColumnUniquenessDuplicatePercentStatisticsCollectorSpec",
    "ColumnUniquenessMonthlyPartitionedChecksSpec",
    "ColumnUniquenessMonthlyRecurringChecksSpec",
    "ColumnUniquenessProfilingChecksSpec",
    "ColumnUniquenessStatisticsCollectorsSpec",
    "ColumnUniquenessUniqueCountSensorParametersSpec",
    "ColumnUniquenessUniqueCountStatisticsCollectorSpec",
    "ColumnUniquenessUniquePercentSensorParametersSpec",
    "ColumnUniquenessUniquePercentStatisticsCollectorSpec",
    "ColumnUniquePercentCheckSpec",
    "ColumnValidLatitudePercentCheckSpec",
    "ColumnValidLongitudePercentCheckSpec",
    "ColumnValueAboveMaxValueCountCheckSpec",
    "ColumnValueAboveMaxValuePercentCheckSpec",
    "ColumnValueBelowMinValueCountCheckSpec",
    "ColumnValueBelowMinValuePercentCheckSpec",
    "ColumnValuesInRangeIntegersPercentCheckSpec",
    "ColumnValuesInRangeNumericPercentCheckSpec",
    "CommentSpec",
    "CommonColumnModel",
    "ConnectionBasicModel",
    "ConnectionBasicModelProviderType",
    "ConnectionIncidentGroupingSpec",
    "ConnectionIncidentGroupingSpecGroupingLevel",
    "ConnectionIncidentGroupingSpecMinimumSeverity",
    "ConnectionModel",
    "ConnectionRemoteModel",
    "ConnectionRemoteModelConnectionStatus",
    "ConnectionSpec",
    "ConnectionSpecProviderType",
    "CustomCheckSpec",
    "CustomRuleParametersSpec",
    "CustomSensorParametersSpec",
    "DashboardsFolderSpec",
    "DashboardSpec",
    "DashboardSpecParameters",
    "DataStreamBasicModel",
    "DataStreamLevelSpec",
    "DataStreamLevelSpecSource",
    "DataStreamMappingSpec",
    "DataStreamModel",
    "DataStreamTrimmedModel",
    "DatatypeEqualsRuleParametersSpec",
    "DeleteStoredDataQueueJobParameters",
    "DqoJobChangeModel",
    "DqoJobChangeModelStatus",
    "DqoJobEntryParametersModel",
    "DqoJobHistoryEntryModel",
    "DqoJobHistoryEntryModelJobType",
    "DqoJobHistoryEntryModelStatus",
    "DqoJobQueueIncrementalSnapshotModel",
    "DqoJobQueueInitialSnapshotModel",
    "DqoQueueJobId",
    "DqoSettingsModel",
    "DqoSettingsModelProperties",
    "DqoSettingsModelPropertiesAdditionalProperty",
    "DqoUserProfileModel",
    "Duration",
    "EqualsInteger1RuleParametersSpec",
    "EqualsIntegerRuleParametersSpec",
    "ErrorDetailedSingleModel",
    "ErrorsDetailedDataModel",
    "ExternalLogEntry",
    "FindRecentIncidentsOnConnectionDirection",
    "FindRecentIncidentsOnConnectionOrder",
    "GetColumnPartitionedChecksOverviewTimeScale",
    "GetColumnPartitionedChecksResultsTimeScale",
    "GetColumnPartitionedChecksUIBasicTimeScale",
    "GetColumnPartitionedChecksUIFilterTimeScale",
    "GetColumnPartitionedChecksUITimeScale",
    "GetColumnPartitionedErrorsTimeScale",
    "GetColumnPartitionedSensorReadoutsTimeScale",
    "GetColumnRecurringChecksOverviewTimeScale",
    "GetColumnRecurringChecksResultsTimeScale",
    "GetColumnRecurringChecksUIBasicTimeScale",
    "GetColumnRecurringChecksUIFilterTimeScale",
    "GetColumnRecurringChecksUITimeScale",
    "GetColumnRecurringErrorsTimeScale",
    "GetColumnRecurringSensorReadoutsTimeScale",
    "GetConnectionSchedulingGroupSchedulingGroup",
    "GetIncidentIssuesDirection",
    "GetIncidentIssuesOrder",
    "GetSchemaPartitionedChecksTemplatesCheckTarget",
    "GetSchemaPartitionedChecksTemplatesTimeScale",
    "GetSchemaPartitionedChecksUICheckTarget",
    "GetSchemaPartitionedChecksUITimeScale",
    "GetSchemaProfilingChecksTemplatesCheckTarget",
    "GetSchemaProfilingChecksUICheckTarget",
    "GetSchemaRecurringChecksTemplatesCheckTarget",
    "GetSchemaRecurringChecksTemplatesTimeScale",
    "GetSchemaRecurringChecksUICheckTarget",
    "GetSchemaRecurringChecksUITimeScale",
    "GetTableColumnsPartitionedChecksUITimeScale",
    "GetTableColumnsRecurringChecksUITimeScale",
    "GetTablePartitionedChecksOverviewTimeScale",
    "GetTablePartitionedChecksResultsTimeScale",
    "GetTablePartitionedChecksTemplatesTimeScale",
    "GetTablePartitionedChecksUIBasicTimeScale",
    "GetTablePartitionedChecksUIFilterTimeScale",
    "GetTablePartitionedChecksUITimeScale",
    "GetTablePartitionedErrorsTimeScale",
    "GetTablePartitionedSensorReadoutsTimeScale",
    "GetTableRecurringChecksOverviewTimeScale",
    "GetTableRecurringChecksResultsTimeScale",
    "GetTableRecurringChecksTemplatesTimeScale",
    "GetTableRecurringChecksUIBasicTimeScale",
    "GetTableRecurringChecksUIFilterTimeScale",
    "GetTableRecurringChecksUITimeScale",
    "GetTableRecurringErrorsTimeScale",
    "GetTableRecurringSensorReadoutsTimeScale",
    "GetTableSchedulingGroupOverrideSchedulingGroup",
    "HierarchyIdModel",
    "HierarchyIdModelPathItem",
    "ImportSchemaQueueJobParameters",
    "ImportTablesQueueJobParameters",
    "IncidentDailyIssuesCount",
    "IncidentIssueHistogramModel",
    "IncidentIssueHistogramModelChecks",
    "IncidentIssueHistogramModelColumns",
    "IncidentIssueHistogramModelDays",
    "IncidentModel",
    "IncidentModelStatus",
    "IncidentsPerConnectionModel",
    "IncidentWebhookNotificationsSpec",
    "MaxCountRule0ParametersSpec",
    "MaxCountRule10ParametersSpec",
    "MaxCountRule15ParametersSpec",
    "MaxDaysRule1ParametersSpec",
    "MaxDaysRule2ParametersSpec",
    "MaxDaysRule7ParametersSpec",
    "MaxDiffPercentRule1ParametersSpec",
    "MaxDiffPercentRule2ParametersSpec",
    "MaxDiffPercentRule5ParametersSpec",
    "MaxFailuresRule0ParametersSpec",
    "MaxFailuresRule10ParametersSpec",
    "MaxFailuresRule5ParametersSpec",
    "MaxMissingRule0ParametersSpec",
    "MaxMissingRule1ParametersSpec",
    "MaxMissingRule2ParametersSpec",
    "MaxPercentRule0ParametersSpec",
    "MaxPercentRule100ParametersSpec",
    "MaxPercentRule1ParametersSpec",
    "MaxPercentRule2ParametersSpec",
    "MaxPercentRule5ParametersSpec",
    "MaxPercentRule95ParametersSpec",
    "MaxPercentRule99ParametersSpec",
    "MaxValueRuleParametersSpec",
    "MinCountRule0ParametersSpec",
    "MinCountRuleFatalParametersSpec",
    "MinCountRuleWarningParametersSpec",
    "MinPercentRule0ParametersSpec",
    "MinPercentRule100ParametersSpec",
    "MinPercentRule2ParametersSpec",
    "MinPercentRule5ParametersSpec",
    "MinPercentRule95ParametersSpec",
    "MinPercentRule99ParametersSpec",
    "MinValueRuleParametersSpec",
    "Mono",
    "MonoDqoQueueJobId",
    "MonoObject",
    "MysqlParametersSpec",
    "MysqlParametersSpecProperties",
    "Optional",
    "OptionalColumnDailyPartitionedCheckCategoriesSpec",
    "OptionalColumnDailyRecurringCheckCategoriesSpec",
    "OptionalColumnMonthlyPartitionedCheckCategoriesSpec",
    "OptionalColumnMonthlyRecurringCheckCategoriesSpec",
    "OptionalColumnProfilingCheckCategoriesSpec",
    "OptionalCommentsListSpec",
    "OptionalConnectionIncidentGroupingSpec",
    "OptionalDataStreamMappingSpec",
    "OptionalLabelSetSpec",
    "OptionalRecurringScheduleSpec",
    "OptionalTableDailyPartitionedCheckCategoriesSpec",
    "OptionalTableDailyRecurringCategoriesSpec",
    "OptionalTableIncidentGroupingSpec",
    "OptionalTableMonthlyPartitionedCheckCategoriesSpec",
    "OptionalTableMonthlyRecurringCheckCategoriesSpec",
    "OptionalTableProfilingCheckCategoriesSpec",
    "OptionalUICheckContainerModel",
    "OracleParametersSpec",
    "OracleParametersSpecProperties",
    "ParameterDefinitionSpec",
    "ParameterDefinitionSpecDataType",
    "ParameterDefinitionSpecDisplayHint",
    "PartitionIncrementalTimeWindowSpec",
    "PercentileMovingWithin30DaysRuleParametersSpec",
    "PercentileMovingWithin60DaysRuleParametersSpec",
    "PercentileMovingWithin7DaysRuleParametersSpec",
    "PhysicalTableName",
    "PostgresqlParametersSpec",
    "PostgresqlParametersSpecProperties",
    "ProviderSensorBasicModel",
    "ProviderSensorBasicModelProviderType",
    "ProviderSensorDefinitionSpec",
    "ProviderSensorDefinitionSpecParameters",
    "ProviderSensorDefinitionSpecType",
    "ProviderSensorModel",
    "ProviderSensorModelProviderType",
    "RecurringScheduleSpec",
    "RecurringSchedulesSpec",
    "RedshiftParametersSpec",
    "RedshiftParametersSpecProperties",
    "RepairStoredDataQueueJobParameters",
    "RuleBasicFolderModel",
    "RuleBasicFolderModelFolders",
    "RuleBasicModel",
    "RuleModel",
    "RuleModelMode",
    "RuleModelParameters",
    "RuleModelType",
    "RuleTimeWindowSettingsSpec",
    "RuleTimeWindowSettingsSpecHistoricDataPointGrouping",
    "RunChecksOnTableQueueJobParameters",
    "RunChecksQueueJobParameters",
    "RunChecksQueueJobResult",
    "RunChecksQueueJobResultHighestSeverity",
    "SchemaModel",
    "SchemaRemoteModel",
    "SensorBasicFolderModel",
    "SensorBasicFolderModelFolders",
    "SensorBasicModel",
    "SensorDefinitionSpec",
    "SensorDefinitionSpecParameters",
    "SensorModel",
    "SensorReadoutDetailedSingleModel",
    "SensorReadoutsDetailedDataModel",
    "SetIncidentStatusStatus",
    "SimilarCheckModel",
    "SimilarCheckModelCheckTarget",
    "SimilarCheckModelCheckType",
    "SimilarCheckModelTimeScale",
    "SnowflakeParametersSpec",
    "SnowflakeParametersSpecProperties",
    "SpringErrorPayload",
    "SqlServerParametersSpec",
    "SqlServerParametersSpecProperties",
    "StatisticsCollectorSearchFilters",
    "StatisticsCollectorSearchFiltersTarget",
    "StatisticsMetricModel",
    "StatisticsMetricModelResult",
    "StatisticsMetricModelResultDataType",
    "SynchronizeMultipleFoldersDqoQueueJobParameters",
    "SynchronizeMultipleFoldersDqoQueueJobParametersDirection",
    "SynchronizeRootFolderDqoQueueJobParameters",
    "SynchronizeRootFolderParameters",
    "SynchronizeRootFolderParametersDirection",
    "SynchronizeRootFolderParametersFolder",
    "TableAccuracyDailyRecurringChecksSpec",
    "TableAccuracyMonthlyRecurringChecksSpec",
    "TableAccuracyProfilingChecksSpec",
    "TableAccuracyRowCountMatchPercentCheckSpec",
    "TableAccuracyRowCountMatchPercentSensorParametersSpec",
    "TableAnomalyRowCount30DaysCheckSpec",
    "TableAnomalyRowCount60DaysCheckSpec",
    "TableAnomalyRowCount7DaysCheckSpec",
    "TableAnomalyRowCountChange30DaysCheckSpec",
    "TableAnomalyRowCountChange60DaysCheckSpec",
    "TableAnomalyRowCountChange7DaysCheckSpec",
    "TableAvailabilityCheckSpec",
    "TableAvailabilityDailyRecurringChecksSpec",
    "TableAvailabilityMonthlyRecurringChecksSpec",
    "TableAvailabilityProfilingChecksSpec",
    "TableAvailabilitySensorParametersSpec",
    "TableBasicModel",
    "TableChangeRowCountCheckSpec",
    "TableChangeRowCountSince30DaysCheckSpec",
    "TableChangeRowCountSince7DaysCheckSpec",
    "TableChangeRowCountSinceYesterdayCheckSpec",
    "TableColumnCountSensorParametersSpec",
    "TableColumnListOrderedHashSensorParametersSpec",
    "TableColumnListUnorderedHashSensorParametersSpec",
    "TableColumnsStatisticsModel",
    "TableColumnTypesHashSensorParametersSpec",
    "TableDailyPartitionedCheckCategoriesSpec",
    "TableDailyPartitionedCheckCategoriesSpecCustom",
    "TableDailyRecurringCategoriesSpec",
    "TableDailyRecurringCategoriesSpecCustom",
    "TableDataIngestionDelayCheckSpec",
    "TableDaysSinceMostRecentEventCheckSpec",
    "TableDaysSinceMostRecentIngestionCheckSpec",
    "TableIncidentGroupingSpec",
    "TableIncidentGroupingSpecGroupingLevel",
    "TableIncidentGroupingSpecMinimumSeverity",
    "TableModel",
    "TableMonthlyPartitionedCheckCategoriesSpec",
    "TableMonthlyPartitionedCheckCategoriesSpecCustom",
    "TableMonthlyRecurringCheckCategoriesSpec",
    "TableMonthlyRecurringCheckCategoriesSpecCustom",
    "TableOwnerSpec",
    "TablePartitionedChecksRootSpec",
    "TablePartitioningModel",
    "TablePartitionReloadLagCheckSpec",
    "TableProfilingCheckCategoriesSpec",
    "TableProfilingCheckCategoriesSpecCustom",
    "TableRecurringChecksSpec",
    "TableRemoteBasicModel",
    "TableRowCountCheckSpec",
    "TableSchemaColumnCountChangedCheckSpec",
    "TableSchemaColumnCountCheckSpec",
    "TableSchemaColumnListChangedCheckSpec",
    "TableSchemaColumnListOrOrderChangedCheckSpec",
    "TableSchemaColumnTypesChangedCheckSpec",
    "TableSchemaDailyRecurringChecksSpec",
    "TableSchemaMonthlyRecurringChecksSpec",
    "TableSchemaProfilingChecksSpec",
    "TableSpec",
    "TableSpecColumns",
    "TableSpecDataStreams",
    "TableSqlAggregatedExpressionSensorParametersSpec",
    "TableSqlAggregateExprCheckSpec",
    "TableSqlConditionFailedCountCheckSpec",
    "TableSqlConditionFailedCountSensorParametersSpec",
    "TableSqlConditionPassedPercentCheckSpec",
    "TableSqlConditionPassedPercentSensorParametersSpec",
    "TableSqlDailyPartitionedChecksSpec",
    "TableSqlDailyRecurringChecksSpec",
    "TableSqlMonthlyPartitionedChecksSpec",
    "TableSqlMonthlyRecurringChecksSpec",
    "TableSqlProfilingChecksSpec",
    "TableStatisticsCollectorsRootCategoriesSpec",
    "TableStatisticsModel",
    "TableTimelinessDailyPartitionedChecksSpec",
    "TableTimelinessDailyRecurringChecksSpec",
    "TableTimelinessDataIngestionDelaySensorParametersSpec",
    "TableTimelinessDaysSinceMostRecentEventSensorParametersSpec",
    "TableTimelinessDaysSinceMostRecentIngestionSensorParametersSpec",
    "TableTimelinessMonthlyPartitionedChecksSpec",
    "TableTimelinessMonthlyRecurringChecksSpec",
    "TableTimelinessPartitionReloadLagSensorParametersSpec",
    "TableTimelinessProfilingChecksSpec",
    "TableVolumeDailyPartitionedChecksSpec",
    "TableVolumeDailyRecurringChecksSpec",
    "TableVolumeMonthlyPartitionedChecksSpec",
    "TableVolumeMonthlyRecurringChecksSpec",
    "TableVolumeProfilingChecksSpec",
    "TableVolumeRowCountSensorParametersSpec",
    "TableVolumeRowCountStatisticsCollectorSpec",
    "TableVolumeStatisticsCollectorsSpec",
    "TemporalUnit",
    "TimestampColumnsSpec",
    "TimeWindowFilterParameters",
    "UIAllChecksModel",
    "UIAllChecksPatchParameters",
    "UIAllChecksPatchParametersSelectedTablesToColumns",
    "UIAllColumnChecksModel",
    "UIAllColumnChecksModelCheckTarget",
    "UIAllTableChecksModel",
    "UIAllTableChecksModelCheckTarget",
    "UICheckBasicModel",
    "UICheckContainerBasicModel",
    "UICheckContainerModel",
    "UICheckContainerModelEffectiveScheduleEnabledStatus",
    "UICheckContainerTypeModel",
    "UICheckContainerTypeModelCheckTimeScale",
    "UICheckContainerTypeModelCheckType",
    "UICheckModel",
    "UICheckModelCheckTarget",
    "UICheckModelConfigurationRequirementsErrorsItem",
    "UICheckModelScheduleEnabledStatus",
    "UIColumnChecksModel",
    "UIColumnChecksModelCheckContainers",
    "UIEffectiveScheduleModel",
    "UIEffectiveScheduleModelScheduleGroup",
    "UIEffectiveScheduleModelScheduleLevel",
    "UIFieldModel",
    "UIQualityCategoryModel",
    "UIRuleParametersModel",
    "UIRuleThresholdsModel",
    "UISchemaTableChecksModel",
    "UITableChecksModel",
    "UITableChecksModelCheckContainers",
    "UITableColumnChecksModel",
    "UpdateColumnPartitionedChecksUITimeScale",
    "UpdateColumnRecurringChecksUITimeScale",
    "UpdateConnectionSchedulingGroupSchedulingGroup",
    "UpdateTablePartitionedChecksUITimeScale",
    "UpdateTableRecurringChecksUITimeScale",
    "UpdateTableSchedulingGroupOverrideSchedulingGroup",
    "ValueChangedParametersSpec",
    "WithinChange1DayRuleParametersSpec",
    "WithinChange30DaysRuleParametersSpec",
    "WithinChange7DaysRuleParametersSpec",
    "WithinChangeRuleParametersSpec",
)
