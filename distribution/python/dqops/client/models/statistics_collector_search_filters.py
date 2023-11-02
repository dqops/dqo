from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.statistics_collector_target import StatisticsCollectorTarget
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.hierarchy_id_model import HierarchyIdModel


T = TypeVar("T", bound="StatisticsCollectorSearchFilters")


@_attrs_define
class StatisticsCollectorSearchFilters:
    r"""
    Attributes:
        connection_name (Union[Unset, str]): The connection (data source) name. Supports search patterns in the format:
            'source\*', '\*_prod', 'prefix\*suffix'.
        schema_table_name (Union[Unset, str]):
        enabled (Union[Unset, bool]):
        tags (Union[Unset, List[str]]):
        labels (Union[Unset, List[str]]):
        column_names (Union[Unset, List[str]]):
        collector_name (Union[Unset, str]):
        sensor_name (Union[Unset, str]):
        collector_category (Union[Unset, str]):
        target (Union[Unset, StatisticsCollectorTarget]):
        collectors_hierarchy_ids_models (Union[Unset, List['HierarchyIdModel']]):
    """

    connection_name: Union[Unset, str] = UNSET
    schema_table_name: Union[Unset, str] = UNSET
    enabled: Union[Unset, bool] = UNSET
    tags: Union[Unset, List[str]] = UNSET
    labels: Union[Unset, List[str]] = UNSET
    column_names: Union[Unset, List[str]] = UNSET
    collector_name: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    collector_category: Union[Unset, str] = UNSET
    target: Union[Unset, StatisticsCollectorTarget] = UNSET
    collectors_hierarchy_ids_models: Union[Unset, List["HierarchyIdModel"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

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

        column_names: Union[Unset, List[str]] = UNSET
        if not isinstance(self.column_names, Unset):
            column_names = self.column_names

        collector_name = self.collector_name
        sensor_name = self.sensor_name
        collector_category = self.collector_category
        target: Union[Unset, str] = UNSET
        if not isinstance(self.target, Unset):
            target = self.target.value

        collectors_hierarchy_ids_models: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.collectors_hierarchy_ids_models, Unset):
            collectors_hierarchy_ids_models = []
            for (
                collectors_hierarchy_ids_models_item_data
            ) in self.collectors_hierarchy_ids_models:
                collectors_hierarchy_ids_models_item = (
                    collectors_hierarchy_ids_models_item_data.to_dict()
                )

                collectors_hierarchy_ids_models.append(
                    collectors_hierarchy_ids_models_item
                )

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
        if column_names is not UNSET:
            field_dict["columnNames"] = column_names
        if collector_name is not UNSET:
            field_dict["collectorName"] = collector_name
        if sensor_name is not UNSET:
            field_dict["sensorName"] = sensor_name
        if collector_category is not UNSET:
            field_dict["collectorCategory"] = collector_category
        if target is not UNSET:
            field_dict["target"] = target
        if collectors_hierarchy_ids_models is not UNSET:
            field_dict["collectorsHierarchyIdsModels"] = collectors_hierarchy_ids_models

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

        column_names = cast(List[str], d.pop("columnNames", UNSET))

        collector_name = d.pop("collectorName", UNSET)

        sensor_name = d.pop("sensorName", UNSET)

        collector_category = d.pop("collectorCategory", UNSET)

        _target = d.pop("target", UNSET)
        target: Union[Unset, StatisticsCollectorTarget]
        if isinstance(_target, Unset):
            target = UNSET
        else:
            target = StatisticsCollectorTarget(_target)

        collectors_hierarchy_ids_models = []
        _collectors_hierarchy_ids_models = d.pop("collectorsHierarchyIdsModels", UNSET)
        for collectors_hierarchy_ids_models_item_data in (
            _collectors_hierarchy_ids_models or []
        ):
            collectors_hierarchy_ids_models_item = HierarchyIdModel.from_dict(
                collectors_hierarchy_ids_models_item_data
            )

            collectors_hierarchy_ids_models.append(collectors_hierarchy_ids_models_item)

        statistics_collector_search_filters = cls(
            connection_name=connection_name,
            schema_table_name=schema_table_name,
            enabled=enabled,
            tags=tags,
            labels=labels,
            column_names=column_names,
            collector_name=collector_name,
            sensor_name=sensor_name,
            collector_category=collector_category,
            target=target,
            collectors_hierarchy_ids_models=collectors_hierarchy_ids_models,
        )

        statistics_collector_search_filters.additional_properties = d
        return statistics_collector_search_filters

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
