from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.data_stream_level_spec_source import DataStreamLevelSpecSource
from ..types import UNSET, Unset

T = TypeVar("T", bound="DataStreamLevelSpec")


@attr.s(auto_attribs=True)
class DataStreamLevelSpec:
    """
    Attributes:
        source (Union[Unset, DataStreamLevelSpecSource]): The source of the data stream level value. The default stream
            level source is a tag. Assign a tag when there are multiple similar tables that store the same data for
            different areas (countries, etc.). This could be a country name if a table or partition stores information for
            that country.
        tag (Union[Unset, str]): Data stream tag. Assign a hardcoded (static) data stream level value (tag) when there
            are multiple similar tables that store the same data for different areas (countries, etc.). This could be a
            country name if a table or partition stores information for that country.
        column (Union[Unset, str]): Column name that contains a dynamic data stream level value (for dynamic data-driven
            data streams). Sensor queries will be extended with a GROUP BY {data stream level colum name}, sensors (and
            alerts) will be calculated for each unique value of the specified column. Also a separate time series will be
            tracked for each value.
        name (Union[Unset, str]): Data stream level name.
    """

    source: Union[Unset, DataStreamLevelSpecSource] = UNSET
    tag: Union[Unset, str] = UNSET
    column: Union[Unset, str] = UNSET
    name: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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
        source: Union[Unset, DataStreamLevelSpecSource]
        if isinstance(_source, Unset):
            source = UNSET
        else:
            source = DataStreamLevelSpecSource(_source)

        tag = d.pop("tag", UNSET)

        column = d.pop("column", UNSET)

        name = d.pop("name", UNSET)

        data_stream_level_spec = cls(
            source=source,
            tag=tag,
            column=column,
            name=name,
        )

        data_stream_level_spec.additional_properties = d
        return data_stream_level_spec

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
