from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.target_rule_severity_level import TargetRuleSeverityLevel
from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckMiningParametersModel")


@_attrs_define
class CheckMiningParametersModel:
    """Data quality check rule mining parameters. Configure what type of checks should be configured.

    Attributes:
        severity_level (Union[Unset, TargetRuleSeverityLevel]):
        category_filter (Union[Unset, str]): Optional filter for the check category names, supports filtering with
            prefixes and suffixes defined as a '*' character.
        check_name_filter (Union[Unset, str]): Optional filter for the check names, supports filtering with prefixes and
            suffixes defined as a '*' character.
        column_name_filter (Union[Unset, str]): Optional filter for the column names, supports filtering with prefixes
            and suffixes defined as a '*' character.
        copy_failed_profiling_checks (Union[Unset, bool]): Copy also the configuration of profiling checks that failed.
        copy_disabled_profiling_checks (Union[Unset, bool]): Copy also the configuration of profiling checks that are
            disabled.
        copy_profiling_checks (Union[Unset, bool]): Copy the configuration of valid profiling checks.
        reconfigure_policy_enabled_checks (Union[Unset, bool]): Propose the rules for default checks that were activated
            using data quality check patterns (policies). The default value of this parameter is 'true'.
        propose_checks_from_statistics (Union[Unset, bool]): Propose the configuration of data quality checks from
            statistics.
        propose_minimum_row_count (Union[Unset, bool]): Propose the default configuration of the minimum row count for
            monitoring checks (full table scans). The default value of this parameter is 'true'.
        propose_column_count (Union[Unset, bool]): Propose the default configuration of the column count check. The
            default value of this parameter is 'true'.
        propose_timeliness_checks (Union[Unset, bool]): Propose the default configuration of the timeliness checks,
            including an accepted freshness delay. The default value of this parameter is 'true'.
        propose_nulls_checks (Union[Unset, bool]): Propose the default configuration the null checks that verify that
            there are no null values. The default value of this parameter is 'true'.
        propose_not_nulls_checks (Union[Unset, bool]): Propose the default configuration the not-null checks that
            validate scale of not-nulls (require a mix of some not-null and null values).The default value of this parameter
            is 'false'.
        propose_text_values_data_type (Union[Unset, bool]): Propose the default configuration the detected data type of
            values in a text column check, when text columns contain an uniform type such as integers or dates. The default
            value of this parameter is 'true'.
        propose_column_exists (Union[Unset, bool]): Enables a rule on the column's schema check that verifies if the
            column exists. It is enabled on columns that were detected as existing during data profiling. The default value
            of this parameter is 'true'.
        propose_uniqueness_checks (Union[Unset, bool]): Propose the default configuration the uniqueness checks that
            validate the number of distinct and duplicate values. The default value of this parameter is 'true'.
        propose_numeric_ranges (Union[Unset, bool]): Propose the default configuration of numeric checks that validate
            the ranges of numeric values, and aggregated measures such as minimum, maximum, mean and sum of values. The
            default value of this parameter is 'true'.
        propose_percentile_ranges (Union[Unset, bool]): Propose the default configuration of the median and percentile
            in range checks that validate the value at a given percentile, such as a value in middle of all column values.
            The default value of this parameter is 'false' because calculating the median requires running a separate query
            on the data source, which is not advisable for data observability.
        propose_text_length_ranges (Union[Unset, bool]): Propose the default configuration of the text length checks.
            The default value of this parameter is 'true'.
        propose_word_count_ranges (Union[Unset, bool]): Propose the default configuration of the minimum and maximum
            word count of text columns. The default value of this parameter is 'true'.
        propose_values_in_set_checks (Union[Unset, bool]): Propose the configuration the categorical values checks from
            the accepted values category. These checks will be configured to ensure that the column contains only sample
            values that were identified during data profiling. The default value of this parameter is 'true'.
        values_in_set_treat_rare_values_as_invalid (Union[Unset, bool]): Configure the values in set checks from the
            accepted values category to raise data quality issues for rare values. DQOps will not add rare categorical
            values to the list of expected values. The default value of this parameter is 'true'.
        propose_top_values_checks (Union[Unset, bool]): Propose the configuration the text values in the top values
            checks from the accepted values category. These checks find the most common values in a table and ensure that
            they are the same values that were identified during data profiling. The default value of this parameter is
            'true'.
        propose_text_conversion_checks (Union[Unset, bool]): Propose the configuration the data type conversion checks
            that verify if text values can be converted to more specific data types such as boolean, date, float or integer.
            This type of checks are used to verify raw tables in the landing zones. The default value of this parameter is
            'true'.
        propose_bool_percent_checks (Union[Unset, bool]): Propose the default configuration for the checks that measure
            the percentage of boolean values. The default value of this parameter is 'true'.
        propose_date_checks (Union[Unset, bool]): Propose the default configuration for the date and datetime checks
            that detect invalid dates. The default value of this parameter is 'true'.
        propose_standard_pattern_checks (Union[Unset, bool]): Propose the default configuration for the patterns check
            that validate the format of popular text patterns, such as UUIDs, phone numbers, or emails. DQOps will configure
            these data quality checks when analyzed columns contain enough values matching a standard pattern. The default
            value of this parameter is 'true'.
        detect_regular_expressions (Union[Unset, bool]): Analyze sample text values and try to find a regular expression
            that detects valid values similar to the sample values. The default value of this parameter is 'true'.
        propose_whitespace_checks (Union[Unset, bool]): Propose the default configuration for the whitespace detection
            checks. Whitespace checks detect common data quality issues with storing text representations of null values,
            such as 'null', 'None', 'n/a' and other texts that should be stored as regular NULL values. The default value of
            this parameter is 'true'.
        apply_pii_checks (Union[Unset, bool]): Applies rules to Personal Identifiable Information checks (sensitive
            data), but only when the checks were activated manually as profiling checks, or through a data quality check
            pattern that scans all columns for PII data. The default value of this parameter is 'true'.
        propose_custom_checks (Union[Unset, bool]): Propose the default configuration for custom checks that use built-
            in data quality rules. The default value of this parameter is 'true'.
        fail_checks_at_percent_error_rows (Union[Unset, float]): The percentage value captured by a profiling check (for
            example 0.03% of errors or 99.97% of valid) that is used to propose a percentage rule that will treat the values
            as errors (i.e., max_percent = 0%, or min_percent = 100%).
        max_percent_error_rows_for_percent_checks (Union[Unset, float]): The default maximum percentage of invalid rows
            for which the rule engine should configure rule values, especially min_percent, min_count or max_percent.
    """

    severity_level: Union[Unset, TargetRuleSeverityLevel] = UNSET
    category_filter: Union[Unset, str] = UNSET
    check_name_filter: Union[Unset, str] = UNSET
    column_name_filter: Union[Unset, str] = UNSET
    copy_failed_profiling_checks: Union[Unset, bool] = UNSET
    copy_disabled_profiling_checks: Union[Unset, bool] = UNSET
    copy_profiling_checks: Union[Unset, bool] = UNSET
    reconfigure_policy_enabled_checks: Union[Unset, bool] = UNSET
    propose_checks_from_statistics: Union[Unset, bool] = UNSET
    propose_minimum_row_count: Union[Unset, bool] = UNSET
    propose_column_count: Union[Unset, bool] = UNSET
    propose_timeliness_checks: Union[Unset, bool] = UNSET
    propose_nulls_checks: Union[Unset, bool] = UNSET
    propose_not_nulls_checks: Union[Unset, bool] = UNSET
    propose_text_values_data_type: Union[Unset, bool] = UNSET
    propose_column_exists: Union[Unset, bool] = UNSET
    propose_uniqueness_checks: Union[Unset, bool] = UNSET
    propose_numeric_ranges: Union[Unset, bool] = UNSET
    propose_percentile_ranges: Union[Unset, bool] = UNSET
    propose_text_length_ranges: Union[Unset, bool] = UNSET
    propose_word_count_ranges: Union[Unset, bool] = UNSET
    propose_values_in_set_checks: Union[Unset, bool] = UNSET
    values_in_set_treat_rare_values_as_invalid: Union[Unset, bool] = UNSET
    propose_top_values_checks: Union[Unset, bool] = UNSET
    propose_text_conversion_checks: Union[Unset, bool] = UNSET
    propose_bool_percent_checks: Union[Unset, bool] = UNSET
    propose_date_checks: Union[Unset, bool] = UNSET
    propose_standard_pattern_checks: Union[Unset, bool] = UNSET
    detect_regular_expressions: Union[Unset, bool] = UNSET
    propose_whitespace_checks: Union[Unset, bool] = UNSET
    apply_pii_checks: Union[Unset, bool] = UNSET
    propose_custom_checks: Union[Unset, bool] = UNSET
    fail_checks_at_percent_error_rows: Union[Unset, float] = UNSET
    max_percent_error_rows_for_percent_checks: Union[Unset, float] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        severity_level: Union[Unset, str] = UNSET
        if not isinstance(self.severity_level, Unset):
            severity_level = self.severity_level.value

        category_filter = self.category_filter
        check_name_filter = self.check_name_filter
        column_name_filter = self.column_name_filter
        copy_failed_profiling_checks = self.copy_failed_profiling_checks
        copy_disabled_profiling_checks = self.copy_disabled_profiling_checks
        copy_profiling_checks = self.copy_profiling_checks
        reconfigure_policy_enabled_checks = self.reconfigure_policy_enabled_checks
        propose_checks_from_statistics = self.propose_checks_from_statistics
        propose_minimum_row_count = self.propose_minimum_row_count
        propose_column_count = self.propose_column_count
        propose_timeliness_checks = self.propose_timeliness_checks
        propose_nulls_checks = self.propose_nulls_checks
        propose_not_nulls_checks = self.propose_not_nulls_checks
        propose_text_values_data_type = self.propose_text_values_data_type
        propose_column_exists = self.propose_column_exists
        propose_uniqueness_checks = self.propose_uniqueness_checks
        propose_numeric_ranges = self.propose_numeric_ranges
        propose_percentile_ranges = self.propose_percentile_ranges
        propose_text_length_ranges = self.propose_text_length_ranges
        propose_word_count_ranges = self.propose_word_count_ranges
        propose_values_in_set_checks = self.propose_values_in_set_checks
        values_in_set_treat_rare_values_as_invalid = (
            self.values_in_set_treat_rare_values_as_invalid
        )
        propose_top_values_checks = self.propose_top_values_checks
        propose_text_conversion_checks = self.propose_text_conversion_checks
        propose_bool_percent_checks = self.propose_bool_percent_checks
        propose_date_checks = self.propose_date_checks
        propose_standard_pattern_checks = self.propose_standard_pattern_checks
        detect_regular_expressions = self.detect_regular_expressions
        propose_whitespace_checks = self.propose_whitespace_checks
        apply_pii_checks = self.apply_pii_checks
        propose_custom_checks = self.propose_custom_checks
        fail_checks_at_percent_error_rows = self.fail_checks_at_percent_error_rows
        max_percent_error_rows_for_percent_checks = (
            self.max_percent_error_rows_for_percent_checks
        )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if severity_level is not UNSET:
            field_dict["severity_level"] = severity_level
        if category_filter is not UNSET:
            field_dict["category_filter"] = category_filter
        if check_name_filter is not UNSET:
            field_dict["check_name_filter"] = check_name_filter
        if column_name_filter is not UNSET:
            field_dict["column_name_filter"] = column_name_filter
        if copy_failed_profiling_checks is not UNSET:
            field_dict["copy_failed_profiling_checks"] = copy_failed_profiling_checks
        if copy_disabled_profiling_checks is not UNSET:
            field_dict["copy_disabled_profiling_checks"] = (
                copy_disabled_profiling_checks
            )
        if copy_profiling_checks is not UNSET:
            field_dict["copy_profiling_checks"] = copy_profiling_checks
        if reconfigure_policy_enabled_checks is not UNSET:
            field_dict["reconfigure_policy_enabled_checks"] = (
                reconfigure_policy_enabled_checks
            )
        if propose_checks_from_statistics is not UNSET:
            field_dict["propose_checks_from_statistics"] = (
                propose_checks_from_statistics
            )
        if propose_minimum_row_count is not UNSET:
            field_dict["propose_minimum_row_count"] = propose_minimum_row_count
        if propose_column_count is not UNSET:
            field_dict["propose_column_count"] = propose_column_count
        if propose_timeliness_checks is not UNSET:
            field_dict["propose_timeliness_checks"] = propose_timeliness_checks
        if propose_nulls_checks is not UNSET:
            field_dict["propose_nulls_checks"] = propose_nulls_checks
        if propose_not_nulls_checks is not UNSET:
            field_dict["propose_not_nulls_checks"] = propose_not_nulls_checks
        if propose_text_values_data_type is not UNSET:
            field_dict["propose_text_values_data_type"] = propose_text_values_data_type
        if propose_column_exists is not UNSET:
            field_dict["propose_column_exists"] = propose_column_exists
        if propose_uniqueness_checks is not UNSET:
            field_dict["propose_uniqueness_checks"] = propose_uniqueness_checks
        if propose_numeric_ranges is not UNSET:
            field_dict["propose_numeric_ranges"] = propose_numeric_ranges
        if propose_percentile_ranges is not UNSET:
            field_dict["propose_percentile_ranges"] = propose_percentile_ranges
        if propose_text_length_ranges is not UNSET:
            field_dict["propose_text_length_ranges"] = propose_text_length_ranges
        if propose_word_count_ranges is not UNSET:
            field_dict["propose_word_count_ranges"] = propose_word_count_ranges
        if propose_values_in_set_checks is not UNSET:
            field_dict["propose_values_in_set_checks"] = propose_values_in_set_checks
        if values_in_set_treat_rare_values_as_invalid is not UNSET:
            field_dict["values_in_set_treat_rare_values_as_invalid"] = (
                values_in_set_treat_rare_values_as_invalid
            )
        if propose_top_values_checks is not UNSET:
            field_dict["propose_top_values_checks"] = propose_top_values_checks
        if propose_text_conversion_checks is not UNSET:
            field_dict["propose_text_conversion_checks"] = (
                propose_text_conversion_checks
            )
        if propose_bool_percent_checks is not UNSET:
            field_dict["propose_bool_percent_checks"] = propose_bool_percent_checks
        if propose_date_checks is not UNSET:
            field_dict["propose_date_checks"] = propose_date_checks
        if propose_standard_pattern_checks is not UNSET:
            field_dict["propose_standard_pattern_checks"] = (
                propose_standard_pattern_checks
            )
        if detect_regular_expressions is not UNSET:
            field_dict["detect_regular_expressions"] = detect_regular_expressions
        if propose_whitespace_checks is not UNSET:
            field_dict["propose_whitespace_checks"] = propose_whitespace_checks
        if apply_pii_checks is not UNSET:
            field_dict["apply_pii_checks"] = apply_pii_checks
        if propose_custom_checks is not UNSET:
            field_dict["propose_custom_checks"] = propose_custom_checks
        if fail_checks_at_percent_error_rows is not UNSET:
            field_dict["fail_checks_at_percent_error_rows"] = (
                fail_checks_at_percent_error_rows
            )
        if max_percent_error_rows_for_percent_checks is not UNSET:
            field_dict["max_percent_error_rows_for_percent_checks"] = (
                max_percent_error_rows_for_percent_checks
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _severity_level = d.pop("severity_level", UNSET)
        severity_level: Union[Unset, TargetRuleSeverityLevel]
        if isinstance(_severity_level, Unset):
            severity_level = UNSET
        else:
            severity_level = TargetRuleSeverityLevel(_severity_level)

        category_filter = d.pop("category_filter", UNSET)

        check_name_filter = d.pop("check_name_filter", UNSET)

        column_name_filter = d.pop("column_name_filter", UNSET)

        copy_failed_profiling_checks = d.pop("copy_failed_profiling_checks", UNSET)

        copy_disabled_profiling_checks = d.pop("copy_disabled_profiling_checks", UNSET)

        copy_profiling_checks = d.pop("copy_profiling_checks", UNSET)

        reconfigure_policy_enabled_checks = d.pop(
            "reconfigure_policy_enabled_checks", UNSET
        )

        propose_checks_from_statistics = d.pop("propose_checks_from_statistics", UNSET)

        propose_minimum_row_count = d.pop("propose_minimum_row_count", UNSET)

        propose_column_count = d.pop("propose_column_count", UNSET)

        propose_timeliness_checks = d.pop("propose_timeliness_checks", UNSET)

        propose_nulls_checks = d.pop("propose_nulls_checks", UNSET)

        propose_not_nulls_checks = d.pop("propose_not_nulls_checks", UNSET)

        propose_text_values_data_type = d.pop("propose_text_values_data_type", UNSET)

        propose_column_exists = d.pop("propose_column_exists", UNSET)

        propose_uniqueness_checks = d.pop("propose_uniqueness_checks", UNSET)

        propose_numeric_ranges = d.pop("propose_numeric_ranges", UNSET)

        propose_percentile_ranges = d.pop("propose_percentile_ranges", UNSET)

        propose_text_length_ranges = d.pop("propose_text_length_ranges", UNSET)

        propose_word_count_ranges = d.pop("propose_word_count_ranges", UNSET)

        propose_values_in_set_checks = d.pop("propose_values_in_set_checks", UNSET)

        values_in_set_treat_rare_values_as_invalid = d.pop(
            "values_in_set_treat_rare_values_as_invalid", UNSET
        )

        propose_top_values_checks = d.pop("propose_top_values_checks", UNSET)

        propose_text_conversion_checks = d.pop("propose_text_conversion_checks", UNSET)

        propose_bool_percent_checks = d.pop("propose_bool_percent_checks", UNSET)

        propose_date_checks = d.pop("propose_date_checks", UNSET)

        propose_standard_pattern_checks = d.pop(
            "propose_standard_pattern_checks", UNSET
        )

        detect_regular_expressions = d.pop("detect_regular_expressions", UNSET)

        propose_whitespace_checks = d.pop("propose_whitespace_checks", UNSET)

        apply_pii_checks = d.pop("apply_pii_checks", UNSET)

        propose_custom_checks = d.pop("propose_custom_checks", UNSET)

        fail_checks_at_percent_error_rows = d.pop(
            "fail_checks_at_percent_error_rows", UNSET
        )

        max_percent_error_rows_for_percent_checks = d.pop(
            "max_percent_error_rows_for_percent_checks", UNSET
        )

        check_mining_parameters_model = cls(
            severity_level=severity_level,
            category_filter=category_filter,
            check_name_filter=check_name_filter,
            column_name_filter=column_name_filter,
            copy_failed_profiling_checks=copy_failed_profiling_checks,
            copy_disabled_profiling_checks=copy_disabled_profiling_checks,
            copy_profiling_checks=copy_profiling_checks,
            reconfigure_policy_enabled_checks=reconfigure_policy_enabled_checks,
            propose_checks_from_statistics=propose_checks_from_statistics,
            propose_minimum_row_count=propose_minimum_row_count,
            propose_column_count=propose_column_count,
            propose_timeliness_checks=propose_timeliness_checks,
            propose_nulls_checks=propose_nulls_checks,
            propose_not_nulls_checks=propose_not_nulls_checks,
            propose_text_values_data_type=propose_text_values_data_type,
            propose_column_exists=propose_column_exists,
            propose_uniqueness_checks=propose_uniqueness_checks,
            propose_numeric_ranges=propose_numeric_ranges,
            propose_percentile_ranges=propose_percentile_ranges,
            propose_text_length_ranges=propose_text_length_ranges,
            propose_word_count_ranges=propose_word_count_ranges,
            propose_values_in_set_checks=propose_values_in_set_checks,
            values_in_set_treat_rare_values_as_invalid=values_in_set_treat_rare_values_as_invalid,
            propose_top_values_checks=propose_top_values_checks,
            propose_text_conversion_checks=propose_text_conversion_checks,
            propose_bool_percent_checks=propose_bool_percent_checks,
            propose_date_checks=propose_date_checks,
            propose_standard_pattern_checks=propose_standard_pattern_checks,
            detect_regular_expressions=detect_regular_expressions,
            propose_whitespace_checks=propose_whitespace_checks,
            apply_pii_checks=apply_pii_checks,
            propose_custom_checks=propose_custom_checks,
            fail_checks_at_percent_error_rows=fail_checks_at_percent_error_rows,
            max_percent_error_rows_for_percent_checks=max_percent_error_rows_for_percent_checks,
        )

        check_mining_parameters_model.additional_properties = d
        return check_mining_parameters_model

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> Any:
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: Any) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
