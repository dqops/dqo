from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_comparison_max_match_check_spec import (
        ColumnComparisonMaxMatchCheckSpec,
    )
    from ..models.column_comparison_mean_match_check_spec import (
        ColumnComparisonMeanMatchCheckSpec,
    )
    from ..models.column_comparison_min_match_check_spec import (
        ColumnComparisonMinMatchCheckSpec,
    )
    from ..models.column_comparison_not_null_count_match_check_spec import (
        ColumnComparisonNotNullCountMatchCheckSpec,
    )
    from ..models.column_comparison_null_count_match_check_spec import (
        ColumnComparisonNullCountMatchCheckSpec,
    )
    from ..models.column_comparison_sum_match_check_spec import (
        ColumnComparisonSumMatchCheckSpec,
    )


T = TypeVar("T", bound="ColumnComparisonProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnComparisonProfilingChecksSpec:
    """
    Attributes:
        reference_column (Union[Unset, str]): The name of the reference column name in the reference table. It is the
            column to which the current column is compared to.
        sum_match (Union[Unset, ColumnComparisonSumMatchCheckSpec]):
        min_match (Union[Unset, ColumnComparisonMinMatchCheckSpec]):
        max_match (Union[Unset, ColumnComparisonMaxMatchCheckSpec]):
        mean_match (Union[Unset, ColumnComparisonMeanMatchCheckSpec]):
        not_null_count_match (Union[Unset, ColumnComparisonNotNullCountMatchCheckSpec]):
        null_count_match (Union[Unset, ColumnComparisonNullCountMatchCheckSpec]):
    """

    reference_column: Union[Unset, str] = UNSET
    sum_match: Union[Unset, "ColumnComparisonSumMatchCheckSpec"] = UNSET
    min_match: Union[Unset, "ColumnComparisonMinMatchCheckSpec"] = UNSET
    max_match: Union[Unset, "ColumnComparisonMaxMatchCheckSpec"] = UNSET
    mean_match: Union[Unset, "ColumnComparisonMeanMatchCheckSpec"] = UNSET
    not_null_count_match: Union[
        Unset, "ColumnComparisonNotNullCountMatchCheckSpec"
    ] = UNSET
    null_count_match: Union[Unset, "ColumnComparisonNullCountMatchCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        reference_column = self.reference_column
        sum_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sum_match, Unset):
            sum_match = self.sum_match.to_dict()

        min_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.min_match, Unset):
            min_match = self.min_match.to_dict()

        max_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.max_match, Unset):
            max_match = self.max_match.to_dict()

        mean_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mean_match, Unset):
            mean_match = self.mean_match.to_dict()

        not_null_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.not_null_count_match, Unset):
            not_null_count_match = self.not_null_count_match.to_dict()

        null_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.null_count_match, Unset):
            null_count_match = self.null_count_match.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if reference_column is not UNSET:
            field_dict["reference_column"] = reference_column
        if sum_match is not UNSET:
            field_dict["sum_match"] = sum_match
        if min_match is not UNSET:
            field_dict["min_match"] = min_match
        if max_match is not UNSET:
            field_dict["max_match"] = max_match
        if mean_match is not UNSET:
            field_dict["mean_match"] = mean_match
        if not_null_count_match is not UNSET:
            field_dict["not_null_count_match"] = not_null_count_match
        if null_count_match is not UNSET:
            field_dict["null_count_match"] = null_count_match

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_comparison_max_match_check_spec import (
            ColumnComparisonMaxMatchCheckSpec,
        )
        from ..models.column_comparison_mean_match_check_spec import (
            ColumnComparisonMeanMatchCheckSpec,
        )
        from ..models.column_comparison_min_match_check_spec import (
            ColumnComparisonMinMatchCheckSpec,
        )
        from ..models.column_comparison_not_null_count_match_check_spec import (
            ColumnComparisonNotNullCountMatchCheckSpec,
        )
        from ..models.column_comparison_null_count_match_check_spec import (
            ColumnComparisonNullCountMatchCheckSpec,
        )
        from ..models.column_comparison_sum_match_check_spec import (
            ColumnComparisonSumMatchCheckSpec,
        )

        d = src_dict.copy()
        reference_column = d.pop("reference_column", UNSET)

        _sum_match = d.pop("sum_match", UNSET)
        sum_match: Union[Unset, ColumnComparisonSumMatchCheckSpec]
        if isinstance(_sum_match, Unset):
            sum_match = UNSET
        else:
            sum_match = ColumnComparisonSumMatchCheckSpec.from_dict(_sum_match)

        _min_match = d.pop("min_match", UNSET)
        min_match: Union[Unset, ColumnComparisonMinMatchCheckSpec]
        if isinstance(_min_match, Unset):
            min_match = UNSET
        else:
            min_match = ColumnComparisonMinMatchCheckSpec.from_dict(_min_match)

        _max_match = d.pop("max_match", UNSET)
        max_match: Union[Unset, ColumnComparisonMaxMatchCheckSpec]
        if isinstance(_max_match, Unset):
            max_match = UNSET
        else:
            max_match = ColumnComparisonMaxMatchCheckSpec.from_dict(_max_match)

        _mean_match = d.pop("mean_match", UNSET)
        mean_match: Union[Unset, ColumnComparisonMeanMatchCheckSpec]
        if isinstance(_mean_match, Unset):
            mean_match = UNSET
        else:
            mean_match = ColumnComparisonMeanMatchCheckSpec.from_dict(_mean_match)

        _not_null_count_match = d.pop("not_null_count_match", UNSET)
        not_null_count_match: Union[Unset, ColumnComparisonNotNullCountMatchCheckSpec]
        if isinstance(_not_null_count_match, Unset):
            not_null_count_match = UNSET
        else:
            not_null_count_match = ColumnComparisonNotNullCountMatchCheckSpec.from_dict(
                _not_null_count_match
            )

        _null_count_match = d.pop("null_count_match", UNSET)
        null_count_match: Union[Unset, ColumnComparisonNullCountMatchCheckSpec]
        if isinstance(_null_count_match, Unset):
            null_count_match = UNSET
        else:
            null_count_match = ColumnComparisonNullCountMatchCheckSpec.from_dict(
                _null_count_match
            )

        column_comparison_profiling_checks_spec = cls(
            reference_column=reference_column,
            sum_match=sum_match,
            min_match=min_match,
            max_match=max_match,
            mean_match=mean_match,
            not_null_count_match=not_null_count_match,
            null_count_match=null_count_match,
        )

        column_comparison_profiling_checks_spec.additional_properties = d
        return column_comparison_profiling_checks_spec

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
