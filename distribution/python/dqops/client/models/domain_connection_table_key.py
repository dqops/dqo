from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.physical_table_name import PhysicalTableName


T = TypeVar("T", bound="DomainConnectionTableKey")


@_attrs_define
class DomainConnectionTableKey:
    """Table key that identifies a table in the data quality cache or a data lineage cache.

    Attributes:
        data_domain (Union[Unset, str]): Data domain name.
        connection_name (Union[Unset, str]): Connection name.
        physical_table_name (Union[Unset, PhysicalTableName]):
        compact_key (Union[Unset, str]): A string key that identifies the table within the data domain. It is based on
            the connection, schema and table names.
    """

    data_domain: Union[Unset, str] = UNSET
    connection_name: Union[Unset, str] = UNSET
    physical_table_name: Union[Unset, "PhysicalTableName"] = UNSET
    compact_key: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        data_domain = self.data_domain
        connection_name = self.connection_name
        physical_table_name: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.physical_table_name, Unset):
            physical_table_name = self.physical_table_name.to_dict()

        compact_key = self.compact_key

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if data_domain is not UNSET:
            field_dict["data_domain"] = data_domain
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if physical_table_name is not UNSET:
            field_dict["physical_table_name"] = physical_table_name
        if compact_key is not UNSET:
            field_dict["compact_key"] = compact_key

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.physical_table_name import PhysicalTableName

        d = src_dict.copy()
        data_domain = d.pop("data_domain", UNSET)

        connection_name = d.pop("connection_name", UNSET)

        _physical_table_name = d.pop("physical_table_name", UNSET)
        physical_table_name: Union[Unset, PhysicalTableName]
        if isinstance(_physical_table_name, Unset):
            physical_table_name = UNSET
        else:
            physical_table_name = PhysicalTableName.from_dict(_physical_table_name)

        compact_key = d.pop("compact_key", UNSET)

        domain_connection_table_key = cls(
            data_domain=data_domain,
            connection_name=connection_name,
            physical_table_name=physical_table_name,
            compact_key=compact_key,
        )

        domain_connection_table_key.additional_properties = d
        return domain_connection_table_key

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
