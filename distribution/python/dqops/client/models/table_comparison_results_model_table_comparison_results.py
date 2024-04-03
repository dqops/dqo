from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.comparison_check_result_model import ComparisonCheckResultModel


T = TypeVar("T", bound="TableComparisonResultsModelTableComparisonResults")


@_attrs_define
class TableComparisonResultsModelTableComparisonResults:
    """The dictionary of comparison results between the tables for table level comparisons (e.g. row count). The keys for
    the dictionary are the check names. The value in the dictionary is a summary information about the most recent
    comparison.

    """

    additional_properties: Dict[str, "ComparisonCheckResultModel"] = _attrs_field(
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
        from ..models.comparison_check_result_model import ComparisonCheckResultModel

        d = src_dict.copy()
        table_comparison_results_model_table_comparison_results = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = ComparisonCheckResultModel.from_dict(prop_dict)

            additional_properties[prop_name] = additional_property

        table_comparison_results_model_table_comparison_results.additional_properties = (
            additional_properties
        )
        return table_comparison_results_model_table_comparison_results

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "ComparisonCheckResultModel":
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: "ComparisonCheckResultModel") -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
