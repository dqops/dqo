from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.data_grouping_configuration_spec import DataGroupingConfigurationSpec


T = TypeVar("T", bound="TableSpecGroupings")


@_attrs_define
class TableSpecGroupings:
    """Data grouping configurations list. Data grouping configurations are configured in two cases: (1) the data in the
    table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for
    example a table contains data from multiple countries and there is a 'country' column used for partitioning. (2) a
    tag is assigned to a table (within a data grouping level hierarchy), when the data is segmented at a table level
    (similar tables store the same information, but for different countries, etc.).

    """

    additional_properties: Dict[str, "DataGroupingConfigurationSpec"] = _attrs_field(
        init=False, factory=dict
    )

    def to_dict(self) -> Dict[str, Any]:
        pass

        field_dict: Dict[str, Any] = {}
        for prop_name, prop in self.additional_properties.items():
            field_dict[prop_name] = prop.to_dict()

        field_dict.update({})

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.data_grouping_configuration_spec import (
            DataGroupingConfigurationSpec,
        )

        d = src_dict.copy()
        table_spec_groupings = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = DataGroupingConfigurationSpec.from_dict(prop_dict)

            additional_properties[prop_name] = additional_property

        table_spec_groupings.additional_properties = additional_properties
        return table_spec_groupings

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "DataGroupingConfigurationSpec":
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: "DataGroupingConfigurationSpec") -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
