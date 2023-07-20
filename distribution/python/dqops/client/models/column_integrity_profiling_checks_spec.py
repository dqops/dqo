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


T = TypeVar("T", bound="ColumnIntegrityProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnIntegrityProfilingChecksSpec:
    """
    Attributes:
        foreign_key_not_match_count (Union[Unset, ColumnIntegrityForeignKeyNotMatchCountCheckSpec]):
        foreign_key_match_percent (Union[Unset, ColumnIntegrityForeignKeyMatchPercentCheckSpec]):
    """

    foreign_key_not_match_count: Union[
        Unset, "ColumnIntegrityForeignKeyNotMatchCountCheckSpec"
    ] = UNSET
    foreign_key_match_percent: Union[
        Unset, "ColumnIntegrityForeignKeyMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        foreign_key_not_match_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.foreign_key_not_match_count, Unset):
            foreign_key_not_match_count = self.foreign_key_not_match_count.to_dict()

        foreign_key_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.foreign_key_match_percent, Unset):
            foreign_key_match_percent = self.foreign_key_match_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if foreign_key_not_match_count is not UNSET:
            field_dict["foreign_key_not_match_count"] = foreign_key_not_match_count
        if foreign_key_match_percent is not UNSET:
            field_dict["foreign_key_match_percent"] = foreign_key_match_percent

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
        _foreign_key_not_match_count = d.pop("foreign_key_not_match_count", UNSET)
        foreign_key_not_match_count: Union[
            Unset, ColumnIntegrityForeignKeyNotMatchCountCheckSpec
        ]
        if isinstance(_foreign_key_not_match_count, Unset):
            foreign_key_not_match_count = UNSET
        else:
            foreign_key_not_match_count = (
                ColumnIntegrityForeignKeyNotMatchCountCheckSpec.from_dict(
                    _foreign_key_not_match_count
                )
            )

        _foreign_key_match_percent = d.pop("foreign_key_match_percent", UNSET)
        foreign_key_match_percent: Union[
            Unset, ColumnIntegrityForeignKeyMatchPercentCheckSpec
        ]
        if isinstance(_foreign_key_match_percent, Unset):
            foreign_key_match_percent = UNSET
        else:
            foreign_key_match_percent = (
                ColumnIntegrityForeignKeyMatchPercentCheckSpec.from_dict(
                    _foreign_key_match_percent
                )
            )

        column_integrity_profiling_checks_spec = cls(
            foreign_key_not_match_count=foreign_key_not_match_count,
            foreign_key_match_percent=foreign_key_match_percent,
        )

        column_integrity_profiling_checks_spec.additional_properties = d
        return column_integrity_profiling_checks_spec

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
