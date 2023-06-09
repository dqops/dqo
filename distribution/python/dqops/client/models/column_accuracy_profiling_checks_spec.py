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


T = TypeVar("T", bound="ColumnAccuracyProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnAccuracyProfilingChecksSpec:
    """
    Attributes:
        total_sum_match_percent (Union[Unset, ColumnAccuracyTotalSumMatchPercentCheckSpec]):
        min_match_percent (Union[Unset, ColumnAccuracyMinMatchPercentCheckSpec]):
        max_match_percent (Union[Unset, ColumnAccuracyMaxMatchPercentCheckSpec]):
        average_match_percent (Union[Unset, ColumnAccuracyAverageMatchPercentCheckSpec]):
        not_null_count_match_percent (Union[Unset, ColumnAccuracyNotNullCountMatchPercentCheckSpec]):
    """

    total_sum_match_percent: Union[
        Unset, "ColumnAccuracyTotalSumMatchPercentCheckSpec"
    ] = UNSET
    min_match_percent: Union[Unset, "ColumnAccuracyMinMatchPercentCheckSpec"] = UNSET
    max_match_percent: Union[Unset, "ColumnAccuracyMaxMatchPercentCheckSpec"] = UNSET
    average_match_percent: Union[
        Unset, "ColumnAccuracyAverageMatchPercentCheckSpec"
    ] = UNSET
    not_null_count_match_percent: Union[
        Unset, "ColumnAccuracyNotNullCountMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        total_sum_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.total_sum_match_percent, Unset):
            total_sum_match_percent = self.total_sum_match_percent.to_dict()

        min_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.min_match_percent, Unset):
            min_match_percent = self.min_match_percent.to_dict()

        max_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.max_match_percent, Unset):
            max_match_percent = self.max_match_percent.to_dict()

        average_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.average_match_percent, Unset):
            average_match_percent = self.average_match_percent.to_dict()

        not_null_count_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.not_null_count_match_percent, Unset):
            not_null_count_match_percent = self.not_null_count_match_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if total_sum_match_percent is not UNSET:
            field_dict["total_sum_match_percent"] = total_sum_match_percent
        if min_match_percent is not UNSET:
            field_dict["min_match_percent"] = min_match_percent
        if max_match_percent is not UNSET:
            field_dict["max_match_percent"] = max_match_percent
        if average_match_percent is not UNSET:
            field_dict["average_match_percent"] = average_match_percent
        if not_null_count_match_percent is not UNSET:
            field_dict["not_null_count_match_percent"] = not_null_count_match_percent

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
        _total_sum_match_percent = d.pop("total_sum_match_percent", UNSET)
        total_sum_match_percent: Union[
            Unset, ColumnAccuracyTotalSumMatchPercentCheckSpec
        ]
        if isinstance(_total_sum_match_percent, Unset):
            total_sum_match_percent = UNSET
        else:
            total_sum_match_percent = (
                ColumnAccuracyTotalSumMatchPercentCheckSpec.from_dict(
                    _total_sum_match_percent
                )
            )

        _min_match_percent = d.pop("min_match_percent", UNSET)
        min_match_percent: Union[Unset, ColumnAccuracyMinMatchPercentCheckSpec]
        if isinstance(_min_match_percent, Unset):
            min_match_percent = UNSET
        else:
            min_match_percent = ColumnAccuracyMinMatchPercentCheckSpec.from_dict(
                _min_match_percent
            )

        _max_match_percent = d.pop("max_match_percent", UNSET)
        max_match_percent: Union[Unset, ColumnAccuracyMaxMatchPercentCheckSpec]
        if isinstance(_max_match_percent, Unset):
            max_match_percent = UNSET
        else:
            max_match_percent = ColumnAccuracyMaxMatchPercentCheckSpec.from_dict(
                _max_match_percent
            )

        _average_match_percent = d.pop("average_match_percent", UNSET)
        average_match_percent: Union[Unset, ColumnAccuracyAverageMatchPercentCheckSpec]
        if isinstance(_average_match_percent, Unset):
            average_match_percent = UNSET
        else:
            average_match_percent = (
                ColumnAccuracyAverageMatchPercentCheckSpec.from_dict(
                    _average_match_percent
                )
            )

        _not_null_count_match_percent = d.pop("not_null_count_match_percent", UNSET)
        not_null_count_match_percent: Union[
            Unset, ColumnAccuracyNotNullCountMatchPercentCheckSpec
        ]
        if isinstance(_not_null_count_match_percent, Unset):
            not_null_count_match_percent = UNSET
        else:
            not_null_count_match_percent = (
                ColumnAccuracyNotNullCountMatchPercentCheckSpec.from_dict(
                    _not_null_count_match_percent
                )
            )

        column_accuracy_profiling_checks_spec = cls(
            total_sum_match_percent=total_sum_match_percent,
            min_match_percent=min_match_percent,
            max_match_percent=max_match_percent,
            average_match_percent=average_match_percent,
            not_null_count_match_percent=not_null_count_match_percent,
        )

        column_accuracy_profiling_checks_spec.additional_properties = d
        return column_accuracy_profiling_checks_spec

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
