from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

import attr

from ..models.check_target import CheckTarget
from ..models.check_time_scale import CheckTimeScale
from ..models.check_type import CheckType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.hierarchy_id_model import HierarchyIdModel


T = TypeVar("T", bound="CheckSearchFilters")


@attr.s(auto_attribs=True)
class CheckSearchFilters:
    """Target data quality checks filter, identifies which checks on which tables and columns should be executed.

    Attributes:
        connection_name (Union[Unset, str]):
        schema_table_name (Union[Unset, str]):
        enabled (Union[Unset, bool]):
        tags (Union[Unset, List[str]]):
        labels (Union[Unset, List[str]]):
        column_name (Union[Unset, str]):
        column_data_type (Union[Unset, str]):
        column_nullable (Union[Unset, bool]):
        check_target (Union[Unset, CheckTarget]):
        check_type (Union[Unset, CheckType]):
        time_scale (Union[Unset, CheckTimeScale]):
        check_category (Union[Unset, str]):
        table_comparison_name (Union[Unset, str]):
        check_name (Union[Unset, str]):
        sensor_name (Union[Unset, str]):
        check_configured (Union[Unset, bool]):
        check_hierarchy_ids_models (Union[Unset, List['HierarchyIdModel']]):
    """

    connection_name: Union[Unset, str] = UNSET
    schema_table_name: Union[Unset, str] = UNSET
    enabled: Union[Unset, bool] = UNSET
    tags: Union[Unset, List[str]] = UNSET
    labels: Union[Unset, List[str]] = UNSET
    column_name: Union[Unset, str] = UNSET
    column_data_type: Union[Unset, str] = UNSET
    column_nullable: Union[Unset, bool] = UNSET
    check_target: Union[Unset, CheckTarget] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    time_scale: Union[Unset, CheckTimeScale] = UNSET
    check_category: Union[Unset, str] = UNSET
    table_comparison_name: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    check_configured: Union[Unset, bool] = UNSET
    check_hierarchy_ids_models: Union[Unset, List["HierarchyIdModel"]] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_table_name = self.schema_table_name
        enabled = self.enabled
        tags: Union[Unset, List[str]] = UNSET
        if not isinstance(self.tags, Unset):
            tags = self.tags

        labels: Union[Unset, List[str]] = UNSET
        if not isinstance(self.labels, Unset):
            labels = self.labels

        column_name = self.column_name
        column_data_type = self.column_data_type
        column_nullable = self.column_nullable
        check_target: Union[Unset, str] = UNSET
        if not isinstance(self.check_target, Unset):
            check_target = self.check_target.value

        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        time_scale: Union[Unset, str] = UNSET
        if not isinstance(self.time_scale, Unset):
            time_scale = self.time_scale.value

        check_category = self.check_category
        table_comparison_name = self.table_comparison_name
        check_name = self.check_name
        sensor_name = self.sensor_name
        check_configured = self.check_configured
        check_hierarchy_ids_models: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.check_hierarchy_ids_models, Unset):
            check_hierarchy_ids_models = []
            for check_hierarchy_ids_models_item_data in self.check_hierarchy_ids_models:
                check_hierarchy_ids_models_item = (
                    check_hierarchy_ids_models_item_data.to_dict()
                )

                check_hierarchy_ids_models.append(check_hierarchy_ids_models_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connectionName"] = connection_name
        if schema_table_name is not UNSET:
            field_dict["schemaTableName"] = schema_table_name
        if enabled is not UNSET:
            field_dict["enabled"] = enabled
        if tags is not UNSET:
            field_dict["tags"] = tags
        if labels is not UNSET:
            field_dict["labels"] = labels
        if column_name is not UNSET:
            field_dict["columnName"] = column_name
        if column_data_type is not UNSET:
            field_dict["columnDataType"] = column_data_type
        if column_nullable is not UNSET:
            field_dict["columnNullable"] = column_nullable
        if check_target is not UNSET:
            field_dict["checkTarget"] = check_target
        if check_type is not UNSET:
            field_dict["checkType"] = check_type
        if time_scale is not UNSET:
            field_dict["timeScale"] = time_scale
        if check_category is not UNSET:
            field_dict["checkCategory"] = check_category
        if table_comparison_name is not UNSET:
            field_dict["tableComparisonName"] = table_comparison_name
        if check_name is not UNSET:
            field_dict["checkName"] = check_name
        if sensor_name is not UNSET:
            field_dict["sensorName"] = sensor_name
        if check_configured is not UNSET:
            field_dict["checkConfigured"] = check_configured
        if check_hierarchy_ids_models is not UNSET:
            field_dict["checkHierarchyIdsModels"] = check_hierarchy_ids_models

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.hierarchy_id_model import HierarchyIdModel

        d = src_dict.copy()
        connection_name = d.pop("connectionName", UNSET)

        schema_table_name = d.pop("schemaTableName", UNSET)

        enabled = d.pop("enabled", UNSET)

        tags = cast(List[str], d.pop("tags", UNSET))

        labels = cast(List[str], d.pop("labels", UNSET))

        column_name = d.pop("columnName", UNSET)

        column_data_type = d.pop("columnDataType", UNSET)

        column_nullable = d.pop("columnNullable", UNSET)

        _check_target = d.pop("checkTarget", UNSET)
        check_target: Union[Unset, CheckTarget]
        if isinstance(_check_target, Unset):
            check_target = UNSET
        else:
            check_target = CheckTarget(_check_target)

        _check_type = d.pop("checkType", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        _time_scale = d.pop("timeScale", UNSET)
        time_scale: Union[Unset, CheckTimeScale]
        if isinstance(_time_scale, Unset):
            time_scale = UNSET
        else:
            time_scale = CheckTimeScale(_time_scale)

        check_category = d.pop("checkCategory", UNSET)

        table_comparison_name = d.pop("tableComparisonName", UNSET)

        check_name = d.pop("checkName", UNSET)

        sensor_name = d.pop("sensorName", UNSET)

        check_configured = d.pop("checkConfigured", UNSET)

        check_hierarchy_ids_models = []
        _check_hierarchy_ids_models = d.pop("checkHierarchyIdsModels", UNSET)
        for check_hierarchy_ids_models_item_data in _check_hierarchy_ids_models or []:
            check_hierarchy_ids_models_item = HierarchyIdModel.from_dict(
                check_hierarchy_ids_models_item_data
            )

            check_hierarchy_ids_models.append(check_hierarchy_ids_models_item)

        check_search_filters = cls(
            connection_name=connection_name,
            schema_table_name=schema_table_name,
            enabled=enabled,
            tags=tags,
            labels=labels,
            column_name=column_name,
            column_data_type=column_data_type,
            column_nullable=column_nullable,
            check_target=check_target,
            check_type=check_type,
            time_scale=time_scale,
            check_category=check_category,
            table_comparison_name=table_comparison_name,
            check_name=check_name,
            sensor_name=sensor_name,
            check_configured=check_configured,
            check_hierarchy_ids_models=check_hierarchy_ids_models,
        )

        check_search_filters.additional_properties = d
        return check_search_filters

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
