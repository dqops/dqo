from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.domain_connection_table_key import DomainConnectionTableKey
    from ..models.table_current_data_quality_status_model import (
        TableCurrentDataQualityStatusModel,
    )
    from ..models.table_lineage_flow_model import TableLineageFlowModel


T = TypeVar("T", bound="TableLineageModel")


@_attrs_define
class TableLineageModel:
    """The table lineage model that returns all upstream tables, downstream tables, or both.

    Attributes:
        relative_table (Union[Unset, DomainConnectionTableKey]): Table key that identifies a table in the data quality
            cache or a data lineage cache.
        relative_table_cumulative_quality_status (Union[Unset, TableCurrentDataQualityStatusModel]): The table's most
            recent data quality status. It is a summary of the results of the most recently executed data quality checks on
            the table. Verify the value of the highest_severity_level to see if there are any data quality issues on the
            table. The values of severity levels are: 0 - all data quality checks passed, 1 - a warning was detected, 2 - an
            error was detected, 3 - a fatal data quality issue was detected.
        flows (Union[Unset, List['TableLineageFlowModel']]): A list of data flows from source tables to direct target
            tables. Describes the data quality status of the source table.
        data_lineage_fully_loaded (Union[Unset, bool]): This flag tells if the data lineage was fully loaded. If any
            data flows are missing or the data quality status of some tables is missing, this flag will return false, which
            means that the data lineage must be loaded again.
    """

    relative_table: Union[Unset, "DomainConnectionTableKey"] = UNSET
    relative_table_cumulative_quality_status: Union[
        Unset, "TableCurrentDataQualityStatusModel"
    ] = UNSET
    flows: Union[Unset, List["TableLineageFlowModel"]] = UNSET
    data_lineage_fully_loaded: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        relative_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.relative_table, Unset):
            relative_table = self.relative_table.to_dict()

        relative_table_cumulative_quality_status: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.relative_table_cumulative_quality_status, Unset):
            relative_table_cumulative_quality_status = (
                self.relative_table_cumulative_quality_status.to_dict()
            )

        flows: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.flows, Unset):
            flows = []
            for flows_item_data in self.flows:
                flows_item = flows_item_data.to_dict()

                flows.append(flows_item)

        data_lineage_fully_loaded = self.data_lineage_fully_loaded

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if relative_table is not UNSET:
            field_dict["relative_table"] = relative_table
        if relative_table_cumulative_quality_status is not UNSET:
            field_dict["relative_table_cumulative_quality_status"] = (
                relative_table_cumulative_quality_status
            )
        if flows is not UNSET:
            field_dict["flows"] = flows
        if data_lineage_fully_loaded is not UNSET:
            field_dict["data_lineage_fully_loaded"] = data_lineage_fully_loaded

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.domain_connection_table_key import DomainConnectionTableKey
        from ..models.table_current_data_quality_status_model import (
            TableCurrentDataQualityStatusModel,
        )
        from ..models.table_lineage_flow_model import TableLineageFlowModel

        d = src_dict.copy()
        _relative_table = d.pop("relative_table", UNSET)
        relative_table: Union[Unset, DomainConnectionTableKey]
        if isinstance(_relative_table, Unset):
            relative_table = UNSET
        else:
            relative_table = DomainConnectionTableKey.from_dict(_relative_table)

        _relative_table_cumulative_quality_status = d.pop(
            "relative_table_cumulative_quality_status", UNSET
        )
        relative_table_cumulative_quality_status: Union[
            Unset, TableCurrentDataQualityStatusModel
        ]
        if isinstance(_relative_table_cumulative_quality_status, Unset):
            relative_table_cumulative_quality_status = UNSET
        else:
            relative_table_cumulative_quality_status = (
                TableCurrentDataQualityStatusModel.from_dict(
                    _relative_table_cumulative_quality_status
                )
            )

        flows = []
        _flows = d.pop("flows", UNSET)
        for flows_item_data in _flows or []:
            flows_item = TableLineageFlowModel.from_dict(flows_item_data)

            flows.append(flows_item)

        data_lineage_fully_loaded = d.pop("data_lineage_fully_loaded", UNSET)

        table_lineage_model = cls(
            relative_table=relative_table,
            relative_table_cumulative_quality_status=relative_table_cumulative_quality_status,
            flows=flows,
            data_lineage_fully_loaded=data_lineage_fully_loaded,
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
