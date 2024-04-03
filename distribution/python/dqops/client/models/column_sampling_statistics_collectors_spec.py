from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_sampling_column_samples_statistics_collector_spec import (
        ColumnSamplingColumnSamplesStatisticsCollectorSpec,
    )


T = TypeVar("T", bound="ColumnSamplingStatisticsCollectorsSpec")


@_attrs_define
class ColumnSamplingStatisticsCollectorsSpec:
    """
    Attributes:
        column_samples (Union[Unset, ColumnSamplingColumnSamplesStatisticsCollectorSpec]):
    """

    column_samples: Union[
        Unset, "ColumnSamplingColumnSamplesStatisticsCollectorSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        column_samples: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_samples, Unset):
            column_samples = self.column_samples.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if column_samples is not UNSET:
            field_dict["column_samples"] = column_samples

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_sampling_column_samples_statistics_collector_spec import (
            ColumnSamplingColumnSamplesStatisticsCollectorSpec,
        )

        d = src_dict.copy()
        _column_samples = d.pop("column_samples", UNSET)
        column_samples: Union[Unset, ColumnSamplingColumnSamplesStatisticsCollectorSpec]
        if isinstance(_column_samples, Unset):
            column_samples = UNSET
        else:
            column_samples = (
                ColumnSamplingColumnSamplesStatisticsCollectorSpec.from_dict(
                    _column_samples
                )
            )

        column_sampling_statistics_collectors_spec = cls(
            column_samples=column_samples,
        )

        column_sampling_statistics_collectors_spec.additional_properties = d
        return column_sampling_statistics_collectors_spec

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
