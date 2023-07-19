from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from ..types import UNSET, Unset
from typing import Union
from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.column_pii_valid_email_percent_check_spec import ColumnPiiValidEmailPercentCheckSpec
  from ..models.column_pii_contains_usa_phone_percent_check_spec import ColumnPiiContainsUsaPhonePercentCheckSpec
  from ..models.column_pii_valid_ip_6_address_percent_check_spec import ColumnPiiValidIp6AddressPercentCheckSpec
  from ..models.column_pii_contains_ip_4_percent_check_spec import ColumnPiiContainsIp4PercentCheckSpec
  from ..models.column_pii_valid_ip_4_address_percent_check_spec import ColumnPiiValidIp4AddressPercentCheckSpec
  from ..models.column_pii_valid_usa_phone_percent_check_spec import ColumnPiiValidUsaPhonePercentCheckSpec
  from ..models.column_pii_contains_ip_6_percent_check_spec import ColumnPiiContainsIp6PercentCheckSpec
  from ..models.column_pii_valid_usa_zipcode_percent_check_spec import ColumnPiiValidUsaZipcodePercentCheckSpec
  from ..models.column_pii_contains_email_percent_check_spec import ColumnPiiContainsEmailPercentCheckSpec
  from ..models.column_pii_contains_usa_zipcode_percent_check_spec import ColumnPiiContainsUsaZipcodePercentCheckSpec





T = TypeVar("T", bound="ColumnPiiProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnPiiProfilingChecksSpec:
    """ 
        Attributes:
            valid_usa_phone_percent (Union[Unset, ColumnPiiValidUsaPhonePercentCheckSpec]):
            contains_usa_phone_percent (Union[Unset, ColumnPiiContainsUsaPhonePercentCheckSpec]):
            valid_usa_zipcode_percent (Union[Unset, ColumnPiiValidUsaZipcodePercentCheckSpec]):
            contains_usa_zipcode_percent (Union[Unset, ColumnPiiContainsUsaZipcodePercentCheckSpec]):
            valid_email_percent (Union[Unset, ColumnPiiValidEmailPercentCheckSpec]):
            contains_email_percent (Union[Unset, ColumnPiiContainsEmailPercentCheckSpec]):
            valid_ip4_address_percent (Union[Unset, ColumnPiiValidIp4AddressPercentCheckSpec]):
            contains_ip4_percent (Union[Unset, ColumnPiiContainsIp4PercentCheckSpec]):
            valid_ip6_address_percent (Union[Unset, ColumnPiiValidIp6AddressPercentCheckSpec]):
            contains_ip6_percent (Union[Unset, ColumnPiiContainsIp6PercentCheckSpec]):
     """

    valid_usa_phone_percent: Union[Unset, 'ColumnPiiValidUsaPhonePercentCheckSpec'] = UNSET
    contains_usa_phone_percent: Union[Unset, 'ColumnPiiContainsUsaPhonePercentCheckSpec'] = UNSET
    valid_usa_zipcode_percent: Union[Unset, 'ColumnPiiValidUsaZipcodePercentCheckSpec'] = UNSET
    contains_usa_zipcode_percent: Union[Unset, 'ColumnPiiContainsUsaZipcodePercentCheckSpec'] = UNSET
    valid_email_percent: Union[Unset, 'ColumnPiiValidEmailPercentCheckSpec'] = UNSET
    contains_email_percent: Union[Unset, 'ColumnPiiContainsEmailPercentCheckSpec'] = UNSET
    valid_ip4_address_percent: Union[Unset, 'ColumnPiiValidIp4AddressPercentCheckSpec'] = UNSET
    contains_ip4_percent: Union[Unset, 'ColumnPiiContainsIp4PercentCheckSpec'] = UNSET
    valid_ip6_address_percent: Union[Unset, 'ColumnPiiValidIp6AddressPercentCheckSpec'] = UNSET
    contains_ip6_percent: Union[Unset, 'ColumnPiiContainsIp6PercentCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.column_pii_valid_email_percent_check_spec import ColumnPiiValidEmailPercentCheckSpec
        from ..models.column_pii_contains_usa_phone_percent_check_spec import ColumnPiiContainsUsaPhonePercentCheckSpec
        from ..models.column_pii_valid_ip_6_address_percent_check_spec import ColumnPiiValidIp6AddressPercentCheckSpec
        from ..models.column_pii_contains_ip_4_percent_check_spec import ColumnPiiContainsIp4PercentCheckSpec
        from ..models.column_pii_valid_ip_4_address_percent_check_spec import ColumnPiiValidIp4AddressPercentCheckSpec
        from ..models.column_pii_valid_usa_phone_percent_check_spec import ColumnPiiValidUsaPhonePercentCheckSpec
        from ..models.column_pii_contains_ip_6_percent_check_spec import ColumnPiiContainsIp6PercentCheckSpec
        from ..models.column_pii_valid_usa_zipcode_percent_check_spec import ColumnPiiValidUsaZipcodePercentCheckSpec
        from ..models.column_pii_contains_email_percent_check_spec import ColumnPiiContainsEmailPercentCheckSpec
        from ..models.column_pii_contains_usa_zipcode_percent_check_spec import ColumnPiiContainsUsaZipcodePercentCheckSpec
        valid_usa_phone_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.valid_usa_phone_percent, Unset):
            valid_usa_phone_percent = self.valid_usa_phone_percent.to_dict()

        contains_usa_phone_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.contains_usa_phone_percent, Unset):
            contains_usa_phone_percent = self.contains_usa_phone_percent.to_dict()

        valid_usa_zipcode_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.valid_usa_zipcode_percent, Unset):
            valid_usa_zipcode_percent = self.valid_usa_zipcode_percent.to_dict()

        contains_usa_zipcode_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.contains_usa_zipcode_percent, Unset):
            contains_usa_zipcode_percent = self.contains_usa_zipcode_percent.to_dict()

        valid_email_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.valid_email_percent, Unset):
            valid_email_percent = self.valid_email_percent.to_dict()

        contains_email_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.contains_email_percent, Unset):
            contains_email_percent = self.contains_email_percent.to_dict()

        valid_ip4_address_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.valid_ip4_address_percent, Unset):
            valid_ip4_address_percent = self.valid_ip4_address_percent.to_dict()

        contains_ip4_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.contains_ip4_percent, Unset):
            contains_ip4_percent = self.contains_ip4_percent.to_dict()

        valid_ip6_address_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.valid_ip6_address_percent, Unset):
            valid_ip6_address_percent = self.valid_ip6_address_percent.to_dict()

        contains_ip6_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.contains_ip6_percent, Unset):
            contains_ip6_percent = self.contains_ip6_percent.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if valid_usa_phone_percent is not UNSET:
            field_dict["valid_usa_phone_percent"] = valid_usa_phone_percent
        if contains_usa_phone_percent is not UNSET:
            field_dict["contains_usa_phone_percent"] = contains_usa_phone_percent
        if valid_usa_zipcode_percent is not UNSET:
            field_dict["valid_usa_zipcode_percent"] = valid_usa_zipcode_percent
        if contains_usa_zipcode_percent is not UNSET:
            field_dict["contains_usa_zipcode_percent"] = contains_usa_zipcode_percent
        if valid_email_percent is not UNSET:
            field_dict["valid_email_percent"] = valid_email_percent
        if contains_email_percent is not UNSET:
            field_dict["contains_email_percent"] = contains_email_percent
        if valid_ip4_address_percent is not UNSET:
            field_dict["valid_ip4_address_percent"] = valid_ip4_address_percent
        if contains_ip4_percent is not UNSET:
            field_dict["contains_ip4_percent"] = contains_ip4_percent
        if valid_ip6_address_percent is not UNSET:
            field_dict["valid_ip6_address_percent"] = valid_ip6_address_percent
        if contains_ip6_percent is not UNSET:
            field_dict["contains_ip6_percent"] = contains_ip6_percent

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_pii_valid_email_percent_check_spec import ColumnPiiValidEmailPercentCheckSpec
        from ..models.column_pii_contains_usa_phone_percent_check_spec import ColumnPiiContainsUsaPhonePercentCheckSpec
        from ..models.column_pii_valid_ip_6_address_percent_check_spec import ColumnPiiValidIp6AddressPercentCheckSpec
        from ..models.column_pii_contains_ip_4_percent_check_spec import ColumnPiiContainsIp4PercentCheckSpec
        from ..models.column_pii_valid_ip_4_address_percent_check_spec import ColumnPiiValidIp4AddressPercentCheckSpec
        from ..models.column_pii_valid_usa_phone_percent_check_spec import ColumnPiiValidUsaPhonePercentCheckSpec
        from ..models.column_pii_contains_ip_6_percent_check_spec import ColumnPiiContainsIp6PercentCheckSpec
        from ..models.column_pii_valid_usa_zipcode_percent_check_spec import ColumnPiiValidUsaZipcodePercentCheckSpec
        from ..models.column_pii_contains_email_percent_check_spec import ColumnPiiContainsEmailPercentCheckSpec
        from ..models.column_pii_contains_usa_zipcode_percent_check_spec import ColumnPiiContainsUsaZipcodePercentCheckSpec
        d = src_dict.copy()
        _valid_usa_phone_percent = d.pop("valid_usa_phone_percent", UNSET)
        valid_usa_phone_percent: Union[Unset, ColumnPiiValidUsaPhonePercentCheckSpec]
        if isinstance(_valid_usa_phone_percent,  Unset):
            valid_usa_phone_percent = UNSET
        else:
            valid_usa_phone_percent = ColumnPiiValidUsaPhonePercentCheckSpec.from_dict(_valid_usa_phone_percent)




        _contains_usa_phone_percent = d.pop("contains_usa_phone_percent", UNSET)
        contains_usa_phone_percent: Union[Unset, ColumnPiiContainsUsaPhonePercentCheckSpec]
        if isinstance(_contains_usa_phone_percent,  Unset):
            contains_usa_phone_percent = UNSET
        else:
            contains_usa_phone_percent = ColumnPiiContainsUsaPhonePercentCheckSpec.from_dict(_contains_usa_phone_percent)




        _valid_usa_zipcode_percent = d.pop("valid_usa_zipcode_percent", UNSET)
        valid_usa_zipcode_percent: Union[Unset, ColumnPiiValidUsaZipcodePercentCheckSpec]
        if isinstance(_valid_usa_zipcode_percent,  Unset):
            valid_usa_zipcode_percent = UNSET
        else:
            valid_usa_zipcode_percent = ColumnPiiValidUsaZipcodePercentCheckSpec.from_dict(_valid_usa_zipcode_percent)




        _contains_usa_zipcode_percent = d.pop("contains_usa_zipcode_percent", UNSET)
        contains_usa_zipcode_percent: Union[Unset, ColumnPiiContainsUsaZipcodePercentCheckSpec]
        if isinstance(_contains_usa_zipcode_percent,  Unset):
            contains_usa_zipcode_percent = UNSET
        else:
            contains_usa_zipcode_percent = ColumnPiiContainsUsaZipcodePercentCheckSpec.from_dict(_contains_usa_zipcode_percent)




        _valid_email_percent = d.pop("valid_email_percent", UNSET)
        valid_email_percent: Union[Unset, ColumnPiiValidEmailPercentCheckSpec]
        if isinstance(_valid_email_percent,  Unset):
            valid_email_percent = UNSET
        else:
            valid_email_percent = ColumnPiiValidEmailPercentCheckSpec.from_dict(_valid_email_percent)




        _contains_email_percent = d.pop("contains_email_percent", UNSET)
        contains_email_percent: Union[Unset, ColumnPiiContainsEmailPercentCheckSpec]
        if isinstance(_contains_email_percent,  Unset):
            contains_email_percent = UNSET
        else:
            contains_email_percent = ColumnPiiContainsEmailPercentCheckSpec.from_dict(_contains_email_percent)




        _valid_ip4_address_percent = d.pop("valid_ip4_address_percent", UNSET)
        valid_ip4_address_percent: Union[Unset, ColumnPiiValidIp4AddressPercentCheckSpec]
        if isinstance(_valid_ip4_address_percent,  Unset):
            valid_ip4_address_percent = UNSET
        else:
            valid_ip4_address_percent = ColumnPiiValidIp4AddressPercentCheckSpec.from_dict(_valid_ip4_address_percent)




        _contains_ip4_percent = d.pop("contains_ip4_percent", UNSET)
        contains_ip4_percent: Union[Unset, ColumnPiiContainsIp4PercentCheckSpec]
        if isinstance(_contains_ip4_percent,  Unset):
            contains_ip4_percent = UNSET
        else:
            contains_ip4_percent = ColumnPiiContainsIp4PercentCheckSpec.from_dict(_contains_ip4_percent)




        _valid_ip6_address_percent = d.pop("valid_ip6_address_percent", UNSET)
        valid_ip6_address_percent: Union[Unset, ColumnPiiValidIp6AddressPercentCheckSpec]
        if isinstance(_valid_ip6_address_percent,  Unset):
            valid_ip6_address_percent = UNSET
        else:
            valid_ip6_address_percent = ColumnPiiValidIp6AddressPercentCheckSpec.from_dict(_valid_ip6_address_percent)




        _contains_ip6_percent = d.pop("contains_ip6_percent", UNSET)
        contains_ip6_percent: Union[Unset, ColumnPiiContainsIp6PercentCheckSpec]
        if isinstance(_contains_ip6_percent,  Unset):
            contains_ip6_percent = UNSET
        else:
            contains_ip6_percent = ColumnPiiContainsIp6PercentCheckSpec.from_dict(_contains_ip6_percent)




        column_pii_profiling_checks_spec = cls(
            valid_usa_phone_percent=valid_usa_phone_percent,
            contains_usa_phone_percent=contains_usa_phone_percent,
            valid_usa_zipcode_percent=valid_usa_zipcode_percent,
            contains_usa_zipcode_percent=contains_usa_zipcode_percent,
            valid_email_percent=valid_email_percent,
            contains_email_percent=contains_email_percent,
            valid_ip4_address_percent=valid_ip4_address_percent,
            contains_ip4_percent=contains_ip4_percent,
            valid_ip6_address_percent=valid_ip6_address_percent,
            contains_ip6_percent=contains_ip6_percent,
        )

        column_pii_profiling_checks_spec.additional_properties = d
        return column_pii_profiling_checks_spec

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
