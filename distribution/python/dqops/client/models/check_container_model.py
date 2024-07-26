from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.schedule_enabled_status_model import ScheduleEnabledStatusModel
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.effective_schedule_model import EffectiveScheduleModel
    from ..models.quality_category_model import QualityCategoryModel


T = TypeVar("T", bound="CheckContainerModel")


@_attrs_define
class CheckContainerModel:
    """Model that returns the form definition and the form data to edit all data quality checks divided by categories.

    Attributes:
        categories (Union[Unset, List['QualityCategoryModel']]): List of all data quality categories that contain data
            quality checks inside.
        effective_schedule (Union[Unset, EffectiveScheduleModel]): Model of a configured schedule (enabled on connection
            or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution,
            as well as the duration until this time.
        effective_schedule_enabled_status (Union[Unset, ScheduleEnabledStatusModel]):
        partition_by_column (Union[Unset, str]): The name of the column that partitioned checks will use for the time
            period partitioning. Important only for partitioned checks.
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can edit the check.
        can_run_checks (Union[Unset, bool]): Boolean flag that decides if the current user can run checks.
        can_delete_data (Union[Unset, bool]): Boolean flag that decides if the current user can delete data (results).
    """

    categories: Union[Unset, List["QualityCategoryModel"]] = UNSET
    effective_schedule: Union[Unset, "EffectiveScheduleModel"] = UNSET
    effective_schedule_enabled_status: Union[Unset, ScheduleEnabledStatusModel] = UNSET
    partition_by_column: Union[Unset, str] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    can_run_checks: Union[Unset, bool] = UNSET
    can_delete_data: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        categories: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.categories, Unset):
            categories = []
            for categories_item_data in self.categories:
                categories_item = categories_item_data.to_dict()

                categories.append(categories_item)

        effective_schedule: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.effective_schedule, Unset):
            effective_schedule = self.effective_schedule.to_dict()

        effective_schedule_enabled_status: Union[Unset, str] = UNSET
        if not isinstance(self.effective_schedule_enabled_status, Unset):
            effective_schedule_enabled_status = (
                self.effective_schedule_enabled_status.value
            )

        partition_by_column = self.partition_by_column
        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        can_edit = self.can_edit
        can_run_checks = self.can_run_checks
        can_delete_data = self.can_delete_data

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if categories is not UNSET:
            field_dict["categories"] = categories
        if effective_schedule is not UNSET:
            field_dict["effective_schedule"] = effective_schedule
        if effective_schedule_enabled_status is not UNSET:
            field_dict["effective_schedule_enabled_status"] = (
                effective_schedule_enabled_status
            )
        if partition_by_column is not UNSET:
            field_dict["partition_by_column"] = partition_by_column
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if can_run_checks is not UNSET:
            field_dict["can_run_checks"] = can_run_checks
        if can_delete_data is not UNSET:
            field_dict["can_delete_data"] = can_delete_data

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.effective_schedule_model import EffectiveScheduleModel
        from ..models.quality_category_model import QualityCategoryModel

        d = src_dict.copy()
        categories = []
        _categories = d.pop("categories", UNSET)
        for categories_item_data in _categories or []:
            categories_item = QualityCategoryModel.from_dict(categories_item_data)

            categories.append(categories_item)

        _effective_schedule = d.pop("effective_schedule", UNSET)
        effective_schedule: Union[Unset, EffectiveScheduleModel]
        if isinstance(_effective_schedule, Unset):
            effective_schedule = UNSET
        else:
            effective_schedule = EffectiveScheduleModel.from_dict(_effective_schedule)

        _effective_schedule_enabled_status = d.pop(
            "effective_schedule_enabled_status", UNSET
        )
        effective_schedule_enabled_status: Union[Unset, ScheduleEnabledStatusModel]
        if isinstance(_effective_schedule_enabled_status, Unset):
            effective_schedule_enabled_status = UNSET
        else:
            effective_schedule_enabled_status = ScheduleEnabledStatusModel(
                _effective_schedule_enabled_status
            )

        partition_by_column = d.pop("partition_by_column", UNSET)

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

        can_edit = d.pop("can_edit", UNSET)

        can_run_checks = d.pop("can_run_checks", UNSET)

        can_delete_data = d.pop("can_delete_data", UNSET)

        check_container_model = cls(
            categories=categories,
            effective_schedule=effective_schedule,
            effective_schedule_enabled_status=effective_schedule_enabled_status,
            partition_by_column=partition_by_column,
            run_checks_job_template=run_checks_job_template,
            data_clean_job_template=data_clean_job_template,
            can_edit=can_edit,
            can_run_checks=can_run_checks,
            can_delete_data=can_delete_data,
        )

        check_container_model.additional_properties = d
        return check_container_model

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
