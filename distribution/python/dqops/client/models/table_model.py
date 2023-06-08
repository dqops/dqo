from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_spec import TableSpec


T = TypeVar("T", bound="TableModel")


@attr.s(auto_attribs=True)
class TableModel:
    """Full table model, including all nested objects like columns or checks.

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        table_hash (Union[Unset, int]): Table hash that identifies the table using a unique hash code.
        spec (Union[Unset, TableSpec]):
    """

    connection_name: Union[Unset, str] = UNSET
    table_hash: Union[Unset, int] = UNSET
    spec: Union[Unset, "TableSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        table_hash = self.table_hash
        spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.spec, Unset):
            spec = self.spec.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if table_hash is not UNSET:
            field_dict["table_hash"] = table_hash
        if spec is not UNSET:
            field_dict["spec"] = spec

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_spec import TableSpec

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        table_hash = d.pop("table_hash", UNSET)

        _spec = d.pop("spec", UNSET)
        spec: Union[Unset, TableSpec]
        if isinstance(_spec, Unset):
            spec = UNSET
        else:
            spec = TableSpec.from_dict(_spec)

        table_model = cls(
            connection_name=connection_name,
            table_hash=table_hash,
            spec=spec,
        )

        table_model.additional_properties = d
        return table_model

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
