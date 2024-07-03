from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ErrorSamplerResult")


@_attrs_define
class ErrorSamplerResult:
    """Returns the result with the summary of the error samples collected.

    Attributes:
        executed_error_samplers (Union[Unset, int]): The total count of all executed error samplers. This count only
            includes data quality checks that have an error sampling template defined.
        columns_analyzed (Union[Unset, int]): The count of columns for which DQOps executed an error sampler and tried
            to collect error samples.
        columns_successfully_analyzed (Union[Unset, int]): The count of columns for which DQOps managed to obtain error
            samples.
        total_error_samplers_failed (Union[Unset, int]): The count of error samplers that failed to execute.
        total_error_samples_collected (Union[Unset, int]): The total number of error samples (values) that were
            collected.
    """

    executed_error_samplers: Union[Unset, int] = UNSET
    columns_analyzed: Union[Unset, int] = UNSET
    columns_successfully_analyzed: Union[Unset, int] = UNSET
    total_error_samplers_failed: Union[Unset, int] = UNSET
    total_error_samples_collected: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        executed_error_samplers = self.executed_error_samplers
        columns_analyzed = self.columns_analyzed
        columns_successfully_analyzed = self.columns_successfully_analyzed
        total_error_samplers_failed = self.total_error_samplers_failed
        total_error_samples_collected = self.total_error_samples_collected

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if executed_error_samplers is not UNSET:
            field_dict["executed_error_samplers"] = executed_error_samplers
        if columns_analyzed is not UNSET:
            field_dict["columns_analyzed"] = columns_analyzed
        if columns_successfully_analyzed is not UNSET:
            field_dict["columns_successfully_analyzed"] = columns_successfully_analyzed
        if total_error_samplers_failed is not UNSET:
            field_dict["total_error_samplers_failed"] = total_error_samplers_failed
        if total_error_samples_collected is not UNSET:
            field_dict["total_error_samples_collected"] = total_error_samples_collected

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        executed_error_samplers = d.pop("executed_error_samplers", UNSET)

        columns_analyzed = d.pop("columns_analyzed", UNSET)

        columns_successfully_analyzed = d.pop("columns_successfully_analyzed", UNSET)

        total_error_samplers_failed = d.pop("total_error_samplers_failed", UNSET)

        total_error_samples_collected = d.pop("total_error_samples_collected", UNSET)

        error_sampler_result = cls(
            executed_error_samplers=executed_error_samplers,
            columns_analyzed=columns_analyzed,
            columns_successfully_analyzed=columns_successfully_analyzed,
            total_error_samplers_failed=total_error_samplers_failed,
            total_error_samples_collected=total_error_samples_collected,
        )

        error_sampler_result.additional_properties = d
        return error_sampler_result

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
