from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_strings_string_datatype_detect_statistics_collector_spec import (
        ColumnStringsStringDatatypeDetectStatisticsCollectorSpec,
    )
    from ..models.column_strings_string_max_length_statistics_collector_spec import (
        ColumnStringsStringMaxLengthStatisticsCollectorSpec,
    )
    from ..models.column_strings_string_mean_length_statistics_collector_spec import (
        ColumnStringsStringMeanLengthStatisticsCollectorSpec,
    )
    from ..models.column_strings_string_min_length_statistics_collector_spec import (
        ColumnStringsStringMinLengthStatisticsCollectorSpec,
    )


T = TypeVar("T", bound="ColumnStringsStatisticsCollectorsSpec")


@_attrs_define
class ColumnStringsStatisticsCollectorsSpec:
    """
    Attributes:
        string_max_length (Union[Unset, ColumnStringsStringMaxLengthStatisticsCollectorSpec]):
        string_mean_length (Union[Unset, ColumnStringsStringMeanLengthStatisticsCollectorSpec]):
        string_min_length (Union[Unset, ColumnStringsStringMinLengthStatisticsCollectorSpec]):
        string_datatype_detect (Union[Unset, ColumnStringsStringDatatypeDetectStatisticsCollectorSpec]):
    """

    string_max_length: Union[
        Unset, "ColumnStringsStringMaxLengthStatisticsCollectorSpec"
    ] = UNSET
    string_mean_length: Union[
        Unset, "ColumnStringsStringMeanLengthStatisticsCollectorSpec"
    ] = UNSET
    string_min_length: Union[
        Unset, "ColumnStringsStringMinLengthStatisticsCollectorSpec"
    ] = UNSET
    string_datatype_detect: Union[
        Unset, "ColumnStringsStringDatatypeDetectStatisticsCollectorSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        string_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_max_length, Unset):
            string_max_length = self.string_max_length.to_dict()

        string_mean_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_mean_length, Unset):
            string_mean_length = self.string_mean_length.to_dict()

        string_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_min_length, Unset):
            string_min_length = self.string_min_length.to_dict()

        string_datatype_detect: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_datatype_detect, Unset):
            string_datatype_detect = self.string_datatype_detect.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if string_max_length is not UNSET:
            field_dict["string_max_length"] = string_max_length
        if string_mean_length is not UNSET:
            field_dict["string_mean_length"] = string_mean_length
        if string_min_length is not UNSET:
            field_dict["string_min_length"] = string_min_length
        if string_datatype_detect is not UNSET:
            field_dict["string_datatype_detect"] = string_datatype_detect

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_strings_string_datatype_detect_statistics_collector_spec import (
            ColumnStringsStringDatatypeDetectStatisticsCollectorSpec,
        )
        from ..models.column_strings_string_max_length_statistics_collector_spec import (
            ColumnStringsStringMaxLengthStatisticsCollectorSpec,
        )
        from ..models.column_strings_string_mean_length_statistics_collector_spec import (
            ColumnStringsStringMeanLengthStatisticsCollectorSpec,
        )
        from ..models.column_strings_string_min_length_statistics_collector_spec import (
            ColumnStringsStringMinLengthStatisticsCollectorSpec,
        )

        d = src_dict.copy()
        _string_max_length = d.pop("string_max_length", UNSET)
        string_max_length: Union[
            Unset, ColumnStringsStringMaxLengthStatisticsCollectorSpec
        ]
        if isinstance(_string_max_length, Unset):
            string_max_length = UNSET
        else:
            string_max_length = (
                ColumnStringsStringMaxLengthStatisticsCollectorSpec.from_dict(
                    _string_max_length
                )
            )

        _string_mean_length = d.pop("string_mean_length", UNSET)
        string_mean_length: Union[
            Unset, ColumnStringsStringMeanLengthStatisticsCollectorSpec
        ]
        if isinstance(_string_mean_length, Unset):
            string_mean_length = UNSET
        else:
            string_mean_length = (
                ColumnStringsStringMeanLengthStatisticsCollectorSpec.from_dict(
                    _string_mean_length
                )
            )

        _string_min_length = d.pop("string_min_length", UNSET)
        string_min_length: Union[
            Unset, ColumnStringsStringMinLengthStatisticsCollectorSpec
        ]
        if isinstance(_string_min_length, Unset):
            string_min_length = UNSET
        else:
            string_min_length = (
                ColumnStringsStringMinLengthStatisticsCollectorSpec.from_dict(
                    _string_min_length
                )
            )

        _string_datatype_detect = d.pop("string_datatype_detect", UNSET)
        string_datatype_detect: Union[
            Unset, ColumnStringsStringDatatypeDetectStatisticsCollectorSpec
        ]
        if isinstance(_string_datatype_detect, Unset):
            string_datatype_detect = UNSET
        else:
            string_datatype_detect = (
                ColumnStringsStringDatatypeDetectStatisticsCollectorSpec.from_dict(
                    _string_datatype_detect
                )
            )

        column_strings_statistics_collectors_spec = cls(
            string_max_length=string_max_length,
            string_mean_length=string_mean_length,
            string_min_length=string_min_length,
            string_datatype_detect=string_datatype_detect,
        )

        column_strings_statistics_collectors_spec.additional_properties = d
        return column_strings_statistics_collectors_spec

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
