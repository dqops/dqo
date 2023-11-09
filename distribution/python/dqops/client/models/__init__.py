""" Contains all the data models used in inputs/outputs """

from .all_checks_patch_parameters import AllChecksPatchParameters
from .all_checks_patch_parameters_selected_tables_to_columns import (
    AllChecksPatchParametersSelectedTablesToColumns,
)
from .anomaly_differencing_percentile_moving_average_30_days_rule_01_parameters_spec import (
    AnomalyDifferencingPercentileMovingAverage30DaysRule01ParametersSpec,
)
from .anomaly_differencing_percentile_moving_average_30_days_rule_1_parameters_spec import (
    AnomalyDifferencingPercentileMovingAverage30DaysRule1ParametersSpec,
)
from .anomaly_differencing_percentile_moving_average_30_days_rule_05_parameters_spec import (
    AnomalyDifferencingPercentileMovingAverage30DaysRule05ParametersSpec,
)
from .anomaly_differencing_percentile_moving_average_rule_01_parameters_spec import (
    AnomalyDifferencingPercentileMovingAverageRule01ParametersSpec,
)
from .anomaly_differencing_percentile_moving_average_rule_1_parameters_spec import (
    AnomalyDifferencingPercentileMovingAverageRule1ParametersSpec,
)
from .anomaly_differencing_percentile_moving_average_rule_05_parameters_spec import (
    AnomalyDifferencingPercentileMovingAverageRule05ParametersSpec,
)
from .anomaly_stationary_percentile_moving_average_30_days_rule_01_parameters_spec import (
    AnomalyStationaryPercentileMovingAverage30DaysRule01ParametersSpec,
)
from .anomaly_stationary_percentile_moving_average_30_days_rule_1_parameters_spec import (
    AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec,
)
from .anomaly_stationary_percentile_moving_average_30_days_rule_05_parameters_spec import (
    AnomalyStationaryPercentileMovingAverage30DaysRule05ParametersSpec,
)
from .anomaly_stationary_percentile_moving_average_rule_01_parameters_spec import (
    AnomalyStationaryPercentileMovingAverageRule01ParametersSpec,
)
from .anomaly_stationary_percentile_moving_average_rule_1_parameters_spec import (
    AnomalyStationaryPercentileMovingAverageRule1ParametersSpec,
)
from .anomaly_stationary_percentile_moving_average_rule_05_parameters_spec import (
    AnomalyStationaryPercentileMovingAverageRule05ParametersSpec,
)
from .authenticated_dashboard_model import AuthenticatedDashboardModel
from .between_floats_rule_parameters_spec import BetweenFloatsRuleParametersSpec
from .big_query_authentication_mode import BigQueryAuthenticationMode
from .big_query_jobs_create_project import BigQueryJobsCreateProject
from .big_query_parameters_spec import BigQueryParametersSpec
from .bulk_check_disable_parameters import BulkCheckDisableParameters
from .bulk_check_disable_parameters_selected_tables_to_columns import (
    BulkCheckDisableParametersSelectedTablesToColumns,
)
from .change_percent_1_day_rule_10_parameters_spec import (
    ChangePercent1DayRule10ParametersSpec,
)
from .change_percent_1_day_rule_20_parameters_spec import (
    ChangePercent1DayRule20ParametersSpec,
)
from .change_percent_1_day_rule_50_parameters_spec import (
    ChangePercent1DayRule50ParametersSpec,
)
from .change_percent_7_days_rule_10_parameters_spec import (
    ChangePercent7DaysRule10ParametersSpec,
)
from .change_percent_7_days_rule_20_parameters_spec import (
    ChangePercent7DaysRule20ParametersSpec,
)
from .change_percent_7_days_rule_50_parameters_spec import (
    ChangePercent7DaysRule50ParametersSpec,
)
from .change_percent_30_days_rule_10_parameters_spec import (
    ChangePercent30DaysRule10ParametersSpec,
)
from .change_percent_30_days_rule_20_parameters_spec import (
    ChangePercent30DaysRule20ParametersSpec,
)
from .change_percent_30_days_rule_50_parameters_spec import (
    ChangePercent30DaysRule50ParametersSpec,
)
from .change_percent_rule_10_parameters_spec import ChangePercentRule10ParametersSpec
from .change_percent_rule_20_parameters_spec import ChangePercentRule20ParametersSpec
from .change_percent_rule_50_parameters_spec import ChangePercentRule50ParametersSpec
from .check_configuration_model import CheckConfigurationModel
from .check_container_list_model import CheckContainerListModel
from .check_container_model import CheckContainerModel
from .check_container_type_model import CheckContainerTypeModel
from .check_current_data_quality_status_model import CheckCurrentDataQualityStatusModel
from .check_definition_folder_model import CheckDefinitionFolderModel
from .check_definition_folder_model_folders import CheckDefinitionFolderModelFolders
from .check_definition_list_model import CheckDefinitionListModel
from .check_definition_model import CheckDefinitionModel
from .check_list_model import CheckListModel
from .check_model import CheckModel
from .check_result_entry_model import CheckResultEntryModel
from .check_result_sort_order import CheckResultSortOrder
from .check_result_status import CheckResultStatus
from .check_results_list_model import CheckResultsListModel
from .check_results_overview_data_model import CheckResultsOverviewDataModel
from .check_run_schedule_group import CheckRunScheduleGroup
from .check_search_filters import CheckSearchFilters
from .check_target import CheckTarget
from .check_target_model import CheckTargetModel
from .check_template import CheckTemplate
from .check_time_scale import CheckTimeScale
from .check_type import CheckType
from .cloud_synchronization_folders_status_model import (
    CloudSynchronizationFoldersStatusModel,
)
from .collect_statistics_on_table_queue_job_parameters import (
    CollectStatisticsOnTableQueueJobParameters,
)
from .collect_statistics_queue_job_parameters import CollectStatisticsQueueJobParameters
from .collect_statistics_queue_job_result import CollectStatisticsQueueJobResult
from .collect_statistics_result import CollectStatisticsResult
from .column_accuracy_daily_monitoring_checks_spec import (
    ColumnAccuracyDailyMonitoringChecksSpec,
)
from .column_accuracy_daily_monitoring_checks_spec_custom_checks import (
    ColumnAccuracyDailyMonitoringChecksSpecCustomChecks,
)
from .column_accuracy_daily_partitioned_checks_spec import (
    ColumnAccuracyDailyPartitionedChecksSpec,
)
from .column_accuracy_daily_partitioned_checks_spec_custom_checks import (
    ColumnAccuracyDailyPartitionedChecksSpecCustomChecks,
)
from .column_accuracy_monthly_monitoring_checks_spec import (
    ColumnAccuracyMonthlyMonitoringChecksSpec,
)
from .column_accuracy_monthly_monitoring_checks_spec_custom_checks import (
    ColumnAccuracyMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_accuracy_monthly_partitioned_checks_spec import (
    ColumnAccuracyMonthlyPartitionedChecksSpec,
)
from .column_accuracy_monthly_partitioned_checks_spec_custom_checks import (
    ColumnAccuracyMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_accuracy_profiling_checks_spec import ColumnAccuracyProfilingChecksSpec
from .column_accuracy_profiling_checks_spec_custom_checks import (
    ColumnAccuracyProfilingChecksSpecCustomChecks,
)
from .column_accuracy_total_average_match_percent_check_spec import (
    ColumnAccuracyTotalAverageMatchPercentCheckSpec,
)
from .column_accuracy_total_average_match_percent_sensor_parameters_spec import (
    ColumnAccuracyTotalAverageMatchPercentSensorParametersSpec,
)
from .column_accuracy_total_max_match_percent_check_spec import (
    ColumnAccuracyTotalMaxMatchPercentCheckSpec,
)
from .column_accuracy_total_max_match_percent_sensor_parameters_spec import (
    ColumnAccuracyTotalMaxMatchPercentSensorParametersSpec,
)
from .column_accuracy_total_min_match_percent_check_spec import (
    ColumnAccuracyTotalMinMatchPercentCheckSpec,
)
from .column_accuracy_total_min_match_percent_sensor_parameters_spec import (
    ColumnAccuracyTotalMinMatchPercentSensorParametersSpec,
)
from .column_accuracy_total_not_null_count_match_percent_check_spec import (
    ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec,
)
from .column_accuracy_total_not_null_count_match_percent_sensor_parameters_spec import (
    ColumnAccuracyTotalNotNullCountMatchPercentSensorParametersSpec,
)
from .column_accuracy_total_sum_match_percent_check_spec import (
    ColumnAccuracyTotalSumMatchPercentCheckSpec,
)
from .column_accuracy_total_sum_match_percent_sensor_parameters_spec import (
    ColumnAccuracyTotalSumMatchPercentSensorParametersSpec,
)
from .column_anomaly_daily_monitoring_checks_spec import (
    ColumnAnomalyDailyMonitoringChecksSpec,
)
from .column_anomaly_daily_monitoring_checks_spec_custom_checks import (
    ColumnAnomalyDailyMonitoringChecksSpecCustomChecks,
)
from .column_anomaly_daily_partitioned_checks_spec import (
    ColumnAnomalyDailyPartitionedChecksSpec,
)
from .column_anomaly_daily_partitioned_checks_spec_custom_checks import (
    ColumnAnomalyDailyPartitionedChecksSpecCustomChecks,
)
from .column_anomaly_differencing_distinct_count_30_days_check_spec import (
    ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec,
)
from .column_anomaly_differencing_distinct_count_check_spec import (
    ColumnAnomalyDifferencingDistinctCountCheckSpec,
)
from .column_anomaly_differencing_sum_30_days_check_spec import (
    ColumnAnomalyDifferencingSum30DaysCheckSpec,
)
from .column_anomaly_differencing_sum_check_spec import (
    ColumnAnomalyDifferencingSumCheckSpec,
)
from .column_anomaly_monthly_monitoring_checks_spec import (
    ColumnAnomalyMonthlyMonitoringChecksSpec,
)
from .column_anomaly_monthly_monitoring_checks_spec_custom_checks import (
    ColumnAnomalyMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_anomaly_monthly_partitioned_checks_spec import (
    ColumnAnomalyMonthlyPartitionedChecksSpec,
)
from .column_anomaly_monthly_partitioned_checks_spec_custom_checks import (
    ColumnAnomalyMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_anomaly_profiling_checks_spec import ColumnAnomalyProfilingChecksSpec
from .column_anomaly_profiling_checks_spec_custom_checks import (
    ColumnAnomalyProfilingChecksSpecCustomChecks,
)
from .column_anomaly_stationary_distinct_percent_30_days_check_spec import (
    ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec,
)
from .column_anomaly_stationary_distinct_percent_check_spec import (
    ColumnAnomalyStationaryDistinctPercentCheckSpec,
)
from .column_anomaly_stationary_mean_30_days_check_spec import (
    ColumnAnomalyStationaryMean30DaysCheckSpec,
)
from .column_anomaly_stationary_mean_check_spec import (
    ColumnAnomalyStationaryMeanCheckSpec,
)
from .column_anomaly_stationary_median_30_days_check_spec import (
    ColumnAnomalyStationaryMedian30DaysCheckSpec,
)
from .column_anomaly_stationary_median_check_spec import (
    ColumnAnomalyStationaryMedianCheckSpec,
)
from .column_anomaly_stationary_null_percent_30_days_check_spec import (
    ColumnAnomalyStationaryNullPercent30DaysCheckSpec,
)
from .column_anomaly_stationary_null_percent_check_spec import (
    ColumnAnomalyStationaryNullPercentCheckSpec,
)
from .column_anomaly_stationary_partition_distinct_count_30_days_check_spec import (
    ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec,
)
from .column_anomaly_stationary_partition_distinct_count_check_spec import (
    ColumnAnomalyStationaryPartitionDistinctCountCheckSpec,
)
from .column_anomaly_stationary_partition_sum_30_days_check_spec import (
    ColumnAnomalyStationaryPartitionSum30DaysCheckSpec,
)
from .column_anomaly_stationary_partition_sum_check_spec import (
    ColumnAnomalyStationaryPartitionSumCheckSpec,
)
from .column_bool_daily_monitoring_checks_spec import (
    ColumnBoolDailyMonitoringChecksSpec,
)
from .column_bool_daily_monitoring_checks_spec_custom_checks import (
    ColumnBoolDailyMonitoringChecksSpecCustomChecks,
)
from .column_bool_daily_partitioned_checks_spec import (
    ColumnBoolDailyPartitionedChecksSpec,
)
from .column_bool_daily_partitioned_checks_spec_custom_checks import (
    ColumnBoolDailyPartitionedChecksSpecCustomChecks,
)
from .column_bool_false_percent_sensor_parameters_spec import (
    ColumnBoolFalsePercentSensorParametersSpec,
)
from .column_bool_monthly_monitoring_checks_spec import (
    ColumnBoolMonthlyMonitoringChecksSpec,
)
from .column_bool_monthly_monitoring_checks_spec_custom_checks import (
    ColumnBoolMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_bool_monthly_partitioned_checks_spec import (
    ColumnBoolMonthlyPartitionedChecksSpec,
)
from .column_bool_monthly_partitioned_checks_spec_custom_checks import (
    ColumnBoolMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_bool_profiling_checks_spec import ColumnBoolProfilingChecksSpec
from .column_bool_profiling_checks_spec_custom_checks import (
    ColumnBoolProfilingChecksSpecCustomChecks,
)
from .column_bool_true_percent_sensor_parameters_spec import (
    ColumnBoolTruePercentSensorParametersSpec,
)
from .column_change_distinct_count_check_spec import ColumnChangeDistinctCountCheckSpec
from .column_change_distinct_count_since_7_days_check_spec import (
    ColumnChangeDistinctCountSince7DaysCheckSpec,
)
from .column_change_distinct_count_since_30_days_check_spec import (
    ColumnChangeDistinctCountSince30DaysCheckSpec,
)
from .column_change_distinct_count_since_yesterday_check_spec import (
    ColumnChangeDistinctCountSinceYesterdayCheckSpec,
)
from .column_change_distinct_percent_check_spec import (
    ColumnChangeDistinctPercentCheckSpec,
)
from .column_change_distinct_percent_since_7_days_check_spec import (
    ColumnChangeDistinctPercentSince7DaysCheckSpec,
)
from .column_change_distinct_percent_since_30_days_check_spec import (
    ColumnChangeDistinctPercentSince30DaysCheckSpec,
)
from .column_change_distinct_percent_since_yesterday_check_spec import (
    ColumnChangeDistinctPercentSinceYesterdayCheckSpec,
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
from .column_change_null_percent_check_spec import ColumnChangeNullPercentCheckSpec
from .column_change_null_percent_since_7_days_check_spec import (
    ColumnChangeNullPercentSince7DaysCheckSpec,
)
from .column_change_null_percent_since_30_days_check_spec import (
    ColumnChangeNullPercentSince30DaysCheckSpec,
)
from .column_change_null_percent_since_yesterday_check_spec import (
    ColumnChangeNullPercentSinceYesterdayCheckSpec,
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
from .column_comparison_daily_monitoring_checks_spec import (
    ColumnComparisonDailyMonitoringChecksSpec,
)
from .column_comparison_daily_monitoring_checks_spec_custom_checks import (
    ColumnComparisonDailyMonitoringChecksSpecCustomChecks,
)
from .column_comparison_daily_partitioned_checks_spec import (
    ColumnComparisonDailyPartitionedChecksSpec,
)
from .column_comparison_daily_partitioned_checks_spec_custom_checks import (
    ColumnComparisonDailyPartitionedChecksSpecCustomChecks,
)
from .column_comparison_max_match_check_spec import ColumnComparisonMaxMatchCheckSpec
from .column_comparison_mean_match_check_spec import ColumnComparisonMeanMatchCheckSpec
from .column_comparison_min_match_check_spec import ColumnComparisonMinMatchCheckSpec
from .column_comparison_model import ColumnComparisonModel
from .column_comparison_monthly_monitoring_checks_spec import (
    ColumnComparisonMonthlyMonitoringChecksSpec,
)
from .column_comparison_monthly_monitoring_checks_spec_custom_checks import (
    ColumnComparisonMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_comparison_monthly_partitioned_checks_spec import (
    ColumnComparisonMonthlyPartitionedChecksSpec,
)
from .column_comparison_monthly_partitioned_checks_spec_custom_checks import (
    ColumnComparisonMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_comparison_not_null_count_match_check_spec import (
    ColumnComparisonNotNullCountMatchCheckSpec,
)
from .column_comparison_null_count_match_check_spec import (
    ColumnComparisonNullCountMatchCheckSpec,
)
from .column_comparison_profiling_checks_spec import ColumnComparisonProfilingChecksSpec
from .column_comparison_profiling_checks_spec_custom_checks import (
    ColumnComparisonProfilingChecksSpecCustomChecks,
)
from .column_comparison_sum_match_check_spec import ColumnComparisonSumMatchCheckSpec
from .column_current_data_quality_status_model import (
    ColumnCurrentDataQualityStatusModel,
)
from .column_current_data_quality_status_model_checks import (
    ColumnCurrentDataQualityStatusModelChecks,
)
from .column_daily_monitoring_check_categories_spec import (
    ColumnDailyMonitoringCheckCategoriesSpec,
)
from .column_daily_monitoring_check_categories_spec_comparisons import (
    ColumnDailyMonitoringCheckCategoriesSpecComparisons,
)
from .column_daily_monitoring_check_categories_spec_custom import (
    ColumnDailyMonitoringCheckCategoriesSpecCustom,
)
from .column_daily_partitioned_check_categories_spec import (
    ColumnDailyPartitionedCheckCategoriesSpec,
)
from .column_daily_partitioned_check_categories_spec_comparisons import (
    ColumnDailyPartitionedCheckCategoriesSpecComparisons,
)
from .column_daily_partitioned_check_categories_spec_custom import (
    ColumnDailyPartitionedCheckCategoriesSpecCustom,
)
from .column_datatype_daily_monitoring_checks_spec import (
    ColumnDatatypeDailyMonitoringChecksSpec,
)
from .column_datatype_daily_monitoring_checks_spec_custom_checks import (
    ColumnDatatypeDailyMonitoringChecksSpecCustomChecks,
)
from .column_datatype_daily_partitioned_checks_spec import (
    ColumnDatatypeDailyPartitionedChecksSpec,
)
from .column_datatype_daily_partitioned_checks_spec_custom_checks import (
    ColumnDatatypeDailyPartitionedChecksSpecCustomChecks,
)
from .column_datatype_monthly_monitoring_checks_spec import (
    ColumnDatatypeMonthlyMonitoringChecksSpec,
)
from .column_datatype_monthly_monitoring_checks_spec_custom_checks import (
    ColumnDatatypeMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_datatype_monthly_partitioned_checks_spec import (
    ColumnDatatypeMonthlyPartitionedChecksSpec,
)
from .column_datatype_monthly_partitioned_checks_spec_custom_checks import (
    ColumnDatatypeMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_datatype_profiling_checks_spec import ColumnDatatypeProfilingChecksSpec
from .column_datatype_profiling_checks_spec_custom_checks import (
    ColumnDatatypeProfilingChecksSpecCustomChecks,
)
from .column_datatype_string_datatype_changed_check_spec import (
    ColumnDatatypeStringDatatypeChangedCheckSpec,
)
from .column_datatype_string_datatype_detect_sensor_parameters_spec import (
    ColumnDatatypeStringDatatypeDetectSensorParametersSpec,
)
from .column_datatype_string_datatype_detected_check_spec import (
    ColumnDatatypeStringDatatypeDetectedCheckSpec,
)
from .column_date_values_in_future_percent_check_spec import (
    ColumnDateValuesInFuturePercentCheckSpec,
)
from .column_datetime_daily_monitoring_checks_spec import (
    ColumnDatetimeDailyMonitoringChecksSpec,
)
from .column_datetime_daily_monitoring_checks_spec_custom_checks import (
    ColumnDatetimeDailyMonitoringChecksSpecCustomChecks,
)
from .column_datetime_daily_partitioned_checks_spec import (
    ColumnDatetimeDailyPartitionedChecksSpec,
)
from .column_datetime_daily_partitioned_checks_spec_custom_checks import (
    ColumnDatetimeDailyPartitionedChecksSpecCustomChecks,
)
from .column_datetime_date_match_format_percent_check_spec import (
    ColumnDatetimeDateMatchFormatPercentCheckSpec,
)
from .column_datetime_date_match_format_percent_sensor_parameters_spec import (
    ColumnDatetimeDateMatchFormatPercentSensorParametersSpec,
)
from .column_datetime_date_values_in_future_percent_sensor_parameters_spec import (
    ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec,
)
from .column_datetime_monthly_monitoring_checks_spec import (
    ColumnDatetimeMonthlyMonitoringChecksSpec,
)
from .column_datetime_monthly_monitoring_checks_spec_custom_checks import (
    ColumnDatetimeMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_datetime_monthly_partitioned_checks_spec import (
    ColumnDatetimeMonthlyPartitionedChecksSpec,
)
from .column_datetime_monthly_partitioned_checks_spec_custom_checks import (
    ColumnDatetimeMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_datetime_profiling_checks_spec import ColumnDatetimeProfilingChecksSpec
from .column_datetime_profiling_checks_spec_custom_checks import (
    ColumnDatetimeProfilingChecksSpecCustomChecks,
)
from .column_datetime_value_in_range_date_percent_check_spec import (
    ColumnDatetimeValueInRangeDatePercentCheckSpec,
)
from .column_datetime_value_in_range_date_percent_sensor_parameters_spec import (
    ColumnDatetimeValueInRangeDatePercentSensorParametersSpec,
)
from .column_distinct_count_check_spec import ColumnDistinctCountCheckSpec
from .column_distinct_percent_check_spec import ColumnDistinctPercentCheckSpec
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
from .column_integrity_daily_monitoring_checks_spec import (
    ColumnIntegrityDailyMonitoringChecksSpec,
)
from .column_integrity_daily_monitoring_checks_spec_custom_checks import (
    ColumnIntegrityDailyMonitoringChecksSpecCustomChecks,
)
from .column_integrity_daily_partitioned_checks_spec import (
    ColumnIntegrityDailyPartitionedChecksSpec,
)
from .column_integrity_daily_partitioned_checks_spec_custom_checks import (
    ColumnIntegrityDailyPartitionedChecksSpecCustomChecks,
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
from .column_integrity_monthly_monitoring_checks_spec import (
    ColumnIntegrityMonthlyMonitoringChecksSpec,
)
from .column_integrity_monthly_monitoring_checks_spec_custom_checks import (
    ColumnIntegrityMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_integrity_monthly_partitioned_checks_spec import (
    ColumnIntegrityMonthlyPartitionedChecksSpec,
)
from .column_integrity_monthly_partitioned_checks_spec_custom_checks import (
    ColumnIntegrityMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_integrity_profiling_checks_spec import ColumnIntegrityProfilingChecksSpec
from .column_integrity_profiling_checks_spec_custom_checks import (
    ColumnIntegrityProfilingChecksSpecCustomChecks,
)
from .column_invalid_latitude_count_check_spec import (
    ColumnInvalidLatitudeCountCheckSpec,
)
from .column_invalid_longitude_count_check_spec import (
    ColumnInvalidLongitudeCountCheckSpec,
)
from .column_list_model import ColumnListModel
from .column_max_in_range_check_spec import ColumnMaxInRangeCheckSpec
from .column_mean_in_range_check_spec import ColumnMeanInRangeCheckSpec
from .column_median_in_range_check_spec import ColumnMedianInRangeCheckSpec
from .column_min_in_range_check_spec import ColumnMinInRangeCheckSpec
from .column_model import ColumnModel
from .column_monitoring_checks_root_spec import ColumnMonitoringChecksRootSpec
from .column_monthly_monitoring_check_categories_spec import (
    ColumnMonthlyMonitoringCheckCategoriesSpec,
)
from .column_monthly_monitoring_check_categories_spec_comparisons import (
    ColumnMonthlyMonitoringCheckCategoriesSpecComparisons,
)
from .column_monthly_monitoring_check_categories_spec_custom import (
    ColumnMonthlyMonitoringCheckCategoriesSpecCustom,
)
from .column_monthly_partitioned_check_categories_spec import (
    ColumnMonthlyPartitionedCheckCategoriesSpec,
)
from .column_monthly_partitioned_check_categories_spec_comparisons import (
    ColumnMonthlyPartitionedCheckCategoriesSpecComparisons,
)
from .column_monthly_partitioned_check_categories_spec_custom import (
    ColumnMonthlyPartitionedCheckCategoriesSpecCustom,
)
from .column_negative_count_check_spec import ColumnNegativeCountCheckSpec
from .column_negative_percent_check_spec import ColumnNegativePercentCheckSpec
from .column_non_negative_count_check_spec import ColumnNonNegativeCountCheckSpec
from .column_non_negative_percent_check_spec import ColumnNonNegativePercentCheckSpec
from .column_not_nulls_count_check_spec import ColumnNotNullsCountCheckSpec
from .column_not_nulls_percent_check_spec import ColumnNotNullsPercentCheckSpec
from .column_nulls_count_check_spec import ColumnNullsCountCheckSpec
from .column_nulls_daily_monitoring_checks_spec import (
    ColumnNullsDailyMonitoringChecksSpec,
)
from .column_nulls_daily_monitoring_checks_spec_custom_checks import (
    ColumnNullsDailyMonitoringChecksSpecCustomChecks,
)
from .column_nulls_daily_partitioned_checks_spec import (
    ColumnNullsDailyPartitionedChecksSpec,
)
from .column_nulls_daily_partitioned_checks_spec_custom_checks import (
    ColumnNullsDailyPartitionedChecksSpecCustomChecks,
)
from .column_nulls_monthly_monitoring_checks_spec import (
    ColumnNullsMonthlyMonitoringChecksSpec,
)
from .column_nulls_monthly_monitoring_checks_spec_custom_checks import (
    ColumnNullsMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_nulls_monthly_partitioned_checks_spec import (
    ColumnNullsMonthlyPartitionedChecksSpec,
)
from .column_nulls_monthly_partitioned_checks_spec_custom_checks import (
    ColumnNullsMonthlyPartitionedChecksSpecCustomChecks,
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
from .column_nulls_profiling_checks_spec_custom_checks import (
    ColumnNullsProfilingChecksSpecCustomChecks,
)
from .column_nulls_statistics_collectors_spec import ColumnNullsStatisticsCollectorsSpec
from .column_number_value_in_set_percent_check_spec import (
    ColumnNumberValueInSetPercentCheckSpec,
)
from .column_numeric_daily_monitoring_checks_spec import (
    ColumnNumericDailyMonitoringChecksSpec,
)
from .column_numeric_daily_monitoring_checks_spec_custom_checks import (
    ColumnNumericDailyMonitoringChecksSpecCustomChecks,
)
from .column_numeric_daily_partitioned_checks_spec import (
    ColumnNumericDailyPartitionedChecksSpec,
)
from .column_numeric_daily_partitioned_checks_spec_custom_checks import (
    ColumnNumericDailyPartitionedChecksSpecCustomChecks,
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
from .column_numeric_monthly_monitoring_checks_spec import (
    ColumnNumericMonthlyMonitoringChecksSpec,
)
from .column_numeric_monthly_monitoring_checks_spec_custom_checks import (
    ColumnNumericMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_numeric_monthly_partitioned_checks_spec import (
    ColumnNumericMonthlyPartitionedChecksSpec,
)
from .column_numeric_monthly_partitioned_checks_spec_custom_checks import (
    ColumnNumericMonthlyPartitionedChecksSpecCustomChecks,
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
from .column_numeric_profiling_checks_spec_custom_checks import (
    ColumnNumericProfilingChecksSpecCustomChecks,
)
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
from .column_pii_daily_monitoring_checks_spec import ColumnPiiDailyMonitoringChecksSpec
from .column_pii_daily_monitoring_checks_spec_custom_checks import (
    ColumnPiiDailyMonitoringChecksSpecCustomChecks,
)
from .column_pii_daily_partitioned_checks_spec import (
    ColumnPiiDailyPartitionedChecksSpec,
)
from .column_pii_daily_partitioned_checks_spec_custom_checks import (
    ColumnPiiDailyPartitionedChecksSpecCustomChecks,
)
from .column_pii_monthly_monitoring_checks_spec import (
    ColumnPiiMonthlyMonitoringChecksSpec,
)
from .column_pii_monthly_monitoring_checks_spec_custom_checks import (
    ColumnPiiMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_pii_monthly_partitioned_checks_spec import (
    ColumnPiiMonthlyPartitionedChecksSpec,
)
from .column_pii_monthly_partitioned_checks_spec_custom_checks import (
    ColumnPiiMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_pii_profiling_checks_spec import ColumnPiiProfilingChecksSpec
from .column_pii_profiling_checks_spec_custom_checks import (
    ColumnPiiProfilingChecksSpecCustomChecks,
)
from .column_population_stddev_in_range_check_spec import (
    ColumnPopulationStddevInRangeCheckSpec,
)
from .column_population_variance_in_range_check_spec import (
    ColumnPopulationVarianceInRangeCheckSpec,
)
from .column_profiling_check_categories_spec import ColumnProfilingCheckCategoriesSpec
from .column_profiling_check_categories_spec_comparisons import (
    ColumnProfilingCheckCategoriesSpecComparisons,
)
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
from .column_schema_daily_monitoring_checks_spec import (
    ColumnSchemaDailyMonitoringChecksSpec,
)
from .column_schema_daily_monitoring_checks_spec_custom_checks import (
    ColumnSchemaDailyMonitoringChecksSpecCustomChecks,
)
from .column_schema_monthly_monitoring_checks_spec import (
    ColumnSchemaMonthlyMonitoringChecksSpec,
)
from .column_schema_monthly_monitoring_checks_spec_custom_checks import (
    ColumnSchemaMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_schema_profiling_checks_spec import ColumnSchemaProfilingChecksSpec
from .column_schema_profiling_checks_spec_custom_checks import (
    ColumnSchemaProfilingChecksSpecCustomChecks,
)
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
from .column_sql_daily_monitoring_checks_spec import ColumnSqlDailyMonitoringChecksSpec
from .column_sql_daily_monitoring_checks_spec_custom_checks import (
    ColumnSqlDailyMonitoringChecksSpecCustomChecks,
)
from .column_sql_daily_partitioned_checks_spec import (
    ColumnSqlDailyPartitionedChecksSpec,
)
from .column_sql_daily_partitioned_checks_spec_custom_checks import (
    ColumnSqlDailyPartitionedChecksSpecCustomChecks,
)
from .column_sql_monthly_monitoring_checks_spec import (
    ColumnSqlMonthlyMonitoringChecksSpec,
)
from .column_sql_monthly_monitoring_checks_spec_custom_checks import (
    ColumnSqlMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_sql_monthly_partitioned_checks_spec import (
    ColumnSqlMonthlyPartitionedChecksSpec,
)
from .column_sql_monthly_partitioned_checks_spec_custom_checks import (
    ColumnSqlMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_sql_profiling_checks_spec import ColumnSqlProfilingChecksSpec
from .column_sql_profiling_checks_spec_custom_checks import (
    ColumnSqlProfilingChecksSpecCustomChecks,
)
from .column_statistics_collectors_root_categories_spec import (
    ColumnStatisticsCollectorsRootCategoriesSpec,
)
from .column_statistics_model import ColumnStatisticsModel
from .column_string_boolean_placeholder_percent_check_spec import (
    ColumnStringBooleanPlaceholderPercentCheckSpec,
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
from .column_strings_daily_monitoring_checks_spec import (
    ColumnStringsDailyMonitoringChecksSpec,
)
from .column_strings_daily_monitoring_checks_spec_custom_checks import (
    ColumnStringsDailyMonitoringChecksSpecCustomChecks,
)
from .column_strings_daily_partitioned_checks_spec import (
    ColumnStringsDailyPartitionedChecksSpec,
)
from .column_strings_daily_partitioned_checks_spec_custom_checks import (
    ColumnStringsDailyPartitionedChecksSpecCustomChecks,
)
from .column_strings_expected_strings_in_top_values_count_sensor_parameters_spec import (
    ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec,
)
from .column_strings_expected_strings_in_use_count_sensor_parameters_spec import (
    ColumnStringsExpectedStringsInUseCountSensorParametersSpec,
)
from .column_strings_monthly_monitoring_checks_spec import (
    ColumnStringsMonthlyMonitoringChecksSpec,
)
from .column_strings_monthly_monitoring_checks_spec_custom_checks import (
    ColumnStringsMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_strings_monthly_partitioned_checks_spec import (
    ColumnStringsMonthlyPartitionedChecksSpec,
)
from .column_strings_monthly_partitioned_checks_spec_custom_checks import (
    ColumnStringsMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_strings_profiling_checks_spec import ColumnStringsProfilingChecksSpec
from .column_strings_profiling_checks_spec_custom_checks import (
    ColumnStringsProfilingChecksSpecCustomChecks,
)
from .column_strings_statistics_collectors_spec import (
    ColumnStringsStatisticsCollectorsSpec,
)
from .column_strings_string_boolean_placeholder_percent_sensor_parameters_spec import (
    ColumnStringsStringBooleanPlaceholderPercentSensorParametersSpec,
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
from .column_uniqueness_daily_monitoring_checks_spec import (
    ColumnUniquenessDailyMonitoringChecksSpec,
)
from .column_uniqueness_daily_monitoring_checks_spec_custom_checks import (
    ColumnUniquenessDailyMonitoringChecksSpecCustomChecks,
)
from .column_uniqueness_daily_partitioned_checks_spec import (
    ColumnUniquenessDailyPartitionedChecksSpec,
)
from .column_uniqueness_daily_partitioned_checks_spec_custom_checks import (
    ColumnUniquenessDailyPartitionedChecksSpecCustomChecks,
)
from .column_uniqueness_distinct_count_sensor_parameters_spec import (
    ColumnUniquenessDistinctCountSensorParametersSpec,
)
from .column_uniqueness_distinct_count_statistics_collector_spec import (
    ColumnUniquenessDistinctCountStatisticsCollectorSpec,
)
from .column_uniqueness_distinct_percent_sensor_parameters_spec import (
    ColumnUniquenessDistinctPercentSensorParametersSpec,
)
from .column_uniqueness_distinct_percent_statistics_collector_spec import (
    ColumnUniquenessDistinctPercentStatisticsCollectorSpec,
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
from .column_uniqueness_monthly_monitoring_checks_spec import (
    ColumnUniquenessMonthlyMonitoringChecksSpec,
)
from .column_uniqueness_monthly_monitoring_checks_spec_custom_checks import (
    ColumnUniquenessMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_uniqueness_monthly_partitioned_checks_spec import (
    ColumnUniquenessMonthlyPartitionedChecksSpec,
)
from .column_uniqueness_monthly_partitioned_checks_spec_custom_checks import (
    ColumnUniquenessMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_uniqueness_profiling_checks_spec import ColumnUniquenessProfilingChecksSpec
from .column_uniqueness_profiling_checks_spec_custom_checks import (
    ColumnUniquenessProfilingChecksSpecCustomChecks,
)
from .column_uniqueness_statistics_collectors_spec import (
    ColumnUniquenessStatisticsCollectorsSpec,
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
from .compare_thresholds_model import CompareThresholdsModel
from .comparison_check_result_model import ComparisonCheckResultModel
from .connection_incident_grouping_spec import ConnectionIncidentGroupingSpec
from .connection_model import ConnectionModel
from .connection_spec import ConnectionSpec
from .connection_specification_model import ConnectionSpecificationModel
from .connection_test_model import ConnectionTestModel
from .connection_test_status import ConnectionTestStatus
from .credential_type import CredentialType
from .custom_check_spec import CustomCheckSpec
from .custom_rule_parameters_spec import CustomRuleParametersSpec
from .custom_sensor_parameters_spec import CustomSensorParametersSpec
from .dashboard_spec import DashboardSpec
from .dashboard_spec_parameters import DashboardSpecParameters
from .dashboards_folder_spec import DashboardsFolderSpec
from .data_delete_result_partition import DataDeleteResultPartition
from .data_grouping_configuration_list_model import DataGroupingConfigurationListModel
from .data_grouping_configuration_model import DataGroupingConfigurationModel
from .data_grouping_configuration_spec import DataGroupingConfigurationSpec
from .data_grouping_configuration_trimmed_model import (
    DataGroupingConfigurationTrimmedModel,
)
from .data_grouping_dimension_source import DataGroupingDimensionSource
from .data_grouping_dimension_spec import DataGroupingDimensionSpec
from .datatype_equals_rule_parameters_spec import DatatypeEqualsRuleParametersSpec
from .datetime_built_in_date_formats import DatetimeBuiltInDateFormats
from .default_schedules_spec import DefaultSchedulesSpec
from .delete_stored_data_queue_job_parameters import DeleteStoredDataQueueJobParameters
from .delete_stored_data_queue_job_result import DeleteStoredDataQueueJobResult
from .delete_stored_data_result import DeleteStoredDataResult
from .delete_stored_data_result_partition_results import (
    DeleteStoredDataResultPartitionResults,
)
from .display_hint import DisplayHint
from .dqo_cloud_user_model import DqoCloudUserModel
from .dqo_job_change_model import DqoJobChangeModel
from .dqo_job_entry_parameters_model import DqoJobEntryParametersModel
from .dqo_job_history_entry_model import DqoJobHistoryEntryModel
from .dqo_job_queue_incremental_snapshot_model import (
    DqoJobQueueIncrementalSnapshotModel,
)
from .dqo_job_queue_initial_snapshot_model import DqoJobQueueInitialSnapshotModel
from .dqo_job_status import DqoJobStatus
from .dqo_job_type import DqoJobType
from .dqo_queue_job_id import DqoQueueJobId
from .dqo_root import DqoRoot
from .dqo_settings_model import DqoSettingsModel
from .dqo_settings_model_properties import DqoSettingsModelProperties
from .dqo_settings_model_properties_additional_property import (
    DqoSettingsModelPropertiesAdditionalProperty,
)
from .dqo_user_profile_model import DqoUserProfileModel
from .dqo_user_role import DqoUserRole
from .duration import Duration
from .effective_schedule_level_model import EffectiveScheduleLevelModel
from .effective_schedule_model import EffectiveScheduleModel
from .equals_integer_1_rule_parameters_spec import EqualsInteger1RuleParametersSpec
from .equals_integer_rule_parameters_spec import EqualsIntegerRuleParametersSpec
from .error_entry_model import ErrorEntryModel
from .errors_list_model import ErrorsListModel
from .external_log_entry import ExternalLogEntry
from .field_model import FieldModel
from .file_synchronization_direction import FileSynchronizationDirection
from .folder_synchronization_status import FolderSynchronizationStatus
from .hierarchy_id_model import HierarchyIdModel
from .hierarchy_id_model_path_item import HierarchyIdModelPathItem
from .historic_data_points_grouping import HistoricDataPointsGrouping
from .import_schema_queue_job_parameters import ImportSchemaQueueJobParameters
from .import_tables_queue_job_parameters import ImportTablesQueueJobParameters
from .import_tables_queue_job_result import ImportTablesQueueJobResult
from .import_tables_result import ImportTablesResult
from .incident_daily_issues_count import IncidentDailyIssuesCount
from .incident_grouping_level import IncidentGroupingLevel
from .incident_issue_histogram_model import IncidentIssueHistogramModel
from .incident_issue_histogram_model_checks import IncidentIssueHistogramModelChecks
from .incident_issue_histogram_model_columns import IncidentIssueHistogramModelColumns
from .incident_issue_histogram_model_days import IncidentIssueHistogramModelDays
from .incident_model import IncidentModel
from .incident_sort_order import IncidentSortOrder
from .incident_status import IncidentStatus
from .incident_webhook_notifications_spec import IncidentWebhookNotificationsSpec
from .incidents_per_connection_model import IncidentsPerConnectionModel
from .max_count_rule_0_parameters_spec import MaxCountRule0ParametersSpec
from .max_count_rule_10_parameters_spec import MaxCountRule10ParametersSpec
from .max_count_rule_15_parameters_spec import MaxCountRule15ParametersSpec
from .max_days_rule_1_parameters_spec import MaxDaysRule1ParametersSpec
from .max_days_rule_2_parameters_spec import MaxDaysRule2ParametersSpec
from .max_days_rule_7_parameters_spec import MaxDaysRule7ParametersSpec
from .max_diff_percent_rule_0_parameters_spec import MaxDiffPercentRule0ParametersSpec
from .max_diff_percent_rule_1_parameters_spec import MaxDiffPercentRule1ParametersSpec
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
from .min_count_rule_1_parameters_spec import MinCountRule1ParametersSpec
from .min_count_rule_fatal_parameters_spec import MinCountRuleFatalParametersSpec
from .min_count_rule_warning_parameters_spec import MinCountRuleWarningParametersSpec
from .min_percent_rule_0_parameters_spec import MinPercentRule0ParametersSpec
from .min_percent_rule_2_parameters_spec import MinPercentRule2ParametersSpec
from .min_percent_rule_5_parameters_spec import MinPercentRule5ParametersSpec
from .min_percent_rule_95_parameters_spec import MinPercentRule95ParametersSpec
from .min_percent_rule_99_parameters_spec import MinPercentRule99ParametersSpec
from .min_percent_rule_100_parameters_spec import MinPercentRule100ParametersSpec
from .min_value_rule_parameters_spec import MinValueRuleParametersSpec
from .minimum_grouping_severity_level import MinimumGroupingSeverityLevel
from .monitoring_schedule_spec import MonitoringScheduleSpec
from .mono import Mono
from .mono_dqo_queue_job_id import MonoDqoQueueJobId
from .mono_object import MonoObject
from .mono_void import MonoVoid
from .my_sql_ssl_mode import MySqlSslMode
from .mysql_parameters_spec import MysqlParametersSpec
from .mysql_parameters_spec_properties import MysqlParametersSpecProperties
from .optional import Optional
from .optional_incident_webhook_notifications_spec import (
    OptionalIncidentWebhookNotificationsSpec,
)
from .optional_monitoring_schedule_spec import OptionalMonitoringScheduleSpec
from .oracle_parameters_spec import OracleParametersSpec
from .oracle_parameters_spec_properties import OracleParametersSpecProperties
from .parameter_data_type import ParameterDataType
from .parameter_definition_spec import ParameterDefinitionSpec
from .partition_incremental_time_window_spec import PartitionIncrementalTimeWindowSpec
from .physical_table_name import PhysicalTableName
from .postgresql_parameters_spec import PostgresqlParametersSpec
from .postgresql_parameters_spec_properties import PostgresqlParametersSpecProperties
from .postgresql_ssl_mode import PostgresqlSslMode
from .profiling_time_period import ProfilingTimePeriod
from .provider_sensor_definition_spec import ProviderSensorDefinitionSpec
from .provider_sensor_definition_spec_parameters import (
    ProviderSensorDefinitionSpecParameters,
)
from .provider_sensor_list_model import ProviderSensorListModel
from .provider_sensor_model import ProviderSensorModel
from .provider_sensor_runner_type import ProviderSensorRunnerType
from .provider_type import ProviderType
from .quality_category_model import QualityCategoryModel
from .redshift_parameters_spec import RedshiftParametersSpec
from .redshift_parameters_spec_properties import RedshiftParametersSpecProperties
from .remote_table_list_model import RemoteTableListModel
from .repair_stored_data_queue_job_parameters import RepairStoredDataQueueJobParameters
from .rule_folder_model import RuleFolderModel
from .rule_folder_model_folders import RuleFolderModelFolders
from .rule_list_model import RuleListModel
from .rule_model import RuleModel
from .rule_model_parameters import RuleModelParameters
from .rule_parameters_model import RuleParametersModel
from .rule_runner_type import RuleRunnerType
from .rule_severity_level import RuleSeverityLevel
from .rule_thresholds_model import RuleThresholdsModel
from .rule_time_window_mode import RuleTimeWindowMode
from .rule_time_window_settings_spec import RuleTimeWindowSettingsSpec
from .run_checks_on_table_parameters import RunChecksOnTableParameters
from .run_checks_parameters import RunChecksParameters
from .run_checks_queue_job_result import RunChecksQueueJobResult
from .run_checks_result import RunChecksResult
from .schedule_enabled_status_model import ScheduleEnabledStatusModel
from .schema_model import SchemaModel
from .schema_remote_model import SchemaRemoteModel
from .sensor_definition_spec import SensorDefinitionSpec
from .sensor_definition_spec_parameters import SensorDefinitionSpecParameters
from .sensor_folder_model import SensorFolderModel
from .sensor_folder_model_folders import SensorFolderModelFolders
from .sensor_list_model import SensorListModel
from .sensor_model import SensorModel
from .sensor_readout_entry_model import SensorReadoutEntryModel
from .sensor_readouts_list_model import SensorReadoutsListModel
from .shared_credential_list_model import SharedCredentialListModel
from .shared_credential_model import SharedCredentialModel
from .similar_check_model import SimilarCheckModel
from .snowflake_parameters_spec import SnowflakeParametersSpec
from .snowflake_parameters_spec_properties import SnowflakeParametersSpecProperties
from .sort_direction import SortDirection
from .spring_error_payload import SpringErrorPayload
from .sql_server_parameters_spec import SqlServerParametersSpec
from .sql_server_parameters_spec_properties import SqlServerParametersSpecProperties
from .statistics_collector_search_filters import StatisticsCollectorSearchFilters
from .statistics_collector_target import StatisticsCollectorTarget
from .statistics_data_scope import StatisticsDataScope
from .statistics_metric_model import StatisticsMetricModel
from .statistics_metric_model_result import StatisticsMetricModelResult
from .statistics_result_data_type import StatisticsResultDataType
from .strings_built_in_date_formats import StringsBuiltInDateFormats
from .synchronize_multiple_folders_dqo_queue_job_parameters import (
    SynchronizeMultipleFoldersDqoQueueJobParameters,
)
from .synchronize_multiple_folders_queue_job_result import (
    SynchronizeMultipleFoldersQueueJobResult,
)
from .synchronize_root_folder_dqo_queue_job_parameters import (
    SynchronizeRootFolderDqoQueueJobParameters,
)
from .synchronize_root_folder_parameters import SynchronizeRootFolderParameters
from .table_accuracy_daily_monitoring_checks_spec import (
    TableAccuracyDailyMonitoringChecksSpec,
)
from .table_accuracy_daily_monitoring_checks_spec_custom_checks import (
    TableAccuracyDailyMonitoringChecksSpecCustomChecks,
)
from .table_accuracy_monthly_monitoring_checks_spec import (
    TableAccuracyMonthlyMonitoringChecksSpec,
)
from .table_accuracy_monthly_monitoring_checks_spec_custom_checks import (
    TableAccuracyMonthlyMonitoringChecksSpecCustomChecks,
)
from .table_accuracy_profiling_checks_spec import TableAccuracyProfilingChecksSpec
from .table_accuracy_profiling_checks_spec_custom_checks import (
    TableAccuracyProfilingChecksSpecCustomChecks,
)
from .table_accuracy_total_row_count_match_percent_check_spec import (
    TableAccuracyTotalRowCountMatchPercentCheckSpec,
)
from .table_accuracy_total_row_count_match_percent_sensor_parameters_spec import (
    TableAccuracyTotalRowCountMatchPercentSensorParametersSpec,
)
from .table_anomaly_differencing_row_count_30_days_check_spec import (
    TableAnomalyDifferencingRowCount30DaysCheckSpec,
)
from .table_anomaly_differencing_row_count_check_spec import (
    TableAnomalyDifferencingRowCountCheckSpec,
)
from .table_anomaly_stationary_partition_row_count_30_days_check_spec import (
    TableAnomalyStationaryPartitionRowCount30DaysCheckSpec,
)
from .table_anomaly_stationary_partition_row_count_check_spec import (
    TableAnomalyStationaryPartitionRowCountCheckSpec,
)
from .table_availability_check_spec import TableAvailabilityCheckSpec
from .table_availability_daily_monitoring_checks_spec import (
    TableAvailabilityDailyMonitoringChecksSpec,
)
from .table_availability_daily_monitoring_checks_spec_custom_checks import (
    TableAvailabilityDailyMonitoringChecksSpecCustomChecks,
)
from .table_availability_monthly_monitoring_checks_spec import (
    TableAvailabilityMonthlyMonitoringChecksSpec,
)
from .table_availability_monthly_monitoring_checks_spec_custom_checks import (
    TableAvailabilityMonthlyMonitoringChecksSpecCustomChecks,
)
from .table_availability_profiling_checks_spec import (
    TableAvailabilityProfilingChecksSpec,
)
from .table_availability_profiling_checks_spec_custom_checks import (
    TableAvailabilityProfilingChecksSpecCustomChecks,
)
from .table_availability_sensor_parameters_spec import (
    TableAvailabilitySensorParametersSpec,
)
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
from .table_comparison_column_count_match_check_spec import (
    TableComparisonColumnCountMatchCheckSpec,
)
from .table_comparison_column_results_model import TableComparisonColumnResultsModel
from .table_comparison_column_results_model_column_comparison_results import (
    TableComparisonColumnResultsModelColumnComparisonResults,
)
from .table_comparison_configuration_model import TableComparisonConfigurationModel
from .table_comparison_configuration_spec import TableComparisonConfigurationSpec
from .table_comparison_daily_monitoring_checks_spec import (
    TableComparisonDailyMonitoringChecksSpec,
)
from .table_comparison_daily_monitoring_checks_spec_custom_checks import (
    TableComparisonDailyMonitoringChecksSpecCustomChecks,
)
from .table_comparison_daily_partitioned_checks_spec import (
    TableComparisonDailyPartitionedChecksSpec,
)
from .table_comparison_daily_partitioned_checks_spec_custom_checks import (
    TableComparisonDailyPartitionedChecksSpecCustomChecks,
)
from .table_comparison_grouping_column_pair_model import (
    TableComparisonGroupingColumnPairModel,
)
from .table_comparison_grouping_columns_pair_spec import (
    TableComparisonGroupingColumnsPairSpec,
)
from .table_comparison_model import TableComparisonModel
from .table_comparison_monthly_monitoring_checks_spec import (
    TableComparisonMonthlyMonitoringChecksSpec,
)
from .table_comparison_monthly_monitoring_checks_spec_custom_checks import (
    TableComparisonMonthlyMonitoringChecksSpecCustomChecks,
)
from .table_comparison_monthly_partitioned_checks_spec import (
    TableComparisonMonthlyPartitionedChecksSpec,
)
from .table_comparison_monthly_partitioned_checks_spec_custom_checks import (
    TableComparisonMonthlyPartitionedChecksSpecCustomChecks,
)
from .table_comparison_profiling_checks_spec import TableComparisonProfilingChecksSpec
from .table_comparison_profiling_checks_spec_custom_checks import (
    TableComparisonProfilingChecksSpecCustomChecks,
)
from .table_comparison_results_model import TableComparisonResultsModel
from .table_comparison_results_model_column_comparison_results import (
    TableComparisonResultsModelColumnComparisonResults,
)
from .table_comparison_results_model_table_comparison_results import (
    TableComparisonResultsModelTableComparisonResults,
)
from .table_comparison_row_count_match_check_spec import (
    TableComparisonRowCountMatchCheckSpec,
)
from .table_current_data_quality_status_model import TableCurrentDataQualityStatusModel
from .table_current_data_quality_status_model_checks import (
    TableCurrentDataQualityStatusModelChecks,
)
from .table_current_data_quality_status_model_columns import (
    TableCurrentDataQualityStatusModelColumns,
)
from .table_daily_monitoring_check_categories_spec import (
    TableDailyMonitoringCheckCategoriesSpec,
)
from .table_daily_monitoring_check_categories_spec_comparisons import (
    TableDailyMonitoringCheckCategoriesSpecComparisons,
)
from .table_daily_monitoring_check_categories_spec_custom import (
    TableDailyMonitoringCheckCategoriesSpecCustom,
)
from .table_daily_partitioned_check_categories_spec import (
    TableDailyPartitionedCheckCategoriesSpec,
)
from .table_daily_partitioned_check_categories_spec_comparisons import (
    TableDailyPartitionedCheckCategoriesSpecComparisons,
)
from .table_daily_partitioned_check_categories_spec_custom import (
    TableDailyPartitionedCheckCategoriesSpecCustom,
)
from .table_data_freshness_check_spec import TableDataFreshnessCheckSpec
from .table_data_ingestion_delay_check_spec import TableDataIngestionDelayCheckSpec
from .table_data_staleness_check_spec import TableDataStalenessCheckSpec
from .table_incident_grouping_spec import TableIncidentGroupingSpec
from .table_list_model import TableListModel
from .table_model import TableModel
from .table_monitoring_checks_spec import TableMonitoringChecksSpec
from .table_monthly_monitoring_check_categories_spec import (
    TableMonthlyMonitoringCheckCategoriesSpec,
)
from .table_monthly_monitoring_check_categories_spec_comparisons import (
    TableMonthlyMonitoringCheckCategoriesSpecComparisons,
)
from .table_monthly_monitoring_check_categories_spec_custom import (
    TableMonthlyMonitoringCheckCategoriesSpecCustom,
)
from .table_monthly_partitioned_check_categories_spec import (
    TableMonthlyPartitionedCheckCategoriesSpec,
)
from .table_monthly_partitioned_check_categories_spec_comparisons import (
    TableMonthlyPartitionedCheckCategoriesSpecComparisons,
)
from .table_monthly_partitioned_check_categories_spec_custom import (
    TableMonthlyPartitionedCheckCategoriesSpecCustom,
)
from .table_owner_spec import TableOwnerSpec
from .table_partition_reload_lag_check_spec import TablePartitionReloadLagCheckSpec
from .table_partitioned_checks_root_spec import TablePartitionedChecksRootSpec
from .table_partitioning_model import TablePartitioningModel
from .table_profiling_check_categories_spec import TableProfilingCheckCategoriesSpec
from .table_profiling_check_categories_spec_comparisons import (
    TableProfilingCheckCategoriesSpecComparisons,
)
from .table_profiling_check_categories_spec_custom import (
    TableProfilingCheckCategoriesSpecCustom,
)
from .table_row_count_check_spec import TableRowCountCheckSpec
from .table_schema_column_count_changed_check_spec import (
    TableSchemaColumnCountChangedCheckSpec,
)
from .table_schema_column_count_check_spec import TableSchemaColumnCountCheckSpec
from .table_schema_column_count_statistics_collector_spec import (
    TableSchemaColumnCountStatisticsCollectorSpec,
)
from .table_schema_column_list_changed_check_spec import (
    TableSchemaColumnListChangedCheckSpec,
)
from .table_schema_column_list_or_order_changed_check_spec import (
    TableSchemaColumnListOrOrderChangedCheckSpec,
)
from .table_schema_column_types_changed_check_spec import (
    TableSchemaColumnTypesChangedCheckSpec,
)
from .table_schema_daily_monitoring_checks_spec import (
    TableSchemaDailyMonitoringChecksSpec,
)
from .table_schema_daily_monitoring_checks_spec_custom_checks import (
    TableSchemaDailyMonitoringChecksSpecCustomChecks,
)
from .table_schema_monthly_monitoring_checks_spec import (
    TableSchemaMonthlyMonitoringChecksSpec,
)
from .table_schema_monthly_monitoring_checks_spec_custom_checks import (
    TableSchemaMonthlyMonitoringChecksSpecCustomChecks,
)
from .table_schema_profiling_checks_spec import TableSchemaProfilingChecksSpec
from .table_schema_profiling_checks_spec_custom_checks import (
    TableSchemaProfilingChecksSpecCustomChecks,
)
from .table_schema_statistics_collectors_spec import TableSchemaStatisticsCollectorsSpec
from .table_spec import TableSpec
from .table_spec_columns import TableSpecColumns
from .table_spec_groupings import TableSpecGroupings
from .table_spec_table_comparisons import TableSpecTableComparisons
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
from .table_sql_daily_monitoring_checks_spec import TableSqlDailyMonitoringChecksSpec
from .table_sql_daily_monitoring_checks_spec_custom_checks import (
    TableSqlDailyMonitoringChecksSpecCustomChecks,
)
from .table_sql_daily_partitioned_checks_spec import TableSqlDailyPartitionedChecksSpec
from .table_sql_daily_partitioned_checks_spec_custom_checks import (
    TableSqlDailyPartitionedChecksSpecCustomChecks,
)
from .table_sql_monthly_monitoring_checks_spec import (
    TableSqlMonthlyMonitoringChecksSpec,
)
from .table_sql_monthly_monitoring_checks_spec_custom_checks import (
    TableSqlMonthlyMonitoringChecksSpecCustomChecks,
)
from .table_sql_monthly_partitioned_checks_spec import (
    TableSqlMonthlyPartitionedChecksSpec,
)
from .table_sql_monthly_partitioned_checks_spec_custom_checks import (
    TableSqlMonthlyPartitionedChecksSpecCustomChecks,
)
from .table_sql_profiling_checks_spec import TableSqlProfilingChecksSpec
from .table_sql_profiling_checks_spec_custom_checks import (
    TableSqlProfilingChecksSpecCustomChecks,
)
from .table_statistics_collectors_root_categories_spec import (
    TableStatisticsCollectorsRootCategoriesSpec,
)
from .table_statistics_model import TableStatisticsModel
from .table_timeliness_daily_monitoring_checks_spec import (
    TableTimelinessDailyMonitoringChecksSpec,
)
from .table_timeliness_daily_monitoring_checks_spec_custom_checks import (
    TableTimelinessDailyMonitoringChecksSpecCustomChecks,
)
from .table_timeliness_daily_partitioned_checks_spec import (
    TableTimelinessDailyPartitionedChecksSpec,
)
from .table_timeliness_daily_partitioned_checks_spec_custom_checks import (
    TableTimelinessDailyPartitionedChecksSpecCustomChecks,
)
from .table_timeliness_data_freshness_sensor_parameters_spec import (
    TableTimelinessDataFreshnessSensorParametersSpec,
)
from .table_timeliness_data_ingestion_delay_sensor_parameters_spec import (
    TableTimelinessDataIngestionDelaySensorParametersSpec,
)
from .table_timeliness_data_staleness_sensor_parameters_spec import (
    TableTimelinessDataStalenessSensorParametersSpec,
)
from .table_timeliness_monthly_monitoring_checks_spec import (
    TableTimelinessMonthlyMonitoringChecksSpec,
)
from .table_timeliness_monthly_monitoring_checks_spec_custom_checks import (
    TableTimelinessMonthlyMonitoringChecksSpecCustomChecks,
)
from .table_timeliness_monthly_partitioned_checks_spec import (
    TableTimelinessMonthlyPartitionedChecksSpec,
)
from .table_timeliness_monthly_partitioned_checks_spec_custom_checks import (
    TableTimelinessMonthlyPartitionedChecksSpecCustomChecks,
)
from .table_timeliness_partition_reload_lag_sensor_parameters_spec import (
    TableTimelinessPartitionReloadLagSensorParametersSpec,
)
from .table_timeliness_profiling_checks_spec import TableTimelinessProfilingChecksSpec
from .table_timeliness_profiling_checks_spec_custom_checks import (
    TableTimelinessProfilingChecksSpecCustomChecks,
)
from .table_volume_daily_monitoring_checks_spec import (
    TableVolumeDailyMonitoringChecksSpec,
)
from .table_volume_daily_monitoring_checks_spec_custom_checks import (
    TableVolumeDailyMonitoringChecksSpecCustomChecks,
)
from .table_volume_daily_partitioned_checks_spec import (
    TableVolumeDailyPartitionedChecksSpec,
)
from .table_volume_daily_partitioned_checks_spec_custom_checks import (
    TableVolumeDailyPartitionedChecksSpecCustomChecks,
)
from .table_volume_monthly_monitoring_checks_spec import (
    TableVolumeMonthlyMonitoringChecksSpec,
)
from .table_volume_monthly_monitoring_checks_spec_custom_checks import (
    TableVolumeMonthlyMonitoringChecksSpecCustomChecks,
)
from .table_volume_monthly_partitioned_checks_spec import (
    TableVolumeMonthlyPartitionedChecksSpec,
)
from .table_volume_monthly_partitioned_checks_spec_custom_checks import (
    TableVolumeMonthlyPartitionedChecksSpecCustomChecks,
)
from .table_volume_profiling_checks_spec import TableVolumeProfilingChecksSpec
from .table_volume_profiling_checks_spec_custom_checks import (
    TableVolumeProfilingChecksSpecCustomChecks,
)
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
from .value_changed_parameters_spec import ValueChangedParametersSpec

__all__ = (
    "AllChecksPatchParameters",
    "AllChecksPatchParametersSelectedTablesToColumns",
    "AnomalyDifferencingPercentileMovingAverage30DaysRule01ParametersSpec",
    "AnomalyDifferencingPercentileMovingAverage30DaysRule05ParametersSpec",
    "AnomalyDifferencingPercentileMovingAverage30DaysRule1ParametersSpec",
    "AnomalyDifferencingPercentileMovingAverageRule01ParametersSpec",
    "AnomalyDifferencingPercentileMovingAverageRule05ParametersSpec",
    "AnomalyDifferencingPercentileMovingAverageRule1ParametersSpec",
    "AnomalyStationaryPercentileMovingAverage30DaysRule01ParametersSpec",
    "AnomalyStationaryPercentileMovingAverage30DaysRule05ParametersSpec",
    "AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec",
    "AnomalyStationaryPercentileMovingAverageRule01ParametersSpec",
    "AnomalyStationaryPercentileMovingAverageRule05ParametersSpec",
    "AnomalyStationaryPercentileMovingAverageRule1ParametersSpec",
    "AuthenticatedDashboardModel",
    "BetweenFloatsRuleParametersSpec",
    "BigQueryAuthenticationMode",
    "BigQueryJobsCreateProject",
    "BigQueryParametersSpec",
    "BulkCheckDisableParameters",
    "BulkCheckDisableParametersSelectedTablesToColumns",
    "ChangePercent1DayRule10ParametersSpec",
    "ChangePercent1DayRule20ParametersSpec",
    "ChangePercent1DayRule50ParametersSpec",
    "ChangePercent30DaysRule10ParametersSpec",
    "ChangePercent30DaysRule20ParametersSpec",
    "ChangePercent30DaysRule50ParametersSpec",
    "ChangePercent7DaysRule10ParametersSpec",
    "ChangePercent7DaysRule20ParametersSpec",
    "ChangePercent7DaysRule50ParametersSpec",
    "ChangePercentRule10ParametersSpec",
    "ChangePercentRule20ParametersSpec",
    "ChangePercentRule50ParametersSpec",
    "CheckConfigurationModel",
    "CheckContainerListModel",
    "CheckContainerModel",
    "CheckContainerTypeModel",
    "CheckCurrentDataQualityStatusModel",
    "CheckDefinitionFolderModel",
    "CheckDefinitionFolderModelFolders",
    "CheckDefinitionListModel",
    "CheckDefinitionModel",
    "CheckListModel",
    "CheckModel",
    "CheckResultEntryModel",
    "CheckResultsListModel",
    "CheckResultSortOrder",
    "CheckResultsOverviewDataModel",
    "CheckResultStatus",
    "CheckRunScheduleGroup",
    "CheckSearchFilters",
    "CheckTarget",
    "CheckTargetModel",
    "CheckTemplate",
    "CheckTimeScale",
    "CheckType",
    "CloudSynchronizationFoldersStatusModel",
    "CollectStatisticsOnTableQueueJobParameters",
    "CollectStatisticsQueueJobParameters",
    "CollectStatisticsQueueJobResult",
    "CollectStatisticsResult",
    "ColumnAccuracyDailyMonitoringChecksSpec",
    "ColumnAccuracyDailyMonitoringChecksSpecCustomChecks",
    "ColumnAccuracyDailyPartitionedChecksSpec",
    "ColumnAccuracyDailyPartitionedChecksSpecCustomChecks",
    "ColumnAccuracyMonthlyMonitoringChecksSpec",
    "ColumnAccuracyMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnAccuracyMonthlyPartitionedChecksSpec",
    "ColumnAccuracyMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnAccuracyProfilingChecksSpec",
    "ColumnAccuracyProfilingChecksSpecCustomChecks",
    "ColumnAccuracyTotalAverageMatchPercentCheckSpec",
    "ColumnAccuracyTotalAverageMatchPercentSensorParametersSpec",
    "ColumnAccuracyTotalMaxMatchPercentCheckSpec",
    "ColumnAccuracyTotalMaxMatchPercentSensorParametersSpec",
    "ColumnAccuracyTotalMinMatchPercentCheckSpec",
    "ColumnAccuracyTotalMinMatchPercentSensorParametersSpec",
    "ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec",
    "ColumnAccuracyTotalNotNullCountMatchPercentSensorParametersSpec",
    "ColumnAccuracyTotalSumMatchPercentCheckSpec",
    "ColumnAccuracyTotalSumMatchPercentSensorParametersSpec",
    "ColumnAnomalyDailyMonitoringChecksSpec",
    "ColumnAnomalyDailyMonitoringChecksSpecCustomChecks",
    "ColumnAnomalyDailyPartitionedChecksSpec",
    "ColumnAnomalyDailyPartitionedChecksSpecCustomChecks",
    "ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec",
    "ColumnAnomalyDifferencingDistinctCountCheckSpec",
    "ColumnAnomalyDifferencingSum30DaysCheckSpec",
    "ColumnAnomalyDifferencingSumCheckSpec",
    "ColumnAnomalyMonthlyMonitoringChecksSpec",
    "ColumnAnomalyMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnAnomalyMonthlyPartitionedChecksSpec",
    "ColumnAnomalyMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnAnomalyProfilingChecksSpec",
    "ColumnAnomalyProfilingChecksSpecCustomChecks",
    "ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec",
    "ColumnAnomalyStationaryDistinctPercentCheckSpec",
    "ColumnAnomalyStationaryMean30DaysCheckSpec",
    "ColumnAnomalyStationaryMeanCheckSpec",
    "ColumnAnomalyStationaryMedian30DaysCheckSpec",
    "ColumnAnomalyStationaryMedianCheckSpec",
    "ColumnAnomalyStationaryNullPercent30DaysCheckSpec",
    "ColumnAnomalyStationaryNullPercentCheckSpec",
    "ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec",
    "ColumnAnomalyStationaryPartitionDistinctCountCheckSpec",
    "ColumnAnomalyStationaryPartitionSum30DaysCheckSpec",
    "ColumnAnomalyStationaryPartitionSumCheckSpec",
    "ColumnBoolDailyMonitoringChecksSpec",
    "ColumnBoolDailyMonitoringChecksSpecCustomChecks",
    "ColumnBoolDailyPartitionedChecksSpec",
    "ColumnBoolDailyPartitionedChecksSpecCustomChecks",
    "ColumnBoolFalsePercentSensorParametersSpec",
    "ColumnBoolMonthlyMonitoringChecksSpec",
    "ColumnBoolMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnBoolMonthlyPartitionedChecksSpec",
    "ColumnBoolMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnBoolProfilingChecksSpec",
    "ColumnBoolProfilingChecksSpecCustomChecks",
    "ColumnBoolTruePercentSensorParametersSpec",
    "ColumnChangeDistinctCountCheckSpec",
    "ColumnChangeDistinctCountSince30DaysCheckSpec",
    "ColumnChangeDistinctCountSince7DaysCheckSpec",
    "ColumnChangeDistinctCountSinceYesterdayCheckSpec",
    "ColumnChangeDistinctPercentCheckSpec",
    "ColumnChangeDistinctPercentSince30DaysCheckSpec",
    "ColumnChangeDistinctPercentSince7DaysCheckSpec",
    "ColumnChangeDistinctPercentSinceYesterdayCheckSpec",
    "ColumnChangeMeanCheckSpec",
    "ColumnChangeMeanSince30DaysCheckSpec",
    "ColumnChangeMeanSince7DaysCheckSpec",
    "ColumnChangeMeanSinceYesterdayCheckSpec",
    "ColumnChangeMedianCheckSpec",
    "ColumnChangeMedianSince30DaysCheckSpec",
    "ColumnChangeMedianSince7DaysCheckSpec",
    "ColumnChangeMedianSinceYesterdayCheckSpec",
    "ColumnChangeNullPercentCheckSpec",
    "ColumnChangeNullPercentSince30DaysCheckSpec",
    "ColumnChangeNullPercentSince7DaysCheckSpec",
    "ColumnChangeNullPercentSinceYesterdayCheckSpec",
    "ColumnChangeSumCheckSpec",
    "ColumnChangeSumSince30DaysCheckSpec",
    "ColumnChangeSumSince7DaysCheckSpec",
    "ColumnChangeSumSinceYesterdayCheckSpec",
    "ColumnColumnExistsSensorParametersSpec",
    "ColumnColumnTypeHashSensorParametersSpec",
    "ColumnComparisonDailyMonitoringChecksSpec",
    "ColumnComparisonDailyMonitoringChecksSpecCustomChecks",
    "ColumnComparisonDailyPartitionedChecksSpec",
    "ColumnComparisonDailyPartitionedChecksSpecCustomChecks",
    "ColumnComparisonMaxMatchCheckSpec",
    "ColumnComparisonMeanMatchCheckSpec",
    "ColumnComparisonMinMatchCheckSpec",
    "ColumnComparisonModel",
    "ColumnComparisonMonthlyMonitoringChecksSpec",
    "ColumnComparisonMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnComparisonMonthlyPartitionedChecksSpec",
    "ColumnComparisonMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnComparisonNotNullCountMatchCheckSpec",
    "ColumnComparisonNullCountMatchCheckSpec",
    "ColumnComparisonProfilingChecksSpec",
    "ColumnComparisonProfilingChecksSpecCustomChecks",
    "ColumnComparisonSumMatchCheckSpec",
    "ColumnCurrentDataQualityStatusModel",
    "ColumnCurrentDataQualityStatusModelChecks",
    "ColumnDailyMonitoringCheckCategoriesSpec",
    "ColumnDailyMonitoringCheckCategoriesSpecComparisons",
    "ColumnDailyMonitoringCheckCategoriesSpecCustom",
    "ColumnDailyPartitionedCheckCategoriesSpec",
    "ColumnDailyPartitionedCheckCategoriesSpecComparisons",
    "ColumnDailyPartitionedCheckCategoriesSpecCustom",
    "ColumnDatatypeDailyMonitoringChecksSpec",
    "ColumnDatatypeDailyMonitoringChecksSpecCustomChecks",
    "ColumnDatatypeDailyPartitionedChecksSpec",
    "ColumnDatatypeDailyPartitionedChecksSpecCustomChecks",
    "ColumnDatatypeMonthlyMonitoringChecksSpec",
    "ColumnDatatypeMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnDatatypeMonthlyPartitionedChecksSpec",
    "ColumnDatatypeMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnDatatypeProfilingChecksSpec",
    "ColumnDatatypeProfilingChecksSpecCustomChecks",
    "ColumnDatatypeStringDatatypeChangedCheckSpec",
    "ColumnDatatypeStringDatatypeDetectedCheckSpec",
    "ColumnDatatypeStringDatatypeDetectSensorParametersSpec",
    "ColumnDatetimeDailyMonitoringChecksSpec",
    "ColumnDatetimeDailyMonitoringChecksSpecCustomChecks",
    "ColumnDatetimeDailyPartitionedChecksSpec",
    "ColumnDatetimeDailyPartitionedChecksSpecCustomChecks",
    "ColumnDatetimeDateMatchFormatPercentCheckSpec",
    "ColumnDatetimeDateMatchFormatPercentSensorParametersSpec",
    "ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec",
    "ColumnDatetimeMonthlyMonitoringChecksSpec",
    "ColumnDatetimeMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnDatetimeMonthlyPartitionedChecksSpec",
    "ColumnDatetimeMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnDatetimeProfilingChecksSpec",
    "ColumnDatetimeProfilingChecksSpecCustomChecks",
    "ColumnDatetimeValueInRangeDatePercentCheckSpec",
    "ColumnDatetimeValueInRangeDatePercentSensorParametersSpec",
    "ColumnDateValuesInFuturePercentCheckSpec",
    "ColumnDistinctCountCheckSpec",
    "ColumnDistinctPercentCheckSpec",
    "ColumnDuplicateCountCheckSpec",
    "ColumnDuplicatePercentCheckSpec",
    "ColumnExpectedNumbersInUseCountCheckSpec",
    "ColumnExpectedStringsInTopValuesCountCheckSpec",
    "ColumnExpectedStringsInUseCountCheckSpec",
    "ColumnFalsePercentCheckSpec",
    "ColumnIntegrityDailyMonitoringChecksSpec",
    "ColumnIntegrityDailyMonitoringChecksSpecCustomChecks",
    "ColumnIntegrityDailyPartitionedChecksSpec",
    "ColumnIntegrityDailyPartitionedChecksSpecCustomChecks",
    "ColumnIntegrityForeignKeyMatchPercentCheckSpec",
    "ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec",
    "ColumnIntegrityForeignKeyNotMatchCountCheckSpec",
    "ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec",
    "ColumnIntegrityMonthlyMonitoringChecksSpec",
    "ColumnIntegrityMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnIntegrityMonthlyPartitionedChecksSpec",
    "ColumnIntegrityMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnIntegrityProfilingChecksSpec",
    "ColumnIntegrityProfilingChecksSpecCustomChecks",
    "ColumnInvalidLatitudeCountCheckSpec",
    "ColumnInvalidLongitudeCountCheckSpec",
    "ColumnListModel",
    "ColumnMaxInRangeCheckSpec",
    "ColumnMeanInRangeCheckSpec",
    "ColumnMedianInRangeCheckSpec",
    "ColumnMinInRangeCheckSpec",
    "ColumnModel",
    "ColumnMonitoringChecksRootSpec",
    "ColumnMonthlyMonitoringCheckCategoriesSpec",
    "ColumnMonthlyMonitoringCheckCategoriesSpecComparisons",
    "ColumnMonthlyMonitoringCheckCategoriesSpecCustom",
    "ColumnMonthlyPartitionedCheckCategoriesSpec",
    "ColumnMonthlyPartitionedCheckCategoriesSpecComparisons",
    "ColumnMonthlyPartitionedCheckCategoriesSpecCustom",
    "ColumnNegativeCountCheckSpec",
    "ColumnNegativePercentCheckSpec",
    "ColumnNonNegativeCountCheckSpec",
    "ColumnNonNegativePercentCheckSpec",
    "ColumnNotNullsCountCheckSpec",
    "ColumnNotNullsPercentCheckSpec",
    "ColumnNullsCountCheckSpec",
    "ColumnNullsDailyMonitoringChecksSpec",
    "ColumnNullsDailyMonitoringChecksSpecCustomChecks",
    "ColumnNullsDailyPartitionedChecksSpec",
    "ColumnNullsDailyPartitionedChecksSpecCustomChecks",
    "ColumnNullsMonthlyMonitoringChecksSpec",
    "ColumnNullsMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnNullsMonthlyPartitionedChecksSpec",
    "ColumnNullsMonthlyPartitionedChecksSpecCustomChecks",
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
    "ColumnNullsProfilingChecksSpecCustomChecks",
    "ColumnNullsStatisticsCollectorsSpec",
    "ColumnNumberValueInSetPercentCheckSpec",
    "ColumnNumericDailyMonitoringChecksSpec",
    "ColumnNumericDailyMonitoringChecksSpecCustomChecks",
    "ColumnNumericDailyPartitionedChecksSpec",
    "ColumnNumericDailyPartitionedChecksSpecCustomChecks",
    "ColumnNumericExpectedNumbersInUseCountSensorParametersSpec",
    "ColumnNumericInvalidLatitudeCountSensorParametersSpec",
    "ColumnNumericInvalidLongitudeCountSensorParametersSpec",
    "ColumnNumericMaxSensorParametersSpec",
    "ColumnNumericMeanSensorParametersSpec",
    "ColumnNumericMedianSensorParametersSpec",
    "ColumnNumericMinSensorParametersSpec",
    "ColumnNumericMonthlyMonitoringChecksSpec",
    "ColumnNumericMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnNumericMonthlyPartitionedChecksSpec",
    "ColumnNumericMonthlyPartitionedChecksSpecCustomChecks",
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
    "ColumnNumericProfilingChecksSpecCustomChecks",
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
    "ColumnPiiDailyMonitoringChecksSpec",
    "ColumnPiiDailyMonitoringChecksSpecCustomChecks",
    "ColumnPiiDailyPartitionedChecksSpec",
    "ColumnPiiDailyPartitionedChecksSpecCustomChecks",
    "ColumnPiiMonthlyMonitoringChecksSpec",
    "ColumnPiiMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnPiiMonthlyPartitionedChecksSpec",
    "ColumnPiiMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnPiiProfilingChecksSpec",
    "ColumnPiiProfilingChecksSpecCustomChecks",
    "ColumnPopulationStddevInRangeCheckSpec",
    "ColumnPopulationVarianceInRangeCheckSpec",
    "ColumnProfilingCheckCategoriesSpec",
    "ColumnProfilingCheckCategoriesSpecComparisons",
    "ColumnProfilingCheckCategoriesSpecCustom",
    "ColumnRangeMaxValueSensorParametersSpec",
    "ColumnRangeMaxValueStatisticsCollectorSpec",
    "ColumnRangeMedianValueStatisticsCollectorSpec",
    "ColumnRangeMinValueSensorParametersSpec",
    "ColumnRangeMinValueStatisticsCollectorSpec",
    "ColumnRangeStatisticsCollectorsSpec",
    "ColumnRangeSumValueStatisticsCollectorSpec",
    "ColumnSampleStddevInRangeCheckSpec",
    "ColumnSampleVarianceInRangeCheckSpec",
    "ColumnSamplingColumnSamplesSensorParametersSpec",
    "ColumnSamplingColumnSamplesStatisticsCollectorSpec",
    "ColumnSamplingStatisticsCollectorsSpec",
    "ColumnSchemaColumnExistsCheckSpec",
    "ColumnSchemaDailyMonitoringChecksSpec",
    "ColumnSchemaDailyMonitoringChecksSpecCustomChecks",
    "ColumnSchemaMonthlyMonitoringChecksSpec",
    "ColumnSchemaMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnSchemaProfilingChecksSpec",
    "ColumnSchemaProfilingChecksSpecCustomChecks",
    "ColumnSchemaTypeChangedCheckSpec",
    "ColumnSpec",
    "ColumnSqlAggregatedExpressionSensorParametersSpec",
    "ColumnSqlAggregateExprCheckSpec",
    "ColumnSqlConditionFailedCountCheckSpec",
    "ColumnSqlConditionFailedCountSensorParametersSpec",
    "ColumnSqlConditionPassedPercentCheckSpec",
    "ColumnSqlConditionPassedPercentSensorParametersSpec",
    "ColumnSqlDailyMonitoringChecksSpec",
    "ColumnSqlDailyMonitoringChecksSpecCustomChecks",
    "ColumnSqlDailyPartitionedChecksSpec",
    "ColumnSqlDailyPartitionedChecksSpecCustomChecks",
    "ColumnSqlMonthlyMonitoringChecksSpec",
    "ColumnSqlMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnSqlMonthlyPartitionedChecksSpec",
    "ColumnSqlMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnSqlProfilingChecksSpec",
    "ColumnSqlProfilingChecksSpecCustomChecks",
    "ColumnStatisticsCollectorsRootCategoriesSpec",
    "ColumnStatisticsModel",
    "ColumnStringBooleanPlaceholderPercentCheckSpec",
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
    "ColumnStringsDailyMonitoringChecksSpec",
    "ColumnStringsDailyMonitoringChecksSpecCustomChecks",
    "ColumnStringsDailyPartitionedChecksSpec",
    "ColumnStringsDailyPartitionedChecksSpecCustomChecks",
    "ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec",
    "ColumnStringsExpectedStringsInUseCountSensorParametersSpec",
    "ColumnStringsMonthlyMonitoringChecksSpec",
    "ColumnStringsMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnStringsMonthlyPartitionedChecksSpec",
    "ColumnStringsMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnStringsProfilingChecksSpec",
    "ColumnStringsProfilingChecksSpecCustomChecks",
    "ColumnStringsStatisticsCollectorsSpec",
    "ColumnStringsStringBooleanPlaceholderPercentSensorParametersSpec",
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
    "ColumnStringsStringMatchNameRegexPercentSensorParametersSpec",
    "ColumnStringsStringMatchRegexPercentSensorParametersSpec",
    "ColumnStringsStringMaxLengthSensorParametersSpec",
    "ColumnStringsStringMaxLengthStatisticsCollectorSpec",
    "ColumnStringsStringMeanLengthSensorParametersSpec",
    "ColumnStringsStringMeanLengthStatisticsCollectorSpec",
    "ColumnStringsStringMinLengthSensorParametersSpec",
    "ColumnStringsStringMinLengthStatisticsCollectorSpec",
    "ColumnStringsStringNotMatchDateRegexCountSensorParametersSpec",
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
    "ColumnUniquenessDailyMonitoringChecksSpec",
    "ColumnUniquenessDailyMonitoringChecksSpecCustomChecks",
    "ColumnUniquenessDailyPartitionedChecksSpec",
    "ColumnUniquenessDailyPartitionedChecksSpecCustomChecks",
    "ColumnUniquenessDistinctCountSensorParametersSpec",
    "ColumnUniquenessDistinctCountStatisticsCollectorSpec",
    "ColumnUniquenessDistinctPercentSensorParametersSpec",
    "ColumnUniquenessDistinctPercentStatisticsCollectorSpec",
    "ColumnUniquenessDuplicateCountSensorParametersSpec",
    "ColumnUniquenessDuplicateCountStatisticsCollectorSpec",
    "ColumnUniquenessDuplicatePercentSensorParametersSpec",
    "ColumnUniquenessDuplicatePercentStatisticsCollectorSpec",
    "ColumnUniquenessMonthlyMonitoringChecksSpec",
    "ColumnUniquenessMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnUniquenessMonthlyPartitionedChecksSpec",
    "ColumnUniquenessMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnUniquenessProfilingChecksSpec",
    "ColumnUniquenessProfilingChecksSpecCustomChecks",
    "ColumnUniquenessStatisticsCollectorsSpec",
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
    "CompareThresholdsModel",
    "ComparisonCheckResultModel",
    "ConnectionIncidentGroupingSpec",
    "ConnectionModel",
    "ConnectionSpec",
    "ConnectionSpecificationModel",
    "ConnectionTestModel",
    "ConnectionTestStatus",
    "CredentialType",
    "CustomCheckSpec",
    "CustomRuleParametersSpec",
    "CustomSensorParametersSpec",
    "DashboardsFolderSpec",
    "DashboardSpec",
    "DashboardSpecParameters",
    "DataDeleteResultPartition",
    "DataGroupingConfigurationListModel",
    "DataGroupingConfigurationModel",
    "DataGroupingConfigurationSpec",
    "DataGroupingConfigurationTrimmedModel",
    "DataGroupingDimensionSource",
    "DataGroupingDimensionSpec",
    "DatatypeEqualsRuleParametersSpec",
    "DatetimeBuiltInDateFormats",
    "DefaultSchedulesSpec",
    "DeleteStoredDataQueueJobParameters",
    "DeleteStoredDataQueueJobResult",
    "DeleteStoredDataResult",
    "DeleteStoredDataResultPartitionResults",
    "DisplayHint",
    "DqoCloudUserModel",
    "DqoJobChangeModel",
    "DqoJobEntryParametersModel",
    "DqoJobHistoryEntryModel",
    "DqoJobQueueIncrementalSnapshotModel",
    "DqoJobQueueInitialSnapshotModel",
    "DqoJobStatus",
    "DqoJobType",
    "DqoQueueJobId",
    "DqoRoot",
    "DqoSettingsModel",
    "DqoSettingsModelProperties",
    "DqoSettingsModelPropertiesAdditionalProperty",
    "DqoUserProfileModel",
    "DqoUserRole",
    "Duration",
    "EffectiveScheduleLevelModel",
    "EffectiveScheduleModel",
    "EqualsInteger1RuleParametersSpec",
    "EqualsIntegerRuleParametersSpec",
    "ErrorEntryModel",
    "ErrorsListModel",
    "ExternalLogEntry",
    "FieldModel",
    "FileSynchronizationDirection",
    "FolderSynchronizationStatus",
    "HierarchyIdModel",
    "HierarchyIdModelPathItem",
    "HistoricDataPointsGrouping",
    "ImportSchemaQueueJobParameters",
    "ImportTablesQueueJobParameters",
    "ImportTablesQueueJobResult",
    "ImportTablesResult",
    "IncidentDailyIssuesCount",
    "IncidentGroupingLevel",
    "IncidentIssueHistogramModel",
    "IncidentIssueHistogramModelChecks",
    "IncidentIssueHistogramModelColumns",
    "IncidentIssueHistogramModelDays",
    "IncidentModel",
    "IncidentSortOrder",
    "IncidentsPerConnectionModel",
    "IncidentStatus",
    "IncidentWebhookNotificationsSpec",
    "MaxCountRule0ParametersSpec",
    "MaxCountRule10ParametersSpec",
    "MaxCountRule15ParametersSpec",
    "MaxDaysRule1ParametersSpec",
    "MaxDaysRule2ParametersSpec",
    "MaxDaysRule7ParametersSpec",
    "MaxDiffPercentRule0ParametersSpec",
    "MaxDiffPercentRule1ParametersSpec",
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
    "MinCountRule1ParametersSpec",
    "MinCountRuleFatalParametersSpec",
    "MinCountRuleWarningParametersSpec",
    "MinimumGroupingSeverityLevel",
    "MinPercentRule0ParametersSpec",
    "MinPercentRule100ParametersSpec",
    "MinPercentRule2ParametersSpec",
    "MinPercentRule5ParametersSpec",
    "MinPercentRule95ParametersSpec",
    "MinPercentRule99ParametersSpec",
    "MinValueRuleParametersSpec",
    "MonitoringScheduleSpec",
    "Mono",
    "MonoDqoQueueJobId",
    "MonoObject",
    "MonoVoid",
    "MysqlParametersSpec",
    "MysqlParametersSpecProperties",
    "MySqlSslMode",
    "Optional",
    "OptionalIncidentWebhookNotificationsSpec",
    "OptionalMonitoringScheduleSpec",
    "OracleParametersSpec",
    "OracleParametersSpecProperties",
    "ParameterDataType",
    "ParameterDefinitionSpec",
    "PartitionIncrementalTimeWindowSpec",
    "PhysicalTableName",
    "PostgresqlParametersSpec",
    "PostgresqlParametersSpecProperties",
    "PostgresqlSslMode",
    "ProfilingTimePeriod",
    "ProviderSensorDefinitionSpec",
    "ProviderSensorDefinitionSpecParameters",
    "ProviderSensorListModel",
    "ProviderSensorModel",
    "ProviderSensorRunnerType",
    "ProviderType",
    "QualityCategoryModel",
    "RedshiftParametersSpec",
    "RedshiftParametersSpecProperties",
    "RemoteTableListModel",
    "RepairStoredDataQueueJobParameters",
    "RuleFolderModel",
    "RuleFolderModelFolders",
    "RuleListModel",
    "RuleModel",
    "RuleModelParameters",
    "RuleParametersModel",
    "RuleRunnerType",
    "RuleSeverityLevel",
    "RuleThresholdsModel",
    "RuleTimeWindowMode",
    "RuleTimeWindowSettingsSpec",
    "RunChecksOnTableParameters",
    "RunChecksParameters",
    "RunChecksQueueJobResult",
    "RunChecksResult",
    "ScheduleEnabledStatusModel",
    "SchemaModel",
    "SchemaRemoteModel",
    "SensorDefinitionSpec",
    "SensorDefinitionSpecParameters",
    "SensorFolderModel",
    "SensorFolderModelFolders",
    "SensorListModel",
    "SensorModel",
    "SensorReadoutEntryModel",
    "SensorReadoutsListModel",
    "SharedCredentialListModel",
    "SharedCredentialModel",
    "SimilarCheckModel",
    "SnowflakeParametersSpec",
    "SnowflakeParametersSpecProperties",
    "SortDirection",
    "SpringErrorPayload",
    "SqlServerParametersSpec",
    "SqlServerParametersSpecProperties",
    "StatisticsCollectorSearchFilters",
    "StatisticsCollectorTarget",
    "StatisticsDataScope",
    "StatisticsMetricModel",
    "StatisticsMetricModelResult",
    "StatisticsResultDataType",
    "StringsBuiltInDateFormats",
    "SynchronizeMultipleFoldersDqoQueueJobParameters",
    "SynchronizeMultipleFoldersQueueJobResult",
    "SynchronizeRootFolderDqoQueueJobParameters",
    "SynchronizeRootFolderParameters",
    "TableAccuracyDailyMonitoringChecksSpec",
    "TableAccuracyDailyMonitoringChecksSpecCustomChecks",
    "TableAccuracyMonthlyMonitoringChecksSpec",
    "TableAccuracyMonthlyMonitoringChecksSpecCustomChecks",
    "TableAccuracyProfilingChecksSpec",
    "TableAccuracyProfilingChecksSpecCustomChecks",
    "TableAccuracyTotalRowCountMatchPercentCheckSpec",
    "TableAccuracyTotalRowCountMatchPercentSensorParametersSpec",
    "TableAnomalyDifferencingRowCount30DaysCheckSpec",
    "TableAnomalyDifferencingRowCountCheckSpec",
    "TableAnomalyStationaryPartitionRowCount30DaysCheckSpec",
    "TableAnomalyStationaryPartitionRowCountCheckSpec",
    "TableAvailabilityCheckSpec",
    "TableAvailabilityDailyMonitoringChecksSpec",
    "TableAvailabilityDailyMonitoringChecksSpecCustomChecks",
    "TableAvailabilityMonthlyMonitoringChecksSpec",
    "TableAvailabilityMonthlyMonitoringChecksSpecCustomChecks",
    "TableAvailabilityProfilingChecksSpec",
    "TableAvailabilityProfilingChecksSpecCustomChecks",
    "TableAvailabilitySensorParametersSpec",
    "TableChangeRowCountCheckSpec",
    "TableChangeRowCountSince30DaysCheckSpec",
    "TableChangeRowCountSince7DaysCheckSpec",
    "TableChangeRowCountSinceYesterdayCheckSpec",
    "TableColumnCountSensorParametersSpec",
    "TableColumnListOrderedHashSensorParametersSpec",
    "TableColumnListUnorderedHashSensorParametersSpec",
    "TableColumnsStatisticsModel",
    "TableColumnTypesHashSensorParametersSpec",
    "TableComparisonColumnCountMatchCheckSpec",
    "TableComparisonColumnResultsModel",
    "TableComparisonColumnResultsModelColumnComparisonResults",
    "TableComparisonConfigurationModel",
    "TableComparisonConfigurationSpec",
    "TableComparisonDailyMonitoringChecksSpec",
    "TableComparisonDailyMonitoringChecksSpecCustomChecks",
    "TableComparisonDailyPartitionedChecksSpec",
    "TableComparisonDailyPartitionedChecksSpecCustomChecks",
    "TableComparisonGroupingColumnPairModel",
    "TableComparisonGroupingColumnsPairSpec",
    "TableComparisonModel",
    "TableComparisonMonthlyMonitoringChecksSpec",
    "TableComparisonMonthlyMonitoringChecksSpecCustomChecks",
    "TableComparisonMonthlyPartitionedChecksSpec",
    "TableComparisonMonthlyPartitionedChecksSpecCustomChecks",
    "TableComparisonProfilingChecksSpec",
    "TableComparisonProfilingChecksSpecCustomChecks",
    "TableComparisonResultsModel",
    "TableComparisonResultsModelColumnComparisonResults",
    "TableComparisonResultsModelTableComparisonResults",
    "TableComparisonRowCountMatchCheckSpec",
    "TableCurrentDataQualityStatusModel",
    "TableCurrentDataQualityStatusModelChecks",
    "TableCurrentDataQualityStatusModelColumns",
    "TableDailyMonitoringCheckCategoriesSpec",
    "TableDailyMonitoringCheckCategoriesSpecComparisons",
    "TableDailyMonitoringCheckCategoriesSpecCustom",
    "TableDailyPartitionedCheckCategoriesSpec",
    "TableDailyPartitionedCheckCategoriesSpecComparisons",
    "TableDailyPartitionedCheckCategoriesSpecCustom",
    "TableDataFreshnessCheckSpec",
    "TableDataIngestionDelayCheckSpec",
    "TableDataStalenessCheckSpec",
    "TableIncidentGroupingSpec",
    "TableListModel",
    "TableModel",
    "TableMonitoringChecksSpec",
    "TableMonthlyMonitoringCheckCategoriesSpec",
    "TableMonthlyMonitoringCheckCategoriesSpecComparisons",
    "TableMonthlyMonitoringCheckCategoriesSpecCustom",
    "TableMonthlyPartitionedCheckCategoriesSpec",
    "TableMonthlyPartitionedCheckCategoriesSpecComparisons",
    "TableMonthlyPartitionedCheckCategoriesSpecCustom",
    "TableOwnerSpec",
    "TablePartitionedChecksRootSpec",
    "TablePartitioningModel",
    "TablePartitionReloadLagCheckSpec",
    "TableProfilingCheckCategoriesSpec",
    "TableProfilingCheckCategoriesSpecComparisons",
    "TableProfilingCheckCategoriesSpecCustom",
    "TableRowCountCheckSpec",
    "TableSchemaColumnCountChangedCheckSpec",
    "TableSchemaColumnCountCheckSpec",
    "TableSchemaColumnCountStatisticsCollectorSpec",
    "TableSchemaColumnListChangedCheckSpec",
    "TableSchemaColumnListOrOrderChangedCheckSpec",
    "TableSchemaColumnTypesChangedCheckSpec",
    "TableSchemaDailyMonitoringChecksSpec",
    "TableSchemaDailyMonitoringChecksSpecCustomChecks",
    "TableSchemaMonthlyMonitoringChecksSpec",
    "TableSchemaMonthlyMonitoringChecksSpecCustomChecks",
    "TableSchemaProfilingChecksSpec",
    "TableSchemaProfilingChecksSpecCustomChecks",
    "TableSchemaStatisticsCollectorsSpec",
    "TableSpec",
    "TableSpecColumns",
    "TableSpecGroupings",
    "TableSpecTableComparisons",
    "TableSqlAggregatedExpressionSensorParametersSpec",
    "TableSqlAggregateExprCheckSpec",
    "TableSqlConditionFailedCountCheckSpec",
    "TableSqlConditionFailedCountSensorParametersSpec",
    "TableSqlConditionPassedPercentCheckSpec",
    "TableSqlConditionPassedPercentSensorParametersSpec",
    "TableSqlDailyMonitoringChecksSpec",
    "TableSqlDailyMonitoringChecksSpecCustomChecks",
    "TableSqlDailyPartitionedChecksSpec",
    "TableSqlDailyPartitionedChecksSpecCustomChecks",
    "TableSqlMonthlyMonitoringChecksSpec",
    "TableSqlMonthlyMonitoringChecksSpecCustomChecks",
    "TableSqlMonthlyPartitionedChecksSpec",
    "TableSqlMonthlyPartitionedChecksSpecCustomChecks",
    "TableSqlProfilingChecksSpec",
    "TableSqlProfilingChecksSpecCustomChecks",
    "TableStatisticsCollectorsRootCategoriesSpec",
    "TableStatisticsModel",
    "TableTimelinessDailyMonitoringChecksSpec",
    "TableTimelinessDailyMonitoringChecksSpecCustomChecks",
    "TableTimelinessDailyPartitionedChecksSpec",
    "TableTimelinessDailyPartitionedChecksSpecCustomChecks",
    "TableTimelinessDataFreshnessSensorParametersSpec",
    "TableTimelinessDataIngestionDelaySensorParametersSpec",
    "TableTimelinessDataStalenessSensorParametersSpec",
    "TableTimelinessMonthlyMonitoringChecksSpec",
    "TableTimelinessMonthlyMonitoringChecksSpecCustomChecks",
    "TableTimelinessMonthlyPartitionedChecksSpec",
    "TableTimelinessMonthlyPartitionedChecksSpecCustomChecks",
    "TableTimelinessPartitionReloadLagSensorParametersSpec",
    "TableTimelinessProfilingChecksSpec",
    "TableTimelinessProfilingChecksSpecCustomChecks",
    "TableVolumeDailyMonitoringChecksSpec",
    "TableVolumeDailyMonitoringChecksSpecCustomChecks",
    "TableVolumeDailyPartitionedChecksSpec",
    "TableVolumeDailyPartitionedChecksSpecCustomChecks",
    "TableVolumeMonthlyMonitoringChecksSpec",
    "TableVolumeMonthlyMonitoringChecksSpecCustomChecks",
    "TableVolumeMonthlyPartitionedChecksSpec",
    "TableVolumeMonthlyPartitionedChecksSpecCustomChecks",
    "TableVolumeProfilingChecksSpec",
    "TableVolumeProfilingChecksSpecCustomChecks",
    "TableVolumeRowCountSensorParametersSpec",
    "TableVolumeRowCountStatisticsCollectorSpec",
    "TableVolumeStatisticsCollectorsSpec",
    "TemporalUnit",
    "TimestampColumnsSpec",
    "TimeWindowFilterParameters",
    "ValueChangedParametersSpec",
)
