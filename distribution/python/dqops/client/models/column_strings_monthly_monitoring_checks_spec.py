from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_expected_strings_in_top_values_count_check_spec import (
        ColumnExpectedStringsInTopValuesCountCheckSpec,
    )
    from ..models.column_expected_strings_in_use_count_check_spec import (
        ColumnExpectedStringsInUseCountCheckSpec,
    )
    from ..models.column_string_boolean_placeholder_percent_check_spec import (
        ColumnStringBooleanPlaceholderPercentCheckSpec,
    )
    from ..models.column_string_empty_count_check_spec import (
        ColumnStringEmptyCountCheckSpec,
    )
    from ..models.column_string_empty_percent_check_spec import (
        ColumnStringEmptyPercentCheckSpec,
    )
    from ..models.column_string_invalid_email_count_check_spec import (
        ColumnStringInvalidEmailCountCheckSpec,
    )
    from ..models.column_string_invalid_ip_4_address_count_check_spec import (
        ColumnStringInvalidIp4AddressCountCheckSpec,
    )
    from ..models.column_string_invalid_ip_6_address_count_check_spec import (
        ColumnStringInvalidIp6AddressCountCheckSpec,
    )
    from ..models.column_string_invalid_uuid_count_check_spec import (
        ColumnStringInvalidUuidCountCheckSpec,
    )
    from ..models.column_string_length_above_max_length_count_check_spec import (
        ColumnStringLengthAboveMaxLengthCountCheckSpec,
    )
    from ..models.column_string_length_above_max_length_percent_check_spec import (
        ColumnStringLengthAboveMaxLengthPercentCheckSpec,
    )
    from ..models.column_string_length_below_min_length_count_check_spec import (
        ColumnStringLengthBelowMinLengthCountCheckSpec,
    )
    from ..models.column_string_length_below_min_length_percent_check_spec import (
        ColumnStringLengthBelowMinLengthPercentCheckSpec,
    )
    from ..models.column_string_length_in_range_percent_check_spec import (
        ColumnStringLengthInRangePercentCheckSpec,
    )
    from ..models.column_string_match_date_regex_percent_check_spec import (
        ColumnStringMatchDateRegexPercentCheckSpec,
    )
    from ..models.column_string_match_name_regex_percent_check_spec import (
        ColumnStringMatchNameRegexPercentCheckSpec,
    )
    from ..models.column_string_match_regex_percent_check_spec import (
        ColumnStringMatchRegexPercentCheckSpec,
    )
    from ..models.column_string_max_length_check_spec import (
        ColumnStringMaxLengthCheckSpec,
    )
    from ..models.column_string_mean_length_check_spec import (
        ColumnStringMeanLengthCheckSpec,
    )
    from ..models.column_string_min_length_check_spec import (
        ColumnStringMinLengthCheckSpec,
    )
    from ..models.column_string_not_match_date_regex_count_check_spec import (
        ColumnStringNotMatchDateRegexCountCheckSpec,
    )
    from ..models.column_string_not_match_regex_count_check_spec import (
        ColumnStringNotMatchRegexCountCheckSpec,
    )
    from ..models.column_string_null_placeholder_count_check_spec import (
        ColumnStringNullPlaceholderCountCheckSpec,
    )
    from ..models.column_string_null_placeholder_percent_check_spec import (
        ColumnStringNullPlaceholderPercentCheckSpec,
    )
    from ..models.column_string_parsable_to_float_percent_check_spec import (
        ColumnStringParsableToFloatPercentCheckSpec,
    )
    from ..models.column_string_parsable_to_integer_percent_check_spec import (
        ColumnStringParsableToIntegerPercentCheckSpec,
    )
    from ..models.column_string_surrounded_by_whitespace_count_check_spec import (
        ColumnStringSurroundedByWhitespaceCountCheckSpec,
    )
    from ..models.column_string_surrounded_by_whitespace_percent_check_spec import (
        ColumnStringSurroundedByWhitespacePercentCheckSpec,
    )
    from ..models.column_string_valid_country_code_percent_check_spec import (
        ColumnStringValidCountryCodePercentCheckSpec,
    )
    from ..models.column_string_valid_currency_code_percent_check_spec import (
        ColumnStringValidCurrencyCodePercentCheckSpec,
    )
    from ..models.column_string_valid_dates_percent_check_spec import (
        ColumnStringValidDatesPercentCheckSpec,
    )
    from ..models.column_string_valid_uuid_percent_check_spec import (
        ColumnStringValidUuidPercentCheckSpec,
    )
    from ..models.column_string_value_in_set_percent_check_spec import (
        ColumnStringValueInSetPercentCheckSpec,
    )
    from ..models.column_string_whitespace_count_check_spec import (
        ColumnStringWhitespaceCountCheckSpec,
    )
    from ..models.column_string_whitespace_percent_check_spec import (
        ColumnStringWhitespacePercentCheckSpec,
    )
    from ..models.column_strings_monthly_monitoring_checks_spec_custom_checks import (
        ColumnStringsMonthlyMonitoringChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnStringsMonthlyMonitoringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnStringsMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnStringsMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_string_max_length (Union[Unset, ColumnStringMaxLengthCheckSpec]):
        monthly_string_min_length (Union[Unset, ColumnStringMinLengthCheckSpec]):
        monthly_string_mean_length (Union[Unset, ColumnStringMeanLengthCheckSpec]):
        monthly_string_length_below_min_length_count (Union[Unset, ColumnStringLengthBelowMinLengthCountCheckSpec]):
        monthly_string_length_below_min_length_percent (Union[Unset, ColumnStringLengthBelowMinLengthPercentCheckSpec]):
        monthly_string_length_above_max_length_count (Union[Unset, ColumnStringLengthAboveMaxLengthCountCheckSpec]):
        monthly_string_length_above_max_length_percent (Union[Unset, ColumnStringLengthAboveMaxLengthPercentCheckSpec]):
        monthly_string_length_in_range_percent (Union[Unset, ColumnStringLengthInRangePercentCheckSpec]):
        monthly_string_empty_count (Union[Unset, ColumnStringEmptyCountCheckSpec]):
        monthly_string_empty_percent (Union[Unset, ColumnStringEmptyPercentCheckSpec]):
        monthly_string_valid_dates_percent (Union[Unset, ColumnStringValidDatesPercentCheckSpec]):
        monthly_string_whitespace_count (Union[Unset, ColumnStringWhitespaceCountCheckSpec]):
        monthly_string_whitespace_percent (Union[Unset, ColumnStringWhitespacePercentCheckSpec]):
        monthly_string_surrounded_by_whitespace_count (Union[Unset, ColumnStringSurroundedByWhitespaceCountCheckSpec]):
        monthly_string_surrounded_by_whitespace_percent (Union[Unset,
            ColumnStringSurroundedByWhitespacePercentCheckSpec]):
        monthly_string_null_placeholder_count (Union[Unset, ColumnStringNullPlaceholderCountCheckSpec]):
        monthly_string_null_placeholder_percent (Union[Unset, ColumnStringNullPlaceholderPercentCheckSpec]):
        monthly_string_boolean_placeholder_percent (Union[Unset, ColumnStringBooleanPlaceholderPercentCheckSpec]):
        monthly_string_parsable_to_integer_percent (Union[Unset, ColumnStringParsableToIntegerPercentCheckSpec]):
        monthly_string_parsable_to_float_percent (Union[Unset, ColumnStringParsableToFloatPercentCheckSpec]):
        monthly_expected_strings_in_use_count (Union[Unset, ColumnExpectedStringsInUseCountCheckSpec]):
        monthly_string_value_in_set_percent (Union[Unset, ColumnStringValueInSetPercentCheckSpec]):
        monthly_string_valid_country_code_percent (Union[Unset, ColumnStringValidCountryCodePercentCheckSpec]):
        monthly_string_valid_currency_code_percent (Union[Unset, ColumnStringValidCurrencyCodePercentCheckSpec]):
        monthly_string_invalid_email_count (Union[Unset, ColumnStringInvalidEmailCountCheckSpec]):
        monthly_string_invalid_uuid_count (Union[Unset, ColumnStringInvalidUuidCountCheckSpec]):
        monthly_string_valid_uuid_percent (Union[Unset, ColumnStringValidUuidPercentCheckSpec]):
        monthly_string_invalid_ip4_address_count (Union[Unset, ColumnStringInvalidIp4AddressCountCheckSpec]):
        monthly_string_invalid_ip6_address_count (Union[Unset, ColumnStringInvalidIp6AddressCountCheckSpec]):
        monthly_string_not_match_regex_count (Union[Unset, ColumnStringNotMatchRegexCountCheckSpec]):
        monthly_string_match_regex_percent (Union[Unset, ColumnStringMatchRegexPercentCheckSpec]):
        monthly_string_not_match_date_regex_count (Union[Unset, ColumnStringNotMatchDateRegexCountCheckSpec]):
        monthly_string_match_date_regex_percent (Union[Unset, ColumnStringMatchDateRegexPercentCheckSpec]):
        monthly_string_match_name_regex_percent (Union[Unset, ColumnStringMatchNameRegexPercentCheckSpec]):
        monthly_expected_strings_in_top_values_count (Union[Unset, ColumnExpectedStringsInTopValuesCountCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnStringsMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    monthly_string_max_length: Union[Unset, "ColumnStringMaxLengthCheckSpec"] = UNSET
    monthly_string_min_length: Union[Unset, "ColumnStringMinLengthCheckSpec"] = UNSET
    monthly_string_mean_length: Union[Unset, "ColumnStringMeanLengthCheckSpec"] = UNSET
    monthly_string_length_below_min_length_count: Union[
        Unset, "ColumnStringLengthBelowMinLengthCountCheckSpec"
    ] = UNSET
    monthly_string_length_below_min_length_percent: Union[
        Unset, "ColumnStringLengthBelowMinLengthPercentCheckSpec"
    ] = UNSET
    monthly_string_length_above_max_length_count: Union[
        Unset, "ColumnStringLengthAboveMaxLengthCountCheckSpec"
    ] = UNSET
    monthly_string_length_above_max_length_percent: Union[
        Unset, "ColumnStringLengthAboveMaxLengthPercentCheckSpec"
    ] = UNSET
    monthly_string_length_in_range_percent: Union[
        Unset, "ColumnStringLengthInRangePercentCheckSpec"
    ] = UNSET
    monthly_string_empty_count: Union[Unset, "ColumnStringEmptyCountCheckSpec"] = UNSET
    monthly_string_empty_percent: Union[
        Unset, "ColumnStringEmptyPercentCheckSpec"
    ] = UNSET
    monthly_string_valid_dates_percent: Union[
        Unset, "ColumnStringValidDatesPercentCheckSpec"
    ] = UNSET
    monthly_string_whitespace_count: Union[
        Unset, "ColumnStringWhitespaceCountCheckSpec"
    ] = UNSET
    monthly_string_whitespace_percent: Union[
        Unset, "ColumnStringWhitespacePercentCheckSpec"
    ] = UNSET
    monthly_string_surrounded_by_whitespace_count: Union[
        Unset, "ColumnStringSurroundedByWhitespaceCountCheckSpec"
    ] = UNSET
    monthly_string_surrounded_by_whitespace_percent: Union[
        Unset, "ColumnStringSurroundedByWhitespacePercentCheckSpec"
    ] = UNSET
    monthly_string_null_placeholder_count: Union[
        Unset, "ColumnStringNullPlaceholderCountCheckSpec"
    ] = UNSET
    monthly_string_null_placeholder_percent: Union[
        Unset, "ColumnStringNullPlaceholderPercentCheckSpec"
    ] = UNSET
    monthly_string_boolean_placeholder_percent: Union[
        Unset, "ColumnStringBooleanPlaceholderPercentCheckSpec"
    ] = UNSET
    monthly_string_parsable_to_integer_percent: Union[
        Unset, "ColumnStringParsableToIntegerPercentCheckSpec"
    ] = UNSET
    monthly_string_parsable_to_float_percent: Union[
        Unset, "ColumnStringParsableToFloatPercentCheckSpec"
    ] = UNSET
    monthly_expected_strings_in_use_count: Union[
        Unset, "ColumnExpectedStringsInUseCountCheckSpec"
    ] = UNSET
    monthly_string_value_in_set_percent: Union[
        Unset, "ColumnStringValueInSetPercentCheckSpec"
    ] = UNSET
    monthly_string_valid_country_code_percent: Union[
        Unset, "ColumnStringValidCountryCodePercentCheckSpec"
    ] = UNSET
    monthly_string_valid_currency_code_percent: Union[
        Unset, "ColumnStringValidCurrencyCodePercentCheckSpec"
    ] = UNSET
    monthly_string_invalid_email_count: Union[
        Unset, "ColumnStringInvalidEmailCountCheckSpec"
    ] = UNSET
    monthly_string_invalid_uuid_count: Union[
        Unset, "ColumnStringInvalidUuidCountCheckSpec"
    ] = UNSET
    monthly_string_valid_uuid_percent: Union[
        Unset, "ColumnStringValidUuidPercentCheckSpec"
    ] = UNSET
    monthly_string_invalid_ip4_address_count: Union[
        Unset, "ColumnStringInvalidIp4AddressCountCheckSpec"
    ] = UNSET
    monthly_string_invalid_ip6_address_count: Union[
        Unset, "ColumnStringInvalidIp6AddressCountCheckSpec"
    ] = UNSET
    monthly_string_not_match_regex_count: Union[
        Unset, "ColumnStringNotMatchRegexCountCheckSpec"
    ] = UNSET
    monthly_string_match_regex_percent: Union[
        Unset, "ColumnStringMatchRegexPercentCheckSpec"
    ] = UNSET
    monthly_string_not_match_date_regex_count: Union[
        Unset, "ColumnStringNotMatchDateRegexCountCheckSpec"
    ] = UNSET
    monthly_string_match_date_regex_percent: Union[
        Unset, "ColumnStringMatchDateRegexPercentCheckSpec"
    ] = UNSET
    monthly_string_match_name_regex_percent: Union[
        Unset, "ColumnStringMatchNameRegexPercentCheckSpec"
    ] = UNSET
    monthly_expected_strings_in_top_values_count: Union[
        Unset, "ColumnExpectedStringsInTopValuesCountCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_string_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_max_length, Unset):
            monthly_string_max_length = self.monthly_string_max_length.to_dict()

        monthly_string_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_min_length, Unset):
            monthly_string_min_length = self.monthly_string_min_length.to_dict()

        monthly_string_mean_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_mean_length, Unset):
            monthly_string_mean_length = self.monthly_string_mean_length.to_dict()

        monthly_string_length_below_min_length_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_string_length_below_min_length_count, Unset):
            monthly_string_length_below_min_length_count = (
                self.monthly_string_length_below_min_length_count.to_dict()
            )

        monthly_string_length_below_min_length_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_string_length_below_min_length_percent, Unset):
            monthly_string_length_below_min_length_percent = (
                self.monthly_string_length_below_min_length_percent.to_dict()
            )

        monthly_string_length_above_max_length_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_string_length_above_max_length_count, Unset):
            monthly_string_length_above_max_length_count = (
                self.monthly_string_length_above_max_length_count.to_dict()
            )

        monthly_string_length_above_max_length_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_string_length_above_max_length_percent, Unset):
            monthly_string_length_above_max_length_percent = (
                self.monthly_string_length_above_max_length_percent.to_dict()
            )

        monthly_string_length_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_length_in_range_percent, Unset):
            monthly_string_length_in_range_percent = (
                self.monthly_string_length_in_range_percent.to_dict()
            )

        monthly_string_empty_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_empty_count, Unset):
            monthly_string_empty_count = self.monthly_string_empty_count.to_dict()

        monthly_string_empty_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_empty_percent, Unset):
            monthly_string_empty_percent = self.monthly_string_empty_percent.to_dict()

        monthly_string_valid_dates_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_valid_dates_percent, Unset):
            monthly_string_valid_dates_percent = (
                self.monthly_string_valid_dates_percent.to_dict()
            )

        monthly_string_whitespace_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_whitespace_count, Unset):
            monthly_string_whitespace_count = (
                self.monthly_string_whitespace_count.to_dict()
            )

        monthly_string_whitespace_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_whitespace_percent, Unset):
            monthly_string_whitespace_percent = (
                self.monthly_string_whitespace_percent.to_dict()
            )

        monthly_string_surrounded_by_whitespace_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_string_surrounded_by_whitespace_count, Unset):
            monthly_string_surrounded_by_whitespace_count = (
                self.monthly_string_surrounded_by_whitespace_count.to_dict()
            )

        monthly_string_surrounded_by_whitespace_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_string_surrounded_by_whitespace_percent, Unset):
            monthly_string_surrounded_by_whitespace_percent = (
                self.monthly_string_surrounded_by_whitespace_percent.to_dict()
            )

        monthly_string_null_placeholder_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_null_placeholder_count, Unset):
            monthly_string_null_placeholder_count = (
                self.monthly_string_null_placeholder_count.to_dict()
            )

        monthly_string_null_placeholder_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_null_placeholder_percent, Unset):
            monthly_string_null_placeholder_percent = (
                self.monthly_string_null_placeholder_percent.to_dict()
            )

        monthly_string_boolean_placeholder_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_boolean_placeholder_percent, Unset):
            monthly_string_boolean_placeholder_percent = (
                self.monthly_string_boolean_placeholder_percent.to_dict()
            )

        monthly_string_parsable_to_integer_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_parsable_to_integer_percent, Unset):
            monthly_string_parsable_to_integer_percent = (
                self.monthly_string_parsable_to_integer_percent.to_dict()
            )

        monthly_string_parsable_to_float_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_parsable_to_float_percent, Unset):
            monthly_string_parsable_to_float_percent = (
                self.monthly_string_parsable_to_float_percent.to_dict()
            )

        monthly_expected_strings_in_use_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_expected_strings_in_use_count, Unset):
            monthly_expected_strings_in_use_count = (
                self.monthly_expected_strings_in_use_count.to_dict()
            )

        monthly_string_value_in_set_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_value_in_set_percent, Unset):
            monthly_string_value_in_set_percent = (
                self.monthly_string_value_in_set_percent.to_dict()
            )

        monthly_string_valid_country_code_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_valid_country_code_percent, Unset):
            monthly_string_valid_country_code_percent = (
                self.monthly_string_valid_country_code_percent.to_dict()
            )

        monthly_string_valid_currency_code_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_valid_currency_code_percent, Unset):
            monthly_string_valid_currency_code_percent = (
                self.monthly_string_valid_currency_code_percent.to_dict()
            )

        monthly_string_invalid_email_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_invalid_email_count, Unset):
            monthly_string_invalid_email_count = (
                self.monthly_string_invalid_email_count.to_dict()
            )

        monthly_string_invalid_uuid_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_invalid_uuid_count, Unset):
            monthly_string_invalid_uuid_count = (
                self.monthly_string_invalid_uuid_count.to_dict()
            )

        monthly_string_valid_uuid_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_valid_uuid_percent, Unset):
            monthly_string_valid_uuid_percent = (
                self.monthly_string_valid_uuid_percent.to_dict()
            )

        monthly_string_invalid_ip4_address_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_invalid_ip4_address_count, Unset):
            monthly_string_invalid_ip4_address_count = (
                self.monthly_string_invalid_ip4_address_count.to_dict()
            )

        monthly_string_invalid_ip6_address_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_invalid_ip6_address_count, Unset):
            monthly_string_invalid_ip6_address_count = (
                self.monthly_string_invalid_ip6_address_count.to_dict()
            )

        monthly_string_not_match_regex_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_not_match_regex_count, Unset):
            monthly_string_not_match_regex_count = (
                self.monthly_string_not_match_regex_count.to_dict()
            )

        monthly_string_match_regex_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_match_regex_percent, Unset):
            monthly_string_match_regex_percent = (
                self.monthly_string_match_regex_percent.to_dict()
            )

        monthly_string_not_match_date_regex_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_not_match_date_regex_count, Unset):
            monthly_string_not_match_date_regex_count = (
                self.monthly_string_not_match_date_regex_count.to_dict()
            )

        monthly_string_match_date_regex_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_match_date_regex_percent, Unset):
            monthly_string_match_date_regex_percent = (
                self.monthly_string_match_date_regex_percent.to_dict()
            )

        monthly_string_match_name_regex_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_match_name_regex_percent, Unset):
            monthly_string_match_name_regex_percent = (
                self.monthly_string_match_name_regex_percent.to_dict()
            )

        monthly_expected_strings_in_top_values_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_expected_strings_in_top_values_count, Unset):
            monthly_expected_strings_in_top_values_count = (
                self.monthly_expected_strings_in_top_values_count.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_string_max_length is not UNSET:
            field_dict["monthly_string_max_length"] = monthly_string_max_length
        if monthly_string_min_length is not UNSET:
            field_dict["monthly_string_min_length"] = monthly_string_min_length
        if monthly_string_mean_length is not UNSET:
            field_dict["monthly_string_mean_length"] = monthly_string_mean_length
        if monthly_string_length_below_min_length_count is not UNSET:
            field_dict[
                "monthly_string_length_below_min_length_count"
            ] = monthly_string_length_below_min_length_count
        if monthly_string_length_below_min_length_percent is not UNSET:
            field_dict[
                "monthly_string_length_below_min_length_percent"
            ] = monthly_string_length_below_min_length_percent
        if monthly_string_length_above_max_length_count is not UNSET:
            field_dict[
                "monthly_string_length_above_max_length_count"
            ] = monthly_string_length_above_max_length_count
        if monthly_string_length_above_max_length_percent is not UNSET:
            field_dict[
                "monthly_string_length_above_max_length_percent"
            ] = monthly_string_length_above_max_length_percent
        if monthly_string_length_in_range_percent is not UNSET:
            field_dict[
                "monthly_string_length_in_range_percent"
            ] = monthly_string_length_in_range_percent
        if monthly_string_empty_count is not UNSET:
            field_dict["monthly_string_empty_count"] = monthly_string_empty_count
        if monthly_string_empty_percent is not UNSET:
            field_dict["monthly_string_empty_percent"] = monthly_string_empty_percent
        if monthly_string_valid_dates_percent is not UNSET:
            field_dict[
                "monthly_string_valid_dates_percent"
            ] = monthly_string_valid_dates_percent
        if monthly_string_whitespace_count is not UNSET:
            field_dict[
                "monthly_string_whitespace_count"
            ] = monthly_string_whitespace_count
        if monthly_string_whitespace_percent is not UNSET:
            field_dict[
                "monthly_string_whitespace_percent"
            ] = monthly_string_whitespace_percent
        if monthly_string_surrounded_by_whitespace_count is not UNSET:
            field_dict[
                "monthly_string_surrounded_by_whitespace_count"
            ] = monthly_string_surrounded_by_whitespace_count
        if monthly_string_surrounded_by_whitespace_percent is not UNSET:
            field_dict[
                "monthly_string_surrounded_by_whitespace_percent"
            ] = monthly_string_surrounded_by_whitespace_percent
        if monthly_string_null_placeholder_count is not UNSET:
            field_dict[
                "monthly_string_null_placeholder_count"
            ] = monthly_string_null_placeholder_count
        if monthly_string_null_placeholder_percent is not UNSET:
            field_dict[
                "monthly_string_null_placeholder_percent"
            ] = monthly_string_null_placeholder_percent
        if monthly_string_boolean_placeholder_percent is not UNSET:
            field_dict[
                "monthly_string_boolean_placeholder_percent"
            ] = monthly_string_boolean_placeholder_percent
        if monthly_string_parsable_to_integer_percent is not UNSET:
            field_dict[
                "monthly_string_parsable_to_integer_percent"
            ] = monthly_string_parsable_to_integer_percent
        if monthly_string_parsable_to_float_percent is not UNSET:
            field_dict[
                "monthly_string_parsable_to_float_percent"
            ] = monthly_string_parsable_to_float_percent
        if monthly_expected_strings_in_use_count is not UNSET:
            field_dict[
                "monthly_expected_strings_in_use_count"
            ] = monthly_expected_strings_in_use_count
        if monthly_string_value_in_set_percent is not UNSET:
            field_dict[
                "monthly_string_value_in_set_percent"
            ] = monthly_string_value_in_set_percent
        if monthly_string_valid_country_code_percent is not UNSET:
            field_dict[
                "monthly_string_valid_country_code_percent"
            ] = monthly_string_valid_country_code_percent
        if monthly_string_valid_currency_code_percent is not UNSET:
            field_dict[
                "monthly_string_valid_currency_code_percent"
            ] = monthly_string_valid_currency_code_percent
        if monthly_string_invalid_email_count is not UNSET:
            field_dict[
                "monthly_string_invalid_email_count"
            ] = monthly_string_invalid_email_count
        if monthly_string_invalid_uuid_count is not UNSET:
            field_dict[
                "monthly_string_invalid_uuid_count"
            ] = monthly_string_invalid_uuid_count
        if monthly_string_valid_uuid_percent is not UNSET:
            field_dict[
                "monthly_string_valid_uuid_percent"
            ] = monthly_string_valid_uuid_percent
        if monthly_string_invalid_ip4_address_count is not UNSET:
            field_dict[
                "monthly_string_invalid_ip4_address_count"
            ] = monthly_string_invalid_ip4_address_count
        if monthly_string_invalid_ip6_address_count is not UNSET:
            field_dict[
                "monthly_string_invalid_ip6_address_count"
            ] = monthly_string_invalid_ip6_address_count
        if monthly_string_not_match_regex_count is not UNSET:
            field_dict[
                "monthly_string_not_match_regex_count"
            ] = monthly_string_not_match_regex_count
        if monthly_string_match_regex_percent is not UNSET:
            field_dict[
                "monthly_string_match_regex_percent"
            ] = monthly_string_match_regex_percent
        if monthly_string_not_match_date_regex_count is not UNSET:
            field_dict[
                "monthly_string_not_match_date_regex_count"
            ] = monthly_string_not_match_date_regex_count
        if monthly_string_match_date_regex_percent is not UNSET:
            field_dict[
                "monthly_string_match_date_regex_percent"
            ] = monthly_string_match_date_regex_percent
        if monthly_string_match_name_regex_percent is not UNSET:
            field_dict[
                "monthly_string_match_name_regex_percent"
            ] = monthly_string_match_name_regex_percent
        if monthly_expected_strings_in_top_values_count is not UNSET:
            field_dict[
                "monthly_expected_strings_in_top_values_count"
            ] = monthly_expected_strings_in_top_values_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_expected_strings_in_top_values_count_check_spec import (
            ColumnExpectedStringsInTopValuesCountCheckSpec,
        )
        from ..models.column_expected_strings_in_use_count_check_spec import (
            ColumnExpectedStringsInUseCountCheckSpec,
        )
        from ..models.column_string_boolean_placeholder_percent_check_spec import (
            ColumnStringBooleanPlaceholderPercentCheckSpec,
        )
        from ..models.column_string_empty_count_check_spec import (
            ColumnStringEmptyCountCheckSpec,
        )
        from ..models.column_string_empty_percent_check_spec import (
            ColumnStringEmptyPercentCheckSpec,
        )
        from ..models.column_string_invalid_email_count_check_spec import (
            ColumnStringInvalidEmailCountCheckSpec,
        )
        from ..models.column_string_invalid_ip_4_address_count_check_spec import (
            ColumnStringInvalidIp4AddressCountCheckSpec,
        )
        from ..models.column_string_invalid_ip_6_address_count_check_spec import (
            ColumnStringInvalidIp6AddressCountCheckSpec,
        )
        from ..models.column_string_invalid_uuid_count_check_spec import (
            ColumnStringInvalidUuidCountCheckSpec,
        )
        from ..models.column_string_length_above_max_length_count_check_spec import (
            ColumnStringLengthAboveMaxLengthCountCheckSpec,
        )
        from ..models.column_string_length_above_max_length_percent_check_spec import (
            ColumnStringLengthAboveMaxLengthPercentCheckSpec,
        )
        from ..models.column_string_length_below_min_length_count_check_spec import (
            ColumnStringLengthBelowMinLengthCountCheckSpec,
        )
        from ..models.column_string_length_below_min_length_percent_check_spec import (
            ColumnStringLengthBelowMinLengthPercentCheckSpec,
        )
        from ..models.column_string_length_in_range_percent_check_spec import (
            ColumnStringLengthInRangePercentCheckSpec,
        )
        from ..models.column_string_match_date_regex_percent_check_spec import (
            ColumnStringMatchDateRegexPercentCheckSpec,
        )
        from ..models.column_string_match_name_regex_percent_check_spec import (
            ColumnStringMatchNameRegexPercentCheckSpec,
        )
        from ..models.column_string_match_regex_percent_check_spec import (
            ColumnStringMatchRegexPercentCheckSpec,
        )
        from ..models.column_string_max_length_check_spec import (
            ColumnStringMaxLengthCheckSpec,
        )
        from ..models.column_string_mean_length_check_spec import (
            ColumnStringMeanLengthCheckSpec,
        )
        from ..models.column_string_min_length_check_spec import (
            ColumnStringMinLengthCheckSpec,
        )
        from ..models.column_string_not_match_date_regex_count_check_spec import (
            ColumnStringNotMatchDateRegexCountCheckSpec,
        )
        from ..models.column_string_not_match_regex_count_check_spec import (
            ColumnStringNotMatchRegexCountCheckSpec,
        )
        from ..models.column_string_null_placeholder_count_check_spec import (
            ColumnStringNullPlaceholderCountCheckSpec,
        )
        from ..models.column_string_null_placeholder_percent_check_spec import (
            ColumnStringNullPlaceholderPercentCheckSpec,
        )
        from ..models.column_string_parsable_to_float_percent_check_spec import (
            ColumnStringParsableToFloatPercentCheckSpec,
        )
        from ..models.column_string_parsable_to_integer_percent_check_spec import (
            ColumnStringParsableToIntegerPercentCheckSpec,
        )
        from ..models.column_string_surrounded_by_whitespace_count_check_spec import (
            ColumnStringSurroundedByWhitespaceCountCheckSpec,
        )
        from ..models.column_string_surrounded_by_whitespace_percent_check_spec import (
            ColumnStringSurroundedByWhitespacePercentCheckSpec,
        )
        from ..models.column_string_valid_country_code_percent_check_spec import (
            ColumnStringValidCountryCodePercentCheckSpec,
        )
        from ..models.column_string_valid_currency_code_percent_check_spec import (
            ColumnStringValidCurrencyCodePercentCheckSpec,
        )
        from ..models.column_string_valid_dates_percent_check_spec import (
            ColumnStringValidDatesPercentCheckSpec,
        )
        from ..models.column_string_valid_uuid_percent_check_spec import (
            ColumnStringValidUuidPercentCheckSpec,
        )
        from ..models.column_string_value_in_set_percent_check_spec import (
            ColumnStringValueInSetPercentCheckSpec,
        )
        from ..models.column_string_whitespace_count_check_spec import (
            ColumnStringWhitespaceCountCheckSpec,
        )
        from ..models.column_string_whitespace_percent_check_spec import (
            ColumnStringWhitespacePercentCheckSpec,
        )
        from ..models.column_strings_monthly_monitoring_checks_spec_custom_checks import (
            ColumnStringsMonthlyMonitoringChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnStringsMonthlyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnStringsMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_string_max_length = d.pop("monthly_string_max_length", UNSET)
        monthly_string_max_length: Union[Unset, ColumnStringMaxLengthCheckSpec]
        if isinstance(_monthly_string_max_length, Unset):
            monthly_string_max_length = UNSET
        else:
            monthly_string_max_length = ColumnStringMaxLengthCheckSpec.from_dict(
                _monthly_string_max_length
            )

        _monthly_string_min_length = d.pop("monthly_string_min_length", UNSET)
        monthly_string_min_length: Union[Unset, ColumnStringMinLengthCheckSpec]
        if isinstance(_monthly_string_min_length, Unset):
            monthly_string_min_length = UNSET
        else:
            monthly_string_min_length = ColumnStringMinLengthCheckSpec.from_dict(
                _monthly_string_min_length
            )

        _monthly_string_mean_length = d.pop("monthly_string_mean_length", UNSET)
        monthly_string_mean_length: Union[Unset, ColumnStringMeanLengthCheckSpec]
        if isinstance(_monthly_string_mean_length, Unset):
            monthly_string_mean_length = UNSET
        else:
            monthly_string_mean_length = ColumnStringMeanLengthCheckSpec.from_dict(
                _monthly_string_mean_length
            )

        _monthly_string_length_below_min_length_count = d.pop(
            "monthly_string_length_below_min_length_count", UNSET
        )
        monthly_string_length_below_min_length_count: Union[
            Unset, ColumnStringLengthBelowMinLengthCountCheckSpec
        ]
        if isinstance(_monthly_string_length_below_min_length_count, Unset):
            monthly_string_length_below_min_length_count = UNSET
        else:
            monthly_string_length_below_min_length_count = (
                ColumnStringLengthBelowMinLengthCountCheckSpec.from_dict(
                    _monthly_string_length_below_min_length_count
                )
            )

        _monthly_string_length_below_min_length_percent = d.pop(
            "monthly_string_length_below_min_length_percent", UNSET
        )
        monthly_string_length_below_min_length_percent: Union[
            Unset, ColumnStringLengthBelowMinLengthPercentCheckSpec
        ]
        if isinstance(_monthly_string_length_below_min_length_percent, Unset):
            monthly_string_length_below_min_length_percent = UNSET
        else:
            monthly_string_length_below_min_length_percent = (
                ColumnStringLengthBelowMinLengthPercentCheckSpec.from_dict(
                    _monthly_string_length_below_min_length_percent
                )
            )

        _monthly_string_length_above_max_length_count = d.pop(
            "monthly_string_length_above_max_length_count", UNSET
        )
        monthly_string_length_above_max_length_count: Union[
            Unset, ColumnStringLengthAboveMaxLengthCountCheckSpec
        ]
        if isinstance(_monthly_string_length_above_max_length_count, Unset):
            monthly_string_length_above_max_length_count = UNSET
        else:
            monthly_string_length_above_max_length_count = (
                ColumnStringLengthAboveMaxLengthCountCheckSpec.from_dict(
                    _monthly_string_length_above_max_length_count
                )
            )

        _monthly_string_length_above_max_length_percent = d.pop(
            "monthly_string_length_above_max_length_percent", UNSET
        )
        monthly_string_length_above_max_length_percent: Union[
            Unset, ColumnStringLengthAboveMaxLengthPercentCheckSpec
        ]
        if isinstance(_monthly_string_length_above_max_length_percent, Unset):
            monthly_string_length_above_max_length_percent = UNSET
        else:
            monthly_string_length_above_max_length_percent = (
                ColumnStringLengthAboveMaxLengthPercentCheckSpec.from_dict(
                    _monthly_string_length_above_max_length_percent
                )
            )

        _monthly_string_length_in_range_percent = d.pop(
            "monthly_string_length_in_range_percent", UNSET
        )
        monthly_string_length_in_range_percent: Union[
            Unset, ColumnStringLengthInRangePercentCheckSpec
        ]
        if isinstance(_monthly_string_length_in_range_percent, Unset):
            monthly_string_length_in_range_percent = UNSET
        else:
            monthly_string_length_in_range_percent = (
                ColumnStringLengthInRangePercentCheckSpec.from_dict(
                    _monthly_string_length_in_range_percent
                )
            )

        _monthly_string_empty_count = d.pop("monthly_string_empty_count", UNSET)
        monthly_string_empty_count: Union[Unset, ColumnStringEmptyCountCheckSpec]
        if isinstance(_monthly_string_empty_count, Unset):
            monthly_string_empty_count = UNSET
        else:
            monthly_string_empty_count = ColumnStringEmptyCountCheckSpec.from_dict(
                _monthly_string_empty_count
            )

        _monthly_string_empty_percent = d.pop("monthly_string_empty_percent", UNSET)
        monthly_string_empty_percent: Union[Unset, ColumnStringEmptyPercentCheckSpec]
        if isinstance(_monthly_string_empty_percent, Unset):
            monthly_string_empty_percent = UNSET
        else:
            monthly_string_empty_percent = ColumnStringEmptyPercentCheckSpec.from_dict(
                _monthly_string_empty_percent
            )

        _monthly_string_valid_dates_percent = d.pop(
            "monthly_string_valid_dates_percent", UNSET
        )
        monthly_string_valid_dates_percent: Union[
            Unset, ColumnStringValidDatesPercentCheckSpec
        ]
        if isinstance(_monthly_string_valid_dates_percent, Unset):
            monthly_string_valid_dates_percent = UNSET
        else:
            monthly_string_valid_dates_percent = (
                ColumnStringValidDatesPercentCheckSpec.from_dict(
                    _monthly_string_valid_dates_percent
                )
            )

        _monthly_string_whitespace_count = d.pop(
            "monthly_string_whitespace_count", UNSET
        )
        monthly_string_whitespace_count: Union[
            Unset, ColumnStringWhitespaceCountCheckSpec
        ]
        if isinstance(_monthly_string_whitespace_count, Unset):
            monthly_string_whitespace_count = UNSET
        else:
            monthly_string_whitespace_count = (
                ColumnStringWhitespaceCountCheckSpec.from_dict(
                    _monthly_string_whitespace_count
                )
            )

        _monthly_string_whitespace_percent = d.pop(
            "monthly_string_whitespace_percent", UNSET
        )
        monthly_string_whitespace_percent: Union[
            Unset, ColumnStringWhitespacePercentCheckSpec
        ]
        if isinstance(_monthly_string_whitespace_percent, Unset):
            monthly_string_whitespace_percent = UNSET
        else:
            monthly_string_whitespace_percent = (
                ColumnStringWhitespacePercentCheckSpec.from_dict(
                    _monthly_string_whitespace_percent
                )
            )

        _monthly_string_surrounded_by_whitespace_count = d.pop(
            "monthly_string_surrounded_by_whitespace_count", UNSET
        )
        monthly_string_surrounded_by_whitespace_count: Union[
            Unset, ColumnStringSurroundedByWhitespaceCountCheckSpec
        ]
        if isinstance(_monthly_string_surrounded_by_whitespace_count, Unset):
            monthly_string_surrounded_by_whitespace_count = UNSET
        else:
            monthly_string_surrounded_by_whitespace_count = (
                ColumnStringSurroundedByWhitespaceCountCheckSpec.from_dict(
                    _monthly_string_surrounded_by_whitespace_count
                )
            )

        _monthly_string_surrounded_by_whitespace_percent = d.pop(
            "monthly_string_surrounded_by_whitespace_percent", UNSET
        )
        monthly_string_surrounded_by_whitespace_percent: Union[
            Unset, ColumnStringSurroundedByWhitespacePercentCheckSpec
        ]
        if isinstance(_monthly_string_surrounded_by_whitespace_percent, Unset):
            monthly_string_surrounded_by_whitespace_percent = UNSET
        else:
            monthly_string_surrounded_by_whitespace_percent = (
                ColumnStringSurroundedByWhitespacePercentCheckSpec.from_dict(
                    _monthly_string_surrounded_by_whitespace_percent
                )
            )

        _monthly_string_null_placeholder_count = d.pop(
            "monthly_string_null_placeholder_count", UNSET
        )
        monthly_string_null_placeholder_count: Union[
            Unset, ColumnStringNullPlaceholderCountCheckSpec
        ]
        if isinstance(_monthly_string_null_placeholder_count, Unset):
            monthly_string_null_placeholder_count = UNSET
        else:
            monthly_string_null_placeholder_count = (
                ColumnStringNullPlaceholderCountCheckSpec.from_dict(
                    _monthly_string_null_placeholder_count
                )
            )

        _monthly_string_null_placeholder_percent = d.pop(
            "monthly_string_null_placeholder_percent", UNSET
        )
        monthly_string_null_placeholder_percent: Union[
            Unset, ColumnStringNullPlaceholderPercentCheckSpec
        ]
        if isinstance(_monthly_string_null_placeholder_percent, Unset):
            monthly_string_null_placeholder_percent = UNSET
        else:
            monthly_string_null_placeholder_percent = (
                ColumnStringNullPlaceholderPercentCheckSpec.from_dict(
                    _monthly_string_null_placeholder_percent
                )
            )

        _monthly_string_boolean_placeholder_percent = d.pop(
            "monthly_string_boolean_placeholder_percent", UNSET
        )
        monthly_string_boolean_placeholder_percent: Union[
            Unset, ColumnStringBooleanPlaceholderPercentCheckSpec
        ]
        if isinstance(_monthly_string_boolean_placeholder_percent, Unset):
            monthly_string_boolean_placeholder_percent = UNSET
        else:
            monthly_string_boolean_placeholder_percent = (
                ColumnStringBooleanPlaceholderPercentCheckSpec.from_dict(
                    _monthly_string_boolean_placeholder_percent
                )
            )

        _monthly_string_parsable_to_integer_percent = d.pop(
            "monthly_string_parsable_to_integer_percent", UNSET
        )
        monthly_string_parsable_to_integer_percent: Union[
            Unset, ColumnStringParsableToIntegerPercentCheckSpec
        ]
        if isinstance(_monthly_string_parsable_to_integer_percent, Unset):
            monthly_string_parsable_to_integer_percent = UNSET
        else:
            monthly_string_parsable_to_integer_percent = (
                ColumnStringParsableToIntegerPercentCheckSpec.from_dict(
                    _monthly_string_parsable_to_integer_percent
                )
            )

        _monthly_string_parsable_to_float_percent = d.pop(
            "monthly_string_parsable_to_float_percent", UNSET
        )
        monthly_string_parsable_to_float_percent: Union[
            Unset, ColumnStringParsableToFloatPercentCheckSpec
        ]
        if isinstance(_monthly_string_parsable_to_float_percent, Unset):
            monthly_string_parsable_to_float_percent = UNSET
        else:
            monthly_string_parsable_to_float_percent = (
                ColumnStringParsableToFloatPercentCheckSpec.from_dict(
                    _monthly_string_parsable_to_float_percent
                )
            )

        _monthly_expected_strings_in_use_count = d.pop(
            "monthly_expected_strings_in_use_count", UNSET
        )
        monthly_expected_strings_in_use_count: Union[
            Unset, ColumnExpectedStringsInUseCountCheckSpec
        ]
        if isinstance(_monthly_expected_strings_in_use_count, Unset):
            monthly_expected_strings_in_use_count = UNSET
        else:
            monthly_expected_strings_in_use_count = (
                ColumnExpectedStringsInUseCountCheckSpec.from_dict(
                    _monthly_expected_strings_in_use_count
                )
            )

        _monthly_string_value_in_set_percent = d.pop(
            "monthly_string_value_in_set_percent", UNSET
        )
        monthly_string_value_in_set_percent: Union[
            Unset, ColumnStringValueInSetPercentCheckSpec
        ]
        if isinstance(_monthly_string_value_in_set_percent, Unset):
            monthly_string_value_in_set_percent = UNSET
        else:
            monthly_string_value_in_set_percent = (
                ColumnStringValueInSetPercentCheckSpec.from_dict(
                    _monthly_string_value_in_set_percent
                )
            )

        _monthly_string_valid_country_code_percent = d.pop(
            "monthly_string_valid_country_code_percent", UNSET
        )
        monthly_string_valid_country_code_percent: Union[
            Unset, ColumnStringValidCountryCodePercentCheckSpec
        ]
        if isinstance(_monthly_string_valid_country_code_percent, Unset):
            monthly_string_valid_country_code_percent = UNSET
        else:
            monthly_string_valid_country_code_percent = (
                ColumnStringValidCountryCodePercentCheckSpec.from_dict(
                    _monthly_string_valid_country_code_percent
                )
            )

        _monthly_string_valid_currency_code_percent = d.pop(
            "monthly_string_valid_currency_code_percent", UNSET
        )
        monthly_string_valid_currency_code_percent: Union[
            Unset, ColumnStringValidCurrencyCodePercentCheckSpec
        ]
        if isinstance(_monthly_string_valid_currency_code_percent, Unset):
            monthly_string_valid_currency_code_percent = UNSET
        else:
            monthly_string_valid_currency_code_percent = (
                ColumnStringValidCurrencyCodePercentCheckSpec.from_dict(
                    _monthly_string_valid_currency_code_percent
                )
            )

        _monthly_string_invalid_email_count = d.pop(
            "monthly_string_invalid_email_count", UNSET
        )
        monthly_string_invalid_email_count: Union[
            Unset, ColumnStringInvalidEmailCountCheckSpec
        ]
        if isinstance(_monthly_string_invalid_email_count, Unset):
            monthly_string_invalid_email_count = UNSET
        else:
            monthly_string_invalid_email_count = (
                ColumnStringInvalidEmailCountCheckSpec.from_dict(
                    _monthly_string_invalid_email_count
                )
            )

        _monthly_string_invalid_uuid_count = d.pop(
            "monthly_string_invalid_uuid_count", UNSET
        )
        monthly_string_invalid_uuid_count: Union[
            Unset, ColumnStringInvalidUuidCountCheckSpec
        ]
        if isinstance(_monthly_string_invalid_uuid_count, Unset):
            monthly_string_invalid_uuid_count = UNSET
        else:
            monthly_string_invalid_uuid_count = (
                ColumnStringInvalidUuidCountCheckSpec.from_dict(
                    _monthly_string_invalid_uuid_count
                )
            )

        _monthly_string_valid_uuid_percent = d.pop(
            "monthly_string_valid_uuid_percent", UNSET
        )
        monthly_string_valid_uuid_percent: Union[
            Unset, ColumnStringValidUuidPercentCheckSpec
        ]
        if isinstance(_monthly_string_valid_uuid_percent, Unset):
            monthly_string_valid_uuid_percent = UNSET
        else:
            monthly_string_valid_uuid_percent = (
                ColumnStringValidUuidPercentCheckSpec.from_dict(
                    _monthly_string_valid_uuid_percent
                )
            )

        _monthly_string_invalid_ip4_address_count = d.pop(
            "monthly_string_invalid_ip4_address_count", UNSET
        )
        monthly_string_invalid_ip4_address_count: Union[
            Unset, ColumnStringInvalidIp4AddressCountCheckSpec
        ]
        if isinstance(_monthly_string_invalid_ip4_address_count, Unset):
            monthly_string_invalid_ip4_address_count = UNSET
        else:
            monthly_string_invalid_ip4_address_count = (
                ColumnStringInvalidIp4AddressCountCheckSpec.from_dict(
                    _monthly_string_invalid_ip4_address_count
                )
            )

        _monthly_string_invalid_ip6_address_count = d.pop(
            "monthly_string_invalid_ip6_address_count", UNSET
        )
        monthly_string_invalid_ip6_address_count: Union[
            Unset, ColumnStringInvalidIp6AddressCountCheckSpec
        ]
        if isinstance(_monthly_string_invalid_ip6_address_count, Unset):
            monthly_string_invalid_ip6_address_count = UNSET
        else:
            monthly_string_invalid_ip6_address_count = (
                ColumnStringInvalidIp6AddressCountCheckSpec.from_dict(
                    _monthly_string_invalid_ip6_address_count
                )
            )

        _monthly_string_not_match_regex_count = d.pop(
            "monthly_string_not_match_regex_count", UNSET
        )
        monthly_string_not_match_regex_count: Union[
            Unset, ColumnStringNotMatchRegexCountCheckSpec
        ]
        if isinstance(_monthly_string_not_match_regex_count, Unset):
            monthly_string_not_match_regex_count = UNSET
        else:
            monthly_string_not_match_regex_count = (
                ColumnStringNotMatchRegexCountCheckSpec.from_dict(
                    _monthly_string_not_match_regex_count
                )
            )

        _monthly_string_match_regex_percent = d.pop(
            "monthly_string_match_regex_percent", UNSET
        )
        monthly_string_match_regex_percent: Union[
            Unset, ColumnStringMatchRegexPercentCheckSpec
        ]
        if isinstance(_monthly_string_match_regex_percent, Unset):
            monthly_string_match_regex_percent = UNSET
        else:
            monthly_string_match_regex_percent = (
                ColumnStringMatchRegexPercentCheckSpec.from_dict(
                    _monthly_string_match_regex_percent
                )
            )

        _monthly_string_not_match_date_regex_count = d.pop(
            "monthly_string_not_match_date_regex_count", UNSET
        )
        monthly_string_not_match_date_regex_count: Union[
            Unset, ColumnStringNotMatchDateRegexCountCheckSpec
        ]
        if isinstance(_monthly_string_not_match_date_regex_count, Unset):
            monthly_string_not_match_date_regex_count = UNSET
        else:
            monthly_string_not_match_date_regex_count = (
                ColumnStringNotMatchDateRegexCountCheckSpec.from_dict(
                    _monthly_string_not_match_date_regex_count
                )
            )

        _monthly_string_match_date_regex_percent = d.pop(
            "monthly_string_match_date_regex_percent", UNSET
        )
        monthly_string_match_date_regex_percent: Union[
            Unset, ColumnStringMatchDateRegexPercentCheckSpec
        ]
        if isinstance(_monthly_string_match_date_regex_percent, Unset):
            monthly_string_match_date_regex_percent = UNSET
        else:
            monthly_string_match_date_regex_percent = (
                ColumnStringMatchDateRegexPercentCheckSpec.from_dict(
                    _monthly_string_match_date_regex_percent
                )
            )

        _monthly_string_match_name_regex_percent = d.pop(
            "monthly_string_match_name_regex_percent", UNSET
        )
        monthly_string_match_name_regex_percent: Union[
            Unset, ColumnStringMatchNameRegexPercentCheckSpec
        ]
        if isinstance(_monthly_string_match_name_regex_percent, Unset):
            monthly_string_match_name_regex_percent = UNSET
        else:
            monthly_string_match_name_regex_percent = (
                ColumnStringMatchNameRegexPercentCheckSpec.from_dict(
                    _monthly_string_match_name_regex_percent
                )
            )

        _monthly_expected_strings_in_top_values_count = d.pop(
            "monthly_expected_strings_in_top_values_count", UNSET
        )
        monthly_expected_strings_in_top_values_count: Union[
            Unset, ColumnExpectedStringsInTopValuesCountCheckSpec
        ]
        if isinstance(_monthly_expected_strings_in_top_values_count, Unset):
            monthly_expected_strings_in_top_values_count = UNSET
        else:
            monthly_expected_strings_in_top_values_count = (
                ColumnExpectedStringsInTopValuesCountCheckSpec.from_dict(
                    _monthly_expected_strings_in_top_values_count
                )
            )

        column_strings_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_string_max_length=monthly_string_max_length,
            monthly_string_min_length=monthly_string_min_length,
            monthly_string_mean_length=monthly_string_mean_length,
            monthly_string_length_below_min_length_count=monthly_string_length_below_min_length_count,
            monthly_string_length_below_min_length_percent=monthly_string_length_below_min_length_percent,
            monthly_string_length_above_max_length_count=monthly_string_length_above_max_length_count,
            monthly_string_length_above_max_length_percent=monthly_string_length_above_max_length_percent,
            monthly_string_length_in_range_percent=monthly_string_length_in_range_percent,
            monthly_string_empty_count=monthly_string_empty_count,
            monthly_string_empty_percent=monthly_string_empty_percent,
            monthly_string_valid_dates_percent=monthly_string_valid_dates_percent,
            monthly_string_whitespace_count=monthly_string_whitespace_count,
            monthly_string_whitespace_percent=monthly_string_whitespace_percent,
            monthly_string_surrounded_by_whitespace_count=monthly_string_surrounded_by_whitespace_count,
            monthly_string_surrounded_by_whitespace_percent=monthly_string_surrounded_by_whitespace_percent,
            monthly_string_null_placeholder_count=monthly_string_null_placeholder_count,
            monthly_string_null_placeholder_percent=monthly_string_null_placeholder_percent,
            monthly_string_boolean_placeholder_percent=monthly_string_boolean_placeholder_percent,
            monthly_string_parsable_to_integer_percent=monthly_string_parsable_to_integer_percent,
            monthly_string_parsable_to_float_percent=monthly_string_parsable_to_float_percent,
            monthly_expected_strings_in_use_count=monthly_expected_strings_in_use_count,
            monthly_string_value_in_set_percent=monthly_string_value_in_set_percent,
            monthly_string_valid_country_code_percent=monthly_string_valid_country_code_percent,
            monthly_string_valid_currency_code_percent=monthly_string_valid_currency_code_percent,
            monthly_string_invalid_email_count=monthly_string_invalid_email_count,
            monthly_string_invalid_uuid_count=monthly_string_invalid_uuid_count,
            monthly_string_valid_uuid_percent=monthly_string_valid_uuid_percent,
            monthly_string_invalid_ip4_address_count=monthly_string_invalid_ip4_address_count,
            monthly_string_invalid_ip6_address_count=monthly_string_invalid_ip6_address_count,
            monthly_string_not_match_regex_count=monthly_string_not_match_regex_count,
            monthly_string_match_regex_percent=monthly_string_match_regex_percent,
            monthly_string_not_match_date_regex_count=monthly_string_not_match_date_regex_count,
            monthly_string_match_date_regex_percent=monthly_string_match_date_regex_percent,
            monthly_string_match_name_regex_percent=monthly_string_match_name_regex_percent,
            monthly_expected_strings_in_top_values_count=monthly_expected_strings_in_top_values_count,
        )

        column_strings_monthly_monitoring_checks_spec.additional_properties = d
        return column_strings_monthly_monitoring_checks_spec

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