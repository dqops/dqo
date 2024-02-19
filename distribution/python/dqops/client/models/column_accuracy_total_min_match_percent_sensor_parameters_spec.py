from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ColumnAccuracyTotalMinMatchPercentSensorParametersSpec")


@_attrs_define
class ColumnAccuracyTotalMinMatchPercentSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        referenced_table (Union[Unset, str]): The name of the reference table. DQOps accepts the name in two forms: a
            fully qualified name including the schema name, for example landing_zone.customer_raw, or only a table name.
            When only a table name is used, DQOps assumes that the table is in the same schema as the analyzed table, and
            prefixes the name with the schema and optionally database name.
        referenced_column (Union[Unset, str]): The name of a column in the reference table. DQOps calculates an
            aggregate value on that column and compares it with the value in the analyzed table.
    """

    filter_: Union[Unset, str] = UNSET
    referenced_table: Union[Unset, str] = UNSET
    referenced_column: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        referenced_table = self.referenced_table
        referenced_column = self.referenced_column

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if referenced_table is not UNSET:
            field_dict["referenced_table"] = referenced_table
        if referenced_column is not UNSET:
            field_dict["referenced_column"] = referenced_column

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        referenced_table = d.pop("referenced_table", UNSET)

        referenced_column = d.pop("referenced_column", UNSET)

        column_accuracy_total_min_match_percent_sensor_parameters_spec = cls(
            filter_=filter_,
            referenced_table=referenced_table,
            referenced_column=referenced_column,
        )

        column_accuracy_total_min_match_percent_sensor_parameters_spec.additional_properties = (
            d
        )
        return column_accuracy_total_min_match_percent_sensor_parameters_spec

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
