from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.all_column_checks_model_check_target import (
    AllColumnChecksModelCheckTarget,
)
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.table_column_checks_model import TableColumnChecksModel


T = TypeVar("T", bound="AllColumnChecksModel")


@attr.s(auto_attribs=True)
class AllColumnChecksModel:
    """Model containing selected information related to column checks on a connection level.

    Attributes:
        check_target (Union[Unset, AllColumnChecksModelCheckTarget]): Check target.
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
        table_column_checks_models (Union[Unset, List['TableColumnChecksModel']]): Flattened table list containing
            information related to columns in each table.
    """

    check_target: Union[Unset, AllColumnChecksModelCheckTarget] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    table_column_checks_models: Union[Unset, List["TableColumnChecksModel"]] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_target: Union[Unset, str] = UNSET
        if not isinstance(self.check_target, Unset):
            check_target = self.check_target.value

        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        table_column_checks_models: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.table_column_checks_models, Unset):
            table_column_checks_models = []
            for table_column_checks_models_item_data in self.table_column_checks_models:
                table_column_checks_models_item = (
                    table_column_checks_models_item_data.to_dict()
                )

                table_column_checks_models.append(table_column_checks_models_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_target is not UNSET:
            field_dict["check_target"] = check_target
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template
        if table_column_checks_models is not UNSET:
            field_dict["table_column_checks_models"] = table_column_checks_models

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.table_column_checks_model import TableColumnChecksModel

        d = src_dict.copy()
        _check_target = d.pop("check_target", UNSET)
        check_target: Union[Unset, AllColumnChecksModelCheckTarget]
        if isinstance(_check_target, Unset):
            check_target = UNSET
        else:
            check_target = AllColumnChecksModelCheckTarget(_check_target)

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

        table_column_checks_models = []
        _table_column_checks_models = d.pop("table_column_checks_models", UNSET)
        for table_column_checks_models_item_data in _table_column_checks_models or []:
            table_column_checks_models_item = TableColumnChecksModel.from_dict(
                table_column_checks_models_item_data
            )

            table_column_checks_models.append(table_column_checks_models_item)

        all_column_checks_model = cls(
            check_target=check_target,
            run_checks_job_template=run_checks_job_template,
            data_clean_job_template=data_clean_job_template,
            table_column_checks_models=table_column_checks_models,
        )

        all_column_checks_model.additional_properties = d
        return all_column_checks_model

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
