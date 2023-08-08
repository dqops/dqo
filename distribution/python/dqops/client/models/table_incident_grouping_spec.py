from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.table_incident_grouping_spec_grouping_level import (
    TableIncidentGroupingSpecGroupingLevel,
)
from ..models.table_incident_grouping_spec_minimum_severity import (
    TableIncidentGroupingSpecMinimumSeverity,
)
from ..types import UNSET, Unset

T = TypeVar("T", bound="TableIncidentGroupingSpec")


@attr.s(auto_attribs=True)
class TableIncidentGroupingSpec:
    """
    Attributes:
        grouping_level (Union[Unset, TableIncidentGroupingSpecGroupingLevel]): Grouping level of failed data quality
            checks for creating higher level data quality incidents. The default grouping level is by a table, a data
            quality dimension and a check category (i.e. a datatype data quality incident detected on a table X in the
            numeric checks category).
        minimum_severity (Union[Unset, TableIncidentGroupingSpecMinimumSeverity]): Minimum severity level of data
            quality issues that are grouped into incidents. The default minimum severity level is 'warning'. Other supported
            severity levels are 'error' and 'fatal'.
        divide_by_data_group (Union[Unset, bool]): Create separate data quality incidents for each data group, creating
            different incidents for different groups of rows. By default, data groups are ignored for grouping data quality
            issues into data quality incidents.
        disabled (Union[Unset, bool]): Disables data quality incident creation for failed data quality checks on the
            table.
    """

    grouping_level: Union[Unset, TableIncidentGroupingSpecGroupingLevel] = UNSET
    minimum_severity: Union[Unset, TableIncidentGroupingSpecMinimumSeverity] = UNSET
    divide_by_data_group: Union[Unset, bool] = UNSET
    disabled: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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
        grouping_level: Union[Unset, TableIncidentGroupingSpecGroupingLevel]
        if isinstance(_grouping_level, Unset):
            grouping_level = UNSET
        else:
            grouping_level = TableIncidentGroupingSpecGroupingLevel(_grouping_level)

        _minimum_severity = d.pop("minimum_severity", UNSET)
        minimum_severity: Union[Unset, TableIncidentGroupingSpecMinimumSeverity]
        if isinstance(_minimum_severity, Unset):
            minimum_severity = UNSET
        else:
            minimum_severity = TableIncidentGroupingSpecMinimumSeverity(
                _minimum_severity
            )

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
