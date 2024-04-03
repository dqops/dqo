from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="TableSqlAggregatedExpressionSensorParametersSpec")


@_attrs_define
class TableSqlAggregatedExpressionSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        sql_expression (Union[Unset, str]): SQL aggregate expression that returns a numeric value calculated from rows.
            The expression is evaluated for the entire table or within a GROUP BY clause for daily partitions and/or data
            groups. The expression can use a {table} placeholder that is replaced with a full table name.
    """

    filter_: Union[Unset, str] = UNSET
    sql_expression: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        sql_expression = self.sql_expression

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if sql_expression is not UNSET:
            field_dict["sql_expression"] = sql_expression

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        sql_expression = d.pop("sql_expression", UNSET)

        table_sql_aggregated_expression_sensor_parameters_spec = cls(
            filter_=filter_,
            sql_expression=sql_expression,
        )

        table_sql_aggregated_expression_sensor_parameters_spec.additional_properties = d
        return table_sql_aggregated_expression_sensor_parameters_spec

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
