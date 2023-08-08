from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_distinct_count_check_spec import ColumnDistinctCountCheckSpec
    from ..models.column_distinct_percent_check_spec import (
        ColumnDistinctPercentCheckSpec,
    )
    from ..models.column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
    from ..models.column_duplicate_percent_check_spec import (
        ColumnDuplicatePercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnUniquenessProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnUniquenessProfilingChecksSpec:
    """
    Attributes:
        profile_distinct_count (Union[Unset, ColumnDistinctCountCheckSpec]):
        profile_distinct_percent (Union[Unset, ColumnDistinctPercentCheckSpec]):
        profile_duplicate_count (Union[Unset, ColumnDuplicateCountCheckSpec]):
        profile_duplicate_percent (Union[Unset, ColumnDuplicatePercentCheckSpec]):
    """

    profile_distinct_count: Union[Unset, "ColumnDistinctCountCheckSpec"] = UNSET
    profile_distinct_percent: Union[Unset, "ColumnDistinctPercentCheckSpec"] = UNSET
    profile_duplicate_count: Union[Unset, "ColumnDuplicateCountCheckSpec"] = UNSET
    profile_duplicate_percent: Union[Unset, "ColumnDuplicatePercentCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_distinct_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_count, Unset):
            profile_distinct_count = self.profile_distinct_count.to_dict()

        profile_distinct_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_percent, Unset):
            profile_distinct_percent = self.profile_distinct_percent.to_dict()

        profile_duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_duplicate_count, Unset):
            profile_duplicate_count = self.profile_duplicate_count.to_dict()

        profile_duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_duplicate_percent, Unset):
            profile_duplicate_percent = self.profile_duplicate_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_distinct_count is not UNSET:
            field_dict["profile_distinct_count"] = profile_distinct_count
        if profile_distinct_percent is not UNSET:
            field_dict["profile_distinct_percent"] = profile_distinct_percent
        if profile_duplicate_count is not UNSET:
            field_dict["profile_duplicate_count"] = profile_duplicate_count
        if profile_duplicate_percent is not UNSET:
            field_dict["profile_duplicate_percent"] = profile_duplicate_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_distinct_count_check_spec import (
            ColumnDistinctCountCheckSpec,
        )
        from ..models.column_distinct_percent_check_spec import (
            ColumnDistinctPercentCheckSpec,
        )
        from ..models.column_duplicate_count_check_spec import (
            ColumnDuplicateCountCheckSpec,
        )
        from ..models.column_duplicate_percent_check_spec import (
            ColumnDuplicatePercentCheckSpec,
        )

        d = src_dict.copy()
        _profile_distinct_count = d.pop("profile_distinct_count", UNSET)
        profile_distinct_count: Union[Unset, ColumnDistinctCountCheckSpec]
        if isinstance(_profile_distinct_count, Unset):
            profile_distinct_count = UNSET
        else:
            profile_distinct_count = ColumnDistinctCountCheckSpec.from_dict(
                _profile_distinct_count
            )

        _profile_distinct_percent = d.pop("profile_distinct_percent", UNSET)
        profile_distinct_percent: Union[Unset, ColumnDistinctPercentCheckSpec]
        if isinstance(_profile_distinct_percent, Unset):
            profile_distinct_percent = UNSET
        else:
            profile_distinct_percent = ColumnDistinctPercentCheckSpec.from_dict(
                _profile_distinct_percent
            )

        _profile_duplicate_count = d.pop("profile_duplicate_count", UNSET)
        profile_duplicate_count: Union[Unset, ColumnDuplicateCountCheckSpec]
        if isinstance(_profile_duplicate_count, Unset):
            profile_duplicate_count = UNSET
        else:
            profile_duplicate_count = ColumnDuplicateCountCheckSpec.from_dict(
                _profile_duplicate_count
            )

        _profile_duplicate_percent = d.pop("profile_duplicate_percent", UNSET)
        profile_duplicate_percent: Union[Unset, ColumnDuplicatePercentCheckSpec]
        if isinstance(_profile_duplicate_percent, Unset):
            profile_duplicate_percent = UNSET
        else:
            profile_duplicate_percent = ColumnDuplicatePercentCheckSpec.from_dict(
                _profile_duplicate_percent
            )

        column_uniqueness_profiling_checks_spec = cls(
            profile_distinct_count=profile_distinct_count,
            profile_distinct_percent=profile_distinct_percent,
            profile_duplicate_count=profile_duplicate_count,
            profile_duplicate_percent=profile_duplicate_percent,
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
