from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_time_scale import CheckTimeScale
from ..models.check_type import CheckType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_comparison_grouping_columns_pair_spec import (
        TableComparisonGroupingColumnsPairSpec,
    )


T = TypeVar("T", bound="TableComparisonConfigurationSpec")


@_attrs_define
class TableComparisonConfigurationSpec:
    """
    Attributes:
        reference_table_connection_name (Union[Unset, str]): The name of the connection in DQOp where the reference
            table (the source of truth) is configured. When the connection name is not provided, DQOps will find the
            reference table on the connection of the parent table.
        reference_table_schema_name (Union[Unset, str]): The name of the schema where the reference table is imported
            into DQOps. The reference table's metadata must be imported into DQOps.
        reference_table_name (Union[Unset, str]): The name of the reference table that is imported into DQOps. The
            reference table's metadata must be imported into DQOps.
        compared_table_filter (Union[Unset, str]): Optional custom SQL filter expression that is added to the SQL query
            that retrieves the data from the compared table. This expression must be a SQL expression that will be added to
            the WHERE clause when querying the compared table.
        reference_table_filter (Union[Unset, str]): Optional custom SQL filter expression that is added to the SQL query
            that retrieves the data from the reference table (the source of truth). This expression must be a SQL expression
            that will be added to the WHERE clause when querying the reference table.
        check_type (Union[Unset, CheckType]):
        time_scale (Union[Unset, CheckTimeScale]):
        grouping_columns (Union[Unset, List['TableComparisonGroupingColumnsPairSpec']]): List of column pairs from both
            the compared table and the reference table that are used in a GROUP BY clause  for grouping both the compared
            table and the reference table (the source of truth). The columns are used in the next of the table comparison to
            join the results of data groups (row counts, sums of columns) between the compared table and the reference table
            to compare the differences.
    """

    reference_table_connection_name: Union[Unset, str] = UNSET
    reference_table_schema_name: Union[Unset, str] = UNSET
    reference_table_name: Union[Unset, str] = UNSET
    compared_table_filter: Union[Unset, str] = UNSET
    reference_table_filter: Union[Unset, str] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    time_scale: Union[Unset, CheckTimeScale] = UNSET
    grouping_columns: Union[Unset, List["TableComparisonGroupingColumnsPairSpec"]] = (
        UNSET
    )
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        reference_table_connection_name = self.reference_table_connection_name
        reference_table_schema_name = self.reference_table_schema_name
        reference_table_name = self.reference_table_name
        compared_table_filter = self.compared_table_filter
        reference_table_filter = self.reference_table_filter
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        time_scale: Union[Unset, str] = UNSET
        if not isinstance(self.time_scale, Unset):
            time_scale = self.time_scale.value

        grouping_columns: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.grouping_columns, Unset):
            grouping_columns = []
            for grouping_columns_item_data in self.grouping_columns:
                grouping_columns_item = grouping_columns_item_data.to_dict()

                grouping_columns.append(grouping_columns_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if reference_table_connection_name is not UNSET:
            field_dict["reference_table_connection_name"] = (
                reference_table_connection_name
            )
        if reference_table_schema_name is not UNSET:
            field_dict["reference_table_schema_name"] = reference_table_schema_name
        if reference_table_name is not UNSET:
            field_dict["reference_table_name"] = reference_table_name
        if compared_table_filter is not UNSET:
            field_dict["compared_table_filter"] = compared_table_filter
        if reference_table_filter is not UNSET:
            field_dict["reference_table_filter"] = reference_table_filter
        if check_type is not UNSET:
            field_dict["check_type"] = check_type
        if time_scale is not UNSET:
            field_dict["time_scale"] = time_scale
        if grouping_columns is not UNSET:
            field_dict["grouping_columns"] = grouping_columns

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_comparison_grouping_columns_pair_spec import (
            TableComparisonGroupingColumnsPairSpec,
        )

        d = src_dict.copy()
        reference_table_connection_name = d.pop(
            "reference_table_connection_name", UNSET
        )

        reference_table_schema_name = d.pop("reference_table_schema_name", UNSET)

        reference_table_name = d.pop("reference_table_name", UNSET)

        compared_table_filter = d.pop("compared_table_filter", UNSET)

        reference_table_filter = d.pop("reference_table_filter", UNSET)

        _check_type = d.pop("check_type", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        _time_scale = d.pop("time_scale", UNSET)
        time_scale: Union[Unset, CheckTimeScale]
        if isinstance(_time_scale, Unset):
            time_scale = UNSET
        else:
            time_scale = CheckTimeScale(_time_scale)

        grouping_columns = []
        _grouping_columns = d.pop("grouping_columns", UNSET)
        for grouping_columns_item_data in _grouping_columns or []:
            grouping_columns_item = TableComparisonGroupingColumnsPairSpec.from_dict(
                grouping_columns_item_data
            )

            grouping_columns.append(grouping_columns_item)

        table_comparison_configuration_spec = cls(
            reference_table_connection_name=reference_table_connection_name,
            reference_table_schema_name=reference_table_schema_name,
            reference_table_name=reference_table_name,
            compared_table_filter=compared_table_filter,
            reference_table_filter=reference_table_filter,
            check_type=check_type,
            time_scale=time_scale,
            grouping_columns=grouping_columns,
        )

        table_comparison_configuration_spec.additional_properties = d
        return table_comparison_configuration_spec

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
