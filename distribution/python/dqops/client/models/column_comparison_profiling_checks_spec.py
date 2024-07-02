from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

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
    from ..models.column_comparison_profiling_checks_spec_custom_checks import (
        ColumnComparisonProfilingChecksSpecCustomChecks,
    )
    from ..models.column_comparison_sum_match_check_spec import (
        ColumnComparisonSumMatchCheckSpec,
    )


T = TypeVar("T", bound="ColumnComparisonProfilingChecksSpec")


@_attrs_define
class ColumnComparisonProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnComparisonProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        reference_column (Union[Unset, str]): The name of the reference column name in the reference table. It is the
            column to which the current column is compared to.
        profile_sum_match (Union[Unset, ColumnComparisonSumMatchCheckSpec]):
        profile_min_match (Union[Unset, ColumnComparisonMinMatchCheckSpec]):
        profile_max_match (Union[Unset, ColumnComparisonMaxMatchCheckSpec]):
        profile_mean_match (Union[Unset, ColumnComparisonMeanMatchCheckSpec]):
        profile_not_null_count_match (Union[Unset, ColumnComparisonNotNullCountMatchCheckSpec]):
        profile_null_count_match (Union[Unset, ColumnComparisonNullCountMatchCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnComparisonProfilingChecksSpecCustomChecks"] = (
        UNSET
    )
    reference_column: Union[Unset, str] = UNSET
    profile_sum_match: Union[Unset, "ColumnComparisonSumMatchCheckSpec"] = UNSET
    profile_min_match: Union[Unset, "ColumnComparisonMinMatchCheckSpec"] = UNSET
    profile_max_match: Union[Unset, "ColumnComparisonMaxMatchCheckSpec"] = UNSET
    profile_mean_match: Union[Unset, "ColumnComparisonMeanMatchCheckSpec"] = UNSET
    profile_not_null_count_match: Union[
        Unset, "ColumnComparisonNotNullCountMatchCheckSpec"
    ] = UNSET
    profile_null_count_match: Union[
        Unset, "ColumnComparisonNullCountMatchCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        reference_column = self.reference_column
        profile_sum_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_match, Unset):
            profile_sum_match = self.profile_sum_match.to_dict()

        profile_min_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_min_match, Unset):
            profile_min_match = self.profile_min_match.to_dict()

        profile_max_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_max_match, Unset):
            profile_max_match = self.profile_max_match.to_dict()

        profile_mean_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_match, Unset):
            profile_mean_match = self.profile_mean_match.to_dict()

        profile_not_null_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_not_null_count_match, Unset):
            profile_not_null_count_match = self.profile_not_null_count_match.to_dict()

        profile_null_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_null_count_match, Unset):
            profile_null_count_match = self.profile_null_count_match.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if reference_column is not UNSET:
            field_dict["reference_column"] = reference_column
        if profile_sum_match is not UNSET:
            field_dict["profile_sum_match"] = profile_sum_match
        if profile_min_match is not UNSET:
            field_dict["profile_min_match"] = profile_min_match
        if profile_max_match is not UNSET:
            field_dict["profile_max_match"] = profile_max_match
        if profile_mean_match is not UNSET:
            field_dict["profile_mean_match"] = profile_mean_match
        if profile_not_null_count_match is not UNSET:
            field_dict["profile_not_null_count_match"] = profile_not_null_count_match
        if profile_null_count_match is not UNSET:
            field_dict["profile_null_count_match"] = profile_null_count_match

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
        from ..models.column_comparison_profiling_checks_spec_custom_checks import (
            ColumnComparisonProfilingChecksSpecCustomChecks,
        )
        from ..models.column_comparison_sum_match_check_spec import (
            ColumnComparisonSumMatchCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnComparisonProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnComparisonProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        reference_column = d.pop("reference_column", UNSET)

        _profile_sum_match = d.pop("profile_sum_match", UNSET)
        profile_sum_match: Union[Unset, ColumnComparisonSumMatchCheckSpec]
        if isinstance(_profile_sum_match, Unset):
            profile_sum_match = UNSET
        else:
            profile_sum_match = ColumnComparisonSumMatchCheckSpec.from_dict(
                _profile_sum_match
            )

        _profile_min_match = d.pop("profile_min_match", UNSET)
        profile_min_match: Union[Unset, ColumnComparisonMinMatchCheckSpec]
        if isinstance(_profile_min_match, Unset):
            profile_min_match = UNSET
        else:
            profile_min_match = ColumnComparisonMinMatchCheckSpec.from_dict(
                _profile_min_match
            )

        _profile_max_match = d.pop("profile_max_match", UNSET)
        profile_max_match: Union[Unset, ColumnComparisonMaxMatchCheckSpec]
        if isinstance(_profile_max_match, Unset):
            profile_max_match = UNSET
        else:
            profile_max_match = ColumnComparisonMaxMatchCheckSpec.from_dict(
                _profile_max_match
            )

        _profile_mean_match = d.pop("profile_mean_match", UNSET)
        profile_mean_match: Union[Unset, ColumnComparisonMeanMatchCheckSpec]
        if isinstance(_profile_mean_match, Unset):
            profile_mean_match = UNSET
        else:
            profile_mean_match = ColumnComparisonMeanMatchCheckSpec.from_dict(
                _profile_mean_match
            )

        _profile_not_null_count_match = d.pop("profile_not_null_count_match", UNSET)
        profile_not_null_count_match: Union[
            Unset, ColumnComparisonNotNullCountMatchCheckSpec
        ]
        if isinstance(_profile_not_null_count_match, Unset):
            profile_not_null_count_match = UNSET
        else:
            profile_not_null_count_match = (
                ColumnComparisonNotNullCountMatchCheckSpec.from_dict(
                    _profile_not_null_count_match
                )
            )

        _profile_null_count_match = d.pop("profile_null_count_match", UNSET)
        profile_null_count_match: Union[Unset, ColumnComparisonNullCountMatchCheckSpec]
        if isinstance(_profile_null_count_match, Unset):
            profile_null_count_match = UNSET
        else:
            profile_null_count_match = (
                ColumnComparisonNullCountMatchCheckSpec.from_dict(
                    _profile_null_count_match
                )
            )

        column_comparison_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            reference_column=reference_column,
            profile_sum_match=profile_sum_match,
            profile_min_match=profile_min_match,
            profile_max_match=profile_max_match,
            profile_mean_match=profile_mean_match,
            profile_not_null_count_match=profile_not_null_count_match,
            profile_null_count_match=profile_null_count_match,
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
