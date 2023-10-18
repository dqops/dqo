from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="TableComparisonGroupingColumnPairModel")


@_attrs_define
class TableComparisonGroupingColumnPairModel:
    """Model that identifies a pair of column names used for grouping the data on both the compared table and the reference
    table. The groups are then matched (joined) by DQOps to compare aggregated results.

        Attributes:
            compared_table_column_name (Union[Unset, str]): The name of the column on the compared table (the parent table)
                that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are
                calculated. This column is also used to join (match) results to the reference table.
            reference_table_column_name (Union[Unset, str]): The name of the column on the reference table (the source of
                truth) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are
                calculated. This column is also used to join (match) results to the compared table.
    """

    compared_table_column_name: Union[Unset, str] = UNSET
    reference_table_column_name: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        compared_table_column_name = self.compared_table_column_name
        reference_table_column_name = self.reference_table_column_name

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if compared_table_column_name is not UNSET:
            field_dict["compared_table_column_name"] = compared_table_column_name
        if reference_table_column_name is not UNSET:
            field_dict["reference_table_column_name"] = reference_table_column_name

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        compared_table_column_name = d.pop("compared_table_column_name", UNSET)

        reference_table_column_name = d.pop("reference_table_column_name", UNSET)

        table_comparison_grouping_column_pair_model = cls(
            compared_table_column_name=compared_table_column_name,
            reference_table_column_name=reference_table_column_name,
        )

        table_comparison_grouping_column_pair_model.additional_properties = d
        return table_comparison_grouping_column_pair_model

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
