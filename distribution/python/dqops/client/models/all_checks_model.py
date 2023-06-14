from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.all_column_checks_model import AllColumnChecksModel
    from ..models.all_table_checks_model import AllTableChecksModel
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )


T = TypeVar("T", bound="AllChecksModel")


@attr.s(auto_attribs=True)
class AllChecksModel:
    """Model that returns the model of selected information related to all checks on a connection level.

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
        table_checks_model (Union[Unset, AllTableChecksModel]): Model containing selected information related to table
            checks on a connection level.
        column_checks_model (Union[Unset, AllColumnChecksModel]): Model containing selected information related to
            column checks on a connection level.
    """

    connection_name: Union[Unset, str] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    table_checks_model: Union[Unset, "AllTableChecksModel"] = UNSET
    column_checks_model: Union[Unset, "AllColumnChecksModel"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        table_checks_model: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.table_checks_model, Unset):
            table_checks_model = self.table_checks_model.to_dict()

        column_checks_model: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_checks_model, Unset):
            column_checks_model = self.column_checks_model.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template
        if table_checks_model is not UNSET:
            field_dict["table_checks_model"] = table_checks_model
        if column_checks_model is not UNSET:
            field_dict["column_checks_model"] = column_checks_model

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.all_column_checks_model import AllColumnChecksModel
        from ..models.all_table_checks_model import AllTableChecksModel
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

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

        _table_checks_model = d.pop("table_checks_model", UNSET)
        table_checks_model: Union[Unset, AllTableChecksModel]
        if isinstance(_table_checks_model, Unset):
            table_checks_model = UNSET
        else:
            table_checks_model = AllTableChecksModel.from_dict(_table_checks_model)

        _column_checks_model = d.pop("column_checks_model", UNSET)
        column_checks_model: Union[Unset, AllColumnChecksModel]
        if isinstance(_column_checks_model, Unset):
            column_checks_model = UNSET
        else:
            column_checks_model = AllColumnChecksModel.from_dict(_column_checks_model)

        all_checks_model = cls(
            connection_name=connection_name,
            run_checks_job_template=run_checks_job_template,
            data_clean_job_template=data_clean_job_template,
            table_checks_model=table_checks_model,
            column_checks_model=column_checks_model,
        )

        all_checks_model.additional_properties = d
        return all_checks_model

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
