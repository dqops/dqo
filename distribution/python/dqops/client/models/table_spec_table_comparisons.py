from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

import attr

if TYPE_CHECKING:
    from ..models.table_comparison_configuration_spec import (
        TableComparisonConfigurationSpec,
    )


T = TypeVar("T", bound="TableSpecTableComparisons")


@attr.s(auto_attribs=True)
class TableSpecTableComparisons:
    """Dictionary of data comparison configurations. Data comparison configurations are used for cross data-source
    comparisons to compare this table (called the compared table) with other reference tables (the source of truth). The
    reference table's metadata must be imported into DQO, but the reference table could be located on a different data
    source. DQO will compare metrics calculated for groups of rows (using a GROUP BY clause). For each comparison, the
    user must specify a name of a data grouping. The number of data grouping dimensions on the parent table and the
    reference table defined in selected data grouping configurations must match. DQO will run the same data quality
    sensors on both the parent table (tested table) and the reference table (the source of truth), comparing the
    measures (sensor readouts) captured from both the tables.

    """

    additional_properties: Dict[str, "TableComparisonConfigurationSpec"] = attr.ib(
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
        from ..models.table_comparison_configuration_spec import (
            TableComparisonConfigurationSpec,
        )

        d = src_dict.copy()
        table_spec_table_comparisons = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = TableComparisonConfigurationSpec.from_dict(prop_dict)

            additional_properties[prop_name] = additional_property

        table_spec_table_comparisons.additional_properties = additional_properties
        return table_spec_table_comparisons

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "TableComparisonConfigurationSpec":
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: "TableComparisonConfigurationSpec") -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
