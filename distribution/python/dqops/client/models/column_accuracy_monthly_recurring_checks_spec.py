from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accuracy_average_match_percent_check_spec import (
        ColumnAccuracyAverageMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_max_match_percent_check_spec import (
        ColumnAccuracyMaxMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_min_match_percent_check_spec import (
        ColumnAccuracyMinMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_not_null_count_match_percent_check_spec import (
        ColumnAccuracyNotNullCountMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_total_sum_match_percent_check_spec import (
        ColumnAccuracyTotalSumMatchPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnAccuracyMonthlyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnAccuracyMonthlyRecurringChecksSpec:
    """
    Attributes:
        monthly_total_sum_match_percent (Union[Unset, ColumnAccuracyTotalSumMatchPercentCheckSpec]):
        monthly_min_match_percent (Union[Unset, ColumnAccuracyMinMatchPercentCheckSpec]):
        monthly_max_match_percent (Union[Unset, ColumnAccuracyMaxMatchPercentCheckSpec]):
        monthly_average_match_percent (Union[Unset, ColumnAccuracyAverageMatchPercentCheckSpec]):
        monthly_not_null_count_match_percent (Union[Unset, ColumnAccuracyNotNullCountMatchPercentCheckSpec]):
    """

    monthly_total_sum_match_percent: Union[
        Unset, "ColumnAccuracyTotalSumMatchPercentCheckSpec"
    ] = UNSET
    monthly_min_match_percent: Union[
        Unset, "ColumnAccuracyMinMatchPercentCheckSpec"
    ] = UNSET
    monthly_max_match_percent: Union[
        Unset, "ColumnAccuracyMaxMatchPercentCheckSpec"
    ] = UNSET
    monthly_average_match_percent: Union[
        Unset, "ColumnAccuracyAverageMatchPercentCheckSpec"
    ] = UNSET
    monthly_not_null_count_match_percent: Union[
        Unset, "ColumnAccuracyNotNullCountMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_total_sum_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_total_sum_match_percent, Unset):
            monthly_total_sum_match_percent = (
                self.monthly_total_sum_match_percent.to_dict()
            )

        monthly_min_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_min_match_percent, Unset):
            monthly_min_match_percent = self.monthly_min_match_percent.to_dict()

        monthly_max_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_max_match_percent, Unset):
            monthly_max_match_percent = self.monthly_max_match_percent.to_dict()

        monthly_average_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_average_match_percent, Unset):
            monthly_average_match_percent = self.monthly_average_match_percent.to_dict()

        monthly_not_null_count_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_not_null_count_match_percent, Unset):
            monthly_not_null_count_match_percent = (
                self.monthly_not_null_count_match_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_total_sum_match_percent is not UNSET:
            field_dict[
                "monthly_total_sum_match_percent"
            ] = monthly_total_sum_match_percent
        if monthly_min_match_percent is not UNSET:
            field_dict["monthly_min_match_percent"] = monthly_min_match_percent
        if monthly_max_match_percent is not UNSET:
            field_dict["monthly_max_match_percent"] = monthly_max_match_percent
        if monthly_average_match_percent is not UNSET:
            field_dict["monthly_average_match_percent"] = monthly_average_match_percent
        if monthly_not_null_count_match_percent is not UNSET:
            field_dict[
                "monthly_not_null_count_match_percent"
            ] = monthly_not_null_count_match_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_accuracy_average_match_percent_check_spec import (
            ColumnAccuracyAverageMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_max_match_percent_check_spec import (
            ColumnAccuracyMaxMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_min_match_percent_check_spec import (
            ColumnAccuracyMinMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_not_null_count_match_percent_check_spec import (
            ColumnAccuracyNotNullCountMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_total_sum_match_percent_check_spec import (
            ColumnAccuracyTotalSumMatchPercentCheckSpec,
        )

        d = src_dict.copy()
        _monthly_total_sum_match_percent = d.pop(
            "monthly_total_sum_match_percent", UNSET
        )
        monthly_total_sum_match_percent: Union[
            Unset, ColumnAccuracyTotalSumMatchPercentCheckSpec
        ]
        if isinstance(_monthly_total_sum_match_percent, Unset):
            monthly_total_sum_match_percent = UNSET
        else:
            monthly_total_sum_match_percent = (
                ColumnAccuracyTotalSumMatchPercentCheckSpec.from_dict(
                    _monthly_total_sum_match_percent
                )
            )

        _monthly_min_match_percent = d.pop("monthly_min_match_percent", UNSET)
        monthly_min_match_percent: Union[Unset, ColumnAccuracyMinMatchPercentCheckSpec]
        if isinstance(_monthly_min_match_percent, Unset):
            monthly_min_match_percent = UNSET
        else:
            monthly_min_match_percent = (
                ColumnAccuracyMinMatchPercentCheckSpec.from_dict(
                    _monthly_min_match_percent
                )
            )

        _monthly_max_match_percent = d.pop("monthly_max_match_percent", UNSET)
        monthly_max_match_percent: Union[Unset, ColumnAccuracyMaxMatchPercentCheckSpec]
        if isinstance(_monthly_max_match_percent, Unset):
            monthly_max_match_percent = UNSET
        else:
            monthly_max_match_percent = (
                ColumnAccuracyMaxMatchPercentCheckSpec.from_dict(
                    _monthly_max_match_percent
                )
            )

        _monthly_average_match_percent = d.pop("monthly_average_match_percent", UNSET)
        monthly_average_match_percent: Union[
            Unset, ColumnAccuracyAverageMatchPercentCheckSpec
        ]
        if isinstance(_monthly_average_match_percent, Unset):
            monthly_average_match_percent = UNSET
        else:
            monthly_average_match_percent = (
                ColumnAccuracyAverageMatchPercentCheckSpec.from_dict(
                    _monthly_average_match_percent
                )
            )

        _monthly_not_null_count_match_percent = d.pop(
            "monthly_not_null_count_match_percent", UNSET
        )
        monthly_not_null_count_match_percent: Union[
            Unset, ColumnAccuracyNotNullCountMatchPercentCheckSpec
        ]
        if isinstance(_monthly_not_null_count_match_percent, Unset):
            monthly_not_null_count_match_percent = UNSET
        else:
            monthly_not_null_count_match_percent = (
                ColumnAccuracyNotNullCountMatchPercentCheckSpec.from_dict(
                    _monthly_not_null_count_match_percent
                )
            )

        column_accuracy_monthly_recurring_checks_spec = cls(
            monthly_total_sum_match_percent=monthly_total_sum_match_percent,
            monthly_min_match_percent=monthly_min_match_percent,
            monthly_max_match_percent=monthly_max_match_percent,
            monthly_average_match_percent=monthly_average_match_percent,
            monthly_not_null_count_match_percent=monthly_not_null_count_match_percent,
        )

        column_accuracy_monthly_recurring_checks_spec.additional_properties = d
        return column_accuracy_monthly_recurring_checks_spec

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
