from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.physical_table_name import PhysicalTableName
    from ..models.table_comparison_grouping_column_pair_model import (
        TableComparisonGroupingColumnPairModel,
    )


T = TypeVar("T", bound="TableComparisonConfigurationModel")


@attr.s(auto_attribs=True)
class TableComparisonConfigurationModel:
    """Model that contains the basic information about a table comparison configuration that specifies how the current
    table could be compared to another table that is a source of truth for comparison.

        Attributes:
            table_comparison_configuration_name (Union[Unset, str]): The name of the table comparison configuration that is
                defined in the 'table_comparisons' node on the table specification.
            compared_connection (Union[Unset, str]): Compared connection name - the connection name to the data source that
                is compared (verified).
            compared_table (Union[Unset, PhysicalTableName]):
            reference_connection (Union[Unset, str]): Reference connection name - the connection name to the data source
                that has the reference data to compare to.
            reference_table (Union[Unset, PhysicalTableName]):
            compared_table_grouping_name (Union[Unset, str]): The name of the data grouping configuration on the parent
                table that will be used for comparison. When the parent table has no data grouping configurations, compares the
                whole table without grouping.
            reference_table_grouping_name (Union[Unset, str]): The name of the data grouping configuration on the referenced
                name that will be used for comparison. When the reference table has no data grouping configurations, compares
                the whole table without grouping. The data grouping configurations on the compared table and the reference table
                must have the same grouping dimension levels configured, but the configuration (the names of the columns) could
                be different.
            grouping_columns (Union[Unset, List['TableComparisonGroupingColumnPairModel']]): List of column pairs from both
                the compared table and the reference table that are used in a GROUP BY clause  for grouping both the compared
                table and the reference table (the source of truth). The columns are used in the next of the table comparison to
                join the results of data groups (row counts, sums of columns) between the compared table and the reference table
                to compare the differences.
    """

    table_comparison_configuration_name: Union[Unset, str] = UNSET
    compared_connection: Union[Unset, str] = UNSET
    compared_table: Union[Unset, "PhysicalTableName"] = UNSET
    reference_connection: Union[Unset, str] = UNSET
    reference_table: Union[Unset, "PhysicalTableName"] = UNSET
    compared_table_grouping_name: Union[Unset, str] = UNSET
    reference_table_grouping_name: Union[Unset, str] = UNSET
    grouping_columns: Union[
        Unset, List["TableComparisonGroupingColumnPairModel"]
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        table_comparison_configuration_name = self.table_comparison_configuration_name
        compared_connection = self.compared_connection
        compared_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.compared_table, Unset):
            compared_table = self.compared_table.to_dict()

        reference_connection = self.reference_connection
        reference_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.reference_table, Unset):
            reference_table = self.reference_table.to_dict()

        compared_table_grouping_name = self.compared_table_grouping_name
        reference_table_grouping_name = self.reference_table_grouping_name
        grouping_columns: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.grouping_columns, Unset):
            grouping_columns = []
            for grouping_columns_item_data in self.grouping_columns:
                grouping_columns_item = grouping_columns_item_data.to_dict()

                grouping_columns.append(grouping_columns_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if table_comparison_configuration_name is not UNSET:
            field_dict[
                "table_comparison_configuration_name"
            ] = table_comparison_configuration_name
        if compared_connection is not UNSET:
            field_dict["compared_connection"] = compared_connection
        if compared_table is not UNSET:
            field_dict["compared_table"] = compared_table
        if reference_connection is not UNSET:
            field_dict["reference_connection"] = reference_connection
        if reference_table is not UNSET:
            field_dict["reference_table"] = reference_table
        if compared_table_grouping_name is not UNSET:
            field_dict["compared_table_grouping_name"] = compared_table_grouping_name
        if reference_table_grouping_name is not UNSET:
            field_dict["reference_table_grouping_name"] = reference_table_grouping_name
        if grouping_columns is not UNSET:
            field_dict["grouping_columns"] = grouping_columns

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.physical_table_name import PhysicalTableName
        from ..models.table_comparison_grouping_column_pair_model import (
            TableComparisonGroupingColumnPairModel,
        )

        d = src_dict.copy()
        table_comparison_configuration_name = d.pop(
            "table_comparison_configuration_name", UNSET
        )

        compared_connection = d.pop("compared_connection", UNSET)

        _compared_table = d.pop("compared_table", UNSET)
        compared_table: Union[Unset, PhysicalTableName]
        if isinstance(_compared_table, Unset):
            compared_table = UNSET
        else:
            compared_table = PhysicalTableName.from_dict(_compared_table)

        reference_connection = d.pop("reference_connection", UNSET)

        _reference_table = d.pop("reference_table", UNSET)
        reference_table: Union[Unset, PhysicalTableName]
        if isinstance(_reference_table, Unset):
            reference_table = UNSET
        else:
            reference_table = PhysicalTableName.from_dict(_reference_table)

        compared_table_grouping_name = d.pop("compared_table_grouping_name", UNSET)

        reference_table_grouping_name = d.pop("reference_table_grouping_name", UNSET)

        grouping_columns = []
        _grouping_columns = d.pop("grouping_columns", UNSET)
        for grouping_columns_item_data in _grouping_columns or []:
            grouping_columns_item = TableComparisonGroupingColumnPairModel.from_dict(
                grouping_columns_item_data
            )

            grouping_columns.append(grouping_columns_item)

        table_comparison_configuration_model = cls(
            table_comparison_configuration_name=table_comparison_configuration_name,
            compared_connection=compared_connection,
            compared_table=compared_table,
            reference_connection=reference_connection,
            reference_table=reference_table,
            compared_table_grouping_name=compared_table_grouping_name,
            reference_table_grouping_name=reference_table_grouping_name,
            grouping_columns=grouping_columns,
        )

        table_comparison_configuration_model.additional_properties = d
        return table_comparison_configuration_model

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
