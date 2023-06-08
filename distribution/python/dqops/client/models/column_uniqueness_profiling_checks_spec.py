from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
    from ..models.column_duplicate_percent_check_spec import (
        ColumnDuplicatePercentCheckSpec,
    )
    from ..models.column_unique_count_check_spec import ColumnUniqueCountCheckSpec
    from ..models.column_unique_percent_check_spec import ColumnUniquePercentCheckSpec


T = TypeVar("T", bound="ColumnUniquenessProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnUniquenessProfilingChecksSpec:
    """
    Attributes:
        unique_count (Union[Unset, ColumnUniqueCountCheckSpec]):
        unique_percent (Union[Unset, ColumnUniquePercentCheckSpec]):
        duplicate_count (Union[Unset, ColumnDuplicateCountCheckSpec]):
        duplicate_percent (Union[Unset, ColumnDuplicatePercentCheckSpec]):
    """

    unique_count: Union[Unset, "ColumnUniqueCountCheckSpec"] = UNSET
    unique_percent: Union[Unset, "ColumnUniquePercentCheckSpec"] = UNSET
    duplicate_count: Union[Unset, "ColumnDuplicateCountCheckSpec"] = UNSET
    duplicate_percent: Union[Unset, "ColumnDuplicatePercentCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        unique_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.unique_count, Unset):
            unique_count = self.unique_count.to_dict()

        unique_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.unique_percent, Unset):
            unique_percent = self.unique_percent.to_dict()

        duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.duplicate_count, Unset):
            duplicate_count = self.duplicate_count.to_dict()

        duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.duplicate_percent, Unset):
            duplicate_percent = self.duplicate_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if unique_count is not UNSET:
            field_dict["unique_count"] = unique_count
        if unique_percent is not UNSET:
            field_dict["unique_percent"] = unique_percent
        if duplicate_count is not UNSET:
            field_dict["duplicate_count"] = duplicate_count
        if duplicate_percent is not UNSET:
            field_dict["duplicate_percent"] = duplicate_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_duplicate_count_check_spec import (
            ColumnDuplicateCountCheckSpec,
        )
        from ..models.column_duplicate_percent_check_spec import (
            ColumnDuplicatePercentCheckSpec,
        )
        from ..models.column_unique_count_check_spec import ColumnUniqueCountCheckSpec
        from ..models.column_unique_percent_check_spec import (
            ColumnUniquePercentCheckSpec,
        )

        d = src_dict.copy()
        _unique_count = d.pop("unique_count", UNSET)
        unique_count: Union[Unset, ColumnUniqueCountCheckSpec]
        if isinstance(_unique_count, Unset):
            unique_count = UNSET
        else:
            unique_count = ColumnUniqueCountCheckSpec.from_dict(_unique_count)

        _unique_percent = d.pop("unique_percent", UNSET)
        unique_percent: Union[Unset, ColumnUniquePercentCheckSpec]
        if isinstance(_unique_percent, Unset):
            unique_percent = UNSET
        else:
            unique_percent = ColumnUniquePercentCheckSpec.from_dict(_unique_percent)

        _duplicate_count = d.pop("duplicate_count", UNSET)
        duplicate_count: Union[Unset, ColumnDuplicateCountCheckSpec]
        if isinstance(_duplicate_count, Unset):
            duplicate_count = UNSET
        else:
            duplicate_count = ColumnDuplicateCountCheckSpec.from_dict(_duplicate_count)

        _duplicate_percent = d.pop("duplicate_percent", UNSET)
        duplicate_percent: Union[Unset, ColumnDuplicatePercentCheckSpec]
        if isinstance(_duplicate_percent, Unset):
            duplicate_percent = UNSET
        else:
            duplicate_percent = ColumnDuplicatePercentCheckSpec.from_dict(
                _duplicate_percent
            )

        column_uniqueness_profiling_checks_spec = cls(
            unique_count=unique_count,
            unique_percent=unique_percent,
            duplicate_count=duplicate_count,
            duplicate_percent=duplicate_percent,
        )

        column_uniqueness_profiling_checks_spec.additional_properties = d
        return column_uniqueness_profiling_checks_spec

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
