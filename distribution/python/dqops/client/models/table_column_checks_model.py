from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.column_checks_model import ColumnChecksModel
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.physical_table_name import PhysicalTableName


T = TypeVar("T", bound="TableColumnChecksModel")


@attr.s(auto_attribs=True)
class TableColumnChecksModel:
    """Model containing information related to column checks on a table level.

    Attributes:
        schema_table_name (Union[Unset, PhysicalTableName]):
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
        column_checks_models (Union[Unset, List['ColumnChecksModel']]): List containing information related to checks on
            each column.
    """

    schema_table_name: Union[Unset, "PhysicalTableName"] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    column_checks_models: Union[Unset, List["ColumnChecksModel"]] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        schema_table_name: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.schema_table_name, Unset):
            schema_table_name = self.schema_table_name.to_dict()

        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        column_checks_models: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.column_checks_models, Unset):
            column_checks_models = []
            for column_checks_models_item_data in self.column_checks_models:
                column_checks_models_item = column_checks_models_item_data.to_dict()

                column_checks_models.append(column_checks_models_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if schema_table_name is not UNSET:
            field_dict["schema_table_name"] = schema_table_name
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template
        if column_checks_models is not UNSET:
            field_dict["column_checks_models"] = column_checks_models

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.column_checks_model import ColumnChecksModel
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.physical_table_name import PhysicalTableName

        d = src_dict.copy()
        _schema_table_name = d.pop("schema_table_name", UNSET)
        schema_table_name: Union[Unset, PhysicalTableName]
        if isinstance(_schema_table_name, Unset):
            schema_table_name = UNSET
        else:
            schema_table_name = PhysicalTableName.from_dict(_schema_table_name)

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

        column_checks_models = []
        _column_checks_models = d.pop("column_checks_models", UNSET)
        for column_checks_models_item_data in _column_checks_models or []:
            column_checks_models_item = ColumnChecksModel.from_dict(
                column_checks_models_item_data
            )

            column_checks_models.append(column_checks_models_item)

        table_column_checks_model = cls(
            schema_table_name=schema_table_name,
            run_checks_job_template=run_checks_job_template,
            data_clean_job_template=data_clean_job_template,
            column_checks_models=column_checks_models,
        )

        table_column_checks_model.additional_properties = d
        return table_column_checks_model

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
