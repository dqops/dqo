from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_spec import ColumnSpec
    from ..models.physical_table_name import PhysicalTableName


T = TypeVar("T", bound="ColumnModel")


@_attrs_define
class ColumnModel:
    """Full column model

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        table (Union[Unset, PhysicalTableName]):
        column_name (Union[Unset, str]): Column name.
        column_hash (Union[Unset, int]): Column hash that identifies the column using a unique hash code.
        spec (Union[Unset, ColumnSpec]):
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    connection_name: Union[Unset, str] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    column_name: Union[Unset, str] = UNSET
    column_hash: Union[Unset, int] = UNSET
    spec: Union[Unset, "ColumnSpec"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.table, Unset):
            table = self.table.to_dict()

        column_name = self.column_name
        column_hash = self.column_hash
        spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.spec, Unset):
            spec = self.spec.to_dict()

        can_edit = self.can_edit
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if table is not UNSET:
            field_dict["table"] = table
        if column_name is not UNSET:
            field_dict["column_name"] = column_name
        if column_hash is not UNSET:
            field_dict["column_hash"] = column_hash
        if spec is not UNSET:
            field_dict["spec"] = spec
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if yaml_parsing_error is not UNSET:
            field_dict["yaml_parsing_error"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_spec import ColumnSpec
        from ..models.physical_table_name import PhysicalTableName

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        _table = d.pop("table", UNSET)
        table: Union[Unset, PhysicalTableName]
        if isinstance(_table, Unset):
            table = UNSET
        else:
            table = PhysicalTableName.from_dict(_table)

        column_name = d.pop("column_name", UNSET)

        column_hash = d.pop("column_hash", UNSET)

        _spec = d.pop("spec", UNSET)
        spec: Union[Unset, ColumnSpec]
        if isinstance(_spec, Unset):
            spec = UNSET
        else:
            spec = ColumnSpec.from_dict(_spec)

        can_edit = d.pop("can_edit", UNSET)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        column_model = cls(
            connection_name=connection_name,
            table=table,
            column_name=column_name,
            column_hash=column_hash,
            spec=spec,
            can_edit=can_edit,
            yaml_parsing_error=yaml_parsing_error,
        )

        column_model.additional_properties = d
        return column_model

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
