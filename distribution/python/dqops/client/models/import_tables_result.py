from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_spec import TableSpec


T = TypeVar("T", bound="ImportTablesResult")


@_attrs_define
class ImportTablesResult:
    """Result object returned from the "import tables" job. Contains the original table schemas and column schemas of
    imported tables.

        Attributes:
            source_table_specs (Union[Unset, List['TableSpec']]): Table schemas (including column schemas) of imported
                tables.
    """

    source_table_specs: Union[Unset, List["TableSpec"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        source_table_specs: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.source_table_specs, Unset):
            source_table_specs = []
            for source_table_specs_item_data in self.source_table_specs:
                source_table_specs_item = source_table_specs_item_data.to_dict()

                source_table_specs.append(source_table_specs_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if source_table_specs is not UNSET:
            field_dict["source_table_specs"] = source_table_specs

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_spec import TableSpec

        d = src_dict.copy()
        source_table_specs = []
        _source_table_specs = d.pop("source_table_specs", UNSET)
        for source_table_specs_item_data in _source_table_specs or []:
            source_table_specs_item = TableSpec.from_dict(source_table_specs_item_data)

            source_table_specs.append(source_table_specs_item)

        import_tables_result = cls(
            source_table_specs=source_table_specs,
        )

        import_tables_result.additional_properties = d
        return import_tables_result

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
