from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="SimilarTableModel")


@_attrs_define
class SimilarTableModel:
    """Model that describes a table that is similar to a reference table. Similar tables are used to build the data lineage
    graph.

        Attributes:
            difference (Union[Unset, int]): Table similarity score. Lower numbers indicate higher similarity.
            connection_name (Union[Unset, str]): Connection name
            schema_name (Union[Unset, str]): Schema name
            table_name (Union[Unset, str]): Table name
    """

    difference: Union[Unset, int] = UNSET
    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        difference = self.difference
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name = self.table_name

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if difference is not UNSET:
            field_dict["difference"] = difference
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if schema_name is not UNSET:
            field_dict["schema_name"] = schema_name
        if table_name is not UNSET:
            field_dict["table_name"] = table_name

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        difference = d.pop("difference", UNSET)

        connection_name = d.pop("connection_name", UNSET)

        schema_name = d.pop("schema_name", UNSET)

        table_name = d.pop("table_name", UNSET)

        similar_table_model = cls(
            difference=difference,
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
        )

        similar_table_model.additional_properties = d
        return similar_table_model

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
