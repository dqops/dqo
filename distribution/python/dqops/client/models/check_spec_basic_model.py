from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckSpecBasicModel")


@attr.s(auto_attribs=True)
class CheckSpecBasicModel:
    """Check spec basic model

    Attributes:
        check_name (Union[Unset, str]): Check name
        full_check_name (Union[Unset, str]): Full check name
        custom (Union[Unset, bool]): This check has is a custom check or was customized by the user.
        built_in (Union[Unset, bool]): This check is provided with DQO as a built-in check.
    """

    check_name: Union[Unset, str] = UNSET
    full_check_name: Union[Unset, str] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_name = self.check_name
        full_check_name = self.full_check_name
        custom = self.custom
        built_in = self.built_in

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_name is not UNSET:
            field_dict["check_name"] = check_name
        if full_check_name is not UNSET:
            field_dict["full_check_name"] = full_check_name
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["built_in"] = built_in

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        check_name = d.pop("check_name", UNSET)

        full_check_name = d.pop("full_check_name", UNSET)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("built_in", UNSET)

        check_spec_basic_model = cls(
            check_name=check_name,
            full_check_name=full_check_name,
            custom=custom,
            built_in=built_in,
        )

        check_spec_basic_model.additional_properties = d
        return check_spec_basic_model

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
