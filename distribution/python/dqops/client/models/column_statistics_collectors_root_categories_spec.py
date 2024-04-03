from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_nulls_statistics_collectors_spec import (
        ColumnNullsStatisticsCollectorsSpec,
    )
    from ..models.column_range_statistics_collectors_spec import (
        ColumnRangeStatisticsCollectorsSpec,
    )
    from ..models.column_sampling_statistics_collectors_spec import (
        ColumnSamplingStatisticsCollectorsSpec,
    )
    from ..models.column_text_statistics_collectors_spec import (
        ColumnTextStatisticsCollectorsSpec,
    )
    from ..models.column_uniqueness_statistics_collectors_spec import (
        ColumnUniquenessStatisticsCollectorsSpec,
    )


T = TypeVar("T", bound="ColumnStatisticsCollectorsRootCategoriesSpec")


@_attrs_define
class ColumnStatisticsCollectorsRootCategoriesSpec:
    """
    Attributes:
        nulls (Union[Unset, ColumnNullsStatisticsCollectorsSpec]):
        text (Union[Unset, ColumnTextStatisticsCollectorsSpec]):
        uniqueness (Union[Unset, ColumnUniquenessStatisticsCollectorsSpec]):
        range_ (Union[Unset, ColumnRangeStatisticsCollectorsSpec]):
        sampling (Union[Unset, ColumnSamplingStatisticsCollectorsSpec]):
        strings (Union[Unset, ColumnTextStatisticsCollectorsSpec]):
    """

    nulls: Union[Unset, "ColumnNullsStatisticsCollectorsSpec"] = UNSET
    text: Union[Unset, "ColumnTextStatisticsCollectorsSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessStatisticsCollectorsSpec"] = UNSET
    range_: Union[Unset, "ColumnRangeStatisticsCollectorsSpec"] = UNSET
    sampling: Union[Unset, "ColumnSamplingStatisticsCollectorsSpec"] = UNSET
    strings: Union[Unset, "ColumnTextStatisticsCollectorsSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        nulls: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls, Unset):
            nulls = self.nulls.to_dict()

        text: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.text, Unset):
            text = self.text.to_dict()

        uniqueness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.uniqueness, Unset):
            uniqueness = self.uniqueness.to_dict()

        range_: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.range_, Unset):
            range_ = self.range_.to_dict()

        sampling: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sampling, Unset):
            sampling = self.sampling.to_dict()

        strings: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.strings, Unset):
            strings = self.strings.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if nulls is not UNSET:
            field_dict["nulls"] = nulls
        if text is not UNSET:
            field_dict["text"] = text
        if uniqueness is not UNSET:
            field_dict["uniqueness"] = uniqueness
        if range_ is not UNSET:
            field_dict["range"] = range_
        if sampling is not UNSET:
            field_dict["sampling"] = sampling
        if strings is not UNSET:
            field_dict["strings"] = strings

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_nulls_statistics_collectors_spec import (
            ColumnNullsStatisticsCollectorsSpec,
        )
        from ..models.column_range_statistics_collectors_spec import (
            ColumnRangeStatisticsCollectorsSpec,
        )
        from ..models.column_sampling_statistics_collectors_spec import (
            ColumnSamplingStatisticsCollectorsSpec,
        )
        from ..models.column_text_statistics_collectors_spec import (
            ColumnTextStatisticsCollectorsSpec,
        )
        from ..models.column_uniqueness_statistics_collectors_spec import (
            ColumnUniquenessStatisticsCollectorsSpec,
        )

        d = src_dict.copy()
        _nulls = d.pop("nulls", UNSET)
        nulls: Union[Unset, ColumnNullsStatisticsCollectorsSpec]
        if isinstance(_nulls, Unset):
            nulls = UNSET
        else:
            nulls = ColumnNullsStatisticsCollectorsSpec.from_dict(_nulls)

        _text = d.pop("text", UNSET)
        text: Union[Unset, ColumnTextStatisticsCollectorsSpec]
        if isinstance(_text, Unset):
            text = UNSET
        else:
            text = ColumnTextStatisticsCollectorsSpec.from_dict(_text)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessStatisticsCollectorsSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessStatisticsCollectorsSpec.from_dict(_uniqueness)

        _range_ = d.pop("range", UNSET)
        range_: Union[Unset, ColumnRangeStatisticsCollectorsSpec]
        if isinstance(_range_, Unset):
            range_ = UNSET
        else:
            range_ = ColumnRangeStatisticsCollectorsSpec.from_dict(_range_)

        _sampling = d.pop("sampling", UNSET)
        sampling: Union[Unset, ColumnSamplingStatisticsCollectorsSpec]
        if isinstance(_sampling, Unset):
            sampling = UNSET
        else:
            sampling = ColumnSamplingStatisticsCollectorsSpec.from_dict(_sampling)

        _strings = d.pop("strings", UNSET)
        strings: Union[Unset, ColumnTextStatisticsCollectorsSpec]
        if isinstance(_strings, Unset):
            strings = UNSET
        else:
            strings = ColumnTextStatisticsCollectorsSpec.from_dict(_strings)

        column_statistics_collectors_root_categories_spec = cls(
            nulls=nulls,
            text=text,
            uniqueness=uniqueness,
            range_=range_,
            sampling=sampling,
            strings=strings,
        )

        column_statistics_collectors_root_categories_spec.additional_properties = d
        return column_statistics_collectors_root_categories_spec

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
