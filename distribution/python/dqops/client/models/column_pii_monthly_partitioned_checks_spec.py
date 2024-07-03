from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

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
    from ..models.column_pii_monthly_partitioned_checks_spec_custom_checks import (
        ColumnPiiMonthlyPartitionedChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnPiiMonthlyPartitionedChecksSpec")


@_attrs_define
class ColumnPiiMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnPiiMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_contains_usa_phone_percent (Union[Unset, ColumnPiiContainsUsaPhonePercentCheckSpec]):
        monthly_partition_contains_email_percent (Union[Unset, ColumnPiiContainsEmailPercentCheckSpec]):
        monthly_partition_contains_usa_zipcode_percent (Union[Unset, ColumnPiiContainsUsaZipcodePercentCheckSpec]):
        monthly_partition_contains_ip4_percent (Union[Unset, ColumnPiiContainsIp4PercentCheckSpec]):
        monthly_partition_contains_ip6_percent (Union[Unset, ColumnPiiContainsIp6PercentCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnPiiMonthlyPartitionedChecksSpecCustomChecks"] = (
        UNSET
    )
    monthly_partition_contains_usa_phone_percent: Union[
        Unset, "ColumnPiiContainsUsaPhonePercentCheckSpec"
    ] = UNSET
    monthly_partition_contains_email_percent: Union[
        Unset, "ColumnPiiContainsEmailPercentCheckSpec"
    ] = UNSET
    monthly_partition_contains_usa_zipcode_percent: Union[
        Unset, "ColumnPiiContainsUsaZipcodePercentCheckSpec"
    ] = UNSET
    monthly_partition_contains_ip4_percent: Union[
        Unset, "ColumnPiiContainsIp4PercentCheckSpec"
    ] = UNSET
    monthly_partition_contains_ip6_percent: Union[
        Unset, "ColumnPiiContainsIp6PercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_partition_contains_usa_phone_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_partition_contains_usa_phone_percent, Unset):
            monthly_partition_contains_usa_phone_percent = (
                self.monthly_partition_contains_usa_phone_percent.to_dict()
            )

        monthly_partition_contains_email_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_contains_email_percent, Unset):
            monthly_partition_contains_email_percent = (
                self.monthly_partition_contains_email_percent.to_dict()
            )

        monthly_partition_contains_usa_zipcode_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_partition_contains_usa_zipcode_percent, Unset):
            monthly_partition_contains_usa_zipcode_percent = (
                self.monthly_partition_contains_usa_zipcode_percent.to_dict()
            )

        monthly_partition_contains_ip4_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_contains_ip4_percent, Unset):
            monthly_partition_contains_ip4_percent = (
                self.monthly_partition_contains_ip4_percent.to_dict()
            )

        monthly_partition_contains_ip6_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_contains_ip6_percent, Unset):
            monthly_partition_contains_ip6_percent = (
                self.monthly_partition_contains_ip6_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_partition_contains_usa_phone_percent is not UNSET:
            field_dict["monthly_partition_contains_usa_phone_percent"] = (
                monthly_partition_contains_usa_phone_percent
            )
        if monthly_partition_contains_email_percent is not UNSET:
            field_dict["monthly_partition_contains_email_percent"] = (
                monthly_partition_contains_email_percent
            )
        if monthly_partition_contains_usa_zipcode_percent is not UNSET:
            field_dict["monthly_partition_contains_usa_zipcode_percent"] = (
                monthly_partition_contains_usa_zipcode_percent
            )
        if monthly_partition_contains_ip4_percent is not UNSET:
            field_dict["monthly_partition_contains_ip4_percent"] = (
                monthly_partition_contains_ip4_percent
            )
        if monthly_partition_contains_ip6_percent is not UNSET:
            field_dict["monthly_partition_contains_ip6_percent"] = (
                monthly_partition_contains_ip6_percent
            )

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
        from ..models.column_pii_monthly_partitioned_checks_spec_custom_checks import (
            ColumnPiiMonthlyPartitionedChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnPiiMonthlyPartitionedChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnPiiMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                _custom_checks
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
            custom_checks=custom_checks,
            monthly_partition_contains_usa_phone_percent=monthly_partition_contains_usa_phone_percent,
            monthly_partition_contains_email_percent=monthly_partition_contains_email_percent,
            monthly_partition_contains_usa_zipcode_percent=monthly_partition_contains_usa_zipcode_percent,
            monthly_partition_contains_ip4_percent=monthly_partition_contains_ip4_percent,
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
