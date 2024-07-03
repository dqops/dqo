""" Contains all the data models used in inputs/outputs """

from .all_checks_patch_parameters import AllChecksPatchParameters
from .all_checks_patch_parameters_selected_tables_to_columns import (
    AllChecksPatchParametersSelectedTablesToColumns,
)
from .anomaly_differencing_percentile_moving_average_rule_error_05_pct_parameters_spec import (
    AnomalyDifferencingPercentileMovingAverageRuleError05PctParametersSpec,
)
from .anomaly_differencing_percentile_moving_average_rule_fatal_01_pct_parameters_spec import (
    AnomalyDifferencingPercentileMovingAverageRuleFatal01PctParametersSpec,
)
from .anomaly_differencing_percentile_moving_average_rule_warning_1_pct_parameters_spec import (
    AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec,
)
from .anomaly_stationary_percentile_moving_average_rule_error_05_pct_parameters_spec import (
    AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec,
)
from .anomaly_stationary_percentile_moving_average_rule_fatal_01_pct_parameters_spec import (
    AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec,
)
from .anomaly_stationary_percentile_moving_average_rule_warning_1_pct_parameters_spec import (
    AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec,
)
from .authenticated_dashboard_model import AuthenticatedDashboardModel
from .aws_authentication_mode import AwsAuthenticationMode
from .azure_authentication_mode import AzureAuthenticationMode
from .between_floats_rule_parameters_spec import BetweenFloatsRuleParametersSpec
from .between_ints_rule_parameters_spec import BetweenIntsRuleParametersSpec
from .between_percent_rule_parameters_spec import BetweenPercentRuleParametersSpec
from .big_query_authentication_mode import BigQueryAuthenticationMode
from .big_query_jobs_create_project import BigQueryJobsCreateProject
from .big_query_parameters_spec import BigQueryParametersSpec
from .bulk_check_deactivate_parameters import BulkCheckDeactivateParameters
from .bulk_check_deactivate_parameters_selected_tables_to_columns import (
    BulkCheckDeactivateParametersSelectedTablesToColumns,
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
from .collect_error_samples_on_table_parameters import (
    CollectErrorSamplesOnTableParameters,
)
from .collect_error_samples_parameters import CollectErrorSamplesParameters
from .collect_error_samples_result import CollectErrorSamplesResult
from .collect_statistics_on_table_queue_job_parameters import (
    CollectStatisticsOnTableQueueJobParameters,
)
from .collect_statistics_queue_job_parameters import CollectStatisticsQueueJobParameters
from .collect_statistics_queue_job_result import CollectStatisticsQueueJobResult
from .collect_statistics_result import CollectStatisticsResult
from .column_accepted_values_daily_monitoring_checks_spec import (
    ColumnAcceptedValuesDailyMonitoringChecksSpec,
)
from .column_accepted_values_daily_monitoring_checks_spec_custom_checks import (
    ColumnAcceptedValuesDailyMonitoringChecksSpecCustomChecks,
)
from .column_accepted_values_daily_partitioned_checks_spec import (
    ColumnAcceptedValuesDailyPartitionedChecksSpec,
)
from .column_accepted_values_daily_partitioned_checks_spec_custom_checks import (
    ColumnAcceptedValuesDailyPartitionedChecksSpecCustomChecks,
)
from .column_accepted_values_monthly_monitoring_checks_spec import (
    ColumnAcceptedValuesMonthlyMonitoringChecksSpec,
)
from .column_accepted_values_monthly_monitoring_checks_spec_custom_checks import (
    ColumnAcceptedValuesMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_accepted_values_monthly_partitioned_checks_spec import (
    ColumnAcceptedValuesMonthlyPartitionedChecksSpec,
)
from .column_accepted_values_monthly_partitioned_checks_spec_custom_checks import (
    ColumnAcceptedValuesMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_accepted_values_profiling_checks_spec import (
    ColumnAcceptedValuesProfilingChecksSpec,
)
from .column_accepted_values_profiling_checks_spec_custom_checks import (
    ColumnAcceptedValuesProfilingChecksSpecCustomChecks,
)
from .column_accepted_values_text_found_in_set_percent_sensor_parameters_spec import (
    ColumnAcceptedValuesTextFoundInSetPercentSensorParametersSpec,
)
from .column_accuracy_daily_monitoring_checks_spec import (
    ColumnAccuracyDailyMonitoringChecksSpec,
)
from .column_accuracy_daily_monitoring_checks_spec_custom_checks import (
    ColumnAccuracyDailyMonitoringChecksSpecCustomChecks,
)
from .column_accuracy_monthly_monitoring_checks_spec import (
    ColumnAccuracyMonthlyMonitoringChecksSpec,
)
from .column_accuracy_monthly_monitoring_checks_spec_custom_checks import (
    ColumnAccuracyMonthlyMonitoringChecksSpecCustomChecks,
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
from .column_anomaly_profiling_checks_spec import ColumnAnomalyProfilingChecksSpec
from .column_anomaly_profiling_checks_spec_custom_checks import (
    ColumnAnomalyProfilingChecksSpecCustomChecks,
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
from .column_conversions_daily_monitoring_checks_spec import (
    ColumnConversionsDailyMonitoringChecksSpec,
)
from .column_conversions_daily_monitoring_checks_spec_custom_checks import (
    ColumnConversionsDailyMonitoringChecksSpecCustomChecks,
)
from .column_conversions_daily_partitioned_checks_spec import (
    ColumnConversionsDailyPartitionedChecksSpec,
)
from .column_conversions_daily_partitioned_checks_spec_custom_checks import (
    ColumnConversionsDailyPartitionedChecksSpecCustomChecks,
)
from .column_conversions_monthly_monitoring_checks_spec import (
    ColumnConversionsMonthlyMonitoringChecksSpec,
)
from .column_conversions_monthly_monitoring_checks_spec_custom_checks import (
    ColumnConversionsMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_conversions_monthly_partitioned_checks_spec import (
    ColumnConversionsMonthlyPartitionedChecksSpec,
)
from .column_conversions_monthly_partitioned_checks_spec_custom_checks import (
    ColumnConversionsMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_conversions_profiling_checks_spec import (
    ColumnConversionsProfilingChecksSpec,
)
from .column_conversions_profiling_checks_spec_custom_checks import (
    ColumnConversionsProfilingChecksSpecCustomChecks,
)
from .column_current_data_quality_status_model import (
    ColumnCurrentDataQualityStatusModel,
)
from .column_current_data_quality_status_model_checks import (
    ColumnCurrentDataQualityStatusModelChecks,
)
from .column_current_data_quality_status_model_dimensions import (
    ColumnCurrentDataQualityStatusModelDimensions,
)
from .column_custom_sql_daily_monitoring_checks_spec import (
    ColumnCustomSqlDailyMonitoringChecksSpec,
)
from .column_custom_sql_daily_monitoring_checks_spec_custom_checks import (
    ColumnCustomSqlDailyMonitoringChecksSpecCustomChecks,
)
from .column_custom_sql_daily_partitioned_checks_spec import (
    ColumnCustomSqlDailyPartitionedChecksSpec,
)
from .column_custom_sql_daily_partitioned_checks_spec_custom_checks import (
    ColumnCustomSqlDailyPartitionedChecksSpecCustomChecks,
)
from .column_custom_sql_monthly_monitoring_checks_spec import (
    ColumnCustomSqlMonthlyMonitoringChecksSpec,
)
from .column_custom_sql_monthly_monitoring_checks_spec_custom_checks import (
    ColumnCustomSqlMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_custom_sql_monthly_partitioned_checks_spec import (
    ColumnCustomSqlMonthlyPartitionedChecksSpec,
)
from .column_custom_sql_monthly_partitioned_checks_spec_custom_checks import (
    ColumnCustomSqlMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_custom_sql_profiling_checks_spec import ColumnCustomSqlProfilingChecksSpec
from .column_custom_sql_profiling_checks_spec_custom_checks import (
    ColumnCustomSqlProfilingChecksSpecCustomChecks,
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
from .column_datatype_detected_datatype_in_text_changed_check_spec import (
    ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec,
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
from .column_datatype_string_datatype_detect_sensor_parameters_spec import (
    ColumnDatatypeStringDatatypeDetectSensorParametersSpec,
)
from .column_date_in_range_percent_check_spec import ColumnDateInRangePercentCheckSpec
from .column_date_in_range_percent_sensor_parameters_spec import (
    ColumnDateInRangePercentSensorParametersSpec,
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
from .column_default_checks_pattern_spec import ColumnDefaultChecksPatternSpec
from .column_detected_datatype_in_text_check_spec import (
    ColumnDetectedDatatypeInTextCheckSpec,
)
from .column_distinct_count_anomaly_differencing_check_spec import (
    ColumnDistinctCountAnomalyDifferencingCheckSpec,
)
from .column_distinct_count_anomaly_stationary_partition_check_spec import (
    ColumnDistinctCountAnomalyStationaryPartitionCheckSpec,
)
from .column_distinct_count_change_1_day_check_spec import (
    ColumnDistinctCountChange1DayCheckSpec,
)
from .column_distinct_count_change_7_days_check_spec import (
    ColumnDistinctCountChange7DaysCheckSpec,
)
from .column_distinct_count_change_30_days_check_spec import (
    ColumnDistinctCountChange30DaysCheckSpec,
)
from .column_distinct_count_change_check_spec import ColumnDistinctCountChangeCheckSpec
from .column_distinct_count_check_spec import ColumnDistinctCountCheckSpec
from .column_distinct_percent_anomaly_stationary_check_spec import (
    ColumnDistinctPercentAnomalyStationaryCheckSpec,
)
from .column_distinct_percent_change_1_day_check_spec import (
    ColumnDistinctPercentChange1DayCheckSpec,
)
from .column_distinct_percent_change_7_days_check_spec import (
    ColumnDistinctPercentChange7DaysCheckSpec,
)
from .column_distinct_percent_change_30_days_check_spec import (
    ColumnDistinctPercentChange30DaysCheckSpec,
)
from .column_distinct_percent_change_check_spec import (
    ColumnDistinctPercentChangeCheckSpec,
)
from .column_distinct_percent_check_spec import ColumnDistinctPercentCheckSpec
from .column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
from .column_duplicate_percent_check_spec import ColumnDuplicatePercentCheckSpec
from .column_expected_numbers_in_use_count_check_spec import (
    ColumnExpectedNumbersInUseCountCheckSpec,
)
from .column_expected_text_values_in_use_count_check_spec import (
    ColumnExpectedTextValuesInUseCountCheckSpec,
)
from .column_expected_texts_in_top_values_count_check_spec import (
    ColumnExpectedTextsInTopValuesCountCheckSpec,
)
from .column_false_percent_check_spec import ColumnFalsePercentCheckSpec
from .column_integer_in_range_percent_check_spec import (
    ColumnIntegerInRangePercentCheckSpec,
)
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
from .column_integrity_foreign_key_not_match_count_sensor_parameters_spec import (
    ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec,
)
from .column_integrity_lookup_key_not_found_count_check_spec import (
    ColumnIntegrityLookupKeyNotFoundCountCheckSpec,
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
from .column_invalid_email_format_found_check_spec import (
    ColumnInvalidEmailFormatFoundCheckSpec,
)
from .column_invalid_email_format_percent_check_spec import (
    ColumnInvalidEmailFormatPercentCheckSpec,
)
from .column_invalid_ip_4_address_format_found_check_spec import (
    ColumnInvalidIp4AddressFormatFoundCheckSpec,
)
from .column_invalid_ip_6_address_format_found_check_spec import (
    ColumnInvalidIp6AddressFormatFoundCheckSpec,
)
from .column_invalid_latitude_count_check_spec import (
    ColumnInvalidLatitudeCountCheckSpec,
)
from .column_invalid_longitude_count_check_spec import (
    ColumnInvalidLongitudeCountCheckSpec,
)
from .column_invalid_uuid_format_found_check_spec import (
    ColumnInvalidUuidFormatFoundCheckSpec,
)
from .column_list_model import ColumnListModel
from .column_max_anomaly_differencing_check_spec import (
    ColumnMaxAnomalyDifferencingCheckSpec,
)
from .column_max_anomaly_stationary_check_spec import (
    ColumnMaxAnomalyStationaryCheckSpec,
)
from .column_max_in_range_check_spec import ColumnMaxInRangeCheckSpec
from .column_mean_anomaly_stationary_check_spec import (
    ColumnMeanAnomalyStationaryCheckSpec,
)
from .column_mean_change_1_day_check_spec import ColumnMeanChange1DayCheckSpec
from .column_mean_change_7_days_check_spec import ColumnMeanChange7DaysCheckSpec
from .column_mean_change_30_days_check_spec import ColumnMeanChange30DaysCheckSpec
from .column_mean_change_check_spec import ColumnMeanChangeCheckSpec
from .column_mean_in_range_check_spec import ColumnMeanInRangeCheckSpec
from .column_median_anomaly_stationary_check_spec import (
    ColumnMedianAnomalyStationaryCheckSpec,
)
from .column_median_change_1_day_check_spec import ColumnMedianChange1DayCheckSpec
from .column_median_change_7_days_check_spec import ColumnMedianChange7DaysCheckSpec
from .column_median_change_30_days_check_spec import ColumnMedianChange30DaysCheckSpec
from .column_median_change_check_spec import ColumnMedianChangeCheckSpec
from .column_median_in_range_check_spec import ColumnMedianInRangeCheckSpec
from .column_min_anomaly_differencing_check_spec import (
    ColumnMinAnomalyDifferencingCheckSpec,
)
from .column_min_anomaly_stationary_check_spec import (
    ColumnMinAnomalyStationaryCheckSpec,
)
from .column_min_in_range_check_spec import ColumnMinInRangeCheckSpec
from .column_model import ColumnModel
from .column_monitoring_check_categories_spec import ColumnMonitoringCheckCategoriesSpec
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
from .column_null_percent_anomaly_stationary_check_spec import (
    ColumnNullPercentAnomalyStationaryCheckSpec,
)
from .column_null_percent_change_1_day_check_spec import (
    ColumnNullPercentChange1DayCheckSpec,
)
from .column_null_percent_change_7_days_check_spec import (
    ColumnNullPercentChange7DaysCheckSpec,
)
from .column_null_percent_change_30_days_check_spec import (
    ColumnNullPercentChange30DaysCheckSpec,
)
from .column_null_percent_change_check_spec import ColumnNullPercentChangeCheckSpec
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
from .column_number_above_max_value_check_spec import ColumnNumberAboveMaxValueCheckSpec
from .column_number_above_max_value_percent_check_spec import (
    ColumnNumberAboveMaxValuePercentCheckSpec,
)
from .column_number_below_min_value_check_spec import ColumnNumberBelowMinValueCheckSpec
from .column_number_below_min_value_percent_check_spec import (
    ColumnNumberBelowMinValuePercentCheckSpec,
)
from .column_number_found_in_set_percent_check_spec import (
    ColumnNumberFoundInSetPercentCheckSpec,
)
from .column_number_in_range_percent_check_spec import (
    ColumnNumberInRangePercentCheckSpec,
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
from .column_numeric_integer_in_range_percent_sensor_parameters_spec import (
    ColumnNumericIntegerInRangePercentSensorParametersSpec,
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
from .column_numeric_number_above_max_value_count_sensor_parameters_spec import (
    ColumnNumericNumberAboveMaxValueCountSensorParametersSpec,
)
from .column_numeric_number_above_max_value_percent_sensor_parameters_spec import (
    ColumnNumericNumberAboveMaxValuePercentSensorParametersSpec,
)
from .column_numeric_number_below_min_value_count_sensor_parameters_spec import (
    ColumnNumericNumberBelowMinValueCountSensorParametersSpec,
)
from .column_numeric_number_below_min_value_percent_sensor_parameters_spec import (
    ColumnNumericNumberBelowMinValuePercentSensorParametersSpec,
)
from .column_numeric_number_found_in_set_percent_sensor_parameters_spec import (
    ColumnNumericNumberFoundInSetPercentSensorParametersSpec,
)
from .column_numeric_number_in_range_percent_sensor_parameters_spec import (
    ColumnNumericNumberInRangePercentSensorParametersSpec,
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
from .column_partitioned_check_categories_spec import (
    ColumnPartitionedCheckCategoriesSpec,
)
from .column_patterns_daily_monitoring_checks_spec import (
    ColumnPatternsDailyMonitoringChecksSpec,
)
from .column_patterns_daily_monitoring_checks_spec_custom_checks import (
    ColumnPatternsDailyMonitoringChecksSpecCustomChecks,
)
from .column_patterns_daily_partitioned_checks_spec import (
    ColumnPatternsDailyPartitionedChecksSpec,
)
from .column_patterns_daily_partitioned_checks_spec_custom_checks import (
    ColumnPatternsDailyPartitionedChecksSpecCustomChecks,
)
from .column_patterns_invalid_email_format_count_sensor_parameters_spec import (
    ColumnPatternsInvalidEmailFormatCountSensorParametersSpec,
)
from .column_patterns_invalid_email_format_percent_sensor_parameters_spec import (
    ColumnPatternsInvalidEmailFormatPercentSensorParametersSpec,
)
from .column_patterns_invalid_ip_4_address_format_count_sensor_parameters_spec import (
    ColumnPatternsInvalidIp4AddressFormatCountSensorParametersSpec,
)
from .column_patterns_invalid_ip_6_address_format_count_sensor_parameters_spec import (
    ColumnPatternsInvalidIp6AddressFormatCountSensorParametersSpec,
)
from .column_patterns_invalid_uuid_format_count_sensor_parameters_spec import (
    ColumnPatternsInvalidUuidFormatCountSensorParametersSpec,
)
from .column_patterns_monthly_monitoring_checks_spec import (
    ColumnPatternsMonthlyMonitoringChecksSpec,
)
from .column_patterns_monthly_monitoring_checks_spec_custom_checks import (
    ColumnPatternsMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_patterns_monthly_partitioned_checks_spec import (
    ColumnPatternsMonthlyPartitionedChecksSpec,
)
from .column_patterns_monthly_partitioned_checks_spec_custom_checks import (
    ColumnPatternsMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_patterns_profiling_checks_spec import ColumnPatternsProfilingChecksSpec
from .column_patterns_profiling_checks_spec_custom_checks import (
    ColumnPatternsProfilingChecksSpecCustomChecks,
)
from .column_patterns_text_matching_date_pattern_percent_sensor_parameters_spec import (
    ColumnPatternsTextMatchingDatePatternPercentSensorParametersSpec,
)
from .column_patterns_text_matching_name_pattern_percent_sensor_parameters_spec import (
    ColumnPatternsTextMatchingNamePatternPercentSensorParametersSpec,
)
from .column_patterns_text_not_matching_date_pattern_count_sensor_parameters_spec import (
    ColumnPatternsTextNotMatchingDatePatternCountSensorParametersSpec,
)
from .column_patterns_text_not_matching_regex_count_sensor_parameters_spec import (
    ColumnPatternsTextNotMatchingRegexCountSensorParametersSpec,
)
from .column_patterns_texts_matching_regex_percent_sensor_parameters_spec import (
    ColumnPatternsTextsMatchingRegexPercentSensorParametersSpec,
)
from .column_patterns_valid_uuid_format_percent_sensor_parameters_spec import (
    ColumnPatternsValidUuidFormatPercentSensorParametersSpec,
)
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
from .column_sql_aggregate_expression_check_spec import (
    ColumnSqlAggregateExpressionCheckSpec,
)
from .column_sql_aggregated_expression_sensor_parameters_spec import (
    ColumnSqlAggregatedExpressionSensorParametersSpec,
)
from .column_sql_condition_failed_check_spec import ColumnSqlConditionFailedCheckSpec
from .column_sql_condition_failed_count_sensor_parameters_spec import (
    ColumnSqlConditionFailedCountSensorParametersSpec,
)
from .column_sql_condition_passed_percent_check_spec import (
    ColumnSqlConditionPassedPercentCheckSpec,
)
from .column_sql_condition_passed_percent_sensor_parameters_spec import (
    ColumnSqlConditionPassedPercentSensorParametersSpec,
)
from .column_sql_import_custom_result_check_spec import (
    ColumnSqlImportCustomResultCheckSpec,
)
from .column_sql_import_custom_result_sensor_parameters_spec import (
    ColumnSqlImportCustomResultSensorParametersSpec,
)
from .column_statistics_collectors_root_categories_spec import (
    ColumnStatisticsCollectorsRootCategoriesSpec,
)
from .column_statistics_model import ColumnStatisticsModel
from .column_strings_expected_text_values_in_use_count_sensor_parameters_spec import (
    ColumnStringsExpectedTextValuesInUseCountSensorParametersSpec,
)
from .column_strings_expected_texts_in_top_values_count_sensor_parameters_spec import (
    ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec,
)
from .column_sum_anomaly_differencing_check_spec import (
    ColumnSumAnomalyDifferencingCheckSpec,
)
from .column_sum_anomaly_stationary_partition_check_spec import (
    ColumnSumAnomalyStationaryPartitionCheckSpec,
)
from .column_sum_change_1_day_check_spec import ColumnSumChange1DayCheckSpec
from .column_sum_change_7_days_check_spec import ColumnSumChange7DaysCheckSpec
from .column_sum_change_30_days_check_spec import ColumnSumChange30DaysCheckSpec
from .column_sum_change_check_spec import ColumnSumChangeCheckSpec
from .column_sum_in_range_check_spec import ColumnSumInRangeCheckSpec
from .column_text_daily_monitoring_checks_spec import (
    ColumnTextDailyMonitoringChecksSpec,
)
from .column_text_daily_monitoring_checks_spec_custom_checks import (
    ColumnTextDailyMonitoringChecksSpecCustomChecks,
)
from .column_text_daily_partitioned_checks_spec import (
    ColumnTextDailyPartitionedChecksSpec,
)
from .column_text_daily_partitioned_checks_spec_custom_checks import (
    ColumnTextDailyPartitionedChecksSpecCustomChecks,
)
from .column_text_found_in_set_percent_check_spec import (
    ColumnTextFoundInSetPercentCheckSpec,
)
from .column_text_length_above_max_length_check_spec import (
    ColumnTextLengthAboveMaxLengthCheckSpec,
)
from .column_text_length_above_max_length_percent_check_spec import (
    ColumnTextLengthAboveMaxLengthPercentCheckSpec,
)
from .column_text_length_below_min_length_check_spec import (
    ColumnTextLengthBelowMinLengthCheckSpec,
)
from .column_text_length_below_min_length_percent_check_spec import (
    ColumnTextLengthBelowMinLengthPercentCheckSpec,
)
from .column_text_length_in_range_percent_check_spec import (
    ColumnTextLengthInRangePercentCheckSpec,
)
from .column_text_match_date_format_percent_check_spec import (
    ColumnTextMatchDateFormatPercentCheckSpec,
)
from .column_text_match_date_format_percent_sensor_parameters_spec import (
    ColumnTextMatchDateFormatPercentSensorParametersSpec,
)
from .column_text_matching_date_pattern_percent_check_spec import (
    ColumnTextMatchingDatePatternPercentCheckSpec,
)
from .column_text_matching_name_pattern_percent_check_spec import (
    ColumnTextMatchingNamePatternPercentCheckSpec,
)
from .column_text_max_length_check_spec import ColumnTextMaxLengthCheckSpec
from .column_text_mean_length_check_spec import ColumnTextMeanLengthCheckSpec
from .column_text_min_length_check_spec import ColumnTextMinLengthCheckSpec
from .column_text_monthly_monitoring_checks_spec import (
    ColumnTextMonthlyMonitoringChecksSpec,
)
from .column_text_monthly_monitoring_checks_spec_custom_checks import (
    ColumnTextMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_text_monthly_partitioned_checks_spec import (
    ColumnTextMonthlyPartitionedChecksSpec,
)
from .column_text_monthly_partitioned_checks_spec_custom_checks import (
    ColumnTextMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_text_not_matching_date_pattern_found_check_spec import (
    ColumnTextNotMatchingDatePatternFoundCheckSpec,
)
from .column_text_not_matching_regex_found_check_spec import (
    ColumnTextNotMatchingRegexFoundCheckSpec,
)
from .column_text_parsable_to_boolean_percent_check_spec import (
    ColumnTextParsableToBooleanPercentCheckSpec,
)
from .column_text_parsable_to_date_percent_check_spec import (
    ColumnTextParsableToDatePercentCheckSpec,
)
from .column_text_parsable_to_float_percent_check_spec import (
    ColumnTextParsableToFloatPercentCheckSpec,
)
from .column_text_parsable_to_integer_percent_check_spec import (
    ColumnTextParsableToIntegerPercentCheckSpec,
)
from .column_text_profiling_checks_spec import ColumnTextProfilingChecksSpec
from .column_text_profiling_checks_spec_custom_checks import (
    ColumnTextProfilingChecksSpecCustomChecks,
)
from .column_text_statistics_collectors_spec import ColumnTextStatisticsCollectorsSpec
from .column_text_text_datatype_detect_statistics_collector_spec import (
    ColumnTextTextDatatypeDetectStatisticsCollectorSpec,
)
from .column_text_text_length_above_max_length_count_sensor_parameters_spec import (
    ColumnTextTextLengthAboveMaxLengthCountSensorParametersSpec,
)
from .column_text_text_length_above_max_length_percent_sensor_parameters_spec import (
    ColumnTextTextLengthAboveMaxLengthPercentSensorParametersSpec,
)
from .column_text_text_length_below_min_length_count_sensor_parameters_spec import (
    ColumnTextTextLengthBelowMinLengthCountSensorParametersSpec,
)
from .column_text_text_length_below_min_length_percent_sensor_parameters_spec import (
    ColumnTextTextLengthBelowMinLengthPercentSensorParametersSpec,
)
from .column_text_text_length_in_range_percent_sensor_parameters_spec import (
    ColumnTextTextLengthInRangePercentSensorParametersSpec,
)
from .column_text_text_max_length_sensor_parameters_spec import (
    ColumnTextTextMaxLengthSensorParametersSpec,
)
from .column_text_text_max_length_statistics_collector_spec import (
    ColumnTextTextMaxLengthStatisticsCollectorSpec,
)
from .column_text_text_mean_length_sensor_parameters_spec import (
    ColumnTextTextMeanLengthSensorParametersSpec,
)
from .column_text_text_mean_length_statistics_collector_spec import (
    ColumnTextTextMeanLengthStatisticsCollectorSpec,
)
from .column_text_text_min_length_sensor_parameters_spec import (
    ColumnTextTextMinLengthSensorParametersSpec,
)
from .column_text_text_min_length_statistics_collector_spec import (
    ColumnTextTextMinLengthStatisticsCollectorSpec,
)
from .column_text_text_parsable_to_boolean_percent_sensor_parameters_spec import (
    ColumnTextTextParsableToBooleanPercentSensorParametersSpec,
)
from .column_text_text_parsable_to_date_percent_sensor_parameters_spec import (
    ColumnTextTextParsableToDatePercentSensorParametersSpec,
)
from .column_text_text_parsable_to_float_percent_sensor_parameters_spec import (
    ColumnTextTextParsableToFloatPercentSensorParametersSpec,
)
from .column_text_text_parsable_to_integer_percent_sensor_parameters_spec import (
    ColumnTextTextParsableToIntegerPercentSensorParametersSpec,
)
from .column_text_text_valid_country_code_percent_sensor_parameters_spec import (
    ColumnTextTextValidCountryCodePercentSensorParametersSpec,
)
from .column_text_text_valid_currency_code_percent_sensor_parameters_spec import (
    ColumnTextTextValidCurrencyCodePercentSensorParametersSpec,
)
from .column_text_valid_country_code_percent_check_spec import (
    ColumnTextValidCountryCodePercentCheckSpec,
)
from .column_text_valid_currency_code_percent_check_spec import (
    ColumnTextValidCurrencyCodePercentCheckSpec,
)
from .column_texts_matching_regex_percent_check_spec import (
    ColumnTextsMatchingRegexPercentCheckSpec,
)
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
from .column_valid_uuid_format_percent_check_spec import (
    ColumnValidUuidFormatPercentCheckSpec,
)
from .column_whitespace_blank_null_placeholder_text_count_sensor_parameters_spec import (
    ColumnWhitespaceBlankNullPlaceholderTextCountSensorParametersSpec,
)
from .column_whitespace_blank_null_placeholder_text_percent_sensor_parameters_spec import (
    ColumnWhitespaceBlankNullPlaceholderTextPercentSensorParametersSpec,
)
from .column_whitespace_daily_monitoring_checks_spec import (
    ColumnWhitespaceDailyMonitoringChecksSpec,
)
from .column_whitespace_daily_monitoring_checks_spec_custom_checks import (
    ColumnWhitespaceDailyMonitoringChecksSpecCustomChecks,
)
from .column_whitespace_daily_partitioned_checks_spec import (
    ColumnWhitespaceDailyPartitionedChecksSpec,
)
from .column_whitespace_daily_partitioned_checks_spec_custom_checks import (
    ColumnWhitespaceDailyPartitionedChecksSpecCustomChecks,
)
from .column_whitespace_empty_text_count_sensor_parameters_spec import (
    ColumnWhitespaceEmptyTextCountSensorParametersSpec,
)
from .column_whitespace_empty_text_found_check_spec import (
    ColumnWhitespaceEmptyTextFoundCheckSpec,
)
from .column_whitespace_empty_text_percent_check_spec import (
    ColumnWhitespaceEmptyTextPercentCheckSpec,
)
from .column_whitespace_empty_text_percent_sensor_parameters_spec import (
    ColumnWhitespaceEmptyTextPercentSensorParametersSpec,
)
from .column_whitespace_monthly_monitoring_checks_spec import (
    ColumnWhitespaceMonthlyMonitoringChecksSpec,
)
from .column_whitespace_monthly_monitoring_checks_spec_custom_checks import (
    ColumnWhitespaceMonthlyMonitoringChecksSpecCustomChecks,
)
from .column_whitespace_monthly_partitioned_checks_spec import (
    ColumnWhitespaceMonthlyPartitionedChecksSpec,
)
from .column_whitespace_monthly_partitioned_checks_spec_custom_checks import (
    ColumnWhitespaceMonthlyPartitionedChecksSpecCustomChecks,
)
from .column_whitespace_null_placeholder_text_found_check_spec import (
    ColumnWhitespaceNullPlaceholderTextFoundCheckSpec,
)
from .column_whitespace_null_placeholder_text_percent_check_spec import (
    ColumnWhitespaceNullPlaceholderTextPercentCheckSpec,
)
from .column_whitespace_profiling_checks_spec import ColumnWhitespaceProfilingChecksSpec
from .column_whitespace_profiling_checks_spec_custom_checks import (
    ColumnWhitespaceProfilingChecksSpecCustomChecks,
)
from .column_whitespace_text_surrounded_by_whitespace_count_sensor_parameters_spec import (
    ColumnWhitespaceTextSurroundedByWhitespaceCountSensorParametersSpec,
)
from .column_whitespace_text_surrounded_by_whitespace_found_check_spec import (
    ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec,
)
from .column_whitespace_text_surrounded_by_whitespace_percent_check_spec import (
    ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec,
)
from .column_whitespace_text_surrounded_by_whitespace_percent_sensor_parameters_spec import (
    ColumnWhitespaceTextSurroundedByWhitespacePercentSensorParametersSpec,
)
from .column_whitespace_whitespace_text_count_sensor_parameters_spec import (
    ColumnWhitespaceWhitespaceTextCountSensorParametersSpec,
)
from .column_whitespace_whitespace_text_found_check_spec import (
    ColumnWhitespaceWhitespaceTextFoundCheckSpec,
)
from .column_whitespace_whitespace_text_percent_check_spec import (
    ColumnWhitespaceWhitespaceTextPercentCheckSpec,
)
from .column_whitespace_whitespace_text_percent_sensor_parameters_spec import (
    ColumnWhitespaceWhitespaceTextPercentSensorParametersSpec,
)
from .comment_spec import CommentSpec
from .common_column_model import CommonColumnModel
from .compare_thresholds_model import CompareThresholdsModel
from .comparison_check_result_model import ComparisonCheckResultModel
from .compression_type import CompressionType
from .connection_incident_grouping_spec import ConnectionIncidentGroupingSpec
from .connection_model import ConnectionModel
from .connection_spec import ConnectionSpec
from .connection_specification_model import ConnectionSpecificationModel
from .connection_test_model import ConnectionTestModel
from .connection_test_status import ConnectionTestStatus
from .count_between_rule_parameters_spec import CountBetweenRuleParametersSpec
from .credential_type import CredentialType
from .csv_file_format_spec import CsvFileFormatSpec
from .custom_check_spec import CustomCheckSpec
from .custom_rule_parameters_spec import CustomRuleParametersSpec
from .custom_sensor_parameters_spec import CustomSensorParametersSpec
from .dashboard_spec import DashboardSpec
from .dashboard_spec_parameters import DashboardSpecParameters
from .dashboards_folder_spec import DashboardsFolderSpec
from .data_delete_result_partition import DataDeleteResultPartition
from .data_dictionary_list_model import DataDictionaryListModel
from .data_dictionary_model import DataDictionaryModel
from .data_grouping_configuration_list_model import DataGroupingConfigurationListModel
from .data_grouping_configuration_model import DataGroupingConfigurationModel
from .data_grouping_configuration_spec import DataGroupingConfigurationSpec
from .data_grouping_configuration_trimmed_model import (
    DataGroupingConfigurationTrimmedModel,
)
from .data_grouping_dimension_source import DataGroupingDimensionSource
from .data_grouping_dimension_spec import DataGroupingDimensionSpec
from .data_type_category import DataTypeCategory
from .databricks_parameters_spec import DatabricksParametersSpec
from .databricks_parameters_spec_properties import DatabricksParametersSpecProperties
from .datetime_built_in_date_formats import DatetimeBuiltInDateFormats
from .default_column_checks_pattern_list_model import (
    DefaultColumnChecksPatternListModel,
)
from .default_column_checks_pattern_model import DefaultColumnChecksPatternModel
from .default_schedules_spec import DefaultSchedulesSpec
from .default_table_checks_pattern_list_model import DefaultTableChecksPatternListModel
from .default_table_checks_pattern_model import DefaultTableChecksPatternModel
from .delete_stored_data_queue_job_parameters import DeleteStoredDataQueueJobParameters
from .delete_stored_data_queue_job_result import DeleteStoredDataQueueJobResult
from .delete_stored_data_result import DeleteStoredDataResult
from .delete_stored_data_result_partition_results import (
    DeleteStoredDataResultPartitionResults,
)
from .detected_datatype_category import DetectedDatatypeCategory
from .detected_datatype_equals_rule_parameters_spec import (
    DetectedDatatypeEqualsRuleParametersSpec,
)
from .dimension_current_data_quality_status_model import (
    DimensionCurrentDataQualityStatusModel,
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
from .duckdb_files_format_type import DuckdbFilesFormatType
from .duckdb_parameters_spec import DuckdbParametersSpec
from .duckdb_parameters_spec_directories import DuckdbParametersSpecDirectories
from .duckdb_parameters_spec_properties import DuckdbParametersSpecProperties
from .duckdb_read_mode import DuckdbReadMode
from .duckdb_storage_type import DuckdbStorageType
from .duration import Duration
from .effective_schedule_level_model import EffectiveScheduleLevelModel
from .effective_schedule_model import EffectiveScheduleModel
from .equals_1_rule_parameters_spec import Equals1RuleParametersSpec
from .equals_integer_rule_parameters_spec import EqualsIntegerRuleParametersSpec
from .error_entry_model import ErrorEntryModel
from .error_sample_entry_model import ErrorSampleEntryModel
from .error_sample_entry_model_result import ErrorSampleEntryModelResult
from .error_sample_result_data_type import ErrorSampleResultDataType
from .error_sampler_result import ErrorSamplerResult
from .error_samples_data_scope import ErrorSamplesDataScope
from .error_samples_list_model import ErrorSamplesListModel
from .errors_list_model import ErrorsListModel
from .external_log_entry import ExternalLogEntry
from .field_model import FieldModel
from .file_format_spec import FileFormatSpec
from .file_synchronization_direction import FileSynchronizationDirection
from .folder_synchronization_status import FolderSynchronizationStatus
from .hierarchy_id_model import HierarchyIdModel
from .hierarchy_id_model_path_item import HierarchyIdModelPathItem
from .historic_data_points_grouping import HistoricDataPointsGrouping
from .import_schema_queue_job_parameters import ImportSchemaQueueJobParameters
from .import_severity_rule_parameters_spec import ImportSeverityRuleParametersSpec
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
from .json_file_format_spec import JsonFileFormatSpec
from .json_format_type import JsonFormatType
from .json_records_type import JsonRecordsType
from .label_model import LabelModel
from .max_count_rule_0_error_parameters_spec import MaxCountRule0ErrorParametersSpec
from .max_count_rule_0_warning_parameters_spec import MaxCountRule0WarningParametersSpec
from .max_count_rule_100_parameters_spec import MaxCountRule100ParametersSpec
from .max_days_rule_1_parameters_spec import MaxDaysRule1ParametersSpec
from .max_days_rule_2_parameters_spec import MaxDaysRule2ParametersSpec
from .max_days_rule_7_parameters_spec import MaxDaysRule7ParametersSpec
from .max_diff_percent_rule_0_parameters_spec import MaxDiffPercentRule0ParametersSpec
from .max_diff_percent_rule_1_parameters_spec import MaxDiffPercentRule1ParametersSpec
from .max_diff_percent_rule_5_parameters_spec import MaxDiffPercentRule5ParametersSpec
from .max_failures_rule_0_parameters_spec import MaxFailuresRule0ParametersSpec
from .max_failures_rule_1_parameters_spec import MaxFailuresRule1ParametersSpec
from .max_failures_rule_5_parameters_spec import MaxFailuresRule5ParametersSpec
from .max_missing_rule_0_error_parameters_spec import MaxMissingRule0ErrorParametersSpec
from .max_missing_rule_0_warning_parameters_spec import (
    MaxMissingRule0WarningParametersSpec,
)
from .max_missing_rule_2_parameters_spec import MaxMissingRule2ParametersSpec
from .max_percent_rule_0_error_parameters_spec import MaxPercentRule0ErrorParametersSpec
from .max_percent_rule_0_warning_parameters_spec import (
    MaxPercentRule0WarningParametersSpec,
)
from .max_percent_rule_5_parameters_spec import MaxPercentRule5ParametersSpec
from .min_count_rule_1_parameters_spec import MinCountRule1ParametersSpec
from .min_percent_rule_95_parameters_spec import MinPercentRule95ParametersSpec
from .min_percent_rule_100_error_parameters_spec import (
    MinPercentRule100ErrorParametersSpec,
)
from .min_percent_rule_100_warning_parameters_spec import (
    MinPercentRule100WarningParametersSpec,
)
from .minimum_grouping_severity_level import MinimumGroupingSeverityLevel
from .monitoring_schedule_spec import MonitoringScheduleSpec
from .mono import Mono
from .mono_response_entity_mono_dqo_queue_job_id import (
    MonoResponseEntityMonoDqoQueueJobId,
)
from .mono_response_entity_mono_object import MonoResponseEntityMonoObject
from .mono_response_entity_mono_void import MonoResponseEntityMonoVoid
from .my_sql_ssl_mode import MySqlSslMode
from .mysql_engine_type import MysqlEngineType
from .mysql_parameters_spec import MysqlParametersSpec
from .mysql_parameters_spec_properties import MysqlParametersSpecProperties
from .new_line_character_type import NewLineCharacterType
from .optional import Optional
from .optional_incident_webhook_notifications_spec import (
    OptionalIncidentWebhookNotificationsSpec,
)
from .optional_monitoring_schedule_spec import OptionalMonitoringScheduleSpec
from .oracle_parameters_spec import OracleParametersSpec
from .oracle_parameters_spec_properties import OracleParametersSpecProperties
from .parameter_data_type import ParameterDataType
from .parameter_definition_spec import ParameterDefinitionSpec
from .parquet_file_format_spec import ParquetFileFormatSpec
from .partition_incremental_time_window_spec import PartitionIncrementalTimeWindowSpec
from .physical_table_name import PhysicalTableName
from .postgresql_parameters_spec import PostgresqlParametersSpec
from .postgresql_parameters_spec_properties import PostgresqlParametersSpecProperties
from .postgresql_ssl_mode import PostgresqlSslMode
from .presto_parameters_spec import PrestoParametersSpec
from .presto_parameters_spec_properties import PrestoParametersSpecProperties
from .profiling_time_period_truncation import ProfilingTimePeriodTruncation
from .provider_sensor_definition_spec import ProviderSensorDefinitionSpec
from .provider_sensor_definition_spec_parameters import (
    ProviderSensorDefinitionSpecParameters,
)
from .provider_sensor_list_model import ProviderSensorListModel
from .provider_sensor_model import ProviderSensorModel
from .provider_sensor_runner_type import ProviderSensorRunnerType
from .provider_type import ProviderType
from .quality_category_model import QualityCategoryModel
from .redshift_authentication_mode import RedshiftAuthenticationMode
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
from .single_store_db_load_balancing_mode import SingleStoreDbLoadBalancingMode
from .single_store_db_parameters_spec import SingleStoreDbParametersSpec
from .snowflake_parameters_spec import SnowflakeParametersSpec
from .snowflake_parameters_spec_properties import SnowflakeParametersSpecProperties
from .sort_direction import SortDirection
from .spark_parameters_spec import SparkParametersSpec
from .spark_parameters_spec_properties import SparkParametersSpecProperties
from .spring_error_payload import SpringErrorPayload
from .sql_server_authentication_mode import SqlServerAuthenticationMode
from .sql_server_parameters_spec import SqlServerParametersSpec
from .sql_server_parameters_spec_properties import SqlServerParametersSpecProperties
from .statistics_collector_search_filters import StatisticsCollectorSearchFilters
from .statistics_collector_target import StatisticsCollectorTarget
from .statistics_data_scope import StatisticsDataScope
from .statistics_metric_model import StatisticsMetricModel
from .statistics_metric_model_result import StatisticsMetricModelResult
from .statistics_result_data_type import StatisticsResultDataType
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
from .table_current_data_quality_status_model_dimensions import (
    TableCurrentDataQualityStatusModelDimensions,
)
from .table_custom_sql_daily_monitoring_checks_spec import (
    TableCustomSqlDailyMonitoringChecksSpec,
)
from .table_custom_sql_daily_monitoring_checks_spec_custom_checks import (
    TableCustomSqlDailyMonitoringChecksSpecCustomChecks,
)
from .table_custom_sql_daily_partitioned_checks_spec import (
    TableCustomSqlDailyPartitionedChecksSpec,
)
from .table_custom_sql_daily_partitioned_checks_spec_custom_checks import (
    TableCustomSqlDailyPartitionedChecksSpecCustomChecks,
)
from .table_custom_sql_monthly_monitoring_checks_spec import (
    TableCustomSqlMonthlyMonitoringChecksSpec,
)
from .table_custom_sql_monthly_monitoring_checks_spec_custom_checks import (
    TableCustomSqlMonthlyMonitoringChecksSpecCustomChecks,
)
from .table_custom_sql_monthly_partitioned_checks_spec import (
    TableCustomSqlMonthlyPartitionedChecksSpec,
)
from .table_custom_sql_monthly_partitioned_checks_spec_custom_checks import (
    TableCustomSqlMonthlyPartitionedChecksSpecCustomChecks,
)
from .table_custom_sql_profiling_checks_spec import TableCustomSqlProfilingChecksSpec
from .table_custom_sql_profiling_checks_spec_custom_checks import (
    TableCustomSqlProfilingChecksSpecCustomChecks,
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
from .table_data_freshness_anomaly_check_spec import TableDataFreshnessAnomalyCheckSpec
from .table_data_freshness_check_spec import TableDataFreshnessCheckSpec
from .table_data_ingestion_delay_check_spec import TableDataIngestionDelayCheckSpec
from .table_data_staleness_check_spec import TableDataStalenessCheckSpec
from .table_default_checks_pattern_spec import TableDefaultChecksPatternSpec
from .table_incident_grouping_spec import TableIncidentGroupingSpec
from .table_list_model import TableListModel
from .table_model import TableModel
from .table_monitoring_check_categories_spec import TableMonitoringCheckCategoriesSpec
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
from .table_partitioned_check_categories_spec import TablePartitionedCheckCategoriesSpec
from .table_partitioning_model import TablePartitioningModel
from .table_profiling_check_categories_spec import TableProfilingCheckCategoriesSpec
from .table_profiling_check_categories_spec_comparisons import (
    TableProfilingCheckCategoriesSpecComparisons,
)
from .table_profiling_check_categories_spec_custom import (
    TableProfilingCheckCategoriesSpecCustom,
)
from .table_row_count_anomaly_differencing_check_spec import (
    TableRowCountAnomalyDifferencingCheckSpec,
)
from .table_row_count_anomaly_stationary_partition_check_spec import (
    TableRowCountAnomalyStationaryPartitionCheckSpec,
)
from .table_row_count_change_1_day_check_spec import TableRowCountChange1DayCheckSpec
from .table_row_count_change_7_days_check_spec import TableRowCountChange7DaysCheckSpec
from .table_row_count_change_30_days_check_spec import (
    TableRowCountChange30DaysCheckSpec,
)
from .table_row_count_change_check_spec import TableRowCountChangeCheckSpec
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
from .table_sql_aggregate_expression_check_spec import (
    TableSqlAggregateExpressionCheckSpec,
)
from .table_sql_aggregated_expression_sensor_parameters_spec import (
    TableSqlAggregatedExpressionSensorParametersSpec,
)
from .table_sql_condition_failed_check_spec import TableSqlConditionFailedCheckSpec
from .table_sql_condition_failed_count_sensor_parameters_spec import (
    TableSqlConditionFailedCountSensorParametersSpec,
)
from .table_sql_condition_passed_percent_check_spec import (
    TableSqlConditionPassedPercentCheckSpec,
)
from .table_sql_condition_passed_percent_sensor_parameters_spec import (
    TableSqlConditionPassedPercentSensorParametersSpec,
)
from .table_sql_import_custom_result_check_spec import (
    TableSqlImportCustomResultCheckSpec,
)
from .table_sql_import_custom_result_sensor_parameters_spec import (
    TableSqlImportCustomResultSensorParametersSpec,
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
from .target_column_pattern_spec import TargetColumnPatternSpec
from .target_table_pattern_spec import TargetTablePatternSpec
from .temporal_unit import TemporalUnit
from .text_built_in_date_formats import TextBuiltInDateFormats
from .time_period_gradient import TimePeriodGradient
from .time_window_filter_parameters import TimeWindowFilterParameters
from .timestamp_columns_spec import TimestampColumnsSpec
from .top_incident_grouping import TopIncidentGrouping
from .top_incidents_model import TopIncidentsModel
from .top_incidents_model_top_incidents import TopIncidentsModelTopIncidents
from .trino_engine_type import TrinoEngineType
from .trino_parameters_spec import TrinoParametersSpec
from .trino_parameters_spec_properties import TrinoParametersSpecProperties
from .value_changed_rule_parameters_spec import ValueChangedRuleParametersSpec

__all__ = (
    "AllChecksPatchParameters",
    "AllChecksPatchParametersSelectedTablesToColumns",
    "AnomalyDifferencingPercentileMovingAverageRuleError05PctParametersSpec",
    "AnomalyDifferencingPercentileMovingAverageRuleFatal01PctParametersSpec",
    "AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec",
    "AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec",
    "AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec",
    "AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec",
    "AuthenticatedDashboardModel",
    "AwsAuthenticationMode",
    "AzureAuthenticationMode",
    "BetweenFloatsRuleParametersSpec",
    "BetweenIntsRuleParametersSpec",
    "BetweenPercentRuleParametersSpec",
    "BigQueryAuthenticationMode",
    "BigQueryJobsCreateProject",
    "BigQueryParametersSpec",
    "BulkCheckDeactivateParameters",
    "BulkCheckDeactivateParametersSelectedTablesToColumns",
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
    "CollectErrorSamplesOnTableParameters",
    "CollectErrorSamplesParameters",
    "CollectErrorSamplesResult",
    "CollectStatisticsOnTableQueueJobParameters",
    "CollectStatisticsQueueJobParameters",
    "CollectStatisticsQueueJobResult",
    "CollectStatisticsResult",
    "ColumnAcceptedValuesDailyMonitoringChecksSpec",
    "ColumnAcceptedValuesDailyMonitoringChecksSpecCustomChecks",
    "ColumnAcceptedValuesDailyPartitionedChecksSpec",
    "ColumnAcceptedValuesDailyPartitionedChecksSpecCustomChecks",
    "ColumnAcceptedValuesMonthlyMonitoringChecksSpec",
    "ColumnAcceptedValuesMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnAcceptedValuesMonthlyPartitionedChecksSpec",
    "ColumnAcceptedValuesMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnAcceptedValuesProfilingChecksSpec",
    "ColumnAcceptedValuesProfilingChecksSpecCustomChecks",
    "ColumnAcceptedValuesTextFoundInSetPercentSensorParametersSpec",
    "ColumnAccuracyDailyMonitoringChecksSpec",
    "ColumnAccuracyDailyMonitoringChecksSpecCustomChecks",
    "ColumnAccuracyMonthlyMonitoringChecksSpec",
    "ColumnAccuracyMonthlyMonitoringChecksSpecCustomChecks",
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
    "ColumnAnomalyProfilingChecksSpec",
    "ColumnAnomalyProfilingChecksSpecCustomChecks",
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
    "ColumnConversionsDailyMonitoringChecksSpec",
    "ColumnConversionsDailyMonitoringChecksSpecCustomChecks",
    "ColumnConversionsDailyPartitionedChecksSpec",
    "ColumnConversionsDailyPartitionedChecksSpecCustomChecks",
    "ColumnConversionsMonthlyMonitoringChecksSpec",
    "ColumnConversionsMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnConversionsMonthlyPartitionedChecksSpec",
    "ColumnConversionsMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnConversionsProfilingChecksSpec",
    "ColumnConversionsProfilingChecksSpecCustomChecks",
    "ColumnCurrentDataQualityStatusModel",
    "ColumnCurrentDataQualityStatusModelChecks",
    "ColumnCurrentDataQualityStatusModelDimensions",
    "ColumnCustomSqlDailyMonitoringChecksSpec",
    "ColumnCustomSqlDailyMonitoringChecksSpecCustomChecks",
    "ColumnCustomSqlDailyPartitionedChecksSpec",
    "ColumnCustomSqlDailyPartitionedChecksSpecCustomChecks",
    "ColumnCustomSqlMonthlyMonitoringChecksSpec",
    "ColumnCustomSqlMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnCustomSqlMonthlyPartitionedChecksSpec",
    "ColumnCustomSqlMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnCustomSqlProfilingChecksSpec",
    "ColumnCustomSqlProfilingChecksSpecCustomChecks",
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
    "ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec",
    "ColumnDatatypeMonthlyMonitoringChecksSpec",
    "ColumnDatatypeMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnDatatypeMonthlyPartitionedChecksSpec",
    "ColumnDatatypeMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnDatatypeProfilingChecksSpec",
    "ColumnDatatypeProfilingChecksSpecCustomChecks",
    "ColumnDatatypeStringDatatypeDetectSensorParametersSpec",
    "ColumnDateInRangePercentCheckSpec",
    "ColumnDateInRangePercentSensorParametersSpec",
    "ColumnDatetimeDailyMonitoringChecksSpec",
    "ColumnDatetimeDailyMonitoringChecksSpecCustomChecks",
    "ColumnDatetimeDailyPartitionedChecksSpec",
    "ColumnDatetimeDailyPartitionedChecksSpecCustomChecks",
    "ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec",
    "ColumnDatetimeMonthlyMonitoringChecksSpec",
    "ColumnDatetimeMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnDatetimeMonthlyPartitionedChecksSpec",
    "ColumnDatetimeMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnDatetimeProfilingChecksSpec",
    "ColumnDatetimeProfilingChecksSpecCustomChecks",
    "ColumnDateValuesInFuturePercentCheckSpec",
    "ColumnDefaultChecksPatternSpec",
    "ColumnDetectedDatatypeInTextCheckSpec",
    "ColumnDistinctCountAnomalyDifferencingCheckSpec",
    "ColumnDistinctCountAnomalyStationaryPartitionCheckSpec",
    "ColumnDistinctCountChange1DayCheckSpec",
    "ColumnDistinctCountChange30DaysCheckSpec",
    "ColumnDistinctCountChange7DaysCheckSpec",
    "ColumnDistinctCountChangeCheckSpec",
    "ColumnDistinctCountCheckSpec",
    "ColumnDistinctPercentAnomalyStationaryCheckSpec",
    "ColumnDistinctPercentChange1DayCheckSpec",
    "ColumnDistinctPercentChange30DaysCheckSpec",
    "ColumnDistinctPercentChange7DaysCheckSpec",
    "ColumnDistinctPercentChangeCheckSpec",
    "ColumnDistinctPercentCheckSpec",
    "ColumnDuplicateCountCheckSpec",
    "ColumnDuplicatePercentCheckSpec",
    "ColumnExpectedNumbersInUseCountCheckSpec",
    "ColumnExpectedTextsInTopValuesCountCheckSpec",
    "ColumnExpectedTextValuesInUseCountCheckSpec",
    "ColumnFalsePercentCheckSpec",
    "ColumnIntegerInRangePercentCheckSpec",
    "ColumnIntegrityDailyMonitoringChecksSpec",
    "ColumnIntegrityDailyMonitoringChecksSpecCustomChecks",
    "ColumnIntegrityDailyPartitionedChecksSpec",
    "ColumnIntegrityDailyPartitionedChecksSpecCustomChecks",
    "ColumnIntegrityForeignKeyMatchPercentCheckSpec",
    "ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec",
    "ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec",
    "ColumnIntegrityLookupKeyNotFoundCountCheckSpec",
    "ColumnIntegrityMonthlyMonitoringChecksSpec",
    "ColumnIntegrityMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnIntegrityMonthlyPartitionedChecksSpec",
    "ColumnIntegrityMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnIntegrityProfilingChecksSpec",
    "ColumnIntegrityProfilingChecksSpecCustomChecks",
    "ColumnInvalidEmailFormatFoundCheckSpec",
    "ColumnInvalidEmailFormatPercentCheckSpec",
    "ColumnInvalidIp4AddressFormatFoundCheckSpec",
    "ColumnInvalidIp6AddressFormatFoundCheckSpec",
    "ColumnInvalidLatitudeCountCheckSpec",
    "ColumnInvalidLongitudeCountCheckSpec",
    "ColumnInvalidUuidFormatFoundCheckSpec",
    "ColumnListModel",
    "ColumnMaxAnomalyDifferencingCheckSpec",
    "ColumnMaxAnomalyStationaryCheckSpec",
    "ColumnMaxInRangeCheckSpec",
    "ColumnMeanAnomalyStationaryCheckSpec",
    "ColumnMeanChange1DayCheckSpec",
    "ColumnMeanChange30DaysCheckSpec",
    "ColumnMeanChange7DaysCheckSpec",
    "ColumnMeanChangeCheckSpec",
    "ColumnMeanInRangeCheckSpec",
    "ColumnMedianAnomalyStationaryCheckSpec",
    "ColumnMedianChange1DayCheckSpec",
    "ColumnMedianChange30DaysCheckSpec",
    "ColumnMedianChange7DaysCheckSpec",
    "ColumnMedianChangeCheckSpec",
    "ColumnMedianInRangeCheckSpec",
    "ColumnMinAnomalyDifferencingCheckSpec",
    "ColumnMinAnomalyStationaryCheckSpec",
    "ColumnMinInRangeCheckSpec",
    "ColumnModel",
    "ColumnMonitoringCheckCategoriesSpec",
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
    "ColumnNullPercentAnomalyStationaryCheckSpec",
    "ColumnNullPercentChange1DayCheckSpec",
    "ColumnNullPercentChange30DaysCheckSpec",
    "ColumnNullPercentChange7DaysCheckSpec",
    "ColumnNullPercentChangeCheckSpec",
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
    "ColumnNumberAboveMaxValueCheckSpec",
    "ColumnNumberAboveMaxValuePercentCheckSpec",
    "ColumnNumberBelowMinValueCheckSpec",
    "ColumnNumberBelowMinValuePercentCheckSpec",
    "ColumnNumberFoundInSetPercentCheckSpec",
    "ColumnNumberInRangePercentCheckSpec",
    "ColumnNumericDailyMonitoringChecksSpec",
    "ColumnNumericDailyMonitoringChecksSpecCustomChecks",
    "ColumnNumericDailyPartitionedChecksSpec",
    "ColumnNumericDailyPartitionedChecksSpecCustomChecks",
    "ColumnNumericExpectedNumbersInUseCountSensorParametersSpec",
    "ColumnNumericIntegerInRangePercentSensorParametersSpec",
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
    "ColumnNumericNumberAboveMaxValueCountSensorParametersSpec",
    "ColumnNumericNumberAboveMaxValuePercentSensorParametersSpec",
    "ColumnNumericNumberBelowMinValueCountSensorParametersSpec",
    "ColumnNumericNumberBelowMinValuePercentSensorParametersSpec",
    "ColumnNumericNumberFoundInSetPercentSensorParametersSpec",
    "ColumnNumericNumberInRangePercentSensorParametersSpec",
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
    "ColumnPartitionedCheckCategoriesSpec",
    "ColumnPatternsDailyMonitoringChecksSpec",
    "ColumnPatternsDailyMonitoringChecksSpecCustomChecks",
    "ColumnPatternsDailyPartitionedChecksSpec",
    "ColumnPatternsDailyPartitionedChecksSpecCustomChecks",
    "ColumnPatternsInvalidEmailFormatCountSensorParametersSpec",
    "ColumnPatternsInvalidEmailFormatPercentSensorParametersSpec",
    "ColumnPatternsInvalidIp4AddressFormatCountSensorParametersSpec",
    "ColumnPatternsInvalidIp6AddressFormatCountSensorParametersSpec",
    "ColumnPatternsInvalidUuidFormatCountSensorParametersSpec",
    "ColumnPatternsMonthlyMonitoringChecksSpec",
    "ColumnPatternsMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnPatternsMonthlyPartitionedChecksSpec",
    "ColumnPatternsMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnPatternsProfilingChecksSpec",
    "ColumnPatternsProfilingChecksSpecCustomChecks",
    "ColumnPatternsTextMatchingDatePatternPercentSensorParametersSpec",
    "ColumnPatternsTextMatchingNamePatternPercentSensorParametersSpec",
    "ColumnPatternsTextNotMatchingDatePatternCountSensorParametersSpec",
    "ColumnPatternsTextNotMatchingRegexCountSensorParametersSpec",
    "ColumnPatternsTextsMatchingRegexPercentSensorParametersSpec",
    "ColumnPatternsValidUuidFormatPercentSensorParametersSpec",
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
    "ColumnSqlAggregateExpressionCheckSpec",
    "ColumnSqlConditionFailedCheckSpec",
    "ColumnSqlConditionFailedCountSensorParametersSpec",
    "ColumnSqlConditionPassedPercentCheckSpec",
    "ColumnSqlConditionPassedPercentSensorParametersSpec",
    "ColumnSqlImportCustomResultCheckSpec",
    "ColumnSqlImportCustomResultSensorParametersSpec",
    "ColumnStatisticsCollectorsRootCategoriesSpec",
    "ColumnStatisticsModel",
    "ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec",
    "ColumnStringsExpectedTextValuesInUseCountSensorParametersSpec",
    "ColumnSumAnomalyDifferencingCheckSpec",
    "ColumnSumAnomalyStationaryPartitionCheckSpec",
    "ColumnSumChange1DayCheckSpec",
    "ColumnSumChange30DaysCheckSpec",
    "ColumnSumChange7DaysCheckSpec",
    "ColumnSumChangeCheckSpec",
    "ColumnSumInRangeCheckSpec",
    "ColumnTextDailyMonitoringChecksSpec",
    "ColumnTextDailyMonitoringChecksSpecCustomChecks",
    "ColumnTextDailyPartitionedChecksSpec",
    "ColumnTextDailyPartitionedChecksSpecCustomChecks",
    "ColumnTextFoundInSetPercentCheckSpec",
    "ColumnTextLengthAboveMaxLengthCheckSpec",
    "ColumnTextLengthAboveMaxLengthPercentCheckSpec",
    "ColumnTextLengthBelowMinLengthCheckSpec",
    "ColumnTextLengthBelowMinLengthPercentCheckSpec",
    "ColumnTextLengthInRangePercentCheckSpec",
    "ColumnTextMatchDateFormatPercentCheckSpec",
    "ColumnTextMatchDateFormatPercentSensorParametersSpec",
    "ColumnTextMatchingDatePatternPercentCheckSpec",
    "ColumnTextMatchingNamePatternPercentCheckSpec",
    "ColumnTextMaxLengthCheckSpec",
    "ColumnTextMeanLengthCheckSpec",
    "ColumnTextMinLengthCheckSpec",
    "ColumnTextMonthlyMonitoringChecksSpec",
    "ColumnTextMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnTextMonthlyPartitionedChecksSpec",
    "ColumnTextMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnTextNotMatchingDatePatternFoundCheckSpec",
    "ColumnTextNotMatchingRegexFoundCheckSpec",
    "ColumnTextParsableToBooleanPercentCheckSpec",
    "ColumnTextParsableToDatePercentCheckSpec",
    "ColumnTextParsableToFloatPercentCheckSpec",
    "ColumnTextParsableToIntegerPercentCheckSpec",
    "ColumnTextProfilingChecksSpec",
    "ColumnTextProfilingChecksSpecCustomChecks",
    "ColumnTextsMatchingRegexPercentCheckSpec",
    "ColumnTextStatisticsCollectorsSpec",
    "ColumnTextTextDatatypeDetectStatisticsCollectorSpec",
    "ColumnTextTextLengthAboveMaxLengthCountSensorParametersSpec",
    "ColumnTextTextLengthAboveMaxLengthPercentSensorParametersSpec",
    "ColumnTextTextLengthBelowMinLengthCountSensorParametersSpec",
    "ColumnTextTextLengthBelowMinLengthPercentSensorParametersSpec",
    "ColumnTextTextLengthInRangePercentSensorParametersSpec",
    "ColumnTextTextMaxLengthSensorParametersSpec",
    "ColumnTextTextMaxLengthStatisticsCollectorSpec",
    "ColumnTextTextMeanLengthSensorParametersSpec",
    "ColumnTextTextMeanLengthStatisticsCollectorSpec",
    "ColumnTextTextMinLengthSensorParametersSpec",
    "ColumnTextTextMinLengthStatisticsCollectorSpec",
    "ColumnTextTextParsableToBooleanPercentSensorParametersSpec",
    "ColumnTextTextParsableToDatePercentSensorParametersSpec",
    "ColumnTextTextParsableToFloatPercentSensorParametersSpec",
    "ColumnTextTextParsableToIntegerPercentSensorParametersSpec",
    "ColumnTextTextValidCountryCodePercentSensorParametersSpec",
    "ColumnTextTextValidCurrencyCodePercentSensorParametersSpec",
    "ColumnTextValidCountryCodePercentCheckSpec",
    "ColumnTextValidCurrencyCodePercentCheckSpec",
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
    "ColumnValidUuidFormatPercentCheckSpec",
    "ColumnWhitespaceBlankNullPlaceholderTextCountSensorParametersSpec",
    "ColumnWhitespaceBlankNullPlaceholderTextPercentSensorParametersSpec",
    "ColumnWhitespaceDailyMonitoringChecksSpec",
    "ColumnWhitespaceDailyMonitoringChecksSpecCustomChecks",
    "ColumnWhitespaceDailyPartitionedChecksSpec",
    "ColumnWhitespaceDailyPartitionedChecksSpecCustomChecks",
    "ColumnWhitespaceEmptyTextCountSensorParametersSpec",
    "ColumnWhitespaceEmptyTextFoundCheckSpec",
    "ColumnWhitespaceEmptyTextPercentCheckSpec",
    "ColumnWhitespaceEmptyTextPercentSensorParametersSpec",
    "ColumnWhitespaceMonthlyMonitoringChecksSpec",
    "ColumnWhitespaceMonthlyMonitoringChecksSpecCustomChecks",
    "ColumnWhitespaceMonthlyPartitionedChecksSpec",
    "ColumnWhitespaceMonthlyPartitionedChecksSpecCustomChecks",
    "ColumnWhitespaceNullPlaceholderTextFoundCheckSpec",
    "ColumnWhitespaceNullPlaceholderTextPercentCheckSpec",
    "ColumnWhitespaceProfilingChecksSpec",
    "ColumnWhitespaceProfilingChecksSpecCustomChecks",
    "ColumnWhitespaceTextSurroundedByWhitespaceCountSensorParametersSpec",
    "ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec",
    "ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec",
    "ColumnWhitespaceTextSurroundedByWhitespacePercentSensorParametersSpec",
    "ColumnWhitespaceWhitespaceTextCountSensorParametersSpec",
    "ColumnWhitespaceWhitespaceTextFoundCheckSpec",
    "ColumnWhitespaceWhitespaceTextPercentCheckSpec",
    "ColumnWhitespaceWhitespaceTextPercentSensorParametersSpec",
    "CommentSpec",
    "CommonColumnModel",
    "CompareThresholdsModel",
    "ComparisonCheckResultModel",
    "CompressionType",
    "ConnectionIncidentGroupingSpec",
    "ConnectionModel",
    "ConnectionSpec",
    "ConnectionSpecificationModel",
    "ConnectionTestModel",
    "ConnectionTestStatus",
    "CountBetweenRuleParametersSpec",
    "CredentialType",
    "CsvFileFormatSpec",
    "CustomCheckSpec",
    "CustomRuleParametersSpec",
    "CustomSensorParametersSpec",
    "DashboardsFolderSpec",
    "DashboardSpec",
    "DashboardSpecParameters",
    "DatabricksParametersSpec",
    "DatabricksParametersSpecProperties",
    "DataDeleteResultPartition",
    "DataDictionaryListModel",
    "DataDictionaryModel",
    "DataGroupingConfigurationListModel",
    "DataGroupingConfigurationModel",
    "DataGroupingConfigurationSpec",
    "DataGroupingConfigurationTrimmedModel",
    "DataGroupingDimensionSource",
    "DataGroupingDimensionSpec",
    "DataTypeCategory",
    "DatetimeBuiltInDateFormats",
    "DefaultColumnChecksPatternListModel",
    "DefaultColumnChecksPatternModel",
    "DefaultSchedulesSpec",
    "DefaultTableChecksPatternListModel",
    "DefaultTableChecksPatternModel",
    "DeleteStoredDataQueueJobParameters",
    "DeleteStoredDataQueueJobResult",
    "DeleteStoredDataResult",
    "DeleteStoredDataResultPartitionResults",
    "DetectedDatatypeCategory",
    "DetectedDatatypeEqualsRuleParametersSpec",
    "DimensionCurrentDataQualityStatusModel",
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
    "DuckdbFilesFormatType",
    "DuckdbParametersSpec",
    "DuckdbParametersSpecDirectories",
    "DuckdbParametersSpecProperties",
    "DuckdbReadMode",
    "DuckdbStorageType",
    "Duration",
    "EffectiveScheduleLevelModel",
    "EffectiveScheduleModel",
    "Equals1RuleParametersSpec",
    "EqualsIntegerRuleParametersSpec",
    "ErrorEntryModel",
    "ErrorSampleEntryModel",
    "ErrorSampleEntryModelResult",
    "ErrorSampleResultDataType",
    "ErrorSamplerResult",
    "ErrorSamplesDataScope",
    "ErrorSamplesListModel",
    "ErrorsListModel",
    "ExternalLogEntry",
    "FieldModel",
    "FileFormatSpec",
    "FileSynchronizationDirection",
    "FolderSynchronizationStatus",
    "HierarchyIdModel",
    "HierarchyIdModelPathItem",
    "HistoricDataPointsGrouping",
    "ImportSchemaQueueJobParameters",
    "ImportSeverityRuleParametersSpec",
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
    "JsonFileFormatSpec",
    "JsonFormatType",
    "JsonRecordsType",
    "LabelModel",
    "MaxCountRule0ErrorParametersSpec",
    "MaxCountRule0WarningParametersSpec",
    "MaxCountRule100ParametersSpec",
    "MaxDaysRule1ParametersSpec",
    "MaxDaysRule2ParametersSpec",
    "MaxDaysRule7ParametersSpec",
    "MaxDiffPercentRule0ParametersSpec",
    "MaxDiffPercentRule1ParametersSpec",
    "MaxDiffPercentRule5ParametersSpec",
    "MaxFailuresRule0ParametersSpec",
    "MaxFailuresRule1ParametersSpec",
    "MaxFailuresRule5ParametersSpec",
    "MaxMissingRule0ErrorParametersSpec",
    "MaxMissingRule0WarningParametersSpec",
    "MaxMissingRule2ParametersSpec",
    "MaxPercentRule0ErrorParametersSpec",
    "MaxPercentRule0WarningParametersSpec",
    "MaxPercentRule5ParametersSpec",
    "MinCountRule1ParametersSpec",
    "MinimumGroupingSeverityLevel",
    "MinPercentRule100ErrorParametersSpec",
    "MinPercentRule100WarningParametersSpec",
    "MinPercentRule95ParametersSpec",
    "MonitoringScheduleSpec",
    "Mono",
    "MonoResponseEntityMonoDqoQueueJobId",
    "MonoResponseEntityMonoObject",
    "MonoResponseEntityMonoVoid",
    "MysqlEngineType",
    "MysqlParametersSpec",
    "MysqlParametersSpecProperties",
    "MySqlSslMode",
    "NewLineCharacterType",
    "Optional",
    "OptionalIncidentWebhookNotificationsSpec",
    "OptionalMonitoringScheduleSpec",
    "OracleParametersSpec",
    "OracleParametersSpecProperties",
    "ParameterDataType",
    "ParameterDefinitionSpec",
    "ParquetFileFormatSpec",
    "PartitionIncrementalTimeWindowSpec",
    "PhysicalTableName",
    "PostgresqlParametersSpec",
    "PostgresqlParametersSpecProperties",
    "PostgresqlSslMode",
    "PrestoParametersSpec",
    "PrestoParametersSpecProperties",
    "ProfilingTimePeriodTruncation",
    "ProviderSensorDefinitionSpec",
    "ProviderSensorDefinitionSpecParameters",
    "ProviderSensorListModel",
    "ProviderSensorModel",
    "ProviderSensorRunnerType",
    "ProviderType",
    "QualityCategoryModel",
    "RedshiftAuthenticationMode",
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
    "SingleStoreDbLoadBalancingMode",
    "SingleStoreDbParametersSpec",
    "SnowflakeParametersSpec",
    "SnowflakeParametersSpecProperties",
    "SortDirection",
    "SparkParametersSpec",
    "SparkParametersSpecProperties",
    "SpringErrorPayload",
    "SqlServerAuthenticationMode",
    "SqlServerParametersSpec",
    "SqlServerParametersSpecProperties",
    "StatisticsCollectorSearchFilters",
    "StatisticsCollectorTarget",
    "StatisticsDataScope",
    "StatisticsMetricModel",
    "StatisticsMetricModelResult",
    "StatisticsResultDataType",
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
    "TableAvailabilityCheckSpec",
    "TableAvailabilityDailyMonitoringChecksSpec",
    "TableAvailabilityDailyMonitoringChecksSpecCustomChecks",
    "TableAvailabilityMonthlyMonitoringChecksSpec",
    "TableAvailabilityMonthlyMonitoringChecksSpecCustomChecks",
    "TableAvailabilityProfilingChecksSpec",
    "TableAvailabilityProfilingChecksSpecCustomChecks",
    "TableAvailabilitySensorParametersSpec",
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
    "TableCurrentDataQualityStatusModelDimensions",
    "TableCustomSqlDailyMonitoringChecksSpec",
    "TableCustomSqlDailyMonitoringChecksSpecCustomChecks",
    "TableCustomSqlDailyPartitionedChecksSpec",
    "TableCustomSqlDailyPartitionedChecksSpecCustomChecks",
    "TableCustomSqlMonthlyMonitoringChecksSpec",
    "TableCustomSqlMonthlyMonitoringChecksSpecCustomChecks",
    "TableCustomSqlMonthlyPartitionedChecksSpec",
    "TableCustomSqlMonthlyPartitionedChecksSpecCustomChecks",
    "TableCustomSqlProfilingChecksSpec",
    "TableCustomSqlProfilingChecksSpecCustomChecks",
    "TableDailyMonitoringCheckCategoriesSpec",
    "TableDailyMonitoringCheckCategoriesSpecComparisons",
    "TableDailyMonitoringCheckCategoriesSpecCustom",
    "TableDailyPartitionedCheckCategoriesSpec",
    "TableDailyPartitionedCheckCategoriesSpecComparisons",
    "TableDailyPartitionedCheckCategoriesSpecCustom",
    "TableDataFreshnessAnomalyCheckSpec",
    "TableDataFreshnessCheckSpec",
    "TableDataIngestionDelayCheckSpec",
    "TableDataStalenessCheckSpec",
    "TableDefaultChecksPatternSpec",
    "TableIncidentGroupingSpec",
    "TableListModel",
    "TableModel",
    "TableMonitoringCheckCategoriesSpec",
    "TableMonthlyMonitoringCheckCategoriesSpec",
    "TableMonthlyMonitoringCheckCategoriesSpecComparisons",
    "TableMonthlyMonitoringCheckCategoriesSpecCustom",
    "TableMonthlyPartitionedCheckCategoriesSpec",
    "TableMonthlyPartitionedCheckCategoriesSpecComparisons",
    "TableMonthlyPartitionedCheckCategoriesSpecCustom",
    "TableOwnerSpec",
    "TablePartitionedCheckCategoriesSpec",
    "TablePartitioningModel",
    "TablePartitionReloadLagCheckSpec",
    "TableProfilingCheckCategoriesSpec",
    "TableProfilingCheckCategoriesSpecComparisons",
    "TableProfilingCheckCategoriesSpecCustom",
    "TableRowCountAnomalyDifferencingCheckSpec",
    "TableRowCountAnomalyStationaryPartitionCheckSpec",
    "TableRowCountChange1DayCheckSpec",
    "TableRowCountChange30DaysCheckSpec",
    "TableRowCountChange7DaysCheckSpec",
    "TableRowCountChangeCheckSpec",
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
    "TableSqlAggregateExpressionCheckSpec",
    "TableSqlConditionFailedCheckSpec",
    "TableSqlConditionFailedCountSensorParametersSpec",
    "TableSqlConditionPassedPercentCheckSpec",
    "TableSqlConditionPassedPercentSensorParametersSpec",
    "TableSqlImportCustomResultCheckSpec",
    "TableSqlImportCustomResultSensorParametersSpec",
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
    "TargetColumnPatternSpec",
    "TargetTablePatternSpec",
    "TemporalUnit",
    "TextBuiltInDateFormats",
    "TimePeriodGradient",
    "TimestampColumnsSpec",
    "TimeWindowFilterParameters",
    "TopIncidentGrouping",
    "TopIncidentsModel",
    "TopIncidentsModelTopIncidents",
    "TrinoEngineType",
    "TrinoParametersSpec",
    "TrinoParametersSpecProperties",
    "ValueChangedRuleParametersSpec",
)
