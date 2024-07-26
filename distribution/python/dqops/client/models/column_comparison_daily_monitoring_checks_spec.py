from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_comparison_daily_monitoring_checks_spec_custom_checks import (
        ColumnComparisonDailyMonitoringChecksSpecCustomChecks,
    )
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


T = TypeVar("T", bound="ColumnComparisonDailyMonitoringChecksSpec")


@_attrs_define
class ColumnComparisonDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnComparisonDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        reference_column (Union[Unset, str]): The name of the reference column name in the reference table. It is the
            column to which the current column is compared to.
        daily_sum_match (Union[Unset, ColumnComparisonSumMatchCheckSpec]):
        daily_min_match (Union[Unset, ColumnComparisonMinMatchCheckSpec]):
        daily_max_match (Union[Unset, ColumnComparisonMaxMatchCheckSpec]):
        daily_mean_match (Union[Unset, ColumnComparisonMeanMatchCheckSpec]):
        daily_not_null_count_match (Union[Unset, ColumnComparisonNotNullCountMatchCheckSpec]):
        daily_null_count_match (Union[Unset, ColumnComparisonNullCountMatchCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnComparisonDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    reference_column: Union[Unset, str] = UNSET
    daily_sum_match: Union[Unset, "ColumnComparisonSumMatchCheckSpec"] = UNSET
    daily_min_match: Union[Unset, "ColumnComparisonMinMatchCheckSpec"] = UNSET
    daily_max_match: Union[Unset, "ColumnComparisonMaxMatchCheckSpec"] = UNSET
    daily_mean_match: Union[Unset, "ColumnComparisonMeanMatchCheckSpec"] = UNSET
    daily_not_null_count_match: Union[
        Unset, "ColumnComparisonNotNullCountMatchCheckSpec"
    ] = UNSET
    daily_null_count_match: Union[Unset, "ColumnComparisonNullCountMatchCheckSpec"] = (
        UNSET
    )
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        reference_column = self.reference_column
        daily_sum_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_match, Unset):
            daily_sum_match = self.daily_sum_match.to_dict()

        daily_min_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_min_match, Unset):
            daily_min_match = self.daily_min_match.to_dict()

        daily_max_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_max_match, Unset):
            daily_max_match = self.daily_max_match.to_dict()

        daily_mean_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_match, Unset):
            daily_mean_match = self.daily_mean_match.to_dict()

        daily_not_null_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_not_null_count_match, Unset):
            daily_not_null_count_match = self.daily_not_null_count_match.to_dict()

        daily_null_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_null_count_match, Unset):
            daily_null_count_match = self.daily_null_count_match.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if reference_column is not UNSET:
            field_dict["reference_column"] = reference_column
        if daily_sum_match is not UNSET:
            field_dict["daily_sum_match"] = daily_sum_match
        if daily_min_match is not UNSET:
            field_dict["daily_min_match"] = daily_min_match
        if daily_max_match is not UNSET:
            field_dict["daily_max_match"] = daily_max_match
        if daily_mean_match is not UNSET:
            field_dict["daily_mean_match"] = daily_mean_match
        if daily_not_null_count_match is not UNSET:
            field_dict["daily_not_null_count_match"] = daily_not_null_count_match
        if daily_null_count_match is not UNSET:
            field_dict["daily_null_count_match"] = daily_null_count_match

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_comparison_daily_monitoring_checks_spec_custom_checks import (
            ColumnComparisonDailyMonitoringChecksSpecCustomChecks,
        )
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
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnComparisonDailyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnComparisonDailyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        reference_column = d.pop("reference_column", UNSET)

        _daily_sum_match = d.pop("daily_sum_match", UNSET)
        daily_sum_match: Union[Unset, ColumnComparisonSumMatchCheckSpec]
        if isinstance(_daily_sum_match, Unset):
            daily_sum_match = UNSET
        else:
            daily_sum_match = ColumnComparisonSumMatchCheckSpec.from_dict(
                _daily_sum_match
            )

        _daily_min_match = d.pop("daily_min_match", UNSET)
        daily_min_match: Union[Unset, ColumnComparisonMinMatchCheckSpec]
        if isinstance(_daily_min_match, Unset):
            daily_min_match = UNSET
        else:
            daily_min_match = ColumnComparisonMinMatchCheckSpec.from_dict(
                _daily_min_match
            )

        _daily_max_match = d.pop("daily_max_match", UNSET)
        daily_max_match: Union[Unset, ColumnComparisonMaxMatchCheckSpec]
        if isinstance(_daily_max_match, Unset):
            daily_max_match = UNSET
        else:
            daily_max_match = ColumnComparisonMaxMatchCheckSpec.from_dict(
                _daily_max_match
            )

        _daily_mean_match = d.pop("daily_mean_match", UNSET)
        daily_mean_match: Union[Unset, ColumnComparisonMeanMatchCheckSpec]
        if isinstance(_daily_mean_match, Unset):
            daily_mean_match = UNSET
        else:
            daily_mean_match = ColumnComparisonMeanMatchCheckSpec.from_dict(
                _daily_mean_match
            )

        _daily_not_null_count_match = d.pop("daily_not_null_count_match", UNSET)
        daily_not_null_count_match: Union[
            Unset, ColumnComparisonNotNullCountMatchCheckSpec
        ]
        if isinstance(_daily_not_null_count_match, Unset):
            daily_not_null_count_match = UNSET
        else:
            daily_not_null_count_match = (
                ColumnComparisonNotNullCountMatchCheckSpec.from_dict(
                    _daily_not_null_count_match
                )
            )

        _daily_null_count_match = d.pop("daily_null_count_match", UNSET)
        daily_null_count_match: Union[Unset, ColumnComparisonNullCountMatchCheckSpec]
        if isinstance(_daily_null_count_match, Unset):
            daily_null_count_match = UNSET
        else:
            daily_null_count_match = ColumnComparisonNullCountMatchCheckSpec.from_dict(
                _daily_null_count_match
            )

        column_comparison_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            reference_column=reference_column,
            daily_sum_match=daily_sum_match,
            daily_min_match=daily_min_match,
            daily_max_match=daily_max_match,
            daily_mean_match=daily_mean_match,
            daily_not_null_count_match=daily_not_null_count_match,
            daily_null_count_match=daily_null_count_match,
        )

        column_comparison_daily_monitoring_checks_spec.additional_properties = d
        return column_comparison_daily_monitoring_checks_spec

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
