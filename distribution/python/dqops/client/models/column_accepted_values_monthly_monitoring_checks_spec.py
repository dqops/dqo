from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accepted_values_monthly_monitoring_checks_spec_custom_checks import (
        ColumnAcceptedValuesMonthlyMonitoringChecksSpecCustomChecks,
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


T = TypeVar("T", bound="ColumnAcceptedValuesMonthlyMonitoringChecksSpec")


@_attrs_define
class ColumnAcceptedValuesMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnAcceptedValuesMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of
            additional custom checks within this category. The keys are check names defined in the definition section. The
            sensor parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_text_found_in_set_percent (Union[Unset, ColumnTextFoundInSetPercentCheckSpec]):
        monthly_number_found_in_set_percent (Union[Unset, ColumnNumberFoundInSetPercentCheckSpec]):
        monthly_expected_text_values_in_use_count (Union[Unset, ColumnExpectedTextValuesInUseCountCheckSpec]):
        monthly_expected_texts_in_top_values_count (Union[Unset, ColumnExpectedTextsInTopValuesCountCheckSpec]):
        monthly_expected_numbers_in_use_count (Union[Unset, ColumnExpectedNumbersInUseCountCheckSpec]):
        monthly_text_valid_country_code_percent (Union[Unset, ColumnTextValidCountryCodePercentCheckSpec]):
        monthly_text_valid_currency_code_percent (Union[Unset, ColumnTextValidCurrencyCodePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnAcceptedValuesMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    monthly_text_found_in_set_percent: Union[
        Unset, "ColumnTextFoundInSetPercentCheckSpec"
    ] = UNSET
    monthly_number_found_in_set_percent: Union[
        Unset, "ColumnNumberFoundInSetPercentCheckSpec"
    ] = UNSET
    monthly_expected_text_values_in_use_count: Union[
        Unset, "ColumnExpectedTextValuesInUseCountCheckSpec"
    ] = UNSET
    monthly_expected_texts_in_top_values_count: Union[
        Unset, "ColumnExpectedTextsInTopValuesCountCheckSpec"
    ] = UNSET
    monthly_expected_numbers_in_use_count: Union[
        Unset, "ColumnExpectedNumbersInUseCountCheckSpec"
    ] = UNSET
    monthly_text_valid_country_code_percent: Union[
        Unset, "ColumnTextValidCountryCodePercentCheckSpec"
    ] = UNSET
    monthly_text_valid_currency_code_percent: Union[
        Unset, "ColumnTextValidCurrencyCodePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_text_found_in_set_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_found_in_set_percent, Unset):
            monthly_text_found_in_set_percent = (
                self.monthly_text_found_in_set_percent.to_dict()
            )

        monthly_number_found_in_set_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_number_found_in_set_percent, Unset):
            monthly_number_found_in_set_percent = (
                self.monthly_number_found_in_set_percent.to_dict()
            )

        monthly_expected_text_values_in_use_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_expected_text_values_in_use_count, Unset):
            monthly_expected_text_values_in_use_count = (
                self.monthly_expected_text_values_in_use_count.to_dict()
            )

        monthly_expected_texts_in_top_values_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_expected_texts_in_top_values_count, Unset):
            monthly_expected_texts_in_top_values_count = (
                self.monthly_expected_texts_in_top_values_count.to_dict()
            )

        monthly_expected_numbers_in_use_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_expected_numbers_in_use_count, Unset):
            monthly_expected_numbers_in_use_count = (
                self.monthly_expected_numbers_in_use_count.to_dict()
            )

        monthly_text_valid_country_code_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_valid_country_code_percent, Unset):
            monthly_text_valid_country_code_percent = (
                self.monthly_text_valid_country_code_percent.to_dict()
            )

        monthly_text_valid_currency_code_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_valid_currency_code_percent, Unset):
            monthly_text_valid_currency_code_percent = (
                self.monthly_text_valid_currency_code_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_text_found_in_set_percent is not UNSET:
            field_dict["monthly_text_found_in_set_percent"] = (
                monthly_text_found_in_set_percent
            )
        if monthly_number_found_in_set_percent is not UNSET:
            field_dict["monthly_number_found_in_set_percent"] = (
                monthly_number_found_in_set_percent
            )
        if monthly_expected_text_values_in_use_count is not UNSET:
            field_dict["monthly_expected_text_values_in_use_count"] = (
                monthly_expected_text_values_in_use_count
            )
        if monthly_expected_texts_in_top_values_count is not UNSET:
            field_dict["monthly_expected_texts_in_top_values_count"] = (
                monthly_expected_texts_in_top_values_count
            )
        if monthly_expected_numbers_in_use_count is not UNSET:
            field_dict["monthly_expected_numbers_in_use_count"] = (
                monthly_expected_numbers_in_use_count
            )
        if monthly_text_valid_country_code_percent is not UNSET:
            field_dict["monthly_text_valid_country_code_percent"] = (
                monthly_text_valid_country_code_percent
            )
        if monthly_text_valid_currency_code_percent is not UNSET:
            field_dict["monthly_text_valid_currency_code_percent"] = (
                monthly_text_valid_currency_code_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_accepted_values_monthly_monitoring_checks_spec_custom_checks import (
            ColumnAcceptedValuesMonthlyMonitoringChecksSpecCustomChecks,
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
        custom_checks: Union[
            Unset, ColumnAcceptedValuesMonthlyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnAcceptedValuesMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_text_found_in_set_percent = d.pop(
            "monthly_text_found_in_set_percent", UNSET
        )
        monthly_text_found_in_set_percent: Union[
            Unset, ColumnTextFoundInSetPercentCheckSpec
        ]
        if isinstance(_monthly_text_found_in_set_percent, Unset):
            monthly_text_found_in_set_percent = UNSET
        else:
            monthly_text_found_in_set_percent = (
                ColumnTextFoundInSetPercentCheckSpec.from_dict(
                    _monthly_text_found_in_set_percent
                )
            )

        _monthly_number_found_in_set_percent = d.pop(
            "monthly_number_found_in_set_percent", UNSET
        )
        monthly_number_found_in_set_percent: Union[
            Unset, ColumnNumberFoundInSetPercentCheckSpec
        ]
        if isinstance(_monthly_number_found_in_set_percent, Unset):
            monthly_number_found_in_set_percent = UNSET
        else:
            monthly_number_found_in_set_percent = (
                ColumnNumberFoundInSetPercentCheckSpec.from_dict(
                    _monthly_number_found_in_set_percent
                )
            )

        _monthly_expected_text_values_in_use_count = d.pop(
            "monthly_expected_text_values_in_use_count", UNSET
        )
        monthly_expected_text_values_in_use_count: Union[
            Unset, ColumnExpectedTextValuesInUseCountCheckSpec
        ]
        if isinstance(_monthly_expected_text_values_in_use_count, Unset):
            monthly_expected_text_values_in_use_count = UNSET
        else:
            monthly_expected_text_values_in_use_count = (
                ColumnExpectedTextValuesInUseCountCheckSpec.from_dict(
                    _monthly_expected_text_values_in_use_count
                )
            )

        _monthly_expected_texts_in_top_values_count = d.pop(
            "monthly_expected_texts_in_top_values_count", UNSET
        )
        monthly_expected_texts_in_top_values_count: Union[
            Unset, ColumnExpectedTextsInTopValuesCountCheckSpec
        ]
        if isinstance(_monthly_expected_texts_in_top_values_count, Unset):
            monthly_expected_texts_in_top_values_count = UNSET
        else:
            monthly_expected_texts_in_top_values_count = (
                ColumnExpectedTextsInTopValuesCountCheckSpec.from_dict(
                    _monthly_expected_texts_in_top_values_count
                )
            )

        _monthly_expected_numbers_in_use_count = d.pop(
            "monthly_expected_numbers_in_use_count", UNSET
        )
        monthly_expected_numbers_in_use_count: Union[
            Unset, ColumnExpectedNumbersInUseCountCheckSpec
        ]
        if isinstance(_monthly_expected_numbers_in_use_count, Unset):
            monthly_expected_numbers_in_use_count = UNSET
        else:
            monthly_expected_numbers_in_use_count = (
                ColumnExpectedNumbersInUseCountCheckSpec.from_dict(
                    _monthly_expected_numbers_in_use_count
                )
            )

        _monthly_text_valid_country_code_percent = d.pop(
            "monthly_text_valid_country_code_percent", UNSET
        )
        monthly_text_valid_country_code_percent: Union[
            Unset, ColumnTextValidCountryCodePercentCheckSpec
        ]
        if isinstance(_monthly_text_valid_country_code_percent, Unset):
            monthly_text_valid_country_code_percent = UNSET
        else:
            monthly_text_valid_country_code_percent = (
                ColumnTextValidCountryCodePercentCheckSpec.from_dict(
                    _monthly_text_valid_country_code_percent
                )
            )

        _monthly_text_valid_currency_code_percent = d.pop(
            "monthly_text_valid_currency_code_percent", UNSET
        )
        monthly_text_valid_currency_code_percent: Union[
            Unset, ColumnTextValidCurrencyCodePercentCheckSpec
        ]
        if isinstance(_monthly_text_valid_currency_code_percent, Unset):
            monthly_text_valid_currency_code_percent = UNSET
        else:
            monthly_text_valid_currency_code_percent = (
                ColumnTextValidCurrencyCodePercentCheckSpec.from_dict(
                    _monthly_text_valid_currency_code_percent
                )
            )

        column_accepted_values_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_text_found_in_set_percent=monthly_text_found_in_set_percent,
            monthly_number_found_in_set_percent=monthly_number_found_in_set_percent,
            monthly_expected_text_values_in_use_count=monthly_expected_text_values_in_use_count,
            monthly_expected_texts_in_top_values_count=monthly_expected_texts_in_top_values_count,
            monthly_expected_numbers_in_use_count=monthly_expected_numbers_in_use_count,
            monthly_text_valid_country_code_percent=monthly_text_valid_country_code_percent,
            monthly_text_valid_currency_code_percent=monthly_text_valid_currency_code_percent,
        )

        column_accepted_values_monthly_monitoring_checks_spec.additional_properties = d
        return column_accepted_values_monthly_monitoring_checks_spec

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
