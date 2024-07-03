from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accepted_values_profiling_checks_spec_custom_checks import (
        ColumnAcceptedValuesProfilingChecksSpecCustomChecks,
    )
    from ..models.column_expected_numbers_in_use_count_check_spec import (
        ColumnExpectedNumbersInUseCountCheckSpec,
    )
    from ..models.column_expected_text_values_in_use_count_check_spec import (
        ColumnExpectedTextValuesInUseCountCheckSpec,
    )
    from ..models.column_expected_texts_in_top_values_count_check_spec import (
        ColumnExpectedTextsInTopValuesCountCheckSpec,
    )
    from ..models.column_number_found_in_set_percent_check_spec import (
        ColumnNumberFoundInSetPercentCheckSpec,
    )
    from ..models.column_text_found_in_set_percent_check_spec import (
        ColumnTextFoundInSetPercentCheckSpec,
    )
    from ..models.column_text_valid_country_code_percent_check_spec import (
        ColumnTextValidCountryCodePercentCheckSpec,
    )
    from ..models.column_text_valid_currency_code_percent_check_spec import (
        ColumnTextValidCurrencyCodePercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnAcceptedValuesProfilingChecksSpec")


@_attrs_define
class ColumnAcceptedValuesProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnAcceptedValuesProfilingChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        profile_text_found_in_set_percent (Union[Unset, ColumnTextFoundInSetPercentCheckSpec]):
        profile_number_found_in_set_percent (Union[Unset, ColumnNumberFoundInSetPercentCheckSpec]):
        profile_expected_text_values_in_use_count (Union[Unset, ColumnExpectedTextValuesInUseCountCheckSpec]):
        profile_expected_texts_in_top_values_count (Union[Unset, ColumnExpectedTextsInTopValuesCountCheckSpec]):
        profile_expected_numbers_in_use_count (Union[Unset, ColumnExpectedNumbersInUseCountCheckSpec]):
        profile_text_valid_country_code_percent (Union[Unset, ColumnTextValidCountryCodePercentCheckSpec]):
        profile_text_valid_currency_code_percent (Union[Unset, ColumnTextValidCurrencyCodePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnAcceptedValuesProfilingChecksSpecCustomChecks"
    ] = UNSET
    profile_text_found_in_set_percent: Union[
        Unset, "ColumnTextFoundInSetPercentCheckSpec"
    ] = UNSET
    profile_number_found_in_set_percent: Union[
        Unset, "ColumnNumberFoundInSetPercentCheckSpec"
    ] = UNSET
    profile_expected_text_values_in_use_count: Union[
        Unset, "ColumnExpectedTextValuesInUseCountCheckSpec"
    ] = UNSET
    profile_expected_texts_in_top_values_count: Union[
        Unset, "ColumnExpectedTextsInTopValuesCountCheckSpec"
    ] = UNSET
    profile_expected_numbers_in_use_count: Union[
        Unset, "ColumnExpectedNumbersInUseCountCheckSpec"
    ] = UNSET
    profile_text_valid_country_code_percent: Union[
        Unset, "ColumnTextValidCountryCodePercentCheckSpec"
    ] = UNSET
    profile_text_valid_currency_code_percent: Union[
        Unset, "ColumnTextValidCurrencyCodePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_text_found_in_set_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_text_found_in_set_percent, Unset):
            profile_text_found_in_set_percent = (
                self.profile_text_found_in_set_percent.to_dict()
            )

        profile_number_found_in_set_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_number_found_in_set_percent, Unset):
            profile_number_found_in_set_percent = (
                self.profile_number_found_in_set_percent.to_dict()
            )

        profile_expected_text_values_in_use_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_expected_text_values_in_use_count, Unset):
            profile_expected_text_values_in_use_count = (
                self.profile_expected_text_values_in_use_count.to_dict()
            )

        profile_expected_texts_in_top_values_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_expected_texts_in_top_values_count, Unset):
            profile_expected_texts_in_top_values_count = (
                self.profile_expected_texts_in_top_values_count.to_dict()
            )

        profile_expected_numbers_in_use_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_expected_numbers_in_use_count, Unset):
            profile_expected_numbers_in_use_count = (
                self.profile_expected_numbers_in_use_count.to_dict()
            )

        profile_text_valid_country_code_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_text_valid_country_code_percent, Unset):
            profile_text_valid_country_code_percent = (
                self.profile_text_valid_country_code_percent.to_dict()
            )

        profile_text_valid_currency_code_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_text_valid_currency_code_percent, Unset):
            profile_text_valid_currency_code_percent = (
                self.profile_text_valid_currency_code_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_text_found_in_set_percent is not UNSET:
            field_dict["profile_text_found_in_set_percent"] = (
                profile_text_found_in_set_percent
            )
        if profile_number_found_in_set_percent is not UNSET:
            field_dict["profile_number_found_in_set_percent"] = (
                profile_number_found_in_set_percent
            )
        if profile_expected_text_values_in_use_count is not UNSET:
            field_dict["profile_expected_text_values_in_use_count"] = (
                profile_expected_text_values_in_use_count
            )
        if profile_expected_texts_in_top_values_count is not UNSET:
            field_dict["profile_expected_texts_in_top_values_count"] = (
                profile_expected_texts_in_top_values_count
            )
        if profile_expected_numbers_in_use_count is not UNSET:
            field_dict["profile_expected_numbers_in_use_count"] = (
                profile_expected_numbers_in_use_count
            )
        if profile_text_valid_country_code_percent is not UNSET:
            field_dict["profile_text_valid_country_code_percent"] = (
                profile_text_valid_country_code_percent
            )
        if profile_text_valid_currency_code_percent is not UNSET:
            field_dict["profile_text_valid_currency_code_percent"] = (
                profile_text_valid_currency_code_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_accepted_values_profiling_checks_spec_custom_checks import (
            ColumnAcceptedValuesProfilingChecksSpecCustomChecks,
        )
        from ..models.column_expected_numbers_in_use_count_check_spec import (
            ColumnExpectedNumbersInUseCountCheckSpec,
        )
        from ..models.column_expected_text_values_in_use_count_check_spec import (
            ColumnExpectedTextValuesInUseCountCheckSpec,
        )
        from ..models.column_expected_texts_in_top_values_count_check_spec import (
            ColumnExpectedTextsInTopValuesCountCheckSpec,
        )
        from ..models.column_number_found_in_set_percent_check_spec import (
            ColumnNumberFoundInSetPercentCheckSpec,
        )
        from ..models.column_text_found_in_set_percent_check_spec import (
            ColumnTextFoundInSetPercentCheckSpec,
        )
        from ..models.column_text_valid_country_code_percent_check_spec import (
            ColumnTextValidCountryCodePercentCheckSpec,
        )
        from ..models.column_text_valid_currency_code_percent_check_spec import (
            ColumnTextValidCurrencyCodePercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnAcceptedValuesProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnAcceptedValuesProfilingChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _profile_text_found_in_set_percent = d.pop(
            "profile_text_found_in_set_percent", UNSET
        )
        profile_text_found_in_set_percent: Union[
            Unset, ColumnTextFoundInSetPercentCheckSpec
        ]
        if isinstance(_profile_text_found_in_set_percent, Unset):
            profile_text_found_in_set_percent = UNSET
        else:
            profile_text_found_in_set_percent = (
                ColumnTextFoundInSetPercentCheckSpec.from_dict(
                    _profile_text_found_in_set_percent
                )
            )

        _profile_number_found_in_set_percent = d.pop(
            "profile_number_found_in_set_percent", UNSET
        )
        profile_number_found_in_set_percent: Union[
            Unset, ColumnNumberFoundInSetPercentCheckSpec
        ]
        if isinstance(_profile_number_found_in_set_percent, Unset):
            profile_number_found_in_set_percent = UNSET
        else:
            profile_number_found_in_set_percent = (
                ColumnNumberFoundInSetPercentCheckSpec.from_dict(
                    _profile_number_found_in_set_percent
                )
            )

        _profile_expected_text_values_in_use_count = d.pop(
            "profile_expected_text_values_in_use_count", UNSET
        )
        profile_expected_text_values_in_use_count: Union[
            Unset, ColumnExpectedTextValuesInUseCountCheckSpec
        ]
        if isinstance(_profile_expected_text_values_in_use_count, Unset):
            profile_expected_text_values_in_use_count = UNSET
        else:
            profile_expected_text_values_in_use_count = (
                ColumnExpectedTextValuesInUseCountCheckSpec.from_dict(
                    _profile_expected_text_values_in_use_count
                )
            )

        _profile_expected_texts_in_top_values_count = d.pop(
            "profile_expected_texts_in_top_values_count", UNSET
        )
        profile_expected_texts_in_top_values_count: Union[
            Unset, ColumnExpectedTextsInTopValuesCountCheckSpec
        ]
        if isinstance(_profile_expected_texts_in_top_values_count, Unset):
            profile_expected_texts_in_top_values_count = UNSET
        else:
            profile_expected_texts_in_top_values_count = (
                ColumnExpectedTextsInTopValuesCountCheckSpec.from_dict(
                    _profile_expected_texts_in_top_values_count
                )
            )

        _profile_expected_numbers_in_use_count = d.pop(
            "profile_expected_numbers_in_use_count", UNSET
        )
        profile_expected_numbers_in_use_count: Union[
            Unset, ColumnExpectedNumbersInUseCountCheckSpec
        ]
        if isinstance(_profile_expected_numbers_in_use_count, Unset):
            profile_expected_numbers_in_use_count = UNSET
        else:
            profile_expected_numbers_in_use_count = (
                ColumnExpectedNumbersInUseCountCheckSpec.from_dict(
                    _profile_expected_numbers_in_use_count
                )
            )

        _profile_text_valid_country_code_percent = d.pop(
            "profile_text_valid_country_code_percent", UNSET
        )
        profile_text_valid_country_code_percent: Union[
            Unset, ColumnTextValidCountryCodePercentCheckSpec
        ]
        if isinstance(_profile_text_valid_country_code_percent, Unset):
            profile_text_valid_country_code_percent = UNSET
        else:
            profile_text_valid_country_code_percent = (
                ColumnTextValidCountryCodePercentCheckSpec.from_dict(
                    _profile_text_valid_country_code_percent
                )
            )

        _profile_text_valid_currency_code_percent = d.pop(
            "profile_text_valid_currency_code_percent", UNSET
        )
        profile_text_valid_currency_code_percent: Union[
            Unset, ColumnTextValidCurrencyCodePercentCheckSpec
        ]
        if isinstance(_profile_text_valid_currency_code_percent, Unset):
            profile_text_valid_currency_code_percent = UNSET
        else:
            profile_text_valid_currency_code_percent = (
                ColumnTextValidCurrencyCodePercentCheckSpec.from_dict(
                    _profile_text_valid_currency_code_percent
                )
            )

        column_accepted_values_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_text_found_in_set_percent=profile_text_found_in_set_percent,
            profile_number_found_in_set_percent=profile_number_found_in_set_percent,
            profile_expected_text_values_in_use_count=profile_expected_text_values_in_use_count,
            profile_expected_texts_in_top_values_count=profile_expected_texts_in_top_values_count,
            profile_expected_numbers_in_use_count=profile_expected_numbers_in_use_count,
            profile_text_valid_country_code_percent=profile_text_valid_country_code_percent,
            profile_text_valid_currency_code_percent=profile_text_valid_currency_code_percent,
        )

        column_accepted_values_profiling_checks_spec.additional_properties = d
        return column_accepted_values_profiling_checks_spec

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
