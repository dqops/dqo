from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="TableSqlInvalidRecordCountSensorParametersSpec")


@_attrs_define
class TableSqlInvalidRecordCountSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        sql_query (Union[Unset, str]): SQL query that returns invalid values. The condition is evaluated for each row.
            The expression can use a {table} placeholder that is replaced with a full table name.
    """

    filter_: Union[Unset, str] = UNSET
    sql_query: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        sql_query = self.sql_query

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if sql_query is not UNSET:
            field_dict["sql_query"] = sql_query

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        sql_query = d.pop("sql_query", UNSET)

        table_sql_invalid_record_count_sensor_parameters_spec = cls(
            filter_=filter_,
            sql_query=sql_query,
        )

        table_sql_invalid_record_count_sensor_parameters_spec.additional_properties = d
        return table_sql_invalid_record_count_sensor_parameters_spec

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
