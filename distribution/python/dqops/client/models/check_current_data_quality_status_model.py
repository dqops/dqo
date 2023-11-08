from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_result_status import CheckResultStatus
from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckCurrentDataQualityStatusModel")


@_attrs_define
class CheckCurrentDataQualityStatusModel:
    """The most recent data quality status for a single data quality check. If data grouping is enabled, this model will
    return the highest data quality issue status from all data quality results for all data groups.

        Attributes:
            severity (Union[Unset, CheckResultStatus]):
            executed_at (Union[Unset, int]): The UTC timestamp when the check was recently executed.
    """

    severity: Union[Unset, CheckResultStatus] = UNSET
    executed_at: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        severity: Union[Unset, str] = UNSET
        if not isinstance(self.severity, Unset):
            severity = self.severity.value

        executed_at = self.executed_at

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if severity is not UNSET:
            field_dict["severity"] = severity
        if executed_at is not UNSET:
            field_dict["executed_at"] = executed_at

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _severity = d.pop("severity", UNSET)
        severity: Union[Unset, CheckResultStatus]
        if isinstance(_severity, Unset):
            severity = UNSET
        else:
            severity = CheckResultStatus(_severity)

        executed_at = d.pop("executed_at", UNSET)

        check_current_data_quality_status_model = cls(
            severity=severity,
            executed_at=executed_at,
        )

        check_current_data_quality_status_model.additional_properties = d
        return check_current_data_quality_status_model

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
