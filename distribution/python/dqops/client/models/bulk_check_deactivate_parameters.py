from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.bulk_check_deactivate_parameters_selected_tables_to_columns import (
        BulkCheckDeactivateParametersSelectedTablesToColumns,
    )
    from ..models.check_search_filters import CheckSearchFilters


T = TypeVar("T", bound="BulkCheckDeactivateParameters")


@_attrs_define
class BulkCheckDeactivateParameters:
    """Parameter object for deactivating all checks that fit the filters.

    Attributes:
        check_search_filters (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        selected_tables_to_columns (Union[Unset, BulkCheckDeactivateParametersSelectedTablesToColumns]): List of
            concrete table and column names which will be the target. Column mappings are ignored for table level checks.
            This filter is applied at the end.
    """

    check_search_filters: Union[Unset, "CheckSearchFilters"] = UNSET
    selected_tables_to_columns: Union[
        Unset, "BulkCheckDeactivateParametersSelectedTablesToColumns"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_search_filters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.check_search_filters, Unset):
            check_search_filters = self.check_search_filters.to_dict()

        selected_tables_to_columns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.selected_tables_to_columns, Unset):
            selected_tables_to_columns = self.selected_tables_to_columns.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_search_filters is not UNSET:
            field_dict["check_search_filters"] = check_search_filters
        if selected_tables_to_columns is not UNSET:
            field_dict["selected_tables_to_columns"] = selected_tables_to_columns

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.bulk_check_deactivate_parameters_selected_tables_to_columns import (
            BulkCheckDeactivateParametersSelectedTablesToColumns,
        )
        from ..models.check_search_filters import CheckSearchFilters

        d = src_dict.copy()
        _check_search_filters = d.pop("check_search_filters", UNSET)
        check_search_filters: Union[Unset, CheckSearchFilters]
        if isinstance(_check_search_filters, Unset):
            check_search_filters = UNSET
        else:
            check_search_filters = CheckSearchFilters.from_dict(_check_search_filters)

        _selected_tables_to_columns = d.pop("selected_tables_to_columns", UNSET)
        selected_tables_to_columns: Union[
            Unset, BulkCheckDeactivateParametersSelectedTablesToColumns
        ]
        if isinstance(_selected_tables_to_columns, Unset):
            selected_tables_to_columns = UNSET
        else:
            selected_tables_to_columns = (
                BulkCheckDeactivateParametersSelectedTablesToColumns.from_dict(
                    _selected_tables_to_columns
                )
            )

        bulk_check_deactivate_parameters = cls(
            check_search_filters=check_search_filters,
            selected_tables_to_columns=selected_tables_to_columns,
        )

        bulk_check_deactivate_parameters.additional_properties = d
        return bulk_check_deactivate_parameters

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
