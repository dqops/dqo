from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

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
    from ..models.column_strings_profiling_checks_spec_custom_checks import (
        ColumnStringsProfilingChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnStringsProfilingChecksSpec")


@_attrs_define
class ColumnStringsProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnStringsProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_string_max_length (Union[Unset, ColumnStringMaxLengthCheckSpec]):
        profile_string_min_length (Union[Unset, ColumnStringMinLengthCheckSpec]):
        profile_string_mean_length (Union[Unset, ColumnStringMeanLengthCheckSpec]):
        profile_string_length_below_min_length_count (Union[Unset, ColumnStringLengthBelowMinLengthCountCheckSpec]):
        profile_string_length_below_min_length_percent (Union[Unset, ColumnStringLengthBelowMinLengthPercentCheckSpec]):
        profile_string_length_above_max_length_count (Union[Unset, ColumnStringLengthAboveMaxLengthCountCheckSpec]):
        profile_string_length_above_max_length_percent (Union[Unset, ColumnStringLengthAboveMaxLengthPercentCheckSpec]):
        profile_string_length_in_range_percent (Union[Unset, ColumnStringLengthInRangePercentCheckSpec]):
        profile_string_empty_count (Union[Unset, ColumnStringEmptyCountCheckSpec]):
        profile_string_empty_percent (Union[Unset, ColumnStringEmptyPercentCheckSpec]):
        profile_string_whitespace_count (Union[Unset, ColumnStringWhitespaceCountCheckSpec]):
        profile_string_whitespace_percent (Union[Unset, ColumnStringWhitespacePercentCheckSpec]):
        profile_string_surrounded_by_whitespace_count (Union[Unset, ColumnStringSurroundedByWhitespaceCountCheckSpec]):
        profile_string_surrounded_by_whitespace_percent (Union[Unset,
            ColumnStringSurroundedByWhitespacePercentCheckSpec]):
        profile_string_null_placeholder_count (Union[Unset, ColumnStringNullPlaceholderCountCheckSpec]):
        profile_string_null_placeholder_percent (Union[Unset, ColumnStringNullPlaceholderPercentCheckSpec]):
        profile_string_boolean_placeholder_percent (Union[Unset, ColumnStringBooleanPlaceholderPercentCheckSpec]):
        profile_string_parsable_to_integer_percent (Union[Unset, ColumnStringParsableToIntegerPercentCheckSpec]):
        profile_string_parsable_to_float_percent (Union[Unset, ColumnStringParsableToFloatPercentCheckSpec]):
        profile_expected_strings_in_use_count (Union[Unset, ColumnExpectedStringsInUseCountCheckSpec]):
        profile_string_value_in_set_percent (Union[Unset, ColumnStringValueInSetPercentCheckSpec]):
        profile_string_valid_dates_percent (Union[Unset, ColumnStringValidDatesPercentCheckSpec]):
        profile_string_valid_country_code_percent (Union[Unset, ColumnStringValidCountryCodePercentCheckSpec]):
        profile_string_valid_currency_code_percent (Union[Unset, ColumnStringValidCurrencyCodePercentCheckSpec]):
        profile_string_invalid_email_count (Union[Unset, ColumnStringInvalidEmailCountCheckSpec]):
        profile_string_invalid_uuid_count (Union[Unset, ColumnStringInvalidUuidCountCheckSpec]):
        profile_string_valid_uuid_percent (Union[Unset, ColumnStringValidUuidPercentCheckSpec]):
        profile_string_invalid_ip4_address_count (Union[Unset, ColumnStringInvalidIp4AddressCountCheckSpec]):
        profile_string_invalid_ip6_address_count (Union[Unset, ColumnStringInvalidIp6AddressCountCheckSpec]):
        profile_string_not_match_regex_count (Union[Unset, ColumnStringNotMatchRegexCountCheckSpec]):
        profile_string_match_regex_percent (Union[Unset, ColumnStringMatchRegexPercentCheckSpec]):
        profile_string_not_match_date_regex_count (Union[Unset, ColumnStringNotMatchDateRegexCountCheckSpec]):
        profile_string_match_date_regex_percent (Union[Unset, ColumnStringMatchDateRegexPercentCheckSpec]):
        profile_string_match_name_regex_percent (Union[Unset, ColumnStringMatchNameRegexPercentCheckSpec]):
        profile_expected_strings_in_top_values_count (Union[Unset, ColumnExpectedStringsInTopValuesCountCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnStringsProfilingChecksSpecCustomChecks"] = UNSET
    profile_string_max_length: Union[Unset, "ColumnStringMaxLengthCheckSpec"] = UNSET
    profile_string_min_length: Union[Unset, "ColumnStringMinLengthCheckSpec"] = UNSET
    profile_string_mean_length: Union[Unset, "ColumnStringMeanLengthCheckSpec"] = UNSET
    profile_string_length_below_min_length_count: Union[
        Unset, "ColumnStringLengthBelowMinLengthCountCheckSpec"
    ] = UNSET
    profile_string_length_below_min_length_percent: Union[
        Unset, "ColumnStringLengthBelowMinLengthPercentCheckSpec"
    ] = UNSET
    profile_string_length_above_max_length_count: Union[
        Unset, "ColumnStringLengthAboveMaxLengthCountCheckSpec"
    ] = UNSET
    profile_string_length_above_max_length_percent: Union[
        Unset, "ColumnStringLengthAboveMaxLengthPercentCheckSpec"
    ] = UNSET
    profile_string_length_in_range_percent: Union[
        Unset, "ColumnStringLengthInRangePercentCheckSpec"
    ] = UNSET
    profile_string_empty_count: Union[Unset, "ColumnStringEmptyCountCheckSpec"] = UNSET
    profile_string_empty_percent: Union[
        Unset, "ColumnStringEmptyPercentCheckSpec"
    ] = UNSET
    profile_string_whitespace_count: Union[
        Unset, "ColumnStringWhitespaceCountCheckSpec"
    ] = UNSET
    profile_string_whitespace_percent: Union[
        Unset, "ColumnStringWhitespacePercentCheckSpec"
    ] = UNSET
    profile_string_surrounded_by_whitespace_count: Union[
        Unset, "ColumnStringSurroundedByWhitespaceCountCheckSpec"
    ] = UNSET
    profile_string_surrounded_by_whitespace_percent: Union[
        Unset, "ColumnStringSurroundedByWhitespacePercentCheckSpec"
    ] = UNSET
    profile_string_null_placeholder_count: Union[
        Unset, "ColumnStringNullPlaceholderCountCheckSpec"
    ] = UNSET
    profile_string_null_placeholder_percent: Union[
        Unset, "ColumnStringNullPlaceholderPercentCheckSpec"
    ] = UNSET
    profile_string_boolean_placeholder_percent: Union[
        Unset, "ColumnStringBooleanPlaceholderPercentCheckSpec"
    ] = UNSET
    profile_string_parsable_to_integer_percent: Union[
        Unset, "ColumnStringParsableToIntegerPercentCheckSpec"
    ] = UNSET
    profile_string_parsable_to_float_percent: Union[
        Unset, "ColumnStringParsableToFloatPercentCheckSpec"
    ] = UNSET
    profile_expected_strings_in_use_count: Union[
        Unset, "ColumnExpectedStringsInUseCountCheckSpec"
    ] = UNSET
    profile_string_value_in_set_percent: Union[
        Unset, "ColumnStringValueInSetPercentCheckSpec"
    ] = UNSET
    profile_string_valid_dates_percent: Union[
        Unset, "ColumnStringValidDatesPercentCheckSpec"
    ] = UNSET
    profile_string_valid_country_code_percent: Union[
        Unset, "ColumnStringValidCountryCodePercentCheckSpec"
    ] = UNSET
    profile_string_valid_currency_code_percent: Union[
        Unset, "ColumnStringValidCurrencyCodePercentCheckSpec"
    ] = UNSET
    profile_string_invalid_email_count: Union[
        Unset, "ColumnStringInvalidEmailCountCheckSpec"
    ] = UNSET
    profile_string_invalid_uuid_count: Union[
        Unset, "ColumnStringInvalidUuidCountCheckSpec"
    ] = UNSET
    profile_string_valid_uuid_percent: Union[
        Unset, "ColumnStringValidUuidPercentCheckSpec"
    ] = UNSET
    profile_string_invalid_ip4_address_count: Union[
        Unset, "ColumnStringInvalidIp4AddressCountCheckSpec"
    ] = UNSET
    profile_string_invalid_ip6_address_count: Union[
        Unset, "ColumnStringInvalidIp6AddressCountCheckSpec"
    ] = UNSET
    profile_string_not_match_regex_count: Union[
        Unset, "ColumnStringNotMatchRegexCountCheckSpec"
    ] = UNSET
    profile_string_match_regex_percent: Union[
        Unset, "ColumnStringMatchRegexPercentCheckSpec"
    ] = UNSET
    profile_string_not_match_date_regex_count: Union[
        Unset, "ColumnStringNotMatchDateRegexCountCheckSpec"
    ] = UNSET
    profile_string_match_date_regex_percent: Union[
        Unset, "ColumnStringMatchDateRegexPercentCheckSpec"
    ] = UNSET
    profile_string_match_name_regex_percent: Union[
        Unset, "ColumnStringMatchNameRegexPercentCheckSpec"
    ] = UNSET
    profile_expected_strings_in_top_values_count: Union[
        Unset, "ColumnExpectedStringsInTopValuesCountCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_string_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_max_length, Unset):
            profile_string_max_length = self.profile_string_max_length.to_dict()

        profile_string_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_min_length, Unset):
            profile_string_min_length = self.profile_string_min_length.to_dict()

        profile_string_mean_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_mean_length, Unset):
            profile_string_mean_length = self.profile_string_mean_length.to_dict()

        profile_string_length_below_min_length_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_string_length_below_min_length_count, Unset):
            profile_string_length_below_min_length_count = (
                self.profile_string_length_below_min_length_count.to_dict()
            )

        profile_string_length_below_min_length_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_string_length_below_min_length_percent, Unset):
            profile_string_length_below_min_length_percent = (
                self.profile_string_length_below_min_length_percent.to_dict()
            )

        profile_string_length_above_max_length_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_string_length_above_max_length_count, Unset):
            profile_string_length_above_max_length_count = (
                self.profile_string_length_above_max_length_count.to_dict()
            )

        profile_string_length_above_max_length_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_string_length_above_max_length_percent, Unset):
            profile_string_length_above_max_length_percent = (
                self.profile_string_length_above_max_length_percent.to_dict()
            )

        profile_string_length_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_length_in_range_percent, Unset):
            profile_string_length_in_range_percent = (
                self.profile_string_length_in_range_percent.to_dict()
            )

        profile_string_empty_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_empty_count, Unset):
            profile_string_empty_count = self.profile_string_empty_count.to_dict()

        profile_string_empty_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_empty_percent, Unset):
            profile_string_empty_percent = self.profile_string_empty_percent.to_dict()

        profile_string_whitespace_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_whitespace_count, Unset):
            profile_string_whitespace_count = (
                self.profile_string_whitespace_count.to_dict()
            )

        profile_string_whitespace_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_whitespace_percent, Unset):
            profile_string_whitespace_percent = (
                self.profile_string_whitespace_percent.to_dict()
            )

        profile_string_surrounded_by_whitespace_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_string_surrounded_by_whitespace_count, Unset):
            profile_string_surrounded_by_whitespace_count = (
                self.profile_string_surrounded_by_whitespace_count.to_dict()
            )

        profile_string_surrounded_by_whitespace_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_string_surrounded_by_whitespace_percent, Unset):
            profile_string_surrounded_by_whitespace_percent = (
                self.profile_string_surrounded_by_whitespace_percent.to_dict()
            )

        profile_string_null_placeholder_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_null_placeholder_count, Unset):
            profile_string_null_placeholder_count = (
                self.profile_string_null_placeholder_count.to_dict()
            )

        profile_string_null_placeholder_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_null_placeholder_percent, Unset):
            profile_string_null_placeholder_percent = (
                self.profile_string_null_placeholder_percent.to_dict()
            )

        profile_string_boolean_placeholder_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_boolean_placeholder_percent, Unset):
            profile_string_boolean_placeholder_percent = (
                self.profile_string_boolean_placeholder_percent.to_dict()
            )

        profile_string_parsable_to_integer_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_parsable_to_integer_percent, Unset):
            profile_string_parsable_to_integer_percent = (
                self.profile_string_parsable_to_integer_percent.to_dict()
            )

        profile_string_parsable_to_float_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_parsable_to_float_percent, Unset):
            profile_string_parsable_to_float_percent = (
                self.profile_string_parsable_to_float_percent.to_dict()
            )

        profile_expected_strings_in_use_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_expected_strings_in_use_count, Unset):
            profile_expected_strings_in_use_count = (
                self.profile_expected_strings_in_use_count.to_dict()
            )

        profile_string_value_in_set_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_value_in_set_percent, Unset):
            profile_string_value_in_set_percent = (
                self.profile_string_value_in_set_percent.to_dict()
            )

        profile_string_valid_dates_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_valid_dates_percent, Unset):
            profile_string_valid_dates_percent = (
                self.profile_string_valid_dates_percent.to_dict()
            )

        profile_string_valid_country_code_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_valid_country_code_percent, Unset):
            profile_string_valid_country_code_percent = (
                self.profile_string_valid_country_code_percent.to_dict()
            )

        profile_string_valid_currency_code_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_valid_currency_code_percent, Unset):
            profile_string_valid_currency_code_percent = (
                self.profile_string_valid_currency_code_percent.to_dict()
            )

        profile_string_invalid_email_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_invalid_email_count, Unset):
            profile_string_invalid_email_count = (
                self.profile_string_invalid_email_count.to_dict()
            )

        profile_string_invalid_uuid_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_invalid_uuid_count, Unset):
            profile_string_invalid_uuid_count = (
                self.profile_string_invalid_uuid_count.to_dict()
            )

        profile_string_valid_uuid_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_valid_uuid_percent, Unset):
            profile_string_valid_uuid_percent = (
                self.profile_string_valid_uuid_percent.to_dict()
            )

        profile_string_invalid_ip4_address_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_invalid_ip4_address_count, Unset):
            profile_string_invalid_ip4_address_count = (
                self.profile_string_invalid_ip4_address_count.to_dict()
            )

        profile_string_invalid_ip6_address_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_invalid_ip6_address_count, Unset):
            profile_string_invalid_ip6_address_count = (
                self.profile_string_invalid_ip6_address_count.to_dict()
            )

        profile_string_not_match_regex_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_not_match_regex_count, Unset):
            profile_string_not_match_regex_count = (
                self.profile_string_not_match_regex_count.to_dict()
            )

        profile_string_match_regex_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_match_regex_percent, Unset):
            profile_string_match_regex_percent = (
                self.profile_string_match_regex_percent.to_dict()
            )

        profile_string_not_match_date_regex_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_not_match_date_regex_count, Unset):
            profile_string_not_match_date_regex_count = (
                self.profile_string_not_match_date_regex_count.to_dict()
            )

        profile_string_match_date_regex_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_match_date_regex_percent, Unset):
            profile_string_match_date_regex_percent = (
                self.profile_string_match_date_regex_percent.to_dict()
            )

        profile_string_match_name_regex_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_match_name_regex_percent, Unset):
            profile_string_match_name_regex_percent = (
                self.profile_string_match_name_regex_percent.to_dict()
            )

        profile_expected_strings_in_top_values_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_expected_strings_in_top_values_count, Unset):
            profile_expected_strings_in_top_values_count = (
                self.profile_expected_strings_in_top_values_count.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_string_max_length is not UNSET:
            field_dict["profile_string_max_length"] = profile_string_max_length
        if profile_string_min_length is not UNSET:
            field_dict["profile_string_min_length"] = profile_string_min_length
        if profile_string_mean_length is not UNSET:
            field_dict["profile_string_mean_length"] = profile_string_mean_length
        if profile_string_length_below_min_length_count is not UNSET:
            field_dict[
                "profile_string_length_below_min_length_count"
            ] = profile_string_length_below_min_length_count
        if profile_string_length_below_min_length_percent is not UNSET:
            field_dict[
                "profile_string_length_below_min_length_percent"
            ] = profile_string_length_below_min_length_percent
        if profile_string_length_above_max_length_count is not UNSET:
            field_dict[
                "profile_string_length_above_max_length_count"
            ] = profile_string_length_above_max_length_count
        if profile_string_length_above_max_length_percent is not UNSET:
            field_dict[
                "profile_string_length_above_max_length_percent"
            ] = profile_string_length_above_max_length_percent
        if profile_string_length_in_range_percent is not UNSET:
            field_dict[
                "profile_string_length_in_range_percent"
            ] = profile_string_length_in_range_percent
        if profile_string_empty_count is not UNSET:
            field_dict["profile_string_empty_count"] = profile_string_empty_count
        if profile_string_empty_percent is not UNSET:
            field_dict["profile_string_empty_percent"] = profile_string_empty_percent
        if profile_string_whitespace_count is not UNSET:
            field_dict[
                "profile_string_whitespace_count"
            ] = profile_string_whitespace_count
        if profile_string_whitespace_percent is not UNSET:
            field_dict[
                "profile_string_whitespace_percent"
            ] = profile_string_whitespace_percent
        if profile_string_surrounded_by_whitespace_count is not UNSET:
            field_dict[
                "profile_string_surrounded_by_whitespace_count"
            ] = profile_string_surrounded_by_whitespace_count
        if profile_string_surrounded_by_whitespace_percent is not UNSET:
            field_dict[
                "profile_string_surrounded_by_whitespace_percent"
            ] = profile_string_surrounded_by_whitespace_percent
        if profile_string_null_placeholder_count is not UNSET:
            field_dict[
                "profile_string_null_placeholder_count"
            ] = profile_string_null_placeholder_count
        if profile_string_null_placeholder_percent is not UNSET:
            field_dict[
                "profile_string_null_placeholder_percent"
            ] = profile_string_null_placeholder_percent
        if profile_string_boolean_placeholder_percent is not UNSET:
            field_dict[
                "profile_string_boolean_placeholder_percent"
            ] = profile_string_boolean_placeholder_percent
        if profile_string_parsable_to_integer_percent is not UNSET:
            field_dict[
                "profile_string_parsable_to_integer_percent"
            ] = profile_string_parsable_to_integer_percent
        if profile_string_parsable_to_float_percent is not UNSET:
            field_dict[
                "profile_string_parsable_to_float_percent"
            ] = profile_string_parsable_to_float_percent
        if profile_expected_strings_in_use_count is not UNSET:
            field_dict[
                "profile_expected_strings_in_use_count"
            ] = profile_expected_strings_in_use_count
        if profile_string_value_in_set_percent is not UNSET:
            field_dict[
                "profile_string_value_in_set_percent"
            ] = profile_string_value_in_set_percent
        if profile_string_valid_dates_percent is not UNSET:
            field_dict[
                "profile_string_valid_dates_percent"
            ] = profile_string_valid_dates_percent
        if profile_string_valid_country_code_percent is not UNSET:
            field_dict[
                "profile_string_valid_country_code_percent"
            ] = profile_string_valid_country_code_percent
        if profile_string_valid_currency_code_percent is not UNSET:
            field_dict[
                "profile_string_valid_currency_code_percent"
            ] = profile_string_valid_currency_code_percent
        if profile_string_invalid_email_count is not UNSET:
            field_dict[
                "profile_string_invalid_email_count"
            ] = profile_string_invalid_email_count
        if profile_string_invalid_uuid_count is not UNSET:
            field_dict[
                "profile_string_invalid_uuid_count"
            ] = profile_string_invalid_uuid_count
        if profile_string_valid_uuid_percent is not UNSET:
            field_dict[
                "profile_string_valid_uuid_percent"
            ] = profile_string_valid_uuid_percent
        if profile_string_invalid_ip4_address_count is not UNSET:
            field_dict[
                "profile_string_invalid_ip4_address_count"
            ] = profile_string_invalid_ip4_address_count
        if profile_string_invalid_ip6_address_count is not UNSET:
            field_dict[
                "profile_string_invalid_ip6_address_count"
            ] = profile_string_invalid_ip6_address_count
        if profile_string_not_match_regex_count is not UNSET:
            field_dict[
                "profile_string_not_match_regex_count"
            ] = profile_string_not_match_regex_count
        if profile_string_match_regex_percent is not UNSET:
            field_dict[
                "profile_string_match_regex_percent"
            ] = profile_string_match_regex_percent
        if profile_string_not_match_date_regex_count is not UNSET:
            field_dict[
                "profile_string_not_match_date_regex_count"
            ] = profile_string_not_match_date_regex_count
        if profile_string_match_date_regex_percent is not UNSET:
            field_dict[
                "profile_string_match_date_regex_percent"
            ] = profile_string_match_date_regex_percent
        if profile_string_match_name_regex_percent is not UNSET:
            field_dict[
                "profile_string_match_name_regex_percent"
            ] = profile_string_match_name_regex_percent
        if profile_expected_strings_in_top_values_count is not UNSET:
            field_dict[
                "profile_expected_strings_in_top_values_count"
            ] = profile_expected_strings_in_top_values_count

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
        from ..models.column_strings_profiling_checks_spec_custom_checks import (
            ColumnStringsProfilingChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnStringsProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnStringsProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_string_max_length = d.pop("profile_string_max_length", UNSET)
        profile_string_max_length: Union[Unset, ColumnStringMaxLengthCheckSpec]
        if isinstance(_profile_string_max_length, Unset):
            profile_string_max_length = UNSET
        else:
            profile_string_max_length = ColumnStringMaxLengthCheckSpec.from_dict(
                _profile_string_max_length
            )

        _profile_string_min_length = d.pop("profile_string_min_length", UNSET)
        profile_string_min_length: Union[Unset, ColumnStringMinLengthCheckSpec]
        if isinstance(_profile_string_min_length, Unset):
            profile_string_min_length = UNSET
        else:
            profile_string_min_length = ColumnStringMinLengthCheckSpec.from_dict(
                _profile_string_min_length
            )

        _profile_string_mean_length = d.pop("profile_string_mean_length", UNSET)
        profile_string_mean_length: Union[Unset, ColumnStringMeanLengthCheckSpec]
        if isinstance(_profile_string_mean_length, Unset):
            profile_string_mean_length = UNSET
        else:
            profile_string_mean_length = ColumnStringMeanLengthCheckSpec.from_dict(
                _profile_string_mean_length
            )

        _profile_string_length_below_min_length_count = d.pop(
            "profile_string_length_below_min_length_count", UNSET
        )
        profile_string_length_below_min_length_count: Union[
            Unset, ColumnStringLengthBelowMinLengthCountCheckSpec
        ]
        if isinstance(_profile_string_length_below_min_length_count, Unset):
            profile_string_length_below_min_length_count = UNSET
        else:
            profile_string_length_below_min_length_count = (
                ColumnStringLengthBelowMinLengthCountCheckSpec.from_dict(
                    _profile_string_length_below_min_length_count
                )
            )

        _profile_string_length_below_min_length_percent = d.pop(
            "profile_string_length_below_min_length_percent", UNSET
        )
        profile_string_length_below_min_length_percent: Union[
            Unset, ColumnStringLengthBelowMinLengthPercentCheckSpec
        ]
        if isinstance(_profile_string_length_below_min_length_percent, Unset):
            profile_string_length_below_min_length_percent = UNSET
        else:
            profile_string_length_below_min_length_percent = (
                ColumnStringLengthBelowMinLengthPercentCheckSpec.from_dict(
                    _profile_string_length_below_min_length_percent
                )
            )

        _profile_string_length_above_max_length_count = d.pop(
            "profile_string_length_above_max_length_count", UNSET
        )
        profile_string_length_above_max_length_count: Union[
            Unset, ColumnStringLengthAboveMaxLengthCountCheckSpec
        ]
        if isinstance(_profile_string_length_above_max_length_count, Unset):
            profile_string_length_above_max_length_count = UNSET
        else:
            profile_string_length_above_max_length_count = (
                ColumnStringLengthAboveMaxLengthCountCheckSpec.from_dict(
                    _profile_string_length_above_max_length_count
                )
            )

        _profile_string_length_above_max_length_percent = d.pop(
            "profile_string_length_above_max_length_percent", UNSET
        )
        profile_string_length_above_max_length_percent: Union[
            Unset, ColumnStringLengthAboveMaxLengthPercentCheckSpec
        ]
        if isinstance(_profile_string_length_above_max_length_percent, Unset):
            profile_string_length_above_max_length_percent = UNSET
        else:
            profile_string_length_above_max_length_percent = (
                ColumnStringLengthAboveMaxLengthPercentCheckSpec.from_dict(
                    _profile_string_length_above_max_length_percent
                )
            )

        _profile_string_length_in_range_percent = d.pop(
            "profile_string_length_in_range_percent", UNSET
        )
        profile_string_length_in_range_percent: Union[
            Unset, ColumnStringLengthInRangePercentCheckSpec
        ]
        if isinstance(_profile_string_length_in_range_percent, Unset):
            profile_string_length_in_range_percent = UNSET
        else:
            profile_string_length_in_range_percent = (
                ColumnStringLengthInRangePercentCheckSpec.from_dict(
                    _profile_string_length_in_range_percent
                )
            )

        _profile_string_empty_count = d.pop("profile_string_empty_count", UNSET)
        profile_string_empty_count: Union[Unset, ColumnStringEmptyCountCheckSpec]
        if isinstance(_profile_string_empty_count, Unset):
            profile_string_empty_count = UNSET
        else:
            profile_string_empty_count = ColumnStringEmptyCountCheckSpec.from_dict(
                _profile_string_empty_count
            )

        _profile_string_empty_percent = d.pop("profile_string_empty_percent", UNSET)
        profile_string_empty_percent: Union[Unset, ColumnStringEmptyPercentCheckSpec]
        if isinstance(_profile_string_empty_percent, Unset):
            profile_string_empty_percent = UNSET
        else:
            profile_string_empty_percent = ColumnStringEmptyPercentCheckSpec.from_dict(
                _profile_string_empty_percent
            )

        _profile_string_whitespace_count = d.pop(
            "profile_string_whitespace_count", UNSET
        )
        profile_string_whitespace_count: Union[
            Unset, ColumnStringWhitespaceCountCheckSpec
        ]
        if isinstance(_profile_string_whitespace_count, Unset):
            profile_string_whitespace_count = UNSET
        else:
            profile_string_whitespace_count = (
                ColumnStringWhitespaceCountCheckSpec.from_dict(
                    _profile_string_whitespace_count
                )
            )

        _profile_string_whitespace_percent = d.pop(
            "profile_string_whitespace_percent", UNSET
        )
        profile_string_whitespace_percent: Union[
            Unset, ColumnStringWhitespacePercentCheckSpec
        ]
        if isinstance(_profile_string_whitespace_percent, Unset):
            profile_string_whitespace_percent = UNSET
        else:
            profile_string_whitespace_percent = (
                ColumnStringWhitespacePercentCheckSpec.from_dict(
                    _profile_string_whitespace_percent
                )
            )

        _profile_string_surrounded_by_whitespace_count = d.pop(
            "profile_string_surrounded_by_whitespace_count", UNSET
        )
        profile_string_surrounded_by_whitespace_count: Union[
            Unset, ColumnStringSurroundedByWhitespaceCountCheckSpec
        ]
        if isinstance(_profile_string_surrounded_by_whitespace_count, Unset):
            profile_string_surrounded_by_whitespace_count = UNSET
        else:
            profile_string_surrounded_by_whitespace_count = (
                ColumnStringSurroundedByWhitespaceCountCheckSpec.from_dict(
                    _profile_string_surrounded_by_whitespace_count
                )
            )

        _profile_string_surrounded_by_whitespace_percent = d.pop(
            "profile_string_surrounded_by_whitespace_percent", UNSET
        )
        profile_string_surrounded_by_whitespace_percent: Union[
            Unset, ColumnStringSurroundedByWhitespacePercentCheckSpec
        ]
        if isinstance(_profile_string_surrounded_by_whitespace_percent, Unset):
            profile_string_surrounded_by_whitespace_percent = UNSET
        else:
            profile_string_surrounded_by_whitespace_percent = (
                ColumnStringSurroundedByWhitespacePercentCheckSpec.from_dict(
                    _profile_string_surrounded_by_whitespace_percent
                )
            )

        _profile_string_null_placeholder_count = d.pop(
            "profile_string_null_placeholder_count", UNSET
        )
        profile_string_null_placeholder_count: Union[
            Unset, ColumnStringNullPlaceholderCountCheckSpec
        ]
        if isinstance(_profile_string_null_placeholder_count, Unset):
            profile_string_null_placeholder_count = UNSET
        else:
            profile_string_null_placeholder_count = (
                ColumnStringNullPlaceholderCountCheckSpec.from_dict(
                    _profile_string_null_placeholder_count
                )
            )

        _profile_string_null_placeholder_percent = d.pop(
            "profile_string_null_placeholder_percent", UNSET
        )
        profile_string_null_placeholder_percent: Union[
            Unset, ColumnStringNullPlaceholderPercentCheckSpec
        ]
        if isinstance(_profile_string_null_placeholder_percent, Unset):
            profile_string_null_placeholder_percent = UNSET
        else:
            profile_string_null_placeholder_percent = (
                ColumnStringNullPlaceholderPercentCheckSpec.from_dict(
                    _profile_string_null_placeholder_percent
                )
            )

        _profile_string_boolean_placeholder_percent = d.pop(
            "profile_string_boolean_placeholder_percent", UNSET
        )
        profile_string_boolean_placeholder_percent: Union[
            Unset, ColumnStringBooleanPlaceholderPercentCheckSpec
        ]
        if isinstance(_profile_string_boolean_placeholder_percent, Unset):
            profile_string_boolean_placeholder_percent = UNSET
        else:
            profile_string_boolean_placeholder_percent = (
                ColumnStringBooleanPlaceholderPercentCheckSpec.from_dict(
                    _profile_string_boolean_placeholder_percent
                )
            )

        _profile_string_parsable_to_integer_percent = d.pop(
            "profile_string_parsable_to_integer_percent", UNSET
        )
        profile_string_parsable_to_integer_percent: Union[
            Unset, ColumnStringParsableToIntegerPercentCheckSpec
        ]
        if isinstance(_profile_string_parsable_to_integer_percent, Unset):
            profile_string_parsable_to_integer_percent = UNSET
        else:
            profile_string_parsable_to_integer_percent = (
                ColumnStringParsableToIntegerPercentCheckSpec.from_dict(
                    _profile_string_parsable_to_integer_percent
                )
            )

        _profile_string_parsable_to_float_percent = d.pop(
            "profile_string_parsable_to_float_percent", UNSET
        )
        profile_string_parsable_to_float_percent: Union[
            Unset, ColumnStringParsableToFloatPercentCheckSpec
        ]
        if isinstance(_profile_string_parsable_to_float_percent, Unset):
            profile_string_parsable_to_float_percent = UNSET
        else:
            profile_string_parsable_to_float_percent = (
                ColumnStringParsableToFloatPercentCheckSpec.from_dict(
                    _profile_string_parsable_to_float_percent
                )
            )

        _profile_expected_strings_in_use_count = d.pop(
            "profile_expected_strings_in_use_count", UNSET
        )
        profile_expected_strings_in_use_count: Union[
            Unset, ColumnExpectedStringsInUseCountCheckSpec
        ]
        if isinstance(_profile_expected_strings_in_use_count, Unset):
            profile_expected_strings_in_use_count = UNSET
        else:
            profile_expected_strings_in_use_count = (
                ColumnExpectedStringsInUseCountCheckSpec.from_dict(
                    _profile_expected_strings_in_use_count
                )
            )

        _profile_string_value_in_set_percent = d.pop(
            "profile_string_value_in_set_percent", UNSET
        )
        profile_string_value_in_set_percent: Union[
            Unset, ColumnStringValueInSetPercentCheckSpec
        ]
        if isinstance(_profile_string_value_in_set_percent, Unset):
            profile_string_value_in_set_percent = UNSET
        else:
            profile_string_value_in_set_percent = (
                ColumnStringValueInSetPercentCheckSpec.from_dict(
                    _profile_string_value_in_set_percent
                )
            )

        _profile_string_valid_dates_percent = d.pop(
            "profile_string_valid_dates_percent", UNSET
        )
        profile_string_valid_dates_percent: Union[
            Unset, ColumnStringValidDatesPercentCheckSpec
        ]
        if isinstance(_profile_string_valid_dates_percent, Unset):
            profile_string_valid_dates_percent = UNSET
        else:
            profile_string_valid_dates_percent = (
                ColumnStringValidDatesPercentCheckSpec.from_dict(
                    _profile_string_valid_dates_percent
                )
            )

        _profile_string_valid_country_code_percent = d.pop(
            "profile_string_valid_country_code_percent", UNSET
        )
        profile_string_valid_country_code_percent: Union[
            Unset, ColumnStringValidCountryCodePercentCheckSpec
        ]
        if isinstance(_profile_string_valid_country_code_percent, Unset):
            profile_string_valid_country_code_percent = UNSET
        else:
            profile_string_valid_country_code_percent = (
                ColumnStringValidCountryCodePercentCheckSpec.from_dict(
                    _profile_string_valid_country_code_percent
                )
            )

        _profile_string_valid_currency_code_percent = d.pop(
            "profile_string_valid_currency_code_percent", UNSET
        )
        profile_string_valid_currency_code_percent: Union[
            Unset, ColumnStringValidCurrencyCodePercentCheckSpec
        ]
        if isinstance(_profile_string_valid_currency_code_percent, Unset):
            profile_string_valid_currency_code_percent = UNSET
        else:
            profile_string_valid_currency_code_percent = (
                ColumnStringValidCurrencyCodePercentCheckSpec.from_dict(
                    _profile_string_valid_currency_code_percent
                )
            )

        _profile_string_invalid_email_count = d.pop(
            "profile_string_invalid_email_count", UNSET
        )
        profile_string_invalid_email_count: Union[
            Unset, ColumnStringInvalidEmailCountCheckSpec
        ]
        if isinstance(_profile_string_invalid_email_count, Unset):
            profile_string_invalid_email_count = UNSET
        else:
            profile_string_invalid_email_count = (
                ColumnStringInvalidEmailCountCheckSpec.from_dict(
                    _profile_string_invalid_email_count
                )
            )

        _profile_string_invalid_uuid_count = d.pop(
            "profile_string_invalid_uuid_count", UNSET
        )
        profile_string_invalid_uuid_count: Union[
            Unset, ColumnStringInvalidUuidCountCheckSpec
        ]
        if isinstance(_profile_string_invalid_uuid_count, Unset):
            profile_string_invalid_uuid_count = UNSET
        else:
            profile_string_invalid_uuid_count = (
                ColumnStringInvalidUuidCountCheckSpec.from_dict(
                    _profile_string_invalid_uuid_count
                )
            )

        _profile_string_valid_uuid_percent = d.pop(
            "profile_string_valid_uuid_percent", UNSET
        )
        profile_string_valid_uuid_percent: Union[
            Unset, ColumnStringValidUuidPercentCheckSpec
        ]
        if isinstance(_profile_string_valid_uuid_percent, Unset):
            profile_string_valid_uuid_percent = UNSET
        else:
            profile_string_valid_uuid_percent = (
                ColumnStringValidUuidPercentCheckSpec.from_dict(
                    _profile_string_valid_uuid_percent
                )
            )

        _profile_string_invalid_ip4_address_count = d.pop(
            "profile_string_invalid_ip4_address_count", UNSET
        )
        profile_string_invalid_ip4_address_count: Union[
            Unset, ColumnStringInvalidIp4AddressCountCheckSpec
        ]
        if isinstance(_profile_string_invalid_ip4_address_count, Unset):
            profile_string_invalid_ip4_address_count = UNSET
        else:
            profile_string_invalid_ip4_address_count = (
                ColumnStringInvalidIp4AddressCountCheckSpec.from_dict(
                    _profile_string_invalid_ip4_address_count
                )
            )

        _profile_string_invalid_ip6_address_count = d.pop(
            "profile_string_invalid_ip6_address_count", UNSET
        )
        profile_string_invalid_ip6_address_count: Union[
            Unset, ColumnStringInvalidIp6AddressCountCheckSpec
        ]
        if isinstance(_profile_string_invalid_ip6_address_count, Unset):
            profile_string_invalid_ip6_address_count = UNSET
        else:
            profile_string_invalid_ip6_address_count = (
                ColumnStringInvalidIp6AddressCountCheckSpec.from_dict(
                    _profile_string_invalid_ip6_address_count
                )
            )

        _profile_string_not_match_regex_count = d.pop(
            "profile_string_not_match_regex_count", UNSET
        )
        profile_string_not_match_regex_count: Union[
            Unset, ColumnStringNotMatchRegexCountCheckSpec
        ]
        if isinstance(_profile_string_not_match_regex_count, Unset):
            profile_string_not_match_regex_count = UNSET
        else:
            profile_string_not_match_regex_count = (
                ColumnStringNotMatchRegexCountCheckSpec.from_dict(
                    _profile_string_not_match_regex_count
                )
            )

        _profile_string_match_regex_percent = d.pop(
            "profile_string_match_regex_percent", UNSET
        )
        profile_string_match_regex_percent: Union[
            Unset, ColumnStringMatchRegexPercentCheckSpec
        ]
        if isinstance(_profile_string_match_regex_percent, Unset):
            profile_string_match_regex_percent = UNSET
        else:
            profile_string_match_regex_percent = (
                ColumnStringMatchRegexPercentCheckSpec.from_dict(
                    _profile_string_match_regex_percent
                )
            )

        _profile_string_not_match_date_regex_count = d.pop(
            "profile_string_not_match_date_regex_count", UNSET
        )
        profile_string_not_match_date_regex_count: Union[
            Unset, ColumnStringNotMatchDateRegexCountCheckSpec
        ]
        if isinstance(_profile_string_not_match_date_regex_count, Unset):
            profile_string_not_match_date_regex_count = UNSET
        else:
            profile_string_not_match_date_regex_count = (
                ColumnStringNotMatchDateRegexCountCheckSpec.from_dict(
                    _profile_string_not_match_date_regex_count
                )
            )

        _profile_string_match_date_regex_percent = d.pop(
            "profile_string_match_date_regex_percent", UNSET
        )
        profile_string_match_date_regex_percent: Union[
            Unset, ColumnStringMatchDateRegexPercentCheckSpec
        ]
        if isinstance(_profile_string_match_date_regex_percent, Unset):
            profile_string_match_date_regex_percent = UNSET
        else:
            profile_string_match_date_regex_percent = (
                ColumnStringMatchDateRegexPercentCheckSpec.from_dict(
                    _profile_string_match_date_regex_percent
                )
            )

        _profile_string_match_name_regex_percent = d.pop(
            "profile_string_match_name_regex_percent", UNSET
        )
        profile_string_match_name_regex_percent: Union[
            Unset, ColumnStringMatchNameRegexPercentCheckSpec
        ]
        if isinstance(_profile_string_match_name_regex_percent, Unset):
            profile_string_match_name_regex_percent = UNSET
        else:
            profile_string_match_name_regex_percent = (
                ColumnStringMatchNameRegexPercentCheckSpec.from_dict(
                    _profile_string_match_name_regex_percent
                )
            )

        _profile_expected_strings_in_top_values_count = d.pop(
            "profile_expected_strings_in_top_values_count", UNSET
        )
        profile_expected_strings_in_top_values_count: Union[
            Unset, ColumnExpectedStringsInTopValuesCountCheckSpec
        ]
        if isinstance(_profile_expected_strings_in_top_values_count, Unset):
            profile_expected_strings_in_top_values_count = UNSET
        else:
            profile_expected_strings_in_top_values_count = (
                ColumnExpectedStringsInTopValuesCountCheckSpec.from_dict(
                    _profile_expected_strings_in_top_values_count
                )
            )

        column_strings_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_string_max_length=profile_string_max_length,
            profile_string_min_length=profile_string_min_length,
            profile_string_mean_length=profile_string_mean_length,
            profile_string_length_below_min_length_count=profile_string_length_below_min_length_count,
            profile_string_length_below_min_length_percent=profile_string_length_below_min_length_percent,
            profile_string_length_above_max_length_count=profile_string_length_above_max_length_count,
            profile_string_length_above_max_length_percent=profile_string_length_above_max_length_percent,
            profile_string_length_in_range_percent=profile_string_length_in_range_percent,
            profile_string_empty_count=profile_string_empty_count,
            profile_string_empty_percent=profile_string_empty_percent,
            profile_string_whitespace_count=profile_string_whitespace_count,
            profile_string_whitespace_percent=profile_string_whitespace_percent,
            profile_string_surrounded_by_whitespace_count=profile_string_surrounded_by_whitespace_count,
            profile_string_surrounded_by_whitespace_percent=profile_string_surrounded_by_whitespace_percent,
            profile_string_null_placeholder_count=profile_string_null_placeholder_count,
            profile_string_null_placeholder_percent=profile_string_null_placeholder_percent,
            profile_string_boolean_placeholder_percent=profile_string_boolean_placeholder_percent,
            profile_string_parsable_to_integer_percent=profile_string_parsable_to_integer_percent,
            profile_string_parsable_to_float_percent=profile_string_parsable_to_float_percent,
            profile_expected_strings_in_use_count=profile_expected_strings_in_use_count,
            profile_string_value_in_set_percent=profile_string_value_in_set_percent,
            profile_string_valid_dates_percent=profile_string_valid_dates_percent,
            profile_string_valid_country_code_percent=profile_string_valid_country_code_percent,
            profile_string_valid_currency_code_percent=profile_string_valid_currency_code_percent,
            profile_string_invalid_email_count=profile_string_invalid_email_count,
            profile_string_invalid_uuid_count=profile_string_invalid_uuid_count,
            profile_string_valid_uuid_percent=profile_string_valid_uuid_percent,
            profile_string_invalid_ip4_address_count=profile_string_invalid_ip4_address_count,
            profile_string_invalid_ip6_address_count=profile_string_invalid_ip6_address_count,
            profile_string_not_match_regex_count=profile_string_not_match_regex_count,
            profile_string_match_regex_percent=profile_string_match_regex_percent,
            profile_string_not_match_date_regex_count=profile_string_not_match_date_regex_count,
            profile_string_match_date_regex_percent=profile_string_match_date_regex_percent,
            profile_string_match_name_regex_percent=profile_string_match_name_regex_percent,
            profile_expected_strings_in_top_values_count=profile_expected_strings_in_top_values_count,
        )

        column_strings_profiling_checks_spec.additional_properties = d
        return column_strings_profiling_checks_spec

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
