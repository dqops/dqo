from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_blanks_empty_text_found_check_spec import (
        ColumnBlanksEmptyTextFoundCheckSpec,
    )
    from ..models.column_blanks_empty_text_percent_check_spec import (
        ColumnBlanksEmptyTextPercentCheckSpec,
    )
    from ..models.column_blanks_monthly_monitoring_checks_spec_custom_checks import (
        ColumnBlanksMonthlyMonitoringChecksSpecCustomChecks,
    )
    from ..models.column_blanks_null_placeholder_text_found_check_spec import (
        ColumnBlanksNullPlaceholderTextFoundCheckSpec,
    )
    from ..models.column_blanks_null_placeholder_text_percent_check_spec import (
        ColumnBlanksNullPlaceholderTextPercentCheckSpec,
    )
    from ..models.column_blanks_whitespace_text_found_check_spec import (
        ColumnBlanksWhitespaceTextFoundCheckSpec,
    )
    from ..models.column_blanks_whitespace_text_percent_check_spec import (
        ColumnBlanksWhitespaceTextPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnBlanksMonthlyMonitoringChecksSpec")


@_attrs_define
class ColumnBlanksMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnBlanksMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_empty_text_found (Union[Unset, ColumnBlanksEmptyTextFoundCheckSpec]):
        monthly_whitespace_text_found (Union[Unset, ColumnBlanksWhitespaceTextFoundCheckSpec]):
        monthly_null_placeholder_text_found (Union[Unset, ColumnBlanksNullPlaceholderTextFoundCheckSpec]):
        monthly_empty_text_percent (Union[Unset, ColumnBlanksEmptyTextPercentCheckSpec]):
        monthly_whitespace_text_percent (Union[Unset, ColumnBlanksWhitespaceTextPercentCheckSpec]):
        monthly_null_placeholder_text_percent (Union[Unset, ColumnBlanksNullPlaceholderTextPercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnBlanksMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    monthly_empty_text_found: Union[
        Unset, "ColumnBlanksEmptyTextFoundCheckSpec"
    ] = UNSET
    monthly_whitespace_text_found: Union[
        Unset, "ColumnBlanksWhitespaceTextFoundCheckSpec"
    ] = UNSET
    monthly_null_placeholder_text_found: Union[
        Unset, "ColumnBlanksNullPlaceholderTextFoundCheckSpec"
    ] = UNSET
    monthly_empty_text_percent: Union[
        Unset, "ColumnBlanksEmptyTextPercentCheckSpec"
    ] = UNSET
    monthly_whitespace_text_percent: Union[
        Unset, "ColumnBlanksWhitespaceTextPercentCheckSpec"
    ] = UNSET
    monthly_null_placeholder_text_percent: Union[
        Unset, "ColumnBlanksNullPlaceholderTextPercentCheckSpec"
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
            field_dict[
                "monthly_null_placeholder_text_found"
            ] = monthly_null_placeholder_text_found
        if monthly_empty_text_percent is not UNSET:
            field_dict["monthly_empty_text_percent"] = monthly_empty_text_percent
        if monthly_whitespace_text_percent is not UNSET:
            field_dict[
                "monthly_whitespace_text_percent"
            ] = monthly_whitespace_text_percent
        if monthly_null_placeholder_text_percent is not UNSET:
            field_dict[
                "monthly_null_placeholder_text_percent"
            ] = monthly_null_placeholder_text_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_blanks_empty_text_found_check_spec import (
            ColumnBlanksEmptyTextFoundCheckSpec,
        )
        from ..models.column_blanks_empty_text_percent_check_spec import (
            ColumnBlanksEmptyTextPercentCheckSpec,
        )
        from ..models.column_blanks_monthly_monitoring_checks_spec_custom_checks import (
            ColumnBlanksMonthlyMonitoringChecksSpecCustomChecks,
        )
        from ..models.column_blanks_null_placeholder_text_found_check_spec import (
            ColumnBlanksNullPlaceholderTextFoundCheckSpec,
        )
        from ..models.column_blanks_null_placeholder_text_percent_check_spec import (
            ColumnBlanksNullPlaceholderTextPercentCheckSpec,
        )
        from ..models.column_blanks_whitespace_text_found_check_spec import (
            ColumnBlanksWhitespaceTextFoundCheckSpec,
        )
        from ..models.column_blanks_whitespace_text_percent_check_spec import (
            ColumnBlanksWhitespaceTextPercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnBlanksMonthlyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnBlanksMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_empty_text_found = d.pop("monthly_empty_text_found", UNSET)
        monthly_empty_text_found: Union[Unset, ColumnBlanksEmptyTextFoundCheckSpec]
        if isinstance(_monthly_empty_text_found, Unset):
            monthly_empty_text_found = UNSET
        else:
            monthly_empty_text_found = ColumnBlanksEmptyTextFoundCheckSpec.from_dict(
                _monthly_empty_text_found
            )

        _monthly_whitespace_text_found = d.pop("monthly_whitespace_text_found", UNSET)
        monthly_whitespace_text_found: Union[
            Unset, ColumnBlanksWhitespaceTextFoundCheckSpec
        ]
        if isinstance(_monthly_whitespace_text_found, Unset):
            monthly_whitespace_text_found = UNSET
        else:
            monthly_whitespace_text_found = (
                ColumnBlanksWhitespaceTextFoundCheckSpec.from_dict(
                    _monthly_whitespace_text_found
                )
            )

        _monthly_null_placeholder_text_found = d.pop(
            "monthly_null_placeholder_text_found", UNSET
        )
        monthly_null_placeholder_text_found: Union[
            Unset, ColumnBlanksNullPlaceholderTextFoundCheckSpec
        ]
        if isinstance(_monthly_null_placeholder_text_found, Unset):
            monthly_null_placeholder_text_found = UNSET
        else:
            monthly_null_placeholder_text_found = (
                ColumnBlanksNullPlaceholderTextFoundCheckSpec.from_dict(
                    _monthly_null_placeholder_text_found
                )
            )

        _monthly_empty_text_percent = d.pop("monthly_empty_text_percent", UNSET)
        monthly_empty_text_percent: Union[Unset, ColumnBlanksEmptyTextPercentCheckSpec]
        if isinstance(_monthly_empty_text_percent, Unset):
            monthly_empty_text_percent = UNSET
        else:
            monthly_empty_text_percent = (
                ColumnBlanksEmptyTextPercentCheckSpec.from_dict(
                    _monthly_empty_text_percent
                )
            )

        _monthly_whitespace_text_percent = d.pop(
            "monthly_whitespace_text_percent", UNSET
        )
        monthly_whitespace_text_percent: Union[
            Unset, ColumnBlanksWhitespaceTextPercentCheckSpec
        ]
        if isinstance(_monthly_whitespace_text_percent, Unset):
            monthly_whitespace_text_percent = UNSET
        else:
            monthly_whitespace_text_percent = (
                ColumnBlanksWhitespaceTextPercentCheckSpec.from_dict(
                    _monthly_whitespace_text_percent
                )
            )

        _monthly_null_placeholder_text_percent = d.pop(
            "monthly_null_placeholder_text_percent", UNSET
        )
        monthly_null_placeholder_text_percent: Union[
            Unset, ColumnBlanksNullPlaceholderTextPercentCheckSpec
        ]
        if isinstance(_monthly_null_placeholder_text_percent, Unset):
            monthly_null_placeholder_text_percent = UNSET
        else:
            monthly_null_placeholder_text_percent = (
                ColumnBlanksNullPlaceholderTextPercentCheckSpec.from_dict(
                    _monthly_null_placeholder_text_percent
                )
            )

        column_blanks_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_empty_text_found=monthly_empty_text_found,
            monthly_whitespace_text_found=monthly_whitespace_text_found,
            monthly_null_placeholder_text_found=monthly_null_placeholder_text_found,
            monthly_empty_text_percent=monthly_empty_text_percent,
            monthly_whitespace_text_percent=monthly_whitespace_text_percent,
            monthly_null_placeholder_text_percent=monthly_null_placeholder_text_percent,
        )

        column_blanks_monthly_monitoring_checks_spec.additional_properties = d
        return column_blanks_monthly_monitoring_checks_spec

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
