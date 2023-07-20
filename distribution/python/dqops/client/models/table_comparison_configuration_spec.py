from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="TableComparisonConfigurationSpec")


@attr.s(auto_attribs=True)
class TableComparisonConfigurationSpec:
    """
    Attributes:
        compared_table_grouping_name (Union[Unset, str]): The name of the data grouping configuration on the parent
            table (the compared table) that will be used for comparison. When the data grouping name is not given then
            compares the whole table without grouping (i.e. the row count of the whole table, the sum of column values for a
            whole table).
        reference_table_grouping_name (Union[Unset, str]): The name of the data grouping configuration on the referenced
            name that will be used for comparison. When the data grouping name is not given then compares the whole table
            without grouping (i.e. the row count of the whole table, the sum of column values for a whole table). The data
            grouping configurations on the parent table and the reference table must have the same grouping dimension levels
            configured, but the configuration (the names of the columns) could be different.
        reference_table_connection_name (Union[Unset, str]): The name of the connection in DQO where the reference table
            (the source of truth) is configured. When the connection name is not provided, DQO will find the reference table
            on the connection of the parent table.
        reference_table_schema_name (Union[Unset, str]): The name of the schema where the reference table is imported
            into DQO. The reference table's metadata must be imported into DQO.
        reference_table_name (Union[Unset, str]): The name of the reference table that is imported into DQO. The
            reference table's metadata must be imported into DQO.
        compared_table_filter (Union[Unset, str]): Optional custom SQL filter expression that is added to the SQL query
            that retrieves the data from the compared table. This expression must be a SQL expression that will be added to
            the WHERE clause when querying the compared table.
        reference_table_filter (Union[Unset, str]): Optional custom SQL filter expression that is added to the SQL query
            that retrieves the data from the reference table (the source of truth). This expression must be a SQL expression
            that will be added to the WHERE clause when querying the reference table.
    """

    compared_table_grouping_name: Union[Unset, str] = UNSET
    reference_table_grouping_name: Union[Unset, str] = UNSET
    reference_table_connection_name: Union[Unset, str] = UNSET
    reference_table_schema_name: Union[Unset, str] = UNSET
    reference_table_name: Union[Unset, str] = UNSET
    compared_table_filter: Union[Unset, str] = UNSET
    reference_table_filter: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        compared_table_grouping_name = self.compared_table_grouping_name
        reference_table_grouping_name = self.reference_table_grouping_name
        reference_table_connection_name = self.reference_table_connection_name
        reference_table_schema_name = self.reference_table_schema_name
        reference_table_name = self.reference_table_name
        compared_table_filter = self.compared_table_filter
        reference_table_filter = self.reference_table_filter

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if compared_table_grouping_name is not UNSET:
            field_dict["compared_table_grouping_name"] = compared_table_grouping_name
        if reference_table_grouping_name is not UNSET:
            field_dict["reference_table_grouping_name"] = reference_table_grouping_name
        if reference_table_connection_name is not UNSET:
            field_dict[
                "reference_table_connection_name"
            ] = reference_table_connection_name
        if reference_table_schema_name is not UNSET:
            field_dict["reference_table_schema_name"] = reference_table_schema_name
        if reference_table_name is not UNSET:
            field_dict["reference_table_name"] = reference_table_name
        if compared_table_filter is not UNSET:
            field_dict["compared_table_filter"] = compared_table_filter
        if reference_table_filter is not UNSET:
            field_dict["reference_table_filter"] = reference_table_filter

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        compared_table_grouping_name = d.pop("compared_table_grouping_name", UNSET)

        reference_table_grouping_name = d.pop("reference_table_grouping_name", UNSET)

        reference_table_connection_name = d.pop(
            "reference_table_connection_name", UNSET
        )

        reference_table_schema_name = d.pop("reference_table_schema_name", UNSET)

        reference_table_name = d.pop("reference_table_name", UNSET)

        compared_table_filter = d.pop("compared_table_filter", UNSET)

        reference_table_filter = d.pop("reference_table_filter", UNSET)

        table_comparison_configuration_spec = cls(
            compared_table_grouping_name=compared_table_grouping_name,
            reference_table_grouping_name=reference_table_grouping_name,
            reference_table_connection_name=reference_table_connection_name,
            reference_table_schema_name=reference_table_schema_name,
            reference_table_name=reference_table_name,
            compared_table_filter=compared_table_filter,
            reference_table_filter=reference_table_filter,
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
