from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.data_stream_mapping_spec import DataStreamMappingSpec


T = TypeVar("T", bound="DataStreamTrimmedModel")


@attr.s(auto_attribs=True)
class DataStreamTrimmedModel:
    """Data stream model with trimmed path

    Attributes:
        data_stream_name (Union[Unset, str]): Data stream name.
        spec (Union[Unset, DataStreamMappingSpec]):
    """

    data_stream_name: Union[Unset, str] = UNSET
    spec: Union[Unset, "DataStreamMappingSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        data_stream_name = self.data_stream_name
        spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.spec, Unset):
            spec = self.spec.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if data_stream_name is not UNSET:
            field_dict["data_stream_name"] = data_stream_name
        if spec is not UNSET:
            field_dict["spec"] = spec

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.data_stream_mapping_spec import DataStreamMappingSpec

        d = src_dict.copy()
        data_stream_name = d.pop("data_stream_name", UNSET)

        _spec = d.pop("spec", UNSET)
        spec: Union[Unset, DataStreamMappingSpec]
        if isinstance(_spec, Unset):
            spec = UNSET
        else:
            spec = DataStreamMappingSpec.from_dict(_spec)

        data_stream_trimmed_model = cls(
            data_stream_name=data_stream_name,
            spec=spec,
        )

        data_stream_trimmed_model.additional_properties = d
        return data_stream_trimmed_model

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
