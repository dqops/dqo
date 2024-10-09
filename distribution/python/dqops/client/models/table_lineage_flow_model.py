from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.domain_connection_table_key import DomainConnectionTableKey
    from ..models.table_current_data_quality_status_model import (
        TableCurrentDataQualityStatusModel,
    )


T = TypeVar("T", bound="TableLineageFlowModel")


@_attrs_define
class TableLineageFlowModel:
    """Table lineage flow model that describes the data flow from one table to another table, and the data quality status
    of the source table.

        Attributes:
            source_table (Union[Unset, DomainConnectionTableKey]): Table key that identifies a table in the data quality
                cache or a data lineage cache.
            target_table (Union[Unset, DomainConnectionTableKey]): Table key that identifies a table in the data quality
                cache or a data lineage cache.
            source_table_quality_status (Union[Unset, TableCurrentDataQualityStatusModel]): The table's most recent data
                quality status. It is a summary of the results of the most recently executed data quality checks on the table.
                Verify the value of the highest_severity_level to see if there are any data quality issues on the table. The
                values of severity levels are: 0 - all data quality checks passed, 1 - a warning was detected, 2 - an error was
                detected, 3 - a fatal data quality issue was detected.
            target_table_quality_status (Union[Unset, TableCurrentDataQualityStatusModel]): The table's most recent data
                quality status. It is a summary of the results of the most recently executed data quality checks on the table.
                Verify the value of the highest_severity_level to see if there are any data quality issues on the table. The
                values of severity levels are: 0 - all data quality checks passed, 1 - a warning was detected, 2 - an error was
                detected, 3 - a fatal data quality issue was detected.
            upstream_combined_quality_status (Union[Unset, TableCurrentDataQualityStatusModel]): The table's most recent
                data quality status. It is a summary of the results of the most recently executed data quality checks on the
                table. Verify the value of the highest_severity_level to see if there are any data quality issues on the table.
                The values of severity levels are: 0 - all data quality checks passed, 1 - a warning was detected, 2 - an error
                was detected, 3 - a fatal data quality issue was detected.
            row_count (Union[Unset, int]): The row count of the source table.
    """

    source_table: Union[Unset, "DomainConnectionTableKey"] = UNSET
    target_table: Union[Unset, "DomainConnectionTableKey"] = UNSET
    source_table_quality_status: Union[Unset, "TableCurrentDataQualityStatusModel"] = (
        UNSET
    )
    target_table_quality_status: Union[Unset, "TableCurrentDataQualityStatusModel"] = (
        UNSET
    )
    upstream_combined_quality_status: Union[
        Unset, "TableCurrentDataQualityStatusModel"
    ] = UNSET
    row_count: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        source_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.source_table, Unset):
            source_table = self.source_table.to_dict()

        target_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.target_table, Unset):
            target_table = self.target_table.to_dict()

        source_table_quality_status: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.source_table_quality_status, Unset):
            source_table_quality_status = self.source_table_quality_status.to_dict()

        target_table_quality_status: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.target_table_quality_status, Unset):
            target_table_quality_status = self.target_table_quality_status.to_dict()

        upstream_combined_quality_status: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.upstream_combined_quality_status, Unset):
            upstream_combined_quality_status = (
                self.upstream_combined_quality_status.to_dict()
            )

        row_count = self.row_count

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if source_table is not UNSET:
            field_dict["source_table"] = source_table
        if target_table is not UNSET:
            field_dict["target_table"] = target_table
        if source_table_quality_status is not UNSET:
            field_dict["source_table_quality_status"] = source_table_quality_status
        if target_table_quality_status is not UNSET:
            field_dict["target_table_quality_status"] = target_table_quality_status
        if upstream_combined_quality_status is not UNSET:
            field_dict["upstream_combined_quality_status"] = (
                upstream_combined_quality_status
            )
        if row_count is not UNSET:
            field_dict["row_count"] = row_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.domain_connection_table_key import DomainConnectionTableKey
        from ..models.table_current_data_quality_status_model import (
            TableCurrentDataQualityStatusModel,
        )

        d = src_dict.copy()
        _source_table = d.pop("source_table", UNSET)
        source_table: Union[Unset, DomainConnectionTableKey]
        if isinstance(_source_table, Unset):
            source_table = UNSET
        else:
            source_table = DomainConnectionTableKey.from_dict(_source_table)

        _target_table = d.pop("target_table", UNSET)
        target_table: Union[Unset, DomainConnectionTableKey]
        if isinstance(_target_table, Unset):
            target_table = UNSET
        else:
            target_table = DomainConnectionTableKey.from_dict(_target_table)

        _source_table_quality_status = d.pop("source_table_quality_status", UNSET)
        source_table_quality_status: Union[Unset, TableCurrentDataQualityStatusModel]
        if isinstance(_source_table_quality_status, Unset):
            source_table_quality_status = UNSET
        else:
            source_table_quality_status = TableCurrentDataQualityStatusModel.from_dict(
                _source_table_quality_status
            )

        _target_table_quality_status = d.pop("target_table_quality_status", UNSET)
        target_table_quality_status: Union[Unset, TableCurrentDataQualityStatusModel]
        if isinstance(_target_table_quality_status, Unset):
            target_table_quality_status = UNSET
        else:
            target_table_quality_status = TableCurrentDataQualityStatusModel.from_dict(
                _target_table_quality_status
            )

        _upstream_combined_quality_status = d.pop(
            "upstream_combined_quality_status", UNSET
        )
        upstream_combined_quality_status: Union[
            Unset, TableCurrentDataQualityStatusModel
        ]
        if isinstance(_upstream_combined_quality_status, Unset):
            upstream_combined_quality_status = UNSET
        else:
            upstream_combined_quality_status = (
                TableCurrentDataQualityStatusModel.from_dict(
                    _upstream_combined_quality_status
                )
            )

        row_count = d.pop("row_count", UNSET)

        table_lineage_flow_model = cls(
            source_table=source_table,
            target_table=target_table,
            source_table_quality_status=source_table_quality_status,
            target_table_quality_status=target_table_quality_status,
            upstream_combined_quality_status=upstream_combined_quality_status,
            row_count=row_count,
        )

        table_lineage_flow_model.additional_properties = d
        return table_lineage_flow_model

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
