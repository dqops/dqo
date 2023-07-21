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
        true_percent (Union[Unset, ColumnTruePercentCheckSpec]):
        false_percent (Union[Unset, ColumnFalsePercentCheckSpec]):
    """

    true_percent: Union[Unset, "ColumnTruePercentCheckSpec"] = UNSET
    false_percent: Union[Unset, "ColumnFalsePercentCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        true_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.true_percent, Unset):
            true_percent = self.true_percent.to_dict()

        false_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.false_percent, Unset):
            false_percent = self.false_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if true_percent is not UNSET:
            field_dict["true_percent"] = true_percent
        if false_percent is not UNSET:
            field_dict["false_percent"] = false_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_false_percent_check_spec import ColumnFalsePercentCheckSpec
        from ..models.column_true_percent_check_spec import ColumnTruePercentCheckSpec

        d = src_dict.copy()
        _true_percent = d.pop("true_percent", UNSET)
        true_percent: Union[Unset, ColumnTruePercentCheckSpec]
        if isinstance(_true_percent, Unset):
            true_percent = UNSET
        else:
            true_percent = ColumnTruePercentCheckSpec.from_dict(_true_percent)

        _false_percent = d.pop("false_percent", UNSET)
        false_percent: Union[Unset, ColumnFalsePercentCheckSpec]
        if isinstance(_false_percent, Unset):
            false_percent = UNSET
        else:
            false_percent = ColumnFalsePercentCheckSpec.from_dict(_false_percent)

        column_bool_profiling_checks_spec = cls(
            true_percent=true_percent,
            false_percent=false_percent,
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
