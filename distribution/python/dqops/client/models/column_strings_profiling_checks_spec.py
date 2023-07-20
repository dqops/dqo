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
    from ..models.column_string_datatype_detected_check_spec import (
        ColumnStringDatatypeDetectedCheckSpec,
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


T = TypeVar("T", bound="ColumnStringsProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnStringsProfilingChecksSpec:
    """
    Attributes:
        string_max_length (Union[Unset, ColumnStringMaxLengthCheckSpec]):
        string_min_length (Union[Unset, ColumnStringMinLengthCheckSpec]):
        string_mean_length (Union[Unset, ColumnStringMeanLengthCheckSpec]):
        string_length_below_min_length_count (Union[Unset, ColumnStringLengthBelowMinLengthCountCheckSpec]):
        string_length_below_min_length_percent (Union[Unset, ColumnStringLengthBelowMinLengthPercentCheckSpec]):
        string_length_above_max_length_count (Union[Unset, ColumnStringLengthAboveMaxLengthCountCheckSpec]):
        string_length_above_max_length_percent (Union[Unset, ColumnStringLengthAboveMaxLengthPercentCheckSpec]):
        string_length_in_range_percent (Union[Unset, ColumnStringLengthInRangePercentCheckSpec]):
        string_empty_count (Union[Unset, ColumnStringEmptyCountCheckSpec]):
        string_empty_percent (Union[Unset, ColumnStringEmptyPercentCheckSpec]):
        string_whitespace_count (Union[Unset, ColumnStringWhitespaceCountCheckSpec]):
        string_whitespace_percent (Union[Unset, ColumnStringWhitespacePercentCheckSpec]):
        string_surrounded_by_whitespace_count (Union[Unset, ColumnStringSurroundedByWhitespaceCountCheckSpec]):
        string_surrounded_by_whitespace_percent (Union[Unset, ColumnStringSurroundedByWhitespacePercentCheckSpec]):
        string_null_placeholder_count (Union[Unset, ColumnStringNullPlaceholderCountCheckSpec]):
        string_null_placeholder_percent (Union[Unset, ColumnStringNullPlaceholderPercentCheckSpec]):
        string_boolean_placeholder_percent (Union[Unset, ColumnStringBooleanPlaceholderPercentCheckSpec]):
        string_parsable_to_integer_percent (Union[Unset, ColumnStringParsableToIntegerPercentCheckSpec]):
        string_parsable_to_float_percent (Union[Unset, ColumnStringParsableToFloatPercentCheckSpec]):
        expected_strings_in_use_count (Union[Unset, ColumnExpectedStringsInUseCountCheckSpec]):
        string_value_in_set_percent (Union[Unset, ColumnStringValueInSetPercentCheckSpec]):
        string_valid_dates_percent (Union[Unset, ColumnStringValidDatesPercentCheckSpec]):
        string_valid_country_code_percent (Union[Unset, ColumnStringValidCountryCodePercentCheckSpec]):
        string_valid_currency_code_percent (Union[Unset, ColumnStringValidCurrencyCodePercentCheckSpec]):
        string_invalid_email_count (Union[Unset, ColumnStringInvalidEmailCountCheckSpec]):
        string_invalid_uuid_count (Union[Unset, ColumnStringInvalidUuidCountCheckSpec]):
        string_valid_uuid_percent (Union[Unset, ColumnStringValidUuidPercentCheckSpec]):
        string_invalid_ip4_address_count (Union[Unset, ColumnStringInvalidIp4AddressCountCheckSpec]):
        string_invalid_ip6_address_count (Union[Unset, ColumnStringInvalidIp6AddressCountCheckSpec]):
        string_not_match_regex_count (Union[Unset, ColumnStringNotMatchRegexCountCheckSpec]):
        string_match_regex_percent (Union[Unset, ColumnStringMatchRegexPercentCheckSpec]):
        string_not_match_date_regex_count (Union[Unset, ColumnStringNotMatchDateRegexCountCheckSpec]):
        string_match_date_regex_percent (Union[Unset, ColumnStringMatchDateRegexPercentCheckSpec]):
        string_match_name_regex_percent (Union[Unset, ColumnStringMatchNameRegexPercentCheckSpec]):
        expected_strings_in_top_values_count (Union[Unset, ColumnExpectedStringsInTopValuesCountCheckSpec]):
        string_datatype_detected (Union[Unset, ColumnStringDatatypeDetectedCheckSpec]):
    """

    string_max_length: Union[Unset, "ColumnStringMaxLengthCheckSpec"] = UNSET
    string_min_length: Union[Unset, "ColumnStringMinLengthCheckSpec"] = UNSET
    string_mean_length: Union[Unset, "ColumnStringMeanLengthCheckSpec"] = UNSET
    string_length_below_min_length_count: Union[
        Unset, "ColumnStringLengthBelowMinLengthCountCheckSpec"
    ] = UNSET
    string_length_below_min_length_percent: Union[
        Unset, "ColumnStringLengthBelowMinLengthPercentCheckSpec"
    ] = UNSET
    string_length_above_max_length_count: Union[
        Unset, "ColumnStringLengthAboveMaxLengthCountCheckSpec"
    ] = UNSET
    string_length_above_max_length_percent: Union[
        Unset, "ColumnStringLengthAboveMaxLengthPercentCheckSpec"
    ] = UNSET
    string_length_in_range_percent: Union[
        Unset, "ColumnStringLengthInRangePercentCheckSpec"
    ] = UNSET
    string_empty_count: Union[Unset, "ColumnStringEmptyCountCheckSpec"] = UNSET
    string_empty_percent: Union[Unset, "ColumnStringEmptyPercentCheckSpec"] = UNSET
    string_whitespace_count: Union[
        Unset, "ColumnStringWhitespaceCountCheckSpec"
    ] = UNSET
    string_whitespace_percent: Union[
        Unset, "ColumnStringWhitespacePercentCheckSpec"
    ] = UNSET
    string_surrounded_by_whitespace_count: Union[
        Unset, "ColumnStringSurroundedByWhitespaceCountCheckSpec"
    ] = UNSET
    string_surrounded_by_whitespace_percent: Union[
        Unset, "ColumnStringSurroundedByWhitespacePercentCheckSpec"
    ] = UNSET
    string_null_placeholder_count: Union[
        Unset, "ColumnStringNullPlaceholderCountCheckSpec"
    ] = UNSET
    string_null_placeholder_percent: Union[
        Unset, "ColumnStringNullPlaceholderPercentCheckSpec"
    ] = UNSET
    string_boolean_placeholder_percent: Union[
        Unset, "ColumnStringBooleanPlaceholderPercentCheckSpec"
    ] = UNSET
    string_parsable_to_integer_percent: Union[
        Unset, "ColumnStringParsableToIntegerPercentCheckSpec"
    ] = UNSET
    string_parsable_to_float_percent: Union[
        Unset, "ColumnStringParsableToFloatPercentCheckSpec"
    ] = UNSET
    expected_strings_in_use_count: Union[
        Unset, "ColumnExpectedStringsInUseCountCheckSpec"
    ] = UNSET
    string_value_in_set_percent: Union[
        Unset, "ColumnStringValueInSetPercentCheckSpec"
    ] = UNSET
    string_valid_dates_percent: Union[
        Unset, "ColumnStringValidDatesPercentCheckSpec"
    ] = UNSET
    string_valid_country_code_percent: Union[
        Unset, "ColumnStringValidCountryCodePercentCheckSpec"
    ] = UNSET
    string_valid_currency_code_percent: Union[
        Unset, "ColumnStringValidCurrencyCodePercentCheckSpec"
    ] = UNSET
    string_invalid_email_count: Union[
        Unset, "ColumnStringInvalidEmailCountCheckSpec"
    ] = UNSET
    string_invalid_uuid_count: Union[
        Unset, "ColumnStringInvalidUuidCountCheckSpec"
    ] = UNSET
    string_valid_uuid_percent: Union[
        Unset, "ColumnStringValidUuidPercentCheckSpec"
    ] = UNSET
    string_invalid_ip4_address_count: Union[
        Unset, "ColumnStringInvalidIp4AddressCountCheckSpec"
    ] = UNSET
    string_invalid_ip6_address_count: Union[
        Unset, "ColumnStringInvalidIp6AddressCountCheckSpec"
    ] = UNSET
    string_not_match_regex_count: Union[
        Unset, "ColumnStringNotMatchRegexCountCheckSpec"
    ] = UNSET
    string_match_regex_percent: Union[
        Unset, "ColumnStringMatchRegexPercentCheckSpec"
    ] = UNSET
    string_not_match_date_regex_count: Union[
        Unset, "ColumnStringNotMatchDateRegexCountCheckSpec"
    ] = UNSET
    string_match_date_regex_percent: Union[
        Unset, "ColumnStringMatchDateRegexPercentCheckSpec"
    ] = UNSET
    string_match_name_regex_percent: Union[
        Unset, "ColumnStringMatchNameRegexPercentCheckSpec"
    ] = UNSET
    expected_strings_in_top_values_count: Union[
        Unset, "ColumnExpectedStringsInTopValuesCountCheckSpec"
    ] = UNSET
    string_datatype_detected: Union[
        Unset, "ColumnStringDatatypeDetectedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        string_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_max_length, Unset):
            string_max_length = self.string_max_length.to_dict()

        string_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_min_length, Unset):
            string_min_length = self.string_min_length.to_dict()

        string_mean_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_mean_length, Unset):
            string_mean_length = self.string_mean_length.to_dict()

        string_length_below_min_length_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_length_below_min_length_count, Unset):
            string_length_below_min_length_count = (
                self.string_length_below_min_length_count.to_dict()
            )

        string_length_below_min_length_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_length_below_min_length_percent, Unset):
            string_length_below_min_length_percent = (
                self.string_length_below_min_length_percent.to_dict()
            )

        string_length_above_max_length_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_length_above_max_length_count, Unset):
            string_length_above_max_length_count = (
                self.string_length_above_max_length_count.to_dict()
            )

        string_length_above_max_length_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_length_above_max_length_percent, Unset):
            string_length_above_max_length_percent = (
                self.string_length_above_max_length_percent.to_dict()
            )

        string_length_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_length_in_range_percent, Unset):
            string_length_in_range_percent = (
                self.string_length_in_range_percent.to_dict()
            )

        string_empty_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_empty_count, Unset):
            string_empty_count = self.string_empty_count.to_dict()

        string_empty_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_empty_percent, Unset):
            string_empty_percent = self.string_empty_percent.to_dict()

        string_whitespace_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_whitespace_count, Unset):
            string_whitespace_count = self.string_whitespace_count.to_dict()

        string_whitespace_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_whitespace_percent, Unset):
            string_whitespace_percent = self.string_whitespace_percent.to_dict()

        string_surrounded_by_whitespace_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_surrounded_by_whitespace_count, Unset):
            string_surrounded_by_whitespace_count = (
                self.string_surrounded_by_whitespace_count.to_dict()
            )

        string_surrounded_by_whitespace_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_surrounded_by_whitespace_percent, Unset):
            string_surrounded_by_whitespace_percent = (
                self.string_surrounded_by_whitespace_percent.to_dict()
            )

        string_null_placeholder_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_null_placeholder_count, Unset):
            string_null_placeholder_count = self.string_null_placeholder_count.to_dict()

        string_null_placeholder_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_null_placeholder_percent, Unset):
            string_null_placeholder_percent = (
                self.string_null_placeholder_percent.to_dict()
            )

        string_boolean_placeholder_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_boolean_placeholder_percent, Unset):
            string_boolean_placeholder_percent = (
                self.string_boolean_placeholder_percent.to_dict()
            )

        string_parsable_to_integer_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_parsable_to_integer_percent, Unset):
            string_parsable_to_integer_percent = (
                self.string_parsable_to_integer_percent.to_dict()
            )

        string_parsable_to_float_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_parsable_to_float_percent, Unset):
            string_parsable_to_float_percent = (
                self.string_parsable_to_float_percent.to_dict()
            )

        expected_strings_in_use_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.expected_strings_in_use_count, Unset):
            expected_strings_in_use_count = self.expected_strings_in_use_count.to_dict()

        string_value_in_set_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_value_in_set_percent, Unset):
            string_value_in_set_percent = self.string_value_in_set_percent.to_dict()

        string_valid_dates_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_valid_dates_percent, Unset):
            string_valid_dates_percent = self.string_valid_dates_percent.to_dict()

        string_valid_country_code_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_valid_country_code_percent, Unset):
            string_valid_country_code_percent = (
                self.string_valid_country_code_percent.to_dict()
            )

        string_valid_currency_code_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_valid_currency_code_percent, Unset):
            string_valid_currency_code_percent = (
                self.string_valid_currency_code_percent.to_dict()
            )

        string_invalid_email_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_invalid_email_count, Unset):
            string_invalid_email_count = self.string_invalid_email_count.to_dict()

        string_invalid_uuid_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_invalid_uuid_count, Unset):
            string_invalid_uuid_count = self.string_invalid_uuid_count.to_dict()

        string_valid_uuid_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_valid_uuid_percent, Unset):
            string_valid_uuid_percent = self.string_valid_uuid_percent.to_dict()

        string_invalid_ip4_address_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_invalid_ip4_address_count, Unset):
            string_invalid_ip4_address_count = (
                self.string_invalid_ip4_address_count.to_dict()
            )

        string_invalid_ip6_address_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_invalid_ip6_address_count, Unset):
            string_invalid_ip6_address_count = (
                self.string_invalid_ip6_address_count.to_dict()
            )

        string_not_match_regex_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_not_match_regex_count, Unset):
            string_not_match_regex_count = self.string_not_match_regex_count.to_dict()

        string_match_regex_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_match_regex_percent, Unset):
            string_match_regex_percent = self.string_match_regex_percent.to_dict()

        string_not_match_date_regex_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_not_match_date_regex_count, Unset):
            string_not_match_date_regex_count = (
                self.string_not_match_date_regex_count.to_dict()
            )

        string_match_date_regex_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_match_date_regex_percent, Unset):
            string_match_date_regex_percent = (
                self.string_match_date_regex_percent.to_dict()
            )

        string_match_name_regex_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_match_name_regex_percent, Unset):
            string_match_name_regex_percent = (
                self.string_match_name_regex_percent.to_dict()
            )

        expected_strings_in_top_values_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.expected_strings_in_top_values_count, Unset):
            expected_strings_in_top_values_count = (
                self.expected_strings_in_top_values_count.to_dict()
            )

        string_datatype_detected: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_datatype_detected, Unset):
            string_datatype_detected = self.string_datatype_detected.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if string_max_length is not UNSET:
            field_dict["string_max_length"] = string_max_length
        if string_min_length is not UNSET:
            field_dict["string_min_length"] = string_min_length
        if string_mean_length is not UNSET:
            field_dict["string_mean_length"] = string_mean_length
        if string_length_below_min_length_count is not UNSET:
            field_dict[
                "string_length_below_min_length_count"
            ] = string_length_below_min_length_count
        if string_length_below_min_length_percent is not UNSET:
            field_dict[
                "string_length_below_min_length_percent"
            ] = string_length_below_min_length_percent
        if string_length_above_max_length_count is not UNSET:
            field_dict[
                "string_length_above_max_length_count"
            ] = string_length_above_max_length_count
        if string_length_above_max_length_percent is not UNSET:
            field_dict[
                "string_length_above_max_length_percent"
            ] = string_length_above_max_length_percent
        if string_length_in_range_percent is not UNSET:
            field_dict[
                "string_length_in_range_percent"
            ] = string_length_in_range_percent
        if string_empty_count is not UNSET:
            field_dict["string_empty_count"] = string_empty_count
        if string_empty_percent is not UNSET:
            field_dict["string_empty_percent"] = string_empty_percent
        if string_whitespace_count is not UNSET:
            field_dict["string_whitespace_count"] = string_whitespace_count
        if string_whitespace_percent is not UNSET:
            field_dict["string_whitespace_percent"] = string_whitespace_percent
        if string_surrounded_by_whitespace_count is not UNSET:
            field_dict[
                "string_surrounded_by_whitespace_count"
            ] = string_surrounded_by_whitespace_count
        if string_surrounded_by_whitespace_percent is not UNSET:
            field_dict[
                "string_surrounded_by_whitespace_percent"
            ] = string_surrounded_by_whitespace_percent
        if string_null_placeholder_count is not UNSET:
            field_dict["string_null_placeholder_count"] = string_null_placeholder_count
        if string_null_placeholder_percent is not UNSET:
            field_dict[
                "string_null_placeholder_percent"
            ] = string_null_placeholder_percent
        if string_boolean_placeholder_percent is not UNSET:
            field_dict[
                "string_boolean_placeholder_percent"
            ] = string_boolean_placeholder_percent
        if string_parsable_to_integer_percent is not UNSET:
            field_dict[
                "string_parsable_to_integer_percent"
            ] = string_parsable_to_integer_percent
        if string_parsable_to_float_percent is not UNSET:
            field_dict[
                "string_parsable_to_float_percent"
            ] = string_parsable_to_float_percent
        if expected_strings_in_use_count is not UNSET:
            field_dict["expected_strings_in_use_count"] = expected_strings_in_use_count
        if string_value_in_set_percent is not UNSET:
            field_dict["string_value_in_set_percent"] = string_value_in_set_percent
        if string_valid_dates_percent is not UNSET:
            field_dict["string_valid_dates_percent"] = string_valid_dates_percent
        if string_valid_country_code_percent is not UNSET:
            field_dict[
                "string_valid_country_code_percent"
            ] = string_valid_country_code_percent
        if string_valid_currency_code_percent is not UNSET:
            field_dict[
                "string_valid_currency_code_percent"
            ] = string_valid_currency_code_percent
        if string_invalid_email_count is not UNSET:
            field_dict["string_invalid_email_count"] = string_invalid_email_count
        if string_invalid_uuid_count is not UNSET:
            field_dict["string_invalid_uuid_count"] = string_invalid_uuid_count
        if string_valid_uuid_percent is not UNSET:
            field_dict["string_valid_uuid_percent"] = string_valid_uuid_percent
        if string_invalid_ip4_address_count is not UNSET:
            field_dict[
                "string_invalid_ip4_address_count"
            ] = string_invalid_ip4_address_count
        if string_invalid_ip6_address_count is not UNSET:
            field_dict[
                "string_invalid_ip6_address_count"
            ] = string_invalid_ip6_address_count
        if string_not_match_regex_count is not UNSET:
            field_dict["string_not_match_regex_count"] = string_not_match_regex_count
        if string_match_regex_percent is not UNSET:
            field_dict["string_match_regex_percent"] = string_match_regex_percent
        if string_not_match_date_regex_count is not UNSET:
            field_dict[
                "string_not_match_date_regex_count"
            ] = string_not_match_date_regex_count
        if string_match_date_regex_percent is not UNSET:
            field_dict[
                "string_match_date_regex_percent"
            ] = string_match_date_regex_percent
        if string_match_name_regex_percent is not UNSET:
            field_dict[
                "string_match_name_regex_percent"
            ] = string_match_name_regex_percent
        if expected_strings_in_top_values_count is not UNSET:
            field_dict[
                "expected_strings_in_top_values_count"
            ] = expected_strings_in_top_values_count
        if string_datatype_detected is not UNSET:
            field_dict["string_datatype_detected"] = string_datatype_detected

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
        from ..models.column_string_datatype_detected_check_spec import (
            ColumnStringDatatypeDetectedCheckSpec,
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

        d = src_dict.copy()
        _string_max_length = d.pop("string_max_length", UNSET)
        string_max_length: Union[Unset, ColumnStringMaxLengthCheckSpec]
        if isinstance(_string_max_length, Unset):
            string_max_length = UNSET
        else:
            string_max_length = ColumnStringMaxLengthCheckSpec.from_dict(
                _string_max_length
            )

        _string_min_length = d.pop("string_min_length", UNSET)
        string_min_length: Union[Unset, ColumnStringMinLengthCheckSpec]
        if isinstance(_string_min_length, Unset):
            string_min_length = UNSET
        else:
            string_min_length = ColumnStringMinLengthCheckSpec.from_dict(
                _string_min_length
            )

        _string_mean_length = d.pop("string_mean_length", UNSET)
        string_mean_length: Union[Unset, ColumnStringMeanLengthCheckSpec]
        if isinstance(_string_mean_length, Unset):
            string_mean_length = UNSET
        else:
            string_mean_length = ColumnStringMeanLengthCheckSpec.from_dict(
                _string_mean_length
            )

        _string_length_below_min_length_count = d.pop(
            "string_length_below_min_length_count", UNSET
        )
        string_length_below_min_length_count: Union[
            Unset, ColumnStringLengthBelowMinLengthCountCheckSpec
        ]
        if isinstance(_string_length_below_min_length_count, Unset):
            string_length_below_min_length_count = UNSET
        else:
            string_length_below_min_length_count = (
                ColumnStringLengthBelowMinLengthCountCheckSpec.from_dict(
                    _string_length_below_min_length_count
                )
            )

        _string_length_below_min_length_percent = d.pop(
            "string_length_below_min_length_percent", UNSET
        )
        string_length_below_min_length_percent: Union[
            Unset, ColumnStringLengthBelowMinLengthPercentCheckSpec
        ]
        if isinstance(_string_length_below_min_length_percent, Unset):
            string_length_below_min_length_percent = UNSET
        else:
            string_length_below_min_length_percent = (
                ColumnStringLengthBelowMinLengthPercentCheckSpec.from_dict(
                    _string_length_below_min_length_percent
                )
            )

        _string_length_above_max_length_count = d.pop(
            "string_length_above_max_length_count", UNSET
        )
        string_length_above_max_length_count: Union[
            Unset, ColumnStringLengthAboveMaxLengthCountCheckSpec
        ]
        if isinstance(_string_length_above_max_length_count, Unset):
            string_length_above_max_length_count = UNSET
        else:
            string_length_above_max_length_count = (
                ColumnStringLengthAboveMaxLengthCountCheckSpec.from_dict(
                    _string_length_above_max_length_count
                )
            )

        _string_length_above_max_length_percent = d.pop(
            "string_length_above_max_length_percent", UNSET
        )
        string_length_above_max_length_percent: Union[
            Unset, ColumnStringLengthAboveMaxLengthPercentCheckSpec
        ]
        if isinstance(_string_length_above_max_length_percent, Unset):
            string_length_above_max_length_percent = UNSET
        else:
            string_length_above_max_length_percent = (
                ColumnStringLengthAboveMaxLengthPercentCheckSpec.from_dict(
                    _string_length_above_max_length_percent
                )
            )

        _string_length_in_range_percent = d.pop("string_length_in_range_percent", UNSET)
        string_length_in_range_percent: Union[
            Unset, ColumnStringLengthInRangePercentCheckSpec
        ]
        if isinstance(_string_length_in_range_percent, Unset):
            string_length_in_range_percent = UNSET
        else:
            string_length_in_range_percent = (
                ColumnStringLengthInRangePercentCheckSpec.from_dict(
                    _string_length_in_range_percent
                )
            )

        _string_empty_count = d.pop("string_empty_count", UNSET)
        string_empty_count: Union[Unset, ColumnStringEmptyCountCheckSpec]
        if isinstance(_string_empty_count, Unset):
            string_empty_count = UNSET
        else:
            string_empty_count = ColumnStringEmptyCountCheckSpec.from_dict(
                _string_empty_count
            )

        _string_empty_percent = d.pop("string_empty_percent", UNSET)
        string_empty_percent: Union[Unset, ColumnStringEmptyPercentCheckSpec]
        if isinstance(_string_empty_percent, Unset):
            string_empty_percent = UNSET
        else:
            string_empty_percent = ColumnStringEmptyPercentCheckSpec.from_dict(
                _string_empty_percent
            )

        _string_whitespace_count = d.pop("string_whitespace_count", UNSET)
        string_whitespace_count: Union[Unset, ColumnStringWhitespaceCountCheckSpec]
        if isinstance(_string_whitespace_count, Unset):
            string_whitespace_count = UNSET
        else:
            string_whitespace_count = ColumnStringWhitespaceCountCheckSpec.from_dict(
                _string_whitespace_count
            )

        _string_whitespace_percent = d.pop("string_whitespace_percent", UNSET)
        string_whitespace_percent: Union[Unset, ColumnStringWhitespacePercentCheckSpec]
        if isinstance(_string_whitespace_percent, Unset):
            string_whitespace_percent = UNSET
        else:
            string_whitespace_percent = (
                ColumnStringWhitespacePercentCheckSpec.from_dict(
                    _string_whitespace_percent
                )
            )

        _string_surrounded_by_whitespace_count = d.pop(
            "string_surrounded_by_whitespace_count", UNSET
        )
        string_surrounded_by_whitespace_count: Union[
            Unset, ColumnStringSurroundedByWhitespaceCountCheckSpec
        ]
        if isinstance(_string_surrounded_by_whitespace_count, Unset):
            string_surrounded_by_whitespace_count = UNSET
        else:
            string_surrounded_by_whitespace_count = (
                ColumnStringSurroundedByWhitespaceCountCheckSpec.from_dict(
                    _string_surrounded_by_whitespace_count
                )
            )

        _string_surrounded_by_whitespace_percent = d.pop(
            "string_surrounded_by_whitespace_percent", UNSET
        )
        string_surrounded_by_whitespace_percent: Union[
            Unset, ColumnStringSurroundedByWhitespacePercentCheckSpec
        ]
        if isinstance(_string_surrounded_by_whitespace_percent, Unset):
            string_surrounded_by_whitespace_percent = UNSET
        else:
            string_surrounded_by_whitespace_percent = (
                ColumnStringSurroundedByWhitespacePercentCheckSpec.from_dict(
                    _string_surrounded_by_whitespace_percent
                )
            )

        _string_null_placeholder_count = d.pop("string_null_placeholder_count", UNSET)
        string_null_placeholder_count: Union[
            Unset, ColumnStringNullPlaceholderCountCheckSpec
        ]
        if isinstance(_string_null_placeholder_count, Unset):
            string_null_placeholder_count = UNSET
        else:
            string_null_placeholder_count = (
                ColumnStringNullPlaceholderCountCheckSpec.from_dict(
                    _string_null_placeholder_count
                )
            )

        _string_null_placeholder_percent = d.pop(
            "string_null_placeholder_percent", UNSET
        )
        string_null_placeholder_percent: Union[
            Unset, ColumnStringNullPlaceholderPercentCheckSpec
        ]
        if isinstance(_string_null_placeholder_percent, Unset):
            string_null_placeholder_percent = UNSET
        else:
            string_null_placeholder_percent = (
                ColumnStringNullPlaceholderPercentCheckSpec.from_dict(
                    _string_null_placeholder_percent
                )
            )

        _string_boolean_placeholder_percent = d.pop(
            "string_boolean_placeholder_percent", UNSET
        )
        string_boolean_placeholder_percent: Union[
            Unset, ColumnStringBooleanPlaceholderPercentCheckSpec
        ]
        if isinstance(_string_boolean_placeholder_percent, Unset):
            string_boolean_placeholder_percent = UNSET
        else:
            string_boolean_placeholder_percent = (
                ColumnStringBooleanPlaceholderPercentCheckSpec.from_dict(
                    _string_boolean_placeholder_percent
                )
            )

        _string_parsable_to_integer_percent = d.pop(
            "string_parsable_to_integer_percent", UNSET
        )
        string_parsable_to_integer_percent: Union[
            Unset, ColumnStringParsableToIntegerPercentCheckSpec
        ]
        if isinstance(_string_parsable_to_integer_percent, Unset):
            string_parsable_to_integer_percent = UNSET
        else:
            string_parsable_to_integer_percent = (
                ColumnStringParsableToIntegerPercentCheckSpec.from_dict(
                    _string_parsable_to_integer_percent
                )
            )

        _string_parsable_to_float_percent = d.pop(
            "string_parsable_to_float_percent", UNSET
        )
        string_parsable_to_float_percent: Union[
            Unset, ColumnStringParsableToFloatPercentCheckSpec
        ]
        if isinstance(_string_parsable_to_float_percent, Unset):
            string_parsable_to_float_percent = UNSET
        else:
            string_parsable_to_float_percent = (
                ColumnStringParsableToFloatPercentCheckSpec.from_dict(
                    _string_parsable_to_float_percent
                )
            )

        _expected_strings_in_use_count = d.pop("expected_strings_in_use_count", UNSET)
        expected_strings_in_use_count: Union[
            Unset, ColumnExpectedStringsInUseCountCheckSpec
        ]
        if isinstance(_expected_strings_in_use_count, Unset):
            expected_strings_in_use_count = UNSET
        else:
            expected_strings_in_use_count = (
                ColumnExpectedStringsInUseCountCheckSpec.from_dict(
                    _expected_strings_in_use_count
                )
            )

        _string_value_in_set_percent = d.pop("string_value_in_set_percent", UNSET)
        string_value_in_set_percent: Union[
            Unset, ColumnStringValueInSetPercentCheckSpec
        ]
        if isinstance(_string_value_in_set_percent, Unset):
            string_value_in_set_percent = UNSET
        else:
            string_value_in_set_percent = (
                ColumnStringValueInSetPercentCheckSpec.from_dict(
                    _string_value_in_set_percent
                )
            )

        _string_valid_dates_percent = d.pop("string_valid_dates_percent", UNSET)
        string_valid_dates_percent: Union[Unset, ColumnStringValidDatesPercentCheckSpec]
        if isinstance(_string_valid_dates_percent, Unset):
            string_valid_dates_percent = UNSET
        else:
            string_valid_dates_percent = (
                ColumnStringValidDatesPercentCheckSpec.from_dict(
                    _string_valid_dates_percent
                )
            )

        _string_valid_country_code_percent = d.pop(
            "string_valid_country_code_percent", UNSET
        )
        string_valid_country_code_percent: Union[
            Unset, ColumnStringValidCountryCodePercentCheckSpec
        ]
        if isinstance(_string_valid_country_code_percent, Unset):
            string_valid_country_code_percent = UNSET
        else:
            string_valid_country_code_percent = (
                ColumnStringValidCountryCodePercentCheckSpec.from_dict(
                    _string_valid_country_code_percent
                )
            )

        _string_valid_currency_code_percent = d.pop(
            "string_valid_currency_code_percent", UNSET
        )
        string_valid_currency_code_percent: Union[
            Unset, ColumnStringValidCurrencyCodePercentCheckSpec
        ]
        if isinstance(_string_valid_currency_code_percent, Unset):
            string_valid_currency_code_percent = UNSET
        else:
            string_valid_currency_code_percent = (
                ColumnStringValidCurrencyCodePercentCheckSpec.from_dict(
                    _string_valid_currency_code_percent
                )
            )

        _string_invalid_email_count = d.pop("string_invalid_email_count", UNSET)
        string_invalid_email_count: Union[Unset, ColumnStringInvalidEmailCountCheckSpec]
        if isinstance(_string_invalid_email_count, Unset):
            string_invalid_email_count = UNSET
        else:
            string_invalid_email_count = (
                ColumnStringInvalidEmailCountCheckSpec.from_dict(
                    _string_invalid_email_count
                )
            )

        _string_invalid_uuid_count = d.pop("string_invalid_uuid_count", UNSET)
        string_invalid_uuid_count: Union[Unset, ColumnStringInvalidUuidCountCheckSpec]
        if isinstance(_string_invalid_uuid_count, Unset):
            string_invalid_uuid_count = UNSET
        else:
            string_invalid_uuid_count = ColumnStringInvalidUuidCountCheckSpec.from_dict(
                _string_invalid_uuid_count
            )

        _string_valid_uuid_percent = d.pop("string_valid_uuid_percent", UNSET)
        string_valid_uuid_percent: Union[Unset, ColumnStringValidUuidPercentCheckSpec]
        if isinstance(_string_valid_uuid_percent, Unset):
            string_valid_uuid_percent = UNSET
        else:
            string_valid_uuid_percent = ColumnStringValidUuidPercentCheckSpec.from_dict(
                _string_valid_uuid_percent
            )

        _string_invalid_ip4_address_count = d.pop(
            "string_invalid_ip4_address_count", UNSET
        )
        string_invalid_ip4_address_count: Union[
            Unset, ColumnStringInvalidIp4AddressCountCheckSpec
        ]
        if isinstance(_string_invalid_ip4_address_count, Unset):
            string_invalid_ip4_address_count = UNSET
        else:
            string_invalid_ip4_address_count = (
                ColumnStringInvalidIp4AddressCountCheckSpec.from_dict(
                    _string_invalid_ip4_address_count
                )
            )

        _string_invalid_ip6_address_count = d.pop(
            "string_invalid_ip6_address_count", UNSET
        )
        string_invalid_ip6_address_count: Union[
            Unset, ColumnStringInvalidIp6AddressCountCheckSpec
        ]
        if isinstance(_string_invalid_ip6_address_count, Unset):
            string_invalid_ip6_address_count = UNSET
        else:
            string_invalid_ip6_address_count = (
                ColumnStringInvalidIp6AddressCountCheckSpec.from_dict(
                    _string_invalid_ip6_address_count
                )
            )

        _string_not_match_regex_count = d.pop("string_not_match_regex_count", UNSET)
        string_not_match_regex_count: Union[
            Unset, ColumnStringNotMatchRegexCountCheckSpec
        ]
        if isinstance(_string_not_match_regex_count, Unset):
            string_not_match_regex_count = UNSET
        else:
            string_not_match_regex_count = (
                ColumnStringNotMatchRegexCountCheckSpec.from_dict(
                    _string_not_match_regex_count
                )
            )

        _string_match_regex_percent = d.pop("string_match_regex_percent", UNSET)
        string_match_regex_percent: Union[Unset, ColumnStringMatchRegexPercentCheckSpec]
        if isinstance(_string_match_regex_percent, Unset):
            string_match_regex_percent = UNSET
        else:
            string_match_regex_percent = (
                ColumnStringMatchRegexPercentCheckSpec.from_dict(
                    _string_match_regex_percent
                )
            )

        _string_not_match_date_regex_count = d.pop(
            "string_not_match_date_regex_count", UNSET
        )
        string_not_match_date_regex_count: Union[
            Unset, ColumnStringNotMatchDateRegexCountCheckSpec
        ]
        if isinstance(_string_not_match_date_regex_count, Unset):
            string_not_match_date_regex_count = UNSET
        else:
            string_not_match_date_regex_count = (
                ColumnStringNotMatchDateRegexCountCheckSpec.from_dict(
                    _string_not_match_date_regex_count
                )
            )

        _string_match_date_regex_percent = d.pop(
            "string_match_date_regex_percent", UNSET
        )
        string_match_date_regex_percent: Union[
            Unset, ColumnStringMatchDateRegexPercentCheckSpec
        ]
        if isinstance(_string_match_date_regex_percent, Unset):
            string_match_date_regex_percent = UNSET
        else:
            string_match_date_regex_percent = (
                ColumnStringMatchDateRegexPercentCheckSpec.from_dict(
                    _string_match_date_regex_percent
                )
            )

        _string_match_name_regex_percent = d.pop(
            "string_match_name_regex_percent", UNSET
        )
        string_match_name_regex_percent: Union[
            Unset, ColumnStringMatchNameRegexPercentCheckSpec
        ]
        if isinstance(_string_match_name_regex_percent, Unset):
            string_match_name_regex_percent = UNSET
        else:
            string_match_name_regex_percent = (
                ColumnStringMatchNameRegexPercentCheckSpec.from_dict(
                    _string_match_name_regex_percent
                )
            )

        _expected_strings_in_top_values_count = d.pop(
            "expected_strings_in_top_values_count", UNSET
        )
        expected_strings_in_top_values_count: Union[
            Unset, ColumnExpectedStringsInTopValuesCountCheckSpec
        ]
        if isinstance(_expected_strings_in_top_values_count, Unset):
            expected_strings_in_top_values_count = UNSET
        else:
            expected_strings_in_top_values_count = (
                ColumnExpectedStringsInTopValuesCountCheckSpec.from_dict(
                    _expected_strings_in_top_values_count
                )
            )

        _string_datatype_detected = d.pop("string_datatype_detected", UNSET)
        string_datatype_detected: Union[Unset, ColumnStringDatatypeDetectedCheckSpec]
        if isinstance(_string_datatype_detected, Unset):
            string_datatype_detected = UNSET
        else:
            string_datatype_detected = ColumnStringDatatypeDetectedCheckSpec.from_dict(
                _string_datatype_detected
            )

        column_strings_profiling_checks_spec = cls(
            string_max_length=string_max_length,
            string_min_length=string_min_length,
            string_mean_length=string_mean_length,
            string_length_below_min_length_count=string_length_below_min_length_count,
            string_length_below_min_length_percent=string_length_below_min_length_percent,
            string_length_above_max_length_count=string_length_above_max_length_count,
            string_length_above_max_length_percent=string_length_above_max_length_percent,
            string_length_in_range_percent=string_length_in_range_percent,
            string_empty_count=string_empty_count,
            string_empty_percent=string_empty_percent,
            string_whitespace_count=string_whitespace_count,
            string_whitespace_percent=string_whitespace_percent,
            string_surrounded_by_whitespace_count=string_surrounded_by_whitespace_count,
            string_surrounded_by_whitespace_percent=string_surrounded_by_whitespace_percent,
            string_null_placeholder_count=string_null_placeholder_count,
            string_null_placeholder_percent=string_null_placeholder_percent,
            string_boolean_placeholder_percent=string_boolean_placeholder_percent,
            string_parsable_to_integer_percent=string_parsable_to_integer_percent,
            string_parsable_to_float_percent=string_parsable_to_float_percent,
            expected_strings_in_use_count=expected_strings_in_use_count,
            string_value_in_set_percent=string_value_in_set_percent,
            string_valid_dates_percent=string_valid_dates_percent,
            string_valid_country_code_percent=string_valid_country_code_percent,
            string_valid_currency_code_percent=string_valid_currency_code_percent,
            string_invalid_email_count=string_invalid_email_count,
            string_invalid_uuid_count=string_invalid_uuid_count,
            string_valid_uuid_percent=string_valid_uuid_percent,
            string_invalid_ip4_address_count=string_invalid_ip4_address_count,
            string_invalid_ip6_address_count=string_invalid_ip6_address_count,
            string_not_match_regex_count=string_not_match_regex_count,
            string_match_regex_percent=string_match_regex_percent,
            string_not_match_date_regex_count=string_not_match_date_regex_count,
            string_match_date_regex_percent=string_match_date_regex_percent,
            string_match_name_regex_percent=string_match_name_regex_percent,
            expected_strings_in_top_values_count=expected_strings_in_top_values_count,
            string_datatype_detected=string_datatype_detected,
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
