from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_invalid_email_format_found_check_spec import (
        ColumnInvalidEmailFormatFoundCheckSpec,
    )
    from ..models.column_invalid_email_format_percent_check_spec import (
        ColumnInvalidEmailFormatPercentCheckSpec,
    )
    from ..models.column_invalid_ip_4_address_format_found_check_spec import (
        ColumnInvalidIp4AddressFormatFoundCheckSpec,
    )
    from ..models.column_invalid_ip_6_address_format_found_check_spec import (
        ColumnInvalidIp6AddressFormatFoundCheckSpec,
    )
    from ..models.column_invalid_usa_phone_found_check_spec import (
        ColumnInvalidUsaPhoneFoundCheckSpec,
    )
    from ..models.column_invalid_usa_phone_percent_check_spec import (
        ColumnInvalidUsaPhonePercentCheckSpec,
    )
    from ..models.column_invalid_usa_zipcode_found_check_spec import (
        ColumnInvalidUsaZipcodeFoundCheckSpec,
    )
    from ..models.column_invalid_usa_zipcode_percent_check_spec import (
        ColumnInvalidUsaZipcodePercentCheckSpec,
    )
    from ..models.column_invalid_uuid_format_found_check_spec import (
        ColumnInvalidUuidFormatFoundCheckSpec,
    )
    from ..models.column_invalid_uuid_format_percent_check_spec import (
        ColumnInvalidUuidFormatPercentCheckSpec,
    )
    from ..models.column_patterns_monthly_partitioned_checks_spec_custom_checks import (
        ColumnPatternsMonthlyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_text_not_matching_date_pattern_found_check_spec import (
        ColumnTextNotMatchingDatePatternFoundCheckSpec,
    )
    from ..models.column_text_not_matching_date_pattern_percent_check_spec import (
        ColumnTextNotMatchingDatePatternPercentCheckSpec,
    )
    from ..models.column_text_not_matching_name_pattern_percent_check_spec import (
        ColumnTextNotMatchingNamePatternPercentCheckSpec,
    )
    from ..models.column_text_not_matching_regex_found_check_spec import (
        ColumnTextNotMatchingRegexFoundCheckSpec,
    )
    from ..models.column_texts_not_matching_regex_percent_check_spec import (
        ColumnTextsNotMatchingRegexPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnPatternsMonthlyPartitionedChecksSpec")


@_attrs_define
class ColumnPatternsMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnPatternsMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_text_not_matching_regex_found (Union[Unset, ColumnTextNotMatchingRegexFoundCheckSpec]):
        monthly_partition_texts_not_matching_regex_percent (Union[Unset, ColumnTextsNotMatchingRegexPercentCheckSpec]):
        monthly_partition_invalid_email_format_found (Union[Unset, ColumnInvalidEmailFormatFoundCheckSpec]):
        monthly_partition_invalid_email_format_percent (Union[Unset, ColumnInvalidEmailFormatPercentCheckSpec]):
        monthly_partition_text_not_matching_date_pattern_found (Union[Unset,
            ColumnTextNotMatchingDatePatternFoundCheckSpec]):
        monthly_partition_text_not_matching_date_pattern_percent (Union[Unset,
            ColumnTextNotMatchingDatePatternPercentCheckSpec]):
        monthly_partition_text_not_matching_name_pattern_percent (Union[Unset,
            ColumnTextNotMatchingNamePatternPercentCheckSpec]):
        monthly_partition_invalid_uuid_format_found (Union[Unset, ColumnInvalidUuidFormatFoundCheckSpec]):
        monthly_partition_invalid_uuid_format_percent (Union[Unset, ColumnInvalidUuidFormatPercentCheckSpec]):
        monthly_partition_invalid_ip4_address_format_found (Union[Unset, ColumnInvalidIp4AddressFormatFoundCheckSpec]):
        monthly_partition_invalid_ip6_address_format_found (Union[Unset, ColumnInvalidIp6AddressFormatFoundCheckSpec]):
        monthly_partition_invalid_usa_phone_format_found (Union[Unset, ColumnInvalidUsaPhoneFoundCheckSpec]):
        monthly_partition_invalid_usa_zipcode_format_found (Union[Unset, ColumnInvalidUsaZipcodeFoundCheckSpec]):
        monthly_partition_invalid_usa_phone_format_percent (Union[Unset, ColumnInvalidUsaPhonePercentCheckSpec]):
        monthly_partition_invalid_usa_zipcode_format_percent (Union[Unset, ColumnInvalidUsaZipcodePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnPatternsMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    monthly_partition_text_not_matching_regex_found: Union[
        Unset, "ColumnTextNotMatchingRegexFoundCheckSpec"
    ] = UNSET
    monthly_partition_texts_not_matching_regex_percent: Union[
        Unset, "ColumnTextsNotMatchingRegexPercentCheckSpec"
    ] = UNSET
    monthly_partition_invalid_email_format_found: Union[
        Unset, "ColumnInvalidEmailFormatFoundCheckSpec"
    ] = UNSET
    monthly_partition_invalid_email_format_percent: Union[
        Unset, "ColumnInvalidEmailFormatPercentCheckSpec"
    ] = UNSET
    monthly_partition_text_not_matching_date_pattern_found: Union[
        Unset, "ColumnTextNotMatchingDatePatternFoundCheckSpec"
    ] = UNSET
    monthly_partition_text_not_matching_date_pattern_percent: Union[
        Unset, "ColumnTextNotMatchingDatePatternPercentCheckSpec"
    ] = UNSET
    monthly_partition_text_not_matching_name_pattern_percent: Union[
        Unset, "ColumnTextNotMatchingNamePatternPercentCheckSpec"
    ] = UNSET
    monthly_partition_invalid_uuid_format_found: Union[
        Unset, "ColumnInvalidUuidFormatFoundCheckSpec"
    ] = UNSET
    monthly_partition_invalid_uuid_format_percent: Union[
        Unset, "ColumnInvalidUuidFormatPercentCheckSpec"
    ] = UNSET
    monthly_partition_invalid_ip4_address_format_found: Union[
        Unset, "ColumnInvalidIp4AddressFormatFoundCheckSpec"
    ] = UNSET
    monthly_partition_invalid_ip6_address_format_found: Union[
        Unset, "ColumnInvalidIp6AddressFormatFoundCheckSpec"
    ] = UNSET
    monthly_partition_invalid_usa_phone_format_found: Union[
        Unset, "ColumnInvalidUsaPhoneFoundCheckSpec"
    ] = UNSET
    monthly_partition_invalid_usa_zipcode_format_found: Union[
        Unset, "ColumnInvalidUsaZipcodeFoundCheckSpec"
    ] = UNSET
    monthly_partition_invalid_usa_phone_format_percent: Union[
        Unset, "ColumnInvalidUsaPhonePercentCheckSpec"
    ] = UNSET
    monthly_partition_invalid_usa_zipcode_format_percent: Union[
        Unset, "ColumnInvalidUsaZipcodePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_partition_text_not_matching_regex_found: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_text_not_matching_regex_found, Unset):
            monthly_partition_text_not_matching_regex_found = (
                self.monthly_partition_text_not_matching_regex_found.to_dict()
            )

        monthly_partition_texts_not_matching_regex_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_texts_not_matching_regex_percent, Unset
        ):
            monthly_partition_texts_not_matching_regex_percent = (
                self.monthly_partition_texts_not_matching_regex_percent.to_dict()
            )

        monthly_partition_invalid_email_format_found: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_partition_invalid_email_format_found, Unset):
            monthly_partition_invalid_email_format_found = (
                self.monthly_partition_invalid_email_format_found.to_dict()
            )

        monthly_partition_invalid_email_format_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_partition_invalid_email_format_percent, Unset):
            monthly_partition_invalid_email_format_percent = (
                self.monthly_partition_invalid_email_format_percent.to_dict()
            )

        monthly_partition_text_not_matching_date_pattern_found: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_text_not_matching_date_pattern_found, Unset
        ):
            monthly_partition_text_not_matching_date_pattern_found = (
                self.monthly_partition_text_not_matching_date_pattern_found.to_dict()
            )

        monthly_partition_text_not_matching_date_pattern_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_text_not_matching_date_pattern_percent, Unset
        ):
            monthly_partition_text_not_matching_date_pattern_percent = (
                self.monthly_partition_text_not_matching_date_pattern_percent.to_dict()
            )

        monthly_partition_text_not_matching_name_pattern_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_text_not_matching_name_pattern_percent, Unset
        ):
            monthly_partition_text_not_matching_name_pattern_percent = (
                self.monthly_partition_text_not_matching_name_pattern_percent.to_dict()
            )

        monthly_partition_invalid_uuid_format_found: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_partition_invalid_uuid_format_found, Unset):
            monthly_partition_invalid_uuid_format_found = (
                self.monthly_partition_invalid_uuid_format_found.to_dict()
            )

        monthly_partition_invalid_uuid_format_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_partition_invalid_uuid_format_percent, Unset):
            monthly_partition_invalid_uuid_format_percent = (
                self.monthly_partition_invalid_uuid_format_percent.to_dict()
            )

        monthly_partition_invalid_ip4_address_format_found: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_invalid_ip4_address_format_found, Unset
        ):
            monthly_partition_invalid_ip4_address_format_found = (
                self.monthly_partition_invalid_ip4_address_format_found.to_dict()
            )

        monthly_partition_invalid_ip6_address_format_found: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_invalid_ip6_address_format_found, Unset
        ):
            monthly_partition_invalid_ip6_address_format_found = (
                self.monthly_partition_invalid_ip6_address_format_found.to_dict()
            )

        monthly_partition_invalid_usa_phone_format_found: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_invalid_usa_phone_format_found, Unset):
            monthly_partition_invalid_usa_phone_format_found = (
                self.monthly_partition_invalid_usa_phone_format_found.to_dict()
            )

        monthly_partition_invalid_usa_zipcode_format_found: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_invalid_usa_zipcode_format_found, Unset
        ):
            monthly_partition_invalid_usa_zipcode_format_found = (
                self.monthly_partition_invalid_usa_zipcode_format_found.to_dict()
            )

        monthly_partition_invalid_usa_phone_format_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_invalid_usa_phone_format_percent, Unset
        ):
            monthly_partition_invalid_usa_phone_format_percent = (
                self.monthly_partition_invalid_usa_phone_format_percent.to_dict()
            )

        monthly_partition_invalid_usa_zipcode_format_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_invalid_usa_zipcode_format_percent, Unset
        ):
            monthly_partition_invalid_usa_zipcode_format_percent = (
                self.monthly_partition_invalid_usa_zipcode_format_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_partition_text_not_matching_regex_found is not UNSET:
            field_dict["monthly_partition_text_not_matching_regex_found"] = (
                monthly_partition_text_not_matching_regex_found
            )
        if monthly_partition_texts_not_matching_regex_percent is not UNSET:
            field_dict["monthly_partition_texts_not_matching_regex_percent"] = (
                monthly_partition_texts_not_matching_regex_percent
            )
        if monthly_partition_invalid_email_format_found is not UNSET:
            field_dict["monthly_partition_invalid_email_format_found"] = (
                monthly_partition_invalid_email_format_found
            )
        if monthly_partition_invalid_email_format_percent is not UNSET:
            field_dict["monthly_partition_invalid_email_format_percent"] = (
                monthly_partition_invalid_email_format_percent
            )
        if monthly_partition_text_not_matching_date_pattern_found is not UNSET:
            field_dict["monthly_partition_text_not_matching_date_pattern_found"] = (
                monthly_partition_text_not_matching_date_pattern_found
            )
        if monthly_partition_text_not_matching_date_pattern_percent is not UNSET:
            field_dict["monthly_partition_text_not_matching_date_pattern_percent"] = (
                monthly_partition_text_not_matching_date_pattern_percent
            )
        if monthly_partition_text_not_matching_name_pattern_percent is not UNSET:
            field_dict["monthly_partition_text_not_matching_name_pattern_percent"] = (
                monthly_partition_text_not_matching_name_pattern_percent
            )
        if monthly_partition_invalid_uuid_format_found is not UNSET:
            field_dict["monthly_partition_invalid_uuid_format_found"] = (
                monthly_partition_invalid_uuid_format_found
            )
        if monthly_partition_invalid_uuid_format_percent is not UNSET:
            field_dict["monthly_partition_invalid_uuid_format_percent"] = (
                monthly_partition_invalid_uuid_format_percent
            )
        if monthly_partition_invalid_ip4_address_format_found is not UNSET:
            field_dict["monthly_partition_invalid_ip4_address_format_found"] = (
                monthly_partition_invalid_ip4_address_format_found
            )
        if monthly_partition_invalid_ip6_address_format_found is not UNSET:
            field_dict["monthly_partition_invalid_ip6_address_format_found"] = (
                monthly_partition_invalid_ip6_address_format_found
            )
        if monthly_partition_invalid_usa_phone_format_found is not UNSET:
            field_dict["monthly_partition_invalid_usa_phone_format_found"] = (
                monthly_partition_invalid_usa_phone_format_found
            )
        if monthly_partition_invalid_usa_zipcode_format_found is not UNSET:
            field_dict["monthly_partition_invalid_usa_zipcode_format_found"] = (
                monthly_partition_invalid_usa_zipcode_format_found
            )
        if monthly_partition_invalid_usa_phone_format_percent is not UNSET:
            field_dict["monthly_partition_invalid_usa_phone_format_percent"] = (
                monthly_partition_invalid_usa_phone_format_percent
            )
        if monthly_partition_invalid_usa_zipcode_format_percent is not UNSET:
            field_dict["monthly_partition_invalid_usa_zipcode_format_percent"] = (
                monthly_partition_invalid_usa_zipcode_format_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_invalid_email_format_found_check_spec import (
            ColumnInvalidEmailFormatFoundCheckSpec,
        )
        from ..models.column_invalid_email_format_percent_check_spec import (
            ColumnInvalidEmailFormatPercentCheckSpec,
        )
        from ..models.column_invalid_ip_4_address_format_found_check_spec import (
            ColumnInvalidIp4AddressFormatFoundCheckSpec,
        )
        from ..models.column_invalid_ip_6_address_format_found_check_spec import (
            ColumnInvalidIp6AddressFormatFoundCheckSpec,
        )
        from ..models.column_invalid_usa_phone_found_check_spec import (
            ColumnInvalidUsaPhoneFoundCheckSpec,
        )
        from ..models.column_invalid_usa_phone_percent_check_spec import (
            ColumnInvalidUsaPhonePercentCheckSpec,
        )
        from ..models.column_invalid_usa_zipcode_found_check_spec import (
            ColumnInvalidUsaZipcodeFoundCheckSpec,
        )
        from ..models.column_invalid_usa_zipcode_percent_check_spec import (
            ColumnInvalidUsaZipcodePercentCheckSpec,
        )
        from ..models.column_invalid_uuid_format_found_check_spec import (
            ColumnInvalidUuidFormatFoundCheckSpec,
        )
        from ..models.column_invalid_uuid_format_percent_check_spec import (
            ColumnInvalidUuidFormatPercentCheckSpec,
        )
        from ..models.column_patterns_monthly_partitioned_checks_spec_custom_checks import (
            ColumnPatternsMonthlyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_text_not_matching_date_pattern_found_check_spec import (
            ColumnTextNotMatchingDatePatternFoundCheckSpec,
        )
        from ..models.column_text_not_matching_date_pattern_percent_check_spec import (
            ColumnTextNotMatchingDatePatternPercentCheckSpec,
        )
        from ..models.column_text_not_matching_name_pattern_percent_check_spec import (
            ColumnTextNotMatchingNamePatternPercentCheckSpec,
        )
        from ..models.column_text_not_matching_regex_found_check_spec import (
            ColumnTextNotMatchingRegexFoundCheckSpec,
        )
        from ..models.column_texts_not_matching_regex_percent_check_spec import (
            ColumnTextsNotMatchingRegexPercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnPatternsMonthlyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnPatternsMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_partition_text_not_matching_regex_found = d.pop(
            "monthly_partition_text_not_matching_regex_found", UNSET
        )
        monthly_partition_text_not_matching_regex_found: Union[
            Unset, ColumnTextNotMatchingRegexFoundCheckSpec
        ]
        if isinstance(_monthly_partition_text_not_matching_regex_found, Unset):
            monthly_partition_text_not_matching_regex_found = UNSET
        else:
            monthly_partition_text_not_matching_regex_found = (
                ColumnTextNotMatchingRegexFoundCheckSpec.from_dict(
                    _monthly_partition_text_not_matching_regex_found
                )
            )

        _monthly_partition_texts_not_matching_regex_percent = d.pop(
            "monthly_partition_texts_not_matching_regex_percent", UNSET
        )
        monthly_partition_texts_not_matching_regex_percent: Union[
            Unset, ColumnTextsNotMatchingRegexPercentCheckSpec
        ]
        if isinstance(_monthly_partition_texts_not_matching_regex_percent, Unset):
            monthly_partition_texts_not_matching_regex_percent = UNSET
        else:
            monthly_partition_texts_not_matching_regex_percent = (
                ColumnTextsNotMatchingRegexPercentCheckSpec.from_dict(
                    _monthly_partition_texts_not_matching_regex_percent
                )
            )

        _monthly_partition_invalid_email_format_found = d.pop(
            "monthly_partition_invalid_email_format_found", UNSET
        )
        monthly_partition_invalid_email_format_found: Union[
            Unset, ColumnInvalidEmailFormatFoundCheckSpec
        ]
        if isinstance(_monthly_partition_invalid_email_format_found, Unset):
            monthly_partition_invalid_email_format_found = UNSET
        else:
            monthly_partition_invalid_email_format_found = (
                ColumnInvalidEmailFormatFoundCheckSpec.from_dict(
                    _monthly_partition_invalid_email_format_found
                )
            )

        _monthly_partition_invalid_email_format_percent = d.pop(
            "monthly_partition_invalid_email_format_percent", UNSET
        )
        monthly_partition_invalid_email_format_percent: Union[
            Unset, ColumnInvalidEmailFormatPercentCheckSpec
        ]
        if isinstance(_monthly_partition_invalid_email_format_percent, Unset):
            monthly_partition_invalid_email_format_percent = UNSET
        else:
            monthly_partition_invalid_email_format_percent = (
                ColumnInvalidEmailFormatPercentCheckSpec.from_dict(
                    _monthly_partition_invalid_email_format_percent
                )
            )

        _monthly_partition_text_not_matching_date_pattern_found = d.pop(
            "monthly_partition_text_not_matching_date_pattern_found", UNSET
        )
        monthly_partition_text_not_matching_date_pattern_found: Union[
            Unset, ColumnTextNotMatchingDatePatternFoundCheckSpec
        ]
        if isinstance(_monthly_partition_text_not_matching_date_pattern_found, Unset):
            monthly_partition_text_not_matching_date_pattern_found = UNSET
        else:
            monthly_partition_text_not_matching_date_pattern_found = (
                ColumnTextNotMatchingDatePatternFoundCheckSpec.from_dict(
                    _monthly_partition_text_not_matching_date_pattern_found
                )
            )

        _monthly_partition_text_not_matching_date_pattern_percent = d.pop(
            "monthly_partition_text_not_matching_date_pattern_percent", UNSET
        )
        monthly_partition_text_not_matching_date_pattern_percent: Union[
            Unset, ColumnTextNotMatchingDatePatternPercentCheckSpec
        ]
        if isinstance(_monthly_partition_text_not_matching_date_pattern_percent, Unset):
            monthly_partition_text_not_matching_date_pattern_percent = UNSET
        else:
            monthly_partition_text_not_matching_date_pattern_percent = (
                ColumnTextNotMatchingDatePatternPercentCheckSpec.from_dict(
                    _monthly_partition_text_not_matching_date_pattern_percent
                )
            )

        _monthly_partition_text_not_matching_name_pattern_percent = d.pop(
            "monthly_partition_text_not_matching_name_pattern_percent", UNSET
        )
        monthly_partition_text_not_matching_name_pattern_percent: Union[
            Unset, ColumnTextNotMatchingNamePatternPercentCheckSpec
        ]
        if isinstance(_monthly_partition_text_not_matching_name_pattern_percent, Unset):
            monthly_partition_text_not_matching_name_pattern_percent = UNSET
        else:
            monthly_partition_text_not_matching_name_pattern_percent = (
                ColumnTextNotMatchingNamePatternPercentCheckSpec.from_dict(
                    _monthly_partition_text_not_matching_name_pattern_percent
                )
            )

        _monthly_partition_invalid_uuid_format_found = d.pop(
            "monthly_partition_invalid_uuid_format_found", UNSET
        )
        monthly_partition_invalid_uuid_format_found: Union[
            Unset, ColumnInvalidUuidFormatFoundCheckSpec
        ]
        if isinstance(_monthly_partition_invalid_uuid_format_found, Unset):
            monthly_partition_invalid_uuid_format_found = UNSET
        else:
            monthly_partition_invalid_uuid_format_found = (
                ColumnInvalidUuidFormatFoundCheckSpec.from_dict(
                    _monthly_partition_invalid_uuid_format_found
                )
            )

        _monthly_partition_invalid_uuid_format_percent = d.pop(
            "monthly_partition_invalid_uuid_format_percent", UNSET
        )
        monthly_partition_invalid_uuid_format_percent: Union[
            Unset, ColumnInvalidUuidFormatPercentCheckSpec
        ]
        if isinstance(_monthly_partition_invalid_uuid_format_percent, Unset):
            monthly_partition_invalid_uuid_format_percent = UNSET
        else:
            monthly_partition_invalid_uuid_format_percent = (
                ColumnInvalidUuidFormatPercentCheckSpec.from_dict(
                    _monthly_partition_invalid_uuid_format_percent
                )
            )

        _monthly_partition_invalid_ip4_address_format_found = d.pop(
            "monthly_partition_invalid_ip4_address_format_found", UNSET
        )
        monthly_partition_invalid_ip4_address_format_found: Union[
            Unset, ColumnInvalidIp4AddressFormatFoundCheckSpec
        ]
        if isinstance(_monthly_partition_invalid_ip4_address_format_found, Unset):
            monthly_partition_invalid_ip4_address_format_found = UNSET
        else:
            monthly_partition_invalid_ip4_address_format_found = (
                ColumnInvalidIp4AddressFormatFoundCheckSpec.from_dict(
                    _monthly_partition_invalid_ip4_address_format_found
                )
            )

        _monthly_partition_invalid_ip6_address_format_found = d.pop(
            "monthly_partition_invalid_ip6_address_format_found", UNSET
        )
        monthly_partition_invalid_ip6_address_format_found: Union[
            Unset, ColumnInvalidIp6AddressFormatFoundCheckSpec
        ]
        if isinstance(_monthly_partition_invalid_ip6_address_format_found, Unset):
            monthly_partition_invalid_ip6_address_format_found = UNSET
        else:
            monthly_partition_invalid_ip6_address_format_found = (
                ColumnInvalidIp6AddressFormatFoundCheckSpec.from_dict(
                    _monthly_partition_invalid_ip6_address_format_found
                )
            )

        _monthly_partition_invalid_usa_phone_format_found = d.pop(
            "monthly_partition_invalid_usa_phone_format_found", UNSET
        )
        monthly_partition_invalid_usa_phone_format_found: Union[
            Unset, ColumnInvalidUsaPhoneFoundCheckSpec
        ]
        if isinstance(_monthly_partition_invalid_usa_phone_format_found, Unset):
            monthly_partition_invalid_usa_phone_format_found = UNSET
        else:
            monthly_partition_invalid_usa_phone_format_found = (
                ColumnInvalidUsaPhoneFoundCheckSpec.from_dict(
                    _monthly_partition_invalid_usa_phone_format_found
                )
            )

        _monthly_partition_invalid_usa_zipcode_format_found = d.pop(
            "monthly_partition_invalid_usa_zipcode_format_found", UNSET
        )
        monthly_partition_invalid_usa_zipcode_format_found: Union[
            Unset, ColumnInvalidUsaZipcodeFoundCheckSpec
        ]
        if isinstance(_monthly_partition_invalid_usa_zipcode_format_found, Unset):
            monthly_partition_invalid_usa_zipcode_format_found = UNSET
        else:
            monthly_partition_invalid_usa_zipcode_format_found = (
                ColumnInvalidUsaZipcodeFoundCheckSpec.from_dict(
                    _monthly_partition_invalid_usa_zipcode_format_found
                )
            )

        _monthly_partition_invalid_usa_phone_format_percent = d.pop(
            "monthly_partition_invalid_usa_phone_format_percent", UNSET
        )
        monthly_partition_invalid_usa_phone_format_percent: Union[
            Unset, ColumnInvalidUsaPhonePercentCheckSpec
        ]
        if isinstance(_monthly_partition_invalid_usa_phone_format_percent, Unset):
            monthly_partition_invalid_usa_phone_format_percent = UNSET
        else:
            monthly_partition_invalid_usa_phone_format_percent = (
                ColumnInvalidUsaPhonePercentCheckSpec.from_dict(
                    _monthly_partition_invalid_usa_phone_format_percent
                )
            )

        _monthly_partition_invalid_usa_zipcode_format_percent = d.pop(
            "monthly_partition_invalid_usa_zipcode_format_percent", UNSET
        )
        monthly_partition_invalid_usa_zipcode_format_percent: Union[
            Unset, ColumnInvalidUsaZipcodePercentCheckSpec
        ]
        if isinstance(_monthly_partition_invalid_usa_zipcode_format_percent, Unset):
            monthly_partition_invalid_usa_zipcode_format_percent = UNSET
        else:
            monthly_partition_invalid_usa_zipcode_format_percent = (
                ColumnInvalidUsaZipcodePercentCheckSpec.from_dict(
                    _monthly_partition_invalid_usa_zipcode_format_percent
                )
            )

        column_patterns_monthly_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_partition_text_not_matching_regex_found=monthly_partition_text_not_matching_regex_found,
            monthly_partition_texts_not_matching_regex_percent=monthly_partition_texts_not_matching_regex_percent,
            monthly_partition_invalid_email_format_found=monthly_partition_invalid_email_format_found,
            monthly_partition_invalid_email_format_percent=monthly_partition_invalid_email_format_percent,
            monthly_partition_text_not_matching_date_pattern_found=monthly_partition_text_not_matching_date_pattern_found,
            monthly_partition_text_not_matching_date_pattern_percent=monthly_partition_text_not_matching_date_pattern_percent,
            monthly_partition_text_not_matching_name_pattern_percent=monthly_partition_text_not_matching_name_pattern_percent,
            monthly_partition_invalid_uuid_format_found=monthly_partition_invalid_uuid_format_found,
            monthly_partition_invalid_uuid_format_percent=monthly_partition_invalid_uuid_format_percent,
            monthly_partition_invalid_ip4_address_format_found=monthly_partition_invalid_ip4_address_format_found,
            monthly_partition_invalid_ip6_address_format_found=monthly_partition_invalid_ip6_address_format_found,
            monthly_partition_invalid_usa_phone_format_found=monthly_partition_invalid_usa_phone_format_found,
            monthly_partition_invalid_usa_zipcode_format_found=monthly_partition_invalid_usa_zipcode_format_found,
            monthly_partition_invalid_usa_phone_format_percent=monthly_partition_invalid_usa_phone_format_percent,
            monthly_partition_invalid_usa_zipcode_format_percent=monthly_partition_invalid_usa_zipcode_format_percent,
        )

        column_patterns_monthly_partitioned_checks_spec.additional_properties = d
        return column_patterns_monthly_partitioned_checks_spec

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
