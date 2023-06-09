from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.ui_table_checks_model_check_containers import (
        UITableChecksModelCheckContainers,
    )


T = TypeVar("T", bound="UITableChecksModel")


@attr.s(auto_attribs=True)
class UITableChecksModel:
    """UI model containing information related to table-level checks on a table.

    Attributes:
        table_name (Union[Unset, str]): Table name.
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
        check_containers (Union[Unset, UITableChecksModelCheckContainers]): Mapping of check type and timescale to check
            container on this table.
    """

    table_name: Union[Unset, str] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    check_containers: Union[Unset, "UITableChecksModelCheckContainers"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        table_name = self.table_name
        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        check_containers: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.check_containers, Unset):
            check_containers = self.check_containers.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if table_name is not UNSET:
            field_dict["table_name"] = table_name
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template
        if check_containers is not UNSET:
            field_dict["check_containers"] = check_containers

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.ui_table_checks_model_check_containers import (
            UITableChecksModelCheckContainers,
        )

        d = src_dict.copy()
        table_name = d.pop("table_name", UNSET)

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

        _check_containers = d.pop("check_containers", UNSET)
        check_containers: Union[Unset, UITableChecksModelCheckContainers]
        if isinstance(_check_containers, Unset):
            check_containers = UNSET
        else:
            check_containers = UITableChecksModelCheckContainers.from_dict(
                _check_containers
            )

        ui_table_checks_model = cls(
            table_name=table_name,
            run_checks_job_template=run_checks_job_template,
            data_clean_job_template=data_clean_job_template,
            check_containers=check_containers,
        )

        ui_table_checks_model.additional_properties = d
        return ui_table_checks_model

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
