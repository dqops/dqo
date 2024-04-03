from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.hierarchy_id_model_path_item import HierarchyIdModelPathItem


T = TypeVar("T", bound="HierarchyIdModel")


@_attrs_define
class HierarchyIdModel:
    """
    Attributes:
        path (Union[Unset, List['HierarchyIdModelPathItem']]):
    """

    path: Union[Unset, List["HierarchyIdModelPathItem"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        path: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.path, Unset):
            path = []
            for path_item_data in self.path:
                path_item = path_item_data.to_dict()

                path.append(path_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if path is not UNSET:
            field_dict["path"] = path

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.hierarchy_id_model_path_item import HierarchyIdModelPathItem

        d = src_dict.copy()
        path = []
        _path = d.pop("path", UNSET)
        for path_item_data in _path or []:
            path_item = HierarchyIdModelPathItem.from_dict(path_item_data)

            path.append(path_item)

        hierarchy_id_model = cls(
            path=path,
        )

        hierarchy_id_model.additional_properties = d
        return hierarchy_id_model

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
