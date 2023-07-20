from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_change_mean_check_spec import ColumnChangeMeanCheckSpec
    from ..models.column_change_median_check_spec import ColumnChangeMedianCheckSpec
    from ..models.column_change_sum_check_spec import ColumnChangeSumCheckSpec


T = TypeVar("T", bound="ColumnAnomalyMonthlyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnAnomalyMonthlyRecurringChecksSpec:
    """
    Attributes:
        monthly_mean_change (Union[Unset, ColumnChangeMeanCheckSpec]):
        monthly_median_change (Union[Unset, ColumnChangeMedianCheckSpec]):
        monthly_sum_change (Union[Unset, ColumnChangeSumCheckSpec]):
    """

    monthly_mean_change: Union[Unset, "ColumnChangeMeanCheckSpec"] = UNSET
    monthly_median_change: Union[Unset, "ColumnChangeMedianCheckSpec"] = UNSET
    monthly_sum_change: Union[Unset, "ColumnChangeSumCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_mean_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_mean_change, Unset):
            monthly_mean_change = self.monthly_mean_change.to_dict()

        monthly_median_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_median_change, Unset):
            monthly_median_change = self.monthly_median_change.to_dict()

        monthly_sum_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_sum_change, Unset):
            monthly_sum_change = self.monthly_sum_change.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_mean_change is not UNSET:
            field_dict["monthly_mean_change"] = monthly_mean_change
        if monthly_median_change is not UNSET:
            field_dict["monthly_median_change"] = monthly_median_change
        if monthly_sum_change is not UNSET:
            field_dict["monthly_sum_change"] = monthly_sum_change

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_change_mean_check_spec import ColumnChangeMeanCheckSpec
        from ..models.column_change_median_check_spec import ColumnChangeMedianCheckSpec
        from ..models.column_change_sum_check_spec import ColumnChangeSumCheckSpec

        d = src_dict.copy()
        _monthly_mean_change = d.pop("monthly_mean_change", UNSET)
        monthly_mean_change: Union[Unset, ColumnChangeMeanCheckSpec]
        if isinstance(_monthly_mean_change, Unset):
            monthly_mean_change = UNSET
        else:
            monthly_mean_change = ColumnChangeMeanCheckSpec.from_dict(
                _monthly_mean_change
            )

        _monthly_median_change = d.pop("monthly_median_change", UNSET)
        monthly_median_change: Union[Unset, ColumnChangeMedianCheckSpec]
        if isinstance(_monthly_median_change, Unset):
            monthly_median_change = UNSET
        else:
            monthly_median_change = ColumnChangeMedianCheckSpec.from_dict(
                _monthly_median_change
            )

        _monthly_sum_change = d.pop("monthly_sum_change", UNSET)
        monthly_sum_change: Union[Unset, ColumnChangeSumCheckSpec]
        if isinstance(_monthly_sum_change, Unset):
            monthly_sum_change = UNSET
        else:
            monthly_sum_change = ColumnChangeSumCheckSpec.from_dict(_monthly_sum_change)

        column_anomaly_monthly_recurring_checks_spec = cls(
            monthly_mean_change=monthly_mean_change,
            monthly_median_change=monthly_median_change,
            monthly_sum_change=monthly_sum_change,
        )

        column_anomaly_monthly_recurring_checks_spec.additional_properties = d
        return column_anomaly_monthly_recurring_checks_spec

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
