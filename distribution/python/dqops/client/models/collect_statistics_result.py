from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="CollectStatisticsResult")


@_attrs_define
class CollectStatisticsResult:
    """Returns the result with the summary of the statistics collected.

    Attributes:
        executed_statistics_collectors (Union[Unset, int]): The total count of all executed statistics collectors.
        columns_analyzed (Union[Unset, int]): The count of columns for which DQOps executed a collector and tried to
            read the statistics.
        columns_successfully_analyzed (Union[Unset, int]): The count of columns for which DQOps managed to obtain
            statistics.
        total_collectors_failed (Union[Unset, int]): The count of statistics collectors that failed to execute.
        total_collected_results (Union[Unset, int]): The total number of results that were collected.
    """

    executed_statistics_collectors: Union[Unset, int] = UNSET
    columns_analyzed: Union[Unset, int] = UNSET
    columns_successfully_analyzed: Union[Unset, int] = UNSET
    total_collectors_failed: Union[Unset, int] = UNSET
    total_collected_results: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        executed_statistics_collectors = self.executed_statistics_collectors
        columns_analyzed = self.columns_analyzed
        columns_successfully_analyzed = self.columns_successfully_analyzed
        total_collectors_failed = self.total_collectors_failed
        total_collected_results = self.total_collected_results

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if executed_statistics_collectors is not UNSET:
            field_dict["executed_statistics_collectors"] = (
                executed_statistics_collectors
            )
        if columns_analyzed is not UNSET:
            field_dict["columns_analyzed"] = columns_analyzed
        if columns_successfully_analyzed is not UNSET:
            field_dict["columns_successfully_analyzed"] = columns_successfully_analyzed
        if total_collectors_failed is not UNSET:
            field_dict["total_collectors_failed"] = total_collectors_failed
        if total_collected_results is not UNSET:
            field_dict["total_collected_results"] = total_collected_results

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        executed_statistics_collectors = d.pop("executed_statistics_collectors", UNSET)

        columns_analyzed = d.pop("columns_analyzed", UNSET)

        columns_successfully_analyzed = d.pop("columns_successfully_analyzed", UNSET)

        total_collectors_failed = d.pop("total_collectors_failed", UNSET)

        total_collected_results = d.pop("total_collected_results", UNSET)

        collect_statistics_result = cls(
            executed_statistics_collectors=executed_statistics_collectors,
            columns_analyzed=columns_analyzed,
            columns_successfully_analyzed=columns_successfully_analyzed,
            total_collectors_failed=total_collectors_failed,
            total_collected_results=total_collected_results,
        )

        collect_statistics_result.additional_properties = d
        return collect_statistics_result

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
