from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ColumnTypeSnapshotSpec")


@_attrs_define
class ColumnTypeSnapshotSpec:
    """
    Attributes:
        column_type (Union[Unset, str]): Column data type using the monitored database type names.
        nullable (Union[Unset, bool]): Column is nullable.
        length (Union[Unset, int]): Maximum length of text and binary columns.
        precision (Union[Unset, int]): Precision of a numeric (decimal) data type.
        scale (Union[Unset, int]): Scale of a numeric (decimal) data type.
        nested (Union[Unset, bool]): This field is a nested field inside another STRUCT. It is used to identify nested
            fields in JSON files.
    """

    column_type: Union[Unset, str] = UNSET
    nullable: Union[Unset, bool] = UNSET
    length: Union[Unset, int] = UNSET
    precision: Union[Unset, int] = UNSET
    scale: Union[Unset, int] = UNSET
    nested: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        column_type = self.column_type
        nullable = self.nullable
        length = self.length
        precision = self.precision
        scale = self.scale
        nested = self.nested

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if column_type is not UNSET:
            field_dict["column_type"] = column_type
        if nullable is not UNSET:
            field_dict["nullable"] = nullable
        if length is not UNSET:
            field_dict["length"] = length
        if precision is not UNSET:
            field_dict["precision"] = precision
        if scale is not UNSET:
            field_dict["scale"] = scale
        if nested is not UNSET:
            field_dict["nested"] = nested

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        column_type = d.pop("column_type", UNSET)

        nullable = d.pop("nullable", UNSET)

        length = d.pop("length", UNSET)

        precision = d.pop("precision", UNSET)

        scale = d.pop("scale", UNSET)

        nested = d.pop("nested", UNSET)

        column_type_snapshot_spec = cls(
            column_type=column_type,
            nullable=nullable,
            length=length,
            precision=precision,
            scale=scale,
            nested=nested,
        )

        column_type_snapshot_spec.additional_properties = d
        return column_type_snapshot_spec

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
