from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.table_comparison_configuration_model_check_type import (
    TableComparisonConfigurationModelCheckType,
)
from ..models.table_comparison_configuration_model_time_scale import (
    TableComparisonConfigurationModelTimeScale,
)
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
            check_type (Union[Unset, TableComparisonConfigurationModelCheckType]): The type of checks (profiling, recurring,
                partitioned) that this check comparison configuration is applicable. The default value is 'profiling'.
            time_scale (Union[Unset, TableComparisonConfigurationModelTimeScale]): The time scale that this check comparison
                configuration is applicable. Supported values are 'daily' and 'monthly' for recurring and partitioned checks or
                an empty value for profiling checks.
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
    check_type: Union[Unset, TableComparisonConfigurationModelCheckType] = UNSET
    time_scale: Union[Unset, TableComparisonConfigurationModelTimeScale] = UNSET
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
        if check_type is not UNSET:
            field_dict["check_type"] = check_type
        if time_scale is not UNSET:
            field_dict["time_scale"] = time_scale
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

        _check_type = d.pop("check_type", UNSET)
        check_type: Union[Unset, TableComparisonConfigurationModelCheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = TableComparisonConfigurationModelCheckType(_check_type)

        _time_scale = d.pop("time_scale", UNSET)
        time_scale: Union[Unset, TableComparisonConfigurationModelTimeScale]
        if isinstance(_time_scale, Unset):
            time_scale = UNSET
        else:
            time_scale = TableComparisonConfigurationModelTimeScale(_time_scale)

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
            check_type=check_type,
            time_scale=time_scale,
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
