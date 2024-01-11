from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_text_length_above_max_length_check_spec import (
        ColumnTextLengthAboveMaxLengthCheckSpec,
    )
    from ..models.column_text_length_above_max_length_percent_check_spec import (
        ColumnTextLengthAboveMaxLengthPercentCheckSpec,
    )
    from ..models.column_text_length_below_min_length_check_spec import (
        ColumnTextLengthBelowMinLengthCheckSpec,
    )
    from ..models.column_text_length_below_min_length_percent_check_spec import (
        ColumnTextLengthBelowMinLengthPercentCheckSpec,
    )
    from ..models.column_text_length_in_range_percent_check_spec import (
        ColumnTextLengthInRangePercentCheckSpec,
    )
    from ..models.column_text_max_length_check_spec import ColumnTextMaxLengthCheckSpec
    from ..models.column_text_mean_length_check_spec import (
        ColumnTextMeanLengthCheckSpec,
    )
    from ..models.column_text_min_length_check_spec import ColumnTextMinLengthCheckSpec
    from ..models.column_text_monthly_monitoring_checks_spec_custom_checks import (
        ColumnTextMonthlyMonitoringChecksSpecCustomChecks,
    )
    from ..models.column_text_parsable_to_boolean_percent_check_spec import (
        ColumnTextParsableToBooleanPercentCheckSpec,
    )
    from ..models.column_text_parsable_to_date_percent_check_spec import (
        ColumnTextParsableToDatePercentCheckSpec,
    )
    from ..models.column_text_parsable_to_float_percent_check_spec import (
        ColumnTextParsableToFloatPercentCheckSpec,
    )
    from ..models.column_text_parsable_to_integer_percent_check_spec import (
        ColumnTextParsableToIntegerPercentCheckSpec,
    )
    from ..models.column_text_surrounded_by_whitespace_check_spec import (
        ColumnTextSurroundedByWhitespaceCheckSpec,
    )
    from ..models.column_text_surrounded_by_whitespace_percent_check_spec import (
        ColumnTextSurroundedByWhitespacePercentCheckSpec,
    )
    from ..models.column_text_valid_country_code_percent_check_spec import (
        ColumnTextValidCountryCodePercentCheckSpec,
    )
    from ..models.column_text_valid_currency_code_percent_check_spec import (
        ColumnTextValidCurrencyCodePercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnTextMonthlyMonitoringChecksSpec")


@_attrs_define
class ColumnTextMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnTextMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        monthly_text_max_length (Union[Unset, ColumnTextMaxLengthCheckSpec]):
        monthly_text_min_length (Union[Unset, ColumnTextMinLengthCheckSpec]):
        monthly_text_mean_length (Union[Unset, ColumnTextMeanLengthCheckSpec]):
        monthly_text_length_below_min_length (Union[Unset, ColumnTextLengthBelowMinLengthCheckSpec]):
        monthly_text_length_below_min_length_percent (Union[Unset, ColumnTextLengthBelowMinLengthPercentCheckSpec]):
        monthly_text_length_above_max_length (Union[Unset, ColumnTextLengthAboveMaxLengthCheckSpec]):
        monthly_text_length_above_max_length_percent (Union[Unset, ColumnTextLengthAboveMaxLengthPercentCheckSpec]):
        monthly_text_length_in_range_percent (Union[Unset, ColumnTextLengthInRangePercentCheckSpec]):
        monthly_text_parsable_to_boolean_percent (Union[Unset, ColumnTextParsableToBooleanPercentCheckSpec]):
        monthly_text_parsable_to_integer_percent (Union[Unset, ColumnTextParsableToIntegerPercentCheckSpec]):
        monthly_text_parsable_to_float_percent (Union[Unset, ColumnTextParsableToFloatPercentCheckSpec]):
        monthly_text_parsable_to_date_percent (Union[Unset, ColumnTextParsableToDatePercentCheckSpec]):
        monthly_text_surrounded_by_whitespace (Union[Unset, ColumnTextSurroundedByWhitespaceCheckSpec]):
        monthly_text_surrounded_by_whitespace_percent (Union[Unset, ColumnTextSurroundedByWhitespacePercentCheckSpec]):
        monthly_text_valid_country_code_percent (Union[Unset, ColumnTextValidCountryCodePercentCheckSpec]):
        monthly_text_valid_currency_code_percent (Union[Unset, ColumnTextValidCurrencyCodePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnTextMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    monthly_text_max_length: Union[Unset, "ColumnTextMaxLengthCheckSpec"] = UNSET
    monthly_text_min_length: Union[Unset, "ColumnTextMinLengthCheckSpec"] = UNSET
    monthly_text_mean_length: Union[Unset, "ColumnTextMeanLengthCheckSpec"] = UNSET
    monthly_text_length_below_min_length: Union[
        Unset, "ColumnTextLengthBelowMinLengthCheckSpec"
    ] = UNSET
    monthly_text_length_below_min_length_percent: Union[
        Unset, "ColumnTextLengthBelowMinLengthPercentCheckSpec"
    ] = UNSET
    monthly_text_length_above_max_length: Union[
        Unset, "ColumnTextLengthAboveMaxLengthCheckSpec"
    ] = UNSET
    monthly_text_length_above_max_length_percent: Union[
        Unset, "ColumnTextLengthAboveMaxLengthPercentCheckSpec"
    ] = UNSET
    monthly_text_length_in_range_percent: Union[
        Unset, "ColumnTextLengthInRangePercentCheckSpec"
    ] = UNSET
    monthly_text_parsable_to_boolean_percent: Union[
        Unset, "ColumnTextParsableToBooleanPercentCheckSpec"
    ] = UNSET
    monthly_text_parsable_to_integer_percent: Union[
        Unset, "ColumnTextParsableToIntegerPercentCheckSpec"
    ] = UNSET
    monthly_text_parsable_to_float_percent: Union[
        Unset, "ColumnTextParsableToFloatPercentCheckSpec"
    ] = UNSET
    monthly_text_parsable_to_date_percent: Union[
        Unset, "ColumnTextParsableToDatePercentCheckSpec"
    ] = UNSET
    monthly_text_surrounded_by_whitespace: Union[
        Unset, "ColumnTextSurroundedByWhitespaceCheckSpec"
    ] = UNSET
    monthly_text_surrounded_by_whitespace_percent: Union[
        Unset, "ColumnTextSurroundedByWhitespacePercentCheckSpec"
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

        monthly_text_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_max_length, Unset):
            monthly_text_max_length = self.monthly_text_max_length.to_dict()

        monthly_text_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_min_length, Unset):
            monthly_text_min_length = self.monthly_text_min_length.to_dict()

        monthly_text_mean_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_mean_length, Unset):
            monthly_text_mean_length = self.monthly_text_mean_length.to_dict()

        monthly_text_length_below_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_length_below_min_length, Unset):
            monthly_text_length_below_min_length = (
                self.monthly_text_length_below_min_length.to_dict()
            )

        monthly_text_length_below_min_length_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_text_length_below_min_length_percent, Unset):
            monthly_text_length_below_min_length_percent = (
                self.monthly_text_length_below_min_length_percent.to_dict()
            )

        monthly_text_length_above_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_length_above_max_length, Unset):
            monthly_text_length_above_max_length = (
                self.monthly_text_length_above_max_length.to_dict()
            )

        monthly_text_length_above_max_length_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_text_length_above_max_length_percent, Unset):
            monthly_text_length_above_max_length_percent = (
                self.monthly_text_length_above_max_length_percent.to_dict()
            )

        monthly_text_length_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_length_in_range_percent, Unset):
            monthly_text_length_in_range_percent = (
                self.monthly_text_length_in_range_percent.to_dict()
            )

        monthly_text_parsable_to_boolean_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_parsable_to_boolean_percent, Unset):
            monthly_text_parsable_to_boolean_percent = (
                self.monthly_text_parsable_to_boolean_percent.to_dict()
            )

        monthly_text_parsable_to_integer_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_parsable_to_integer_percent, Unset):
            monthly_text_parsable_to_integer_percent = (
                self.monthly_text_parsable_to_integer_percent.to_dict()
            )

        monthly_text_parsable_to_float_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_parsable_to_float_percent, Unset):
            monthly_text_parsable_to_float_percent = (
                self.monthly_text_parsable_to_float_percent.to_dict()
            )

        monthly_text_parsable_to_date_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_parsable_to_date_percent, Unset):
            monthly_text_parsable_to_date_percent = (
                self.monthly_text_parsable_to_date_percent.to_dict()
            )

        monthly_text_surrounded_by_whitespace: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_text_surrounded_by_whitespace, Unset):
            monthly_text_surrounded_by_whitespace = (
                self.monthly_text_surrounded_by_whitespace.to_dict()
            )

        monthly_text_surrounded_by_whitespace_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_text_surrounded_by_whitespace_percent, Unset):
            monthly_text_surrounded_by_whitespace_percent = (
                self.monthly_text_surrounded_by_whitespace_percent.to_dict()
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
        if monthly_text_max_length is not UNSET:
            field_dict["monthly_text_max_length"] = monthly_text_max_length
        if monthly_text_min_length is not UNSET:
            field_dict["monthly_text_min_length"] = monthly_text_min_length
        if monthly_text_mean_length is not UNSET:
            field_dict["monthly_text_mean_length"] = monthly_text_mean_length
        if monthly_text_length_below_min_length is not UNSET:
            field_dict[
                "monthly_text_length_below_min_length"
            ] = monthly_text_length_below_min_length
        if monthly_text_length_below_min_length_percent is not UNSET:
            field_dict[
                "monthly_text_length_below_min_length_percent"
            ] = monthly_text_length_below_min_length_percent
        if monthly_text_length_above_max_length is not UNSET:
            field_dict[
                "monthly_text_length_above_max_length"
            ] = monthly_text_length_above_max_length
        if monthly_text_length_above_max_length_percent is not UNSET:
            field_dict[
                "monthly_text_length_above_max_length_percent"
            ] = monthly_text_length_above_max_length_percent
        if monthly_text_length_in_range_percent is not UNSET:
            field_dict[
                "monthly_text_length_in_range_percent"
            ] = monthly_text_length_in_range_percent
        if monthly_text_parsable_to_boolean_percent is not UNSET:
            field_dict[
                "monthly_text_parsable_to_boolean_percent"
            ] = monthly_text_parsable_to_boolean_percent
        if monthly_text_parsable_to_integer_percent is not UNSET:
            field_dict[
                "monthly_text_parsable_to_integer_percent"
            ] = monthly_text_parsable_to_integer_percent
        if monthly_text_parsable_to_float_percent is not UNSET:
            field_dict[
                "monthly_text_parsable_to_float_percent"
            ] = monthly_text_parsable_to_float_percent
        if monthly_text_parsable_to_date_percent is not UNSET:
            field_dict[
                "monthly_text_parsable_to_date_percent"
            ] = monthly_text_parsable_to_date_percent
        if monthly_text_surrounded_by_whitespace is not UNSET:
            field_dict[
                "monthly_text_surrounded_by_whitespace"
            ] = monthly_text_surrounded_by_whitespace
        if monthly_text_surrounded_by_whitespace_percent is not UNSET:
            field_dict[
                "monthly_text_surrounded_by_whitespace_percent"
            ] = monthly_text_surrounded_by_whitespace_percent
        if monthly_text_valid_country_code_percent is not UNSET:
            field_dict[
                "monthly_text_valid_country_code_percent"
            ] = monthly_text_valid_country_code_percent
        if monthly_text_valid_currency_code_percent is not UNSET:
            field_dict[
                "monthly_text_valid_currency_code_percent"
            ] = monthly_text_valid_currency_code_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_text_length_above_max_length_check_spec import (
            ColumnTextLengthAboveMaxLengthCheckSpec,
        )
        from ..models.column_text_length_above_max_length_percent_check_spec import (
            ColumnTextLengthAboveMaxLengthPercentCheckSpec,
        )
        from ..models.column_text_length_below_min_length_check_spec import (
            ColumnTextLengthBelowMinLengthCheckSpec,
        )
        from ..models.column_text_length_below_min_length_percent_check_spec import (
            ColumnTextLengthBelowMinLengthPercentCheckSpec,
        )
        from ..models.column_text_length_in_range_percent_check_spec import (
            ColumnTextLengthInRangePercentCheckSpec,
        )
        from ..models.column_text_max_length_check_spec import (
            ColumnTextMaxLengthCheckSpec,
        )
        from ..models.column_text_mean_length_check_spec import (
            ColumnTextMeanLengthCheckSpec,
        )
        from ..models.column_text_min_length_check_spec import (
            ColumnTextMinLengthCheckSpec,
        )
        from ..models.column_text_monthly_monitoring_checks_spec_custom_checks import (
            ColumnTextMonthlyMonitoringChecksSpecCustomChecks,
        )
        from ..models.column_text_parsable_to_boolean_percent_check_spec import (
            ColumnTextParsableToBooleanPercentCheckSpec,
        )
        from ..models.column_text_parsable_to_date_percent_check_spec import (
            ColumnTextParsableToDatePercentCheckSpec,
        )
        from ..models.column_text_parsable_to_float_percent_check_spec import (
            ColumnTextParsableToFloatPercentCheckSpec,
        )
        from ..models.column_text_parsable_to_integer_percent_check_spec import (
            ColumnTextParsableToIntegerPercentCheckSpec,
        )
        from ..models.column_text_surrounded_by_whitespace_check_spec import (
            ColumnTextSurroundedByWhitespaceCheckSpec,
        )
        from ..models.column_text_surrounded_by_whitespace_percent_check_spec import (
            ColumnTextSurroundedByWhitespacePercentCheckSpec,
        )
        from ..models.column_text_valid_country_code_percent_check_spec import (
            ColumnTextValidCountryCodePercentCheckSpec,
        )
        from ..models.column_text_valid_currency_code_percent_check_spec import (
            ColumnTextValidCurrencyCodePercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnTextMonthlyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnTextMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _monthly_text_max_length = d.pop("monthly_text_max_length", UNSET)
        monthly_text_max_length: Union[Unset, ColumnTextMaxLengthCheckSpec]
        if isinstance(_monthly_text_max_length, Unset):
            monthly_text_max_length = UNSET
        else:
            monthly_text_max_length = ColumnTextMaxLengthCheckSpec.from_dict(
                _monthly_text_max_length
            )

        _monthly_text_min_length = d.pop("monthly_text_min_length", UNSET)
        monthly_text_min_length: Union[Unset, ColumnTextMinLengthCheckSpec]
        if isinstance(_monthly_text_min_length, Unset):
            monthly_text_min_length = UNSET
        else:
            monthly_text_min_length = ColumnTextMinLengthCheckSpec.from_dict(
                _monthly_text_min_length
            )

        _monthly_text_mean_length = d.pop("monthly_text_mean_length", UNSET)
        monthly_text_mean_length: Union[Unset, ColumnTextMeanLengthCheckSpec]
        if isinstance(_monthly_text_mean_length, Unset):
            monthly_text_mean_length = UNSET
        else:
            monthly_text_mean_length = ColumnTextMeanLengthCheckSpec.from_dict(
                _monthly_text_mean_length
            )

        _monthly_text_length_below_min_length = d.pop(
            "monthly_text_length_below_min_length", UNSET
        )
        monthly_text_length_below_min_length: Union[
            Unset, ColumnTextLengthBelowMinLengthCheckSpec
        ]
        if isinstance(_monthly_text_length_below_min_length, Unset):
            monthly_text_length_below_min_length = UNSET
        else:
            monthly_text_length_below_min_length = (
                ColumnTextLengthBelowMinLengthCheckSpec.from_dict(
                    _monthly_text_length_below_min_length
                )
            )

        _monthly_text_length_below_min_length_percent = d.pop(
            "monthly_text_length_below_min_length_percent", UNSET
        )
        monthly_text_length_below_min_length_percent: Union[
            Unset, ColumnTextLengthBelowMinLengthPercentCheckSpec
        ]
        if isinstance(_monthly_text_length_below_min_length_percent, Unset):
            monthly_text_length_below_min_length_percent = UNSET
        else:
            monthly_text_length_below_min_length_percent = (
                ColumnTextLengthBelowMinLengthPercentCheckSpec.from_dict(
                    _monthly_text_length_below_min_length_percent
                )
            )

        _monthly_text_length_above_max_length = d.pop(
            "monthly_text_length_above_max_length", UNSET
        )
        monthly_text_length_above_max_length: Union[
            Unset, ColumnTextLengthAboveMaxLengthCheckSpec
        ]
        if isinstance(_monthly_text_length_above_max_length, Unset):
            monthly_text_length_above_max_length = UNSET
        else:
            monthly_text_length_above_max_length = (
                ColumnTextLengthAboveMaxLengthCheckSpec.from_dict(
                    _monthly_text_length_above_max_length
                )
            )

        _monthly_text_length_above_max_length_percent = d.pop(
            "monthly_text_length_above_max_length_percent", UNSET
        )
        monthly_text_length_above_max_length_percent: Union[
            Unset, ColumnTextLengthAboveMaxLengthPercentCheckSpec
        ]
        if isinstance(_monthly_text_length_above_max_length_percent, Unset):
            monthly_text_length_above_max_length_percent = UNSET
        else:
            monthly_text_length_above_max_length_percent = (
                ColumnTextLengthAboveMaxLengthPercentCheckSpec.from_dict(
                    _monthly_text_length_above_max_length_percent
                )
            )

        _monthly_text_length_in_range_percent = d.pop(
            "monthly_text_length_in_range_percent", UNSET
        )
        monthly_text_length_in_range_percent: Union[
            Unset, ColumnTextLengthInRangePercentCheckSpec
        ]
        if isinstance(_monthly_text_length_in_range_percent, Unset):
            monthly_text_length_in_range_percent = UNSET
        else:
            monthly_text_length_in_range_percent = (
                ColumnTextLengthInRangePercentCheckSpec.from_dict(
                    _monthly_text_length_in_range_percent
                )
            )

        _monthly_text_parsable_to_boolean_percent = d.pop(
            "monthly_text_parsable_to_boolean_percent", UNSET
        )
        monthly_text_parsable_to_boolean_percent: Union[
            Unset, ColumnTextParsableToBooleanPercentCheckSpec
        ]
        if isinstance(_monthly_text_parsable_to_boolean_percent, Unset):
            monthly_text_parsable_to_boolean_percent = UNSET
        else:
            monthly_text_parsable_to_boolean_percent = (
                ColumnTextParsableToBooleanPercentCheckSpec.from_dict(
                    _monthly_text_parsable_to_boolean_percent
                )
            )

        _monthly_text_parsable_to_integer_percent = d.pop(
            "monthly_text_parsable_to_integer_percent", UNSET
        )
        monthly_text_parsable_to_integer_percent: Union[
            Unset, ColumnTextParsableToIntegerPercentCheckSpec
        ]
        if isinstance(_monthly_text_parsable_to_integer_percent, Unset):
            monthly_text_parsable_to_integer_percent = UNSET
        else:
            monthly_text_parsable_to_integer_percent = (
                ColumnTextParsableToIntegerPercentCheckSpec.from_dict(
                    _monthly_text_parsable_to_integer_percent
                )
            )

        _monthly_text_parsable_to_float_percent = d.pop(
            "monthly_text_parsable_to_float_percent", UNSET
        )
        monthly_text_parsable_to_float_percent: Union[
            Unset, ColumnTextParsableToFloatPercentCheckSpec
        ]
        if isinstance(_monthly_text_parsable_to_float_percent, Unset):
            monthly_text_parsable_to_float_percent = UNSET
        else:
            monthly_text_parsable_to_float_percent = (
                ColumnTextParsableToFloatPercentCheckSpec.from_dict(
                    _monthly_text_parsable_to_float_percent
                )
            )

        _monthly_text_parsable_to_date_percent = d.pop(
            "monthly_text_parsable_to_date_percent", UNSET
        )
        monthly_text_parsable_to_date_percent: Union[
            Unset, ColumnTextParsableToDatePercentCheckSpec
        ]
        if isinstance(_monthly_text_parsable_to_date_percent, Unset):
            monthly_text_parsable_to_date_percent = UNSET
        else:
            monthly_text_parsable_to_date_percent = (
                ColumnTextParsableToDatePercentCheckSpec.from_dict(
                    _monthly_text_parsable_to_date_percent
                )
            )

        _monthly_text_surrounded_by_whitespace = d.pop(
            "monthly_text_surrounded_by_whitespace", UNSET
        )
        monthly_text_surrounded_by_whitespace: Union[
            Unset, ColumnTextSurroundedByWhitespaceCheckSpec
        ]
        if isinstance(_monthly_text_surrounded_by_whitespace, Unset):
            monthly_text_surrounded_by_whitespace = UNSET
        else:
            monthly_text_surrounded_by_whitespace = (
                ColumnTextSurroundedByWhitespaceCheckSpec.from_dict(
                    _monthly_text_surrounded_by_whitespace
                )
            )

        _monthly_text_surrounded_by_whitespace_percent = d.pop(
            "monthly_text_surrounded_by_whitespace_percent", UNSET
        )
        monthly_text_surrounded_by_whitespace_percent: Union[
            Unset, ColumnTextSurroundedByWhitespacePercentCheckSpec
        ]
        if isinstance(_monthly_text_surrounded_by_whitespace_percent, Unset):
            monthly_text_surrounded_by_whitespace_percent = UNSET
        else:
            monthly_text_surrounded_by_whitespace_percent = (
                ColumnTextSurroundedByWhitespacePercentCheckSpec.from_dict(
                    _monthly_text_surrounded_by_whitespace_percent
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

        column_text_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_text_max_length=monthly_text_max_length,
            monthly_text_min_length=monthly_text_min_length,
            monthly_text_mean_length=monthly_text_mean_length,
            monthly_text_length_below_min_length=monthly_text_length_below_min_length,
            monthly_text_length_below_min_length_percent=monthly_text_length_below_min_length_percent,
            monthly_text_length_above_max_length=monthly_text_length_above_max_length,
            monthly_text_length_above_max_length_percent=monthly_text_length_above_max_length_percent,
            monthly_text_length_in_range_percent=monthly_text_length_in_range_percent,
            monthly_text_parsable_to_boolean_percent=monthly_text_parsable_to_boolean_percent,
            monthly_text_parsable_to_integer_percent=monthly_text_parsable_to_integer_percent,
            monthly_text_parsable_to_float_percent=monthly_text_parsable_to_float_percent,
            monthly_text_parsable_to_date_percent=monthly_text_parsable_to_date_percent,
            monthly_text_surrounded_by_whitespace=monthly_text_surrounded_by_whitespace,
            monthly_text_surrounded_by_whitespace_percent=monthly_text_surrounded_by_whitespace_percent,
            monthly_text_valid_country_code_percent=monthly_text_valid_country_code_percent,
            monthly_text_valid_currency_code_percent=monthly_text_valid_currency_code_percent,
        )

        column_text_monthly_monitoring_checks_spec.additional_properties = d
        return column_text_monthly_monitoring_checks_spec

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
