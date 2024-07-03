from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_whitespace_daily_monitoring_checks_spec_custom_checks import (
        ColumnWhitespaceDailyMonitoringChecksSpecCustomChecks,
    )
    from ..models.column_whitespace_empty_text_found_check_spec import (
        ColumnWhitespaceEmptyTextFoundCheckSpec,
    )
    from ..models.column_whitespace_empty_text_percent_check_spec import (
        ColumnWhitespaceEmptyTextPercentCheckSpec,
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


T = TypeVar("T", bound="ColumnWhitespaceDailyMonitoringChecksSpec")


@_attrs_define
class ColumnWhitespaceDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnWhitespaceDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_empty_text_found (Union[Unset, ColumnWhitespaceEmptyTextFoundCheckSpec]):
        daily_whitespace_text_found (Union[Unset, ColumnWhitespaceWhitespaceTextFoundCheckSpec]):
        daily_null_placeholder_text_found (Union[Unset, ColumnWhitespaceNullPlaceholderTextFoundCheckSpec]):
        daily_empty_text_percent (Union[Unset, ColumnWhitespaceEmptyTextPercentCheckSpec]):
        daily_whitespace_text_percent (Union[Unset, ColumnWhitespaceWhitespaceTextPercentCheckSpec]):
        daily_null_placeholder_text_percent (Union[Unset, ColumnWhitespaceNullPlaceholderTextPercentCheckSpec]):
        daily_text_surrounded_by_whitespace_found (Union[Unset,
            ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec]):
        daily_text_surrounded_by_whitespace_percent (Union[Unset,
            ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnWhitespaceDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_empty_text_found: Union[Unset, "ColumnWhitespaceEmptyTextFoundCheckSpec"] = (
        UNSET
    )
    daily_whitespace_text_found: Union[
        Unset, "ColumnWhitespaceWhitespaceTextFoundCheckSpec"
    ] = UNSET
    daily_null_placeholder_text_found: Union[
        Unset, "ColumnWhitespaceNullPlaceholderTextFoundCheckSpec"
    ] = UNSET
    daily_empty_text_percent: Union[
        Unset, "ColumnWhitespaceEmptyTextPercentCheckSpec"
    ] = UNSET
    daily_whitespace_text_percent: Union[
        Unset, "ColumnWhitespaceWhitespaceTextPercentCheckSpec"
    ] = UNSET
    daily_null_placeholder_text_percent: Union[
        Unset, "ColumnWhitespaceNullPlaceholderTextPercentCheckSpec"
    ] = UNSET
    daily_text_surrounded_by_whitespace_found: Union[
        Unset, "ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec"
    ] = UNSET
    daily_text_surrounded_by_whitespace_percent: Union[
        Unset, "ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec"
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

        daily_text_surrounded_by_whitespace_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_surrounded_by_whitespace_found, Unset):
            daily_text_surrounded_by_whitespace_found = (
                self.daily_text_surrounded_by_whitespace_found.to_dict()
            )

        daily_text_surrounded_by_whitespace_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.daily_text_surrounded_by_whitespace_percent, Unset):
            daily_text_surrounded_by_whitespace_percent = (
                self.daily_text_surrounded_by_whitespace_percent.to_dict()
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
            field_dict["daily_null_placeholder_text_found"] = (
                daily_null_placeholder_text_found
            )
        if daily_empty_text_percent is not UNSET:
            field_dict["daily_empty_text_percent"] = daily_empty_text_percent
        if daily_whitespace_text_percent is not UNSET:
            field_dict["daily_whitespace_text_percent"] = daily_whitespace_text_percent
        if daily_null_placeholder_text_percent is not UNSET:
            field_dict["daily_null_placeholder_text_percent"] = (
                daily_null_placeholder_text_percent
            )
        if daily_text_surrounded_by_whitespace_found is not UNSET:
            field_dict["daily_text_surrounded_by_whitespace_found"] = (
                daily_text_surrounded_by_whitespace_found
            )
        if daily_text_surrounded_by_whitespace_percent is not UNSET:
            field_dict["daily_text_surrounded_by_whitespace_percent"] = (
                daily_text_surrounded_by_whitespace_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_whitespace_daily_monitoring_checks_spec_custom_checks import (
            ColumnWhitespaceDailyMonitoringChecksSpecCustomChecks,
        )
        from ..models.column_whitespace_empty_text_found_check_spec import (
            ColumnWhitespaceEmptyTextFoundCheckSpec,
        )
        from ..models.column_whitespace_empty_text_percent_check_spec import (
            ColumnWhitespaceEmptyTextPercentCheckSpec,
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
            Unset, ColumnWhitespaceDailyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnWhitespaceDailyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_empty_text_found = d.pop("daily_empty_text_found", UNSET)
        daily_empty_text_found: Union[Unset, ColumnWhitespaceEmptyTextFoundCheckSpec]
        if isinstance(_daily_empty_text_found, Unset):
            daily_empty_text_found = UNSET
        else:
            daily_empty_text_found = ColumnWhitespaceEmptyTextFoundCheckSpec.from_dict(
                _daily_empty_text_found
            )

        _daily_whitespace_text_found = d.pop("daily_whitespace_text_found", UNSET)
        daily_whitespace_text_found: Union[
            Unset, ColumnWhitespaceWhitespaceTextFoundCheckSpec
        ]
        if isinstance(_daily_whitespace_text_found, Unset):
            daily_whitespace_text_found = UNSET
        else:
            daily_whitespace_text_found = (
                ColumnWhitespaceWhitespaceTextFoundCheckSpec.from_dict(
                    _daily_whitespace_text_found
                )
            )

        _daily_null_placeholder_text_found = d.pop(
            "daily_null_placeholder_text_found", UNSET
        )
        daily_null_placeholder_text_found: Union[
            Unset, ColumnWhitespaceNullPlaceholderTextFoundCheckSpec
        ]
        if isinstance(_daily_null_placeholder_text_found, Unset):
            daily_null_placeholder_text_found = UNSET
        else:
            daily_null_placeholder_text_found = (
                ColumnWhitespaceNullPlaceholderTextFoundCheckSpec.from_dict(
                    _daily_null_placeholder_text_found
                )
            )

        _daily_empty_text_percent = d.pop("daily_empty_text_percent", UNSET)
        daily_empty_text_percent: Union[
            Unset, ColumnWhitespaceEmptyTextPercentCheckSpec
        ]
        if isinstance(_daily_empty_text_percent, Unset):
            daily_empty_text_percent = UNSET
        else:
            daily_empty_text_percent = (
                ColumnWhitespaceEmptyTextPercentCheckSpec.from_dict(
                    _daily_empty_text_percent
                )
            )

        _daily_whitespace_text_percent = d.pop("daily_whitespace_text_percent", UNSET)
        daily_whitespace_text_percent: Union[
            Unset, ColumnWhitespaceWhitespaceTextPercentCheckSpec
        ]
        if isinstance(_daily_whitespace_text_percent, Unset):
            daily_whitespace_text_percent = UNSET
        else:
            daily_whitespace_text_percent = (
                ColumnWhitespaceWhitespaceTextPercentCheckSpec.from_dict(
                    _daily_whitespace_text_percent
                )
            )

        _daily_null_placeholder_text_percent = d.pop(
            "daily_null_placeholder_text_percent", UNSET
        )
        daily_null_placeholder_text_percent: Union[
            Unset, ColumnWhitespaceNullPlaceholderTextPercentCheckSpec
        ]
        if isinstance(_daily_null_placeholder_text_percent, Unset):
            daily_null_placeholder_text_percent = UNSET
        else:
            daily_null_placeholder_text_percent = (
                ColumnWhitespaceNullPlaceholderTextPercentCheckSpec.from_dict(
                    _daily_null_placeholder_text_percent
                )
            )

        _daily_text_surrounded_by_whitespace_found = d.pop(
            "daily_text_surrounded_by_whitespace_found", UNSET
        )
        daily_text_surrounded_by_whitespace_found: Union[
            Unset, ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec
        ]
        if isinstance(_daily_text_surrounded_by_whitespace_found, Unset):
            daily_text_surrounded_by_whitespace_found = UNSET
        else:
            daily_text_surrounded_by_whitespace_found = (
                ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec.from_dict(
                    _daily_text_surrounded_by_whitespace_found
                )
            )

        _daily_text_surrounded_by_whitespace_percent = d.pop(
            "daily_text_surrounded_by_whitespace_percent", UNSET
        )
        daily_text_surrounded_by_whitespace_percent: Union[
            Unset, ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec
        ]
        if isinstance(_daily_text_surrounded_by_whitespace_percent, Unset):
            daily_text_surrounded_by_whitespace_percent = UNSET
        else:
            daily_text_surrounded_by_whitespace_percent = (
                ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec.from_dict(
                    _daily_text_surrounded_by_whitespace_percent
                )
            )

        column_whitespace_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_empty_text_found=daily_empty_text_found,
            daily_whitespace_text_found=daily_whitespace_text_found,
            daily_null_placeholder_text_found=daily_null_placeholder_text_found,
            daily_empty_text_percent=daily_empty_text_percent,
            daily_whitespace_text_percent=daily_whitespace_text_percent,
            daily_null_placeholder_text_percent=daily_null_placeholder_text_percent,
            daily_text_surrounded_by_whitespace_found=daily_text_surrounded_by_whitespace_found,
            daily_text_surrounded_by_whitespace_percent=daily_text_surrounded_by_whitespace_percent,
        )

        column_whitespace_daily_monitoring_checks_spec.additional_properties = d
        return column_whitespace_daily_monitoring_checks_spec

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
