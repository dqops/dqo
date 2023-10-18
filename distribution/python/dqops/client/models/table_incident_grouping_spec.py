from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.incident_grouping_level import IncidentGroupingLevel
from ..models.minimum_grouping_severity_level import MinimumGroupingSeverityLevel
from ..types import UNSET, Unset

T = TypeVar("T", bound="TableIncidentGroupingSpec")


@_attrs_define
class TableIncidentGroupingSpec:
    """
    Attributes:
        grouping_level (Union[Unset, IncidentGroupingLevel]):
        minimum_severity (Union[Unset, MinimumGroupingSeverityLevel]):
        divide_by_data_group (Union[Unset, bool]): Create separate data quality incidents for each data group, creating
            different incidents for different groups of rows. By default, data groups are ignored for grouping data quality
            issues into data quality incidents.
        disabled (Union[Unset, bool]): Disables data quality incident creation for failed data quality checks on the
            table.
    """

    grouping_level: Union[Unset, IncidentGroupingLevel] = UNSET
    minimum_severity: Union[Unset, MinimumGroupingSeverityLevel] = UNSET
    divide_by_data_group: Union[Unset, bool] = UNSET
    disabled: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        grouping_level: Union[Unset, str] = UNSET
        if not isinstance(self.grouping_level, Unset):
            grouping_level = self.grouping_level.value

        minimum_severity: Union[Unset, str] = UNSET
        if not isinstance(self.minimum_severity, Unset):
            minimum_severity = self.minimum_severity.value

        divide_by_data_group = self.divide_by_data_group
        disabled = self.disabled

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if grouping_level is not UNSET:
            field_dict["grouping_level"] = grouping_level
        if minimum_severity is not UNSET:
            field_dict["minimum_severity"] = minimum_severity
        if divide_by_data_group is not UNSET:
            field_dict["divide_by_data_group"] = divide_by_data_group
        if disabled is not UNSET:
            field_dict["disabled"] = disabled

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _grouping_level = d.pop("grouping_level", UNSET)
        grouping_level: Union[Unset, IncidentGroupingLevel]
        if isinstance(_grouping_level, Unset):
            grouping_level = UNSET
        else:
            grouping_level = IncidentGroupingLevel(_grouping_level)

        _minimum_severity = d.pop("minimum_severity", UNSET)
        minimum_severity: Union[Unset, MinimumGroupingSeverityLevel]
        if isinstance(_minimum_severity, Unset):
            minimum_severity = UNSET
        else:
            minimum_severity = MinimumGroupingSeverityLevel(_minimum_severity)

        divide_by_data_group = d.pop("divide_by_data_group", UNSET)

        disabled = d.pop("disabled", UNSET)

        table_incident_grouping_spec = cls(
            grouping_level=grouping_level,
            minimum_severity=minimum_severity,
            divide_by_data_group=divide_by_data_group,
            disabled=disabled,
        )

        table_incident_grouping_spec.additional_properties = d
        return table_incident_grouping_spec

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
