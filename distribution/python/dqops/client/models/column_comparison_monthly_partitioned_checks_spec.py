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
    from ..models.column_comparison_monthly_partitioned_checks_spec_custom_checks import (
        ColumnComparisonMonthlyPartitionedChecksSpecCustomChecks,
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


T = TypeVar("T", bound="ColumnComparisonMonthlyPartitionedChecksSpec")


@_attrs_define
class ColumnComparisonMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnComparisonMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        reference_column (Union[Unset, str]): The name of the reference column name in the reference table. It is the
            column to which the current column is compared to.
        monthly_partition_sum_match (Union[Unset, ColumnComparisonSumMatchCheckSpec]):
        monthly_partition_min_match (Union[Unset, ColumnComparisonMinMatchCheckSpec]):
        monthly_partition_max_match (Union[Unset, ColumnComparisonMaxMatchCheckSpec]):
        monthly_partition_mean_match (Union[Unset, ColumnComparisonMeanMatchCheckSpec]):
        monthly_partition_not_null_count_match (Union[Unset, ColumnComparisonNotNullCountMatchCheckSpec]):
        monthly_partition_null_count_match (Union[Unset, ColumnComparisonNullCountMatchCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnComparisonMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    reference_column: Union[Unset, str] = UNSET
    monthly_partition_sum_match: Union[Unset, "ColumnComparisonSumMatchCheckSpec"] = (
        UNSET
    )
    monthly_partition_min_match: Union[Unset, "ColumnComparisonMinMatchCheckSpec"] = (
        UNSET
    )
    monthly_partition_max_match: Union[Unset, "ColumnComparisonMaxMatchCheckSpec"] = (
        UNSET
    )
    monthly_partition_mean_match: Union[Unset, "ColumnComparisonMeanMatchCheckSpec"] = (
        UNSET
    )
    monthly_partition_not_null_count_match: Union[
        Unset, "ColumnComparisonNotNullCountMatchCheckSpec"
    ] = UNSET
    monthly_partition_null_count_match: Union[
        Unset, "ColumnComparisonNullCountMatchCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        reference_column = self.reference_column
        monthly_partition_sum_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_sum_match, Unset):
            monthly_partition_sum_match = self.monthly_partition_sum_match.to_dict()

        monthly_partition_min_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_min_match, Unset):
            monthly_partition_min_match = self.monthly_partition_min_match.to_dict()

        monthly_partition_max_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_max_match, Unset):
            monthly_partition_max_match = self.monthly_partition_max_match.to_dict()

        monthly_partition_mean_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_mean_match, Unset):
            monthly_partition_mean_match = self.monthly_partition_mean_match.to_dict()

        monthly_partition_not_null_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_not_null_count_match, Unset):
            monthly_partition_not_null_count_match = (
                self.monthly_partition_not_null_count_match.to_dict()
            )

        monthly_partition_null_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_null_count_match, Unset):
            monthly_partition_null_count_match = (
                self.monthly_partition_null_count_match.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if reference_column is not UNSET:
            field_dict["reference_column"] = reference_column
        if monthly_partition_sum_match is not UNSET:
            field_dict["monthly_partition_sum_match"] = monthly_partition_sum_match
        if monthly_partition_min_match is not UNSET:
            field_dict["monthly_partition_min_match"] = monthly_partition_min_match
        if monthly_partition_max_match is not UNSET:
            field_dict["monthly_partition_max_match"] = monthly_partition_max_match
        if monthly_partition_mean_match is not UNSET:
            field_dict["monthly_partition_mean_match"] = monthly_partition_mean_match
        if monthly_partition_not_null_count_match is not UNSET:
            field_dict["monthly_partition_not_null_count_match"] = (
                monthly_partition_not_null_count_match
            )
        if monthly_partition_null_count_match is not UNSET:
            field_dict["monthly_partition_null_count_match"] = (
                monthly_partition_null_count_match
            )

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
        from ..models.column_comparison_monthly_partitioned_checks_spec_custom_checks import (
            ColumnComparisonMonthlyPartitionedChecksSpecCustomChecks,
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
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnComparisonMonthlyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnComparisonMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        reference_column = d.pop("reference_column", UNSET)

        _monthly_partition_sum_match = d.pop("monthly_partition_sum_match", UNSET)
        monthly_partition_sum_match: Union[Unset, ColumnComparisonSumMatchCheckSpec]
        if isinstance(_monthly_partition_sum_match, Unset):
            monthly_partition_sum_match = UNSET
        else:
            monthly_partition_sum_match = ColumnComparisonSumMatchCheckSpec.from_dict(
                _monthly_partition_sum_match
            )

        _monthly_partition_min_match = d.pop("monthly_partition_min_match", UNSET)
        monthly_partition_min_match: Union[Unset, ColumnComparisonMinMatchCheckSpec]
        if isinstance(_monthly_partition_min_match, Unset):
            monthly_partition_min_match = UNSET
        else:
            monthly_partition_min_match = ColumnComparisonMinMatchCheckSpec.from_dict(
                _monthly_partition_min_match
            )

        _monthly_partition_max_match = d.pop("monthly_partition_max_match", UNSET)
        monthly_partition_max_match: Union[Unset, ColumnComparisonMaxMatchCheckSpec]
        if isinstance(_monthly_partition_max_match, Unset):
            monthly_partition_max_match = UNSET
        else:
            monthly_partition_max_match = ColumnComparisonMaxMatchCheckSpec.from_dict(
                _monthly_partition_max_match
            )

        _monthly_partition_mean_match = d.pop("monthly_partition_mean_match", UNSET)
        monthly_partition_mean_match: Union[Unset, ColumnComparisonMeanMatchCheckSpec]
        if isinstance(_monthly_partition_mean_match, Unset):
            monthly_partition_mean_match = UNSET
        else:
            monthly_partition_mean_match = ColumnComparisonMeanMatchCheckSpec.from_dict(
                _monthly_partition_mean_match
            )

        _monthly_partition_not_null_count_match = d.pop(
            "monthly_partition_not_null_count_match", UNSET
        )
        monthly_partition_not_null_count_match: Union[
            Unset, ColumnComparisonNotNullCountMatchCheckSpec
        ]
        if isinstance(_monthly_partition_not_null_count_match, Unset):
            monthly_partition_not_null_count_match = UNSET
        else:
            monthly_partition_not_null_count_match = (
                ColumnComparisonNotNullCountMatchCheckSpec.from_dict(
                    _monthly_partition_not_null_count_match
                )
            )

        _monthly_partition_null_count_match = d.pop(
            "monthly_partition_null_count_match", UNSET
        )
        monthly_partition_null_count_match: Union[
            Unset, ColumnComparisonNullCountMatchCheckSpec
        ]
        if isinstance(_monthly_partition_null_count_match, Unset):
            monthly_partition_null_count_match = UNSET
        else:
            monthly_partition_null_count_match = (
                ColumnComparisonNullCountMatchCheckSpec.from_dict(
                    _monthly_partition_null_count_match
                )
            )

        column_comparison_monthly_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            reference_column=reference_column,
            monthly_partition_sum_match=monthly_partition_sum_match,
            monthly_partition_min_match=monthly_partition_min_match,
            monthly_partition_max_match=monthly_partition_max_match,
            monthly_partition_mean_match=monthly_partition_mean_match,
            monthly_partition_not_null_count_match=monthly_partition_not_null_count_match,
            monthly_partition_null_count_match=monthly_partition_null_count_match,
        )

        column_comparison_monthly_partitioned_checks_spec.additional_properties = d
        return column_comparison_monthly_partitioned_checks_spec

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
