from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_integrity_foreign_key_match_percent_check_spec import (
        ColumnIntegrityForeignKeyMatchPercentCheckSpec,
    )
    from ..models.column_integrity_foreign_key_not_match_count_check_spec import (
        ColumnIntegrityForeignKeyNotMatchCountCheckSpec,
    )


T = TypeVar("T", bound="ColumnIntegrityDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnIntegrityDailyRecurringChecksSpec:
    """
    Attributes:
        daily_foreign_key_not_match_count (Union[Unset, ColumnIntegrityForeignKeyNotMatchCountCheckSpec]):
        daily_foreign_key_match_percent (Union[Unset, ColumnIntegrityForeignKeyMatchPercentCheckSpec]):
    """

    daily_foreign_key_not_match_count: Union[
        Unset, "ColumnIntegrityForeignKeyNotMatchCountCheckSpec"
    ] = UNSET
    daily_foreign_key_match_percent: Union[
        Unset, "ColumnIntegrityForeignKeyMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
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
        from ..models.column_integrity_foreign_key_match_percent_check_spec import (
            ColumnIntegrityForeignKeyMatchPercentCheckSpec,
        )
        from ..models.column_integrity_foreign_key_not_match_count_check_spec import (
            ColumnIntegrityForeignKeyNotMatchCountCheckSpec,
        )

        d = src_dict.copy()
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

        column_integrity_daily_recurring_checks_spec = cls(
            daily_foreign_key_not_match_count=daily_foreign_key_not_match_count,
            daily_foreign_key_match_percent=daily_foreign_key_match_percent,
        )

        column_integrity_daily_recurring_checks_spec.additional_properties = d
        return column_integrity_daily_recurring_checks_spec

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
