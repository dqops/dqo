from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_comparison_column_results_model_column_comparison_results import (
        TableComparisonColumnResultsModelColumnComparisonResults,
    )


T = TypeVar("T", bound="TableComparisonColumnResultsModel")


@_attrs_define
class TableComparisonColumnResultsModel:
    """The table comparison column results model with the information about the most recent table comparison relating to a
    single compared column.

        Attributes:
            column_name (Union[Unset, str]): Column name
            column_comparison_results (Union[Unset, TableComparisonColumnResultsModelColumnComparisonResults]): The
                dictionary of comparison results between the tables for the specific column. The keys for the dictionary are
                check names. The values are summaries of the most recent comparison on this column.
    """

    column_name: Union[Unset, str] = UNSET
    column_comparison_results: Union[
        Unset, "TableComparisonColumnResultsModelColumnComparisonResults"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        column_name = self.column_name
        column_comparison_results: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_comparison_results, Unset):
            column_comparison_results = self.column_comparison_results.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if column_name is not UNSET:
            field_dict["column_name"] = column_name
        if column_comparison_results is not UNSET:
            field_dict["column_comparison_results"] = column_comparison_results

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_comparison_column_results_model_column_comparison_results import (
            TableComparisonColumnResultsModelColumnComparisonResults,
        )

        d = src_dict.copy()
        column_name = d.pop("column_name", UNSET)

        _column_comparison_results = d.pop("column_comparison_results", UNSET)
        column_comparison_results: Union[
            Unset, TableComparisonColumnResultsModelColumnComparisonResults
        ]
        if isinstance(_column_comparison_results, Unset):
            column_comparison_results = UNSET
        else:
            column_comparison_results = (
                TableComparisonColumnResultsModelColumnComparisonResults.from_dict(
                    _column_comparison_results
                )
            )

        table_comparison_column_results_model = cls(
            column_name=column_name,
            column_comparison_results=column_comparison_results,
        )

        table_comparison_column_results_model.additional_properties = d
        return table_comparison_column_results_model

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
