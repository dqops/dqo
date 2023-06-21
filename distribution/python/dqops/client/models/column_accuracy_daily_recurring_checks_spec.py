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


T = TypeVar("T", bound="ColumnAccuracyDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnAccuracyDailyRecurringChecksSpec:
    """
    Attributes:
        daily_total_sum_match_percent (Union[Unset, ColumnAccuracyTotalSumMatchPercentCheckSpec]):
        daily_min_match_percent (Union[Unset, ColumnAccuracyMinMatchPercentCheckSpec]):
        daily_max_match_percent (Union[Unset, ColumnAccuracyMaxMatchPercentCheckSpec]):
        daily_average_match_percent (Union[Unset, ColumnAccuracyAverageMatchPercentCheckSpec]):
        daily_not_null_count_match_percent (Union[Unset, ColumnAccuracyNotNullCountMatchPercentCheckSpec]):
    """

    daily_total_sum_match_percent: Union[
        Unset, "ColumnAccuracyTotalSumMatchPercentCheckSpec"
    ] = UNSET
    daily_min_match_percent: Union[
        Unset, "ColumnAccuracyMinMatchPercentCheckSpec"
    ] = UNSET
    daily_max_match_percent: Union[
        Unset, "ColumnAccuracyMaxMatchPercentCheckSpec"
    ] = UNSET
    daily_average_match_percent: Union[
        Unset, "ColumnAccuracyAverageMatchPercentCheckSpec"
    ] = UNSET
    daily_not_null_count_match_percent: Union[
        Unset, "ColumnAccuracyNotNullCountMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_total_sum_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_total_sum_match_percent, Unset):
            daily_total_sum_match_percent = self.daily_total_sum_match_percent.to_dict()

        daily_min_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_min_match_percent, Unset):
            daily_min_match_percent = self.daily_min_match_percent.to_dict()

        daily_max_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_max_match_percent, Unset):
            daily_max_match_percent = self.daily_max_match_percent.to_dict()

        daily_average_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_average_match_percent, Unset):
            daily_average_match_percent = self.daily_average_match_percent.to_dict()

        daily_not_null_count_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_not_null_count_match_percent, Unset):
            daily_not_null_count_match_percent = (
                self.daily_not_null_count_match_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_total_sum_match_percent is not UNSET:
            field_dict["daily_total_sum_match_percent"] = daily_total_sum_match_percent
        if daily_min_match_percent is not UNSET:
            field_dict["daily_min_match_percent"] = daily_min_match_percent
        if daily_max_match_percent is not UNSET:
            field_dict["daily_max_match_percent"] = daily_max_match_percent
        if daily_average_match_percent is not UNSET:
            field_dict["daily_average_match_percent"] = daily_average_match_percent
        if daily_not_null_count_match_percent is not UNSET:
            field_dict[
                "daily_not_null_count_match_percent"
            ] = daily_not_null_count_match_percent

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
        _daily_total_sum_match_percent = d.pop("daily_total_sum_match_percent", UNSET)
        daily_total_sum_match_percent: Union[
            Unset, ColumnAccuracyTotalSumMatchPercentCheckSpec
        ]
        if isinstance(_daily_total_sum_match_percent, Unset):
            daily_total_sum_match_percent = UNSET
        else:
            daily_total_sum_match_percent = (
                ColumnAccuracyTotalSumMatchPercentCheckSpec.from_dict(
                    _daily_total_sum_match_percent
                )
            )

        _daily_min_match_percent = d.pop("daily_min_match_percent", UNSET)
        daily_min_match_percent: Union[Unset, ColumnAccuracyMinMatchPercentCheckSpec]
        if isinstance(_daily_min_match_percent, Unset):
            daily_min_match_percent = UNSET
        else:
            daily_min_match_percent = ColumnAccuracyMinMatchPercentCheckSpec.from_dict(
                _daily_min_match_percent
            )

        _daily_max_match_percent = d.pop("daily_max_match_percent", UNSET)
        daily_max_match_percent: Union[Unset, ColumnAccuracyMaxMatchPercentCheckSpec]
        if isinstance(_daily_max_match_percent, Unset):
            daily_max_match_percent = UNSET
        else:
            daily_max_match_percent = ColumnAccuracyMaxMatchPercentCheckSpec.from_dict(
                _daily_max_match_percent
            )

        _daily_average_match_percent = d.pop("daily_average_match_percent", UNSET)
        daily_average_match_percent: Union[
            Unset, ColumnAccuracyAverageMatchPercentCheckSpec
        ]
        if isinstance(_daily_average_match_percent, Unset):
            daily_average_match_percent = UNSET
        else:
            daily_average_match_percent = (
                ColumnAccuracyAverageMatchPercentCheckSpec.from_dict(
                    _daily_average_match_percent
                )
            )

        _daily_not_null_count_match_percent = d.pop(
            "daily_not_null_count_match_percent", UNSET
        )
        daily_not_null_count_match_percent: Union[
            Unset, ColumnAccuracyNotNullCountMatchPercentCheckSpec
        ]
        if isinstance(_daily_not_null_count_match_percent, Unset):
            daily_not_null_count_match_percent = UNSET
        else:
            daily_not_null_count_match_percent = (
                ColumnAccuracyNotNullCountMatchPercentCheckSpec.from_dict(
                    _daily_not_null_count_match_percent
                )
            )

        column_accuracy_daily_recurring_checks_spec = cls(
            daily_total_sum_match_percent=daily_total_sum_match_percent,
            daily_min_match_percent=daily_min_match_percent,
            daily_max_match_percent=daily_max_match_percent,
            daily_average_match_percent=daily_average_match_percent,
            daily_not_null_count_match_percent=daily_not_null_count_match_percent,
        )

        column_accuracy_daily_recurring_checks_spec.additional_properties = d
        return column_accuracy_daily_recurring_checks_spec

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
