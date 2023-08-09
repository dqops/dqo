from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_false_percent_check_spec import ColumnFalsePercentCheckSpec
    from ..models.column_true_percent_check_spec import ColumnTruePercentCheckSpec


T = TypeVar("T", bound="ColumnBoolProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnBoolProfilingChecksSpec:
    """
    Attributes:
        profile_true_percent (Union[Unset, ColumnTruePercentCheckSpec]):
        profile_false_percent (Union[Unset, ColumnFalsePercentCheckSpec]):
    """

    profile_true_percent: Union[Unset, "ColumnTruePercentCheckSpec"] = UNSET
    profile_false_percent: Union[Unset, "ColumnFalsePercentCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_true_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_true_percent, Unset):
            profile_true_percent = self.profile_true_percent.to_dict()

        profile_false_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_false_percent, Unset):
            profile_false_percent = self.profile_false_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_true_percent is not UNSET:
            field_dict["profile_true_percent"] = profile_true_percent
        if profile_false_percent is not UNSET:
            field_dict["profile_false_percent"] = profile_false_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_false_percent_check_spec import ColumnFalsePercentCheckSpec
        from ..models.column_true_percent_check_spec import ColumnTruePercentCheckSpec

        d = src_dict.copy()
        _profile_true_percent = d.pop("profile_true_percent", UNSET)
        profile_true_percent: Union[Unset, ColumnTruePercentCheckSpec]
        if isinstance(_profile_true_percent, Unset):
            profile_true_percent = UNSET
        else:
            profile_true_percent = ColumnTruePercentCheckSpec.from_dict(
                _profile_true_percent
            )

        _profile_false_percent = d.pop("profile_false_percent", UNSET)
        profile_false_percent: Union[Unset, ColumnFalsePercentCheckSpec]
        if isinstance(_profile_false_percent, Unset):
            profile_false_percent = UNSET
        else:
            profile_false_percent = ColumnFalsePercentCheckSpec.from_dict(
                _profile_false_percent
            )

        column_bool_profiling_checks_spec = cls(
            profile_true_percent=profile_true_percent,
            profile_false_percent=profile_false_percent,
        )

        column_bool_profiling_checks_spec.additional_properties = d
        return column_bool_profiling_checks_spec

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
