from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_lineage_source_spec_columns import TableLineageSourceSpecColumns
    from ..models.table_lineage_source_spec_properties import (
        TableLineageSourceSpecProperties,
    )


T = TypeVar("T", bound="TableLineageSourceSpec")


@_attrs_define
class TableLineageSourceSpec:
    """
    Attributes:
        source_connection (Union[Unset, str]): The name of a source connection that is defined in DQOps and contains a
            source table from which the current table receives data.
        source_schema (Union[Unset, str]): The name of a source schema within the source connection that contains a
            source table from which the current table receives data.
        source_table (Union[Unset, str]): The name of a source table in the source schema from which the current table
            receives data.
        data_lineage_source_tool (Union[Unset, str]): The name of a source tool from which this data lineage information
            was copied. This field should be filled when the data lineage was imported from another data catalog or a data
            lineage tracking platform.
        properties (Union[Unset, TableLineageSourceSpecProperties]): A dictionary of mapping properties stored as a
            key/value dictionary. Data lineage synchronization tools that are importing data lineage mappings from external
            data lineage sources can use it to store mapping information.
        columns (Union[Unset, TableLineageSourceSpecColumns]): Configuration of source columns for each column in the
            current table. The keys in this dictionary are column names in the current table. The object stored in the
            dictionary contain a list of source columns.
    """

    source_connection: Union[Unset, str] = UNSET
    source_schema: Union[Unset, str] = UNSET
    source_table: Union[Unset, str] = UNSET
    data_lineage_source_tool: Union[Unset, str] = UNSET
    properties: Union[Unset, "TableLineageSourceSpecProperties"] = UNSET
    columns: Union[Unset, "TableLineageSourceSpecColumns"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        source_connection = self.source_connection
        source_schema = self.source_schema
        source_table = self.source_table
        data_lineage_source_tool = self.data_lineage_source_tool
        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        columns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.columns, Unset):
            columns = self.columns.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if source_connection is not UNSET:
            field_dict["source_connection"] = source_connection
        if source_schema is not UNSET:
            field_dict["source_schema"] = source_schema
        if source_table is not UNSET:
            field_dict["source_table"] = source_table
        if data_lineage_source_tool is not UNSET:
            field_dict["data_lineage_source_tool"] = data_lineage_source_tool
        if properties is not UNSET:
            field_dict["properties"] = properties
        if columns is not UNSET:
            field_dict["columns"] = columns

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_lineage_source_spec_columns import (
            TableLineageSourceSpecColumns,
        )
        from ..models.table_lineage_source_spec_properties import (
            TableLineageSourceSpecProperties,
        )

        d = src_dict.copy()
        source_connection = d.pop("source_connection", UNSET)

        source_schema = d.pop("source_schema", UNSET)

        source_table = d.pop("source_table", UNSET)

        data_lineage_source_tool = d.pop("data_lineage_source_tool", UNSET)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, TableLineageSourceSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = TableLineageSourceSpecProperties.from_dict(_properties)

        _columns = d.pop("columns", UNSET)
        columns: Union[Unset, TableLineageSourceSpecColumns]
        if isinstance(_columns, Unset):
            columns = UNSET
        else:
            columns = TableLineageSourceSpecColumns.from_dict(_columns)

        table_lineage_source_spec = cls(
            source_connection=source_connection,
            source_schema=source_schema,
            source_table=source_table,
            data_lineage_source_tool=data_lineage_source_tool,
            properties=properties,
            columns=columns,
        )

        table_lineage_source_spec.additional_properties = d
        return table_lineage_source_spec

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
