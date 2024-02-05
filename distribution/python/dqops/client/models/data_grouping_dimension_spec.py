from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.data_grouping_dimension_source import DataGroupingDimensionSource
from ..types import UNSET, Unset

T = TypeVar("T", bound="DataGroupingDimensionSpec")


@_attrs_define
class DataGroupingDimensionSpec:
    """
    Attributes:
        source (Union[Unset, DataGroupingDimensionSource]):
        tag (Union[Unset, str]): The value assigned to the data quality grouping dimension when the source is 'tag'.
            Assign a hard-coded (static) value to the data grouping dimension (tag) when there are multiple similar tables
            storing the same data for different areas (countries, etc.). This can be the name of the country if the table or
            partition stores information for that country.
        column (Union[Unset, str]): Column name that contains a dynamic data grouping dimension value (for dynamic data-
            driven data groupings). Sensor queries will be extended with a GROUP BY {data group level colum name}, sensors
            (and alerts) will be calculated for each unique value of the specified column. Also a separate time series will
            be tracked for each value.
        name (Union[Unset, str]): Data grouping dimension name.
    """

    source: Union[Unset, DataGroupingDimensionSource] = UNSET
    tag: Union[Unset, str] = UNSET
    column: Union[Unset, str] = UNSET
    name: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        source: Union[Unset, str] = UNSET
        if not isinstance(self.source, Unset):
            source = self.source.value

        tag = self.tag
        column = self.column
        name = self.name

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if source is not UNSET:
            field_dict["source"] = source
        if tag is not UNSET:
            field_dict["tag"] = tag
        if column is not UNSET:
            field_dict["column"] = column
        if name is not UNSET:
            field_dict["name"] = name

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _source = d.pop("source", UNSET)
        source: Union[Unset, DataGroupingDimensionSource]
        if isinstance(_source, Unset):
            source = UNSET
        else:
            source = DataGroupingDimensionSource(_source)

        tag = d.pop("tag", UNSET)

        column = d.pop("column", UNSET)

        name = d.pop("name", UNSET)

        data_grouping_dimension_spec = cls(
            source=source,
            tag=tag,
            column=column,
            name=name,
        )

        data_grouping_dimension_spec.additional_properties = d
        return data_grouping_dimension_spec

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
