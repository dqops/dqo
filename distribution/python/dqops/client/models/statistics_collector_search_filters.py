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
        connection (Union[Unset, str]): The connection (data source) name. Supports search patterns in the format:
            'source\*', '\*_prod', 'prefix\*suffix'.
        full_table_name (Union[Unset, str]): The schema and table name. It is provided as *<schema_name>.<table_name>*,
            for example *public.fact_sales*. The schema and table name accept patterns both in the schema name and table
            name parts. Sample patterns are: 'schema_name.tab_prefix_\*', 'schema_name.*', '*.*', 'schema_name.\*_customer',
            'schema_name.tab_\*_suffix'.
        enabled (Union[Unset, bool]): A boolean flag to target enabled tables, columns or checks. When the value of this
            field is not set, the default value of this field is *true*, targeting only tables, columns and checks that are
            not implicitly disabled.
        tags (Union[Unset, List[str]]): An array of tags assigned to the table. All tags must be present on a table to
            match. The tags can use patterns:  'prefix\*', '\*suffix', 'prefix\*suffix'. The tags are assigned to the table
            on the data grouping screen when any of the data grouping hierarchy level is assigned a static value, which is a
            tag.
        labels (Union[Unset, List[str]]): An array of labels assigned to the table. All labels must be present on a
            table to match. The labels can use patterns:  'prefix\*', '\*suffix', 'prefix\*suffix'. The labels are assigned
            on the labels screen and stored in the *labels* node in the *.dqotable.yaml* file.
        max_results (Union[Unset, int]): Optional limit for the maximum number of results to return.
        column_names (Union[Unset, List[str]]): The list of column names or column name patters. This field accepts
            search patterns in the format: 'fk_\*', '\*_id', 'prefix\*suffix'.
        collector_name (Union[Unset, str]): The target statistics collector name to capture only selected statistics.
            Uses the short collector nameThis field supports search patterns such as: 'prefix\*', '\*suffix',
            'prefix_\*_suffix'. In order to collect only top 10 most common column samples, use 'column_samples'.
        sensor_name (Union[Unset, str]): The target sensor name to run only data quality checks that are using this
            sensor. Uses the full sensor name which is the full folder path within the *sensors* folder. This field supports
            search patterns such as: 'table/volume/row_\*', '\*_count', 'table/volume/prefix_\*_suffix'.
        collector_category (Union[Unset, str]): The target statistics collector category, for example: *nulls*,
            *volume*, *sampling*.
        target (Union[Unset, StatisticsCollectorTarget]):
        enabled_cron_schedule_expression (Union[Unset, str]): Expected CRON profiling schedule.
        collectors_hierarchy_ids_models (Union[Unset, List['HierarchyIdModel']]):
    """

    connection: Union[Unset, str] = UNSET
    full_table_name: Union[Unset, str] = UNSET
    enabled: Union[Unset, bool] = UNSET
    tags: Union[Unset, List[str]] = UNSET
    labels: Union[Unset, List[str]] = UNSET
    max_results: Union[Unset, int] = UNSET
    column_names: Union[Unset, List[str]] = UNSET
    collector_name: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    collector_category: Union[Unset, str] = UNSET
    target: Union[Unset, StatisticsCollectorTarget] = UNSET
    enabled_cron_schedule_expression: Union[Unset, str] = UNSET
    collectors_hierarchy_ids_models: Union[Unset, List["HierarchyIdModel"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection = self.connection
        full_table_name = self.full_table_name
        enabled = self.enabled
        tags: Union[Unset, List[str]] = UNSET
        if not isinstance(self.tags, Unset):
            tags = self.tags

        labels: Union[Unset, List[str]] = UNSET
        if not isinstance(self.labels, Unset):
            labels = self.labels

        max_results = self.max_results
        column_names: Union[Unset, List[str]] = UNSET
        if not isinstance(self.column_names, Unset):
            column_names = self.column_names

        collector_name = self.collector_name
        sensor_name = self.sensor_name
        collector_category = self.collector_category
        target: Union[Unset, str] = UNSET
        if not isinstance(self.target, Unset):
            target = self.target.value

        enabled_cron_schedule_expression = self.enabled_cron_schedule_expression
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
        if connection is not UNSET:
            field_dict["connection"] = connection
        if full_table_name is not UNSET:
            field_dict["fullTableName"] = full_table_name
        if enabled is not UNSET:
            field_dict["enabled"] = enabled
        if tags is not UNSET:
            field_dict["tags"] = tags
        if labels is not UNSET:
            field_dict["labels"] = labels
        if max_results is not UNSET:
            field_dict["maxResults"] = max_results
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
        if enabled_cron_schedule_expression is not UNSET:
            field_dict["enabledCronScheduleExpression"] = (
                enabled_cron_schedule_expression
            )
        if collectors_hierarchy_ids_models is not UNSET:
            field_dict["collectorsHierarchyIdsModels"] = collectors_hierarchy_ids_models

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.hierarchy_id_model import HierarchyIdModel

        d = src_dict.copy()
        connection = d.pop("connection", UNSET)

        full_table_name = d.pop("fullTableName", UNSET)

        enabled = d.pop("enabled", UNSET)

        tags = cast(List[str], d.pop("tags", UNSET))

        labels = cast(List[str], d.pop("labels", UNSET))

        max_results = d.pop("maxResults", UNSET)

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

        enabled_cron_schedule_expression = d.pop("enabledCronScheduleExpression", UNSET)

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
            connection=connection,
            full_table_name=full_table_name,
            enabled=enabled,
            tags=tags,
            labels=labels,
            max_results=max_results,
            column_names=column_names,
            collector_name=collector_name,
            sensor_name=sensor_name,
            collector_category=collector_category,
            target=target,
            enabled_cron_schedule_expression=enabled_cron_schedule_expression,
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
