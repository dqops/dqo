from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_integrity_daily_monitoring_checks_spec_custom_checks import (
        ColumnIntegrityDailyMonitoringChecksSpecCustomChecks,
    )
    from ..models.column_integrity_foreign_key_match_percent_check_spec import (
        ColumnIntegrityForeignKeyMatchPercentCheckSpec,
    )
    from ..models.column_integrity_foreign_key_not_match_count_check_spec import (
        ColumnIntegrityForeignKeyNotMatchCountCheckSpec,
    )


T = TypeVar("T", bound="ColumnIntegrityDailyMonitoringChecksSpec")


@_attrs_define
class ColumnIntegrityDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnIntegrityDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_foreign_key_not_match_count (Union[Unset, ColumnIntegrityForeignKeyNotMatchCountCheckSpec]):
        daily_foreign_key_match_percent (Union[Unset, ColumnIntegrityForeignKeyMatchPercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnIntegrityDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_foreign_key_not_match_count: Union[
        Unset, "ColumnIntegrityForeignKeyNotMatchCountCheckSpec"
    ] = UNSET
    daily_foreign_key_match_percent: Union[
        Unset, "ColumnIntegrityForeignKeyMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_foreign_key_not_match_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_foreign_key_not_match_count, Unset):
            daily_foreign_key_not_match_count = (
                self.daily_foreign_key_not_match_count.to_dict()
            )

        daily_foreign_key_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_foreign_key_match_percent, Unset):
            daily_foreign_key_match_percent = (
                self.daily_foreign_key_match_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_foreign_key_not_match_count is not UNSET:
            field_dict[
                "daily_foreign_key_not_match_count"
            ] = daily_foreign_key_not_match_count
        if daily_foreign_key_match_percent is not UNSET:
            field_dict[
                "daily_foreign_key_match_percent"
            ] = daily_foreign_key_match_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_integrity_daily_monitoring_checks_spec_custom_checks import (
            ColumnIntegrityDailyMonitoringChecksSpecCustomChecks,
        )
        from ..models.column_integrity_foreign_key_match_percent_check_spec import (
            ColumnIntegrityForeignKeyMatchPercentCheckSpec,
        )
        from ..models.column_integrity_foreign_key_not_match_count_check_spec import (
            ColumnIntegrityForeignKeyNotMatchCountCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnIntegrityDailyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnIntegrityDailyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_foreign_key_not_match_count = d.pop(
            "daily_foreign_key_not_match_count", UNSET
        )
        daily_foreign_key_not_match_count: Union[
            Unset, ColumnIntegrityForeignKeyNotMatchCountCheckSpec
        ]
        if isinstance(_daily_foreign_key_not_match_count, Unset):
            daily_foreign_key_not_match_count = UNSET
        else:
            daily_foreign_key_not_match_count = (
                ColumnIntegrityForeignKeyNotMatchCountCheckSpec.from_dict(
                    _daily_foreign_key_not_match_count
                )
            )

        _daily_foreign_key_match_percent = d.pop(
            "daily_foreign_key_match_percent", UNSET
        )
        daily_foreign_key_match_percent: Union[
            Unset, ColumnIntegrityForeignKeyMatchPercentCheckSpec
        ]
        if isinstance(_daily_foreign_key_match_percent, Unset):
            daily_foreign_key_match_percent = UNSET
        else:
            daily_foreign_key_match_percent = (
                ColumnIntegrityForeignKeyMatchPercentCheckSpec.from_dict(
                    _daily_foreign_key_match_percent
                )
            )

        column_integrity_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_foreign_key_not_match_count=daily_foreign_key_not_match_count,
            daily_foreign_key_match_percent=daily_foreign_key_match_percent,
        )

        column_integrity_daily_monitoring_checks_spec.additional_properties = d
        return column_integrity_daily_monitoring_checks_spec

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
