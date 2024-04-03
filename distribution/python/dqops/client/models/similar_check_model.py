from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_target import CheckTarget
from ..models.check_time_scale import CheckTimeScale
from ..models.check_type import CheckType
from ..types import UNSET, Unset

T = TypeVar("T", bound="SimilarCheckModel")


@_attrs_define
class SimilarCheckModel:
    """Model that identifies a similar check in another category or another type of check (monitoring, partition).

    Attributes:
        check_target (Union[Unset, CheckTarget]):
        check_type (Union[Unset, CheckType]):
        time_scale (Union[Unset, CheckTimeScale]):
        category (Union[Unset, str]): The check's category.
        check_name (Union[Unset, str]): Similar check name in another category.
    """

    check_target: Union[Unset, CheckTarget] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    time_scale: Union[Unset, CheckTimeScale] = UNSET
    category: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_target: Union[Unset, str] = UNSET
        if not isinstance(self.check_target, Unset):
            check_target = self.check_target.value

        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        time_scale: Union[Unset, str] = UNSET
        if not isinstance(self.time_scale, Unset):
            time_scale = self.time_scale.value

        category = self.category
        check_name = self.check_name

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_target is not UNSET:
            field_dict["check_target"] = check_target
        if check_type is not UNSET:
            field_dict["check_type"] = check_type
        if time_scale is not UNSET:
            field_dict["time_scale"] = time_scale
        if category is not UNSET:
            field_dict["category"] = category
        if check_name is not UNSET:
            field_dict["check_name"] = check_name

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _check_target = d.pop("check_target", UNSET)
        check_target: Union[Unset, CheckTarget]
        if isinstance(_check_target, Unset):
            check_target = UNSET
        else:
            check_target = CheckTarget(_check_target)

        _check_type = d.pop("check_type", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        _time_scale = d.pop("time_scale", UNSET)
        time_scale: Union[Unset, CheckTimeScale]
        if isinstance(_time_scale, Unset):
            time_scale = UNSET
        else:
            time_scale = CheckTimeScale(_time_scale)

        category = d.pop("category", UNSET)

        check_name = d.pop("check_name", UNSET)

        similar_check_model = cls(
            check_target=check_target,
            check_type=check_type,
            time_scale=time_scale,
            category=category,
            check_name=check_name,
        )

        similar_check_model.additional_properties = d
        return similar_check_model

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
