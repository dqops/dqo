from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_whitespace_empty_text_found_check_spec import (
        ColumnWhitespaceEmptyTextFoundCheckSpec,
    )
    from ..models.column_whitespace_empty_text_percent_check_spec import (
        ColumnWhitespaceEmptyTextPercentCheckSpec,
    )
    from ..models.column_whitespace_monthly_monitoring_checks_spec_custom_checks import (
        ColumnWhitespaceMonthlyMonitoringChecksSpecCustomChecks,
    )
    from ..models.column_whitespace_null_placeholder_text_found_check_spec import (
        ColumnWhitespaceNullPlaceholderTextFoundCheckSpec,
    )
    from ..models.column_whitespace_null_placeholder_text_percent_check_spec import (
        ColumnWhitespaceNullPlaceholderTextPercentCheckSpec,
    )
    from ..models.column_whitespace_text_surrounded_by_whitespace_found_check_spec import (
        ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec,
    )
    from ..models.column_whitespace_text_surrounded_by_whitespace_percent_check_spec import (
        ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec,
    )
    from ..models.column_whitespace_whitespace_text_found_check_spec import (
        ColumnWhitespaceWhitespaceTextFoundCheckSpec,
    )
    from ..models.column_whitespace_whitespace_text_percent_check_spec import (
        ColumnWhitespaceWhitespaceTextPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnWhitespaceMonthlyMonitoringChecksSpec")


@_attrs_define
class ColumnWhitespaceMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnWhitespaceMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_empty_text_found (Union[Unset, ColumnWhitespaceEmptyTextFoundCheckSpec]):
        monthly_whitespace_text_found (Union[Unset, ColumnWhitespaceWhitespaceTextFoundCheckSpec]):
        monthly_null_placeholder_text_found (Union[Unset, ColumnWhitespaceNullPlaceholderTextFoundCheckSpec]):
        monthly_empty_text_percent (Union[Unset, ColumnWhitespaceEmptyTextPercentCheckSpec]):
        monthly_whitespace_text_percent (Union[Unset, ColumnWhitespaceWhitespaceTextPercentCheckSpec]):
        monthly_null_placeholder_text_percent (Union[Unset, ColumnWhitespaceNullPlaceholderTextPercentCheckSpec]):
        monthly_text_surrounded_by_whitespace_found (Union[Unset,
            ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec]):
        monthly_text_surrounded_by_whitespace_percent (Union[Unset,
            ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnWhitespaceMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    monthly_empty_text_found: Union[
        Unset, "ColumnWhitespaceEmptyTextFoundCheckSpec"
    ] = UNSET
    monthly_whitespace_text_found: Union[
        Unset, "ColumnWhitespaceWhitespaceTextFoundCheckSpec"
    ] = UNSET
    monthly_null_placeholder_text_found: Union[
        Unset, "ColumnWhitespaceNullPlaceholderTextFoundCheckSpec"
    ] = UNSET
    monthly_empty_text_percent: Union[
        Unset, "ColumnWhitespaceEmptyTextPercentCheckSpec"
    ] = UNSET
    monthly_whitespace_text_percent: Union[
        Unset, "ColumnWhitespaceWhitespaceTextPercentCheckSpec"
    ] = UNSET
    monthly_null_placeholder_text_percent: Union[
        Unset, "ColumnWhitespaceNullPlaceholderTextPercentCheckSpec"
    ] = UNSET
    monthly_text_surrounded_by_whitespace_found: Union[
        Unset, "ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec"
    ] = UNSET
    monthly_text_surrounded_by_whitespace_percent: Union[
        Unset, "ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_empty_text_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_empty_text_found, Unset):
            monthly_empty_text_found = self.monthly_empty_text_found.to_dict()

        monthly_whitespace_text_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_whitespace_text_found, Unset):
            monthly_whitespace_text_found = self.monthly_whitespace_text_found.to_dict()

        monthly_null_placeholder_text_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_null_placeholder_text_found, Unset):
            monthly_null_placeholder_text_found = (
                self.monthly_null_placeholder_text_found.to_dict()
            )

        monthly_empty_text_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_empty_text_percent, Unset):
            monthly_empty_text_percent = self.monthly_empty_text_percent.to_dict()

        monthly_whitespace_text_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_whitespace_text_percent, Unset):
            monthly_whitespace_text_percent = (
                self.monthly_whitespace_text_percent.to_dict()
            )

        monthly_null_placeholder_text_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_null_placeholder_text_percent, Unset):
            monthly_null_placeholder_text_percent = (
                self.monthly_null_placeholder_text_percent.to_dict()
            )

        monthly_text_surrounded_by_whitespace_found: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_text_surrounded_by_whitespace_found, Unset):
            monthly_text_surrounded_by_whitespace_found = (
                self.monthly_text_surrounded_by_whitespace_found.to_dict()
            )

        monthly_text_surrounded_by_whitespace_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_text_surrounded_by_whitespace_percent, Unset):
            monthly_text_surrounded_by_whitespace_percent = (
                self.monthly_text_surrounded_by_whitespace_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_empty_text_found is not UNSET:
            field_dict["monthly_empty_text_found"] = monthly_empty_text_found
        if monthly_whitespace_text_found is not UNSET:
            field_dict["monthly_whitespace_text_found"] = monthly_whitespace_text_found
        if monthly_null_placeholder_text_found is not UNSET:
            field_dict["monthly_null_placeholder_text_found"] = (
                monthly_null_placeholder_text_found
            )
        if monthly_empty_text_percent is not UNSET:
            field_dict["monthly_empty_text_percent"] = monthly_empty_text_percent
        if monthly_whitespace_text_percent is not UNSET:
            field_dict["monthly_whitespace_text_percent"] = (
                monthly_whitespace_text_percent
            )
        if monthly_null_placeholder_text_percent is not UNSET:
            field_dict["monthly_null_placeholder_text_percent"] = (
                monthly_null_placeholder_text_percent
            )
        if monthly_text_surrounded_by_whitespace_found is not UNSET:
            field_dict["monthly_text_surrounded_by_whitespace_found"] = (
                monthly_text_surrounded_by_whitespace_found
            )
        if monthly_text_surrounded_by_whitespace_percent is not UNSET:
            field_dict["monthly_text_surrounded_by_whitespace_percent"] = (
                monthly_text_surrounded_by_whitespace_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_whitespace_empty_text_found_check_spec import (
            ColumnWhitespaceEmptyTextFoundCheckSpec,
        )
        from ..models.column_whitespace_empty_text_percent_check_spec import (
            ColumnWhitespaceEmptyTextPercentCheckSpec,
        )
        from ..models.column_whitespace_monthly_monitoring_checks_spec_custom_checks import (
            ColumnWhitespaceMonthlyMonitoringChecksSpecCustomChecks,
        )
        from ..models.column_whitespace_null_placeholder_text_found_check_spec import (
            ColumnWhitespaceNullPlaceholderTextFoundCheckSpec,
        )
        from ..models.column_whitespace_null_placeholder_text_percent_check_spec import (
            ColumnWhitespaceNullPlaceholderTextPercentCheckSpec,
        )
        from ..models.column_whitespace_text_surrounded_by_whitespace_found_check_spec import (
            ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec,
        )
        from ..models.column_whitespace_text_surrounded_by_whitespace_percent_check_spec import (
            ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec,
        )
        from ..models.column_whitespace_whitespace_text_found_check_spec import (
            ColumnWhitespaceWhitespaceTextFoundCheckSpec,
        )
        from ..models.column_whitespace_whitespace_text_percent_check_spec import (
            ColumnWhitespaceWhitespaceTextPercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnWhitespaceMonthlyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnWhitespaceMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_empty_text_found = d.pop("monthly_empty_text_found", UNSET)
        monthly_empty_text_found: Union[Unset, ColumnWhitespaceEmptyTextFoundCheckSpec]
        if isinstance(_monthly_empty_text_found, Unset):
            monthly_empty_text_found = UNSET
        else:
            monthly_empty_text_found = (
                ColumnWhitespaceEmptyTextFoundCheckSpec.from_dict(
                    _monthly_empty_text_found
                )
            )

        _monthly_whitespace_text_found = d.pop("monthly_whitespace_text_found", UNSET)
        monthly_whitespace_text_found: Union[
            Unset, ColumnWhitespaceWhitespaceTextFoundCheckSpec
        ]
        if isinstance(_monthly_whitespace_text_found, Unset):
            monthly_whitespace_text_found = UNSET
        else:
            monthly_whitespace_text_found = (
                ColumnWhitespaceWhitespaceTextFoundCheckSpec.from_dict(
                    _monthly_whitespace_text_found
                )
            )

        _monthly_null_placeholder_text_found = d.pop(
            "monthly_null_placeholder_text_found", UNSET
        )
        monthly_null_placeholder_text_found: Union[
            Unset, ColumnWhitespaceNullPlaceholderTextFoundCheckSpec
        ]
        if isinstance(_monthly_null_placeholder_text_found, Unset):
            monthly_null_placeholder_text_found = UNSET
        else:
            monthly_null_placeholder_text_found = (
                ColumnWhitespaceNullPlaceholderTextFoundCheckSpec.from_dict(
                    _monthly_null_placeholder_text_found
                )
            )

        _monthly_empty_text_percent = d.pop("monthly_empty_text_percent", UNSET)
        monthly_empty_text_percent: Union[
            Unset, ColumnWhitespaceEmptyTextPercentCheckSpec
        ]
        if isinstance(_monthly_empty_text_percent, Unset):
            monthly_empty_text_percent = UNSET
        else:
            monthly_empty_text_percent = (
                ColumnWhitespaceEmptyTextPercentCheckSpec.from_dict(
                    _monthly_empty_text_percent
                )
            )

        _monthly_whitespace_text_percent = d.pop(
            "monthly_whitespace_text_percent", UNSET
        )
        monthly_whitespace_text_percent: Union[
            Unset, ColumnWhitespaceWhitespaceTextPercentCheckSpec
        ]
        if isinstance(_monthly_whitespace_text_percent, Unset):
            monthly_whitespace_text_percent = UNSET
        else:
            monthly_whitespace_text_percent = (
                ColumnWhitespaceWhitespaceTextPercentCheckSpec.from_dict(
                    _monthly_whitespace_text_percent
                )
            )

        _monthly_null_placeholder_text_percent = d.pop(
            "monthly_null_placeholder_text_percent", UNSET
        )
        monthly_null_placeholder_text_percent: Union[
            Unset, ColumnWhitespaceNullPlaceholderTextPercentCheckSpec
        ]
        if isinstance(_monthly_null_placeholder_text_percent, Unset):
            monthly_null_placeholder_text_percent = UNSET
        else:
            monthly_null_placeholder_text_percent = (
                ColumnWhitespaceNullPlaceholderTextPercentCheckSpec.from_dict(
                    _monthly_null_placeholder_text_percent
                )
            )

        _monthly_text_surrounded_by_whitespace_found = d.pop(
            "monthly_text_surrounded_by_whitespace_found", UNSET
        )
        monthly_text_surrounded_by_whitespace_found: Union[
            Unset, ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec
        ]
        if isinstance(_monthly_text_surrounded_by_whitespace_found, Unset):
            monthly_text_surrounded_by_whitespace_found = UNSET
        else:
            monthly_text_surrounded_by_whitespace_found = (
                ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec.from_dict(
                    _monthly_text_surrounded_by_whitespace_found
                )
            )

        _monthly_text_surrounded_by_whitespace_percent = d.pop(
            "monthly_text_surrounded_by_whitespace_percent", UNSET
        )
        monthly_text_surrounded_by_whitespace_percent: Union[
            Unset, ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec
        ]
        if isinstance(_monthly_text_surrounded_by_whitespace_percent, Unset):
            monthly_text_surrounded_by_whitespace_percent = UNSET
        else:
            monthly_text_surrounded_by_whitespace_percent = (
                ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec.from_dict(
                    _monthly_text_surrounded_by_whitespace_percent
                )
            )

        column_whitespace_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_empty_text_found=monthly_empty_text_found,
            monthly_whitespace_text_found=monthly_whitespace_text_found,
            monthly_null_placeholder_text_found=monthly_null_placeholder_text_found,
            monthly_empty_text_percent=monthly_empty_text_percent,
            monthly_whitespace_text_percent=monthly_whitespace_text_percent,
            monthly_null_placeholder_text_percent=monthly_null_placeholder_text_percent,
            monthly_text_surrounded_by_whitespace_found=monthly_text_surrounded_by_whitespace_found,
            monthly_text_surrounded_by_whitespace_percent=monthly_text_surrounded_by_whitespace_percent,
        )

        column_whitespace_monthly_monitoring_checks_spec.additional_properties = d
        return column_whitespace_monthly_monitoring_checks_spec

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
