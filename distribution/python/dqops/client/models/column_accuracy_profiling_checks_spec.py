from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accuracy_total_average_match_percent_check_spec import (
        ColumnAccuracyTotalAverageMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_total_max_match_percent_check_spec import (
        ColumnAccuracyTotalMaxMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_total_min_match_percent_check_spec import (
        ColumnAccuracyTotalMinMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_total_not_null_count_match_percent_check_spec import (
        ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_total_sum_match_percent_check_spec import (
        ColumnAccuracyTotalSumMatchPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnAccuracyProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnAccuracyProfilingChecksSpec:
    """
    Attributes:
        profile_total_sum_match_percent (Union[Unset, ColumnAccuracyTotalSumMatchPercentCheckSpec]):
        profile_total_min_match_percent (Union[Unset, ColumnAccuracyTotalMinMatchPercentCheckSpec]):
        profile_total_max_match_percent (Union[Unset, ColumnAccuracyTotalMaxMatchPercentCheckSpec]):
        profile_total_average_match_percent (Union[Unset, ColumnAccuracyTotalAverageMatchPercentCheckSpec]):
        profile_total_not_null_count_match_percent (Union[Unset, ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec]):
    """

    profile_total_sum_match_percent: Union[
        Unset, "ColumnAccuracyTotalSumMatchPercentCheckSpec"
    ] = UNSET
    profile_total_min_match_percent: Union[
        Unset, "ColumnAccuracyTotalMinMatchPercentCheckSpec"
    ] = UNSET
    profile_total_max_match_percent: Union[
        Unset, "ColumnAccuracyTotalMaxMatchPercentCheckSpec"
    ] = UNSET
    profile_total_average_match_percent: Union[
        Unset, "ColumnAccuracyTotalAverageMatchPercentCheckSpec"
    ] = UNSET
    profile_total_not_null_count_match_percent: Union[
        Unset, "ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_total_sum_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_total_sum_match_percent, Unset):
            profile_total_sum_match_percent = (
                self.profile_total_sum_match_percent.to_dict()
            )

        profile_total_min_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_total_min_match_percent, Unset):
            profile_total_min_match_percent = (
                self.profile_total_min_match_percent.to_dict()
            )

        profile_total_max_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_total_max_match_percent, Unset):
            profile_total_max_match_percent = (
                self.profile_total_max_match_percent.to_dict()
            )

        profile_total_average_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_total_average_match_percent, Unset):
            profile_total_average_match_percent = (
                self.profile_total_average_match_percent.to_dict()
            )

        profile_total_not_null_count_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_total_not_null_count_match_percent, Unset):
            profile_total_not_null_count_match_percent = (
                self.profile_total_not_null_count_match_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_total_sum_match_percent is not UNSET:
            field_dict[
                "profile_total_sum_match_percent"
            ] = profile_total_sum_match_percent
        if profile_total_min_match_percent is not UNSET:
            field_dict[
                "profile_total_min_match_percent"
            ] = profile_total_min_match_percent
        if profile_total_max_match_percent is not UNSET:
            field_dict[
                "profile_total_max_match_percent"
            ] = profile_total_max_match_percent
        if profile_total_average_match_percent is not UNSET:
            field_dict[
                "profile_total_average_match_percent"
            ] = profile_total_average_match_percent
        if profile_total_not_null_count_match_percent is not UNSET:
            field_dict[
                "profile_total_not_null_count_match_percent"
            ] = profile_total_not_null_count_match_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_accuracy_total_average_match_percent_check_spec import (
            ColumnAccuracyTotalAverageMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_total_max_match_percent_check_spec import (
            ColumnAccuracyTotalMaxMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_total_min_match_percent_check_spec import (
            ColumnAccuracyTotalMinMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_total_not_null_count_match_percent_check_spec import (
            ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_total_sum_match_percent_check_spec import (
            ColumnAccuracyTotalSumMatchPercentCheckSpec,
        )

        d = src_dict.copy()
        _profile_total_sum_match_percent = d.pop(
            "profile_total_sum_match_percent", UNSET
        )
        profile_total_sum_match_percent: Union[
            Unset, ColumnAccuracyTotalSumMatchPercentCheckSpec
        ]
        if isinstance(_profile_total_sum_match_percent, Unset):
            profile_total_sum_match_percent = UNSET
        else:
            profile_total_sum_match_percent = (
                ColumnAccuracyTotalSumMatchPercentCheckSpec.from_dict(
                    _profile_total_sum_match_percent
                )
            )

        _profile_total_min_match_percent = d.pop(
            "profile_total_min_match_percent", UNSET
        )
        profile_total_min_match_percent: Union[
            Unset, ColumnAccuracyTotalMinMatchPercentCheckSpec
        ]
        if isinstance(_profile_total_min_match_percent, Unset):
            profile_total_min_match_percent = UNSET
        else:
            profile_total_min_match_percent = (
                ColumnAccuracyTotalMinMatchPercentCheckSpec.from_dict(
                    _profile_total_min_match_percent
                )
            )

        _profile_total_max_match_percent = d.pop(
            "profile_total_max_match_percent", UNSET
        )
        profile_total_max_match_percent: Union[
            Unset, ColumnAccuracyTotalMaxMatchPercentCheckSpec
        ]
        if isinstance(_profile_total_max_match_percent, Unset):
            profile_total_max_match_percent = UNSET
        else:
            profile_total_max_match_percent = (
                ColumnAccuracyTotalMaxMatchPercentCheckSpec.from_dict(
                    _profile_total_max_match_percent
                )
            )

        _profile_total_average_match_percent = d.pop(
            "profile_total_average_match_percent", UNSET
        )
        profile_total_average_match_percent: Union[
            Unset, ColumnAccuracyTotalAverageMatchPercentCheckSpec
        ]
        if isinstance(_profile_total_average_match_percent, Unset):
            profile_total_average_match_percent = UNSET
        else:
            profile_total_average_match_percent = (
                ColumnAccuracyTotalAverageMatchPercentCheckSpec.from_dict(
                    _profile_total_average_match_percent
                )
            )

        _profile_total_not_null_count_match_percent = d.pop(
            "profile_total_not_null_count_match_percent", UNSET
        )
        profile_total_not_null_count_match_percent: Union[
            Unset, ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec
        ]
        if isinstance(_profile_total_not_null_count_match_percent, Unset):
            profile_total_not_null_count_match_percent = UNSET
        else:
            profile_total_not_null_count_match_percent = (
                ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec.from_dict(
                    _profile_total_not_null_count_match_percent
                )
            )

        column_accuracy_profiling_checks_spec = cls(
            profile_total_sum_match_percent=profile_total_sum_match_percent,
            profile_total_min_match_percent=profile_total_min_match_percent,
            profile_total_max_match_percent=profile_total_max_match_percent,
            profile_total_average_match_percent=profile_total_average_match_percent,
            profile_total_not_null_count_match_percent=profile_total_not_null_count_match_percent,
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
