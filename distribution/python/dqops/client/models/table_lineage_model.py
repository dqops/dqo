from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.domain_connection_table_key import DomainConnectionTableKey
    from ..models.table_lineage_flow_model import TableLineageFlowModel


T = TypeVar("T", bound="TableLineageModel")


@_attrs_define
class TableLineageModel:
    """The table lineage model that returns all upstream tables, downstream tables, or both.

    Attributes:
        relative_table (Union[Unset, DomainConnectionTableKey]): Table key that identifies a table in the data quality
            cache or a data lineage cache.
        flows (Union[Unset, List['TableLineageFlowModel']]): A list of data flows from source tables to direct target
            tables. Describes the data quality status of the source table.
    """

    relative_table: Union[Unset, "DomainConnectionTableKey"] = UNSET
    flows: Union[Unset, List["TableLineageFlowModel"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        relative_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.relative_table, Unset):
            relative_table = self.relative_table.to_dict()

        flows: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.flows, Unset):
            flows = []
            for flows_item_data in self.flows:
                flows_item = flows_item_data.to_dict()

                flows.append(flows_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if relative_table is not UNSET:
            field_dict["relative_table"] = relative_table
        if flows is not UNSET:
            field_dict["flows"] = flows

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.domain_connection_table_key import DomainConnectionTableKey
        from ..models.table_lineage_flow_model import TableLineageFlowModel

        d = src_dict.copy()
        _relative_table = d.pop("relative_table", UNSET)
        relative_table: Union[Unset, DomainConnectionTableKey]
        if isinstance(_relative_table, Unset):
            relative_table = UNSET
        else:
            relative_table = DomainConnectionTableKey.from_dict(_relative_table)

        flows = []
        _flows = d.pop("flows", UNSET)
        for flows_item_data in _flows or []:
            flows_item = TableLineageFlowModel.from_dict(flows_item_data)

            flows.append(flows_item)

        table_lineage_model = cls(
            relative_table=relative_table,
            flows=flows,
        )

        table_lineage_model.additional_properties = d
        return table_lineage_model

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
