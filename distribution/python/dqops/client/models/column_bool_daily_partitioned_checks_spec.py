from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_false_percent_check_spec import ColumnFalsePercentCheckSpec
    from ..models.column_true_percent_check_spec import ColumnTruePercentCheckSpec


T = TypeVar("T", bound="ColumnBoolDailyPartitionedChecksSpec")


@attr.s(auto_attribs=True)
class ColumnBoolDailyPartitionedChecksSpec:
    """
    Attributes:
        daily_partition_true_percent (Union[Unset, ColumnTruePercentCheckSpec]):
        daily_partition_false_percent (Union[Unset, ColumnFalsePercentCheckSpec]):
    """

    daily_partition_true_percent: Union[Unset, "ColumnTruePercentCheckSpec"] = UNSET
    daily_partition_false_percent: Union[Unset, "ColumnFalsePercentCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_partition_true_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_true_percent, Unset):
            daily_partition_true_percent = self.daily_partition_true_percent.to_dict()

        daily_partition_false_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_false_percent, Unset):
            daily_partition_false_percent = self.daily_partition_false_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_partition_true_percent is not UNSET:
            field_dict["daily_partition_true_percent"] = daily_partition_true_percent
        if daily_partition_false_percent is not UNSET:
            field_dict["daily_partition_false_percent"] = daily_partition_false_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_false_percent_check_spec import ColumnFalsePercentCheckSpec
        from ..models.column_true_percent_check_spec import ColumnTruePercentCheckSpec

        d = src_dict.copy()
        _daily_partition_true_percent = d.pop("daily_partition_true_percent", UNSET)
        daily_partition_true_percent: Union[Unset, ColumnTruePercentCheckSpec]
        if isinstance(_daily_partition_true_percent, Unset):
            daily_partition_true_percent = UNSET
        else:
            daily_partition_true_percent = ColumnTruePercentCheckSpec.from_dict(
                _daily_partition_true_percent
            )

        _daily_partition_false_percent = d.pop("daily_partition_false_percent", UNSET)
        daily_partition_false_percent: Union[Unset, ColumnFalsePercentCheckSpec]
        if isinstance(_daily_partition_false_percent, Unset):
            daily_partition_false_percent = UNSET
        else:
            daily_partition_false_percent = ColumnFalsePercentCheckSpec.from_dict(
                _daily_partition_false_percent
            )

        column_bool_daily_partitioned_checks_spec = cls(
            daily_partition_true_percent=daily_partition_true_percent,
            daily_partition_false_percent=daily_partition_false_percent,
        )

        column_bool_daily_partitioned_checks_spec.additional_properties = d
        return column_bool_daily_partitioned_checks_spec

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
