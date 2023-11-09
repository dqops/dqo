from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_comparison_results_model_column_comparison_results import (
        TableComparisonResultsModelColumnComparisonResults,
    )
    from ..models.table_comparison_results_model_table_comparison_results import (
        TableComparisonResultsModelTableComparisonResults,
    )


T = TypeVar("T", bound="TableComparisonResultsModel")


@_attrs_define
class TableComparisonResultsModel:
    """The table comparison results model with the summary information about the most recent table comparison that was
    performed.

        Attributes:
            table_comparison_results (Union[Unset, TableComparisonResultsModelTableComparisonResults]): The dictionary of
                comparison results between the tables for table level comparisons (e.g. row count). The keys for the dictionary
                are the check names. The value in the dictionary is a summary information about the most recent comparison.
            column_comparison_results (Union[Unset, TableComparisonResultsModelColumnComparisonResults]): The dictionary of
                comparison results between the tables for each compared column. The keys for the dictionary are the column
                names. The values are dictionaries of the data quality check names and their results.
    """

    table_comparison_results: Union[
        Unset, "TableComparisonResultsModelTableComparisonResults"
    ] = UNSET
    column_comparison_results: Union[
        Unset, "TableComparisonResultsModelColumnComparisonResults"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        table_comparison_results: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.table_comparison_results, Unset):
            table_comparison_results = self.table_comparison_results.to_dict()

        column_comparison_results: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_comparison_results, Unset):
            column_comparison_results = self.column_comparison_results.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if table_comparison_results is not UNSET:
            field_dict["table_comparison_results"] = table_comparison_results
        if column_comparison_results is not UNSET:
            field_dict["column_comparison_results"] = column_comparison_results

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_comparison_results_model_column_comparison_results import (
            TableComparisonResultsModelColumnComparisonResults,
        )
        from ..models.table_comparison_results_model_table_comparison_results import (
            TableComparisonResultsModelTableComparisonResults,
        )

        d = src_dict.copy()
        _table_comparison_results = d.pop("table_comparison_results", UNSET)
        table_comparison_results: Union[
            Unset, TableComparisonResultsModelTableComparisonResults
        ]
        if isinstance(_table_comparison_results, Unset):
            table_comparison_results = UNSET
        else:
            table_comparison_results = (
                TableComparisonResultsModelTableComparisonResults.from_dict(
                    _table_comparison_results
                )
            )

        _column_comparison_results = d.pop("column_comparison_results", UNSET)
        column_comparison_results: Union[
            Unset, TableComparisonResultsModelColumnComparisonResults
        ]
        if isinstance(_column_comparison_results, Unset):
            column_comparison_results = UNSET
        else:
            column_comparison_results = (
                TableComparisonResultsModelColumnComparisonResults.from_dict(
                    _column_comparison_results
                )
            )

        table_comparison_results_model = cls(
            table_comparison_results=table_comparison_results,
            column_comparison_results=column_comparison_results,
        )

        table_comparison_results_model.additional_properties = d
        return table_comparison_results_model

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
