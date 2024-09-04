from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_lineage_source_spec_properties import (
        ColumnLineageSourceSpecProperties,
    )


T = TypeVar("T", bound="ColumnLineageSourceSpec")


@_attrs_define
class ColumnLineageSourceSpec:
    """
    Attributes:
        source_columns (Union[Unset, List[str]]): A list of source columns from the source table name from which this
            column receives data.
        properties (Union[Unset, ColumnLineageSourceSpecProperties]): A dictionary of mapping properties stored as a
            key/value dictionary. Data lineage synchronization tools that are importing data lineage mappings from external
            data lineage sources can use it to store mapping information.
    """

    source_columns: Union[Unset, List[str]] = UNSET
    properties: Union[Unset, "ColumnLineageSourceSpecProperties"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        source_columns: Union[Unset, List[str]] = UNSET
        if not isinstance(self.source_columns, Unset):
            source_columns = self.source_columns

        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if source_columns is not UNSET:
            field_dict["source_columns"] = source_columns
        if properties is not UNSET:
            field_dict["properties"] = properties

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_lineage_source_spec_properties import (
            ColumnLineageSourceSpecProperties,
        )

        d = src_dict.copy()
        source_columns = cast(List[str], d.pop("source_columns", UNSET))

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, ColumnLineageSourceSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = ColumnLineageSourceSpecProperties.from_dict(_properties)

        column_lineage_source_spec = cls(
            source_columns=source_columns,
            properties=properties,
        )

        column_lineage_source_spec.additional_properties = d
        return column_lineage_source_spec

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
