from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.table_checks_model import TableChecksModel


T = TypeVar("T", bound="SchemaTableChecksModel")


@attr.s(auto_attribs=True)
class SchemaTableChecksModel:
    """Model containing information related to table checks on a schema level.

    Attributes:
        schema_name (Union[Unset, str]): Schema name.
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
        table_checks_models (Union[Unset, List['TableChecksModel']]): Tables in schema, containing information related
            to table checks.
    """

    schema_name: Union[Unset, str] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    table_checks_models: Union[Unset, List["TableChecksModel"]] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        schema_name = self.schema_name
        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        table_checks_models: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.table_checks_models, Unset):
            table_checks_models = []
            for table_checks_models_item_data in self.table_checks_models:
                table_checks_models_item = table_checks_models_item_data.to_dict()

                table_checks_models.append(table_checks_models_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if schema_name is not UNSET:
            field_dict["schema_name"] = schema_name
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template
        if table_checks_models is not UNSET:
            field_dict["table_checks_models"] = table_checks_models

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.table_checks_model import TableChecksModel

        d = src_dict.copy()
        schema_name = d.pop("schema_name", UNSET)

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

        table_checks_models = []
        _table_checks_models = d.pop("table_checks_models", UNSET)
        for table_checks_models_item_data in _table_checks_models or []:
            table_checks_models_item = TableChecksModel.from_dict(
                table_checks_models_item_data
            )

            table_checks_models.append(table_checks_models_item)

        schema_table_checks_model = cls(
            schema_name=schema_name,
            run_checks_job_template=run_checks_job_template,
            data_clean_job_template=data_clean_job_template,
            table_checks_models=table_checks_models,
        )

        schema_table_checks_model.additional_properties = d
        return schema_table_checks_model

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
