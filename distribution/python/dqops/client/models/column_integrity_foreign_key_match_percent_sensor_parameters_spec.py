from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec")


@_attrs_define
class ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        foreign_table (Union[Unset, str]): This field can be used to define the name of the table to be compared to. In
            order to define the name of the table, user should write correct name as a String.
        foreign_column (Union[Unset, str]): This field can be used to define the name of the column to be compared to.
            In order to define the name of the column, user should write correct name as a String.
    """

    filter_: Union[Unset, str] = UNSET
    foreign_table: Union[Unset, str] = UNSET
    foreign_column: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        foreign_table = self.foreign_table
        foreign_column = self.foreign_column

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if foreign_table is not UNSET:
            field_dict["foreign_table"] = foreign_table
        if foreign_column is not UNSET:
            field_dict["foreign_column"] = foreign_column

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        foreign_table = d.pop("foreign_table", UNSET)

        foreign_column = d.pop("foreign_column", UNSET)

        column_integrity_foreign_key_match_percent_sensor_parameters_spec = cls(
            filter_=filter_,
            foreign_table=foreign_table,
            foreign_column=foreign_column,
        )

        column_integrity_foreign_key_match_percent_sensor_parameters_spec.additional_properties = (
            d
        )
        return column_integrity_foreign_key_match_percent_sensor_parameters_spec

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
