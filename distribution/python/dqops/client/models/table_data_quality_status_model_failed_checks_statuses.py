from typing import Any, Dict, List, Type, TypeVar

import attr

from ..models.table_data_quality_status_model_failed_checks_statuses_additional_property import (
    TableDataQualityStatusModelFailedChecksStatusesAdditionalProperty,
)

T = TypeVar("T", bound="TableDataQualityStatusModelFailedChecksStatuses")


@attr.s(auto_attribs=True)
class TableDataQualityStatusModelFailedChecksStatuses:
    """The paths to all failed data quality checks (keys) and severity of the highest data quality issue that was detected.
    Table-level checks are identified by the check name. Column-level checks are identified as a
    check_name[column_name].

    """

    additional_properties: Dict[
        str, TableDataQualityStatusModelFailedChecksStatusesAdditionalProperty
    ] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        field_dict: Dict[str, Any] = {}
        for prop_name, prop in self.additional_properties.items():
            field_dict[prop_name] = prop.value

        field_dict.update({})

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        table_data_quality_status_model_failed_checks_statuses = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = (
                TableDataQualityStatusModelFailedChecksStatusesAdditionalProperty(
                    prop_dict
                )
            )

            additional_properties[prop_name] = additional_property

        table_data_quality_status_model_failed_checks_statuses.additional_properties = (
            additional_properties
        )
        return table_data_quality_status_model_failed_checks_statuses

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(
        self, key: str
    ) -> TableDataQualityStatusModelFailedChecksStatusesAdditionalProperty:
        return self.additional_properties[key]

    def __setitem__(
        self,
        key: str,
        value: TableDataQualityStatusModelFailedChecksStatusesAdditionalProperty,
    ) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
