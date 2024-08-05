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
        propose_default_checks (Union[Unset, bool]): Propose the rules for default checks that were activated using data
            quality check patterns (policies). The default value of this parameter is 'true'.
        propose_minimum_row_count (Union[Unset, bool]): Proposes the default configuration of the minimum row count for
            monitoring checks (full table scans). The default value of this parameter is 'true'.
        propose_column_count (Union[Unset, bool]): Proposes the default configuration of the column count check. The
            default value of this parameter is 'true'.
        propose_timeliness_checks (Union[Unset, bool]): Proposes the default configuration of the timeliness checks,
            including an accepted freshness delay. The default value of this parameter is 'true'.
        propose_nulls_checks (Union[Unset, bool]): Proposes the default configuration the null checks that verify that
            there are no null values. The default value of this parameter is 'true'.
        propose_not_nulls_checks (Union[Unset, bool]): Proposes the default configuration the not-null checks that
            validate scale of not-nulls (require a mix of some not-null and null values).The default value of this parameter
            is 'false'.
        propose_text_values_data_type (Union[Unset, bool]): Proposes the default configuration the detected data type of
            values in a text column check, when text columns contain an uniform type such as integers or dates. The default
            value of this parameter is 'true'.
        propose_column_exists (Union[Unset, bool]): Enables a rule on the column's schema check that verifies if the
            column exists. It is enabled on columns that were detected as existing during data profiling. The default value
            of this parameter is 'true'.
        propose_uniqueness_checks (Union[Unset, bool]): Proposes the default configuration the uniqueness checks that
            validate the number of distinct and duplicate values. The default value of this parameter is 'true'.
        propose_numeric_ranges (Union[Unset, bool]): Proposes the default configuration of numeric checks that validate
            the ranges of numeric values, and aggregated measures such as minimum, maximum, mean and sum of values. The
            default value of this parameter is 'true'.
        propose_text_length_ranges (Union[Unset, bool]): Proposes the default configuration of the text length checks.
            The default value of this parameter is 'true'.
        propose_accepted_values_checks (Union[Unset, bool]): Proposes the default configuration the accepted values
            checks. The default value of this parameter is 'true'.
        fail_checks_at_percent_error_rows (Union[Unset, float]): The percentage value captured by a profiling check (for
            example 0.03% of errors or 99.97% of valid) that is used to propose a percentage rule that will treat the values
            as errors (i.e., max_percent = 0%, or min_percent = 100%).
        max_percent_error_rows_for_percent_checks (Union[Unset, float]): The default maximum percentage of invalid rows
            for which the rule engine should configure rule values, especially min_percent, min_count or max_percent.
        propose_custom_checks (Union[Unset, bool]): Proposes the default configuration for custom checks that use built-
            in data quality rules. The default value of this parameter is 'true'.
    """

    severity_level: Union[Unset, TargetRuleSeverityLevel] = UNSET
    category_filter: Union[Unset, str] = UNSET
    check_name_filter: Union[Unset, str] = UNSET
    column_name_filter: Union[Unset, str] = UNSET
    copy_failed_profiling_checks: Union[Unset, bool] = UNSET
    copy_disabled_profiling_checks: Union[Unset, bool] = UNSET
    propose_default_checks: Union[Unset, bool] = UNSET
    propose_minimum_row_count: Union[Unset, bool] = UNSET
    propose_column_count: Union[Unset, bool] = UNSET
    propose_timeliness_checks: Union[Unset, bool] = UNSET
    propose_nulls_checks: Union[Unset, bool] = UNSET
    propose_not_nulls_checks: Union[Unset, bool] = UNSET
    propose_text_values_data_type: Union[Unset, bool] = UNSET
    propose_column_exists: Union[Unset, bool] = UNSET
    propose_uniqueness_checks: Union[Unset, bool] = UNSET
    propose_numeric_ranges: Union[Unset, bool] = UNSET
    propose_text_length_ranges: Union[Unset, bool] = UNSET
    propose_accepted_values_checks: Union[Unset, bool] = UNSET
    fail_checks_at_percent_error_rows: Union[Unset, float] = UNSET
    max_percent_error_rows_for_percent_checks: Union[Unset, float] = UNSET
    propose_custom_checks: Union[Unset, bool] = UNSET
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
        propose_default_checks = self.propose_default_checks
        propose_minimum_row_count = self.propose_minimum_row_count
        propose_column_count = self.propose_column_count
        propose_timeliness_checks = self.propose_timeliness_checks
        propose_nulls_checks = self.propose_nulls_checks
        propose_not_nulls_checks = self.propose_not_nulls_checks
        propose_text_values_data_type = self.propose_text_values_data_type
        propose_column_exists = self.propose_column_exists
        propose_uniqueness_checks = self.propose_uniqueness_checks
        propose_numeric_ranges = self.propose_numeric_ranges
        propose_text_length_ranges = self.propose_text_length_ranges
        propose_accepted_values_checks = self.propose_accepted_values_checks
        fail_checks_at_percent_error_rows = self.fail_checks_at_percent_error_rows
        max_percent_error_rows_for_percent_checks = (
            self.max_percent_error_rows_for_percent_checks
        )
        propose_custom_checks = self.propose_custom_checks

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
        if propose_default_checks is not UNSET:
            field_dict["propose_default_checks"] = propose_default_checks
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
        if propose_text_length_ranges is not UNSET:
            field_dict["propose_text_length_ranges"] = propose_text_length_ranges
        if propose_accepted_values_checks is not UNSET:
            field_dict["propose_accepted_values_checks"] = (
                propose_accepted_values_checks
            )
        if fail_checks_at_percent_error_rows is not UNSET:
            field_dict["fail_checks_at_percent_error_rows"] = (
                fail_checks_at_percent_error_rows
            )
        if max_percent_error_rows_for_percent_checks is not UNSET:
            field_dict["max_percent_error_rows_for_percent_checks"] = (
                max_percent_error_rows_for_percent_checks
            )
        if propose_custom_checks is not UNSET:
            field_dict["propose_custom_checks"] = propose_custom_checks

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

        propose_default_checks = d.pop("propose_default_checks", UNSET)

        propose_minimum_row_count = d.pop("propose_minimum_row_count", UNSET)

        propose_column_count = d.pop("propose_column_count", UNSET)

        propose_timeliness_checks = d.pop("propose_timeliness_checks", UNSET)

        propose_nulls_checks = d.pop("propose_nulls_checks", UNSET)

        propose_not_nulls_checks = d.pop("propose_not_nulls_checks", UNSET)

        propose_text_values_data_type = d.pop("propose_text_values_data_type", UNSET)

        propose_column_exists = d.pop("propose_column_exists", UNSET)

        propose_uniqueness_checks = d.pop("propose_uniqueness_checks", UNSET)

        propose_numeric_ranges = d.pop("propose_numeric_ranges", UNSET)

        propose_text_length_ranges = d.pop("propose_text_length_ranges", UNSET)

        propose_accepted_values_checks = d.pop("propose_accepted_values_checks", UNSET)

        fail_checks_at_percent_error_rows = d.pop(
            "fail_checks_at_percent_error_rows", UNSET
        )

        max_percent_error_rows_for_percent_checks = d.pop(
            "max_percent_error_rows_for_percent_checks", UNSET
        )

        propose_custom_checks = d.pop("propose_custom_checks", UNSET)

        check_mining_parameters_model = cls(
            severity_level=severity_level,
            category_filter=category_filter,
            check_name_filter=check_name_filter,
            column_name_filter=column_name_filter,
            copy_failed_profiling_checks=copy_failed_profiling_checks,
            copy_disabled_profiling_checks=copy_disabled_profiling_checks,
            propose_default_checks=propose_default_checks,
            propose_minimum_row_count=propose_minimum_row_count,
            propose_column_count=propose_column_count,
            propose_timeliness_checks=propose_timeliness_checks,
            propose_nulls_checks=propose_nulls_checks,
            propose_not_nulls_checks=propose_not_nulls_checks,
            propose_text_values_data_type=propose_text_values_data_type,
            propose_column_exists=propose_column_exists,
            propose_uniqueness_checks=propose_uniqueness_checks,
            propose_numeric_ranges=propose_numeric_ranges,
            propose_text_length_ranges=propose_text_length_ranges,
            propose_accepted_values_checks=propose_accepted_values_checks,
            fail_checks_at_percent_error_rows=fail_checks_at_percent_error_rows,
            max_percent_error_rows_for_percent_checks=max_percent_error_rows_for_percent_checks,
            propose_custom_checks=propose_custom_checks,
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
