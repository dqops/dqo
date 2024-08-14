from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_text_max_word_count_statistics_collector_spec import (
        ColumnTextMaxWordCountStatisticsCollectorSpec,
    )
    from ..models.column_text_min_word_count_statistics_collector_spec import (
        ColumnTextMinWordCountStatisticsCollectorSpec,
    )
    from ..models.column_text_text_datatype_detect_statistics_collector_spec import (
        ColumnTextTextDatatypeDetectStatisticsCollectorSpec,
    )
    from ..models.column_text_text_max_length_statistics_collector_spec import (
        ColumnTextTextMaxLengthStatisticsCollectorSpec,
    )
    from ..models.column_text_text_mean_length_statistics_collector_spec import (
        ColumnTextTextMeanLengthStatisticsCollectorSpec,
    )
    from ..models.column_text_text_min_length_statistics_collector_spec import (
        ColumnTextTextMinLengthStatisticsCollectorSpec,
    )


T = TypeVar("T", bound="ColumnTextStatisticsCollectorsSpec")


@_attrs_define
class ColumnTextStatisticsCollectorsSpec:
    """
    Attributes:
        text_max_length (Union[Unset, ColumnTextTextMaxLengthStatisticsCollectorSpec]):
        text_mean_length (Union[Unset, ColumnTextTextMeanLengthStatisticsCollectorSpec]):
        text_min_length (Union[Unset, ColumnTextTextMinLengthStatisticsCollectorSpec]):
        text_datatype_detect (Union[Unset, ColumnTextTextDatatypeDetectStatisticsCollectorSpec]):
        text_min_word_count (Union[Unset, ColumnTextMinWordCountStatisticsCollectorSpec]):
        text_max_word_count (Union[Unset, ColumnTextMaxWordCountStatisticsCollectorSpec]):
    """

    text_max_length: Union[Unset, "ColumnTextTextMaxLengthStatisticsCollectorSpec"] = (
        UNSET
    )
    text_mean_length: Union[
        Unset, "ColumnTextTextMeanLengthStatisticsCollectorSpec"
    ] = UNSET
    text_min_length: Union[Unset, "ColumnTextTextMinLengthStatisticsCollectorSpec"] = (
        UNSET
    )
    text_datatype_detect: Union[
        Unset, "ColumnTextTextDatatypeDetectStatisticsCollectorSpec"
    ] = UNSET
    text_min_word_count: Union[
        Unset, "ColumnTextMinWordCountStatisticsCollectorSpec"
    ] = UNSET
    text_max_word_count: Union[
        Unset, "ColumnTextMaxWordCountStatisticsCollectorSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        text_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.text_max_length, Unset):
            text_max_length = self.text_max_length.to_dict()

        text_mean_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.text_mean_length, Unset):
            text_mean_length = self.text_mean_length.to_dict()

        text_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.text_min_length, Unset):
            text_min_length = self.text_min_length.to_dict()

        text_datatype_detect: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.text_datatype_detect, Unset):
            text_datatype_detect = self.text_datatype_detect.to_dict()

        text_min_word_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.text_min_word_count, Unset):
            text_min_word_count = self.text_min_word_count.to_dict()

        text_max_word_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.text_max_word_count, Unset):
            text_max_word_count = self.text_max_word_count.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if text_max_length is not UNSET:
            field_dict["text_max_length"] = text_max_length
        if text_mean_length is not UNSET:
            field_dict["text_mean_length"] = text_mean_length
        if text_min_length is not UNSET:
            field_dict["text_min_length"] = text_min_length
        if text_datatype_detect is not UNSET:
            field_dict["text_datatype_detect"] = text_datatype_detect
        if text_min_word_count is not UNSET:
            field_dict["text_min_word_count"] = text_min_word_count
        if text_max_word_count is not UNSET:
            field_dict["text_max_word_count"] = text_max_word_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_text_max_word_count_statistics_collector_spec import (
            ColumnTextMaxWordCountStatisticsCollectorSpec,
        )
        from ..models.column_text_min_word_count_statistics_collector_spec import (
            ColumnTextMinWordCountStatisticsCollectorSpec,
        )
        from ..models.column_text_text_datatype_detect_statistics_collector_spec import (
            ColumnTextTextDatatypeDetectStatisticsCollectorSpec,
        )
        from ..models.column_text_text_max_length_statistics_collector_spec import (
            ColumnTextTextMaxLengthStatisticsCollectorSpec,
        )
        from ..models.column_text_text_mean_length_statistics_collector_spec import (
            ColumnTextTextMeanLengthStatisticsCollectorSpec,
        )
        from ..models.column_text_text_min_length_statistics_collector_spec import (
            ColumnTextTextMinLengthStatisticsCollectorSpec,
        )

        d = src_dict.copy()
        _text_max_length = d.pop("text_max_length", UNSET)
        text_max_length: Union[Unset, ColumnTextTextMaxLengthStatisticsCollectorSpec]
        if isinstance(_text_max_length, Unset):
            text_max_length = UNSET
        else:
            text_max_length = ColumnTextTextMaxLengthStatisticsCollectorSpec.from_dict(
                _text_max_length
            )

        _text_mean_length = d.pop("text_mean_length", UNSET)
        text_mean_length: Union[Unset, ColumnTextTextMeanLengthStatisticsCollectorSpec]
        if isinstance(_text_mean_length, Unset):
            text_mean_length = UNSET
        else:
            text_mean_length = (
                ColumnTextTextMeanLengthStatisticsCollectorSpec.from_dict(
                    _text_mean_length
                )
            )

        _text_min_length = d.pop("text_min_length", UNSET)
        text_min_length: Union[Unset, ColumnTextTextMinLengthStatisticsCollectorSpec]
        if isinstance(_text_min_length, Unset):
            text_min_length = UNSET
        else:
            text_min_length = ColumnTextTextMinLengthStatisticsCollectorSpec.from_dict(
                _text_min_length
            )

        _text_datatype_detect = d.pop("text_datatype_detect", UNSET)
        text_datatype_detect: Union[
            Unset, ColumnTextTextDatatypeDetectStatisticsCollectorSpec
        ]
        if isinstance(_text_datatype_detect, Unset):
            text_datatype_detect = UNSET
        else:
            text_datatype_detect = (
                ColumnTextTextDatatypeDetectStatisticsCollectorSpec.from_dict(
                    _text_datatype_detect
                )
            )

        _text_min_word_count = d.pop("text_min_word_count", UNSET)
        text_min_word_count: Union[Unset, ColumnTextMinWordCountStatisticsCollectorSpec]
        if isinstance(_text_min_word_count, Unset):
            text_min_word_count = UNSET
        else:
            text_min_word_count = (
                ColumnTextMinWordCountStatisticsCollectorSpec.from_dict(
                    _text_min_word_count
                )
            )

        _text_max_word_count = d.pop("text_max_word_count", UNSET)
        text_max_word_count: Union[Unset, ColumnTextMaxWordCountStatisticsCollectorSpec]
        if isinstance(_text_max_word_count, Unset):
            text_max_word_count = UNSET
        else:
            text_max_word_count = (
                ColumnTextMaxWordCountStatisticsCollectorSpec.from_dict(
                    _text_max_word_count
                )
            )

        column_text_statistics_collectors_spec = cls(
            text_max_length=text_max_length,
            text_mean_length=text_mean_length,
            text_min_length=text_min_length,
            text_datatype_detect=text_datatype_detect,
            text_min_word_count=text_min_word_count,
            text_max_word_count=text_max_word_count,
        )

        column_text_statistics_collectors_spec.additional_properties = d
        return column_text_statistics_collectors_spec

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
