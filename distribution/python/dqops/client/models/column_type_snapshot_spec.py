from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="ColumnTypeSnapshotSpec")


@attr.s(auto_attribs=True)
class ColumnTypeSnapshotSpec:
    """
    Attributes:
        column_type (Union[Unset, str]): Column data type using the monitored database type names.
        nullable (Union[Unset, bool]): Column is nullable.
        length (Union[Unset, int]): Maximum length of text and binary columns.
        scale (Union[Unset, int]): Scale of a numeric (decimal) data type.
        precision (Union[Unset, int]): Precision of a numeric (decimal) data type.
    """

    column_type: Union[Unset, str] = UNSET
    nullable: Union[Unset, bool] = UNSET
    length: Union[Unset, int] = UNSET
    scale: Union[Unset, int] = UNSET
    precision: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        column_type = self.column_type
        nullable = self.nullable
        length = self.length
        scale = self.scale
        precision = self.precision

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if column_type is not UNSET:
            field_dict["column_type"] = column_type
        if nullable is not UNSET:
            field_dict["nullable"] = nullable
        if length is not UNSET:
            field_dict["length"] = length
        if scale is not UNSET:
            field_dict["scale"] = scale
        if precision is not UNSET:
            field_dict["precision"] = precision

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        column_type = d.pop("column_type", UNSET)

        nullable = d.pop("nullable", UNSET)

        length = d.pop("length", UNSET)

        scale = d.pop("scale", UNSET)

        precision = d.pop("precision", UNSET)

        column_type_snapshot_spec = cls(
            column_type=column_type,
            nullable=nullable,
            length=length,
            scale=scale,
            precision=precision,
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
