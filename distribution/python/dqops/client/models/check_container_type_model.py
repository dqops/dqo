from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_time_scale import CheckTimeScale
from ..models.check_type import CheckType
from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckContainerTypeModel")


@_attrs_define
class CheckContainerTypeModel:
    """Model identifying the check type and timescale of checks belonging to a container.

    Attributes:
        check_type (Union[Unset, CheckType]):
        check_time_scale (Union[Unset, CheckTimeScale]):
    """

    check_type: Union[Unset, CheckType] = UNSET
    check_time_scale: Union[Unset, CheckTimeScale] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        check_time_scale: Union[Unset, str] = UNSET
        if not isinstance(self.check_time_scale, Unset):
            check_time_scale = self.check_time_scale.value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_type is not UNSET:
            field_dict["check_type"] = check_type
        if check_time_scale is not UNSET:
            field_dict["check_time_scale"] = check_time_scale

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _check_type = d.pop("check_type", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        _check_time_scale = d.pop("check_time_scale", UNSET)
        check_time_scale: Union[Unset, CheckTimeScale]
        if isinstance(_check_time_scale, Unset):
            check_time_scale = UNSET
        else:
            check_time_scale = CheckTimeScale(_check_time_scale)

        check_container_type_model = cls(
            check_type=check_type,
            check_time_scale=check_time_scale,
        )

        check_container_type_model.additional_properties = d
        return check_container_type_model

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
