from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.check_container_type_model_check_time_scale import (
    CheckContainerTypeModelCheckTimeScale,
)
from ..models.check_container_type_model_check_type import (
    CheckContainerTypeModelCheckType,
)
from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckContainerTypeModel")


@attr.s(auto_attribs=True)
class CheckContainerTypeModel:
    """Model identifying the check type and timescale of checks belonging to a container.

    Attributes:
        check_type (Union[Unset, CheckContainerTypeModelCheckType]): Check type.
        check_time_scale (Union[Unset, CheckContainerTypeModelCheckTimeScale]): Check timescale.
    """

    check_type: Union[Unset, CheckContainerTypeModelCheckType] = UNSET
    check_time_scale: Union[Unset, CheckContainerTypeModelCheckTimeScale] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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
        check_type: Union[Unset, CheckContainerTypeModelCheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckContainerTypeModelCheckType(_check_type)

        _check_time_scale = d.pop("check_time_scale", UNSET)
        check_time_scale: Union[Unset, CheckContainerTypeModelCheckTimeScale]
        if isinstance(_check_time_scale, Unset):
            check_time_scale = UNSET
        else:
            check_time_scale = CheckContainerTypeModelCheckTimeScale(_check_time_scale)

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
