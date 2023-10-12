from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.provider_type import ProviderType
from ..types import UNSET, Unset

T = TypeVar("T", bound="ProviderSensorListModel")


@_attrs_define
class ProviderSensorListModel:
    """Provider sensor list model

    Attributes:
        provider_type (Union[Unset, ProviderType]):
        custom (Union[Unset, bool]): This connection specific template is a custom sensor template or was customized by
            the user.
        built_in (Union[Unset, bool]): This connection specific template is provided with DQOps as a built-in sensor.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
    """

    provider_type: Union[Unset, ProviderType] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        provider_type: Union[Unset, str] = UNSET
        if not isinstance(self.provider_type, Unset):
            provider_type = self.provider_type.value

        custom = self.custom
        built_in = self.built_in
        can_edit = self.can_edit

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if provider_type is not UNSET:
            field_dict["provider_type"] = provider_type
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["built_in"] = built_in
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _provider_type = d.pop("provider_type", UNSET)
        provider_type: Union[Unset, ProviderType]
        if isinstance(_provider_type, Unset):
            provider_type = UNSET
        else:
            provider_type = ProviderType(_provider_type)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("built_in", UNSET)

        can_edit = d.pop("can_edit", UNSET)

        provider_sensor_list_model = cls(
            provider_type=provider_type,
            custom=custom,
            built_in=built_in,
            can_edit=can_edit,
        )

        provider_sensor_list_model.additional_properties = d
        return provider_sensor_list_model

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
