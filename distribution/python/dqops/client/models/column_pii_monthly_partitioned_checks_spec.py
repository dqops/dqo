from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_pii_contains_email_percent_check_spec import (
        ColumnPiiContainsEmailPercentCheckSpec,
    )
    from ..models.column_pii_contains_ip_4_percent_check_spec import (
        ColumnPiiContainsIp4PercentCheckSpec,
    )
    from ..models.column_pii_contains_ip_6_percent_check_spec import (
        ColumnPiiContainsIp6PercentCheckSpec,
    )
    from ..models.column_pii_contains_usa_phone_percent_check_spec import (
        ColumnPiiContainsUsaPhonePercentCheckSpec,
    )
    from ..models.column_pii_contains_usa_zipcode_percent_check_spec import (
        ColumnPiiContainsUsaZipcodePercentCheckSpec,
    )
    from ..models.column_pii_valid_email_percent_check_spec import (
        ColumnPiiValidEmailPercentCheckSpec,
    )
    from ..models.column_pii_valid_ip_4_address_percent_check_spec import (
        ColumnPiiValidIp4AddressPercentCheckSpec,
    )
    from ..models.column_pii_valid_ip_6_address_percent_check_spec import (
        ColumnPiiValidIp6AddressPercentCheckSpec,
    )
    from ..models.column_pii_valid_usa_phone_percent_check_spec import (
        ColumnPiiValidUsaPhonePercentCheckSpec,
    )
    from ..models.column_pii_valid_usa_zipcode_percent_check_spec import (
        ColumnPiiValidUsaZipcodePercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnPiiMonthlyPartitionedChecksSpec")


@attr.s(auto_attribs=True)
class ColumnPiiMonthlyPartitionedChecksSpec:
    """
    Attributes:
        monthly_partition_valid_usa_phone_percent (Union[Unset, ColumnPiiValidUsaPhonePercentCheckSpec]):
        monthly_partition_contains_usa_phone_percent (Union[Unset, ColumnPiiContainsUsaPhonePercentCheckSpec]):
        monthly_partition_valid_usa_zipcode_percent (Union[Unset, ColumnPiiValidUsaZipcodePercentCheckSpec]):
        monthly_partition_contains_usa_zipcode_percent (Union[Unset, ColumnPiiContainsUsaZipcodePercentCheckSpec]):
        monthly_partition_valid_email_percent (Union[Unset, ColumnPiiValidEmailPercentCheckSpec]):
        monthly_partition_contains_email_percent (Union[Unset, ColumnPiiContainsEmailPercentCheckSpec]):
        monthly_partition_valid_ip4_address_percent (Union[Unset, ColumnPiiValidIp4AddressPercentCheckSpec]):
        monthly_partition_contains_ip4_percent (Union[Unset, ColumnPiiContainsIp4PercentCheckSpec]):
        monthly_partition_valid_ip6_address_percent (Union[Unset, ColumnPiiValidIp6AddressPercentCheckSpec]):
        monthly_partition_contains_ip6_percent (Union[Unset, ColumnPiiContainsIp6PercentCheckSpec]):
    """

    monthly_partition_valid_usa_phone_percent: Union[
        Unset, "ColumnPiiValidUsaPhonePercentCheckSpec"
    ] = UNSET
    monthly_partition_contains_usa_phone_percent: Union[
        Unset, "ColumnPiiContainsUsaPhonePercentCheckSpec"
    ] = UNSET
    monthly_partition_valid_usa_zipcode_percent: Union[
        Unset, "ColumnPiiValidUsaZipcodePercentCheckSpec"
    ] = UNSET
    monthly_partition_contains_usa_zipcode_percent: Union[
        Unset, "ColumnPiiContainsUsaZipcodePercentCheckSpec"
    ] = UNSET
    monthly_partition_valid_email_percent: Union[
        Unset, "ColumnPiiValidEmailPercentCheckSpec"
    ] = UNSET
    monthly_partition_contains_email_percent: Union[
        Unset, "ColumnPiiContainsEmailPercentCheckSpec"
    ] = UNSET
    monthly_partition_valid_ip4_address_percent: Union[
        Unset, "ColumnPiiValidIp4AddressPercentCheckSpec"
    ] = UNSET
    monthly_partition_contains_ip4_percent: Union[
        Unset, "ColumnPiiContainsIp4PercentCheckSpec"
    ] = UNSET
    monthly_partition_valid_ip6_address_percent: Union[
        Unset, "ColumnPiiValidIp6AddressPercentCheckSpec"
    ] = UNSET
    monthly_partition_contains_ip6_percent: Union[
        Unset, "ColumnPiiContainsIp6PercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_partition_valid_usa_phone_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_valid_usa_phone_percent, Unset):
            monthly_partition_valid_usa_phone_percent = (
                self.monthly_partition_valid_usa_phone_percent.to_dict()
            )

        monthly_partition_contains_usa_phone_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_contains_usa_phone_percent, Unset):
            monthly_partition_contains_usa_phone_percent = (
                self.monthly_partition_contains_usa_phone_percent.to_dict()
            )

        monthly_partition_valid_usa_zipcode_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_valid_usa_zipcode_percent, Unset):
            monthly_partition_valid_usa_zipcode_percent = (
                self.monthly_partition_valid_usa_zipcode_percent.to_dict()
            )

        monthly_partition_contains_usa_zipcode_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_contains_usa_zipcode_percent, Unset):
            monthly_partition_contains_usa_zipcode_percent = (
                self.monthly_partition_contains_usa_zipcode_percent.to_dict()
            )

        monthly_partition_valid_email_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_valid_email_percent, Unset):
            monthly_partition_valid_email_percent = (
                self.monthly_partition_valid_email_percent.to_dict()
            )

        monthly_partition_contains_email_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_contains_email_percent, Unset):
            monthly_partition_contains_email_percent = (
                self.monthly_partition_contains_email_percent.to_dict()
            )

        monthly_partition_valid_ip4_address_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_valid_ip4_address_percent, Unset):
            monthly_partition_valid_ip4_address_percent = (
                self.monthly_partition_valid_ip4_address_percent.to_dict()
            )

        monthly_partition_contains_ip4_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_contains_ip4_percent, Unset):
            monthly_partition_contains_ip4_percent = (
                self.monthly_partition_contains_ip4_percent.to_dict()
            )

        monthly_partition_valid_ip6_address_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_valid_ip6_address_percent, Unset):
            monthly_partition_valid_ip6_address_percent = (
                self.monthly_partition_valid_ip6_address_percent.to_dict()
            )

        monthly_partition_contains_ip6_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_contains_ip6_percent, Unset):
            monthly_partition_contains_ip6_percent = (
                self.monthly_partition_contains_ip6_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_partition_valid_usa_phone_percent is not UNSET:
            field_dict[
                "monthly_partition_valid_usa_phone_percent"
            ] = monthly_partition_valid_usa_phone_percent
        if monthly_partition_contains_usa_phone_percent is not UNSET:
            field_dict[
                "monthly_partition_contains_usa_phone_percent"
            ] = monthly_partition_contains_usa_phone_percent
        if monthly_partition_valid_usa_zipcode_percent is not UNSET:
            field_dict[
                "monthly_partition_valid_usa_zipcode_percent"
            ] = monthly_partition_valid_usa_zipcode_percent
        if monthly_partition_contains_usa_zipcode_percent is not UNSET:
            field_dict[
                "monthly_partition_contains_usa_zipcode_percent"
            ] = monthly_partition_contains_usa_zipcode_percent
        if monthly_partition_valid_email_percent is not UNSET:
            field_dict[
                "monthly_partition_valid_email_percent"
            ] = monthly_partition_valid_email_percent
        if monthly_partition_contains_email_percent is not UNSET:
            field_dict[
                "monthly_partition_contains_email_percent"
            ] = monthly_partition_contains_email_percent
        if monthly_partition_valid_ip4_address_percent is not UNSET:
            field_dict[
                "monthly_partition_valid_ip4_address_percent"
            ] = monthly_partition_valid_ip4_address_percent
        if monthly_partition_contains_ip4_percent is not UNSET:
            field_dict[
                "monthly_partition_contains_ip4_percent"
            ] = monthly_partition_contains_ip4_percent
        if monthly_partition_valid_ip6_address_percent is not UNSET:
            field_dict[
                "monthly_partition_valid_ip6_address_percent"
            ] = monthly_partition_valid_ip6_address_percent
        if monthly_partition_contains_ip6_percent is not UNSET:
            field_dict[
                "monthly_partition_contains_ip6_percent"
            ] = monthly_partition_contains_ip6_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_pii_contains_email_percent_check_spec import (
            ColumnPiiContainsEmailPercentCheckSpec,
        )
        from ..models.column_pii_contains_ip_4_percent_check_spec import (
            ColumnPiiContainsIp4PercentCheckSpec,
        )
        from ..models.column_pii_contains_ip_6_percent_check_spec import (
            ColumnPiiContainsIp6PercentCheckSpec,
        )
        from ..models.column_pii_contains_usa_phone_percent_check_spec import (
            ColumnPiiContainsUsaPhonePercentCheckSpec,
        )
        from ..models.column_pii_contains_usa_zipcode_percent_check_spec import (
            ColumnPiiContainsUsaZipcodePercentCheckSpec,
        )
        from ..models.column_pii_valid_email_percent_check_spec import (
            ColumnPiiValidEmailPercentCheckSpec,
        )
        from ..models.column_pii_valid_ip_4_address_percent_check_spec import (
            ColumnPiiValidIp4AddressPercentCheckSpec,
        )
        from ..models.column_pii_valid_ip_6_address_percent_check_spec import (
            ColumnPiiValidIp6AddressPercentCheckSpec,
        )
        from ..models.column_pii_valid_usa_phone_percent_check_spec import (
            ColumnPiiValidUsaPhonePercentCheckSpec,
        )
        from ..models.column_pii_valid_usa_zipcode_percent_check_spec import (
            ColumnPiiValidUsaZipcodePercentCheckSpec,
        )

        d = src_dict.copy()
        _monthly_partition_valid_usa_phone_percent = d.pop(
            "monthly_partition_valid_usa_phone_percent", UNSET
        )
        monthly_partition_valid_usa_phone_percent: Union[
            Unset, ColumnPiiValidUsaPhonePercentCheckSpec
        ]
        if isinstance(_monthly_partition_valid_usa_phone_percent, Unset):
            monthly_partition_valid_usa_phone_percent = UNSET
        else:
            monthly_partition_valid_usa_phone_percent = (
                ColumnPiiValidUsaPhonePercentCheckSpec.from_dict(
                    _monthly_partition_valid_usa_phone_percent
                )
            )

        _monthly_partition_contains_usa_phone_percent = d.pop(
            "monthly_partition_contains_usa_phone_percent", UNSET
        )
        monthly_partition_contains_usa_phone_percent: Union[
            Unset, ColumnPiiContainsUsaPhonePercentCheckSpec
        ]
        if isinstance(_monthly_partition_contains_usa_phone_percent, Unset):
            monthly_partition_contains_usa_phone_percent = UNSET
        else:
            monthly_partition_contains_usa_phone_percent = (
                ColumnPiiContainsUsaPhonePercentCheckSpec.from_dict(
                    _monthly_partition_contains_usa_phone_percent
                )
            )

        _monthly_partition_valid_usa_zipcode_percent = d.pop(
            "monthly_partition_valid_usa_zipcode_percent", UNSET
        )
        monthly_partition_valid_usa_zipcode_percent: Union[
            Unset, ColumnPiiValidUsaZipcodePercentCheckSpec
        ]
        if isinstance(_monthly_partition_valid_usa_zipcode_percent, Unset):
            monthly_partition_valid_usa_zipcode_percent = UNSET
        else:
            monthly_partition_valid_usa_zipcode_percent = (
                ColumnPiiValidUsaZipcodePercentCheckSpec.from_dict(
                    _monthly_partition_valid_usa_zipcode_percent
                )
            )

        _monthly_partition_contains_usa_zipcode_percent = d.pop(
            "monthly_partition_contains_usa_zipcode_percent", UNSET
        )
        monthly_partition_contains_usa_zipcode_percent: Union[
            Unset, ColumnPiiContainsUsaZipcodePercentCheckSpec
        ]
        if isinstance(_monthly_partition_contains_usa_zipcode_percent, Unset):
            monthly_partition_contains_usa_zipcode_percent = UNSET
        else:
            monthly_partition_contains_usa_zipcode_percent = (
                ColumnPiiContainsUsaZipcodePercentCheckSpec.from_dict(
                    _monthly_partition_contains_usa_zipcode_percent
                )
            )

        _monthly_partition_valid_email_percent = d.pop(
            "monthly_partition_valid_email_percent", UNSET
        )
        monthly_partition_valid_email_percent: Union[
            Unset, ColumnPiiValidEmailPercentCheckSpec
        ]
        if isinstance(_monthly_partition_valid_email_percent, Unset):
            monthly_partition_valid_email_percent = UNSET
        else:
            monthly_partition_valid_email_percent = (
                ColumnPiiValidEmailPercentCheckSpec.from_dict(
                    _monthly_partition_valid_email_percent
                )
            )

        _monthly_partition_contains_email_percent = d.pop(
            "monthly_partition_contains_email_percent", UNSET
        )
        monthly_partition_contains_email_percent: Union[
            Unset, ColumnPiiContainsEmailPercentCheckSpec
        ]
        if isinstance(_monthly_partition_contains_email_percent, Unset):
            monthly_partition_contains_email_percent = UNSET
        else:
            monthly_partition_contains_email_percent = (
                ColumnPiiContainsEmailPercentCheckSpec.from_dict(
                    _monthly_partition_contains_email_percent
                )
            )

        _monthly_partition_valid_ip4_address_percent = d.pop(
            "monthly_partition_valid_ip4_address_percent", UNSET
        )
        monthly_partition_valid_ip4_address_percent: Union[
            Unset, ColumnPiiValidIp4AddressPercentCheckSpec
        ]
        if isinstance(_monthly_partition_valid_ip4_address_percent, Unset):
            monthly_partition_valid_ip4_address_percent = UNSET
        else:
            monthly_partition_valid_ip4_address_percent = (
                ColumnPiiValidIp4AddressPercentCheckSpec.from_dict(
                    _monthly_partition_valid_ip4_address_percent
                )
            )

        _monthly_partition_contains_ip4_percent = d.pop(
            "monthly_partition_contains_ip4_percent", UNSET
        )
        monthly_partition_contains_ip4_percent: Union[
            Unset, ColumnPiiContainsIp4PercentCheckSpec
        ]
        if isinstance(_monthly_partition_contains_ip4_percent, Unset):
            monthly_partition_contains_ip4_percent = UNSET
        else:
            monthly_partition_contains_ip4_percent = (
                ColumnPiiContainsIp4PercentCheckSpec.from_dict(
                    _monthly_partition_contains_ip4_percent
                )
            )

        _monthly_partition_valid_ip6_address_percent = d.pop(
            "monthly_partition_valid_ip6_address_percent", UNSET
        )
        monthly_partition_valid_ip6_address_percent: Union[
            Unset, ColumnPiiValidIp6AddressPercentCheckSpec
        ]
        if isinstance(_monthly_partition_valid_ip6_address_percent, Unset):
            monthly_partition_valid_ip6_address_percent = UNSET
        else:
            monthly_partition_valid_ip6_address_percent = (
                ColumnPiiValidIp6AddressPercentCheckSpec.from_dict(
                    _monthly_partition_valid_ip6_address_percent
                )
            )

        _monthly_partition_contains_ip6_percent = d.pop(
            "monthly_partition_contains_ip6_percent", UNSET
        )
        monthly_partition_contains_ip6_percent: Union[
            Unset, ColumnPiiContainsIp6PercentCheckSpec
        ]
        if isinstance(_monthly_partition_contains_ip6_percent, Unset):
            monthly_partition_contains_ip6_percent = UNSET
        else:
            monthly_partition_contains_ip6_percent = (
                ColumnPiiContainsIp6PercentCheckSpec.from_dict(
                    _monthly_partition_contains_ip6_percent
                )
            )

        column_pii_monthly_partitioned_checks_spec = cls(
            monthly_partition_valid_usa_phone_percent=monthly_partition_valid_usa_phone_percent,
            monthly_partition_contains_usa_phone_percent=monthly_partition_contains_usa_phone_percent,
            monthly_partition_valid_usa_zipcode_percent=monthly_partition_valid_usa_zipcode_percent,
            monthly_partition_contains_usa_zipcode_percent=monthly_partition_contains_usa_zipcode_percent,
            monthly_partition_valid_email_percent=monthly_partition_valid_email_percent,
            monthly_partition_contains_email_percent=monthly_partition_contains_email_percent,
            monthly_partition_valid_ip4_address_percent=monthly_partition_valid_ip4_address_percent,
            monthly_partition_contains_ip4_percent=monthly_partition_contains_ip4_percent,
            monthly_partition_valid_ip6_address_percent=monthly_partition_valid_ip6_address_percent,
            monthly_partition_contains_ip6_percent=monthly_partition_contains_ip6_percent,
        )

        column_pii_monthly_partitioned_checks_spec.additional_properties = d
        return column_pii_monthly_partitioned_checks_spec

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
