from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_blanks_daily_monitoring_checks_spec_custom_checks import (
        ColumnBlanksDailyMonitoringChecksSpecCustomChecks,
    )
    from ..models.column_blanks_empty_text_found_check_spec import (
        ColumnBlanksEmptyTextFoundCheckSpec,
    )
    from ..models.column_blanks_empty_text_percent_check_spec import (
        ColumnBlanksEmptyTextPercentCheckSpec,
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


T = TypeVar("T", bound="ColumnBlanksDailyMonitoringChecksSpec")


@_attrs_define
class ColumnBlanksDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnBlanksDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        daily_empty_text_found (Union[Unset, ColumnBlanksEmptyTextFoundCheckSpec]):
        daily_whitespace_text_found (Union[Unset, ColumnBlanksWhitespaceTextFoundCheckSpec]):
        daily_null_placeholder_text_found (Union[Unset, ColumnBlanksNullPlaceholderTextFoundCheckSpec]):
        daily_empty_text_percent (Union[Unset, ColumnBlanksEmptyTextPercentCheckSpec]):
        daily_whitespace_text_percent (Union[Unset, ColumnBlanksWhitespaceTextPercentCheckSpec]):
        daily_null_placeholder_text_percent (Union[Unset, ColumnBlanksNullPlaceholderTextPercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnBlanksDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_empty_text_found: Union[Unset, "ColumnBlanksEmptyTextFoundCheckSpec"] = UNSET
    daily_whitespace_text_found: Union[
        Unset, "ColumnBlanksWhitespaceTextFoundCheckSpec"
    ] = UNSET
    daily_null_placeholder_text_found: Union[
        Unset, "ColumnBlanksNullPlaceholderTextFoundCheckSpec"
    ] = UNSET
    daily_empty_text_percent: Union[
        Unset, "ColumnBlanksEmptyTextPercentCheckSpec"
    ] = UNSET
    daily_whitespace_text_percent: Union[
        Unset, "ColumnBlanksWhitespaceTextPercentCheckSpec"
    ] = UNSET
    daily_null_placeholder_text_percent: Union[
        Unset, "ColumnBlanksNullPlaceholderTextPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_empty_text_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_empty_text_found, Unset):
            daily_empty_text_found = self.daily_empty_text_found.to_dict()

        daily_whitespace_text_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_whitespace_text_found, Unset):
            daily_whitespace_text_found = self.daily_whitespace_text_found.to_dict()

        daily_null_placeholder_text_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_null_placeholder_text_found, Unset):
            daily_null_placeholder_text_found = (
                self.daily_null_placeholder_text_found.to_dict()
            )

        daily_empty_text_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_empty_text_percent, Unset):
            daily_empty_text_percent = self.daily_empty_text_percent.to_dict()

        daily_whitespace_text_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_whitespace_text_percent, Unset):
            daily_whitespace_text_percent = self.daily_whitespace_text_percent.to_dict()

        daily_null_placeholder_text_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_null_placeholder_text_percent, Unset):
            daily_null_placeholder_text_percent = (
                self.daily_null_placeholder_text_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_empty_text_found is not UNSET:
            field_dict["daily_empty_text_found"] = daily_empty_text_found
        if daily_whitespace_text_found is not UNSET:
            field_dict["daily_whitespace_text_found"] = daily_whitespace_text_found
        if daily_null_placeholder_text_found is not UNSET:
            field_dict[
                "daily_null_placeholder_text_found"
            ] = daily_null_placeholder_text_found
        if daily_empty_text_percent is not UNSET:
            field_dict["daily_empty_text_percent"] = daily_empty_text_percent
        if daily_whitespace_text_percent is not UNSET:
            field_dict["daily_whitespace_text_percent"] = daily_whitespace_text_percent
        if daily_null_placeholder_text_percent is not UNSET:
            field_dict[
                "daily_null_placeholder_text_percent"
            ] = daily_null_placeholder_text_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_blanks_daily_monitoring_checks_spec_custom_checks import (
            ColumnBlanksDailyMonitoringChecksSpecCustomChecks,
        )
        from ..models.column_blanks_empty_text_found_check_spec import (
            ColumnBlanksEmptyTextFoundCheckSpec,
        )
        from ..models.column_blanks_empty_text_percent_check_spec import (
            ColumnBlanksEmptyTextPercentCheckSpec,
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
        custom_checks: Union[Unset, ColumnBlanksDailyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnBlanksDailyMonitoringChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _daily_empty_text_found = d.pop("daily_empty_text_found", UNSET)
        daily_empty_text_found: Union[Unset, ColumnBlanksEmptyTextFoundCheckSpec]
        if isinstance(_daily_empty_text_found, Unset):
            daily_empty_text_found = UNSET
        else:
            daily_empty_text_found = ColumnBlanksEmptyTextFoundCheckSpec.from_dict(
                _daily_empty_text_found
            )

        _daily_whitespace_text_found = d.pop("daily_whitespace_text_found", UNSET)
        daily_whitespace_text_found: Union[
            Unset, ColumnBlanksWhitespaceTextFoundCheckSpec
        ]
        if isinstance(_daily_whitespace_text_found, Unset):
            daily_whitespace_text_found = UNSET
        else:
            daily_whitespace_text_found = (
                ColumnBlanksWhitespaceTextFoundCheckSpec.from_dict(
                    _daily_whitespace_text_found
                )
            )

        _daily_null_placeholder_text_found = d.pop(
            "daily_null_placeholder_text_found", UNSET
        )
        daily_null_placeholder_text_found: Union[
            Unset, ColumnBlanksNullPlaceholderTextFoundCheckSpec
        ]
        if isinstance(_daily_null_placeholder_text_found, Unset):
            daily_null_placeholder_text_found = UNSET
        else:
            daily_null_placeholder_text_found = (
                ColumnBlanksNullPlaceholderTextFoundCheckSpec.from_dict(
                    _daily_null_placeholder_text_found
                )
            )

        _daily_empty_text_percent = d.pop("daily_empty_text_percent", UNSET)
        daily_empty_text_percent: Union[Unset, ColumnBlanksEmptyTextPercentCheckSpec]
        if isinstance(_daily_empty_text_percent, Unset):
            daily_empty_text_percent = UNSET
        else:
            daily_empty_text_percent = ColumnBlanksEmptyTextPercentCheckSpec.from_dict(
                _daily_empty_text_percent
            )

        _daily_whitespace_text_percent = d.pop("daily_whitespace_text_percent", UNSET)
        daily_whitespace_text_percent: Union[
            Unset, ColumnBlanksWhitespaceTextPercentCheckSpec
        ]
        if isinstance(_daily_whitespace_text_percent, Unset):
            daily_whitespace_text_percent = UNSET
        else:
            daily_whitespace_text_percent = (
                ColumnBlanksWhitespaceTextPercentCheckSpec.from_dict(
                    _daily_whitespace_text_percent
                )
            )

        _daily_null_placeholder_text_percent = d.pop(
            "daily_null_placeholder_text_percent", UNSET
        )
        daily_null_placeholder_text_percent: Union[
            Unset, ColumnBlanksNullPlaceholderTextPercentCheckSpec
        ]
        if isinstance(_daily_null_placeholder_text_percent, Unset):
            daily_null_placeholder_text_percent = UNSET
        else:
            daily_null_placeholder_text_percent = (
                ColumnBlanksNullPlaceholderTextPercentCheckSpec.from_dict(
                    _daily_null_placeholder_text_percent
                )
            )

        column_blanks_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_empty_text_found=daily_empty_text_found,
            daily_whitespace_text_found=daily_whitespace_text_found,
            daily_null_placeholder_text_found=daily_null_placeholder_text_found,
            daily_empty_text_percent=daily_empty_text_percent,
            daily_whitespace_text_percent=daily_whitespace_text_percent,
            daily_null_placeholder_text_percent=daily_null_placeholder_text_percent,
        )

        column_blanks_daily_monitoring_checks_spec.additional_properties = d
        return column_blanks_daily_monitoring_checks_spec

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
