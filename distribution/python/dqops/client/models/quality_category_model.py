from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_model import CheckModel
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )


T = TypeVar("T", bound="QualityCategoryModel")


@_attrs_define
class QualityCategoryModel:
    """Model that returns the form definition and the form data to edit all checks within a single category.

    Attributes:
        category (Union[Unset, str]): Data quality check category name.
        comparison_name (Union[Unset, str]): The name of the reference table configuration used for a cross table data
            comparison (when the category is 'comparisons').
        compare_to_column (Union[Unset, str]): The name of the column in the reference table that is compared.
        help_text (Union[Unset, str]): Help text that describes the category.
        checks (Union[Unset, List['CheckModel']]): List of data quality checks within the category.
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
    """

    category: Union[Unset, str] = UNSET
    comparison_name: Union[Unset, str] = UNSET
    compare_to_column: Union[Unset, str] = UNSET
    help_text: Union[Unset, str] = UNSET
    checks: Union[Unset, List["CheckModel"]] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        category = self.category
        comparison_name = self.comparison_name
        compare_to_column = self.compare_to_column
        help_text = self.help_text
        checks: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.checks, Unset):
            checks = []
            for checks_item_data in self.checks:
                checks_item = checks_item_data.to_dict()

                checks.append(checks_item)

        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if category is not UNSET:
            field_dict["category"] = category
        if comparison_name is not UNSET:
            field_dict["comparison_name"] = comparison_name
        if compare_to_column is not UNSET:
            field_dict["compare_to_column"] = compare_to_column
        if help_text is not UNSET:
            field_dict["help_text"] = help_text
        if checks is not UNSET:
            field_dict["checks"] = checks
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_model import CheckModel
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )

        d = src_dict.copy()
        category = d.pop("category", UNSET)

        comparison_name = d.pop("comparison_name", UNSET)

        compare_to_column = d.pop("compare_to_column", UNSET)

        help_text = d.pop("help_text", UNSET)

        checks = []
        _checks = d.pop("checks", UNSET)
        for checks_item_data in _checks or []:
            checks_item = CheckModel.from_dict(checks_item_data)

            checks.append(checks_item)

        _run_checks_job_template = d.pop("run_checks_job_template", UNSET)
        run_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_checks_job_template, Unset):
            run_checks_job_template = UNSET
        else:
            run_checks_job_template = CheckSearchFilters.from_dict(
                _run_checks_job_template
            )

        _data_clean_job_template = d.pop("data_clean_job_template", UNSET)
        data_clean_job_template: Union[Unset, DeleteStoredDataQueueJobParameters]
        if isinstance(_data_clean_job_template, Unset):
            data_clean_job_template = UNSET
        else:
            data_clean_job_template = DeleteStoredDataQueueJobParameters.from_dict(
                _data_clean_job_template
            )

        quality_category_model = cls(
            category=category,
            comparison_name=comparison_name,
            compare_to_column=compare_to_column,
            help_text=help_text,
            checks=checks,
            run_checks_job_template=run_checks_job_template,
            data_clean_job_template=data_clean_job_template,
        )

        quality_category_model.additional_properties = d
        return quality_category_model

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
