from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_text_daily_monitoring_checks_spec_custom_checks import (
        ColumnTextDailyMonitoringChecksSpecCustomChecks,
    )
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


T = TypeVar("T", bound="ColumnTextDailyMonitoringChecksSpec")


@_attrs_define
class ColumnTextDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnTextDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        daily_text_min_length (Union[Unset, ColumnTextMinLengthCheckSpec]):
        daily_text_max_length (Union[Unset, ColumnTextMaxLengthCheckSpec]):
        daily_text_mean_length (Union[Unset, ColumnTextMeanLengthCheckSpec]):
        daily_text_length_below_min_length (Union[Unset, ColumnTextLengthBelowMinLengthCheckSpec]):
        daily_text_length_below_min_length_percent (Union[Unset, ColumnTextLengthBelowMinLengthPercentCheckSpec]):
        daily_text_length_above_max_length (Union[Unset, ColumnTextLengthAboveMaxLengthCheckSpec]):
        daily_text_length_above_max_length_percent (Union[Unset, ColumnTextLengthAboveMaxLengthPercentCheckSpec]):
        daily_text_length_in_range_percent (Union[Unset, ColumnTextLengthInRangePercentCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnTextDailyMonitoringChecksSpecCustomChecks"] = (
        UNSET
    )
    daily_text_min_length: Union[Unset, "ColumnTextMinLengthCheckSpec"] = UNSET
    daily_text_max_length: Union[Unset, "ColumnTextMaxLengthCheckSpec"] = UNSET
    daily_text_mean_length: Union[Unset, "ColumnTextMeanLengthCheckSpec"] = UNSET
    daily_text_length_below_min_length: Union[
        Unset, "ColumnTextLengthBelowMinLengthCheckSpec"
    ] = UNSET
    daily_text_length_below_min_length_percent: Union[
        Unset, "ColumnTextLengthBelowMinLengthPercentCheckSpec"
    ] = UNSET
    daily_text_length_above_max_length: Union[
        Unset, "ColumnTextLengthAboveMaxLengthCheckSpec"
    ] = UNSET
    daily_text_length_above_max_length_percent: Union[
        Unset, "ColumnTextLengthAboveMaxLengthPercentCheckSpec"
    ] = UNSET
    daily_text_length_in_range_percent: Union[
        Unset, "ColumnTextLengthInRangePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_text_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_min_length, Unset):
            daily_text_min_length = self.daily_text_min_length.to_dict()

        daily_text_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_max_length, Unset):
            daily_text_max_length = self.daily_text_max_length.to_dict()

        daily_text_mean_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_mean_length, Unset):
            daily_text_mean_length = self.daily_text_mean_length.to_dict()

        daily_text_length_below_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_length_below_min_length, Unset):
            daily_text_length_below_min_length = (
                self.daily_text_length_below_min_length.to_dict()
            )

        daily_text_length_below_min_length_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_length_below_min_length_percent, Unset):
            daily_text_length_below_min_length_percent = (
                self.daily_text_length_below_min_length_percent.to_dict()
            )

        daily_text_length_above_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_length_above_max_length, Unset):
            daily_text_length_above_max_length = (
                self.daily_text_length_above_max_length.to_dict()
            )

        daily_text_length_above_max_length_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_length_above_max_length_percent, Unset):
            daily_text_length_above_max_length_percent = (
                self.daily_text_length_above_max_length_percent.to_dict()
            )

        daily_text_length_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_length_in_range_percent, Unset):
            daily_text_length_in_range_percent = (
                self.daily_text_length_in_range_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_text_min_length is not UNSET:
            field_dict["daily_text_min_length"] = daily_text_min_length
        if daily_text_max_length is not UNSET:
            field_dict["daily_text_max_length"] = daily_text_max_length
        if daily_text_mean_length is not UNSET:
            field_dict["daily_text_mean_length"] = daily_text_mean_length
        if daily_text_length_below_min_length is not UNSET:
            field_dict["daily_text_length_below_min_length"] = (
                daily_text_length_below_min_length
            )
        if daily_text_length_below_min_length_percent is not UNSET:
            field_dict["daily_text_length_below_min_length_percent"] = (
                daily_text_length_below_min_length_percent
            )
        if daily_text_length_above_max_length is not UNSET:
            field_dict["daily_text_length_above_max_length"] = (
                daily_text_length_above_max_length
            )
        if daily_text_length_above_max_length_percent is not UNSET:
            field_dict["daily_text_length_above_max_length_percent"] = (
                daily_text_length_above_max_length_percent
            )
        if daily_text_length_in_range_percent is not UNSET:
            field_dict["daily_text_length_in_range_percent"] = (
                daily_text_length_in_range_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_text_daily_monitoring_checks_spec_custom_checks import (
            ColumnTextDailyMonitoringChecksSpecCustomChecks,
        )
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

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnTextDailyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnTextDailyMonitoringChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _daily_text_min_length = d.pop("daily_text_min_length", UNSET)
        daily_text_min_length: Union[Unset, ColumnTextMinLengthCheckSpec]
        if isinstance(_daily_text_min_length, Unset):
            daily_text_min_length = UNSET
        else:
            daily_text_min_length = ColumnTextMinLengthCheckSpec.from_dict(
                _daily_text_min_length
            )

        _daily_text_max_length = d.pop("daily_text_max_length", UNSET)
        daily_text_max_length: Union[Unset, ColumnTextMaxLengthCheckSpec]
        if isinstance(_daily_text_max_length, Unset):
            daily_text_max_length = UNSET
        else:
            daily_text_max_length = ColumnTextMaxLengthCheckSpec.from_dict(
                _daily_text_max_length
            )

        _daily_text_mean_length = d.pop("daily_text_mean_length", UNSET)
        daily_text_mean_length: Union[Unset, ColumnTextMeanLengthCheckSpec]
        if isinstance(_daily_text_mean_length, Unset):
            daily_text_mean_length = UNSET
        else:
            daily_text_mean_length = ColumnTextMeanLengthCheckSpec.from_dict(
                _daily_text_mean_length
            )

        _daily_text_length_below_min_length = d.pop(
            "daily_text_length_below_min_length", UNSET
        )
        daily_text_length_below_min_length: Union[
            Unset, ColumnTextLengthBelowMinLengthCheckSpec
        ]
        if isinstance(_daily_text_length_below_min_length, Unset):
            daily_text_length_below_min_length = UNSET
        else:
            daily_text_length_below_min_length = (
                ColumnTextLengthBelowMinLengthCheckSpec.from_dict(
                    _daily_text_length_below_min_length
                )
            )

        _daily_text_length_below_min_length_percent = d.pop(
            "daily_text_length_below_min_length_percent", UNSET
        )
        daily_text_length_below_min_length_percent: Union[
            Unset, ColumnTextLengthBelowMinLengthPercentCheckSpec
        ]
        if isinstance(_daily_text_length_below_min_length_percent, Unset):
            daily_text_length_below_min_length_percent = UNSET
        else:
            daily_text_length_below_min_length_percent = (
                ColumnTextLengthBelowMinLengthPercentCheckSpec.from_dict(
                    _daily_text_length_below_min_length_percent
                )
            )

        _daily_text_length_above_max_length = d.pop(
            "daily_text_length_above_max_length", UNSET
        )
        daily_text_length_above_max_length: Union[
            Unset, ColumnTextLengthAboveMaxLengthCheckSpec
        ]
        if isinstance(_daily_text_length_above_max_length, Unset):
            daily_text_length_above_max_length = UNSET
        else:
            daily_text_length_above_max_length = (
                ColumnTextLengthAboveMaxLengthCheckSpec.from_dict(
                    _daily_text_length_above_max_length
                )
            )

        _daily_text_length_above_max_length_percent = d.pop(
            "daily_text_length_above_max_length_percent", UNSET
        )
        daily_text_length_above_max_length_percent: Union[
            Unset, ColumnTextLengthAboveMaxLengthPercentCheckSpec
        ]
        if isinstance(_daily_text_length_above_max_length_percent, Unset):
            daily_text_length_above_max_length_percent = UNSET
        else:
            daily_text_length_above_max_length_percent = (
                ColumnTextLengthAboveMaxLengthPercentCheckSpec.from_dict(
                    _daily_text_length_above_max_length_percent
                )
            )

        _daily_text_length_in_range_percent = d.pop(
            "daily_text_length_in_range_percent", UNSET
        )
        daily_text_length_in_range_percent: Union[
            Unset, ColumnTextLengthInRangePercentCheckSpec
        ]
        if isinstance(_daily_text_length_in_range_percent, Unset):
            daily_text_length_in_range_percent = UNSET
        else:
            daily_text_length_in_range_percent = (
                ColumnTextLengthInRangePercentCheckSpec.from_dict(
                    _daily_text_length_in_range_percent
                )
            )

        column_text_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_text_min_length=daily_text_min_length,
            daily_text_max_length=daily_text_max_length,
            daily_text_mean_length=daily_text_mean_length,
            daily_text_length_below_min_length=daily_text_length_below_min_length,
            daily_text_length_below_min_length_percent=daily_text_length_below_min_length_percent,
            daily_text_length_above_max_length=daily_text_length_above_max_length,
            daily_text_length_above_max_length_percent=daily_text_length_above_max_length_percent,
            daily_text_length_in_range_percent=daily_text_length_in_range_percent,
        )

        column_text_daily_monitoring_checks_spec.additional_properties = d
        return column_text_daily_monitoring_checks_spec

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
