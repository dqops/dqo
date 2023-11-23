from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_result_status import CheckResultStatus
from ..models.check_time_scale import CheckTimeScale
from ..models.check_type import CheckType
from ..models.rule_severity_level import RuleSeverityLevel
from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckCurrentDataQualityStatusModel")


@_attrs_define
class CheckCurrentDataQualityStatusModel:
    """The most recent data quality status for a single data quality check. If data grouping is enabled, the
    *current_severity* will be the highest data quality issue status from all data quality results for all data groups.
    For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed
    time range.

        Attributes:
            current_severity (Union[Unset, CheckResultStatus]):
            highest_historical_severity (Union[Unset, RuleSeverityLevel]):
            last_executed_at (Union[Unset, int]): The UTC timestamp when the check was recently executed.
            check_type (Union[Unset, CheckType]):
            time_scale (Union[Unset, CheckTimeScale]):
            category (Union[Unset, str]): Check category name, such as nulls, schema, strings, volume.
            quality_dimension (Union[Unset, str]): Data quality dimension, such as Completeness, Uniqueness, Validity.
    """

    current_severity: Union[Unset, CheckResultStatus] = UNSET
    highest_historical_severity: Union[Unset, RuleSeverityLevel] = UNSET
    last_executed_at: Union[Unset, int] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    time_scale: Union[Unset, CheckTimeScale] = UNSET
    category: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        current_severity: Union[Unset, str] = UNSET
        if not isinstance(self.current_severity, Unset):
            current_severity = self.current_severity.value

        highest_historical_severity: Union[Unset, str] = UNSET
        if not isinstance(self.highest_historical_severity, Unset):
            highest_historical_severity = self.highest_historical_severity.value

        last_executed_at = self.last_executed_at
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        time_scale: Union[Unset, str] = UNSET
        if not isinstance(self.time_scale, Unset):
            time_scale = self.time_scale.value

        category = self.category
        quality_dimension = self.quality_dimension

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if current_severity is not UNSET:
            field_dict["current_severity"] = current_severity
        if highest_historical_severity is not UNSET:
            field_dict["highest_historical_severity"] = highest_historical_severity
        if last_executed_at is not UNSET:
            field_dict["last_executed_at"] = last_executed_at
        if check_type is not UNSET:
            field_dict["check_type"] = check_type
        if time_scale is not UNSET:
            field_dict["time_scale"] = time_scale
        if category is not UNSET:
            field_dict["category"] = category
        if quality_dimension is not UNSET:
            field_dict["quality_dimension"] = quality_dimension

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _current_severity = d.pop("current_severity", UNSET)
        current_severity: Union[Unset, CheckResultStatus]
        if isinstance(_current_severity, Unset):
            current_severity = UNSET
        else:
            current_severity = CheckResultStatus(_current_severity)

        _highest_historical_severity = d.pop("highest_historical_severity", UNSET)
        highest_historical_severity: Union[Unset, RuleSeverityLevel]
        if isinstance(_highest_historical_severity, Unset):
            highest_historical_severity = UNSET
        else:
            highest_historical_severity = RuleSeverityLevel(
                _highest_historical_severity
            )

        last_executed_at = d.pop("last_executed_at", UNSET)

        _check_type = d.pop("check_type", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        _time_scale = d.pop("time_scale", UNSET)
        time_scale: Union[Unset, CheckTimeScale]
        if isinstance(_time_scale, Unset):
            time_scale = UNSET
        else:
            time_scale = CheckTimeScale(_time_scale)

        category = d.pop("category", UNSET)

        quality_dimension = d.pop("quality_dimension", UNSET)

        check_current_data_quality_status_model = cls(
            current_severity=current_severity,
            highest_historical_severity=highest_historical_severity,
            last_executed_at=last_executed_at,
            check_type=check_type,
            time_scale=time_scale,
            category=category,
            quality_dimension=quality_dimension,
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
