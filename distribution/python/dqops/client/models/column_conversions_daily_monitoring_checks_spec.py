from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_conversions_daily_monitoring_checks_spec_custom_checks import (
        ColumnConversionsDailyMonitoringChecksSpecCustomChecks,
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


T = TypeVar("T", bound="ColumnConversionsDailyMonitoringChecksSpec")


@_attrs_define
class ColumnConversionsDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnConversionsDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_text_parsable_to_boolean_percent (Union[Unset, ColumnTextParsableToBooleanPercentCheckSpec]):
        daily_text_parsable_to_integer_percent (Union[Unset, ColumnTextParsableToIntegerPercentCheckSpec]):
        daily_text_parsable_to_float_percent (Union[Unset, ColumnTextParsableToFloatPercentCheckSpec]):
        daily_text_parsable_to_date_percent (Union[Unset, ColumnTextParsableToDatePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnConversionsDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_text_parsable_to_boolean_percent: Union[
        Unset, "ColumnTextParsableToBooleanPercentCheckSpec"
    ] = UNSET
    daily_text_parsable_to_integer_percent: Union[
        Unset, "ColumnTextParsableToIntegerPercentCheckSpec"
    ] = UNSET
    daily_text_parsable_to_float_percent: Union[
        Unset, "ColumnTextParsableToFloatPercentCheckSpec"
    ] = UNSET
    daily_text_parsable_to_date_percent: Union[
        Unset, "ColumnTextParsableToDatePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_text_parsable_to_boolean_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_parsable_to_boolean_percent, Unset):
            daily_text_parsable_to_boolean_percent = (
                self.daily_text_parsable_to_boolean_percent.to_dict()
            )

        daily_text_parsable_to_integer_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_parsable_to_integer_percent, Unset):
            daily_text_parsable_to_integer_percent = (
                self.daily_text_parsable_to_integer_percent.to_dict()
            )

        daily_text_parsable_to_float_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_parsable_to_float_percent, Unset):
            daily_text_parsable_to_float_percent = (
                self.daily_text_parsable_to_float_percent.to_dict()
            )

        daily_text_parsable_to_date_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_parsable_to_date_percent, Unset):
            daily_text_parsable_to_date_percent = (
                self.daily_text_parsable_to_date_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_text_parsable_to_boolean_percent is not UNSET:
            field_dict["daily_text_parsable_to_boolean_percent"] = (
                daily_text_parsable_to_boolean_percent
            )
        if daily_text_parsable_to_integer_percent is not UNSET:
            field_dict["daily_text_parsable_to_integer_percent"] = (
                daily_text_parsable_to_integer_percent
            )
        if daily_text_parsable_to_float_percent is not UNSET:
            field_dict["daily_text_parsable_to_float_percent"] = (
                daily_text_parsable_to_float_percent
            )
        if daily_text_parsable_to_date_percent is not UNSET:
            field_dict["daily_text_parsable_to_date_percent"] = (
                daily_text_parsable_to_date_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_conversions_daily_monitoring_checks_spec_custom_checks import (
            ColumnConversionsDailyMonitoringChecksSpecCustomChecks,
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

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnConversionsDailyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnConversionsDailyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_text_parsable_to_boolean_percent = d.pop(
            "daily_text_parsable_to_boolean_percent", UNSET
        )
        daily_text_parsable_to_boolean_percent: Union[
            Unset, ColumnTextParsableToBooleanPercentCheckSpec
        ]
        if isinstance(_daily_text_parsable_to_boolean_percent, Unset):
            daily_text_parsable_to_boolean_percent = UNSET
        else:
            daily_text_parsable_to_boolean_percent = (
                ColumnTextParsableToBooleanPercentCheckSpec.from_dict(
                    _daily_text_parsable_to_boolean_percent
                )
            )

        _daily_text_parsable_to_integer_percent = d.pop(
            "daily_text_parsable_to_integer_percent", UNSET
        )
        daily_text_parsable_to_integer_percent: Union[
            Unset, ColumnTextParsableToIntegerPercentCheckSpec
        ]
        if isinstance(_daily_text_parsable_to_integer_percent, Unset):
            daily_text_parsable_to_integer_percent = UNSET
        else:
            daily_text_parsable_to_integer_percent = (
                ColumnTextParsableToIntegerPercentCheckSpec.from_dict(
                    _daily_text_parsable_to_integer_percent
                )
            )

        _daily_text_parsable_to_float_percent = d.pop(
            "daily_text_parsable_to_float_percent", UNSET
        )
        daily_text_parsable_to_float_percent: Union[
            Unset, ColumnTextParsableToFloatPercentCheckSpec
        ]
        if isinstance(_daily_text_parsable_to_float_percent, Unset):
            daily_text_parsable_to_float_percent = UNSET
        else:
            daily_text_parsable_to_float_percent = (
                ColumnTextParsableToFloatPercentCheckSpec.from_dict(
                    _daily_text_parsable_to_float_percent
                )
            )

        _daily_text_parsable_to_date_percent = d.pop(
            "daily_text_parsable_to_date_percent", UNSET
        )
        daily_text_parsable_to_date_percent: Union[
            Unset, ColumnTextParsableToDatePercentCheckSpec
        ]
        if isinstance(_daily_text_parsable_to_date_percent, Unset):
            daily_text_parsable_to_date_percent = UNSET
        else:
            daily_text_parsable_to_date_percent = (
                ColumnTextParsableToDatePercentCheckSpec.from_dict(
                    _daily_text_parsable_to_date_percent
                )
            )

        column_conversions_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_text_parsable_to_boolean_percent=daily_text_parsable_to_boolean_percent,
            daily_text_parsable_to_integer_percent=daily_text_parsable_to_integer_percent,
            daily_text_parsable_to_float_percent=daily_text_parsable_to_float_percent,
            daily_text_parsable_to_date_percent=daily_text_parsable_to_date_percent,
        )

        column_conversions_daily_monitoring_checks_spec.additional_properties = d
        return column_conversions_daily_monitoring_checks_spec

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
