from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="IcebergFileFormatSpec")


@_attrs_define
class IcebergFileFormatSpec:
    """
    Attributes:
        allow_moved_paths (Union[Unset, bool]): The option ensures that some path resolution is performed, which allows
            scanning Iceberg tables that are moved.
    """

    allow_moved_paths: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        allow_moved_paths = self.allow_moved_paths

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if allow_moved_paths is not UNSET:
            field_dict["allow_moved_paths"] = allow_moved_paths

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        allow_moved_paths = d.pop("allow_moved_paths", UNSET)

        iceberg_file_format_spec = cls(
            allow_moved_paths=allow_moved_paths,
        )

        iceberg_file_format_spec.additional_properties = d
        return iceberg_file_format_spec

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
