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
    from ..models.column_comparison_monthly_monitoring_checks_spec_custom_checks import (
        ColumnComparisonMonthlyMonitoringChecksSpecCustomChecks,
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


T = TypeVar("T", bound="ColumnComparisonMonthlyMonitoringChecksSpec")


@_attrs_define
class ColumnComparisonMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnComparisonMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        reference_column (Union[Unset, str]): The name of the reference column name in the reference table. It is the
            column to which the current column is compared to.
        monthly_sum_match (Union[Unset, ColumnComparisonSumMatchCheckSpec]):
        monthly_min_match (Union[Unset, ColumnComparisonMinMatchCheckSpec]):
        monthly_max_match (Union[Unset, ColumnComparisonMaxMatchCheckSpec]):
        monthly_mean_match (Union[Unset, ColumnComparisonMeanMatchCheckSpec]):
        monthly_not_null_count_match (Union[Unset, ColumnComparisonNotNullCountMatchCheckSpec]):
        monthly_null_count_match (Union[Unset, ColumnComparisonNullCountMatchCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnComparisonMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    reference_column: Union[Unset, str] = UNSET
    monthly_sum_match: Union[Unset, "ColumnComparisonSumMatchCheckSpec"] = UNSET
    monthly_min_match: Union[Unset, "ColumnComparisonMinMatchCheckSpec"] = UNSET
    monthly_max_match: Union[Unset, "ColumnComparisonMaxMatchCheckSpec"] = UNSET
    monthly_mean_match: Union[Unset, "ColumnComparisonMeanMatchCheckSpec"] = UNSET
    monthly_not_null_count_match: Union[
        Unset, "ColumnComparisonNotNullCountMatchCheckSpec"
    ] = UNSET
    monthly_null_count_match: Union[
        Unset, "ColumnComparisonNullCountMatchCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        reference_column = self.reference_column
        monthly_sum_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_sum_match, Unset):
            monthly_sum_match = self.monthly_sum_match.to_dict()

        monthly_min_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_min_match, Unset):
            monthly_min_match = self.monthly_min_match.to_dict()

        monthly_max_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_max_match, Unset):
            monthly_max_match = self.monthly_max_match.to_dict()

        monthly_mean_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_mean_match, Unset):
            monthly_mean_match = self.monthly_mean_match.to_dict()

        monthly_not_null_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_not_null_count_match, Unset):
            monthly_not_null_count_match = self.monthly_not_null_count_match.to_dict()

        monthly_null_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_null_count_match, Unset):
            monthly_null_count_match = self.monthly_null_count_match.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if reference_column is not UNSET:
            field_dict["reference_column"] = reference_column
        if monthly_sum_match is not UNSET:
            field_dict["monthly_sum_match"] = monthly_sum_match
        if monthly_min_match is not UNSET:
            field_dict["monthly_min_match"] = monthly_min_match
        if monthly_max_match is not UNSET:
            field_dict["monthly_max_match"] = monthly_max_match
        if monthly_mean_match is not UNSET:
            field_dict["monthly_mean_match"] = monthly_mean_match
        if monthly_not_null_count_match is not UNSET:
            field_dict["monthly_not_null_count_match"] = monthly_not_null_count_match
        if monthly_null_count_match is not UNSET:
            field_dict["monthly_null_count_match"] = monthly_null_count_match

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
        from ..models.column_comparison_monthly_monitoring_checks_spec_custom_checks import (
            ColumnComparisonMonthlyMonitoringChecksSpecCustomChecks,
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
            Unset, ColumnComparisonMonthlyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnComparisonMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        reference_column = d.pop("reference_column", UNSET)

        _monthly_sum_match = d.pop("monthly_sum_match", UNSET)
        monthly_sum_match: Union[Unset, ColumnComparisonSumMatchCheckSpec]
        if isinstance(_monthly_sum_match, Unset):
            monthly_sum_match = UNSET
        else:
            monthly_sum_match = ColumnComparisonSumMatchCheckSpec.from_dict(
                _monthly_sum_match
            )

        _monthly_min_match = d.pop("monthly_min_match", UNSET)
        monthly_min_match: Union[Unset, ColumnComparisonMinMatchCheckSpec]
        if isinstance(_monthly_min_match, Unset):
            monthly_min_match = UNSET
        else:
            monthly_min_match = ColumnComparisonMinMatchCheckSpec.from_dict(
                _monthly_min_match
            )

        _monthly_max_match = d.pop("monthly_max_match", UNSET)
        monthly_max_match: Union[Unset, ColumnComparisonMaxMatchCheckSpec]
        if isinstance(_monthly_max_match, Unset):
            monthly_max_match = UNSET
        else:
            monthly_max_match = ColumnComparisonMaxMatchCheckSpec.from_dict(
                _monthly_max_match
            )

        _monthly_mean_match = d.pop("monthly_mean_match", UNSET)
        monthly_mean_match: Union[Unset, ColumnComparisonMeanMatchCheckSpec]
        if isinstance(_monthly_mean_match, Unset):
            monthly_mean_match = UNSET
        else:
            monthly_mean_match = ColumnComparisonMeanMatchCheckSpec.from_dict(
                _monthly_mean_match
            )

        _monthly_not_null_count_match = d.pop("monthly_not_null_count_match", UNSET)
        monthly_not_null_count_match: Union[
            Unset, ColumnComparisonNotNullCountMatchCheckSpec
        ]
        if isinstance(_monthly_not_null_count_match, Unset):
            monthly_not_null_count_match = UNSET
        else:
            monthly_not_null_count_match = (
                ColumnComparisonNotNullCountMatchCheckSpec.from_dict(
                    _monthly_not_null_count_match
                )
            )

        _monthly_null_count_match = d.pop("monthly_null_count_match", UNSET)
        monthly_null_count_match: Union[Unset, ColumnComparisonNullCountMatchCheckSpec]
        if isinstance(_monthly_null_count_match, Unset):
            monthly_null_count_match = UNSET
        else:
            monthly_null_count_match = (
                ColumnComparisonNullCountMatchCheckSpec.from_dict(
                    _monthly_null_count_match
                )
            )

        column_comparison_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            reference_column=reference_column,
            monthly_sum_match=monthly_sum_match,
            monthly_min_match=monthly_min_match,
            monthly_max_match=monthly_max_match,
            monthly_mean_match=monthly_mean_match,
            monthly_not_null_count_match=monthly_not_null_count_match,
            monthly_null_count_match=monthly_null_count_match,
        )

        column_comparison_monthly_monitoring_checks_spec.additional_properties = d
        return column_comparison_monthly_monitoring_checks_spec

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
