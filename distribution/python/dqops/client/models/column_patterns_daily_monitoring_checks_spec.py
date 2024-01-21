from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_invalid_email_format_found_check_spec import (
        ColumnInvalidEmailFormatFoundCheckSpec,
    )
    from ..models.column_invalid_ip_4_address_format_found_check_spec import (
        ColumnInvalidIp4AddressFormatFoundCheckSpec,
    )
    from ..models.column_invalid_ip_6_address_format_found_check_spec import (
        ColumnInvalidIp6AddressFormatFoundCheckSpec,
    )
    from ..models.column_invalid_uuid_format_found_check_spec import (
        ColumnInvalidUuidFormatFoundCheckSpec,
    )
    from ..models.column_patterns_daily_monitoring_checks_spec_custom_checks import (
        ColumnPatternsDailyMonitoringChecksSpecCustomChecks,
    )
    from ..models.column_text_matching_date_pattern_percent_check_spec import (
        ColumnTextMatchingDatePatternPercentCheckSpec,
    )
    from ..models.column_text_matching_name_pattern_percent_check_spec import (
        ColumnTextMatchingNamePatternPercentCheckSpec,
    )
    from ..models.column_text_not_matching_date_pattern_found_check_spec import (
        ColumnTextNotMatchingDatePatternFoundCheckSpec,
    )
    from ..models.column_text_not_matching_regex_found_check_spec import (
        ColumnTextNotMatchingRegexFoundCheckSpec,
    )
    from ..models.column_texts_matching_regex_percent_check_spec import (
        ColumnTextsMatchingRegexPercentCheckSpec,
    )
    from ..models.column_valid_uuid_format_percent_check_spec import (
        ColumnValidUuidFormatPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnPatternsDailyMonitoringChecksSpec")


@_attrs_define
class ColumnPatternsDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnPatternsDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_text_not_matching_regex_found (Union[Unset, ColumnTextNotMatchingRegexFoundCheckSpec]):
        daily_texts_matching_regex_percent (Union[Unset, ColumnTextsMatchingRegexPercentCheckSpec]):
        daily_invalid_email_format_found (Union[Unset, ColumnInvalidEmailFormatFoundCheckSpec]):
        daily_text_not_matching_date_pattern_found (Union[Unset, ColumnTextNotMatchingDatePatternFoundCheckSpec]):
        daily_text_matching_date_pattern_percent (Union[Unset, ColumnTextMatchingDatePatternPercentCheckSpec]):
        daily_text_matching_name_pattern_percent (Union[Unset, ColumnTextMatchingNamePatternPercentCheckSpec]):
        daily_invalid_uuid_format_found (Union[Unset, ColumnInvalidUuidFormatFoundCheckSpec]):
        daily_valid_uuid_format_percent (Union[Unset, ColumnValidUuidFormatPercentCheckSpec]):
        daily_invalid_ip4_address_format_found (Union[Unset, ColumnInvalidIp4AddressFormatFoundCheckSpec]):
        daily_invalid_ip6_address_format_found (Union[Unset, ColumnInvalidIp6AddressFormatFoundCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnPatternsDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_text_not_matching_regex_found: Union[
        Unset, "ColumnTextNotMatchingRegexFoundCheckSpec"
    ] = UNSET
    daily_texts_matching_regex_percent: Union[
        Unset, "ColumnTextsMatchingRegexPercentCheckSpec"
    ] = UNSET
    daily_invalid_email_format_found: Union[
        Unset, "ColumnInvalidEmailFormatFoundCheckSpec"
    ] = UNSET
    daily_text_not_matching_date_pattern_found: Union[
        Unset, "ColumnTextNotMatchingDatePatternFoundCheckSpec"
    ] = UNSET
    daily_text_matching_date_pattern_percent: Union[
        Unset, "ColumnTextMatchingDatePatternPercentCheckSpec"
    ] = UNSET
    daily_text_matching_name_pattern_percent: Union[
        Unset, "ColumnTextMatchingNamePatternPercentCheckSpec"
    ] = UNSET
    daily_invalid_uuid_format_found: Union[
        Unset, "ColumnInvalidUuidFormatFoundCheckSpec"
    ] = UNSET
    daily_valid_uuid_format_percent: Union[
        Unset, "ColumnValidUuidFormatPercentCheckSpec"
    ] = UNSET
    daily_invalid_ip4_address_format_found: Union[
        Unset, "ColumnInvalidIp4AddressFormatFoundCheckSpec"
    ] = UNSET
    daily_invalid_ip6_address_format_found: Union[
        Unset, "ColumnInvalidIp6AddressFormatFoundCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_text_not_matching_regex_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_not_matching_regex_found, Unset):
            daily_text_not_matching_regex_found = (
                self.daily_text_not_matching_regex_found.to_dict()
            )

        daily_texts_matching_regex_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_texts_matching_regex_percent, Unset):
            daily_texts_matching_regex_percent = (
                self.daily_texts_matching_regex_percent.to_dict()
            )

        daily_invalid_email_format_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_invalid_email_format_found, Unset):
            daily_invalid_email_format_found = (
                self.daily_invalid_email_format_found.to_dict()
            )

        daily_text_not_matching_date_pattern_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_not_matching_date_pattern_found, Unset):
            daily_text_not_matching_date_pattern_found = (
                self.daily_text_not_matching_date_pattern_found.to_dict()
            )

        daily_text_matching_date_pattern_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_matching_date_pattern_percent, Unset):
            daily_text_matching_date_pattern_percent = (
                self.daily_text_matching_date_pattern_percent.to_dict()
            )

        daily_text_matching_name_pattern_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_text_matching_name_pattern_percent, Unset):
            daily_text_matching_name_pattern_percent = (
                self.daily_text_matching_name_pattern_percent.to_dict()
            )

        daily_invalid_uuid_format_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_invalid_uuid_format_found, Unset):
            daily_invalid_uuid_format_found = (
                self.daily_invalid_uuid_format_found.to_dict()
            )

        daily_valid_uuid_format_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_valid_uuid_format_percent, Unset):
            daily_valid_uuid_format_percent = (
                self.daily_valid_uuid_format_percent.to_dict()
            )

        daily_invalid_ip4_address_format_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_invalid_ip4_address_format_found, Unset):
            daily_invalid_ip4_address_format_found = (
                self.daily_invalid_ip4_address_format_found.to_dict()
            )

        daily_invalid_ip6_address_format_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_invalid_ip6_address_format_found, Unset):
            daily_invalid_ip6_address_format_found = (
                self.daily_invalid_ip6_address_format_found.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_text_not_matching_regex_found is not UNSET:
            field_dict[
                "daily_text_not_matching_regex_found"
            ] = daily_text_not_matching_regex_found
        if daily_texts_matching_regex_percent is not UNSET:
            field_dict[
                "daily_texts_matching_regex_percent"
            ] = daily_texts_matching_regex_percent
        if daily_invalid_email_format_found is not UNSET:
            field_dict[
                "daily_invalid_email_format_found"
            ] = daily_invalid_email_format_found
        if daily_text_not_matching_date_pattern_found is not UNSET:
            field_dict[
                "daily_text_not_matching_date_pattern_found"
            ] = daily_text_not_matching_date_pattern_found
        if daily_text_matching_date_pattern_percent is not UNSET:
            field_dict[
                "daily_text_matching_date_pattern_percent"
            ] = daily_text_matching_date_pattern_percent
        if daily_text_matching_name_pattern_percent is not UNSET:
            field_dict[
                "daily_text_matching_name_pattern_percent"
            ] = daily_text_matching_name_pattern_percent
        if daily_invalid_uuid_format_found is not UNSET:
            field_dict[
                "daily_invalid_uuid_format_found"
            ] = daily_invalid_uuid_format_found
        if daily_valid_uuid_format_percent is not UNSET:
            field_dict[
                "daily_valid_uuid_format_percent"
            ] = daily_valid_uuid_format_percent
        if daily_invalid_ip4_address_format_found is not UNSET:
            field_dict[
                "daily_invalid_ip4_address_format_found"
            ] = daily_invalid_ip4_address_format_found
        if daily_invalid_ip6_address_format_found is not UNSET:
            field_dict[
                "daily_invalid_ip6_address_format_found"
            ] = daily_invalid_ip6_address_format_found

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_invalid_email_format_found_check_spec import (
            ColumnInvalidEmailFormatFoundCheckSpec,
        )
        from ..models.column_invalid_ip_4_address_format_found_check_spec import (
            ColumnInvalidIp4AddressFormatFoundCheckSpec,
        )
        from ..models.column_invalid_ip_6_address_format_found_check_spec import (
            ColumnInvalidIp6AddressFormatFoundCheckSpec,
        )
        from ..models.column_invalid_uuid_format_found_check_spec import (
            ColumnInvalidUuidFormatFoundCheckSpec,
        )
        from ..models.column_patterns_daily_monitoring_checks_spec_custom_checks import (
            ColumnPatternsDailyMonitoringChecksSpecCustomChecks,
        )
        from ..models.column_text_matching_date_pattern_percent_check_spec import (
            ColumnTextMatchingDatePatternPercentCheckSpec,
        )
        from ..models.column_text_matching_name_pattern_percent_check_spec import (
            ColumnTextMatchingNamePatternPercentCheckSpec,
        )
        from ..models.column_text_not_matching_date_pattern_found_check_spec import (
            ColumnTextNotMatchingDatePatternFoundCheckSpec,
        )
        from ..models.column_text_not_matching_regex_found_check_spec import (
            ColumnTextNotMatchingRegexFoundCheckSpec,
        )
        from ..models.column_texts_matching_regex_percent_check_spec import (
            ColumnTextsMatchingRegexPercentCheckSpec,
        )
        from ..models.column_valid_uuid_format_percent_check_spec import (
            ColumnValidUuidFormatPercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnPatternsDailyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnPatternsDailyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_text_not_matching_regex_found = d.pop(
            "daily_text_not_matching_regex_found", UNSET
        )
        daily_text_not_matching_regex_found: Union[
            Unset, ColumnTextNotMatchingRegexFoundCheckSpec
        ]
        if isinstance(_daily_text_not_matching_regex_found, Unset):
            daily_text_not_matching_regex_found = UNSET
        else:
            daily_text_not_matching_regex_found = (
                ColumnTextNotMatchingRegexFoundCheckSpec.from_dict(
                    _daily_text_not_matching_regex_found
                )
            )

        _daily_texts_matching_regex_percent = d.pop(
            "daily_texts_matching_regex_percent", UNSET
        )
        daily_texts_matching_regex_percent: Union[
            Unset, ColumnTextsMatchingRegexPercentCheckSpec
        ]
        if isinstance(_daily_texts_matching_regex_percent, Unset):
            daily_texts_matching_regex_percent = UNSET
        else:
            daily_texts_matching_regex_percent = (
                ColumnTextsMatchingRegexPercentCheckSpec.from_dict(
                    _daily_texts_matching_regex_percent
                )
            )

        _daily_invalid_email_format_found = d.pop(
            "daily_invalid_email_format_found", UNSET
        )
        daily_invalid_email_format_found: Union[
            Unset, ColumnInvalidEmailFormatFoundCheckSpec
        ]
        if isinstance(_daily_invalid_email_format_found, Unset):
            daily_invalid_email_format_found = UNSET
        else:
            daily_invalid_email_format_found = (
                ColumnInvalidEmailFormatFoundCheckSpec.from_dict(
                    _daily_invalid_email_format_found
                )
            )

        _daily_text_not_matching_date_pattern_found = d.pop(
            "daily_text_not_matching_date_pattern_found", UNSET
        )
        daily_text_not_matching_date_pattern_found: Union[
            Unset, ColumnTextNotMatchingDatePatternFoundCheckSpec
        ]
        if isinstance(_daily_text_not_matching_date_pattern_found, Unset):
            daily_text_not_matching_date_pattern_found = UNSET
        else:
            daily_text_not_matching_date_pattern_found = (
                ColumnTextNotMatchingDatePatternFoundCheckSpec.from_dict(
                    _daily_text_not_matching_date_pattern_found
                )
            )

        _daily_text_matching_date_pattern_percent = d.pop(
            "daily_text_matching_date_pattern_percent", UNSET
        )
        daily_text_matching_date_pattern_percent: Union[
            Unset, ColumnTextMatchingDatePatternPercentCheckSpec
        ]
        if isinstance(_daily_text_matching_date_pattern_percent, Unset):
            daily_text_matching_date_pattern_percent = UNSET
        else:
            daily_text_matching_date_pattern_percent = (
                ColumnTextMatchingDatePatternPercentCheckSpec.from_dict(
                    _daily_text_matching_date_pattern_percent
                )
            )

        _daily_text_matching_name_pattern_percent = d.pop(
            "daily_text_matching_name_pattern_percent", UNSET
        )
        daily_text_matching_name_pattern_percent: Union[
            Unset, ColumnTextMatchingNamePatternPercentCheckSpec
        ]
        if isinstance(_daily_text_matching_name_pattern_percent, Unset):
            daily_text_matching_name_pattern_percent = UNSET
        else:
            daily_text_matching_name_pattern_percent = (
                ColumnTextMatchingNamePatternPercentCheckSpec.from_dict(
                    _daily_text_matching_name_pattern_percent
                )
            )

        _daily_invalid_uuid_format_found = d.pop(
            "daily_invalid_uuid_format_found", UNSET
        )
        daily_invalid_uuid_format_found: Union[
            Unset, ColumnInvalidUuidFormatFoundCheckSpec
        ]
        if isinstance(_daily_invalid_uuid_format_found, Unset):
            daily_invalid_uuid_format_found = UNSET
        else:
            daily_invalid_uuid_format_found = (
                ColumnInvalidUuidFormatFoundCheckSpec.from_dict(
                    _daily_invalid_uuid_format_found
                )
            )

        _daily_valid_uuid_format_percent = d.pop(
            "daily_valid_uuid_format_percent", UNSET
        )
        daily_valid_uuid_format_percent: Union[
            Unset, ColumnValidUuidFormatPercentCheckSpec
        ]
        if isinstance(_daily_valid_uuid_format_percent, Unset):
            daily_valid_uuid_format_percent = UNSET
        else:
            daily_valid_uuid_format_percent = (
                ColumnValidUuidFormatPercentCheckSpec.from_dict(
                    _daily_valid_uuid_format_percent
                )
            )

        _daily_invalid_ip4_address_format_found = d.pop(
            "daily_invalid_ip4_address_format_found", UNSET
        )
        daily_invalid_ip4_address_format_found: Union[
            Unset, ColumnInvalidIp4AddressFormatFoundCheckSpec
        ]
        if isinstance(_daily_invalid_ip4_address_format_found, Unset):
            daily_invalid_ip4_address_format_found = UNSET
        else:
            daily_invalid_ip4_address_format_found = (
                ColumnInvalidIp4AddressFormatFoundCheckSpec.from_dict(
                    _daily_invalid_ip4_address_format_found
                )
            )

        _daily_invalid_ip6_address_format_found = d.pop(
            "daily_invalid_ip6_address_format_found", UNSET
        )
        daily_invalid_ip6_address_format_found: Union[
            Unset, ColumnInvalidIp6AddressFormatFoundCheckSpec
        ]
        if isinstance(_daily_invalid_ip6_address_format_found, Unset):
            daily_invalid_ip6_address_format_found = UNSET
        else:
            daily_invalid_ip6_address_format_found = (
                ColumnInvalidIp6AddressFormatFoundCheckSpec.from_dict(
                    _daily_invalid_ip6_address_format_found
                )
            )

        column_patterns_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_text_not_matching_regex_found=daily_text_not_matching_regex_found,
            daily_texts_matching_regex_percent=daily_texts_matching_regex_percent,
            daily_invalid_email_format_found=daily_invalid_email_format_found,
            daily_text_not_matching_date_pattern_found=daily_text_not_matching_date_pattern_found,
            daily_text_matching_date_pattern_percent=daily_text_matching_date_pattern_percent,
            daily_text_matching_name_pattern_percent=daily_text_matching_name_pattern_percent,
            daily_invalid_uuid_format_found=daily_invalid_uuid_format_found,
            daily_valid_uuid_format_percent=daily_valid_uuid_format_percent,
            daily_invalid_ip4_address_format_found=daily_invalid_ip4_address_format_found,
            daily_invalid_ip6_address_format_found=daily_invalid_ip6_address_format_found,
        )

        column_patterns_daily_monitoring_checks_spec.additional_properties = d
        return column_patterns_daily_monitoring_checks_spec

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
